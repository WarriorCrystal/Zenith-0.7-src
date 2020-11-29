// 
// Decompiled by Procyon v0.5.36
// 

package baritone;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import baritone.api.command.exception.CommandInvalidStateException;
import baritone.api.command.datatypes.RelativeBlockPos;
import baritone.api.utils.BetterBlockPos;
import org.apache.commons.io.FilenameUtils;
import baritone.api.command.datatypes.RelativeFile;
import baritone.api.command.argument.IArgConsumer;
import baritone.api.IBaritone;
import java.io.File;
import baritone.api.command.Command;

public final class am extends Command
{
    private static final File a;
    
    public am(final IBaritone baritone) {
        super(baritone, new String[] { "build" });
    }
    
    @Override
    public final void execute(final String s, final IArgConsumer argConsumer) {
        File absoluteFile;
        if (FilenameUtils.getExtension((absoluteFile = argConsumer.getDatatypePost(RelativeFile.INSTANCE, am.a).getAbsoluteFile()).getAbsolutePath()).isEmpty()) {
            absoluteFile = new File(absoluteFile.getAbsolutePath() + "." + baritone.a.a().schematicFallbackExtension);
        }
        final BetterBlockPos playerFeet = this.ctx.playerFeet();
        BetterBlockPos betterBlockPos;
        if (argConsumer.hasAny()) {
            argConsumer.requireMax(3);
            betterBlockPos = argConsumer.getDatatypePost(RelativeBlockPos.INSTANCE, playerFeet);
        }
        else {
            argConsumer.requireMax(0);
            betterBlockPos = playerFeet;
        }
        if (!this.baritone.getBuilderProcess().build(absoluteFile.getName(), absoluteFile, (fq)betterBlockPos)) {
            throw new CommandInvalidStateException("Couldn't load the schematic. Make sure to use the FULL file name, including the extension (e.g. blah.schematic).");
        }
        this.logDirect(String.format("Successfully loaded schematic for building\nOrigin: %s", betterBlockPos));
    }
    
    @Override
    public final Stream<String> tabComplete(final String s, final IArgConsumer argConsumer) {
        if (argConsumer.hasExactlyOne()) {
            return RelativeFile.tabComplete(argConsumer, am.a);
        }
        if (argConsumer.has(2)) {
            argConsumer.get();
            return argConsumer.tabCompleteDatatype(RelativeBlockPos.INSTANCE);
        }
        return Stream.empty();
    }
    
    @Override
    public final String getShortDesc() {
        return "Build a schematic";
    }
    
    @Override
    public final List<String> getLongDesc() {
        return Arrays.asList("Build a schematic from a file.", "", "Usage:", "> build <filename> - Loads and builds '<filename>.schematic'", "> build <filename> <x> <y> <z> - Custom position");
    }
    
    static {
        a = new File(bib.z().w, "schematics");
    }
}
