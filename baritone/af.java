// 
// Decompiled by Procyon v0.5.36
// 

package baritone;

import baritone.api.command.argument.ICommandArgument;
import baritone.api.command.argparser.IArgParser$Stateless;

public enum af implements IArgParser$Stateless<Long>
{
    a;
    
    @Override
    public final Class<Long> getTarget() {
        return Long.class;
    }
}
