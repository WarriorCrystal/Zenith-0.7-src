//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.modules.misc;

import net.minecraft.item.ItemStack;
import java.util.function.Predicate;
import net.minecraft.client.gui.inventory.GuiShulkerBox;
import net.minecraft.client.gui.inventory.GuiScreenHorseInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.ItemShulkerBox;
import net.minecraft.init.Items;
import net.minecraft.client.gui.inventory.GuiChest;
import me.zero.alpine.fork.listener.EventHandler;
import me.gopro336.goprohack.events.player.EventPlayerUpdate;
import me.zero.alpine.fork.listener.Listener;
import me.gopro336.goprohack.util.Timer;
import me.gopro336.goprohack.modules.Value;
import me.gopro336.goprohack.modules.Module;

public class ChestStealerModule extends Module
{
    public Value<Modes> Mode;
    public Value<Float> Delay;
    public Value<Boolean> DepositShulkers;
    public Value<Boolean> EntityChests;
    public Value<Boolean> Shulkers;
    private Timer timer;
    @EventHandler
    private Listener<EventPlayerUpdate> OnPlayerUpdate;
    
    public ChestStealerModule() {
        super("ChestStealer", new String[] { "Chest" }, "Steals the contents from chests", "NONE", 14376484, ModuleType.MISC);
        this.Mode = new Value<Modes>("Mode", new String[] { "M" }, "The mode for chest stealer", Modes.Steal);
        this.Delay = new Value<Float>("Delay", new String[] { "D" }, "Delay for each tick", 1.0f, 0.0f, 10.0f, 1.0f);
        this.DepositShulkers = new Value<Boolean>("DepositShulkers", new String[] { "S" }, "Only deposit shulkers", false);
        this.EntityChests = new Value<Boolean>("EntityChests", new String[] { "EC" }, "Take from entity chests", false);
        this.Shulkers = new Value<Boolean>("Shulkers", new String[] { "EC" }, "Take from shulkers", false);
        this.timer = new Timer();
        GuiChest l_Chest;
        int l_I;
        ItemStack l_Stack;
        GuiScreenHorseInventory l_Chest2;
        int l_I2;
        ItemStack l_Stack2;
        GuiShulkerBox l_Chest3;
        int l_I3;
        ItemStack l_Stack3;
        this.OnPlayerUpdate = new Listener<EventPlayerUpdate>(p_Event -> {
            if (!(!this.timer.passed(this.Delay.getValue() * 100.0f))) {
                this.timer.reset();
                if (this.mc.currentScreen instanceof GuiChest) {
                    l_Chest = (GuiChest)this.mc.currentScreen;
                    l_I = 0;
                    while (l_I < l_Chest.lowerChestInventory.getSizeInventory()) {
                        l_Stack = l_Chest.lowerChestInventory.getStackInSlot(l_I);
                        if ((l_Stack.isEmpty() || l_Stack.getItem() == Items.AIR) && this.Mode.getValue() == Modes.Store) {
                            this.HandleStoring(l_Chest.inventorySlots.windowId, l_Chest.lowerChestInventory.getSizeInventory() - 9);
                        }
                        else {
                            if (!this.Shulkers.getValue() || l_Stack.getItem() instanceof ItemShulkerBox) {
                                if (!l_Stack.isEmpty()) {
                                    switch (this.Mode.getValue()) {
                                        case Steal: {
                                            this.mc.playerController.windowClick(l_Chest.inventorySlots.windowId, l_I, 0, ClickType.QUICK_MOVE, (EntityPlayer)this.mc.player);
                                            return;
                                        }
                                        case Drop: {
                                            this.mc.playerController.windowClick(l_Chest.inventorySlots.windowId, l_I, -999, ClickType.THROW, (EntityPlayer)this.mc.player);
                                            return;
                                        }
                                    }
                                }
                            }
                            ++l_I;
                        }
                    }
                }
                else if (this.mc.currentScreen instanceof GuiScreenHorseInventory && this.EntityChests.getValue()) {
                    l_Chest2 = (GuiScreenHorseInventory)this.mc.currentScreen;
                    l_I2 = 0;
                    while (l_I2 < l_Chest2.horseInventory.getSizeInventory()) {
                        l_Stack2 = l_Chest2.horseInventory.getStackInSlot(l_I2);
                        if ((l_Stack2.isEmpty() || l_Stack2.getItem() == Items.AIR) && this.Mode.getValue() == Modes.Store) {
                            this.HandleStoring(l_Chest2.inventorySlots.windowId, l_Chest2.horseInventory.getSizeInventory() - 9);
                        }
                        else {
                            if (!this.Shulkers.getValue() || l_Stack2.getItem() instanceof ItemShulkerBox) {
                                if (!l_Stack2.isEmpty()) {
                                    switch (this.Mode.getValue()) {
                                        case Steal: {
                                            this.mc.playerController.windowClick(l_Chest2.inventorySlots.windowId, l_I2, 0, ClickType.QUICK_MOVE, (EntityPlayer)this.mc.player);
                                            return;
                                        }
                                        case Drop: {
                                            this.mc.playerController.windowClick(l_Chest2.inventorySlots.windowId, l_I2, -999, ClickType.THROW, (EntityPlayer)this.mc.player);
                                            return;
                                        }
                                    }
                                }
                            }
                            ++l_I2;
                        }
                    }
                }
                else if (this.mc.currentScreen instanceof GuiShulkerBox && this.Shulkers.getValue()) {
                    l_Chest3 = (GuiShulkerBox)this.mc.currentScreen;
                    l_I3 = 0;
                    while (l_I3 < l_Chest3.inventory.getSizeInventory()) {
                        l_Stack3 = l_Chest3.inventory.getStackInSlot(l_I3);
                        if ((l_Stack3.isEmpty() || l_Stack3.getItem() == Items.AIR) && this.Mode.getValue() == Modes.Store) {
                            this.HandleStoring(l_Chest3.inventorySlots.windowId, l_Chest3.inventory.getSizeInventory() - 9);
                        }
                        else {
                            if (!this.Shulkers.getValue() || l_Stack3.getItem() instanceof ItemShulkerBox) {
                                if (!l_Stack3.isEmpty()) {
                                    switch (this.Mode.getValue()) {
                                        case Steal: {
                                            this.mc.playerController.windowClick(l_Chest3.inventorySlots.windowId, l_I3, 0, ClickType.QUICK_MOVE, (EntityPlayer)this.mc.player);
                                            return;
                                        }
                                        case Drop: {
                                            this.mc.playerController.windowClick(l_Chest3.inventorySlots.windowId, l_I3, -999, ClickType.THROW, (EntityPlayer)this.mc.player);
                                            return;
                                        }
                                    }
                                }
                            }
                            ++l_I3;
                        }
                    }
                }
            }
        }, (Predicate<EventPlayerUpdate>[])new Predicate[0]);
    }
    
    @Override
    public String getMetaData() {
        return this.Mode.getValue().toString();
    }
    
    private void HandleStoring(final int p_WindowId, final int p_Slot) {
        if (this.Mode.getValue() == Modes.Store) {
            for (int l_Y = 9; l_Y < this.mc.player.inventoryContainer.inventorySlots.size() - 1; ++l_Y) {
                final ItemStack l_InvStack = this.mc.player.inventoryContainer.getSlot(l_Y).getStack();
                if (!l_InvStack.isEmpty()) {
                    if (l_InvStack.getItem() != Items.AIR) {
                        if (!this.Shulkers.getValue() || l_InvStack.getItem() instanceof ItemShulkerBox) {
                            this.mc.playerController.windowClick(p_WindowId, l_Y + p_Slot, 0, ClickType.QUICK_MOVE, (EntityPlayer)this.mc.player);
                            return;
                        }
                    }
                }
            }
        }
    }
    
    public enum Modes
    {
        Steal, 
        Store, 
        Drop;
    }
}
