// 
// Decompiled by Procyon v0.5.36
// 

package baritone;

import java.util.stream.Collector;
import java.util.stream.Collectors;
import baritone.api.utils.BetterBlockPos;
import baritone.api.utils.BlockUtils;
import baritone.api.utils.BlockOptionalMeta;
import java.util.Collections;
import java.util.Iterator;
import java.util.HashMap;
import java.util.Optional;
import baritone.api.pathing.goals.GoalComposite;
import baritone.api.pathing.goals.GoalBlock;
import baritone.api.pathing.goals.GoalTwoBlocks;
import baritone.api.pathing.goals.Goal;
import baritone.api.process.PathingCommandType;
import baritone.api.utils.input.Input;
import baritone.api.utils.Rotation;
import baritone.api.utils.RotationUtils;
import baritone.api.IBaritone;
import java.util.Collection;
import java.util.ArrayList;
import java.util.function.Predicate;
import java.util.function.Consumer;
import java.util.function.ToDoubleFunction;
import java.util.Comparator;
import java.util.function.ToIntFunction;
import baritone.api.process.PathingCommand;
import baritone.api.pathing.goals.GoalRunAway;
import java.util.Map;
import java.util.List;
import baritone.api.utils.BlockOptionalMetaLookup;
import baritone.api.process.IMineProcess;

public final class fc extends fk implements IMineProcess
{
    private BlockOptionalMetaLookup a;
    private List<et> a;
    private List<et> b;
    private Map<et, Long> a;
    private et a;
    private GoalRunAway a;
    private int a;
    private int b;
    
    public fc(final a a) {
        super(a);
    }
    
    @Override
    public final boolean isActive() {
        return this.a != null;
    }
    
    @Override
    public final PathingCommand onTick(final boolean b, final boolean b2) {
        if (this.a > 0) {
            final int sum = this.a.player().bv.a.stream().filter(aip -> this.a.has(aip)).mapToInt(aip::E).sum();
            System.out.println("Currently have " + sum + " valid items");
            if (sum >= this.a) {
                this.logDirect("Have " + sum + " valid items");
                this.cancel();
                return null;
            }
        }
        if (b) {
            if (this.a.isEmpty() || !baritone.a.a().blacklistClosestOnFailure.value) {
                this.logDirect("Unable to find any path to " + this.a + ", canceling mine");
                this.cancel();
                return null;
            }
            this.logDirect("Unable to find any path to " + this.a + ", blacklisting presumably unreachable closest instance...");
            this.a.stream().min(Comparator.comparingDouble((ToDoubleFunction<? super Object>)this.a.player()::c)).ifPresent(this.b::add);
            this.a.removeIf(this.b::contains);
        }
        if (!baritone.a.a().allowBreak.value) {
            this.logDirect("Unable to mine when allowBreak is false!");
            this.cancel();
            return null;
        }
        this.a();
        final int intValue = baritone.a.a().mineGoalUpdateInterval.value;
        final ArrayList<et> list = new ArrayList<et>(this.a);
        if (intValue != 0 && this.b++ % intValue == 0) {
            baritone.a.a().execute(() -> this.a(list, new cj(this.a, true)));
        }
        if (baritone.a.a().legitMine.value) {
            this.b();
        }
        final Optional<et> min = list.stream().filter(et -> et.p() == this.a.playerFeet().p() && et.r() == this.a.playerFeet().r()).filter(et2 -> et2.q() >= this.a.playerFeet().q()).filter(et3 -> !(fn.a(this.a, et3).u() instanceof aom)).min(Comparator.comparingDouble((ToDoubleFunction<? super et>)this.a.player()::c));
        this.a.a.clearAllKeys();
        if (min.isPresent() && this.a.player().z) {
            final et et5 = min.get();
            final Optional<Rotation> reachable;
            if (!cl.a(this.a.a, et5.p(), et5.q(), et5.r(), this.a.a.a(et5)) && (reachable = RotationUtils.reachable(this.a, et5)).isPresent() && b2) {
                this.a.a.updateTarget(reachable.get(), true);
                cl.a(this.a, this.a.world().o(et5));
                if (this.a.isLookingAt(et5) || this.a.playerRotations().isReallyCloseTo(reachable.get())) {
                    this.a.a.setInputForceState(Input.CLICK_LEFT, true);
                }
                return new PathingCommand(null, PathingCommandType.REQUEST_PAUSE);
            }
        }
        final boolean booleanValue = baritone.a.a().legitMine.value;
        final List<et> a;
        PathingCommand pathingCommand2;
        PathingCommand pathingCommand;
        if (!(a = this.a).isEmpty()) {
            final cj cj;
            final List<et> a2 = a(cj = new cj(this.a), new ArrayList<et>(a), this.a, this.b, this.a());
            final boolean b3;
            final cj cj2;
            final List<et> list2;
            final int n2;
            final int n3;
            final boolean b4;
            final GoalComposite goalComposite = new GoalComposite((Goal[])a2.stream().map(et4 -> {
                b3 = !(this.a.a.a(et4.a()).u() instanceof aqm);
                if (!baritone.a.a().forceInternalMining.value) {
                    if (b3) {
                        return new fe(et4);
                    }
                    else {
                        return new GoalTwoBlocks(et4);
                    }
                }
                else {
                    this.a(et4.a(), cj2, list2);
                    this.a(et4.b(), cj2, list2);
                    this.a(et4.c(2), cj2, list2);
                    if (n2 == n3) {
                        if (b4 && b3) {
                            return new fe(et4);
                        }
                        else {
                            return new GoalTwoBlocks(et4);
                        }
                    }
                    else if (n2 != 0) {
                        return new GoalBlock(et4);
                    }
                    else if (b4 && b3) {
                        return new GoalTwoBlocks(et4.b());
                    }
                    else {
                        return new GoalBlock(et4.b());
                    }
                }
            }).toArray(Goal[]::new));
            this.a = a2;
            pathingCommand = (pathingCommand2 = new PathingCommand(goalComposite, booleanValue ? PathingCommandType.FORCE_REVALIDATE_GOAL_AND_PATH : PathingCommandType.REVALIDATE_GOAL_AND_PATH));
        }
        else if (!booleanValue) {
            pathingCommand = (pathingCommand2 = null);
        }
        else {
            final int intValue2 = baritone.a.a().legitMineYLevel.value;
            if (this.a == null) {
                this.a = this.a.playerFeet();
            }
            if (this.a == null) {
                this.a = new fd(this, intValue2, new et[] { this.a });
            }
            pathingCommand = (pathingCommand2 = new PathingCommand(this.a, PathingCommandType.REVALIDATE_GOAL_AND_PATH));
        }
        final PathingCommand pathingCommand3 = pathingCommand2;
        if (pathingCommand == null) {
            this.cancel();
            return null;
        }
        return pathingCommand3;
    }
    
    private void a() {
        final HashMap<et, Long> a = (HashMap<et, Long>)new HashMap<Object, Long>(this.a);
        final Map<et, Long> map;
        this.a.getSelectedBlock().ifPresent(et -> {
            if (this.a.contains(et)) {
                map.put(et, System.currentTimeMillis() + baritone.a.a().mineDropLoiterDurationMSThanksLouca.value);
            }
            return;
        });
        for (final et et2 : this.a.keySet()) {
            if (a.get(et2) < System.currentTimeMillis()) {
                a.remove(et2);
            }
        }
        this.a = a;
    }
    
    @Override
    public final void onLostControl() {
        this.mine(0, (BlockOptionalMetaLookup)null);
    }
    
    @Override
    public final String displayName0() {
        return "Mine " + this.a;
    }
    
    private void a(final List<et> list, final cj cj) {
        if (this.a == null) {
            return;
        }
        if (baritone.a.a().legitMine.value) {
            return;
        }
        final List<et> a = this.a();
        final List<et> a2;
        (a2 = a(cj, this.a, list, this.b, a)).addAll(a);
        if (a2.isEmpty()) {
            this.logDirect("No locations for " + this.a + " known, cancelling");
            this.cancel();
            return;
        }
        this.a = a2;
    }
    
    private boolean a(final et et, final cj cj, final List<et> list) {
        if (list.contains(et)) {
            return true;
        }
        final awt a = cj.a.a(et);
        return (baritone.a.a().internalMiningAirException.value && a.u() instanceof aom) || (this.a.has(a) && a(cj, et));
    }
    
    private List<et> a() {
        if (!baritone.a.a().mineScanDroppedItems.value) {
            return Collections.emptyList();
        }
        final ArrayList<Object> list = new ArrayList<Object>();
        final Iterator<vg> iterator = this.a.world().e.iterator();
        while (iterator.hasNext()) {
            final vg vg;
            if ((vg = iterator.next()) instanceof acl && this.a.has(((acl)vg).k())) {
                list.add(new et(vg));
            }
        }
        list.addAll(this.a.keySet());
        return (List<et>)list;
    }
    
    public static List<et> a(final cj cj, final BlockOptionalMetaLookup blockOptionalMetaLookup, final List<et> list, final List<et> list2, final List<et> list3) {
        final ArrayList<Object> list4 = new ArrayList<Object>();
        final ArrayList<aow> list5 = new ArrayList<aow>();
        final Iterator<BlockOptionalMeta> iterator = blockOptionalMetaLookup.blocks().iterator();
        while (iterator.hasNext()) {
            final aow block = iterator.next().getBlock();
            if (l.a.contains((Object)block)) {
                final BetterBlockPos playerFeet = cj.a.getPlayerContext().playerFeet();
                list4.addAll(cj.a.getCachedWorld().getLocationsOf(BlockUtils.blockToString(block), a.a().maxCachedWorldScanCount.value, playerFeet.a, playerFeet.c, 2));
            }
            else {
                list5.add(block);
            }
        }
        final List<et> a = a(cj, (List<et>)list4, blockOptionalMetaLookup, list2, list3);
        if (!list5.isEmpty() || (baritone.a.a().extendCacheOnThreshold.value && a.size() < 64)) {
            a.addAll(w.a.scanChunkRadius(cj.a.getPlayerContext(), blockOptionalMetaLookup, 64, 10, 32));
        }
        a.addAll(list);
        return a(cj, a, blockOptionalMetaLookup, list2, list3);
    }
    
    private void b() {
        final List<et> a = this.a();
        this.a.addAll(a);
        final BetterBlockPos playerFeet = this.a.playerFeet();
        final fn fn = new fn(this.a);
        for (int i = playerFeet.p() - 10; i <= playerFeet.p() + 10; ++i) {
            for (int j = playerFeet.q() - 10; j <= playerFeet.q() + 10; ++j) {
                for (int k = playerFeet.r() - 10; k <= playerFeet.r() + 10; ++k) {
                    if (this.a.has(fn.a(i, j, k))) {
                        final et et2 = new et(i, j, k);
                        if ((baritone.a.a().legitMineIncludeDiagonals.value && this.a.stream().anyMatch(et -> et.n((fq)et2) <= 2.0)) || RotationUtils.reachable(this.a.player(), et2, 20.0).isPresent()) {
                            this.a.add(et2);
                        }
                    }
                }
            }
        }
        this.a = a(new cj(this.a), this.a, this.a, this.b, a);
    }
    
    private static List<et> a(final cj cj, final List<et> list, final BlockOptionalMetaLookup blockOptionalMetaLookup, final List<et> list2, final List<et> list3) {
        final Iterator<et> iterator;
        et et4;
        final et et5;
        list3.removeIf(fq -> {
            list.iterator();
            while (iterator.hasNext()) {
                et4 = iterator.next();
                if (et5.n(fq) <= 9.0 && blockOptionalMetaLookup.has(cj.a(et4.p(), et4.q(), et4.r())) && a(cj, et4)) {
                    return true;
                }
            }
            return false;
        });
        final List<? super Object> list4;
        if ((list4 = list.stream().distinct().filter(et3 -> !cj.a.a(et3.p(), et3.r()) || blockOptionalMetaLookup.has(cj.a(et3.p(), et3.q(), et3.r())) || list3.contains(et3)).filter(et -> a(cj, et)).filter(et2 -> !list2.contains(et2)).sorted(Comparator.comparingDouble((ToDoubleFunction<? super Object>)cj.a.getPlayerContext().player()::c)).collect((Collector<? super Object, ?, List<? super Object>>)Collectors.toList())).size() > 64) {
            return (List<et>)list4.subList(0, 64);
        }
        return (List<et>)list4;
    }
    
    private static boolean a(final cj cj, final et et) {
        return cl.a(cj, et.p(), et.q(), et.r(), cj.a.a(et), true) < 1000000.0 && (cj.a.a(et.a()).u() != aox.h || cj.a.a(et.b()).u() != aox.h);
    }
    
    @Override
    public final void mineByName(final int n, final String... array) {
        this.mine(n, new BlockOptionalMetaLookup(array));
    }
    
    @Override
    public final void mine(int a, BlockOptionalMetaLookup a2) {
        while (true) {
            this.a = a2;
            if (a2 == null || a.a().allowBreak.value) {
                break;
            }
            this.logDirect("Unable to mine when allowBreak is false!");
            final fc fc = this;
            final int n = a;
            a2 = null;
            a = n;
            this = fc;
        }
        this.a = a;
        this.a = new ArrayList<et>();
        this.b = new ArrayList<et>();
        this.a = null;
        this.a = null;
        this.a = new HashMap<et, Long>();
        if (a2 != null) {
            this.a(new ArrayList<et>(), new cj(this.a));
        }
    }
}
