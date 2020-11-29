// 
// Decompiled by Procyon v0.5.36
// 

package baritone;

import baritone.api.command.argument.ICommandArgument;
import baritone.api.command.argparser.IArgParser$Stateless;

public enum ad implements IArgParser$Stateless<Float>
{
    a;
    
    @Override
    public final Class<Float> getTarget() {
        return Float.class;
    }
}
