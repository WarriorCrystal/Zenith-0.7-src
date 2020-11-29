// 
// Decompiled by Procyon v0.5.36
// 

package baritone.api.utils;

import java.util.HashMap;
import java.util.Map;

public class BlockUtils
{
    private static transient Map<String, aow> resourceCache;
    
    public static String blockToString(final aow aow) {
        final nf nf;
        String s = (nf = (nf)aow.h.b((Object)aow)).a();
        if (!nf.b().equals("minecraft")) {
            s = nf.toString();
        }
        return s;
    }
    
    public static aow stringToBlockRequired(final String s) {
        final aow stringToBlockNullable;
        if ((stringToBlockNullable = stringToBlockNullable(s)) == null) {
            throw new IllegalArgumentException(String.format("Invalid block name %s", s));
        }
        return stringToBlockNullable;
    }
    
    public static aow stringToBlockNullable(final String obj) {
        final aow aow;
        if ((aow = BlockUtils.resourceCache.get(obj)) != null) {
            return aow;
        }
        if (BlockUtils.resourceCache.containsKey(obj)) {
            return null;
        }
        final aow b = aow.b(obj.contains(":") ? obj : "minecraft:".concat(String.valueOf(obj)));
        final HashMap<String, aow> resourceCache;
        (resourceCache = new HashMap<String, aow>(BlockUtils.resourceCache)).put(obj, b);
        BlockUtils.resourceCache = resourceCache;
        return b;
    }
    
    private BlockUtils() {
    }
    
    static {
        BlockUtils.resourceCache = new HashMap<String, aow>();
    }
}
