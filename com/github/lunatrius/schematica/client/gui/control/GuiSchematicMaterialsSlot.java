//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package com.github.lunatrius.schematica.client.gui.control;

import net.minecraft.item.ItemStack;
import net.minecraft.client.renderer.GlStateManager;
import com.github.lunatrius.core.client.gui.GuiHelper;
import com.github.lunatrius.schematica.client.util.BlockList;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiSlot;

class GuiSchematicMaterialsSlot extends GuiSlot
{
    private final Minecraft minecraft;
    private final GuiSchematicMaterials guiSchematicMaterials;
    private final String strMaterialAvailable;
    private final String strMaterialMissing;
    protected int selectedIndex;
    
    public GuiSchematicMaterialsSlot(final GuiSchematicMaterials parent) {
        super(Minecraft.getMinecraft(), parent.width, parent.height, 16, parent.height - 34, 24);
        this.minecraft = Minecraft.getMinecraft();
        this.strMaterialAvailable = I18n.format("schematica.gui.materialavailable", new Object[0]);
        this.strMaterialMissing = I18n.format("schematica.gui.materialmissing", new Object[0]);
        this.selectedIndex = -1;
        this.guiSchematicMaterials = parent;
        this.selectedIndex = -1;
    }
    
    protected int getSize() {
        return this.guiSchematicMaterials.blockList.size();
    }
    
    protected void elementClicked(final int index, final boolean par2, final int par3, final int par4) {
        this.selectedIndex = index;
    }
    
    protected boolean isSelected(final int index) {
        return index == this.selectedIndex;
    }
    
    protected void drawBackground() {
    }
    
    protected void drawContainerBackground(final Tessellator tessellator) {
    }
    
    protected int getScrollBarX() {
        return this.width / 2 + this.getListWidth() / 2 + 2;
    }
    
    protected void drawSlot(final int index, final int x, final int y, final int par4, final int mouseX, final int mouseY, final float partialTicks) {
        final BlockList.WrappedItemStack wrappedItemStack = this.guiSchematicMaterials.blockList.get(index);
        final ItemStack itemStack = wrappedItemStack.itemStack;
        final String itemName = wrappedItemStack.getItemStackDisplayName();
        final String amount = wrappedItemStack.getFormattedAmount();
        final String amountMissing = wrappedItemStack.getFormattedAmountMissing(this.strMaterialAvailable, this.strMaterialMissing);
        GuiHelper.drawItemStackWithSlot(this.minecraft.renderEngine, itemStack, x, y);
        this.guiSchematicMaterials.drawString(this.minecraft.fontRenderer, itemName, x + 24, y + 6, 16777215);
        this.guiSchematicMaterials.drawString(this.minecraft.fontRenderer, amount, x + 215 - this.minecraft.fontRenderer.getStringWidth(amount), y + 1, 16777215);
        this.guiSchematicMaterials.drawString(this.minecraft.fontRenderer, amountMissing, x + 215 - this.minecraft.fontRenderer.getStringWidth(amountMissing), y + 11, 16777215);
        if (mouseX > x && mouseY > y && mouseX <= x + 18 && mouseY <= y + 18) {
            this.guiSchematicMaterials.renderToolTip(itemStack, mouseX, mouseY);
            GlStateManager.disableLighting();
        }
    }
}
