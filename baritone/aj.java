// 
// Decompiled by Procyon v0.5.36
// 

package baritone;

import java.util.regex.Matcher;
import java.util.ArrayList;
import baritone.api.command.argument.ICommandArgument;
import java.util.List;
import java.util.regex.Pattern;

public final class aj
{
    private static final Pattern a;
    
    public static List<ICommandArgument> a(final String input, final boolean b) {
        final ArrayList<ai> list = (ArrayList<ai>)new ArrayList<ICommandArgument>();
        final Matcher matcher = aj.a.matcher(input);
        int end = -1;
        while (matcher.find()) {
            list.add(new ai(list.size(), matcher.group(), input.substring(matcher.start())));
            end = matcher.end();
        }
        if (b && end < input.length()) {
            list.add(new ai(list.size(), "", ""));
        }
        return (List<ICommandArgument>)list;
    }
    
    public static ai a() {
        return new ai(-1, "<unknown>", "");
    }
    
    static {
        a = Pattern.compile("\\S+");
    }
}
