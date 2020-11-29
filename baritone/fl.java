// 
// Decompiled by Procyon v0.5.36
// 

package baritone;

import baritone.api.utils.IPlayerContext;
import baritone.api.utils.Helper;

public final class fl implements Helper
{
    final IPlayerContext a;
    boolean a;
    
    fl(final IPlayerContext a) {
        this.a = a;
    }
    
    public final void a() {
        if (this.a.player() != null && this.a) {
            if (!this.a.playerController().hasBrokenBlock()) {
                this.a.playerController().setHittingBlock(true);
            }
            this.a.playerController().resetBlockRemoving();
            this.a = false;
        }
    }
}
