// 
// Decompiled by Procyon v0.5.36
// 

package baritone;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import baritone.api.command.exception.CommandInvalidStateException;
import baritone.api.command.argument.IArgConsumer;
import baritone.api.IBaritone;
import baritone.api.command.Command;

public final class ca extends Command
{
    public ca(final IBaritone baritone) {
        super(baritone, new String[] { "version" });
    }
    
    @Override
    public final void execute(String implementationVersion, final IArgConsumer argConsumer) {
        argConsumer.requireMax(0);
        if ((implementationVersion = this.getClass().getPackage().getImplementationVersion()) == null) {
            throw new CommandInvalidStateException("Null version (this is normal in a dev environment)");
        }
        this.logDirect(String.format("You are running Baritone v%s", implementationVersion));
    }
    
    @Override
    public final Stream<String> tabComplete(final String s, final IArgConsumer argConsumer) {
        return Stream.empty();
    }
    
    @Override
    public final String getShortDesc() {
        return "View the Baritone version";
    }
    
    @Override
    public final List<String> getLongDesc() {
        return Arrays.asList("The version command prints the version of Baritone you're currently running.", "", "Usage:", "> version - View version information, if present");
    }
}
