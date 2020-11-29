//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.modules.misc;

import java.util.function.Predicate;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketChatMessage;
import me.zero.alpine.fork.listener.EventHandler;
import me.gopro336.goprohack.events.player.EventPlayerSendChatMessage;
import me.zero.alpine.fork.listener.Listener;
import me.gopro336.goprohack.modules.Value;
import me.gopro336.goprohack.modules.Module;

public final class RetardChatModule extends Module
{
    public final Value<Modes> mode;
    @EventHandler
    private Listener<EventPlayerSendChatMessage> OnSendChatMsg;
    
    public RetardChatModule() {
        super("ChatFucker", new String[] { "Fucker" }, "Makes your chat gay", "NONE", 14361733, ModuleType.MISC);
        this.mode = new Value<Modes>("Mode", new String[] { "M" }, "The retard chat mode", Modes.Spongebob);
        String l_Message;
        boolean l_Flag;
        final char[] array;
        int length;
        int i = 0;
        char l_Char;
        String l_Val;
        this.OnSendChatMsg = new Listener<EventPlayerSendChatMessage>(p_Event -> {
            if (!p_Event.Message.startsWith("/")) {
                if (!p_Event.Message.startsWith(".")) {
                    if (!p_Event.Message.startsWith("*")) {
                        l_Message = "";
                        switch (this.mode.getValue()) {
                            case Spongebob: {
                                l_Flag = false;
                                p_Event.Message.toCharArray();
                                for (length = array.length; i < length; ++i) {
                                    l_Char = array[i];
                                    l_Val = String.valueOf(l_Char);
                                    l_Message += (l_Flag ? l_Val.toUpperCase() : l_Val.toLowerCase());
                                    if (l_Char != ' ') {
                                        l_Flag = !l_Flag;
                                    }
                                }
                                break;
                            }
                        }
                        p_Event.cancel();
                        this.mc.getConnection().sendPacket((Packet)new CPacketChatMessage(l_Message));
                    }
                }
            }
        }, (Predicate<EventPlayerSendChatMessage>[])new Predicate[0]);
    }
    
    @Override
    public String getMetaData() {
        return this.mode.getValue().toString();
    }
    
    public enum Modes
    {
        Spongebob;
    }
}
