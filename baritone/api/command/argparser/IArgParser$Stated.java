// 
// Decompiled by Procyon v0.5.36
// 

package baritone.api.command.argparser;

import baritone.api.command.argument.ICommandArgument;

public interface IArgParser$Stated<T, S> extends IArgParser<T>
{
    Class<S> getStateType();
    
    T parseArg(final ICommandArgument p0, final S p1);
}
