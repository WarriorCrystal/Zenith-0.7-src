// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.mixin.client;

import me.gopro336.goprohack.events.network.EventNetworkPostPacketEvent;
import io.netty.channel.ChannelHandlerContext;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import me.gopro336.goprohack.GoproHackMod;
import me.gopro336.goprohack.events.network.EventNetworkPacketEvent;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.network.Packet;
import net.minecraft.network.NetworkManager;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ NetworkManager.class })
public class MixinNetworkManager
{
    @Inject(method = { "sendPacket(Lnet/minecraft/network/Packet;)V" }, at = { @At("HEAD") }, cancellable = true)
    private void onSendPacket(final Packet<?> p_Packet, final CallbackInfo callbackInfo) {
        final EventNetworkPacketEvent l_Event = new EventNetworkPacketEvent(p_Packet);
        GoproHackMod.EVENT_BUS.post(l_Event);
        if (l_Event.isCancelled()) {
            callbackInfo.cancel();
        }
    }
    
    @Inject(method = { "channelRead0" }, at = { @At("HEAD") }, cancellable = true)
    private void onChannelRead(final ChannelHandlerContext context, final Packet<?> p_Packet, final CallbackInfo callbackInfo) {
        final EventNetworkPacketEvent l_Event = new EventNetworkPacketEvent(p_Packet);
        GoproHackMod.EVENT_BUS.post(l_Event);
        if (l_Event.isCancelled()) {
            callbackInfo.cancel();
        }
    }
    
    @Inject(method = { "sendPacket(Lnet/minecraft/network/Packet;)V" }, at = { @At("RETURN") })
    private void onPostSendPacket(final Packet<?> p_Packet, final CallbackInfo callbackInfo) {
        GoproHackMod.EVENT_BUS.post(new EventNetworkPostPacketEvent(p_Packet));
    }
    
    @Inject(method = { "channelRead0" }, at = { @At("RETURN") })
    private void onPostChannelRead(final ChannelHandlerContext context, final Packet<?> p_Packet, final CallbackInfo callbackInfo) {
        GoproHackMod.EVENT_BUS.post(new EventNetworkPostPacketEvent(p_Packet));
    }
}
