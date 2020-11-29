//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.modules.misc;

import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFarmland;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.block.BlockReed;
import net.minecraft.block.BlockCrops;
import me.gopro336.goprohack.managers.ModuleManager;
import java.util.function.Predicate;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumFacing;
import net.minecraft.entity.player.EntityPlayer;
import java.util.Comparator;
import net.minecraft.entity.Entity;
import me.gopro336.goprohack.util.entity.EntityUtil;
import me.gopro336.goprohack.util.BlockInteractionHelper;
import me.gopro336.goprohack.util.entity.PlayerUtil;
import net.minecraft.util.math.BlockPos;
import me.zero.alpine.fork.listener.EventHandler;
import me.gopro336.goprohack.events.player.EventPlayerMotionUpdate;
import me.zero.alpine.fork.listener.Listener;
import me.gopro336.goprohack.util.Timer;
import me.gopro336.goprohack.modules.Value;
import me.gopro336.goprohack.modules.Module;

public class AutoTendModule extends Module
{
    public final Value<Integer> Radius;
    public final Value<Boolean> Harvest;
    public final Value<Boolean> Replant;
    public final Value<Modes> Mode;
    public final Value<Float> Delay;
    private AutoBonemealModule Bonemeal;
    private Timer timer;
    @EventHandler
    private Listener<EventPlayerMotionUpdate> OnPlayerUpdate;
    
    public AutoTendModule() {
        super("AutoTend", new String[] { "" }, "Breaks and replants plants nearby", "NONE", -1, ModuleType.MISC);
        this.Radius = new Value<Integer>("Radius", new String[] { "R" }, "Radius to search for and break torches", 4, 0, 10, 1);
        this.Harvest = new Value<Boolean>("Harvest", new String[] { "Harvest" }, "Automatically Harvests", true);
        this.Replant = new Value<Boolean>("Replant", new String[] { "Replants" }, "Automatically plants if not harvesting, and there's nothing to harvest", true);
        this.Mode = new Value<Modes>("ReplantMode", new String[] { "Replant" }, "What crop to plant at empty plowed places", Modes.Wheat);
        this.Delay = new Value<Float>("Delay", new String[] { "D" }, "Delay to harvest/replant", 1.0f, 0.0f, 10.0f, 0.1f);
        this.timer = new Timer();
        BlockPos l_ClosestPos;
        double[] l_Pos;
        BlockPos l_ClosestPos2;
        double[] l_Pos2;
        final CPacketPlayerTryUseItemOnBlock cPacketPlayerTryUseItemOnBlock;
        final Object o;
        this.OnPlayerUpdate = new Listener<EventPlayerMotionUpdate>(p_Event -> {
            if (!this.Bonemeal.IsRunning() || !this.Bonemeal.isEnabled()) {
                if (this.Harvest.getValue()) {
                    if (!this.timer.passed(this.Delay.getValue() * 100.0f)) {
                        return;
                    }
                    else {
                        this.timer.reset();
                        l_ClosestPos = BlockInteractionHelper.getSphere(PlayerUtil.GetLocalPlayerPosFloored(), this.Radius.getValue(), this.Radius.getValue(), false, true, 0).stream().filter(p_Pos -> this.IsHarvestBlock(p_Pos)).min(Comparator.comparing(p_Pos -> EntityUtil.GetDistanceOfEntityToBlock((Entity)this.mc.player, p_Pos))).orElse(null);
                        if (l_ClosestPos != null) {
                            p_Event.cancel();
                            l_Pos = EntityUtil.calculateLookAt(l_ClosestPos.getX() + 0.5, l_ClosestPos.getY() - 0.5, l_ClosestPos.getZ() + 0.5, (EntityPlayer)this.mc.player);
                            PlayerUtil.PacketFacePitchAndYaw((float)l_Pos[1], (float)l_Pos[0]);
                            this.mc.playerController.clickBlock(l_ClosestPos, EnumFacing.UP);
                            this.mc.player.swingArm(EnumHand.MAIN_HAND);
                            return;
                        }
                    }
                }
                if (this.Replant.getValue() && this.HasSeeds()) {
                    if (!(!this.timer.passed(this.Delay.getValue() * 100.0f))) {
                        this.timer.reset();
                        l_ClosestPos2 = BlockInteractionHelper.getSphere(PlayerUtil.GetLocalPlayerPosFloored(), this.Radius.getValue(), this.Radius.getValue(), false, true, 0).stream().filter(p_Pos -> this.IsReplantBlock(p_Pos)).min(Comparator.comparing(p_Pos -> EntityUtil.GetDistanceOfEntityToBlock((Entity)this.mc.player, p_Pos))).orElse(null);
                        if (l_ClosestPos2 != null) {
                            p_Event.cancel();
                            l_Pos2 = EntityUtil.calculateLookAt(l_ClosestPos2.getX() + 0.5, l_ClosestPos2.getY() - 0.5, l_ClosestPos2.getZ() + 0.5, (EntityPlayer)this.mc.player);
                            PlayerUtil.PacketFacePitchAndYaw((float)l_Pos2[1], (float)l_Pos2[0]);
                            this.SwitchToSeedSlot();
                            this.mc.player.swingArm(this.IsItemStackSeed(this.mc.player.getHeldItemOffhand()) ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND);
                            this.mc.getConnection();
                            new CPacketPlayerTryUseItemOnBlock(l_ClosestPos2, EnumFacing.UP, this.IsItemStackSeed(this.mc.player.getHeldItemOffhand()) ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND, 0.0f, 0.0f, 0.0f);
                            ((NetHandlerPlayClient)o).sendPacket((Packet)cPacketPlayerTryUseItemOnBlock);
                        }
                    }
                }
            }
        }, (Predicate<EventPlayerMotionUpdate>[])new Predicate[0]);
    }
    
    @Override
    public void onEnable() {
        super.onEnable();
        if (this.Bonemeal == null) {
            this.Bonemeal = (AutoBonemealModule)ModuleManager.Get().GetMod(AutoBonemealModule.class);
        }
    }
    
    private boolean IsHarvestBlock(final BlockPos p_Pos) {
        final IBlockState l_State = this.mc.world.getBlockState(p_Pos);
        if (l_State.getBlock() instanceof BlockCrops) {
            final BlockCrops l_Crop = (BlockCrops)l_State.getBlock();
            if (l_Crop.isMaxAge(l_State)) {
                return true;
            }
        }
        else if (l_State.getBlock() instanceof BlockReed && this.mc.world.getBlockState(p_Pos.down()).getBlock() == Blocks.REEDS) {
            return true;
        }
        return false;
    }
    
    private boolean IsReplantBlock(final BlockPos p_Pos) {
        final IBlockState l_State = this.mc.world.getBlockState(p_Pos);
        return l_State.getBlock() instanceof BlockFarmland && this.HasNoCropsAndIsPlantable(p_Pos);
    }
    
    private boolean HasNoCropsAndIsPlantable(final BlockPos p_Pos) {
        final Block block = this.mc.world.getBlockState(p_Pos.up()).getBlock();
        return block == Blocks.AIR;
    }
    
    private boolean HasSeeds() {
        for (int l_I = 0; l_I < 9; ++l_I) {
            final ItemStack l_Stack = this.mc.player.inventory.getStackInSlot(l_I);
            if (!l_Stack.isEmpty()) {
                if (this.IsItemStackSeed(l_Stack)) {
                    return true;
                }
            }
        }
        return this.IsItemStackSeed(this.mc.player.getHeldItemOffhand());
    }
    
    private boolean IsItemStackSeed(final ItemStack p_Stack) {
        return !p_Stack.isEmpty() && (p_Stack.getItem() == Items.BEETROOT_SEEDS || p_Stack.getItem() == Items.POTATO || p_Stack.getItem() == Items.WHEAT_SEEDS || p_Stack.getItem() == Items.CARROT);
    }
    
    private void SwitchToSeedSlot() {
        if (this.IsItemStackSeed(this.mc.player.getHeldItemOffhand()) || this.IsItemStackSeed(this.mc.player.getHeldItemMainhand())) {
            return;
        }
        for (int l_I = 0; l_I < 9; ++l_I) {
            final ItemStack l_Stack = this.mc.player.inventory.getStackInSlot(l_I);
            if (!l_Stack.isEmpty()) {
                if (this.IsItemStackSeed(l_Stack)) {
                    this.mc.player.inventory.currentItem = l_I;
                    this.mc.playerController.updateController();
                    return;
                }
            }
        }
    }
    
    private enum Modes
    {
        Beetrot, 
        Carrots, 
        Potatos, 
        Wheat;
    }
}
