// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.events.render;

import com.google.common.base.Predicate;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.entity.Entity;
import net.minecraft.client.multiplayer.WorldClient;
import me.gopro336.goprohack.events.MinecraftEvent;

public class EventRenderGetEntitiesINAABBexcluding extends MinecraftEvent
{
    public EventRenderGetEntitiesINAABBexcluding(final WorldClient worldClient, final Entity entityIn, final AxisAlignedBB boundingBox, final Predicate predicate) {
    }
}
