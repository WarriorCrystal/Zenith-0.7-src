// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.mixin.client;

import me.gopro336.goprohack.events.liquid.EventLiquidCollisionBB;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import me.gopro336.goprohack.GoproHackMod;
import me.gopro336.goprohack.events.blocks.EventCanCollideCheck;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.BlockLiquid;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ BlockLiquid.class })
public class MixinBlockLiquid
{
    @Inject(method = { "canCollideCheck" }, at = { @At("HEAD") }, cancellable = true)
    public void canCollideCheck(final IBlockState blockState, final boolean b, final CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        final EventCanCollideCheck l_Event = new EventCanCollideCheck();
        GoproHackMod.EVENT_BUS.post(l_Event);
        callbackInfoReturnable.setReturnValue(l_Event.isCancelled());
    }
    
    @Inject(method = { "getCollisionBoundingBox" }, at = { @At("HEAD") }, cancellable = true)
    public void getCollisionBoundingBox(final IBlockState blockState, final IBlockAccess worldIn, final BlockPos pos, final CallbackInfoReturnable<AxisAlignedBB> callbackInfoReturnable) {
        final EventLiquidCollisionBB l_Collision = new EventLiquidCollisionBB(pos);
        GoproHackMod.EVENT_BUS.post(l_Collision);
        if (l_Collision.isCancelled()) {
            callbackInfoReturnable.setReturnValue(l_Collision.getBoundingBox());
            callbackInfoReturnable.cancel();
        }
    }
}
