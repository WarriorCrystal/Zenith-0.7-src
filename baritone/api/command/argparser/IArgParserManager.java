// 
// Decompiled by Procyon v0.5.36
// 

package baritone.api.command.argparser;

import baritone.api.command.registry.Registry;
import baritone.api.command.argument.ICommandArgument;

public interface IArgParserManager
{
     <T> IArgParser$Stateless<T> getParserStateless(final Class<T> p0);
    
     <T, S> IArgParser$Stated<T, S> getParserStated(final Class<T> p0, final Class<S> p1);
    
     <T> T parseStateless(final Class<T> p0, final ICommandArgument p1);
    
     <T, S> T parseStated(final Class<T> p0, final Class<S> p1, final ICommandArgument p2, final S p3);
    
    Registry<IArgParser> getRegistry();
}
