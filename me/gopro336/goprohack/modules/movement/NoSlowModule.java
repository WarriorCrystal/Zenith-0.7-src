//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.modules.movement;

import net.minecraft.util.MovementInput;
import net.minecraft.util.EnumFacing;
import me.gopro336.goprohack.util.entity.PlayerUtil;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.network.Packet;
import net.minecraft.util.math.BlockPos;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.item.ItemShield;
import java.util.function.Predicate;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;
import me.gopro336.goprohack.gui.GoproGuiScreen;
import me.gopro336.goprohack.events.network.EventNetworkPostPacketEvent;
import me.gopro336.goprohack.events.client.EventClientTick;
import me.zero.alpine.fork.listener.EventHandler;
import me.gopro336.goprohack.events.player.EventPlayerUpdateMoveState;
import me.zero.alpine.fork.listener.Listener;
import me.gopro336.goprohack.modules.Value;
import me.gopro336.goprohack.modules.Module;

public final class NoSlowModule extends Module
{
    public final Value<Boolean> InventoryMove;
    public final Value<Boolean> OnlyOnCustom;
    public final Value<Boolean> items;
    public final Value<Boolean> ice;
    public final Value<Boolean> NCPStrict;
    @EventHandler
    private Listener<EventPlayerUpdateMoveState> OnIsKeyPressed;
    @EventHandler
    private Listener<EventClientTick> OnTick;
    @EventHandler
    private Listener<EventPlayerUpdateMoveState> OnUpdateMoveState;
    @EventHandler
    private Listener<EventNetworkPostPacketEvent> PacketEvent;
    
    public NoSlowModule() {
        super("NoSlow", new String[] { "AntiSlow", "NoSlowdown", "AntiSlowdown" }, "Allows you to move faster with things that slow you down", "NONE", 5131167, ModuleType.MOVEMENT);
        this.InventoryMove = new Value<Boolean>("InventoryMove", new String[] { "InvMove", "InventoryMove", "GUIMove" }, "Allows you to move while guis are open", true);
        this.OnlyOnCustom = new Value<Boolean>("OnlyOnCustom", new String[] { "Custom" }, "Only inventory move on custom GUIs", true);
        this.items = new Value<Boolean>("Items", new String[] { "it" }, "Disables the slowness from using items (shields, eating, etc).", true);
        this.ice = new Value<Boolean>("Ice", new String[] { "ic" }, "Disables slowness from walking on ice.", true);
        this.NCPStrict = new Value<Boolean>("NCPStrict", new String[] { "NCP" }, "Allows NoSlow to work on nocheatplus", true);
        MovementInput movementInput;
        MovementInput movementInput2;
        MovementInput movementInput3;
        MovementInput movementInput4;
        this.OnIsKeyPressed = new Listener<EventPlayerUpdateMoveState>(event -> {
            if (this.InventoryMove.getValue() && this.mc.currentScreen != null) {
                if (!this.OnlyOnCustom.getValue() || this.mc.currentScreen instanceof GoproGuiScreen) {
                    if (!(this.mc.currentScreen instanceof GoproGuiScreen)) {
                        if (Keyboard.isKeyDown(200)) {
                            GoproGuiScreen.UpdateRotationPitch(-5.0f);
                        }
                        if (Keyboard.isKeyDown(208)) {
                            GoproGuiScreen.UpdateRotationPitch(5.0f);
                        }
                        if (Keyboard.isKeyDown(205)) {
                            GoproGuiScreen.UpdateRotationYaw(5.0f);
                        }
                        if (Keyboard.isKeyDown(203)) {
                            GoproGuiScreen.UpdateRotationYaw(-5.0f);
                        }
                    }
                    this.mc.player.movementInput.moveStrafe = 0.0f;
                    this.mc.player.movementInput.moveForward = 0.0f;
                    KeyBinding.setKeyBindState(this.mc.gameSettings.keyBindForward.getKeyCode(), Keyboard.isKeyDown(this.mc.gameSettings.keyBindForward.getKeyCode()));
                    if (Keyboard.isKeyDown(this.mc.gameSettings.keyBindForward.getKeyCode())) {
                        movementInput = this.mc.player.movementInput;
                        ++movementInput.moveForward;
                        this.mc.player.movementInput.forwardKeyDown = true;
                    }
                    else {
                        this.mc.player.movementInput.forwardKeyDown = false;
                    }
                    KeyBinding.setKeyBindState(this.mc.gameSettings.keyBindBack.getKeyCode(), Keyboard.isKeyDown(this.mc.gameSettings.keyBindBack.getKeyCode()));
                    if (Keyboard.isKeyDown(this.mc.gameSettings.keyBindBack.getKeyCode())) {
                        movementInput2 = this.mc.player.movementInput;
                        --movementInput2.moveForward;
                        this.mc.player.movementInput.backKeyDown = true;
                    }
                    else {
                        this.mc.player.movementInput.backKeyDown = false;
                    }
                    KeyBinding.setKeyBindState(this.mc.gameSettings.keyBindLeft.getKeyCode(), Keyboard.isKeyDown(this.mc.gameSettings.keyBindLeft.getKeyCode()));
                    if (Keyboard.isKeyDown(this.mc.gameSettings.keyBindLeft.getKeyCode())) {
                        movementInput3 = this.mc.player.movementInput;
                        ++movementInput3.moveStrafe;
                        this.mc.player.movementInput.leftKeyDown = true;
                    }
                    else {
                        this.mc.player.movementInput.leftKeyDown = false;
                    }
                    KeyBinding.setKeyBindState(this.mc.gameSettings.keyBindRight.getKeyCode(), Keyboard.isKeyDown(this.mc.gameSettings.keyBindRight.getKeyCode()));
                    if (Keyboard.isKeyDown(this.mc.gameSettings.keyBindRight.getKeyCode())) {
                        movementInput4 = this.mc.player.movementInput;
                        --movementInput4.moveStrafe;
                        this.mc.player.movementInput.rightKeyDown = true;
                    }
                    else {
                        this.mc.player.movementInput.rightKeyDown = false;
                    }
                    KeyBinding.setKeyBindState(this.mc.gameSettings.keyBindJump.getKeyCode(), Keyboard.isKeyDown(this.mc.gameSettings.keyBindJump.getKeyCode()));
                    this.mc.player.movementInput.jump = Keyboard.isKeyDown(this.mc.gameSettings.keyBindJump.getKeyCode());
                }
            }
            return;
        }, (Predicate<EventPlayerUpdateMoveState>[])new Predicate[0]);
        this.OnTick = new Listener<EventClientTick>(p_Event -> {
            if (this.mc.player.isHandActive() && this.mc.player.getHeldItem(this.mc.player.getActiveHand()).getItem() instanceof ItemShield && (this.mc.player.movementInput.moveStrafe != 0.0f || (this.mc.player.movementInput.moveForward != 0.0f && this.mc.player.getItemInUseMaxCount() >= 8))) {
                this.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, this.mc.player.getHorizontalFacing()));
            }
            if (this.ice.getValue()) {
                if (this.mc.player.getRidingEntity() != null) {
                    Blocks.ICE.setDefaultSlipperiness(0.98f);
                    Blocks.FROSTED_ICE.setDefaultSlipperiness(0.98f);
                    Blocks.PACKED_ICE.setDefaultSlipperiness(0.98f);
                }
                else {
                    Blocks.ICE.setDefaultSlipperiness(0.45f);
                    Blocks.FROSTED_ICE.setDefaultSlipperiness(0.45f);
                    Blocks.PACKED_ICE.setDefaultSlipperiness(0.45f);
                }
            }
            return;
        }, (Predicate<EventClientTick>[])new Predicate[0]);
        MovementInput movementInput5;
        MovementInput movementInput6;
        this.OnUpdateMoveState = new Listener<EventPlayerUpdateMoveState>(event -> {
            if (this.items.getValue() && this.mc.player.isHandActive() && !this.mc.player.isRiding()) {
                movementInput5 = this.mc.player.movementInput;
                movementInput5.moveForward /= 0.2f;
                movementInput6 = this.mc.player.movementInput;
                movementInput6.moveStrafe /= 0.2f;
            }
            return;
        }, (Predicate<EventPlayerUpdateMoveState>[])new Predicate[0]);
        this.PacketEvent = new Listener<EventNetworkPostPacketEvent>(p_Event -> {
            if (p_Event.getPacket() instanceof CPacketPlayer && this.NCPStrict.getValue() && this.items.getValue() && this.mc.player.isHandActive() && !this.mc.player.isRiding()) {
                this.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.ABORT_DESTROY_BLOCK, PlayerUtil.GetLocalPlayerPosFloored(), EnumFacing.DOWN));
            }
        }, (Predicate<EventNetworkPostPacketEvent>[])new Predicate[0]);
    }
    
    @Override
    public void onDisable() {
        super.onDisable();
        Blocks.ICE.setDefaultSlipperiness(0.98f);
        Blocks.FROSTED_ICE.setDefaultSlipperiness(0.98f);
        Blocks.PACKED_ICE.setDefaultSlipperiness(0.98f);
    }
}
