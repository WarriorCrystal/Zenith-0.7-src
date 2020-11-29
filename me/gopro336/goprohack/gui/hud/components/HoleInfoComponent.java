//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.gui.hud.components;

import net.minecraft.block.Block;
import net.minecraft.util.math.Vec3d;
import me.gopro336.goprohack.util.render.RenderUtil;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.entity.Entity;
import me.gopro336.goprohack.util.MathUtil;
import me.gopro336.goprohack.gui.hud.HudComponentItem;

public class HoleInfoComponent extends HudComponentItem
{
    public HoleInfoComponent() {
        super("HoleInfo", 2.0f, 170.0f);
    }
    
    @Override
    public void render(final int p_MouseX, final int p_MouseY, final float p_PartialTicks) {
        super.render(p_MouseX, p_MouseY, p_PartialTicks);
        this.SetWidth(100.0f);
        this.SetHeight(20.0f);
        String l_Addon = "None";
        final Vec3d l_PlayerPos = MathUtil.interpolateEntity((Entity)this.mc.player, this.mc.getRenderPartialTicks());
        final BlockPos l_BlockPos = new BlockPos(l_PlayerPos.x, l_PlayerPos.y, l_PlayerPos.z);
        final BlockPos[] l_Positions = { l_BlockPos.north(), l_BlockPos.south(), l_BlockPos.east(), l_BlockPos.west() };
        int l_Counter = 0;
        boolean l_AllBedrock = true;
        for (final BlockPos l_Pos : l_Positions) {
            final Block l_Block = this.mc.world.getBlockState(l_Pos).getBlock();
            if (l_Block == Blocks.AIR) {
                break;
            }
            if (l_Block != Blocks.BEDROCK) {
                l_AllBedrock = false;
            }
            if (l_Block == Blocks.OBSIDIAN || l_Block == Blocks.BEDROCK) {
                ++l_Counter;
            }
        }
        if (l_Counter == 4) {
            if (l_AllBedrock) {
                l_Addon = ChatFormatting.GREEN + "Safe";
            }
            else {
                l_Addon = ChatFormatting.YELLOW + "Unsafe";
            }
        }
        RenderUtil.drawStringWithShadow(String.format("Hole: %s", l_Addon), this.GetX(), this.GetY(), 16777215);
    }
}
