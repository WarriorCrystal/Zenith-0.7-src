//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package com.github.lunatrius.schematica.client.gui.load;

import com.github.lunatrius.schematica.client.world.SchematicWorld;
import com.github.lunatrius.schematica.proxy.ClientProxy;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import com.github.lunatrius.schematica.world.schematic.SchematicUtil;
import net.minecraft.init.Blocks;
import java.util.Arrays;
import java.io.FileFilter;
import net.minecraft.init.Items;
import com.github.lunatrius.schematica.Schematica;
import org.lwjgl.Sys;
import com.github.lunatrius.schematica.reference.Reference;
import java.net.URI;
import java.io.IOException;
import java.util.ArrayList;
import com.github.lunatrius.schematica.handler.ConfigurationHandler;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.gui.GuiScreen;
import java.util.List;
import java.io.File;
import net.minecraft.client.gui.GuiButton;
import com.github.lunatrius.schematica.util.FileFilterSchematic;
import com.github.lunatrius.core.client.gui.GuiScreenBase;

public class GuiSchematicLoad extends GuiScreenBase
{
    private static final FileFilterSchematic FILE_FILTER_FOLDER;
    private static final FileFilterSchematic FILE_FILTER_SCHEMATIC;
    private GuiSchematicLoadSlot guiSchematicLoadSlot;
    private GuiButton btnOpenDir;
    private GuiButton btnDone;
    private final String strTitle;
    private final String strFolderInfo;
    private String strNoSchematic;
    protected File currentDirectory;
    protected final List<GuiSchematicEntry> schematicFiles;
    
    public GuiSchematicLoad(final GuiScreen guiScreen) {
        super(guiScreen);
        this.btnOpenDir = null;
        this.btnDone = null;
        this.strTitle = I18n.format("schematica.gui.title", new Object[0]);
        this.strFolderInfo = I18n.format("schematica.gui.folderInfo", new Object[0]);
        this.strNoSchematic = I18n.format("schematica.gui.noschematic", new Object[0]);
        this.currentDirectory = ConfigurationHandler.schematicDirectory;
        this.schematicFiles = new ArrayList<GuiSchematicEntry>();
    }
    
    @Override
    public void initGui() {
        int id = 0;
        this.btnOpenDir = new GuiButton(id++, this.width / 2 - 154, this.height - 36, 150, 20, I18n.format("schematica.gui.openFolder", new Object[0]));
        this.buttonList.add(this.btnOpenDir);
        this.btnDone = new GuiButton(id++, this.width / 2 + 4, this.height - 36, 150, 20, I18n.format("schematica.gui.done", new Object[0]));
        this.buttonList.add(this.btnDone);
        this.guiSchematicLoadSlot = new GuiSchematicLoadSlot(this);
        this.reloadSchematics();
    }
    
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        this.guiSchematicLoadSlot.handleMouseInput();
    }
    
    protected void actionPerformed(final GuiButton guiButton) {
        if (guiButton.enabled) {
            if (guiButton.id == this.btnOpenDir.id) {
                boolean retry = false;
                try {
                    final Class<?> c = Class.forName("java.awt.Desktop");
                    final Object m = c.getMethod("getDesktop", (Class<?>[])new Class[0]).invoke(null, new Object[0]);
                    c.getMethod("browse", URI.class).invoke(m, ConfigurationHandler.schematicDirectory.toURI());
                }
                catch (Throwable e) {
                    retry = true;
                }
                if (retry) {
                    Reference.logger.info("Opening via Sys class!");
                    Sys.openURL("file://" + ConfigurationHandler.schematicDirectory.getAbsolutePath());
                }
            }
            else if (guiButton.id == this.btnDone.id) {
                if (Schematica.proxy.isLoadEnabled) {
                    this.loadSchematic();
                }
                this.mc.displayGuiScreen(this.parentScreen);
            }
            else {
                this.guiSchematicLoadSlot.actionPerformed(guiButton);
            }
        }
    }
    
    @Override
    public void drawScreen(final int x, final int y, final float partialTicks) {
        this.guiSchematicLoadSlot.drawScreen(x, y, partialTicks);
        this.drawCenteredString(this.fontRenderer, this.strTitle, this.width / 2, 4, 16777215);
        this.drawCenteredString(this.fontRenderer, this.strFolderInfo, this.width / 2 - 78, this.height - 12, 8421504);
        super.drawScreen(x, y, partialTicks);
    }
    
    public void onGuiClosed() {
    }
    
    protected void changeDirectory(final String directory) {
        this.currentDirectory = new File(this.currentDirectory, directory);
        try {
            this.currentDirectory = this.currentDirectory.getCanonicalFile();
        }
        catch (IOException ioe) {
            Reference.logger.error("Failed to canonize directory!", (Throwable)ioe);
        }
        this.reloadSchematics();
    }
    
    protected void reloadSchematics() {
        String name = null;
        Item item = null;
        this.schematicFiles.clear();
        try {
            if (!this.currentDirectory.getCanonicalPath().equals(ConfigurationHandler.schematicDirectory.getCanonicalPath())) {
                this.schematicFiles.add(new GuiSchematicEntry("..", Items.LAVA_BUCKET, 0, true));
            }
        }
        catch (IOException e) {
            Reference.logger.error("Failed to add GuiSchematicEntry!", (Throwable)e);
        }
        final File[] filesFolders = this.currentDirectory.listFiles(GuiSchematicLoad.FILE_FILTER_FOLDER);
        if (filesFolders == null) {
            Reference.logger.error("listFiles returned null (directory: {})!", (Object)this.currentDirectory);
        }
        else {
            Arrays.sort(filesFolders, (a, b) -> a.getName().compareToIgnoreCase(b.getName()));
            for (final File file : filesFolders) {
                if (file != null) {
                    name = file.getName();
                    final File[] files = file.listFiles();
                    item = ((files == null || files.length == 0) ? Items.BUCKET : Items.WATER_BUCKET);
                    this.schematicFiles.add(new GuiSchematicEntry(name, item, 0, file.isDirectory()));
                }
            }
        }
        final File[] filesSchematics = this.currentDirectory.listFiles(GuiSchematicLoad.FILE_FILTER_SCHEMATIC);
        if (filesSchematics == null || filesSchematics.length == 0) {
            this.schematicFiles.add(new GuiSchematicEntry(this.strNoSchematic, Blocks.DIRT, 0, false));
        }
        else {
            Arrays.sort(filesSchematics, (a, b) -> a.getName().compareToIgnoreCase(b.getName()));
            for (final File file2 : filesSchematics) {
                name = file2.getName();
                this.schematicFiles.add(new GuiSchematicEntry(name, SchematicUtil.getIconFromFile(file2), file2.isDirectory()));
            }
        }
    }
    
    private void loadSchematic() {
        final int selectedIndex = this.guiSchematicLoadSlot.selectedIndex;
        try {
            if (selectedIndex >= 0 && selectedIndex < this.schematicFiles.size()) {
                final GuiSchematicEntry schematicEntry = this.schematicFiles.get(selectedIndex);
                if (Schematica.proxy.loadSchematic(null, this.currentDirectory, schematicEntry.getName())) {
                    final SchematicWorld schematic = ClientProxy.schematic;
                    if (schematic != null) {
                        ClientProxy.moveSchematicToPlayer(schematic);
                    }
                }
            }
        }
        catch (Exception e) {
            Reference.logger.error("Failed to load schematic!", (Throwable)e);
        }
    }
    
    static {
        FILE_FILTER_FOLDER = new FileFilterSchematic(true);
        FILE_FILTER_SCHEMATIC = new FileFilterSchematic(false);
    }
}
