//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.modules.combat;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import java.util.function.Predicate;
import me.gopro336.goprohack.util.entity.PlayerUtil;
import me.gopro336.goprohack.gui.GoproGuiScreen;
import net.minecraft.client.gui.inventory.GuiInventory;
import me.zero.alpine.fork.listener.EventHandler;
import me.gopro336.goprohack.events.player.EventPlayerUpdate;
import me.zero.alpine.fork.listener.Listener;
import me.gopro336.goprohack.modules.Value;
import me.gopro336.goprohack.modules.Module;

public final class OffhandModule extends Module
{
    public final Value<Modes> Mode;
    public final Value<Float> ToggleHealth;
    public final Value<Boolean> HotbarFirst;
    @EventHandler
    private Listener<EventPlayerUpdate> OnPlayerUpdate;
    
    public OffhandModule() {
        super("Offhand", new String[] { "Totem" }, "Pauses AutoTotem and places something else in your offhand.", "NONE", 5131167, ModuleType.COMBAT);
        this.Mode = new Value<Modes>("Mode", new String[] { "Mode" }, "If you are above the required health for a totem, x will be used in offhand instead.", Modes.Gap);
        this.ToggleHealth = new Value<Float>("ToggleHealth", new String[] { "TH" }, "When you are below this value, this will disable the module.", 10.0f, 0.0f, 20.0f, 0.5f);
        this.HotbarFirst = new Value<Boolean>("HotbarFirst", new String[] { "Recursive" }, "Prioritizes your hotbar before inventory slots", false);
        this.OnPlayerUpdate = new Listener<EventPlayerUpdate>(p_Event -> {
            if (this.mc.currentScreen == null || this.mc.currentScreen instanceof GuiInventory || this.mc.currentScreen instanceof GoproGuiScreen) {
                if (PlayerUtil.GetHealthWithAbsorption() < this.ToggleHealth.getValue()) {
                    this.SendMessage("You are below the ToggleHealth requirement, toggling..");
                    this.toggle();
                }
                else {
                    this.SwitchOffHandIfNeed(this.Mode.getValue());
                }
            }
        }, (Predicate<EventPlayerUpdate>[])new Predicate[0]);
    }
    
    @Override
    public String getMetaData() {
        return String.valueOf(this.Mode.getValue());
    }
    
    private void SwitchOffHandIfNeed(final Modes p_Val) {
        final Item l_Item = this.GetItemFromModeVal(p_Val);
        if (this.mc.player.getHeldItemOffhand().getItem() != l_Item) {
            final int l_Slot = this.HotbarFirst.getValue() ? PlayerUtil.GetRecursiveItemSlot(l_Item) : PlayerUtil.GetItemSlot(l_Item);
            if (l_Slot != -1) {
                this.mc.playerController.windowClick(this.mc.player.inventoryContainer.windowId, l_Slot, 0, ClickType.PICKUP, (EntityPlayer)this.mc.player);
                this.mc.playerController.windowClick(this.mc.player.inventoryContainer.windowId, 45, 0, ClickType.PICKUP, (EntityPlayer)this.mc.player);
                this.mc.playerController.windowClick(this.mc.player.inventoryContainer.windowId, l_Slot, 0, ClickType.PICKUP, (EntityPlayer)this.mc.player);
                this.mc.playerController.updateController();
                this.SendMessage(ChatFormatting.LIGHT_PURPLE + "Offhand now has a " + this.GetItemNameFromModeVal(this.Mode.getValue()));
            }
        }
    }
    
    public Item GetItemFromModeVal(final Modes p_Val) {
        switch (p_Val) {
            case Crystal: {
                return Items.END_CRYSTAL;
            }
            case Gap: {
                return Items.GOLDEN_APPLE;
            }
            case Bow: {
                return (Item)Items.BOW;
            }
            default: {
                return Items.TOTEM_OF_UNDYING;
            }
        }
    }
    
    private String GetItemNameFromModeVal(final Modes p_Val) {
        switch (p_Val) {
            case Crystal: {
                return "End Crystal";
            }
            case Gap: {
                return "Gap";
            }
            case Bow: {
                return "Bow";
            }
            default: {
                return "Totem";
            }
        }
    }
    
    public enum Modes
    {
        Gap, 
        Crystal, 
        Bow;
    }
}
