// 
// Decompiled by Procyon v0.5.36
// 

package baritone;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import java.util.Iterator;
import java.util.Set;
import baritone.api.utils.BetterBlockPos;
import baritone.api.cache.IRememberedInventory;
import java.util.Map;
import baritone.api.command.exception.CommandInvalidStateException;
import baritone.api.command.argument.IArgConsumer;
import baritone.api.IBaritone;
import baritone.api.command.Command;

public final class an extends Command
{
    public an(final IBaritone baritone) {
        super(baritone, new String[] { "chests" });
    }
    
    @Override
    public final void execute(final String s, final IArgConsumer argConsumer) {
        argConsumer.requireMax(0);
        final Set<Map.Entry<et, IRememberedInventory>> entrySet;
        if ((entrySet = this.ctx.worldData().getContainerMemory().getRememberedInventories().entrySet()).isEmpty()) {
            throw new CommandInvalidStateException("No remembered inventories");
        }
        for (final Map.Entry<et, IRememberedInventory> entry : entrySet) {
            final BetterBlockPos betterBlockPos = new BetterBlockPos(entry.getKey());
            final IRememberedInventory rememberedInventory = entry.getValue();
            this.logDirect(betterBlockPos.toString());
            final Iterator<aip> iterator2 = rememberedInventory.getContents().iterator();
            while (iterator2.hasNext()) {
                final aip aip;
                final hh c;
                (c = (aip = iterator2.next()).C()).a(String.format(" x %d", aip.E()));
                this.logDirect(c);
            }
        }
    }
    
    @Override
    public final Stream<String> tabComplete(final String s, final IArgConsumer argConsumer) {
        return Stream.empty();
    }
    
    @Override
    public final String getShortDesc() {
        return "Display remembered inventories";
    }
    
    @Override
    public final List<String> getLongDesc() {
        return Arrays.asList("The chests command lists remembered inventories, I guess?", "", "Usage:", "> chests");
    }
}
