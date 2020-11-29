// 
// Decompiled by Procyon v0.5.36
// 

package baritone;

import baritone.api.pathing.goals.GoalXZ;
import java.util.ArrayList;
import baritone.api.pathing.goals.GoalComposite;
import baritone.api.pathing.goals.Goal;
import baritone.api.process.PathingCommandType;
import baritone.api.process.PathingCommand;
import java.nio.file.Path;
import baritone.api.process.IExploreProcess;

public final class en extends fk implements IExploreProcess
{
    private et a;
    private es a;
    private int a;
    
    public en(final a a) {
        super(a);
    }
    
    @Override
    public final boolean isActive() {
        return this.a != null;
    }
    
    @Override
    public final void explore(final int n, final int n2) {
        this.a = new et(n, 0, n2);
        this.a = 0;
    }
    
    @Override
    public final void applyJsonFilter(final Path path, final boolean b) {
        this.a = new baritone.et(this, path, b, (byte)0);
    }
    
    private es a() {
        es es;
        if (this.a != null) {
            es = new er(this, this.a, new eq(this, (byte)0), (byte)0);
        }
        else {
            es = new eq(this, (byte)0);
        }
        return es;
    }
    
    @Override
    public final PathingCommand onTick(final boolean b, final boolean b2) {
        if (b) {
            this.logDirect("Failed");
            this.onLostControl();
            return null;
        }
        final es a = this.a();
        if (!baritone.a.a().disableCompletionCheck.value && a.a() == 0) {
            this.logDirect("Explored all chunks");
            this.onLostControl();
            return null;
        }
        final Goal[] a2;
        if ((a2 = this.a(this.a, a)) == null) {
            this.logDebug("awaiting region load from disk");
            return new PathingCommand(null, PathingCommandType.REQUEST_PAUSE);
        }
        return new PathingCommand(new GoalComposite(a2), PathingCommandType.FORCE_REVALIDATE_GOAL_AND_PATH);
    }
    
    private Goal[] a(final et et2, final es es) {
        final int n2 = et2.p() >> 4;
        final int n3 = et2.r() >> 4;
        int n4 = Math.min(es.a(), baritone.a.a().exploreChunkSetMinimumSize.value);
        final ArrayList<Object> list = new ArrayList<Object>();
        final int intValue = baritone.a.a().worldExploringChunkOffset.value;
        int a = this.a;
        while (true) {
            for (int i = -a; i <= a; ++i) {
                final int n5 = a - Math.abs(i);
                for (int j = 0; j < 2; ++j) {
                    final int a2 = ((j << 1) - 1) * n5;
                    if (Math.abs(i) + Math.abs(a2) != a) {
                        throw new IllegalStateException();
                    }
                    switch (ep.a[es.a(n2 + i, n3 + a2) - 1]) {
                        case 1: {
                            return null;
                        }
                        case 3: {
                            continue;
                        }
                    }
                    final int n6 = (n2 + i << 4) + 8;
                    final int n7 = (n3 + a2 << 4) + 8;
                    final int n8 = intValue << 4;
                    int n9;
                    if (i < 0) {
                        n9 = n6 - n8;
                    }
                    else {
                        n9 = n6 + n8;
                    }
                    int n10;
                    if (a2 < 0) {
                        n10 = n7 - n8;
                    }
                    else {
                        n10 = n7 + n8;
                    }
                    list.add(new et(n9, 0, n10));
                }
            }
            if (a % 10 == 0) {
                n4 = Math.min(es.a(), baritone.a.a().exploreChunkSetMinimumSize.value);
            }
            if (list.size() >= n4) {
                final int n11;
                final int n12;
                return list.stream().map(et -> {
                    et.p();
                    et.r();
                    if (baritone.a.a().exploreMaintainY.value == -1) {
                        return new GoalXZ(n11, n12);
                    }
                    else {
                        return new eo(n11, n12);
                    }
                }).toArray(Goal[]::new);
            }
            if (list.isEmpty()) {
                this.a = a + 1;
            }
            ++a;
        }
    }
    
    @Override
    public final void onLostControl() {
        this.a = null;
    }
    
    @Override
    public final String displayName0() {
        return "Exploring around " + this.a + ", distance completed " + this.a + ", currently going to " + new GoalComposite(this.a(this.a, this.a()));
    }
}
