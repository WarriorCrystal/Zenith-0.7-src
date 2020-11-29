// 
// Decompiled by Procyon v0.5.36
// 

package baritone;

import baritone.api.cache.IContainerMemory;
import baritone.api.cache.IWaypointCollection;
import baritone.api.cache.ICachedWorld;
import java.io.IOException;
import java.nio.file.Path;
import baritone.api.cache.IWorldData;

public final class u implements IWorldData
{
    public final n a;
    private final t a;
    private final r a;
    public final Path a;
    private int a;
    
    u(final Path a, final int a2) {
        this.a = a;
        this.a = new n(a.resolve("cache"), a2);
        this.a = new t(a.resolve("waypoints"));
        this.a = new r(a.resolve("containers"));
        this.a = a2;
    }
    
    public final void a() {
        baritone.a.a().execute(() -> {
            System.out.println("Started saving the world in a new thread");
            this.a.save();
            return;
        });
        baritone.a.a().execute(() -> {
            System.out.println("Started saving saved containers in a new thread");
            try {
                this.a.a();
            }
            catch (IOException ex) {
                ex.printStackTrace();
                System.out.println("Failed to save saved containers");
            }
        });
    }
    
    @Override
    public final ICachedWorld getCachedWorld() {
        return this.a;
    }
    
    @Override
    public final IWaypointCollection getWaypoints() {
        return this.a;
    }
    
    @Override
    public final IContainerMemory getContainerMemory() {
        return this.a;
    }
}
