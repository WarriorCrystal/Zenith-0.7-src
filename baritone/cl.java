// 
// Decompiled by Procyon v0.5.36
// 

package baritone;

import java.util.Optional;
import baritone.api.utils.RayTraceUtils;
import baritone.api.pathing.movement.MovementStatus;
import baritone.api.IBaritone;
import baritone.api.utils.input.Input;
import baritone.api.utils.Rotation;
import baritone.api.utils.RotationUtils;
import baritone.api.utils.VecUtils;
import baritone.api.BaritoneAPI;
import java.util.List;
import baritone.api.utils.BetterBlockPos;
import baritone.api.utils.IPlayerContext;
import baritone.api.utils.Helper;
import baritone.api.pathing.movement.ActionCosts;

public interface cl extends ActionCosts, Helper
{
    default boolean a(final fn fn, final int n, final int n2, final int n3, final awt awt) {
        final aow u;
        return (u = awt.u()) == aox.aI || u instanceof asa || a(fn, n, n2 + 1, n3, true) || a(fn, n + 1, n2, n3, false) || a(fn, n - 1, n2, n3, false) || a(fn, n, n2, n3 + 1, false) || a(fn, n, n2, n3 - 1, false);
    }
    
    default boolean a(final fn fn, final int n, final int n2, final int n3, final boolean b) {
        final aow u = fn.a(n, n2, n3).u();
        return (!b && u instanceof aqm && a.a().avoidUpdatingFallingBlocks.value && aqm.x(fn.a(n, n2 - 1, n3))) || u instanceof aru;
    }
    
    default boolean a(final IPlayerContext playerContext, final BetterBlockPos betterBlockPos) {
        return a(new fn(playerContext), betterBlockPos.a, betterBlockPos.b, betterBlockPos.c);
    }
    
    default boolean a(final fn fn, final int n, final int n2, final int n3) {
        return b(fn, n, n2, n3, fn.a(n, n2, n3));
    }
    
    default boolean b(final fn fn, final int n, final int n2, final int n3, final awt awt) {
        final aow u;
        if ((u = awt.u()) == aox.a) {
            return true;
        }
        if (u == aox.ab || u == aox.bS || u == aox.G || u == aox.bF || u == aox.bN || u instanceof att || u instanceof aur || u == aox.cQ) {
            return false;
        }
        if (a.a().blocksToAvoid.value.contains(u)) {
            return false;
        }
        if (u instanceof aqa || u instanceof aqp) {
            return u != aox.aA;
        }
        if (u == aox.cy) {
            return b(fn, n, n2 - 1, n3);
        }
        if (u instanceof atw) {
            return !fn.a(n, n3) || ((int)awt.c((axj)atw.a) < 3 && b(fn, n, n2 - 1, n3));
        }
        if (a(n, n2, n3, awt, fn)) {
            return false;
        }
        if (u instanceof aru) {
            final awt a;
            return !baritone.a.a().assumeWalkOnWater.value && !((a = fn.a(n, n2 + 1, n3)).u() instanceof aru) && !(a.u() instanceof auy) && (u == aox.j || u == aox.i);
        }
        return u.b(fn.b, (et)fn.a.c(n, n2, n3));
    }
    
    default boolean a(final cj cj, final int n, final int n2, final int n3) {
        return a(cj.a.b, (et)cj.a.a.c(n, n2, n3), cj.a.a(n, n2, n3));
    }
    
    default boolean a(final IPlayerContext playerContext, final et et) {
        return a((amy)playerContext.world(), et, playerContext.world().o(et));
    }
    
    default boolean a(final amy amy, final et et, final awt awt) {
        final aow u;
        return (u = awt.u()) == aox.a || (u != aox.ab && u != aox.bS && u != aox.G && u != aox.bn && u != aox.au && u != aox.bN && !(u instanceof aqa) && !(u instanceof aqp) && !(u instanceof atw) && !(u instanceof aru) && !(u instanceof aur) && !(u instanceof aqh) && !(u instanceof att) && u.b(amy, et));
    }
    
    default boolean a(final int n, final int n2, final awt awt, final fn fn) {
        final aow u;
        if ((u = awt.u()) == aox.a || b(u)) {
            return true;
        }
        if (u instanceof atw) {
            return !fn.a(n, n2) || (int)awt.c((axj)atw.a) == 1;
        }
        if (u instanceof aqb) {
            final aqb$b aqb$b;
            return (aqb$b = (aqb$b)awt.c((axj)aqb.a)) == aqb$b.d || aqb$b == aqb$b.c;
        }
        return awt.a().j();
    }
    
    default boolean a(final IPlayerContext playerContext, final et et, et et2) {
        if (et2.equals((Object)et)) {
            return false;
        }
        final awt a;
        if (!((a = fn.a(playerContext, et)).u() instanceof aqa)) {
            return true;
        }
        final awt awt = a;
        final et et3 = et2;
        final axf b = aqa.b;
        et2 = et3;
        final awt awt2 = awt;
        if (!et2.equals((Object)et)) {
            final fa$a k = ((fa)awt2.c((axj)arm.D)).k();
            final boolean booleanValue = (boolean)awt2.c((axj)b);
            fa$a fa$a;
            if (et2.c().equals((Object)et) || et2.d().equals((Object)et)) {
                fa$a = fa$a.c;
            }
            else {
                if (!et2.f().equals((Object)et) && !et2.e().equals((Object)et)) {
                    return true;
                }
                fa$a = fa$a.a;
            }
            if (k == fa$a == booleanValue) {
                return true;
            }
        }
        return false;
    }
    
    default boolean b(final IPlayerContext playerContext, final et et, final et et2) {
        final awt a;
        return !et2.equals((Object)et) && (!((a = fn.a(playerContext, et)).u() instanceof aqp) || (boolean)a.c((axj)aqp.a));
    }
    
    default boolean a(final aow aow) {
        return aow instanceof aru || aow == aox.df || aow == aox.aK || aow == aox.ab || aow == aox.bF || aow == aox.G;
    }
    
    default boolean c(final fn fn, final int n, final int n2, final int n3, final awt awt) {
        final aow u;
        if ((u = awt.u()) == aox.a || u == aox.df) {
            return false;
        }
        if (awt.k()) {
            return true;
        }
        if (u == aox.au || (u == aox.bn && a.a().allowVines.value)) {
            return true;
        }
        if (u == aox.ak || u == aox.da) {
            return true;
        }
        if (u == aox.bQ || u == aox.ae || u == aox.cg) {
            return true;
        }
        if (b(u)) {
            final aow u2;
            if ((u2 = fn.a(n, n2 + 1, n3).u()) == aox.bx || u2 == aox.cy) {
                return true;
            }
            if (a(n, n2, n3, awt, fn) || u == aox.i) {
                return b(u2) && !a.a().assumeWalkOnWater.value;
            }
            return b(u2) ^ a.a().assumeWalkOnWater.value;
        }
        else {
            if (a.a().assumeWalkOnLava.value && c(u) && !a(n, n2, n3, awt, fn)) {
                return true;
            }
            if (u == aox.w || u == aox.cG) {
                return true;
            }
            if (u instanceof arf) {
                return a.a().allowWalkOnBottomSlab.value || ((arf)u).e() || awt.c((axj)arf.a) != arf$a.b;
            }
            return u instanceof aud;
        }
    }
    
    default boolean b(final IPlayerContext playerContext, final et et) {
        return b(new fn(playerContext), et.p(), et.q(), et.r());
    }
    
    default boolean b(final IPlayerContext playerContext, final BetterBlockPos betterBlockPos) {
        return b(new fn(playerContext), betterBlockPos.a, betterBlockPos.b, betterBlockPos.c);
    }
    
    default boolean b(final fn fn, final int n, final int n2, final int n3) {
        return c(fn, n, n2, n3, fn.a(n, n2, n3));
    }
    
    default boolean c(final fn fn, final int n, final int n2, final int n3) {
        final awt a;
        return (a = fn.a(n, n2, n3)).k() || a.b() || a.u() == aox.w || a.u() == aox.cG;
    }
    
    default boolean c(final IPlayerContext playerContext, final et et) {
        return c(new fn(playerContext), et.p(), et.q(), et.r());
    }
    
    default double a(final cj cj, final int n, final int n2, final int n3, final boolean b) {
        return a(cj, n, n2, n3, cj.a(n, n2, n3), b);
    }
    
    default double a(final cj cj, final int n, final int n2, final int n3, awt a, final boolean b) {
        final aow u = a.u();
        if (b(cj.a, n, n2, n3, a)) {
            return 0.0;
        }
        if (u instanceof aru) {
            return 1000000.0;
        }
        final double b2;
        if ((b2 = cj.b(n, n2, n3, a)) >= 1000000.0) {
            return 1000000.0;
        }
        if (a(cj.a, n, n2, n3, a)) {
            return 1000000.0;
        }
        final double a2;
        if ((a2 = cj.a.a(a)) <= 0.0) {
            return 1000000.0;
        }
        double n4 = (1.0 / a2 + cj.c) * b2;
        if (b && (a = cj.a(n, n2 + 1, n3)).u() instanceof aqm) {
            n4 += a(cj, n, n2 + 1, n3, a, true);
        }
        return n4;
    }
    
    default boolean a(final awt awt) {
        return awt.u() instanceof arf && !((arf)awt.u()).e() && awt.c((axj)arf.a) == arf$a.b;
    }
    
    default void a(final IPlayerContext playerContext, final awt awt) {
        a(playerContext, awt, new fz(playerContext.player()), BaritoneAPI.getSettings().preferSilkTouch.value);
    }
    
    default void a(final IPlayerContext playerContext, final awt awt, final fz fz, final boolean b) {
        playerContext.player().bv.d = fz.a(awt.u(), b);
    }
    
    default void a(final IPlayerContext playerContext, final cn cn, final et et) {
        cn.a(new co(new Rotation(RotationUtils.calcRotationFromVec3d(playerContext.playerHead(), VecUtils.getBlockPosCenter(et), playerContext.playerRotations()).getYaw(), playerContext.player().w), false)).a(Input.MOVE_FORWARD, true);
    }
    
    default boolean b(final aow aow) {
        return aow == aox.i || aow == aox.j;
    }
    
    default boolean d(final IPlayerContext playerContext, final et et) {
        return b(fn.a(playerContext, et));
    }
    
    default boolean c(final aow aow) {
        return aow == aox.k || aow == aox.l;
    }
    
    default boolean e(final IPlayerContext playerContext, final et et) {
        return fn.a(playerContext, et) instanceof aru;
    }
    
    default boolean b(final awt awt) {
        return awt.u() instanceof aru && (int)awt.c((axj)aru.b) != 0;
    }
    
    default boolean a(final int n, final int n2, final int n3, final awt awt, final fn fn) {
        return awt.u() instanceof aru && ((int)awt.c((axj)aru.b) != 0 || (b(fn.a(n + 1, n2, n3)) || b(fn.a(n - 1, n2, n3)) || b(fn.a(n, n2, n3 + 1)) || b(fn.a(n, n2, n3 - 1))));
    }
    
    default int a(final cn cn, final IBaritone baritone, final et et, final boolean b, final boolean b2) {
        final IPlayerContext playerContext;
        final Optional<Rotation> reachable = RotationUtils.reachable(playerContext = baritone.getPlayerContext(), et, b2);
        boolean b3 = false;
        if (reachable.isPresent()) {
            cn.a(new co(reachable.get(), true));
            b3 = true;
        }
        for (int i = 0; i < 5; ++i) {
            final et a = et.a(ck.a[i]);
            if (c(playerContext, a)) {
                if (!((a)baritone).a.a(false, et.p(), et.q(), et.r())) {
                    Helper.HELPER.logDebug("bb pls get me some blocks. dirt, netherrack, cobble");
                    cn.a = MovementStatus.UNREACHABLE;
                    return cm.c;
                }
                final Rotation calcRotationFromVec3d = RotationUtils.calcRotationFromVec3d(b2 ? RayTraceUtils.inferSneakingEyePosition((vg)playerContext.player()) : playerContext.playerHead(), new bhe((et.p() + a.p() + 1.0) * 0.5, (et.q() + a.q() + 0.5) * 0.5, (et.r() + a.r() + 1.0) * 0.5), playerContext.playerRotations());
                final bhc rayTraceTowards;
                if ((rayTraceTowards = RayTraceUtils.rayTraceTowards((vg)playerContext.player(), calcRotationFromVec3d, playerContext.playerController().getBlockReachDistance(), b2)) != null && rayTraceTowards.a == bhc$a.b && rayTraceTowards.a().equals((Object)a) && rayTraceTowards.a().a(rayTraceTowards.b).equals((Object)et)) {
                    cn.a(new co(calcRotationFromVec3d, true));
                    b3 = true;
                    if (!b) {
                        break;
                    }
                }
            }
        }
        if (playerContext.getSelectedBlock().isPresent()) {
            final et et2 = playerContext.getSelectedBlock().get();
            final fa b4 = playerContext.objectMouseOver().b;
            if (et2.equals((Object)et) || (c(playerContext, et2) && et2.a(b4).equals((Object)et))) {
                if (b2) {
                    cn.a(Input.SNEAK, true);
                }
                ((a)baritone).a.a(true, et.p(), et.q(), et.r());
                return cm.a;
            }
        }
        if (b3) {
            if (b2) {
                cn.a(Input.SNEAK, true);
            }
            ((a)baritone).a.a(true, et.p(), et.q(), et.r());
            return cm.b;
        }
        return cm.c;
    }
}
