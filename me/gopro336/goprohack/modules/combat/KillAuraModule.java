//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.modules.combat;

import net.minecraft.entity.passive.AbstractChestHorse;
import me.gopro336.goprohack.util.entity.EntityUtil;
import me.gopro336.goprohack.managers.FriendManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.entity.projectile.EntityShulkerBullet;
import net.minecraft.entity.EntityLivingBase;
import javax.annotation.Nullable;
import me.gopro336.goprohack.managers.ModuleManager;
import java.util.function.Predicate;
import net.minecraft.util.EnumHand;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketUseEntity;
import me.gopro336.goprohack.managers.TickRateManager;
import me.gopro336.goprohack.util.RotationSpoof;
import me.gopro336.goprohack.util.MathUtil;
import java.util.Comparator;
import me.gopro336.goprohack.util.entity.ItemUtil;
import net.minecraft.init.Items;
import net.minecraft.item.ItemSword;
import me.zero.alpine.fork.listener.EventHandler;
import me.gopro336.goprohack.events.client.EventClientTick;
import me.zero.alpine.fork.listener.Listener;
import me.gopro336.goprohack.util.Timer;
import net.minecraft.entity.Entity;
import me.gopro336.goprohack.modules.Value;
import me.gopro336.goprohack.modules.Module;

public class KillAuraModule extends Module
{
    public final Value<Modes> Mode;
    public final Value<Float> Distance;
    public final Value<Boolean> HitDelay;
    public final Value<Boolean> TPSSync;
    public final Value<Boolean> Players;
    public final Value<Boolean> Monsters;
    public final Value<Boolean> Neutrals;
    public final Value<Boolean> Animals;
    public final Value<Boolean> Tamed;
    public final Value<Boolean> Projectiles;
    public final Value<Boolean> SwordOnly;
    public final Value<Boolean> PauseIfCrystal;
    public final Value<Boolean> PauseIfEating;
    public final Value<Boolean> AutoSwitch;
    public final Value<Integer> Ticks;
    public final Value<Integer> Iterations;
    public final Value<Boolean> Only32k;
    private Entity CurrentTarget;
    private AutoCrystalModule AutoCrystal;
    private AimbotModule Aimbot;
    private Timer AimbotResetTimer;
    private int RemainingTicks;
    @EventHandler
    private Listener<EventClientTick> OnTick;
    
    public KillAuraModule() {
        super("Aura", new String[] { "Aura" }, "Automatically faces and hits entities around you", "NONE", 16711680, ModuleType.COMBAT);
        this.Mode = new Value<Modes>("Mode", new String[] { "Mode" }, "The KillAura Mode to use", Modes.Closest);
        this.Distance = new Value<Float>("Distance", new String[] { "Range" }, "Range for attacking a target", 5.0f, 0.0f, 10.0f, 1.0f);
        this.HitDelay = new Value<Boolean>("Hit Delay", new String[] { "Hit Delay" }, "Use vanilla hit delay", true);
        this.TPSSync = new Value<Boolean>("TPSSync", new String[] { "TPSSync" }, "Use TPS Sync for hit delay", false);
        this.Players = new Value<Boolean>("Players", new String[] { "Players" }, "Should we target Players", true);
        this.Monsters = new Value<Boolean>("Monsters", new String[] { "Players" }, "Should we target Monsters", true);
        this.Neutrals = new Value<Boolean>("Neutrals", new String[] { "Players" }, "Should we target Neutrals", false);
        this.Animals = new Value<Boolean>("Animals", new String[] { "Players" }, "Should we target Animals", false);
        this.Tamed = new Value<Boolean>("Tamed", new String[] { "Players" }, "Should we target Tamed", false);
        this.Projectiles = new Value<Boolean>("Projectile", new String[] { "Projectile" }, "Should we target Projectiles (shulker bullets, etc)", false);
        this.SwordOnly = new Value<Boolean>("SwordOnly", new String[] { "SwordOnly" }, "Only activate on sword", false);
        this.PauseIfCrystal = new Value<Boolean>("PauseIfCrystal", new String[] { "PauseIfCrystal" }, "Pauses if a crystal is in your hand", false);
        this.PauseIfEating = new Value<Boolean>("PauseIfEating", new String[] { "PauseIfEating" }, "Pauses if your eating", false);
        this.AutoSwitch = new Value<Boolean>("AutoSwitch", new String[] { "AutoSwitch" }, "Automatically switches to a sword in your hotbar", false);
        this.Ticks = new Value<Integer>("Ticks", new String[] { "Ticks" }, "If you don't have HitDelay on, how fast the kill aura should be hitting", 10, 0, 40, 1);
        this.Iterations = new Value<Integer>("Iterations", new String[] { "" }, "Allows you to do more iteratons per tick", 1, 1, 10, 1);
        this.Only32k = new Value<Boolean>("32kOnly", new String[] { "" }, "Only killauras when 32k sword is in your hand", false);
        this.AimbotResetTimer = new Timer();
        this.RemainingTicks = 0;
        int l_Slot;
        int l_I;
        Entity l_TargetToHit;
        float[] l_Rotation;
        float l_Ticks;
        boolean b;
        boolean l_IsAttackReady;
        int l_I2;
        this.OnTick = new Listener<EventClientTick>(p_Event -> {
            if (!(this.mc.player.getHeldItemMainhand().getItem() instanceof ItemSword)) {
                if (this.mc.player.getHeldItemMainhand().getItem() == Items.END_CRYSTAL && this.PauseIfCrystal.getValue()) {
                    return;
                }
                else if (this.mc.player.getHeldItemMainhand().getItem() == Items.GOLDEN_APPLE && this.PauseIfEating.getValue()) {
                    return;
                }
                else {
                    l_Slot = -1;
                    if (this.AutoSwitch.getValue()) {
                        l_I = 0;
                        while (l_I < 9) {
                            if (this.mc.player.inventory.getStackInSlot(l_I).getItem() instanceof ItemSword) {
                                l_Slot = l_I;
                                this.mc.player.inventory.currentItem = l_Slot;
                                this.mc.playerController.updateController();
                                break;
                            }
                            else {
                                ++l_I;
                            }
                        }
                    }
                    if (this.SwordOnly.getValue() && l_Slot == -1) {
                        return;
                    }
                }
            }
            if (!this.Only32k.getValue() || ItemUtil.Is32k(this.mc.player.getHeldItemMainhand())) {
                if (this.AimbotResetTimer.passed(5000.0)) {
                    this.AimbotResetTimer.reset();
                    this.Aimbot.m_RotationSpoof = null;
                }
                if (this.RemainingTicks > 0) {
                    --this.RemainingTicks;
                }
                l_TargetToHit = this.CurrentTarget;
                switch (this.Mode.getValue()) {
                    case Closest: {
                        l_TargetToHit = (Entity)this.mc.world.loadedEntityList.stream().filter(p_Entity -> this.IsValidTarget(p_Entity, null)).min(Comparator.comparing(p_Entity -> this.mc.player.getDistance(p_Entity))).orElse(null);
                        break;
                    }
                    case Priority: {
                        if (l_TargetToHit == null) {
                            l_TargetToHit = (Entity)this.mc.world.loadedEntityList.stream().filter(p_Entity -> this.IsValidTarget(p_Entity, null)).min(Comparator.comparing(p_Entity -> this.mc.player.getDistance(p_Entity))).orElse(null);
                            break;
                        }
                        else {
                            break;
                        }
                        break;
                    }
                    case Switch: {
                        l_TargetToHit = (Entity)this.mc.world.loadedEntityList.stream().filter(p_Entity -> this.IsValidTarget(p_Entity, null)).min(Comparator.comparing(p_Entity -> this.mc.player.getDistance(p_Entity))).orElse(null);
                        if (l_TargetToHit == null) {
                            l_TargetToHit = this.CurrentTarget;
                            break;
                        }
                        else {
                            break;
                        }
                        break;
                    }
                }
                if (l_TargetToHit == null || l_TargetToHit.getDistance((Entity)this.mc.player) > this.Distance.getValue()) {
                    this.CurrentTarget = null;
                }
                else {
                    l_Rotation = MathUtil.calcAngle(this.mc.player.getPositionEyes(this.mc.getRenderPartialTicks()), l_TargetToHit.getPositionEyes(this.mc.getRenderPartialTicks()));
                    this.Aimbot.m_RotationSpoof = new RotationSpoof(l_Rotation[0], l_Rotation[1]);
                    l_Ticks = 20.0f - TickRateManager.Get().getTickRate();
                    if (this.HitDelay.getValue()) {
                        b = (this.mc.player.getCooledAttackStrength(((boolean)this.TPSSync.getValue()) ? (-l_Ticks) : 0.0f) >= 1.0f);
                    }
                    else {
                        b = true;
                    }
                    l_IsAttackReady = b;
                    if (!(!l_IsAttackReady)) {
                        if (this.HitDelay.getValue() || this.RemainingTicks <= 0) {
                            this.RemainingTicks = this.Ticks.getValue();
                            for (l_I2 = 0; l_I2 < this.Iterations.getValue(); ++l_I2) {
                                this.mc.player.connection.sendPacket((Packet)new CPacketUseEntity(l_TargetToHit));
                                this.mc.player.swingArm(EnumHand.MAIN_HAND);
                                this.mc.player.resetCooldown();
                            }
                        }
                    }
                }
            }
        }, (Predicate<EventClientTick>[])new Predicate[0]);
    }
    
    @Override
    public void onEnable() {
        super.onEnable();
        this.RemainingTicks = 0;
        if (this.AutoCrystal == null) {
            this.AutoCrystal = (AutoCrystalModule)ModuleManager.Get().GetMod(AutoCrystalModule.class);
        }
        if (this.Aimbot == null) {
            this.Aimbot = (AimbotModule)ModuleManager.Get().GetMod(AimbotModule.class);
            if (!this.Aimbot.isEnabled()) {
                this.Aimbot.toggle();
            }
        }
    }
    
    @Override
    public void onDisable() {
        super.onDisable();
        if (this.Aimbot != null) {
            this.Aimbot.m_RotationSpoof = null;
        }
    }
    
    @Override
    public String getMetaData() {
        return this.Mode.getValue().toString();
    }
    
    @Override
    public void toggleNoSave() {
    }
    
    private boolean IsValidTarget(final Entity p_Entity, @Nullable final Entity p_ToIgnore) {
        if (!(p_Entity instanceof EntityLivingBase)) {
            final boolean l_IsProjectile = p_Entity instanceof EntityShulkerBullet || p_Entity instanceof EntityFireball;
            if (!l_IsProjectile) {
                return false;
            }
            if (l_IsProjectile && !this.Projectiles.getValue()) {
                return false;
            }
        }
        if (p_ToIgnore != null && p_Entity == p_ToIgnore) {
            return false;
        }
        if (p_Entity instanceof EntityPlayer) {
            if (p_Entity == this.mc.player) {
                return false;
            }
            if (!this.Players.getValue()) {
                return false;
            }
            if (FriendManager.Get().IsFriend(p_Entity)) {
                return false;
            }
        }
        if (EntityUtil.isHostileMob(p_Entity) && !this.Monsters.getValue()) {
            return false;
        }
        if (EntityUtil.isPassive(p_Entity)) {
            if (p_Entity instanceof AbstractChestHorse) {
                final AbstractChestHorse l_Horse = (AbstractChestHorse)p_Entity;
                if (l_Horse.isTame() && !this.Tamed.getValue()) {
                    return false;
                }
            }
            if (!this.Animals.getValue()) {
                return false;
            }
        }
        if (EntityUtil.isHostileMob(p_Entity) && !this.Monsters.getValue()) {
            return false;
        }
        if (EntityUtil.isNeutralMob(p_Entity) && !this.Neutrals.getValue()) {
            return false;
        }
        boolean l_HealthCheck = true;
        if (p_Entity instanceof EntityLivingBase) {
            final EntityLivingBase l_Base = (EntityLivingBase)p_Entity;
            l_HealthCheck = (!l_Base.isDead && l_Base.getHealth() > 0.0f);
        }
        return l_HealthCheck && p_Entity.getDistance(p_Entity) <= this.Distance.getValue();
    }
    
    public enum Modes
    {
        Closest, 
        Priority, 
        Switch;
    }
}
