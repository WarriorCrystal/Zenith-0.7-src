//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package com.github.lunatrius.core.client.gui;

import java.io.IOException;
import java.util.Iterator;
import net.minecraft.client.gui.GuiButton;
import java.util.ArrayList;
import net.minecraft.client.gui.GuiTextField;
import java.util.List;
import net.minecraft.client.gui.GuiScreen;

public class GuiScreenBase extends GuiScreen
{
    protected final GuiScreen parentScreen;
    protected List<GuiTextField> textFields;
    
    public GuiScreenBase() {
        this(null);
    }
    
    public GuiScreenBase(final GuiScreen parentScreen) {
        this.textFields = new ArrayList<GuiTextField>();
        this.parentScreen = parentScreen;
    }
    
    public void initGui() {
        this.buttonList.clear();
        this.textFields.clear();
    }
    
    protected void mouseClicked(final int mouseX, final int mouseY, final int mouseEvent) throws IOException {
        for (final GuiButton button : this.buttonList) {
            if (button instanceof GuiNumericField) {
                final GuiNumericField numericField = (GuiNumericField)button;
                numericField.mouseClicked(mouseX, mouseY, mouseEvent);
            }
        }
        for (final GuiTextField textField : this.textFields) {
            textField.mouseClicked(mouseX, mouseY, mouseEvent);
        }
        super.mouseClicked(mouseX, mouseY, mouseEvent);
    }
    
    protected void keyTyped(final char character, final int code) throws IOException {
        if (code == 1) {
            this.mc.displayGuiScreen(this.parentScreen);
            return;
        }
        for (final GuiButton button : this.buttonList) {
            if (button instanceof GuiNumericField) {
                final GuiNumericField numericField = (GuiNumericField)button;
                numericField.keyTyped(character, code);
                if (!numericField.isFocused()) {
                    continue;
                }
                this.actionPerformed((GuiButton)numericField);
            }
        }
        for (final GuiTextField textField : this.textFields) {
            textField.textboxKeyTyped(character, code);
        }
        super.keyTyped(character, code);
    }
    
    public void updateScreen() {
        super.updateScreen();
        for (final GuiButton button : this.buttonList) {
            if (button instanceof GuiNumericField) {
                final GuiNumericField numericField = (GuiNumericField)button;
                numericField.updateCursorCounter();
            }
        }
        for (final GuiTextField textField : this.textFields) {
            textField.updateCursorCounter();
        }
    }
    
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        for (final GuiTextField textField : this.textFields) {
            textField.drawTextBox();
        }
    }
}
