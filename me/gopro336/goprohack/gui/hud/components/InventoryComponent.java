//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.gui.hud.components;

import net.minecraft.item.ItemStack;
import me.gopro336.goprohack.util.render.RenderUtil;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.GlStateManager;
import me.gopro336.goprohack.modules.Value;
import me.gopro336.goprohack.gui.hud.HudComponentItem;

public class InventoryComponent extends HudComponentItem
{
    public final Value<Boolean> ShowHotbar;
    public final Value<Boolean> ShowXCarry;
    public final Value<Boolean> Background;
    public final Value<Float> Scale;
    
    public InventoryComponent() {
        super("Inventory", 2.0f, 15.0f);
        this.ShowHotbar = new Value<Boolean>("ShowHotbar", new String[] { "" }, "Displays the hotbar", false);
        this.ShowXCarry = new Value<Boolean>("ShowXCarry", new String[] { "" }, "Displays the crafting inventory", true);
        this.Background = new Value<Boolean>("Background", new String[] { "" }, "Displays the Background", false);
        this.Scale = new Value<Float>("Scale", new String[] { "" }, "Allows you to modify the scale", 1.0f, 0.0f, 10.0f, 1.0f);
    }
    
    @Override
    public void render(final int p_MouseX, final int p_MouseY, final float p_PartialTicks) {
        super.render(p_MouseX, p_MouseY, p_PartialTicks);
        GlStateManager.pushMatrix();
        RenderHelper.enableGUIStandardItemLighting();
        GlStateManager.scale((float)this.Scale.getValue(), (float)this.Scale.getValue(), (float)this.Scale.getValue());
        if (this.Background.getValue()) {
            RenderUtil.drawRect(this.GetX(), this.GetY(), this.GetX() + this.GetWidth(), this.GetY() + this.GetHeight(), 1963986960);
        }
        for (int i = 0; i < 27; ++i) {
            final ItemStack itemStack = (ItemStack)this.mc.player.inventory.mainInventory.get(i + 9);
            final int offsetX = (int)this.GetX() + i % 9 * 16;
            final int offsetY = (int)this.GetY() + i / 9 * 16;
            this.mc.getRenderItem().renderItemAndEffectIntoGUI(itemStack, offsetX, offsetY);
            this.mc.getRenderItem().renderItemOverlayIntoGUI(this.mc.fontRenderer, itemStack, offsetX, offsetY, (String)null);
        }
        if (this.ShowHotbar.getValue()) {
            for (int i = 0; i < 9; ++i) {
                final ItemStack itemStack = (ItemStack)this.mc.player.inventory.mainInventory.get(i);
                final int offsetX = (int)this.GetX() + i % 9 * 16;
                final int offsetY = (int)this.GetY() + 48;
                this.mc.getRenderItem().renderItemAndEffectIntoGUI(itemStack, offsetX, offsetY);
                this.mc.getRenderItem().renderItemOverlayIntoGUI(this.mc.fontRenderer, itemStack, offsetX, offsetY, (String)null);
            }
        }
        if (this.ShowXCarry.getValue()) {
            if (this.Background.getValue()) {
                RenderUtil.drawRect(this.GetX() + this.GetWidth(), this.GetY(), this.GetX() + this.GetWidth() + 32.0f, this.GetY() + 32.0f, 1963986960);
            }
            for (int i = 1; i < 5; ++i) {
                final ItemStack itemStack = (ItemStack)this.mc.player.inventoryContainer.getInventory().get(i);
                int offsetX = (int)this.GetX();
                int offsetY = (int)this.GetY();
                switch (i) {
                    case 1:
                    case 2: {
                        offsetX += 128 + i * 16;
                        break;
                    }
                    case 3:
                    case 4: {
                        offsetX += 128 + (i - 2) * 16;
                        offsetY += 16;
                        break;
                    }
                }
                this.mc.getRenderItem().renderItemAndEffectIntoGUI(itemStack, offsetX, offsetY);
                this.mc.getRenderItem().renderItemOverlayIntoGUI(this.mc.fontRenderer, itemStack, offsetX, offsetY, (String)null);
            }
        }
        this.SetWidth(144.0f);
        this.SetHeight((float)(16 * (this.ShowHotbar.getValue() ? 4 : 3)));
        RenderHelper.disableStandardItemLighting();
        this.mc.getRenderItem().zLevel = 0.0f;
        GlStateManager.popMatrix();
    }
}
