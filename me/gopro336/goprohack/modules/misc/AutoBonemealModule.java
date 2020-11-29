//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.modules.misc;

import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemDye;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.BlockCrops;
import java.util.function.Predicate;
import net.minecraft.network.Packet;
import net.minecraft.init.Items;
import net.minecraft.util.EnumFacing;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.util.EnumHand;
import net.minecraft.entity.player.EntityPlayer;
import java.util.Comparator;
import net.minecraft.entity.Entity;
import me.gopro336.goprohack.util.entity.EntityUtil;
import me.gopro336.goprohack.util.BlockInteractionHelper;
import me.gopro336.goprohack.util.entity.PlayerUtil;
import net.minecraft.util.math.BlockPos;
import me.zero.alpine.fork.listener.EventHandler;
import me.gopro336.goprohack.events.player.EventPlayerMotionUpdate;
import me.zero.alpine.fork.listener.Listener;
import me.gopro336.goprohack.modules.Value;
import me.gopro336.goprohack.modules.Module;

public class AutoBonemealModule extends Module
{
    public static Value<Integer> Radius;
    private boolean IsRunning;
    @EventHandler
    private Listener<EventPlayerMotionUpdate> OnPlayerUpdate;
    
    public AutoBonemealModule() {
        super("AutoBonemeal", new String[] { "" }, "Bonemeals anything nearby", "NONE", -1, ModuleType.MISC);
        this.IsRunning = false;
        final BlockPos l_ClosestPos;
        double[] l_Pos;
        final CPacketPlayerTryUseItemOnBlock cPacketPlayerTryUseItemOnBlock;
        final Object o;
        this.OnPlayerUpdate = new Listener<EventPlayerMotionUpdate>(p_Event -> {
            l_ClosestPos = BlockInteractionHelper.getSphere(PlayerUtil.GetLocalPlayerPosFloored(), AutoBonemealModule.Radius.getValue(), AutoBonemealModule.Radius.getValue(), false, true, 0).stream().filter(p_Pos -> this.IsValidBlockPos(p_Pos)).min(Comparator.comparing(p_Pos -> EntityUtil.GetDistanceOfEntityToBlock((Entity)this.mc.player, p_Pos))).orElse(null);
            if (l_ClosestPos != null && this.UpdateBonemealIfNeed()) {
                p_Event.cancel();
                l_Pos = EntityUtil.calculateLookAt(l_ClosestPos.getX() + 0.5, l_ClosestPos.getY() + 0.5, l_ClosestPos.getZ() + 0.5, (EntityPlayer)this.mc.player);
                this.mc.player.rotationYawHead = (float)l_Pos[0];
                PlayerUtil.PacketFacePitchAndYaw((float)l_Pos[1], (float)l_Pos[0]);
                this.mc.player.swingArm(EnumHand.MAIN_HAND);
                this.mc.getConnection();
                new CPacketPlayerTryUseItemOnBlock(l_ClosestPos, EnumFacing.UP, (this.mc.player.getHeldItemOffhand().getItem() == Items.DYE) ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND, 0.0f, 0.0f, 0.0f);
                ((NetHandlerPlayClient)o).sendPacket((Packet)cPacketPlayerTryUseItemOnBlock);
                this.IsRunning = true;
            }
            else {
                this.IsRunning = false;
            }
        }, (Predicate<EventPlayerMotionUpdate>[])new Predicate[0]);
    }
    
    private boolean IsValidBlockPos(final BlockPos p_Pos) {
        final IBlockState l_State = this.mc.world.getBlockState(p_Pos);
        if (l_State.getBlock() instanceof BlockCrops) {
            final BlockCrops l_Crop = (BlockCrops)l_State.getBlock();
            if (!l_Crop.isMaxAge(l_State)) {
                return true;
            }
        }
        return false;
    }
    
    public boolean IsRunning() {
        return this.IsRunning;
    }
    
    private boolean UpdateBonemealIfNeed() {
        final ItemStack l_Main = this.mc.player.getHeldItemMainhand();
        final ItemStack l_Off = this.mc.player.getHeldItemOffhand();
        if (!l_Main.isEmpty() && l_Main.getItem() instanceof ItemDye) {
            if (this.IsBoneMealItem(l_Main)) {
                return true;
            }
        }
        else if (!l_Off.isEmpty() && l_Off.getItem() instanceof ItemDye && this.IsBoneMealItem(l_Off)) {
            return true;
        }
        for (int l_I = 0; l_I < 9; ++l_I) {
            final ItemStack l_Stack = this.mc.player.inventory.getStackInSlot(l_I);
            if (!l_Stack.isEmpty()) {
                if (l_Stack.getItem() instanceof ItemDye) {
                    if (this.IsBoneMealItem(l_Stack)) {
                        this.mc.player.inventory.currentItem = l_I;
                        this.mc.playerController.updateController();
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    private boolean IsBoneMealItem(final ItemStack p_Stack) {
        return EnumDyeColor.byDyeDamage(p_Stack.getMetadata()) == EnumDyeColor.WHITE;
    }
    
    static {
        AutoBonemealModule.Radius = new Value<Integer>("Radius", new String[] { "R" }, "Radius to search for not fully grown seeds", 4, 0, 10, 1);
    }
}
