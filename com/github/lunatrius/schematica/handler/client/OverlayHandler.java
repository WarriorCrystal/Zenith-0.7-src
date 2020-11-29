//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package com.github.lunatrius.schematica.handler.client;

import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import java.util.Iterator;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import java.util.ArrayList;
import com.github.lunatrius.schematica.client.world.SchematicWorld;
import net.minecraft.util.math.Vec3i;
import com.github.lunatrius.schematica.block.state.BlockStateHelper;
import net.minecraft.block.Block;
import net.minecraft.util.math.RayTraceResult;
import com.github.lunatrius.schematica.client.renderer.RenderSchematic;
import com.github.lunatrius.schematica.proxy.ClientProxy;
import com.github.lunatrius.schematica.handler.ConfigurationHandler;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraft.client.Minecraft;

public class OverlayHandler
{
    private final Minecraft minecraft;
    private static final String SCHEMATICA_PREFIX;
    private static final String SCHEMATICA_SUFFIX;
    
    public OverlayHandler() {
        this.minecraft = Minecraft.getMinecraft();
    }
    
    @SubscribeEvent
    public void onText(final RenderGameOverlayEvent.Text event) {
        if (this.minecraft.gameSettings.showDebugInfo && ConfigurationHandler.showDebugInfo) {
            final SchematicWorld schematic = ClientProxy.schematic;
            if (schematic != null && schematic.isRendering) {
                final ArrayList<String> left = (ArrayList<String>)event.getLeft();
                final ArrayList<String> right = (ArrayList<String>)event.getRight();
                left.add("");
                left.add(OverlayHandler.SCHEMATICA_PREFIX + schematic.getDebugDimensions());
                left.add(OverlayHandler.SCHEMATICA_PREFIX + RenderSchematic.INSTANCE.getDebugInfoTileEntities());
                left.add(OverlayHandler.SCHEMATICA_PREFIX + RenderSchematic.INSTANCE.getDebugInfoRenders());
                final RayTraceResult rtr = ClientProxy.objectMouseOver;
                if (rtr != null && rtr.typeOfHit == RayTraceResult.Type.BLOCK) {
                    final BlockPos pos = rtr.getBlockPos();
                    final IBlockState blockState = schematic.getBlockState(pos);
                    right.add("");
                    right.add(String.valueOf(Block.REGISTRY.getNameForObject((Object)blockState.getBlock())) + OverlayHandler.SCHEMATICA_SUFFIX);
                    for (final String formattedProperty : BlockStateHelper.getFormattedProperties(blockState)) {
                        right.add(formattedProperty + OverlayHandler.SCHEMATICA_SUFFIX);
                    }
                    final BlockPos offsetPos = pos.add((Vec3i)schematic.position);
                    String lookMessage = String.format("Looking at: %d %d %d (%d %d %d)", pos.getX(), pos.getY(), pos.getZ(), offsetPos.getX(), offsetPos.getY(), offsetPos.getZ());
                    if (this.minecraft.objectMouseOver != null && this.minecraft.objectMouseOver.typeOfHit == RayTraceResult.Type.BLOCK) {
                        final BlockPos origPos = this.minecraft.objectMouseOver.getBlockPos();
                        if (offsetPos.equals((Object)origPos)) {
                            lookMessage += " (matches)";
                        }
                    }
                    left.add(OverlayHandler.SCHEMATICA_PREFIX + lookMessage);
                }
            }
        }
    }
    
    static {
        SCHEMATICA_PREFIX = "[" + TextFormatting.GOLD + "Schematica" + TextFormatting.RESET + "] ";
        SCHEMATICA_SUFFIX = " [" + TextFormatting.GOLD + "S" + TextFormatting.RESET + "]";
    }
}
