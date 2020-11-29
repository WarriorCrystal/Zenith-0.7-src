//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.command.impl;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import me.gopro336.goprohack.util.MathUtil;
import me.gopro336.goprohack.command.Command;

public class HClipCommand extends Command
{
    public HClipCommand() {
        super("HClip", "Allows you to hclip x blocks");
    }
    
    @Override
    public void ProcessCommand(final String p_Args) {
        final String[] l_Split = p_Args.split(" ");
        if (l_Split == null || l_Split.length <= 1) {
            this.SendToChat("Invalid Input");
            return;
        }
        final double l_Number = Double.parseDouble(l_Split[1]);
        final Vec3d l_Direction = MathUtil.direction(this.mc.player.rotationYaw);
        if (l_Direction != null) {
            final Entity l_Entity = (Entity)(this.mc.player.isRiding() ? this.mc.player.getRidingEntity() : this.mc.player);
            l_Entity.setPosition(this.mc.player.posX + l_Direction.x * l_Number, this.mc.player.posY, this.mc.player.posZ + l_Direction.z * l_Number);
            this.SendToChat(String.format("Teleported you %s blocks forward", l_Number));
        }
    }
    
    @Override
    public String GetHelp() {
        return "Allows you teleport forward x amount of blocks.";
    }
}
