// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.managers;

import club.minnced.discord.rpc.DiscordUser;
import me.gopro336.goprohack.main.GoproHack;
import club.minnced.discord.rpc.DiscordRichPresence;
import club.minnced.discord.rpc.DiscordEventHandlers;
import club.minnced.discord.rpc.DiscordRPC;
import me.gopro336.goprohack.modules.misc.DiscordRPCModule;

public class DiscordManager
{
    private DiscordRPCModule _rpcModule;
    private Thread _thread;
    
    public DiscordManager() {
        this._rpcModule = null;
        this._thread = null;
    }
    
    public void enable() {
        this._rpcModule = (DiscordRPCModule)ModuleManager.Get().GetMod(DiscordRPCModule.class);
        final DiscordRPC lib = DiscordRPC.INSTANCE;
        final String applicationId = "731993310226415717";
        final String steamId = "";
        final DiscordEventHandlers handlers = new DiscordEventHandlers();
        handlers.ready = (user -> System.out.println("Ready!"));
        lib.Discord_Initialize(applicationId, handlers, true, steamId);
        final DiscordRichPresence presence = new DiscordRichPresence();
        presence.startTimestamp = System.currentTimeMillis() / 1000L;
        lib.Discord_UpdatePresence(presence);
        presence.largeImageKey = "logo";
        presence.largeImageText = "Join The GoproHack Development Discord!";
        final DiscordRPC discordRPC;
        final DiscordRichPresence discordRichPresence;
        (this._thread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                discordRPC.Discord_RunCallbacks();
                discordRichPresence.details = this._rpcModule.generateDetails();
                discordRichPresence.state = this._rpcModule.generateState();
                discordRPC.Discord_UpdatePresence(discordRichPresence);
                try {
                    Thread.sleep(2000L);
                }
                catch (InterruptedException ex) {}
            }
        }, "RPC-Callback-Handler")).start();
    }
    
    public void disable() throws InterruptedException {
        if (this._thread != null) {
            this._thread.interrupt();
        }
    }
    
    public static DiscordManager Get() {
        return GoproHack.GetDiscordManager();
    }
}
