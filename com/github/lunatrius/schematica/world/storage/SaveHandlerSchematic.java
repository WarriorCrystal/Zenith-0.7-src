//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package com.github.lunatrius.schematica.world.storage;

import net.minecraft.world.gen.structure.template.TemplateManager;
import java.io.File;
import net.minecraft.world.storage.IPlayerFileData;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.chunk.storage.IChunkLoader;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.MinecraftException;
import net.minecraft.world.storage.WorldInfo;
import javax.annotation.ParametersAreNonnullByDefault;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.world.storage.ISaveHandler;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class SaveHandlerSchematic implements ISaveHandler
{
    public WorldInfo loadWorldInfo() {
        return null;
    }
    
    public void checkSessionLock() throws MinecraftException {
    }
    
    public IChunkLoader getChunkLoader(final WorldProvider provider) {
        return null;
    }
    
    public void saveWorldInfoWithPlayer(final WorldInfo info, final NBTTagCompound compound) {
    }
    
    public void saveWorldInfo(final WorldInfo info) {
    }
    
    public IPlayerFileData getPlayerNBTManager() {
        return null;
    }
    
    public void flush() {
    }
    
    public File getWorldDirectory() {
        return null;
    }
    
    public File getMapFileFromName(final String name) {
        return null;
    }
    
    public TemplateManager getStructureTemplateManager() {
        return null;
    }
}
