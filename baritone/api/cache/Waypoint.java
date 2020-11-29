// 
// Decompiled by Procyon v0.5.36
// 

package baritone.api.cache;

import java.util.Date;
import baritone.api.utils.BetterBlockPos;

public class Waypoint implements IWaypoint
{
    private final String name;
    private final IWaypoint$Tag tag;
    private final long creationTimestamp;
    private final BetterBlockPos location;
    
    public Waypoint(final String s, final IWaypoint$Tag waypoint$Tag, final BetterBlockPos betterBlockPos) {
        this(s, waypoint$Tag, betterBlockPos, System.currentTimeMillis());
    }
    
    public Waypoint(final String name, final IWaypoint$Tag tag, final BetterBlockPos location, final long creationTimestamp) {
        this.name = name;
        this.tag = tag;
        this.location = location;
        this.creationTimestamp = creationTimestamp;
    }
    
    @Override
    public int hashCode() {
        return this.name.hashCode() ^ this.tag.hashCode() ^ this.location.hashCode() ^ Long.hashCode(this.creationTimestamp);
    }
    
    @Override
    public String getName() {
        return this.name;
    }
    
    @Override
    public IWaypoint$Tag getTag() {
        return this.tag;
    }
    
    @Override
    public long getCreationTimestamp() {
        return this.creationTimestamp;
    }
    
    @Override
    public BetterBlockPos getLocation() {
        return this.location;
    }
    
    @Override
    public String toString() {
        return String.format("%s %s %s", this.name, BetterBlockPos.from(this.location).toString(), new Date(this.creationTimestamp).toString());
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o == null) {
            return false;
        }
        if (!(o instanceof IWaypoint)) {
            return false;
        }
        final IWaypoint waypoint = (IWaypoint)o;
        return this.name.equals(waypoint.getName()) && this.tag == waypoint.getTag() && this.location.equals(waypoint.getLocation());
    }
}
