//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.modules.movement;

import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketPlayer;
import java.util.function.Predicate;
import net.minecraft.network.Packet;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.item.ItemElytra;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.world.World;
import net.minecraft.util.EnumHand;
import net.minecraft.init.Items;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import me.gopro336.goprohack.events.MinecraftEvent;
import me.gopro336.goprohack.events.network.EventNetworkPacketEvent;
import me.zero.alpine.fork.listener.EventHandler;
import me.gopro336.goprohack.events.player.EventPlayerMotionUpdate;
import me.zero.alpine.fork.listener.Listener;
import me.gopro336.goprohack.util.Timer;
import me.gopro336.goprohack.modules.Value;
import me.gopro336.goprohack.modules.Module;

public final class NoFallModule extends Module
{
    public final Value<Mode> mode;
    public final Value<Boolean> NoVoid;
    private boolean SendElytraPacket;
    private boolean SendInvPackets;
    private int ElytraSlot;
    private Timer ReplaceChestTimer;
    @EventHandler
    private Listener<EventPlayerMotionUpdate> OnPlayerUpdate;
    @EventHandler
    private Listener<EventNetworkPacketEvent> PacketEvent;
    
    public NoFallModule() {
        super("NoFall", new String[] { "NoFallDamage" }, "Prevents fall damage", "NONE", 5131167, ModuleType.MOVEMENT);
        this.mode = new Value<Mode>("Mode", new String[] { "Mode", "M" }, "NoFall Module to use", Mode.Packet);
        this.NoVoid = new Value<Boolean>("NoVoid", new String[] { "NoVoid" }, "Prevents you from falling into the void", true);
        this.SendElytraPacket = false;
        this.SendInvPackets = false;
        this.ElytraSlot = -1;
        this.ReplaceChestTimer = new Timer();
        RayTraceResult l_Trace;
        int l_CollisionHeight;
        int l_DistanceCheck;
        int l_I;
        int l_I2;
        int l_I3;
        ItemStack l_Stack;
        ItemElytra l_Elytra;
        this.OnPlayerUpdate = new Listener<EventPlayerMotionUpdate>(p_Event -> {
            if (p_Event.getEra() != MinecraftEvent.Era.PRE) {
                return;
            }
            else if (this.mc.player.isElytraFlying()) {
                if (this.SendElytraPacket && this.ReplaceChestTimer.passed(1000.0)) {
                    this.SendElytraPacket = false;
                    this.SendInvPackets = false;
                    if (this.ElytraSlot != -1) {
                        this.mc.playerController.windowClick(this.mc.player.inventoryContainer.windowId, this.ElytraSlot, 0, ClickType.PICKUP, (EntityPlayer)this.mc.player);
                        this.mc.playerController.windowClick(this.mc.player.inventoryContainer.windowId, 6, 0, ClickType.PICKUP, (EntityPlayer)this.mc.player);
                        this.mc.playerController.windowClick(this.mc.player.inventoryContainer.windowId, this.ElytraSlot, 0, ClickType.PICKUP, (EntityPlayer)this.mc.player);
                        this.mc.playerController.updateController();
                        this.ElytraSlot = -1;
                    }
                }
                return;
            }
            else {
                if (this.NoVoid.getValue() && this.mc.player.posY <= 5.0) {
                    l_Trace = this.mc.world.rayTraceBlocks(this.mc.player.getPositionVector(), new Vec3d(this.mc.player.posX, 0.0, this.mc.player.posZ), false, false, false);
                    if (l_Trace == null || l_Trace.typeOfHit != RayTraceResult.Type.BLOCK) {
                        this.mc.player.setVelocity(0.0, 0.0, 0.0);
                    }
                }
                if (this.mc.player.fallDistance >= 3.0f) {
                    l_CollisionHeight = -1;
                    for (l_DistanceCheck = ((this.mode.getValue() == Mode.Bucket) ? 5 : 8), l_I = (int)this.mc.player.posY; l_I > this.mc.player.posY - l_DistanceCheck; --l_I) {
                        if (!this.mc.world.isAirBlock(new BlockPos(this.mc.player.posX, (double)l_I, this.mc.player.posZ))) {
                            if (this.mc.world.getBlockState(new BlockPos(this.mc.player.posX, (double)l_I, this.mc.player.posZ)).getMaterial() != Material.WATER) {
                                l_CollisionHeight = l_I;
                                break;
                            }
                        }
                    }
                    if (l_CollisionHeight != -1) {
                        if (this.mode.getValue() == Mode.Bucket) {
                            if (this.mc.player.getHeldItemMainhand().getItem() != Items.WATER_BUCKET) {
                                l_I2 = 0;
                                while (l_I2 < 9) {
                                    if (this.mc.player.inventory.getStackInSlot(l_I2).getItem() == Items.WATER_BUCKET) {
                                        this.mc.player.inventory.currentItem = l_I2;
                                        this.mc.playerController.updateController();
                                        break;
                                    }
                                    else {
                                        ++l_I2;
                                    }
                                }
                            }
                            this.mc.player.rotationPitch = 90.0f;
                            this.mc.playerController.processRightClick((EntityPlayer)this.mc.player, (World)this.mc.world, EnumHand.MAIN_HAND);
                        }
                        else if (this.mode.getValue() == Mode.Elytra && this.mc.player.fallDistance > 3.0f && !this.mc.player.onGround) {
                            if (this.mc.player.getItemStackFromSlot(EntityEquipmentSlot.CHEST).getItem() != Items.ELYTRA) {
                                for (l_I3 = 0; l_I3 < 44; ++l_I3) {
                                    l_Stack = this.mc.player.inventory.getStackInSlot(l_I3);
                                    if (!l_Stack.isEmpty()) {
                                        if (l_Stack.getItem() == Items.ELYTRA) {
                                            l_Elytra = (ItemElytra)l_Stack.getItem();
                                            if (l_Elytra.getDurabilityForDisplay(l_Stack) != 0.0) {
                                                this.ElytraSlot = l_I3;
                                                break;
                                            }
                                        }
                                    }
                                }
                                if (this.ElytraSlot != -1 && !this.SendInvPackets) {
                                    this.SendInvPackets = true;
                                    this.mc.playerController.windowClick(this.mc.player.inventoryContainer.windowId, this.ElytraSlot, 0, ClickType.PICKUP, (EntityPlayer)this.mc.player);
                                    this.mc.playerController.windowClick(this.mc.player.inventoryContainer.windowId, 6, 0, ClickType.PICKUP, (EntityPlayer)this.mc.player);
                                    this.mc.playerController.windowClick(this.mc.player.inventoryContainer.windowId, this.ElytraSlot, 0, ClickType.PICKUP, (EntityPlayer)this.mc.player);
                                    this.mc.playerController.updateController();
                                }
                            }
                            else {
                                this.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)this.mc.player, CPacketEntityAction.Action.START_FALL_FLYING));
                                this.SendElytraPacket = true;
                                this.ReplaceChestTimer.reset();
                            }
                        }
                    }
                }
                else {
                    this.SendInvPackets = false;
                    this.SendElytraPacket = false;
                    this.ReplaceChestTimer.reset();
                }
                return;
            }
        }, (Predicate<EventPlayerMotionUpdate>[])new Predicate[0]);
        CPacketPlayer packet;
        this.PacketEvent = new Listener<EventNetworkPacketEvent>(p_Event -> {
            if (p_Event.getPacket() instanceof CPacketPlayer) {
                if (!this.mc.player.isElytraFlying()) {
                    packet = (CPacketPlayer)p_Event.getPacket();
                    switch (this.mode.getValue()) {
                        case Packet: {
                            if (this.mc.player.fallDistance > 3.0f) {
                                packet.onGround = true;
                                break;
                            }
                            else {
                                break;
                            }
                            break;
                        }
                        case Anti: {
                            if (this.mc.player.fallDistance > 3.0f) {
                                packet.y = this.mc.player.posY + 0.10000000149011612;
                                break;
                            }
                            else {
                                break;
                            }
                            break;
                        }
                    }
                }
            }
        }, (Predicate<EventNetworkPacketEvent>[])new Predicate[0]);
    }
    
    @Override
    public void onEnable() {
        super.onEnable();
        this.SendElytraPacket = false;
        this.SendInvPackets = false;
    }
    
    @Override
    public String getMetaData() {
        return this.mode.getValue().toString();
    }
    
    private enum Mode
    {
        None, 
        Packet, 
        Anti, 
        Bucket, 
        Elytra;
    }
}
