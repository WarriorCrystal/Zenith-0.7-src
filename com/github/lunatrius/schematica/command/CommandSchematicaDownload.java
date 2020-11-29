//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package com.github.lunatrius.schematica.command;

import com.github.lunatrius.schematica.api.ISchematic;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import com.github.lunatrius.schematica.network.transfer.SchematicTransfer;
import com.github.lunatrius.schematica.handler.DownloadHandler;
import com.github.lunatrius.schematica.world.schematic.SchematicFormat;
import com.github.lunatrius.schematica.reference.Reference;
import com.github.lunatrius.core.util.FileUtils;
import net.minecraft.command.CommandException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.command.WrongUsageException;
import java.io.File;
import java.util.Collection;
import java.util.ArrayList;
import java.io.FileFilter;
import com.github.lunatrius.schematica.Schematica;
import java.util.Collections;
import net.minecraft.entity.player.EntityPlayer;
import java.util.List;
import net.minecraft.util.math.BlockPos;
import net.minecraft.server.MinecraftServer;
import net.minecraft.command.ICommandSender;
import com.github.lunatrius.schematica.util.FileFilterSchematic;
import javax.annotation.ParametersAreNonnullByDefault;
import mcp.MethodsReturnNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class CommandSchematicaDownload extends CommandSchematicaBase
{
    private static final FileFilterSchematic FILE_FILTER_SCHEMATIC;
    
    public String getName() {
        return "schematicaDownload";
    }
    
    public String getUsage(final ICommandSender sender) {
        return "schematica.command.download.usage";
    }
    
    public List<String> getTabCompletions(final MinecraftServer server, final ICommandSender sender, final String[] args, final BlockPos pos) {
        if (!(sender instanceof EntityPlayer)) {
            return Collections.emptyList();
        }
        final File directory = Schematica.proxy.getPlayerSchematicDirectory((EntityPlayer)sender, true);
        final File[] files = directory.listFiles(CommandSchematicaDownload.FILE_FILTER_SCHEMATIC);
        if (files != null) {
            final List<String> filenames = new ArrayList<String>();
            for (final File file : files) {
                filenames.add(file.getName());
            }
            return (List<String>)getListOfStringsMatchingLastWord(args, (Collection)filenames);
        }
        return Collections.emptyList();
    }
    
    public void execute(final MinecraftServer server, final ICommandSender sender, final String[] args) throws CommandException {
        if (args.length < 1) {
            throw new WrongUsageException(this.getUsage(sender), new Object[0]);
        }
        if (!(sender instanceof EntityPlayerMP)) {
            throw new CommandException("schematica.command.save.playersOnly", new Object[0]);
        }
        final String filename = String.join(" ", (CharSequence[])args);
        final EntityPlayerMP player = (EntityPlayerMP)sender;
        final File directory = Schematica.proxy.getPlayerSchematicDirectory((EntityPlayer)player, true);
        if (!FileUtils.contains(directory, filename)) {
            Reference.logger.error("{} has tried to download the file {}", (Object)player.getName(), (Object)filename);
            throw new CommandException("schematica.command.download.downloadFail", new Object[0]);
        }
        final ISchematic schematic = SchematicFormat.readFromFile(directory, filename);
        if (schematic != null) {
            DownloadHandler.INSTANCE.transferMap.put(player, new SchematicTransfer(schematic, filename));
            sender.sendMessage((ITextComponent)new TextComponentTranslation("schematica.command.download.started", new Object[] { filename }));
            return;
        }
        throw new CommandException("schematica.command.download.downloadFail", new Object[0]);
    }
    
    static {
        FILE_FILTER_SCHEMATIC = new FileFilterSchematic(false);
    }
}
