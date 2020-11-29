// 
// Decompiled by Procyon v0.5.36
// 

package baritone;

import baritone.api.utils.BetterBlockPos;
import java.util.Optional;
import baritone.api.utils.PathCalculationResult$Type;
import java.util.function.Function;
import baritone.api.pathing.calc.IPath;
import baritone.api.utils.PathCalculationResult;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import baritone.api.pathing.goals.Goal;
import baritone.api.utils.Helper;
import baritone.api.pathing.calc.IPathFinder;

public abstract class ch implements IPathFinder, Helper
{
    protected final int a;
    protected final int b;
    protected final int c;
    protected final Goal a;
    private final cj a;
    final Long2ObjectOpenHashMap<g> a;
    protected g a;
    protected g b;
    protected final g[] a;
    private volatile boolean b;
    protected boolean a;
    protected static final double[] a;
    
    ch(final int a, final int b, final int c, final Goal a2, final cj a3) {
        this.a = new g[7];
        this.a = a;
        this.b = b;
        this.c = c;
        this.a = a2;
        this.a = a3;
        this.a = (Long2ObjectOpenHashMap<g>)new Long2ObjectOpenHashMap((int)baritone.a.a().pathingMapDefaultSize.value, (float)baritone.a.a().pathingMapLoadFactor.value);
    }
    
    public void a() {
        this.a = true;
    }
    
    @Override
    public synchronized PathCalculationResult calculate(final long n, final long n2) {
        if (this.b) {
            throw new IllegalStateException("Path finder cannot be reused!");
        }
        this.a = false;
        try {
            final IPath path = this.a(n, n2).map((Function<? super IPath, ? extends IPath>)IPath::postProcess).orElse(null);
            if (this.a) {
                return new PathCalculationResult(PathCalculationResult$Type.CANCELLATION);
            }
            if (path == null) {
                return new PathCalculationResult(PathCalculationResult$Type.FAILURE);
            }
            final int length = path.length();
            final IPath cutoffAtLoadedChunks;
            if ((cutoffAtLoadedChunks = path.cutoffAtLoadedChunks(this.a.a)).length() < length) {
                Helper.HELPER.logDebug("Cutting off path at edge of loaded chunks");
                Helper.HELPER.logDebug("Length decreased by " + (length - cutoffAtLoadedChunks.length()));
            }
            else {
                Helper.HELPER.logDebug("Path ends within loaded chunks");
            }
            final int length2 = cutoffAtLoadedChunks.length();
            final IPath staticCutoff;
            if ((staticCutoff = cutoffAtLoadedChunks.staticCutoff(this.a)).length() < length2) {
                Helper.HELPER.logDebug("Static cutoff " + length2 + " to " + staticCutoff.length());
            }
            if (this.a.isInGoal(staticCutoff.getDest())) {
                return new PathCalculationResult(PathCalculationResult$Type.SUCCESS_TO_GOAL, staticCutoff);
            }
            return new PathCalculationResult(PathCalculationResult$Type.SUCCESS_SEGMENT, staticCutoff);
        }
        catch (Exception obj) {
            Helper.HELPER.logDirect("Pathing exception: ".concat(String.valueOf(obj)));
            obj.printStackTrace();
            return new PathCalculationResult(PathCalculationResult$Type.EXCEPTION);
        }
        finally {
            this.b = true;
        }
    }
    
    protected abstract Optional<IPath> a(final long p0, final long p1);
    
    protected final double a(final g g) {
        final int n = g.a - this.a;
        final int n2 = g.b - this.b;
        final int n3 = g.c - this.c;
        final int n4 = n;
        final int n5 = n4 * n4;
        final int n6 = n2;
        final int n7 = n5 + n6 * n6;
        final int n8 = n3;
        return n7 + n8 * n8;
    }
    
    protected final g a(final int n, final int n2, final int n3, final long n4) {
        g g;
        if ((g = (g)this.a.get(n4)) == null) {
            g = new g(n, n2, n3, this.a);
            this.a.put(n4, (Object)g);
        }
        return g;
    }
    
    @Override
    public Optional<IPath> pathToMostRecentNodeConsidered() {
        return Optional.ofNullable(this.b).map(g -> new ci(this.a, g, 0, this.a, this.a));
    }
    
    @Override
    public Optional<IPath> bestPathSoFar() {
        return this.a(false, 0);
    }
    
    protected final Optional<IPath> a(final boolean b, final int n) {
        if (this.a == null) {
            return Optional.empty();
        }
        double a = 0.0;
        for (int i = 0; i < 7; ++i) {
            if (this.a[i] != null) {
                final double a2;
                if ((a2 = this.a(this.a[i])) > a) {
                    a = a2;
                }
                if (a2 > 25.0) {
                    if (b) {
                        if (ch.a[i] >= 3.0) {
                            System.out.println("Warning: cost coefficient is greater than three! Probably means that");
                            System.out.println("the path I found is pretty terrible (like sneak-bridging for dozens of blocks)");
                            System.out.println("But I'm going to do it anyway, because yolo");
                        }
                        System.out.println("Path goes for " + Math.sqrt(a2) + " blocks");
                        this.logDebug("A* cost coefficient " + ch.a[i]);
                    }
                    return (Optional<IPath>)Optional.of(new ci(this.a, this.a[i], n, this.a, this.a));
                }
            }
        }
        if (b) {
            this.logDebug("Even with a cost coefficient of " + ch.a[6] + ", I couldn't get more than " + Math.sqrt(a) + " blocks");
            this.logDebug("No path found =(");
            if (baritone.a.a().desktopNotifications.value) {
                fs.a("No path found =(");
            }
        }
        return Optional.empty();
    }
    
    @Override
    public final boolean isFinished() {
        return this.b;
    }
    
    @Override
    public final Goal getGoal() {
        return this.a;
    }
    
    public final BetterBlockPos a() {
        return new BetterBlockPos(this.a, this.b, this.c);
    }
    
    static {
        a = new double[] { 1.5, 2.0, 2.5, 3.0, 4.0, 5.0, 10.0 };
    }
}
