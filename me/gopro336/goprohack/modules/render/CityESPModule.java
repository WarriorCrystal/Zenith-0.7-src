//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.modules.render;

import java.util.Iterator;
import me.gopro336.goprohack.util.CrystalUtils;
import net.minecraft.init.Blocks;
import me.gopro336.goprohack.util.entity.EntityUtil;
import net.minecraft.entity.Entity;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import me.gopro336.goprohack.managers.FriendManager;
import me.gopro336.goprohack.main.Wrapper;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import me.gopro336.goprohack.util.Pair;
import java.util.function.Predicate;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.math.AxisAlignedBB;
import java.util.ArrayList;
import net.minecraft.client.renderer.culling.Frustum;
import me.zero.alpine.fork.listener.EventHandler;
import me.gopro336.goprohack.events.render.RenderEvent;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.util.math.BlockPos;
import net.minecraft.client.renderer.culling.ICamera;
import me.gopro336.goprohack.util.render.ESPUtil;
import me.gopro336.goprohack.modules.Value;
import me.gopro336.goprohack.modules.Module;

public class CityESPModule extends Module
{
    public final Value<ESPUtil.HoleModes> HoleMode;
    public final Value<Float> ObsidianRed;
    public final Value<Float> ObsidianGreen;
    public final Value<Float> ObsidianBlue;
    public final Value<Float> ObsidianAlpha;
    private ICamera camera;
    private static final BlockPos[] surroundOffset;
    @EventHandler
    private Listener<RenderEvent> OnRenderEvent;
    
    public CityESPModule() {
        super("CityESP", new String[] { "CityESP" }, "Renders the blocks that if broken, allow you to city someone", "NONE", -1, ModuleType.RENDER);
        this.HoleMode = new Value<ESPUtil.HoleModes>("Mode", new String[] { "HM" }, "Mode for rendering the blocks", ESPUtil.HoleModes.Full);
        this.ObsidianRed = new Value<Float>("ObsidianRed", new String[] { "oRed" }, "Red for rendering", 1.0f, 0.0f, 1.0f, 0.1f);
        this.ObsidianGreen = new Value<Float>("ObsidianGreen", new String[] { "oGreen" }, "Green for rendering", 0.0f, 0.0f, 1.0f, 0.1f);
        this.ObsidianBlue = new Value<Float>("ObsidianBlue", new String[] { "oBlue" }, "Blue for rendering", 0.0f, 0.0f, 1.0f, 0.1f);
        this.ObsidianAlpha = new Value<Float>("ObsidianAlpha", new String[] { "oAlpha" }, "Alpha for rendering", 0.5f, 0.0f, 1.0f, 0.1f);
        this.camera = (ICamera)new Frustum();
        AxisAlignedBB bb;
        this.OnRenderEvent = new Listener<RenderEvent>(p_Event -> {
            if (this.mc.getRenderManager() != null && this.mc.getRenderManager().options != null) {
                GetPlayersReadyToBeCitied().forEach(pair -> pair.getSecond().forEach(o -> {
                    bb = new AxisAlignedBB(o.getX() - this.mc.getRenderManager().viewerPosX, o.getY() - this.mc.getRenderManager().viewerPosY, o.getZ() - this.mc.getRenderManager().viewerPosZ, o.getX() + 1 - this.mc.getRenderManager().viewerPosX, o.getY() + 1 - this.mc.getRenderManager().viewerPosY, o.getZ() + 1 - this.mc.getRenderManager().viewerPosZ);
                    this.camera.setPosition(this.mc.getRenderViewEntity().posX, this.mc.getRenderViewEntity().posY, this.mc.getRenderViewEntity().posZ);
                    if (this.camera.isBoundingBoxInFrustum(new AxisAlignedBB(bb.minX + this.mc.getRenderManager().viewerPosX, bb.minY + this.mc.getRenderManager().viewerPosY, bb.minZ + this.mc.getRenderManager().viewerPosZ, bb.maxX + this.mc.getRenderManager().viewerPosX, bb.maxY + this.mc.getRenderManager().viewerPosY, bb.maxZ + this.mc.getRenderManager().viewerPosZ))) {
                        GlStateManager.pushMatrix();
                        GlStateManager.enableBlend();
                        GlStateManager.disableDepth();
                        GlStateManager.tryBlendFuncSeparate(770, 771, 0, 1);
                        GlStateManager.disableTexture2D();
                        GlStateManager.depthMask((boolean)(0 != 0));
                        GL11.glEnable(2848);
                        GL11.glHint(3154, 4354);
                        GL11.glLineWidth(1.5f);
                        ESPUtil.Render(this.HoleMode.getValue(), bb, this.ObsidianRed.getValue(), this.ObsidianGreen.getValue(), this.ObsidianBlue.getValue(), this.ObsidianAlpha.getValue());
                        GL11.glDisable(2848);
                        GlStateManager.depthMask((boolean)(1 != 0));
                        GlStateManager.enableDepth();
                        GlStateManager.enableTexture2D();
                        GlStateManager.disableBlend();
                        GlStateManager.popMatrix();
                    }
                }));
            }
        }, (Predicate<RenderEvent>[])new Predicate[0]);
    }
    
    public static ArrayList<Pair<EntityPlayer, ArrayList<BlockPos>>> GetPlayersReadyToBeCitied() {
        final ArrayList<Pair<EntityPlayer, ArrayList<BlockPos>>> players = new ArrayList<Pair<EntityPlayer, ArrayList<BlockPos>>>();
        for (final Entity entity : (List)Wrapper.GetMC().world.playerEntities.stream().filter(entityPlayer -> !FriendManager.Get().IsFriend(entityPlayer)).collect(Collectors.toList())) {
            final ArrayList<BlockPos> positions = new ArrayList<BlockPos>();
            for (int i = 0; i < 4; ++i) {
                final BlockPos o = EntityUtil.GetPositionVectorBlockPos(entity, CityESPModule.surroundOffset[i]);
                if (Wrapper.GetMC().world.getBlockState(o).getBlock() == Blocks.OBSIDIAN) {
                    boolean passCheck = false;
                    switch (i) {
                        case 0: {
                            passCheck = CrystalUtils.canPlaceCrystal(o.north(1).down());
                            break;
                        }
                        case 1: {
                            passCheck = CrystalUtils.canPlaceCrystal(o.east(1).down());
                            break;
                        }
                        case 2: {
                            passCheck = CrystalUtils.canPlaceCrystal(o.south(1).down());
                            break;
                        }
                        case 3: {
                            passCheck = CrystalUtils.canPlaceCrystal(o.west(1).down());
                            break;
                        }
                    }
                    if (passCheck) {
                        positions.add(o);
                    }
                }
            }
            if (!positions.isEmpty()) {
                players.add(new Pair<EntityPlayer, ArrayList<BlockPos>>((EntityPlayer)entity, positions));
            }
        }
        return players;
    }
    
    static {
        surroundOffset = new BlockPos[] { new BlockPos(0, 0, -1), new BlockPos(1, 0, 0), new BlockPos(0, 0, 1), new BlockPos(-1, 0, 0) };
    }
}
