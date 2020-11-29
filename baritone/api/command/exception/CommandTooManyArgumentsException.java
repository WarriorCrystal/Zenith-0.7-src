// 
// Decompiled by Procyon v0.5.36
// 

package baritone.api.command.exception;

public class CommandTooManyArgumentsException extends CommandErrorMessageException
{
    public CommandTooManyArgumentsException(final int i) {
        super(String.format("Too many arguments (expected at most %d)", i));
    }
}
