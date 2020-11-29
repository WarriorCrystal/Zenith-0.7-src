//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.modules.world;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.world.World;
import net.minecraft.util.math.BlockPos;
import java.util.function.Predicate;
import net.minecraft.client.Minecraft;
import me.gopro336.goprohack.events.player.EventPlayerDamageBlock;
import me.gopro336.goprohack.events.player.EventPlayerClickBlock;
import me.gopro336.goprohack.events.player.EventPlayerResetBlockRemoving;
import me.zero.alpine.fork.listener.EventHandler;
import me.gopro336.goprohack.events.player.EventPlayerUpdate;
import me.zero.alpine.fork.listener.Listener;
import me.gopro336.goprohack.modules.Value;
import me.gopro336.goprohack.modules.Module;

public final class BlockMod extends Module
{
    public final Value<Boolean> reset;
    @EventHandler
    private Listener<EventPlayerUpdate> OnPlayerUpdate;
    @EventHandler
    private Listener<EventPlayerResetBlockRemoving> ResetBlock;
    @EventHandler
    private Listener<EventPlayerClickBlock> ClickBlock;
    @EventHandler
    private Listener<EventPlayerDamageBlock> OnDamageBlock;
    
    public BlockMod() {
        super("NoMineReset", new String[] { "No Block RESet" }, "Allows you to break blocks faster", "NONE", 5131167, ModuleType.WORLD);
        this.reset = new Value<Boolean>("Reset", new String[] { "Res" }, "Stops current block destroy damage from resetting if enabled.", true);
        this.OnPlayerUpdate = new Listener<EventPlayerUpdate>(p_Event -> {
            this.mc.playerController.blockHitDelay = 0;
            if (this.reset.getValue() && Minecraft.getMinecraft().gameSettings.keyBindUseItem.isKeyDown()) {
                this.mc.playerController.isHittingBlock = false;
            }
            return;
        }, (Predicate<EventPlayerUpdate>[])new Predicate[0]);
        this.ResetBlock = new Listener<EventPlayerResetBlockRemoving>(p_Event -> {
            if (this.reset.getValue()) {
                p_Event.cancel();
            }
            return;
        }, (Predicate<EventPlayerResetBlockRemoving>[])new Predicate[0]);
        this.ClickBlock = new Listener<EventPlayerClickBlock>(p_Event -> {
            if (this.reset.getValue() && this.mc.playerController.curBlockDamageMP > 0.1f) {
                this.mc.playerController.isHittingBlock = true;
            }
            return;
        }, (Predicate<EventPlayerClickBlock>[])new Predicate[0]);
        this.OnDamageBlock = new Listener<EventPlayerDamageBlock>(p_Event -> {
            if (this.canBreak(p_Event.getPos()) && this.reset.getValue()) {
                this.mc.playerController.isHittingBlock = false;
            }
        }, (Predicate<EventPlayerDamageBlock>[])new Predicate[0]);
    }
    
    private boolean canBreak(final BlockPos pos) {
        final IBlockState blockState = this.mc.world.getBlockState(pos);
        final Block block = blockState.getBlock();
        return block.getBlockHardness(blockState, (World)Minecraft.getMinecraft().world, pos) != -1.0f;
    }
}
