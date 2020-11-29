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

final class at extends Command
{
    private /* synthetic */ boolean[] a;
    private /* synthetic */ ar a;
    
    at(final ar a, final IBaritone baritone, final String[] array, final boolean... a2) {
        this.a = a;
        this.a = a2;
        super(baritone, array);
    }
    
    @Override
    public final void execute(final String s, final IArgConsumer argConsumer) {
        argConsumer.requireMax(0);
        if (this.a[0]) {
            throw new CommandInvalidStateException("Already paused");
        }
        this.a[0] = true;
        this.logDirect("Paused");
    }
    
    @Override
    public final Stream<String> tabComplete(final String s, final IArgConsumer argConsumer) {
        return Stream.empty();
    }
    
    @Override
    public final String getShortDesc() {
        return "Pauses Baritone until you use resume";
    }
    
    @Override
    public final List<String> getLongDesc() {
        return Arrays.asList("The pause command tells Baritone to temporarily stop whatever it's doing.", "", "This can be used to pause pathing, building, following, whatever. A single use of the resume command will start it right back up again!", "", "Usage:", "> pause");
    }
}
