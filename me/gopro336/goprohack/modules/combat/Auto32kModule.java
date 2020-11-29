//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.modules.combat;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemShulkerBox;
import me.gopro336.goprohack.util.Pair;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.RayTraceResult;
import java.util.Comparator;
import net.minecraft.entity.Entity;
import me.gopro336.goprohack.util.entity.EntityUtil;
import me.gopro336.goprohack.util.entity.PlayerUtil;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.gui.inventory.GuiDispenser;
import me.gopro336.goprohack.managers.ModuleManager;
import net.minecraft.client.gui.inventory.GuiContainer;
import me.gopro336.goprohack.util.entity.ItemUtil;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemStack;
import net.minecraft.block.BlockShulkerBox;
import net.minecraft.client.gui.GuiHopper;
import java.util.function.Predicate;
import net.minecraft.network.play.server.SPacketWindowItems;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.network.play.server.SPacketOpenWindow;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumFacing;
import me.gopro336.goprohack.util.BlockInteractionHelper;
import net.minecraft.network.play.server.SPacketConfirmTransaction;
import me.gopro336.goprohack.events.player.EventPlayerUpdate;
import me.zero.alpine.fork.listener.EventHandler;
import me.gopro336.goprohack.events.network.EventNetworkPacketEvent;
import me.zero.alpine.fork.listener.Listener;
import me.gopro336.goprohack.util.Timer;
import net.minecraft.util.math.BlockPos;
import me.gopro336.goprohack.modules.Value;
import me.gopro336.goprohack.modules.Module;

public class Auto32kModule extends Module
{
    private Value<Modes> Mode;
    private Value<Boolean> Automatic;
    private Value<Integer> Delay;
    private Value<Boolean> ThrowReverted;
    private Value<Boolean> Toggles;
    private int ShulkerSlot;
    private BlockPos HopperPosition;
    private boolean WasInHopper;
    private boolean WasInDispenser;
    private Timer Take32kTimer;
    private BlockPos DispenserPosition;
    @EventHandler
    private Listener<EventNetworkPacketEvent> PacketEvent;
    @EventHandler
    private Listener<EventPlayerUpdate> OnPlayerUpdate;
    
    public Auto32kModule() {
        super("Auto32k", new String[] { "Auto32k" }, "Automatically bypasses the illegals plugin to let you use a 32k", "NONE", 5131167, ModuleType.COMBAT);
        this.Mode = new Value<Modes>("Mode", new String[] { "Modes" }, "The mode to use", Modes.Hopper);
        this.Automatic = new Value<Boolean>("Automatic", new String[] { "Automatic" }, "Automatically finds a place in range to place at", true);
        this.Delay = new Value<Integer>("Delay", new String[] { "Delay" }, "Delay", 250, 0, 2000, 100);
        this.ThrowReverted = new Value<Boolean>("ThrowReverted", new String[] { "ThrowRevered" }, "Automatically throws reverted 32ks", true);
        this.Toggles = new Value<Boolean>("Toggles", new String[] { "Toggles" }, "Toggles off when out of the hopper", true);
        this.ShulkerSlot = -1;
        this.HopperPosition = null;
        this.WasInHopper = false;
        this.WasInDispenser = false;
        this.Take32kTimer = new Timer();
        this.DispenserPosition = null;
        SPacketConfirmTransaction l_Packet;
        int l_RedstoneBlock;
        SPacketOpenWindow l_Packet2;
        this.PacketEvent = new Listener<EventNetworkPacketEvent>(p_Event -> {
            if (p_Event.getPacket() instanceof SPacketConfirmTransaction) {
                l_Packet = (SPacketConfirmTransaction)p_Event.getPacket();
                if (this.WasInDispenser && this.DispenserPosition != null) {
                    this.mc.player.closeScreen();
                    l_RedstoneBlock = this.GetSlotById(152);
                    this.mc.player.inventory.currentItem = l_RedstoneBlock;
                    this.mc.playerController.updateController();
                    BlockInteractionHelper.place(this.DispenserPosition.east(), 5.0f, true, false);
                    this.DispenserPosition = null;
                    this.mc.getConnection().sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(this.HopperPosition, EnumFacing.NORTH, EnumHand.MAIN_HAND, 0.0f, 0.0f, 0.0f));
                }
            }
            else if (p_Event.getPacket() instanceof SPacketOpenWindow) {
                l_Packet2 = (SPacketOpenWindow)p_Event.getPacket();
                if (this.DispenserPosition != null) {
                    this.WasInDispenser = true;
                    this.mc.playerController.windowClick(l_Packet2.getWindowId(), this.ShulkerSlot + 36, 0, ClickType.QUICK_MOVE, (EntityPlayer)this.mc.player);
                }
            }
            else if (p_Event.getPacket() instanceof SPacketWindowItems) {
                this.Take32kTimer.reset();
            }
            return;
        }, (Predicate<EventNetworkPacketEvent>[])new Predicate[0]);
        int l_I;
        ItemStack l_Stack;
        int l_FreeHotbarSlot;
        int l_I2;
        ItemStack l_Stack2;
        KillAuraModule l_KillAura;
        this.OnPlayerUpdate = new Listener<EventPlayerUpdate>(p_Event -> {
            if (this.WasInHopper && this.Toggles.getValue() && this.mc.currentScreen == null) {
                this.toggle();
            }
            else if (this.HopperPosition != null && this.DispenserPosition == null) {
                if (!(this.mc.currentScreen instanceof GuiHopper)) {
                    if (!this.WasInHopper) {
                        this.mc.getConnection().sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(this.HopperPosition, EnumFacing.NORTH, EnumHand.MAIN_HAND, 0.0f, 0.0f, 0.0f));
                    }
                }
                else if (!(!this.Take32kTimer.passed(this.Delay.getValue()))) {
                    this.WasInHopper = true;
                    if (!(this.mc.world.getBlockState(this.HopperPosition.up()).getBlock() instanceof BlockShulkerBox)) {
                        this.mc.player.inventory.currentItem = this.ShulkerSlot;
                        this.mc.playerController.updateController();
                        BlockInteractionHelper.place(this.HopperPosition.up(), 5.0f, true, false);
                    }
                    if (this.mc.player.getHeldItemMainhand() == ItemStack.EMPTY || !(this.mc.player.getHeldItemMainhand().getItem() instanceof ItemSword) || !ItemUtil.Is32k(this.mc.player.getHeldItemMainhand())) {
                        for (l_I = 0; l_I < 4; ++l_I) {
                            l_Stack = this.mc.player.openContainer.getSlot(l_I).getStack();
                            if (l_Stack != ItemStack.EMPTY) {
                                if (l_Stack.getItem() instanceof ItemSword) {
                                    if (!(!ItemUtil.Is32k(l_Stack))) {
                                        l_FreeHotbarSlot = this.GetFreeHotbarSlot();
                                        if (l_FreeHotbarSlot != -1) {
                                            this.mc.playerController.windowClick(((GuiContainer)this.mc.currentScreen).inventorySlots.windowId, l_I, 0, ClickType.QUICK_MOVE, (EntityPlayer)this.mc.player);
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                        for (l_I2 = 0; l_I2 < 9; ++l_I2) {
                            l_Stack2 = this.mc.player.inventory.getStackInSlot(l_I2);
                            if (!l_Stack2.isEmpty()) {
                                if (!(!(l_Stack2.getItem() instanceof ItemSword))) {
                                    if (ItemUtil.Is32k(l_Stack2)) {
                                        this.mc.player.inventory.currentItem = l_I2;
                                        this.mc.playerController.updateController();
                                        this.SendMessage(String.format("Found 32k in slot %s", l_I2));
                                        l_KillAura = (KillAuraModule)ModuleManager.Get().GetMod(KillAuraModule.class);
                                        if (!l_KillAura.isEnabled()) {
                                            l_KillAura.toggle();
                                        }
                                        l_KillAura.HitDelay.setValue(false);
                                    }
                                    else if (this.ThrowReverted.getValue()) {
                                        this.mc.playerController.windowClick(((GuiContainer)this.mc.currentScreen).inventorySlots.windowId, l_I2 + 32, 999, ClickType.THROW, (EntityPlayer)this.mc.player);
                                    }
                                }
                            }
                        }
                    }
                }
            }
            else if (this.DispenserPosition != null) {
                if (!(this.mc.currentScreen instanceof GuiDispenser) && !this.WasInDispenser) {
                    this.mc.getConnection().sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(this.DispenserPosition, EnumFacing.NORTH, EnumHand.MAIN_HAND, 0.0f, 0.0f, 0.0f));
                }
            }
        }, (Predicate<EventPlayerUpdate>[])new Predicate[0]);
    }
    
    @Override
    public String getMetaData() {
        if (this.ShulkerSlot == -1) {
            return "No shulker";
        }
        return null;
    }
    
    @Override
    public void onEnable() {
        super.onEnable();
        if (this.mc.player == null) {
            this.toggle();
            return;
        }
        final Pair<Integer, ItemStack> l_Pair = this.GetShulkerSlotInHotbar();
        this.ShulkerSlot = l_Pair.getFirst();
        if (l_Pair.getSecond() != ItemStack.EMPTY) {
            this.SendMessage(String.format("%s[Auto32k] Found shulker %s", ChatFormatting.LIGHT_PURPLE, l_Pair.getSecond().getDisplayName()));
        }
        this.HopperPosition = null;
        this.DispenserPosition = null;
        this.WasInHopper = false;
        this.WasInDispenser = false;
        if (this.Automatic.getValue()) {
            final int l_HopperSlot = this.GetHopperSlot();
            if (l_HopperSlot == -1 || this.ShulkerSlot == -1) {
                return;
            }
            this.HopperPosition = BlockInteractionHelper.getSphere(PlayerUtil.GetLocalPlayerPosFloored(), 4.0f, 4, false, true, 0).stream().filter(p_Pos -> this.IsValidBlockPos(p_Pos)).min(Comparator.comparing(p_Pos -> EntityUtil.GetDistanceOfEntityToBlock((Entity)this.mc.player, p_Pos))).orElse(null);
            if (this.HopperPosition == null) {
                return;
            }
            this.mc.player.inventory.currentItem = l_HopperSlot;
            this.mc.playerController.updateController();
            BlockInteractionHelper.place(this.HopperPosition, 5.0f, true, false);
        }
        else {
            final RayTraceResult l_Ray = this.mc.objectMouseOver;
            if (l_Ray == null) {
                return;
            }
            if (l_Ray.typeOfHit != RayTraceResult.Type.BLOCK) {
                return;
            }
            final BlockPos l_BlockPos = l_Ray.getBlockPos();
            final int l_LastSlot = this.mc.player.inventory.currentItem;
            boolean l_NeedHopper = true;
            if (this.mc.world.getBlockState(l_BlockPos).getBlock() == Blocks.HOPPER && BlockInteractionHelper.IsLiquidOrAir(l_BlockPos.up())) {
                l_NeedHopper = false;
            }
            this.HopperPosition = (l_NeedHopper ? l_BlockPos.up() : l_BlockPos);
        }
    }
    
    private Pair<Integer, ItemStack> GetShulkerSlotInHotbar() {
        for (int l_I = 0; l_I < 9; ++l_I) {
            final ItemStack l_Stack = this.mc.player.inventory.getStackInSlot(l_I);
            if (l_Stack != ItemStack.EMPTY && l_Stack.getItem() instanceof ItemShulkerBox) {
                return new Pair<Integer, ItemStack>(l_I, l_Stack);
            }
        }
        return new Pair<Integer, ItemStack>(-1, ItemStack.EMPTY);
    }
    
    private int GetHopperSlot() {
        for (int l_I = 0; l_I < 9; ++l_I) {
            final ItemStack l_Stack = this.mc.player.inventory.getStackInSlot(l_I);
            if (l_Stack != ItemStack.EMPTY && Item.getIdFromItem(l_Stack.getItem()) == 154) {
                return l_I;
            }
        }
        return -1;
    }
    
    private int GetSlotById(final int p_Id) {
        for (int l_I = 0; l_I < 9; ++l_I) {
            final ItemStack l_Stack = this.mc.player.inventory.getStackInSlot(l_I);
            if (l_Stack != ItemStack.EMPTY && Item.getIdFromItem(l_Stack.getItem()) == p_Id) {
                return l_I;
            }
        }
        return -1;
    }
    
    private int GetFreeHotbarSlot() {
        for (int l_I = 0; l_I < 9; ++l_I) {
            final ItemStack l_Stack = this.mc.player.inventory.getStackInSlot(l_I);
            if (l_Stack == ItemStack.EMPTY || l_Stack.getItem() == Items.AIR) {
                return l_I;
            }
        }
        return -1;
    }
    
    private boolean IsValidBlockPos(final BlockPos p_Pos) {
        final IBlockState l_State = this.mc.world.getBlockState(p_Pos);
        if (l_State.getBlock() == Blocks.AIR && this.mc.world.getBlockState(p_Pos.up()).getBlock() == Blocks.AIR) {
            final BlockInteractionHelper.ValidResult l_Result = BlockInteractionHelper.valid(p_Pos);
            if (l_Result == BlockInteractionHelper.ValidResult.Ok) {
                return true;
            }
        }
        return false;
    }
    
    enum Modes
    {
        Hopper;
    }
}
