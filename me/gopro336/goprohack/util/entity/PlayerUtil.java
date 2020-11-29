//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.util.entity;

import net.minecraft.util.math.MathHelper;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemFood;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.BlockPos;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;
import java.text.DecimalFormat;
import net.minecraft.client.Minecraft;

public class PlayerUtil
{
    private static Minecraft mc;
    static final DecimalFormat Formatter;
    
    public static int GetItemSlot(final Item input) {
        if (PlayerUtil.mc.player == null) {
            return 0;
        }
        for (int i = 0; i < PlayerUtil.mc.player.inventoryContainer.getInventory().size(); ++i) {
            if (i != 0 && i != 5 && i != 6 && i != 7) {
                if (i != 8) {
                    final ItemStack s = (ItemStack)PlayerUtil.mc.player.inventoryContainer.getInventory().get(i);
                    if (!s.isEmpty()) {
                        if (s.getItem() == input) {
                            return i;
                        }
                    }
                }
            }
        }
        return -1;
    }
    
    public static int GetRecursiveItemSlot(final Item input) {
        if (PlayerUtil.mc.player == null) {
            return 0;
        }
        for (int i = PlayerUtil.mc.player.inventoryContainer.getInventory().size() - 1; i > 0; --i) {
            if (i != 0 && i != 5 && i != 6 && i != 7) {
                if (i != 8) {
                    final ItemStack s = (ItemStack)PlayerUtil.mc.player.inventoryContainer.getInventory().get(i);
                    if (!s.isEmpty()) {
                        if (s.getItem() == input) {
                            return i;
                        }
                    }
                }
            }
        }
        return -1;
    }
    
    public static int GetItemSlotNotHotbar(final Item input) {
        if (PlayerUtil.mc.player == null) {
            return 0;
        }
        for (int i = 9; i < 36; ++i) {
            final Item item = PlayerUtil.mc.player.inventory.getStackInSlot(i).getItem();
            if (item == input) {
                return i;
            }
        }
        return -1;
    }
    
    public static int GetItemCount(final Item input) {
        if (PlayerUtil.mc.player == null) {
            return 0;
        }
        int items = 0;
        for (int i = 0; i < 45; ++i) {
            final ItemStack stack = PlayerUtil.mc.player.inventory.getStackInSlot(i);
            if (stack.getItem() == input) {
                items += stack.getCount();
            }
        }
        return items;
    }
    
    public static boolean CanSeeBlock(final BlockPos p_Pos) {
        return PlayerUtil.mc.player != null && PlayerUtil.mc.world.rayTraceBlocks(new Vec3d(PlayerUtil.mc.player.posX, PlayerUtil.mc.player.posY + PlayerUtil.mc.player.getEyeHeight(), PlayerUtil.mc.player.posZ), new Vec3d((double)p_Pos.getX(), (double)p_Pos.getY(), (double)p_Pos.getZ()), false, true, false) == null;
    }
    
    public static boolean isCurrentViewEntity() {
        return PlayerUtil.mc.getRenderViewEntity() == PlayerUtil.mc.player;
    }
    
    public static boolean IsEating() {
        return PlayerUtil.mc.player != null && PlayerUtil.mc.player.getHeldItemMainhand().getItem() instanceof ItemFood && PlayerUtil.mc.player.isHandActive();
    }
    
    public static int GetItemInHotbar(final Item p_Item) {
        for (int l_I = 0; l_I < 9; ++l_I) {
            final ItemStack l_Stack = PlayerUtil.mc.player.inventory.getStackInSlot(l_I);
            if (l_Stack != ItemStack.EMPTY && l_Stack.getItem() == p_Item) {
                return l_I;
            }
        }
        return -1;
    }
    
    public static BlockPos GetLocalPlayerPosFloored() {
        return new BlockPos(Math.floor(PlayerUtil.mc.player.posX), Math.floor(PlayerUtil.mc.player.posY), Math.floor(PlayerUtil.mc.player.posZ));
    }
    
    public static BlockPos EntityPosToFloorBlockPos(final Entity e) {
        return new BlockPos(Math.floor(e.posX), Math.floor(e.posY), Math.floor(e.posZ));
    }
    
    public static float GetHealthWithAbsorption() {
        return PlayerUtil.mc.player.getHealth() + PlayerUtil.mc.player.getAbsorptionAmount();
    }
    
    public static boolean IsPlayerInHole() {
        final BlockPos blockPos = GetLocalPlayerPosFloored();
        final IBlockState blockState = PlayerUtil.mc.world.getBlockState(blockPos);
        if (blockState.getBlock() != Blocks.AIR) {
            return false;
        }
        if (PlayerUtil.mc.world.getBlockState(blockPos.up()).getBlock() != Blocks.AIR) {
            return false;
        }
        if (PlayerUtil.mc.world.getBlockState(blockPos.down()).getBlock() == Blocks.AIR) {
            return false;
        }
        final BlockPos[] touchingBlocks = { blockPos.north(), blockPos.south(), blockPos.east(), blockPos.west() };
        int validHorizontalBlocks = 0;
        for (final BlockPos touching : touchingBlocks) {
            final IBlockState touchingState = PlayerUtil.mc.world.getBlockState(touching);
            if (touchingState.getBlock() != Blocks.AIR && touchingState.isFullBlock()) {
                ++validHorizontalBlocks;
            }
        }
        return validHorizontalBlocks >= 4;
    }
    
    public static void PacketFacePitchAndYaw(final float p_Pitch, final float p_Yaw) {
        final boolean l_IsSprinting = PlayerUtil.mc.player.isSprinting();
        if (l_IsSprinting != PlayerUtil.mc.player.serverSprintState) {
            if (l_IsSprinting) {
                PlayerUtil.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)PlayerUtil.mc.player, CPacketEntityAction.Action.START_SPRINTING));
            }
            else {
                PlayerUtil.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)PlayerUtil.mc.player, CPacketEntityAction.Action.STOP_SPRINTING));
            }
            PlayerUtil.mc.player.serverSprintState = l_IsSprinting;
        }
        final boolean l_IsSneaking = PlayerUtil.mc.player.isSneaking();
        if (l_IsSneaking != PlayerUtil.mc.player.serverSneakState) {
            if (l_IsSneaking) {
                PlayerUtil.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)PlayerUtil.mc.player, CPacketEntityAction.Action.START_SNEAKING));
            }
            else {
                PlayerUtil.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)PlayerUtil.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
            }
            PlayerUtil.mc.player.serverSneakState = l_IsSneaking;
        }
        if (isCurrentViewEntity()) {
            final float l_Pitch = p_Pitch;
            final float l_Yaw = p_Yaw;
            final AxisAlignedBB axisalignedbb = PlayerUtil.mc.player.getEntityBoundingBox();
            final double l_PosXDifference = PlayerUtil.mc.player.posX - PlayerUtil.mc.player.lastReportedPosX;
            final double l_PosYDifference = axisalignedbb.minY - PlayerUtil.mc.player.lastReportedPosY;
            final double l_PosZDifference = PlayerUtil.mc.player.posZ - PlayerUtil.mc.player.lastReportedPosZ;
            final double l_YawDifference = l_Yaw - PlayerUtil.mc.player.lastReportedYaw;
            final double l_RotationDifference = l_Pitch - PlayerUtil.mc.player.lastReportedPitch;
            final EntityPlayerSP player = PlayerUtil.mc.player;
            ++player.positionUpdateTicks;
            boolean l_MovedXYZ = l_PosXDifference * l_PosXDifference + l_PosYDifference * l_PosYDifference + l_PosZDifference * l_PosZDifference > 9.0E-4 || PlayerUtil.mc.player.positionUpdateTicks >= 20;
            final boolean l_MovedRotation = l_YawDifference != 0.0 || l_RotationDifference != 0.0;
            if (PlayerUtil.mc.player.isRiding()) {
                PlayerUtil.mc.player.connection.sendPacket((Packet)new CPacketPlayer.PositionRotation(PlayerUtil.mc.player.motionX, -999.0, PlayerUtil.mc.player.motionZ, l_Yaw, l_Pitch, PlayerUtil.mc.player.onGround));
                l_MovedXYZ = false;
            }
            else if (l_MovedXYZ && l_MovedRotation) {
                PlayerUtil.mc.player.connection.sendPacket((Packet)new CPacketPlayer.PositionRotation(PlayerUtil.mc.player.posX, axisalignedbb.minY, PlayerUtil.mc.player.posZ, l_Yaw, l_Pitch, PlayerUtil.mc.player.onGround));
            }
            else if (l_MovedXYZ) {
                PlayerUtil.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(PlayerUtil.mc.player.posX, axisalignedbb.minY, PlayerUtil.mc.player.posZ, PlayerUtil.mc.player.onGround));
            }
            else if (l_MovedRotation) {
                PlayerUtil.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(l_Yaw, l_Pitch, PlayerUtil.mc.player.onGround));
            }
            else if (PlayerUtil.mc.player.prevOnGround != PlayerUtil.mc.player.onGround) {
                PlayerUtil.mc.player.connection.sendPacket((Packet)new CPacketPlayer(PlayerUtil.mc.player.onGround));
            }
            if (l_MovedXYZ) {
                PlayerUtil.mc.player.lastReportedPosX = PlayerUtil.mc.player.posX;
                PlayerUtil.mc.player.lastReportedPosY = axisalignedbb.minY;
                PlayerUtil.mc.player.lastReportedPosZ = PlayerUtil.mc.player.posZ;
                PlayerUtil.mc.player.positionUpdateTicks = 0;
            }
            if (l_MovedRotation) {
                PlayerUtil.mc.player.lastReportedYaw = l_Yaw;
                PlayerUtil.mc.player.lastReportedPitch = l_Pitch;
            }
            PlayerUtil.mc.player.prevOnGround = PlayerUtil.mc.player.onGround;
            PlayerUtil.mc.player.autoJumpEnabled = PlayerUtil.mc.player.mc.gameSettings.autoJump;
        }
    }
    
    public static boolean IsPlayerTrapped() {
        final BlockPos l_PlayerPos = GetLocalPlayerPosFloored();
        final BlockPos[] array;
        final BlockPos[] l_TrapPositions = array = new BlockPos[] { l_PlayerPos.down(), l_PlayerPos.up().up(), l_PlayerPos.north(), l_PlayerPos.south(), l_PlayerPos.east(), l_PlayerPos.west(), l_PlayerPos.north().up(), l_PlayerPos.south().up(), l_PlayerPos.east().up(), l_PlayerPos.west().up() };
        for (final BlockPos l_Pos : array) {
            final IBlockState l_State = PlayerUtil.mc.world.getBlockState(l_Pos);
            if (l_State.getBlock() != Blocks.OBSIDIAN && PlayerUtil.mc.world.getBlockState(l_Pos).getBlock() != Blocks.BEDROCK) {
                return false;
            }
        }
        return true;
    }
    
    public static boolean IsEntityTrapped(final Entity e) {
        final BlockPos l_PlayerPos = EntityPosToFloorBlockPos(e);
        final BlockPos[] array;
        final BlockPos[] l_TrapPositions = array = new BlockPos[] { l_PlayerPos.up().up(), l_PlayerPos.north(), l_PlayerPos.south(), l_PlayerPos.east(), l_PlayerPos.west(), l_PlayerPos.north().up(), l_PlayerPos.south().up(), l_PlayerPos.east().up(), l_PlayerPos.west().up() };
        for (final BlockPos l_Pos : array) {
            final IBlockState l_State = PlayerUtil.mc.world.getBlockState(l_Pos);
            if (l_State.getBlock() != Blocks.OBSIDIAN && PlayerUtil.mc.world.getBlockState(l_Pos).getBlock() != Blocks.BEDROCK) {
                return false;
            }
        }
        return true;
    }
    
    public static FacingDirection GetFacing() {
        switch (MathHelper.floor(PlayerUtil.mc.player.rotationYaw * 8.0f / 360.0f + 0.5) & 0x7) {
            case 0:
            case 1: {
                return FacingDirection.South;
            }
            case 2:
            case 3: {
                return FacingDirection.West;
            }
            case 4:
            case 5: {
                return FacingDirection.North;
            }
            case 6:
            case 7: {
                return FacingDirection.East;
            }
            default: {
                return FacingDirection.North;
            }
        }
    }
    
    public static float getSpeedInKM() {
        final double deltaX = PlayerUtil.mc.player.posX - PlayerUtil.mc.player.prevPosX;
        final double deltaZ = PlayerUtil.mc.player.posZ - PlayerUtil.mc.player.prevPosZ;
        final float l_Distance = MathHelper.sqrt(deltaX * deltaX + deltaZ * deltaZ);
        final double l_KMH = Math.floor(l_Distance / 1000.0f / 1.3888889E-5f);
        String l_Formatter = PlayerUtil.Formatter.format(l_KMH);
        if (!l_Formatter.contains(".")) {
            l_Formatter += ".0";
        }
        return Float.valueOf(l_Formatter);
    }
    
    static {
        PlayerUtil.mc = Minecraft.getMinecraft();
        Formatter = new DecimalFormat("#.#");
    }
    
    public enum FacingDirection
    {
        North, 
        South, 
        East, 
        West;
    }
}
