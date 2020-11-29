//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package com.github.lunatrius.schematica.command;

import java.util.Iterator;
import java.io.File;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextComponentString;
import org.apache.commons.io.FilenameUtils;
import com.github.lunatrius.core.util.FileUtils;
import java.io.FileFilter;
import com.github.lunatrius.schematica.reference.Reference;
import com.github.lunatrius.schematica.Schematica;
import net.minecraft.util.text.ITextComponent;
import java.util.LinkedList;
import net.minecraft.command.WrongUsageException;
import net.minecraft.command.CommandException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.command.ICommandSender;
import com.github.lunatrius.schematica.util.FileFilterSchematic;
import javax.annotation.ParametersAreNonnullByDefault;
import mcp.MethodsReturnNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class CommandSchematicaList extends CommandSchematicaBase
{
    private static final FileFilterSchematic FILE_FILTER_SCHEMATIC;
    
    public String getName() {
        return "schematicaList";
    }
    
    public String getUsage(final ICommandSender sender) {
        return "schematica.command.list.usage";
    }
    
    public void execute(final MinecraftServer server, final ICommandSender sender, final String[] args) throws CommandException {
        if (!(sender instanceof EntityPlayer)) {
            throw new CommandException("schematica.command.save.playersOnly", new Object[0]);
        }
        int page = 0;
        try {
            if (args.length > 0) {
                page = Integer.parseInt(args[0]) - 1;
                if (page < 0) {
                    page = 0;
                }
            }
        }
        catch (NumberFormatException e) {
            throw new WrongUsageException(this.getUsage(sender), new Object[0]);
        }
        final EntityPlayer player = (EntityPlayer)sender;
        final int pageSize = 9;
        final int pageStart = page * 9;
        final int pageEnd = pageStart + 9;
        int currentFile = 0;
        final LinkedList<ITextComponent> componentsToSend = new LinkedList<ITextComponent>();
        final File schematicDirectory = Schematica.proxy.getPlayerSchematicDirectory(player, true);
        if (schematicDirectory == null) {
            Reference.logger.warn("Unable to determine the schematic directory for player {}", (Object)player);
            throw new CommandException("schematica.command.save.playerSchematicDirUnavailable", new Object[0]);
        }
        if (!schematicDirectory.exists() && !schematicDirectory.mkdirs()) {
            Reference.logger.warn("Could not create player schematic directory {}", (Object)schematicDirectory.getAbsolutePath());
            throw new CommandException("schematica.command.save.playerSchematicDirUnavailable", new Object[0]);
        }
        final File[] listFiles;
        final File[] files = listFiles = schematicDirectory.listFiles(CommandSchematicaList.FILE_FILTER_SCHEMATIC);
        for (final File path : listFiles) {
            if (currentFile >= pageStart && currentFile < pageEnd) {
                final String fileName = path.getName();
                final ITextComponent chatComponent = (ITextComponent)new TextComponentString(String.format("%2d (%s): %s [", currentFile + 1, FileUtils.humanReadableByteCount(path.length()), FilenameUtils.removeExtension(fileName)));
                final String removeCommand = String.format("/%s %s", "schematicaRemove", fileName);
                final ITextComponent removeLink = this.withStyle((ITextComponent)new TextComponentTranslation("schematica.command.list.remove", new Object[0]), TextFormatting.RED, removeCommand);
                chatComponent.appendSibling(removeLink);
                chatComponent.appendText("][");
                final String downloadCommand = String.format("/%s %s", "schematicaDownload", fileName);
                final ITextComponent downloadLink = this.withStyle((ITextComponent)new TextComponentTranslation("schematica.command.list.download", new Object[0]), TextFormatting.GREEN, downloadCommand);
                chatComponent.appendSibling(downloadLink);
                chatComponent.appendText("]");
                componentsToSend.add(chatComponent);
            }
            ++currentFile;
        }
        if (currentFile == 0) {
            sender.sendMessage((ITextComponent)new TextComponentTranslation("schematica.command.list.noSchematics", new Object[0]));
            return;
        }
        final int totalPages = (currentFile - 1) / 9;
        if (page > totalPages) {
            throw new CommandException("schematica.command.list.noSuchPage", new Object[0]);
        }
        sender.sendMessage(this.withStyle((ITextComponent)new TextComponentTranslation("schematica.command.list.header", new Object[] { page + 1, totalPages + 1 }), TextFormatting.DARK_GREEN, null));
        for (final ITextComponent chatComponent2 : componentsToSend) {
            sender.sendMessage(chatComponent2);
        }
    }
    
    static {
        FILE_FILTER_SCHEMATIC = new FileFilterSchematic(false);
    }
}
