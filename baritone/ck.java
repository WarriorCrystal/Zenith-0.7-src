// 
// Decompiled by Procyon v0.5.36
// 

package baritone;

import java.util.ArrayList;
import baritone.api.utils.VecUtils;
import baritone.api.utils.Rotation;
import baritone.api.utils.RotationUtils;
import java.util.Optional;
import baritone.api.utils.input.Input;
import java.util.Objects;
import baritone.api.pathing.movement.MovementStatus;
import java.util.Set;
import java.util.List;
import baritone.api.utils.BetterBlockPos;
import baritone.api.utils.IPlayerContext;
import baritone.api.IBaritone;
import baritone.api.pathing.movement.IMovement;

public abstract class ck implements IMovement, cl
{
    public static final fa[] a;
    protected final IBaritone a;
    protected final IPlayerContext a;
    private cn a;
    protected final BetterBlockPos a;
    protected final BetterBlockPos b;
    protected final BetterBlockPos[] a;
    protected final BetterBlockPos c;
    public Double a;
    public List<et> a;
    private List<et> c;
    public List<et> b;
    private Set<BetterBlockPos> a;
    private Boolean a;
    
    protected ck(final IBaritone a, final BetterBlockPos a2, final BetterBlockPos b, final BetterBlockPos[] a3, final BetterBlockPos c) {
        final cn cn = new cn();
        final MovementStatus prepping = MovementStatus.PREPPING;
        final cn a4 = cn;
        cn.a = prepping;
        this.a = a4;
        this.a = null;
        this.c = null;
        this.b = null;
        this.a = null;
        this.a = a;
        this.a = a.getPlayerContext();
        this.a = a2;
        this.b = b;
        this.a = a3;
        this.c = c;
    }
    
    protected ck(final IBaritone baritone, final BetterBlockPos betterBlockPos, final BetterBlockPos betterBlockPos2, final BetterBlockPos[] array) {
        this(baritone, betterBlockPos, betterBlockPos2, array, null);
    }
    
    @Override
    public double getCost() {
        return this.a;
    }
    
    public abstract double a(final cj p0);
    
    protected abstract Set<BetterBlockPos> a();
    
    public final Set<BetterBlockPos> b() {
        if (this.a == null) {
            Objects.requireNonNull(this.a = this.a());
        }
        return this.a;
    }
    
    protected final boolean a() {
        return this.b().contains(this.a.playerFeet()) || this.b().contains(((j)this.a.getPathingBehavior()).a());
    }
    
    @Override
    public MovementStatus update() {
        this.a.player().bO.b = false;
        this.a = this.a(this.a);
        if (cl.e(this.a, this.a.playerFeet())) {
            this.a.a(Input.JUMP, true);
        }
        if (this.a.player().aD()) {
            this.a.getSelectedBlock().ifPresent(et -> cl.a(this.a, fn.a(this.a, et)));
            this.a.a(Input.CLICK_LEFT, true);
        }
        Optional.ofNullable(this.a.a.a).ifPresent(rotation -> this.a.getLookBehavior().updateTarget(rotation, this.a.a.a));
        this.a.getInputOverrideHandler().clearAllKeys();
        this.a.a.forEach((input, b) -> this.a.getInputOverrideHandler().setInputForceState(input, b));
        this.a.a.clear();
        if (this.a.a.isComplete()) {
            this.a.getInputOverrideHandler().clearAllKeys();
        }
        return this.a.a;
    }
    
    protected boolean a(final cn cn) {
        if (cn.a == MovementStatus.WAITING) {
            return true;
        }
        final BetterBlockPos[] a;
        final int length = (a = this.a).length;
        int i = 0;
        while (i < length) {
            final BetterBlockPos betterBlockPos = a[i];
            if (!this.a.world().a((Class)ack.class, new bhb(0.0, 0.0, 0.0, 1.0, 1.1, 1.0).a((et)betterBlockPos)).isEmpty() && baritone.a.a().pauseMiningForFallingBlocks.value) {
                return false;
            }
            if (!cl.a(this.a, betterBlockPos) && !(fn.a(this.a, (et)betterBlockPos) instanceof aru)) {
                cl.a(this.a, fn.a(this.a, (et)betterBlockPos));
                final Optional<Rotation> reachable;
                if ((reachable = RotationUtils.reachable(this.a.player(), betterBlockPos, this.a.playerController().getBlockReachDistance())).isPresent()) {
                    final Rotation rotation = reachable.get();
                    cn.a(new co(rotation, true));
                    if (this.a.isLookingAt(betterBlockPos) || this.a.playerRotations().isReallyCloseTo(rotation)) {
                        cn.a(Input.CLICK_LEFT, true);
                    }
                    return false;
                }
                cn.a(new co(RotationUtils.calcRotationFromVec3d(this.a.playerHead(), VecUtils.getBlockPosCenter(betterBlockPos), this.a.playerRotations()), true));
                cn.a(Input.CLICK_LEFT, true);
                return false;
            }
            else {
                ++i;
            }
        }
        return true;
    }
    
    @Override
    public boolean safeToCancel() {
        return this.b(this.a);
    }
    
    protected boolean b(final cn cn) {
        return true;
    }
    
    @Override
    public BetterBlockPos getSrc() {
        return this.a;
    }
    
    @Override
    public BetterBlockPos getDest() {
        return this.b;
    }
    
    @Override
    public void reset() {
        final cn cn = new cn();
        final MovementStatus prepping = MovementStatus.PREPPING;
        final cn a = cn;
        cn.a = prepping;
        this.a = a;
    }
    
    public cn a(cn cn) {
        if (!this.a(cn)) {
            final cn cn2 = cn;
            final MovementStatus prepping = MovementStatus.PREPPING;
            cn = cn2;
            cn2.a = prepping;
            return cn;
        }
        if (cn.a == MovementStatus.PREPPING) {
            cn.a = MovementStatus.WAITING;
        }
        if (cn.a == MovementStatus.WAITING) {
            cn.a = MovementStatus.RUNNING;
        }
        return cn;
    }
    
    @Override
    public et getDirection() {
        return this.getDest().b((fq)this.getSrc());
    }
    
    public final void a(final cj cj) {
        this.a = cj.a.a(this.b.a, this.b.c);
    }
    
    @Override
    public boolean calculatedWhileLoaded() {
        return this.a;
    }
    
    @Override
    public void resetBlockCache() {
        this.a = null;
        this.c = null;
        this.b = null;
    }
    
    public List<et> a(final fn fn) {
        if (this.a != null) {
            return this.a;
        }
        final ArrayList<BetterBlockPos> a = new ArrayList<BetterBlockPos>();
        BetterBlockPos[] a2;
        for (int length = (a2 = this.a).length, i = 0; i < length; ++i) {
            final BetterBlockPos betterBlockPos = a2[i];
            if (!cl.a(fn, betterBlockPos.a, betterBlockPos.b, betterBlockPos.c)) {
                a.add(betterBlockPos);
            }
        }
        return this.a = (List<et>)a;
    }
    
    public final List<et> b(final fn fn) {
        if (this.c != null) {
            return this.c;
        }
        final ArrayList<BetterBlockPos> c = new ArrayList<BetterBlockPos>();
        if (this.c != null && !cl.b(fn, this.c.a, this.c.b, this.c.c)) {
            c.add(this.c);
        }
        return this.c = (List<et>)c;
    }
    
    public List<et> c(final fn fn) {
        if (this.b == null) {
            this.b = new ArrayList<et>();
        }
        return this.b;
    }
    
    public final et[] a() {
        return this.a;
    }
    
    static {
        a = new fa[] { fa.c, fa.d, fa.f, fa.e, fa.a };
    }
}
