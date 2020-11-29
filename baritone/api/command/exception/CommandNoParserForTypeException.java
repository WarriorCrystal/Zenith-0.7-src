// 
// Decompiled by Procyon v0.5.36
// 

package baritone.api.command.exception;

public class CommandNoParserForTypeException extends CommandUnhandledException
{
    public CommandNoParserForTypeException(final Class<?> clazz) {
        super(String.format("Could not find a handler for type %s", clazz.getSimpleName()));
    }
}
