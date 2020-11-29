//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.modules.player;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import me.gopro336.goprohack.util.entity.PlayerUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.init.Items;
import java.util.function.Predicate;
import me.zero.alpine.fork.listener.EventHandler;
import me.gopro336.goprohack.events.player.EventPlayerUpdate;
import me.zero.alpine.fork.listener.Listener;
import me.gopro336.goprohack.util.Timer;
import net.minecraft.item.Item;
import java.util.ArrayList;
import me.gopro336.goprohack.modules.Value;
import me.gopro336.goprohack.modules.Module;

public class HotbarCacheModule extends Module
{
    public final Value<Modes> Mode;
    public final Value<Float> Delay;
    private ArrayList<Item> Hotbar;
    private Timer timer;
    @EventHandler
    private Listener<EventPlayerUpdate> OnPlayerUpdate;
    
    public HotbarCacheModule() {
        super("Replenish", new String[] { "RP" }, "Automatically refills your hotbar similar to how autototem works", "NONE", 11740379, ModuleType.PLAYER);
        this.Mode = new Value<Modes>("Mode", new String[] { "M" }, "The mode of refilling to use, Refill may cause desync", Modes.Refill);
        this.Delay = new Value<Float>("Delay", new String[] { "D" }, "Delay to use", 1.0f, 0.0f, 10.0f, 1.0f);
        this.Hotbar = new ArrayList<Item>();
        this.timer = new Timer();
        int l_I;
        int l_I2;
        this.OnPlayerUpdate = new Listener<EventPlayerUpdate>(p_Event -> {
            if (this.mc.currentScreen == null) {
                if (!(!this.timer.passed(this.Delay.getValue() * 1000.0f))) {
                    switch (this.Mode.getValue()) {
                        case Cache: {
                            l_I = 0;
                            while (l_I < 9) {
                                if (this.SwitchSlotIfNeed(l_I)) {
                                    this.timer.reset();
                                    return;
                                }
                                else {
                                    ++l_I;
                                }
                            }
                            break;
                        }
                        case Refill: {
                            l_I2 = 0;
                            while (l_I2 < 9) {
                                if (this.RefillSlotIfNeed(l_I2)) {
                                    this.timer.reset();
                                    return;
                                }
                                else {
                                    ++l_I2;
                                }
                            }
                            break;
                        }
                    }
                }
            }
        }, (Predicate<EventPlayerUpdate>[])new Predicate[0]);
    }
    
    @Override
    public String getMetaData() {
        return String.valueOf(this.Mode.getValue());
    }
    
    @Override
    public void onEnable() {
        super.onEnable();
        this.Hotbar.clear();
        for (int l_I = 0; l_I < 9; ++l_I) {
            final ItemStack l_Stack = this.mc.player.inventory.getStackInSlot(l_I);
            if (!l_Stack.isEmpty() && !this.Hotbar.contains(l_Stack.getItem())) {
                this.Hotbar.add(l_Stack.getItem());
            }
            else {
                this.Hotbar.add(Items.AIR);
            }
        }
    }
    
    @Override
    public void toggleNoSave() {
    }
    
    private boolean SwitchSlotIfNeed(final int p_Slot) {
        final Item l_Item = this.Hotbar.get(p_Slot);
        if (l_Item == Items.AIR) {
            return false;
        }
        if (!this.mc.player.inventory.getStackInSlot(p_Slot).isEmpty() && this.mc.player.inventory.getStackInSlot(p_Slot).getItem() == l_Item) {
            return false;
        }
        final int l_Slot = PlayerUtil.GetItemSlot(l_Item);
        if (l_Slot != -1 && l_Slot != 45) {
            this.mc.playerController.windowClick(this.mc.player.inventoryContainer.windowId, l_Slot, 0, ClickType.PICKUP, (EntityPlayer)this.mc.player);
            this.mc.playerController.windowClick(this.mc.player.inventoryContainer.windowId, p_Slot + 36, 0, ClickType.PICKUP, (EntityPlayer)this.mc.player);
            this.mc.playerController.windowClick(this.mc.player.inventoryContainer.windowId, l_Slot, 0, ClickType.PICKUP, (EntityPlayer)this.mc.player);
            this.mc.playerController.updateController();
            return true;
        }
        return false;
    }
    
    private boolean RefillSlotIfNeed(final int p_Slot) {
        final ItemStack l_Stack = this.mc.player.inventory.getStackInSlot(p_Slot);
        if (l_Stack.isEmpty() || l_Stack.getItem() == Items.AIR) {
            return false;
        }
        if (!l_Stack.isStackable()) {
            return false;
        }
        if (l_Stack.getCount() >= l_Stack.getMaxStackSize()) {
            return false;
        }
        for (int l_I = 9; l_I < 36; ++l_I) {
            final ItemStack l_Item = this.mc.player.inventory.getStackInSlot(l_I);
            if (!l_Item.isEmpty()) {
                if (this.CanItemBeMergedWith(l_Stack, l_Item)) {
                    this.mc.playerController.windowClick(this.mc.player.inventoryContainer.windowId, l_I, 0, ClickType.QUICK_MOVE, (EntityPlayer)this.mc.player);
                    this.mc.playerController.updateController();
                    return true;
                }
            }
        }
        return false;
    }
    
    private boolean CanItemBeMergedWith(final ItemStack p_Source, final ItemStack p_Target) {
        return p_Source.getItem() == p_Target.getItem() && p_Source.getDisplayName().equals(p_Target.getDisplayName());
    }
    
    public enum Modes
    {
        Cache, 
        Refill;
    }
}
