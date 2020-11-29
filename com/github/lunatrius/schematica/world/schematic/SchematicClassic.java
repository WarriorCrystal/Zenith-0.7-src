// 
// Decompiled by Procyon v0.5.36
// 

package com.github.lunatrius.schematica.world.schematic;

import com.github.lunatrius.schematica.api.ISchematic;
import net.minecraft.nbt.NBTTagCompound;

public class SchematicClassic extends SchematicFormat
{
    @Override
    public ISchematic readFromNBT(final NBTTagCompound tagCompound) {
        return null;
    }
    
    @Override
    public boolean writeToNBT(final NBTTagCompound tagCompound, final ISchematic schematic) {
        return false;
    }
    
    @Override
    public String getName() {
        return "schematica.format.classic";
    }
    
    @Override
    public String getExtension() {
        return ".schematic";
    }
}
