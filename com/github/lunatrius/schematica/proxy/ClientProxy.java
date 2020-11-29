//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package com.github.lunatrius.schematica.proxy;

import com.github.lunatrius.schematica.api.ISchematic;
import com.github.lunatrius.schematica.world.schematic.SchematicFormat;
import com.github.lunatrius.schematica.client.printer.SchematicPrinter;
import java.io.IOException;
import com.github.lunatrius.schematica.reference.Reference;
import java.io.File;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraft.command.ICommand;
import com.github.lunatrius.schematica.command.client.CommandSchematicaReplace;
import net.minecraftforge.client.ClientCommandHandler;
import com.github.lunatrius.schematica.handler.client.WorldHandler;
import com.github.lunatrius.schematica.handler.client.OverlayHandler;
import com.github.lunatrius.schematica.handler.client.GuiHandler;
import com.github.lunatrius.schematica.client.renderer.RenderSchematic;
import com.github.lunatrius.schematica.handler.client.RenderTickHandler;
import com.github.lunatrius.schematica.handler.client.TickHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import com.github.lunatrius.schematica.handler.client.InputHandler;
import net.minecraftforge.fml.client.config.GuiConfigEntries;
import com.github.lunatrius.schematica.handler.ConfigurationHandler;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraft.util.math.MathHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.RayTraceResult;
import com.github.lunatrius.core.util.math.MBlockPos;
import com.github.lunatrius.schematica.client.world.SchematicWorld;
import net.minecraft.util.EnumFacing;
import com.github.lunatrius.core.util.vector.Vector3d;

public class ClientProxy extends CommonProxy
{
    public static boolean isRenderingGuide;
    public static boolean isPendingReset;
    public static final Vector3d playerPosition;
    public static EnumFacing orientation;
    public static int rotationRender;
    public static SchematicWorld schematic;
    public static final MBlockPos pointA;
    public static final MBlockPos pointB;
    public static final MBlockPos pointMin;
    public static final MBlockPos pointMax;
    public static EnumFacing axisFlip;
    public static EnumFacing axisRotation;
    public static RayTraceResult objectMouseOver;
    private static final Minecraft MINECRAFT;
    
    public static void setPlayerData(final EntityPlayer player, final float partialTicks) {
        ClientProxy.playerPosition.x = player.lastTickPosX + (player.posX - player.lastTickPosX) * partialTicks;
        ClientProxy.playerPosition.y = player.lastTickPosY + (player.posY - player.lastTickPosY) * partialTicks;
        ClientProxy.playerPosition.z = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * partialTicks;
        ClientProxy.orientation = getOrientation(player);
        ClientProxy.rotationRender = (MathHelper.floor(player.rotationYaw / 90.0f) & 0x3);
    }
    
    private static EnumFacing getOrientation(final EntityPlayer player) {
        if (player.rotationPitch > 45.0f) {
            return EnumFacing.DOWN;
        }
        if (player.rotationPitch < -45.0f) {
            return EnumFacing.UP;
        }
        switch (MathHelper.floor(player.rotationYaw / 90.0 + 0.5) & 0x3) {
            case 0: {
                return EnumFacing.SOUTH;
            }
            case 1: {
                return EnumFacing.WEST;
            }
            case 2: {
                return EnumFacing.NORTH;
            }
            case 3: {
                return EnumFacing.EAST;
            }
            default: {
                return null;
            }
        }
    }
    
    public static void updatePoints() {
        ClientProxy.pointMin.x = Math.min(ClientProxy.pointA.x, ClientProxy.pointB.x);
        ClientProxy.pointMin.y = Math.min(ClientProxy.pointA.y, ClientProxy.pointB.y);
        ClientProxy.pointMin.z = Math.min(ClientProxy.pointA.z, ClientProxy.pointB.z);
        ClientProxy.pointMax.x = Math.max(ClientProxy.pointA.x, ClientProxy.pointB.x);
        ClientProxy.pointMax.y = Math.max(ClientProxy.pointA.y, ClientProxy.pointB.y);
        ClientProxy.pointMax.z = Math.max(ClientProxy.pointA.z, ClientProxy.pointB.z);
    }
    
    public static void movePointToPlayer(final MBlockPos point) {
        point.x = (int)Math.floor(ClientProxy.playerPosition.x);
        point.y = (int)Math.floor(ClientProxy.playerPosition.y);
        point.z = (int)Math.floor(ClientProxy.playerPosition.z);
        switch (ClientProxy.rotationRender) {
            case 0: {
                --point.x;
                ++point.z;
                break;
            }
            case 1: {
                --point.x;
                --point.z;
                break;
            }
            case 2: {
                ++point.x;
                --point.z;
                break;
            }
            case 3: {
                ++point.x;
                ++point.z;
                break;
            }
        }
    }
    
    public static void moveSchematicToPlayer(final SchematicWorld schematic) {
        if (schematic != null) {
            final MBlockPos position = schematic.position;
            position.x = (int)Math.floor(ClientProxy.playerPosition.x);
            position.y = (int)Math.floor(ClientProxy.playerPosition.y);
            position.z = (int)Math.floor(ClientProxy.playerPosition.z);
            switch (ClientProxy.rotationRender) {
                case 0: {
                    final MBlockPos mBlockPos = position;
                    mBlockPos.x -= schematic.getWidth();
                    final MBlockPos mBlockPos2 = position;
                    ++mBlockPos2.z;
                    break;
                }
                case 1: {
                    final MBlockPos mBlockPos3 = position;
                    mBlockPos3.x -= schematic.getWidth();
                    final MBlockPos mBlockPos4 = position;
                    mBlockPos4.z -= schematic.getLength();
                    break;
                }
                case 2: {
                    final MBlockPos mBlockPos5 = position;
                    ++mBlockPos5.x;
                    final MBlockPos mBlockPos6 = position;
                    mBlockPos6.z -= schematic.getLength();
                    break;
                }
                case 3: {
                    final MBlockPos mBlockPos7 = position;
                    ++mBlockPos7.x;
                    final MBlockPos mBlockPos8 = position;
                    ++mBlockPos8.z;
                    break;
                }
            }
        }
    }
    
    @Override
    public void preInit(final FMLPreInitializationEvent event) {
        super.preInit(event);
        final Property[] array;
        final Property[] sliders = array = new Property[] { ConfigurationHandler.propAlpha, ConfigurationHandler.propBlockDelta, ConfigurationHandler.propRenderDistance, ConfigurationHandler.propPlaceDelay, ConfigurationHandler.propTimeout, ConfigurationHandler.propPlaceDistance };
        for (final Property prop : array) {
            prop.setConfigEntryClass((Class)GuiConfigEntries.NumberSliderEntry.class);
        }
        for (final KeyBinding keyBinding : InputHandler.KEY_BINDINGS) {
            ClientRegistry.registerKeyBinding(keyBinding);
        }
    }
    
    @Override
    public void init(final FMLInitializationEvent event) {
        super.init(event);
        MinecraftForge.EVENT_BUS.register((Object)InputHandler.INSTANCE);
        MinecraftForge.EVENT_BUS.register((Object)TickHandler.INSTANCE);
        MinecraftForge.EVENT_BUS.register((Object)RenderTickHandler.INSTANCE);
        MinecraftForge.EVENT_BUS.register((Object)ConfigurationHandler.INSTANCE);
        MinecraftForge.EVENT_BUS.register((Object)RenderSchematic.INSTANCE);
        MinecraftForge.EVENT_BUS.register((Object)GuiHandler.INSTANCE);
        MinecraftForge.EVENT_BUS.register((Object)new OverlayHandler());
        MinecraftForge.EVENT_BUS.register((Object)new WorldHandler());
        ClientCommandHandler.instance.registerCommand((ICommand)new CommandSchematicaReplace());
    }
    
    @Override
    public void postInit(final FMLPostInitializationEvent event) {
        super.postInit(event);
        this.resetSettings();
    }
    
    @Override
    public File getDataDirectory() {
        final File file = ClientProxy.MINECRAFT.gameDir;
        try {
            return file.getCanonicalFile();
        }
        catch (IOException e) {
            Reference.logger.debug("Could not canonize path!", (Throwable)e);
            return file;
        }
    }
    
    @Override
    public void resetSettings() {
        super.resetSettings();
        SchematicPrinter.INSTANCE.setEnabled(true);
        this.unloadSchematic();
        ClientProxy.isRenderingGuide = false;
        ClientProxy.playerPosition.set(0.0, 0.0, 0.0);
        ClientProxy.orientation = null;
        ClientProxy.rotationRender = 0;
        ClientProxy.pointA.set(0, 0, 0);
        ClientProxy.pointB.set(0, 0, 0);
        updatePoints();
    }
    
    @Override
    public void unloadSchematic() {
        ClientProxy.schematic = null;
        RenderSchematic.INSTANCE.setWorldAndLoadRenderers(null);
        SchematicPrinter.INSTANCE.setSchematic(null);
    }
    
    @Override
    public boolean loadSchematic(final EntityPlayer player, final File directory, final String filename) {
        final ISchematic schematic = SchematicFormat.readFromFile(directory, filename);
        if (schematic == null) {
            return false;
        }
        final SchematicWorld world = new SchematicWorld(schematic);
        Reference.logger.debug("Loaded {} [w:{},h:{},l:{}]", (Object)filename, (Object)world.getWidth(), (Object)world.getHeight(), (Object)world.getLength());
        ClientProxy.schematic = world;
        RenderSchematic.INSTANCE.setWorldAndLoadRenderers(world);
        SchematicPrinter.INSTANCE.setSchematic(world);
        return world.isRendering = true;
    }
    
    @Override
    public boolean isPlayerQuotaExceeded(final EntityPlayer player) {
        return false;
    }
    
    @Override
    public File getPlayerSchematicDirectory(final EntityPlayer player, final boolean privateDirectory) {
        return ConfigurationHandler.schematicDirectory;
    }
    
    static {
        ClientProxy.isRenderingGuide = false;
        ClientProxy.isPendingReset = false;
        playerPosition = new Vector3d();
        ClientProxy.orientation = null;
        ClientProxy.rotationRender = 0;
        ClientProxy.schematic = null;
        pointA = new MBlockPos();
        pointB = new MBlockPos();
        pointMin = new MBlockPos();
        pointMax = new MBlockPos();
        ClientProxy.axisFlip = EnumFacing.UP;
        ClientProxy.axisRotation = EnumFacing.UP;
        ClientProxy.objectMouseOver = null;
        MINECRAFT = Minecraft.getMinecraft();
    }
}
