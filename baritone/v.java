// 
// Decompiled by Procyon v0.5.36
// 

package baritone;

import java.util.HashMap;
import baritone.api.cache.IWorldData;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.io.IOException;
import java.io.FileOutputStream;
import org.apache.commons.lang3.SystemUtils;
import java.io.File;
import java.nio.file.Path;
import java.util.Map;
import baritone.api.utils.Helper;
import baritone.api.cache.IWorldProvider;

public final class v implements IWorldProvider, Helper
{
    private static final Map<Path, u> a;
    public u a;
    
    public final void a(final int i) {
        final chd f = v.mc.F();
        File a;
        File parent2;
        if (v.mc.E()) {
            File parent;
            if ((parent = ((ga)((ge)f.a(i).r()).getChunkLoader()).getChunkSaveLocation()).toPath().relativize(v.mc.w.toPath()).getNameCount() != 2) {
                parent = parent.getParentFile();
            }
            parent2 = (a = new File(parent, "baritone"));
        }
        else {
            String child = v.mc.C().b;
            if (SystemUtils.IS_OS_WINDOWS) {
                child = child.replace(":", "_");
            }
            parent2 = new File(baritone.a.a(), child);
            a = baritone.a.a();
        }
        try {
            final FileOutputStream fileOutputStream = new FileOutputStream(new File(a, "readme.txt"));
            Throwable t = null;
            try {
                fileOutputStream.write("https://github.com/cabaletta/baritone\n".getBytes());
                fileOutputStream.close();
            }
            catch (Throwable t3) {
                final Throwable t2 = t3;
                t = t3;
                throw t2;
            }
            finally {
                if (t != null) {
                    try {
                        fileOutputStream.close();
                    }
                    catch (Throwable exception) {
                        t.addSuppressed(exception);
                    }
                }
                else {
                    fileOutputStream.close();
                }
            }
        }
        catch (IOException ex) {}
        final Path path2;
        if (!Files.exists(path2 = new File(parent2, "DIM".concat(String.valueOf(i))).toPath(), new LinkOption[0])) {
            try {
                Files.createDirectories(path2, (FileAttribute<?>[])new FileAttribute[0]);
            }
            catch (IOException ex2) {}
        }
        System.out.println("Baritone world data dir: ".concat(String.valueOf(path2)));
        synchronized (v.a) {
            this.a = v.a.computeIfAbsent(path2, path -> new u(path, i));
        }
    }
    
    static {
        a = new HashMap<Path, u>();
    }
}
