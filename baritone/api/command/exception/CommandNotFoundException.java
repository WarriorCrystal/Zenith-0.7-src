// 
// Decompiled by Procyon v0.5.36
// 

package baritone.api.command.exception;

import baritone.api.utils.Helper;
import baritone.api.command.argument.ICommandArgument;
import java.util.List;
import baritone.api.command.ICommand;

public class CommandNotFoundException extends CommandException
{
    public final String command;
    
    public CommandNotFoundException(final String command) {
        super(String.format("Command not found: %s", command));
        this.command = command;
    }
    
    @Override
    public void handle(final ICommand command, final List<ICommandArgument> list) {
        Helper.HELPER.logDirect(this.getMessage());
    }
}
