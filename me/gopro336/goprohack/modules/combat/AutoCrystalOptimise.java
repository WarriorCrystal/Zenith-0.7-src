//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.modules.combat;

import net.minecraft.client.network.NetHandlerPlayClient;
import java.util.Comparator;
import me.gopro336.goprohack.managers.ModuleManager;
import java.lang.constant.Constable;
import net.minecraft.util.math.RayTraceResult;
import java.util.Iterator;
import me.gopro336.goprohack.util.render.RenderUtil;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.math.AxisAlignedBB;
import java.util.Collection;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundCategory;
import net.minecraft.network.play.server.SPacketSoundEffect;
import me.gopro336.goprohack.util.entity.PlayerUtil;
import me.gopro336.goprohack.events.MinecraftEvent;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.Vec3d;
import net.minecraft.init.Items;
import net.minecraft.util.EnumHand;
import me.gopro336.goprohack.util.entity.EntityUtil;
import net.minecraft.item.ItemTool;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemStack;
import net.minecraft.init.MobEffects;
import java.util.Collections;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;
import net.minecraft.world.World;
import me.gopro336.goprohack.util.CrystalUtils;
import net.minecraft.entity.Entity;
import me.gopro336.goprohack.managers.FriendManager;
import net.minecraft.entity.player.EntityPlayer;
import java.util.function.Predicate;
import net.minecraft.client.renderer.culling.Frustum;
import me.gopro336.goprohack.events.render.RenderEvent;
import me.gopro336.goprohack.events.network.EventNetworkPacketEvent;
import me.gopro336.goprohack.events.player.EventPlayerMotionUpdate;
import me.gopro336.goprohack.events.client.EventClientTick;
import me.zero.alpine.fork.listener.EventHandler;
import me.gopro336.goprohack.events.entity.EventEntityRemoved;
import me.zero.alpine.fork.listener.Listener;
import me.gopro336.goprohack.modules.misc.AutoMendArmorModule;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.client.renderer.culling.ICamera;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.util.math.BlockPos;
import java.util.concurrent.ConcurrentLinkedQueue;
import me.gopro336.goprohack.util.Timer;
import me.gopro336.goprohack.modules.Value;
import me.gopro336.goprohack.modules.Module;

public class AutoCrystalOptimise extends Module
{
    public static final Value<BreakModes> BreakMode;
    public static final Value<PlaceModes> PlaceMode;
    public static final Value<Float> PlaceRadius;
    public static final Value<Float> BreakRadius;
    public static final Value<Float> WallsRange;
    public static final Value<Boolean> MultiPlace;
    public static final Value<Integer> Ticks;
    public static final Value<Float> MinDMG;
    public static final Value<Float> MaxSelfDMG;
    public static final Value<Float> FacePlace;
    public static final Value<Boolean> AutoSwitch;
    public static final Value<Boolean> PauseIfHittingBlock;
    public static final Value<Boolean> PauseWhileEating;
    public static final Value<Boolean> NoSuicide;
    public static final Value<Boolean> AntiWeakness;
    public static final Value<Boolean> Render;
    public static final Value<Integer> Red;
    public static final Value<Integer> Green;
    public static final Value<Integer> Blue;
    public static final Value<Integer> Alpha;
    private AutoCrystalOptimise Mod;
    public static Timer _removeVisualTimer;
    private Timer _rotationResetTimer;
    private ConcurrentLinkedQueue<BlockPos> _placedCrystals;
    private ConcurrentHashMap<BlockPos, Float> _placedCrystalsDamage;
    private ICamera camera;
    private double[] _rotations;
    private ConcurrentHashMap<EntityEnderCrystal, Integer> _attackedEnderCrystals;
    private final Minecraft mc;
    private String _lastTarget;
    private int _remainingTicks;
    private BlockPos _lastPlaceLocation;
    private SurroundModule _surround;
    private AutoTrapFeet _autoTrapFeet;
    private AutoMendArmorModule _autoMend;
    private SelfTrapModule _selfTrap;
    private HoleFillerModule _holeFiller;
    private AutoCityModule _autoCity;
    @EventHandler
    private Listener<EventEntityRemoved> OnEntityRemove;
    @EventHandler
    private Listener<EventClientTick> OnClientTick;
    @EventHandler
    private Listener<EventPlayerMotionUpdate> OnPlayerUpdate;
    @EventHandler
    private Listener<EventNetworkPacketEvent> OnPacketEvent;
    @EventHandler
    private Listener<RenderEvent> OnRenderEvent;
    
    public AutoCrystalOptimise() {
        super("AutoCrystalOptimize", new String[] { "AutoCrystal2" }, "Crystal Aura optimised for 2b2t", "NONE", -1, ModuleType.COMBAT);
        this.Mod = null;
        this._rotationResetTimer = new Timer();
        this._placedCrystals = new ConcurrentLinkedQueue<BlockPos>();
        this._placedCrystalsDamage = new ConcurrentHashMap<BlockPos, Float>();
        this.camera = (ICamera)new Frustum();
        this._rotations = null;
        this._attackedEnderCrystals = new ConcurrentHashMap<EntityEnderCrystal, Integer>();
        this.mc = Minecraft.getMinecraft();
        this._lastTarget = null;
        this._lastPlaceLocation = BlockPos.ORIGIN;
        this._surround = null;
        this._autoTrapFeet = null;
        this._autoMend = null;
        this._selfTrap = null;
        this._holeFiller = null;
        this._autoCity = null;
        this.OnEntityRemove = new Listener<EventEntityRemoved>(event -> {
            if (event.GetEntity() instanceof EntityEnderCrystal) {
                this._attackedEnderCrystals.remove(event.GetEntity());
            }
            return;
        }, (Predicate<EventEntityRemoved>[])new Predicate[0]);
        BlockPos removed;
        float damage;
        EntityPlayer trappedTarget;
        final Iterator<EntityPlayer> iterator;
        EntityPlayer player;
        float minDamage;
        float calculatedDamage;
        boolean skipUpdateBlocks;
        ArrayList<BlockPos> placeLocations;
        EntityPlayer playerTarget;
        List<BlockPos> cachedCrystalBlocks;
        float damage2;
        String target;
        final Iterator<EntityPlayer> iterator2;
        EntityPlayer player2;
        float minDamage2;
        final Iterator<BlockPos> iterator3;
        BlockPos pos2;
        float calculatedDamage2;
        float minDamage3;
        float finalMinDamage;
        EntityPlayer finalTarget;
        EntityEnderCrystal crystal;
        boolean isValidCrystal;
        int i;
        ItemStack stack;
        int j;
        ItemStack stack2;
        BlockPos selectedPos;
        final Iterator<BlockPos> iterator4;
        BlockPos pos3;
        RayTraceResult result;
        EnumFacing facing;
        final CPacketPlayerTryUseItemOnBlock cPacketPlayerTryUseItemOnBlock;
        final Object o;
        float calculatedDamage3;
        this.OnClientTick = new Listener<EventClientTick>(event -> {
            if (AutoCrystalOptimise._removeVisualTimer.passed(1000.0)) {
                AutoCrystalOptimise._removeVisualTimer.reset();
                if (!this._placedCrystals.isEmpty()) {
                    removed = this._placedCrystals.remove();
                    if (removed != null) {
                        this._placedCrystalsDamage.remove(removed);
                    }
                }
                this._attackedEnderCrystals.clear();
            }
            if (this.NeedPause()) {
                this._remainingTicks = 0;
                return;
            }
            else {
                if (AutoCrystalOptimise.PlaceMode.getValue() == PlaceModes.Lethal && this._lastPlaceLocation != BlockPos.ORIGIN) {
                    damage = 0.0f;
                    trappedTarget = null;
                    this.mc.world.playerEntities.iterator();
                    while (iterator.hasNext()) {
                        player = iterator.next();
                        if (player != this.mc.player && !FriendManager.Get().IsFriend((Entity)player) && !this.mc.player.isDead) {
                            if (this.mc.player.getHealth() + this.mc.player.getAbsorptionAmount() <= 0.0f) {
                                continue;
                            }
                            else {
                                minDamage = AutoCrystalOptimise.MinDMG.getValue();
                                if (player.getHealth() + player.getAbsorptionAmount() <= AutoCrystalOptimise.FacePlace.getValue()) {
                                    minDamage = 1.0f;
                                }
                                calculatedDamage = CrystalUtils.calculateDamage((World)this.mc.world, this._lastPlaceLocation.getX() + 0.5, this._lastPlaceLocation.getY() + 1.0, this._lastPlaceLocation.getZ() + 0.5, (Entity)player, 0);
                                if (calculatedDamage >= minDamage && calculatedDamage > damage) {
                                    damage = calculatedDamage;
                                    trappedTarget = player;
                                }
                                else {
                                    continue;
                                }
                            }
                        }
                    }
                    if (damage == 0.0f || trappedTarget == null) {
                        this._lastPlaceLocation = BlockPos.ORIGIN;
                    }
                }
                if (this._remainingTicks > 0) {
                    --this._remainingTicks;
                }
                skipUpdateBlocks = (this._lastPlaceLocation != BlockPos.ORIGIN && AutoCrystalOptimise.PlaceMode.getValue() == PlaceModes.Lethal);
                placeLocations = new ArrayList<BlockPos>();
                playerTarget = null;
                if (!skipUpdateBlocks && this._remainingTicks <= 0) {
                    this._remainingTicks = AutoCrystalOptimise.Ticks.getValue();
                    cachedCrystalBlocks = CrystalUtils.findCrystalBlocks((EntityPlayer)this.mc.player, AutoCrystalOptimise.PlaceRadius.getValue()).stream().filter(pos -> this.VerifyCrystalBlocks(pos)).collect((Collector<? super Object, ?, List<BlockPos>>)Collectors.toList());
                    if (!cachedCrystalBlocks.isEmpty()) {
                        damage2 = 0.0f;
                        target = null;
                        this.mc.world.playerEntities.iterator();
                        while (iterator2.hasNext()) {
                            player2 = iterator2.next();
                            if (player2 != this.mc.player && !FriendManager.Get().IsFriend((Entity)player2) && !this.mc.player.isDead) {
                                if (this.mc.player.getHealth() + this.mc.player.getAbsorptionAmount() <= 0.0f) {
                                    continue;
                                }
                                else {
                                    minDamage2 = AutoCrystalOptimise.MinDMG.getValue();
                                    if (player2.getHealth() + player2.getAbsorptionAmount() <= AutoCrystalOptimise.FacePlace.getValue()) {
                                        minDamage2 = 1.0f;
                                    }
                                    cachedCrystalBlocks.iterator();
                                    while (iterator3.hasNext()) {
                                        pos2 = iterator3.next();
                                        calculatedDamage2 = CrystalUtils.calculateDamage((World)this.mc.world, pos2.getX() + 0.5, pos2.getY() + 1.0, pos2.getZ() + 0.5, (Entity)player2, 0);
                                        if (calculatedDamage2 >= minDamage2 && calculatedDamage2 > damage2) {
                                            damage2 = calculatedDamage2;
                                            if (!placeLocations.contains(pos2)) {
                                                placeLocations.add(pos2);
                                            }
                                            target = player2.getName();
                                            playerTarget = player2;
                                        }
                                    }
                                }
                            }
                        }
                        if (playerTarget != null) {
                            if (playerTarget.isDead || playerTarget.getHealth() <= 0.0f) {
                                return;
                            }
                            else if (!placeLocations.isEmpty()) {
                                minDamage3 = AutoCrystalOptimise.MinDMG.getValue();
                                if (playerTarget.getHealth() + playerTarget.getAbsorptionAmount() <= AutoCrystalOptimise.FacePlace.getValue()) {
                                    minDamage3 = 1.0f;
                                }
                                finalMinDamage = minDamage3;
                                finalTarget = playerTarget;
                                placeLocations.removeIf(pos -> CrystalUtils.calculateDamage((World)this.mc.world, pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5, (Entity)finalTarget, 0) < finalMinDamage);
                                Collections.reverse(placeLocations);
                                this._lastTarget = target;
                            }
                        }
                    }
                }
                crystal = this.GetNearestCrystalTo((Entity)this.mc.player);
                isValidCrystal = (crystal != null && this.mc.player.getDistance((Entity)crystal) < AutoCrystalOptimise.BreakRadius.getValue());
                if (!isValidCrystal && placeLocations.isEmpty() && !skipUpdateBlocks) {
                    this._remainingTicks = 0;
                    return;
                }
                else {
                    Label_1260_4: {
                        if (isValidCrystal) {
                            if (!skipUpdateBlocks) {
                                if (this._remainingTicks != AutoCrystalOptimise.Ticks.getValue()) {
                                    break Label_1260_4;
                                }
                            }
                            if (AutoCrystalOptimise.AntiWeakness.getValue() && this.mc.player.isPotionActive(MobEffects.WEAKNESS) && (this.mc.player.getHeldItemMainhand() == ItemStack.EMPTY || (!(this.mc.player.getHeldItemMainhand().getItem() instanceof ItemSword) && !(this.mc.player.getHeldItemMainhand().getItem() instanceof ItemTool)))) {
                                for (i = 0; i < 9; ++i) {
                                    stack = this.mc.player.inventory.getStackInSlot(i);
                                    if (!stack.isEmpty()) {
                                        if (stack.getItem() instanceof ItemTool || stack.getItem() instanceof ItemSword) {
                                            this.mc.player.inventory.currentItem = i;
                                            this.mc.playerController.updateController();
                                            break;
                                        }
                                    }
                                }
                            }
                            this._rotations = EntityUtil.calculateLookAt(crystal.posX + 0.5, crystal.posY - 0.5, crystal.posZ + 0.5, (EntityPlayer)this.mc.player);
                            this._rotationResetTimer.reset();
                            this.mc.playerController.attackEntity((EntityPlayer)this.mc.player, (Entity)crystal);
                            this.mc.player.swingArm(EnumHand.MAIN_HAND);
                            this.AddAttackedCrystal(crystal);
                            if (!AutoCrystalOptimise.MultiPlace.getValue()) {
                                return;
                            }
                        }
                    }
                    if (!placeLocations.isEmpty() || skipUpdateBlocks) {
                        if (AutoCrystalOptimise.AutoSwitch.getValue() && this.mc.player.getHeldItemOffhand().getItem() != Items.END_CRYSTAL && this.mc.player.getHeldItemMainhand().getItem() != Items.END_CRYSTAL) {
                            j = 0;
                            while (j < 9) {
                                stack2 = this.mc.player.inventory.getStackInSlot(j);
                                if (!stack2.isEmpty() && stack2.getItem() == Items.END_CRYSTAL) {
                                    this.mc.player.inventory.currentItem = j;
                                    this.mc.playerController.updateController();
                                    break;
                                }
                                else {
                                    ++j;
                                }
                            }
                        }
                        if (this.mc.player.getHeldItemMainhand().getItem() == Items.END_CRYSTAL || this.mc.player.getHeldItemOffhand().getItem() == Items.END_CRYSTAL) {
                            selectedPos = null;
                            if (!skipUpdateBlocks) {
                                placeLocations.iterator();
                                while (iterator4.hasNext()) {
                                    pos3 = iterator4.next();
                                    if (CrystalUtils.canPlaceCrystal(pos3)) {
                                        selectedPos = pos3;
                                        break;
                                    }
                                }
                            }
                            else {
                                selectedPos = this._lastPlaceLocation;
                            }
                            if (selectedPos == null) {
                                this._remainingTicks = 0;
                            }
                            else {
                                this._rotations = EntityUtil.calculateLookAt(selectedPos.getX() + 0.5, selectedPos.getY() - 0.5, selectedPos.getZ() + 0.5, (EntityPlayer)this.mc.player);
                                this._rotationResetTimer.reset();
                                result = this.mc.world.rayTraceBlocks(new Vec3d(this.mc.player.posX, this.mc.player.posY + this.mc.player.getEyeHeight(), this.mc.player.posZ), new Vec3d(selectedPos.getX() + 0.5, selectedPos.getY() - 0.5, selectedPos.getZ() + 0.5));
                                if (result == null || result.sideHit == null) {
                                    facing = EnumFacing.UP;
                                }
                                else {
                                    facing = result.sideHit;
                                }
                                this.mc.getConnection();
                                new CPacketPlayerTryUseItemOnBlock(selectedPos, facing, (this.mc.player.getHeldItemOffhand().getItem() == Items.END_CRYSTAL) ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND, 0.0f, 0.0f, 0.0f);
                                ((NetHandlerPlayClient)o).sendPacket((Packet)cPacketPlayerTryUseItemOnBlock);
                                if (this._placedCrystals.contains(selectedPos)) {
                                    this._placedCrystals.remove(selectedPos);
                                }
                                this._placedCrystals.add(selectedPos);
                                if (playerTarget != null) {
                                    calculatedDamage3 = CrystalUtils.calculateDamage((World)this.mc.world, selectedPos.getX() + 0.5, selectedPos.getY() + 1.0, selectedPos.getZ() + 0.5, (Entity)playerTarget, 0);
                                    this._placedCrystalsDamage.put(selectedPos, calculatedDamage3);
                                }
                                if (this._lastPlaceLocation != BlockPos.ORIGIN && this._lastPlaceLocation == selectedPos) {
                                    if (AutoCrystalOptimise.PlaceMode.getValue() == PlaceModes.Lethal) {
                                        this._remainingTicks = 0;
                                    }
                                }
                                else {
                                    this._lastPlaceLocation = selectedPos;
                                }
                            }
                        }
                    }
                    return;
                }
            }
        }, (Predicate<EventClientTick>[])new Predicate[0]);
        this.OnPlayerUpdate = new Listener<EventPlayerMotionUpdate>(event -> {
            if (event.getEra() != MinecraftEvent.Era.PRE) {
                return;
            }
            else if (event.isCancelled()) {
                this._rotations = null;
                return;
            }
            else if (this.NeedPause()) {
                this._rotations = null;
                return;
            }
            else {
                if (this._rotationResetTimer.passed(1000.0)) {
                    this._rotations = null;
                }
                if (this._rotations != null) {
                    event.cancel();
                    PlayerUtil.PacketFacePitchAndYaw((float)this._rotations[1], (float)this._rotations[0]);
                }
                return;
            }
        }, (Predicate<EventPlayerMotionUpdate>[])new Predicate[0]);
        SPacketSoundEffect packet;
        final SPacketSoundEffect sPacketSoundEffect;
        final SPacketSoundEffect sPacketSoundEffect2;
        this.OnPacketEvent = new Listener<EventNetworkPacketEvent>(event -> {
            if (event.getPacket() instanceof SPacketSoundEffect) {
                packet = (SPacketSoundEffect)event.getPacket();
                if (this.mc.world != null) {
                    if (packet.getCategory() == SoundCategory.BLOCKS && packet.getSound() == SoundEvents.ENTITY_GENERIC_EXPLODE) {
                        new ArrayList(this.mc.world.loadedEntityList).forEach(e -> {
                            if (e instanceof EntityEnderCrystal && e.getDistance(sPacketSoundEffect.getX(), sPacketSoundEffect.getY(), sPacketSoundEffect.getZ()) <= 6.0) {
                                e.setDead();
                            }
                            this._placedCrystals.removeIf(p_Pos -> p_Pos.getDistance((int)sPacketSoundEffect2.getX(), (int)sPacketSoundEffect2.getY(), (int)sPacketSoundEffect2.getZ()) <= 6.0);
                        });
                    }
                }
            }
            return;
        }, (Predicate<EventNetworkPacketEvent>[])new Predicate[0]);
        AxisAlignedBB bb;
        int color;
        float damage3;
        StringBuilder sb;
        Constable obj;
        String damageText;
        this.OnRenderEvent = new Listener<RenderEvent>(p_Event -> {
            if (this.mc.getRenderManager() != null && AutoCrystalOptimise.Render.getValue()) {
                this._placedCrystals.forEach(pos -> {
                    bb = new AxisAlignedBB(pos.getX() - this.mc.getRenderManager().viewerPosX, pos.getY() - this.mc.getRenderManager().viewerPosY, pos.getZ() - this.mc.getRenderManager().viewerPosZ, pos.getX() + 1 - this.mc.getRenderManager().viewerPosX, pos.getY() + 1 - this.mc.getRenderManager().viewerPosY, pos.getZ() + 1 - this.mc.getRenderManager().viewerPosZ);
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
                        color = (AutoCrystalOptimise.Alpha.getValue() << 24 | AutoCrystalOptimise.Red.getValue() << 16 | AutoCrystalOptimise.Green.getValue() << 8 | AutoCrystalOptimise.Blue.getValue());
                        RenderUtil.drawBoundingBox(bb, 1.0f, color);
                        RenderUtil.drawFilledBox(bb, color);
                        GL11.glDisable(2848);
                        GlStateManager.depthMask(true);
                        GlStateManager.enableDepth();
                        GlStateManager.enableTexture2D();
                        GlStateManager.disableBlend();
                        GlStateManager.popMatrix();
                        if (this._placedCrystalsDamage.containsKey(pos)) {
                            GlStateManager.pushMatrix();
                            RenderUtil.glBillboardDistanceScaled(pos.getX() + 0.5f, pos.getY() + 0.5f, pos.getZ() + 0.5f, (EntityPlayer)this.mc.player, 1.0f);
                            damage3 = this._placedCrystalsDamage.get(pos);
                            sb = new StringBuilder();
                            if (Math.floor(damage3) == damage3) {
                                obj = (int)damage3;
                            }
                            else {
                                obj = String.format("%.1f", damage3);
                            }
                            damageText = sb.append(obj).append("").toString();
                            GlStateManager.disableDepth();
                            GlStateManager.translate(-(RenderUtil.getStringWidth(damageText) / 2.0), 0.0, 0.0);
                            RenderUtil.drawStringWithShadow(damageText, 0.0f, 0.0f, -1);
                            GlStateManager.popMatrix();
                        }
                    }
                });
            }
        }, (Predicate<RenderEvent>[])new Predicate[0]);
    }
    
    @Override
    public void init() {
        this.Mod = this;
        this._surround = (SurroundModule)ModuleManager.Get().GetMod(SurroundModule.class);
        this._autoTrapFeet = (AutoTrapFeet)ModuleManager.Get().GetMod(AutoTrapFeet.class);
        this._autoMend = (AutoMendArmorModule)ModuleManager.Get().GetMod(AutoMendArmorModule.class);
        this._selfTrap = (SelfTrapModule)ModuleManager.Get().GetMod(SelfTrapModule.class);
        this._holeFiller = (HoleFillerModule)ModuleManager.Get().GetMod(HoleFillerModule.class);
        this._autoCity = (AutoCityModule)ModuleManager.Get().GetMod(AutoCityModule.class);
    }
    
    @Override
    public void toggleNoSave() {
    }
    
    @Override
    public void onEnable() {
        super.onEnable();
        this._placedCrystals.clear();
        this._placedCrystalsDamage.clear();
        this._remainingTicks = 0;
        this._lastPlaceLocation = BlockPos.ORIGIN;
    }
    
    @Override
    public String getMetaData() {
        return this._lastTarget;
    }
    
    private boolean ValidateCrystal(final EntityEnderCrystal e) {
        if (e == null || e.isDead) {
            return false;
        }
        if (this._attackedEnderCrystals.containsKey(e) && this._attackedEnderCrystals.get(e) > 5) {
            return false;
        }
        if (e.getDistance((Entity)this.mc.player) > (this.mc.player.canEntityBeSeen((Entity)e) ? AutoCrystalOptimise.BreakRadius.getValue() : AutoCrystalOptimise.WallsRange.getValue())) {
            return false;
        }
        switch (AutoCrystalOptimise.BreakMode.getValue()) {
            case OnlyOwn: {
                return e.getDistance(e.posX, e.posY, e.posZ) <= 3.0;
            }
            case Smart: {
                final float selfDamage = CrystalUtils.calculateDamage((World)this.mc.world, e.posX, e.posY, e.posZ, (Entity)this.mc.player, 0);
                if (selfDamage > AutoCrystalOptimise.MaxSelfDMG.getValue()) {
                    return false;
                }
                if (AutoCrystalOptimise.NoSuicide.getValue() && selfDamage >= this.mc.player.getHealth() + this.mc.player.getAbsorptionAmount()) {
                    return false;
                }
                for (final EntityPlayer player : this.mc.world.playerEntities) {
                    if (player != this.mc.player && !FriendManager.Get().IsFriend((Entity)player) && !this.mc.player.isDead) {
                        if (this.mc.player.getHealth() + this.mc.player.getAbsorptionAmount() <= 0.0f) {
                            continue;
                        }
                        float minDamage = AutoCrystalOptimise.MinDMG.getValue();
                        if (player.getHealth() + player.getAbsorptionAmount() <= AutoCrystalOptimise.FacePlace.getValue()) {
                            minDamage = 1.0f;
                        }
                        final float calculatedDamage = CrystalUtils.calculateDamage((World)this.mc.world, e.posX, e.posY, e.posZ, (Entity)player, 0);
                        if (calculatedDamage > minDamage) {
                            return true;
                        }
                        continue;
                    }
                }
                return false;
            }
            default: {
                return true;
            }
        }
    }
    
    public EntityEnderCrystal GetNearestCrystalTo(final Entity entity) {
        return (EntityEnderCrystal)this.mc.world.getLoadedEntityList().stream().filter(e -> e instanceof EntityEnderCrystal && this.ValidateCrystal(e)).map(e -> e).min(Comparator.comparing(e -> entity.getDistance(e))).orElse(null);
    }
    
    public void AddAttackedCrystal(final EntityEnderCrystal crystal) {
        if (this._attackedEnderCrystals.containsKey(crystal)) {
            final int value = this._attackedEnderCrystals.get(crystal);
            this._attackedEnderCrystals.put(crystal, value + 1);
        }
        else {
            this._attackedEnderCrystals.put(crystal, 1);
        }
    }
    
    private boolean VerifyCrystalBlocks(final BlockPos pos) {
        if (this.mc.player.getDistanceSq(pos) > AutoCrystalOptimise.PlaceRadius.getValue() * AutoCrystalOptimise.PlaceRadius.getValue()) {
            return false;
        }
        if (AutoCrystalOptimise.WallsRange.getValue() > 0.0f && !PlayerUtil.CanSeeBlock(pos) && pos.getDistance((int)this.mc.player.posX, (int)this.mc.player.posY, (int)this.mc.player.posZ) > AutoCrystalOptimise.WallsRange.getValue()) {
            return false;
        }
        final float selfDamage = CrystalUtils.calculateDamage((World)this.mc.world, pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5, (Entity)this.mc.player, 0);
        return selfDamage <= AutoCrystalOptimise.MaxSelfDMG.getValue() && (!AutoCrystalOptimise.NoSuicide.getValue() || selfDamage < this.mc.player.getHealth() + this.mc.player.getAbsorptionAmount());
    }
    
    public boolean NeedPause() {
        if (this._surround.isEnabled() && !this._surround.IsSurrounded((EntityPlayer)this.mc.player) && this._surround.HasObsidian()) {
            if (!this._surround.ActivateOnlyOnShift.getValue()) {
                return true;
            }
            if (!this.mc.gameSettings.keyBindSneak.isKeyDown()) {
                return true;
            }
        }
        return (this._autoTrapFeet.isEnabled() && !this._autoTrapFeet.IsCurrentTargetTrapped() && this._autoTrapFeet.HasObsidian()) || this._autoMend.isEnabled() || (this._selfTrap.isEnabled() && !this._selfTrap.IsSelfTrapped() && this._surround.HasObsidian()) || (this._holeFiller.isEnabled() && this._holeFiller.IsProcessing()) || (AutoCrystalOptimise.PauseIfHittingBlock.getValue() && this.mc.playerController.isHittingBlock && this.mc.player.getHeldItemMainhand().getItem() instanceof ItemTool) || this._autoCity.isEnabled();
    }
    
    public String getTarget() {
        return this._lastTarget;
    }
    
    static {
        BreakMode = new Value<BreakModes>("BreakMode", new String[] { "BM" }, "Mode of breaking to use", BreakModes.Always);
        PlaceMode = new Value<PlaceModes>("PlaceMode", new String[] { "BM" }, "Mode of placing to use", PlaceModes.Most);
        PlaceRadius = new Value<Float>("PlaceRadius", new String[] { "" }, "Radius for placing", 4.0f, 0.0f, 5.0f, 0.5f);
        BreakRadius = new Value<Float>("BreakRadius", new String[] { "" }, "Radius for BreakRadius", 4.0f, 0.0f, 5.0f, 0.5f);
        WallsRange = new Value<Float>("WallRange", new String[] { "" }, "Max distance through a wall", 3.5f, 0.0f, 5.0f, 0.5f);
        MultiPlace = new Value<Boolean>("MultiPlace", new String[] { "MultiPlaces" }, "Tries to polyplace", false);
        Ticks = new Value<Integer>("Ticks", new String[] { "IgnoreTicks" }, "The number of ticks to ignore on client update", 2, 0, 20, 1);
        MinDMG = new Value<Float>("MinimumDMG", new String[] { "" }, "Minimum damage to do to your opponent", 4.0f, 0.0f, 20.0f, 1.0f);
        MaxSelfDMG = new Value<Float>("MaxSelfDMG", new String[] { "" }, "Max self dmg for breaking crystals", 4.0f, 0.0f, 20.0f, 1.0f);
        FacePlace = new Value<Float>("FacePlace", new String[] { "" }, "Required target health for faceplacing", 8.0f, 0.0f, 20.0f, 0.5f);
        AutoSwitch = new Value<Boolean>("AutoSwitch", new String[] { "" }, "Automatically switches to crystals in your hotbar", true);
        PauseIfHittingBlock = new Value<Boolean>("PauseWhenMiningBlock", new String[] { "" }, "Pauses when your hitting a block with a pickaxe", false);
        PauseWhileEating = new Value<Boolean>("PauseWhileEating", new String[] { "PauseWhileEating" }, "Pause while eating", false);
        NoSuicide = new Value<Boolean>("NoSuicide", new String[] { "NS" }, "Doesn't commit suicide/pop if you are going to take fatal damage from self placed crystal", true);
        AntiWeakness = new Value<Boolean>("AntiWeakness", new String[] { "AW" }, "Switches to a sword to try and break crystals", true);
        Render = new Value<Boolean>("Render", new String[] { "Render" }, "Allows for rendering of block placements", true);
        Red = new Value<Integer>("Red", new String[] { "Red" }, "Red for rendering", 51, 0, 255, 5);
        Green = new Value<Integer>("Green", new String[] { "Green" }, "Green for rendering", 255, 0, 255, 5);
        Blue = new Value<Integer>("Blue", new String[] { "Blue" }, "Blue for rendering", 243, 0, 255, 5);
        Alpha = new Value<Integer>("Alpha", new String[] { "Alpha" }, "Alpha for rendering", 153, 0, 255, 5);
        AutoCrystalOptimise._removeVisualTimer = new Timer();
    }
    
    public enum BreakModes
    {
        Always, 
        Smart, 
        OnlyOwn;
    }
    
    public enum PlaceModes
    {
        Most, 
        Lethal;
    }
}
