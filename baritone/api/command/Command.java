// 
// Decompiled by Procyon v0.5.36
// 

package baritone.api.command;

import java.util.Collections;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.Locale;
import java.util.stream.Stream;
import java.util.List;
import baritone.api.utils.IPlayerContext;
import baritone.api.IBaritone;

public abstract class Command implements ICommand
{
    protected IBaritone baritone;
    protected IPlayerContext ctx;
    protected final List<String> names;
    
    protected Command(final IBaritone baritone, final String... values) {
        this.names = Collections.unmodifiableList((List<? extends String>)Stream.of(values).map(s -> s.toLowerCase(Locale.US)).collect((Collector<? super Object, ?, List<? extends T>>)Collectors.toList()));
        this.baritone = baritone;
        this.ctx = baritone.getPlayerContext();
    }
    
    @Override
    public final List<String> getNames() {
        return this.names;
    }
}
