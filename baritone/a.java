// 
// Decompiled by Procyon v0.5.36
// 

package baritone;

import java.io.IOException;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;
import baritone.api.behavior.IPathingBehavior;
import baritone.api.behavior.ILookBehavior;
import baritone.api.process.IFollowProcess;
import baritone.api.process.IMineProcess;
import baritone.api.process.IBuilderProcess;
import baritone.api.process.IExploreProcess;
import baritone.api.process.IFarmProcess;
import baritone.api.process.ICustomGoalProcess;
import baritone.api.process.IGetToBlockProcess;
import baritone.api.cache.IWorldProvider;
import baritone.api.pathing.calc.IPathingControlManager;
import baritone.api.utils.IInputOverrideHandler;
import baritone.api.selection.ISelectionManager;
import baritone.api.command.manager.ICommandManager;
import java.util.concurrent.Executor;
import baritone.api.BaritoneAPI;
import baritone.api.Settings;
import baritone.api.utils.Helper;
import baritone.api.event.listener.IEventBus;
import baritone.api.event.listener.IGameEventListener;
import baritone.api.utils.IPlayerContext;
import java.io.File;
import java.util.concurrent.ThreadPoolExecutor;
import baritone.api.IBaritone;

public final class a implements IBaritone
{
    private static ThreadPoolExecutor a;
    private static File a;
    public cf a;
    public j a;
    public d a;
    private f a;
    public c a;
    public fr a;
    private ez a;
    private fc a;
    private fa a;
    private ek a;
    public ea a;
    private en a;
    private dy a;
    private ev a;
    public fv a;
    private fh a;
    public cd a;
    private IPlayerContext a;
    public v a;
    public fn a;
    
    a() {
        this.a = new cf(this);
        this.a = gm.a;
        this.a = new j(this);
        this.a = new d(this);
        this.a = new f(this);
        this.a = new c(this);
        this.a = new fr(this);
        this.a = new fv(this);
        this.a = new ez(this);
        this.a = new fc(this);
        this.a = new ek(this);
        this.a = new fa(this);
        this.a = new ea(this);
        this.a = new en(this);
        this.a = new dy(this);
        this.a = new ev(this);
        this.a = new v();
        this.a = new fh(this);
        this.a = new cd(this);
        if (fj.a) {
            this.a.registerEventListener(fj.a);
        }
    }
    
    @Override
    public final IPlayerContext getPlayerContext() {
        return this.a;
    }
    
    @Override
    public final IEventBus getGameEventHandler() {
        return this.a;
    }
    
    @Override
    public final void openClick() {
        new Thread(() -> {
            try {
                Thread.sleep(100L);
                Helper.mc.a(() -> Helper.mc.a((blk)new fp()));
            }
            catch (Exception ex) {}
        }).start();
    }
    
    public static Settings a() {
        return BaritoneAPI.getSettings();
    }
    
    public static File a() {
        return baritone.a.a;
    }
    
    public static Executor a() {
        return baritone.a.a;
    }
    
    static {
        baritone.a.a = new ThreadPoolExecutor(4, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>());
        if (!Files.exists((baritone.a.a = new File(bib.z().w, "baritone")).toPath(), new LinkOption[0])) {
            try {
                Files.createDirectories(baritone.a.a.toPath(), (FileAttribute<?>[])new FileAttribute[0]);
            }
            catch (IOException ex) {}
        }
    }
}
