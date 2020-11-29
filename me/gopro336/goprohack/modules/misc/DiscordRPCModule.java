//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.modules.misc;

import me.gopro336.goprohack.util.entity.PlayerUtil;
import me.gopro336.goprohack.main.Wrapper;
import me.gopro336.goprohack.managers.DiscordManager;
import me.gopro336.goprohack.managers.ModuleManager;
import me.gopro336.goprohack.modules.combat.AutoCrystalOptimise;
import me.gopro336.goprohack.modules.Value;
import me.gopro336.goprohack.modules.Module;

public class DiscordRPCModule extends Module
{
    public final Value<Boolean> Username;
    public final Value<Boolean> ServerIP;
    public final Value<String> DetailsAddon;
    public final Value<Boolean> Gopro336;
    public final Value<Boolean> Speed;
    public final Value<Boolean> Movement;
    public final Value<Boolean> Crystalling;
    public final Value<Boolean> Health;
    public final Value<Boolean> GitHub;
    private AutoCrystalOptimise _autoCrystal;
    
    public DiscordRPCModule() {
        super("DiscordRPC", new String[] { "RPC" }, "Shows discord rich presence for this mod", "NONE", -1, ModuleType.MISC);
        this.Username = new Value<Boolean>("Username", new String[] { "U" }, "Displays your username in the rich presence", true);
        this.ServerIP = new Value<Boolean>("ServerIP", new String[] { "S" }, "Displays your current playing server in the rich presence", true);
        this.DetailsAddon = new Value<String>("DetailsAddon", new String[] { "D" }, "Displays a custom message after the previous", "Gaming");
        this.Gopro336 = new Value<Boolean>("Gopro336", new String[] { "U" }, "Displays a message about gopro336", false);
        this.Speed = new Value<Boolean>("Speed", new String[] { "U" }, "Displays your speed in the rich presence", true);
        this.Movement = new Value<Boolean>("Movement", new String[] { "U" }, "Displays if you're flying/onground in the rich presence", false);
        this.Crystalling = new Value<Boolean>("Crystalling", new String[] { "U" }, "Displays the current target from autocrystal", true);
        this.Health = new Value<Boolean>("Health", new String[] { "U" }, "Displays your Health in the rich presence", false);
        this.GitHub = new Value<Boolean>("GitHub", new String[] { "U" }, "Displays the github link", false);
        this._autoCrystal = null;
        this.setEnabled(true);
    }
    
    @Override
    public void init() {
        this._autoCrystal = (AutoCrystalOptimise)ModuleManager.Get().GetMod(AutoCrystalOptimise.class);
        if (this.isEnabled()) {
            DiscordManager.Get().enable();
        }
    }
    
    @Override
    public void onEnable() {
        super.onEnable();
        DiscordManager.Get().enable();
    }
    
    @Override
    public void onDisable() {
        super.onDisable();
        try {
            DiscordManager.Get().disable();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    public String generateDetails() {
        String result = this.DetailsAddon.getValue();
        if (result == null) {
            result = "";
        }
        if (this.ServerIP.getValue()) {
            result = ((Wrapper.GetMC().getCurrentServerData() != null) ? Wrapper.GetMC().getCurrentServerData().serverIP : "none") + " | " + result;
        }
        if (this.Username.getValue()) {
            result = Wrapper.GetMC().session.getUsername() + " | " + result;
        }
        return result;
    }
    
    public String generateState() {
        if (this.mc.player == null) {
            return "Loading...";
        }
        if (this.Gopro336.getValue()) {
            return "Thank you Gopro336 for GoproHack!";
        }
        if (this.GitHub.getValue()) {
            return "The GoproHack source is privite!";
        }
        String result = "";
        if (this.Crystalling.getValue() && this._autoCrystal.isEnabled() && this._autoCrystal.getTarget() != null) {
            return "Crystalling " + this._autoCrystal.getTarget() + " with GoproHack's autocrystal!";
        }
        if (this.Movement.getValue()) {
            result = (this.mc.player.onGround ? "On the ground" : "Airborne");
            if (this.mc.player.isElytraFlying()) {
                result = "Zooming";
            }
        }
        if (this.Speed.getValue()) {
            final float speed = PlayerUtil.getSpeedInKM();
            if (result.isEmpty()) {
                result = "Moving " + speed + " km/h";
            }
            else if (result.equals("Zooming")) {
                result = result + " at " + speed + " km/h";
            }
            else {
                result = result + " going " + speed + " km/h";
            }
        }
        if (this.Health.getValue()) {
            if (!result.isEmpty()) {
                result += " ";
            }
            result = result + Math.floor(this.mc.player.getHealth() + this.mc.player.getAbsorptionAmount()) + " health";
        }
        return result;
    }
}
