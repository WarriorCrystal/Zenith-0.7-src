//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.modules.combat;

import me.gopro336.goprohack.managers.ModuleManager;
import net.minecraft.item.Item;
import me.gopro336.goprohack.main.GoproHack;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.init.Items;
import java.util.function.Predicate;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemSword;
import me.gopro336.goprohack.util.entity.PlayerUtil;
import me.gopro336.goprohack.gui.GoproGuiScreen;
import net.minecraft.client.gui.inventory.GuiInventory;
import me.zero.alpine.fork.listener.EventHandler;
import me.gopro336.goprohack.events.player.EventPlayerUpdate;
import me.zero.alpine.fork.listener.Listener;
import me.gopro336.goprohack.modules.Value;
import me.gopro336.goprohack.modules.Module;

public final class AutoTotemModule extends Module
{
    public final Value<Float> health;
    public final Value<AutoTotemMode> Mode;
    public final Value<AutoTotemMode> FallbackMode;
    public final Value<Float> FallDistance;
    public final Value<Boolean> TotemOnElytra;
    public final Value<Boolean> OffhandGapOnSword;
    public final Value<Boolean> OffhandStrNoStrSword;
    public final Value<Boolean> HotbarFirst;
    private OffhandModule OffhandMod;
    @EventHandler
    private Listener<EventPlayerUpdate> OnPlayerUpdate;
    
    public AutoTotemModule() {
        super("AutoTotem", new String[] { "Totem" }, "Automatically places a totem of undying in your offhand", "NONE", 5131167, ModuleType.COMBAT);
        this.health = new Value<Float>("Health", new String[] { "Hp" }, "The amount of health needed to acquire a totem.", 16.0f, 0.0f, 20.0f, 0.5f);
        this.Mode = new Value<AutoTotemMode>("Mode", new String[] { "Mode" }, "If you are above the required health for a totem, x will be used in offhand instead.", AutoTotemMode.Totem);
        this.FallbackMode = new Value<AutoTotemMode>("Fallback", new String[] { "FallbackMode" }, "If you don't have the required item for mode, this will be the fallback.", AutoTotemMode.Crystal);
        this.FallDistance = new Value<Float>("FallDistance", new String[] { "Fall" }, "If your fall distance exceeds this value, use a totem", 15.0f, 0.0f, 100.0f, 10.0f);
        this.TotemOnElytra = new Value<Boolean>("TotemOnElytra", new String[] { "Elytra" }, "Will automatically switch to a totem if you're elytra flying", true);
        this.OffhandGapOnSword = new Value<Boolean>("SwordGap", new String[] { "SwordGap" }, "Will override all else, and try and use a gap in offhand when using a sword in main hand", false);
        this.OffhandStrNoStrSword = new Value<Boolean>("StrGap", new String[] { "Strength" }, "Will put a potion if offhand if you don't have strength and wearing a sword", false);
        this.HotbarFirst = new Value<Boolean>("HotbarFirst", new String[] { "Recursive" }, "Prioritizes your hotbar before inventory slots", false);
        this.OffhandMod = null;
        this.OnPlayerUpdate = new Listener<EventPlayerUpdate>(p_Event -> {
            if ((this.mc.currentScreen == null || this.mc.currentScreen instanceof GuiInventory || this.mc.currentScreen instanceof GoproGuiScreen) && !this.OffhandMod.isEnabled()) {
                if (!this.mc.player.getHeldItemMainhand().isEmpty()) {
                    if (this.health.getValue() <= PlayerUtil.GetHealthWithAbsorption() && this.mc.player.getHeldItemMainhand().getItem() instanceof ItemSword && this.OffhandStrNoStrSword.getValue() && !this.mc.player.isPotionActive(MobEffects.STRENGTH)) {
                        this.SwitchOffHandIfNeed(AutoTotemMode.Strength);
                        return;
                    }
                    else if (this.health.getValue() <= PlayerUtil.GetHealthWithAbsorption() && this.mc.player.getHeldItemMainhand().getItem() instanceof ItemSword && this.OffhandGapOnSword.getValue()) {
                        this.SwitchOffHandIfNeed(AutoTotemMode.Gap);
                        return;
                    }
                }
                if (this.health.getValue() > PlayerUtil.GetHealthWithAbsorption() || this.Mode.getValue() == AutoTotemMode.Totem || (this.TotemOnElytra.getValue() && this.mc.player.isElytraFlying()) || (this.mc.player.fallDistance >= this.FallDistance.getValue() && !this.mc.player.isElytraFlying())) {
                    this.SwitchOffHandIfNeed(AutoTotemMode.Totem);
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
    
    private void SwitchOffHandIfNeed(final AutoTotemMode p_Val) {
        final Item l_Item = this.GetItemFromModeVal(p_Val);
        if (this.mc.player.getHeldItemOffhand().getItem() != l_Item) {
            int l_Slot = this.HotbarFirst.getValue() ? PlayerUtil.GetRecursiveItemSlot(l_Item) : PlayerUtil.GetItemSlot(l_Item);
            Item l_Fallback = this.GetItemFromModeVal(this.FallbackMode.getValue());
            String l_Display = this.GetItemNameFromModeVal(p_Val);
            if (l_Slot == -1 && l_Item != l_Fallback && this.mc.player.getHeldItemOffhand().getItem() != l_Fallback) {
                l_Slot = PlayerUtil.GetRecursiveItemSlot(l_Fallback);
                l_Display = this.GetItemNameFromModeVal(this.FallbackMode.getValue());
                if (l_Slot == -1 && l_Fallback != Items.TOTEM_OF_UNDYING) {
                    l_Fallback = Items.TOTEM_OF_UNDYING;
                    if (l_Item != l_Fallback && this.mc.player.getHeldItemOffhand().getItem() != l_Fallback) {
                        l_Slot = PlayerUtil.GetRecursiveItemSlot(l_Fallback);
                        l_Display = "Emergency Totem";
                    }
                }
            }
            if (l_Slot != -1) {
                this.mc.playerController.windowClick(this.mc.player.inventoryContainer.windowId, l_Slot, 0, ClickType.PICKUP, (EntityPlayer)this.mc.player);
                this.mc.playerController.windowClick(this.mc.player.inventoryContainer.windowId, 45, 0, ClickType.PICKUP, (EntityPlayer)this.mc.player);
                this.mc.playerController.windowClick(this.mc.player.inventoryContainer.windowId, l_Slot, 0, ClickType.PICKUP, (EntityPlayer)this.mc.player);
                this.mc.playerController.updateController();
                GoproHack.SendMessage(ChatFormatting.YELLOW + "[AutoTotem] " + ChatFormatting.LIGHT_PURPLE + "Offhand now has a " + l_Display);
            }
        }
    }
    
    @Override
    public void onEnable() {
        super.onEnable();
        this.OffhandMod = (OffhandModule)ModuleManager.Get().GetMod(OffhandModule.class);
    }
    
    public Item GetItemFromModeVal(final AutoTotemMode p_Val) {
        switch (p_Val) {
            case Crystal: {
                return Items.END_CRYSTAL;
            }
            case Gap: {
                return Items.GOLDEN_APPLE;
            }
            case Pearl: {
                return Items.ENDER_PEARL;
            }
            case Chorus: {
                return Items.CHORUS_FRUIT;
            }
            case Strength: {
                return (Item)Items.POTIONITEM;
            }
            default: {
                return Items.TOTEM_OF_UNDYING;
            }
        }
    }
    
    private String GetItemNameFromModeVal(final AutoTotemMode p_Val) {
        switch (p_Val) {
            case Crystal: {
                return "End Crystal";
            }
            case Gap: {
                return "Gap";
            }
            case Pearl: {
                return "Pearl";
            }
            case Chorus: {
                return "Chorus";
            }
            case Strength: {
                return "Strength";
            }
            default: {
                return "Totem";
            }
        }
    }
    
    public enum AutoTotemMode
    {
        Totem, 
        Gap, 
        Crystal, 
        Pearl, 
        Chorus, 
        Strength;
    }
}
