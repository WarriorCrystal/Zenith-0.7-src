//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.modules.combat;

import java.util.Iterator;
import java.util.ArrayList;
import me.gopro336.goprohack.util.Pair;
import me.gopro336.goprohack.modules.render.CityESPModule;
import net.minecraft.util.math.BlockPos;
import net.minecraft.item.ItemStack;
import java.util.function.Predicate;
import me.gopro336.goprohack.util.entity.PlayerUtil;
import net.minecraft.entity.player.EntityPlayer;
import me.gopro336.goprohack.util.entity.EntityUtil;
import me.gopro336.goprohack.managers.BlockManager;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.init.Items;
import me.gopro336.goprohack.events.MinecraftEvent;
import me.zero.alpine.fork.listener.EventHandler;
import me.gopro336.goprohack.events.player.EventPlayerMotionUpdate;
import me.zero.alpine.fork.listener.Listener;
import me.gopro336.goprohack.modules.Module;

public class AutoCityModule extends Module
{
    @EventHandler
    private Listener<EventPlayerMotionUpdate> OnPlayerUpdate;
    
    public AutoCityModule() {
        super("AutoCity", new String[] { "AutoCityBoss" }, "Automatically mines the city block if a target near you can be citied", "NONE", 5131167, ModuleType.COMBAT);
        boolean hasPickaxe;
        int i;
        ItemStack stack;
        BlockPos currBlock;
        double[] rotations;
        this.OnPlayerUpdate = new Listener<EventPlayerMotionUpdate>(p_Event -> {
            if (p_Event.getEra() == MinecraftEvent.Era.PRE) {
                hasPickaxe = (this.mc.player.getHeldItemMainhand().getItem() == Items.DIAMOND_PICKAXE);
                if (!hasPickaxe) {
                    for (i = 0; i < 9; ++i) {
                        stack = this.mc.player.inventory.getStackInSlot(i);
                        if (!stack.isEmpty()) {
                            if (stack.getItem() == Items.DIAMOND_PICKAXE) {
                                hasPickaxe = true;
                                this.mc.player.inventory.currentItem = i;
                                this.mc.playerController.updateController();
                                break;
                            }
                        }
                    }
                }
                if (!hasPickaxe) {
                    this.SendMessage(ChatFormatting.RED + "No pickaxe!");
                    this.toggle();
                }
                else {
                    currBlock = BlockManager.GetCurrBlock();
                    if (currBlock == null) {
                        this.SendMessage(ChatFormatting.GREEN + "Done!");
                        this.toggle();
                    }
                    else {
                        p_Event.cancel();
                        rotations = EntityUtil.calculateLookAt(currBlock.getX() + 0.5, currBlock.getY() - 0.5, currBlock.getZ() + 0.5, (EntityPlayer)this.mc.player);
                        PlayerUtil.PacketFacePitchAndYaw((float)rotations[1], (float)rotations[0]);
                        BlockManager.Update(3.0f, false);
                    }
                }
            }
        }, (Predicate<EventPlayerMotionUpdate>[])new Predicate[0]);
    }
    
    @Override
    public void onEnable() {
        super.onEnable();
        final ArrayList<Pair<EntityPlayer, ArrayList<BlockPos>>> cityPlayers = CityESPModule.GetPlayersReadyToBeCitied();
        if (cityPlayers.isEmpty()) {
            this.SendMessage(ChatFormatting.RED + "There is no one to city!");
            this.toggle();
            return;
        }
        EntityPlayer target = null;
        BlockPos targetBlock = null;
        double currDistance = 100.0;
        for (final Pair<EntityPlayer, ArrayList<BlockPos>> pair : cityPlayers) {
            for (final BlockPos pos : pair.getSecond()) {
                if (targetBlock == null) {
                    target = pair.getFirst();
                    targetBlock = pos;
                }
                else {
                    final double dist = pos.getDistance(targetBlock.getX(), targetBlock.getY(), targetBlock.getZ());
                    if (dist >= currDistance) {
                        continue;
                    }
                    currDistance = dist;
                    targetBlock = pos;
                    target = pair.getFirst();
                }
            }
        }
        if (targetBlock == null) {
            this.SendMessage(ChatFormatting.RED + "Couldn't find any blocks to mine!");
            this.toggle();
            return;
        }
        BlockManager.SetCurrentBlock(targetBlock);
        this.SendMessage(ChatFormatting.LIGHT_PURPLE + "Attempting to mine a block by your target: " + ChatFormatting.RED + target.getName());
    }
}
