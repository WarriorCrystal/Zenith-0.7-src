// 
// Decompiled by Procyon v0.5.36
// 

package baritone;

import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.function.ToDoubleFunction;
import java.util.Comparator;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import baritone.api.utils.Rotation;
import baritone.api.utils.input.Input;
import baritone.api.IBaritone;
import baritone.api.pathing.goals.Goal;
import baritone.api.process.PathingCommandType;
import baritone.api.process.PathingCommand;
import java.util.Iterator;
import java.util.Collection;
import java.util.ArrayList;
import java.util.HashMap;

public final class dy extends fk
{
    private HashMap<et, awt> a;
    
    public dy(final a a) {
        super(a);
        this.a = new HashMap<et, awt>();
    }
    
    @Override
    public final boolean isActive() {
        if (this.a.player() == null || this.a.world() == null) {
            return false;
        }
        if (!baritone.a.a().backfill.value) {
            return false;
        }
        if (baritone.a.a().allowParkour.value) {
            this.logDirect("Backfill cannot be used with allowParkour true");
            baritone.a.a().backfill.value = Boolean.FALSE;
            return false;
        }
        if (this.a.getSelectedBlock().isPresent()) {
            this.a.put(this.a.getSelectedBlock().get(), this.a.world().o((et)this.a.getSelectedBlock().get()));
        }
        for (final et key : new ArrayList<et>(this.a.keySet())) {
            if (this.a.world().f(key) instanceof axt) {
                this.a.remove(key);
            }
        }
        this.a.a.clearAllKeys();
        return !this.a().isEmpty();
    }
    
    @Override
    public final PathingCommand onTick(final boolean b, final boolean b2) {
        if (!b2) {
            return new PathingCommand(null, PathingCommandType.REQUEST_PAUSE);
        }
        this.a.a.clearAllKeys();
        for (final et et : this.a()) {
            final cn cn = new cn();
            switch (dz.a[cl.a(cn, this.a, et, false, false) - 1]) {
                case 1: {
                    continue;
                }
                case 2: {
                    this.a.a.setInputForceState(Input.CLICK_RIGHT, true);
                    return new PathingCommand(null, PathingCommandType.REQUEST_PAUSE);
                }
                case 3: {
                    this.a.a.updateTarget(Optional.ofNullable(cn.a.a).get(), true);
                    return new PathingCommand(null, PathingCommandType.REQUEST_PAUSE);
                }
                default: {
                    throw new IllegalStateException();
                }
            }
        }
        return new PathingCommand(null, PathingCommandType.DEFER);
    }
    
    private List<et> a() {
        final dw a;
        final Object o;
        return this.a.keySet().stream().filter(et -> this.a.world().o(et).u() == aox.a).filter(et2 -> this.a.world().a(aox.d, et2, false, fa.b, (vg)null)).filter(et3 -> {
            a = this.a.a.a;
            return o == null || a.b() || a.a || !Arrays.asList(a.getPath().movements().get(a.getPosition()).a()).contains(et3);
        }).sorted(Comparator.comparingDouble((ToDoubleFunction<? super Object>)this.a.player()::c).reversed()).collect((Collector<? super Object, ?, List<et>>)Collectors.toList());
    }
    
    @Override
    public final void onLostControl() {
        if (this.a != null && !this.a.isEmpty()) {
            this.a.clear();
        }
    }
    
    @Override
    public final String displayName0() {
        return "Backfill";
    }
    
    @Override
    public final boolean isTemporary() {
        return true;
    }
    
    @Override
    public final double priority() {
        return 5.0;
    }
}
