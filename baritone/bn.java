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

public final class bn extends Command
{
    public bn(final IBaritone baritone) {
        super(baritone, new String[] { "reloadall" });
    }
    
    @Override
    public final void execute(final String s, final IArgConsumer argConsumer) {
        argConsumer.requireMax(0);
        this.ctx.worldData().getCachedWorld().reloadAllFromDisk();
        this.logDirect("Reloaded");
    }
    
    @Override
    public final Stream<String> tabComplete(final String s, final IArgConsumer argConsumer) {
        return Stream.empty();
    }
    
    @Override
    public final String getShortDesc() {
        return "Reloads Baritone's cache for this world";
    }
    
    @Override
    public final List<String> getLongDesc() {
        return Arrays.asList("The reloadall command reloads Baritone's world cache.", "", "Usage:", "> reloadall");
    }
}
