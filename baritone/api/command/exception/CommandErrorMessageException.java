// 
// Decompiled by Procyon v0.5.36
// 

package baritone.api.command.exception;

public abstract class CommandErrorMessageException extends CommandException
{
    protected CommandErrorMessageException(final String s) {
        super(s);
    }
    
    protected CommandErrorMessageException(final String s, final Throwable t) {
        super(s, t);
    }
}
