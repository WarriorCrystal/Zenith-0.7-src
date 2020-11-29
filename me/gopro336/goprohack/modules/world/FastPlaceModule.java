//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.modules.world;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;
import java.util.function.Predicate;
import net.minecraft.init.Items;
import net.minecraft.item.ItemExpBottle;
import me.zero.alpine.fork.listener.EventHandler;
import me.gopro336.goprohack.events.player.EventPlayerUpdate;
import me.zero.alpine.fork.listener.Listener;
import me.gopro336.goprohack.modules.Value;
import me.gopro336.goprohack.modules.Module;

public final class FastPlaceModule extends Module
{
    public final Value<Boolean> xp;
    public final Value<Boolean> Crystals;
    @EventHandler
    private Listener<EventPlayerUpdate> OnPlayerUpdate;
    
    public FastPlaceModule() {
        super("FastPlace", new String[] { "Fp" }, "Removes place delay", "NONE", 6740772, ModuleType.WORLD);
        this.xp = new Value<Boolean>("XP", new String[] { "EXP" }, "Only activate while holding XP bottles.", false);
        this.Crystals = new Value<Boolean>("Crystals", new String[] { "Cry" }, "Active only when using crystals", false);
        this.OnPlayerUpdate = new Listener<EventPlayerUpdate>(p_Event -> {
            if (this.xp.getValue()) {
                if (this.mc.player.getHeldItemMainhand().getItem() instanceof ItemExpBottle || this.mc.player.getHeldItemOffhand().getItem() instanceof ItemExpBottle) {
                    this.mc.rightClickDelayTimer = 0;
                }
            }
            else if (this.Crystals.getValue()) {
                if (this.mc.player.inventory.getCurrentItem().getItem() == Items.END_CRYSTAL || this.mc.player.getHeldItemOffhand().getItem() == Items.END_CRYSTAL) {
                    this.mc.rightClickDelayTimer = 0;
                }
            }
            else {
                this.mc.rightClickDelayTimer = 0;
            }
        }, (Predicate<EventPlayerUpdate>[])new Predicate[0]);
    }
    
    @Override
    public void onDisable() {
        super.onDisable();
        this.mc.rightClickDelayTimer = 6;
    }
    
    @Override
    public String getMetaData() {
        if (this.xp.getValue()) {
            return "EXP:" + this.getItemCount(Items.EXPERIENCE_BOTTLE);
        }
        return null;
    }
    
    private int getItemCount(final Item input) {
        if (this.mc.player == null) {
            return 0;
        }
        int items = 0;
        for (int i = 0; i < 45; ++i) {
            final ItemStack stack = this.mc.player.inventory.getStackInSlot(i);
            if (stack.getItem() == input) {
                items += stack.getCount();
            }
        }
        return items;
    }
}
