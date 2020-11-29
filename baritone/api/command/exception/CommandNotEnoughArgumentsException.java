// 
// Decompiled by Procyon v0.5.36
// 

package baritone.api.command.exception;

public class CommandNotEnoughArgumentsException extends CommandErrorMessageException
{
    public CommandNotEnoughArgumentsException(final int i) {
        super(String.format("Not enough arguments (expected at least %d)", i));
    }
}
