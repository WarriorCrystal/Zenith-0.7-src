//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package com.github.lunatrius.schematica.handler.client;

import net.minecraftforge.client.settings.IKeyConflictContext;
import net.minecraftforge.client.settings.KeyModifier;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.world.World;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.ForgeHooks;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import com.github.lunatrius.schematica.client.renderer.RenderSchematic;
import net.minecraft.util.math.MathHelper;
import com.github.lunatrius.schematica.client.world.SchematicWorld;
import com.github.lunatrius.schematica.proxy.ClientProxy;
import com.github.lunatrius.schematica.client.gui.control.GuiSchematicControl;
import com.github.lunatrius.schematica.client.gui.save.GuiSchematicSave;
import net.minecraft.client.gui.GuiScreen;
import com.github.lunatrius.schematica.client.gui.load.GuiSchematicLoad;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;

public class InputHandler
{
    public static final InputHandler INSTANCE;
    private static final KeyBinding KEY_BINDING_LOAD;
    private static final KeyBinding KEY_BINDING_SAVE;
    private static final KeyBinding KEY_BINDING_CONTROL;
    private static final KeyBinding KEY_BINDING_LAYER_INC;
    private static final KeyBinding KEY_BINDING_LAYER_DEC;
    private static final KeyBinding KEY_BINDING_LAYER_TOGGLE;
    private static final KeyBinding KEY_BINDING_RENDER_TOGGLE;
    private static final KeyBinding KEY_BINDING_MOVE_HERE;
    private static final KeyBinding KEY_BINDING_PICK_BLOCK;
    public static final KeyBinding[] KEY_BINDINGS;
    private final Minecraft minecraft;
    
    private InputHandler() {
        this.minecraft = Minecraft.getMinecraft();
    }
    
    @SubscribeEvent
    public void onKeyInput(final InputEvent event) {
        if (this.minecraft.currentScreen == null) {
            if (InputHandler.KEY_BINDING_LOAD.isPressed()) {
                this.minecraft.displayGuiScreen((GuiScreen)new GuiSchematicLoad(this.minecraft.currentScreen));
            }
            if (InputHandler.KEY_BINDING_SAVE.isPressed()) {
                this.minecraft.displayGuiScreen((GuiScreen)new GuiSchematicSave(this.minecraft.currentScreen));
            }
            if (InputHandler.KEY_BINDING_CONTROL.isPressed()) {
                this.minecraft.displayGuiScreen((GuiScreen)new GuiSchematicControl(this.minecraft.currentScreen));
            }
            if (InputHandler.KEY_BINDING_LAYER_INC.isPressed()) {
                final SchematicWorld schematic = ClientProxy.schematic;
                if (schematic != null && schematic.layerMode != SchematicWorld.LayerMode.ALL) {
                    schematic.renderingLayer = MathHelper.clamp(schematic.renderingLayer + 1, 0, schematic.getHeight() - 1);
                    RenderSchematic.INSTANCE.refresh();
                }
            }
            if (InputHandler.KEY_BINDING_LAYER_DEC.isPressed()) {
                final SchematicWorld schematic = ClientProxy.schematic;
                if (schematic != null && schematic.layerMode != SchematicWorld.LayerMode.ALL) {
                    schematic.renderingLayer = MathHelper.clamp(schematic.renderingLayer - 1, 0, schematic.getHeight() - 1);
                    RenderSchematic.INSTANCE.refresh();
                }
            }
            if (InputHandler.KEY_BINDING_LAYER_TOGGLE.isPressed()) {
                final SchematicWorld schematic = ClientProxy.schematic;
                if (schematic != null) {
                    schematic.layerMode = SchematicWorld.LayerMode.next(schematic.layerMode);
                    RenderSchematic.INSTANCE.refresh();
                }
            }
            if (InputHandler.KEY_BINDING_RENDER_TOGGLE.isPressed()) {
                final SchematicWorld schematic = ClientProxy.schematic;
                if (schematic != null) {
                    schematic.isRendering = !schematic.isRendering;
                    RenderSchematic.INSTANCE.refresh();
                }
            }
            if (InputHandler.KEY_BINDING_MOVE_HERE.isPressed()) {
                final SchematicWorld schematic = ClientProxy.schematic;
                if (schematic != null) {
                    ClientProxy.moveSchematicToPlayer(schematic);
                    RenderSchematic.INSTANCE.refresh();
                }
            }
            if (InputHandler.KEY_BINDING_PICK_BLOCK.isPressed()) {
                final SchematicWorld schematic = ClientProxy.schematic;
                if (schematic != null && schematic.isRendering) {
                    this.pickBlock(schematic, ClientProxy.objectMouseOver);
                }
            }
        }
    }
    
    private boolean pickBlock(final SchematicWorld schematic, final RayTraceResult objectMouseOver) {
        if (objectMouseOver == null) {
            return false;
        }
        if (objectMouseOver.typeOfHit == RayTraceResult.Type.MISS) {
            return false;
        }
        final EntityPlayerSP player = this.minecraft.player;
        if (!ForgeHooks.onPickBlock(objectMouseOver, (EntityPlayer)player, (World)schematic)) {
            return true;
        }
        if (player.capabilities.isCreativeMode) {
            final int slot = player.inventoryContainer.inventorySlots.size() - 10 + player.inventory.currentItem;
            this.minecraft.playerController.sendSlotPacket(player.inventory.getStackInSlot(player.inventory.currentItem), slot);
            return true;
        }
        return false;
    }
    
    static {
        INSTANCE = new InputHandler();
        KEY_BINDING_LOAD = new KeyBinding("schematica.key.load", 181, "schematica.key.category");
        KEY_BINDING_SAVE = new KeyBinding("schematica.key.save", 55, "schematica.key.category");
        KEY_BINDING_CONTROL = new KeyBinding("schematica.key.control", 74, "schematica.key.category");
        KEY_BINDING_LAYER_INC = new KeyBinding("schematica.key.layerInc", 0, "schematica.key.category");
        KEY_BINDING_LAYER_DEC = new KeyBinding("schematica.key.layerDec", 0, "schematica.key.category");
        KEY_BINDING_LAYER_TOGGLE = new KeyBinding("schematica.key.layerToggle", 0, "schematica.key.category");
        KEY_BINDING_RENDER_TOGGLE = new KeyBinding("schematica.key.renderToggle", 0, "schematica.key.category");
        KEY_BINDING_MOVE_HERE = new KeyBinding("schematica.key.moveHere", 0, "schematica.key.category");
        KEY_BINDING_PICK_BLOCK = new KeyBinding("schematica.key.pickBlock", (IKeyConflictContext)KeyConflictContext.IN_GAME, KeyModifier.SHIFT, -98, "schematica.key.category");
        KEY_BINDINGS = new KeyBinding[] { InputHandler.KEY_BINDING_LOAD, InputHandler.KEY_BINDING_SAVE, InputHandler.KEY_BINDING_CONTROL, InputHandler.KEY_BINDING_LAYER_INC, InputHandler.KEY_BINDING_LAYER_DEC, InputHandler.KEY_BINDING_LAYER_TOGGLE, InputHandler.KEY_BINDING_RENDER_TOGGLE, InputHandler.KEY_BINDING_MOVE_HERE, InputHandler.KEY_BINDING_PICK_BLOCK };
    }
}
