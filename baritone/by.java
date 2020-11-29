// 
// Decompiled by Procyon v0.5.36
// 

package baritone;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import baritone.api.pathing.goals.Goal;
import baritone.api.pathing.goals.GoalStrictDirection;
import baritone.api.command.argument.ICommandArgument;
import baritone.api.command.argument.IArgConsumer;
import baritone.api.IBaritone;
import baritone.api.command.Command;

public final class by extends Command
{
    public by(final IBaritone baritone) {
        super(baritone, new String[] { "tunnel" });
    }
    
    @Override
    public final void execute(final String s, final IArgConsumer argConsumer) {
        argConsumer.requireMax(3);
        if (argConsumer.hasExactly(3)) {
            boolean b = true;
            int int1 = Integer.parseInt(argConsumer.getArgs().get(0).getValue());
            int int2 = Integer.parseInt(argConsumer.getArgs().get(1).getValue());
            final int int3 = Integer.parseInt(argConsumer.getArgs().get(2).getValue());
            if (int2 <= 0 || int1 < 2 || int3 <= 0 || int1 > 255) {
                this.logDirect("Width and depth must at least be 1 block; Height must at least be 2 blocks, and cannot be greater than the build limit.");
                b = false;
            }
            if (b) {
                --int1;
                --int2;
                final fa bt = this.ctx.player().bt();
                final int n = (int2 % 2 != 0) ? 1 : 0;
                et et = null;
                et et2 = null;
                switch (bz.a[bt.ordinal()]) {
                    case 1: {
                        et = new et(this.ctx.playerFeet().a, this.ctx.playerFeet().b, this.ctx.playerFeet().c - int2 / 2);
                        et2 = new et(this.ctx.playerFeet().a + int3, this.ctx.playerFeet().b + int1, this.ctx.playerFeet().c + int2 / 2 + n);
                        break;
                    }
                    case 2: {
                        et = new et(this.ctx.playerFeet().a, this.ctx.playerFeet().b, this.ctx.playerFeet().c + int2 / 2 + n);
                        et2 = new et(this.ctx.playerFeet().a - int3, this.ctx.playerFeet().b + int1, this.ctx.playerFeet().c - int2 / 2);
                        break;
                    }
                    case 3: {
                        et = new et(this.ctx.playerFeet().a - int2 / 2, this.ctx.playerFeet().b, this.ctx.playerFeet().c);
                        et2 = new et(this.ctx.playerFeet().a + int2 / 2 + n, this.ctx.playerFeet().b + int1, this.ctx.playerFeet().c - int3);
                        break;
                    }
                    case 4: {
                        et = new et(this.ctx.playerFeet().a + int2 / 2 + n, this.ctx.playerFeet().b, this.ctx.playerFeet().c);
                        et2 = new et(this.ctx.playerFeet().a - int2 / 2, this.ctx.playerFeet().b + int1, this.ctx.playerFeet().c + int3);
                        break;
                    }
                    default: {
                        throw new IllegalStateException("Unexpected value: ".concat(String.valueOf(bt)));
                    }
                }
                this.logDirect(String.format("Creating a tunnel %s block(s) high, %s block(s) wide, and %s block(s) deep", int1 + 1, int2 + 1, int3));
                this.baritone.getBuilderProcess().clearArea(et, et2);
            }
            return;
        }
        final GoalStrictDirection goalAndPath = new GoalStrictDirection(this.ctx.playerFeet(), this.ctx.player().bt());
        this.baritone.getCustomGoalProcess().setGoalAndPath(goalAndPath);
        this.logDirect(String.format("Goal: %s", goalAndPath.toString()));
    }
    
    @Override
    public final Stream<String> tabComplete(final String s, final IArgConsumer argConsumer) {
        return Stream.empty();
    }
    
    @Override
    public final String getShortDesc() {
        return "Set a goal to tunnel in your current direction";
    }
    
    @Override
    public final List<String> getLongDesc() {
        return Arrays.asList("The tunnel command sets a goal that tells Baritone to mine completely straight in the direction that you're facing.", "", "Usage:", "> tunnel - No arguments, mines in a 1x2 radius.", "> tunnel <height> <width> <depth> - Tunnels in a user defined height, width and depth.");
    }
}
