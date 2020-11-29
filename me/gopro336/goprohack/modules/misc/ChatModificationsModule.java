//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.modules.misc;

import java.util.function.Predicate;
import me.gopro336.goprohack.main.GoproHack;
import com.mojang.realmsclient.gui.ChatFormatting;
import java.util.Date;
import java.text.SimpleDateFormat;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.network.play.server.SPacketChat;
import me.zero.alpine.fork.listener.EventHandler;
import me.gopro336.goprohack.events.network.EventNetworkPacketEvent;
import me.zero.alpine.fork.listener.Listener;
import me.gopro336.goprohack.modules.Value;
import me.gopro336.goprohack.modules.Module;

public final class ChatModificationsModule extends Module
{
    public final Value<TimeModes> TimeMode;
    public final Value<Boolean> AntiEZ;
    public final Value<Boolean> NoDiscord;
    public final Value<Boolean> NameHighlight;
    public final Value<Integer> ChatLength;
    @EventHandler
    private Listener<EventNetworkPacketEvent> PacketEvent;
    
    public ChatModificationsModule() {
        super("ChatModifications", new String[] { "ChatStamp", "ChatStamps" }, "Allows for chat modifications", "NONE", 14361680, ModuleType.MISC);
        this.TimeMode = new Value<TimeModes>("TimeMode", new String[] { "TimeModes", "Time" }, "Time format, 12 hour (NA) or 24 hour (EU).", TimeModes.NA);
        this.AntiEZ = new Value<Boolean>("AntiEZ", new String[] { "NoEZ" }, "Prevents EZ from being rendered in chat, very useful for 2b2tpvp", true);
        this.NoDiscord = new Value<Boolean>("NoDiscord", new String[] { "NoEZ" }, "Prevents discord from being rendered in chat", true);
        this.NameHighlight = new Value<Boolean>("NameHighlight", new String[] { "Highlight" }, "Highlights your name in gold in chat", true);
        this.ChatLength = new Value<Integer>("ChatLength", new String[] { "ChatLength" }, "ChatLength number for more chat length", 100, 0, 16777215, 1000);
        SPacketChat packet;
        TextComponentString component;
        String date;
        String l_Text;
        String l_Text2;
        String l_Text3;
        this.PacketEvent = new Listener<EventNetworkPacketEvent>(p_Event -> {
            if (p_Event.getPacket() instanceof SPacketChat) {
                packet = (SPacketChat)p_Event.getPacket();
                if (packet.getChatComponent() instanceof TextComponentString) {
                    component = (TextComponentString)packet.getChatComponent();
                    date = "";
                    switch (this.TimeMode.getValue()) {
                        case NA: {
                            date = new SimpleDateFormat("h:mm a").format(new Date());
                            break;
                        }
                        case EU: {
                            date = new SimpleDateFormat("k:mm").format(new Date());
                            break;
                        }
                    }
                    component.text = "§7[" + date + "]§r " + component.getText();
                    if (component.getFormattedText().contains("> ")) {
                        l_Text = component.getFormattedText().substring(component.getFormattedText().indexOf("> "));
                        if (l_Text.toLowerCase().contains("ez") && this.AntiEZ.getValue()) {
                            p_Event.cancel();
                        }
                        if (this.NoDiscord.getValue() && l_Text.toLowerCase().contains("discord")) {
                            p_Event.cancel();
                        }
                        if (p_Event.isCancelled()) {
                            return;
                        }
                    }
                    l_Text2 = component.getFormattedText();
                    if (this.NameHighlight.getValue() && this.mc.player != null && l_Text2.toLowerCase().contains(this.mc.player.getName().toLowerCase())) {
                        l_Text3 = l_Text2.replaceAll("(?i)" + this.mc.player.getName(), ChatFormatting.GOLD + this.mc.player.getName() + ChatFormatting.RESET);
                        p_Event.cancel();
                        GoproHack.SendMessage(l_Text3);
                    }
                }
            }
        }, (Predicate<EventNetworkPacketEvent>[])new Predicate[0]);
    }
    
    @Override
    public String getMetaData() {
        return this.TimeMode.getValue().toString();
    }
    
    private enum TimeModes
    {
        NA, 
        EU;
    }
}
