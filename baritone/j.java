// 
// Decompiled by Procyon v0.5.36
// 

package baritone;

import baritone.api.pathing.path.IPathExecutor;
import baritone.api.event.events.RenderEvent;
import baritone.api.pathing.goals.GoalXZ;
import baritone.api.utils.interfaces.IGoalRenderPos;
import baritone.api.utils.PathCalculationResult;
import baritone.api.utils.PathCalculationResult$Type;
import java.util.Comparator;
import baritone.api.IBaritone;
import baritone.api.process.PathingCommand;
import baritone.api.event.events.PlayerUpdateEvent;
import baritone.api.event.events.SprintStateEvent;
import java.util.Optional;
import baritone.api.pathing.calc.IPath;
import baritone.api.process.PathingCommandType;
import java.util.Objects;
import baritone.api.event.events.TickEvent$Type;
import baritone.api.event.events.TickEvent;
import java.util.Iterator;
import java.util.Collection;
import java.util.ArrayList;
import baritone.api.event.events.PathEvent;
import java.util.concurrent.LinkedBlockingQueue;
import baritone.api.utils.BetterBlockPos;
import baritone.api.pathing.goals.Goal;
import baritone.api.utils.Helper;
import baritone.api.behavior.IPathingBehavior;

public final class j extends b implements IPathingBehavior, Helper
{
    public dw a;
    public dw b;
    private Goal a;
    public cj a;
    private boolean b;
    private boolean c;
    private boolean d;
    private boolean e;
    private boolean f;
    public boolean a;
    private volatile ch a;
    private final Object a;
    private final Object b;
    private boolean g;
    private BetterBlockPos a;
    private final LinkedBlockingQueue<PathEvent> a;
    
    public j(final a a) {
        super(a);
        this.a = new Object();
        this.b = new Object();
        this.a = new LinkedBlockingQueue<PathEvent>();
    }
    
    private void a(final PathEvent e) {
        this.a.add(e);
    }
    
    private void b() {
        final ArrayList<PathEvent> c = new ArrayList<PathEvent>();
        this.a.drainTo(c);
        this.a = c.contains(PathEvent.CALC_FAILED);
        final Iterator<PathEvent> iterator = c.iterator();
        while (iterator.hasNext()) {
            this.a.getGameEventHandler().onPathEvent(iterator.next());
        }
    }
    
    @Override
    public final void onTick(final TickEvent tickEvent) {
        this.b();
        if (tickEvent.getType() == TickEvent$Type.OUT) {
            this.c();
            this.a.a.a();
            return;
        }
        this.a = this.a();
        final fv a;
        final fv fv = a = this.a.a;
        fv.a = fv.b;
        a.b = null;
        final j a2 = a.a.a;
        final fv fv2 = a;
        fv2.a = fv2.a();
        if (a.a == null) {
            a2.b();
            a2.a = null;
        }
        else {
            if (!Objects.equals(a.b, a.a) && a.a.commandType != PathingCommandType.REQUEST_PAUSE && a.a != null && !a.a.isTemporary()) {
                a2.b();
            }
            switch (fx.a[a.a.commandType.ordinal()]) {
                case 1: {
                    a2.c = true;
                    break;
                }
                case 2: {
                    a2.a = a.a.goal;
                    a2.b();
                    break;
                }
                case 3: {
                    if (!a2.isPathing() && !a2.getInProgress().isPresent()) {
                        a2.a(a.a);
                        break;
                    }
                    break;
                }
                case 4: {
                    if (!a2.isPathing() && !a2.getInProgress().isPresent()) {
                        a2.a(a.a);
                        break;
                    }
                    break;
                }
                case 5: {
                    if (a.a.goal != null) {
                        a.a.a.a(a.a);
                        break;
                    }
                    break;
                }
                default: {
                    throw new IllegalStateException();
                }
            }
        }
        this.e = false;
        Label_1338: {
            if (this.c && this.b) {
                this.c = false;
                if (this.d) {
                    this.a.a.clearAllKeys();
                    this.a.a.a.a();
                }
                this.d = false;
                this.e = true;
            }
            else {
                this.d = true;
                if (this.f) {
                    this.f = false;
                    this.a.a.clearAllKeys();
                }
                synchronized (this.b) {
                    synchronized (this.a) {
                        if (this.a != null) {
                            final BetterBlockPos a3 = this.a.a();
                            final Optional<IPath> bestPathSoFar = this.a.bestPathSoFar();
                            if ((this.a == null || !this.a.getPath().getDest().equals(a3)) && !a3.equals(this.a.playerFeet()) && !a3.equals(this.a) && (!bestPathSoFar.isPresent() || (!bestPathSoFar.get().positions().contains(this.a.playerFeet()) && !bestPathSoFar.get().positions().contains(this.a)))) {
                                this.a.a();
                            }
                        }
                    }
                    final j j;
                    if (j.a != null) {
                        final j i = j;
                        i.b = i.a.a();
                        if (j.a.a || j.a.b()) {
                            j.a = null;
                            if (j.a == null || j.a.isInGoal(j.a.playerFeet())) {
                                j.logDebug("All done. At " + j.a);
                                j.a(PathEvent.AT_GOAL);
                                j.b = null;
                                if (baritone.a.a().disconnectOnArrival.value) {
                                    j.a.world().O();
                                }
                            }
                            else {
                                if (j.b != null && !j.b.getPath().positions().contains(j.a.playerFeet()) && !j.b.getPath().positions().contains(j.a)) {
                                    j.logDebug("Discarding next path as it does not contain current position");
                                    j.a(PathEvent.DISCARD_NEXT);
                                    j.b = null;
                                }
                                if (j.b != null) {
                                    j.logDebug("Continuing on to planned next path");
                                    j.a(PathEvent.CONTINUING_ONTO_PLANNED_NEXT);
                                    final j k = j;
                                    k.a = k.b;
                                    j.b = null;
                                    j.a.a();
                                }
                                else {
                                    synchronized (j.a) {
                                        if (j.a != null) {
                                            j.a(PathEvent.PATH_FINISHED_NEXT_STILL_CALCULATING);
                                        }
                                        else {
                                            j.a(PathEvent.CALC_STARTED);
                                            final j l = j;
                                            l.a(l.a, true, j.a);
                                        }
                                    }
                                }
                            }
                        }
                        else {
                            if (j.b && j.b != null) {
                                final dw b;
                                boolean b2;
                                if (!(b = j.b).a.player().z && !(b.a.world().o((et)b.a.playerFeet()).u() instanceof aru)) {
                                    b2 = false;
                                }
                                else if (b.a.player().t < -0.1) {
                                    b2 = false;
                                }
                                else {
                                    final int index;
                                    if ((index = b.a.positions().indexOf(b.a.playerFeet())) == -1) {
                                        b2 = false;
                                    }
                                    else {
                                        b.a = index;
                                        b.a();
                                        b2 = true;
                                    }
                                }
                                if (b2) {
                                    j.logDebug("Splicing into planned next path early...");
                                    j.a(PathEvent.SPLICING_ONTO_NEXT_EARLY);
                                    final j m = j;
                                    m.a = m.b;
                                    j.b = null;
                                    j.a.a();
                                    break Label_1338;
                                }
                            }
                            if (baritone.a.a().splicePath.value) {
                                final j j2 = j;
                                j2.a = j2.a.a(j.b);
                            }
                            if (j.b != null && j.a.getPath().getDest().equals(j.b.getPath().getDest())) {
                                j.b = null;
                            }
                            synchronized (j.a) {
                                if (j.a == null) {
                                    if (j.b == null) {
                                        if (j.a != null && !j.a.isInGoal(j.a.getPath().getDest())) {
                                            if (j.ticksRemainingInSegment(false).get() < baritone.a.a().planningTickLookahead.value) {
                                                j.logDebug("Path almost over. Planning ahead...");
                                                j.a(PathEvent.NEXT_SEGMENT_CALC_STARTED);
                                                final j j3 = j;
                                                j3.a(j3.a.getPath().getDest(), false, j.a);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        this.b();
    }
    
    @Override
    public final void onPlayerSprintState(final SprintStateEvent sprintStateEvent) {
        if (this.isPathing()) {
            sprintStateEvent.setState(this.a.b);
        }
    }
    
    @Override
    public final void onPlayerUpdate(final PlayerUpdateEvent playerUpdateEvent) {
        if (this.a != null) {
            switch (k.a[playerUpdateEvent.getState().ordinal()]) {
                case 1: {
                    this.g = j.mc.t.R;
                    j.mc.t.R = false;
                }
                case 2: {
                    j.mc.t.R = this.g;
                    break;
                }
            }
        }
    }
    
    public final boolean a(final PathingCommand pathingCommand) {
        this.a = pathingCommand.goal;
        if (pathingCommand instanceof fu) {
            this.a = ((fu)pathingCommand).a;
        }
        else {
            this.a = new cj(this.a, true);
        }
        if (this.a == null) {
            return false;
        }
        if (this.a.isInGoal(this.a.playerFeet()) || this.a.isInGoal(this.a)) {
            return false;
        }
        synchronized (this.b) {
            if (this.a != null) {
                return false;
            }
            synchronized (this.a) {
                if (this.a != null) {
                    return false;
                }
                this.a(PathEvent.CALC_STARTED);
                this.a(this.a, true, this.a);
                return true;
            }
        }
    }
    
    @Override
    public final Goal getGoal() {
        return this.a;
    }
    
    @Override
    public final boolean isPathing() {
        return this.hasPath() && !this.e;
    }
    
    @Override
    public final Optional<ch> getInProgress() {
        return Optional.ofNullable(this.a);
    }
    
    public final boolean a() {
        return this.a == null || this.b;
    }
    
    private boolean b() {
        if (this.a()) {
            this.c();
            return true;
        }
        return false;
    }
    
    @Override
    public final boolean cancelEverything() {
        final boolean a;
        if (a = this.a()) {
            this.c();
        }
        this.a.a.a();
        return a;
    }
    
    public final void a() {
        synchronized (this.b) {
            this.getInProgress().ifPresent(ch::a);
            if (!this.a()) {
                return;
            }
            this.a = null;
            this.b = null;
        }
        this.f = true;
    }
    
    private void c() {
        this.a(PathEvent.CANCELED);
        synchronized (this.b) {
            this.getInProgress().ifPresent(ch::a);
            if (this.a != null) {
                this.a = null;
                this.b = null;
                this.a.a.clearAllKeys();
                this.a.a.a.a();
            }
        }
    }
    
    @Override
    public final void forceCancel() {
        this.cancelEverything();
        this.c();
        synchronized (this.a) {
            this.a = null;
        }
    }
    
    public final BetterBlockPos a() {
        final BetterBlockPos playerFeet = this.a.playerFeet();
        if (!cl.b(this.a, playerFeet.down())) {
            if (this.a.player().z) {
                final double p3 = this.a.player().p;
                final double r = this.a.player().r;
                final ArrayList<BetterBlockPos> list = new ArrayList<BetterBlockPos>();
                for (int i = -1; i <= 1; ++i) {
                    for (int j = -1; j <= 1; ++j) {
                        list.add(new BetterBlockPos(playerFeet.a + i, playerFeet.b, playerFeet.c + j));
                    }
                }
                final double n;
                final double n2;
                list.sort(Comparator.comparingDouble(betterBlockPos -> (betterBlockPos.a + 0.5 - n) * (betterBlockPos.a + 0.5 - n) + (betterBlockPos.c + 0.5 - n2) * (betterBlockPos.c + 0.5 - n2)));
                for (int k = 0; k < 4; ++k) {
                    final BetterBlockPos betterBlockPos2;
                    final double abs = Math.abs((betterBlockPos2 = list.get(k)).a + 0.5 - p3);
                    final double abs2 = Math.abs(betterBlockPos2.c + 0.5 - r);
                    if ((abs <= 0.8 || abs2 <= 0.8) && cl.b(this.a, betterBlockPos2.down()) && cl.a(this.a, betterBlockPos2) && cl.a(this.a, betterBlockPos2.up())) {
                        return betterBlockPos2;
                    }
                }
            }
            else if (cl.b(this.a, playerFeet.down().down())) {
                return playerFeet.down();
            }
        }
        return playerFeet;
    }
    
    private void a(final et obj, final boolean b, final cj cj) {
        if (!Thread.holdsLock(this.a)) {
            throw new IllegalStateException("Must be called with synchronization on pathCalcLock");
        }
        if (this.a != null) {
            throw new IllegalStateException("Already doing it");
        }
        if (!cj.a) {
            throw new IllegalStateException("Improper context thread safety level");
        }
        final Goal a;
        if ((a = this.a) == null) {
            this.logDebug("no goal");
            return;
        }
        if (this.a == null) {
            baritone.a.a().primaryTimeoutMS.value;
            baritone.a.a().failureTimeoutMS.value;
        }
        else {
            baritone.a.a().planAheadPrimaryTimeoutMS.value;
            baritone.a.a().planAheadFailureTimeoutMS.value;
        }
        final ch a2;
        if (!Objects.equals((a2 = a(obj, a, (this.a == null) ? null : this.a.getPath(), cj)).getGoal(), a)) {
            this.logDebug("Simplifying " + a.getClass() + " to GoalXZ due to distance");
        }
        this.a = a2;
        final boolean b2;
        final Goal obj2;
        final ch ch;
        final long n;
        final long n2;
        final PathCalculationResult pathCalculationResult;
        final Optional<dw> optional;
        baritone.a.a().execute(() -> {
            if (b2) {
                this.logDebug("Starting to search for path from " + obj + " to " + obj2);
            }
            ch.calculate(n, n2);
            synchronized (this.b) {
                pathCalculationResult.getPath().map(path -> new dw(this, path));
                if (this.a == null) {
                    if (optional.isPresent()) {
                        if (optional.get().getPath().positions().contains(this.a)) {
                            this.a(PathEvent.CALC_FINISHED_NOW_EXECUTING);
                            this.a = optional.get();
                        }
                        else {
                            this.logDebug("Warning: discarding orphan path segment with incorrect start");
                        }
                    }
                    else if (pathCalculationResult.getType() != PathCalculationResult$Type.CANCELLATION && pathCalculationResult.getType() != PathCalculationResult$Type.EXCEPTION) {
                        this.a(PathEvent.CALC_FAILED);
                    }
                }
                else if (this.b == null) {
                    if (optional.isPresent()) {
                        if (optional.get().getPath().getSrc().equals(this.a.getPath().getDest())) {
                            this.a(PathEvent.NEXT_SEGMENT_CALC_FINISHED);
                            this.b = optional.get();
                        }
                        else {
                            this.logDebug("Warning: discarding orphan next segment with incorrect start");
                        }
                    }
                    else {
                        this.a(PathEvent.NEXT_CALC_FAILED);
                    }
                }
                else {
                    this.logDirect("Warning: PathingBehaivor illegal state! Discarding invalid path!");
                }
                if (b2 && this.a != null && this.a.getPath() != null) {
                    if (obj2.isInGoal(this.a.getPath().getDest())) {
                        this.logDebug("Finished finding a path from " + obj + " to " + obj2 + ". " + this.a.getPath().getNumNodesConsidered() + " nodes considered");
                    }
                    else {
                        this.logDebug("Found path segment from " + obj + " towards " + obj2 + ". " + this.a.getPath().getNumNodesConsidered() + " nodes considered");
                    }
                }
                synchronized (this.a) {
                    this.a = null;
                }
            }
        });
    }
    
    private static ch a(final et et, final Goal goal, final IPath path, final cj cj) {
        Goal goal2 = goal;
        if (a.a().simplifyUnloadedYCoord.value && goal instanceof IGoalRenderPos) {
            final et goalPos = ((IGoalRenderPos)goal).getGoalPos();
            if (!cj.a.a(goalPos.p(), goalPos.r())) {
                goal2 = new GoalXZ(goalPos.p(), goalPos.r());
            }
        }
        return new cg(et.p(), et.q(), et.r(), goal2, new gi(cj.a.getPlayerContext(), path, cj), cj);
    }
    
    @Override
    public final void onRenderPass(final RenderEvent renderEvent) {
        ft.a(renderEvent, this);
    }
}
