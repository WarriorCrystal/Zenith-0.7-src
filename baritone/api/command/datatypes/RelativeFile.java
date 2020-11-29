// 
// Decompiled by Procyon v0.5.36
// 

package baritone.api.command.datatypes;

import baritone.api.utils.Helper;
import java.util.Locale;
import java.util.Objects;
import baritone.api.command.argument.IArgConsumer;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.stream.Stream;
import java.nio.file.Path;
import java.nio.file.InvalidPathException;
import java.nio.file.FileSystems;
import java.io.File;

public enum RelativeFile implements IDatatypePost<File, File>
{
    INSTANCE;
    
    @Override
    public final File apply(final IDatatypeContext datatypeContext, File file) {
        if (file == null) {
            file = new File("./");
        }
        Path path;
        try {
            path = FileSystems.getDefault().getPath(datatypeContext.getConsumer().getString(), new String[0]);
        }
        catch (InvalidPathException ex) {
            throw new IllegalArgumentException("invalid path");
        }
        return getCanonicalFileUnchecked(file.toPath().resolve(path).toFile());
    }
    
    @Override
    public final Stream<String> tabComplete(final IDatatypeContext datatypeContext) {
        return Stream.empty();
    }
    
    private static File getCanonicalFileUnchecked(final File file) {
        try {
            return file.getCanonicalFile();
        }
        catch (IOException cause) {
            throw new UncheckedIOException(cause);
        }
    }
    
    public static Stream<String> tabComplete(final IArgConsumer argConsumer, File canonicalFileUnchecked) {
        canonicalFileUnchecked = getCanonicalFileUnchecked(canonicalFileUnchecked);
        final String string = argConsumer.getString();
        final Path path2;
        final Path path = (path2 = FileSystems.getDefault().getPath(string, new String[0])).isAbsolute() ? path2.getRoot() : canonicalFileUnchecked.toPath();
        final boolean b = !string.isEmpty() && !string.endsWith(File.separator);
        canonicalFileUnchecked = (path2.isAbsolute() ? path2.toFile() : new File(canonicalFileUnchecked, string));
        final Path path3;
        final Path path4;
        return Stream.of((Object[])Objects.requireNonNull((T[])getCanonicalFileUnchecked(b ? canonicalFileUnchecked.getParentFile() : canonicalFileUnchecked).listFiles())).map(file -> (path3.isAbsolute() ? file : path4.relativize(file.toPath()).toString()) + (file.isDirectory() ? File.separator : "")).filter(s2 -> s2.toLowerCase(Locale.US).startsWith(string.toLowerCase(Locale.US))).filter(s -> !s.contains(" "));
    }
    
    public static File gameDir() {
        final Helper helper = Helper.HELPER;
        final File absoluteFile;
        if ((absoluteFile = Helper.mc.w.getAbsoluteFile()).getName().equals(".")) {
            return absoluteFile.getParentFile();
        }
        return absoluteFile;
    }
}
