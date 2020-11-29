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

public final class bq extends Command
{
    public bq(final IBaritone baritone) {
        super(baritone, new String[] { "saveall" });
    }
    
    @Override
    public final void execute(final String s, final IArgConsumer argConsumer) {
        argConsumer.requireMax(0);
        this.ctx.worldData().getCachedWorld().save();
        this.logDirect("Saved");
    }
    
    @Override
    public final Stream<String> tabComplete(final String s, final IArgConsumer argConsumer) {
        return Stream.empty();
    }
    
    @Override
    public final String getShortDesc() {
        return "Saves Baritone's cache for this world";
    }
    
    @Override
    public final List<String> getLongDesc() {
        return Arrays.asList("The saveall command saves Baritone's world cache.", "", "Usage:", "> saveall");
    }
}
