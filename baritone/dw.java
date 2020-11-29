// 
// Decompiled by Procyon v0.5.36
// 

package baritone;

import java.util.function.Supplier;
import java.util.Iterator;
import baritone.api.utils.VecUtils;
import java.util.Optional;
import java.util.List;
import baritone.api.utils.RotationUtils;
import baritone.api.utils.BetterBlockPos;
import baritone.api.IBaritone;
import baritone.api.utils.input.Input;
import baritone.api.pathing.movement.MovementStatus;
import java.util.Collection;
import baritone.api.pathing.movement.IMovement;
import baritone.api.utils.IPlayerContext;
import java.util.HashSet;
import baritone.api.pathing.calc.IPath;
import baritone.api.utils.Helper;
import baritone.api.pathing.path.IPathExecutor;

public final class dw implements IPathExecutor, Helper
{
    public final IPath a;
    public int a;
    private int b;
    private int c;
    private Double a;
    private Integer a;
    public boolean a;
    private boolean c;
    public HashSet<et> a;
    public HashSet<et> b;
    public HashSet<et> c;
    private j a;
    public IPlayerContext a;
    public boolean b;
    
    public dw(final j a, final IPath a2) {
        this.c = true;
        this.a = new HashSet<et>();
        this.b = new HashSet<et>();
        this.c = new HashSet<et>();
        this.a = a;
        this.a = a.a;
        this.a = a2;
        this.a = 0;
    }
    
    public final boolean a() {
        if (this.a == this.a.length() - 1) {
            ++this.a;
        }
        if (this.a >= this.a.length()) {
            return true;
        }
        final ck ck = this.a.movements().get(this.a);
        final BetterBlockPos playerFeet = this.a.playerFeet();
        if (!ck.b().contains(playerFeet)) {
            for (int a = 0; a < this.a && a < this.a.length(); ++a) {
                if (((ck)this.a.movements().get(a)).b().contains(playerFeet)) {
                    final int a2 = this.a;
                    this.a = a;
                    for (int i = this.a; i <= a2; ++i) {
                        this.a.movements().get(i).reset();
                    }
                    this.b();
                    this.a();
                    return false;
                }
            }
            for (int j = this.a + 3; j < this.a.length() - 1; ++j) {
                if (((ck)this.a.movements().get(j)).b().contains(playerFeet)) {
                    if (j - this.a > 2) {
                        this.logDebug("Skipping forward " + (j - this.a) + " steps, to " + j);
                    }
                    this.a = j - 1;
                    this.b();
                    this.a();
                    return false;
                }
            }
        }
        final rr<Double, et> a3 = this.a(this.a);
        if (this.a(a3, 2.0)) {
            ++this.b;
            System.out.println("FAR AWAY FROM PATH FOR " + this.b + " TICKS. Current distance: " + a3.a() + ". Threshold: 2.0");
            if (this.b > 200.0) {
                this.logDebug("Too far away from path for too long, cancelling path");
                this.c();
                return false;
            }
        }
        else {
            this.b = 0;
        }
        if (this.a(a3, 3.0)) {
            this.logDebug("too far from path");
            this.c();
            return false;
        }
        final fn fn = new fn(this.a);
        for (int k = this.a - 10; k < this.a + 10; ++k) {
            if (k >= 0 && k < this.a.movements().size()) {
                final ck ck2;
                final List<et> a4 = (ck2 = this.a.movements().get(k)).a(fn);
                final List<et> b = ck2.b(fn);
                final List<et> c = ck2.c(fn);
                ck2.resetBlockCache();
                if (!a4.equals(ck2.a(fn))) {
                    this.c = true;
                }
                if (!b.equals(ck2.b(fn))) {
                    this.c = true;
                }
                if (!c.equals(ck2.c(fn))) {
                    this.c = true;
                }
            }
        }
        if (this.c) {
            final HashSet<et> a5 = new HashSet<et>();
            final HashSet<et> b2 = new HashSet<et>();
            final HashSet<et> c2 = new HashSet<et>();
            for (int l = this.a; l < this.a.movements().size(); ++l) {
                final ck ck3 = this.a.movements().get(l);
                a5.addAll((Collection<?>)ck3.a(fn));
                b2.addAll((Collection<?>)ck3.b(fn));
                c2.addAll((Collection<?>)ck3.c(fn));
            }
            this.a = a5;
            this.b = b2;
            this.c = c2;
            this.c = false;
        }
        if (this.a < this.a.movements().size() - 1) {
            final IMovement movement = this.a.movements().get(this.a + 1);
            if (!this.a.a.a.a(movement.getDest().a, movement.getDest().c)) {
                this.logDebug("Pausing since destination is at edge of loaded chunks");
                this.a();
                return true;
            }
        }
        final boolean safeToCancel = ck.safeToCancel();
        if (this.a == null || this.a != this.a) {
            this.a = this.a;
            this.a = ck.getCost();
            for (int n = 1; n < baritone.a.a().costVerificationLookahead.value && this.a + n < this.a.length() - 1; ++n) {
                if (((ck)this.a.movements().get(this.a + n)).a(this.a.a) >= 1000000.0 && safeToCancel) {
                    this.logDebug("Something has changed in the world and a future movement has become impossible. Cancelling.");
                    this.c();
                    return true;
                }
            }
        }
        final ck ck4 = ck;
        final cj a6 = this.a.a;
        final ck ck5 = ck4;
        ck4.a = null;
        final ck ck6 = ck5;
        final cj cj = a6;
        final ck ck7 = ck6;
        if (ck6.a == null) {
            final ck ck8 = ck7;
            ck8.a = ck8.a(cj);
        }
        final double doubleValue;
        if ((doubleValue = ck7.a) >= 1000000.0 && safeToCancel) {
            this.logDebug("Something has changed in the world and this movement has become impossible. Cancelling.");
            this.c();
            return true;
        }
        if (!ck.calculatedWhileLoaded() && doubleValue - this.a > baritone.a.a().maxCostIncrease.value && safeToCancel) {
            this.logDebug("Original cost " + this.a + " current cost " + doubleValue + ". Cancelling.");
            this.c();
            return true;
        }
        final Optional<ch> inProgress;
        final Optional<IPath> bestPathSoFar;
        final List<BetterBlockPos> positions;
        if ((inProgress = this.a.getInProgress()).isPresent() && this.a.player().z && cl.b(this.a, this.a.playerFeet().down()) && cl.a(this.a, this.a.playerFeet()) && cl.a(this.a, this.a.playerFeet().up()) && this.a.movements().get(this.a).safeToCancel() && (bestPathSoFar = inProgress.get().bestPathSoFar()).isPresent() && (positions = bestPathSoFar.get().positions()).size() >= 3 && positions.subList(1, positions.size()).contains(this.a.playerFeet())) {
            this.logDebug("Pausing since current best path is a backtrack");
            this.a();
            return true;
        }
        final MovementStatus update;
        if ((update = ck.update()) == MovementStatus.UNREACHABLE || update == MovementStatus.FAILED) {
            this.logDebug("Movement returns status ".concat(String.valueOf(update)));
            this.c();
            return true;
        }
        if (update == MovementStatus.SUCCESS) {
            ++this.a;
            this.b();
            this.a();
            return true;
        }
        final boolean inputForcedDown = this.a.a.a.isInputForcedDown(Input.SPRINT);
        this.a.a.a.setInputForceState(Input.SPRINT, false);
        boolean b5 = false;
        Label_2666: {
            Label_2665: {
                if (new cj(this.a.a).d) {
                    final IMovement movement2;
                    final dm dm;
                    if ((movement2 = this.a.movements().get(this.a)) instanceof dt && this.a < this.a.length() - 3 && (dm = (dm)this.a.movements().get(this.a + 1)) instanceof dm && a(this.a, (dt)movement2, dm, this.a.movements().get(this.a + 2))) {
                        final IPlayerContext a7 = this.a;
                        final dt dt = (dt)movement2;
                        final IPlayerContext playerContext = a7;
                        boolean b4 = false;
                        Label_1890: {
                            if (Math.abs(dt.getDirection().p() * (dt.getSrc().c + 0.5 - playerContext.player().r)) + Math.abs(dt.getDirection().r() * (dt.getSrc().a + 0.5 - playerContext.player().p)) <= 0.1) {
                                final et b3 = dt.getSrc().b((fq)dt.getDirection()).b(2);
                                if (cl.a(playerContext, b3)) {
                                    b4 = true;
                                    break Label_1890;
                                }
                                if (Math.abs(dt.getDirection().p() * (b3.p() + 0.5 - playerContext.player().p)) + Math.abs(dt.getDirection().r() * (b3.r() + 0.5 - playerContext.player().r)) > 0.8) {
                                    b4 = true;
                                    break Label_1890;
                                }
                            }
                            b4 = false;
                        }
                        if (b4) {
                            this.logDebug("Skipping traverse to straight ascend");
                            ++this.a;
                            this.b();
                            this.a();
                            this.a.a.a.setInputForceState(Input.JUMP, true);
                            b5 = true;
                            break Label_2666;
                        }
                        this.logDebug("Too far to the side to safely sprint ascend");
                    }
                    if (inputForcedDown) {
                        b5 = true;
                        break Label_2666;
                    }
                    if (movement2 instanceof dn) {
                        if (((dn)movement2).b() && !((dn)movement2).c()) {
                            this.logDebug("Sprinting would be unsafe");
                            break Label_2665;
                        }
                        if (this.a < this.a.length() - 2) {
                            final IMovement movement3;
                            if ((movement3 = this.a.movements().get(this.a + 1)) instanceof dm && movement2.getDirection().a().equals((Object)movement3.getDirection().b())) {
                                ++this.a;
                                this.b();
                                this.a();
                                this.logDebug("Skipping descend to straight ascend");
                                b5 = true;
                                break Label_2666;
                            }
                            final IPlayerContext a8 = this.a;
                            final dn dn = (dn)movement2;
                            final IMovement movement4 = movement3;
                            final dn dn2 = dn;
                            final IPlayerContext playerContext2 = a8;
                            boolean b6 = false;
                            Label_2234: {
                                if (movement4 instanceof dn && movement4.getDirection().equals((Object)dn2.getDirection())) {
                                    b6 = true;
                                }
                                else {
                                    if (cl.b(playerContext2, dn2.getDest().a((fq)dn2.getDirection()))) {
                                        if (movement4 instanceof dt && movement4.getDirection().b().equals((Object)dn2.getDirection())) {
                                            b6 = true;
                                            break Label_2234;
                                        }
                                        if (movement4 instanceof do && baritone.a.a().allowOvershootDiagonalDescend.value) {
                                            b6 = true;
                                            break Label_2234;
                                        }
                                    }
                                    b6 = false;
                                }
                            }
                            if (b6) {
                                if (this.a.playerFeet().equals(movement2.getDest())) {
                                    ++this.a;
                                    this.b();
                                    this.a();
                                }
                                b5 = true;
                                break Label_2666;
                            }
                        }
                    }
                    if (movement2 instanceof dm && this.a != 0) {
                        final dt dt2;
                        if ((dt2 = (dt)this.a.movements().get(this.a - 1)) instanceof dn && dt2.getDirection().a().equals((Object)movement2.getDirection().b()) && this.a.player().q >= movement2.getSrc().up().q() - 0.07) {
                            this.a.a.a.setInputForceState(Input.JUMP, false);
                            b5 = true;
                            break Label_2666;
                        }
                        if (this.a < this.a.length() - 2 && dt2 instanceof dt && a(this.a, dt2, (dm)movement2, this.a.movements().get(this.a + 1))) {
                            b5 = true;
                            break Label_2666;
                        }
                    }
                    final rr<bhe, et> a9;
                    if (movement2 instanceof dq && (a9 = this.a((dq)movement2)) != null) {
                        final BetterBlockPos betterBlockPos = new BetterBlockPos((et)a9.b());
                        if (!this.a.positions().contains(betterBlockPos)) {
                            throw new IllegalStateException();
                        }
                        if (this.a.playerFeet().equals(betterBlockPos)) {
                            this.a = this.a.positions().indexOf(betterBlockPos);
                            this.b();
                            this.a();
                            b5 = true;
                            break Label_2666;
                        }
                        this.a();
                        this.a.a.a.updateTarget(RotationUtils.calcRotationFromVec3d(this.a.playerHead(), (bhe)a9.a(), this.a.playerRotations()), false);
                        this.a.a.a.setInputForceState(Input.MOVE_FORWARD, true);
                        b5 = true;
                        break Label_2666;
                    }
                }
            }
            b5 = false;
        }
        if (!(this.b = b5)) {
            this.a.player().f(false);
        }
        ++this.c;
        if (this.c > this.a + baritone.a.a().movementTimeoutTicks.value) {
            this.logDebug("This movement has taken too long (" + this.c + " ticks, expected " + this.a + "). Cancelling.");
            this.c();
            return true;
        }
        return safeToCancel;
    }
    
    private rr<Double, et> a(final IPath path) {
        double d = -1.0;
        Object o = null;
        final Iterator<IMovement> iterator = path.movements().iterator();
        while (iterator.hasNext()) {
            for (final BetterBlockPos betterBlockPos : ((ck)iterator.next()).b()) {
                final double entityDistanceToCenter;
                if ((entityDistanceToCenter = VecUtils.entityDistanceToCenter((vg)this.a.player(), betterBlockPos)) < d || d == -1.0) {
                    d = entityDistanceToCenter;
                    o = betterBlockPos;
                }
            }
        }
        return (rr<Double, et>)new rr((Object)d, o);
    }
    
    private boolean a(final rr<Double, et> rr, final double n) {
        return (double)rr.a() > n && (!(this.a.movements().get(this.a) instanceof dq) || VecUtils.entityFlatDistanceToCenter((vg)this.a.player(), this.a.positions().get(this.a + 1)) >= n);
    }
    
    private rr<bhe, et> a(final dq dq) {
        final et direction;
        if (((fq)(direction = dq.getDirection())).q() < -3) {
            return null;
        }
        if (!dq.a.isEmpty()) {
            return null;
        }
        fq fq = null;
        int n = 0;
        IMovement movement;
    Label_0221:
        for (fq = new fq(((fq)direction).p(), 0, ((fq)direction).r()), n = this.a + 1; n < this.a.length() - 1 && n < this.a + 3 && (movement = this.a.movements().get(n)) instanceof dt && fq.equals((Object)movement.getDirection()); ++n) {
            for (int i = movement.getDest().b; i <= dq.getSrc().b + 1; ++i) {
                if (!cl.a(this.a, new et(movement.getDest().a, i, movement.getDest().c))) {
                    break Label_0221;
                }
            }
            if (!cl.b(this.a, movement.getDest().down())) {
                break;
            }
        }
        if (--n == this.a) {
            return null;
        }
        final double n2 = n - this.a - 0.4;
        return (rr<bhe, et>)new rr((Object)new bhe(fq.p() * n2 + dq.getDest().a + 0.5, (double)dq.getDest().b, fq.r() * n2 + dq.getDest().c + 0.5), (Object)dq.getDest().a(fq.p() * (n - this.a), 0, fq.r() * (n - this.a)));
    }
    
    private static boolean a(final IPlayerContext playerContext, final dt dt, final dm dm, final IMovement movement) {
        if (!a.a().sprintAscends.value) {
            return false;
        }
        if (!dt.getDirection().equals((Object)dm.getDirection().b())) {
            return false;
        }
        if (movement.getDirection().p() != dm.getDirection().p() || movement.getDirection().r() != dm.getDirection().r()) {
            return false;
        }
        if (!cl.b(playerContext, dt.getDest().down())) {
            return false;
        }
        if (!cl.b(playerContext, dm.getDest().down())) {
            return false;
        }
        if (!dm.a.isEmpty()) {
            return false;
        }
        for (int i = 0; i < 2; ++i) {
            for (int j = 0; j < 3; ++j) {
                et et = dt.getSrc().up(j);
                if (i == 1) {
                    et = et.a((fq)dt.getDirection());
                }
                if (!cl.a(playerContext, et)) {
                    return false;
                }
            }
        }
        return !cl.a(playerContext.world().o((et)dt.getSrc().up(3)).u()) && !cl.a(playerContext.world().o((et)dm.getDest().up(2)).u());
    }
    
    private void b() {
        this.a();
        this.c = 0;
    }
    
    public final void a() {
        this.a.a.a.clearAllKeys();
    }
    
    private void c() {
        this.a();
        this.a.a.a.a.a();
        this.a = this.a.length() + 3;
        this.a = true;
    }
    
    @Override
    public final int getPosition() {
        return this.a;
    }
    
    public final dw a(final dw dw) {
        if (dw == null) {
            return this.a();
        }
        dw dw2 = null;
        final dw dw3;
        return dx.a(this.a, dw.a).map(dx -> {
            if (!dx.getDest().equals(dw2.getPath().getDest())) {
                throw new IllegalStateException();
            }
            else {
                dw2 = new dw(this.a, dx);
                dw3.a = this.a;
                dw2.a = this.a;
                dw2.a = this.a;
                dw2.c = this.c;
                return dw2;
            }
        }).orElseGet(this::a);
    }
    
    private dw a() {
        if (this.a <= baritone.a.a().maxPathHistoryLength.value) {
            return this;
        }
        final int intValue;
        final int n = intValue = baritone.a.a().pathHistoryCutoffAmount.value;
        final IPath a = this.a;
        final dv dv;
        if (!(dv = new dv(a, intValue, a.length() - 1)).getDest().equals(this.a.getDest())) {
            throw new IllegalStateException();
        }
        this.logDebug("Discarding earliest segment movements, length cut from " + this.a.length() + " to " + dv.length());
        final dw dw;
        (dw = new dw(this.a, dv)).a = this.a - n;
        dw.a = this.a;
        if (this.a != null) {
            dw.a = this.a - n;
        }
        dw.c = this.c;
        return dw;
    }
    
    @Override
    public final IPath getPath() {
        return this.a;
    }
    
    public final boolean b() {
        return this.a >= this.a.length();
    }
}
