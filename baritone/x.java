// 
// Decompiled by Procyon v0.5.36
// 

package baritone;

import baritone.api.BaritoneAPI;
import baritone.api.utils.SettingsUtil;
import baritone.api.command.helpers.TabCompleteHelper;
import java.util.stream.Stream;
import baritone.api.event.events.TabCompleteEvent;
import java.util.Iterator;
import baritone.api.command.exception.CommandNotEnoughArgumentsException;
import java.util.Locale;
import baritone.api.Settings$Setting;
import java.net.URISyntaxException;
import java.net.URI;
import baritone.api.command.argument.ICommandArgument;
import java.util.List;
import baritone.api.command.ICommand;
import baritone.api.command.exception.CommandNotFoundException;
import baritone.api.command.IBaritoneChatControl;
import baritone.api.event.events.ChatEvent;
import baritone.api.event.listener.IGameEventListener;
import baritone.api.IBaritone;
import baritone.api.command.manager.ICommandManager;
import baritone.api.Settings;
import baritone.api.utils.Helper;
import baritone.api.event.listener.AbstractGameEventListener;

public final class x implements AbstractGameEventListener, Helper
{
    private static final Settings a;
    private final ICommandManager a;
    
    public x(final IBaritone baritone) {
        this.a = baritone.getCommandManager();
        baritone.getGameEventHandler().registerEventListener(this);
    }
    
    @Override
    public final void onSendChatMessage(final ChatEvent chatEvent) {
        final String message = chatEvent.getMessage();
        final String prefix = x.a.prefix.value;
        final boolean startsWith = message.startsWith(IBaritoneChatControl.FORCE_COMMAND_PREFIX);
        if ((x.a.prefixControl.value && message.startsWith(prefix)) || startsWith) {
            chatEvent.cancel();
            final String substring = message.substring(startsWith ? IBaritoneChatControl.FORCE_COMMAND_PREFIX.length() : prefix.length());
            if (!this.a(substring) && !substring.trim().isEmpty()) {
                new CommandNotFoundException((String)cd.a(substring).a()).handle(null, null);
            }
            return;
        }
        if ((x.a.chatControl.value || x.a.chatControlAnyway.value) && this.a(message)) {
            chatEvent.cancel();
        }
    }
    
    private void a(String s, String string) {
        if (x.a.echoCommands.value) {
            string = s + string;
            s = (x.a.censorRanCommands.value ? (s + " ...") : string);
            final ho ho;
            ((hh)(ho = new ho(String.format("> %s", s)))).b().a(a.p).a(new hj(hj$a.a, (hh)new ho("Click to rerun command"))).a(new hg(hg$a.c, IBaritoneChatControl.FORCE_COMMAND_PREFIX + string));
            this.logDirect((hh)ho);
        }
    }
    
    private boolean a(String substring) {
        while (!substring.trim().equalsIgnoreCase("damn")) {
            if (substring.trim().equalsIgnoreCase("orderpizza")) {
                try {
                    ((gf)x.mc.m).openLink(new URI("https://www.dominos.com/en/pages/order/"));
                }
                catch (NullPointerException | URISyntaxException ex) {}
                return false;
            }
            if (!substring.isEmpty()) {
                final rr<String, List<ICommandArgument>> a;
                final String s = (String)(a = cd.a(substring)).a();
                substring = substring.substring(((String)a.a()).length());
                final ag ag;
                if (!(ag = new ag(this.a, (List<ICommandArgument>)a.b())).hasAny()) {
                    final Settings$Setting<?> settings$Setting;
                    if ((settings$Setting = x.a.byLowerName.get(s.toLowerCase(Locale.US))) != null) {
                        this.a(s, substring);
                        if (settings$Setting.getValueClass() == Boolean.class) {
                            this.a.execute(String.format("set toggle %s", settings$Setting.getName()));
                        }
                        else {
                            this.a.execute(String.format("set %s", settings$Setting.getName()));
                        }
                        return true;
                    }
                }
                else if (ag.hasExactlyOne()) {
                    final Iterator<Settings$Setting<?>> iterator = x.a.allSettings.iterator();
                    while (iterator.hasNext()) {
                        final Settings$Setting<?> settings$Setting2;
                        if (!(settings$Setting2 = iterator.next()).getName().equals("logger")) {
                            if (settings$Setting2.getName().equalsIgnoreCase((String)a.a())) {
                                this.a(s, substring);
                                try {
                                    this.a.execute(String.format("set %s %s", settings$Setting2.getName(), ag.getString()));
                                }
                                catch (CommandNotEnoughArgumentsException ex2) {}
                                return true;
                            }
                            continue;
                        }
                    }
                }
                if (this.a.getCommand((String)a.a()) != null) {
                    this.a(s, substring);
                }
                return this.a.execute(a);
            }
            final x x = this;
            substring = "help";
            this = x;
        }
        this.logDirect("daniel");
        return false;
    }
    
    @Override
    public final void onPreTabComplete(final TabCompleteEvent tabCompleteEvent) {
        if (!x.a.prefixControl.value) {
            return;
        }
        final String prefix = tabCompleteEvent.prefix;
        final String s = x.a.prefix.value;
        if (!prefix.startsWith(s)) {
            return;
        }
        final String substring;
        final List<ICommandArgument> a = aj.a(substring = prefix.substring(s.length()), true);
        Object o = this.a(substring);
        if (a.size() == 1) {
            o = ((Stream<String>)o).map(str -> s + str);
        }
        tabCompleteEvent.completions = ((Stream<String>)o).toArray(String[]::new);
    }
    
    private Stream<String> a(final String s) {
        try {
            final ag ag;
            if ((ag = new ag(this.a, aj.a(s, true))).hasAtMost(2)) {
                if (ag.hasExactly(1)) {
                    return new TabCompleteHelper().addCommands(this.a).addSettings().filterPrefix(ag.getString()).stream();
                }
                final Settings$Setting<?> settings$Setting;
                if ((settings$Setting = x.a.byLowerName.get(ag.getString().toLowerCase(Locale.US))) != null) {
                    if (settings$Setting.getValueClass() == Boolean.class) {
                        final TabCompleteHelper tabCompleteHelper = new TabCompleteHelper();
                        if (settings$Setting.value) {
                            tabCompleteHelper.append("true", "false");
                        }
                        else {
                            tabCompleteHelper.append("false", "true");
                        }
                        return tabCompleteHelper.filterPrefix(ag.getString()).stream();
                    }
                    return Stream.of(SettingsUtil.settingValueToString(settings$Setting));
                }
            }
            return this.a.tabComplete(s);
        }
        catch (CommandNotEnoughArgumentsException ex) {
            return Stream.empty();
        }
    }
    
    static {
        a = BaritoneAPI.getSettings();
    }
}
