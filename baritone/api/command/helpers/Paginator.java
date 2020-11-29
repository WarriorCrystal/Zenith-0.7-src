// 
// Decompiled by Procyon v0.5.36
// 

package baritone.api.command.helpers;

import baritone.api.command.exception.CommandInvalidTypeException;
import baritone.api.command.argument.IArgConsumer;
import java.util.function.Function;
import java.util.Arrays;
import java.util.List;
import baritone.api.utils.Helper;

public class Paginator<E> implements Helper
{
    public final List<E> entries;
    public int pageSize;
    public int page;
    
    public Paginator(final List<E> entries) {
        this.pageSize = 8;
        this.page = 1;
        this.entries = entries;
    }
    
    public Paginator(final E... a) {
        this.pageSize = 8;
        this.page = 1;
        this.entries = Arrays.asList(a);
    }
    
    public Paginator<E> setPageSize(final int pageSize) {
        this.pageSize = pageSize;
        return this;
    }
    
    public int getMaxPage() {
        return (this.entries.size() - 1) / this.pageSize + 1;
    }
    
    public boolean validPage(final int n) {
        return n > 0 && n <= this.getMaxPage();
    }
    
    public Paginator<E> skipPages(final int n) {
        this.page += n;
        return this;
    }
    
    public void display(final Function<E, hh> function, final String s) {
        int i = 0;
        while (i < (i = (this.page - 1) * this.pageSize) + this.pageSize) {
            if (i < this.entries.size()) {
                this.logDirect(function.apply(this.entries.get(i)));
            }
            else {
                this.logDirect("--", a.i);
            }
            ++i;
        }
        final boolean b = s != null && this.validPage(this.page - 1);
        final boolean b2 = s != null && this.validPage(this.page + 1);
        final ho ho = new ho("<<");
        if (b) {
            ((hh)ho).b().a(new hg(hg$a.c, String.format("%s %d", s, this.page - 1))).a(new hj(hj$a.a, (hh)new ho("Click to view previous page")));
        }
        else {
            ((hh)ho).b().a(a.i);
        }
        final ho ho2 = new ho(">>");
        if (b2) {
            ((hh)ho2).b().a(new hg(hg$a.c, String.format("%s %d", s, this.page + 1))).a(new hj(hj$a.a, (hh)new ho("Click to view next page")));
        }
        else {
            ((hh)ho2).b().a(a.i);
        }
        final ho ho3;
        ((hh)(ho3 = new ho(""))).b().a(a.h);
        ((hh)ho3).a((hh)ho);
        ((hh)ho3).a(" | ");
        ((hh)ho3).a((hh)ho2);
        ((hh)ho3).a(String.format(" %d/%d", this.page, this.getMaxPage()));
        this.logDirect((hh)ho3);
    }
    
    public void display(final Function<E, hh> function) {
        this.display(function, null);
    }
    
    public static <T> void paginate(final IArgConsumer argConsumer, final Paginator<T> paginator, final Runnable runnable, final Function<T, hh> function, final String s) {
        int intValue = 1;
        argConsumer.requireMax(1);
        if (argConsumer.hasAny()) {
            intValue = argConsumer.getAs(Integer.class);
            if (!paginator.validPage(intValue)) {
                throw new CommandInvalidTypeException(argConsumer.consumed(), String.format("a valid page (1-%d)", paginator.getMaxPage()), argConsumer.consumed().getValue());
            }
        }
        paginator.skipPages(intValue - paginator.page);
        if (runnable != null) {
            runnable.run();
        }
        paginator.display(function, s);
    }
    
    public static <T> void paginate(final IArgConsumer argConsumer, final List<T> list, final Runnable runnable, final Function<T, hh> function, final String s) {
        paginate(argConsumer, new Paginator<T>(list), runnable, function, s);
    }
    
    public static <T> void paginate(final IArgConsumer argConsumer, final T[] a, final Runnable runnable, final Function<T, hh> function, final String s) {
        paginate(argConsumer, (List<T>)Arrays.asList((T[])a), runnable, function, s);
    }
    
    public static <T> void paginate(final IArgConsumer argConsumer, final Paginator<T> paginator, final Function<T, hh> function, final String s) {
        paginate(argConsumer, paginator, null, function, s);
    }
    
    public static <T> void paginate(final IArgConsumer argConsumer, final List<T> list, final Function<T, hh> function, final String s) {
        paginate(argConsumer, new Paginator<T>(list), null, function, s);
    }
    
    public static <T> void paginate(final IArgConsumer argConsumer, final T[] a, final Function<T, hh> function, final String s) {
        paginate(argConsumer, (List<T>)Arrays.asList((T[])a), null, function, s);
    }
    
    public static <T> void paginate(final IArgConsumer argConsumer, final Paginator<T> paginator, final Runnable runnable, final Function<T, hh> function) {
        paginate(argConsumer, paginator, runnable, function, null);
    }
    
    public static <T> void paginate(final IArgConsumer argConsumer, final List<T> list, final Runnable runnable, final Function<T, hh> function) {
        paginate(argConsumer, new Paginator<T>(list), runnable, function, null);
    }
    
    public static <T> void paginate(final IArgConsumer argConsumer, final T[] a, final Runnable runnable, final Function<T, hh> function) {
        paginate(argConsumer, (List<T>)Arrays.asList((T[])a), runnable, function, null);
    }
    
    public static <T> void paginate(final IArgConsumer argConsumer, final Paginator<T> paginator, final Function<T, hh> function) {
        paginate(argConsumer, paginator, null, function, null);
    }
    
    public static <T> void paginate(final IArgConsumer argConsumer, final List<T> list, final Function<T, hh> function) {
        paginate(argConsumer, new Paginator<T>(list), null, function, null);
    }
    
    public static <T> void paginate(final IArgConsumer argConsumer, final T[] a, final Function<T, hh> function) {
        paginate(argConsumer, (List<T>)Arrays.asList((T[])a), null, function, null);
    }
}
