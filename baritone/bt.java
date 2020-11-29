// 
// Decompiled by Procyon v0.5.36
// 

package baritone;

import java.awt.Color;
import baritone.api.event.events.RenderEvent;
import baritone.api.event.listener.AbstractGameEventListener;

final class bt implements AbstractGameEventListener
{
    private /* synthetic */ bs a;
    
    bt(final bs a) {
        this.a = a;
    }
    
    @Override
    public final void onRenderPass(final RenderEvent renderEvent) {
        if (!baritone.a.a().renderSelectionCorners.value || this.a.a == null) {
            return;
        }
        final Color color = baritone.a.a().colorSelectionPos1.value;
        final float floatValue = baritone.a.a().selectionOpacity.value;
        final float floatValue2 = baritone.a.a().selectionLineWidth.value;
        final boolean booleanValue = baritone.a.a().renderSelectionIgnoreDepth.value;
        fq.a(color, floatValue, floatValue2, booleanValue);
        fq.a(new bhb((et)this.a.a, this.a.a.a(1, 1, 1)));
        fq.a(booleanValue);
    }
}
