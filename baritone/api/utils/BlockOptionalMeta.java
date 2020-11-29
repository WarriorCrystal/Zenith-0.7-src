// 
// Decompiled by Procyon v0.5.36
// 

package baritone.api.utils;

import java.util.Iterator;
import java.util.function.Consumer;
import java.util.Collections;
import java.util.HashMap;
import baritone.api.utils.accessor.IItemStack;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import com.google.common.collect.UnmodifiableIterator;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import javax.annotation.Nullable;
import javax.annotation.Nonnull;
import java.util.Map;
import java.util.regex.Pattern;
import com.google.common.collect.ImmutableSet;
import java.util.Set;

public final class BlockOptionalMeta
{
    private final aow block;
    private final int meta;
    private final boolean noMeta;
    private final Set<awt> blockstates;
    private final ImmutableSet<Integer> stateHashes;
    private final ImmutableSet<Integer> stackHashes;
    private static final Pattern pattern;
    private static final Map<Object, Object> normalizations;
    
    public BlockOptionalMeta(@Nonnull final aow block, @Nullable final Integer n) {
        this.block = block;
        this.noMeta = (n == null);
        this.meta = (this.noMeta ? 0 : n);
        this.blockstates = getStates(block, n);
        this.stateHashes = getStateHashes(this.blockstates);
        this.stackHashes = getStackHashes(this.blockstates);
    }
    
    public BlockOptionalMeta(@Nonnull final aow aow) {
        this(aow, null);
    }
    
    public BlockOptionalMeta(@Nonnull final String input) {
        final Matcher matcher;
        if (!(matcher = BlockOptionalMeta.pattern.matcher(input)).find()) {
            throw new IllegalArgumentException("invalid block selector");
        }
        final MatchResult matchResult = matcher.toMatchResult();
        this.noMeta = (matchResult.group(2) == null);
        final nf nf = new nf(matchResult.group(1));
        if (!aow.h.d((Object)nf)) {
            throw new IllegalArgumentException("Invalid block ID");
        }
        this.block = (aow)aow.h.c((Object)nf);
        this.meta = (this.noMeta ? 0 : Integer.parseInt(matchResult.group(2)));
        this.blockstates = getStates(this.block, this.getMeta());
        this.stateHashes = getStateHashes(this.blockstates);
        this.stackHashes = getStackHashes(this.blockstates);
    }
    
    public static <C extends Comparable<C>, P extends axj<C>> P castToIProperty(final Object o) {
        return (P)o;
    }
    
    public static <C extends Comparable<C>, P extends axj<C>> C castToIPropertyValue(final P p2, final Object o) {
        return (C)o;
    }
    
    public static awt normalize(final awt awt) {
        awt awt2 = awt;
        final UnmodifiableIterator iterator = awt.t().keySet().iterator();
        while (((Iterator)iterator).hasNext()) {
            final axj axj;
            final Class b = (axj = ((Iterator<axj>)iterator).next()).b();
            if (BlockOptionalMeta.normalizations.containsKey(axj)) {
                try {
                    awt2 = awt2.a(castToIProperty(axj), (Comparable)castToIPropertyValue(axj, BlockOptionalMeta.normalizations.get(axj)));
                }
                catch (IllegalArgumentException ex) {}
            }
            else if (BlockOptionalMeta.normalizations.containsKey(awt.c(axj))) {
                try {
                    awt2 = awt2.a(castToIProperty(axj), (Comparable)castToIPropertyValue(axj, BlockOptionalMeta.normalizations.get(awt.c(axj))));
                }
                catch (IllegalArgumentException ex2) {}
            }
            else {
                if (!BlockOptionalMeta.normalizations.containsKey(b)) {
                    continue;
                }
                try {
                    awt2 = awt2.a(castToIProperty(axj), (Comparable)castToIPropertyValue(axj, BlockOptionalMeta.normalizations.get(b)));
                }
                catch (IllegalArgumentException ex3) {}
            }
        }
        return awt2;
    }
    
    public static int stateMeta(final awt awt) {
        return awt.u().e(normalize(awt));
    }
    
    private static Set<awt> getStates(@Nonnull final aow aow, @Nullable final Integer n) {
        return aow.s().a().stream().filter(awt -> n == null || stateMeta(awt) == n).collect((Collector<? super Object, ?, Set<awt>>)Collectors.toSet());
    }
    
    private static ImmutableSet<Integer> getStateHashes(final Set<awt> set) {
        return (ImmutableSet<Integer>)ImmutableSet.copyOf((Object[])set.stream().map((Function<? super Object, ?>)Object::hashCode).toArray(Integer[]::new));
    }
    
    private static ImmutableSet<Integer> getStackHashes(final Set<awt> set) {
        final aip aip;
        return (ImmutableSet<Integer>)ImmutableSet.copyOf((Object[])set.stream().map(awt -> {
            new aip(awt.u().a(awt, new Random(), 0), awt.u().d(awt));
            return aip;
        }).map(itemStack -> itemStack.getBaritoneHash()).toArray(Integer[]::new));
    }
    
    public final aow getBlock() {
        return this.block;
    }
    
    public final Integer getMeta() {
        if (this.noMeta) {
            return null;
        }
        return this.meta;
    }
    
    public final boolean matches(@Nonnull final aow aow) {
        return aow == this.block;
    }
    
    public final boolean matches(@Nonnull final awt awt) {
        return awt.u() == this.block && this.stateHashes.contains((Object)awt.hashCode());
    }
    
    public final boolean matches(final aip aip) {
        int baritoneHash = ((IItemStack)aip).getBaritoneHash();
        if (this.noMeta) {
            baritoneHash -= aip.i();
        }
        return this.stackHashes.contains((Object)baritoneHash);
    }
    
    @Override
    public final String toString() {
        return String.format("BlockOptionalMeta{block=%s,meta=%s}", this.block, this.getMeta());
    }
    
    public static awt blockStateFromStack(final aip aip) {
        return aow.a(aip.c()).a(aip.j());
    }
    
    public final awt getAnyBlockState() {
        if (this.blockstates.size() > 0) {
            return this.blockstates.iterator().next();
        }
        return null;
    }
    
    static {
        pattern = Pattern.compile("^(.+?)(?::(\\d+))?$");
        final HashMap<axh, Boolean> m = new HashMap<axh, Boolean>();
        final Map<Class<? extends Enum>, Enum> map;
        final Consumer<fa$a> consumer;
        (consumer = (enum1 -> map.put(enum1.getClass(), enum1))).accept((fa$a)fa.c);
        consumer.accept(fa$a.b);
        consumer.accept((fa$a)arv$a.b);
        consumer.accept((fa$a)aud$a.b);
        consumer.accept((fa$a)aud$b.a);
        consumer.accept((fa$a)art$a.a);
        consumer.accept((fa$a)aqb$a.b);
        consumer.accept((fa$a)arf$a.b);
        consumer.accept((fa$a)aqa$a.b);
        consumer.accept((fa$a)aqa$b.a);
        consumer.accept((fa$a)aou$a.a);
        consumer.accept((fa$a)aos$b.a);
        consumer.accept((fa$a)aur$a.b);
        m.put(aoo.b, Integer.valueOf(0));
        m.put((axh)aou.b, Boolean.FALSE);
        m.put((axh)apb.a[0], Boolean.FALSE);
        m.put((axh)apb.a[1], Boolean.FALSE);
        m.put((axh)apb.a[2], Boolean.FALSE);
        m.put((axh)apd.a, Boolean.FALSE);
        m.put((axh)apk.a, Boolean.FALSE);
        m.put((axh)apk.b, Boolean.FALSE);
        m.put((axh)apk.c, Boolean.FALSE);
        m.put((axh)apk.d, Boolean.FALSE);
        m.put((axh)apk.e, Boolean.FALSE);
        m.put((axh)apk.f, Boolean.FALSE);
        m.put((axh)apy.b, Boolean.FALSE);
        m.put((axh)aqa.b, Boolean.FALSE);
        m.put((axh)aqa.d, Boolean.FALSE);
        m.put((axh)aqo.a, Boolean.FALSE);
        m.put((axh)aqo.b, Boolean.FALSE);
        m.put((axh)aqo.d, Boolean.FALSE);
        m.put((axh)aqo.c, Boolean.FALSE);
        m.put((axh)aqq.a, (Boolean)(Object)Integer.valueOf(0));
        m.put((axh)aqq.b, Boolean.FALSE);
        m.put((axh)aqq.c, Boolean.FALSE);
        m.put((axh)aqq.d, Boolean.FALSE);
        m.put((axh)aqq.e, Boolean.FALSE);
        m.put((axh)aqq.f, Boolean.FALSE);
        m.put((axh)arb.a, Boolean.FALSE);
        m.put((axh)arr.b, Boolean.FALSE);
        m.put((axh)auo.b, Boolean.FALSE);
        m.put((axh)auo.c, Boolean.FALSE);
        m.put((axh)auo.e, Boolean.FALSE);
        m.put((axh)auo.d, Boolean.FALSE);
        m.put((axh)ata$a.d, (Boolean)ata$a.c);
        m.put((axh)ata$a.e, (Boolean)ata$a.c);
        m.put(atf.a, Boolean.FALSE);
        m.put(atf.b, Boolean.FALSE);
        m.put(atf.c, Boolean.FALSE);
        m.put(atf.d, Boolean.FALSE);
        m.put(atp.c, Integer.valueOf(0));
        m.put(att.b, Boolean.FALSE);
        m.put(aue.b, Integer.valueOf(0));
        m.put(aug.a, Integer.valueOf(0));
        m.put(aus.d, Boolean.FALSE);
        m.put(aus.e, Boolean.FALSE);
        m.put(aus.g, Boolean.FALSE);
        m.put(aus.f, Boolean.FALSE);
        m.put(auu.b, Boolean.FALSE);
        m.put(auu.c, Boolean.FALSE);
        m.put(auu.d, Boolean.FALSE);
        m.put(auu.e, Boolean.FALSE);
        m.put(auu.a, Boolean.FALSE);
        m.put(auv.a, Boolean.FALSE);
        m.put(auv.b, Boolean.FALSE);
        m.put(auv.c, Boolean.FALSE);
        m.put(auv.e, Boolean.FALSE);
        m.put(auv.d, Boolean.FALSE);
        normalizations = Collections.unmodifiableMap((Map<?, ?>)m);
    }
}
