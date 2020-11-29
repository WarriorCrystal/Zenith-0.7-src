// 
// Decompiled by Procyon v0.5.36
// 

package baritone;

import java.util.Arrays;
import java.util.Optional;
import java.util.Iterator;
import baritone.api.pathing.goals.GoalComposite;
import baritone.api.pathing.goals.GoalBlock;
import baritone.api.utils.RayTraceUtils;
import java.util.function.Predicate;
import java.util.Collection;
import baritone.api.utils.input.Input;
import baritone.api.utils.Rotation;
import baritone.api.utils.RotationUtils;
import baritone.api.pathing.goals.Goal;
import baritone.api.process.PathingCommandType;
import java.util.ArrayList;
import baritone.api.process.PathingCommand;
import java.util.List;
import baritone.api.process.IFarmProcess;

public final class ev extends fk implements IFarmProcess
{
    private boolean a;
    private List<et> a;
    private int a;
    private static final List<ain> b;
    private static final List<ain> c;
    
    public ev(final a a) {
        super(a);
    }
    
    @Override
    public final boolean isActive() {
        return this.a;
    }
    
    @Override
    public final void farm() {
        this.a = true;
        this.a = null;
    }
    
    private static boolean a(final amu amu, final et et, final awt awt) {
        ew[] values;
        for (int length = (values = ew.values()).length, i = 0; i < length; ++i) {
            final ew ew;
            if ((ew = values[i]).a == awt.u()) {
                return ew.a(amu, et, awt);
            }
        }
        return false;
    }
    
    private boolean a(final aip aip) {
        return ev.b.contains(aip.c());
    }
    
    private boolean b(final aip aip) {
        return !aip.b() && aip.c() instanceof aht && ahs.a(aip.j()) == ahs.a;
    }
    
    private boolean c(final aip aip) {
        return !aip.b() && aip.c().equals(air.bG);
    }
    
    @Override
    public final PathingCommand onTick(final boolean b, final boolean b2) {
        final ArrayList<aow> list = new ArrayList<aow>();
        ew[] values;
        for (int length = (values = ew.values()).length, i = 0; i < length; ++i) {
            list.add(values[i].a);
        }
        if (baritone.a.a().replantCrops.value) {
            list.add(aox.ak);
            if (baritone.a.a().replantNetherWart.value) {
                list.add(aox.aW);
            }
        }
        if (baritone.a.a().mineGoalUpdateInterval.value != 0 && this.a++ % baritone.a.a().mineGoalUpdateInterval.value == 0) {
            baritone.a.a().execute(() -> this.a = w.a.scanChunkRadius(this.a, list, 256, 10, 10));
        }
        if (this.a == null) {
            return new PathingCommand(null, PathingCommandType.REQUEST_PAUSE);
        }
        final ArrayList<et> list2 = new ArrayList<et>();
        final ArrayList<et> c = new ArrayList<et>();
        final ArrayList<et> list3 = new ArrayList<et>();
        final ArrayList<et> c2 = new ArrayList<et>();
        for (final et et : this.a) {
            final awt o = this.a.world().o(et);
            final boolean b3 = this.a.world().o(et.a()).u() instanceof aom;
            if (o.u() == aox.ak) {
                if (!b3) {
                    continue;
                }
                c.add(et);
            }
            else if (o.u() == aox.aW) {
                if (!b3) {
                    continue;
                }
                c2.add(et);
            }
            else if (a(this.a.world(), et, o)) {
                list2.add(et);
            }
            else {
                final aoz aoz;
                if (!(o.u() instanceof aoz) || !(aoz = (aoz)o.u()).a(this.a.world(), et, o, true) || !aoz.a(this.a.world(), this.a.world().r, et, o)) {
                    continue;
                }
                list3.add(et);
            }
        }
        this.a.a.clearAllKeys();
        for (final et et2 : list2) {
            final Optional<Rotation> reachable;
            if ((reachable = RotationUtils.reachable(this.a, et2)).isPresent() && b2) {
                this.a.a.updateTarget(reachable.get(), true);
                cl.a(this.a, this.a.world().o(et2));
                if (this.a.isLookingAt(et2)) {
                    this.a.a.setInputForceState(Input.CLICK_LEFT, true);
                }
                return new PathingCommand(null, PathingCommandType.REQUEST_PAUSE);
            }
        }
        final ArrayList<et> list4;
        (list4 = new ArrayList<et>(c)).addAll(c2);
        for (final et et3 : list4) {
            final boolean contains = c2.contains(et3);
            final Optional<Rotation> reachableOffset;
            final bhc rayTraceTowards;
            if ((reachableOffset = RotationUtils.reachableOffset((vg)this.a.player(), et3, new bhe(et3.p() + 0.5, (double)(et3.q() + 1), et3.r() + 0.5), this.a.playerController().getBlockReachDistance(), false)).isPresent() && b2 && this.a.a.a(true, contains ? this::c : this::a) && (rayTraceTowards = RayTraceUtils.rayTraceTowards((vg)this.a.player(), reachableOffset.get(), this.a.playerController().getBlockReachDistance())).a == bhc$a.b && rayTraceTowards.b == fa.b) {
                this.a.a.updateTarget(reachableOffset.get(), true);
                if (this.a.isLookingAt(et3)) {
                    this.a.a.setInputForceState(Input.CLICK_RIGHT, true);
                }
                return new PathingCommand(null, PathingCommandType.REQUEST_PAUSE);
            }
        }
        for (final et et4 : list3) {
            final Optional<Rotation> reachable2;
            if ((reachable2 = RotationUtils.reachable(this.a, et4)).isPresent() && b2 && this.a.a.a(true, this::b)) {
                this.a.a.updateTarget(reachable2.get(), true);
                if (this.a.isLookingAt(et4)) {
                    this.a.a.setInputForceState(Input.CLICK_RIGHT, true);
                }
                return new PathingCommand(null, PathingCommandType.REQUEST_PAUSE);
            }
        }
        if (b) {
            this.logDirect("Farm failed");
            this.onLostControl();
            return new PathingCommand(null, PathingCommandType.REQUEST_PAUSE);
        }
        final ArrayList<eg> list5 = new ArrayList<eg>();
        final Iterator<Object> iterator5 = list2.iterator();
        while (iterator5.hasNext()) {
            list5.add(new eg(iterator5.next()));
        }
        if (this.a.a.a(false, this::a)) {
            final Iterator<Object> iterator6 = c.iterator();
            while (iterator6.hasNext()) {
                list5.add((eg)new GoalBlock(iterator6.next().a()));
            }
        }
        if (this.a.a.a(false, this::c)) {
            final Iterator<Object> iterator7 = c2.iterator();
            while (iterator7.hasNext()) {
                list5.add((eg)new GoalBlock(iterator7.next().a()));
            }
        }
        if (this.a.a.a(false, this::b)) {
            final Iterator<Object> iterator8 = list3.iterator();
            while (iterator8.hasNext()) {
                list5.add((eg)new GoalBlock(iterator8.next()));
            }
        }
        final Iterator<vg> iterator9 = (Iterator<vg>)this.a.world().e.iterator();
        while (iterator9.hasNext()) {
            final vg vg;
            if ((vg = iterator9.next()) instanceof acl && vg.z && ev.c.contains(((acl)vg).k().c())) {
                list5.add((eg)new GoalBlock(new et(vg.p, vg.q + 0.1, vg.r)));
            }
        }
        return new PathingCommand(new GoalComposite((Goal[])list5.toArray(new Goal[0])), PathingCommandType.SET_GOAL_AND_PATH);
    }
    
    @Override
    public final void onLostControl() {
        this.a = false;
    }
    
    @Override
    public final String displayName0() {
        return "Farming";
    }
    
    static {
        b = Arrays.asList(air.cV, air.bp, air.Q, air.bo, air.cd, air.cc);
        c = Arrays.asList(air.cV, air.cW, air.bp, air.bn, ain.a(aox.bk), air.Q, air.R, air.bo, ain.a(aox.aU), air.cd, air.cc, air.bG, air.aR, ain.a((aow)aox.aK));
    }
}
