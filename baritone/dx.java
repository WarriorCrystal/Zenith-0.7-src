// 
// Decompiled by Procyon v0.5.36
// 

package baritone;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import baritone.api.pathing.calc.IPath;
import java.util.Collections;
import baritone.api.pathing.goals.Goal;
import baritone.api.pathing.movement.IMovement;
import baritone.api.utils.BetterBlockPos;
import java.util.List;

public final class dx extends gk
{
    private final List<BetterBlockPos> a;
    private final List<IMovement> b;
    private final int a;
    private final Goal a;
    
    private dx(final List<BetterBlockPos> a, final List<IMovement> b, final int a2, final Goal a3) {
        this.a = a;
        this.b = b;
        this.a = a2;
        this.a = a3;
        this.sanityCheck();
    }
    
    @Override
    public final Goal getGoal() {
        return this.a;
    }
    
    @Override
    public final List<IMovement> movements() {
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
    public final int length() {
        return this.a.size();
    }
    
    public static Optional<dx> a(final IPath path, final IPath path2) {
        if (path2 == null || path == null) {
            return Optional.empty();
        }
        if (!path.getDest().equals(path2.getSrc())) {
            return Optional.empty();
        }
        final HashSet set = new HashSet((Collection<? extends E>)path2.positions());
        int n = -1;
        for (int i = 0; i < path.length() - 1; ++i) {
            if (set.contains(path.positions().get(i))) {
                n = i;
                break;
            }
        }
        if (n != -1) {
            return Optional.empty();
        }
        final int n2 = path.length() - 1;
        final int index;
        if ((index = path2.positions().indexOf(path.positions().get(n2))) != 0) {
            throw new IllegalStateException();
        }
        final ArrayList<Object> list = new ArrayList<Object>();
        final ArrayList<IMovement> list2 = new ArrayList<IMovement>();
        list.addAll(path.positions().subList(0, n2 + 1));
        list2.addAll((Collection<?>)path.movements().subList(0, n2));
        list.addAll(path2.positions().subList(index + 1, path2.length()));
        list2.addAll((Collection<?>)path2.movements().subList(index, path2.length() - 1));
        return Optional.of(new dx((List<BetterBlockPos>)list, list2, path.getNumNodesConsidered() + path2.getNumNodesConsidered(), path.getGoal()));
    }
}
