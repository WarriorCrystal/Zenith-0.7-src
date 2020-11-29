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

final class av extends Command
{
    private /* synthetic */ boolean[] a;
    private /* synthetic */ ar a;
    
    av(final ar a, final IBaritone baritone, final String[] array, final boolean... a2) {
        this.a = a;
        this.a = a2;
        super(baritone, array);
    }
    
    @Override
    public final void execute(final String s, final IArgConsumer argConsumer) {
        argConsumer.requireMax(0);
        this.logDirect(String.format("Baritone is %spaused", this.a[0] ? "" : "not "));
    }
    
    @Override
    public final Stream<String> tabComplete(final String s, final IArgConsumer argConsumer) {
        return Stream.empty();
    }
    
    @Override
    public final String getShortDesc() {
        return "Tells you if Baritone is paused";
    }
    
    @Override
    public final List<String> getLongDesc() {
        return Arrays.asList("The paused command tells you if Baritone is currently paused by use of the pause command.", "", "Usage:", "> paused");
    }
}
