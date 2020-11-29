// 
// Decompiled by Procyon v0.5.36
// 

package baritone;

import java.util.Objects;
import java.util.function.Consumer;
import baritone.api.utils.Rotation;
import baritone.api.utils.input.Input;
import baritone.api.utils.RotationUtils;
import baritone.api.utils.VecUtils;
import baritone.api.pathing.movement.MovementStatus;
import com.google.common.collect.ImmutableSet;
import java.util.Set;
import baritone.api.utils.BetterBlockPos;
import baritone.api.IBaritone;

public final class ds extends ck
{
    public ds(final IBaritone baritone, final BetterBlockPos betterBlockPos, final BetterBlockPos betterBlockPos2) {
        super(baritone, betterBlockPos, betterBlockPos2, new BetterBlockPos[] { betterBlockPos.up(2) }, betterBlockPos);
    }
    
    @Override
    public final double a(final cj cj) {
        return a(cj, this.a.a, this.a.b, this.a.c);
    }
    
    public final Set<BetterBlockPos> a() {
        return (Set<BetterBlockPos>)ImmutableSet.of((Object)this.a, (Object)this.b);
    }
    
    public static double a(final cj cj, final int n, final int n2, final int n3) {
        final awt a;
        final aow u;
        final boolean b = (u = (a = cj.a(n, n2, n3)).u()) == aox.au || u == aox.bn;
        final awt a2 = cj.a(n, n2 - 1, n3);
        if (!b) {
            if (a2.u() == aox.au || a2.u() == aox.bn) {
                return 1000000.0;
            }
            if (a2.u() instanceof arf && !((arf)a2.u()).e() && a2.c((axj)arf.a) == arf$a.b) {
                return 1000000.0;
            }
        }
        if (u == aox.bn && !b(cj, n, n2, n3)) {
            return 1000000.0;
        }
        final awt a3;
        final aow u2;
        if ((u2 = (a3 = cj.a(n, n2 + 2, n3)).u()) instanceof aqp) {
            return 1000000.0;
        }
        aow aow = null;
        if (cl.b(u2) && cl.b(u) && cl.b(aow = cj.a(n, n2 + 1, n3).u())) {
            return 8.51063829787234;
        }
        double a4 = 0.0;
        if (!b) {
            if ((a4 = cj.a(n, n2, n3, a)) >= 1000000.0) {
                return 1000000.0;
            }
            if (a2.u() == aox.a) {
                a4 += 0.1;
            }
        }
        if (u instanceof aru || (a2.u() instanceof aru && cj.j)) {
            return 1000000.0;
        }
        double a5;
        if ((a5 = cl.a(cj, n, n2 + 2, n3, a3, true)) >= 1000000.0) {
            return 1000000.0;
        }
        if (a5 != 0.0) {
            if (u2 == aox.au || u2 == aox.bn) {
                a5 = 0.0;
            }
            else if (cj.a(n, n2 + 3, n3).u() instanceof aqm) {
                if (aow == null) {
                    aow = cj.a(n, n2 + 1, n3).u();
                }
                if (!(u2 instanceof aqm) || !(aow instanceof aqm)) {
                    return 1000000.0;
                }
            }
        }
        if (b) {
            return 8.51063829787234 + a5 * 5.0;
        }
        return ds.JUMP_ONE_BLOCK_COST + a4 + cj.e + a5;
    }
    
    private static boolean b(final cj cj, final int n, final int n2, final int n3) {
        return cj.a(n + 1, n2, n3).k() || cj.a(n - 1, n2, n3).k() || cj.a(n, n2, n3 + 1).k() || cj.a(n, n2, n3 - 1).k();
    }
    
    public static et a(final cj cj, final BetterBlockPos betterBlockPos) {
        if (cj.a(betterBlockPos.north()).k()) {
            return betterBlockPos.north();
        }
        if (cj.a(betterBlockPos.south()).k()) {
            return betterBlockPos.south();
        }
        if (cj.a(betterBlockPos.east()).k()) {
            return betterBlockPos.east();
        }
        if (cj.a(betterBlockPos.west()).k()) {
            return betterBlockPos.west();
        }
        return null;
    }
    
    @Override
    public final cn a(final cn cn) {
        super.a(cn);
        if (cn.a != MovementStatus.RUNNING) {
            return cn;
        }
        if (this.a.playerFeet().b < this.a.b) {
            cn.a = MovementStatus.UNREACHABLE;
            return cn;
        }
        final awt a;
        if (cl.b((a = fn.a(this.a, (et)this.a)).u()) && cl.d(this.a, this.b)) {
            cn.a(new co(RotationUtils.calcRotationFromVec3d(this.a.playerHead(), VecUtils.getBlockPosCenter(this.b), this.a.playerRotations()), false));
            final bhe blockPosCenter = VecUtils.getBlockPosCenter(this.b);
            if (Math.abs(this.a.player().p - blockPosCenter.b) > 0.2 || Math.abs(this.a.player().r - blockPosCenter.d) > 0.2) {
                cn.a(Input.MOVE_FORWARD, true);
            }
            if (this.a.playerFeet().equals(this.b)) {
                cn.a = MovementStatus.SUCCESS;
                return cn;
            }
            return cn;
        }
        else {
            final boolean b = a.u() == aox.au || a.u() == aox.bn;
            final boolean b2 = a.u() == aox.bn;
            final Rotation calcRotationFromVec3d = RotationUtils.calcRotationFromVec3d(this.a.playerHead(), VecUtils.getBlockPosCenter(this.c), new Rotation(this.a.player().v, this.a.player().w));
            if (!b) {
                cn.a(new co(new Rotation(this.a.player().v, calcRotationFromVec3d.getPitch()), true));
            }
            int n = (cl.b(this.a, this.a) || b) ? 1 : 0;
            if (b) {
                final et et;
                if ((et = (b2 ? a(new cj(this.a), this.a) : this.a.offset(((fa)a.c((axj)arq.a)).d()))) == null) {
                    this.logDirect("Unable to climb vines. Consider disabling allowVines.");
                    cn.a = MovementStatus.UNREACHABLE;
                    return cn;
                }
                if (this.a.playerFeet().equals(et.a()) || this.a.playerFeet().equals(this.b)) {
                    cn.a = MovementStatus.SUCCESS;
                    return cn;
                }
                if (cl.a(fn.a(this.a, (et)this.a.down()))) {
                    cn.a(Input.JUMP, true);
                }
                cl.a(this.a, cn, et);
                return cn;
            }
            else {
                if (!((a)this.a).a.a(true, this.a.a, this.a.b, this.a.c)) {
                    cn.a = MovementStatus.UNREACHABLE;
                    return cn;
                }
                cn.a(Input.SNEAK, this.a.player().q > this.b.q() || this.a.player().q < this.a.q() + 0.2);
                final double n2 = this.a.player().p - (this.b.p() + 0.5);
                final double n3 = this.a.player().r - (this.b.r() + 0.5);
                final double n4 = n2;
                final double n5 = n4 * n4;
                final double n6 = n3;
                final double sqrt = Math.sqrt(n5 + n6 * n6);
                final double sqrt2 = Math.sqrt(this.a.player().s * this.a.player().s + this.a.player().u * this.a.player().u);
                if (sqrt > 0.17) {
                    cn.a(Input.MOVE_FORWARD, true);
                    cn.a(new co(calcRotationFromVec3d, true));
                }
                else if (sqrt2 < 0.05) {
                    cn.a(Input.JUMP, this.a.player().q < this.b.q());
                }
                if (n == 0) {
                    final awt a2;
                    if (!((a2 = fn.a(this.a, (et)this.a)).u() instanceof aom) && !a2.a().j()) {
                        RotationUtils.reachable(this.a.player(), this.a, this.a.playerController().getBlockReachDistance()).map(rotation -> new co(rotation, true)).ifPresent((Consumer<? super Object>)cn::a);
                        cn.a(Input.JUMP, false);
                        cn.a(Input.CLICK_LEFT, true);
                        n = 0;
                    }
                    else if (this.a.player().aU() && (Objects.equals(this.a.down(), this.a.objectMouseOver().a()) || Objects.equals(this.a, this.a.objectMouseOver().a())) && this.a.player().q > this.b.q() + 0.1) {
                        cn.a(Input.CLICK_RIGHT, true);
                    }
                }
                if (this.a.playerFeet().equals(this.b) && n != 0) {
                    cn.a = MovementStatus.SUCCESS;
                    return cn;
                }
                return cn;
            }
        }
    }
    
    public final boolean a(final cn cn) {
        final aow a;
        if ((this.a.playerFeet().equals(this.a) || this.a.playerFeet().equals(this.a.down())) && ((a = fn.a(this.a, (et)this.a.down())) == aox.au || a == aox.bn)) {
            cn.a(Input.SNEAK, true);
        }
        return cl.d(this.a, this.b.up()) || super.a(cn);
    }
}
