// 
// Decompiled by Procyon v0.5.36
// 

package baritone.api.utils;

import java.util.Optional;
import baritone.api.cache.IWorldData;

public interface IPlayerContext
{
    bud player();
    
    IPlayerController playerController();
    
    amu world();
    
    IWorldData worldData();
    
    bhc objectMouseOver();
    
    default BetterBlockPos playerFeet() {
        final BetterBlockPos betterBlockPos = new BetterBlockPos(this.player().p, this.player().q + 0.1251, this.player().r);
        try {
            if (this.world().o((et)betterBlockPos).u() instanceof arf) {
                return betterBlockPos.up();
            }
        }
        catch (NullPointerException ex) {}
        return betterBlockPos;
    }
    
    default bhe playerFeetAsVec() {
        return new bhe(this.player().p, this.player().q, this.player().r);
    }
    
    default bhe playerHead() {
        return new bhe(this.player().p, this.player().q + this.player().by(), this.player().r);
    }
    
    default Rotation playerRotations() {
        return new Rotation(this.player().v, this.player().w);
    }
    
    default double eyeHeight(final boolean b) {
        if (b) {
            return 1.54;
        }
        return 1.62;
    }
    
    default Optional<et> getSelectedBlock() {
        final bhc objectMouseOver;
        if ((objectMouseOver = this.objectMouseOver()) != null && objectMouseOver.a == bhc$a.b) {
            return Optional.of(objectMouseOver.a());
        }
        return Optional.empty();
    }
    
    default boolean isLookingAt(final et value) {
        return this.getSelectedBlock().equals(Optional.of(value));
    }
    
    default Optional<vg> getSelectedEntity() {
        final bhc objectMouseOver;
        if ((objectMouseOver = this.objectMouseOver()) != null && objectMouseOver.a == bhc$a.c) {
            return Optional.of(objectMouseOver.d);
        }
        return Optional.empty();
    }
}
