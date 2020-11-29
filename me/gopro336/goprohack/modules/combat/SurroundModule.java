//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.modules.combat;

import net.minecraft.item.ItemStack;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemBlock;
import net.minecraft.block.Block;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.math.AxisAlignedBB;
import me.gopro336.goprohack.util.entity.PlayerUtil;
import net.minecraft.network.play.client.CPacketEntityAction;
import java.util.function.Predicate;
import me.gopro336.goprohack.util.BlockInteractionHelper;
import net.minecraft.init.Blocks;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.entity.Entity;
import me.gopro336.goprohack.util.MathUtil;
import me.gopro336.goprohack.main.GoproHack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import me.gopro336.goprohack.events.MinecraftEvent;
import me.zero.alpine.fork.listener.EventHandler;
import me.gopro336.goprohack.events.player.EventPlayerMotionUpdate;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.util.math.Vec3d;
import me.gopro336.goprohack.modules.Value;
import me.gopro336.goprohack.modules.Module;

public class SurroundModule extends Module
{
    public final Value<Boolean> disable;
    public final Value<Boolean> ToggleOffGround;
    public final Value<CenterModes> CenterMode;
    public final Value<Boolean> rotate;
    public final Value<Integer> BlocksPerTick;
    public final Value<Boolean> ActivateOnlyOnShift;
    private Vec3d Center;
    @EventHandler
    private Listener<EventPlayerMotionUpdate> OnPlayerUpdate;
    
    public SurroundModule() {
        super("Surround", new String[] { "NoCrystal" }, "Automatically surrounds you with obsidian in the four cardinal direrctions", "NONE", 5448923, ModuleType.COMBAT);
        this.disable = new Value<Boolean>("Toggles", new String[] { "Toggles", "Disables" }, "Will toggle off after a place", false);
        this.ToggleOffGround = new Value<Boolean>("ToggleOffGround", new String[] { "Toggles", "Disables" }, "Will toggle off after a place", false);
        this.CenterMode = new Value<CenterModes>("Center", new String[] { "Center" }, "Moves you to center of block", CenterModes.NCP);
        this.rotate = new Value<Boolean>("Rotate", new String[] { "rotate" }, "Rotate", true);
        this.BlocksPerTick = new Value<Integer>("BlocksPerTick", new String[] { "BPT" }, "Blocks per tick", 1, 1, 10, 1);
        this.ActivateOnlyOnShift = new Value<Boolean>("ActivateOnlyOnShift", new String[] { "AoOS" }, "Activates only when shift is pressed.", false);
        this.Center = Vec3d.ZERO;
        double l_XDiff;
        double l_ZDiff;
        double l_MotionX;
        double l_MotionZ;
        Vec3d pos;
        BlockPos interpPos;
        BlockPos north;
        BlockPos south;
        BlockPos east;
        BlockPos west;
        BlockPos[] l_Array;
        int slot;
        int lastSlot;
        int l_BlocksPerTick;
        final BlockPos[] array;
        int length;
        int i = 0;
        BlockPos l_Pos;
        BlockInteractionHelper.ValidResult l_Result;
        BlockPos[] array2;
        BlockPos[] l_Test;
        int length2;
        int j = 0;
        BlockPos l_Pos2;
        BlockInteractionHelper.ValidResult l_Result2;
        float[] rotations;
        float[] rotations2;
        this.OnPlayerUpdate = new Listener<EventPlayerMotionUpdate>(p_Event -> {
            if (p_Event.getEra() == MinecraftEvent.Era.PRE) {
                if (this.ActivateOnlyOnShift.getValue()) {
                    if (!this.mc.gameSettings.keyBindSneak.isKeyDown()) {
                        this.Center = Vec3d.ZERO;
                        return;
                    }
                    else if (this.Center == Vec3d.ZERO) {
                        this.Center = this.GetCenter(this.mc.player.posX, this.mc.player.posY, this.mc.player.posZ);
                        if (this.CenterMode.getValue() != CenterModes.None) {
                            this.mc.player.motionX = 0.0;
                            this.mc.player.motionZ = 0.0;
                        }
                        if (this.CenterMode.getValue() == CenterModes.Teleport) {
                            this.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(this.Center.x, this.Center.y, this.Center.z, true));
                            this.mc.player.setPosition(this.Center.x, this.Center.y, this.Center.z);
                        }
                    }
                }
                if (this.Center != Vec3d.ZERO && this.CenterMode.getValue() == CenterModes.NCP) {
                    l_XDiff = Math.abs(this.Center.x - this.mc.player.posX);
                    l_ZDiff = Math.abs(this.Center.z - this.mc.player.posZ);
                    if (l_XDiff <= 0.1 && l_ZDiff <= 0.1) {
                        this.Center = Vec3d.ZERO;
                    }
                    else {
                        l_MotionX = this.Center.x - this.mc.player.posX;
                        l_MotionZ = this.Center.z - this.mc.player.posZ;
                        this.mc.player.motionX = l_MotionX / 2.0;
                        this.mc.player.motionZ = l_MotionZ / 2.0;
                    }
                }
                if (!this.mc.player.onGround && !this.mc.player.prevOnGround && !this.ActivateOnlyOnShift.getValue() && this.ToggleOffGround.getValue()) {
                    this.toggle();
                    GoproHack.SendMessage("[Surround]: You are off ground! toggling!");
                }
                else {
                    pos = MathUtil.interpolateEntity((Entity)this.mc.player, this.mc.getRenderPartialTicks());
                    interpPos = new BlockPos(pos.x, pos.y, pos.z);
                    north = interpPos.north();
                    south = interpPos.south();
                    east = interpPos.east();
                    west = interpPos.west();
                    l_Array = new BlockPos[] { north, south, east, west };
                    if (!this.IsSurrounded((EntityPlayer)this.mc.player)) {
                        slot = this.findStackHotbar(Blocks.OBSIDIAN);
                        if ((this.hasStack(Blocks.OBSIDIAN) || slot != -1) && this.mc.player.onGround) {
                            lastSlot = this.mc.player.inventory.currentItem;
                            this.mc.player.inventory.currentItem = slot;
                            this.mc.playerController.updateController();
                            l_BlocksPerTick = this.BlocksPerTick.getValue();
                            for (length = array.length; i < length; ++i) {
                                l_Pos = array[i];
                                l_Result = BlockInteractionHelper.valid(l_Pos);
                                if (l_Result != BlockInteractionHelper.ValidResult.AlreadyBlockThere || this.mc.world.getBlockState(l_Pos).getMaterial().isReplaceable()) {
                                    if (l_Result == BlockInteractionHelper.ValidResult.NoNeighbors) {
                                        l_Test = (array2 = new BlockPos[] { l_Pos.down(), l_Pos.north(), l_Pos.south(), l_Pos.east(), l_Pos.west(), l_Pos.up() });
                                        length2 = array2.length;
                                        while (j < length2) {
                                            l_Pos2 = array2[j];
                                            l_Result2 = BlockInteractionHelper.valid(l_Pos2);
                                            if (l_Result2 != BlockInteractionHelper.ValidResult.NoNeighbors && l_Result2 != BlockInteractionHelper.ValidResult.NoEntityCollision) {
                                                BlockInteractionHelper.place(l_Pos2, 5.0f, false, false);
                                                p_Event.cancel();
                                                rotations = BlockInteractionHelper.getLegitRotations(new Vec3d((double)l_Pos2.getX(), (double)l_Pos2.getY(), (double)l_Pos2.getZ()));
                                                this.ProcessBlizzFacing(rotations[0], rotations[1]);
                                                break;
                                            }
                                            else {
                                                ++j;
                                            }
                                        }
                                    }
                                    else {
                                        BlockInteractionHelper.place(l_Pos, 5.0f, false, false);
                                        p_Event.cancel();
                                        rotations2 = BlockInteractionHelper.getLegitRotations(new Vec3d((double)l_Pos.getX(), (double)l_Pos.getY(), (double)l_Pos.getZ()));
                                        this.ProcessBlizzFacing(rotations2[0], rotations2[1]);
                                        if (--l_BlocksPerTick <= 0) {
                                            break;
                                        }
                                    }
                                }
                            }
                            if (!this.slotEqualsBlock(lastSlot, Blocks.OBSIDIAN)) {
                                this.mc.player.inventory.currentItem = lastSlot;
                            }
                            this.mc.playerController.updateController();
                            if (this.disable.getValue()) {
                                this.toggle();
                            }
                        }
                    }
                }
            }
        }, (Predicate<EventPlayerMotionUpdate>[])new Predicate[0]);
    }
    
    @Override
    public String getMetaData() {
        return this.CenterMode.getValue().toString();
    }
    
    @Override
    public void onEnable() {
        super.onEnable();
        if (this.mc.player == null) {
            this.toggle();
            return;
        }
        if (this.ActivateOnlyOnShift.getValue()) {
            return;
        }
        this.Center = this.GetCenter(this.mc.player.posX, this.mc.player.posY, this.mc.player.posZ);
        if (this.CenterMode.getValue() != CenterModes.None) {
            this.mc.player.motionX = 0.0;
            this.mc.player.motionZ = 0.0;
        }
        if (this.CenterMode.getValue() == CenterModes.Teleport) {
            this.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(this.Center.x, this.Center.y, this.Center.z, true));
            this.mc.player.setPosition(this.Center.x, this.Center.y, this.Center.z);
        }
    }
    
    @Override
    public void toggleNoSave() {
    }
    
    private void ProcessBlizzFacing(final float p_Yaw, final float p_Pitch) {
        final boolean l_IsSprinting = this.mc.player.isSprinting();
        if (l_IsSprinting != this.mc.player.serverSprintState) {
            if (l_IsSprinting) {
                this.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)this.mc.player, CPacketEntityAction.Action.START_SPRINTING));
            }
            else {
                this.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)this.mc.player, CPacketEntityAction.Action.STOP_SPRINTING));
            }
            this.mc.player.serverSprintState = l_IsSprinting;
        }
        final boolean l_IsSneaking = this.mc.player.isSneaking();
        if (l_IsSneaking != this.mc.player.serverSneakState) {
            if (l_IsSneaking) {
                this.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)this.mc.player, CPacketEntityAction.Action.START_SNEAKING));
            }
            else {
                this.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)this.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
            }
            this.mc.player.serverSneakState = l_IsSneaking;
        }
        if (PlayerUtil.isCurrentViewEntity()) {
            final float l_Pitch = p_Pitch;
            final float l_Yaw = p_Yaw;
            final AxisAlignedBB axisalignedbb = this.mc.player.getEntityBoundingBox();
            final double l_PosXDifference = this.mc.player.posX - this.mc.player.lastReportedPosX;
            final double l_PosYDifference = axisalignedbb.minY - this.mc.player.lastReportedPosY;
            final double l_PosZDifference = this.mc.player.posZ - this.mc.player.lastReportedPosZ;
            final double l_YawDifference = l_Yaw - this.mc.player.lastReportedYaw;
            final double l_RotationDifference = l_Pitch - this.mc.player.lastReportedPitch;
            final EntityPlayerSP player = this.mc.player;
            ++player.positionUpdateTicks;
            boolean l_MovedXYZ = l_PosXDifference * l_PosXDifference + l_PosYDifference * l_PosYDifference + l_PosZDifference * l_PosZDifference > 9.0E-4 || this.mc.player.positionUpdateTicks >= 20;
            final boolean l_MovedRotation = l_YawDifference != 0.0 || l_RotationDifference != 0.0;
            if (this.mc.player.isRiding()) {
                this.mc.player.connection.sendPacket((Packet)new CPacketPlayer.PositionRotation(this.mc.player.motionX, -999.0, this.mc.player.motionZ, l_Yaw, l_Pitch, this.mc.player.onGround));
                l_MovedXYZ = false;
            }
            else if (l_MovedXYZ && l_MovedRotation) {
                this.mc.player.connection.sendPacket((Packet)new CPacketPlayer.PositionRotation(this.mc.player.posX, axisalignedbb.minY, this.mc.player.posZ, l_Yaw, l_Pitch, this.mc.player.onGround));
            }
            else if (l_MovedXYZ) {
                this.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(this.mc.player.posX, axisalignedbb.minY, this.mc.player.posZ, this.mc.player.onGround));
            }
            else if (l_MovedRotation) {
                this.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(l_Yaw, l_Pitch, this.mc.player.onGround));
            }
            else if (this.mc.player.prevOnGround != this.mc.player.onGround) {
                this.mc.player.connection.sendPacket((Packet)new CPacketPlayer(this.mc.player.onGround));
            }
            if (l_MovedXYZ) {
                this.mc.player.lastReportedPosX = this.mc.player.posX;
                this.mc.player.lastReportedPosY = axisalignedbb.minY;
                this.mc.player.lastReportedPosZ = this.mc.player.posZ;
                this.mc.player.positionUpdateTicks = 0;
            }
            if (l_MovedRotation) {
                this.mc.player.lastReportedYaw = l_Yaw;
                this.mc.player.lastReportedPitch = l_Pitch;
            }
            this.mc.player.prevOnGround = this.mc.player.onGround;
            this.mc.player.autoJumpEnabled = this.mc.player.mc.gameSettings.autoJump;
        }
    }
    
    public boolean IsSurrounded(final EntityPlayer p_Who) {
        final Vec3d l_PlayerPos = MathUtil.interpolateEntity((Entity)this.mc.player, this.mc.getRenderPartialTicks());
        final BlockPos l_InterpPos = new BlockPos(l_PlayerPos.x, l_PlayerPos.y, l_PlayerPos.z);
        final BlockPos l_North = l_InterpPos.north();
        final BlockPos l_South = l_InterpPos.south();
        final BlockPos l_East = l_InterpPos.east();
        final BlockPos l_West = l_InterpPos.west();
        final BlockPos[] array;
        final BlockPos[] l_Array = array = new BlockPos[] { l_North, l_South, l_East, l_West };
        for (final BlockPos l_Pos : array) {
            if (BlockInteractionHelper.valid(l_Pos) != BlockInteractionHelper.ValidResult.AlreadyBlockThere) {
                return false;
            }
        }
        return true;
    }
    
    public boolean hasStack(final Block type) {
        if (this.mc.player.inventory.getCurrentItem().getItem() instanceof ItemBlock) {
            final ItemBlock block = (ItemBlock)this.mc.player.inventory.getCurrentItem().getItem();
            return block.getBlock() == type;
        }
        return false;
    }
    
    private boolean slotEqualsBlock(final int slot, final Block type) {
        if (this.mc.player.inventory.getStackInSlot(slot).getItem() instanceof ItemBlock) {
            final ItemBlock block = (ItemBlock)this.mc.player.inventory.getStackInSlot(slot).getItem();
            return block.getBlock() == type;
        }
        return false;
    }
    
    private int findStackHotbar(final Block type) {
        for (int i = 0; i < 9; ++i) {
            final ItemStack stack = Minecraft.getMinecraft().player.inventory.getStackInSlot(i);
            if (stack.getItem() instanceof ItemBlock) {
                final ItemBlock block = (ItemBlock)stack.getItem();
                if (block.getBlock() == type) {
                    return i;
                }
            }
        }
        return -1;
    }
    
    public Vec3d GetCenter(final double posX, final double posY, final double posZ) {
        final double x = Math.floor(posX) + 0.5;
        final double y = Math.floor(posY);
        final double z = Math.floor(posZ) + 0.5;
        return new Vec3d(x, y, z);
    }
    
    public boolean HasObsidian() {
        return this.findStackHotbar(Blocks.OBSIDIAN) != -1;
    }
    
    public enum CenterModes
    {
        Teleport, 
        NCP, 
        None;
    }
}
