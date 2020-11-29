// 
// Decompiled by Procyon v0.5.36
// 

package baritone;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import baritone.api.utils.BetterBlockPos;
import baritone.api.command.argument.IArgConsumer;
import baritone.api.IBaritone;
import baritone.api.command.Command;

public final class bo extends Command
{
    public bo(final IBaritone baritone) {
        super(baritone, new String[] { "render" });
    }
    
    @Override
    public final void execute(final String s, final IArgConsumer argConsumer) {
        argConsumer.requireMax(0);
        final BetterBlockPos playerFeet = this.ctx.playerFeet();
        final int n = bo.mc.t.e + 1 << 4;
        bo.mc.g.a(playerFeet.a - n, 0, playerFeet.c - n, playerFeet.a + n, 255, playerFeet.c + n);
        this.logDirect("Done");
    }
    
    @Override
    public final Stream<String> tabComplete(final String s, final IArgConsumer argConsumer) {
        return Stream.empty();
    }
    
    @Override
    public final String getShortDesc() {
        return "Fix glitched chunks";
    }
    
    @Override
    public final List<String> getLongDesc() {
        return Arrays.asList("The render command fixes glitched chunk rendering without having to reload all of them.", "", "Usage:", "> render");
    }
}
