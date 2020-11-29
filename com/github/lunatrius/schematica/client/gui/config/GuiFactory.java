// 
// Decompiled by Procyon v0.5.36
// 

package com.github.lunatrius.schematica.client.gui.config;

import com.github.lunatrius.schematica.handler.ConfigurationHandler;
import com.github.lunatrius.core.client.gui.config.GuiConfigComplex;
import java.util.Set;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.client.IModGuiFactory;

public class GuiFactory implements IModGuiFactory
{
    public void initialize(final Minecraft minecraftInstance) {
    }
    
    public boolean hasConfigGui() {
        return true;
    }
    
    public GuiScreen createConfigGui(final GuiScreen parentScreen) {
        return (GuiScreen)new GuiModConfig(parentScreen);
    }
    
    public Set<IModGuiFactory.RuntimeOptionCategoryElement> runtimeGuiCategories() {
        return null;
    }
    
    public static class GuiModConfig extends GuiConfigComplex
    {
        public GuiModConfig(final GuiScreen guiScreen) {
            super(guiScreen, "schematica", ConfigurationHandler.configuration, "schematica.config");
        }
    }
}
