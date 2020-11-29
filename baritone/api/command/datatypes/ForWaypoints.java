// 
// Decompiled by Procyon v0.5.36
// 

package baritone.api.command.datatypes;

import java.util.function.Function;
import java.util.Comparator;
import baritone.api.cache.IWaypointCollection;
import baritone.api.IBaritone;
import baritone.api.command.helpers.TabCompleteHelper;
import java.util.stream.Stream;
import baritone.api.cache.IWaypoint$Tag;
import baritone.api.cache.IWaypoint;

public enum ForWaypoints implements IDatatypeFor<IWaypoint[]>
{
    INSTANCE;
    
    @Override
    public final IWaypoint[] get(final IDatatypeContext datatypeContext) {
        final String string;
        final IWaypoint$Tag byName;
        if ((byName = IWaypoint$Tag.getByName(string = datatypeContext.getConsumer().getString())) == null) {
            return getWaypointsByName(datatypeContext.getBaritone(), string);
        }
        return getWaypointsByTag(datatypeContext.getBaritone(), byName);
    }
    
    @Override
    public final Stream<String> tabComplete(final IDatatypeContext datatypeContext) {
        return new TabCompleteHelper().append(getWaypointNames(datatypeContext.getBaritone())).sortAlphabetically().prepend(IWaypoint$Tag.getAllNames()).filterPrefix(datatypeContext.getConsumer().getString()).stream();
    }
    
    public static IWaypointCollection waypoints(final IBaritone baritone) {
        return baritone.getWorldProvider().getCurrentWorld().getWaypoints();
    }
    
    public static IWaypoint[] getWaypoints(final IBaritone baritone) {
        return waypoints(baritone).getAllWaypoints().stream().sorted(Comparator.comparingLong(IWaypoint::getCreationTimestamp).reversed()).toArray(IWaypoint[]::new);
    }
    
    public static String[] getWaypointNames(final IBaritone baritone) {
        return Stream.of(getWaypoints(baritone)).map((Function<? super IWaypoint, ?>)IWaypoint::getName).filter(s -> !s.isEmpty()).toArray(String[]::new);
    }
    
    public static IWaypoint[] getWaypointsByTag(final IBaritone baritone, final IWaypoint$Tag waypoint$Tag) {
        return waypoints(baritone).getByTag(waypoint$Tag).stream().sorted(Comparator.comparingLong(IWaypoint::getCreationTimestamp).reversed()).toArray(IWaypoint[]::new);
    }
    
    public static IWaypoint[] getWaypointsByName(final IBaritone baritone, final String anotherString) {
        return Stream.of(getWaypoints(baritone)).filter(waypoint -> waypoint.getName().equalsIgnoreCase(anotherString)).toArray(IWaypoint[]::new);
    }
}
