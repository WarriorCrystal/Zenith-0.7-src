//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.gui.hud.components;

import java.util.Iterator;
import me.gopro336.goprohack.managers.FontManager;
import me.gopro336.goprohack.util.render.RenderUtil;
import java.util.List;
import java.util.Collections;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import java.util.ArrayList;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.GlStateManager;
import java.text.DecimalFormat;
import me.gopro336.goprohack.modules.Value;
import me.gopro336.goprohack.gui.hud.HudComponentItem;

public class ArmorComponent extends HudComponentItem
{
    public final Value<Modes> Mode;
    DecimalFormat Formatter;
    
    public ArmorComponent() {
        super("Armor", 200.0f, 200.0f);
        this.Mode = new Value<Modes>("Mode", new String[] { "Mode" }, "Modes", Modes.Bars);
        this.Formatter = new DecimalFormat("#");
    }
    
    @Override
    public void render(final int mouseX, final int mouseY, final float partialTicks) {
        GlStateManager.pushMatrix();
        RenderHelper.enableGUIStandardItemLighting();
        super.render(mouseX, mouseY, partialTicks);
        final Iterator<ItemStack> l_Items = this.mc.player.getArmorInventoryList().iterator();
        final ArrayList<ItemStack> l_Stacks = new ArrayList<ItemStack>();
        while (l_Items.hasNext()) {
            final ItemStack l_Stack = l_Items.next();
            if (l_Stack != ItemStack.EMPTY && l_Stack.getItem() != Items.AIR) {
                l_Stacks.add(l_Stack);
            }
        }
        Collections.reverse(l_Stacks);
        switch (this.Mode.getValue()) {
            case Bars: {
                RenderUtil.drawRect(this.GetX(), this.GetY(), this.GetX() + this.GetWidth(), this.GetY() + this.GetHeight(), -1727263732);
                int l_Y = 0;
                for (int l_I = 0; l_I < l_Stacks.size(); ++l_I) {
                    final ItemStack l_Stack2 = l_Stacks.get(l_I);
                    float l_X = this.GetX() + 1.0f;
                    final float l_ArmorPct = (l_Stack2.getMaxDamage() - l_Stack2.getItemDamage()) / (float)l_Stack2.getMaxDamage() * 100.0f;
                    final float l_ArmorBarPct = Math.min(l_ArmorPct, 100.0f);
                    int l_ColorMin = -1717570715;
                    int l_ColorMax = -1726742784;
                    if (l_ArmorBarPct < 80.0f && l_ArmorPct > 30.0f) {
                        l_ColorMin = -1711294976;
                        l_ColorMax = -1711278336;
                    }
                    else if (l_ArmorBarPct < 30.0f) {
                        l_ColorMin = -1711341568;
                        l_ColorMax = -1713759718;
                    }
                    RenderUtil.drawGradientRect(l_X, this.GetY() + l_Y, l_X + this.GetWidth() * (l_ArmorBarPct / 100.0f), this.GetY() + l_Y + 15.0f, l_ColorMin, l_ColorMax);
                    this.mc.getRenderItem().renderItemAndEffectIntoGUI(l_Stack2, (int)l_X, (int)this.GetY() + l_Y);
                    final String l_Durability = String.format("%s %s / %s", this.Formatter.format(l_ArmorBarPct) + "%", l_Stack2.getMaxDamage() - l_Stack2.getItemDamage(), l_Stack2.getMaxDamage());
                    l_X = this.GetX() + 18.0f;
                    RenderUtil.drawStringWithShadow(l_Durability, l_X, this.GetY() + l_Y + 2.0f, 16777215);
                    l_Y += 15;
                }
                this.SetWidth(100.0f);
                this.SetHeight((float)l_Y);
                break;
            }
            case SimplePct: {
                float l_X2 = 0.0f;
                float l_TextX = 4.0f;
                for (int l_I2 = 0; l_I2 < l_Stacks.size(); ++l_I2) {
                    final ItemStack l_Stack3 = l_Stacks.get(l_I2);
                    this.mc.getRenderItem().renderItemAndEffectIntoGUI(l_Stack3, (int)(this.GetX() + l_X2), (int)this.GetY() + 10);
                    this.mc.getRenderItem().renderItemOverlays(this.mc.fontRenderer, l_Stack3, (int)(this.GetX() + l_X2), (int)this.GetY() + 10);
                    FontManager.Get().TWCenMt18.drawCenteredString(this.Formatter.format(GetPctFromStack(l_Stack3)), this.GetX() + l_TextX, this.GetY(), -1);
                    l_X2 += 20.0f;
                    if (l_I2 < l_Stacks.size() - 1) {
                        final float l_Pct = GetPctFromStack(l_Stacks.get(l_I2 + 1));
                        if (l_Pct == 100.0f) {
                            l_TextX += 22.0f;
                        }
                        else if (l_Pct >= 10.0) {
                            l_TextX += 21.0f;
                        }
                        else {
                            l_TextX += 25.0f;
                        }
                    }
                }
                this.SetWidth(75.0f);
                this.SetHeight(24.0f);
                break;
            }
        }
        RenderHelper.disableStandardItemLighting();
        GlStateManager.popMatrix();
    }
    
    public static float GetPctFromStack(final ItemStack p_Stack) {
        final float l_ArmorPct = (p_Stack.getMaxDamage() - p_Stack.getItemDamage()) / (float)p_Stack.getMaxDamage() * 100.0f;
        final float l_ArmorBarPct = Math.min(l_ArmorPct, 100.0f);
        return l_ArmorBarPct;
    }
    
    public enum Modes
    {
        Bars, 
        SimplePct;
    }
}
