// 
// Decompiled by Procyon v0.5.36
// 

package baritone;

import java.util.Collections;
import java.util.stream.Stream;
import baritone.api.command.argument.IArgConsumer;
import java.util.List;
import baritone.api.IBaritone;
import baritone.api.command.Command;

public final class aq extends Command
{
    private final String a;
    private String b;
    
    public aq(final IBaritone baritone, final List<String> list, final String a, final String b) {
        super(baritone, (String[])list.toArray(new String[0]));
        this.a = a;
        this.b = b;
    }
    
    public aq(final IBaritone baritone, final String s, final String a, final String b) {
        super(baritone, new String[] { s });
        this.a = a;
        this.b = b;
    }
    
    @Override
    public final void execute(final String s, final IArgConsumer argConsumer) {
        this.baritone.getCommandManager().execute(String.format("%s %s", this.b, argConsumer.rawRest()));
    }
    
    @Override
    public final Stream<String> tabComplete(final String s, final IArgConsumer argConsumer) {
        return this.baritone.getCommandManager().tabComplete(String.format("%s %s", this.b, argConsumer.rawRest()));
    }
    
    @Override
    public final String getShortDesc() {
        return this.a;
    }
    
    @Override
    public final List<String> getLongDesc() {
        return Collections.singletonList(String.format("This command is an alias, for: %s ...", this.b));
    }
}
