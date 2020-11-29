//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.modules.combat;

import java.util.function.Predicate;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.network.Packet;
import net.minecraft.util.math.BlockPos;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.item.ItemBow;
import me.zero.alpine.fork.listener.EventHandler;
import me.gopro336.goprohack.events.client.EventClientTick;
import me.zero.alpine.fork.listener.Listener;
import me.gopro336.goprohack.modules.Module;

public class BowSpamModule extends Module
{
    @EventHandler
    private Listener<EventClientTick> OnTick;
    
    public BowSpamModule() {
        super("BowSpam", new String[] { "BS" }, "Releases the bow as fast as possible", "NONE", 14361636, ModuleType.COMBAT);
        this.OnTick = new Listener<EventClientTick>(p_Event -> {
            if (this.mc.player.getHeldItemMainhand().getItem() instanceof ItemBow && this.mc.player.isHandActive() && this.mc.player.getItemInUseMaxCount() >= 3) {
                this.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, this.mc.player.getHorizontalFacing()));
                this.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItem(this.mc.player.getActiveHand()));
                this.mc.player.stopActiveHand();
            }
        }, (Predicate<EventClientTick>[])new Predicate[0]);
    }
}
