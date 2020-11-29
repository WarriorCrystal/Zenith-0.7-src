// 
// Decompiled by Procyon v0.5.36
// 

package com.github.lunatrius.core.client.gui.config;

import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.fml.client.config.IConfigElement;
import java.util.List;
import net.minecraftforge.common.config.Configuration;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.client.config.GuiConfig;

@Deprecated
public abstract class GuiConfigSimple extends GuiConfig
{
    public GuiConfigSimple(final GuiScreen guiScreen, final String modID, final Configuration configuration, final String categoryName) {
        super(guiScreen, (List)getConfigElements(configuration, categoryName), modID, false, false, GuiConfig.getAbridgedConfigPath(configuration.toString()));
    }
    
    private static List<IConfigElement> getConfigElements(final Configuration configuration, final String categoryName) {
        final ConfigElement element = new ConfigElement(configuration.getCategory(categoryName));
        return (List<IConfigElement>)element.getChildElements();
    }
}
