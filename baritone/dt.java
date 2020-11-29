// 
// Decompiled by Procyon v0.5.36
// 

package baritone;

import baritone.api.utils.input.Input;
import baritone.api.utils.Rotation;
import baritone.api.utils.RotationUtils;
import baritone.api.utils.VecUtils;
import java.util.Optional;
import baritone.api.pathing.movement.MovementStatus;
import com.google.common.collect.ImmutableSet;
import java.util.Set;
import baritone.api.utils.BetterBlockPos;
import baritone.api.IBaritone;

public final class dt extends ck
{
    private boolean a;
    
    public dt(final IBaritone baritone, final BetterBlockPos betterBlockPos, final BetterBlockPos betterBlockPos2) {
        super(baritone, betterBlockPos, betterBlockPos2, new BetterBlockPos[] { betterBlockPos2.up(), betterBlockPos2 }, betterBlockPos2.down());
        this.a = true;
    }
    
    @Override
    public final void reset() {
        super.reset();
        this.a = true;
    }
    
    @Override
    public final double a(final cj cj) {
        return a(cj, this.a.a, this.a.b, this.a.c, this.b.a, this.b.c);
    }
    
    public final Set<BetterBlockPos> a() {
        return (Set<BetterBlockPos>)ImmutableSet.of((Object)this.a, (Object)this.b);
    }
    
    public static double a(final cj cj, final int n, final int n2, final int n3, final int n4, final int n5) {
        final awt a = cj.a(n4, n2 + 1, n5);
        final awt a2 = cj.a(n4, n2, n5);
        final awt a3 = cj.a(n4, n2 - 1, n5);
        final aow a4 = cj.a(n, n2 - 1, n3);
        if (cl.c(cj.a, n4, n2 - 1, n5, a3)) {
            double b = 4.63284688441047;
            boolean b2 = false;
            if (cl.b(a.u()) || cl.b(a2.u())) {
                b = cj.b;
                b2 = true;
            }
            else {
                if (a3.u() == aox.aW) {
                    b = 6.949270326615705;
                }
                else if (a3.u() == aox.j) {
                    b = 4.63284688441047 + cj.f;
                }
                if (a4 == aox.aW) {
                    b += 2.316423442205235;
                }
            }
            double a5;
            if ((a5 = cl.a(cj, n4, n2, n5, a2, false)) >= 1000000.0) {
                return 1000000.0;
            }
            double a6 = cl.a(cj, n4, n2 + 1, n5, a, true);
            if (a5 == 0.0 && a6 == 0.0) {
                if (!b2 && cj.d) {
                    b *= 0.7692444761225944;
                }
                return b;
            }
            if (a4 == aox.au || a4 == aox.bn) {
                a5 *= 5.0;
                a6 *= 5.0;
            }
            return b + a5 + a6;
        }
        else {
            if (a4 == aox.au || a4 == aox.bn) {
                return 1000000.0;
            }
            if (!cl.a(n4, n5, a3, cj.a)) {
                return 1000000.0;
            }
            final boolean b3 = cl.b(a.u()) || cl.b(a2.u());
            if (cl.b(a3.u()) && b3) {
                return 1000000.0;
            }
            final double a7;
            if ((a7 = cj.a(n4, n2 - 1, n5, a3)) >= 1000000.0) {
                return 1000000.0;
            }
            final double a8;
            if ((a8 = cl.a(cj, n4, n2, n5, a2, false)) >= 1000000.0) {
                return 1000000.0;
            }
            final double a9 = cl.a(cj, n4, n2 + 1, n5, a, true);
            final double n6 = b3 ? cj.b : 4.63284688441047;
            for (int i = 0; i < 5; ++i) {
                final int n7 = n4 + dt.a[i].g();
                final int n8 = n2 - 1 + dt.a[i].h();
                final int n9 = n5 + dt.a[i].i();
                if ((n7 != n || n9 != n3) && cl.c(cj.a, n7, n8, n9)) {
                    return n6 + a7 + a8 + a9;
                }
            }
            if (a4 == aox.aW || (a4 instanceof arf && !((arf)a4).e())) {
                return 1000000.0;
            }
            if (a4 == aox.i || a4 == aox.j) {
                return 1000000.0;
            }
            return n6 * 3.3207692307692307 + a7 + a8 + a9;
        }
    }
    
    @Override
    public final cn a(final cn cn) {
        super.a(cn);
        final awt a = fn.a(this.a, (et)this.a[0]);
        final awt a2 = fn.a(this.a, (et)this.a[1]);
        if (cn.a != MovementStatus.RUNNING) {
            if (!baritone.a.a().walkWhileBreaking.value) {
                return cn;
            }
            if (cn.a != MovementStatus.PREPPING) {
                return cn;
            }
            if (cl.a(a.u())) {
                return cn;
            }
            if (cl.a(a2.u())) {
                return cn;
            }
            if (Math.max(Math.abs(this.a.player().p - (this.b.p() + 0.5)), Math.abs(this.a.player().r - (this.b.r() + 0.5))) < 0.83) {
                return cn;
            }
            if (!Optional.ofNullable(cn.a.a).isPresent()) {
                return cn;
            }
            final float yaw = RotationUtils.calcRotationFromVec3d(this.a.playerHead(), VecUtils.calculateBlockCenter(this.a.world(), this.b), this.a.playerRotations()).getYaw();
            float pitch = Optional.ofNullable(cn.a.a).get().getPitch();
            if (a.g() || (a.u() instanceof aom && (a2.g() || a2.u() instanceof aom))) {
                pitch = 26.0f;
            }
            return cn.a(new co(new Rotation(yaw, pitch), true)).a(Input.MOVE_FORWARD, true).a(Input.SPRINT, true);
        }
        else {
            cn.a(Input.SNEAK, false);
            final aow u;
            final boolean b = (u = fn.a(this.a, (et)this.a.down()).u()) == aox.au || u == aox.bn;
            if (a.u() instanceof aqa || a2.u() instanceof aqa) {
                final boolean b2 = (a.u() instanceof aqa && !cl.a(this.a, this.a, this.b)) || (a2.u() instanceof aqa && !cl.a(this.a, this.b, this.a));
                final boolean b3 = !aox.aA.equals(a.u()) && !aox.aA.equals(a2.u());
                if (b2 && b3) {
                    return cn.a(new co(RotationUtils.calcRotationFromVec3d(this.a.playerHead(), VecUtils.calculateBlockCenter(this.a.world(), this.a[0]), this.a.playerRotations()), true)).a(Input.CLICK_RIGHT, true);
                }
            }
            if (a.u() instanceof aqp || a2.u() instanceof aqp) {
                BetterBlockPos betterBlockPos2;
                final BetterBlockPos betterBlockPos = cl.b(this.a, this.a[0], this.a.up()) ? (cl.b(this.a, this.a[1], this.a) ? (betterBlockPos2 = null) : (betterBlockPos2 = this.a[1])) : (betterBlockPos2 = this.a[0]);
                final BetterBlockPos betterBlockPos3 = betterBlockPos2;
                final Optional<Rotation> reachable;
                if (betterBlockPos != null && (reachable = RotationUtils.reachable(this.a, betterBlockPos3)).isPresent()) {
                    return cn.a(new co(reachable.get(), true)).a(Input.CLICK_RIGHT, true);
                }
            }
            final boolean b4 = cl.b(this.a, this.c) || b;
            final BetterBlockPos playerFeet;
            if ((playerFeet = this.a.playerFeet()).q() != this.b.q() && !b) {
                this.logDebug("Wrong Y coordinate");
                if (playerFeet.q() < this.b.q()) {
                    return cn.a(Input.JUMP, true);
                }
                return cn;
            }
            else if (b4) {
                if (playerFeet.equals((Object)this.b)) {
                    cn.a = MovementStatus.SUCCESS;
                    return cn;
                }
                if (baritone.a.a().overshootTraverse.value && (playerFeet.equals((Object)this.b.a((fq)this.getDirection())) || playerFeet.equals((Object)this.b.a((fq)this.getDirection()).a((fq)this.getDirection())))) {
                    cn.a = MovementStatus.SUCCESS;
                    return cn;
                }
                final aow u2 = fn.a(this.a, (et)this.a).u();
                final aow u3 = fn.a(this.a, (et)this.a.up()).u();
                if (this.a.player().q > this.a.b + 0.1 && !this.a.player().z && (u2 == aox.bn || u2 == aox.au || u3 == aox.bn || u3 == aox.au)) {
                    return cn;
                }
                final et a3 = this.b.b((fq)this.a).a((fq)this.b);
                final aow u4 = fn.a(this.a, a3).u();
                final aow u5 = fn.a(this.a, a3.a()).u();
                if (this.a && (!cl.e(this.a, playerFeet) || baritone.a.a().sprintInWater.value) && (!cl.a(u4) || cl.b(u4)) && !cl.a(u5)) {
                    cn.a(Input.SPRINT, true);
                }
                final awt a4 = fn.a(this.a, (et)this.b.down());
                et et = this.a[0];
                if (playerFeet.q() != this.b.q() && b && (a4.u() == aox.bn || a4.u() == aox.au) && (et = ((a4.u() == aox.bn) ? ds.a(new cj(this.a), this.b.down()) : this.b.offset(((fa)a4.c((axj)arq.a)).d()))) == null) {
                    this.logDirect("Unable to climb vines. Consider disabling allowVines.");
                    cn.a = MovementStatus.UNREACHABLE;
                    return cn;
                }
                cl.a(this.a, cn, et);
                return cn;
            }
            else {
                this.a = false;
                final aow u6;
                if (((u6 = fn.a(this.a, playerFeet.b()).u()).equals(aox.aW) || u6 instanceof arf) && Math.max(Math.abs(this.b.p() + 0.5 - this.a.player().p), Math.abs(this.b.r() + 0.5 - this.a.player().r)) < 0.85) {
                    cl.a(this.a, cn, this.b);
                    return cn.a(Input.MOVE_FORWARD, false).a(Input.MOVE_BACK, true);
                }
                final double max = Math.max(Math.abs(this.a.player().p - (this.b.p() + 0.5)), Math.abs(this.a.player().r - (this.b.r() + 0.5)));
                final int a5;
                if (((a5 = cl.a(cn, this.a, this.b.down(), false, true)) == cm.a || max < 0.6) && !baritone.a.a().assumeSafeWalk.value) {
                    cn.a(Input.SNEAK, true);
                }
                switch (du.a[a5 - 1]) {
                    case 1: {
                        if (this.a.player().aU() || baritone.a.a().assumeSafeWalk.value) {
                            cn.a(Input.CLICK_RIGHT, true);
                        }
                        return cn;
                    }
                    case 2: {
                        if (max > 0.83) {
                            if (Math.abs(cn.a.a.getYaw() - RotationUtils.calcRotationFromVec3d(this.a.playerHead(), VecUtils.getBlockPosCenter(this.b), this.a.playerRotations()).getYaw()) < 0.1) {
                                return cn.a(Input.MOVE_FORWARD, true);
                            }
                        }
                        else if (this.a.playerRotations().isReallyCloseTo(cn.a.a)) {
                            return cn.a(Input.CLICK_LEFT, true);
                        }
                        return cn;
                    }
                    default: {
                        if (!playerFeet.equals((Object)this.b)) {
                            cl.a(this.a, cn, this.a[0]);
                            return cn;
                        }
                        final double n = (this.b.p() + this.a.p() + 1.0) * 0.5;
                        final double n2 = (this.b.q() + this.a.q() - 1.0) * 0.5;
                        final double n3 = (this.b.r() + this.a.r() + 1.0) * 0.5;
                        final BetterBlockPos down = this.a.down();
                        final Rotation calcRotationFromVec3d;
                        final float pitch2 = (calcRotationFromVec3d = RotationUtils.calcRotationFromVec3d(this.a.playerHead(), new bhe(n, n2, n3), this.a.playerRotations())).getPitch();
                        if (Math.max(Math.abs(this.a.player().p - n), Math.abs(this.a.player().r - n3)) < 0.29) {
                            cn.a(new co(new Rotation(RotationUtils.calcRotationFromVec3d(VecUtils.getBlockPosCenter(this.b), this.a.playerHead(), this.a.playerRotations()).getYaw(), pitch2), true));
                            cn.a(Input.MOVE_BACK, true);
                        }
                        else {
                            cn.a(new co(calcRotationFromVec3d, true));
                        }
                        if (this.a.isLookingAt(down)) {
                            return cn.a(Input.CLICK_RIGHT, true);
                        }
                        if (this.a.playerRotations().isReallyCloseTo(cn.a.a)) {
                            cn.a(Input.CLICK_LEFT, true);
                        }
                        return cn;
                    }
                }
            }
        }
    }
    
    public final boolean b(final cn cn) {
        return cn.a != MovementStatus.RUNNING || cl.b(this.a, this.b.down());
    }
    
    public final boolean a(final cn cn) {
        final aow a;
        if ((this.a.playerFeet().equals(this.a) || this.a.playerFeet().equals(this.a.down())) && ((a = fn.a(this.a, (et)this.a.down())) == aox.au || a == aox.bn)) {
            cn.a(Input.SNEAK, true);
        }
        return super.a(cn);
    }
}
