// 
// Decompiled by Procyon v0.5.36
// 

package baritone.api.command.argument;

import java.util.stream.Stream;
import baritone.api.command.datatypes.IDatatype;
import baritone.api.command.datatypes.IDatatypePost;
import baritone.api.command.datatypes.IDatatypeFor;
import java.util.Deque;
import java.util.LinkedList;

public interface IArgConsumer
{
    LinkedList<ICommandArgument> getArgs();
    
    Deque<ICommandArgument> getConsumed();
    
    boolean has(final int p0);
    
    boolean hasAny();
    
    boolean hasAtMost(final int p0);
    
    boolean hasAtMostOne();
    
    boolean hasExactly(final int p0);
    
    boolean hasExactlyOne();
    
    ICommandArgument peek(final int p0);
    
    ICommandArgument peek();
    
    boolean is(final Class<?> p0, final int p1);
    
    boolean is(final Class<?> p0);
    
    String peekString(final int p0);
    
    String peekString();
    
     <E extends Enum<?>> E peekEnum(final Class<E> p0, final int p1);
    
     <E extends Enum<?>> E peekEnum(final Class<E> p0);
    
     <E extends Enum<?>> E peekEnumOrNull(final Class<E> p0, final int p1);
    
     <E extends Enum<?>> E peekEnumOrNull(final Class<E> p0);
    
     <T> T peekAs(final Class<T> p0, final int p1);
    
     <T> T peekAs(final Class<T> p0);
    
     <T> T peekAsOrDefault(final Class<T> p0, final T p1, final int p2);
    
     <T> T peekAsOrDefault(final Class<T> p0, final T p1);
    
     <T> T peekAsOrNull(final Class<T> p0, final int p1);
    
     <T> T peekAsOrNull(final Class<T> p0);
    
     <T> T peekDatatype(final IDatatypeFor<T> p0);
    
     <T, O> T peekDatatype(final IDatatypePost<T, O> p0);
    
     <T, O> T peekDatatype(final IDatatypePost<T, O> p0, final O p1);
    
     <T> T peekDatatypeOrNull(final IDatatypeFor<T> p0);
    
     <T, O> T peekDatatypeOrNull(final IDatatypePost<T, O> p0);
    
     <T, O, D extends IDatatypePost<T, O>> T peekDatatypePost(final D p0, final O p1);
    
     <T, O, D extends IDatatypePost<T, O>> T peekDatatypePostOrDefault(final D p0, final O p1, final T p2);
    
     <T, O, D extends IDatatypePost<T, O>> T peekDatatypePostOrNull(final D p0, final O p1);
    
     <T, D extends IDatatypeFor<T>> T peekDatatypeFor(final Class<D> p0);
    
     <T, D extends IDatatypeFor<T>> T peekDatatypeForOrDefault(final Class<D> p0, final T p1);
    
     <T, D extends IDatatypeFor<T>> T peekDatatypeForOrNull(final Class<D> p0);
    
    ICommandArgument get();
    
    String getString();
    
     <E extends Enum<?>> E getEnum(final Class<E> p0);
    
     <E extends Enum<?>> E getEnumOrDefault(final Class<E> p0, final E p1);
    
     <E extends Enum<?>> E getEnumOrNull(final Class<E> p0);
    
     <T> T getAs(final Class<T> p0);
    
     <T> T getAsOrDefault(final Class<T> p0, final T p1);
    
     <T> T getAsOrNull(final Class<T> p0);
    
     <T, O, D extends IDatatypePost<T, O>> T getDatatypePost(final D p0, final O p1);
    
     <T, O, D extends IDatatypePost<T, O>> T getDatatypePostOrDefault(final D p0, final O p1, final T p2);
    
     <T, O, D extends IDatatypePost<T, O>> T getDatatypePostOrNull(final D p0, final O p1);
    
     <T, D extends IDatatypeFor<T>> T getDatatypeFor(final D p0);
    
     <T, D extends IDatatypeFor<T>> T getDatatypeForOrDefault(final D p0, final T p1);
    
     <T, D extends IDatatypeFor<T>> T getDatatypeForOrNull(final D p0);
    
     <T extends IDatatype> Stream<String> tabCompleteDatatype(final T p0);
    
    String rawRest();
    
    void requireMin(final int p0);
    
    void requireMax(final int p0);
    
    void requireExactly(final int p0);
    
    boolean hasConsumed();
    
    ICommandArgument consumed();
    
    String consumedString();
    
    IArgConsumer copy();
}
