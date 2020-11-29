// 
// Decompiled by Procyon v0.5.36
// 

package baritone.api.command.manager;

import java.util.stream.Stream;
import baritone.api.command.argument.ICommandArgument;
import java.util.List;
import baritone.api.command.ICommand;
import baritone.api.command.registry.Registry;
import baritone.api.IBaritone;

public interface ICommandManager
{
    IBaritone getBaritone();
    
    Registry<ICommand> getRegistry();
    
    ICommand getCommand(final String p0);
    
    boolean execute(final String p0);
    
    boolean execute(final rr<String, List<ICommandArgument>> p0);
    
    Stream<String> tabComplete(final rr<String, List<ICommandArgument>> p0);
    
    Stream<String> tabComplete(final String p0);
}
