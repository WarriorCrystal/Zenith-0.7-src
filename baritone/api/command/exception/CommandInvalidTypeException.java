// 
// Decompiled by Procyon v0.5.36
// 

package baritone.api.command.exception;

import baritone.api.command.argument.ICommandArgument;

public class CommandInvalidTypeException extends CommandInvalidArgumentException
{
    public CommandInvalidTypeException(final ICommandArgument commandArgument, final String s) {
        super(commandArgument, String.format("Expected %s", s));
    }
    
    public CommandInvalidTypeException(final ICommandArgument commandArgument, final String s, final Throwable t) {
        super(commandArgument, String.format("Expected %s", s), t);
    }
    
    public CommandInvalidTypeException(final ICommandArgument commandArgument, final String s, final String s2) {
        super(commandArgument, String.format("Expected %s, but got %s instead", s, s2));
    }
    
    public CommandInvalidTypeException(final ICommandArgument commandArgument, final String s, final String s2, final Throwable t) {
        super(commandArgument, String.format("Expected %s, but got %s instead", s, s2), t);
    }
}
