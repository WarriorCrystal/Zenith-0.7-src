// 
// Decompiled by Procyon v0.5.36
// 

package com.github.lunatrius.schematica.api.event;

import net.minecraft.nbt.NBTTagCompound;
import com.github.lunatrius.schematica.api.ISchematic;
import java.util.Map;
import net.minecraftforge.fml.common.eventhandler.Event;

public class PreSchematicSaveEvent extends Event
{
    private final Map<String, Short> mappings;
    public final ISchematic schematic;
    public final NBTTagCompound extendedMetadata;
    
    @Deprecated
    public PreSchematicSaveEvent(final Map<String, Short> mappings) {
        this(null, mappings);
    }
    
    public PreSchematicSaveEvent(final ISchematic schematic, final Map<String, Short> mappings) {
        this.schematic = schematic;
        this.mappings = mappings;
        this.extendedMetadata = new NBTTagCompound();
    }
    
    public boolean replaceMapping(final String oldName, final String newName) throws DuplicateMappingException {
        if (this.mappings.containsKey(newName)) {
            throw new DuplicateMappingException(String.format("Could not replace block type %s, the block type %s already exists in the schematic.", oldName, newName));
        }
        final Short id = this.mappings.get(oldName);
        if (id != null) {
            this.mappings.remove(oldName);
            this.mappings.put(newName, id);
            return true;
        }
        return false;
    }
}
