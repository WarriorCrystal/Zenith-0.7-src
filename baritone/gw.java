// 
// Decompiled by Procyon v0.5.36
// 

package baritone;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

final class gw
{
    private static final Pattern a;
    private final nf a;
    private final Map<String, String> a;
    private awt a;
    
    private gw(final nf a, final Map<String, String> a2) {
        this.a = a;
        this.a = a2;
    }
    
    private static gw b(String group) {
        final Matcher matcher;
        if (!(matcher = gw.a.matcher(group)).matches()) {
            return null;
        }
        try {
            final String group2 = matcher.group("location");
            group = matcher.group("properties");
            final nf nf = new nf(group2);
            final HashMap<String, String> hashMap = new HashMap<String, String>();
            if (group != null) {
                String[] split;
                for (int length = (split = group.split(",")).length, i = 0; i < length; ++i) {
                    final String[] split2 = split[i].split("=");
                    hashMap.put(split2[0], split2[1]);
                }
            }
            return new gw(nf, hashMap);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    static {
        a = Pattern.compile("(?<location>(\\w+:)?\\w+)(\\[(?<properties>(\\w+=\\w+,?)+)])?");
    }
}
