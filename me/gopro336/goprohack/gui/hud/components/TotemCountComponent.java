//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.gui.hud.components;

import me.gopro336.goprohack.util.render.RenderUtil;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import me.gopro336.goprohack.gui.hud.HudComponentItem;

public class TotemCountComponent extends HudComponentItem
{
    public TotemCountComponent() {
        super("TotemCount", 2.0f, 215.0f);
    }
    
    @Override
    public void render(final int p_MouseX, final int p_MouseY, final float p_PartialTicks) {
        super.render(p_MouseX, p_MouseY, p_PartialTicks);
        int l_TotemCount = 0;
        for (int i = 0; i < this.mc.player.inventoryContainer.getInventory().size(); ++i) {
            final ItemStack s = (ItemStack)this.mc.player.inventoryContainer.getInventory().get(i);
            if (!s.isEmpty()) {
                if (s.getItem() == Items.TOTEM_OF_UNDYING) {
                    ++l_TotemCount;
                }
            }
        }
        final String totemCount = ChatFormatting.GRAY + "Totems: " + ChatFormatting.WHITE + l_TotemCount;
        this.SetWidth(RenderUtil.getStringWidth(totemCount));
        this.SetHeight(RenderUtil.getStringHeight(totemCount));
        RenderUtil.drawStringWithShadow(totemCount, this.GetX(), this.GetY(), -1);
    }
}
