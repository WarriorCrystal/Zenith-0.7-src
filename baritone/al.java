// 
// Decompiled by Procyon v0.5.36
// 

package baritone;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import baritone.api.process.IGetToBlockProcess;
import baritone.api.command.exception.CommandInvalidStateException;
import baritone.api.command.argument.IArgConsumer;
import baritone.api.IBaritone;
import baritone.api.command.Command;

public final class al extends Command
{
    public al(final IBaritone baritone) {
        super(baritone, new String[] { "blacklist" });
    }
    
    @Override
    public final void execute(final String s, final IArgConsumer argConsumer) {
        argConsumer.requireMax(0);
        final IGetToBlockProcess getToBlockProcess;
        if (!(getToBlockProcess = this.baritone.getGetToBlockProcess()).isActive()) {
            throw new CommandInvalidStateException("GetToBlockProcess is not currently active");
        }
        if (getToBlockProcess.blacklistClosest()) {
            this.logDirect("Blacklisted closest instances");
            return;
        }
        throw new CommandInvalidStateException("No known locations, unable to blacklist");
    }
    
    @Override
    public final Stream<String> tabComplete(final String s, final IArgConsumer argConsumer) {
        return Stream.empty();
    }
    
    @Override
    public final String getShortDesc() {
        return "Blacklist closest block";
    }
    
    @Override
    public final List<String> getLongDesc() {
        return Arrays.asList("While going to a block this command blacklists the closest block so that Baritone won't attempt to get to it.", "", "Usage:", "> blacklist");
    }
}
