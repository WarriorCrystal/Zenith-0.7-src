// 
// Decompiled by Procyon v0.5.36
// 

package baritone;

import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.OptionalInt;
import java.util.ArrayList;
import java.util.function.Predicate;
import baritone.api.event.events.TickEvent$Type;
import baritone.api.event.events.TickEvent;

public final class c extends b
{
    public c(final a a) {
        super(a);
    }
    
    @Override
    public final void onTick(final TickEvent tickEvent) {
        if (!baritone.a.a().allowInventory.value) {
            return;
        }
        if (tickEvent.getType() == TickEvent$Type.OUT) {
            return;
        }
        if (this.a.player().by != this.a.player().bx) {
            return;
        }
        if (this.a() >= 9) {
            this.a(this.a(), 8);
        }
        final aow b = aox.b;
        final Class<ajb> clazz = ajb.class;
        final aow aow = b;
        final fi a = this.a.player().bv.a;
        int n = -1;
        double n2 = -1.0;
        for (int i = 0; i < a.size(); ++i) {
            final aip aip;
            final double a2;
            if (!(aip = (aip)a.get(i)).b() && clazz.isInstance(aip.c()) && (a2 = fz.a(aip, aow.t())) > n2) {
                n2 = a2;
                n = i;
            }
        }
        final int n3;
        if ((n3 = n) >= 9) {
            this.a(n3, 0);
        }
    }
    
    public final void a(final int n, final Predicate<Integer> predicate) {
        final ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i = 1; i < 8; ++i) {
            if (((aip)this.a.player().bv.a.get(i)).b() && !predicate.test(i)) {
                list.add(i);
            }
        }
        if (list.isEmpty()) {
            for (int j = 1; j < 8; ++j) {
                if (!predicate.test(j)) {
                    list.add(j);
                }
            }
        }
        final OptionalInt optionalInt;
        if ((optionalInt = (list.isEmpty() ? OptionalInt.empty() : OptionalInt.of(list.get(new Random().nextInt(list.size()))))).isPresent()) {
            this.a(n, optionalInt.getAsInt());
        }
    }
    
    private void a(final int n, final int n2) {
        this.a.playerController().windowClick(this.a.player().bx.d, (n < 9) ? (n + 36) : n, n2, afw.c, (aed)this.a.player());
    }
    
    private int a() {
        final fi a = this.a.player().bv.a;
        for (int i = 0; i < a.size(); ++i) {
            if (baritone.a.a().acceptableThrowawayItems.value.contains(((aip)a.get(i)).c())) {
                return i;
            }
        }
        return -1;
    }
    
    public final boolean a() {
        while (baritone.a.a().acceptableThrowawayItems.value.iterator().hasNext()) {
            final Iterator<ain> iterator;
            if (this.a(false, aip -> iterator.next().equals(aip.c()))) {
                return true;
            }
        }
        return false;
    }
    
    public final boolean a(final boolean b, final int n, int n2, int n3) {
        final ea a = this.a.a;
        final int n4 = n2;
        final int n5 = n3;
        final awt a2 = this.a.a.a(n, n2, n3);
        final int n6 = n5;
        n3 = n4;
        n2 = n;
        final ea ea = a;
        awt desiredState;
        awt awt2;
        final awt awt = a.isActive() ? (ea.a.inSchematic(n2 - ea.a.p(), n3 - ea.a.q(), n6 - ea.a.r(), a2) ? (((desiredState = ea.a.desiredState(n2 - ea.a.p(), n3 - ea.a.q(), n6 - ea.a.r(), a2, ea.a)).u() == aox.a) ? (awt2 = null) : (awt2 = desiredState)) : (awt2 = null)) : (awt2 = null);
        final awt awt3 = awt2;
        final Object o;
        if (awt != null && this.a(b, aip3 -> aip3.c() instanceof ahb && o.equals(((ahb)aip3.c()).d().a(this.a.world(), (et)this.a.playerFeet(), fa.b, (float)this.a.player().p, (float)this.a.player().q, (float)this.a.player().r, aip3.c().a(aip3.j()), (vp)this.a.player())))) {
            return true;
        }
        final awt awt4;
        if (awt3 != null && this.a(b, aip -> aip.c() instanceof ahb && ((ahb)aip.c()).d().equals(awt4.u()))) {
            return true;
        }
        while (baritone.a.a().acceptableThrowawayItems.value.iterator().hasNext()) {
            final Iterator<ain> iterator;
            if (this.a(b, aip2 -> iterator.next().equals(aip2.c()))) {
                return true;
            }
        }
        return false;
    }
    
    public final boolean a(final boolean b, final Predicate<? super aip> predicate) {
        final bud player;
        final fi a = (player = this.a.player()).bv.a;
        for (int i = 0; i < 9; ++i) {
            if (predicate.test((aip)a.get(i))) {
                if (b) {
                    player.bv.d = i;
                }
                return true;
            }
        }
        if (predicate.test((aip)player.bv.c.get(0))) {
            for (int j = 0; j < 9; ++j) {
                final aip aip;
                if ((aip = (aip)a.get(j)).b() || aip.c() instanceof ajb) {
                    if (b) {
                        player.bv.d = j;
                    }
                    return true;
                }
            }
        }
        return false;
    }
}
