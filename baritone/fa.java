// 
// Decompiled by Procyon v0.5.36
// 

package baritone;

import java.util.Optional;
import baritone.api.utils.input.Input;
import baritone.api.utils.Rotation;
import baritone.api.utils.RotationUtils;
import baritone.api.pathing.goals.GoalGetToBlock;
import baritone.api.pathing.goals.GoalBlock;
import baritone.api.pathing.goals.GoalTwoBlocks;
import java.util.function.Predicate;
import java.util.Collections;
import baritone.api.utils.BlockOptionalMetaLookup;
import java.util.Iterator;
import java.util.function.Consumer;
import java.util.function.ToDoubleFunction;
import java.util.Comparator;
import java.util.Collection;
import baritone.api.pathing.goals.GoalComposite;
import java.util.function.Function;
import baritone.api.pathing.goals.Goal;
import baritone.api.process.PathingCommandType;
import baritone.api.process.PathingCommand;
import baritone.api.IBaritone;
import java.util.ArrayList;
import java.util.List;
import baritone.api.utils.BlockOptionalMeta;
import baritone.api.process.IGetToBlockProcess;

public final class fa extends fk implements IGetToBlockProcess
{
    private BlockOptionalMeta a;
    private List<et> a;
    private List<et> b;
    private et a;
    private int a;
    private int b;
    
    public fa(final a a) {
        super(a);
        this.a = 0;
        this.b = 0;
    }
    
    @Override
    public final void getToBlock(final BlockOptionalMeta a) {
        this.onLostControl();
        this.a = a;
        this.a = this.a.playerFeet();
        this.b = new ArrayList<et>();
        this.b = 0;
        this.a(new ArrayList<et>(), new cj(this.a));
    }
    
    @Override
    public final boolean isActive() {
        return this.a != null;
    }
    
    @Override
    public final synchronized PathingCommand onTick(final boolean b, final boolean b2) {
        if (this.a == null) {
            this.a(new ArrayList<et>(), new cj(this.a));
        }
        if (this.a.isEmpty()) {
            if (baritone.a.a().exploreForBlocks.value && !b) {
                return new PathingCommand(new fb(this, new et[] { this.a }), PathingCommandType.FORCE_REVALIDATE_GOAL_AND_PATH);
            }
            this.logDirect("No known locations of " + this.a + ", canceling GetToBlock");
            if (b2) {
                this.onLostControl();
            }
            return new PathingCommand(null, PathingCommandType.CANCEL_AND_SET_GOAL);
        }
        else {
            final GoalComposite goalComposite = new GoalComposite((Goal[])this.a.stream().map((Function<? super Object, ?>)this::a).toArray(Goal[]::new));
            if (!b) {
                final int intValue;
                if ((intValue = baritone.a.a().mineGoalUpdateInterval.value) != 0 && this.a++ % intValue == 0) {
                    baritone.a.a().execute(() -> this.a(new ArrayList<et>(this.a), new cj(this.a, true)));
                }
                if (goalComposite.isInGoal(this.a.playerFeet()) && goalComposite.isInGoal(this.a.a.a()) && b2) {
                    if (!a(this.a.getBlock())) {
                        this.onLostControl();
                        return new PathingCommand(null, PathingCommandType.CANCEL_AND_SET_GOAL);
                    }
                    if (this.a()) {
                        this.onLostControl();
                        return new PathingCommand(null, PathingCommandType.CANCEL_AND_SET_GOAL);
                    }
                }
                return new PathingCommand(goalComposite, PathingCommandType.REVALIDATE_GOAL_AND_PATH);
            }
            if (baritone.a.a().blacklistClosestOnFailure.value) {
                this.logDirect("Unable to find any path to " + this.a + ", blacklisting presumably unreachable closest instances...");
                this.blacklistClosest();
                return this.onTick(false, b2);
            }
            this.logDirect("Unable to find any path to " + this.a + ", canceling GetToBlock");
            if (b2) {
                this.onLostControl();
            }
            return new PathingCommand(goalComposite, PathingCommandType.CANCEL_AND_SET_GOAL);
        }
    }
    
    @Override
    public final synchronized boolean blacklistClosest() {
        final ArrayList<et> obj = new ArrayList<et>();
        this.a.stream().min(Comparator.comparingDouble((ToDoubleFunction<? super Object>)this.a.player()::c)).ifPresent(obj::add);
    Label_0058:
        while (true) {
            for (final et et : this.a) {
                for (final et et2 : obj) {
                    final et et3 = et;
                    final et et4 = et2;
                    final et et5 = et3;
                    if (Math.abs(et3.p() - et4.p()) + Math.abs(et5.q() - et4.q()) + Math.abs(et5.r() - et4.r()) == 1) {
                        obj.add(et);
                        this.a.remove(et);
                        continue Label_0058;
                    }
                }
            }
            break;
        }
        obj.size();
        this.logDebug("Blacklisting unreachable locations ".concat(String.valueOf(obj)));
        this.b.addAll(obj);
        return !obj.isEmpty();
    }
    
    @Override
    public final synchronized void onLostControl() {
        this.a = null;
        this.a = null;
        this.a = null;
        this.b = null;
        this.a.a.clearAllKeys();
    }
    
    @Override
    public final String displayName0() {
        if (this.a.isEmpty()) {
            return "Exploring randomly to find " + this.a + ", no known locations";
        }
        return "Get To " + this.a + ", " + this.a.size() + " known locations";
    }
    
    private synchronized void a(final List<et> list, final cj cj) {
        final List<et> a;
        (a = fc.a(cj, new BlockOptionalMetaLookup(new BlockOptionalMeta[] { this.a }), list, this.b, Collections.emptyList())).removeIf(this.b::contains);
        this.a = a;
    }
    
    private Goal a(final et et) {
        final aow block = this.a.getBlock();
        if (baritone.a.a().enterPortal.value && block == aox.aY) {
            return new GoalTwoBlocks(et);
        }
        final aow block2;
        if (a(block2 = this.a.getBlock()) && (block2 == aox.bQ || block2 == aox.ae || block2 == aox.cg) && this.a.a.a(et.a()).k()) {
            return new GoalBlock(et.a());
        }
        return new GoalGetToBlock(et);
    }
    
    private boolean a() {
        final Iterator<et> iterator = this.a.iterator();
        while (iterator.hasNext()) {
            final Optional<Rotation> reachable;
            if ((reachable = RotationUtils.reachable(this.a.player(), iterator.next(), this.a.playerController().getBlockReachDistance())).isPresent()) {
                this.a.a.updateTarget(reachable.get(), true);
                if (this.a.contains(this.a.getSelectedBlock().orElse(null))) {
                    this.a.a.setInputForceState(Input.CLICK_RIGHT, true);
                    System.out.println(this.a.player().by);
                    if (!(this.a.player().by instanceof agi)) {
                        return true;
                    }
                }
                if (this.b++ > 20) {
                    this.logDirect("Right click timed out");
                    return true;
                }
                return false;
            }
        }
        this.logDirect("Arrived but failed to right click open");
        return true;
    }
    
    private static boolean a(final aow aow) {
        return a.a().rightClickContainerOnArrival.value && (aow == aox.ai || aow == aox.al || aow == aox.bQ || aow == aox.ae || aow == aox.cg);
    }
}
