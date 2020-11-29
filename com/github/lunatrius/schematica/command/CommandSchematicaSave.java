//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package com.github.lunatrius.schematica.command;

import net.minecraft.command.NumberInvalidException;
import java.io.File;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.math.BlockPos;
import com.github.lunatrius.schematica.reference.Reference;
import com.github.lunatrius.schematica.world.schematic.SchematicFormat;
import com.github.lunatrius.core.util.math.MBlockPos;
import com.github.lunatrius.schematica.Schematica;
import net.minecraft.command.CommandException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.command.ICommandSender;
import javax.annotation.ParametersAreNonnullByDefault;
import mcp.MethodsReturnNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class CommandSchematicaSave extends CommandSchematicaBase
{
    public String getName() {
        return "schematicaSave";
    }
    
    public String getUsage(final ICommandSender sender) {
        return "schematica.command.save.usage";
    }
    
    public void execute(final MinecraftServer server, final ICommandSender sender, final String[] args) throws CommandException {
        if (args.length < 7) {
            throw new WrongUsageException(this.getUsage(sender), new Object[0]);
        }
        if (!(sender instanceof EntityPlayer)) {
            throw new CommandException("schematica.command.save.playersOnly", new Object[0]);
        }
        final EntityPlayer player = (EntityPlayer)sender;
        if (Schematica.proxy.isPlayerQuotaExceeded(player)) {
            throw new CommandException("schematica.command.save.quotaExceeded", new Object[0]);
        }
        final MBlockPos from = new MBlockPos();
        final MBlockPos to = new MBlockPos();
        String name;
        String format;
        String filename;
        try {
            from.set(this.parseCoord(args[0]), this.parseCoord(args[1]), this.parseCoord(args[2]));
            to.set(this.parseCoord(args[3]), this.parseCoord(args[4]), this.parseCoord(args[5]));
            name = args[6];
            if (args.length >= 8) {
                format = args[7];
                if (!SchematicFormat.FORMATS.containsKey(format)) {
                    throw new CommandException("schematica.command.save.unknownFormat", new Object[] { format });
                }
            }
            else {
                format = null;
            }
            filename = name + SchematicFormat.getExtension(format);
        }
        catch (NumberFormatException exception) {
            throw new WrongUsageException(this.getUsage(sender), new Object[0]);
        }
        Reference.logger.debug("Saving schematic from {} to {} to {}", (Object)from, (Object)to, (Object)filename);
        final File schematicDirectory = Schematica.proxy.getPlayerSchematicDirectory(player, true);
        if (schematicDirectory == null) {
            Reference.logger.warn("Unable to determine the schematic directory for player {}", (Object)player);
            throw new CommandException("schematica.command.save.playerSchematicDirUnavailable", new Object[0]);
        }
        if (!schematicDirectory.exists() && !schematicDirectory.mkdirs()) {
            Reference.logger.warn("Could not create player schematic directory {}", (Object)schematicDirectory.getAbsolutePath());
            throw new CommandException("schematica.command.save.playerSchematicDirUnavailable", new Object[0]);
        }
        try {
            Schematica.proxy.saveSchematic(player, schematicDirectory, filename, player.getEntityWorld(), format, from, to);
            sender.sendMessage((ITextComponent)new TextComponentTranslation("schematica.command.save.saveSucceeded", new Object[] { name }));
        }
        catch (Exception e) {
            throw new CommandException("schematica.command.save.saveFailed", new Object[] { name });
        }
    }
    
    private int parseCoord(final String argument) throws NumberInvalidException {
        return parseInt(argument, -30000000, 30000000);
    }
}
