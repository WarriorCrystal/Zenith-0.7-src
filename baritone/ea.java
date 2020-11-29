// 
// Decompiled by Procyon v0.5.36
// 

package baritone;

import baritone.api.pathing.goals.GoalComposite;
import baritone.api.pathing.goals.GoalBlock;
import java.util.Iterator;
import baritone.api.pathing.goals.Goal;
import baritone.api.process.PathingCommandType;
import baritone.api.utils.input.Input;
import baritone.api.process.PathingCommand;
import java.util.OptionalInt;
import baritone.api.utils.RayTraceUtils;
import baritone.api.utils.RotationUtils;
import baritone.api.utils.Rotation;
import java.util.Collection;
import java.util.ArrayList;
import baritone.api.schematic.FillSchematic;
import baritone.utils.schematic.schematica.SchematicaHelper;
import java.util.Optional;
import baritone.api.schematic.IStaticSchematic;
import java.io.InputStream;
import java.io.FileInputStream;
import baritone.api.schematic.format.ISchematicFormat;
import java.io.File;
import java.util.List;
import baritone.api.schematic.ISchematic;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import baritone.api.utils.BetterBlockPos;
import java.util.HashSet;
import baritone.api.process.IBuilderProcess;

public final class ea extends fk implements IBuilderProcess
{
    private HashSet<BetterBlockPos> a;
    private LongOpenHashSet a;
    private String a;
    private ISchematic b;
    public ISchematic a;
    public fq a;
    private int a;
    private boolean a;
    private int b;
    private int c;
    public List<awt> a;
    
    public ea(final a a) {
        super(a);
    }
    
    @Override
    public final void build(final String a, final ISchematic a2, final fq fq) {
        this.a = a;
        this.a = a2;
        this.b = null;
        int p3 = fq.p();
        int q = fq.q();
        int r = fq.r();
        if (a.a().schematicOrientationX.value) {
            p3 += a2.widthX();
        }
        if (a.a().schematicOrientationY.value) {
            q += a2.heightY();
        }
        if (a.a().schematicOrientationZ.value) {
            r += a2.lengthZ();
        }
        this.a = new fq(p3, q, r);
        this.a = false;
        this.b = 0;
        this.c = 0;
        this.a = new LongOpenHashSet();
    }
    
    @Override
    public final void resume() {
        this.a = false;
    }
    
    @Override
    public final void pause() {
        this.a = true;
    }
    
    @Override
    public final boolean isPaused() {
        return this.a;
    }
    
    @Override
    public final boolean build(final String s, final File file, final fq fq) {
        final Optional<ISchematicFormat> byFile;
        if (!(byFile = gp.a.getByFile(file)).isPresent()) {
            return false;
        }
        Object parse;
        try {
            parse = byFile.get().parse(new FileInputStream(file));
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
        if (baritone.a.a().mapArtMode.value) {
            parse = new go((IStaticSchematic)parse);
        }
        this.build(s, (ISchematic)parse, fq);
        return true;
    }
    
    @Override
    public final void buildOpenSchematic() {
        if (!SchematicaHelper.a()) {
            this.logDirect("Schematica is not present");
            return;
        }
        final Optional<rr<IStaticSchematic, et>> a;
        if ((a = SchematicaHelper.a()).isPresent()) {
            final IStaticSchematic staticSchematic = (IStaticSchematic)a.get().a();
            this.build(((IStaticSchematic)a.get().a()).toString(), (ISchematic)(((boolean)baritone.a.a().mapArtMode.value) ? new go(staticSchematic) : staticSchematic), (fq)a.get().b());
            return;
        }
        this.logDirect("No schematic currently open");
    }
    
    @Override
    public final void clearArea(final et et, final et et2) {
        this.build("clear area", new FillSchematic(Math.abs(et.p() - et2.p()) + 1, Math.abs(et.q() - et2.q()) + 1, Math.abs(et.r() - et2.r()) + 1, aox.a.t()), (fq)new et(Math.min(et.p(), et2.p()), Math.min(et.q(), et2.q()), Math.min(et.r(), et2.r())));
    }
    
    @Override
    public final List<awt> getApproxPlaceable() {
        return new ArrayList<awt>(this.a);
    }
    
    @Override
    public final boolean isActive() {
        return this.a != null;
    }
    
    private Optional<rr<BetterBlockPos, Rotation>> a(final ee ee) {
        final BetterBlockPos playerFeet = this.a.playerFeet();
        final BetterBlockPos a = this.a.a.a();
        for (int i = -5; i <= 5; ++i) {
            for (int j = baritone.a.a().breakFromAbove.value ? -1 : 0; j <= 5; ++j) {
                for (int k = -5; k <= 5; ++k) {
                    final int n = playerFeet.a + i;
                    final int n2 = playerFeet.b + j;
                    final int n3 = playerFeet.c + k;
                    final awt a2;
                    final awt a3;
                    if ((j != -1 || n != a.a || n3 != a.c) && (a2 = ee.a(n, n2, n3, ee.a.a(n, n2, n3))) != null && (a3 = ee.a.a(n, n2, n3)).u() != aox.a && !(a3.u() instanceof aru) && !a(a3, a2, false)) {
                        final BetterBlockPos betterBlockPos = new BetterBlockPos(n, n2, n3);
                        final Optional<Rotation> reachable;
                        if ((reachable = RotationUtils.reachable(this.a.player(), betterBlockPos, this.a.playerController().getBlockReachDistance())).isPresent()) {
                            return Optional.of(new rr((Object)betterBlockPos, (Object)reachable.get()));
                        }
                    }
                }
            }
        }
        return Optional.empty();
    }
    
    private Optional<ej> a(final ee ee, final List<awt> list) {
        final BetterBlockPos playerFeet = this.a.playerFeet();
        for (int i = -5; i <= 5; ++i) {
            for (int j = -5; j <= 1; ++j) {
            Label_0995:
                for (int k = -5; k <= 5; ++k) {
                    final int n = playerFeet.a + i;
                    final int n2 = playerFeet.b + j;
                    final int n3 = playerFeet.c + k;
                    final awt a;
                    if ((a = ee.a(n, n2, n3, ee.a.a(n, n2, n3))) != null) {
                        final awt a2 = ee.a.a(n, n2, n3);
                        if (cl.a(n, n3, a2, ee.a) && !a(a2, a, false) && (j != 1 || ee.a.a(n, n2 + 1, n3).u() != aox.a)) {
                            list.add(a);
                            final awt awt = a;
                            final int n4 = n;
                            final int n5 = n2;
                            final int n6 = n3;
                            final fn a3 = ee.a;
                            final int n7 = n6;
                            final int n8 = n5;
                            final int n9 = n4;
                            final awt awt2 = awt;
                        Label_0983:
                            while (true) {
                                fa[] values;
                                for (int length = (values = fa.values()).length, l = 0; l < length; ++l) {
                                    final fa fa = values[l];
                                    final BetterBlockPos offset = new BetterBlockPos(n9, n8, n7).offset(fa);
                                    final awt a4 = a3.a(offset);
                                    if (!cl.a(offset.a, offset.c, a4, a3) && this.a.world().a(awt2.u(), (et)new BetterBlockPos(n9, n8, n7), false, fa, (vg)null)) {
                                        final bhb e = a4.e((amy)this.a.world(), (et)offset);
                                        final fa fa2 = fa;
                                        bhe[] array3 = null;
                                        bhe[] array2 = null;
                                        switch (ed.a[fa2.ordinal()]) {
                                            case 1: {
                                                final bhe[] array = array2 = (array3 = new bhe[5]);
                                                array[0] = new bhe(0.5, 1.0, 0.5);
                                                array[1] = new bhe(0.1, 1.0, 0.5);
                                                array[2] = new bhe(0.9, 1.0, 0.5);
                                                array[3] = new bhe(0.5, 1.0, 0.1);
                                                array[4] = new bhe(0.5, 1.0, 0.9);
                                                break;
                                            }
                                            case 2: {
                                                final bhe[] array4 = array2 = (array3 = new bhe[5]);
                                                array4[0] = new bhe(0.5, 0.0, 0.5);
                                                array4[1] = new bhe(0.1, 0.0, 0.5);
                                                array4[2] = new bhe(0.9, 0.0, 0.5);
                                                array4[3] = new bhe(0.5, 0.0, 0.1);
                                                array4[4] = new bhe(0.5, 0.0, 0.9);
                                                break;
                                            }
                                            case 3:
                                            case 4:
                                            case 5:
                                            case 6: {
                                                final double n10 = (fa2.g() == 0) ? 0.5 : ((1 + fa2.g()) / 2.0);
                                                final double n11 = (fa2.i() == 0) ? 0.5 : ((1 + fa2.i()) / 2.0);
                                                final bhe[] array5 = array2 = (array3 = new bhe[2]);
                                                array5[0] = new bhe(n10, 0.25, n11);
                                                array5[1] = new bhe(n10, 0.75, n11);
                                                break;
                                            }
                                            default: {
                                                throw new IllegalStateException();
                                            }
                                        }
                                        final bhe[] array6 = array2;
                                        for (int length2 = array3.length, n12 = 0; n12 < length2; ++n12) {
                                            final bhe bhe = array6[n12];
                                            final Rotation calcRotationFromVec3d = RotationUtils.calcRotationFromVec3d(RayTraceUtils.inferSneakingEyePosition((vg)this.a.player()), new bhe(offset.a + e.a * bhe.b + e.d * (1.0 - bhe.b), offset.b + e.b * bhe.c + e.e * (1.0 - bhe.c), offset.c + e.c * bhe.d + e.f * (1.0 - bhe.d)), this.a.playerRotations());
                                            final bhc rayTraceTowards;
                                            final OptionalInt a5;
                                            if ((rayTraceTowards = RayTraceUtils.rayTraceTowards((vg)this.a.player(), calcRotationFromVec3d, this.a.playerController().getBlockReachDistance(), true)) != null && rayTraceTowards.a == bhc$a.b && rayTraceTowards.a().equals((Object)offset) && rayTraceTowards.b == fa.d() && (a5 = this.a(awt2, rayTraceTowards, calcRotationFromVec3d)).isPresent()) {
                                                final Optional<ej> optional2;
                                                final Optional<ej> optional = optional2 = Optional.of(new ej(a5.getAsInt(), offset, fa.d(), calcRotationFromVec3d));
                                                break Label_0983;
                                            }
                                        }
                                        continue;
                                        Optional<ej> optional2 = null;
                                        final Optional<ej> optional3 = optional2;
                                        Optional<ej> optional = null;
                                        if (optional.isPresent()) {
                                            return optional3;
                                        }
                                        continue Label_0995;
                                    }
                                }
                                Optional<ej> optional2;
                                final Optional<ej> optional = optional2 = Optional.empty();
                                continue Label_0983;
                            }
                        }
                    }
                }
            }
        }
        return Optional.empty();
    }
    
    private OptionalInt a(final awt awt, final bhc bhc, final Rotation rotation) {
        for (int i = 0; i < 9; ++i) {
            final aip aip;
            if (!(aip = (aip)this.a.player().bv.a.get(i)).b() && aip.c() instanceof ahb) {
                final float v = this.a.player().v;
                final float w = this.a.player().w;
                this.a.player().v = rotation.getYaw();
                this.a.player().w = rotation.getPitch();
                final awt a = ((ahb)aip.c()).d().a(this.a.world(), bhc.a().a(bhc.b), bhc.b, (float)bhc.c.b - bhc.a().p(), (float)bhc.c.c - bhc.a().q(), (float)bhc.c.d - bhc.a().r(), aip.c().a(aip.j()), (vp)this.a.player());
                this.a.player().v = v;
                this.a.player().w = w;
                if (a(a, awt, true)) {
                    return OptionalInt.of(i);
                }
            }
        }
        return OptionalInt.empty();
    }
    
    @Override
    public final PathingCommand onTick(final boolean b, final boolean b2) {
        while (true) {
            this.a = this.a(36);
            if (this.a.a.isInputForcedDown(Input.CLICK_LEFT)) {
                this.a = 5;
            }
            else {
                --this.a;
            }
            this.a.a.clearAllKeys();
            if (this.a) {
                return new PathingCommand(null, PathingCommandType.CANCEL_AND_SET_GOAL);
            }
            if (baritone.a.a().buildInLayers.value) {
                if (this.b == null) {
                    this.b = this.a;
                }
                final ISchematic b3 = this.b;
                int n;
                int n2;
                if (baritone.a.a().layerOrder.value) {
                    n = b3.heightY() - 1;
                    n2 = b3.heightY() - this.b;
                }
                else {
                    n = this.b - 1;
                    n2 = 0;
                }
                this.a = new eb(this, b3, n2, n);
            }
            final ee ee2;
            final ee ee = ee2 = new ee(this);
            boolean b4 = false;
            Label_0276: {
                Label_0275: {
                    if (this.a == null) {
                        this.a = new HashSet<BetterBlockPos>();
                        this.b(ee2);
                        if (this.a.isEmpty()) {
                            break Label_0275;
                        }
                    }
                    this.a(ee2);
                    if (this.a.isEmpty()) {
                        this.b(ee2);
                    }
                    if (!this.a.isEmpty()) {
                        b4 = true;
                        break Label_0276;
                    }
                }
                b4 = false;
            }
            if (!b4) {
                if (baritone.a.a().buildInLayers.value && this.b < this.b.heightY()) {
                    this.logDirect("Starting layer " + this.b);
                    ++this.b;
                }
                else {
                    final fq obj = baritone.a.a().buildRepeat.value;
                    final int intValue = baritone.a.a().buildRepeatCount.value;
                    ++this.c;
                    if (obj.equals((Object)new fq(0, 0, 0)) || (intValue != -1 && this.c >= intValue)) {
                        this.logDirect("Done building");
                        this.onLostControl();
                        return null;
                    }
                    this.b = 0;
                    this.a = (fq)new et(this.a).a(obj);
                    this.logDirect("Repeating build in vector " + obj + ", new origin is " + this.a);
                }
            }
            else {
                if (baritone.a.a().distanceTrim.value) {
                    final HashSet<BetterBlockPos> a;
                    (a = new HashSet<BetterBlockPos>(this.a)).removeIf(betterBlockPos -> betterBlockPos.f(this.a.player().p, this.a.player().q, this.a.player().r) > 200.0);
                    if (!a.isEmpty()) {
                        this.a = a;
                    }
                }
                final Optional<rr<BetterBlockPos, Rotation>> a2;
                if ((a2 = this.a(ee)).isPresent() && b2 && this.a.player().z) {
                    final Rotation rotation = (Rotation)a2.get().b();
                    final BetterBlockPos betterBlockPos2 = (BetterBlockPos)a2.get().a();
                    this.a.a.updateTarget(rotation, true);
                    cl.a(this.a, ee.a(betterBlockPos2));
                    if (this.a.player().aU()) {
                        this.a.a.setInputForceState(Input.SNEAK, true);
                    }
                    if (this.a.isLookingAt(betterBlockPos2) || this.a.playerRotations().isReallyCloseTo(rotation)) {
                        this.a.a.setInputForceState(Input.CLICK_LEFT, true);
                    }
                    return new PathingCommand(null, PathingCommandType.CANCEL_AND_SET_GOAL);
                }
                final ArrayList<awt> list = new ArrayList<awt>();
                final Optional<ej> a3;
                if ((a3 = this.a(ee, (List<awt>)list)).isPresent() && b2 && this.a.player().z && this.a <= 0) {
                    final Rotation a4 = a3.get().a;
                    this.a.a.updateTarget(a4, true);
                    this.a.player().bv.d = a3.get().a;
                    this.a.a.setInputForceState(Input.SNEAK, true);
                    if ((this.a.isLookingAt(a3.get().a) && this.a.objectMouseOver().b.equals((Object)a3.get().a)) || this.a.playerRotations().isReallyCloseTo(a4)) {
                        this.a.a.setInputForceState(Input.CLICK_RIGHT, true);
                    }
                    return new PathingCommand(null, PathingCommandType.CANCEL_AND_SET_GOAL);
                }
                Label_1168: {
                    if (baritone.a.a().allowInventory.value) {
                        final ArrayList<Integer> list2 = new ArrayList<Integer>();
                        final ArrayList<awt> list3 = new ArrayList<awt>();
                    Label_0983:
                        for (final awt awt : list) {
                            for (int i = 0; i < 9; ++i) {
                                if (a(this.a.get(i), awt, true)) {
                                    list2.add(i);
                                    continue Label_0983;
                                }
                            }
                            list3.add(awt);
                        }
                        for (int j = 9; j < 36; ++j) {
                            final Iterator<Object> iterator2 = list3.iterator();
                            while (iterator2.hasNext()) {
                                if (a(this.a.get(j), iterator2.next(), true)) {
                                    this.a.a.a(j, list2::contains);
                                    break Label_1168;
                                }
                            }
                        }
                    }
                }
                Goal goal;
                if ((goal = this.a(ee, this.a.subList(0, 9))) == null && (goal = this.a(ee, this.a)) == null) {
                    this.logDirect("Unable to do it. Pausing. resume to resume, cancel to cancel");
                    this.a = true;
                    return new PathingCommand(null, PathingCommandType.REQUEST_PAUSE);
                }
                return new fu(goal, PathingCommandType.FORCE_REVALIDATE_GOAL_AND_PATH, ee);
            }
        }
    }
    
    private void a(final ee ee) {
        final BetterBlockPos playerFeet = this.a.playerFeet();
        int intValue;
        for (int i = -(intValue = baritone.a.a().builderTickScanRadius.value); i <= intValue; ++i) {
            for (int j = -intValue; j <= intValue; ++j) {
                for (int k = -intValue; k <= intValue; ++k) {
                    final int n = playerFeet.a + i;
                    final int n2 = playerFeet.b + j;
                    final int n3 = playerFeet.c + k;
                    final awt a;
                    if ((a = ee.a(n, n2, n3, ee.a.a(n, n2, n3))) != null) {
                        final BetterBlockPos betterBlockPos = new BetterBlockPos(n, n2, n3);
                        if (a(ee.a.a(n, n2, n3), a, false)) {
                            this.a.remove(betterBlockPos);
                            this.a.add(BetterBlockPos.longHash(betterBlockPos));
                        }
                        else {
                            this.a.add(betterBlockPos);
                            this.a.remove(BetterBlockPos.longHash(betterBlockPos));
                        }
                    }
                }
            }
        }
    }
    
    private void b(final ee ee) {
        this.a = new HashSet<BetterBlockPos>();
        for (int i = 0; i < this.a.heightY(); ++i) {
            for (int j = 0; j < this.a.lengthZ(); ++j) {
                for (int k = 0; k < this.a.widthX(); ++k) {
                    final int n = k + this.a.p();
                    final int n2 = i + this.a.q();
                    final int n3 = j + this.a.r();
                    final awt a = ee.a.a(n, n2, n3);
                    if (this.a.inSchematic(k, i, j, a)) {
                        if (ee.a.a(n, n3)) {
                            if (a(ee.a.a(n, n2, n3), this.a.desiredState(k, i, j, a, this.a), false)) {
                                this.a.add(BetterBlockPos.longHash(n, n2, n3));
                            }
                            else {
                                this.a.add(new BetterBlockPos(n, n2, n3));
                                this.a.remove(BetterBlockPos.longHash(n, n2, n3));
                                if (this.a.size() > baritone.a.a().incorrectSize.value) {
                                    return;
                                }
                            }
                        }
                        else if (!this.a.contains(BetterBlockPos.longHash(n, n2, n3))) {
                            this.a.add(new BetterBlockPos(n, n2, n3));
                            if (this.a.size() > baritone.a.a().incorrectSize.value) {
                                return;
                            }
                        }
                    }
                }
            }
        }
    }
    
    private Goal a(final ee ee, final List<awt> list) {
        final ArrayList list2 = new ArrayList();
        final ArrayList list3 = new ArrayList();
        final ArrayList list4 = new ArrayList();
        final Object o;
        final awt awt;
        final List<BetterBlockPos> list5;
        final List<BetterBlockPos> list6;
        final List<BetterBlockPos> list7;
        this.a.forEach(betterBlockPos4 -> {
            ee.a.a(betterBlockPos4);
            if (((awt)o).u() instanceof aom) {
                if (list.contains(ee.a(betterBlockPos4.a, betterBlockPos4.b, betterBlockPos4.c, awt))) {
                    list5.add(betterBlockPos4);
                }
            }
            else if (awt.u() instanceof aru) {
                if (!cl.b(awt)) {
                    list6.add(betterBlockPos4);
                }
            }
            else {
                list7.add(betterBlockPos4);
            }
            return;
        });
        final ArrayList list8 = new ArrayList();
        final List<Goal> list9;
        final Goal goal;
        list3.forEach(betterBlockPos2 -> {
            if (baritone.a.a().goalBreakFromAbove.value && ee.a.a(betterBlockPos2.a()).u() instanceof aom && ee.a.a(betterBlockPos2.b(2)).u() instanceof aom) {
                // new(baritone.ei.class)
                new ei(new eg(betterBlockPos2), new ec(this, betterBlockPos2.a()));
            }
            else {
                // new(baritone.eg.class)
                new eg(betterBlockPos2);
            }
            list9.add(goal);
            return;
        });
        final ArrayList<GoalBlock> list10 = new ArrayList<GoalBlock>();
        final List list11;
        boolean b;
        fa[] a;
        int i = 0;
        fa fa;
        final awt awt2;
        final List<Goal> list12;
        final Goal goal2;
        list2.forEach(betterBlockPos3 -> {
            if (!list11.contains(betterBlockPos3.down()) && !list11.contains(betterBlockPos3.down(2))) {
                Label_0232_2: {
                    if (this.a.world().o((et)betterBlockPos3).u() != aox.a) {
                        // new(baritone.eh.class)
                        new eh(betterBlockPos3);
                    }
                    else {
                        b = (this.a.world().o(betterBlockPos3.a()).u() != aox.a);
                        this.a.world().o((et)betterBlockPos3);
                        a = ck.a;
                        while (i < 5) {
                            fa = a[i];
                            if (cl.c(this.a, betterBlockPos3.a(fa)) && this.a.world().a(ee.a(betterBlockPos3.p(), betterBlockPos3.q(), betterBlockPos3.r(), awt2).u(), (et)betterBlockPos3, false, fa, (vg)null)) {
                                // new(baritone.ef.class)
                                new ef(betterBlockPos3, betterBlockPos3.a(fa), b);
                                break Label_0232_2;
                            }
                            else {
                                ++i;
                            }
                        }
                        // new(baritone.eh.class)
                        new eh(betterBlockPos3);
                    }
                }
                list12.add(goal2);
            }
            return;
        });
        list4.forEach(betterBlockPos -> list10.add(new GoalBlock(betterBlockPos.up())));
        if (!list10.isEmpty()) {
            return new ei(new GoalComposite((Goal[])list10.toArray(new Goal[0])), new GoalComposite((Goal[])list8.toArray(new Goal[0])));
        }
        if (list8.isEmpty()) {
            return null;
        }
        return new GoalComposite((Goal[])list8.toArray(new Goal[0]));
    }
    
    @Override
    public final void onLostControl() {
        this.a = null;
        this.a = null;
        this.a = null;
        this.b = null;
        this.b = 0;
        this.c = 0;
        this.a = false;
        this.a = null;
    }
    
    @Override
    public final String displayName0() {
        if (this.a) {
            return "Builder Paused";
        }
        return "Building " + this.a;
    }
    
    private List<awt> a(final int n) {
        final ArrayList<awt> list = new ArrayList<awt>();
        for (int i = 0; i < n; ++i) {
            final aip aip;
            if ((aip = (aip)this.a.player().bv.a.get(i)).b() || !(aip.c() instanceof ahb)) {
                list.add(aox.a.t());
            }
            else {
                list.add(((ahb)aip.c()).d().a(this.a.world(), (et)this.a.playerFeet(), fa.b, (float)this.a.player().p, (float)this.a.player().q, (float)this.a.player().r, aip.c().a(aip.j()), (vp)this.a.player()));
            }
        }
        return list;
    }
    
    private static boolean a(final awt awt, final awt obj, final boolean b) {
        return obj == null || (awt.u() instanceof aru && a.a().okIfWater.value) || (obj.u() instanceof aom && a.a().buildIgnoreBlocks.value.contains(awt.u())) || (!(awt.u() instanceof aom) && a.a().buildIgnoreExisting.value && !b) || awt.equals(obj);
    }
}
