//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.modules.combat;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.item.Item;
import net.minecraft.util.math.RayTraceResult;
import java.util.List;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.Vec3d;
import me.gopro336.goprohack.util.BlockInteractionHelper;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.entity.passive.AbstractChestHorse;
import me.gopro336.goprohack.managers.FriendManager;
import net.minecraft.util.EnumHand;
import net.minecraft.item.ItemTool;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemStack;
import net.minecraft.init.MobEffects;
import java.util.Random;
import me.gopro336.goprohack.util.RotationSpoof;
import net.minecraft.entity.player.EntityPlayer;
import me.gopro336.goprohack.util.entity.EntityUtil;
import javax.annotation.Nullable;
import net.minecraft.world.World;
import me.gopro336.goprohack.util.CrystalUtils;
import me.gopro336.goprohack.managers.ModuleManager;
import java.util.Iterator;
import me.gopro336.goprohack.util.render.RenderUtil;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.math.AxisAlignedBB;
import java.util.Collection;
import net.minecraft.entity.Entity;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundCategory;
import net.minecraft.network.play.server.SPacketSoundEffect;
import me.gopro336.goprohack.events.MinecraftEvent;
import java.util.function.Predicate;
import java.util.Comparator;
import net.minecraft.entity.item.EntityEnderCrystal;
import me.gopro336.goprohack.util.entity.PlayerUtil;
import net.minecraft.client.renderer.culling.Frustum;
import me.gopro336.goprohack.events.render.RenderEvent;
import me.gopro336.goprohack.events.network.EventNetworkPacketEvent;
import me.gopro336.goprohack.events.player.EventPlayerMotionUpdate;
import me.zero.alpine.fork.listener.EventHandler;
import me.gopro336.goprohack.events.client.EventClientTick;
import me.zero.alpine.fork.listener.Listener;
import me.gopro336.goprohack.modules.misc.AutoMendArmorModule;
import net.minecraft.util.math.BlockPos;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.network.play.client.CPacketPlayer;
import java.util.ArrayList;
import me.gopro336.goprohack.modules.Value;
import me.gopro336.goprohack.modules.Module;

public class AutoCrystalModule extends Module
{
    public static final Value<Modes> Mode;
    public static final Value<PlaceModes> PlaceMode;
    public static final Value<DestroyModes> DestroyMode;
    public static final Value<Integer> Ticks;
    public static final Value<Float> DestroyDistance;
    public static final Value<Float> PlaceDistance;
    public static final Value<Float> WallsRange;
    public static final Value<Float> MinDMG;
    public static final Value<Float> MaxSelfDMG;
    public static final Value<Float> FacePlace;
    public static final Value<Boolean> NoSuicide;
    public static final Value<Boolean> PauseWhileEating;
    public static final Value<Boolean> AntiWeakness;
    public static final Value<Boolean> GhostHand;
    public static final Value<Boolean> GhostHandWeakness;
    public static final Value<Boolean> ChatMsgs;
    public static final Value<Boolean> Players;
    public static final Value<Boolean> Hostile;
    public static final Value<Boolean> Animals;
    public static final Value<Boolean> Tamed;
    public static final Value<Boolean> ResetRotationNoTarget;
    public static final Value<Boolean> Polyplace;
    public static final Value<Boolean> OnlyPlaceWithCrystal;
    public static final Value<Boolean> PlaceObsidianIfNoValidSpots;
    public static final Value<Boolean> MinHealthPause;
    public static final Value<Float> RequiredHealth;
    public static final Value<Boolean> AutoPolyplace;
    public static final Value<Float> HealthBelowAutoPolyplace;
    public static final Value<Boolean> PauseIfHittingBlock;
    public static final Value<Boolean> Render;
    public static final Value<Integer> Red;
    public static final Value<Integer> Green;
    public static final Value<Integer> Blue;
    public static final Value<Integer> Alpha;
    private int m_WaitTicks;
    private ArrayList<CPacketPlayer.PositionRotation> Packets;
    private int m_SpoofTimerResetTicks;
    private AimbotModule Aimbot;
    private ICamera camera;
    private EntityLivingBase m_Target;
    private ArrayList<BlockPos> PlacedCrystals;
    private SurroundModule Surround;
    private AutoTrapFeet AutoTrapFeet;
    private AutoMendArmorModule AutoMend;
    private SelfTrapModule SelfTrap;
    private HoleFillerModule HoleFiller;
    private AutoCityModule AutoCity;
    @EventHandler
    private Listener<EventClientTick> OnTick;
    @EventHandler
    private Listener<EventPlayerMotionUpdate> OnPlayerMotionUpdate;
    @EventHandler
    private Listener<EventNetworkPacketEvent> PacketEvent;
    @EventHandler
    private Listener<RenderEvent> OnRenderEvent;
    
    public AutoCrystalModule() {
        super("AutoCrystal", new String[] { "AC" }, "Automatically places and destroys crystals around targets, if they meet the requirements", "NONE", 2411227, ModuleType.COMBAT);
        this.m_WaitTicks = 0;
        this.Packets = new ArrayList<CPacketPlayer.PositionRotation>();
        this.m_SpoofTimerResetTicks = 0;
        this.camera = (ICamera)new Frustum();
        this.m_Target = null;
        this.PlacedCrystals = new ArrayList<BlockPos>();
        this.Surround = null;
        this.AutoTrapFeet = null;
        this.AutoMend = null;
        this.SelfTrap = null;
        this.HoleFiller = null;
        this.AutoCity = null;
        EntityEnderCrystal l_Crystal;
        int l_WaitValue;
        this.OnTick = new Listener<EventClientTick>(p_Event -> {
            if (AutoCrystalModule.Mode.getValue() != Modes.ClientTick) {
                return;
            }
            else if (AutoCrystalModule.PauseWhileEating.getValue() && PlayerUtil.IsEating()) {
                this.m_WaitTicks = 0;
                return;
            }
            else if (this.NeedPause()) {
                this.m_WaitTicks = 0;
                this.Aimbot.m_RotationSpoof = null;
                return;
            }
            else {
                l_Crystal = (EntityEnderCrystal)this.mc.world.loadedEntityList.stream().filter(p_Entity -> this.IsValidCrystal(p_Entity)).map(p_Entity -> (EntityEnderCrystal)p_Entity).min(Comparator.comparing(p_Entity -> this.mc.player.getDistance(p_Entity))).orElse(null);
                l_WaitValue = AutoCrystalModule.Ticks.getValue();
                if (this.m_WaitTicks < l_WaitValue) {
                    ++this.m_WaitTicks;
                    return;
                }
                else {
                    this.m_WaitTicks = 0;
                    if (this.m_SpoofTimerResetTicks > 0) {
                        --this.m_SpoofTimerResetTicks;
                    }
                    if (AutoCrystalModule.ResetRotationNoTarget.getValue()) {
                        if (this.m_Target == null && this.Aimbot.m_RotationSpoof != null) {
                            this.Aimbot.m_RotationSpoof = null;
                        }
                    }
                    else if (this.m_SpoofTimerResetTicks == 0) {
                        this.m_SpoofTimerResetTicks = 200;
                        this.Aimbot.m_RotationSpoof = null;
                    }
                    if (AutoCrystalModule.Polyplace.getValue()) {
                        if (AutoCrystalModule.DestroyMode.getValue() != DestroyModes.None) {
                            this.HandleBreakCrystals(l_Crystal, null);
                        }
                        try {
                            if (AutoCrystalModule.PlaceMode.getValue() != PlaceModes.None) {
                                this.HandlePlaceCrystal(null);
                            }
                        }
                        catch (Exception ex) {}
                    }
                    else {
                        try {
                            if (!this.HandleBreakCrystals(l_Crystal, null)) {
                                this.HandlePlaceCrystal(null);
                            }
                        }
                        catch (Exception ex2) {}
                    }
                    return;
                }
            }
        }, (Predicate<EventClientTick>[])new Predicate[0]);
        EntityEnderCrystal l_Crystal2;
        int l_WaitValue2;
        boolean l_Result;
        BlockPos l_Pos;
        BlockPos l_Pos2;
        this.OnPlayerMotionUpdate = new Listener<EventPlayerMotionUpdate>(p_Event -> {
            if (p_Event.getEra() != MinecraftEvent.Era.PRE) {
                return;
            }
            else if (AutoCrystalModule.Mode.getValue() != Modes.MotionTick) {
                return;
            }
            else if (AutoCrystalModule.PauseWhileEating.getValue() && PlayerUtil.IsEating()) {
                this.m_WaitTicks = 0;
                return;
            }
            else if (this.NeedPause()) {
                this.m_WaitTicks = 0;
                this.Aimbot.m_RotationSpoof = null;
                return;
            }
            else {
                l_Crystal2 = (EntityEnderCrystal)this.mc.world.loadedEntityList.stream().filter(p_Entity -> this.IsValidCrystal(p_Entity)).map(p_Entity -> (EntityEnderCrystal)p_Entity).min(Comparator.comparing(p_Entity -> this.mc.player.getDistance(p_Entity))).orElse(null);
                l_WaitValue2 = AutoCrystalModule.Ticks.getValue();
                if (this.m_WaitTicks < l_WaitValue2) {
                    ++this.m_WaitTicks;
                    return;
                }
                else {
                    if (AutoCrystalModule.Polyplace.getValue()) {
                        l_Result = false;
                        if (AutoCrystalModule.DestroyMode.getValue() != DestroyModes.None) {
                            l_Result = !this.HandleBreakCrystals(l_Crystal2, p_Event);
                        }
                        if (AutoCrystalModule.PlaceMode.getValue() != PlaceModes.None) {
                            try {
                                l_Pos = this.HandlePlaceCrystal(p_Event);
                                if (!l_Result && l_Pos != BlockPos.ORIGIN) {
                                    l_Result = true;
                                }
                            }
                            catch (Exception ex3) {}
                        }
                        if (l_Result) {
                            this.m_WaitTicks = AutoCrystalModule.Ticks.getValue();
                        }
                    }
                    else if (!this.HandleBreakCrystals(l_Crystal2, p_Event)) {
                        try {
                            l_Pos2 = this.HandlePlaceCrystal(p_Event);
                            if (l_Pos2 != BlockPos.ORIGIN) {
                                this.m_WaitTicks = AutoCrystalModule.Ticks.getValue();
                            }
                        }
                        catch (Exception ex4) {}
                    }
                    return;
                }
            }
        }, (Predicate<EventPlayerMotionUpdate>[])new Predicate[0]);
        SPacketSoundEffect l_Packet;
        final Iterator<Entity> iterator;
        Entity l_Entity;
        final SPacketSoundEffect sPacketSoundEffect;
        this.PacketEvent = new Listener<EventNetworkPacketEvent>(p_Event -> {
            if (p_Event.getPacket() instanceof SPacketSoundEffect) {
                l_Packet = (SPacketSoundEffect)p_Event.getPacket();
                if (l_Packet.getCategory() == SoundCategory.BLOCKS && l_Packet.getSound() == SoundEvents.ENTITY_GENERIC_EXPLODE) {
                    new ArrayList<Entity>(this.mc.world.loadedEntityList).iterator();
                    while (iterator.hasNext()) {
                        l_Entity = iterator.next();
                        if (l_Entity instanceof EntityEnderCrystal) {
                            if (l_Entity.getDistance(l_Packet.getX(), l_Packet.getY(), l_Packet.getZ()) <= 6.0) {
                                l_Entity.setDead();
                            }
                            this.PlacedCrystals.removeIf(p_Pos -> p_Pos.getDistance((int)sPacketSoundEffect.getX(), (int)sPacketSoundEffect.getY(), (int)sPacketSoundEffect.getZ()) <= 6.0);
                        }
                    }
                }
            }
            return;
        }, (Predicate<EventNetworkPacketEvent>[])new Predicate[0]);
        ArrayList<BlockPos> l_PlacedCrystalsCopy;
        final Iterator<BlockPos> iterator2;
        BlockPos l_Pos3;
        AxisAlignedBB bb;
        int l_Color;
        this.OnRenderEvent = new Listener<RenderEvent>(p_Event -> {
            if (this.mc.getRenderManager() != null && AutoCrystalModule.Render.getValue()) {
                l_PlacedCrystalsCopy = new ArrayList<BlockPos>(this.PlacedCrystals);
                l_PlacedCrystalsCopy.iterator();
                while (iterator2.hasNext()) {
                    l_Pos3 = iterator2.next();
                    if (l_Pos3 == null) {
                        continue;
                    }
                    else {
                        bb = new AxisAlignedBB(l_Pos3.getX() - this.mc.getRenderManager().viewerPosX, l_Pos3.getY() - this.mc.getRenderManager().viewerPosY, l_Pos3.getZ() - this.mc.getRenderManager().viewerPosZ, l_Pos3.getX() + 1 - this.mc.getRenderManager().viewerPosX, l_Pos3.getY() + 1 - this.mc.getRenderManager().viewerPosY, l_Pos3.getZ() + 1 - this.mc.getRenderManager().viewerPosZ);
                        this.camera.setPosition(this.mc.getRenderViewEntity().posX, this.mc.getRenderViewEntity().posY, this.mc.getRenderViewEntity().posZ);
                        if (this.camera.isBoundingBoxInFrustum(new AxisAlignedBB(bb.minX + this.mc.getRenderManager().viewerPosX, bb.minY + this.mc.getRenderManager().viewerPosY, bb.minZ + this.mc.getRenderManager().viewerPosZ, bb.maxX + this.mc.getRenderManager().viewerPosX, bb.maxY + this.mc.getRenderManager().viewerPosY, bb.maxZ + this.mc.getRenderManager().viewerPosZ))) {
                            GlStateManager.pushMatrix();
                            GlStateManager.enableBlend();
                            GlStateManager.disableDepth();
                            GlStateManager.tryBlendFuncSeparate(770, 771, 0, 1);
                            GlStateManager.disableTexture2D();
                            GlStateManager.depthMask(false);
                            GL11.glEnable(2848);
                            GL11.glHint(3154, 4354);
                            GL11.glLineWidth(1.5f);
                            l_Color = (AutoCrystalModule.Alpha.getValue() << 24 | AutoCrystalModule.Red.getValue() << 16 | AutoCrystalModule.Green.getValue() << 8 | AutoCrystalModule.Blue.getValue());
                            RenderUtil.drawBoundingBox(bb, 1.0f, l_Color);
                            RenderUtil.drawFilledBox(bb, l_Color);
                            GL11.glDisable(2848);
                            GlStateManager.depthMask(true);
                            GlStateManager.enableDepth();
                            GlStateManager.enableTexture2D();
                            GlStateManager.disableBlend();
                            GlStateManager.popMatrix();
                        }
                        else {
                            continue;
                        }
                    }
                }
            }
        }, (Predicate<RenderEvent>[])new Predicate[0]);
    }
    
    public void SendMessage(final String p_Msg) {
        if (AutoCrystalModule.ChatMsgs.getValue()) {
            super.SendMessage(p_Msg);
        }
    }
    
    @Override
    public void onEnable() {
        super.onEnable();
        this.Packets.clear();
        this.Aimbot = (AimbotModule)ModuleManager.Get().GetMod(AimbotModule.class);
        this.Surround = (SurroundModule)ModuleManager.Get().GetMod(SurroundModule.class);
        this.AutoTrapFeet = (AutoTrapFeet)ModuleManager.Get().GetMod(AutoTrapFeet.class);
        this.AutoMend = (AutoMendArmorModule)ModuleManager.Get().GetMod(AutoMendArmorModule.class);
        this.SelfTrap = (SelfTrapModule)ModuleManager.Get().GetMod(SelfTrapModule.class);
        this.HoleFiller = (HoleFillerModule)ModuleManager.Get().GetMod(HoleFillerModule.class);
        this.AutoCity = (AutoCityModule)ModuleManager.Get().GetMod(AutoCityModule.class);
        if (!this.Aimbot.isEnabled()) {
            this.Aimbot.toggle();
        }
        this.Aimbot.m_RotationSpoof = null;
        this.m_WaitTicks = AutoCrystalModule.Ticks.getValue();
        this.PlacedCrystals.clear();
    }
    
    @Override
    public void onDisable() {
        super.onDisable();
        this.Packets.clear();
        this.Aimbot.m_RotationSpoof = null;
        this.m_WaitTicks = AutoCrystalModule.Ticks.getValue();
        this.PlacedCrystals.clear();
    }
    
    @Override
    public void toggleNoSave() {
    }
    
    @Override
    public String getMetaData() {
        String l_Result = (this.m_Target != null) ? this.m_Target.getName() : null;
        if (AutoCrystalModule.AutoPolyplace.getValue() && AutoCrystalModule.Polyplace.getValue() && l_Result != null) {
            l_Result += " Multiplacing";
        }
        return l_Result;
    }
    
    private boolean IsValidCrystal(final Entity p_Entity) {
        if (!(p_Entity instanceof EntityEnderCrystal)) {
            return false;
        }
        if (p_Entity.getDistance((Entity)this.mc.player) > (this.mc.player.canEntityBeSeen(p_Entity) ? AutoCrystalModule.DestroyDistance.getValue() : AutoCrystalModule.WallsRange.getValue())) {
            return false;
        }
        switch (AutoCrystalModule.DestroyMode.getValue()) {
            case Always: {
                return true;
            }
            case OnlyOwn: {
                for (final BlockPos l_Pos : new ArrayList<BlockPos>(this.PlacedCrystals)) {
                    if (l_Pos != null && l_Pos.getDistance((int)p_Entity.posX, (int)p_Entity.posY, (int)p_Entity.posZ) <= 3.0) {
                        return true;
                    }
                }
                break;
            }
            case Smart: {
                final EntityLivingBase l_Target = (this.m_Target != null) ? this.m_Target : this.GetNearTarget(p_Entity);
                if (l_Target == null) {
                    return false;
                }
                final float l_TargetDMG = CrystalUtils.calculateDamage((World)this.mc.world, p_Entity.posX + 0.5, p_Entity.posY + 1.0, p_Entity.posZ + 0.5, (Entity)l_Target, 0);
                final float l_SelfDMG = CrystalUtils.calculateDamage((World)this.mc.world, p_Entity.posX + 0.5, p_Entity.posY + 1.0, p_Entity.posZ + 0.5, (Entity)this.mc.player, 0);
                float l_MinDmg = AutoCrystalModule.MinDMG.getValue();
                if (l_Target.getHealth() + l_Target.getAbsorptionAmount() <= AutoCrystalModule.FacePlace.getValue()) {
                    l_MinDmg = 1.0f;
                }
                if (l_TargetDMG > l_MinDmg && l_SelfDMG < AutoCrystalModule.MaxSelfDMG.getValue()) {
                    return true;
                }
                break;
            }
        }
        return false;
    }
    
    private boolean HandleBreakCrystals(final EntityEnderCrystal p_Crystal, @Nullable final EventPlayerMotionUpdate p_Event) {
        if (p_Crystal != null) {
            final double[] l_Pos = EntityUtil.calculateLookAt(p_Crystal.posX + 0.5, p_Crystal.posY - 0.5, p_Crystal.posZ + 0.5, (EntityPlayer)this.mc.player);
            if (AutoCrystalModule.Mode.getValue() == Modes.ClientTick) {
                this.Aimbot.m_RotationSpoof = new RotationSpoof((float)l_Pos[0], (float)l_Pos[1]);
                final Random rand = new Random(2L);
                final RotationSpoof rotationSpoof = this.Aimbot.m_RotationSpoof;
                rotationSpoof.Yaw += rand.nextFloat() / 100.0f;
                final RotationSpoof rotationSpoof2 = this.Aimbot.m_RotationSpoof;
                rotationSpoof2.Pitch += rand.nextFloat() / 100.0f;
            }
            int l_PrevSlot = -1;
            if (AutoCrystalModule.AntiWeakness.getValue() && this.mc.player.isPotionActive(MobEffects.WEAKNESS) && (this.mc.player.getHeldItemMainhand() == ItemStack.EMPTY || (!(this.mc.player.getHeldItemMainhand().getItem() instanceof ItemSword) && !(this.mc.player.getHeldItemMainhand().getItem() instanceof ItemTool)))) {
                for (int l_I = 0; l_I < 9; ++l_I) {
                    final ItemStack l_Stack = this.mc.player.inventory.getStackInSlot(l_I);
                    if (l_Stack != ItemStack.EMPTY) {
                        if (l_Stack.getItem() instanceof ItemTool || l_Stack.getItem() instanceof ItemSword) {
                            l_PrevSlot = this.mc.player.inventory.currentItem;
                            this.mc.player.inventory.currentItem = l_I;
                            this.mc.playerController.updateController();
                            break;
                        }
                    }
                }
            }
            if (AutoCrystalModule.Mode.getValue() == Modes.MotionTick && p_Event != null) {
                p_Event.cancel();
                this.SpoofRotationsTo((float)l_Pos[0], (float)l_Pos[1]);
            }
            this.mc.playerController.attackEntity((EntityPlayer)this.mc.player, (Entity)p_Crystal);
            this.mc.player.swingArm(EnumHand.MAIN_HAND);
            if (AutoCrystalModule.GhostHandWeakness.getValue() && l_PrevSlot != -1) {
                this.mc.player.inventory.currentItem = l_PrevSlot;
                this.mc.playerController.updateController();
            }
            return true;
        }
        return false;
    }
    
    private boolean IsValidTarget(final Entity p_Entity) {
        if (p_Entity == null) {
            return false;
        }
        if (!(p_Entity instanceof EntityLivingBase)) {
            return false;
        }
        if (FriendManager.Get().IsFriend(p_Entity)) {
            return false;
        }
        if (p_Entity.isDead || ((EntityLivingBase)p_Entity).getHealth() <= 0.0f) {
            return false;
        }
        if (p_Entity.getDistance((Entity)this.mc.player) > 20.0f) {
            return false;
        }
        if (p_Entity instanceof EntityPlayer && AutoCrystalModule.Players.getValue()) {
            return p_Entity != this.mc.player;
        }
        return (AutoCrystalModule.Hostile.getValue() && EntityUtil.isHostileMob(p_Entity)) || (AutoCrystalModule.Animals.getValue() && EntityUtil.isPassive(p_Entity)) || (AutoCrystalModule.Tamed.getValue() && p_Entity instanceof AbstractChestHorse && ((AbstractChestHorse)p_Entity).isTame());
    }
    
    private EntityLivingBase GetNearTarget(final Entity p_DistanceTarget) {
        return (EntityLivingBase)this.mc.world.loadedEntityList.stream().filter(p_Entity -> this.IsValidTarget(p_Entity)).map(p_Entity -> p_Entity).min(Comparator.comparing(p_Entity -> p_DistanceTarget.getDistance(p_Entity))).orElse(null);
    }
    
    private void FindNewTarget() {
        this.m_Target = this.GetNearTarget((Entity)this.mc.player);
    }
    
    private BlockPos HandlePlaceCrystal(@Nullable final EventPlayerMotionUpdate p_Event) throws Exception {
        if (AutoCrystalModule.OnlyPlaceWithCrystal.getValue() && this.mc.player.getHeldItemMainhand().getItem() != Items.END_CRYSTAL && this.mc.player.getHeldItemOffhand().getItem() != Items.END_CRYSTAL) {
            return BlockPos.ORIGIN;
        }
        final List<BlockPos> l_AvailableBlockPositions = CrystalUtils.findCrystalBlocks((EntityPlayer)this.mc.player, AutoCrystalModule.PlaceDistance.getValue());
        switch (AutoCrystalModule.PlaceMode.getValue()) {
            case Nearest: {
                this.FindNewTarget();
                break;
            }
            case Priority: {
                if (this.m_Target == null || this.m_Target.getDistance((Entity)this.mc.player) > AutoCrystalModule.PlaceDistance.getValue() + 2.0f || this.m_Target.isDead || this.m_Target.getHealth() <= 0.0f) {
                    this.FindNewTarget();
                    break;
                }
                break;
            }
            case MostDamage: {
                if (l_AvailableBlockPositions.isEmpty()) {
                    this.FindNewTarget();
                    break;
                }
                EntityLivingBase l_Target = null;
                final float l_MinDmg = AutoCrystalModule.MinDMG.getValue();
                final float l_MaxSelfDmg = AutoCrystalModule.MaxSelfDMG.getValue();
                float l_DMG = 0.0f;
                for (final EntityPlayer l_Player : this.mc.world.playerEntities) {
                    if (!this.IsValidTarget((Entity)l_Player)) {
                        continue;
                    }
                    for (final BlockPos l_Pos : l_AvailableBlockPositions) {
                        if (l_Player.getDistanceSq(l_Pos) >= 169.0) {
                            continue;
                        }
                        final float l_TempDMG = CrystalUtils.calculateDamage((World)this.mc.world, l_Pos.getX() + 0.5, l_Pos.getY() + 1.0, l_Pos.getZ() + 0.5, (Entity)l_Player, 0);
                        if (l_TempDMG < l_MinDmg) {
                            continue;
                        }
                        final float l_SelfTempDMG = CrystalUtils.calculateDamage((World)this.mc.world, l_Pos.getX() + 0.5, l_Pos.getY() + 1.0, l_Pos.getZ() + 0.5, (Entity)this.mc.player, 0);
                        if (l_SelfTempDMG > l_MaxSelfDmg) {
                            continue;
                        }
                        if (AutoCrystalModule.WallsRange.getValue() > 0.0f && !PlayerUtil.CanSeeBlock(l_Pos) && l_Pos.getDistance((int)this.mc.player.posX, (int)this.mc.player.posY, (int)this.mc.player.posZ) > AutoCrystalModule.WallsRange.getValue()) {
                            continue;
                        }
                        if (l_TempDMG <= l_DMG) {
                            continue;
                        }
                        l_DMG = l_TempDMG;
                        l_Target = (EntityLivingBase)l_Player;
                    }
                }
                if (l_Target == null) {
                    l_Target = this.GetNearTarget((Entity)this.mc.player);
                }
                if (this.m_Target != null && l_Target != this.m_Target && l_Target != null) {
                    this.SendMessage(String.format("Found new target %s", l_Target.getName()));
                }
                this.m_Target = l_Target;
                break;
            }
        }
        if (l_AvailableBlockPositions.isEmpty()) {
            if (AutoCrystalModule.PlaceObsidianIfNoValidSpots.getValue() && this.m_Target != null) {
                final int l_Slot = this.AutoTrapFeet.findStackHotbar(Blocks.OBSIDIAN);
                if (l_Slot != -1) {
                    if (this.mc.player.inventory.currentItem != l_Slot) {
                        this.mc.player.inventory.currentItem = l_Slot;
                        this.mc.playerController.updateController();
                        return BlockPos.ORIGIN;
                    }
                    final float l_Range = AutoCrystalModule.PlaceDistance.getValue();
                    float l_TargetDMG = 0.0f;
                    float l_MinDmg2 = AutoCrystalModule.MinDMG.getValue();
                    if (this.m_Target.getHealth() + this.m_Target.getAbsorptionAmount() <= AutoCrystalModule.FacePlace.getValue()) {
                        l_MinDmg2 = 1.0f;
                    }
                    BlockPos l_TargetPos = null;
                    for (final BlockPos l_Pos2 : BlockInteractionHelper.getSphere(PlayerUtil.GetLocalPlayerPosFloored(), AutoCrystalModule.PlaceDistance.getValue(), (int)l_Range, false, true, 0)) {
                        final BlockInteractionHelper.ValidResult l_Result = BlockInteractionHelper.valid(l_Pos2);
                        if (l_Result != BlockInteractionHelper.ValidResult.Ok) {
                            continue;
                        }
                        if (!CrystalUtils.CanPlaceCrystalIfObbyWasAtPos(l_Pos2)) {
                            continue;
                        }
                        final float l_TempDMG = CrystalUtils.calculateDamage((World)this.mc.world, l_Pos2.getX() + 0.5, l_Pos2.getY() + 1.0, l_Pos2.getZ() + 0.5, (Entity)this.m_Target, 0);
                        if (l_TempDMG < l_MinDmg2) {
                            continue;
                        }
                        if (l_TempDMG < l_TargetDMG) {
                            continue;
                        }
                        l_TargetPos = l_Pos2;
                        l_TargetDMG = l_TempDMG;
                    }
                    if (l_TargetPos != null) {
                        BlockInteractionHelper.place(l_TargetPos, AutoCrystalModule.PlaceDistance.getValue(), true, false);
                        this.SendMessage(String.format("Tried to place obsidian at %s would deal %s dmg", l_TargetPos.toString(), l_TargetDMG));
                    }
                }
            }
            return BlockPos.ORIGIN;
        }
        if (this.m_Target == null) {
            return BlockPos.ORIGIN;
        }
        if (AutoCrystalModule.AutoPolyplace.getValue()) {
            if (this.m_Target.getHealth() + this.m_Target.getAbsorptionAmount() <= AutoCrystalModule.HealthBelowAutoPolyplace.getValue()) {
                AutoCrystalModule.Polyplace.setValue(true);
            }
            else {
                AutoCrystalModule.Polyplace.setValue(false);
            }
        }
        float l_MinDmg3 = AutoCrystalModule.MinDMG.getValue();
        float l_MaxSelfDmg2 = AutoCrystalModule.MaxSelfDMG.getValue();
        final float l_FacePlaceHealth = AutoCrystalModule.FacePlace.getValue();
        if (this.m_Target.getHealth() <= l_FacePlaceHealth) {
            l_MinDmg3 = 1.0f;
        }
        if (AutoCrystalModule.NoSuicide.getValue()) {
            while (this.mc.player.getHealth() + this.mc.player.getAbsorptionAmount() < l_MaxSelfDmg2) {
                l_MaxSelfDmg2 /= 2.0f;
            }
        }
        BlockPos l_BestPosition = null;
        float l_DMG2 = 0.0f;
        float l_SelfDMG = 0.0f;
        for (final BlockPos l_Pos : l_AvailableBlockPositions) {
            if (this.m_Target.getDistanceSq(l_Pos) >= 169.0) {
                continue;
            }
            final float l_TempDMG = CrystalUtils.calculateDamage((World)this.mc.world, l_Pos.getX() + 0.5, l_Pos.getY() + 1.0, l_Pos.getZ() + 0.5, (Entity)this.m_Target, 0);
            if (l_TempDMG < l_MinDmg3) {
                continue;
            }
            final float l_SelfTempDMG = CrystalUtils.calculateDamage((World)this.mc.world, l_Pos.getX() + 0.5, l_Pos.getY() + 1.0, l_Pos.getZ() + 0.5, (Entity)this.mc.player, 0);
            if (l_SelfTempDMG > l_MaxSelfDmg2) {
                continue;
            }
            if (AutoCrystalModule.WallsRange.getValue() > 0.0f && !PlayerUtil.CanSeeBlock(l_Pos) && l_Pos.getDistance((int)this.mc.player.posX, (int)this.mc.player.posY, (int)this.mc.player.posZ) > AutoCrystalModule.WallsRange.getValue()) {
                continue;
            }
            if (l_TempDMG <= l_DMG2) {
                continue;
            }
            l_DMG2 = l_TempDMG;
            l_SelfDMG = l_SelfTempDMG;
            l_BestPosition = l_Pos;
        }
        if (l_BestPosition == null) {
            return BlockPos.ORIGIN;
        }
        int l_PrevSlot = -1;
        if (!AutoCrystalModule.GhostHand.getValue()) {
            if (this.SwitchHandToItemIfNeed(Items.END_CRYSTAL)) {
                return BlockPos.ORIGIN;
            }
        }
        else if (this.mc.player.getHeldItemMainhand().getItem() != Items.END_CRYSTAL && this.mc.player.getHeldItemOffhand().getItem() != Items.END_CRYSTAL) {
            for (int l_I = 0; l_I < 9; ++l_I) {
                final ItemStack l_Stack = this.mc.player.inventory.getStackInSlot(l_I);
                if (l_Stack != ItemStack.EMPTY) {
                    if (l_Stack.getItem() == Items.END_CRYSTAL) {
                        l_PrevSlot = this.mc.player.inventory.currentItem;
                        this.mc.player.inventory.currentItem = l_I;
                        this.mc.playerController.updateController();
                    }
                }
            }
        }
        final double[] l_Pos3 = EntityUtil.calculateLookAt(l_BestPosition.getX() + 0.5, l_BestPosition.getY() - 0.5, l_BestPosition.getZ() + 0.5, (EntityPlayer)this.mc.player);
        if (AutoCrystalModule.Mode.getValue() == Modes.ClientTick) {
            this.Aimbot.m_RotationSpoof = new RotationSpoof((float)l_Pos3[0], (float)l_Pos3[1]);
            final Random rand = new Random(2L);
            final RotationSpoof rotationSpoof = this.Aimbot.m_RotationSpoof;
            rotationSpoof.Yaw += rand.nextFloat() / 100.0f;
            final RotationSpoof rotationSpoof2 = this.Aimbot.m_RotationSpoof;
            rotationSpoof2.Pitch += rand.nextFloat() / 100.0f;
        }
        final RayTraceResult l_Result2 = this.mc.world.rayTraceBlocks(new Vec3d(this.mc.player.posX, this.mc.player.posY + this.mc.player.getEyeHeight(), this.mc.player.posZ), new Vec3d(l_BestPosition.getX() + 0.5, l_BestPosition.getY() - 0.5, l_BestPosition.getZ() + 0.5));
        EnumFacing l_Facing;
        if (l_Result2 == null || l_Result2.sideHit == null) {
            l_Facing = EnumFacing.UP;
        }
        else {
            l_Facing = l_Result2.sideHit;
        }
        if (AutoCrystalModule.Mode.getValue() == Modes.MotionTick && p_Event != null) {
            p_Event.cancel();
            this.SpoofRotationsTo((float)l_Pos3[0], (float)l_Pos3[1]);
        }
        this.mc.getConnection().sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(l_BestPosition, l_Facing, (this.mc.player.getHeldItemOffhand().getItem() == Items.END_CRYSTAL) ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND, 0.0f, 0.0f, 0.0f));
        this.PlacedCrystals.add(l_BestPosition);
        if (l_PrevSlot != -1 && AutoCrystalModule.GhostHand.getValue()) {
            this.mc.player.inventory.currentItem = l_PrevSlot;
            this.mc.playerController.updateController();
        }
        return l_BestPosition;
    }
    
    private boolean SwitchHandToItemIfNeed(final Item p_Item) {
        if (this.mc.player.getHeldItemMainhand().getItem() == p_Item || this.mc.player.getHeldItemOffhand().getItem() == p_Item) {
            return false;
        }
        for (int l_I = 0; l_I < 9; ++l_I) {
            final ItemStack l_Stack = this.mc.player.inventory.getStackInSlot(l_I);
            if (l_Stack != ItemStack.EMPTY) {
                if (l_Stack.getItem() == p_Item) {
                    this.mc.player.inventory.currentItem = l_I;
                    this.mc.playerController.updateController();
                    return true;
                }
            }
        }
        return true;
    }
    
    private void SpoofRotationsTo(final float p_Yaw, final float p_Pitch) {
        final boolean l_IsSprinting = this.mc.player.isSprinting();
        if (l_IsSprinting != this.mc.player.serverSprintState) {
            if (l_IsSprinting) {
                this.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)this.mc.player, CPacketEntityAction.Action.START_SPRINTING));
            }
            else {
                this.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)this.mc.player, CPacketEntityAction.Action.STOP_SPRINTING));
            }
            this.mc.player.serverSprintState = l_IsSprinting;
        }
        final boolean l_IsSneaking = this.mc.player.isSneaking();
        if (l_IsSneaking != this.mc.player.serverSneakState) {
            if (l_IsSneaking) {
                this.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)this.mc.player, CPacketEntityAction.Action.START_SNEAKING));
            }
            else {
                this.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)this.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
            }
            this.mc.player.serverSneakState = l_IsSneaking;
        }
        if (PlayerUtil.isCurrentViewEntity()) {
            final float l_Pitch = p_Pitch;
            final float l_Yaw = p_Yaw;
            final AxisAlignedBB axisalignedbb = this.mc.player.getEntityBoundingBox();
            final double l_PosXDifference = this.mc.player.posX - this.mc.player.lastReportedPosX;
            final double l_PosYDifference = axisalignedbb.minY - this.mc.player.lastReportedPosY;
            final double l_PosZDifference = this.mc.player.posZ - this.mc.player.lastReportedPosZ;
            final double l_YawDifference = l_Yaw - this.mc.player.lastReportedYaw;
            final double l_RotationDifference = l_Pitch - this.mc.player.lastReportedPitch;
            final EntityPlayerSP player = this.mc.player;
            ++player.positionUpdateTicks;
            boolean l_MovedXYZ = l_PosXDifference * l_PosXDifference + l_PosYDifference * l_PosYDifference + l_PosZDifference * l_PosZDifference > 9.0E-4 || this.mc.player.positionUpdateTicks >= 20;
            final boolean l_MovedRotation = l_YawDifference != 0.0 || l_RotationDifference != 0.0;
            if (this.mc.player.isRiding()) {
                this.mc.player.connection.sendPacket((Packet)new CPacketPlayer.PositionRotation(this.mc.player.motionX, -999.0, this.mc.player.motionZ, l_Yaw, l_Pitch, this.mc.player.onGround));
                l_MovedXYZ = false;
            }
            else if (l_MovedXYZ && l_MovedRotation) {
                this.mc.player.connection.sendPacket((Packet)new CPacketPlayer.PositionRotation(this.mc.player.posX, axisalignedbb.minY, this.mc.player.posZ, l_Yaw, l_Pitch, this.mc.player.onGround));
            }
            else if (l_MovedXYZ) {
                this.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(this.mc.player.posX, axisalignedbb.minY, this.mc.player.posZ, this.mc.player.onGround));
            }
            else if (l_MovedRotation) {
                this.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(l_Yaw, l_Pitch, this.mc.player.onGround));
            }
            else if (this.mc.player.prevOnGround != this.mc.player.onGround) {
                this.mc.player.connection.sendPacket((Packet)new CPacketPlayer(this.mc.player.onGround));
            }
            if (l_MovedXYZ) {
                this.mc.player.lastReportedPosX = this.mc.player.posX;
                this.mc.player.lastReportedPosY = axisalignedbb.minY;
                this.mc.player.lastReportedPosZ = this.mc.player.posZ;
                this.mc.player.positionUpdateTicks = 0;
            }
            if (l_MovedRotation) {
                this.mc.player.lastReportedYaw = l_Yaw;
                this.mc.player.lastReportedPitch = l_Pitch;
            }
            this.mc.player.prevOnGround = this.mc.player.onGround;
            this.mc.player.autoJumpEnabled = this.mc.player.mc.gameSettings.autoJump;
        }
    }
    
    public boolean NeedPause() {
        if (this.Surround.isEnabled() && !this.Surround.IsSurrounded((EntityPlayer)this.mc.player) && this.Surround.HasObsidian()) {
            if (!this.Surround.ActivateOnlyOnShift.getValue()) {
                return true;
            }
            if (!this.mc.gameSettings.keyBindSneak.isKeyDown()) {
                return true;
            }
        }
        return (this.AutoTrapFeet.isEnabled() && !this.AutoTrapFeet.IsCurrentTargetTrapped() && this.AutoTrapFeet.HasObsidian()) || this.AutoMend.isEnabled() || (this.SelfTrap.isEnabled() && !this.SelfTrap.IsSelfTrapped() && this.Surround.HasObsidian()) || (AutoCrystalModule.MinHealthPause.getValue() && this.mc.player.getHealth() + this.mc.player.getAbsorptionAmount() < AutoCrystalModule.RequiredHealth.getValue()) || (this.HoleFiller.isEnabled() && this.HoleFiller.IsProcessing()) || (AutoCrystalModule.PauseIfHittingBlock.getValue() && this.mc.playerController.isHittingBlock && this.mc.player.getHeldItemMainhand().getItem() instanceof ItemTool) || this.AutoCity.isEnabled();
    }
    
    static {
        Mode = new Value<Modes>("Mode", new String[] { "M" }, "Mode of updating to use", Modes.ClientTick);
        PlaceMode = new Value<PlaceModes>("PlaceMode", new String[] { "" }, "Automatically place mode", PlaceModes.MostDamage);
        DestroyMode = new Value<DestroyModes>("BreakMode", new String[] { "" }, "Automatically Destroy mode", DestroyModes.Always);
        Ticks = new Value<Integer>("Ticks", new String[] { "Ticks" }, "Ticks", 1, 0, 10, 1);
        DestroyDistance = new Value<Float>("BreakDistance", new String[] { "" }, "Range to attepmt to destory", 4.0f, 0.0f, 5.0f, 0.5f);
        PlaceDistance = new Value<Float>("PlaceDistance", new String[] { "" }, "Place range", 4.0f, 0.0f, 5.0f, 0.5f);
        WallsRange = new Value<Float>("WallsRange", new String[] { "" }, "Maximum distance through a wall", 3.5f, 0.0f, 5.0f, 0.5f);
        MinDMG = new Value<Float>("MinimumDMG", new String[] { "" }, "Minimum dmg for placing crystals near target", 4.0f, 0.0f, 20.0f, 1.0f);
        MaxSelfDMG = new Value<Float>("MaxSelfDMG", new String[] { "" }, "Max self dmg for breaking crystals that will deal tons of dmg", 4.0f, 0.0f, 20.0f, 1.0f);
        FacePlace = new Value<Float>("FacePlace", new String[] { "" }, "Oppoment health level for faceplacing", 8.0f, 0.0f, 20.0f, 0.5f);
        NoSuicide = new Value<Boolean>("NoSuicide", new String[] { "NS" }, "Doesn't commit suicide/pop if you are going to take fatal damage from self placed crystal", true);
        PauseWhileEating = new Value<Boolean>("PauseWhileEating", new String[] { "PauseWhileEating" }, "Pause while eating", true);
        AntiWeakness = new Value<Boolean>("AntiWeakness", new String[] { "AntiWeakness" }, "Uses a tool or sword to hit the crystals", true);
        GhostHand = new Value<Boolean>("ItemSpoof", new String[] { "GhostHand" }, "Allows you to place crystals by spoofing item packets", false);
        GhostHandWeakness = new Value<Boolean>("SpoofHandIfWeakness", new String[] { "GhostHandWeakness" }, "Breaks crystals with sword with ghosthand", false);
        ChatMsgs = new Value<Boolean>("ChatMsgs", new String[] { "ChatMsgs" }, "Displays ChatMsgs", false);
        Players = new Value<Boolean>("Players", new String[] { "Players" }, "Place on players", true);
        Hostile = new Value<Boolean>("Hostile", new String[] { "Hostile" }, "Place on Hostile", false);
        Animals = new Value<Boolean>("Animals", new String[] { "Animals" }, "Place on Animals", false);
        Tamed = new Value<Boolean>("Tamed", new String[] { "Tamed" }, "Place on Tamed", false);
        ResetRotationNoTarget = new Value<Boolean>("ResetRotationNoTarget", new String[] { "ResetRotationNoTarget" }, "ResetRotationNoTarget", false);
        Polyplace = new Value<Boolean>("Polyplace", new String[] { "Polyplace" }, "Polyplace", true);
        OnlyPlaceWithCrystal = new Value<Boolean>("OnlyPlaceWithCrystal ", new String[] { "OPWC" }, "Only places when you're manually using a crystal in your main or offhand", false);
        PlaceObsidianIfNoValidSpots = new Value<Boolean>("PlaceObsidian", new String[] { "POINVS" }, "Automatically places obsidian if there are no available crystal spots, so you can crystal your opponent", false);
        MinHealthPause = new Value<Boolean>("MinHealthPause", new String[] { "MHP" }, "Automatically pauses if you are below RequiredHealth", false);
        RequiredHealth = new Value<Float>("RequiredHealth", new String[] { "" }, "RequiredHealth for autocrystal to function, must be above or equal to this amount.", 11.0f, 0.0f, 20.0f, 1.0f);
        AutoPolyplace = new Value<Boolean>("AutoPolyplace", new String[] { "" }, "Automatically enables/disables polyplace", false);
        HealthBelowAutoPolyplace = new Value<Float>("DisableAutoPolyplaceHealth", new String[] { "" }, "RequiredHealth for target to be for automatic polyplace toggling.", 11.0f, 0.0f, 20.0f, 1.0f);
        PauseIfHittingBlock = new Value<Boolean>("PauseIfHittingBlock", new String[] { "" }, "Pauses when your hitting a block with a pickaxe", false);
        Render = new Value<Boolean>("Render", new String[] { "Render" }, "Allows for rendering of block placements", true);
        Red = new Value<Integer>("Red", new String[] { "Red" }, "Red for rendering", 51, 0, 255, 5);
        Green = new Value<Integer>("Green", new String[] { "Green" }, "Green for rendering", 255, 0, 255, 5);
        Blue = new Value<Integer>("Blue", new String[] { "Blue" }, "Blue for rendering", 243, 0, 255, 5);
        Alpha = new Value<Integer>("Alpha", new String[] { "Alpha" }, "Alpha for rendering", 153, 0, 255, 5);
    }
    
    private enum Modes
    {
        ClientTick, 
        MotionTick;
    }
    
    private enum DestroyModes
    {
        None, 
        Smart, 
        Always, 
        OnlyOwn;
    }
    
    private enum PlaceModes
    {
        None, 
        Nearest, 
        Priority, 
        MostDamage;
    }
}
