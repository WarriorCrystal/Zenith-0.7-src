// 
// Decompiled by Procyon v0.5.36
// 

package baritone;

import baritone.api.event.listener.IGameEventListener;
import baritone.api.utils.IPlayerContext;
import baritone.api.behavior.IBehavior;

public class b implements IBehavior
{
    public final a a;
    public final IPlayerContext a;
    
    protected b(final a a) {
        this.a = a;
        this.a = a.getPlayerContext();
        a.a.registerEventListener(this);
    }
}
