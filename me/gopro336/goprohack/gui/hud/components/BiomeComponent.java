//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.gui.hud.components;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.util.math.BlockPos;
import me.gopro336.goprohack.util.render.RenderUtil;
import me.gopro336.goprohack.gui.hud.HudComponentItem;

public class BiomeComponent extends HudComponentItem
{
    public BiomeComponent() {
        super("Biome", 2.0f, 95.0f);
    }
    
    @Override
    public void render(final int p_MouseX, final int p_MouseY, final float p_PartialTicks) {
        super.render(p_MouseX, p_MouseY, p_PartialTicks);
        final BlockPos pos = this.mc.player.getPosition();
        final Chunk chunk = this.mc.world.getChunk(pos);
        final Biome biome = chunk.getBiome(pos, this.mc.world.getBiomeProvider());
        this.SetWidth(RenderUtil.getStringWidth(biome.getBiomeName()));
        this.SetHeight(RenderUtil.getStringHeight(biome.getBiomeName()));
        RenderUtil.drawStringWithShadow(biome.getBiomeName(), this.GetX(), this.GetY(), -1);
    }
}
