// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.command.impl;

import me.gopro336.goprohack.friend.Friend;
import com.google.gson.internal.LinkedTreeMap;
import me.gopro336.goprohack.managers.FriendManager;
import me.gopro336.goprohack.command.Command;

public class FriendCommand extends Command
{
    public FriendCommand() {
        super("Friend", "Allows you to communicate with the friend manager, allowing for adding/removing/updating friends");
        this.CommandChunks.add("add <username>");
        this.CommandChunks.add("remove <username>");
        this.CommandChunks.add("list");
    }
    
    @Override
    public void ProcessCommand(final String p_Args) {
        final String[] l_Split = p_Args.split(" ");
        if (l_Split == null || l_Split.length <= 1) {
            this.SendToChat("Invalid Input");
            return;
        }
        if (l_Split[1].toLowerCase().startsWith("a")) {
            if (l_Split.length > 1) {
                if (FriendManager.Get().AddFriend(l_Split[2].toLowerCase())) {
                    this.SendToChat(String.format("Added %s as a friend.", l_Split[2]));
                }
                else {
                    this.SendToChat(String.format("%s is already a friend.", l_Split[2]));
                }
                return;
            }
            this.SendToChat("Usage: friend add <name>");
        }
        else {
            if (!l_Split[1].toLowerCase().startsWith("r")) {
                if (l_Split[1].toLowerCase().startsWith("l")) {
                    final LinkedTreeMap<String, Friend> l_Map = FriendManager.Get().GetFriends();
                    l_Map.forEach((k, v) -> this.SendToChat(String.format("F: %s A: %s", v.GetName(), v.GetAlias())));
                    if (l_Map.isEmpty()) {
                        this.SendToChat("You don't have any friends...");
                    }
                }
                return;
            }
            if (l_Split.length > 1) {
                if (FriendManager.Get().RemoveFriend(l_Split[2].toLowerCase())) {
                    this.SendToChat(String.format("Removed %s as a friend.", l_Split[2]));
                }
                else {
                    this.SendToChat(String.format("%s is not a friend.", l_Split[2]));
                }
                return;
            }
            this.SendToChat("Usage: friend remove <name>");
        }
    }
    
    @Override
    public String GetHelp() {
        return "Allows you to add friends, or remove friends or list friends..\nfriend add <name>\nfriend remove<name>\nfriend list";
    }
}
