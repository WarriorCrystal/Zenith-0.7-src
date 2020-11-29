//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.modules.render;

import net.minecraft.util.math.Vec3i;
import net.minecraft.util.math.BlockPos;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.math.AxisAlignedBB;
import java.util.Collection;
import java.util.function.Predicate;
import net.minecraft.tileentity.TileEntityShulkerBox;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityEnderChest;
import net.minecraft.client.renderer.culling.Frustum;
import java.util.ArrayList;
import me.gopro336.goprohack.events.render.RenderEvent;
import me.zero.alpine.fork.listener.EventHandler;
import me.gopro336.goprohack.events.player.EventPlayerUpdate;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.client.renderer.culling.ICamera;
import java.util.List;
import me.gopro336.goprohack.modules.Value;
import me.gopro336.goprohack.modules.Module;

public class StorageESPModule extends Module
{
    public final Value<Boolean> EnderChests;
    public final Value<Boolean> Chests;
    public final Value<Boolean> Shulkers;
    public final List<StorageBlockPos> Storages;
    private ICamera camera;
    @EventHandler
    private Listener<EventPlayerUpdate> OnPlayerUpdate;
    @EventHandler
    private Listener<RenderEvent> OnRenderEvent;
    
    public StorageESPModule() {
        super("ChestESP", new String[] { "" }, "Highlights different kind of storages", "NONE", -1, ModuleType.RENDER);
        this.EnderChests = new Value<Boolean>("EnderChests", new String[] { "S" }, "Highlights EnderChests", true);
        this.Chests = new Value<Boolean>("Chests", new String[] { "S" }, "Highlights Chests", true);
        this.Shulkers = new Value<Boolean>("Shulkers", new String[] { "S" }, "Highlights Shulkers", true);
        this.Storages = new ArrayList<StorageBlockPos>();
        this.camera = (ICamera)new Frustum();
        this.OnPlayerUpdate = new Listener<EventPlayerUpdate>(p_Event -> {
            this.Storages.clear();
            this.mc.world.loadedTileEntityList.forEach(p_Tile -> {
                if (p_Tile instanceof TileEntityEnderChest && this.EnderChests.getValue()) {
                    this.Storages.add(new StorageBlockPos(p_Tile.getPos().getX(), p_Tile.getPos().getY(), p_Tile.getPos().getZ(), StorageType.Ender));
                }
                else if (p_Tile instanceof TileEntityChest && this.Chests.getValue()) {
                    this.Storages.add(new StorageBlockPos(p_Tile.getPos().getX(), p_Tile.getPos().getY(), p_Tile.getPos().getZ(), StorageType.Chest));
                }
                else if (p_Tile instanceof TileEntityShulkerBox && this.Shulkers.getValue()) {
                    this.Storages.add(new StorageBlockPos(p_Tile.getPos().getX(), p_Tile.getPos().getY(), p_Tile.getPos().getZ(), StorageType.Shulker));
                }
            });
            return;
        }, (Predicate<EventPlayerUpdate>[])new Predicate[0]);
        AxisAlignedBB bb;
        this.OnRenderEvent = new Listener<RenderEvent>(p_Event -> {
            if (this.mc.getRenderManager() != null && this.mc.getRenderManager().options != null) {
                new ArrayList(this.Storages).forEach(p_Pos -> {
                    bb = new AxisAlignedBB(p_Pos.getX() - this.mc.getRenderManager().viewerPosX, p_Pos.getY() - this.mc.getRenderManager().viewerPosY, p_Pos.getZ() - this.mc.getRenderManager().viewerPosZ, p_Pos.getX() + 1 - this.mc.getRenderManager().viewerPosX, p_Pos.getY() + 1 - this.mc.getRenderManager().viewerPosY, p_Pos.getZ() + 1 - this.mc.getRenderManager().viewerPosZ);
                    this.camera.setPosition(this.mc.getRenderViewEntity().posX, this.mc.getRenderViewEntity().posY, this.mc.getRenderViewEntity().posZ);
                    if (this.camera.isBoundingBoxInFrustum(new AxisAlignedBB(bb.minX + this.mc.getRenderManager().viewerPosX, bb.minY + this.mc.getRenderManager().viewerPosY, bb.minZ + this.mc.getRenderManager().viewerPosZ, bb.maxX + this.mc.getRenderManager().viewerPosX, bb.maxY + this.mc.getRenderManager().viewerPosY, bb.maxZ + this.mc.getRenderManager().viewerPosZ))) {
                        GlStateManager.pushMatrix();
                        switch (p_Pos.GetType()) {
                            case Chest: {
                                RenderGlobal.drawBoundingBox(bb.minX, bb.minY, bb.minZ, bb.maxX, bb.maxY, bb.maxZ, 0.94f, 1.0f, 0.0f, 0.6f);
                                break;
                            }
                            case Ender: {
                                RenderGlobal.drawBoundingBox(bb.minX, bb.minY, bb.minZ, bb.maxX, bb.maxY, bb.maxZ, 0.65f, 0.0f, 0.93f, 0.6f);
                                break;
                            }
                            case Shulker: {
                                RenderGlobal.drawBoundingBox(bb.minX, bb.minY, bb.minZ, bb.maxX, bb.maxY, bb.maxZ, 1.0f, 0.0f, 0.59f, 0.6f);
                                break;
                            }
                        }
                        GlStateManager.popMatrix();
                    }
                });
            }
        }, (Predicate<RenderEvent>[])new Predicate[0]);
    }
    
    public enum StorageType
    {
        Chest, 
        Shulker, 
        Ender;
    }
    
    public class StorageBlockPos extends BlockPos
    {
        public StorageType Type;
        
        public StorageBlockPos(final int x, final int y, final int z, final StorageType p_Type) {
            super(x, y, z);
            this.Type = p_Type;
        }
        
        public StorageType GetType() {
            return this.Type;
        }
    }
}
