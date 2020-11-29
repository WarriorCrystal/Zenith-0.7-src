// 
// Decompiled by Procyon v0.5.36
// 

package baritone;

import baritone.api.command.exception.CommandInvalidTypeException;
import baritone.api.command.exception.CommandNoParserForTypeException;
import baritone.api.command.argument.ICommandArgument;
import baritone.api.command.argparser.IArgParser$Stated;
import java.util.function.Function;
import java.util.function.Predicate;
import baritone.api.command.argparser.IArgParser$Stateless;
import java.util.function.Consumer;
import baritone.api.command.argparser.IArgParser;
import baritone.api.command.registry.Registry;
import baritone.api.command.argparser.IArgParserManager;

public enum z implements IArgParserManager
{
    a;
    
    private Registry<IArgParser> a;
    
    private z() {
        this.a = new Registry<IArgParser>();
        aa.a.forEach(this.a::register);
    }
    
    @Override
    public final <T> IArgParser$Stateless<T> getParserStateless(final Class<T> clazz) {
        return this.a.descendingStream().filter(IArgParser$Stateless.class::isInstance).map((Function<? super IArgParser, ? extends IArgParser$Stateless<T>>)IArgParser$Stateless.class::cast).filter(argParser$Stateless -> argParser$Stateless.getTarget().isAssignableFrom(clazz)).findFirst().orElse(null);
    }
    
    @Override
    public final <T, S> IArgParser$Stated<T, S> getParserStated(final Class<T> clazz, final Class<S> clazz2) {
        return this.a.descendingStream().filter(IArgParser$Stated.class::isInstance).map((Function<? super IArgParser, ?>)IArgParser$Stated.class::cast).filter(argParser$Stated -> argParser$Stated.getTarget().isAssignableFrom(clazz)).filter(argParser$Stated2 -> argParser$Stated2.getStateType().isAssignableFrom(clazz2)).map((Function<? super Object, ? extends IArgParser$Stated<T, S>>)IArgParser$Stated.class::cast).findFirst().orElse(null);
    }
    
    @Override
    public final <T> T parseStateless(final Class<T> clazz, final ICommandArgument commandArgument) {
        final IArgParser$Stateless<T> parserStateless;
        if ((parserStateless = this.getParserStateless(clazz)) == null) {
            throw new CommandNoParserForTypeException(clazz);
        }
        try {
            return parserStateless.parseArg(commandArgument);
        }
        catch (Exception ex) {
            throw new CommandInvalidTypeException(commandArgument, clazz.getSimpleName());
        }
    }
    
    @Override
    public final <T, S> T parseStated(final Class<T> clazz, final Class<S> clazz2, final ICommandArgument commandArgument, final S n) {
        final IArgParser$Stated<T, S> parserStated;
        if ((parserStated = this.getParserStated(clazz, clazz2)) == null) {
            throw new CommandNoParserForTypeException(clazz);
        }
        try {
            return parserStated.parseArg(commandArgument, n);
        }
        catch (Exception ex) {
            throw new CommandInvalidTypeException(commandArgument, clazz.getSimpleName());
        }
    }
    
    @Override
    public final Registry<IArgParser> getRegistry() {
        return this.a;
    }
}
