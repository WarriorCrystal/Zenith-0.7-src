// 
// Decompiled by Procyon v0.5.36
// 

package baritone;

import baritone.api.pathing.goals.GoalYLevel;
import baritone.api.pathing.goals.GoalInverted;
import baritone.api.pathing.goals.GoalComposite;
import org.lwjgl.opengl.GL11;
import baritone.api.pathing.goals.GoalXZ;
import baritone.api.pathing.goals.GoalTwoBlocks;
import baritone.api.pathing.goals.GoalGetToBlock;
import baritone.api.utils.interfaces.IGoalRenderPos;
import java.util.List;
import baritone.api.utils.BetterBlockPos;
import baritone.api.pathing.calc.IPath;
import baritone.api.pathing.goals.Goal;
import java.util.Collection;
import java.util.Set;
import java.util.Collections;
import java.awt.Color;
import baritone.api.BaritoneAPI;
import baritone.api.utils.Helper;
import baritone.api.event.events.RenderEvent;

public final class ft implements fq
{
    public static void a(final RenderEvent renderEvent, final j j) {
        final float partialTicks = renderEvent.getPartialTicks();
        final Goal goal = j.getGoal();
        if (Helper.mc.m instanceof fp) {
            ((fp)Helper.mc.m).a();
        }
        if (j.a.getPlayerContext().world().s.q().a() != BaritoneAPI.getProvider().getPrimaryBaritone().getPlayerContext().world().s.q().a()) {
            return;
        }
        final vg aa;
        if ((aa = Helper.mc.aa()).l != BaritoneAPI.getProvider().getPrimaryBaritone().getPlayerContext().world()) {
            System.out.println("I have no idea what's going on");
            System.out.println("The primary baritone is in a different world than the render view entity");
            System.out.println("Not rendering the path");
            return;
        }
        if (goal != null && ft.a.renderGoal.value) {
            a(aa, goal, partialTicks, ft.a.colorGoalBox.value);
        }
        if (!ft.a.renderPath.value) {
            return;
        }
        final dw a = j.a;
        final dw b = j.b;
        if (a != null && ft.a.renderSelectionBoxes.value) {
            a(aa, (Collection<et>)Collections.unmodifiableSet((Set<?>)a.a), ft.a.colorBlocksToBreak.value);
            a(aa, (Collection<et>)Collections.unmodifiableSet((Set<?>)a.b), ft.a.colorBlocksToPlace.value);
            a(aa, (Collection<et>)Collections.unmodifiableSet((Set<?>)a.c), ft.a.colorBlocksToWalkInto.value);
        }
        if (a != null && a.getPath() != null) {
            a(a.getPath(), Math.max(a.getPosition() - 3, 0), ft.a.colorCurrentPath.value, ft.a.fadePath.value);
        }
        if (b != null && b.getPath() != null) {
            a(b.getPath(), 0, ft.a.colorNextPath.value, ft.a.fadePath.value);
        }
        final vg vg;
        j.getInProgress().ifPresent(ch -> {
            ch.bestPathSoFar().ifPresent(path -> a(path, 0, ft.a.colorBestPathSoFar.value, ft.a.fadePath.value));
            ch.pathToMostRecentNodeConsidered().ifPresent(path2 -> {
                a(path2, 0, ft.a.colorMostRecentConsidered.value, ft.a.fadePath.value);
                a(vg, (Collection<et>)Collections.singletonList(path2.getDest()), ft.a.colorMostRecentConsidered.value);
            });
        });
    }
    
    private static void a(final IPath path, int i, final Color color, final boolean b) {
        fq.a(color, ft.a.pathRenderLineWidthPixels.value, ft.a.renderPathIgnoreDepth.value);
        final int n = i + 10;
        final int n2 = i + 20;
        int n4;
        for (List<BetterBlockPos> positions = path.positions(); i < positions.size() - 1; i = n4) {
            final BetterBlockPos betterBlockPos = positions.get(i);
            BetterBlockPos betterBlockPos2;
            for (int n3 = (betterBlockPos2 = positions.get(n4 = i + 1)).a - betterBlockPos.a, n5 = betterBlockPos2.b - betterBlockPos.b, n6 = betterBlockPos2.c - betterBlockPos.c; n4 + 1 < positions.size() && (!b || n4 + 1 < n) && n3 == positions.get(n4 + 1).a - betterBlockPos2.a && n5 == positions.get(n4 + 1).b - betterBlockPos2.b && n6 == positions.get(n4 + 1).c - betterBlockPos2.c; betterBlockPos2 = positions.get(++n4)) {}
            if (b) {
                float n7;
                if (i <= n) {
                    n7 = 0.4f;
                }
                else {
                    if (i > n2) {
                        break;
                    }
                    n7 = 0.4f * (1.0f - (i - n) / (float)(n2 - n));
                }
                fq.a(color, n7);
            }
            a(betterBlockPos.a, betterBlockPos.b, betterBlockPos.c, betterBlockPos2.a, betterBlockPos2.b, betterBlockPos2.c);
            ft.a.b();
        }
        fq.a(ft.a.renderPathIgnoreDepth.value);
    }
    
    private static void a(final double n, final double n2, final double n3, final double n4, final double n5, final double n6) {
        final double h = ft.a.h;
        final double i = ft.a.i;
        final double j = ft.a.j;
        final boolean b = !ft.a.renderPathAsLine.value;
        ft.a.a(b ? 3 : 1, cdy.e);
        ft.a.b(n + 0.5 - h, n2 + 0.5 - i, n3 + 0.5 - j).d();
        ft.a.b(n4 + 0.5 - h, n5 + 0.5 - i, n6 + 0.5 - j).d();
        if (b) {
            ft.a.b(n4 + 0.5 - h, n5 + 0.53 - i, n6 + 0.5 - j).d();
            ft.a.b(n + 0.5 - h, n2 + 0.53 - i, n3 + 0.5 - j).d();
            ft.a.b(n + 0.5 - h, n2 + 0.5 - i, n3 + 0.5 - j).d();
        }
    }
    
    public static void a(final vg vg, final Collection<et> collection, final Color color) {
        fq.a(color, ft.a.pathRenderLineWidthPixels.value, ft.a.renderSelectionBoxesIgnoreDepth.value);
        final Object o;
        bhb bhb;
        final awt awt;
        collection.forEach(et -> {
            new fn(BaritoneAPI.getProvider().getPrimaryBaritone().getPlayerContext()).a(et);
            if (((awt)o).u().equals(aox.a)) {
                bhb = aox.d.t().c(vg.l, et);
            }
            else {
                bhb = awt.c(vg.l, et);
            }
            fq.a(bhb, 0.002);
            return;
        });
        fq.a(ft.a.renderSelectionBoxesIgnoreDepth.value);
    }
    
    private static void a(vg vg, Goal goal, float n, Color color) {
        double n3;
        double n4;
        double n5;
        double n6;
        double n7;
        double n8;
        double n10;
        double n9;
        while (true) {
            final double h = ft.a.h;
            final double i = ft.a.i;
            final double j = ft.a.j;
            double n2 = rk.b((float)(System.nanoTime() / 100000L % 20000L / 20000.0f * 3.141592653589793 * 2.0));
            if (goal instanceof IGoalRenderPos) {
                final et goalPos;
                n3 = (goalPos = ((IGoalRenderPos)goal).getGoalPos()).p() + 0.002 - h;
                n4 = goalPos.p() + 1 - 0.002 - h;
                n5 = goalPos.r() + 0.002 - j;
                n6 = goalPos.r() + 1 - 0.002 - j;
                if (goal instanceof GoalGetToBlock || goal instanceof GoalTwoBlocks) {
                    n2 /= 2.0;
                }
                n7 = n2 + 1.0 + goalPos.q() - i;
                n8 = 1.0 - n2 + goalPos.q() - i;
                n9 = (n10 = goalPos.q() - i) + 2.0;
                if (goal instanceof GoalGetToBlock || goal instanceof GoalTwoBlocks) {
                    n7 -= 0.5;
                    n8 -= 0.5;
                    --n9;
                }
                break;
            }
            if (goal instanceof GoalXZ) {
                final GoalXZ goalXZ = (GoalXZ)goal;
                if (ft.a.renderGoalXZBeacon.value) {
                    GL11.glPushAttrib(64);
                    Helper.mc.N().a(bwv.a);
                    if (ft.a.renderGoalIgnoreDepth.value) {
                        bus.j();
                    }
                    bwv.a(goalXZ.getX() - h, -i, goalXZ.getZ() - j, (double)n, 1.0, (double)vg.l.R(), 0, 256, color.getColorComponents(null));
                    if (ft.a.renderGoalIgnoreDepth.value) {
                        bus.k();
                    }
                    GL11.glPopAttrib();
                    return;
                }
                n3 = goalXZ.getX() + 0.002 - h;
                n4 = goalXZ.getX() + 1 - 0.002 - h;
                n5 = goalXZ.getZ() + 0.002 - j;
                n6 = goalXZ.getZ() + 1 - 0.002 - j;
                n7 = 0.0;
                n8 = 0.0;
                n10 = 0.0 - i;
                n9 = 256.0 - i;
                break;
            }
            else {
                if (goal instanceof GoalComposite) {
                    Goal[] goals;
                    for (int length = (goals = ((GoalComposite)goal).goals()).length, k = 0; k < length; ++k) {
                        a(vg, goals[k], n, color);
                    }
                    return;
                }
                if (goal instanceof GoalInverted) {
                    final vg vg2 = vg;
                    final Goal origin = ((GoalInverted)goal).origin;
                    final float n11 = n;
                    color = ft.a.colorInvertedGoalBox.value;
                    n = n11;
                    goal = origin;
                    vg = vg2;
                }
                else {
                    if (goal instanceof GoalYLevel) {
                        final GoalYLevel goalYLevel = (GoalYLevel)goal;
                        n3 = vg.p - ft.a.yLevelBoxSize.value - h;
                        n5 = vg.r - ft.a.yLevelBoxSize.value - j;
                        n4 = vg.p + ft.a.yLevelBoxSize.value - h;
                        n6 = vg.r + ft.a.yLevelBoxSize.value - j;
                        n9 = (n10 = ((GoalYLevel)goal).level - i) + 2.0;
                        n7 = n2 + 1.0 + goalYLevel.level - i;
                        n8 = 1.0 - n2 + goalYLevel.level - i;
                        break;
                    }
                    return;
                }
            }
        }
        fq.a(color, ft.a.goalRenderLineWidthPixels.value, ft.a.renderGoalIgnoreDepth.value);
        a(n3, n4, n5, n6, n7);
        a(n3, n4, n5, n6, n8);
        ft.a.a(1, cdy.e);
        ft.a.b(n3, n10, n5).d();
        ft.a.b(n3, n9, n5).d();
        ft.a.b(n4, n10, n5).d();
        ft.a.b(n4, n9, n5).d();
        ft.a.b(n4, n10, n6).d();
        ft.a.b(n4, n9, n6).d();
        ft.a.b(n3, n10, n6).d();
        ft.a.b(n3, n9, n6).d();
        ft.a.b();
        fq.a(ft.a.renderGoalIgnoreDepth.value);
    }
    
    private static void a(final double n, final double n2, final double n3, final double n4, final double n5) {
        if (n5 != 0.0) {
            ft.a.a(2, cdy.e);
            ft.a.b(n, n5, n3).d();
            ft.a.b(n2, n5, n3).d();
            ft.a.b(n2, n5, n4).d();
            ft.a.b(n, n5, n4).d();
            ft.a.b();
        }
    }
}
