// 
// Decompiled by Procyon v0.5.36
// 

package baritone;

import baritone.api.event.events.RotationMoveEvent$Type;
import baritone.api.event.events.RotationMoveEvent;
import baritone.api.event.events.PlayerUpdateEvent;
import baritone.api.utils.Rotation;
import baritone.api.behavior.ILookBehavior;

public final class d extends b implements ILookBehavior
{
    public Rotation a;
    private boolean a;
    private float a;
    
    public d(final a a) {
        super(a);
    }
    
    @Override
    public final void updateTarget(final Rotation a, final boolean b) {
        this.a = a;
        this.a = (b || !a.a().freeLook.value);
    }
    
    @Override
    public final void onPlayerUpdate(final PlayerUpdateEvent playerUpdateEvent) {
        if (this.a == null) {
            return;
        }
        final boolean b = baritone.a.a().antiCheatCompatibility.value && !this.a;
        switch (e.a[playerUpdateEvent.getState().ordinal()]) {
            case 1: {
                if (this.a) {
                    this.a.player().v = this.a.getYaw();
                    final float w = this.a.player().w;
                    final float pitch = this.a.getPitch();
                    this.a.player().w = pitch;
                    final bud player = this.a.player();
                    player.v += (float)((Math.random() - 0.5) * baritone.a.a().randomLooking.value);
                    final bud player2 = this.a.player();
                    player2.w += (float)((Math.random() - 0.5) * baritone.a.a().randomLooking.value);
                    if (pitch == w && !baritone.a.a().freeLook.value) {
                        if (this.a.player().w < -20.0f) {
                            final bud player3 = this.a.player();
                            ++player3.w;
                        }
                        else if (this.a.player().w > 10.0f) {
                            final bud player4 = this.a.player();
                            --player4.w;
                        }
                    }
                    this.a = null;
                }
                if (b) {
                    this.a = this.a.player().v;
                    this.a.player().v = this.a.getYaw();
                    return;
                }
                break;
            }
            case 2: {
                if (b) {
                    this.a.player().v = this.a;
                    this.a = null;
                    break;
                }
                break;
            }
        }
    }
    
    @Override
    public final void onPlayerRotationMove(final RotationMoveEvent rotationMoveEvent) {
        if (this.a != null) {
            rotationMoveEvent.setYaw(this.a.getYaw());
            if (!baritone.a.a().antiCheatCompatibility.value && rotationMoveEvent.getType() == RotationMoveEvent$Type.MOTION_UPDATE && !this.a) {
                this.a = null;
            }
        }
    }
}
