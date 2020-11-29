// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.events.render;

import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityLivingBase;
import me.zero.alpine.fork.event.type.Cancellable;

public class EventRenderLayers extends Cancellable
{
    private final EntityLivingBase entityLivingBase;
    private final LayerRenderer layerRenderer;
    private float HeadPitch;
    
    public EventRenderLayers(final EntityLivingBase entityLivingBase, final LayerRenderer layerRenderer, final float headPitch) {
        this.entityLivingBase = entityLivingBase;
        this.layerRenderer = layerRenderer;
        this.HeadPitch = headPitch;
    }
    
    public EntityLivingBase getEntityLivingBase() {
        return this.entityLivingBase;
    }
    
    public LayerRenderer getLayerRenderer() {
        return this.layerRenderer;
    }
    
    public float GetHeadPitch() {
        return this.HeadPitch;
    }
    
    public void SetHeadPitch(final float p_Pitch) {
        this.HeadPitch = p_Pitch;
    }
}
