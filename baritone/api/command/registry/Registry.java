// 
// Decompiled by Procyon v0.5.36
// 

package baritone.api.command.registry;

import java.util.stream.StreamSupport;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.Iterator;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Collection;
import java.util.Set;
import java.util.Deque;

public class Registry<V>
{
    private final Deque<V> _entries;
    private final Set<V> registered;
    public final Collection<V> entries;
    
    public Registry() {
        this._entries = new LinkedList<V>();
        this.registered = new HashSet<V>();
        this.entries = Collections.unmodifiableCollection((Collection<? extends V>)this._entries);
    }
    
    public boolean registered(final V v) {
        return this.registered.contains(v);
    }
    
    public boolean register(final V v) {
        if (!this.registered(v)) {
            this._entries.addFirst(v);
            this.registered.add(v);
            return true;
        }
        return false;
    }
    
    public void unregister(final V v) {
        if (this.registered(v)) {
            return;
        }
        this._entries.remove(v);
        this.registered.remove(v);
    }
    
    public Iterator<V> iterator() {
        return this._entries.iterator();
    }
    
    public Iterator<V> descendingIterator() {
        return this._entries.descendingIterator();
    }
    
    public Stream<V> stream() {
        return this._entries.stream();
    }
    
    public Stream<V> descendingStream() {
        return StreamSupport.stream(Spliterators.spliterator(this.descendingIterator(), (long)this._entries.size(), 16448), false);
    }
}
