// 
// Decompiled by Procyon v0.5.36
// 

package baritone;

import baritone.api.utils.RayTraceUtils;
import baritone.api.BaritoneAPI;
import baritone.api.cache.IWorldData;
import baritone.api.utils.IPlayerController;
import baritone.api.utils.IPlayerContext;
import baritone.api.utils.Helper;

public enum gm implements Helper, IPlayerContext
{
    a;
    
    @Override
    public final bud player() {
        return gm.mc.h;
    }
    
    @Override
    public final IPlayerController playerController() {
        return gn.a;
    }
    
    @Override
    public final amu world() {
        return (amu)gm.mc.f;
    }
    
    @Override
    public final IWorldData worldData() {
        return BaritoneAPI.getProvider().getPrimaryBaritone().getWorldProvider().getCurrentWorld();
    }
    
    @Override
    public final bhc objectMouseOver() {
        return RayTraceUtils.rayTraceTowards((vg)this.player(), this.playerRotations(), this.playerController().getBlockReachDistance());
    }
}
