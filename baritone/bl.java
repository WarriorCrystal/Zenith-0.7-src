// 
// Decompiled by Procyon v0.5.36
// 

package baritone;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import baritone.api.process.ICustomGoalProcess;
import baritone.api.command.argument.IArgConsumer;
import baritone.api.IBaritone;
import baritone.api.command.Command;

public final class bl extends Command
{
    public bl(final IBaritone baritone) {
        super(baritone, new String[] { "path" });
    }
    
    @Override
    public final void execute(final String s, final IArgConsumer argConsumer) {
        final ICustomGoalProcess customGoalProcess = this.baritone.getCustomGoalProcess();
        argConsumer.requireMax(0);
        w.a.repack(this.ctx);
        customGoalProcess.path();
        this.logDirect("Now pathing");
    }
    
    @Override
    public final Stream<String> tabComplete(final String s, final IArgConsumer argConsumer) {
        return Stream.empty();
    }
    
    @Override
    public final String getShortDesc() {
        return "Start heading towards the goal";
    }
    
    @Override
    public final List<String> getLongDesc() {
        return Arrays.asList("The path command tells Baritone to head towards the current goal.", "", "Usage:", "> path - Start the pathing.");
    }
}
