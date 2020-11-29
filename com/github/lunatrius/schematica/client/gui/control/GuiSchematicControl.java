//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package com.github.lunatrius.schematica.client.gui.control;

import java.io.IOException;
import com.github.lunatrius.schematica.client.util.RotationHelper;
import com.github.lunatrius.schematica.client.util.FlipHelper;
import net.minecraft.util.EnumFacing;
import com.github.lunatrius.schematica.Schematica;
import com.github.lunatrius.schematica.client.renderer.RenderSchematic;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.client.config.GuiUnicodeGlyphButton;
import com.github.lunatrius.schematica.proxy.ClientProxy;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiButton;
import com.github.lunatrius.core.client.gui.GuiNumericField;
import com.github.lunatrius.schematica.client.printer.SchematicPrinter;
import com.github.lunatrius.schematica.client.world.SchematicWorld;
import com.github.lunatrius.core.client.gui.GuiScreenBase;

public class GuiSchematicControl extends GuiScreenBase
{
    private final SchematicWorld schematic;
    private final SchematicPrinter printer;
    private int centerX;
    private int centerY;
    private GuiNumericField numericX;
    private GuiNumericField numericY;
    private GuiNumericField numericZ;
    private GuiButton btnUnload;
    private GuiButton btnLayerMode;
    private GuiNumericField nfLayer;
    private GuiButton btnHide;
    private GuiButton btnMove;
    private GuiButton btnFlipDirection;
    private GuiButton btnFlip;
    private GuiButton btnRotateDirection;
    private GuiButton btnRotate;
    private GuiButton btnMaterials;
    private final String strMoveSchematic;
    private final String strOperations;
    private final String strUnload;
    private final String strMaterials;
    private final String strHide;
    private final String strShow;
    private final String strX;
    private final String strY;
    private final String strZ;
    private final String strOn;
    private final String strOff;
    
    public GuiSchematicControl(final GuiScreen guiScreen) {
        super(guiScreen);
        this.centerX = 0;
        this.centerY = 0;
        this.numericX = null;
        this.numericY = null;
        this.numericZ = null;
        this.btnUnload = null;
        this.btnLayerMode = null;
        this.nfLayer = null;
        this.btnHide = null;
        this.btnMove = null;
        this.btnFlipDirection = null;
        this.btnFlip = null;
        this.btnRotateDirection = null;
        this.btnRotate = null;
        this.btnMaterials = null;
        this.strMoveSchematic = I18n.format("schematica.gui.moveschematic", new Object[0]);
        this.strOperations = I18n.format("schematica.gui.operations", new Object[0]);
        this.strUnload = I18n.format("schematica.gui.unload", new Object[0]);
        this.strMaterials = I18n.format("schematica.gui.materials", new Object[0]);
        this.strHide = I18n.format("schematica.gui.hide", new Object[0]);
        this.strShow = I18n.format("schematica.gui.show", new Object[0]);
        this.strX = I18n.format("schematica.gui.x", new Object[0]);
        this.strY = I18n.format("schematica.gui.y", new Object[0]);
        this.strZ = I18n.format("schematica.gui.z", new Object[0]);
        this.strOn = I18n.format("schematica.gui.on", new Object[0]);
        this.strOff = I18n.format("schematica.gui.off", new Object[0]);
        this.schematic = ClientProxy.schematic;
        this.printer = SchematicPrinter.INSTANCE;
    }
    
    @Override
    public void initGui() {
        this.centerX = this.width / 2;
        this.centerY = this.height / 2;
        this.buttonList.clear();
        int id = 0;
        this.numericX = new GuiNumericField(this.fontRenderer, id++, this.centerX - 50, this.centerY - 30, 100, 20);
        this.buttonList.add(this.numericX);
        this.numericY = new GuiNumericField(this.fontRenderer, id++, this.centerX - 50, this.centerY - 5, 100, 20);
        this.buttonList.add(this.numericY);
        this.numericZ = new GuiNumericField(this.fontRenderer, id++, this.centerX - 50, this.centerY + 20, 100, 20);
        this.buttonList.add(this.numericZ);
        this.btnUnload = new GuiButton(id++, this.width - 90, this.height - 200, 80, 20, this.strUnload);
        this.buttonList.add(this.btnUnload);
        this.btnLayerMode = new GuiButton(id++, this.width - 90, this.height - 150 - 25, 80, 20, I18n.format(((this.schematic != null) ? this.schematic.layerMode : SchematicWorld.LayerMode.ALL).name, new Object[0]));
        this.buttonList.add(this.btnLayerMode);
        this.nfLayer = new GuiNumericField(this.fontRenderer, id++, this.width - 90, this.height - 150, 80, 20);
        this.buttonList.add(this.nfLayer);
        this.btnHide = new GuiButton(id++, this.width - 90, this.height - 105, 80, 20, (this.schematic != null && this.schematic.isRendering) ? this.strHide : this.strShow);
        this.buttonList.add(this.btnHide);
        this.btnMove = new GuiButton(id++, this.width - 90, this.height - 80, 80, 20, I18n.format("schematica.gui.movehere", new Object[0]));
        this.buttonList.add(this.btnMove);
        this.btnFlipDirection = new GuiButton(id++, this.width - 180, this.height - 55, 80, 20, I18n.format("schematica.gui." + ClientProxy.axisFlip.getName(), new Object[0]));
        this.buttonList.add(this.btnFlipDirection);
        this.btnFlip = (GuiButton)new GuiUnicodeGlyphButton(id++, this.width - 90, this.height - 55, 80, 20, " " + I18n.format("schematica.gui.flip", new Object[0]), "\u2194", 2.0f);
        this.buttonList.add(this.btnFlip);
        this.btnRotateDirection = new GuiButton(id++, this.width - 180, this.height - 30, 80, 20, I18n.format("schematica.gui." + ClientProxy.axisRotation.getName(), new Object[0]));
        this.buttonList.add(this.btnRotateDirection);
        this.btnRotate = (GuiButton)new GuiUnicodeGlyphButton(id++, this.width - 90, this.height - 30, 80, 20, " " + I18n.format("schematica.gui.rotate", new Object[0]), "\u21bb", 2.0f);
        this.buttonList.add(this.btnRotate);
        this.btnMaterials = new GuiButton(id++, 10, this.height - 70, 80, 20, this.strMaterials);
        this.buttonList.add(this.btnMaterials);
        this.numericX.setEnabled(this.schematic != null);
        this.numericY.setEnabled(this.schematic != null);
        this.numericZ.setEnabled(this.schematic != null);
        this.btnUnload.enabled = (this.schematic != null);
        this.btnLayerMode.enabled = (this.schematic != null);
        this.nfLayer.setEnabled(this.schematic != null && this.schematic.layerMode != SchematicWorld.LayerMode.ALL);
        this.btnHide.enabled = (this.schematic != null);
        this.btnMove.enabled = (this.schematic != null);
        this.btnFlipDirection.enabled = (this.schematic != null);
        this.btnFlip.enabled = (this.schematic != null);
        this.btnRotateDirection.enabled = (this.schematic != null);
        this.btnRotate.enabled = (this.schematic != null);
        this.btnMaterials.enabled = (this.schematic != null);
        this.setMinMax(this.numericX);
        this.setMinMax(this.numericY);
        this.setMinMax(this.numericZ);
        if (this.schematic != null) {
            this.setPoint(this.numericX, this.numericY, this.numericZ, this.schematic.position);
        }
        this.nfLayer.setMinimum(0);
        this.nfLayer.setMaximum((this.schematic != null) ? (this.schematic.getHeight() - 1) : 0);
        if (this.schematic != null) {
            this.nfLayer.setValue(this.schematic.renderingLayer);
        }
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
            if (this.schematic == null) {
                return;
            }
            if (guiButton.id == this.numericX.id) {
                this.schematic.position.x = this.numericX.getValue();
                RenderSchematic.INSTANCE.refresh();
            }
            else if (guiButton.id == this.numericY.id) {
                this.schematic.position.y = this.numericY.getValue();
                RenderSchematic.INSTANCE.refresh();
            }
            else if (guiButton.id == this.numericZ.id) {
                this.schematic.position.z = this.numericZ.getValue();
                RenderSchematic.INSTANCE.refresh();
            }
            else if (guiButton.id == this.btnUnload.id) {
                Schematica.proxy.unloadSchematic();
                this.mc.displayGuiScreen(this.parentScreen);
            }
            else if (guiButton.id == this.btnLayerMode.id) {
                this.schematic.layerMode = SchematicWorld.LayerMode.next(this.schematic.layerMode);
                this.btnLayerMode.displayString = I18n.format(this.schematic.layerMode.name, new Object[0]);
                this.nfLayer.setEnabled(this.schematic.layerMode != SchematicWorld.LayerMode.ALL);
                RenderSchematic.INSTANCE.refresh();
            }
            else if (guiButton.id == this.nfLayer.id) {
                this.schematic.renderingLayer = this.nfLayer.getValue();
                RenderSchematic.INSTANCE.refresh();
            }
            else if (guiButton.id == this.btnHide.id) {
                this.btnHide.displayString = (this.schematic.toggleRendering() ? this.strHide : this.strShow);
            }
            else if (guiButton.id == this.btnMove.id) {
                ClientProxy.moveSchematicToPlayer(this.schematic);
                RenderSchematic.INSTANCE.refresh();
                this.setPoint(this.numericX, this.numericY, this.numericZ, this.schematic.position);
            }
            else if (guiButton.id == this.btnFlipDirection.id) {
                final EnumFacing[] values = EnumFacing.VALUES;
                ClientProxy.axisFlip = values[(ClientProxy.axisFlip.ordinal() + 2) % values.length];
                guiButton.displayString = I18n.format("schematica.gui." + ClientProxy.axisFlip.getName(), new Object[0]);
            }
            else if (guiButton.id == this.btnFlip.id) {
                if (FlipHelper.INSTANCE.flip(this.schematic, ClientProxy.axisFlip, isShiftKeyDown())) {
                    RenderSchematic.INSTANCE.refresh();
                    SchematicPrinter.INSTANCE.refresh();
                }
            }
            else if (guiButton.id == this.btnRotateDirection.id) {
                final EnumFacing[] values = EnumFacing.VALUES;
                ClientProxy.axisRotation = values[(ClientProxy.axisRotation.ordinal() + 1) % values.length];
                guiButton.displayString = I18n.format("schematica.gui." + ClientProxy.axisRotation.getName(), new Object[0]);
            }
            else if (guiButton.id == this.btnRotate.id) {
                if (RotationHelper.INSTANCE.rotate(this.schematic, ClientProxy.axisRotation, isShiftKeyDown())) {
                    this.setPoint(this.numericX, this.numericY, this.numericZ, this.schematic.position);
                    RenderSchematic.INSTANCE.refresh();
                    SchematicPrinter.INSTANCE.refresh();
                }
            }
            else if (guiButton.id == this.btnMaterials.id) {
                this.mc.displayGuiScreen((GuiScreen)new GuiSchematicMaterials(this));
            }
        }
    }
    
    public void handleKeyboardInput() throws IOException {
        super.handleKeyboardInput();
        if (this.btnFlip.enabled) {
            this.btnFlip.packedFGColour = (isShiftKeyDown() ? 16711680 : 0);
        }
        if (this.btnRotate.enabled) {
            this.btnRotate.packedFGColour = (isShiftKeyDown() ? 16711680 : 0);
        }
    }
    
    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        this.drawCenteredString(this.fontRenderer, this.strMoveSchematic, this.centerX, this.centerY - 45, 16777215);
        this.drawCenteredString(this.fontRenderer, this.strMaterials, 50, this.height - 85, 16777215);
        this.drawCenteredString(this.fontRenderer, this.strOperations, this.width - 50, this.height - 120, 16777215);
        this.drawString(this.fontRenderer, this.strX, this.centerX - 65, this.centerY - 24, 16777215);
        this.drawString(this.fontRenderer, this.strY, this.centerX - 65, this.centerY + 1, 16777215);
        this.drawString(this.fontRenderer, this.strZ, this.centerX - 65, this.centerY + 26, 16777215);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
}
