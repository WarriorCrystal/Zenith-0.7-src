// 
// Decompiled by Procyon v0.5.36
// 

package baritone.api.command.argument;

public interface ICommandArgument
{
    int getIndex();
    
    String getValue();
    
    String getRawRest();
    
     <E extends Enum<?>> E getEnum(final Class<E> p0);
    
     <T> T getAs(final Class<T> p0);
    
     <T> boolean is(final Class<T> p0);
    
     <T, S> T getAs(final Class<T> p0, final Class<S> p1, final S p2);
    
     <T, S> boolean is(final Class<T> p0, final Class<S> p1, final S p2);
}
