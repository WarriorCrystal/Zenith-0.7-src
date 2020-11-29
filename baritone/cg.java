// 
// Decompiled by Procyon v0.5.36
// 

package baritone;

import baritone.api.utils.BetterBlockPos;
import baritone.api.pathing.calc.IPath;
import java.util.Optional;
import baritone.api.pathing.goals.Goal;

public final class cg extends ch
{
    private final gi a;
    private final cj a;
    
    public cg(final int n, final int n2, final int n3, final Goal goal, final gi a, final cj a2) {
        super(n, n2, n3, goal, a2);
        this.a = a;
        this.a = a2;
    }
    
    @Override
    protected final Optional<IPath> a(long n, long i) {
        this.a = this.a(this.a, this.b, this.c, BetterBlockPos.longHash(this.a, this.b, this.c));
        this.a.b = 0.0;
        this.a.c = this.a.a;
        final p p2;
        (p2 = new p()).a(this.a);
        final double[] array = new double[7];
        for (int j = 0; j < 7; ++j) {
            array[j] = this.a.a;
            this.a[j] = this.a;
        }
        final gj gj = new gj();
        final fs fs = new fs(this.a.a.al());
        final long currentTimeMillis = System.currentTimeMillis();
        final boolean booleanValue;
        if (booleanValue = baritone.a.a().slowPath.value) {
            this.logDebug("slowPath is on, path timeout will be " + baritone.a.a().slowPathTimeoutMS.value + "ms instead of " + lng + "ms");
        }
        final long n3 = currentTimeMillis + (booleanValue ? baritone.a.a().slowPathTimeoutMS.value : lng);
        final long n4 = currentTimeMillis + (booleanValue ? baritone.a.a().slowPathTimeoutMS.value : n2);
        n = 1;
        int n5 = 0;
        i = 0;
        int n6 = 0;
        final boolean b = !this.a.a.isEmpty();
        final int intValue = baritone.a.a().pathingMaxChunkBorderFetch.value;
        final double n7 = baritone.a.a().minimumImprovementRepropagation.value ? 0.01 : 0.0;
        final cp[] values = cp.values();
        long currentTimeMillis2;
        while (p2.a != 0 && n6 < intValue && !this.a && ((n5 & 0x3F) != 0x0 || ((currentTimeMillis2 = System.currentTimeMillis()) - n4 < 0L && (n != 0 || currentTimeMillis2 - n3 < 0L)))) {
            if (booleanValue) {
                try {
                    Thread.sleep(baritone.a.a().slowPathTimeDelayMS.value);
                }
                catch (InterruptedException ex) {}
            }
            final g a = p2.a();
            this.b = a;
            ++n5;
            if (this.a.isInGoal(a.a, a.b, a.c)) {
                this.logDebug("Took " + (System.currentTimeMillis() - currentTimeMillis) + "ms, " + (int)i + " movements considered");
                return (Optional<IPath>)Optional.of(new ci(this.a, a, n5, this.a, this.a));
            }
            cp[] array2;
            for (int length = (array2 = values).length, k = 0; k < length; ++k) {
                final cp obj = array2[k];
                final int l = a.a + obj.a;
                final int m = a.c + obj.c;
                if ((l >> 4 != a.a >> 4 || m >> 4 != a.c >> 4) && !this.a.a.b(l, m)) {
                    if (!obj.a) {
                        ++n6;
                    }
                }
                else if ((obj.a || fs.a(l, m)) && a.b + obj.b <= 256 && a.b + obj.b >= 0) {
                    gj.a();
                    obj.a(this.a, a.a, a.b, a.c, gj);
                    ++i;
                    double a2;
                    if ((a2 = gj.a) < 1000000.0) {
                        if (a2 <= 0.0 || Double.isNaN(a2)) {
                            throw new IllegalStateException(obj + " calculated implausible cost " + a2);
                        }
                        if (!obj.a || fs.a(gj.a, gj.c)) {
                            if (!obj.a && (gj.a != l || gj.c != m)) {
                                throw new IllegalStateException(obj + " " + gj.a + " " + l + " " + gj.c + " " + m);
                            }
                            if (!obj.b && gj.b != a.b + obj.b) {
                                throw new IllegalStateException(obj + " " + gj.b + " " + (a.b + obj.b));
                            }
                            final long longHash = BetterBlockPos.longHash(gj.a, gj.b, gj.c);
                            if (b) {
                                a2 *= this.a.a.get(longHash);
                            }
                            final g a3 = this.a(gj.a, gj.b, gj.c, longHash);
                            final double b2 = a.b + a2;
                            if (a3.b - b2 > n7) {
                                a3.a = a;
                                a3.b = b2;
                                a3.c = b2 + a3.a;
                                if (a3.d != -1) {
                                    p2.b(a3);
                                }
                                else {
                                    p2.a(a3);
                                }
                                for (int n8 = 0; n8 < 7; ++n8) {
                                    final double n9 = a3.a + a3.b / cg.a[n8];
                                    if (array[n8] - n9 > n7) {
                                        array[n8] = n9;
                                        this.a[n8] = a3;
                                        if (n != 0 && this.a(a3) > 25.0) {
                                            n = 0;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        if (this.a) {
            return Optional.empty();
        }
        System.out.println((int)i + " movements considered");
        System.out.println("Open set size: " + p2.a);
        System.out.println("PathNode map size: " + super.a.size());
        System.out.println((int)(n5 / (double)((System.currentTimeMillis() - currentTimeMillis) / 1000.0f)) + " nodes per second");
        final Optional<IPath> a4;
        if ((a4 = this.a(true, n5)).isPresent()) {
            this.logDebug("Took " + (System.currentTimeMillis() - currentTimeMillis) + "ms, " + (int)i + " movements considered");
        }
        return a4;
    }
}
