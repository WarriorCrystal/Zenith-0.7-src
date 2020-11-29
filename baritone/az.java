// 
// Decompiled by Procyon v0.5.36
// 

package baritone;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import baritone.api.command.argument.IArgConsumer;
import baritone.api.IBaritone;
import baritone.api.command.Command;

public final class az extends Command
{
    public az(final IBaritone baritone) {
        super(baritone, new String[] { "farm" });
    }
    
    @Override
    public final void execute(final String s, final IArgConsumer argConsumer) {
        argConsumer.requireMax(0);
        this.baritone.getFarmProcess().farm();
        this.logDirect("Farming");
    }
    
    @Override
    public final Stream<String> tabComplete(final String s, final IArgConsumer argConsumer) {
        return Stream.empty();
    }
    
    @Override
    public final String getShortDesc() {
        return "Farm nearby crops";
    }
    
    @Override
    public final List<String> getLongDesc() {
        return Arrays.asList("The farm command starts farming nearby plants. It harvests mature crops and plants new ones.", "", "Usage:", "> farm");
    }
}
