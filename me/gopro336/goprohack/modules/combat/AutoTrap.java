//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.modules.combat;

import java.util.List;
import net.minecraft.block.BlockObsidian;
import net.minecraft.block.BlockEnderChest;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.block.Block;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.EnumFacing;
import me.gopro336.goprohack.util.BlockInteractionHelper;
import com.mojang.realmsclient.gui.ChatFormatting;
import java.util.Iterator;
import net.minecraft.entity.player.EntityPlayer;
import java.util.function.Predicate;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import java.util.Collection;
import java.util.Collections;
import java.util.ArrayList;
import net.minecraft.entity.Entity;
import me.gopro336.goprohack.util.entity.PlayerUtil;
import me.gopro336.goprohack.main.GoproHack;
import me.gopro336.goprohack.util.GoproUtil;
import me.gopro336.goprohack.events.MinecraftEvent;
import me.zero.alpine.fork.listener.EventHandler;
import me.gopro336.goprohack.events.player.EventPlayerMotionUpdate;
import me.zero.alpine.fork.listener.Listener;
import me.gopro336.goprohack.modules.Value;
import net.minecraft.util.math.Vec3d;
import me.gopro336.goprohack.modules.Module;

public final class AutoTrap extends Module
{
    private final Vec3d[] offsetsDefault;
    private final Vec3d[] offsetsTall;
    public final Value<Boolean> toggleMode;
    public final Value<Float> range;
    public final Value<Integer> blockPerTick;
    public final Value<Boolean> rotate;
    public final Value<Boolean> announceUsage;
    public final Value<Boolean> EChests;
    public final Value<Modes> Mode;
    private String lastTickTargetName;
    private int playerHotbarSlot;
    private int lastHotbarSlot;
    private boolean isSneaking;
    private int offsetStep;
    private boolean firstRun;
    @EventHandler
    private Listener<EventPlayerMotionUpdate> OnPlayerUpdate;
    
    public AutoTrap() {
        super("AutoTrap", new String[] { "AutoTrap" }, "Traps enemies in obsidian", "NONE", 5131167, ModuleType.COMBAT);
        this.offsetsDefault = new Vec3d[] { new Vec3d(0.0, 0.0, -1.0), new Vec3d(1.0, 0.0, 0.0), new Vec3d(0.0, 0.0, 1.0), new Vec3d(-1.0, 0.0, 0.0), new Vec3d(0.0, 1.0, -1.0), new Vec3d(1.0, 1.0, 0.0), new Vec3d(0.0, 1.0, 1.0), new Vec3d(-1.0, 1.0, 0.0), new Vec3d(0.0, 2.0, -1.0), new Vec3d(1.0, 2.0, 0.0), new Vec3d(0.0, 2.0, 1.0), new Vec3d(-1.0, 2.0, 0.0), new Vec3d(0.0, 3.0, -1.0), new Vec3d(0.0, 3.0, 0.0) };
        this.offsetsTall = new Vec3d[] { new Vec3d(0.0, 0.0, -1.0), new Vec3d(1.0, 0.0, 0.0), new Vec3d(0.0, 0.0, 1.0), new Vec3d(-1.0, 0.0, 0.0), new Vec3d(0.0, 1.0, -1.0), new Vec3d(1.0, 1.0, 0.0), new Vec3d(0.0, 1.0, 1.0), new Vec3d(-1.0, 1.0, 0.0), new Vec3d(0.0, 2.0, -1.0), new Vec3d(1.0, 2.0, 0.0), new Vec3d(0.0, 2.0, 1.0), new Vec3d(-1.0, 2.0, 0.0), new Vec3d(0.0, 3.0, -1.0), new Vec3d(0.0, 3.0, 0.0), new Vec3d(0.0, 4.0, 0.0) };
        this.toggleMode = new Value<Boolean>("toggleMode", new String[] { "toggleMode " }, "ToggleMode", true);
        this.range = new Value<Float>("range", new String[] { "range" }, "Range", 5.5f, 0.0f, 10.0f, 1.0f);
        this.blockPerTick = new Value<Integer>("blockPerTick", new String[] { "blockPerTick" }, "Blocks per Tick", 4, 1, 10, 1);
        this.rotate = new Value<Boolean>("rotate", new String[] { "rotate" }, "Rotate", true);
        this.announceUsage = new Value<Boolean>("announceUsage", new String[] { "announceUsage" }, "Announce Usage", true);
        this.EChests = new Value<Boolean>("EChests", new String[] { "EChests" }, "EChests", false);
        this.Mode = new Value<Modes>("Mode", new String[] { "Mode" }, "The mode to use for autotrap", Modes.Full);
        this.lastTickTargetName = "";
        this.playerHotbarSlot = -1;
        this.lastHotbarSlot = -1;
        this.isSneaking = false;
        this.offsetStep = 0;
        this.firstRun = true;
        EntityPlayer closestTarget;
        ArrayList<Object> placeTargets;
        int blocksPlaced;
        BlockPos offsetPos;
        BlockPos targetPos;
        boolean shouldTryToPlace;
        final Iterator<Entity> iterator;
        Entity entity;
        this.OnPlayerUpdate = new Listener<EventPlayerMotionUpdate>(p_Event -> {
            if (p_Event.getEra() == MinecraftEvent.Era.PRE) {
                closestTarget = GoproUtil.findClosestTarget();
                if (closestTarget == null) {
                    if (this.firstRun) {
                        this.firstRun = false;
                        if (this.announceUsage.getValue()) {
                            GoproHack.SendMessage("[AutoTrap] Enabled, waiting for target.");
                        }
                    }
                }
                else {
                    if (this.firstRun) {
                        this.firstRun = false;
                        this.lastTickTargetName = closestTarget.getName();
                        if (this.announceUsage.getValue()) {
                            GoproHack.SendMessage("[AutoTrap] Enabled, target: " + this.lastTickTargetName);
                        }
                    }
                    else if (!this.lastTickTargetName.equals(closestTarget.getName())) {
                        this.lastTickTargetName = closestTarget.getName();
                        this.offsetStep = 0;
                        if (this.announceUsage.getValue()) {
                            GoproHack.SendMessage("[AutoTrap] New target: " + this.lastTickTargetName);
                        }
                    }
                    if (this.toggleMode.getValue() && PlayerUtil.IsEntityTrapped((Entity)closestTarget)) {
                        this.toggle();
                    }
                    else {
                        placeTargets = new ArrayList<Object>();
                        switch (this.Mode.getValue()) {
                            case Full: {
                                Collections.addAll(placeTargets, this.offsetsDefault);
                                break;
                            }
                            case Tall: {
                                Collections.addAll(placeTargets, this.offsetsTall);
                                break;
                            }
                        }
                        blocksPlaced = 0;
                        while (blocksPlaced < this.blockPerTick.getValue()) {
                            if (this.offsetStep >= placeTargets.size()) {
                                this.offsetStep = 0;
                                break;
                            }
                            else {
                                offsetPos = new BlockPos((Vec3d)placeTargets.get(this.offsetStep));
                                targetPos = new BlockPos(closestTarget.getPositionVector()).down().add(offsetPos.getX(), offsetPos.getY(), offsetPos.getZ());
                                shouldTryToPlace = true;
                                if (!this.mc.world.getBlockState(targetPos).getMaterial().isReplaceable()) {
                                    shouldTryToPlace = false;
                                }
                                this.mc.world.getEntitiesWithinAABBExcludingEntity((Entity)null, new AxisAlignedBB(targetPos)).iterator();
                                while (iterator.hasNext()) {
                                    entity = iterator.next();
                                    if (!(entity instanceof EntityItem) && !(entity instanceof EntityXPOrb)) {
                                        shouldTryToPlace = false;
                                        break;
                                    }
                                }
                                if (shouldTryToPlace && this.placeBlock(targetPos)) {
                                    ++blocksPlaced;
                                }
                                ++this.offsetStep;
                            }
                        }
                        if (blocksPlaced > 0) {
                            if (this.lastHotbarSlot != this.playerHotbarSlot && this.playerHotbarSlot != -1) {
                                this.mc.player.inventory.currentItem = this.playerHotbarSlot;
                                this.lastHotbarSlot = this.playerHotbarSlot;
                            }
                            if (this.isSneaking) {
                                this.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)this.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
                                this.isSneaking = false;
                            }
                        }
                    }
                }
            }
        }, (Predicate<EventPlayerMotionUpdate>[])new Predicate[0]);
    }
    
    @Override
    public String getMetaData() {
        if (this.EChests.getValue()) {
            return "Ender Chests";
        }
        return "Obsidian";
    }
    
    @Override
    public void toggleNoSave() {
    }
    
    @Override
    public void onEnable() {
        super.onEnable();
        if (this.mc.player == null) {
            this.toggle();
            return;
        }
        this.firstRun = true;
        this.playerHotbarSlot = this.mc.player.inventory.currentItem;
        this.lastHotbarSlot = -1;
        if (this.findObiInHotbar() == -1) {
            GoproHack.SendMessage(String.format("[AutoTrap] You do not have any %s in your hotbar!", ChatFormatting.LIGHT_PURPLE + (this.EChests.getValue() ? "Ender Chests" : "Obsidian") + ChatFormatting.RESET));
            this.toggle();
        }
    }
    
    @Override
    public void onDisable() {
        super.onDisable();
        if (this.lastHotbarSlot != this.playerHotbarSlot && this.playerHotbarSlot != -1) {
            this.mc.player.inventory.currentItem = this.playerHotbarSlot;
        }
        if (this.isSneaking) {
            this.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)this.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
            this.isSneaking = false;
        }
        this.playerHotbarSlot = -1;
        this.lastHotbarSlot = -1;
        if (this.announceUsage.getValue()) {
            GoproHack.SendMessage("[AutoTrap] Disabled!");
        }
    }
    
    private boolean placeBlock(final BlockPos pos) {
        if (!this.mc.world.getBlockState(pos).getMaterial().isReplaceable()) {
            return false;
        }
        if (!BlockInteractionHelper.checkForNeighbours(pos)) {
            return false;
        }
        final Vec3d eyesPos = new Vec3d(this.mc.player.posX, this.mc.player.posY + this.mc.player.getEyeHeight(), this.mc.player.posZ);
        for (final EnumFacing side : EnumFacing.values()) {
            final BlockPos neighbor = pos.offset(side);
            final EnumFacing side2 = side.getOpposite();
            if (this.mc.world.getBlockState(neighbor).getBlock().canCollideCheck(this.mc.world.getBlockState(neighbor), false)) {
                final Vec3d hitVec = new Vec3d((Vec3i)neighbor).add(0.5, 0.5, 0.5).add(new Vec3d(side2.getDirectionVec()).scale(0.5));
                if (eyesPos.distanceTo(hitVec) <= this.range.getValue()) {
                    final int obiSlot = this.findObiInHotbar();
                    if (obiSlot == -1) {
                        this.toggle();
                        return false;
                    }
                    if (this.lastHotbarSlot != obiSlot) {
                        this.mc.player.inventory.currentItem = obiSlot;
                        this.lastHotbarSlot = obiSlot;
                    }
                    final Block neighborPos = this.mc.world.getBlockState(neighbor).getBlock();
                    if (BlockInteractionHelper.blackList.contains(neighborPos) || BlockInteractionHelper.shulkerList.contains(neighborPos)) {
                        this.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)this.mc.player, CPacketEntityAction.Action.START_SNEAKING));
                        this.isSneaking = true;
                    }
                    if (this.rotate.getValue()) {
                        BlockInteractionHelper.faceVectorPacketInstant(hitVec);
                    }
                    this.mc.playerController.processRightClickBlock(this.mc.player, this.mc.world, neighbor, side2, hitVec, EnumHand.MAIN_HAND);
                    this.mc.player.swingArm(EnumHand.MAIN_HAND);
                    return true;
                }
            }
        }
        return false;
    }
    
    private int findObiInHotbar() {
        for (int i = 0; i < 9; ++i) {
            final ItemStack stack = this.mc.player.inventory.getStackInSlot(i);
            if (stack != ItemStack.EMPTY && stack.getItem() instanceof ItemBlock) {
                final Block block = ((ItemBlock)stack.getItem()).getBlock();
                if (this.EChests.getValue()) {
                    if (block instanceof BlockEnderChest) {
                        return i;
                    }
                }
                else if (block instanceof BlockObsidian) {
                    return i;
                }
            }
        }
        return -1;
    }
    
    public enum Modes
    {
        Full, 
        Tall;
    }
}
