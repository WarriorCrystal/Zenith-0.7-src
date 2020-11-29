//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package com.github.lunatrius.core.entity;

import com.github.lunatrius.core.util.vector.Vector3i;
import com.github.lunatrius.core.util.vector.Vector3f;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;
import net.minecraft.inventory.IInventory;

public class EntityHelper
{
    public static final int WILDMARK = -1;
    
    public static int getItemCountInInventory(final IInventory inventory, final Item item) {
        return getItemCountInInventory(inventory, item, -1);
    }
    
    public static int getItemCountInInventory(final IInventory inventory, final Item item, final int itemDamage) {
        final int inventorySize = inventory.getSizeInventory();
        int count = 0;
        for (int slot = 0; slot < inventorySize; ++slot) {
            final ItemStack itemStack = inventory.getStackInSlot(slot);
            if (itemStack.getItem() == item && (itemDamage == -1 || itemDamage == itemStack.getItemDamage())) {
                count += itemStack.getCount();
            }
        }
        return count;
    }
    
    public static Vector3f getVector3fFromEntity(final Entity entity) {
        return new Vector3f((float)entity.posX, (float)entity.posY, (float)entity.posZ);
    }
    
    public static Vector3f getVector3fFromEntity(final Entity entity, final Vector3f vec) {
        return vec.set((float)entity.posX, (float)entity.posY, (float)entity.posZ);
    }
    
    public static Vector3i getVector3iFromEntity(final Entity entity) {
        return new Vector3i((int)Math.floor(entity.posX), (int)Math.floor(entity.posY), (int)Math.floor(entity.posZ));
    }
    
    public static Vector3i getVector3iFromEntity(final Entity entity, final Vector3i vec) {
        return vec.set((int)Math.floor(entity.posX), (int)Math.floor(entity.posY), (int)Math.floor(entity.posZ));
    }
}
