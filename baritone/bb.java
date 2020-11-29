// 
// Decompiled by Procyon v0.5.36
// 

package baritone;

import java.util.Arrays;
import java.util.List;
import baritone.api.command.datatypes.IDatatypeFor;
import baritone.api.command.helpers.TabCompleteHelper;
import java.util.stream.Stream;
import baritone.api.process.IFollowProcess;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.Locale;
import java.util.function.Predicate;
import java.util.ArrayList;
import baritone.api.command.argument.IArgConsumer;
import baritone.api.IBaritone;
import baritone.api.command.Command;

public final class bb extends Command
{
    public bb(final IBaritone baritone) {
        super(baritone, new String[] { "follow" });
    }
    
    @Override
    public final void execute(final String s, final IArgConsumer argConsumer) {
        argConsumer.requireMin(1);
        final ArrayList<Object> list = new ArrayList<Object>();
        final ArrayList<Object> list2 = new ArrayList<Object>();
        IFollowProcess followProcess;
        bc bc;
        Object a;
        if (argConsumer.hasExactlyOne()) {
            followProcess = this.baritone.getFollowProcess();
            a = (bc = argConsumer.getEnum(bc.class)).a;
        }
        else {
            argConsumer.requireMin(2);
            bc = null;
            final bd bd = argConsumer.getEnum(bd.class);
            while (argConsumer.hasAny()) {
                final Object datatype;
                if ((datatype = argConsumer.getDatatypeFor(bd.a)) instanceof Class) {
                    list2.add(datatype);
                }
                else {
                    list.add(datatype);
                }
            }
            followProcess = this.baritone.getFollowProcess();
            a = (list2.isEmpty() ? list::contains : (vg -> list2.stream().anyMatch(clazz -> clazz.isInstance(vg))));
        }
        followProcess.follow((Predicate<vg>)a);
        if (bc != null) {
            this.logDirect(String.format("Following all %s", bc.name().toLowerCase(Locale.US)));
            return;
        }
        this.logDirect("Following these types of entities:");
        if (list2.isEmpty()) {
            list.stream().map((Function<? super Object, ?>)vg::toString).forEach((Consumer<? super Object>)this::logDirect);
            return;
        }
        list2.stream().map((Function<? super Object, ?>)vi::a).map((Function<? super Object, ?>)Objects::requireNonNull).map((Function<? super Object, ?>)nf::toString).forEach((Consumer<? super Object>)this::logDirect);
    }
    
    @Override
    public final Stream<String> tabComplete(final String s, final IArgConsumer argConsumer) {
        if (argConsumer.hasExactlyOne()) {
            return new TabCompleteHelper().append(bc.class).append(bd.class).filterPrefix(argConsumer.getString()).stream();
        }
        IDatatypeFor a;
        try {
            a = argConsumer.getEnum(bd.class).a;
        }
        catch (NullPointerException ex) {
            return Stream.empty();
        }
        while (argConsumer.has(2)) {
            if (argConsumer.peekDatatypeOrNull((IDatatypeFor<Object>)a) == null) {
                return Stream.empty();
            }
            argConsumer.get();
        }
        return argConsumer.tabCompleteDatatype(a);
    }
    
    @Override
    public final String getShortDesc() {
        return "Follow entity things";
    }
    
    @Override
    public final List<String> getLongDesc() {
        return Arrays.asList("The follow command tells Baritone to follow certain kinds of entities.", "", "Usage:", "> follow entities - Follows all entities.", "> follow entity <entity1> <entity2> <...> - Follow certain entities (for example 'skeleton', 'horse' etc.)", "> follow players - Follow players", "> follow player <username1> <username2> <...> - Follow certain players");
    }
}
