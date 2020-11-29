// 
// Decompiled by Procyon v0.5.36
// 

package baritone;

import java.util.Arrays;
import java.util.List;
import baritone.api.command.datatypes.BlockById;
import java.util.stream.Stream;
import baritone.api.utils.BlockOptionalMeta;
import baritone.api.command.datatypes.ForBlockOptionalMeta;
import java.util.ArrayList;
import baritone.api.command.argument.IArgConsumer;
import baritone.api.IBaritone;
import baritone.api.command.Command;

public final class bk extends Command
{
    public bk(final IBaritone baritone) {
        super(baritone, new String[] { "mine" });
    }
    
    @Override
    public final void execute(final String s, final IArgConsumer argConsumer) {
        final int intValue = argConsumer.getAsOrDefault(Integer.class, 0);
        argConsumer.requireMin(1);
        final ArrayList<Object> list = new ArrayList<Object>();
        while (argConsumer.hasAny()) {
            list.add(argConsumer.getDatatypeFor(ForBlockOptionalMeta.INSTANCE));
        }
        w.a.repack(this.ctx);
        this.logDirect(String.format("Mining %s", list.toString()));
        this.baritone.getMineProcess().mine(intValue, (BlockOptionalMeta[])list.toArray(new BlockOptionalMeta[0]));
    }
    
    @Override
    public final Stream<String> tabComplete(final String s, final IArgConsumer argConsumer) {
        return argConsumer.tabCompleteDatatype(BlockById.INSTANCE);
    }
    
    @Override
    public final String getShortDesc() {
        return "Mine some blocks";
    }
    
    @Override
    public final List<String> getLongDesc() {
        return Arrays.asList("The mine command allows you to tell Baritone to search for and mine individual blocks.", "", "The specified blocks can be ores (which are commonly cached), or any other block.", "", "Also see the legitMine settings (see #set l legitMine).", "", "Usage:", "> mine diamond_ore - Mines all diamonds it can find.", "> mine redstone_ore lit_redstone_ore - Mines redstone ore.", "> mine log:0 - Mines only oak logs.");
    }
}
