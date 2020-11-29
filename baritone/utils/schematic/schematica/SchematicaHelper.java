// 
// Decompiled by Procyon v0.5.36
// 

package baritone.utils.schematic.schematica;

import com.github.lunatrius.schematica.client.world.SchematicWorld;
import com.github.lunatrius.schematica.proxy.ClientProxy;
import baritone.api.schematic.IStaticSchematic;
import java.util.Optional;
import com.github.lunatrius.schematica.Schematica;

public enum SchematicaHelper
{
    public static boolean a() {
        try {
            Class.forName(Schematica.class.getName());
            return true;
        }
        catch (ClassNotFoundException | NoClassDefFoundError ex) {
            return false;
        }
    }
    
    public static Optional<rr<IStaticSchematic, et>> a() {
        final rr rr;
        return Optional.ofNullable(ClientProxy.schematic).map(schematicWorld -> {
            new rr((Object)new SchematicAdapter(schematicWorld), (Object)schematicWorld.position);
            return rr;
        });
    }
}
