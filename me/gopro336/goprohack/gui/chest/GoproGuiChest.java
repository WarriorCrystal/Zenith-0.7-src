//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.gui.chest;

import java.io.IOException;
import net.minecraft.client.gui.GuiButton;
import me.gopro336.goprohack.managers.ModuleManager;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.Minecraft;
import net.minecraft.inventory.IInventory;
import me.gopro336.goprohack.modules.misc.ChestStealerModule;
import net.minecraft.client.gui.inventory.GuiChest;

public class GoproGuiChest extends GuiChest
{
    private ChestStealerModule ChestStealer;
    private boolean WasEnabledByGUI;
    
    public GoproGuiChest(final IInventory upperInv, final IInventory lowerInv) {
        super(upperInv, lowerInv);
        this.WasEnabledByGUI = false;
        this.mc = Minecraft.getMinecraft();
        final ScaledResolution l_Res = new ScaledResolution(this.mc);
        this.setWorldAndResolution(this.mc, l_Res.getScaledWidth(), l_Res.getScaledHeight());
    }
    
    public void initGui() {
        super.initGui();
        this.ChestStealer = (ChestStealerModule)ModuleManager.Get().GetMod(ChestStealerModule.class);
        this.buttonList.add(new GuiButton(1337, this.width / 2 + 100, this.height / 2 - this.ySize + 110, 50, 20, "Steal"));
        this.buttonList.add(new GuiButton(1338, this.width / 2 + 100, this.height / 2 - this.ySize + 130, 50, 20, "Store"));
        this.buttonList.add(new GuiButton(1339, this.width / 2 + 100, this.height / 2 - this.ySize + 150, 50, 20, "Drop"));
    }
    
    protected void actionPerformed(final GuiButton button) throws IOException {
        super.actionPerformed(button);
        switch (button.id) {
            case 1337: {
                this.ChestStealer.Mode.setValue(ChestStealerModule.Modes.Steal);
                if (!this.ChestStealer.isEnabled()) {
                    this.WasEnabledByGUI = true;
                    this.ChestStealer.toggle();
                    break;
                }
                break;
            }
            case 1338: {
                this.ChestStealer.Mode.setValue(ChestStealerModule.Modes.Store);
                if (!this.ChestStealer.isEnabled()) {
                    this.WasEnabledByGUI = true;
                    this.ChestStealer.toggle();
                    break;
                }
                break;
            }
            case 1339: {
                this.ChestStealer.Mode.setValue(ChestStealerModule.Modes.Drop);
                if (!this.ChestStealer.isEnabled()) {
                    this.WasEnabledByGUI = true;
                    this.ChestStealer.toggle();
                    break;
                }
                break;
            }
        }
    }
    
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
    
    public void onGuiClosed() {
        super.onGuiClosed();
        if (this.WasEnabledByGUI && this.ChestStealer.isEnabled()) {
            this.ChestStealer.toggle();
        }
    }
}
