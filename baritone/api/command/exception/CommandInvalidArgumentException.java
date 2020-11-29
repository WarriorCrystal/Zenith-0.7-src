// 
// Decompiled by Procyon v0.5.36
// 

package baritone.api.command.exception;

import baritone.api.command.argument.ICommandArgument;

public abstract class CommandInvalidArgumentException extends CommandErrorMessageException
{
    public final ICommandArgument arg;
    
    protected CommandInvalidArgumentException(final ICommandArgument arg, final String s) {
        super(formatMessage(arg, s));
        this.arg = arg;
    }
    
    protected CommandInvalidArgumentException(final ICommandArgument arg, final String s, final Throwable t) {
        super(formatMessage(arg, s), t);
        this.arg = arg;
    }
    
    private static String formatMessage(final ICommandArgument commandArgument, final String s) {
        return String.format("Error at argument #%s: %s", (commandArgument.getIndex() == -1) ? "<unknown>" : Integer.toString(commandArgument.getIndex() + 1), s);
    }
}
