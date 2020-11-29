// 
// Decompiled by Procyon v0.5.36
// 

package baritone.api.command.exception;

import baritone.api.utils.Helper;
import baritone.api.command.argument.ICommandArgument;
import java.util.List;
import baritone.api.command.ICommand;

public class CommandUnhandledException extends RuntimeException implements ICommandException
{
    public CommandUnhandledException(final String message) {
        super(message);
    }
    
    public CommandUnhandledException(final Throwable cause) {
        super(cause);
    }
    
    @Override
    public void handle(final ICommand command, final List<ICommandArgument> list) {
        Helper.HELPER.logDirect("An unhandled exception occurred. The error is in your game's log, please report this at https://github.com/cabaletta/baritone/issues", a.m);
        this.printStackTrace();
    }
}
