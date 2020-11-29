// 
// Decompiled by Procyon v0.5.36
// 

package baritone;

import java.util.function.Function;
import java.util.Optional;
import baritone.api.utils.input.Input;
import baritone.api.utils.Rotation;
import baritone.api.utils.RotationUtils;
import baritone.api.utils.VecUtils;
import baritone.api.pathing.movement.MovementStatus;
import java.util.HashSet;
import java.util.Set;
import baritone.api.utils.BetterBlockPos;
import baritone.api.IBaritone;

public final class dq extends ck
{
    private static final aip a;
    private static final aip b;
    
    public dq(final IBaritone baritone, final BetterBlockPos betterBlockPos, final BetterBlockPos betterBlockPos2) {
        super(baritone, betterBlockPos, betterBlockPos2, a(betterBlockPos, betterBlockPos2));
    }
    
    @Override
    public final double a(final cj cj) {
        final gj gj = new gj();
        dn.a(cj, this.a.a, this.a.b, this.a.c, this.b.a, this.b.c, gj);
        if (gj.b != this.b.b) {
            return 1000000.0;
        }
        return gj.a;
    }
    
    public final Set<BetterBlockPos> a() {
        final HashSet<BetterBlockPos> set;
        (set = new HashSet<BetterBlockPos>()).add(this.a);
        for (int i = this.a.b - this.b.b; i >= 0; --i) {
            set.add(this.b.up(i));
        }
        return set;
    }
    
    @Override
    public final cn a(final cn cn) {
        super.a(cn);
        if (cn.a != MovementStatus.RUNNING) {
            return cn;
        }
        final BetterBlockPos playerFeet = this.a.playerFeet();
        final Rotation calcRotationFromVec3d = RotationUtils.calcRotationFromVec3d(this.a.playerHead(), VecUtils.getBlockPosCenter(this.b), this.a.playerRotations());
        Rotation rotation = null;
        final aow u;
        final boolean b;
        if (!(b = ((u = this.a.world().o((et)this.b).u()) == aox.j || u == aox.i))) {
            final cj cj = new cj(this.a);
            if (dn.a(cj, this.a.b, this.b.a, this.b.c, 0.0, cj.a(this.b.a, this.a.b - 2, this.b.c), new gj()) && !playerFeet.equals((Object)this.b)) {
                if (!aec.e(this.a.player().bv.b(dq.a)) || this.a.world().s.n()) {
                    cn.a = MovementStatus.UNREACHABLE;
                    return cn;
                }
                if (this.a.player().q - this.b.q() < this.a.playerController().getBlockReachDistance() && !this.a.player().z) {
                    this.a.player().bv.d = this.a.player().bv.b(dq.a);
                    rotation = new Rotation(calcRotationFromVec3d.getYaw(), 90.0f);
                    if (this.a.isLookingAt(this.b) || this.a.isLookingAt(this.b.down())) {
                        cn.a(Input.CLICK_RIGHT, true);
                    }
                }
            }
        }
        if (rotation != null) {
            cn.a(new co(rotation, true));
        }
        else {
            cn.a(new co(calcRotationFromVec3d, false));
        }
        if (playerFeet.equals((Object)this.b) && (this.a.player().q - playerFeet.q() < 0.094 || b)) {
            if (!b) {
                cn.a = MovementStatus.SUCCESS;
                return cn;
            }
            if (aec.e(this.a.player().bv.b(dq.b))) {
                this.a.player().bv.d = this.a.player().bv.b(dq.b);
                if (this.a.player().t >= 0.0) {
                    return cn.a(Input.CLICK_RIGHT, true);
                }
                return cn;
            }
            else if (this.a.player().t >= 0.0) {
                cn.a = MovementStatus.SUCCESS;
                return cn;
            }
        }
        final bhe blockPosCenter = VecUtils.getBlockPosCenter(this.b);
        if (Math.abs(this.a.player().p + this.a.player().s - blockPosCenter.b) > 0.1 || Math.abs(this.a.player().r + this.a.player().u - blockPosCenter.d) > 0.1) {
            if (!this.a.player().z && Math.abs(this.a.player().t) > 0.4) {
                cn.a(Input.SNEAK, true);
            }
            cn.a(Input.MOVE_FORWARD, true);
        }
        while (true) {
            for (int i = 0; i < 15; ++i) {
                final awt o;
                if ((o = this.a.world().o((et)this.a.playerFeet().down(i))).u() == aox.au) {
                    final fa value = (fa)o.c((axj)arq.a);
                    Object b2;
                    if ((b2 = Optional.ofNullable(value).map((Function<? super fa, ? extends fq>)fa::n).orElse(null)) == null) {
                        b2 = this.a.b((fq)this.b);
                    }
                    else if (Math.abs(((fq)b2).p() * (blockPosCenter.b - ((fq)b2).p() / 2.0 - this.a.player().p)) + Math.abs(((fq)b2).r() * (blockPosCenter.d - ((fq)b2).r() / 2.0 - this.a.player().r)) < 0.6) {
                        cn.a(Input.MOVE_FORWARD, true);
                    }
                    else if (!this.a.player().z) {
                        cn.a(Input.SNEAK, false);
                    }
                    if (rotation == null) {
                        cn.a(new co(RotationUtils.calcRotationFromVec3d(this.a.playerHead(), new bhe(blockPosCenter.b + 0.125 * ((fq)b2).p(), blockPosCenter.c, blockPosCenter.d + 0.125 * ((fq)b2).r()), this.a.playerRotations()), false));
                    }
                    return cn;
                }
            }
            final fa value = null;
            continue;
        }
    }
    
    public final boolean b(final cn cn) {
        return this.a.playerFeet().equals(this.a) || cn.a != MovementStatus.RUNNING;
    }
    
    private static BetterBlockPos[] a(final BetterBlockPos betterBlockPos, final BetterBlockPos betterBlockPos2) {
        final int n = betterBlockPos.p() - betterBlockPos2.p();
        final int n2 = betterBlockPos.r() - betterBlockPos2.r();
        final BetterBlockPos[] array = new BetterBlockPos[betterBlockPos.q() - betterBlockPos2.q() + 2];
        for (int i = 0; i < array.length; ++i) {
            array[i] = new BetterBlockPos(betterBlockPos.p() - n, betterBlockPos.q() + 1 - i, betterBlockPos.r() - n2);
        }
        return array;
    }
    
    public final boolean a(final cn cn) {
        if (cn.a == MovementStatus.WAITING) {
            return true;
        }
        for (int n = 0; n < 4 && n < this.a.length; ++n) {
            if (!cl.a(this.a, this.a[n])) {
                return super.a(cn);
            }
        }
        return true;
    }
    
    static {
        a = new aip(air.aA);
        b = new aip(air.az);
    }
}
