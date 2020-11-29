//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package com.github.lunatrius.schematica.world;

import com.github.lunatrius.schematica.world.storage.SaveHandlerSchematic;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.WorldType;
import net.minecraft.world.GameType;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.profiler.Profiler;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.storage.WorldInfo;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraft.world.World;

public class WorldDummy extends World
{
    private static WorldDummy instance;
    
    protected WorldDummy(final ISaveHandler saveHandler, final WorldInfo worldInfo, final WorldProvider worldProvider, final Profiler profiler, final boolean client) {
        super(saveHandler, worldInfo, worldProvider, profiler, client);
    }
    
    protected IChunkProvider createChunkProvider() {
        return null;
    }
    
    protected boolean isChunkLoaded(final int x, final int z, final boolean allowEmpty) {
        return false;
    }
    
    public static WorldDummy instance() {
        if (WorldDummy.instance == null) {
            final WorldSettings worldSettings = new WorldSettings(0L, GameType.CREATIVE, false, false, WorldType.FLAT);
            final WorldInfo worldInfo = new WorldInfo(worldSettings, "FakeWorld");
            WorldDummy.instance = new WorldDummy((ISaveHandler)new SaveHandlerSchematic(), worldInfo, new WorldProviderSchematic(), new Profiler(), false);
        }
        return WorldDummy.instance;
    }
}
