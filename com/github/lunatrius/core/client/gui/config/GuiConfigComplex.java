// 
// Decompiled by Procyon v0.5.36
// 

package com.github.lunatrius.core.client.gui.config;

import net.minecraftforge.common.config.ConfigCategory;
import java.util.Iterator;
import net.minecraftforge.common.config.ConfigElement;
import java.util.ArrayList;
import net.minecraftforge.fml.client.config.IConfigElement;
import java.util.List;
import net.minecraftforge.common.config.Configuration;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.client.config.GuiConfig;

@Deprecated
public abstract class GuiConfigComplex extends GuiConfig
{
    public GuiConfigComplex(final GuiScreen parent, final String modID, final Configuration configuration, final String langPrefix) {
        super(parent, (List)getConfigElements(configuration, langPrefix), modID, false, false, GuiConfig.getAbridgedConfigPath(configuration.toString()));
    }
    
    private static List<IConfigElement> getConfigElements(final Configuration configuration, final String langPrefix) {
        final List<IConfigElement> elements = new ArrayList<IConfigElement>();
        for (final String name : configuration.getCategoryNames()) {
            final ConfigCategory category = configuration.getCategory(name).setLanguageKey(langPrefix + ".category." + name);
            if (category.parent == null) {
                elements.add((IConfigElement)new ConfigElement(category));
            }
        }
        return elements;
    }
}
