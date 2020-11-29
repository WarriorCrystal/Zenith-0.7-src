//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.gui.hud.components;

import net.minecraft.nbt.NBTTagList;
import net.minecraft.item.Item;
import java.util.Iterator;
import java.util.function.Function;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.nbt.NBTTagCompound;
import com.google.common.collect.Lists;
import java.util.List;
import java.util.Collections;
import net.minecraft.init.Items;
import net.minecraft.entity.Entity;
import java.text.DecimalFormat;
import me.gopro336.goprohack.util.MathUtil;
import me.gopro336.goprohack.util.render.RenderUtil;
import net.minecraft.client.renderer.RenderHelper;
import java.util.Comparator;
import me.gopro336.goprohack.managers.FriendManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.GlStateManager;
import com.mojang.authlib.GameProfile;
import net.minecraft.item.ItemStack;
import java.util.ArrayList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.client.Minecraft;
import me.gopro336.goprohack.gui.hud.HudComponentItem;

public final class TooltipComponent extends HudComponentItem
{
    final Minecraft mc;
    private EntityPlayer m_LastPlayer;
    private ArrayList<ItemStack> HotbarGuesser;
    private int[] InternalThreatLevel;
    
    public TooltipComponent() {
        super("Tooltip", 700.0f, 600.0f);
        this.mc = Minecraft.getMinecraft();
        this.m_LastPlayer = null;
        this.HotbarGuesser = new ArrayList<ItemStack>();
        this.InternalThreatLevel = new int[4];
    }
    
    private void drawCharacter(final float posX, final float posY, final int size, final int cursorX, final int cursorY, final EntityPlayer p_Ent) {
        final GameProfile profile = new GameProfile(p_Ent.getUniqueID(), "");
        final float mouseX = posX - cursorX;
        final float mouseY = posY - size * 1.67f - cursorY;
        GlStateManager.disableRescaleNormal();
        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GlStateManager.disableTexture2D();
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
        GuiInventory.drawEntityOnScreen((int)posX, (int)posY, size, mouseX, mouseY, (EntityLivingBase)p_Ent);
        GlStateManager.enableRescaleNormal();
        GlStateManager.enableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
    }
    
    @Override
    public void render(final int mouseX, final int mouseY, final float partialTicks) {
        super.render(mouseX, mouseY, partialTicks);
        if (this.mc.player == null) {
            return;
        }
        EntityPlayer l_Entity = null;
        l_Entity = (EntityPlayer)this.mc.world.loadedEntityList.stream().filter(entity -> entity instanceof EntityPlayer && entity != this.mc.player && entity.getName() != this.mc.player.getName() && !FriendManager.Get().IsFriend(entity)).map(entity -> entity).min(Comparator.comparing(c -> this.mc.player.getDistance(c))).orElse(null);
        if (l_Entity == null) {
            return;
        }
        if (this.m_LastPlayer == null || l_Entity.getName() != this.m_LastPlayer.getName()) {
            this.m_LastPlayer = l_Entity;
            this.HotbarGuesser.clear();
        }
        this.SetWidth(260.0f);
        this.SetHeight(120.0f);
        this.InternalThreatLevel[0] = 2;
        this.InternalThreatLevel[1] = 2;
        this.InternalThreatLevel[2] = 2;
        this.InternalThreatLevel[3] = 2;
        GlStateManager.pushMatrix();
        RenderHelper.enableGUIStandardItemLighting();
        RenderUtil.drawRect(this.GetX(), this.GetY(), this.GetX() + this.GetWidth(), this.GetY() + this.GetHeight(), 1963986960);
        this.drawCharacter(this.GetX() + 25.0f, this.GetY() + 95.0f, 40, (int)this.GetX(), (int)this.GetY(), l_Entity);
        int responseTime = -1;
        try {
            responseTime = (int)MathUtil.clamp((float)this.mc.getConnection().getPlayerInfo(l_Entity.getUniqueID()).getResponseTime(), 0.0f, 300.0f);
        }
        catch (NullPointerException ex) {}
        RenderUtil.drawStringWithShadow(responseTime + "ms", this.GetX() + this.GetWidth() - (RenderUtil.getStringWidth(responseTime + "ms") + 5.0f), this.GetY() + 5.0f, 16777215);
        RenderUtil.drawStringWithShadow(l_Entity.getName(), this.GetX() + 52.0f, this.GetY() + 2.0f, 16777215);
        final DecimalFormat l_2D = new DecimalFormat("#.##");
        final double l_Distance = this.mc.player.getDistance((Entity)l_Entity);
        String l_Line1 = "Distance: " + l_2D.format(l_Distance);
        if (!l_Line1.contains(".")) {
            l_Line1 += ".00";
        }
        else {
            final String[] l_Split = l_Line1.split("\\.");
            if (l_Split != null && l_Split[1] != null && l_Split[1].length() != 2) {
                l_Line1 += 0;
            }
        }
        final float l_TheirHealth = l_Entity.getHealth() + l_Entity.getAbsorptionAmount();
        l_Line1 = l_Line1 + " | Health: " + l_2D.format(l_TheirHealth);
        RenderUtil.drawStringWithShadow(l_Line1, this.GetX() + 52.0f, this.GetY() + 15.0f, 16777215);
        final Iterator<ItemStack> items = l_Entity.getArmorInventoryList().iterator();
        final ArrayList<ItemStack> stacks = new ArrayList<ItemStack>();
        while (items.hasNext()) {
            final ItemStack stack = items.next();
            if (stack != null && stack.getItem() != Items.AIR) {
                stacks.add(stack);
            }
        }
        Collections.reverse(stacks);
        final int l_X = (int)(this.GetX() + 50.0f);
        int l_Y = (int)(this.GetY() + 10.0f);
        int l_TextY = (int)(this.GetY() + 35.0f);
        int l_Itr = 0;
        ThreatLevels l_ThreatLevel = ThreatLevels.None;
        for (final ItemStack stack2 : stacks) {
            if (stack2 == null) {
                continue;
            }
            final Item item = stack2.getItem();
            if (item != Items.AIR) {
                GlStateManager.pushMatrix();
                GlStateManager.enableBlend();
                GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
                RenderHelper.enableGUIStandardItemLighting();
                l_Y += 22;
                this.mc.getRenderItem().renderItemAndEffectIntoGUI(stack2, l_X, l_Y);
                this.mc.getRenderItem().renderItemOverlays(this.mc.fontRenderer, stack2, l_X, l_Y);
                RenderHelper.disableStandardItemLighting();
                GlStateManager.disableBlend();
                GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
                GlStateManager.popMatrix();
                final List<String> stringsToDraw = (List<String>)Lists.newArrayList();
                if (stack2.getEnchantmentTagList() != null) {
                    final NBTTagList tags = stack2.getEnchantmentTagList();
                    final ArrayList<NBTTagCompound> l_EnchantmentList = new ArrayList<NBTTagCompound>();
                    for (int i = 0; i < tags.tagCount(); ++i) {
                        final NBTTagCompound tagCompound = tags.getCompoundTagAt(i);
                        if (tagCompound != null && Enchantment.getEnchantmentByID((int)tagCompound.getByte("id")) != null) {
                            final Enchantment enchantment = Enchantment.getEnchantmentByID((int)tagCompound.getShort("id"));
                            final short lvl = tagCompound.getShort("lvl");
                            if (enchantment != null) {
                                String ench = "";
                                if (enchantment.isCurse()) {
                                    if (enchantment.getTranslatedName((int)lvl).contains("Vanish")) {
                                        ench = ChatFormatting.RED + "Vanishing";
                                    }
                                    else {
                                        ench = ChatFormatting.RED + "Binding";
                                    }
                                    stringsToDraw.add(ench);
                                }
                                else {
                                    ench = enchantment.getTranslatedName((int)lvl);
                                    stringsToDraw.add(ench);
                                }
                            }
                        }
                    }
                    this.CompareEnchantsToSelf(l_Itr, l_EnchantmentList);
                    float l_LastStringWidth = 0.0f;
                    int offsetY = 0;
                    for (int l_I = 0; l_I < stringsToDraw.size(); ++l_I) {
                        String string = stringsToDraw.get(l_I);
                        if (l_I != stringsToDraw.size() - 1) {
                            string += "   ";
                        }
                        if (l_I > 6) {
                            break;
                        }
                        final int offsetX = (int)(l_X + l_I % 2 * l_LastStringWidth + 18.0f);
                        offsetY = l_TextY + l_I / 2 * 8;
                        l_LastStringWidth = RenderUtil.getStringWidth(string);
                        RenderUtil.drawStringWithShadow(string, (float)offsetX, (float)offsetY, -1);
                    }
                    l_TextY = l_Y + 23;
                }
            }
            ++l_Itr;
        }
        if (l_Entity.getHeldItemMainhand() != null && l_Entity.getHeldItemMainhand().getItem() != Items.AIR) {
            final List<String> stringsToDraw2 = (List<String>)Lists.newArrayList();
            if (l_Entity.getHeldItemMainhand().getEnchantmentTagList() != null) {
                final NBTTagList tags2 = l_Entity.getHeldItemMainhand().getEnchantmentTagList();
                for (int j = 0; j < tags2.tagCount(); ++j) {
                    final NBTTagCompound tagCompound2 = tags2.getCompoundTagAt(j);
                    if (tagCompound2 != null && Enchantment.getEnchantmentByID((int)tagCompound2.getByte("id")) != null) {
                        final Enchantment enchantment2 = Enchantment.getEnchantmentByID((int)tagCompound2.getShort("id"));
                        final short lvl2 = tagCompound2.getShort("lvl");
                        if (enchantment2 != null) {
                            String ench2 = "";
                            if (enchantment2.isCurse()) {
                                if (enchantment2.getTranslatedName((int)lvl2).contains("Vanish")) {
                                    ench2 = ChatFormatting.RED + "Vanishing";
                                }
                                else {
                                    ench2 = ChatFormatting.RED + "Binding";
                                }
                            }
                            else {
                                ench2 = enchantment2.getTranslatedName((int)lvl2);
                            }
                            stringsToDraw2.add(ench2);
                        }
                    }
                }
            }
            if (!stringsToDraw2.isEmpty()) {
                stringsToDraw2.sort(Comparator.comparing((Function<? super Object, ? extends Comparable>)String::length).thenComparing(s -> !s.contains("Vanishing")));
            }
            String l_String = l_Entity.getHeldItemMainhand().getDisplayName();
            if (l_String.length() > 25) {
                l_String = l_String.substring(0, 25);
            }
            float l_X2 = this.GetX() + this.GetWidth() - (RenderUtil.getStringWidth(l_String) + 5.0f);
            float l_Y2 = this.GetY() + this.GetHeight() - 6 * stringsToDraw2.size() - 5.0f - RenderUtil.getStringHeight(l_String) - 16.0f;
            GlStateManager.pushMatrix();
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            RenderHelper.enableGUIStandardItemLighting();
            this.mc.getRenderItem().renderItemAndEffectIntoGUI(l_Entity.getHeldItemMainhand(), (int)l_X2 - 17, (int)l_Y2 - 5);
            this.mc.getRenderItem().renderItemOverlays(this.mc.fontRenderer, l_Entity.getHeldItemMainhand(), (int)l_X2 - 17, (int)l_Y2 - 5);
            RenderHelper.disableStandardItemLighting();
            GlStateManager.disableBlend();
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            GlStateManager.popMatrix();
            RenderUtil.drawStringWithShadow(ChatFormatting.LIGHT_PURPLE + l_String, l_X2, l_Y2, 16777215);
            int l_I2 = 0;
            for (final String s2 : stringsToDraw2) {
                if (l_I2 > 8) {
                    break;
                }
                l_X2 = this.GetX() + this.GetWidth() - (RenderUtil.getStringWidth(s2) + 5.0f);
                l_Y2 = this.GetY() + this.GetHeight() - 5.0f - RenderUtil.getStringHeight(s2) - 6 * l_I2++ - 16.0f;
                RenderUtil.drawStringWithShadow(s2, l_X2, l_Y2, 16777215);
            }
        }
        if (l_Entity.getHeldItemOffhand() != null && l_Entity.getHeldItemOffhand().getItem() != Items.AIR) {
            final List<String> stringsToDraw2 = (List<String>)Lists.newArrayList();
            String l_String = l_Entity.getHeldItemOffhand().getDisplayName();
            if (l_String.length() > 25) {
                l_String = l_String.substring(0, 25);
            }
            final float l_X2 = this.GetX() + this.GetWidth() - (RenderUtil.getStringWidth(l_String) + 5.0f);
            final float l_Y2 = this.GetY() + this.GetHeight() - 6 * stringsToDraw2.size() - 5.0f - RenderUtil.getStringHeight(l_String) - 3.0f;
            GlStateManager.pushMatrix();
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            RenderHelper.enableGUIStandardItemLighting();
            this.mc.getRenderItem().renderItemAndEffectIntoGUI(l_Entity.getHeldItemOffhand(), (int)l_X2 - 17, (int)l_Y2 - 5);
            this.mc.getRenderItem().renderItemOverlays(this.mc.fontRenderer, l_Entity.getHeldItemOffhand(), (int)l_X2 - 17, (int)l_Y2 - 5);
            RenderHelper.disableStandardItemLighting();
            GlStateManager.disableBlend();
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            GlStateManager.popMatrix();
            RenderUtil.drawStringWithShadow(ChatFormatting.LIGHT_PURPLE + l_String, l_X2, l_Y2, 16777215);
        }
        if (l_Entity.getHeldItemMainhand() != ItemStack.EMPTY) {
            ItemStack l_ItemToRemove = null;
            boolean l_Readd = true;
            for (final ItemStack l_PrevItem : this.HotbarGuesser) {
                if (l_PrevItem != ItemStack.EMPTY) {
                    if (l_PrevItem.getItem() == Items.AIR) {
                        continue;
                    }
                    if (l_PrevItem.getItem() != l_Entity.getHeldItemMainhand().getItem()) {
                        continue;
                    }
                    if (l_PrevItem.getCount() != l_Entity.getHeldItemMainhand().getCount()) {
                        l_ItemToRemove = l_PrevItem;
                        break;
                    }
                    l_Readd = false;
                    break;
                }
            }
            if (l_ItemToRemove != null) {
                this.HotbarGuesser.remove(l_ItemToRemove);
            }
            else if (l_Readd) {
                this.HotbarGuesser.add(l_Entity.getHeldItemMainhand());
            }
        }
        if (this.HotbarGuesser.size() > 9) {
            this.HotbarGuesser.remove(0);
        }
        int l_I3 = 0;
        for (final ItemStack itemStack : this.HotbarGuesser) {
            final int offsetX2 = (int)this.GetX() + 16 * l_I3++;
            final int offsetY2 = (int)(this.GetY() + this.GetHeight()) - 16;
            GlStateManager.pushMatrix();
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            RenderHelper.enableGUIStandardItemLighting();
            this.mc.getRenderItem().renderItemAndEffectIntoGUI(itemStack, offsetX2, offsetY2);
            this.mc.getRenderItem().renderItemOverlayIntoGUI(this.mc.fontRenderer, itemStack, offsetX2, offsetY2, (String)null);
            RenderHelper.disableStandardItemLighting();
            GlStateManager.disableBlend();
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            GlStateManager.popMatrix();
        }
        int l_NoThreatMatches = 0;
        int l_MatchedThreatMatches = 0;
        int l_HighThreatMatches = 0;
        for (int l_I4 = 0; l_I4 < 4; ++l_I4) {
            switch (this.InternalThreatLevel[l_I4]) {
                case 0: {
                    ++l_HighThreatMatches;
                    break;
                }
                case 1: {
                    ++l_MatchedThreatMatches;
                    break;
                }
                case 2: {
                    ++l_NoThreatMatches;
                    break;
                }
            }
        }
        if (l_HighThreatMatches > l_MatchedThreatMatches && l_HighThreatMatches > l_NoThreatMatches) {
            l_ThreatLevel = ThreatLevels.High;
        }
        else if ((l_MatchedThreatMatches > l_HighThreatMatches && l_MatchedThreatMatches > l_NoThreatMatches) || l_MatchedThreatMatches == l_HighThreatMatches) {
            l_ThreatLevel = ThreatLevels.Matched;
            final float l_OurHealth = this.mc.player.getHealth() + this.mc.player.getAbsorptionAmount();
            if (l_OurHealth >= l_TheirHealth) {
                l_ThreatLevel = ThreatLevels.Matched;
            }
        }
        else {
            l_ThreatLevel = ThreatLevels.None;
        }
        String l_String2 = "";
        switch (l_ThreatLevel) {
            case None: {
                l_String2 = ChatFormatting.GREEN + "Kill";
                break;
            }
            case Matched: {
                l_String2 = ChatFormatting.YELLOW + "Matched";
                break;
            }
            case High: {
                l_String2 = ChatFormatting.RED + "Threat";
                break;
            }
        }
        RenderUtil.drawStringWithShadow(l_String2, this.GetX() + 27.0f - RenderUtil.getStringWidth(l_String2), this.GetY() + 5.0f, 16777215);
        RenderHelper.disableStandardItemLighting();
        this.mc.getRenderItem().zLevel = 0.0f;
        GlStateManager.popMatrix();
    }
    
    private void CompareEnchantsToSelf(final int p_Itr, final ArrayList<NBTTagCompound> p_EnchantmentList) {
        final ItemStack l_Stack = (ItemStack)this.mc.player.inventory.armorInventory.get(p_Itr);
        if (l_Stack == ItemStack.EMPTY) {
            this.InternalThreatLevel[p_Itr] = 0;
            return;
        }
        if (l_Stack.getEnchantmentTagList() != null) {
            final NBTTagList tags = l_Stack.getEnchantmentTagList();
            for (int i = 0; i < tags.tagCount(); ++i) {
                final NBTTagCompound tagCompound = tags.getCompoundTagAt(i);
                if (tagCompound != null && Enchantment.getEnchantmentByID((int)tagCompound.getByte("id")) != null) {
                    final Enchantment enchantment = Enchantment.getEnchantmentByID((int)tagCompound.getShort("id"));
                    final short lvl = tagCompound.getShort("lvl");
                    if (enchantment != null) {
                        if (!enchantment.isCurse()) {
                            for (final NBTTagCompound l_EnemyTag : p_EnchantmentList) {
                                if (l_EnemyTag.getShort("id") == tagCompound.getShort("id")) {
                                    final short l_EnemyLevel = l_EnemyTag.getShort("lvl");
                                    if (l_EnemyLevel == lvl) {
                                        this.InternalThreatLevel[p_Itr] = 1;
                                        return;
                                    }
                                    if (lvl < l_EnemyLevel) {
                                        this.InternalThreatLevel[p_Itr] = 0;
                                        return;
                                    }
                                    this.InternalThreatLevel[p_Itr] = 2;
                                    return;
                                }
                            }
                        }
                    }
                }
            }
        }
        this.InternalThreatLevel[p_Itr] = 0;
    }
    
    private String ToRomainNumerals(final int p_Input) {
        switch (p_Input) {
            case 1: {
                return "I";
            }
            case 2: {
                return "II";
            }
            case 3: {
                return "III";
            }
            case 4: {
                return "IV";
            }
            case 5: {
                return "V";
            }
            default: {
                return "";
            }
        }
    }
    
    private enum ThreatLevels
    {
        None, 
        Matched, 
        High;
    }
}
