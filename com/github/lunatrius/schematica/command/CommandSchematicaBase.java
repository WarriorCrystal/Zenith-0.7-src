//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package com.github.lunatrius.schematica.command;

import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.ITextComponent;
import javax.annotation.Nullable;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import javax.annotation.ParametersAreNonnullByDefault;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.command.CommandBase;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public abstract class CommandSchematicaBase extends CommandBase
{
    public int getRequiredPermissionLevel() {
        return 0;
    }
    
    public boolean checkPermission(final MinecraftServer server, final ICommandSender sender) {
        return super.checkPermission(server, sender) || (sender instanceof EntityPlayerMP && this.getRequiredPermissionLevel() <= 0);
    }
    
    protected <T extends ITextComponent> T withStyle(final T component, final TextFormatting formatting, @Nullable final String command) {
        final Style style = new Style();
        style.setColor(formatting);
        if (command != null) {
            style.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, command));
        }
        component.setStyle(style);
        return component;
    }
}
