//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.gui.hud.components;

import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.monster.EntityPigZombie;
import me.gopro336.goprohack.util.entity.EntityUtil;
import me.gopro336.goprohack.managers.FriendManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.GlStateManager;
import java.text.DecimalFormat;
import java.util.Comparator;
import net.minecraft.entity.EntityLivingBase;
import me.gopro336.goprohack.util.render.RenderUtil;
import me.gopro336.goprohack.modules.Value;
import me.gopro336.goprohack.gui.hud.HudComponentItem;

public class NearestEntityFrameComponent extends HudComponentItem
{
    public final Value<Boolean> Players;
    public final Value<Boolean> Friends;
    public final Value<Boolean> Mobs;
    public final Value<Boolean> Animals;
    
    public NearestEntityFrameComponent() {
        super("NearestEntityHUD", 400.0f, 2.0f);
        this.Players = new Value<Boolean>("Players", new String[] { "P" }, "Displays players", true);
        this.Friends = new Value<Boolean>("Friends", new String[] { "F" }, "Displays Friends", false);
        this.Mobs = new Value<Boolean>("Mobs", new String[] { "M" }, "Displays Mobs", true);
        this.Animals = new Value<Boolean>("Animals", new String[] { "A" }, "Displays Animals", true);
    }
    
    @Override
    public void render(final int p_MouseX, final int p_MouseY, final float p_PartialTicks) {
        super.render(p_MouseX, p_MouseY, p_PartialTicks);
        RenderUtil.drawRect(this.GetX(), this.GetY(), this.GetX() + this.GetWidth(), this.GetY() + this.GetHeight(), -1727263732);
        final EntityLivingBase l_Entity = (EntityLivingBase)this.mc.world.loadedEntityList.stream().filter(entity -> this.IsValidEntity(entity)).map(entity -> entity).min(Comparator.comparing(c -> this.mc.player.getDistance(c))).orElse(null);
        if (l_Entity == null) {
            return;
        }
        final float l_HealthPct = (l_Entity.getHealth() + l_Entity.getAbsorptionAmount()) / l_Entity.getMaxHealth() * 100.0f;
        final float l_HealthBarPct = Math.min(l_HealthPct, 100.0f);
        final DecimalFormat l_Format = new DecimalFormat("#.#");
        GlStateManager.disableRescaleNormal();
        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GlStateManager.disableTexture2D();
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
        GuiInventory.drawEntityOnScreen((int)this.GetX() + 10, (int)this.GetY() + 30, 15, (float)p_MouseX, (float)p_MouseY, l_Entity);
        GlStateManager.enableRescaleNormal();
        GlStateManager.enableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        RenderUtil.drawStringWithShadow(l_Entity.getName(), this.GetX() + 20.0f, this.GetY() + 1.0f, 16777215);
        RenderUtil.drawGradientRect(this.GetX() + 20.0f, this.GetY() + 11.0f, this.GetX() + 20.0f + l_HealthBarPct, this.GetY() + 22.0f, -1717570715, -1726742784);
        RenderUtil.drawStringWithShadow(String.format("(%s) %s / %s", l_Format.format(l_HealthPct) + "%", l_Format.format(l_Entity.getHealth() + l_Entity.getAbsorptionAmount()), l_Format.format(l_Entity.getMaxHealth())), this.GetX() + 20.0f, this.GetY() + 11.0f, 16777215);
        this.SetWidth(120.0f);
        this.SetHeight(33.0f);
    }
    
    private boolean IsValidEntity(final Entity p_Entity) {
        if (!(p_Entity instanceof EntityLivingBase)) {
            return false;
        }
        if (p_Entity instanceof EntityPlayer) {
            if (p_Entity == this.mc.player) {
                return false;
            }
            if (!this.Players.getValue()) {
                return false;
            }
            if (FriendManager.Get().IsFriend(p_Entity) && !this.Friends.getValue()) {
                return false;
            }
        }
        return (!EntityUtil.isHostileMob(p_Entity) || this.Mobs.getValue()) && (!(p_Entity instanceof EntityPigZombie) || this.Mobs.getValue()) && (!(p_Entity instanceof EntityAnimal) || this.Animals.getValue());
    }
}
