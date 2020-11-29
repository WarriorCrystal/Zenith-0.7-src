//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package com.github.lunatrius.schematica.client.renderer;

import java.util.EnumSet;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.chunk.VisGraph;
import org.lwjgl.util.vector.Vector3f;
import java.util.LinkedList;
import java.util.Collection;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.MathHelper;
import net.minecraft.client.renderer.chunk.CompiledChunk;
import net.minecraft.util.math.AxisAlignedBB;
import java.util.Iterator;
import net.minecraft.util.math.Vec3i;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.entity.Entity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.culling.Frustum;
import com.github.lunatrius.core.client.renderer.GeometryTessellator;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import com.github.lunatrius.schematica.handler.ConfigurationHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.player.EntityPlayer;
import com.github.lunatrius.schematica.proxy.ClientProxy;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraft.world.IWorldEventListener;
import javax.annotation.Nullable;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.resources.IResourceManager;
import com.github.lunatrius.schematica.client.renderer.chunk.overlay.RenderOverlayList;
import net.minecraft.util.math.BlockPos;
import com.github.lunatrius.schematica.client.renderer.chunk.proxy.SchematicRenderChunkList;
import com.github.lunatrius.schematica.client.renderer.chunk.container.SchematicChunkRenderContainerList;
import com.github.lunatrius.schematica.client.renderer.chunk.proxy.SchematicRenderChunkVbo;
import net.minecraft.world.World;
import com.github.lunatrius.schematica.client.renderer.chunk.container.SchematicChunkRenderContainerVbo;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.GlStateManager;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.github.lunatrius.schematica.client.renderer.chunk.overlay.ISchematicRenderChunkFactory;
import com.github.lunatrius.schematica.client.renderer.chunk.container.SchematicChunkRenderContainer;
import com.github.lunatrius.schematica.client.renderer.chunk.OverlayRenderDispatcher;
import net.minecraft.client.renderer.chunk.ChunkRenderDispatcher;
import java.util.List;
import com.github.lunatrius.schematica.client.renderer.chunk.overlay.RenderOverlay;
import net.minecraft.client.renderer.chunk.RenderChunk;
import java.util.Set;
import com.github.lunatrius.schematica.client.world.SchematicWorld;
import com.github.lunatrius.core.util.math.MBlockPos;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.profiler.Profiler;
import net.minecraft.client.Minecraft;
import com.github.lunatrius.core.util.vector.Vector3d;
import com.github.lunatrius.schematica.client.renderer.shader.ShaderProgram;
import javax.annotation.ParametersAreNonnullByDefault;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.RenderGlobal;

@SideOnly(Side.CLIENT)
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class RenderSchematic extends RenderGlobal
{
    public static final RenderSchematic INSTANCE;
    public static final int RENDER_DISTANCE = 32;
    public static final int CHUNKS_XZ = 66;
    public static final int CHUNKS_Y = 16;
    public static final int CHUNKS = 69696;
    public static final int PASS = 2;
    private static final ShaderProgram SHADER_ALPHA;
    private static final Vector3d PLAYER_POSITION_OFFSET;
    private final Minecraft mc;
    private final Profiler profiler;
    private final RenderManager renderManager;
    private final MBlockPos tmp;
    private SchematicWorld world;
    private Set<RenderChunk> chunksToUpdate;
    private Set<RenderOverlay> overlaysToUpdate;
    private List<ContainerLocalRenderInformation> renderInfos;
    private ViewFrustumOverlay viewFrustum;
    private double frustumUpdatePosX;
    private double frustumUpdatePosY;
    private double frustumUpdatePosZ;
    private int frustumUpdatePosChunkX;
    private int frustumUpdatePosChunkY;
    private int frustumUpdatePosChunkZ;
    private double lastViewEntityX;
    private double lastViewEntityY;
    private double lastViewEntityZ;
    private double lastViewEntityPitch;
    private double lastViewEntityYaw;
    private ChunkRenderDispatcher renderDispatcher;
    private OverlayRenderDispatcher renderDispatcherOverlay;
    private SchematicChunkRenderContainer renderContainer;
    private int renderDistanceChunks;
    private int countEntitiesTotal;
    private int countEntitiesRendered;
    private int countTileEntitiesTotal;
    private int countTileEntitiesRendered;
    private boolean vboEnabled;
    private ISchematicRenderChunkFactory renderChunkFactory;
    private double prevRenderSortX;
    private double prevRenderSortY;
    private double prevRenderSortZ;
    private boolean displayListEntitiesDirty;
    private int frameCount;
    
    public RenderSchematic(final Minecraft minecraft) {
        super(minecraft);
        this.tmp = new MBlockPos();
        this.chunksToUpdate = (Set<RenderChunk>)Sets.newLinkedHashSet();
        this.overlaysToUpdate = (Set<RenderOverlay>)Sets.newLinkedHashSet();
        this.renderInfos = (List<ContainerLocalRenderInformation>)Lists.newArrayListWithCapacity(69696);
        this.viewFrustum = null;
        this.frustumUpdatePosX = Double.MIN_VALUE;
        this.frustumUpdatePosY = Double.MIN_VALUE;
        this.frustumUpdatePosZ = Double.MIN_VALUE;
        this.frustumUpdatePosChunkX = Integer.MIN_VALUE;
        this.frustumUpdatePosChunkY = Integer.MIN_VALUE;
        this.frustumUpdatePosChunkZ = Integer.MIN_VALUE;
        this.lastViewEntityX = Double.MIN_VALUE;
        this.lastViewEntityY = Double.MIN_VALUE;
        this.lastViewEntityZ = Double.MIN_VALUE;
        this.lastViewEntityPitch = Double.MIN_VALUE;
        this.lastViewEntityYaw = Double.MIN_VALUE;
        this.renderDispatcher = null;
        this.renderDispatcherOverlay = null;
        this.renderDistanceChunks = -1;
        this.vboEnabled = false;
        this.displayListEntitiesDirty = true;
        this.frameCount = 0;
        this.mc = minecraft;
        this.profiler = minecraft.profiler;
        this.renderManager = minecraft.getRenderManager();
        GlStateManager.glTexParameteri(3553, 10242, 10497);
        GlStateManager.glTexParameteri(3553, 10243, 10497);
        GlStateManager.bindTexture(0);
        this.vboEnabled = OpenGlHelper.useVbo();
        if (this.vboEnabled) {
            this.initVbo();
        }
        else {
            this.initList();
        }
    }
    
    private void initVbo() {
        this.renderContainer = new SchematicChunkRenderContainerVbo();
        this.renderChunkFactory = new ISchematicRenderChunkFactory() {
            public RenderChunk create(final World world, final RenderGlobal renderGlobal, final int index) {
                return new SchematicRenderChunkVbo(world, renderGlobal, index);
            }
            
            @Override
            public RenderOverlay makeRenderOverlay(final World world, final RenderGlobal renderGlobal, final int index) {
                return new RenderOverlay(world, renderGlobal, index);
            }
        };
    }
    
    private void initList() {
        this.renderContainer = new SchematicChunkRenderContainerList();
        this.renderChunkFactory = new ISchematicRenderChunkFactory() {
            public RenderChunk create(final World world, final RenderGlobal renderGlobal, final int index) {
                return (RenderChunk)new SchematicRenderChunkList(world, renderGlobal, null, index);
            }
            
            @Override
            public RenderOverlay makeRenderOverlay(final World world, final RenderGlobal renderGlobal, final int index) {
                return new RenderOverlayList(world, renderGlobal, null, index);
            }
        };
    }
    
    public void onResourceManagerReload(final IResourceManager resourceManager) {
    }
    
    public void makeEntityOutlineShader() {
    }
    
    public void renderEntityOutlineFramebuffer() {
    }
    
    protected boolean isRenderEntityOutlines() {
        return false;
    }
    
    public void setWorldAndLoadRenderers(@Nullable final WorldClient worldClient) {
        if (worldClient instanceof SchematicWorld) {
            this.setWorldAndLoadRenderers((SchematicWorld)worldClient);
        }
        else {
            this.setWorldAndLoadRenderers(null);
        }
    }
    
    public void setWorldAndLoadRenderers(@Nullable final SchematicWorld world) {
        if (this.world != null) {
            this.world.removeEventListener((IWorldEventListener)this);
        }
        this.frustumUpdatePosX = Double.MIN_VALUE;
        this.frustumUpdatePosY = Double.MIN_VALUE;
        this.frustumUpdatePosZ = Double.MIN_VALUE;
        this.frustumUpdatePosChunkX = Integer.MIN_VALUE;
        this.frustumUpdatePosChunkY = Integer.MIN_VALUE;
        this.frustumUpdatePosChunkZ = Integer.MIN_VALUE;
        this.renderManager.setWorld((World)world);
        if ((this.world = world) != null) {
            world.addEventListener((IWorldEventListener)this);
            this.loadRenderers();
        }
        else {
            this.chunksToUpdate.clear();
            this.overlaysToUpdate.clear();
            this.renderInfos.clear();
            if (this.viewFrustum != null) {
                this.viewFrustum.deleteGlResources();
            }
            this.viewFrustum = null;
            if (this.renderDispatcher != null) {
                this.renderDispatcher.stopWorkerThreads();
            }
            this.renderDispatcher = null;
            if (this.renderDispatcherOverlay != null) {
                this.renderDispatcherOverlay.stopWorkerThreads();
            }
            this.renderDispatcherOverlay = null;
        }
    }
    
    @SubscribeEvent
    public void onRenderWorldLast(final RenderWorldLastEvent event) {
        final EntityPlayerSP player = (this.mc.getRenderViewEntity() instanceof EntityPlayerSP) ? this.mc.getRenderViewEntity() : this.mc.player;
        if (player != null) {
            this.profiler.startSection("schematica");
            ClientProxy.setPlayerData((EntityPlayer)player, event.getPartialTicks());
            final SchematicWorld schematic = ClientProxy.schematic;
            final boolean isRenderingSchematic = schematic != null && schematic.isRendering;
            this.profiler.startSection("schematic");
            if (isRenderingSchematic) {
                GlStateManager.pushMatrix();
                this.renderSchematic(schematic, event.getPartialTicks());
                GlStateManager.popMatrix();
            }
            this.profiler.endStartSection("guide");
            if (ClientProxy.isRenderingGuide || isRenderingSchematic) {
                GlStateManager.pushMatrix();
                this.renderOverlay(schematic, isRenderingSchematic);
                GlStateManager.popMatrix();
            }
            this.profiler.endSection();
            this.profiler.endSection();
        }
    }
    
    private void renderSchematic(final SchematicWorld schematic, final float partialTicks) {
        if (this.world != schematic) {
            this.world = schematic;
            this.loadRenderers();
        }
        RenderSchematic.PLAYER_POSITION_OFFSET.set(ClientProxy.playerPosition).sub(this.world.position.x, this.world.position.y, this.world.position.z);
        if (OpenGlHelper.shadersSupported && ConfigurationHandler.enableAlpha) {
            GL20.glUseProgram(RenderSchematic.SHADER_ALPHA.getProgram());
            GL20.glUniform1f(GL20.glGetUniformLocation(RenderSchematic.SHADER_ALPHA.getProgram(), (CharSequence)"alpha_multiplier"), ConfigurationHandler.alpha);
        }
        final int fps = Math.max(Minecraft.getDebugFPS(), 30);
        this.renderWorld(partialTicks, System.nanoTime() + 1000000000 / fps);
        if (OpenGlHelper.shadersSupported && ConfigurationHandler.enableAlpha) {
            GL20.glUseProgram(0);
        }
    }
    
    private void renderOverlay(final SchematicWorld schematic, final boolean isRenderingSchematic) {
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GL11.glEnable(2848);
        final GeometryTessellator tessellator = GeometryTessellator.getInstance();
        tessellator.setTranslation(-ClientProxy.playerPosition.x, -ClientProxy.playerPosition.y, -ClientProxy.playerPosition.z);
        tessellator.setDelta(ConfigurationHandler.blockDelta);
        if (ClientProxy.isRenderingGuide) {
            tessellator.beginQuads();
            tessellator.drawCuboid(ClientProxy.pointA, 63, 1069481984);
            tessellator.drawCuboid(ClientProxy.pointB, 63, 1056964799);
            tessellator.draw();
        }
        tessellator.beginLines();
        if (ClientProxy.isRenderingGuide) {
            tessellator.drawCuboid(ClientProxy.pointA, 63, 1069481984);
            tessellator.drawCuboid(ClientProxy.pointB, 63, 1056964799);
            tessellator.drawCuboid(ClientProxy.pointMin, ClientProxy.pointMax, 63, 2130755328);
        }
        if (isRenderingSchematic) {
            this.tmp.set(schematic.position.x + schematic.getWidth() - 1, schematic.position.y + schematic.getHeight() - 1, schematic.position.z + schematic.getLength() - 1);
            tessellator.drawCuboid(schematic.position, this.tmp, 63, 2143223999);
        }
        tessellator.draw();
        GlStateManager.depthMask(false);
        this.renderContainer.renderOverlay();
        GlStateManager.depthMask(true);
        GL11.glDisable(2848);
        GlStateManager.disableBlend();
        GlStateManager.enableTexture2D();
    }
    
    private void renderWorld(final float partialTicks, final long finishTimeNano) {
        GlStateManager.enableCull();
        this.profiler.endStartSection("culling");
        final Frustum frustum = new Frustum();
        final Entity entity = this.mc.getRenderViewEntity();
        final double x = RenderSchematic.PLAYER_POSITION_OFFSET.x;
        final double y = RenderSchematic.PLAYER_POSITION_OFFSET.y;
        final double z = RenderSchematic.PLAYER_POSITION_OFFSET.z;
        frustum.setPosition(x, y, z);
        GlStateManager.shadeModel(7425);
        this.profiler.endStartSection("prepareterrain");
        this.mc.getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
        RenderHelper.disableStandardItemLighting();
        this.profiler.endStartSection("terrain_setup");
        this.setupTerrain(entity, partialTicks, (ICamera)frustum, this.frameCount++, this.isInsideWorld(x, y, z));
        this.profiler.endStartSection("updatechunks");
        this.updateChunks(finishTimeNano / 2L);
        this.profiler.endStartSection("terrain");
        GlStateManager.matrixMode(5888);
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        this.renderBlockLayer(BlockRenderLayer.SOLID, partialTicks, 2, entity);
        this.renderBlockLayer(BlockRenderLayer.CUTOUT_MIPPED, partialTicks, 2, entity);
        this.mc.getTextureManager().getTexture(TextureMap.LOCATION_BLOCKS_TEXTURE).setBlurMipmap(false, false);
        this.renderBlockLayer(BlockRenderLayer.CUTOUT, partialTicks, 2, entity);
        this.mc.getTextureManager().getTexture(TextureMap.LOCATION_BLOCKS_TEXTURE).restoreLastBlurMipmap();
        GlStateManager.disableBlend();
        GlStateManager.shadeModel(7424);
        GlStateManager.alphaFunc(516, 0.1f);
        GlStateManager.matrixMode(5888);
        GlStateManager.popMatrix();
        GlStateManager.pushMatrix();
        this.profiler.endStartSection("entities");
        RenderHelper.enableStandardItemLighting();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        this.renderEntities(entity, (ICamera)frustum, partialTicks);
        GlStateManager.disableBlend();
        RenderHelper.disableStandardItemLighting();
        this.disableLightmap();
        GlStateManager.matrixMode(5888);
        GlStateManager.popMatrix();
        GlStateManager.enableCull();
        GlStateManager.alphaFunc(516, 0.1f);
        this.mc.getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
        GlStateManager.shadeModel(7425);
        GlStateManager.depthMask(false);
        GlStateManager.pushMatrix();
        this.profiler.endStartSection("translucent");
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        this.renderBlockLayer(BlockRenderLayer.TRANSLUCENT, partialTicks, 2, entity);
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
        GlStateManager.depthMask(true);
        GlStateManager.shadeModel(7424);
        GlStateManager.enableCull();
    }
    
    private boolean isInsideWorld(final double x, final double y, final double z) {
        return x >= -1.0 && y >= -1.0 && z >= -1.0 && x <= this.world.getWidth() && y <= this.world.getHeight() && z <= this.world.getLength();
    }
    
    private void disableLightmap() {
        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GlStateManager.disableTexture2D();
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
    }
    
    public void refresh() {
        this.loadRenderers();
    }
    
    public void loadRenderers() {
        if (this.world != null) {
            if (this.renderDispatcher == null) {
                this.renderDispatcher = new ChunkRenderDispatcher(5);
            }
            if (this.renderDispatcherOverlay == null) {
                this.renderDispatcherOverlay = new OverlayRenderDispatcher(5);
            }
            this.displayListEntitiesDirty = true;
            this.renderDistanceChunks = ConfigurationHandler.renderDistance;
            final boolean vbo = this.vboEnabled;
            this.vboEnabled = OpenGlHelper.useVbo();
            if (vbo && !this.vboEnabled) {
                this.initList();
            }
            else if (!vbo && this.vboEnabled) {
                this.initVbo();
            }
            if (this.viewFrustum != null) {
                this.viewFrustum.deleteGlResources();
            }
            this.stopChunkUpdates();
            this.viewFrustum = new ViewFrustumOverlay((World)this.world, this.renderDistanceChunks, this, this.renderChunkFactory);
            final double posX = RenderSchematic.PLAYER_POSITION_OFFSET.x;
            final double posZ = RenderSchematic.PLAYER_POSITION_OFFSET.z;
            this.viewFrustum.updateChunkPositions(posX, posZ);
        }
    }
    
    protected void stopChunkUpdates() {
        this.chunksToUpdate.clear();
        this.overlaysToUpdate.clear();
        this.renderDispatcher.stopChunkUpdates();
        this.renderDispatcherOverlay.stopChunkUpdates();
    }
    
    public void createBindEntityOutlineFbs(final int width, final int height) {
    }
    
    public void renderEntities(final Entity renderViewEntity, final ICamera camera, final float partialTicks) {
        final int entityPass = 0;
        this.profiler.startSection("prepare");
        TileEntityRendererDispatcher.instance.prepare((World)this.world, this.mc.getTextureManager(), this.mc.fontRenderer, renderViewEntity, this.mc.objectMouseOver, partialTicks);
        this.renderManager.cacheActiveRenderInfo((World)this.world, this.mc.fontRenderer, renderViewEntity, this.mc.pointedEntity, this.mc.gameSettings, partialTicks);
        this.countEntitiesTotal = 0;
        this.countEntitiesRendered = 0;
        this.countTileEntitiesTotal = 0;
        this.countTileEntitiesRendered = 0;
        final double x = RenderSchematic.PLAYER_POSITION_OFFSET.x;
        final double y = RenderSchematic.PLAYER_POSITION_OFFSET.y;
        final double z = RenderSchematic.PLAYER_POSITION_OFFSET.z;
        TileEntityRendererDispatcher.staticPlayerX = x;
        TileEntityRendererDispatcher.staticPlayerY = y;
        TileEntityRendererDispatcher.staticPlayerZ = z;
        TileEntityRendererDispatcher.instance.entityX = x;
        TileEntityRendererDispatcher.instance.entityY = y;
        TileEntityRendererDispatcher.instance.entityZ = z;
        this.renderManager.setRenderPosition(x, y, z);
        this.mc.entityRenderer.enableLightmap();
        this.profiler.endStartSection("blockentities");
        RenderHelper.enableStandardItemLighting();
        TileEntityRendererDispatcher.instance.preDrawBatch();
        for (final ContainerLocalRenderInformation renderInfo : this.renderInfos) {
            for (final TileEntity tileEntity : renderInfo.renderChunk.getCompiledChunk().getTileEntities()) {
                final AxisAlignedBB renderBB = tileEntity.getRenderBoundingBox();
                ++this.countTileEntitiesTotal;
                if (tileEntity.shouldRenderInPass(0)) {
                    if (!camera.isBoundingBoxInFrustum(renderBB)) {
                        continue;
                    }
                    if (!this.mc.world.isAirBlock(tileEntity.getPos().add((Vec3i)this.world.position))) {
                        continue;
                    }
                    TileEntityRendererDispatcher.instance.render(tileEntity, partialTicks, -1);
                    ++this.countTileEntitiesRendered;
                }
            }
        }
        TileEntityRendererDispatcher.instance.drawBatch(0);
        this.mc.entityRenderer.disableLightmap();
        this.profiler.endSection();
    }
    
    public String getDebugInfoRenders() {
        final int total = this.viewFrustum.renderChunks.length;
        final int rendered = this.getRenderedChunks();
        return String.format("C: %d/%d %sD: %d, %s", rendered, total, this.mc.renderChunksMany ? "(s) " : "", this.renderDistanceChunks, this.renderDispatcher.getDebugInfo());
    }
    
    protected int getRenderedChunks() {
        int rendered = 0;
        for (final ContainerLocalRenderInformation renderInfo : this.renderInfos) {
            final CompiledChunk compiledChunk = renderInfo.renderChunk.compiledChunk;
            if (compiledChunk != CompiledChunk.DUMMY && !compiledChunk.isEmpty()) {
                ++rendered;
            }
        }
        return rendered;
    }
    
    public String getDebugInfoEntities() {
        return String.format("E: %d/%d", this.countEntitiesRendered, this.countEntitiesTotal);
    }
    
    public String getDebugInfoTileEntities() {
        return String.format("TE: %d/%d", this.countTileEntitiesRendered, this.countTileEntitiesTotal);
    }
    
    public void setupTerrain(final Entity viewEntity, final double partialTicks, final ICamera camera, final int frameCount, final boolean playerSpectator) {
        if (ConfigurationHandler.renderDistance != this.renderDistanceChunks || this.vboEnabled != OpenGlHelper.useVbo()) {
            this.loadRenderers();
        }
        this.profiler.startSection("camera");
        final double posX = RenderSchematic.PLAYER_POSITION_OFFSET.x;
        final double posY = RenderSchematic.PLAYER_POSITION_OFFSET.y;
        final double posZ = RenderSchematic.PLAYER_POSITION_OFFSET.z;
        final double deltaX = posX - this.frustumUpdatePosX;
        final double deltaY = posY - this.frustumUpdatePosY;
        final double deltaZ = posZ - this.frustumUpdatePosZ;
        final int chunkCoordX = MathHelper.floor(posX) >> 4;
        final int chunkCoordY = MathHelper.floor(posY) >> 4;
        final int chunkCoordZ = MathHelper.floor(posZ) >> 4;
        if (this.frustumUpdatePosChunkX != chunkCoordX || this.frustumUpdatePosChunkY != chunkCoordY || this.frustumUpdatePosChunkZ != chunkCoordZ || deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ > 16.0) {
            this.frustumUpdatePosX = posX;
            this.frustumUpdatePosY = posY;
            this.frustumUpdatePosZ = posZ;
            this.frustumUpdatePosChunkX = chunkCoordX;
            this.frustumUpdatePosChunkY = chunkCoordY;
            this.frustumUpdatePosChunkZ = chunkCoordZ;
            this.viewFrustum.updateChunkPositions(posX, posZ);
        }
        this.profiler.endStartSection("renderlistcamera");
        this.renderContainer.initialize(posX, posY, posZ);
        this.profiler.endStartSection("culling");
        final BlockPos posEye = new BlockPos(posX, posY + viewEntity.getEyeHeight(), posZ);
        final RenderChunk renderChunkCurrent = this.viewFrustum.getRenderChunk(posEye);
        final RenderOverlay renderOverlayCurrent = this.viewFrustum.getRenderOverlay(posEye);
        this.displayListEntitiesDirty = (this.displayListEntitiesDirty || !this.chunksToUpdate.isEmpty() || posX != this.lastViewEntityX || posY != this.lastViewEntityY || posZ != this.lastViewEntityZ || viewEntity.rotationPitch != this.lastViewEntityPitch || viewEntity.rotationYaw != this.lastViewEntityYaw);
        this.lastViewEntityX = posX;
        this.lastViewEntityY = posY;
        this.lastViewEntityZ = posZ;
        this.lastViewEntityPitch = viewEntity.rotationPitch;
        this.lastViewEntityYaw = viewEntity.rotationYaw;
        this.profiler.endStartSection("update");
        if (this.displayListEntitiesDirty) {
            this.displayListEntitiesDirty = false;
            this.renderInfos = (List<ContainerLocalRenderInformation>)Lists.newArrayListWithCapacity(69696);
            final LinkedList<ContainerLocalRenderInformation> renderInfoList = (LinkedList<ContainerLocalRenderInformation>)Lists.newLinkedList();
            boolean renderChunksMany = this.mc.renderChunksMany;
            if (renderChunkCurrent == null) {
                final int chunkY = (posEye.getY() > 0) ? 248 : 8;
                for (int chunkX = -this.renderDistanceChunks; chunkX <= this.renderDistanceChunks; ++chunkX) {
                    for (int chunkZ = -this.renderDistanceChunks; chunkZ <= this.renderDistanceChunks; ++chunkZ) {
                        final BlockPos pos = new BlockPos((chunkX << 4) + 8, chunkY, (chunkZ << 4) + 8);
                        final RenderChunk renderChunk = this.viewFrustum.getRenderChunk(pos);
                        final RenderOverlay renderOverlay = this.viewFrustum.getRenderOverlay(pos);
                        if (renderChunk != null && camera.isBoundingBoxInFrustum(renderChunk.boundingBox)) {
                            renderChunk.setFrameIndex(frameCount);
                            renderOverlay.setFrameIndex(frameCount);
                            renderInfoList.add(new ContainerLocalRenderInformation(renderChunk, renderOverlay, null, 0));
                        }
                    }
                }
            }
            else {
                boolean add = false;
                final ContainerLocalRenderInformation renderInfo = new ContainerLocalRenderInformation(renderChunkCurrent, renderOverlayCurrent, null, 0);
                final Set<EnumFacing> visibleSides = this.getVisibleSides(posEye);
                if (visibleSides.size() == 1) {
                    final Vector3f viewVector = this.getViewVector(viewEntity, partialTicks);
                    final EnumFacing facing = EnumFacing.getFacingFromVector(viewVector.x, viewVector.y, viewVector.z).getOpposite();
                    visibleSides.remove(facing);
                }
                if (visibleSides.isEmpty()) {
                    add = true;
                }
                if (add && !playerSpectator) {
                    this.renderInfos.add(renderInfo);
                }
                else {
                    if (playerSpectator && this.world.getBlockState(posEye).isOpaqueCube()) {
                        renderChunksMany = false;
                    }
                    renderChunkCurrent.setFrameIndex(frameCount);
                    renderOverlayCurrent.setFrameIndex(frameCount);
                    renderInfoList.add(renderInfo);
                }
            }
            this.profiler.startSection("iteration");
            while (!renderInfoList.isEmpty()) {
                final ContainerLocalRenderInformation renderInfo2 = renderInfoList.poll();
                final RenderChunk renderChunk2 = renderInfo2.renderChunk;
                final EnumFacing facing2 = renderInfo2.facing;
                this.renderInfos.add(renderInfo2);
                for (final EnumFacing side : EnumFacing.VALUES) {
                    final RenderChunk neighborRenderChunk = this.getNeighborRenderChunk(posEye, renderChunk2, side);
                    final RenderOverlay neighborRenderOverlay = this.getNeighborRenderOverlay(posEye, renderChunk2, side);
                    if ((!renderChunksMany || !renderInfo2.setFacing.contains(side.getOpposite())) && (!renderChunksMany || facing2 == null || renderChunk2.getCompiledChunk().isVisible(facing2.getOpposite(), side)) && neighborRenderChunk != null && neighborRenderChunk.setFrameIndex(frameCount) && camera.isBoundingBoxInFrustum(neighborRenderChunk.boundingBox)) {
                        final ContainerLocalRenderInformation renderInfoNext = new ContainerLocalRenderInformation(neighborRenderChunk, neighborRenderOverlay, side, renderInfo2.counter + 1);
                        renderInfoNext.setFacing.addAll(renderInfo2.setFacing);
                        renderInfoNext.setFacing.add(side);
                        renderInfoList.add(renderInfoNext);
                    }
                }
            }
            this.profiler.endSection();
        }
        this.profiler.endStartSection("rebuild");
        final Set<RenderChunk> set = this.chunksToUpdate;
        final Set<RenderOverlay> set2 = this.overlaysToUpdate;
        this.chunksToUpdate = (Set<RenderChunk>)Sets.newLinkedHashSet();
        this.overlaysToUpdate = (Set<RenderOverlay>)Sets.newLinkedHashSet();
        final Iterator<ContainerLocalRenderInformation> iterator = this.renderInfos.iterator();
        while (iterator.hasNext()) {
            final ContainerLocalRenderInformation renderInfo = iterator.next();
            final RenderChunk renderChunk3 = renderInfo.renderChunk;
            final RenderOverlay renderOverlay2 = renderInfo.renderOverlay;
            if (renderChunk3.needsUpdate() || set.contains(renderChunk3)) {
                this.displayListEntitiesDirty = true;
                this.chunksToUpdate.add(renderChunk3);
            }
            if (renderOverlay2.needsUpdate() || set2.contains(renderOverlay2)) {
                this.displayListEntitiesDirty = true;
                this.overlaysToUpdate.add(renderOverlay2);
            }
        }
        this.chunksToUpdate.addAll(set);
        this.overlaysToUpdate.addAll(set2);
        this.profiler.endSection();
    }
    
    private Set<EnumFacing> getVisibleSides(final BlockPos pos) {
        final VisGraph visgraph = new VisGraph();
        final BlockPos posChunk = new BlockPos(pos.getX() & 0xFFFFFFF0, pos.getY() & 0xFFFFFFF0, pos.getZ() & 0xFFFFFFF0);
        for (final BlockPos.MutableBlockPos mutableBlockPos : BlockPos.getAllInBoxMutable(posChunk, posChunk.add(15, 15, 15))) {
            if (this.world.getBlockState((BlockPos)mutableBlockPos).isOpaqueCube()) {
                visgraph.setOpaqueCube((BlockPos)mutableBlockPos);
            }
        }
        return (Set<EnumFacing>)visgraph.getVisibleFacings(pos);
    }
    
    private RenderChunk getNeighborRenderChunk(final BlockPos posEye, final RenderChunk renderChunkBase, final EnumFacing side) {
        final BlockPos offset = renderChunkBase.getBlockPosOffset16(side);
        if (MathHelper.abs(posEye.getX() - offset.getX()) > this.renderDistanceChunks * 16) {
            return null;
        }
        if (offset.getY() < 0 || offset.getY() >= 256) {
            return null;
        }
        if (MathHelper.abs(posEye.getZ() - offset.getZ()) > this.renderDistanceChunks * 16) {
            return null;
        }
        return this.viewFrustum.getRenderChunk(offset);
    }
    
    private RenderOverlay getNeighborRenderOverlay(final BlockPos posEye, final RenderChunk renderChunkBase, final EnumFacing side) {
        final BlockPos offset = renderChunkBase.getBlockPosOffset16(side);
        if (MathHelper.abs(posEye.getX() - offset.getX()) > this.renderDistanceChunks * 16) {
            return null;
        }
        if (offset.getY() < 0 || offset.getY() >= 256) {
            return null;
        }
        if (MathHelper.abs(posEye.getZ() - offset.getZ()) > this.renderDistanceChunks * 16) {
            return null;
        }
        return this.viewFrustum.getRenderOverlay(offset);
    }
    
    protected Vector3f getViewVector(final Entity entity, final double partialTicks) {
        return super.getViewVector(entity, partialTicks);
    }
    
    public int renderBlockLayer(final BlockRenderLayer layer, final double partialTicks, final int pass, final Entity entity) {
        RenderHelper.disableStandardItemLighting();
        if (layer == BlockRenderLayer.TRANSLUCENT) {
            this.profiler.startSection("translucent_sort");
            final double posX = RenderSchematic.PLAYER_POSITION_OFFSET.x;
            final double posY = RenderSchematic.PLAYER_POSITION_OFFSET.y;
            final double posZ = RenderSchematic.PLAYER_POSITION_OFFSET.z;
            final double deltaX = posX - this.prevRenderSortX;
            final double deltaY = posY - this.prevRenderSortY;
            final double deltaZ = posZ - this.prevRenderSortZ;
            if (deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ > 1.0) {
                this.prevRenderSortX = posX;
                this.prevRenderSortY = posY;
                this.prevRenderSortZ = posZ;
                int count = 0;
                for (final ContainerLocalRenderInformation renderInfo : this.renderInfos) {
                    if (renderInfo.renderChunk.compiledChunk.isLayerStarted(layer) && count++ < 15) {
                        this.renderDispatcher.updateTransparencyLater(renderInfo.renderChunk);
                        this.renderDispatcherOverlay.updateTransparencyLater((RenderChunk)renderInfo.renderOverlay);
                    }
                }
            }
            this.profiler.endSection();
        }
        this.profiler.startSection("filterempty");
        int count2 = 0;
        final boolean isTranslucent = layer == BlockRenderLayer.TRANSLUCENT;
        final int start = isTranslucent ? (this.renderInfos.size() - 1) : 0;
        for (int end = isTranslucent ? -1 : this.renderInfos.size(), step = isTranslucent ? -1 : 1, index = start; index != end; index += step) {
            final ContainerLocalRenderInformation renderInfo2 = this.renderInfos.get(index);
            final RenderChunk renderChunk = renderInfo2.renderChunk;
            final RenderOverlay renderOverlay = renderInfo2.renderOverlay;
            if (!renderChunk.getCompiledChunk().isLayerEmpty(layer)) {
                ++count2;
                this.renderContainer.addRenderChunk(renderChunk, layer);
            }
            if (isTranslucent && renderOverlay != null && !renderOverlay.getCompiledChunk().isLayerEmpty(layer)) {
                ++count2;
                this.renderContainer.addRenderOverlay(renderOverlay);
            }
        }
        this.profiler.endStartSection("render_" + layer);
        this.renderBlockLayer(layer);
        this.profiler.endSection();
        return count2;
    }
    
    private void renderBlockLayer(final BlockRenderLayer layer) {
        this.mc.entityRenderer.enableLightmap();
        this.renderContainer.renderChunkLayer(layer);
        this.mc.entityRenderer.disableLightmap();
    }
    
    public void updateClouds() {
    }
    
    public void renderSky(final float partialTicks, final int pass) {
    }
    
    public void renderClouds(final float partialTicks, final int pass, final double x, final double y, final double z) {
    }
    
    public boolean hasCloudFog(final double x, final double y, final double z, final float partialTicks) {
        return false;
    }
    
    public void updateChunks(final long finishTimeNano) {
        this.displayListEntitiesDirty |= this.renderDispatcher.runChunkUploads(finishTimeNano);
        final Iterator<RenderChunk> chunkIterator = this.chunksToUpdate.iterator();
        while (chunkIterator.hasNext()) {
            final RenderChunk renderChunk = chunkIterator.next();
            if (!this.renderDispatcher.updateChunkLater(renderChunk)) {
                break;
            }
            renderChunk.clearNeedsUpdate();
            chunkIterator.remove();
            final long diff = finishTimeNano - System.nanoTime();
            if (diff < 0L) {
                break;
            }
        }
        this.displayListEntitiesDirty |= this.renderDispatcherOverlay.runChunkUploads(finishTimeNano);
        final Iterator<RenderOverlay> overlayIterator = this.overlaysToUpdate.iterator();
        while (overlayIterator.hasNext()) {
            final RenderOverlay renderOverlay = overlayIterator.next();
            if (!this.renderDispatcherOverlay.updateChunkLater((RenderChunk)renderOverlay)) {
                break;
            }
            renderOverlay.clearNeedsUpdate();
            overlayIterator.remove();
            final long diff2 = finishTimeNano - System.nanoTime();
            if (diff2 < 0L) {
                break;
            }
        }
    }
    
    public void renderWorldBorder(final Entity entity, final float partialTicks) {
    }
    
    public void drawBlockDamageTexture(final Tessellator tessellator, final BufferBuilder buffer, final Entity entity, final float partialTicks) {
    }
    
    public void drawSelectionBox(final EntityPlayer player, final RayTraceResult rayTraceResult, final int execute, final float partialTicks) {
    }
    
    public void notifyBlockUpdate(final World world, final BlockPos pos, final IBlockState oldState, final IBlockState newState, final int flags) {
        final int x = pos.getX();
        final int y = pos.getY();
        final int z = pos.getZ();
        this.markBlocksForUpdate(x - 1, y - 1, z - 1, x + 1, y + 1, z + 1, (flags & 0x8) != 0x0);
    }
    
    public void notifyLightSet(final BlockPos pos) {
        final int x = pos.getX();
        final int y = pos.getY();
        final int z = pos.getZ();
        this.markBlocksForUpdate(x - 1, y - 1, z - 1, x + 1, y + 1, z + 1, true);
    }
    
    public void markBlockRangeForRenderUpdate(final int x1, final int y1, final int z1, final int x2, final int y2, final int z2) {
        this.markBlocksForUpdate(x1 - 1, y1 - 1, z1 - 1, x2 + 1, y2 + 1, z2 + 1, true);
    }
    
    private void markBlocksForUpdate(final int x1, final int y1, final int z1, final int x2, final int y2, final int z2, final boolean needsUpdate) {
        if (this.world == null) {
            return;
        }
        final MBlockPos position = this.world.position;
        this.viewFrustum.markBlocksForUpdate(x1 - position.x, y1 - position.y, z1 - position.z, x2 - position.x, y2 - position.y, z2 - position.z, needsUpdate);
    }
    
    public void playRecord(final SoundEvent soundEvent, final BlockPos pos) {
    }
    
    public void playSoundToAllNearExcept(final EntityPlayer player, final SoundEvent soundEvent, final SoundCategory category, final double x, final double y, final double z, final float volume, final float pitch) {
    }
    
    public void spawnParticle(final int particleID, final boolean ignoreRange, final double x, final double y, final double z, final double xOffset, final double yOffset, final double zOffset, final int... parameters) {
    }
    
    public void spawnParticle(final int particleID, final boolean ignoreRange, final boolean minParticles, final double x, final double y, final double z, final double xOffset, final double yOffset, final double zOffset, final int... parameters) {
    }
    
    public void onEntityAdded(final Entity entity) {
    }
    
    public void onEntityRemoved(final Entity entity) {
    }
    
    public void deleteAllDisplayLists() {
    }
    
    public void broadcastSound(final int soundID, final BlockPos pos, final int data) {
    }
    
    public void playEvent(final EntityPlayer player, final int type, final BlockPos pos, final int data) {
    }
    
    public void sendBlockBreakProgress(final int breakerId, final BlockPos pos, final int progress) {
    }
    
    public boolean hasNoChunkUpdates() {
        return this.chunksToUpdate.isEmpty() && this.renderDispatcher.hasNoChunkUpdates();
    }
    
    public void setDisplayListEntitiesDirty() {
        this.displayListEntitiesDirty = true;
    }
    
    public void updateTileEntities(final Collection<TileEntity> tileEntitiesToRemove, final Collection<TileEntity> tileEntitiesToAdd) {
    }
    
    static {
        INSTANCE = new RenderSchematic(Minecraft.getMinecraft());
        SHADER_ALPHA = new ShaderProgram("schematica", null, "shaders/alpha.frag");
        PLAYER_POSITION_OFFSET = new Vector3d();
    }
    
    @SideOnly(Side.CLIENT)
    class ContainerLocalRenderInformation
    {
        final RenderChunk renderChunk;
        final RenderOverlay renderOverlay;
        final EnumFacing facing;
        final Set<EnumFacing> setFacing;
        final int counter;
        
        ContainerLocalRenderInformation(final RenderChunk renderChunk, final RenderOverlay renderOverlay, final EnumFacing facing, final int counter) {
            this.setFacing = EnumSet.noneOf(EnumFacing.class);
            this.renderChunk = renderChunk;
            this.renderOverlay = renderOverlay;
            this.facing = facing;
            this.counter = counter;
        }
    }
}
