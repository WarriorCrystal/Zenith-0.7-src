// 
// Decompiled by Procyon v0.5.36
// 

package baritone.api.cache;

import java.util.Collections;
import java.util.Collection;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public enum IWaypoint$Tag
{
    HOME(new String[] { "home", "base" }), 
    DEATH(new String[] { "death" }), 
    BED(new String[] { "bed", "spawn" }), 
    USER(new String[] { "user" });
    
    private static final List<IWaypoint$Tag> TAG_LIST;
    public final String[] names;
    
    private IWaypoint$Tag(final String[] names) {
        this.names = names;
    }
    
    public final String getName() {
        return this.names[0];
    }
    
    public static IWaypoint$Tag getByName(final String anotherString) {
        IWaypoint$Tag[] values;
        for (int length = (values = values()).length, i = 0; i < length; ++i) {
            IWaypoint$Tag waypoint$Tag;
            String[] names;
            for (int length2 = (names = (waypoint$Tag = values[i]).names).length, j = 0; j < length2; ++j) {
                if (names[j].equalsIgnoreCase(anotherString)) {
                    return waypoint$Tag;
                }
            }
        }
        return null;
    }
    
    public static String[] getAllNames() {
        final HashSet set = new HashSet();
        IWaypoint$Tag[] values;
        for (int length = (values = values()).length, i = 0; i < length; ++i) {
            set.addAll(Arrays.asList(values[i].names));
        }
        return (String[])set.toArray(new String[0]);
    }
    
    static {
        TAG_LIST = Collections.unmodifiableList((List<? extends IWaypoint$Tag>)Arrays.asList((T[])values()));
    }
}
