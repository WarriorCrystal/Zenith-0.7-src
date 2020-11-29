//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package com.github.lunatrius.schematica.command;

import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import com.github.lunatrius.schematica.reference.Reference;
import com.github.lunatrius.core.util.FileUtils;
import java.io.File;
import com.github.lunatrius.schematica.Schematica;
import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
import java.util.Arrays;
import net.minecraft.command.CommandException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.command.ICommandSender;
import javax.annotation.ParametersAreNonnullByDefault;
import mcp.MethodsReturnNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class CommandSchematicaRemove extends CommandSchematicaBase
{
    public String getName() {
        return "schematicaRemove";
    }
    
    public String getUsage(final ICommandSender sender) {
        return "schematica.command.remove.usage";
    }
    
    public void execute(final MinecraftServer server, final ICommandSender sender, final String[] args) throws CommandException {
        if (args.length < 1) {
            throw new WrongUsageException(this.getUsage(sender), new Object[0]);
        }
        if (!(sender instanceof EntityPlayer)) {
            throw new CommandException("schematica.command.save.playersOnly", new Object[0]);
        }
        final EntityPlayer player = (EntityPlayer)sender;
        boolean delete = false;
        String name = String.join(" ", (CharSequence[])args);
        if (args.length > 1) {
            final String potentialNameHash = args[args.length - 1];
            if (potentialNameHash.length() == 32) {
                final String[] a = Arrays.copyOfRange(args, 0, args.length - 1);
                name = String.join(" ", (CharSequence[])a);
                final String hash = Hashing.md5().hashString((CharSequence)name, Charsets.UTF_8).toString();
                if (potentialNameHash.equals(hash)) {
                    delete = true;
                }
            }
        }
        final File schematicDirectory = Schematica.proxy.getPlayerSchematicDirectory(player, true);
        final File file = new File(schematicDirectory, name);
        if (!FileUtils.contains(schematicDirectory, file)) {
            Reference.logger.error("{} has tried to download the file {}", (Object)player.getName(), (Object)name);
            throw new CommandException("schematica.command.remove.schematicNotFound", new Object[0]);
        }
        if (file.exists()) {
            if (delete) {
                if (!file.delete()) {
                    throw new CommandException("schematica.command.remove.schematicNotFound", new Object[0]);
                }
                sender.sendMessage((ITextComponent)new TextComponentTranslation("schematica.command.remove.schematicRemoved", new Object[] { name }));
            }
            else {
                final String hash = Hashing.md5().hashString((CharSequence)name, Charsets.UTF_8).toString();
                final String confirmCommand = String.format("/%s %s %s", "schematicaRemove", name, hash);
                final ITextComponent chatComponent = (ITextComponent)new TextComponentTranslation("schematica.command.remove.areYouSure", new Object[] { name });
                chatComponent.appendSibling((ITextComponent)new TextComponentString(" ["));
                chatComponent.appendSibling(this.withStyle((ITextComponent)new TextComponentTranslation("gui.yes", new Object[0]), TextFormatting.RED, confirmCommand));
                chatComponent.appendSibling((ITextComponent)new TextComponentString("]"));
                sender.sendMessage(chatComponent);
            }
            return;
        }
        throw new CommandException("schematica.command.remove.schematicNotFound", new Object[0]);
    }
}
