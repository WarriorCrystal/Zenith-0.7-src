//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package com.github.lunatrius.schematica.world.schematic;

import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import java.io.IOException;
import com.github.lunatrius.schematica.reference.Reference;
import java.io.InputStream;
import net.minecraft.nbt.CompressedStreamTools;
import java.io.FileInputStream;
import net.minecraft.nbt.NBTTagCompound;
import java.io.File;
import net.minecraft.item.ItemStack;

public final class SchematicUtil
{
    public static final ItemStack DEFAULT_ICON;
    
    public static NBTTagCompound readTagCompoundFromFile(final File file) throws IOException {
        try {
            return CompressedStreamTools.readCompressed((InputStream)new FileInputStream(file));
        }
        catch (Exception ex) {
            Reference.logger.warn("Failed compressed read, trying normal read...", (Throwable)ex);
            return CompressedStreamTools.read(file);
        }
    }
    
    public static ItemStack getIconFromName(final String iconName) {
        ResourceLocation rl = null;
        int damage = 0;
        final String[] parts = iconName.split(",");
        if (parts.length >= 1) {
            rl = new ResourceLocation(parts[0]);
            if (parts.length >= 2) {
                try {
                    damage = Integer.parseInt(parts[1]);
                }
                catch (NumberFormatException ex) {}
            }
        }
        if (rl == null) {
            return SchematicUtil.DEFAULT_ICON.copy();
        }
        final ItemStack block = new ItemStack((Block)Block.REGISTRY.getObject((Object)rl), 1, damage);
        if (!block.isEmpty()) {
            return block;
        }
        final ItemStack item = new ItemStack((Item)Item.REGISTRY.getObject((Object)rl), 1, damage);
        if (!item.isEmpty()) {
            return item;
        }
        return SchematicUtil.DEFAULT_ICON.copy();
    }
    
    public static ItemStack getIconFromNBT(final NBTTagCompound tagCompound) {
        ItemStack icon = SchematicUtil.DEFAULT_ICON.copy();
        if (tagCompound != null && tagCompound.hasKey("Icon")) {
            icon.deserializeNBT(tagCompound.getCompoundTag("Icon"));
            if (icon.isEmpty()) {
                icon = SchematicUtil.DEFAULT_ICON.copy();
            }
        }
        return icon;
    }
    
    public static ItemStack getIconFromFile(final File file) {
        try {
            return getIconFromNBT(readTagCompoundFromFile(file));
        }
        catch (Exception e) {
            Reference.logger.error("Failed to read schematic icon!", (Throwable)e);
            return SchematicUtil.DEFAULT_ICON.copy();
        }
    }
    
    static {
        DEFAULT_ICON = new ItemStack((Block)Blocks.GRASS);
    }
}
