// 
// Decompiled by Procyon v0.5.36
// 

package baritone;

import baritone.api.pathing.goals.GoalBlock;
import baritone.api.utils.IPlayerContext;
import baritone.api.event.events.TickEvent$Type;
import baritone.api.BaritoneAPI;
import baritone.api.event.events.TickEvent;
import baritone.api.pathing.goals.Goal;
import baritone.api.utils.Helper;
import baritone.api.event.listener.AbstractGameEventListener;

public final class fj implements AbstractGameEventListener, Helper
{
    public static final fj a;
    public static final boolean a;
    private static final et a;
    private static final Goal a;
    
    public static void a() {
        if (!fj.a) {
            return;
        }
        System.out.println("Optimizing Game Settings");
        final bid t;
        (t = fj.mc.t).i = 20;
        t.K = 0;
        t.aH = 2;
        t.D = 128;
        t.E = 128;
        t.F = false;
        t.M = false;
        t.G = 0.0f;
        t.l = 0;
        t.j = 0;
        t.k = false;
        t.S = cib.f;
        t.av = true;
        t.aD = 30.0f;
    }
    
    @Override
    public final void onTick(final TickEvent tickEvent) {
        final IPlayerContext playerContext = BaritoneAPI.getProvider().getPrimaryBaritone().getPlayerContext();
        if (fj.mc.m instanceof blr) {
            System.out.println("Beginning Baritone automatic test routine");
            fj.mc.a((blk)null);
            fj.mc.a("BaritoneAutoTest", "BaritoneAutoTest", new amx(-928872506371745L, ams.b, true, false, amz.b));
        }
        if (fj.mc.F() != null) {
            fj.mc.F().a(tz.a);
            oo[] d;
            for (int length = (d = fj.mc.F().d).length, i = 0; i < length; ++i) {
                final oo oo;
                if ((oo = d[i]) != null) {
                    oo.A(fj.a);
                    oo.W().a("spawnRadius", "0");
                }
            }
        }
        if (tickEvent.getType() == TickEvent$Type.IN) {
            if (fj.mc.E() && !fj.mc.F().a()) {
                fj.mc.F().a(ams.b, false);
            }
            if (tickEvent.getCount() < 200) {
                System.out.println("Waiting for world to generate... " + tickEvent.getCount());
                return;
            }
            if (tickEvent.getCount() % 100 == 0) {
                System.out.println(playerContext.playerFeet() + " " + tickEvent.getCount());
            }
            if (!BaritoneAPI.getProvider().getPrimaryBaritone().getCustomGoalProcess().isActive()) {
                BaritoneAPI.getProvider().getPrimaryBaritone().getCustomGoalProcess().setGoalAndPath(fj.a);
            }
            if (fj.a.isInGoal(playerContext.playerFeet())) {
                System.out.println("Successfully pathed to " + playerContext.playerFeet() + " in " + tickEvent.getCount() + " ticks");
                fj.mc.n();
            }
            if (tickEvent.getCount() > 3300) {
                throw new IllegalStateException("took too long");
            }
        }
    }
    
    private fj() {
    }
    
    static {
        a = new fj();
        a = "true".equals(System.getenv("BARITONE_AUTO_TEST"));
        a = new et(0, 65, 0);
        a = new GoalBlock(69, 69, 420);
    }
}
