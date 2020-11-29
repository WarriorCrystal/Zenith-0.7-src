// 
// Decompiled by Procyon v0.5.36
// 

package baritone;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import java.util.function.Consumer;
import java.util.function.Function;
import baritone.api.utils.BetterBlockPos;
import baritone.api.command.datatypes.BlockById;
import java.util.ArrayList;
import baritone.api.command.argument.IArgConsumer;
import baritone.api.IBaritone;
import baritone.api.command.Command;

public final class ba extends Command
{
    public ba(final IBaritone baritone) {
        super(baritone, new String[] { "find" });
    }
    
    @Override
    public final void execute(final String s, final IArgConsumer argConsumer) {
        final ArrayList<Object> list = new ArrayList<Object>();
        while (argConsumer.hasAny()) {
            list.add(argConsumer.getDatatypeFor(BlockById.INSTANCE));
        }
        final BetterBlockPos betterBlockPos;
        list.stream().flatMap(aow -> {
            this.ctx.playerFeet();
            return this.ctx.worldData().getCachedWorld().getLocationsOf(((nf)aow.h.b((Object)aow)).a(), Integer.MAX_VALUE, betterBlockPos.a, betterBlockPos.b, 4).stream();
        }).map((Function<? super Object, ?>)BetterBlockPos::new).map((Function<? super Object, ?>)BetterBlockPos::toString).forEach((Consumer<? super Object>)this::logDirect);
    }
    
    @Override
    public final Stream<String> tabComplete(final String s, final IArgConsumer argConsumer) {
        return argConsumer.tabCompleteDatatype(BlockById.INSTANCE);
    }
    
    @Override
    public final String getShortDesc() {
        return "Find positions of a certain block";
    }
    
    @Override
    public final List<String> getLongDesc() {
        return Arrays.asList("", "", "Usage:", "> ");
    }
}
