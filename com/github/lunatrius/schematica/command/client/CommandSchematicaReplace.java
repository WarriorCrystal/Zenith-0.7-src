//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package com.github.lunatrius.schematica.command.client;

import net.minecraft.block.state.pattern.BlockStateMatcher;
import com.github.lunatrius.schematica.client.world.SchematicWorld;
import com.github.lunatrius.schematica.reference.Reference;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import com.github.lunatrius.schematica.block.state.pattern.BlockStateReplacer;
import net.minecraft.command.CommandException;
import com.github.lunatrius.schematica.proxy.ClientProxy;
import java.util.Collections;
import java.util.Collection;
import net.minecraft.block.Block;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.server.MinecraftServer;
import net.minecraft.command.ICommandSender;
import javax.annotation.ParametersAreNonnullByDefault;
import mcp.MethodsReturnNonnullByDefault;
import com.github.lunatrius.schematica.command.CommandSchematicaBase;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class CommandSchematicaReplace extends CommandSchematicaBase
{
    public String getName() {
        return "schematicaReplace";
    }
    
    public String getUsage(final ICommandSender sender) {
        return "schematica.command.replace.usage";
    }
    
    public List<String> getTabCompletions(final MinecraftServer server, final ICommandSender sender, final String[] args, @Nullable final BlockPos pos) {
        if (args.length < 3) {
            return (List<String>)getListOfStringsMatchingLastWord(args, (Collection)Block.REGISTRY.getKeys());
        }
        return Collections.emptyList();
    }
    
    public void execute(final MinecraftServer server, final ICommandSender sender, final String[] args) throws CommandException {
        final SchematicWorld schematic = ClientProxy.schematic;
        if (schematic == null) {
            throw new CommandException("schematica.command.replace.noSchematic", new Object[0]);
        }
        if (args.length != 2) {
            throw new CommandException("schematica.command.replace.usage", new Object[0]);
        }
        try {
            final BlockStateReplacer.BlockStateInfo patternInfo = BlockStateReplacer.fromString(args[0]);
            final BlockStateMatcher matcher = BlockStateReplacer.getMatcher(patternInfo);
            final BlockStateReplacer.BlockStateInfo replacementInfo = BlockStateReplacer.fromString(args[1]);
            final BlockStateReplacer replacer = BlockStateReplacer.forBlockState(replacementInfo.block.getDefaultState());
            final int count = schematic.replaceBlock(matcher, replacer, replacementInfo.stateData);
            sender.sendMessage((ITextComponent)new TextComponentTranslation("schematica.command.replace.success", new Object[] { count }));
        }
        catch (Exception e) {
            Reference.logger.error("Something went wrong!", (Throwable)e);
            throw new CommandException(e.getMessage(), new Object[0]);
        }
    }
}
