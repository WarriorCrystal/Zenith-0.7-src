//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.modules.world;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.BlockEnderChest;
import java.util.Iterator;
import net.minecraft.item.ItemStack;
import java.util.function.Predicate;
import net.minecraft.network.Packet;
import net.minecraft.util.EnumFacing;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.util.EnumHand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import java.util.Comparator;
import net.minecraft.entity.Entity;
import me.gopro336.goprohack.util.entity.EntityUtil;
import me.gopro336.goprohack.util.BlockInteractionHelper;
import me.gopro336.goprohack.util.entity.PlayerUtil;
import net.minecraft.util.math.BlockPos;
import me.gopro336.goprohack.events.MinecraftEvent;
import me.zero.alpine.fork.listener.EventHandler;
import me.gopro336.goprohack.events.player.EventPlayerMotionUpdate;
import me.zero.alpine.fork.listener.Listener;
import me.gopro336.goprohack.util.Timer;
import me.gopro336.goprohack.modules.Value;
import me.gopro336.goprohack.modules.Module;

public class EnderChestFarmer extends Module
{
    public static Value<Integer> Radius;
    public Value<Float> Delay;
    private Timer PlaceTimer;
    @EventHandler
    private Listener<EventPlayerMotionUpdate> OnPlayerUpdate;
    
    public EnderChestFarmer() {
        super("ObsidianMaker", new String[] { "ObsidianMaker" }, "Autoamatically places enderchests around you, and attempts to mine it", "NONE", -1, ModuleType.WORLD);
        this.Delay = new Value<Float>("Delay", new String[] { "D" }, "Timed delay for each place of ender chest", 1.0f, 0.0f, 10.0f, 1.0f);
        this.PlaceTimer = new Timer();
        BlockPos l_ClosestPos;
        boolean hasPickaxe;
        int i;
        ItemStack stack;
        double[] l_Pos;
        int slot;
        final Iterator<BlockPos> iterator;
        BlockPos pos;
        BlockInteractionHelper.PlaceResult result;
        double[] rotations;
        this.OnPlayerUpdate = new Listener<EventPlayerMotionUpdate>(p_Event -> {
            if (p_Event.getEra() == MinecraftEvent.Era.PRE) {
                l_ClosestPos = BlockInteractionHelper.getSphere(PlayerUtil.GetLocalPlayerPosFloored(), EnderChestFarmer.Radius.getValue(), EnderChestFarmer.Radius.getValue(), false, true, 0).stream().filter(p_Pos -> this.IsValidBlockPos(p_Pos)).min(Comparator.comparing(p_Pos -> EntityUtil.GetDistanceOfEntityToBlock((Entity)this.mc.player, p_Pos))).orElse(null);
                if (l_ClosestPos != null) {
                    hasPickaxe = (this.mc.player.getHeldItemMainhand().getItem() == Items.DIAMOND_PICKAXE);
                    if (!hasPickaxe) {
                        for (i = 0; i < 9; ++i) {
                            stack = this.mc.player.inventory.getStackInSlot(i);
                            if (!stack.isEmpty()) {
                                if (stack.getItem() == Items.DIAMOND_PICKAXE) {
                                    hasPickaxe = true;
                                    this.mc.player.inventory.currentItem = i;
                                    this.mc.playerController.updateController();
                                    break;
                                }
                            }
                        }
                    }
                    if (!(!hasPickaxe)) {
                        p_Event.cancel();
                        l_Pos = EntityUtil.calculateLookAt(l_ClosestPos.getX() + 0.5, l_ClosestPos.getY() - 0.5, l_ClosestPos.getZ() + 0.5, (EntityPlayer)this.mc.player);
                        PlayerUtil.PacketFacePitchAndYaw((float)l_Pos[1], (float)l_Pos[0]);
                        this.mc.player.swingArm(EnumHand.MAIN_HAND);
                        this.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, l_ClosestPos, EnumFacing.UP));
                        this.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, l_ClosestPos, EnumFacing.UP));
                    }
                }
                else if (!(!this.PlaceTimer.passed(this.Delay.getValue() * 1000.0f))) {
                    this.PlaceTimer.reset();
                    if (!this.IsCurrItemEnderChest()) {
                        slot = this.GetEnderChestSlot();
                        if (slot == -1) {
                            return;
                        }
                        else {
                            this.mc.player.inventory.currentItem = slot;
                            this.mc.playerController.updateController();
                        }
                    }
                    BlockInteractionHelper.getSphere(PlayerUtil.GetLocalPlayerPosFloored(), EnderChestFarmer.Radius.getValue(), EnderChestFarmer.Radius.getValue(), false, true, 0).iterator();
                    while (iterator.hasNext()) {
                        pos = iterator.next();
                        result = BlockInteractionHelper.place(pos, EnderChestFarmer.Radius.getValue(), true, false);
                        if (result == BlockInteractionHelper.PlaceResult.Placed) {
                            p_Event.cancel();
                            rotations = EntityUtil.calculateLookAt(pos.getX() + 0.5, pos.getY() - 0.5, pos.getZ() + 0.5, (EntityPlayer)this.mc.player);
                            PlayerUtil.PacketFacePitchAndYaw((float)rotations[1], (float)rotations[0]);
                        }
                    }
                }
            }
        }, (Predicate<EventPlayerMotionUpdate>[])new Predicate[0]);
    }
    
    private boolean IsValidBlockPos(final BlockPos p_Pos) {
        final IBlockState l_State = this.mc.world.getBlockState(p_Pos);
        return l_State.getBlock() instanceof BlockEnderChest;
    }
    
    private boolean IsCurrItemEnderChest() {
        if (this.mc.player.getHeldItemMainhand().getItem() instanceof ItemBlock) {
            final ItemBlock block = (ItemBlock)this.mc.player.getHeldItemMainhand().getItem();
            return block.getBlock() == Blocks.ENDER_CHEST;
        }
        return false;
    }
    
    private int GetEnderChestSlot() {
        for (int i = 0; i < 9; ++i) {
            final ItemStack stack = this.mc.player.inventory.getStackInSlot(i);
            if (!stack.isEmpty()) {
                if (stack.getItem() instanceof ItemBlock) {
                    final ItemBlock block = (ItemBlock)stack.getItem();
                    if (block.getBlock() == Blocks.ENDER_CHEST) {
                        return i;
                    }
                }
            }
        }
        return -1;
    }
    
    static {
        EnderChestFarmer.Radius = new Value<Integer>("Radius", new String[] { "R" }, "Radius to search for enderchests, and place them", 4, 0, 10, 1);
    }
}
