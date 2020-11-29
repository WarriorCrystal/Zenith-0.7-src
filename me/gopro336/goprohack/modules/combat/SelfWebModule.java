//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.modules.combat;

import net.minecraft.item.ItemStack;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemBlock;
import net.minecraft.block.Block;
import java.util.Iterator;
import java.util.function.Predicate;
import net.minecraft.util.math.Vec3d;
import me.gopro336.goprohack.util.BlockInteractionHelper;
import net.minecraft.util.math.BlockPos;
import java.util.ArrayList;
import net.minecraft.init.Blocks;
import me.gopro336.goprohack.util.entity.PlayerUtil;
import me.gopro336.goprohack.events.MinecraftEvent;
import me.zero.alpine.fork.listener.EventHandler;
import me.gopro336.goprohack.events.player.EventPlayerMotionUpdate;
import me.zero.alpine.fork.listener.Listener;
import me.gopro336.goprohack.modules.Value;
import me.gopro336.goprohack.modules.Module;

public class SelfWebModule extends Module
{
    public final Value<Boolean> TrapCheck;
    @EventHandler
    private Listener<EventPlayerMotionUpdate> OnPlayerUpdate;
    
    public SelfWebModule() {
        super("SelfWeb", new String[] { "SW" }, "Automatically traps yourself in web", "NONE", -1, ModuleType.COMBAT);
        this.TrapCheck = new Value<Boolean>("TrapCheck", new String[] { "HC" }, "Only functions if you're trapped", false);
        int slot;
        BlockPos l_CenterPos;
        ArrayList<BlockPos> BlocksToFill;
        BlockPos l_PosToFill;
        final Iterator<BlockPos> iterator;
        BlockPos l_Pos;
        BlockInteractionHelper.ValidResult l_Result;
        int lastSlot;
        float[] rotations;
        this.OnPlayerUpdate = new Listener<EventPlayerMotionUpdate>(p_Event -> {
            if (p_Event.getEra() == MinecraftEvent.Era.PRE) {
                if (!this.TrapCheck.getValue() || PlayerUtil.IsPlayerTrapped()) {
                    slot = this.findStackHotbar(Blocks.WEB);
                    if (slot != -1) {
                        l_CenterPos = PlayerUtil.GetLocalPlayerPosFloored();
                        BlocksToFill = new ArrayList<BlockPos>();
                        switch (PlayerUtil.GetFacing()) {
                            case East: {
                                BlocksToFill.add(l_CenterPos.down().up());
                                break;
                            }
                            case North: {
                                BlocksToFill.add(l_CenterPos.down().up());
                                break;
                            }
                            case South: {
                                BlocksToFill.add(l_CenterPos.down().up());
                                break;
                            }
                            case West: {
                                BlocksToFill.add(l_CenterPos.down().up());
                                break;
                            }
                        }
                        l_PosToFill = null;
                        BlocksToFill.iterator();
                        while (iterator.hasNext()) {
                            l_Pos = iterator.next();
                            l_Result = BlockInteractionHelper.valid(l_Pos);
                            if (l_Result != BlockInteractionHelper.ValidResult.Ok) {
                                continue;
                            }
                            else {
                                l_PosToFill = l_Pos;
                                break;
                            }
                        }
                        if (l_PosToFill != null) {
                            lastSlot = this.mc.player.inventory.currentItem;
                            this.mc.player.inventory.currentItem = slot;
                            this.mc.playerController.updateController();
                            p_Event.cancel();
                            rotations = BlockInteractionHelper.getLegitRotations(new Vec3d((double)l_PosToFill.getX(), (double)l_PosToFill.getY(), (double)l_PosToFill.getZ()));
                            PlayerUtil.PacketFacePitchAndYaw(rotations[1], rotations[0]);
                            BlockInteractionHelper.place(l_PosToFill, 5.0f, false, false);
                            this.Finish(lastSlot);
                        }
                    }
                }
            }
        }, (Predicate<EventPlayerMotionUpdate>[])new Predicate[0]);
    }
    
    @Override
    public void toggleNoSave() {
    }
    
    private void Finish(final int p_LastSlot) {
        if (!this.slotEqualsBlock(p_LastSlot, Blocks.WEB)) {
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
}
