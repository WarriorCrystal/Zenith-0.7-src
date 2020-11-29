//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.gui.hud.components;

import java.util.Iterator;
import java.util.List;
import java.util.Collections;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import java.util.ArrayList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.GlStateManager;
import java.text.DecimalFormat;
import me.gopro336.goprohack.util.render.RenderUtil;
import me.gopro336.goprohack.gui.hud.HudComponentItem;

public class PlayerFrameComponent extends HudComponentItem
{
    public PlayerFrameComponent() {
        super("PlayerHUD", 200.0f, 2.0f);
    }
    
    @Override
    public void render(final int p_MouseX, final int p_MouseY, final float p_PartialTicks) {
        super.render(p_MouseX, p_MouseY, p_PartialTicks);
        RenderUtil.drawRect(this.GetX(), this.GetY(), this.GetX() + this.GetWidth(), this.GetY() + this.GetHeight(), -1727263732);
        final float l_HealthPct = (this.mc.player.getHealth() + this.mc.player.getAbsorptionAmount()) / this.mc.player.getMaxHealth() * 100.0f;
        final float l_HealthBarPct = Math.min(l_HealthPct, 100.0f);
        final float l_HungerPct = (this.mc.player.getFoodStats().getFoodLevel() + this.mc.player.getFoodStats().getSaturationLevel()) / 20.0f * 100.0f;
        final float l_HungerBarPct = Math.min(l_HungerPct, 100.0f);
        final DecimalFormat l_Format = new DecimalFormat("#.#");
        GlStateManager.disableRescaleNormal();
        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GlStateManager.disableTexture2D();
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
        GuiInventory.drawEntityOnScreen((int)this.GetX() + 10, (int)this.GetY() + 30, 15, (float)p_MouseX, (float)p_MouseY, (EntityLivingBase)this.mc.player);
        GlStateManager.enableRescaleNormal();
        GlStateManager.enableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        RenderUtil.drawStringWithShadow(this.mc.getSession().getUsername(), this.GetX() + 20.0f, this.GetY() + 1.0f, 16777215);
        RenderUtil.drawGradientRect(this.GetX() + 20.0f, this.GetY() + 11.0f, this.GetX() + 20.0f + l_HealthBarPct, this.GetY() + 22.0f, -1717570715, -1726742784);
        RenderUtil.drawGradientRect(this.GetX() + 20.0f, this.GetY() + 22.0f, this.GetX() + 20.0f + l_HungerBarPct, this.GetY() + 33.0f, -1711690747, -1711690747);
        RenderUtil.drawStringWithShadow(String.format("(%s) %s / %s", l_Format.format(l_HealthPct) + "%", l_Format.format(this.mc.player.getHealth() + this.mc.player.getAbsorptionAmount()), l_Format.format(this.mc.player.getMaxHealth())), this.GetX() + 20.0f, this.GetY() + 11.0f, 16777215);
        RenderUtil.drawStringWithShadow(String.format("(%s) %s / %s", l_Format.format(l_HungerPct) + "%", l_Format.format(this.mc.player.getFoodStats().getFoodLevel() + this.mc.player.getFoodStats().getSaturationLevel()), "20"), this.GetX() + 20.0f, this.GetY() + 22.0f, 16777215);
        final Iterator<ItemStack> l_Items = this.mc.player.getArmorInventoryList().iterator();
        final ArrayList<ItemStack> l_Stacks = new ArrayList<ItemStack>();
        while (l_Items.hasNext()) {
            final ItemStack l_Stack = l_Items.next();
            if (l_Stack != ItemStack.EMPTY && l_Stack.getItem() != Items.AIR) {
                l_Stacks.add(l_Stack);
            }
        }
        Collections.reverse(l_Stacks);
        for (int l_I = 0; l_I < l_Stacks.size(); ++l_I) {
            final ItemStack l_Stack2 = l_Stacks.get(l_I);
            final int l_X = (int)(this.GetX() + 5.0f) + l_I * 15;
            final int l_Y = (int)(this.GetY() + 35.0f);
            this.mc.getRenderItem().renderItemAndEffectIntoGUI(l_Stack2, l_X, l_Y);
            this.mc.getRenderItem().renderItemOverlays(this.mc.fontRenderer, l_Stack2, l_X, l_Y);
        }
        this.SetWidth(120.0f);
        this.SetHeight(37.0f);
    }
}
