// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.events.render;

import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.entity.EntityLivingBase;
import me.gopro336.goprohack.events.MinecraftEvent;

public class EventRenderArmorLayer extends MinecraftEvent
{
    public EntityLivingBase Entity;
    
    public EventRenderArmorLayer(final EntityLivingBase p_Entity, final float limbSwing, final float limbSwingAmount, final float partialTicks, final float ageInTicks, final float netHeadYaw, final float headPitch, final float scale, final EntityEquipmentSlot slotIn) {
        this.Entity = p_Entity;
    }
}
