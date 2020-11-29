//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.modules.misc;

import net.minecraft.item.ItemStack;
import java.util.function.Predicate;
import net.minecraft.item.ItemFood;
import net.minecraft.world.World;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.init.Items;
import me.gopro336.goprohack.util.entity.PlayerUtil;
import me.zero.alpine.fork.listener.EventHandler;
import me.gopro336.goprohack.events.player.EventPlayerUpdate;
import me.zero.alpine.fork.listener.Listener;
import me.gopro336.goprohack.modules.Value;
import me.gopro336.goprohack.modules.Module;

public class AutoEatModule extends Module
{
    public final Value<Float> HealthToEatAt;
    public final Value<Float> RequiredHunger;
    private boolean m_WasEating;
    @EventHandler
    private Listener<EventPlayerUpdate> OnPlayerUpdate;
    
    public AutoEatModule() {
        super("AutoGap", new String[] { "Gap" }, "Automatically eats a gap, depending on hunger, or health", "NONE", 5131167, ModuleType.COMBAT);
        this.HealthToEatAt = new Value<Float>("HealthToEatAt", new String[] { "Health" }, "Will eat gaps at required health", 15.0f, 0.0f, 36.0f, 3.0f);
        this.RequiredHunger = new Value<Float>("Hunger", new String[] { "Hunger" }, "Required hunger to eat", 18.0f, 0.0f, 20.0f, 1.0f);
        this.m_WasEating = false;
        final float l_Health;
        int l_I;
        boolean l_CanEat;
        int l_I2;
        ItemStack l_Stack;
        this.OnPlayerUpdate = new Listener<EventPlayerUpdate>(p_Event -> {
            l_Health = this.mc.player.getHealth() + this.mc.player.getAbsorptionAmount();
            if (this.HealthToEatAt.getValue() >= l_Health && !PlayerUtil.IsEating()) {
                if (this.mc.player.getHeldItemMainhand().getItem() != Items.GOLDEN_APPLE) {
                    l_I = 0;
                    while (l_I < 9) {
                        if (!this.mc.player.inventory.getStackInSlot(l_I).isEmpty() && this.mc.player.inventory.getStackInSlot(l_I).getItem() == Items.GOLDEN_APPLE) {
                            this.mc.player.inventory.currentItem = l_I;
                            this.mc.playerController.updateController();
                            break;
                        }
                        else {
                            ++l_I;
                        }
                    }
                    if (this.mc.currentScreen == null) {
                        this.mc.gameSettings.keyBindUseItem.pressed = true;
                    }
                    else {
                        this.mc.playerController.processRightClick((EntityPlayer)this.mc.player, (World)this.mc.world, EnumHand.MAIN_HAND);
                    }
                    this.m_WasEating = true;
                }
            }
            else {
                if (!PlayerUtil.IsEating() && this.RequiredHunger.getValue() >= this.mc.player.getFoodStats().getFoodLevel()) {
                    l_CanEat = false;
                    for (l_I2 = 0; l_I2 < 9; ++l_I2) {
                        l_Stack = this.mc.player.inventory.getStackInSlot(l_I2);
                        if (!this.mc.player.inventory.getStackInSlot(l_I2).isEmpty()) {
                            if (l_Stack.getItem() instanceof ItemFood) {
                                l_CanEat = true;
                                this.mc.player.inventory.currentItem = l_I2;
                                this.mc.playerController.updateController();
                                break;
                            }
                        }
                    }
                    if (l_CanEat) {
                        if (this.mc.currentScreen == null) {
                            this.mc.gameSettings.keyBindUseItem.pressed = true;
                        }
                        else {
                            this.mc.playerController.processRightClick((EntityPlayer)this.mc.player, (World)this.mc.world, EnumHand.MAIN_HAND);
                        }
                        this.m_WasEating = true;
                    }
                }
                if (this.m_WasEating) {
                    this.m_WasEating = false;
                    this.mc.gameSettings.keyBindUseItem.pressed = false;
                }
            }
        }, (Predicate<EventPlayerUpdate>[])new Predicate[0]);
    }
    
    @Override
    public void onDisable() {
        super.onDisable();
        if (this.m_WasEating) {
            this.m_WasEating = false;
            this.mc.gameSettings.keyBindUseItem.pressed = false;
        }
    }
}
