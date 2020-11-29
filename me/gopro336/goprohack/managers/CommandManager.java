// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.managers;

import java.util.Iterator;
import me.gopro336.goprohack.main.GoproHack;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.function.Function;
import java.util.Comparator;
import me.gopro336.goprohack.modules.Value;
import java.util.List;
import me.gopro336.goprohack.gui.hud.HudComponentItem;
import me.gopro336.goprohack.command.impl.ModuleCommand;
import me.gopro336.goprohack.modules.Module;
import me.gopro336.goprohack.command.util.ModuleCommandListener;
import me.gopro336.goprohack.command.impl.WaypointCommand;
import me.gopro336.goprohack.command.impl.PresetsCommand;
import me.gopro336.goprohack.command.impl.FontCommand;
import me.gopro336.goprohack.command.impl.ResetGUICommand;
import me.gopro336.goprohack.command.impl.UnbindCommand;
import me.gopro336.goprohack.command.impl.BindCommand;
import me.gopro336.goprohack.command.impl.ToggleCommand;
import me.gopro336.goprohack.command.impl.VClipCommand;
import me.gopro336.goprohack.command.impl.HClipCommand;
import me.gopro336.goprohack.command.impl.SoundReloadCommand;
import me.gopro336.goprohack.command.impl.HelpCommand;
import me.gopro336.goprohack.command.impl.FriendCommand;
import me.gopro336.goprohack.command.Command;
import java.util.ArrayList;

public class CommandManager
{
    private ArrayList<Command> Commands;
    
    public CommandManager() {
        this.Commands = new ArrayList<Command>();
    }
    
    public void InitalizeCommands() {
        this.Commands.add(new FriendCommand());
        this.Commands.add(new HelpCommand());
        this.Commands.add(new SoundReloadCommand());
        this.Commands.add(new HClipCommand());
        this.Commands.add(new VClipCommand());
        this.Commands.add(new ToggleCommand());
        this.Commands.add(new BindCommand());
        this.Commands.add(new UnbindCommand());
        this.Commands.add(new ResetGUICommand());
        this.Commands.add(new FontCommand());
        this.Commands.add(new PresetsCommand());
        this.Commands.add(new WaypointCommand());
        final ModuleCommandListener l_Listener;
        ModuleManager.Get().GetModuleList().forEach(p_Mod -> {
            l_Listener = new ModuleCommandListener() {
                final /* synthetic */ Module val$p_Mod;
                
                @Override
                public void OnHide() {
                    this.val$p_Mod.setHidden(!this.val$p_Mod.isHidden());
                }
                
                @Override
                public void OnToggle() {
                    this.val$p_Mod.toggle();
                }
                
                @Override
                public void OnRename(final String p_NewName) {
                    this.val$p_Mod.setDisplayName(p_NewName);
                }
            };
            this.Commands.add(new ModuleCommand(p_Mod.getDisplayName(), p_Mod.getDesc(), l_Listener, p_Mod.getValueList()));
            return;
        });
        final ModuleCommandListener l_Listener2;
        HudManager.Get().Items.forEach(p_Item -> {
            l_Listener2 = new ModuleCommandListener() {
                final /* synthetic */ HudComponentItem val$p_Item;
                
                @Override
                public void OnHide() {
                    this.val$p_Item.SetHidden(!this.val$p_Item.IsHidden());
                }
                
                @Override
                public void OnToggle() {
                    this.val$p_Item.SetHidden(!this.val$p_Item.IsHidden());
                }
                
                @Override
                public void OnRename(final String p_NewName) {
                    this.val$p_Item.SetDisplayName(p_NewName, true);
                }
            };
            this.Commands.add(new ModuleCommand(p_Item.GetDisplayName(), "NYI", l_Listener2, p_Item.ValueList));
            return;
        });
        this.Commands.sort(Comparator.comparing((Function<? super Command, ? extends Comparable>)Command::GetName));
    }
    
    public final ArrayList<Command> GetCommands() {
        return this.Commands;
    }
    
    public final List<Command> GetCommandsLike(final String p_Like) {
        return this.Commands.stream().filter(p_Command -> p_Command.GetName().toLowerCase().startsWith(p_Like.toLowerCase())).collect((Collector<? super Object, ?, List<Command>>)Collectors.toList());
    }
    
    public static CommandManager Get() {
        return GoproHack.GetCommandManager();
    }
    
    public Command GetCommandLike(final String p_Like) {
        for (final Command l_Command : this.Commands) {
            if (l_Command.GetName().toLowerCase().startsWith(p_Like.toLowerCase())) {
                return l_Command;
            }
        }
        return null;
    }
    
    public void Reload() {
        this.Commands.clear();
        this.InitalizeCommands();
    }
}
