// 
// Decompiled by Procyon v0.5.36
// 

package baritone;

import java.util.HashMap;
import java.util.function.Function;
import java.util.UUID;
import java.nio.file.OpenOption;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.nio.file.Path;
import java.util.Map;

public final class h
{
    private static final Map<Path, h> a;
    private final Path a;
    private List<aip> a;
    
    private h(final Path path) {
        this.a = path;
        System.out.println("Echest storing in ".concat(String.valueOf(path)));
        try {
            this.a = r.a(Files.readAllBytes(path));
        }
        catch (IOException ex) {
            ex.printStackTrace();
            System.out.println("CANNOT read echest =( =(");
            this.a = null;
        }
    }
    
    public final synchronized void a() {
        System.out.println("Saving");
        if (this.a != null) {
            try {
                this.a.getParent().toFile().mkdir();
                Files.write(this.a, r.a(this.a), new OpenOption[0]);
            }
            catch (IOException ex) {
                ex.printStackTrace();
                System.out.println("CANNOT save echest =( =(");
            }
        }
    }
    
    private static synchronized h b(final Path path, final UUID uuid) {
        return h.a.computeIfAbsent(path.resolve("echests").resolve(uuid.toString()), h::new);
    }
    
    static {
        a = new HashMap<Path, h>();
    }
}
