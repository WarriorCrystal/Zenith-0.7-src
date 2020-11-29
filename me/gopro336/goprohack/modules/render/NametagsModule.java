//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.modules.render;

import me.gopro336.goprohack.util.render.GLUProjection;
import net.minecraft.util.math.Vec3d;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.item.Item;
import java.util.Iterator;
import me.gopro336.goprohack.friend.Friend;
import net.minecraft.enchantment.Enchantment;
import com.google.common.collect.Lists;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.GlStateManager;
import me.gopro336.goprohack.managers.FontManager;
import java.util.List;
import java.util.Collections;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import java.util.ArrayList;
import me.gopro336.goprohack.util.render.RenderUtil;
import com.mojang.realmsclient.gui.ChatFormatting;
import me.gopro336.goprohack.util.MathUtil;
import me.gopro336.goprohack.managers.FriendManager;
import net.minecraft.util.StringUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import java.util.function.Predicate;
import me.gopro336.goprohack.util.entity.EntityUtil;
import net.minecraft.client.renderer.culling.Frustum;
import me.gopro336.goprohack.events.render.EventRenderEntityName;
import me.zero.alpine.fork.listener.EventHandler;
import me.gopro336.goprohack.events.render.EventRenderGameOverlay;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.client.renderer.culling.ICamera;
import me.gopro336.goprohack.modules.Value;
import me.gopro336.goprohack.modules.Module;

public class NametagsModule extends Module
{
    public final Value<Boolean> Armor;
    public final Value<Boolean> Durability;
    public final Value<Boolean> ItemName;
    public final Value<Boolean> Health;
    public final Value<Boolean> Invisibles;
    public final Value<Boolean> EntityID;
    public final Value<Boolean> GameMode;
    public final Value<Boolean> Ping;
    private ICamera camera;
    @EventHandler
    private Listener<EventRenderGameOverlay> OnRenderGameOverlay;
    @EventHandler
    private Listener<EventRenderEntityName> OnRenderEntityName;
    
    public NametagsModule() {
        super("NameTags", new String[] { "Nametag" }, "Improves nametags of players around you", "NONE", -1, ModuleType.RENDER);
        this.Armor = new Value<Boolean>("Armor", new String[] { "" }, "", true);
        this.Durability = new Value<Boolean>("Durability", new String[] { "" }, "", true);
        this.ItemName = new Value<Boolean>("ItemName", new String[] { "" }, "", true);
        this.Health = new Value<Boolean>("Health", new String[] { "" }, "", true);
        this.Invisibles = new Value<Boolean>("Invisibles", new String[] { "" }, "", false);
        this.EntityID = new Value<Boolean>("EntityID", new String[] { "" }, "", false);
        this.GameMode = new Value<Boolean>("GameMode", new String[] { "" }, "", false);
        this.Ping = new Value<Boolean>("Ping", new String[] { "" }, "", true);
        this.camera = (ICamera)new Frustum();
        this.OnRenderGameOverlay = new Listener<EventRenderGameOverlay>(p_Event -> this.mc.world.loadedEntityList.stream().filter(EntityUtil::isLiving).filter(entity -> !EntityUtil.isFakeLocalPlayer(entity)).filter(entity -> entity instanceof EntityPlayer && this.mc.player != entity).forEach(e -> this.RenderNameTagFor(e, p_Event)), (Predicate<EventRenderGameOverlay>[])new Predicate[0]);
        this.OnRenderEntityName = new Listener<EventRenderEntityName>(p_Event -> p_Event.cancel(), (Predicate<EventRenderEntityName>[])new Predicate[0]);
    }
    
    private void RenderNameTagFor(final EntityPlayer e, final EventRenderGameOverlay p_Event) {
        final float[] bounds = this.convertBounds((Entity)e, p_Event.getPartialTicks(), p_Event.getScaledResolution().getScaledWidth(), p_Event.getScaledResolution().getScaledHeight());
        if (bounds != null) {
            String name = StringUtils.stripControlCodes(e.getName());
            int color = -1;
            final Friend friend = FriendManager.Get().GetFriend((Entity)e);
            if (friend != null) {
                name = friend.GetAlias();
                color = 50158;
            }
            final EntityPlayer player = e;
            int responseTime = -1;
            if (this.Ping.getValue()) {
                try {
                    responseTime = (int)MathUtil.clamp((float)this.mc.getConnection().getPlayerInfo(player.getUniqueID()).getResponseTime(), 0.0f, 300.0f);
                }
                catch (NullPointerException ex) {}
            }
            String l_Name = String.format("%s %sms %s", name, responseTime, ChatFormatting.GREEN + String.valueOf(Math.floor(e.getHealth() + e.getAbsorptionAmount())));
            RenderUtil.drawStringWithShadow(l_Name, bounds[0] + (bounds[2] - bounds[0]) / 2.0f - RenderUtil.getStringWidth(l_Name) / 2.0f, bounds[1] + (bounds[3] - bounds[1]) - 8.0f - 1.0f, color);
            if (this.Armor.getValue()) {
                final Iterator<ItemStack> items = e.getArmorInventoryList().iterator();
                final ArrayList<ItemStack> stacks = new ArrayList<ItemStack>();
                stacks.add(e.getHeldItemOffhand());
                while (items.hasNext()) {
                    final ItemStack stack = items.next();
                    if (stack != null && stack.getItem() != Items.AIR) {
                        stacks.add(stack);
                    }
                }
                stacks.add(e.getHeldItemMainhand());
                Collections.reverse(stacks);
                int x = 0;
                if (!e.getHeldItemMainhand().isEmpty() && e.getHeldItemMainhand().hasDisplayName()) {
                    l_Name = e.getHeldItemMainhand().getDisplayName();
                    FontManager.Get().FontRenderers[15].drawStringWithShadow(l_Name, bounds[0] + (bounds[2] - bounds[0]) / 2.0f - FontManager.Get().FontRenderers[15].getStringWidth(l_Name) / 2.0f, bounds[1] + (bounds[3] - bounds[1]) - this.mc.fontRenderer.FONT_HEIGHT - 35.0f, -1);
                }
                for (final ItemStack stack2 : stacks) {
                    if (stack2 != null) {
                        final Item item = stack2.getItem();
                        if (item == Items.AIR) {
                            continue;
                        }
                        GlStateManager.pushMatrix();
                        GlStateManager.enableBlend();
                        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
                        RenderHelper.enableGUIStandardItemLighting();
                        GlStateManager.translate(bounds[0] + (bounds[2] - bounds[0]) / 2.0f + x - 16 * stacks.size() / 2, bounds[1] + (bounds[3] - bounds[1]) - this.mc.fontRenderer.FONT_HEIGHT - 19.0f, 0.0f);
                        this.mc.getRenderItem().renderItemAndEffectIntoGUI(stack2, 0, 0);
                        this.mc.getRenderItem().renderItemOverlays(this.mc.fontRenderer, stack2, 0, 0);
                        RenderHelper.disableStandardItemLighting();
                        GlStateManager.disableBlend();
                        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
                        GlStateManager.popMatrix();
                        x += 16;
                        final List<String> stringsToDraw = (List<String>)Lists.newArrayList();
                        if (stack2.isItemDamaged()) {
                            final float l_ArmorPct = (stack2.getMaxDamage() - stack2.getItemDamage()) / (float)stack2.getMaxDamage() * 100.0f;
                            final float l_ArmorBarPct = Math.min(l_ArmorPct, 100.0f);
                            stringsToDraw.add(String.format("%s", (int)l_ArmorBarPct + "%"));
                        }
                        int y = 0;
                        if (stack2.getEnchantmentTagList() != null) {
                            final NBTTagList tags = stack2.getEnchantmentTagList();
                            for (int i = 0; i < tags.tagCount(); ++i) {
                                final NBTTagCompound tagCompound = tags.getCompoundTagAt(i);
                                if (tagCompound != null && Enchantment.getEnchantmentByID((int)tagCompound.getByte("id")) != null) {
                                    final Enchantment enchantment = Enchantment.getEnchantmentByID((int)tagCompound.getShort("id"));
                                    final short lvl = tagCompound.getShort("lvl");
                                    if (enchantment != null) {
                                        String ench = "";
                                        if (enchantment.isCurse()) {
                                            ench = ChatFormatting.RED + enchantment.getTranslatedName((int)lvl).substring(11).substring(0, 2) + ChatFormatting.GRAY + lvl;
                                        }
                                        else {
                                            ench = enchantment.getTranslatedName((int)lvl).substring(0, 2) + lvl;
                                        }
                                        stringsToDraw.add(ench);
                                    }
                                }
                            }
                        }
                        if (item == Items.GOLDEN_APPLE && stack2.getItemDamage() == 1) {
                            stringsToDraw.add(ChatFormatting.DARK_RED + "God");
                        }
                        for (final String string : stringsToDraw) {
                            GlStateManager.pushMatrix();
                            GlStateManager.disableDepth();
                            GlStateManager.translate(bounds[0] + (bounds[2] - bounds[0]) / 2.0f + x - 16.0f * stacks.size() / 2.0f - 8.0f - RenderUtil.getStringWidth(string) / 4.0f, bounds[1] + (bounds[3] - bounds[1]) - this.mc.fontRenderer.FONT_HEIGHT - 23.0f - y, 0.0f);
                            GlStateManager.scale(0.5f, 0.5f, 0.5f);
                            RenderUtil.drawStringWithShadow(string, 0.0f, 0.0f, -1);
                            GlStateManager.scale(2.0f, 2.0f, 2.0f);
                            GlStateManager.enableDepth();
                            GlStateManager.popMatrix();
                            y += 4;
                        }
                    }
                }
            }
        }
    }
    
    private float[] convertBounds(final Entity e, final float partialTicks, final int width, final int height) {
        float x = -1.0f;
        float y = -1.0f;
        float w = (float)(width + 1);
        float h = (float)(height + 1);
        final Vec3d pos = MathUtil.interpolateEntity(e, partialTicks);
        if (pos == null) {
            return null;
        }
        AxisAlignedBB bb = e.getEntityBoundingBox();
        if (e instanceof EntityEnderCrystal) {
            bb = new AxisAlignedBB(bb.minX + 0.30000001192092896, bb.minY + 0.20000000298023224, bb.minZ + 0.30000001192092896, bb.maxX - 0.30000001192092896, bb.maxY, bb.maxZ - 0.30000001192092896);
        }
        if (e instanceof EntityItem) {
            bb = new AxisAlignedBB(bb.minX, bb.minY + 0.699999988079071, bb.minZ, bb.maxX, bb.maxY, bb.maxZ);
        }
        bb = bb.expand(0.15000000596046448, 0.10000000149011612, 0.15000000596046448);
        this.camera.setPosition(Minecraft.getMinecraft().getRenderViewEntity().posX, Minecraft.getMinecraft().getRenderViewEntity().posY, Minecraft.getMinecraft().getRenderViewEntity().posZ);
        if (!this.camera.isBoundingBoxInFrustum(bb)) {
            return null;
        }
        final Vec3d[] array;
        final Vec3d[] corners = array = new Vec3d[] { new Vec3d(bb.minX - bb.maxX + e.width / 2.0f, 0.0, bb.minZ - bb.maxZ + e.width / 2.0f), new Vec3d(bb.maxX - bb.minX - e.width / 2.0f, 0.0, bb.minZ - bb.maxZ + e.width / 2.0f), new Vec3d(bb.minX - bb.maxX + e.width / 2.0f, 0.0, bb.maxZ - bb.minZ - e.width / 2.0f), new Vec3d(bb.maxX - bb.minX - e.width / 2.0f, 0.0, bb.maxZ - bb.minZ - e.width / 2.0f), new Vec3d(bb.minX - bb.maxX + e.width / 2.0f, bb.maxY - bb.minY, bb.minZ - bb.maxZ + e.width / 2.0f), new Vec3d(bb.maxX - bb.minX - e.width / 2.0f, bb.maxY - bb.minY, bb.minZ - bb.maxZ + e.width / 2.0f), new Vec3d(bb.minX - bb.maxX + e.width / 2.0f, bb.maxY - bb.minY, bb.maxZ - bb.minZ - e.width / 2.0f), new Vec3d(bb.maxX - bb.minX - e.width / 2.0f, bb.maxY - bb.minY, bb.maxZ - bb.minZ - e.width / 2.0f) };
        for (final Vec3d vec : array) {
            final GLUProjection.Projection projection = GLUProjection.getInstance().project(pos.x + vec.x - Minecraft.getMinecraft().getRenderManager().viewerPosX, pos.y + vec.y - Minecraft.getMinecraft().getRenderManager().viewerPosY, pos.z + vec.z - Minecraft.getMinecraft().getRenderManager().viewerPosZ, GLUProjection.ClampMode.NONE, false);
            if (projection == null) {
                return null;
            }
            x = Math.max(x, (float)projection.getX());
            y = Math.max(y, (float)projection.getY());
            w = Math.min(w, (float)projection.getX());
            h = Math.min(h, (float)projection.getY());
        }
        if (x != -1.0f && y != -1.0f && w != width + 1 && h != height + 1) {
            return new float[] { x, y, w, h };
        }
        return null;
    }
}
