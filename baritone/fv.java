// 
// Decompiled by Procyon v0.5.36
// 

package baritone;

import baritone.api.process.PathingCommandType;
import java.util.Objects;
import java.util.Comparator;
import baritone.api.utils.BetterBlockPos;
import baritone.api.pathing.goals.Goal;
import java.util.Optional;
import java.util.Iterator;
import baritone.api.event.listener.IGameEventListener;
import java.util.ArrayList;
import baritone.api.process.PathingCommand;
import java.util.List;
import baritone.api.process.IBaritoneProcess;
import java.util.HashSet;
import baritone.api.pathing.calc.IPathingControlManager;

public final class fv implements IPathingControlManager
{
    public final a a;
    private final HashSet<IBaritoneProcess> a;
    private final List<IBaritoneProcess> a;
    public IBaritoneProcess a;
    public IBaritoneProcess b;
    public PathingCommand a;
    
    public fv(final a a) {
        this.a = a;
        this.a = new HashSet<IBaritoneProcess>();
        this.a = new ArrayList<IBaritoneProcess>();
        a.getGameEventHandler().registerEventListener(new fw(this));
    }
    
    @Override
    public final void registerProcess(final IBaritoneProcess e) {
        e.onLostControl();
        this.a.add(e);
    }
    
    public final void a() {
        this.a = null;
        this.b = null;
        ((List)(this.a = null)).clear();
        final Iterator<IBaritoneProcess> iterator = this.a.iterator();
        while (iterator.hasNext()) {
            final IBaritoneProcess baritoneProcess;
            (baritoneProcess = iterator.next()).onLostControl();
            if (baritoneProcess.isActive() && !baritoneProcess.isTemporary()) {
                throw new IllegalStateException(baritoneProcess.displayName());
            }
        }
    }
    
    @Override
    public final Optional<IBaritoneProcess> mostRecentInControl() {
        return Optional.ofNullable(this.b);
    }
    
    @Override
    public final Optional<PathingCommand> mostRecentCommand() {
        return Optional.ofNullable(this.a);
    }
    
    private boolean a(final Goal goal) {
        final dw a;
        if ((a = this.a.a.a) != null) {
            final Goal goal2 = a.getPath().getGoal();
            final BetterBlockPos dest = a.getPath().getDest();
            if (goal2.isInGoal(dest) && !goal.isInGoal(dest)) {
                return true;
            }
        }
        return false;
    }
    
    public final PathingCommand a() {
        final Iterator<IBaritoneProcess> iterator = this.a.iterator();
        while (iterator.hasNext()) {
            final IBaritoneProcess baritoneProcess;
            if ((baritoneProcess = iterator.next()).isActive()) {
                if (this.a.contains(baritoneProcess)) {
                    continue;
                }
                this.a.add(0, baritoneProcess);
            }
            else {
                this.a.remove(baritoneProcess);
            }
        }
        this.a.sort(Comparator.comparingDouble(IBaritoneProcess::priority).reversed());
        final Iterator<IBaritoneProcess> iterator2 = this.a.iterator();
        while (iterator2.hasNext()) {
            final IBaritoneProcess b;
            final IBaritoneProcess a = b = iterator2.next();
            final PathingCommand onTick;
            if ((onTick = a.onTick(Objects.equals(a, this.a) && this.a.a.a, this.a.a.a())) == null) {
                if (b.isActive()) {
                    throw new IllegalStateException(b.displayName() + " actively returned null PathingCommand");
                }
                continue;
            }
            else {
                if (onTick.commandType != PathingCommandType.DEFER) {
                    this.b = b;
                    if (!b.isTemporary()) {
                        iterator2.forEachRemaining(IBaritoneProcess::onLostControl);
                    }
                    return onTick;
                }
                continue;
            }
        }
        return null;
    }
}
