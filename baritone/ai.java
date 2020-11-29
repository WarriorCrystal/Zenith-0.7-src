// 
// Decompiled by Procyon v0.5.36
// 

package baritone;

import baritone.api.command.exception.CommandInvalidTypeException;
import java.util.stream.Stream;
import baritone.api.command.argument.ICommandArgument;

final class ai implements ICommandArgument
{
    private final int a;
    private final String a;
    private final String b;
    
    ai(final int a, final String a2, final String b) {
        this.a = a;
        this.a = a2;
        this.b = b;
    }
    
    @Override
    public final int getIndex() {
        return this.a;
    }
    
    @Override
    public final String getValue() {
        return this.a;
    }
    
    @Override
    public final String getRawRest() {
        return this.b;
    }
    
    @Override
    public final <E extends Enum<?>> E getEnum(final Class<E> clazz) {
        return Stream.of(clazz.getEnumConstants()).filter(enum1 -> enum1.name().equalsIgnoreCase(this.a)).findFirst().orElseThrow(() -> new CommandInvalidTypeException(this, clazz.getSimpleName()));
    }
    
    @Override
    public final <T> T getAs(final Class<T> clazz) {
        return z.a.parseStateless(clazz, this);
    }
    
    @Override
    public final <T> boolean is(final Class<T> clazz) {
        try {
            this.getAs(clazz);
            return true;
        }
        catch (Throwable t) {
            return false;
        }
    }
    
    @Override
    public final <T, S> T getAs(final Class<T> clazz, final Class<S> clazz2, final S n) {
        return z.a.parseStated(clazz, clazz2, this, n);
    }
    
    @Override
    public final <T, S> boolean is(final Class<T> clazz, final Class<S> clazz2, final S n) {
        try {
            this.getAs(clazz, clazz2, n);
            return true;
        }
        catch (Throwable t) {
            return false;
        }
    }
}
