// 
// Decompiled by Procyon v0.5.36
// 

package baritone.api.pathing.calc;

import java.util.Optional;
import baritone.api.utils.PathCalculationResult;
import baritone.api.pathing.goals.Goal;

public interface IPathFinder
{
    Goal getGoal();
    
    PathCalculationResult calculate(final long p0, final long p1);
    
    boolean isFinished();
    
    Optional<IPath> pathToMostRecentNodeConsidered();
    
    Optional<IPath> bestPathSoFar();
}
