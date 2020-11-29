//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.gui.hud.components;

import net.minecraft.tileentity.TileEntity;
import me.gopro336.goprohack.util.render.RenderUtil;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.tileentity.TileEntityChest;
import me.gopro336.goprohack.gui.hud.HudComponentItem;

public class ChestCountComponent extends HudComponentItem
{
    public ChestCountComponent() {
        super("ChestCount", 2.0f, 245.0f);
    }
    
    @Override
    public void render(final int p_MouseX, final int p_MouseY, final float p_PartialTicks) {
        super.render(p_MouseX, p_MouseY, p_PartialTicks);
        final long chest = this.mc.world.loadedTileEntityList.stream().filter(e -> e instanceof TileEntityChest).count();
        final String l_Chests = ChatFormatting.GRAY + "Chests: " + ChatFormatting.WHITE + chest;
        RenderUtil.drawStringWithShadow(l_Chests, this.GetX(), this.GetY(), -1);
        this.SetWidth(RenderUtil.getStringWidth(l_Chests));
        this.SetHeight(RenderUtil.getStringHeight(l_Chests));
    }
}
