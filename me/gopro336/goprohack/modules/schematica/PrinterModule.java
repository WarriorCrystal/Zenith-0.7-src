// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.modules.schematica;

import com.github.lunatrius.schematica.client.printer.SchematicPrinter;
import me.gopro336.goprohack.modules.Module;

public class PrinterModule extends Module
{
    public PrinterModule() {
        super("Printer", new String[] { "SchematicaPrinter" }, "Integration of Schematica's printer", "NONE", -1, ModuleType.WORLD);
    }
    
    @Override
    public void toggleNoSave() {
    }
    
    @Override
    public String getMetaData() {
        return SchematicPrinter.INSTANCE.IsStatgopro336y() ? "Statgopro336y" : "Printing";
    }
    
    @Override
    public void toggle() {
        super.toggle();
        SchematicPrinter.INSTANCE.setPrinting(this.isEnabled());
    }
}
