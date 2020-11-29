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

public final class ao extends Command
{
    public ao(final IBaritone baritone) {
        super(baritone, new String[] { "click" });
    }
    
    @Override
    public final void execute(final String s, final IArgConsumer argConsumer) {
        argConsumer.requireMax(0);
        this.baritone.openClick();
        this.logDirect("aight dude");
    }
    
    @Override
    public final Stream<String> tabComplete(final String s, final IArgConsumer argConsumer) {
        return Stream.empty();
    }
    
    @Override
    public final String getShortDesc() {
        return "Open click";
    }
    
    @Override
    public final List<String> getLongDesc() {
        return Arrays.asList("Opens click dude", "", "Usage:", "> click");
    }
}
