// 
// Decompiled by Procyon v0.5.36
// 

package baritone;

import baritone.api.selection.ISelection;
import java.awt.Color;
import baritone.api.event.events.RenderEvent;
import baritone.api.event.listener.IGameEventListener;
import baritone.api.event.listener.AbstractGameEventListener;

public final class fi implements AbstractGameEventListener, fq
{
    private final fh a;
    
    fi(final a a, final fh a2) {
        this.a = a2;
        a.getGameEventHandler().registerEventListener(this);
    }
    
    @Override
    public final void onRenderPass(final RenderEvent renderEvent) {
        final ISelection[] selections = this.a.getSelections();
        final float floatValue = fi.a.selectionOpacity.value;
        final boolean booleanValue = fi.a.renderSelectionIgnoreDepth.value;
        final float floatValue2 = fi.a.selectionLineWidth.value;
        if (fi.a.renderSelection.value) {
            fq.a(fi.a.colorSelection.value, floatValue, floatValue2, booleanValue);
            ISelection[] array;
            for (int length = (array = selections).length, i = 0; i < length; ++i) {
                fq.a(array[i].aabb(), 0.005);
            }
            if (fi.a.renderSelectionCorners.value) {
                fq.a(fi.a.colorSelectionPos1.value, floatValue);
                ISelection[] array2;
                for (int length2 = (array2 = selections).length, j = 0; j < length2; ++j) {
                    final ISelection selection = array2[j];
                    fq.a(new bhb((et)selection.pos1(), selection.pos1().a(1, 1, 1)));
                }
                fq.a(fi.a.colorSelectionPos2.value, floatValue);
                ISelection[] array3;
                for (int length3 = (array3 = selections).length, k = 0; k < length3; ++k) {
                    final ISelection selection2 = array3[k];
                    fq.a(new bhb((et)selection2.pos2(), selection2.pos2().a(1, 1, 1)));
                }
            }
            fq.a(booleanValue);
        }
    }
}
