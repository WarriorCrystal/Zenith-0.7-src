// 
// Decompiled by Procyon v0.5.36
// 

package baritone.api.command.argparser;

import baritone.api.command.argument.ICommandArgument;

public interface IArgParser$Stateless<T> extends IArgParser<T>
{
    T parseArg(final ICommandArgument p0);
}
