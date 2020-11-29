// 
// Decompiled by Procyon v0.5.36
// 

package baritone.api.command;

import java.util.List;
import java.util.stream.Stream;
import baritone.api.command.argument.IArgConsumer;
import baritone.api.utils.Helper;

public interface ICommand extends Helper
{
    void execute(final String p0, final IArgConsumer p1);
    
    Stream<String> tabComplete(final String p0, final IArgConsumer p1);
    
    String getShortDesc();
    
    List<String> getLongDesc();
    
    List<String> getNames();
    
    default boolean hiddenFromHelp() {
        return false;
    }
}
