// 
// Decompiled by Procyon v0.5.36
// 

package com.tterrag.blur;

import java.util.Map;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;

@IFMLLoadingPlugin.SortingIndex(Integer.MAX_VALUE)
public class BlurPlugin implements IFMLLoadingPlugin
{
    public String[] getASMTransformerClass() {
        return new String[] { "com.tterrag.blur.BlurTransformer" };
    }
    
    public String getModContainerClass() {
        return null;
    }
    
    public String getSetupClass() {
        return null;
    }
    
    public void injectData(final Map<String, Object> data) {
    }
    
    public String getAccessTransformerClass() {
        return null;
    }
}
