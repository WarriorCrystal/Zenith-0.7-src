// 
// Decompiled by Procyon v0.5.36
// 

package baritone;

import baritone.api.command.exception.CommandTooManyArgumentsException;
import baritone.api.command.exception.CommandNotEnoughArgumentsException;
import java.util.stream.Stream;
import baritone.api.command.datatypes.IDatatype;
import java.util.ArrayList;
import baritone.api.command.datatypes.IDatatypePost;
import baritone.api.command.datatypes.IDatatypeFor;
import baritone.api.command.exception.CommandInvalidTypeException;
import java.util.List;
import java.util.Collection;
import java.util.Deque;
import baritone.api.command.argument.ICommandArgument;
import java.util.LinkedList;
import baritone.api.command.datatypes.IDatatypeContext;
import baritone.api.command.manager.ICommandManager;
import baritone.api.command.argument.IArgConsumer;

public final class ag implements IArgConsumer
{
    private final ICommandManager a;
    private final IDatatypeContext a;
    private final LinkedList<ICommandArgument> a;
    private final Deque<ICommandArgument> a;
    
    private ag(final ICommandManager a, final Deque<ICommandArgument> c, final Deque<ICommandArgument> c2) {
        this.a = a;
        this.a = new ah(this, (byte)0);
        this.a = new LinkedList<ICommandArgument>(c);
        this.a = new LinkedList<ICommandArgument>(c2);
    }
    
    public ag(final ICommandManager commandManager, final List<ICommandArgument> c) {
        this(commandManager, new LinkedList<ICommandArgument>(c), new LinkedList<ICommandArgument>());
    }
    
    @Override
    public final LinkedList<ICommandArgument> getArgs() {
        return this.a;
    }
    
    @Override
    public final Deque<ICommandArgument> getConsumed() {
        return this.a;
    }
    
    @Override
    public final boolean has(final int n) {
        return this.a.size() >= n;
    }
    
    @Override
    public final boolean hasAny() {
        return this.has(1);
    }
    
    @Override
    public final boolean hasAtMost(final int n) {
        return this.a.size() <= n;
    }
    
    @Override
    public final boolean hasAtMostOne() {
        return this.hasAtMost(1);
    }
    
    @Override
    public final boolean hasExactly(final int n) {
        return this.a.size() == n;
    }
    
    @Override
    public final boolean hasExactlyOne() {
        return this.hasExactly(1);
    }
    
    @Override
    public final ICommandArgument peek(final int index) {
        this.requireMin(index + 1);
        return this.a.get(index);
    }
    
    @Override
    public final ICommandArgument peek() {
        return this.peek(0);
    }
    
    @Override
    public final boolean is(final Class<?> clazz, final int n) {
        return this.peek(n).is(clazz);
    }
    
    @Override
    public final boolean is(final Class<?> clazz) {
        return this.is(clazz, 0);
    }
    
    @Override
    public final String peekString(final int n) {
        return this.peek(n).getValue();
    }
    
    @Override
    public final String peekString() {
        return this.peekString(0);
    }
    
    @Override
    public final <E extends Enum<?>> E peekEnum(final Class<E> clazz, final int n) {
        return this.peek(n).getEnum(clazz);
    }
    
    @Override
    public final <E extends Enum<?>> E peekEnum(final Class<E> clazz) {
        return this.peekEnum(clazz, 0);
    }
    
    @Override
    public final <E extends Enum<?>> E peekEnumOrNull(final Class<E> clazz, final int n) {
        try {
            return (E)this.peekEnum((Class<Enum>)clazz, n);
        }
        catch (CommandInvalidTypeException ex) {
            return null;
        }
    }
    
    @Override
    public final <E extends Enum<?>> E peekEnumOrNull(final Class<E> clazz) {
        return this.peekEnumOrNull(clazz, 0);
    }
    
    @Override
    public final <T> T peekAs(final Class<T> clazz, final int n) {
        return this.peek(n).getAs(clazz);
    }
    
    @Override
    public final <T> T peekAs(final Class<T> clazz) {
        return this.peekAs(clazz, 0);
    }
    
    @Override
    public final <T> T peekAsOrDefault(final Class<T> clazz, final T t, final int n) {
        try {
            return this.peekAs(clazz, n);
        }
        catch (CommandInvalidTypeException ex) {
            return t;
        }
    }
    
    @Override
    public final <T> T peekAsOrDefault(final Class<T> clazz, final T t) {
        return this.peekAsOrDefault(clazz, t, 0);
    }
    
    @Override
    public final <T> T peekAsOrNull(final Class<T> clazz, final int n) {
        return this.peekAsOrDefault(clazz, (T)null, n);
    }
    
    @Override
    public final <T> T peekAsOrNull(final Class<T> clazz) {
        return this.peekAsOrNull(clazz, 0);
    }
    
    @Override
    public final <T> T peekDatatype(final IDatatypeFor<T> datatypeFor) {
        return (T)this.a().getDatatypeFor(datatypeFor);
    }
    
    @Override
    public final <T, O> T peekDatatype(final IDatatypePost<T, O> datatypePost) {
        return this.peekDatatype(datatypePost, (O)null);
    }
    
    @Override
    public final <T, O> T peekDatatype(final IDatatypePost<T, O> datatypePost, final O o) {
        return (T)this.a().getDatatypePost(datatypePost, (Object)o);
    }
    
    @Override
    public final <T> T peekDatatypeOrNull(final IDatatypeFor<T> datatypeFor) {
        return (T)this.a().getDatatypeForOrNull(datatypeFor);
    }
    
    @Override
    public final <T, O> T peekDatatypeOrNull(final IDatatypePost<T, O> datatypePost) {
        return this.a().getDatatypePostOrNull(datatypePost, (Object)null);
    }
    
    @Override
    public final <T, O, D extends IDatatypePost<T, O>> T peekDatatypePost(final D n, final O o) {
        return (T)this.a().getDatatypePost((IDatatypePost)n, (Object)o);
    }
    
    @Override
    public final <T, O, D extends IDatatypePost<T, O>> T peekDatatypePostOrDefault(final D n, final O o, final T t) {
        return (T)this.a().getDatatypePostOrDefault((IDatatypePost)n, (Object)o, (Object)t);
    }
    
    @Override
    public final <T, O, D extends IDatatypePost<T, O>> T peekDatatypePostOrNull(final D n, final O o) {
        return this.peekDatatypePostOrDefault(n, o, (T)null);
    }
    
    @Override
    public final <T, D extends IDatatypeFor<T>> T peekDatatypeFor(final Class<D> clazz) {
        while (true) {
            this = this.a();
        }
    }
    
    @Override
    public final <T, D extends IDatatypeFor<T>> T peekDatatypeForOrDefault(final Class<D> clazz, final T t) {
        while (true) {
            this = this.a();
        }
    }
    
    @Override
    public final <T, D extends IDatatypeFor<T>> T peekDatatypeForOrNull(final Class<D> clazz) {
        return this.peekDatatypeForOrDefault(clazz, (T)null);
    }
    
    @Override
    public final ICommandArgument get() {
        this.requireMin(1);
        final ICommandArgument commandArgument = this.a.removeFirst();
        this.a.add(commandArgument);
        return commandArgument;
    }
    
    @Override
    public final String getString() {
        return this.get().getValue();
    }
    
    @Override
    public final <E extends Enum<?>> E getEnum(final Class<E> clazz) {
        return this.get().getEnum(clazz);
    }
    
    @Override
    public final <E extends Enum<?>> E getEnumOrDefault(final Class<E> clazz, final E e) {
        try {
            this.peekEnum(clazz);
            return this.getEnum(clazz);
        }
        catch (CommandInvalidTypeException ex) {
            return e;
        }
    }
    
    @Override
    public final <E extends Enum<?>> E getEnumOrNull(final Class<E> clazz) {
        return this.getEnumOrDefault(clazz, (E)null);
    }
    
    @Override
    public final <T> T getAs(final Class<T> clazz) {
        return this.get().getAs(clazz);
    }
    
    @Override
    public final <T> T getAsOrDefault(final Class<T> clazz, final T t) {
        try {
            final T as = this.peek().getAs(clazz);
            this.get();
            return as;
        }
        catch (CommandInvalidTypeException ex) {
            return t;
        }
    }
    
    @Override
    public final <T> T getAsOrNull(final Class<T> clazz) {
        return this.getAsOrDefault(clazz, (T)null);
    }
    
    @Override
    public final <T, O, D extends IDatatypePost<T, O>> T getDatatypePost(final D n, final O o) {
        try {
            return n.apply(this.a, o);
        }
        catch (Exception ex) {
            throw new CommandInvalidTypeException(this.hasAny() ? this.peek() : this.consumed(), n.getClass().getSimpleName(), ex);
        }
    }
    
    @Override
    public final <T, O, D extends IDatatypePost<T, O>> T getDatatypePostOrDefault(final D n, final O o, final T t) {
        final ArrayList<ICommandArgument> c = new ArrayList<ICommandArgument>(this.a);
        final ArrayList<ICommandArgument> list = new ArrayList<ICommandArgument>(this.a);
        try {
            return this.getDatatypePost(n, o);
        }
        catch (Exception ex) {
            this.a.clear();
            this.a.addAll(c);
            this.a.clear();
            this.a.addAll(list);
            return t;
        }
    }
    
    @Override
    public final <T, O, D extends IDatatypePost<T, O>> T getDatatypePostOrNull(final D n, final O o) {
        return this.getDatatypePostOrDefault(n, o, (T)null);
    }
    
    @Override
    public final <T, D extends IDatatypeFor<T>> T getDatatypeFor(final D n) {
        try {
            return n.get(this.a);
        }
        catch (Exception ex) {
            throw new CommandInvalidTypeException(this.hasAny() ? this.peek() : this.consumed(), n.getClass().getSimpleName(), ex);
        }
    }
    
    @Override
    public final <T, D extends IDatatypeFor<T>> T getDatatypeForOrDefault(final D n, final T t) {
        final ArrayList<ICommandArgument> c = new ArrayList<ICommandArgument>(this.a);
        final ArrayList<ICommandArgument> list = new ArrayList<ICommandArgument>(this.a);
        try {
            return this.getDatatypeFor(n);
        }
        catch (Exception ex) {
            this.a.clear();
            this.a.addAll(c);
            this.a.clear();
            this.a.addAll(list);
            return t;
        }
    }
    
    @Override
    public final <T, D extends IDatatypeFor<T>> T getDatatypeForOrNull(final D n) {
        return this.getDatatypeForOrDefault(n, (T)null);
    }
    
    @Override
    public final <T extends IDatatype> Stream<String> tabCompleteDatatype(final T t) {
        try {
            return t.tabComplete(this.a);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return Stream.empty();
        }
    }
    
    @Override
    public final String rawRest() {
        if (this.a.size() > 0) {
            return this.a.getFirst().getRawRest();
        }
        return "";
    }
    
    @Override
    public final void requireMin(final int n) {
        if (this.a.size() < n) {
            throw new CommandNotEnoughArgumentsException(n + this.a.size());
        }
    }
    
    @Override
    public final void requireMax(final int n) {
        if (this.a.size() > n) {
            throw new CommandTooManyArgumentsException(n + this.a.size());
        }
    }
    
    @Override
    public final void requireExactly(final int n) {
        this.requireMin(n);
        this.requireMax(n);
    }
    
    @Override
    public final boolean hasConsumed() {
        return !this.a.isEmpty();
    }
    
    @Override
    public final ICommandArgument consumed() {
        if (this.a.size() > 0) {
            return this.a.getLast();
        }
        return aj.a();
    }
    
    @Override
    public final String consumedString() {
        return this.consumed().getValue();
    }
    
    private ag a() {
        return new ag(this.a, this.a, this.a);
    }
}
