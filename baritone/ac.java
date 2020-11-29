// 
// Decompiled by Procyon v0.5.36
// 

package baritone;

import baritone.api.command.argument.ICommandArgument;
import baritone.api.command.argparser.IArgParser$Stateless;

public enum ac implements IArgParser$Stateless<Double>
{
    a;
    
    @Override
    public final Class<Double> getTarget() {
        return Double.class;
    }
}
