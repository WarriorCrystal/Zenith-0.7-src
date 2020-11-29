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

final class aw extends Command
{
    private /* synthetic */ boolean[] a;
    private /* synthetic */ ar a;
    
    aw(final ar a, final IBaritone baritone, final String[] array, final boolean... a2) {
        this.a = a;
        this.a = a2;
        super(baritone, array);
    }
    
    @Override
    public final void execute(final String s, final IArgConsumer argConsumer) {
        argConsumer.requireMax(0);
        if (this.a[0]) {
            this.a[0] = false;
        }
        this.baritone.getPathingBehavior().cancelEverything();
        this.logDirect("ok canceled");
    }
    
    @Override
    public final Stream<String> tabComplete(final String s, final IArgConsumer argConsumer) {
        return Stream.empty();
    }
    
    @Override
    public final String getShortDesc() {
        return "Cancel what Baritone is currently doing";
    }
    
    @Override
    public final List<String> getLongDesc() {
        return Arrays.asList("The cancel command tells Baritone to stop whatever it's currently doing.", "", "Usage:", "> cancel");
    }
}
