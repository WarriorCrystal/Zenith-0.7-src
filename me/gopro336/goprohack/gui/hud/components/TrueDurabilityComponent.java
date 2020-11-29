//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.gui.hud.components;

import net.minecraft.item.ItemStack;
import me.gopro336.goprohack.util.render.RenderUtil;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemTool;
import me.gopro336.goprohack.gui.hud.HudComponentItem;

public class TrueDurabilityComponent extends HudComponentItem
{
    public TrueDurabilityComponent() {
        super("TrueDurability", 2.0f, 260.0f);
    }
    
    @Override
    public void render(final int p_MouseX, final int p_MouseY, final float p_PartialTicks) {
        super.render(p_MouseX, p_MouseY, p_PartialTicks);
        final ItemStack l_Stack = this.mc.player.getHeldItemMainhand();
        if (!l_Stack.isEmpty() && (l_Stack.getItem() instanceof ItemTool || l_Stack.getItem() instanceof ItemArmor || l_Stack.getItem() instanceof ItemSword)) {
            final String l_Durability = ChatFormatting.WHITE + "Durability: " + ChatFormatting.GREEN + (l_Stack.getMaxDamage() - l_Stack.getItemDamage());
            RenderUtil.drawStringWithShadow(l_Durability, this.GetX(), this.GetY(), -1);
            this.SetWidth(RenderUtil.getStringWidth(l_Durability));
            this.SetHeight(RenderUtil.getStringHeight(l_Durability));
        }
    }
}
