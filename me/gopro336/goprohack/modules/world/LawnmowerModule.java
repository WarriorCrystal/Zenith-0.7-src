//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.modules.world;

import net.minecraft.block.state.IBlockState;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.block.BlockTallGrass;
import java.util.function.Predicate;
import net.minecraft.util.EnumFacing;
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

public class LawnmowerModule extends Module
{
    public static Value<Integer> Radius;
    public static Value<Boolean> Flowers;
    @EventHandler
    private Listener<EventPlayerMotionUpdate> OnPlayerUpdate;
    
    public LawnmowerModule() {
        super("Lawnmower", new String[] { "" }, "Breaks grass and flowers in range", "NONE", -1, ModuleType.WORLD);
        final BlockPos l_ClosestPos;
        double[] l_Pos;
        this.OnPlayerUpdate = new Listener<EventPlayerMotionUpdate>(p_Event -> {
            l_ClosestPos = BlockInteractionHelper.getSphere(PlayerUtil.GetLocalPlayerPosFloored(), LawnmowerModule.Radius.getValue(), LawnmowerModule.Radius.getValue(), false, true, 0).stream().filter(p_Pos -> this.IsValidBlockPos(p_Pos)).min(Comparator.comparing(p_Pos -> EntityUtil.GetDistanceOfEntityToBlock((Entity)this.mc.player, p_Pos))).orElse(null);
            if (l_ClosestPos != null) {
                p_Event.cancel();
                l_Pos = EntityUtil.calculateLookAt(l_ClosestPos.getX() + 0.5, l_ClosestPos.getY() - 0.5, l_ClosestPos.getZ() + 0.5, (EntityPlayer)this.mc.player);
                PlayerUtil.PacketFacePitchAndYaw((float)l_Pos[1], (float)l_Pos[0]);
                this.mc.player.swingArm(EnumHand.MAIN_HAND);
                this.mc.playerController.clickBlock(l_ClosestPos, EnumFacing.UP);
            }
        }, (Predicate<EventPlayerMotionUpdate>[])new Predicate[0]);
    }
    
    private boolean IsValidBlockPos(final BlockPos p_Pos) {
        final IBlockState l_State = this.mc.world.getBlockState(p_Pos);
        return l_State.getBlock() instanceof BlockTallGrass || l_State.getBlock() instanceof BlockDoublePlant || (LawnmowerModule.Flowers.getValue() && l_State.getBlock() instanceof BlockFlower);
    }
    
    static {
        LawnmowerModule.Radius = new Value<Integer>("Radius", new String[] { "R" }, "Radius to search for and break tall grass", 4, 0, 10, 1);
        LawnmowerModule.Flowers = new Value<Boolean>("Flowers", new String[] { "R" }, "Break Flowers", true);
    }
}
