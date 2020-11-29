// 
// Decompiled by Procyon v0.5.36
// 

package baritone;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import baritone.api.pathing.calc.IPathingControlManager;
import java.util.function.Function;
import baritone.api.process.PathingCommand;
import baritone.api.command.exception.CommandInvalidStateException;
import baritone.api.process.IBaritoneProcess;
import baritone.api.command.argument.IArgConsumer;
import baritone.api.IBaritone;
import baritone.api.command.Command;

public final class bm extends Command
{
    public bm(final IBaritone baritone) {
        super(baritone, new String[] { "proc" });
    }
    
    @Override
    public final void execute(final String s, final IArgConsumer argConsumer) {
        argConsumer.requireMax(0);
        final IPathingControlManager pathingControlManager;
        final IBaritoneProcess baritoneProcess;
        if ((baritoneProcess = (pathingControlManager = this.baritone.getPathingControlManager()).mostRecentInControl().orElse(null)) == null) {
            throw new CommandInvalidStateException("No process in control");
        }
        this.logDirect(String.format("Class: %s\nPriority: %f\nTemporary: %b\nDisplay name: %s\nLast command: %s", baritoneProcess.getClass().getTypeName(), baritoneProcess.priority(), baritoneProcess.isTemporary(), baritoneProcess.displayName(), pathingControlManager.mostRecentCommand().map((Function<? super PathingCommand, ? extends String>)PathingCommand::toString).orElse("None")));
    }
    
    @Override
    public final Stream<String> tabComplete(final String s, final IArgConsumer argConsumer) {
        return Stream.empty();
    }
    
    @Override
    public final String getShortDesc() {
        return "View process state information";
    }
    
    @Override
    public final List<String> getLongDesc() {
        return Arrays.asList("The proc command provides miscellaneous information about the process currently controlling Baritone.", "", "You are not expected to understand this if you aren't familiar with how Baritone works.", "", "Usage:", "> proc - View process information, if present");
    }
}
