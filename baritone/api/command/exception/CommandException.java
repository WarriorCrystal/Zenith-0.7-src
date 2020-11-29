// 
// Decompiled by Procyon v0.5.36
// 

package baritone.api.command.exception;

public abstract class CommandException extends Exception implements ICommandException
{
    protected CommandException(final String message) {
        super(message);
    }
    
    protected CommandException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
