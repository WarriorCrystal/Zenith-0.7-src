//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.modules.render;

import net.minecraft.network.play.server.SPacketEntityEffect;
import java.util.function.Predicate;
import net.minecraft.potion.PotionEffect;
import net.minecraft.init.MobEffects;
import me.gopro336.goprohack.events.network.EventNetworkPacketEvent;
import me.zero.alpine.fork.listener.EventHandler;
import me.gopro336.goprohack.events.player.EventPlayerUpdate;
import me.zero.alpine.fork.listener.Listener;
import me.gopro336.goprohack.util.Timer;
import net.minecraft.world.World;
import me.gopro336.goprohack.modules.Value;
import me.gopro336.goprohack.modules.Module;

public final class BrightnessModule extends Module
{
    public final Value<Mode> mode;
    public final Value<Boolean> effects;
    private float lastGamma;
    private World world;
    private Timer timer;
    @EventHandler
    private Listener<EventPlayerUpdate> OnPlayerUpdate;
    @EventHandler
    private Listener<EventNetworkPacketEvent> PacketEvent;
    
    public BrightnessModule() {
        super("Brightness", new String[] { "FullBright", "Bright" }, "Makes the world brighter", "NONE", 5131167, ModuleType.RENDER);
        this.mode = new Value<Mode>("Mode", new String[] { "Mode", "M" }, "The brightness mode to use.", Mode.Gamma);
        this.effects = new Value<Boolean>("Effects", new String[] { "Eff" }, "Blocks blindness & nausea effects if enabled.", true);
        this.timer = new Timer();
        int i;
        this.OnPlayerUpdate = new Listener<EventPlayerUpdate>(p_Event -> {
            switch (this.mode.getValue()) {
                case Gamma: {
                    this.mc.gameSettings.gammaSetting = 1000.0f;
                    this.mc.player.removePotionEffect(MobEffects.NIGHT_VISION);
                    break;
                }
                case Potion: {
                    this.mc.gameSettings.gammaSetting = 1.0f;
                    this.mc.player.addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION, 5210));
                    break;
                }
                case Table: {
                    if (this.world != this.mc.world) {
                        if (this.mc.world != null) {
                            for (i = 0; i <= 15; ++i) {
                                this.mc.world.provider.getLightBrightnessTable()[i] = 1.0f;
                            }
                        }
                        this.world = (World)this.mc.world;
                        break;
                    }
                    else {
                        break;
                    }
                    break;
                }
            }
            return;
        }, (Predicate<EventPlayerUpdate>[])new Predicate[0]);
        SPacketEntityEffect packet;
        this.PacketEvent = new Listener<EventNetworkPacketEvent>(p_Event -> {
            if (p_Event.GetPacket() instanceof SPacketEntityEffect && this.effects.getValue()) {
                packet = (SPacketEntityEffect)p_Event.GetPacket();
                if (this.mc.player != null && packet.getEntityId() == this.mc.player.getEntityId() && (packet.getEffectId() == 9 || packet.getEffectId() == 15)) {
                    p_Event.cancel();
                }
            }
        }, (Predicate<EventNetworkPacketEvent>[])new Predicate[0]);
    }
    
    @Override
    public String getMetaData() {
        return String.valueOf(this.mode.getValue());
    }
    
    @Override
    public void onEnable() {
        super.onEnable();
        if (this.mode.getValue() == Mode.Gamma) {
            this.lastGamma = this.mc.gameSettings.gammaSetting;
        }
    }
    
    @Override
    public void onDisable() {
        super.onDisable();
        if (this.mode.getValue() == Mode.Gamma) {
            this.mc.gameSettings.gammaSetting = this.lastGamma;
        }
        if (this.mode.getValue() == Mode.Potion && this.mc.player != null) {
            this.mc.player.removePotionEffect(MobEffects.NIGHT_VISION);
        }
        if (this.mode.getValue() == Mode.Table && this.mc.world != null) {
            final float f = 0.0f;
            for (int i = 0; i <= 15; ++i) {
                final float f2 = 1.0f - i / 15.0f;
                this.mc.world.provider.getLightBrightnessTable()[i] = (1.0f - f2) / (f2 * 3.0f + 1.0f) * 1.0f + 0.0f;
            }
        }
    }
    
    private enum Mode
    {
        Gamma, 
        Potion, 
        Table;
    }
}
