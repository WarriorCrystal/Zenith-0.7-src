//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package com.github.lunatrius.core.world.chunk;

import net.minecraft.util.math.BlockPos;
import java.util.Random;

public class ChunkHelper
{
    private static final Random RANDOM;
    
    public static boolean isSlimeChunk(final long seed, final BlockPos pos) {
        final int x = pos.getX() >> 4;
        final int z = pos.getZ() >> 4;
        ChunkHelper.RANDOM.setSeed(seed + x * x * 4987142 + x * 5947611 + z * z * 4392871L + z * 389711 ^ 0x3AD8025FL);
        return ChunkHelper.RANDOM.nextInt(10) == 0;
    }
    
    static {
        RANDOM = new Random();
    }
}
