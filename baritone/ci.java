// 
// Decompiled by Procyon v0.5.36
// 

package baritone;

import java.util.Collections;
import baritone.api.pathing.movement.IMovement;
import baritone.api.pathing.calc.IPath;
import baritone.api.utils.Helper;
import java.util.Collection;
import java.util.LinkedList;
import java.util.ArrayList;
import baritone.api.pathing.goals.Goal;
import java.util.List;
import baritone.api.utils.BetterBlockPos;

final class ci extends gk
{
    private final BetterBlockPos a;
    private final BetterBlockPos b;
    private final List<BetterBlockPos> a;
    private final List<ck> b;
    private final List<g> c;
    private final Goal a;
    private final int a;
    private final cj a;
    private volatile boolean a;
    
    ci(g a, final g g, final int a2, final Goal a3, final cj a4) {
        this.a = new BetterBlockPos(a.a, a.b, a.c);
        this.b = new BetterBlockPos(g.a, g.b, g.c);
        this.a = a2;
        this.b = new ArrayList<ck>();
        this.a = a3;
        this.a = a4;
        a = g;
        final LinkedList<BetterBlockPos> c = new LinkedList<BetterBlockPos>();
        final LinkedList<g> c2 = new LinkedList<g>();
        while (a != null) {
            c2.addFirst(a);
            c.addFirst(new BetterBlockPos(a.a, a.b, a.c));
            a = a.a;
        }
        this.a = new ArrayList<BetterBlockPos>(c);
        this.c = new ArrayList<g>(c2);
    }
    
    @Override
    public final Goal getGoal() {
        return this.a;
    }
    
    private boolean a() {
        if (this.a.isEmpty() || !this.b.isEmpty()) {
            throw new IllegalStateException();
        }
        for (int i = 0; i < this.a.size() - 1; ++i) {
            final ck a;
            if ((a = this.a(this.a.get(i), this.a.get(i + 1), this.c.get(i + 1).b - this.c.get(i).b)) == null) {
                return true;
            }
            this.b.add(a);
        }
        return false;
    }
    
    private ck a(final BetterBlockPos obj, final BetterBlockPos obj2, final double b) {
        cp[] values;
        for (int length = (values = cp.values()).length, i = 0; i < length; ++i) {
            final ck a;
            if ((a = values[i].a(this.a, obj)).getDest().equals(obj2)) {
                final ck ck = a;
                ck.a = Math.min(ck.a(this.a), b);
                return a;
            }
        }
        Helper.HELPER.logDebug("Movement became impossible during calculation " + obj + " " + obj2 + " " + obj2.b((fq)obj));
        return null;
    }
    
    @Override
    public final IPath postProcess() {
        if (this.a) {
            throw new IllegalStateException();
        }
        this.a = true;
        final boolean a = this.a();
        this.b.forEach(ck -> ck.a(this.a));
        if (!a) {
            this.sanityCheck();
            return this;
        }
        final dv dv;
        if ((dv = new dv(this, this.movements().size())).movements().size() != this.b.size()) {
            throw new IllegalStateException();
        }
        return dv;
    }
    
    @Override
    public final List<IMovement> movements() {
        if (!this.a) {
            throw new IllegalStateException();
        }
        return Collections.unmodifiableList((List<? extends IMovement>)this.b);
    }
    
    @Override
    public final List<BetterBlockPos> positions() {
        return Collections.unmodifiableList((List<? extends BetterBlockPos>)this.a);
    }
    
    @Override
    public final int getNumNodesConsidered() {
        return this.a;
    }
    
    @Override
    public final BetterBlockPos getSrc() {
        return this.a;
    }
    
    @Override
    public final BetterBlockPos getDest() {
        return this.b;
    }
}
