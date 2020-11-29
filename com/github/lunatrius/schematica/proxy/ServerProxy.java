//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package com.github.lunatrius.schematica.proxy;

import java.util.UUID;
import com.github.lunatrius.schematica.handler.ConfigurationHandler;
import net.minecraft.entity.player.EntityPlayer;
import java.io.IOException;
import com.github.lunatrius.schematica.reference.Reference;
import java.io.File;
import net.minecraft.command.ICommand;
import com.github.lunatrius.schematica.command.CommandSchematicaDownload;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import com.github.lunatrius.schematica.handler.PlayerHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraft.server.MinecraftServer;
import java.lang.ref.WeakReference;

public class ServerProxy extends CommonProxy
{
    private WeakReference<MinecraftServer> serverWeakReference;
    
    public ServerProxy() {
        this.serverWeakReference = null;
    }
    
    @Override
    public void init(final FMLInitializationEvent event) {
        super.init(event);
        MinecraftForge.EVENT_BUS.register((Object)PlayerHandler.INSTANCE);
    }
    
    @Override
    public void serverStarting(final FMLServerStartingEvent event) {
        super.serverStarting(event);
        event.registerServerCommand((ICommand)new CommandSchematicaDownload());
        this.serverWeakReference = new WeakReference<MinecraftServer>(event.getServer());
    }
    
    @Override
    public File getDataDirectory() {
        final MinecraftServer server = (this.serverWeakReference != null) ? this.serverWeakReference.get() : null;
        final File file = (server != null) ? server.getFile(".") : new File(".");
        try {
            return file.getCanonicalFile();
        }
        catch (IOException e) {
            Reference.logger.warn("Could not canonize path!", (Throwable)e);
            return file;
        }
    }
    
    @Override
    public boolean loadSchematic(final EntityPlayer player, final File directory, final String filename) {
        return false;
    }
    
    @Override
    public boolean isPlayerQuotaExceeded(final EntityPlayer player) {
        int spaceUsed = 0;
        File schematicDirectory = this.getPlayerSchematicDirectory(player, true);
        spaceUsed += this.getSpaceUsedByDirectory(schematicDirectory);
        schematicDirectory = this.getPlayerSchematicDirectory(player, false);
        spaceUsed += this.getSpaceUsedByDirectory(schematicDirectory);
        return spaceUsed / 1024 > ConfigurationHandler.playerQuotaKilobytes;
    }
    
    private int getSpaceUsedByDirectory(final File directory) {
        int spaceUsed = 0;
        if (directory == null || !directory.exists()) {
            return 0;
        }
        File[] files = directory.listFiles();
        if (files == null) {
            files = new File[0];
        }
        for (final File path : files) {
            spaceUsed += (int)path.length();
        }
        return spaceUsed;
    }
    
    @Override
    public File getPlayerSchematicDirectory(final EntityPlayer player, final boolean privateDirectory) {
        final UUID playerId = player.getUniqueID();
        if (playerId == null) {
            Reference.logger.warn("Unable to identify player {}", (Object)player.toString());
            return null;
        }
        final File playerDir = new File(ConfigurationHandler.schematicDirectory.getAbsolutePath(), playerId.toString());
        if (privateDirectory) {
            return new File(playerDir, "private");
        }
        return new File(playerDir, "public");
    }
}
