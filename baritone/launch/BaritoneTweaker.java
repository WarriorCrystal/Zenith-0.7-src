// 
// Decompiled by Procyon v0.5.36
// 

package baritone.launch;

import org.spongepowered.asm.mixin.Mixins;
import org.spongepowered.asm.mixin.MixinEnvironment;
import net.minecraft.launchwrapper.Launch;
import java.util.List;
import org.spongepowered.asm.launch.MixinBootstrap;
import net.minecraft.launchwrapper.LaunchClassLoader;
import io.github.impactdevelopment.simpletweaker.SimpleTweaker;

public class BaritoneTweaker extends SimpleTweaker
{
    public void injectIntoClassLoader(final LaunchClassLoader launchClassLoader) {
        super.injectIntoClassLoader(launchClassLoader);
        MixinBootstrap.init();
        final List list = Launch.blackboard.get("TweakClasses");
        String obfuscationContext = "notch";
        if (list.stream().anyMatch(s -> s.contains("net.minecraftforge.fml.common.launcher"))) {
            obfuscationContext = "searge";
        }
        MixinEnvironment.getDefaultEnvironment().setSide(MixinEnvironment.Side.CLIENT);
        MixinEnvironment.getDefaultEnvironment().setObfuscationContext(obfuscationContext);
        Mixins.addConfiguration("mixins.baritone.json");
    }
}
