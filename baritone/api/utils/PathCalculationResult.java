// 
// Decompiled by Procyon v0.5.36
// 

package baritone.api.utils;

import java.util.Optional;
import java.util.Objects;
import baritone.api.pathing.calc.IPath;

public class PathCalculationResult
{
    private final IPath path;
    private final PathCalculationResult$Type type;
    
    public PathCalculationResult(final PathCalculationResult$Type pathCalculationResult$Type) {
        this(pathCalculationResult$Type, null);
    }
    
    public PathCalculationResult(final PathCalculationResult$Type pathCalculationResult$Type, final IPath path) {
        Objects.requireNonNull(pathCalculationResult$Type);
        this.path = path;
        this.type = pathCalculationResult$Type;
    }
    
    public final Optional<IPath> getPath() {
        return Optional.ofNullable(this.path);
    }
    
    public final PathCalculationResult$Type getType() {
        return this.type;
    }
}
