// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.modules.player;

import me.gopro336.goprohack.modules.Module;

public class FriendsModule extends Module
{
    public FriendsModule() {
        super("Friends", new String[] { "Homies" }, "Allows the friend system to function, disabling this ignores friend requirements, useful for dueling friends.", "NONE", -1, ModuleType.PLAYER);
        this.setEnabled(true);
    }
}
