//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.gui.hud.components;

import java.util.Iterator;
import java.util.List;
import com.github.lunatrius.core.client.gui.GuiHelper;
import com.github.lunatrius.schematica.util.ItemStackSortType;
import net.minecraft.world.World;
import net.minecraft.entity.player.EntityPlayer;
import com.github.lunatrius.schematica.client.util.BlockList;
import com.github.lunatrius.schematica.client.printer.SchematicPrinter;
import me.gopro336.goprohack.util.render.RenderUtil;
import org.lwjgl.opengl.GL11;
import me.gopro336.goprohack.gui.hud.HudComponentItem;

public class SchematicaMaterialInfoComponent extends HudComponentItem
{
    public SchematicaMaterialInfoComponent() {
        super("SchematicaMaterialInfo", 300.0f, 300.0f);
    }
    
    @Override
    public void render(final int p_MouseX, final int p_MouseY, final float p_PartialTicks) {
        super.render(p_MouseX, p_MouseY, p_PartialTicks);
        GL11.glPushMatrix();
        RenderUtil.drawRect(this.GetX(), this.GetY(), this.GetX() + this.GetWidth(), this.GetY() + this.GetHeight(), 1963986960);
        if (SchematicPrinter.INSTANCE.getSchematic() == null) {
            final String l_String = "No Schematic loaded";
            RenderUtil.drawStringWithShadow("No Schematic loaded", this.GetX(), this.GetY(), -1);
            this.SetWidth(RenderUtil.getStringWidth("No Schematic loaded"));
            this.SetHeight(RenderUtil.getStringHeight("No Schematic loaded"));
            GL11.glPopMatrix();
            return;
        }
        final List<BlockList.WrappedItemStack> blockList = new BlockList().getList((EntityPlayer)this.mc.player, SchematicPrinter.INSTANCE.getSchematic(), (World)this.mc.world);
        ItemStackSortType.fromString("SIZE_DESC").sort(blockList);
        float l_Height = 0.0f;
        float l_MaxWidth = 0.0f;
        for (final BlockList.WrappedItemStack l_Stack : blockList) {
            final String l_String2 = String.format("%s: %s", l_Stack.getItemStackDisplayName(), l_Stack.getFormattedAmount(), l_Stack.getFormattedAmount());
            GuiHelper.drawItemStack(l_Stack.itemStack, (int)this.GetX(), (int)(this.GetY() + l_Height));
            final float l_Width = RenderUtil.drawStringWithShadow(l_String2, this.GetX() + 20.0f, this.GetY() + l_Height + 4.0f, -1) + 22.0f;
            if (l_Width >= l_MaxWidth) {
                l_MaxWidth = l_Width;
            }
            l_Height += 16.0f;
        }
        this.SetWidth(l_MaxWidth);
        this.SetHeight(l_Height);
        GL11.glPopMatrix();
    }
}
