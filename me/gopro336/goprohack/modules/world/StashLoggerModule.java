//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.modules.world;

import java.io.FileWriter;
import java.io.IOException;
import me.gopro336.goprohack.managers.DirectoryManager;
import java.util.Iterator;
import net.minecraft.entity.passive.AbstractChestHorse;
import java.util.function.Predicate;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.SPacketChunkData;
import me.gopro336.goprohack.events.entity.EventEntityAdded;
import me.zero.alpine.fork.listener.EventHandler;
import me.gopro336.goprohack.events.network.EventNetworkPacketEvent;
import me.zero.alpine.fork.listener.Listener;
import me.gopro336.goprohack.modules.Value;
import me.gopro336.goprohack.modules.Module;

public class StashLoggerModule extends Module
{
    public final Value<Integer> ChestNumberToImportantNotify;
    public final Value<Boolean> Chests;
    public final Value<Boolean> Shulkers;
    public final Value<Boolean> ChestedAnimals;
    public final Value<Boolean> WriteToFile;
    private String WriterName;
    @EventHandler
    private Listener<EventNetworkPacketEvent> PacketEvent;
    @EventHandler
    private Listener<EventEntityAdded> OnEntityAdded;
    
    public StashLoggerModule() {
        super("StashLogger", new String[] { "SL" }, "Logs chests, chested donkeys, etc on chunk loads", "NONE", -1, ModuleType.WORLD);
        this.ChestNumberToImportantNotify = new Value<Integer>("MaxCount", new String[] { "ChestNumberToImportantNotify" }, "Number of chests to inform you there was probably unnatural gen chests (a base!)", 5, 0, 20, 1);
        this.Chests = new Value<Boolean>("Chests", new String[] { "" }, "Logs chests.", true);
        this.Shulkers = new Value<Boolean>("Shulkers", new String[] { "" }, "Logs Shulkers.", true);
        this.ChestedAnimals = new Value<Boolean>("Donkeys", new String[] { "" }, "Logs chested animals.", true);
        this.WriteToFile = new Value<Boolean>("WriteToFile", new String[] { "" }, "Writes what this finds to a file.", true);
        this.WriterName = null;
        SPacketChunkData l_Packet;
        int l_ChestsCount;
        int shulkers;
        final Iterator<NBTTagCompound> iterator;
        NBTTagCompound l_Tag;
        String l_Id;
        this.PacketEvent = new Listener<EventNetworkPacketEvent>(p_Event -> {
            if (p_Event.getPacket() instanceof SPacketChunkData) {
                l_Packet = (SPacketChunkData)p_Event.getPacket();
                l_ChestsCount = 0;
                shulkers = 0;
                l_Packet.getTileEntityTags().iterator();
                while (iterator.hasNext()) {
                    l_Tag = iterator.next();
                    l_Id = l_Tag.getString("id");
                    if (l_Id.equals("minecraft:chest") && this.Chests.getValue()) {
                        ++l_ChestsCount;
                    }
                    else if (l_Id.equals("minecraft:shulker_box") && this.Shulkers.getValue()) {
                        ++shulkers;
                    }
                    else {
                        continue;
                    }
                }
                if (l_ChestsCount >= this.ChestNumberToImportantNotify.getValue()) {
                    this.SendMessage(String.format("%s chests located at chunk [%s, %s] Dimension: %s", l_ChestsCount, l_Packet.getChunkX() * 16, l_Packet.getChunkZ() * 16, this.GetDimensionName()), true);
                }
                if (shulkers > 0) {
                    this.SendMessage(String.format("%s shulker boxes at [%s, %s] Dimension: %s", shulkers, l_Packet.getChunkX() * 16, l_Packet.getChunkZ() * 16, this.GetDimensionName()), true);
                }
            }
            return;
        }, (Predicate<EventNetworkPacketEvent>[])new Predicate[0]);
        AbstractChestHorse horse;
        this.OnEntityAdded = new Listener<EventEntityAdded>(event -> {
            if (event.GetEntity() instanceof AbstractChestHorse && this.ChestedAnimals.getValue()) {
                horse = (AbstractChestHorse)event.GetEntity();
                if (horse.hasChest()) {
                    this.SendMessage(String.format("%s chested animal located at [%s, %s] Dimension: %s", horse.getName(), Math.floor(horse.posX), Math.floor(horse.posZ), this.GetDimensionName()), true);
                }
            }
        }, (Predicate<EventEntityAdded>[])new Predicate[0]);
    }
    
    @Override
    public void toggleNoSave() {
    }
    
    @Override
    public void onEnable() {
        super.onEnable();
        if (this.WriteToFile.getValue()) {
            String server = (this.mc.getCurrentServerData() != null) ? this.mc.getCurrentServerData().serverIP : "singleplayer";
            server = server.replaceAll("\\.", "");
            if (server.contains(":")) {
                server = server.substring(0, server.indexOf(":"));
            }
            final String name = this.mc.player.getName();
            final String file = server + "_" + name + "_" + System.currentTimeMillis();
            try {
                this.WriterName = DirectoryManager.Get().GetCurrentDirectory() + "/GoproHack/StashFinder/" + file + ".txt";
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            this.SendMessage("Created the file named: " + file, false);
        }
    }
    
    @Override
    public void onDisable() {
        super.onDisable();
    }
    
    private void SendMessage(final String message, final boolean save) {
        if (this.WriteToFile.getValue() && save) {
            try {
                final FileWriter writer = new FileWriter(this.WriterName, true);
                writer.write(message + "\n");
                writer.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.SendMessage(message);
    }
    
    private String GetDimensionName() {
        switch (this.mc.player.dimension) {
            case -1: {
                return "Nether";
            }
            case 0: {
                return "Overworld";
            }
            case 1: {
                return "End";
            }
            default: {
                return "Aether";
            }
        }
    }
}
