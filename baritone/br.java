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

public final class br extends Command
{
    public br(final IBaritone baritone) {
        super(baritone, new String[] { "schematica" });
    }
    
    @Override
    public final void execute(final String s, final IArgConsumer argConsumer) {
        argConsumer.requireMax(0);
        this.baritone.getBuilderProcess().buildOpenSchematic();
    }
    
    @Override
    public final Stream<String> tabComplete(final String s, final IArgConsumer argConsumer) {
        return Stream.empty();
    }
    
    @Override
    public final String getShortDesc() {
        return "Builds the loaded schematic";
    }
    
    @Override
    public final List<String> getLongDesc() {
        return Arrays.asList("Builds the schematica currently open in Schematica.", "", "Usage:", "> schematica");
    }
}
