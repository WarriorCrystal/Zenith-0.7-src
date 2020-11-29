// 
// Decompiled by Procyon v0.5.36
// 

package baritone;

import baritone.api.command.helpers.TabCompleteHelper;
import java.util.stream.Stream;
import baritone.api.command.exception.CommandUnhandledException;
import baritone.api.command.exception.ICommandException;
import baritone.api.command.argument.IArgConsumer;
import baritone.api.command.argument.ICommandArgument;
import java.util.Iterator;
import java.util.Locale;
import java.util.function.Consumer;
import java.util.List;
import java.util.Collections;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Arrays;
import baritone.api.IBaritone;
import baritone.api.command.Command;
import java.util.Objects;
import baritone.api.command.ICommand;
import baritone.api.command.registry.Registry;
import baritone.api.command.manager.ICommandManager;

public final class cd implements ICommandManager
{
    private final Registry<ICommand> a;
    private final a a;
    
    public cd(final a a) {
        this.a = new Registry<ICommand>();
        Objects.requireNonNull(this.a = a);
        final ArrayList<Command> list = new ArrayList<Command>(Arrays.asList(new bi(a), new bw(a), new aq(a, Arrays.asList("modified", "mod", "baritone", "modifiedsettings"), "List modified settings", "set modified"), new aq(a, "reset", "Reset all settings or just one", "set reset"), new bg(a), new bh(a), new bl(a), new bm(a), new ca(a), new bp(a), new am(a), new br(a), new ap(a), new ak(a), new be(a), new bf(a), new bj(a), new by(a), new bo(a), new az(a), new an(a), new bb(a), new ay(a), new bn(a), new bq(a), new ax(a), new al(a), new ba(a), new bk(a), new ao(a), new bx(a), new cb(a), new aq(a, "sethome", "Sets your home waypoint", "waypoints save home"), new aq(a, "home", "Set goal to your home waypoint", "waypoints goal home"), new bs(a)));
        final ar ar = new ar(a);
        list.add(ar.a);
        list.add(ar.b);
        list.add(ar.c);
        list.add(ar.d);
        Collections.unmodifiableList((List<?>)list).forEach(this.a::register);
    }
    
    @Override
    public final IBaritone getBaritone() {
        return this.a;
    }
    
    @Override
    public final Registry<ICommand> getRegistry() {
        return this.a;
    }
    
    @Override
    public final ICommand getCommand(final String s) {
        final Iterator<ICommand> iterator = this.a.entries.iterator();
        while (iterator.hasNext()) {
            final ICommand command;
            if ((command = iterator.next()).getNames().contains(s.toLowerCase(Locale.US))) {
                return command;
            }
        }
        return null;
    }
    
    @Override
    public final boolean execute(final String s) {
        return this.execute(a(s, false));
    }
    
    @Override
    public final boolean execute(rr<String, List<ICommandArgument>> a) {
        if ((a = this.a((rr<String, List<ICommandArgument>>)a)) != null) {
            final ce ce = a;
            try {
                ce.a.execute(ce.a, ce.a);
            }
            catch (Throwable t2) {
                final Throwable t = t2;
                ((t2 instanceof ICommandException) ? t : new CommandUnhandledException(t)).handle(ce.a, ce.a.getArgs());
            }
        }
        return a != null;
    }
    
    @Override
    public final Stream<String> tabComplete(final rr<String, List<ICommandArgument>> rr) {
        final ce a;
        if ((a = this.a(rr)) == null) {
            return Stream.empty();
        }
        return a.a();
    }
    
    @Override
    public final Stream<String> tabComplete(final String s) {
        final rr<String, List<ICommandArgument>> a;
        final String s2 = (String)(a = a(s, (boolean)(1 != 0))).a();
        if (((List)a.b()).isEmpty()) {
            return new TabCompleteHelper().addCommands(this.a.a).filterPrefix(s2).stream();
        }
        return this.tabComplete(a);
    }
    
    private ce a(final rr<String, List<ICommandArgument>> rr) {
        final String s = (String)rr.a();
        final ag ag = new ag(this, (List<ICommandArgument>)rr.b());
        final ICommand command;
        if ((command = this.getCommand(s)) == null) {
            return null;
        }
        return new ce(command, s, ag, (byte)0);
    }
    
    private static rr<String, List<ICommandArgument>> a(final String s, final boolean b) {
        final String s2 = s.split("\\s", 2)[0];
        return (rr<String, List<ICommandArgument>>)new rr((Object)s2, (Object)aj.a(s.substring(s2.length()), b));
    }
    
    public static rr<String, List<ICommandArgument>> a(final String s) {
        return a(s, false);
    }
}
