// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.mixin;

import java.util.Map;
import me.gopro336.goprohack.GoproHackMod;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.Mixins;
import org.spongepowered.asm.launch.MixinBootstrap;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;

public class MixinLoaderForge implements IFMLLoadingPlugin
{
    private static boolean isObfuscatedEnvironment;
    
    public MixinLoaderForge() {
        MixinBootstrap.init();
        Mixins.addConfiguration("mixins.goprohack.json");
        MixinEnvironment.getDefaultEnvironment().setObfuscationContext("searge");
        GoproHackMod.log.info(MixinEnvironment.getDefaultEnvironment().getObfuscationContext());
    }
    
    public String[] getASMTransformerClass() {
        return new String[0];
    }
    
    public String getModContainerClass() {
        return null;
    }
    
    public String getSetupClass() {
        return null;
    }
    
    public void injectData(final Map<String, Object> data) {
        MixinLoaderForge.isObfuscatedEnvironment = data.get("runtimeDeobfuscationEnabled");
    }
    
    public String getAccessTransformerClass() {
        return null;
    }
    
    static {
        MixinLoaderForge.isObfuscatedEnvironment = false;
    }
}
