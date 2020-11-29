//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.modules.misc;

import java.util.function.Predicate;
import net.minecraft.network.Packet;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.util.EnumHand;
import me.gopro336.goprohack.util.entity.PlayerUtil;
import net.minecraft.entity.player.EntityPlayer;
import me.gopro336.goprohack.util.entity.EntityUtil;
import java.util.Comparator;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemDye;
import me.zero.alpine.fork.listener.EventHandler;
import me.gopro336.goprohack.events.player.EventPlayerMotionUpdate;
import me.zero.alpine.fork.listener.Listener;
import me.gopro336.goprohack.modules.Value;
import me.gopro336.goprohack.modules.Module;

public class AutoDyeModule extends Module
{
    public final Value<Integer> Radius;
    @EventHandler
    private Listener<EventPlayerMotionUpdate> OnPlayerUpdate;
    
    public AutoDyeModule() {
        super("AutoDye", new String[] { "" }, "Dyes sheep in range, if they are not same color as dye in hand.", "NONE", -1, ModuleType.MISC);
        this.Radius = new Value<Integer>("Radius", new String[] { "R" }, "Radius to search for sheep", 4, 0, 10, 1);
        EnumDyeColor l_Color;
        EntitySheep l_Sheep;
        double[] l_Pos;
        this.OnPlayerUpdate = new Listener<EventPlayerMotionUpdate>(p_Event -> {
            if (!(!(this.mc.player.getHeldItemMainhand().getItem() instanceof ItemDye))) {
                l_Color = EnumDyeColor.byDyeDamage(this.mc.player.getHeldItemMainhand().getMetadata());
                l_Sheep = (EntitySheep)this.mc.world.loadedEntityList.stream().filter(p_Entity -> this.IsValidSheep(p_Entity, l_Color)).map(p_Entity -> (EntitySheep)p_Entity).min(Comparator.comparing(p_Entity -> this.mc.player.getDistance(p_Entity))).orElse(null);
                if (l_Sheep != null) {
                    p_Event.cancel();
                    l_Pos = EntityUtil.calculateLookAt(l_Sheep.posX, l_Sheep.posY, l_Sheep.posZ, (EntityPlayer)this.mc.player);
                    this.mc.player.rotationYawHead = (float)l_Pos[0];
                    PlayerUtil.PacketFacePitchAndYaw((float)l_Pos[1], (float)l_Pos[0]);
                    this.mc.getConnection().sendPacket((Packet)new CPacketUseEntity((Entity)l_Sheep, EnumHand.MAIN_HAND));
                }
            }
        }, (Predicate<EventPlayerMotionUpdate>[])new Predicate[0]);
    }
    
    private boolean IsValidSheep(final Entity p_Entity, final EnumDyeColor p_Color) {
        if (p_Entity.getDistance((Entity)this.mc.player) > this.Radius.getValue()) {
            return false;
        }
        if (!(p_Entity instanceof EntitySheep)) {
            return false;
        }
        final EntitySheep l_Sheep = (EntitySheep)p_Entity;
        return l_Sheep.getFleeceColor() != p_Color;
    }
}
