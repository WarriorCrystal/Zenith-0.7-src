// 
// Decompiled by Procyon v0.5.36
// 

package baritone;

import java.util.Arrays;
import java.util.List;
import baritone.api.command.helpers.TabCompleteHelper;
import java.util.stream.Stream;
import baritone.api.schematic.AbstractSchematic;
import baritone.api.schematic.ReplaceSchematic;
import baritone.api.schematic.ShellSchematic;
import baritone.api.schematic.ISchematic;
import baritone.api.schematic.WallsSchematic;
import baritone.api.schematic.FillSchematic;
import baritone.api.schematic.CompositeSchematic;
import baritone.api.utils.BlockOptionalMetaLookup;
import java.util.ArrayList;
import baritone.api.command.datatypes.ForBlockOptionalMeta;
import baritone.api.utils.BlockOptionalMeta;
import baritone.api.selection.ISelection;
import baritone.api.command.datatypes.ForEnumFacing;
import baritone.api.command.datatypes.RelativeBlockPos;
import baritone.api.command.exception.CommandInvalidStateException;
import baritone.api.command.exception.CommandInvalidTypeException;
import baritone.api.command.argument.IArgConsumer;
import baritone.api.event.listener.IGameEventListener;
import baritone.api.IBaritone;
import baritone.api.utils.BetterBlockPos;
import baritone.api.selection.ISelectionManager;
import baritone.api.command.Command;

public final class bs extends Command
{
    private ISelectionManager a;
    private BetterBlockPos a;
    
    public bs(final IBaritone baritone) {
        super(baritone, new String[] { "sel", "selection", "s" });
        this.a = this.baritone.getSelectionManager();
        this.a = null;
        baritone.getGameEventHandler().registerEventListener(new bt(this));
    }
    
    @Override
    public final void execute(final String s, final IArgConsumer argConsumer) {
        final bu a;
        if ((a = bu.a(argConsumer.getString())) == null) {
            throw new CommandInvalidTypeException(argConsumer.consumed(), "an action");
        }
        if (a == bu.a || a == bu.b) {
            if (a == bu.b && this.a == null) {
                throw new CommandInvalidStateException("Set pos1 first before using pos2");
            }
            final BetterBlockPos betterBlockPos = (bs.mc.aa() != null) ? BetterBlockPos.from(new et(bs.mc.aa())) : this.ctx.playerFeet();
            final BetterBlockPos a2 = argConsumer.hasAny() ? argConsumer.getDatatypePost(RelativeBlockPos.INSTANCE, betterBlockPos) : betterBlockPos;
            argConsumer.requireMax(0);
            if (a == bu.a) {
                this.a = a2;
                this.logDirect("Position 1 has been set");
                return;
            }
            this.a.addSelection(this.a, a2);
            this.a = null;
            this.logDirect("Selection added");
        }
        else {
            if (a == bu.c) {
                argConsumer.requireMax(0);
                this.a = null;
                this.logDirect(String.format("Removed %d selections", this.a.removeAllSelections().length));
                return;
            }
            if (a == bu.d) {
                argConsumer.requireMax(0);
                if (this.a != null) {
                    this.a = null;
                    this.logDirect("Undid pos1");
                    return;
                }
                final ISelection[] selections;
                if ((selections = this.a.getSelections()).length <= 0) {
                    throw new CommandInvalidStateException("Nothing to undo!");
                }
                final ISelectionManager a3 = this.a;
                final ISelection[] array = selections;
                this.a = a3.removeSelection(array[array.length - 1]).pos1();
                this.logDirect("Undid pos2");
            }
            else {
                if (a != bu.e && a != bu.f && a != bu.g && a != bu.h && a != bu.i) {
                    if (a == bu.j || a == bu.k || a == bu.l) {
                        argConsumer.requireExactly(3);
                        final bv a4;
                        if ((a4 = bv.a(argConsumer.getString())) == null) {
                            throw new CommandInvalidStateException("Invalid transform type");
                        }
                        final fa fa = argConsumer.getDatatypeFor(ForEnumFacing.INSTANCE);
                        final int intValue = argConsumer.getAs(Integer.class);
                        final ISelection[] selections2;
                        if ((selections2 = this.a.getSelections()).length <= 0) {
                            throw new CommandInvalidStateException("No selections found");
                        }
                        ISelection[] array3;
                        ISelection[] array2;
                        for (int length = (array2 = (array3 = a4.a.apply(selections2))).length, i = 0; i < length; ++i) {
                            final ISelection selection = array2[i];
                            if (a == bu.j) {
                                this.a.expand(selection, fa, intValue);
                            }
                            else if (a == bu.k) {
                                this.a.contract(selection, fa, intValue);
                            }
                            else {
                                this.a.shift(selection, fa, intValue);
                            }
                        }
                        this.logDirect(String.format("Transformed %d selections", array3.length));
                    }
                    return;
                }
                BlockOptionalMeta blockOptionalMeta = (a == bu.h) ? new BlockOptionalMeta(aox.a) : argConsumer.getDatatypeFor(ForBlockOptionalMeta.INSTANCE);
                BlockOptionalMetaLookup blockOptionalMetaLookup = null;
                if (a == bu.i) {
                    argConsumer.requireMin(1);
                    final ArrayList<Object> list;
                    (list = new ArrayList<Object>()).add(blockOptionalMeta);
                    while (argConsumer.has(2)) {
                        list.add(argConsumer.getDatatypeFor(ForBlockOptionalMeta.INSTANCE));
                    }
                    blockOptionalMeta = argConsumer.getDatatypeFor(ForBlockOptionalMeta.INSTANCE);
                    blockOptionalMetaLookup = new BlockOptionalMetaLookup((BlockOptionalMeta[])list.toArray(new BlockOptionalMeta[0]));
                }
                else {
                    argConsumer.requireMax(0);
                }
                final ISelection[] selections3;
                if ((selections3 = this.a.getSelections()).length == 0) {
                    throw new CommandInvalidStateException("No selections");
                }
                BetterBlockPos min = selections3[0].min();
                final CompositeSchematic compositeSchematic = new CompositeSchematic(0, 0, 0);
                ISelection[] array4;
                for (int length2 = (array4 = selections3).length, j = 0; j < length2; ++j) {
                    final BetterBlockPos min2 = array4[j].min();
                    min = new BetterBlockPos(Math.min(min.a, min2.a), Math.min(min.b, min2.b), Math.min(min.c, min2.c));
                }
                ISelection[] array5;
                for (int length3 = (array5 = selections3).length, k = 0; k < length3; ++k) {
                    final ISelection selection2;
                    final fq size = (selection2 = array5[k]).size();
                    final BetterBlockPos min3 = selection2.min();
                    AbstractSchematic abstractSchematic = new FillSchematic(size.p(), size.q(), size.r(), blockOptionalMeta);
                    if (a == bu.f) {
                        abstractSchematic = new WallsSchematic(abstractSchematic);
                    }
                    else if (a == bu.g) {
                        abstractSchematic = new ShellSchematic(abstractSchematic);
                    }
                    else if (a == bu.i) {
                        abstractSchematic = new ReplaceSchematic(abstractSchematic, blockOptionalMetaLookup);
                    }
                    compositeSchematic.put(abstractSchematic, min3.a - min.a, min3.b - min.b, min3.c - min.c);
                }
                this.baritone.getBuilderProcess().build("Fill", compositeSchematic, (fq)min);
                this.logDirect("Filling now");
            }
        }
    }
    
    @Override
    public final Stream<String> tabComplete(final String s, final IArgConsumer argConsumer) {
        if (argConsumer.hasExactlyOne()) {
            return new TabCompleteHelper().append(bu.a()).filterPrefix(argConsumer.getString()).sortAlphabetically().stream();
        }
        final bu a;
        if ((a = bu.a(argConsumer.getString())) != null) {
            if (a == bu.a || a == bu.b) {
                if (argConsumer.hasAtMost(3)) {
                    return argConsumer.tabCompleteDatatype(RelativeBlockPos.INSTANCE);
                }
            }
            else if (a == bu.e || a == bu.f || a == bu.h || a == bu.i) {
                if (argConsumer.hasExactlyOne() || a == bu.i) {
                    while (argConsumer.has(2)) {
                        argConsumer.get();
                    }
                    return argConsumer.tabCompleteDatatype(ForBlockOptionalMeta.INSTANCE);
                }
            }
            else if (a == bu.j || a == bu.k || a == bu.l) {
                if (argConsumer.hasExactlyOne()) {
                    return new TabCompleteHelper().append(bv.a()).filterPrefix(argConsumer.getString()).sortAlphabetically().stream();
                }
                if (bv.a(argConsumer.getString()) != null && argConsumer.hasExactlyOne()) {
                    return argConsumer.tabCompleteDatatype(ForEnumFacing.INSTANCE);
                }
            }
        }
        return Stream.empty();
    }
    
    @Override
    public final String getShortDesc() {
        return "WorldEdit-like commands";
    }
    
    @Override
    public final List<String> getLongDesc() {
        return Arrays.asList("The sel command allows you to manipulate Baritone's selections, similarly to WorldEdit.", "", "Using these selections, you can clear areas, fill them with blocks, or something else.", "", "The expand/contract/shift commands use a kind of selector to choose which selections to target. Supported ones are a/all, n/newest, and o/oldest.", "", "Usage:", "> sel pos1/p1/1 - Set position 1 to your current position.", "> sel pos1/p1/1 <x> <y> <z> - Set position 1 to a relative position.", "> sel pos2/p2/2 - Set position 2 to your current position.", "> sel pos2/p2/2 <x> <y> <z> - Set position 2 to a relative position.", "", "> sel clear/c - Clear the selection.", "> sel undo/u - Undo the last action (setting positions, creating selections, etc.)", "> sel set/fill/s/f [block] - Completely fill all selections with a block.", "> sel walls/w [block] - Fill in the walls of the selection with a specified block.", "> sel shell/shl [block] - The same as walls, but fills in a ceiling and floor too.", "> sel cleararea/ca - Basically 'set air'.", "> sel replace/r <blocks...> <with> - Replaces blocks with another block.", "", "> sel expand <target> <direction> <blocks> - Expand the targets.", "> sel contract <target> <direction> <blocks> - Contract the targets.", "> sel shift <target> <direction> <blocks> - Shift the targets (does not resize).");
    }
}
