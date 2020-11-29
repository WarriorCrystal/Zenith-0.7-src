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

final class au extends Command
{
    private /* synthetic */ boolean[] a;
    private /* synthetic */ ar a;
    
    au(final ar a, final IBaritone baritone, final String[] array, final boolean... a2) {
        this.a = a;
        this.a = a2;
        super(baritone, array);
    }
    
    @Override
    public final void execute(final String s, final IArgConsumer argConsumer) {
        argConsumer.requireMax(0);
        this.baritone.getBuilderProcess().resume();
        if (!this.a[0]) {
            throw new CommandInvalidStateException("Not paused");
        }
        this.a[0] = false;
        this.logDirect("Resumed");
    }
    
    @Override
    public final Stream<String> tabComplete(final String s, final IArgConsumer argConsumer) {
        return Stream.empty();
    }
    
    @Override
    public final String getShortDesc() {
        return "Resumes Baritone after a pause";
    }
    
    @Override
    public final List<String> getLongDesc() {
        return Arrays.asList("The resume command tells Baritone to resume whatever it was doing when you last used pause.", "", "Usage:", "> resume");
    }
}
