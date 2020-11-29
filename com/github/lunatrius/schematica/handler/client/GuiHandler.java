// 
// Decompiled by Procyon v0.5.36
// 

package com.github.lunatrius.schematica.handler.client;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiEditSign;
import com.github.lunatrius.schematica.client.printer.SchematicPrinter;
import net.minecraftforge.client.event.GuiOpenEvent;

public class GuiHandler
{
    public static final GuiHandler INSTANCE;
    
    @SubscribeEvent
    public void onGuiOpen(final GuiOpenEvent event) {
        if (SchematicPrinter.INSTANCE.isPrinting() && event.getGui() instanceof GuiEditSign) {
            event.setGui((GuiScreen)null);
        }
    }
    
    static {
        INSTANCE = new GuiHandler();
    }
}
