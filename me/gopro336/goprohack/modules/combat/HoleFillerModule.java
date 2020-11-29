//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.modules.combat;

import net.minecraft.client.renderer.RenderGlobal;
import me.gopro336.goprohack.util.Hole;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemBlock;
import net.minecraft.block.Block;
import java.util.Iterator;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.math.AxisAlignedBB;
import java.util.function.Predicate;
import me.gopro336.goprohack.util.entity.PlayerUtil;
import net.minecraft.util.math.Vec3d;
import net.minecraft.init.Blocks;
import me.gopro336.goprohack.util.BlockInteractionHelper;
import java.util.Collection;
import me.gopro336.goprohack.events.MinecraftEvent;
import net.minecraft.client.renderer.culling.Frustum;
import me.gopro336.goprohack.events.render.RenderEvent;
import me.zero.alpine.fork.listener.EventHandler;
import me.gopro336.goprohack.events.player.EventPlayerMotionUpdate;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.util.math.BlockPos;
import java.util.ArrayList;
import net.minecraft.client.renderer.culling.ICamera;
import me.gopro336.goprohack.modules.Value;
import me.gopro336.goprohack.modules.Module;

public class HoleFillerModule extends Module
{
    public final Value<Boolean> TogglesOff;
    public final Value<Integer> MaxHoles;
    public final Value<Float> Radius;
    public final Value<Boolean> Render;
    public final Value<Float> ObsidianRed;
    public final Value<Float> ObsidianGreen;
    public final Value<Float> ObsidianBlue;
    public final Value<Float> ObsidianAlpha;
    public final Value<HoleModes> HoleMode;
    private ICamera camera;
    private ArrayList<BlockPos> HolesToFill;
    @EventHandler
    private Listener<EventPlayerMotionUpdate> OnPlayerUpdate;
    @EventHandler
    private Listener<RenderEvent> OnRenderEvent;
    
    public HoleFillerModule() {
        super("HoleFiller", new String[] { "HoleFill" }, "Automatically fills up to x holes around you when enabled, if togglesoff is not enabled, it will continue to fill holes.", "NONE", -1, ModuleType.COMBAT);
        this.TogglesOff = new Value<Boolean>("TogglesOff", new String[] { "TogglesOff" }, "Toggles Off after filling all the holes around you", true);
        this.MaxHoles = new Value<Integer>("MaxHoles", new String[] { "MaxHoles" }, "Maximum number of holes to fill", 5, 1, 20, 1);
        this.Radius = new Value<Float>("Radius", new String[] { "Range" }, "Range to search for holes", 5.0f, 1.0f, 10.0f, 1.0f);
        this.Render = new Value<Boolean>("Visualize", new String[] { "Visualize" }, "Visualizes the holes that we are attempting to fill", true);
        this.ObsidianRed = new Value<Float>("ObsidianRed", new String[] { "oRed" }, "Red for rendering", 0.0f, 0.0f, 1.0f, 0.1f);
        this.ObsidianGreen = new Value<Float>("ObsidianGreen", new String[] { "oGreen" }, "Green for rendering", 1.0f, 0.0f, 1.0f, 0.1f);
        this.ObsidianBlue = new Value<Float>("ObsidianBlue", new String[] { "oBlue" }, "Blue for rendering", 0.0f, 0.0f, 1.0f, 0.1f);
        this.ObsidianAlpha = new Value<Float>("ObsidianAlpha", new String[] { "oAlpha" }, "Alpha for rendering", 0.5f, 0.0f, 1.0f, 0.1f);
        this.HoleMode = new Value<HoleModes>("HoleModed", new String[] { "HM" }, "Mode for rendering holes", HoleModes.Full);
        this.camera = (ICamera)new Frustum();
        this.HolesToFill = new ArrayList<BlockPos>();
        BlockPos l_PosToFill;
        final Iterator<BlockPos> iterator;
        BlockPos l_Pos;
        BlockInteractionHelper.ValidResult l_Result;
        int slot;
        int lastSlot;
        float[] rotations;
        this.OnPlayerUpdate = new Listener<EventPlayerMotionUpdate>(p_Event -> {
            if (p_Event.getEra() != MinecraftEvent.Era.PRE) {
                return;
            }
            else {
                if (this.HolesToFill.isEmpty()) {
                    if (this.TogglesOff.getValue()) {
                        this.SendMessage("We are finished hole filling. toggling");
                        this.toggle();
                        return;
                    }
                    else {
                        this.FindNewHoles();
                    }
                }
                l_PosToFill = null;
                new ArrayList<BlockPos>(this.HolesToFill).iterator();
                while (iterator.hasNext()) {
                    l_Pos = iterator.next();
                    if (l_Pos == null) {
                        continue;
                    }
                    else {
                        l_Result = BlockInteractionHelper.valid(l_Pos);
                        if (l_Result != BlockInteractionHelper.ValidResult.Ok) {
                            this.HolesToFill.remove(l_Pos);
                        }
                        else {
                            l_PosToFill = l_Pos;
                            break;
                        }
                    }
                }
                slot = this.findStackHotbar(Blocks.OBSIDIAN);
                if (l_PosToFill != null && slot != -1) {
                    lastSlot = this.mc.player.inventory.currentItem;
                    this.mc.player.inventory.currentItem = slot;
                    this.mc.playerController.updateController();
                    p_Event.cancel();
                    rotations = BlockInteractionHelper.getLegitRotations(new Vec3d((double)l_PosToFill.getX(), (double)l_PosToFill.getY(), (double)l_PosToFill.getZ()));
                    PlayerUtil.PacketFacePitchAndYaw(rotations[1], rotations[0]);
                    if (BlockInteractionHelper.place(l_PosToFill, this.Radius.getValue(), false, false) == BlockInteractionHelper.PlaceResult.Placed) {
                        this.HolesToFill.remove(l_PosToFill);
                    }
                    this.Finish(lastSlot);
                }
                return;
            }
        }, (Predicate<EventPlayerMotionUpdate>[])new Predicate[0]);
        final Iterator<BlockPos> iterator2;
        BlockPos l_Pos2;
        AxisAlignedBB bb;
        this.OnRenderEvent = new Listener<RenderEvent>(p_Event -> {
            if (this.mc.getRenderManager() != null && this.Render.getValue()) {
                new ArrayList<BlockPos>(this.HolesToFill).iterator();
                while (iterator2.hasNext()) {
                    l_Pos2 = iterator2.next();
                    if (l_Pos2 == null) {
                        continue;
                    }
                    else {
                        bb = new AxisAlignedBB(l_Pos2.getX() - this.mc.getRenderManager().viewerPosX, l_Pos2.getY() - this.mc.getRenderManager().viewerPosY, l_Pos2.getZ() - this.mc.getRenderManager().viewerPosZ, l_Pos2.getX() + 1 - this.mc.getRenderManager().viewerPosX, l_Pos2.getY() + 1 - this.mc.getRenderManager().viewerPosY, l_Pos2.getZ() + 1 - this.mc.getRenderManager().viewerPosZ);
                        this.camera.setPosition(this.mc.getRenderViewEntity().posX, this.mc.getRenderViewEntity().posY, this.mc.getRenderViewEntity().posZ);
                        if (this.camera.isBoundingBoxInFrustum(new AxisAlignedBB(bb.minX + this.mc.getRenderManager().viewerPosX, bb.minY + this.mc.getRenderManager().viewerPosY, bb.minZ + this.mc.getRenderManager().viewerPosZ, bb.maxX + this.mc.getRenderManager().viewerPosX, bb.maxY + this.mc.getRenderManager().viewerPosY, bb.maxZ + this.mc.getRenderManager().viewerPosZ))) {
                            GlStateManager.pushMatrix();
                            GlStateManager.enableBlend();
                            GlStateManager.disableDepth();
                            GlStateManager.tryBlendFuncSeparate(770, 771, 0, 1);
                            GlStateManager.disableTexture2D();
                            GlStateManager.depthMask(false);
                            GL11.glEnable(2848);
                            GL11.glHint(3154, 4354);
                            GL11.glLineWidth(1.5f);
                            this.Render(bb, this.ObsidianRed.getValue(), this.ObsidianGreen.getValue(), this.ObsidianBlue.getValue(), this.ObsidianAlpha.getValue());
                            GL11.glDisable(2848);
                            GlStateManager.depthMask(true);
                            GlStateManager.enableDepth();
                            GlStateManager.enableTexture2D();
                            GlStateManager.disableBlend();
                            GlStateManager.popMatrix();
                        }
                        else {
                            continue;
                        }
                    }
                }
            }
        }, (Predicate<RenderEvent>[])new Predicate[0]);
    }
    
    @Override
    public void toggleNoSave() {
    }
    
    @Override
    public String getMetaData() {
        return String.valueOf(this.HolesToFill.size());
    }
    
    @Override
    public void onEnable() {
        super.onEnable();
        this.FindNewHoles();
    }
    
    public boolean IsProcessing() {
        return !this.HolesToFill.isEmpty();
    }
    
    private void Finish(final int p_LastSlot) {
        if (!this.slotEqualsBlock(p_LastSlot, Blocks.OBSIDIAN)) {
            this.mc.player.inventory.currentItem = p_LastSlot;
        }
        this.mc.playerController.updateController();
    }
    
    public boolean hasStack(final Block type) {
        if (this.mc.player.inventory.getCurrentItem().getItem() instanceof ItemBlock) {
            final ItemBlock block = (ItemBlock)this.mc.player.inventory.getCurrentItem().getItem();
            return block.getBlock() == type;
        }
        return false;
    }
    
    private boolean slotEqualsBlock(final int slot, final Block type) {
        if (this.mc.player.inventory.getStackInSlot(slot).getItem() instanceof ItemBlock) {
            final ItemBlock block = (ItemBlock)this.mc.player.inventory.getStackInSlot(slot).getItem();
            return block.getBlock() == type;
        }
        return false;
    }
    
    private int findStackHotbar(final Block type) {
        for (int i = 0; i < 9; ++i) {
            final ItemStack stack = Minecraft.getMinecraft().player.inventory.getStackInSlot(i);
            if (stack.getItem() instanceof ItemBlock) {
                final ItemBlock block = (ItemBlock)stack.getItem();
                if (block.getBlock() == type) {
                    return i;
                }
            }
        }
        return -1;
    }
    
    private Hole.HoleTypes isBlockValid(final IBlockState blockState, final BlockPos blockPos) {
        if (blockState.getBlock() != Blocks.AIR) {
            return Hole.HoleTypes.None;
        }
        if (this.mc.world.getBlockState(blockPos.up()).getBlock() != Blocks.AIR) {
            return Hole.HoleTypes.None;
        }
        if (this.mc.world.getBlockState(blockPos.up(2)).getBlock() != Blocks.AIR) {
            return Hole.HoleTypes.None;
        }
        if (this.mc.world.getBlockState(blockPos.down()).getBlock() == Blocks.AIR) {
            return Hole.HoleTypes.None;
        }
        final BlockPos[] touchingBlocks = { blockPos.north(), blockPos.south(), blockPos.east(), blockPos.west() };
        boolean l_Bedrock = true;
        boolean l_Obsidian = true;
        int validHorizontalBlocks = 0;
        for (final BlockPos touching : touchingBlocks) {
            final IBlockState touchingState = this.mc.world.getBlockState(touching);
            if (touchingState.getBlock() != Blocks.AIR && touchingState.isFullBlock()) {
                ++validHorizontalBlocks;
                if (touchingState.getBlock() != Blocks.BEDROCK && l_Bedrock) {
                    l_Bedrock = false;
                }
                if (!l_Bedrock && touchingState.getBlock() != Blocks.OBSIDIAN && touchingState.getBlock() != Blocks.BEDROCK) {
                    l_Obsidian = false;
                }
            }
        }
        if (validHorizontalBlocks < 4) {
            return Hole.HoleTypes.None;
        }
        if (l_Bedrock) {
            return Hole.HoleTypes.Bedrock;
        }
        if (l_Obsidian) {
            return Hole.HoleTypes.Obsidian;
        }
        return Hole.HoleTypes.Normal;
    }
    
    public void FindNewHoles() {
        this.HolesToFill.clear();
        final float l_Radius = this.Radius.getValue();
        int l_Holes = 0;
        for (final BlockPos l_Pos : BlockInteractionHelper.getSphere(PlayerUtil.GetLocalPlayerPosFloored(), l_Radius, (int)l_Radius, false, true, 0)) {
            final Hole.HoleTypes l_Type = this.isBlockValid(this.mc.world.getBlockState(l_Pos), l_Pos);
            switch (l_Type) {
                case None: {
                    continue;
                }
                case Normal:
                case Obsidian:
                case Bedrock: {
                    this.HolesToFill.add(l_Pos);
                    if (++l_Holes >= this.MaxHoles.getValue()) {}
                    break;
                }
            }
        }
        this.VerifyHoles();
    }
    
    private void VerifyHoles() {
        for (final BlockPos l_Pos : new ArrayList<BlockPos>(this.HolesToFill)) {
            final BlockInteractionHelper.ValidResult l_Result = BlockInteractionHelper.valid(l_Pos);
            if (l_Result != BlockInteractionHelper.ValidResult.Ok) {
                this.HolesToFill.remove(l_Pos);
            }
        }
    }
    
    private void Render(final AxisAlignedBB bb, final float p_Red, final float p_Green, final float p_Blue, final float p_Alpha) {
        switch (this.HoleMode.getValue()) {
            case Flat: {
                RenderGlobal.renderFilledBox(bb.minX, bb.minY, bb.minZ, bb.maxX, bb.minY, bb.maxZ, p_Red, p_Green, p_Blue, p_Alpha);
                break;
            }
            case FlatOutline: {
                RenderGlobal.drawBoundingBox(bb.minX, bb.minY, bb.minZ, bb.maxX, bb.minY, bb.maxZ, p_Red, p_Green, p_Blue, p_Alpha);
                break;
            }
            case Full: {
                RenderGlobal.drawBoundingBox(bb.minX, bb.minY, bb.minZ, bb.maxX, bb.maxY, bb.maxZ, p_Red, p_Green, p_Blue, p_Alpha);
                RenderGlobal.renderFilledBox(bb.minX, bb.minY, bb.minZ, bb.maxX, bb.maxY, bb.maxZ, p_Red, p_Green, p_Blue, p_Alpha);
                break;
            }
            case Outline: {
                RenderGlobal.drawBoundingBox(bb.minX, bb.minY, bb.minZ, bb.maxX, bb.maxY, bb.maxZ, p_Red, p_Green, p_Blue, p_Alpha);
                break;
            }
        }
    }
    
    private enum HoleModes
    {
        FlatOutline, 
        Flat, 
        Outline, 
        Full;
    }
}
