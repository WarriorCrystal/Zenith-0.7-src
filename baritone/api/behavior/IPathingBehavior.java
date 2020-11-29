// 
// Decompiled by Procyon v0.5.36
// 

package baritone.api.behavior;

import baritone.api.pathing.calc.IPathFinder;
import java.util.function.Function;
import baritone.api.pathing.calc.IPath;
import baritone.api.pathing.goals.Goal;
import baritone.api.pathing.path.IPathExecutor;
import java.util.Optional;

public interface IPathingBehavior extends IBehavior
{
    default Optional<Double> ticksRemainingInSegment() {
        return this.ticksRemainingInSegment(true);
    }
    
    default Optional<Double> ticksRemainingInSegment(final boolean b) {
        final IPathExecutor current;
        if ((current = this.getCurrent()) == null) {
            return Optional.empty();
        }
        return Optional.of(current.getPath().ticksRemainingFrom(b ? current.getPosition() : (current.getPosition() + 1)));
    }
    
    Goal getGoal();
    
    boolean isPathing();
    
    default boolean hasPath() {
        return this.getCurrent() != null;
    }
    
    boolean cancelEverything();
    
    void forceCancel();
    
    default Optional<IPath> getPath() {
        return Optional.ofNullable(this.getCurrent()).map((Function<? super IPathExecutor, ? extends IPath>)IPathExecutor::getPath);
    }
    
    Optional<? extends IPathFinder> getInProgress();
    
    IPathExecutor getCurrent();
    
    IPathExecutor getNext();
}
