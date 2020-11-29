// 
// Decompiled by Procyon v0.5.36
// 

package baritone;

import java.util.Collections;
import java.util.ArrayList;
import java.util.List;
import baritone.api.cache.IRememberedInventory;

public final class s implements IRememberedInventory
{
    public final List<aip> a;
    private int b;
    public int a;
    
    private s() {
        this.a = new ArrayList<aip>();
    }
    
    @Override
    public final List<aip> getContents() {
        return Collections.unmodifiableList((List<? extends aip>)this.a);
    }
    
    @Override
    public final int getSize() {
        return this.a;
    }
}
