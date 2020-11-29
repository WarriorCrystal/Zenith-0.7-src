//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package com.github.lunatrius.schematica.client.world.chunk;

import com.google.common.base.MoreObjects;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.chunk.EmptyChunk;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.world.World;
import java.util.Map;
import net.minecraft.world.chunk.Chunk;
import com.github.lunatrius.schematica.client.world.SchematicWorld;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.client.multiplayer.ChunkProviderClient;

@MethodsReturnNonnullByDefault
public class ChunkProviderSchematic extends ChunkProviderClient implements IChunkProvider
{
    private final SchematicWorld world;
    private final Chunk emptyChunk;
    private final Map<Long, ChunkSchematic> chunks;
    
    public ChunkProviderSchematic(final SchematicWorld world) {
        super((World)world);
        this.chunks = new ConcurrentHashMap<Long, ChunkSchematic>();
        this.world = world;
        this.emptyChunk = (Chunk)new EmptyChunk(world, 0, 0) {
            public boolean isEmpty() {
                return false;
            }
        };
    }
    
    private boolean chunkExists(final int x, final int z) {
        return x >= 0 && z >= 0 && x < this.world.getWidth() && z < this.world.getLength();
    }
    
    public Chunk getLoadedChunk(final int x, final int z) {
        if (!this.chunkExists(x, z)) {
            return this.emptyChunk;
        }
        final long key = ChunkPos.asLong(x, z);
        ChunkSchematic chunk = this.chunks.get(key);
        if (chunk == null) {
            chunk = new ChunkSchematic((World)this.world, x, z);
            this.chunks.put(key, chunk);
        }
        return chunk;
    }
    
    public Chunk provideChunk(final int x, final int z) {
        return this.getLoadedChunk(x, z);
    }
    
    public String makeString() {
        return "SchematicChunkCache";
    }
    
    public Chunk loadChunk(final int x, final int z) {
        return (Chunk)MoreObjects.firstNonNull((Object)this.getLoadedChunk(x, z), (Object)this.emptyChunk);
    }
    
    public void unloadChunk(final int x, final int z) {
    }
}
