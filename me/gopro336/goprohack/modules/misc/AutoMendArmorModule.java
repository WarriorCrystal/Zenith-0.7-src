//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.modules.misc;

import net.minecraft.item.ItemArmor;
import java.util.function.Function;
import java.util.Comparator;
import java.util.ArrayList;
import java.util.Iterator;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.math.AxisAlignedBB;
import java.util.function.Predicate;
import net.minecraft.world.World;
import net.minecraft.util.EnumHand;
import net.minecraft.item.ItemStack;
import net.minecraft.init.Items;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.network.play.client.CPacketPlayer;
import me.gopro336.goprohack.util.entity.PlayerUtil;
import net.minecraft.network.Packet;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.CPacketEntityAction;
import me.gopro336.goprohack.events.MinecraftEvent;
import me.zero.alpine.fork.listener.EventHandler;
import me.gopro336.goprohack.events.player.EventPlayerMotionUpdate;
import me.zero.alpine.fork.listener.Listener;
import me.gopro336.goprohack.util.Timer;
import java.util.LinkedList;
import me.gopro336.goprohack.modules.Value;
import me.gopro336.goprohack.modules.Module;

public final class AutoMendArmorModule extends Module
{
    public final Value<Float> Delay;
    public final Value<Float> Pct;
    public final Value<Boolean> GhostHand;
    private LinkedList<MendState> SlotsToMoveTo;
    private Timer timer;
    private Timer internalTimer;
    private boolean ReadyToMend;
    private boolean AllDone;
    @EventHandler
    private Listener<EventPlayerMotionUpdate> OnPlayerUpdate;
    
    public AutoMendArmorModule() {
        super("AutoMendArmor", new String[] { "AMA" }, "Moves your armor to a free slot and mends them piece by piece. Recommended to use autoarmor incase you need to toggle this off while using it", "NONE", 2415572, ModuleType.MISC);
        this.Delay = new Value<Float>("Delay", new String[] { "D" }, "Delay for moving armor pieces", 1.0f, 0.0f, 10.0f, 1.0f);
        this.Pct = new Value<Float>("Pct", new String[] { "P" }, "Amount of armor pct to heal at, so you don't waste extra experience potions", 90.0f, 0.0f, 100.0f, 10.0f);
        this.GhostHand = new Value<Boolean>("GhostHand", new String[] { "GH" }, "Uses ghost hand for exp", false);
        this.SlotsToMoveTo = new LinkedList<MendState>();
        this.timer = new Timer();
        this.internalTimer = new Timer();
        this.ReadyToMend = false;
        this.AllDone = false;
        boolean l_IsSprinting;
        boolean l_IsSneaking;
        float l_Pitch;
        float l_Yaw;
        AxisAlignedBB axisalignedbb;
        double l_PosXDifference;
        double l_PosYDifference;
        double l_PosZDifference;
        double l_YawDifference;
        double l_RotationDifference;
        EntityPlayerSP player;
        boolean l_MovedXYZ;
        boolean l_MovedRotation;
        boolean l_NeedBreak;
        final Iterator<MendState> iterator;
        MendState l_State;
        ItemStack l_CurrItem;
        int l_CurrSlot;
        int l_Slot;
        ItemStack l_CurrItem2;
        Iterator<ItemStack> l_Armor;
        ItemStack l_Stack;
        float l_ArmorPct;
        MendState l_State2;
        MendState l_NewState;
        this.OnPlayerUpdate = new Listener<EventPlayerMotionUpdate>(p_Event -> {
            if (p_Event.getEra() == MinecraftEvent.Era.PRE) {
                p_Event.cancel();
                l_IsSprinting = this.mc.player.isSprinting();
                if (l_IsSprinting != this.mc.player.serverSprintState) {
                    if (l_IsSprinting) {
                        this.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)this.mc.player, CPacketEntityAction.Action.START_SPRINTING));
                    }
                    else {
                        this.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)this.mc.player, CPacketEntityAction.Action.STOP_SPRINTING));
                    }
                    this.mc.player.serverSprintState = l_IsSprinting;
                }
                l_IsSneaking = this.mc.player.isSneaking();
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
                    l_Pitch = 90.0f;
                    l_Yaw = this.mc.player.rotationYaw;
                    axisalignedbb = this.mc.player.getEntityBoundingBox();
                    l_PosXDifference = this.mc.player.posX - this.mc.player.lastReportedPosX;
                    l_PosYDifference = axisalignedbb.minY - this.mc.player.lastReportedPosY;
                    l_PosZDifference = this.mc.player.posZ - this.mc.player.lastReportedPosZ;
                    l_YawDifference = l_Yaw - this.mc.player.lastReportedYaw;
                    l_RotationDifference = l_Pitch - this.mc.player.lastReportedPitch;
                    player = this.mc.player;
                    ++player.positionUpdateTicks;
                    l_MovedXYZ = (l_PosXDifference * l_PosXDifference + l_PosYDifference * l_PosYDifference + l_PosZDifference * l_PosZDifference > 9.0E-4 || this.mc.player.positionUpdateTicks >= 20);
                    l_MovedRotation = (l_YawDifference != 0.0 || l_RotationDifference != 0.0);
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
                if (this.timer.passed(this.Delay.getValue() * 100.0f)) {
                    this.timer.reset();
                    if (this.SlotsToMoveTo.isEmpty()) {
                        return;
                    }
                    else {
                        l_NeedBreak = false;
                        this.SlotsToMoveTo.iterator();
                        while (iterator.hasNext()) {
                            l_State = iterator.next();
                            if (l_State.MovedToInv) {
                                continue;
                            }
                            else {
                                l_State.MovedToInv = true;
                                if (l_State.Reequip) {
                                    if (l_State.SlotMovedTo <= 4) {
                                        this.mc.playerController.windowClick(this.mc.player.inventoryContainer.windowId, l_State.SlotMovedTo, 0, ClickType.PICKUP, (EntityPlayer)this.mc.player);
                                        this.mc.playerController.windowClick(this.mc.player.inventoryContainer.windowId, l_State.ArmorSlot, 0, ClickType.PICKUP, (EntityPlayer)this.mc.player);
                                    }
                                    else {
                                        this.mc.playerController.windowClick(this.mc.player.inventoryContainer.windowId, l_State.SlotMovedTo, 0, ClickType.QUICK_MOVE, (EntityPlayer)this.mc.player);
                                    }
                                }
                                else {
                                    this.mc.playerController.windowClick(this.mc.player.inventoryContainer.windowId, l_State.SlotMovedTo, 0, ClickType.PICKUP, (EntityPlayer)this.mc.player);
                                    this.mc.playerController.windowClick(this.mc.player.inventoryContainer.windowId, l_State.ArmorSlot, 0, ClickType.PICKUP, (EntityPlayer)this.mc.player);
                                    this.mc.playerController.windowClick(this.mc.player.inventoryContainer.windowId, l_State.SlotMovedTo, 0, ClickType.PICKUP, (EntityPlayer)this.mc.player);
                                }
                                l_NeedBreak = true;
                                break;
                            }
                        }
                        if (!l_NeedBreak) {
                            this.ReadyToMend = true;
                            if (this.AllDone) {
                                this.SendMessage(ChatFormatting.AQUA + "Disabling.");
                                this.toggle();
                                return;
                            }
                        }
                    }
                }
                if (!(!this.internalTimer.passed(1000.0))) {
                    if (this.ReadyToMend && !this.AllDone) {
                        l_CurrItem = this.mc.player.getHeldItemMainhand();
                        l_CurrSlot = -1;
                        if (l_CurrItem.isEmpty() || l_CurrItem.getItem() != Items.EXPERIENCE_BOTTLE) {
                            l_Slot = PlayerUtil.GetItemInHotbar(Items.EXPERIENCE_BOTTLE);
                            if (l_Slot != -1) {
                                l_CurrSlot = this.mc.player.inventory.currentItem;
                                this.mc.player.inventory.currentItem = l_Slot;
                                this.mc.playerController.updateController();
                            }
                            else {
                                this.SendMessage(ChatFormatting.RED + "No XP Found!");
                                this.SlotsToMoveTo.forEach(p_State -> {
                                    p_State.MovedToInv = false;
                                    p_State.Reequip = true;
                                    return;
                                });
                                this.SlotsToMoveTo.get(0).MovedToInv = true;
                                this.AllDone = true;
                                return;
                            }
                        }
                        l_CurrItem2 = this.mc.player.getHeldItemMainhand();
                        if (!l_CurrItem2.isEmpty() && l_CurrItem2.getItem() == Items.EXPERIENCE_BOTTLE) {
                            l_Armor = this.mc.player.getArmorInventoryList().iterator();
                            while (l_Armor.hasNext()) {
                                l_Stack = l_Armor.next();
                                if (l_Stack != ItemStack.EMPTY) {
                                    if (l_Stack.getItem() == Items.AIR) {
                                        continue;
                                    }
                                    else {
                                        l_ArmorPct = this.GetArmorPct(l_Stack);
                                        if (l_ArmorPct >= this.Pct.getValue()) {
                                            if (!this.SlotsToMoveTo.isEmpty()) {
                                                l_State2 = this.SlotsToMoveTo.get(0);
                                                if (l_State2.DoneMending) {
                                                    this.SlotsToMoveTo.forEach(p_State -> {
                                                        p_State.MovedToInv = false;
                                                        p_State.Reequip = true;
                                                        return;
                                                    });
                                                    this.SendMessage(ChatFormatting.GREEN + "All done!");
                                                    l_State2.MovedToInv = true;
                                                    this.AllDone = true;
                                                }
                                                else {
                                                    l_State2.DoneMending = true;
                                                    l_State2.MovedToInv = false;
                                                    l_State2.Reequip = false;
                                                    this.SendMessage(String.format("%sDone Mending %s%s %sat the requirement of %s NewPct: %s", ChatFormatting.LIGHT_PURPLE, ChatFormatting.AQUA, l_Stack.getDisplayName(), ChatFormatting.LIGHT_PURPLE, this.Pct.getValue().toString() + "%", l_ArmorPct + "%"));
                                                    this.ReadyToMend = false;
                                                    this.SlotsToMoveTo.remove(0);
                                                    this.SlotsToMoveTo.add(l_State2);
                                                    l_NewState = this.SlotsToMoveTo.get(0);
                                                    if (l_NewState.DoneMending || !l_NewState.NeedMend) {
                                                        this.SlotsToMoveTo.forEach(p_State -> {
                                                            p_State.MovedToInv = false;
                                                            p_State.Reequip = true;
                                                            return;
                                                        });
                                                        l_State2.MovedToInv = true;
                                                        this.SendMessage(ChatFormatting.GREEN + "All done!");
                                                        this.AllDone = true;
                                                    }
                                                    else {
                                                        this.SendMessage(ChatFormatting.LIGHT_PURPLE + "Mending next piece.. it's name is " + ChatFormatting.AQUA + l_NewState.ItemName);
                                                        l_NewState.MovedToInv = false;
                                                        l_NewState.Reequip = true;
                                                    }
                                                }
                                            }
                                        }
                                        else {
                                            this.mc.playerController.processRightClick((EntityPlayer)this.mc.player, (World)this.mc.world, EnumHand.MAIN_HAND);
                                            if (l_CurrSlot != -1 && this.GhostHand.getValue()) {
                                                this.mc.player.inventory.currentItem = l_CurrSlot;
                                                this.mc.playerController.updateController();
                                                break;
                                            }
                                            else {
                                                break;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }, (Predicate<EventPlayerMotionUpdate>[])new Predicate[0]);
    }
    
    @Override
    public void onEnable() {
        super.onEnable();
        final ArrayList<ItemStack> ArmorsToMend = new ArrayList<ItemStack>();
        this.SlotsToMoveTo.clear();
        this.ReadyToMend = false;
        this.AllDone = false;
        final int l_Slot = PlayerUtil.GetItemInHotbar(Items.EXPERIENCE_BOTTLE);
        if (l_Slot == -1) {
            this.SendMessage("You don't have any XP! Disabling!");
            this.toggle();
            return;
        }
        final Iterator<ItemStack> l_Armor = this.mc.player.getArmorInventoryList().iterator();
        int l_I = 0;
        boolean l_NeedMend = false;
        while (l_Armor.hasNext()) {
            final ItemStack l_Item = l_Armor.next();
            if (l_Item != ItemStack.EMPTY && l_Item.getItem() != Items.AIR) {
                ArmorsToMend.add(l_Item);
                final float l_Pct = this.GetArmorPct(l_Item);
                if (l_Pct >= this.Pct.getValue()) {
                    continue;
                }
                l_NeedMend = true;
                this.SendMessage(ChatFormatting.LIGHT_PURPLE + "[" + ++l_I + "] Mending " + ChatFormatting.AQUA + l_Item.getDisplayName() + ChatFormatting.LIGHT_PURPLE + " it has " + l_Pct + "%.");
            }
        }
        if (ArmorsToMend.isEmpty() || !l_NeedMend) {
            this.SendMessage(ChatFormatting.RED + "Nothing to mend!");
            this.toggle();
            return;
        }
        ArmorsToMend.sort(Comparator.comparing((Function<? super Object, ? extends Comparable>)ItemStack::getItemDamage).reversed());
        ArmorsToMend.forEach(p_Item -> this.SendMessage(p_Item.getDisplayName() + " " + p_Item.getItemDamage()));
        l_I = 0;
        final Iterator<ItemStack> l_Itr = ArmorsToMend.iterator();
        boolean l_First = true;
        for (l_I = 0; l_I < this.mc.player.inventoryContainer.getInventory().size(); ++l_I) {
            if (l_I != 0 && l_I != 5 && l_I != 6 && l_I != 7) {
                if (l_I != 8) {
                    final ItemStack l_Stack = (ItemStack)this.mc.player.inventoryContainer.getInventory().get(l_I);
                    if (l_Stack.isEmpty() || l_Stack.getItem() == Items.AIR) {
                        if (!l_Itr.hasNext()) {
                            break;
                        }
                        final ItemStack l_ArmorS = l_Itr.next();
                        this.SlotsToMoveTo.add(new MendState(l_First, l_I, this.GetSlotByItemStack(l_ArmorS), this.GetArmorPct(l_ArmorS) < this.Pct.getValue(), l_ArmorS.getDisplayName()));
                        if (l_First) {
                            l_First = false;
                        }
                    }
                }
            }
        }
    }
    
    public int GetSlotByItemStack(final ItemStack p_Stack) {
        if (p_Stack.getItem() instanceof ItemArmor) {
            final ItemArmor l_Armor = (ItemArmor)p_Stack.getItem();
            switch (l_Armor.getEquipmentSlot()) {
                case CHEST: {
                    return 6;
                }
                case FEET: {
                    return 8;
                }
                case HEAD: {
                    return 5;
                }
                case LEGS: {
                    return 7;
                }
            }
        }
        return this.mc.player.inventory.armorInventory.indexOf((Object)p_Stack) + 5;
    }
    
    private float GetArmorPct(final ItemStack p_Stack) {
        return (p_Stack.getMaxDamage() - p_Stack.getItemDamage()) / (float)p_Stack.getMaxDamage() * 100.0f;
    }
    
    private class MendState
    {
        public boolean MovedToInv;
        public int SlotMovedTo;
        public boolean Reequip;
        public int ArmorSlot;
        public boolean DoneMending;
        public boolean NeedMend;
        public String ItemName;
        
        public MendState(final boolean p_MovedToInv, final int p_SlotMovedTo, final int p_ArmorSlot, final boolean p_NeedMend, final String p_ItemName) {
            this.MovedToInv = false;
            this.SlotMovedTo = -1;
            this.Reequip = false;
            this.ArmorSlot = -1;
            this.DoneMending = false;
            this.NeedMend = true;
            this.MovedToInv = p_MovedToInv;
            this.SlotMovedTo = p_SlotMovedTo;
            this.ArmorSlot = p_ArmorSlot;
            this.NeedMend = p_NeedMend;
            this.ItemName = p_ItemName;
        }
    }
}
