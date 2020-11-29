//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.modules.render;

import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.util.math.BlockPos;
import java.util.function.Predicate;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.client.renderer.culling.Frustum;
import me.zero.alpine.fork.listener.EventHandler;
import me.gopro336.goprohack.events.render.RenderEvent;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.client.renderer.culling.ICamera;
import me.gopro336.goprohack.modules.Value;
import me.gopro336.goprohack.modules.Module;

public class BlockHighlightModule extends Module
{
    public final Value<HoleModes> HighlightMode;
    public final Value<Float> ObsidianRed;
    public final Value<Float> ObsidianGreen;
    public final Value<Float> ObsidianBlue;
    public final Value<Float> ObsidianAlpha;
    private ICamera camera;
    @EventHandler
    private Listener<RenderEvent> OnRenderEvent;
    
    public BlockHighlightModule() {
        super("BlockHighlight", new String[] { "BlockHighlights" }, "Highlights the block you are looking at", "NONE", -1, ModuleType.RENDER);
        this.HighlightMode = new Value<HoleModes>("HighlightModes", new String[] { "HM" }, "Mode for highlighting blocks", HoleModes.Full);
        this.ObsidianRed = new Value<Float>("Red", new String[] { "oRed" }, "Red for rendering", 0.0f, 0.0f, 1.0f, 0.1f);
        this.ObsidianGreen = new Value<Float>("Green", new String[] { "oGreen" }, "Green for rendering", 1.0f, 0.0f, 1.0f, 0.1f);
        this.ObsidianBlue = new Value<Float>("Blue", new String[] { "oBlue" }, "Blue for rendering", 0.0f, 0.0f, 1.0f, 0.1f);
        this.ObsidianAlpha = new Value<Float>("Alpha", new String[] { "oAlpha" }, "Alpha for rendering", 0.5f, 0.0f, 1.0f, 0.1f);
        this.camera = (ICamera)new Frustum();
        RayTraceResult ray;
        BlockPos l_Pos;
        AxisAlignedBB bb;
        this.OnRenderEvent = new Listener<RenderEvent>(p_Event -> {
            if (this.mc.getRenderManager() != null) {
                ray = this.mc.objectMouseOver;
                if (ray != null) {
                    if (ray.typeOfHit == RayTraceResult.Type.BLOCK) {
                        l_Pos = ray.getBlockPos();
                        bb = new AxisAlignedBB(l_Pos.getX() - this.mc.getRenderManager().viewerPosX, l_Pos.getY() - this.mc.getRenderManager().viewerPosY, l_Pos.getZ() - this.mc.getRenderManager().viewerPosZ, l_Pos.getX() + 1 - this.mc.getRenderManager().viewerPosX, l_Pos.getY() + 1 - this.mc.getRenderManager().viewerPosY, l_Pos.getZ() + 1 - this.mc.getRenderManager().viewerPosZ);
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
                    }
                }
            }
        }, (Predicate<RenderEvent>[])new Predicate[0]);
    }
    
    private void Render(final AxisAlignedBB bb, final float p_Red, final float p_Green, final float p_Blue, final float p_Alpha) {
        switch (this.HighlightMode.getValue()) {
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
