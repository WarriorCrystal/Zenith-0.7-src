//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.command.impl;

import me.gopro336.goprohack.waypoints.Waypoint;
import me.gopro336.goprohack.waypoints.WaypointManager;
import java.time.temporal.TemporalAccessor;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import me.gopro336.goprohack.util.GoproVec3d;
import me.gopro336.goprohack.command.Command;

public class WaypointCommand extends Command
{
    public WaypointCommand() {
        super("Waypoint", "Allows you to create waypoints, remove them, or edit them, if no args are put in, the last created waypoint is used");
        this.CommandChunks.add("add <optional: name> x y z");
        this.CommandChunks.add("remove <optional: name>");
        this.CommandChunks.add("edit <optional: name> x y z");
    }
    
    @Override
    public void ProcessCommand(final String args) {
        final String[] split = args.split(" ");
        if (split == null || split.length <= 1) {
            this.SendToChat(this.GetHelp());
            return;
        }
        String name = null;
        GoproVec3d pos = null;
        if (split.length >= 3) {
            name = split[2];
        }
        if (split.length > 3) {
            pos = new GoproVec3d(0.0, -420.0, 0.0);
            pos.x = Double.parseDouble(split[3]);
            if (split.length == 4) {
                pos.z = Double.parseDouble(split[4]);
            }
            else if (split.length > 4) {
                pos.y = Double.parseDouble(split[4]);
            }
            if (split.length > 5) {
                pos.z = Double.parseDouble(split[5]);
            }
            if (pos.y == -420.0) {
                pos.y = 100.0;
            }
        }
        if (pos == null) {
            pos = new GoproVec3d(this.mc.player.posX, this.mc.player.posY, this.mc.player.posZ);
        }
        if (split[1].startsWith("a")) {
            if (name == null) {
                final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                final LocalDateTime now = LocalDateTime.now();
                name = dtf.format(now);
            }
            WaypointManager.Get().AddWaypoint(Waypoint.Type.Normal, name, pos, this.mc.player.dimension);
        }
        else if (split[1].startsWith("r")) {
            if (WaypointManager.Get().RemoveWaypoint(name)) {
                this.SendToChat("Successfully removed the waypoint named " + ((name == null) ? "last" : name));
            }
            else {
                this.SendToChat("Fail!");
            }
        }
        else if (split[1].startsWith("e")) {
            if (WaypointManager.Get().EditWaypoint(name, pos)) {
                this.SendToChat("Successfully edited the waypoint");
            }
            else {
                this.SendToChat("Fail!");
            }
        }
    }
}
