// 
// Decompiled by Procyon v0.5.36
// 

package baritone.launch.mixins;

import io.netty.channel.ChannelHandlerContext;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import java.util.Iterator;
import baritone.api.event.events.PacketEvent;
import baritone.api.event.events.type.EventState;
import baritone.api.IBaritone;
import baritone.api.BaritoneAPI;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import io.netty.channel.Channel;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ gw.class })
public class MixinNetworkManager
{
    @Shadow
    private Channel k;
    @Shadow
    @Final
    private hu h;
    
    @Inject(method = { "dispatchPacket" }, at = { @At("HEAD") })
    private void preDispatchPacket(final ht<?> ht, final GenericFutureListener<? extends Future<? super Void>>[] array, final CallbackInfo callbackInfo) {
        if (this.h != hu.b) {
            return;
        }
        final Iterator<IBaritone> iterator = BaritoneAPI.getProvider().getAllBaritones().iterator();
        while (iterator.hasNext()) {
            final IBaritone baritone;
            if ((baritone = iterator.next()).getPlayerContext().player() != null && baritone.getPlayerContext().player().d.a() == (gw)this) {
                baritone.getGameEventHandler().onSendPacket(new PacketEvent((gw)this, EventState.PRE, ht));
            }
        }
    }
    
    @Inject(method = { "dispatchPacket" }, at = { @At("RETURN") })
    private void postDispatchPacket(final ht<?> ht, final GenericFutureListener<? extends Future<? super Void>>[] array, final CallbackInfo callbackInfo) {
        if (this.h != hu.b) {
            return;
        }
        final Iterator<IBaritone> iterator = BaritoneAPI.getProvider().getAllBaritones().iterator();
        while (iterator.hasNext()) {
            final IBaritone baritone;
            if ((baritone = iterator.next()).getPlayerContext().player() != null && baritone.getPlayerContext().player().d.a() == (gw)this) {
                baritone.getGameEventHandler().onSendPacket(new PacketEvent((gw)this, EventState.POST, ht));
            }
        }
    }
    
    @Inject(method = { "channelRead0" }, at = { @At(value = "INVOKE", target = "net/minecraft/network/Packet.processPacket(Lnet/minecraft/network/INetHandler;)V") })
    private void preProcessPacket(final ChannelHandlerContext channelHandlerContext, final ht<?> ht, final CallbackInfo callbackInfo) {
        if (this.h != hu.b) {
            return;
        }
        final Iterator<IBaritone> iterator = BaritoneAPI.getProvider().getAllBaritones().iterator();
        while (iterator.hasNext()) {
            final IBaritone baritone;
            if ((baritone = iterator.next()).getPlayerContext().player() != null && baritone.getPlayerContext().player().d.a() == (gw)this) {
                baritone.getGameEventHandler().onReceivePacket(new PacketEvent((gw)this, EventState.PRE, ht));
            }
        }
    }
    
    @Inject(method = { "channelRead0" }, at = { @At("RETURN") })
    private void postProcessPacket(final ChannelHandlerContext channelHandlerContext, final ht<?> ht, final CallbackInfo callbackInfo) {
        if (!this.k.isOpen() || this.h != hu.b) {
            return;
        }
        final Iterator<IBaritone> iterator = BaritoneAPI.getProvider().getAllBaritones().iterator();
        while (iterator.hasNext()) {
            final IBaritone baritone;
            if ((baritone = iterator.next()).getPlayerContext().player() != null && baritone.getPlayerContext().player().d.a() == (gw)this) {
                baritone.getGameEventHandler().onReceivePacket(new PacketEvent((gw)this, EventState.POST, ht));
            }
        }
    }
}
