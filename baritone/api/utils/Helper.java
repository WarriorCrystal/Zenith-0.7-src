// 
// Decompiled by Procyon v0.5.36
// 

package baritone.api.utils;

import java.util.stream.Stream;
import java.util.function.Consumer;
import java.util.Arrays;
import baritone.api.BaritoneAPI;
import java.util.Calendar;

public interface Helper
{
    public static final Helper HELPER = new Helper$1();
    public static final bib mc = bib.z();
    
    default hh getPrefix() {
        final Calendar instance;
        final ho ho;
        ((hh)(ho = new ho(((instance = Calendar.getInstance()).get(2) == 3 && instance.get(5) <= 3) ? "Baritoe" : (BaritoneAPI.getSettings().shortBaritonePrefix.value ? "B" : "Baritone")))).b().a(a.n);
        final ho ho2;
        ((hh)(ho2 = new ho(""))).b().a(a.f);
        ((hh)ho2).a("[");
        ((hh)ho2).a((hh)ho);
        ((hh)ho2).a("]");
        return (hh)ho2;
    }
    
    default void logDebug(final String s) {
        if (!BaritoneAPI.getSettings().chatDebug.value) {
            return;
        }
        this.logDirect(s);
    }
    
    default void logDirect(final hh... a) {
        final ho ho;
        ((hh)(ho = new ho(""))).a(getPrefix());
        ((hh)ho).a((hh)new ho(" "));
        Arrays.asList(a).forEach(ho::a);
        bib.z().a(() -> BaritoneAPI.getSettings().logger.value.accept((hh)ho));
    }
    
    default void logDirect(final String s, final a a) {
        final ho ho;
        final Object o;
        Stream.of(s.split("\n")).forEach(s2 -> {
            ho = new ho(s2.replace("\t", "    "));
            ((hh)o).b().a(a);
            this.logDirect((hh)ho);
        });
    }
    
    default void logDirect(final String s) {
        this.logDirect(s, a.h);
    }
}
