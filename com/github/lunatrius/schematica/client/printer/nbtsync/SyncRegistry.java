//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package com.github.lunatrius.schematica.client.printer.nbtsync;

import net.minecraft.init.Blocks;
import net.minecraft.block.Block;
import java.util.HashMap;

public class SyncRegistry
{
    public static final SyncRegistry INSTANCE;
    private HashMap<Block, NBTSync> map;
    
    public SyncRegistry() {
        this.map = new HashMap<Block, NBTSync>();
    }
    
    public void register(final Block block, final NBTSync handler) {
        if (block == null || handler == null) {
            return;
        }
        this.map.put(block, handler);
    }
    
    public NBTSync getHandler(final Block block) {
        return this.map.get(block);
    }
    
    static {
        (INSTANCE = new SyncRegistry()).register(Blocks.COMMAND_BLOCK, new NBTSyncCommandBlock());
        SyncRegistry.INSTANCE.register(Blocks.STANDING_SIGN, new NBTSyncSign());
        SyncRegistry.INSTANCE.register(Blocks.WALL_SIGN, new NBTSyncSign());
    }
}
