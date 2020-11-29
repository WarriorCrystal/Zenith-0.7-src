//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.util;

import net.minecraft.util.math.Vec3d;
import java.util.Iterator;
import me.gopro336.goprohack.util.entity.EntityUtil;
import net.minecraft.entity.Entity;
import me.gopro336.goprohack.managers.FriendManager;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

public class GoproUtil
{
    public static EntityPlayer findClosestTarget() {
        if (Minecraft.getMinecraft().world.playerEntities.isEmpty()) {
            return null;
        }
        EntityPlayer closestTarget = null;
        for (final EntityPlayer target : Minecraft.getMinecraft().world.playerEntities) {
            if (target == Minecraft.getMinecraft().player) {
                continue;
            }
            if (FriendManager.Get().IsFriend((Entity)target)) {
                continue;
            }
            if (!EntityUtil.isLiving((Entity)target)) {
                continue;
            }
            if (target.getHealth() <= 0.0f) {
                continue;
            }
            if (closestTarget != null && Minecraft.getMinecraft().player.getDistance((Entity)target) > Minecraft.getMinecraft().player.getDistance((Entity)closestTarget)) {
                continue;
            }
            closestTarget = target;
        }
        return closestTarget;
    }
    
    public Vec3d GetCenter(final double posX, final double posY, final double posZ) {
        final double x = Math.floor(posX) + 0.5;
        final double y = Math.floor(posY);
        final double z = Math.floor(posZ) + 0.5;
        return new Vec3d(x, y, z);
    }
}
