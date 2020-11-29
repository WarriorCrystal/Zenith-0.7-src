// 
// Decompiled by Procyon v0.5.36
// 

package baritone;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import baritone.api.behavior.IPathingBehavior;
import baritone.api.command.argument.IArgConsumer;
import baritone.api.IBaritone;
import baritone.api.command.Command;

public final class be extends Command
{
    public be(final IBaritone baritone) {
        super(baritone, new String[] { "forcecancel" });
    }
    
    @Override
    public final void execute(final String s, final IArgConsumer argConsumer) {
        argConsumer.requireMax(0);
        final IPathingBehavior pathingBehavior;
        (pathingBehavior = this.baritone.getPathingBehavior()).cancelEverything();
        pathingBehavior.forceCancel();
        this.logDirect("ok force canceled");
    }
    
    @Override
    public final Stream<String> tabComplete(final String s, final IArgConsumer argConsumer) {
        return Stream.empty();
    }
    
    @Override
    public final String getShortDesc() {
        return "Force cancel";
    }
    
    @Override
    public final List<String> getLongDesc() {
        return Arrays.asList("Like cancel, but more forceful.", "", "Usage:", "> forcecancel");
    }
}
