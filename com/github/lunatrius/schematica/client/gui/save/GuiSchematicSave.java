//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package com.github.lunatrius.schematica.client.gui.save;

import java.io.IOException;
import java.io.File;
import net.minecraft.world.World;
import net.minecraft.entity.player.EntityPlayer;
import com.github.lunatrius.schematica.handler.ConfigurationHandler;
import net.minecraft.util.math.BlockPos;
import com.github.lunatrius.schematica.world.schematic.SchematicFormat;
import com.github.lunatrius.schematica.Schematica;
import com.github.lunatrius.schematica.proxy.ClientProxy;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.gui.GuiScreen;
import java.util.Iterator;
import net.minecraft.client.gui.GuiTextField;
import com.github.lunatrius.core.client.gui.GuiNumericField;
import net.minecraft.client.gui.GuiButton;
import com.github.lunatrius.core.client.gui.GuiScreenBase;

public class GuiSchematicSave extends GuiScreenBase
{
    private int centerX;
    private int centerY;
    private GuiButton btnPointA;
    private GuiNumericField numericAX;
    private GuiNumericField numericAY;
    private GuiNumericField numericAZ;
    private GuiButton btnPointB;
    private GuiNumericField numericBX;
    private GuiNumericField numericBY;
    private GuiNumericField numericBZ;
    private GuiButton btnEnable;
    private GuiButton btnFormat;
    private GuiButton btnSave;
    private GuiTextField tfFilename;
    private String filename;
    private String format;
    private Iterator<String> formatIterator;
    private final String strSaveSelection;
    private final String strX;
    private final String strY;
    private final String strZ;
    private final String strOn;
    private final String strOff;
    
    public GuiSchematicSave(final GuiScreen guiScreen) {
        super(guiScreen);
        this.centerX = 0;
        this.centerY = 0;
        this.btnPointA = null;
        this.numericAX = null;
        this.numericAY = null;
        this.numericAZ = null;
        this.btnPointB = null;
        this.numericBX = null;
        this.numericBY = null;
        this.numericBZ = null;
        this.btnEnable = null;
        this.btnFormat = null;
        this.btnSave = null;
        this.tfFilename = null;
        this.filename = "";
        this.formatIterator = null;
        this.strSaveSelection = I18n.format("schematica.gui.saveselection", new Object[0]);
        this.strX = I18n.format("schematica.gui.x", new Object[0]);
        this.strY = I18n.format("schematica.gui.y", new Object[0]);
        this.strZ = I18n.format("schematica.gui.z", new Object[0]);
        this.strOn = I18n.format("schematica.gui.on", new Object[0]);
        this.strOff = I18n.format("schematica.gui.off", new Object[0]);
        this.format = this.nextFormat();
    }
    
    @Override
    public void initGui() {
        this.centerX = this.width / 2;
        this.centerY = this.height / 2;
        this.buttonList.clear();
        int id = 0;
        this.btnPointA = new GuiButton(id++, this.centerX - 130, this.centerY - 55, 100, 20, I18n.format("schematica.gui.point.red", new Object[0]));
        this.buttonList.add(this.btnPointA);
        this.numericAX = new GuiNumericField(this.fontRenderer, id++, this.centerX - 130, this.centerY - 30);
        this.buttonList.add(this.numericAX);
        this.numericAY = new GuiNumericField(this.fontRenderer, id++, this.centerX - 130, this.centerY - 5);
        this.buttonList.add(this.numericAY);
        this.numericAZ = new GuiNumericField(this.fontRenderer, id++, this.centerX - 130, this.centerY + 20);
        this.buttonList.add(this.numericAZ);
        this.btnPointB = new GuiButton(id++, this.centerX + 30, this.centerY - 55, 100, 20, I18n.format("schematica.gui.point.blue", new Object[0]));
        this.buttonList.add(this.btnPointB);
        this.numericBX = new GuiNumericField(this.fontRenderer, id++, this.centerX + 30, this.centerY - 30);
        this.buttonList.add(this.numericBX);
        this.numericBY = new GuiNumericField(this.fontRenderer, id++, this.centerX + 30, this.centerY - 5);
        this.buttonList.add(this.numericBY);
        this.numericBZ = new GuiNumericField(this.fontRenderer, id++, this.centerX + 30, this.centerY + 20);
        this.buttonList.add(this.numericBZ);
        this.btnEnable = new GuiButton(id++, this.width - 210, this.height - 55, 50, 20, (ClientProxy.isRenderingGuide && Schematica.proxy.isSaveEnabled) ? this.strOn : this.strOff);
        this.buttonList.add(this.btnEnable);
        this.tfFilename = new GuiTextField(id++, this.fontRenderer, this.width - 209, this.height - 29, 153, 18);
        this.textFields.add(this.tfFilename);
        this.btnSave = new GuiButton(id++, this.width - 50, this.height - 30, 40, 20, I18n.format("schematica.gui.save", new Object[0]));
        this.btnSave.enabled = ((ClientProxy.isRenderingGuide && Schematica.proxy.isSaveEnabled) || ClientProxy.schematic != null);
        this.buttonList.add(this.btnSave);
        this.btnFormat = new GuiButton(id++, this.width - 155, this.height - 55, 145, 20, I18n.format("schematica.gui.format", new Object[] { I18n.format(SchematicFormat.getFormatName(this.format), new Object[0]) }));
        this.btnFormat.enabled = ((ClientProxy.isRenderingGuide && Schematica.proxy.isSaveEnabled) || ClientProxy.schematic != null);
        this.buttonList.add(this.btnFormat);
        this.tfFilename.setMaxStringLength(1024);
        this.tfFilename.setText(this.filename);
        this.setMinMax(this.numericAX);
        this.setMinMax(this.numericAY);
        this.setMinMax(this.numericAZ);
        this.setMinMax(this.numericBX);
        this.setMinMax(this.numericBY);
        this.setMinMax(this.numericBZ);
        this.setPoint(this.numericAX, this.numericAY, this.numericAZ, ClientProxy.pointA);
        this.setPoint(this.numericBX, this.numericBY, this.numericBZ, ClientProxy.pointB);
    }
    
    private void setMinMax(final GuiNumericField numericField) {
        numericField.setMinimum(-30000000);
        numericField.setMaximum(30000000);
    }
    
    private void setPoint(final GuiNumericField numX, final GuiNumericField numY, final GuiNumericField numZ, final BlockPos point) {
        numX.setValue(point.getX());
        numY.setValue(point.getY());
        numZ.setValue(point.getZ());
    }
    
    protected void actionPerformed(final GuiButton guiButton) {
        if (guiButton.enabled) {
            if (guiButton.id == this.btnPointA.id) {
                ClientProxy.movePointToPlayer(ClientProxy.pointA);
                ClientProxy.updatePoints();
                this.setPoint(this.numericAX, this.numericAY, this.numericAZ, ClientProxy.pointA);
            }
            else if (guiButton.id == this.numericAX.id) {
                ClientProxy.pointA.x = this.numericAX.getValue();
                ClientProxy.updatePoints();
            }
            else if (guiButton.id == this.numericAY.id) {
                ClientProxy.pointA.y = this.numericAY.getValue();
                ClientProxy.updatePoints();
            }
            else if (guiButton.id == this.numericAZ.id) {
                ClientProxy.pointA.z = this.numericAZ.getValue();
                ClientProxy.updatePoints();
            }
            else if (guiButton.id == this.btnPointB.id) {
                ClientProxy.movePointToPlayer(ClientProxy.pointB);
                ClientProxy.updatePoints();
                this.setPoint(this.numericBX, this.numericBY, this.numericBZ, ClientProxy.pointB);
            }
            else if (guiButton.id == this.numericBX.id) {
                ClientProxy.pointB.x = this.numericBX.getValue();
                ClientProxy.updatePoints();
            }
            else if (guiButton.id == this.numericBY.id) {
                ClientProxy.pointB.y = this.numericBY.getValue();
                ClientProxy.updatePoints();
            }
            else if (guiButton.id == this.numericBZ.id) {
                ClientProxy.pointB.z = this.numericBZ.getValue();
                ClientProxy.updatePoints();
            }
            else if (guiButton.id == this.btnEnable.id) {
                ClientProxy.isRenderingGuide = (!ClientProxy.isRenderingGuide && Schematica.proxy.isSaveEnabled);
                this.btnEnable.displayString = (ClientProxy.isRenderingGuide ? this.strOn : this.strOff);
                this.btnSave.enabled = (ClientProxy.isRenderingGuide || ClientProxy.schematic != null);
                this.btnFormat.enabled = (ClientProxy.isRenderingGuide || ClientProxy.schematic != null);
            }
            else if (guiButton.id == this.btnFormat.id) {
                this.format = this.nextFormat();
                this.btnFormat.displayString = I18n.format("schematica.gui.format", new Object[] { I18n.format(SchematicFormat.getFormatName(this.format), new Object[0]) });
            }
            else if (guiButton.id == this.btnSave.id) {
                final String path = this.tfFilename.getText() + SchematicFormat.getExtension(this.format);
                if (ClientProxy.isRenderingGuide) {
                    if (Schematica.proxy.saveSchematic((EntityPlayer)this.mc.player, ConfigurationHandler.schematicDirectory, path, (World)this.mc.world, this.format, ClientProxy.pointMin, ClientProxy.pointMax)) {
                        this.filename = "";
                        this.tfFilename.setText(this.filename);
                    }
                }
                else {
                    SchematicFormat.writeToFileAndNotify(new File(ConfigurationHandler.schematicDirectory, path), this.format, ClientProxy.schematic.getSchematic(), (EntityPlayer)this.mc.player);
                }
            }
        }
    }
    
    @Override
    protected void keyTyped(final char character, final int code) throws IOException {
        super.keyTyped(character, code);
        this.filename = this.tfFilename.getText();
    }
    
    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        this.drawString(this.fontRenderer, this.strSaveSelection, this.width - 205, this.height - 70, 16777215);
        this.drawString(this.fontRenderer, this.strX, this.centerX - 145, this.centerY - 24, 16777215);
        this.drawString(this.fontRenderer, Integer.toString(ClientProxy.pointA.x), this.centerX - 25, this.centerY - 24, 16777215);
        this.drawString(this.fontRenderer, this.strY, this.centerX - 145, this.centerY + 1, 16777215);
        this.drawString(this.fontRenderer, Integer.toString(ClientProxy.pointA.y), this.centerX - 25, this.centerY + 1, 16777215);
        this.drawString(this.fontRenderer, this.strZ, this.centerX - 145, this.centerY + 26, 16777215);
        this.drawString(this.fontRenderer, Integer.toString(ClientProxy.pointA.z), this.centerX - 25, this.centerY + 26, 16777215);
        this.drawString(this.fontRenderer, this.strX, this.centerX + 15, this.centerY - 24, 16777215);
        this.drawString(this.fontRenderer, Integer.toString(ClientProxy.pointB.x), this.centerX + 135, this.centerY - 24, 16777215);
        this.drawString(this.fontRenderer, this.strY, this.centerX + 15, this.centerY + 1, 16777215);
        this.drawString(this.fontRenderer, Integer.toString(ClientProxy.pointB.y), this.centerX + 135, this.centerY + 1, 16777215);
        this.drawString(this.fontRenderer, this.strZ, this.centerX + 15, this.centerY + 26, 16777215);
        this.drawString(this.fontRenderer, Integer.toString(ClientProxy.pointB.z), this.centerX + 135, this.centerY + 26, 16777215);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
    
    private String nextFormat() {
        if (this.formatIterator != null) {
            if (!this.formatIterator.hasNext()) {
                this.formatIterator = SchematicFormat.FORMATS.keySet().iterator();
            }
            return this.formatIterator.next();
        }
        assert SchematicFormat.FORMATS.size() > 0 : "No formats are defined!";
        assert SchematicFormat.FORMATS.containsKey(SchematicFormat.FORMAT_DEFAULT) : "The default format does not exist!";
        this.formatIterator = SchematicFormat.FORMATS.keySet().iterator();
        while (!this.formatIterator.next().equals(SchematicFormat.FORMAT_DEFAULT)) {}
        return SchematicFormat.FORMAT_DEFAULT;
    }
}
