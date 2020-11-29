//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.command.impl;

import net.minecraft.entity.Entity;
import me.gopro336.goprohack.command.Command;

public class VClipCommand extends Command
{
    public VClipCommand() {
        super("VClip", "Allows you to vclip x blocks");
    }
    
    @Override
    public void ProcessCommand(final String p_Args) {
        final String[] l_Split = p_Args.split(" ");
        if (l_Split == null || l_Split.length <= 1) {
            this.SendToChat("Invalid Input");
            return;
        }
        final double l_Number = Double.parseDouble(l_Split[1]);
        final Entity l_Entity = (Entity)(this.mc.player.isRiding() ? this.mc.player.getRidingEntity() : this.mc.player);
        l_Entity.setPosition(this.mc.player.posX, this.mc.player.posY + l_Number, this.mc.player.posZ);
        this.SendToChat(String.format("Teleported you %s blocks up", l_Number));
    }
    
    @Override
    public String GetHelp() {
        return "Allows you teleport up x amount of blocks.";
    }
}
