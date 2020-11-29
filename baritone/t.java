// 
// Decompiled by Procyon v0.5.36
// 

package baritone;

import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.function.Function;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.io.DataOutputStream;
import java.io.OutputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import baritone.api.cache.Waypoint;
import baritone.api.utils.BetterBlockPos;
import java.io.DataInputStream;
import java.io.InputStream;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.util.HashSet;
import java.util.HashMap;
import java.io.IOException;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import baritone.api.cache.IWaypoint;
import java.util.Set;
import baritone.api.cache.IWaypoint$Tag;
import java.util.Map;
import java.nio.file.Path;
import baritone.api.cache.IWaypointCollection;

public final class t implements IWaypointCollection
{
    private final Path a;
    private final Map<IWaypoint$Tag, Set<IWaypoint>> a;
    
    t(final Path path) {
        this.a = path;
        if (!Files.exists(path, new LinkOption[0])) {
            try {
                Files.createDirectories(path, (FileAttribute<?>[])new FileAttribute[0]);
            }
            catch (IOException ex) {}
        }
        System.out.println("Would save waypoints to ".concat(String.valueOf(path)));
        this.a = new HashMap<IWaypoint$Tag, Set<IWaypoint>>();
        this.a();
    }
    
    private void a() {
        IWaypoint$Tag[] values;
        for (int length = (values = IWaypoint$Tag.values()).length, i = 0; i < length; ++i) {
            this.a(values[i]);
        }
    }
    
    private synchronized void a(final IWaypoint$Tag waypoint$Tag) {
        this.a.put(waypoint$Tag, new HashSet<IWaypoint>());
        final Path resolve;
        if (!Files.exists(resolve = this.a.resolve(waypoint$Tag.name().toLowerCase() + ".mp4"), new LinkOption[0])) {
            return;
        }
        try {
            final FileInputStream in = new FileInputStream(resolve.toFile());
            Throwable t = null;
            try {
                final BufferedInputStream in2 = new BufferedInputStream(in);
                Throwable t2 = null;
                try {
                    final DataInputStream dataInputStream = new DataInputStream(in2);
                    Throwable t3 = null;
                    try {
                        final long long1;
                        if ((long1 = dataInputStream.readLong()) != 121977993584L) {
                            throw new IOException("Bad magic value ".concat(String.valueOf(long1)));
                        }
                        long long2 = dataInputStream.readLong();
                        while (long2-- > 0L) {
                            this.a.get(waypoint$Tag).add(new Waypoint(dataInputStream.readUTF(), waypoint$Tag, new BetterBlockPos(dataInputStream.readInt(), dataInputStream.readInt(), dataInputStream.readInt()), dataInputStream.readLong()));
                        }
                        dataInputStream.close();
                    }
                    catch (Throwable t5) {
                        final Throwable t4 = t5;
                        t3 = t5;
                        throw t4;
                    }
                    finally {
                        if (t3 != null) {
                            try {
                                dataInputStream.close();
                            }
                            catch (Throwable exception) {
                                t3.addSuppressed(exception);
                            }
                        }
                        else {
                            dataInputStream.close();
                        }
                    }
                    in2.close();
                }
                catch (Throwable t7) {
                    final Throwable t6 = t7;
                    t2 = t7;
                    throw t6;
                }
                finally {
                    if (t2 != null) {
                        try {
                            in2.close();
                        }
                        catch (Throwable exception2) {
                            t2.addSuppressed(exception2);
                        }
                    }
                    else {
                        in2.close();
                    }
                }
                in.close();
            }
            catch (Throwable t9) {
                final Throwable t8 = t9;
                t = t9;
                throw t8;
            }
            finally {
                if (t != null) {
                    try {
                        in.close();
                    }
                    catch (Throwable exception3) {
                        t.addSuppressed(exception3);
                    }
                }
                else {
                    in.close();
                }
            }
        }
        catch (IOException ex) {}
    }
    
    private synchronized void b(final IWaypoint$Tag waypoint$Tag) {
        final Path resolve = this.a.resolve(waypoint$Tag.name().toLowerCase() + ".mp4");
        try {
            final FileOutputStream out = new FileOutputStream(resolve.toFile());
            Throwable t = null;
            try {
                final BufferedOutputStream out2 = new BufferedOutputStream(out);
                Throwable t2 = null;
                try {
                    final DataOutputStream dataOutputStream = new DataOutputStream(out2);
                    Throwable t3 = null;
                    try {
                        dataOutputStream.writeLong(121977993584L);
                        dataOutputStream.writeLong(this.a.get(waypoint$Tag).size());
                        for (final IWaypoint waypoint : this.a.get(waypoint$Tag)) {
                            dataOutputStream.writeUTF(waypoint.getName());
                            dataOutputStream.writeLong(waypoint.getCreationTimestamp());
                            dataOutputStream.writeInt(waypoint.getLocation().p());
                            dataOutputStream.writeInt(waypoint.getLocation().q());
                            dataOutputStream.writeInt(waypoint.getLocation().r());
                        }
                        dataOutputStream.close();
                    }
                    catch (Throwable t5) {
                        final Throwable t4 = t5;
                        t3 = t5;
                        throw t4;
                    }
                    finally {
                        if (t3 != null) {
                            try {
                                dataOutputStream.close();
                            }
                            catch (Throwable exception) {
                                t3.addSuppressed(exception);
                            }
                        }
                        else {
                            dataOutputStream.close();
                        }
                    }
                    out2.close();
                }
                catch (Throwable t7) {
                    final Throwable t6 = t7;
                    t2 = t7;
                    throw t6;
                }
                finally {
                    if (t2 != null) {
                        try {
                            out2.close();
                        }
                        catch (Throwable exception2) {
                            t2.addSuppressed(exception2);
                        }
                    }
                    else {
                        out2.close();
                    }
                }
                out.close();
            }
            catch (Throwable t9) {
                final Throwable t8 = t9;
                t = t9;
                throw t8;
            }
            finally {
                if (t != null) {
                    try {
                        out.close();
                    }
                    catch (Throwable exception3) {
                        t.addSuppressed(exception3);
                    }
                }
                else {
                    out.close();
                }
            }
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    @Override
    public final void addWaypoint(final IWaypoint waypoint) {
        if (this.a.get(waypoint.getTag()).add(waypoint)) {
            this.b(waypoint.getTag());
        }
    }
    
    @Override
    public final void removeWaypoint(final IWaypoint waypoint) {
        if (this.a.get(waypoint.getTag()).remove(waypoint)) {
            this.b(waypoint.getTag());
        }
    }
    
    @Override
    public final IWaypoint getMostRecentByTag(final IWaypoint$Tag waypoint$Tag) {
        return this.a.get(waypoint$Tag).stream().min(Comparator.comparingLong(waypoint -> -waypoint.getCreationTimestamp())).orElse(null);
    }
    
    @Override
    public final Set<IWaypoint> getByTag(final IWaypoint$Tag waypoint$Tag) {
        return Collections.unmodifiableSet((Set<? extends IWaypoint>)this.a.get(waypoint$Tag));
    }
    
    @Override
    public final Set<IWaypoint> getAllWaypoints() {
        return this.a.values().stream().flatMap((Function<? super Set<IWaypoint>, ? extends Stream<?>>)Collection::stream).collect((Collector<? super Object, ?, Set<IWaypoint>>)Collectors.toSet());
    }
}
