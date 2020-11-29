// 
// Decompiled by Procyon v0.5.36
// 

package baritone;

import baritone.api.cache.IWaypoint;
import baritone.api.cache.Waypoint;
import baritone.api.cache.IWaypoint$Tag;
import baritone.api.event.events.BlockInteractEvent$Type;
import baritone.api.event.events.BlockInteractEvent;
import baritone.api.utils.BetterBlockPos;
import baritone.api.event.events.PacketEvent;
import baritone.api.utils.IPlayerContext;
import java.util.Collection;
import baritone.api.event.events.type.EventState;
import baritone.api.event.events.PlayerUpdateEvent;
import baritone.api.event.events.TickEvent$Type;
import baritone.api.event.events.TickEvent;
import java.util.ArrayList;
import java.util.List;

public final class f extends b
{
    private final List<i> a;
    private Integer a;
    
    public f(final a a) {
        super(a);
        this.a = new ArrayList<i>();
    }
    
    @Override
    public final synchronized void onTick(final TickEvent tickEvent) {
        if (!baritone.a.a().containerMemory.value) {
            return;
        }
        if (tickEvent.getType() == TickEvent$Type.OUT) {
            ((List)(this.a = null)).clear();
        }
    }
    
    @Override
    public final synchronized void onPlayerUpdate(final PlayerUpdateEvent playerUpdateEvent) {
        if (playerUpdateEvent.getState() == EventState.PRE && baritone.a.a().containerMemory.value) {
            final int d = this.a.player().by.d;
            if (this.a != null) {
                if (d == this.a) {
                    this.a().a = (List<aip>)this.a.player().by.a().subList(0, 27);
                }
                else {
                    this.a().a();
                    this.a = null;
                }
            }
            if (this.a() != null) {
                final IPlayerContext a;
                final s s2;
                this.a().a(d).ifPresent(s -> {
                    a = this.a;
                    s = s2;
                    s2.a.clear();
                    s.a.addAll(a.player().by.a().subList(0, s.a));
                });
            }
        }
    }
    
    @Override
    public final synchronized void onSendPacket(final PacketEvent packetEvent) {
        if (!baritone.a.a().containerMemory.value) {
            return;
        }
        final ht<?> packet = packetEvent.getPacket();
        if (packetEvent.getState() == EventState.PRE) {
            final avj r;
            if (packet instanceof ma && (r = this.a.world().r(packetEvent.cast().a())) instanceof avx) {
                final avx avx;
                int w_ = (avx = (avx)r).w_();
                final BetterBlockPos from;
                BetterBlockPos obj = from = BetterBlockPos.from(r.w());
                et et = null;
                Label_0182: {
                    final fn a;
                    final aow u;
                    if ((u = (a = this.a.a).a(from).u()) == aox.cg || u == aox.ae) {
                        for (int i = 0; i < 4; ++i) {
                            final et a2 = from.a(fa.b(i));
                            if (a.a(a2).u() == u) {
                                et = a2;
                                break Label_0182;
                            }
                        }
                    }
                    et = null;
                }
                final BetterBlockPos from2 = BetterBlockPos.from(et);
                System.out.println(obj + " " + from2);
                if (from2 != null) {
                    w_ <<= 1;
                    if (from2.p() < obj.p() || from2.r() < obj.r()) {
                        obj = from2;
                    }
                }
                this.a.add(new i(System.nanoTime() / 1000000L, w_, avx.l(), obj, (byte)0));
            }
            if (packet instanceof lg) {
                this.a().a();
            }
        }
    }
    
    @Override
    public final synchronized void onReceivePacket(final PacketEvent packetEvent) {
        if (!baritone.a.a().containerMemory.value) {
            return;
        }
        final ht<?> packet = packetEvent.getPacket();
        if (packetEvent.getState() == EventState.PRE) {
            if (packet instanceof ir) {
                final ir ir = packetEvent.cast();
                this.a.removeIf(i -> System.nanoTime() / 1000000L - i.a > 1000L);
                System.out.println("Received packet " + ir.b() + " " + ir.e() + " " + ir.d() + " " + ir.a());
                System.out.println(ir.c());
                if (ir.c() instanceof hp && ((hp)ir.c()).i().equals("container.enderchest")) {
                    this.a = ir.a();
                    return;
                }
                final ir ir2;
                final ir ir3;
                this.a.stream().filter(j -> j.a.equals(ir2.b()) && j.a == ir2.d()).findFirst().ifPresent(k -> {
                    this.a.remove(k);
                    this.a().a(k.a, ir3.a(), ir3.d());
                    return;
                });
            }
            if (packet instanceof iq) {
                this.a().a();
            }
        }
    }
    
    @Override
    public final void onBlockInteract(final BlockInteractEvent blockInteractEvent) {
        if (blockInteractEvent.getType() == BlockInteractEvent$Type.USE && fn.a(this.a, blockInteractEvent.getPos()) instanceof aou) {
            this.a.a.a.getWaypoints().addWaypoint(new Waypoint("bed", IWaypoint$Tag.BED, BetterBlockPos.from(blockInteractEvent.getPos())));
        }
    }
    
    @Override
    public final void onPlayerDeath() {
        this.a.a.a.getWaypoints().addWaypoint(new Waypoint("death", IWaypoint$Tag.DEATH, this.a.playerFeet()));
    }
    
    private r a() {
        if (this.a.a.a == null) {
            return null;
        }
        return (r)this.a.a.a.getContainerMemory();
    }
    
    private h a() {
        return b(this.a.a.a.a.getParent(), this.a.player().bm());
    }
}
