// 
// Decompiled by Procyon v0.5.36
// 

package baritone;

import baritone.api.BaritoneAPI;
import baritone.api.event.events.TickEvent$Type;
import baritone.api.event.events.TickEvent;
import java.util.HashMap;
import baritone.api.utils.input.Input;
import java.util.Map;
import baritone.api.utils.IInputOverrideHandler;

public final class fr extends b implements IInputOverrideHandler
{
    private final Map<Input, Boolean> a;
    public final fl a;
    private final fm a;
    
    public fr(final a a) {
        super(a);
        this.a = new HashMap<Input, Boolean>();
        this.a = new fl(a.getPlayerContext());
        this.a = new fm(a.getPlayerContext());
    }
    
    @Override
    public final boolean isInputForcedDown(final Input key) {
        return key != null && this.a.getOrDefault(key, Boolean.FALSE);
    }
    
    @Override
    public final void setInputForceState(final Input input, final boolean b) {
        this.a.put(input, b);
    }
    
    @Override
    public final void clearAllKeys() {
        this.a.clear();
    }
    
    @Override
    public final void onTick(final TickEvent tickEvent) {
        if (tickEvent.getType() == TickEvent$Type.OUT) {
            return;
        }
        if (this.isInputForcedDown(Input.CLICK_LEFT)) {
            this.setInputForceState(Input.CLICK_RIGHT, false);
        }
        final fl a = this.a;
        final boolean inputForcedDown = this.isInputForcedDown(Input.CLICK_LEFT);
        final fl fl = a;
        final bhc objectMouseOver;
        final boolean b = (objectMouseOver = a.a.objectMouseOver()) != null && objectMouseOver.a == bhc$a.b;
        if (inputForcedDown && b) {
            if (!fl.a) {
                fl.a.playerController().syncHeldItem();
                fl.a.playerController().clickBlock(objectMouseOver.a(), objectMouseOver.b);
                fl.a.player().a(ub.a);
            }
            if (fl.a.playerController().onPlayerDamageBlock(objectMouseOver.a(), objectMouseOver.b)) {
                fl.a.player().a(ub.a);
            }
            fl.a.playerController().setHittingBlock(false);
            fl.a = true;
        }
        else if (fl.a) {
            fl.a();
            fl.a = false;
        }
        final fm a2 = this.a;
        final boolean inputForcedDown2 = this.isInputForcedDown(Input.CLICK_RIGHT);
        final fm fm = a2;
        if (a2.a > 0) {
            final fm fm2 = fm;
            --fm2.a;
        }
        else {
            final bhc objectMouseOver2 = fm.a.objectMouseOver();
            if (inputForcedDown2 && !fm.a.player().L() && objectMouseOver2 != null && objectMouseOver2.a() != null && objectMouseOver2.a == bhc$a.b) {
                fm.a = baritone.a.a().rightClickSpeed.value;
                ub[] values;
                for (int length = (values = ub.values()).length, i = 0; i < length; ++i) {
                    final ub ub = values[i];
                    if (fm.a.playerController().processRightClickBlock(fm.a.player(), fm.a.world(), objectMouseOver2.a(), objectMouseOver2.b, objectMouseOver2.c, ub) == ud.a) {
                        fm.a.player().a(ub);
                        break;
                    }
                    if (!fm.a.player().b(ub).b() && fm.a.playerController().processRightClick(fm.a.player(), fm.a.world(), ub) == ud.a) {
                        break;
                    }
                }
            }
        }
        final Input[] array = { Input.MOVE_FORWARD, Input.MOVE_BACK, Input.MOVE_LEFT, Input.MOVE_RIGHT, Input.SNEAK };
        while (true) {
            for (int j = 0; j < 5; ++j) {
                if (this.isInputForcedDown(array[j])) {
                    final boolean b2 = true;
                    if (b2) {
                        if (this.a.player().e.getClass() != fy.class) {
                            this.a.player().e = new fy(this);
                        }
                    }
                    else if (this.a.player().e.getClass() == fy.class) {
                        this.a.player().e = (bub)new buc(bib.z().t);
                    }
                    return;
                }
            }
            final boolean b2 = this.a.a.isPathing() || this.a != BaritoneAPI.getProvider().getPrimaryBaritone();
            continue;
        }
    }
}
