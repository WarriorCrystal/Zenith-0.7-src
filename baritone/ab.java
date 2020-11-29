// 
// Decompiled by Procyon v0.5.36
// 

package baritone;

import java.util.Arrays;
import java.util.Locale;
import baritone.api.command.argument.ICommandArgument;
import java.util.List;
import baritone.api.command.argparser.IArgParser$Stateless;

public final class ab implements IArgParser$Stateless<Boolean>
{
    public static final ab a;
    private static List<String> a;
    private static List<String> b;
    
    @Override
    public final Class<Boolean> getTarget() {
        return Boolean.class;
    }
    
    static {
        a = new ab();
        ab.a = Arrays.asList("1", "true", "yes", "t", "y", "on", "enable");
        ab.b = Arrays.asList("0", "false", "no", "f", "n", "off", "disable");
    }
}
