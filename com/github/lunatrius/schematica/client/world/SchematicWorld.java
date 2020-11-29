//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package com.github.lunatrius.schematica.client.world;

import net.minecraft.world.WorldType;
import net.minecraft.world.GameType;
import net.minecraft.util.math.Vec3i;
import com.github.lunatrius.core.util.math.BlockPosHelper;
import net.minecraft.block.properties.IProperty;
import java.util.Map;
import com.github.lunatrius.schematica.block.state.pattern.BlockStateReplacer;
import net.minecraft.block.state.pattern.BlockStateMatcher;
import java.util.List;
import net.minecraft.item.ItemStack;
import com.github.lunatrius.schematica.reference.Reference;
import net.minecraft.world.World;
import net.minecraft.util.EnumFacing;
import net.minecraft.entity.Entity;
import com.github.lunatrius.schematica.client.world.chunk.ChunkProviderSchematic;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.init.Biomes;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.init.Blocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import java.util.Iterator;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.Minecraft;
import net.minecraft.world.EnumDifficulty;
import com.github.lunatrius.core.util.math.MBlockPos;
import com.github.lunatrius.schematica.api.ISchematic;
import net.minecraft.world.WorldSettings;
import net.minecraft.client.multiplayer.WorldClient;

public class SchematicWorld extends WorldClient
{
    private static final WorldSettings WORLD_SETTINGS;
    private ISchematic schematic;
    public final MBlockPos position;
    public boolean isRendering;
    public LayerMode layerMode;
    public int renderingLayer;
    
    public SchematicWorld(final ISchematic schematic) {
        super((NetHandlerPlayClient)null, SchematicWorld.WORLD_SETTINGS, 0, EnumDifficulty.PEACEFUL, Minecraft.getMinecraft().profiler);
        this.position = new MBlockPos();
        this.isRendering = false;
        this.layerMode = LayerMode.ALL;
        this.renderingLayer = 0;
        this.schematic = schematic;
        for (final TileEntity tileEntity : schematic.getTileEntities()) {
            this.initializeTileEntity(tileEntity);
        }
    }
    
    public IBlockState getBlockState(final BlockPos pos) {
        if (!this.layerMode.shouldUseLayer(this, pos.getY())) {
            return Blocks.AIR.getDefaultState();
        }
        return this.schematic.getBlockState(pos);
    }
    
    public boolean setBlockState(final BlockPos pos, final IBlockState state, final int flags) {
        return this.schematic.setBlockState(pos, state);
    }
    
    public TileEntity getTileEntity(final BlockPos pos) {
        if (!this.layerMode.shouldUseLayer(this, pos.getY())) {
            return null;
        }
        return this.schematic.getTileEntity(pos);
    }
    
    public void setTileEntity(final BlockPos pos, final TileEntity tileEntity) {
        this.schematic.setTileEntity(pos, tileEntity);
        this.initializeTileEntity(tileEntity);
    }
    
    public void removeTileEntity(final BlockPos pos) {
        this.schematic.removeTileEntity(pos);
    }
    
    @SideOnly(Side.CLIENT)
    public int getLightFromNeighborsFor(final EnumSkyBlock type, final BlockPos pos) {
        return 15;
    }
    
    public float getLightBrightness(final BlockPos pos) {
        return 1.0f;
    }
    
    public boolean isBlockNormalCube(final BlockPos pos, final boolean _default) {
        return this.getBlockState(pos).isNormalCube();
    }
    
    public void calculateInitialSkylight() {
    }
    
    protected void calculateInitialWeather() {
    }
    
    public void setSpawnPoint(final BlockPos pos) {
    }
    
    public boolean isAirBlock(final BlockPos pos) {
        final IBlockState blockState = this.getBlockState(pos);
        return blockState.getBlock().isAir(blockState, (IBlockAccess)this, pos);
    }
    
    public Biome getBiome(final BlockPos pos) {
        return Biomes.JUNGLE;
    }
    
    public int getWidth() {
        return this.schematic.getWidth();
    }
    
    public int getLength() {
        return this.schematic.getLength();
    }
    
    public int getHeight() {
        return this.schematic.getHeight();
    }
    
    protected IChunkProvider createChunkProvider() {
        return this.chunkProvider = (IChunkProvider)new ChunkProviderSchematic(this);
    }
    
    public Entity getEntityByID(final int id) {
        return null;
    }
    
    public boolean isSideSolid(final BlockPos pos, final EnumFacing side) {
        return this.isSideSolid(pos, side, false);
    }
    
    public boolean isSideSolid(final BlockPos pos, final EnumFacing side, final boolean _default) {
        return this.getBlockState(pos).isSideSolid((IBlockAccess)this, pos, side);
    }
    
    public void setSchematic(final ISchematic schematic) {
        this.schematic = schematic;
    }
    
    public ISchematic getSchematic() {
        return this.schematic;
    }
    
    public void initializeTileEntity(final TileEntity tileEntity) {
        tileEntity.setWorld((World)this);
        tileEntity.getBlockType();
        try {
            tileEntity.invalidate();
            tileEntity.validate();
        }
        catch (Exception e) {
            Reference.logger.error("TileEntity validation for {} failed!", (Object)tileEntity.getClass(), (Object)e);
        }
    }
    
    public void setIcon(final ItemStack icon) {
        this.schematic.setIcon(icon);
    }
    
    public ItemStack getIcon() {
        return this.schematic.getIcon();
    }
    
    public List<TileEntity> getTileEntities() {
        return this.schematic.getTileEntities();
    }
    
    public boolean toggleRendering() {
        return this.isRendering = !this.isRendering;
    }
    
    public String getDebugDimensions() {
        return "WHL: " + this.getWidth() + " / " + this.getHeight() + " / " + this.getLength();
    }
    
    public int replaceBlock(final BlockStateMatcher matcher, final BlockStateReplacer replacer, final Map<IProperty, Comparable> properties) {
        int count = 0;
        for (final MBlockPos pos : BlockPosHelper.getAllInBox(0, 0, 0, this.getWidth(), this.getHeight(), this.getLength())) {
            final IBlockState blockState = this.schematic.getBlockState(pos);
            if (blockState.getBlock().hasTileEntity(blockState)) {
                continue;
            }
            if (!matcher.apply(blockState)) {
                continue;
            }
            final IBlockState replacement = replacer.getReplacement(blockState, properties);
            if (replacement.getBlock().hasTileEntity(replacement)) {
                continue;
            }
            if (!this.schematic.setBlockState(pos, replacement)) {
                continue;
            }
            this.notifyBlockUpdate((BlockPos)pos.add((Vec3i)this.position), blockState, replacement, 3);
            ++count;
        }
        return count;
    }
    
    public boolean isInside(final BlockPos pos) {
        final int x = pos.getX();
        final int y = pos.getY();
        final int z = pos.getZ();
        return x >= 0 && y >= 0 && z >= 0 && x < this.getWidth() && y < this.getHeight() && z < this.getLength();
    }
    
    static {
        WORLD_SETTINGS = new WorldSettings(0L, GameType.CREATIVE, false, false, WorldType.FLAT);
    }
    
    public enum LayerMode
    {
        ALL("schematica.gui.all") {
            @Override
            public boolean shouldUseLayer(final SchematicWorld world, final int layer) {
                return true;
            }
        }, 
        SINGLE_LAYER("schematica.gui.layers") {
            @Override
            public boolean shouldUseLayer(final SchematicWorld world, final int layer) {
                return layer == world.renderingLayer;
            }
        }, 
        ALL_BELOW("schematica.gui.below") {
            @Override
            public boolean shouldUseLayer(final SchematicWorld world, final int layer) {
                return layer <= world.renderingLayer;
            }
        };
        
        public final String name;
        
        public abstract boolean shouldUseLayer(final SchematicWorld p0, final int p1);
        
        private LayerMode(final String name) {
            this.name = name;
        }
        
        public static LayerMode next(final LayerMode mode) {
            final LayerMode[] values = values();
            return values[(mode.ordinal() + 1) % values.length];
        }
    }
}
