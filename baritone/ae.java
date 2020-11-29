// 
// Decompiled by Procyon v0.5.36
// 

package baritone;

import baritone.api.command.argument.ICommandArgument;
import baritone.api.command.argparser.IArgParser$Stateless;

public enum ae implements IArgParser$Stateless<Integer>
{
    a;
    
    @Override
    public final Class<Integer> getTarget() {
        return Integer.class;
    }
}
