// 
// Decompiled by Procyon v0.5.36
// 

package baritone;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import com.google.gson.JsonSyntaxException;
import java.nio.file.NoSuchFileException;
import baritone.api.command.exception.CommandInvalidStateException;
import baritone.api.command.exception.CommandInvalidTypeException;
import baritone.api.command.datatypes.RelativeFile;
import java.io.File;
import baritone.api.command.argument.IArgConsumer;
import baritone.api.IBaritone;
import baritone.api.command.Command;

public final class ay extends Command
{
    public ay(final IBaritone baritone) {
        super(baritone, new String[] { "explorefilter" });
    }
    
    @Override
    public final void execute(final String s, final IArgConsumer argConsumer) {
        argConsumer.requireMax(2);
        final File file = argConsumer.getDatatypePost(RelativeFile.INSTANCE, ay.mc.w.getAbsoluteFile().getParentFile());
        boolean b = false;
        if (argConsumer.hasAny()) {
            if (!argConsumer.getString().equalsIgnoreCase("invert")) {
                throw new CommandInvalidTypeException(argConsumer.consumed(), "either \"invert\" or nothing");
            }
            b = true;
        }
        try {
            this.baritone.getExploreProcess().applyJsonFilter(file.toPath().toAbsolutePath(), b);
        }
        catch (NoSuchFileException ex) {
            throw new CommandInvalidStateException("File not found");
        }
        catch (JsonSyntaxException ex2) {
            throw new CommandInvalidStateException("Invalid JSON syntax");
        }
        catch (Exception cause) {
            throw new IllegalStateException(cause);
        }
        this.logDirect(String.format("Explore filter applied. Inverted: %s", Boolean.toString(b)));
    }
    
    @Override
    public final Stream<String> tabComplete(final String s, final IArgConsumer argConsumer) {
        if (argConsumer.hasExactlyOne()) {
            return RelativeFile.tabComplete(argConsumer, RelativeFile.gameDir());
        }
        return Stream.empty();
    }
    
    @Override
    public final String getShortDesc() {
        return "Explore chunks from a json";
    }
    
    @Override
    public final List<String> getLongDesc() {
        return Arrays.asList("Apply an explore filter before using explore, which tells the explore process which chunks have been explored/not explored.", "", "The JSON file will follow this format: [{\"x\":0,\"z\":0},...]", "", "If 'invert' is specified, the chunks listed will be considered NOT explored, rather than explored.", "", "Usage:", "> explorefilter <path> [invert] - Load the JSON file referenced by the specified path. If invert is specified, it must be the literal word 'invert'.");
    }
}
