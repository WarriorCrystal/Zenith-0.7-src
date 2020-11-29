//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package com.github.lunatrius.schematica.client.gui.load;

import com.github.lunatrius.core.client.gui.GuiHelper;
import org.apache.commons.io.FilenameUtils;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiSlot;

public class GuiSchematicLoadSlot extends GuiSlot
{
    private final Minecraft minecraft;
    private final GuiSchematicLoad guiSchematicLoad;
    protected int selectedIndex;
    private long lastClick;
    
    public GuiSchematicLoadSlot(final GuiSchematicLoad guiSchematicLoad) {
        super(Minecraft.getMinecraft(), guiSchematicLoad.width, guiSchematicLoad.height, 16, guiSchematicLoad.height - 40, 24);
        this.minecraft = Minecraft.getMinecraft();
        this.selectedIndex = -1;
        this.lastClick = 0L;
        this.guiSchematicLoad = guiSchematicLoad;
    }
    
    protected int getSize() {
        return this.guiSchematicLoad.schematicFiles.size();
    }
    
    protected void elementClicked(final int slotIndex, final boolean isDoubleClick, final int mouseX, final int mouseY) {
        final boolean ignore = Minecraft.getSystemTime() - this.lastClick < 500L;
        this.lastClick = Minecraft.getSystemTime();
        if (ignore) {
            return;
        }
        final GuiSchematicEntry schematic = this.guiSchematicLoad.schematicFiles.get(slotIndex);
        if (schematic.isDirectory()) {
            this.guiSchematicLoad.changeDirectory(schematic.getName());
            this.selectedIndex = -1;
        }
        else {
            this.selectedIndex = slotIndex;
        }
    }
    
    protected boolean isSelected(final int index) {
        return index == this.selectedIndex;
    }
    
    protected void drawBackground() {
    }
    
    protected void drawContainerBackground(final Tessellator tessellator) {
    }
    
    protected void drawSlot(final int index, final int x, final int y, final int par4, final int mouseX, final int mouseY, final float partialTicks) {
        if (index < 0 || index >= this.guiSchematicLoad.schematicFiles.size()) {
            return;
        }
        final GuiSchematicEntry schematic = this.guiSchematicLoad.schematicFiles.get(index);
        String schematicName = schematic.getName();
        if (schematic.isDirectory()) {
            schematicName += "/";
        }
        else {
            schematicName = FilenameUtils.getBaseName(schematicName);
        }
        GuiHelper.drawItemStackWithSlot(this.minecraft.renderEngine, schematic.getItemStack(), x, y);
        this.guiSchematicLoad.drawString(this.minecraft.fontRenderer, schematicName, x + 24, y + 6, 16777215);
    }
}
