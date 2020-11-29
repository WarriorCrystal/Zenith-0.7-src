//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package com.github.lunatrius.schematica.client.gui.control;

import java.util.Iterator;
import com.github.lunatrius.schematica.reference.Reference;
import java.io.OutputStream;
import org.apache.commons.io.IOUtils;
import java.nio.charset.Charset;
import java.io.FileOutputStream;
import java.io.File;
import com.github.lunatrius.schematica.Schematica;
import java.util.Formatter;
import net.minecraft.item.ItemStack;
import java.io.IOException;
import com.github.lunatrius.schematica.client.world.SchematicWorld;
import net.minecraft.world.World;
import net.minecraft.entity.player.EntityPlayer;
import com.github.lunatrius.schematica.proxy.ClientProxy;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import com.github.lunatrius.schematica.handler.ConfigurationHandler;
import net.minecraft.client.gui.GuiScreen;
import com.github.lunatrius.schematica.client.util.BlockList;
import java.util.List;
import net.minecraft.client.gui.GuiButton;
import net.minecraftforge.fml.client.config.GuiUnicodeGlyphButton;
import com.github.lunatrius.schematica.util.ItemStackSortType;
import com.github.lunatrius.core.client.gui.GuiScreenBase;

public class GuiSchematicMaterials extends GuiScreenBase
{
    private GuiSchematicMaterialsSlot guiSchematicMaterialsSlot;
    private ItemStackSortType sortType;
    private GuiUnicodeGlyphButton btnSort;
    private GuiButton btnDump;
    private GuiButton btnDone;
    private final String strMaterialName;
    private final String strMaterialAmount;
    protected final List<BlockList.WrappedItemStack> blockList;
    
    public GuiSchematicMaterials(final GuiScreen guiScreen) {
        super(guiScreen);
        this.sortType = ItemStackSortType.fromString(ConfigurationHandler.sortType);
        this.btnSort = null;
        this.btnDump = null;
        this.btnDone = null;
        this.strMaterialName = I18n.format("schematica.gui.materialname", new Object[0]);
        this.strMaterialAmount = I18n.format("schematica.gui.materialamount", new Object[0]);
        final Minecraft minecraft = Minecraft.getMinecraft();
        final SchematicWorld schematic = ClientProxy.schematic;
        this.blockList = new BlockList().getList((EntityPlayer)minecraft.player, schematic, (World)minecraft.world);
        this.sortType.sort(this.blockList);
    }
    
    @Override
    public void initGui() {
        int id = 0;
        this.btnSort = new GuiUnicodeGlyphButton(++id, this.width / 2 - 154, this.height - 30, 100, 20, " " + I18n.format("schematica.gui.material" + this.sortType.label, new Object[0]), this.sortType.glyph, 2.0f);
        this.buttonList.add(this.btnSort);
        this.btnDump = new GuiButton(++id, this.width / 2 - 50, this.height - 30, 100, 20, I18n.format("schematica.gui.materialdump", new Object[0]));
        this.buttonList.add(this.btnDump);
        this.btnDone = new GuiButton(++id, this.width / 2 + 54, this.height - 30, 100, 20, I18n.format("schematica.gui.done", new Object[0]));
        this.buttonList.add(this.btnDone);
        this.guiSchematicMaterialsSlot = new GuiSchematicMaterialsSlot(this);
    }
    
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        this.guiSchematicMaterialsSlot.handleMouseInput();
    }
    
    protected void actionPerformed(final GuiButton guiButton) {
        if (guiButton.enabled) {
            if (guiButton.id == this.btnSort.id) {
                (this.sortType = this.sortType.next()).sort(this.blockList);
                this.btnSort.displayString = " " + I18n.format("schematica.gui.material" + this.sortType.label, new Object[0]);
                this.btnSort.glyph = this.sortType.glyph;
                ConfigurationHandler.propSortType.set(String.valueOf(this.sortType));
                ConfigurationHandler.loadConfiguration();
            }
            else if (guiButton.id == this.btnDump.id) {
                this.dumpMaterialList(this.blockList);
            }
            else if (guiButton.id == this.btnDone.id) {
                this.mc.displayGuiScreen(this.parentScreen);
            }
            else {
                this.guiSchematicMaterialsSlot.actionPerformed(guiButton);
            }
        }
    }
    
    public void renderToolTip(final ItemStack stack, final int x, final int y) {
        super.renderToolTip(stack, x, y);
    }
    
    @Override
    public void drawScreen(final int x, final int y, final float partialTicks) {
        this.guiSchematicMaterialsSlot.drawScreen(x, y, partialTicks);
        this.drawString(this.fontRenderer, this.strMaterialName, this.width / 2 - 108, 4, 16777215);
        this.drawString(this.fontRenderer, this.strMaterialAmount, this.width / 2 + 108 - this.fontRenderer.getStringWidth(this.strMaterialAmount), 4, 16777215);
        super.drawScreen(x, y, partialTicks);
    }
    
    private void dumpMaterialList(final List<BlockList.WrappedItemStack> blockList) {
        if (blockList.size() <= 0) {
            return;
        }
        int maxLengthName = 0;
        int maxSize = 0;
        for (final BlockList.WrappedItemStack wrappedItemStack : blockList) {
            maxLengthName = Math.max(maxLengthName, wrappedItemStack.getItemStackDisplayName().length());
            maxSize = Math.max(maxSize, wrappedItemStack.total);
        }
        final int maxLengthSize = String.valueOf(maxSize).length();
        final String formatName = "%-" + maxLengthName + "s";
        final String formatSize = "%" + maxLengthSize + "d";
        final StringBuilder stringBuilder = new StringBuilder((maxLengthName + 1 + maxLengthSize) * blockList.size());
        final Formatter formatter = new Formatter(stringBuilder);
        for (final BlockList.WrappedItemStack wrappedItemStack2 : blockList) {
            formatter.format(formatName, wrappedItemStack2.getItemStackDisplayName());
            stringBuilder.append(" ");
            formatter.format(formatSize, wrappedItemStack2.total);
            stringBuilder.append(System.lineSeparator());
        }
        final File dumps = Schematica.proxy.getDirectory("dumps");
        try (final FileOutputStream outputStream = new FileOutputStream(new File(dumps, "schematica-materials.txt"))) {
            IOUtils.write(stringBuilder.toString(), (OutputStream)outputStream, Charset.forName("utf-8"));
        }
        catch (Exception e) {
            Reference.logger.error("Could not dump the material list!", (Throwable)e);
        }
    }
}
