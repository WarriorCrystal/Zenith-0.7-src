// 
// Decompiled by Procyon v0.5.36
// 

package com.github.lunatrius.schematica.api.event;

import com.github.lunatrius.schematica.api.ISchematic;
import net.minecraftforge.fml.common.eventhandler.Event;

public class PostSchematicCaptureEvent extends Event
{
    public final ISchematic schematic;
    
    public PostSchematicCaptureEvent(final ISchematic schematic) {
        this.schematic = schematic;
    }
}
