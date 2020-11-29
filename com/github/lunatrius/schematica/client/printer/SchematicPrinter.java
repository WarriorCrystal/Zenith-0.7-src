//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package com.github.lunatrius.schematica.client.printer;

import net.minecraft.inventory.ClickType;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.EnumHand;
import com.github.lunatrius.schematica.client.printer.registry.PlacementData;
import me.gopro336.goprohack.events.schematica.EventSchematicaPlaceBlockFull;
import me.gopro336.goprohack.events.schematica.EventSchematicaPlaceBlock;
import me.gopro336.goprohack.GoproHackMod;
import com.github.lunatrius.schematica.client.printer.registry.PlacementRegistry;
import net.minecraft.item.ItemBucket;
import me.gopro336.goprohack.util.BlockInteractionHelper;
import java.util.ArrayList;
import me.gopro336.goprohack.modules.exploit.LiquidInteractModule;
import me.gopro336.goprohack.managers.ModuleManager;
import java.util.Arrays;
import java.util.List;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraft.item.ItemStack;
import com.github.lunatrius.schematica.client.printer.nbtsync.NBTSync;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import com.github.lunatrius.schematica.client.util.BlockStateToItemStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraft.entity.player.EntityPlayer;
import com.github.lunatrius.schematica.client.printer.nbtsync.SyncRegistry;
import com.github.lunatrius.schematica.block.state.BlockStateHelper;
import java.util.Iterator;
import com.github.lunatrius.schematica.reference.Reference;
import com.github.lunatrius.core.util.math.MBlockPos;
import com.github.lunatrius.core.util.math.BlockPosHelper;
import com.github.lunatrius.schematica.handler.ConfigurationHandler;
import com.github.lunatrius.schematica.proxy.ClientProxy;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.util.math.BlockPos;
import java.util.HashMap;
import com.github.lunatrius.schematica.client.world.SchematicWorld;
import net.minecraft.client.Minecraft;

public class SchematicPrinter
{
    public static final SchematicPrinter INSTANCE;
    private final Minecraft minecraft;
    private boolean isEnabled;
    private boolean isPrinting;
    private boolean Statgopro336y;
    private SchematicWorld schematic;
    private byte[][][] timeout;
    private HashMap<BlockPos, Integer> syncBlacklist;
    
    public SchematicPrinter() {
        this.minecraft = Minecraft.getMinecraft();
        this.isEnabled = true;
        this.isPrinting = false;
        this.Statgopro336y = false;
        this.schematic = null;
        this.timeout = null;
        this.syncBlacklist = new HashMap<BlockPos, Integer>();
    }
    
    public boolean isEnabled() {
        return this.isEnabled;
    }
    
    public void setEnabled(final boolean isEnabled) {
        this.isEnabled = isEnabled;
    }
    
    public boolean togglePrinting() {
        return this.isPrinting = (!this.isPrinting && this.schematic != null);
    }
    
    public boolean isPrinting() {
        return this.isPrinting;
    }
    
    public void setPrinting(final boolean isPrinting) {
        this.isPrinting = isPrinting;
    }
    
    public SchematicWorld getSchematic() {
        return this.schematic;
    }
    
    public boolean IsStatgopro336y() {
        return this.schematic == null || this.Statgopro336y;
    }
    
    public void setSchematic(final SchematicWorld schematic) {
        this.schematic = schematic;
        this.refresh();
    }
    
    public void refresh() {
        if (this.schematic != null) {
            this.timeout = new byte[this.schematic.getWidth()][this.schematic.getHeight()][this.schematic.getLength()];
        }
        else {
            this.timeout = null;
        }
        this.syncBlacklist.clear();
    }
    
    public boolean print(final WorldClient world, final EntityPlayerSP player) {
        final double dX = ClientProxy.playerPosition.x - this.schematic.position.x;
        final double dY = ClientProxy.playerPosition.y - this.schematic.position.y;
        final double dZ = ClientProxy.playerPosition.z - this.schematic.position.z;
        final int x = (int)Math.floor(dX);
        final int y = (int)Math.floor(dY);
        final int z = (int)Math.floor(dZ);
        final int range = ConfigurationHandler.placeDistance;
        final int minX = Math.max(0, x - range);
        final int maxX = Math.min(this.schematic.getWidth() - 1, x + range);
        final int minY = Math.max(0, y - range);
        int maxY = Math.min(this.schematic.getHeight() - 1, y + range);
        final int minZ = Math.max(0, z - range);
        final int maxZ = Math.min(this.schematic.getLength() - 1, z + range);
        if (minX > maxX || minY > maxY || minZ > maxZ) {
            return false;
        }
        final int slot = player.inventory.currentItem;
        final boolean isSneaking = player.isSneaking();
        switch (this.schematic.layerMode) {
            case SINGLE_LAYER: {
                if (this.schematic.renderingLayer > maxY) {
                    return false;
                }
                maxY = this.schematic.renderingLayer;
            }
            case ALL_BELOW: {
                if (this.schematic.renderingLayer < minY) {
                    return false;
                }
                maxY = this.schematic.renderingLayer;
                break;
            }
        }
        this.syncSneaking(player, true);
        final double blockReachDistance = this.minecraft.playerController.getBlockReachDistance() - 0.1;
        final double blockReachDistanceSq = blockReachDistance * blockReachDistance;
        for (final MBlockPos pos : BlockPosHelper.getAllInBoxXZY(minX, minY, minZ, maxX, maxY, maxZ)) {
            if (pos.distanceSqToCenter(dX, dY, dZ) > blockReachDistanceSq) {
                continue;
            }
            try {
                if (this.placeBlock(world, player, pos)) {
                    this.Statgopro336y = false;
                    return this.syncSlotAndSneaking(player, slot, isSneaking, true);
                }
                continue;
            }
            catch (Exception e) {
                Reference.logger.error("Could not place block!", (Throwable)e);
                return this.syncSlotAndSneaking(player, slot, isSneaking, false);
            }
        }
        this.Statgopro336y = true;
        return this.syncSlotAndSneaking(player, slot, isSneaking, true);
    }
    
    private boolean syncSlotAndSneaking(final EntityPlayerSP player, final int slot, final boolean isSneaking, final boolean success) {
        this.syncSneaking(player, isSneaking);
        return success;
    }
    
    private boolean placeBlock(final WorldClient world, final EntityPlayerSP player, final BlockPos pos) {
        final int x = pos.getX();
        final int y = pos.getY();
        final int z = pos.getZ();
        final int wx = this.schematic.position.x + x;
        final int wy = this.schematic.position.y + y;
        final int wz = this.schematic.position.z + z;
        final BlockPos realPos = new BlockPos(wx, wy, wz);
        final IBlockState blockState = this.schematic.getBlockState(pos);
        final IBlockState realBlockState = world.getBlockState(realPos);
        final Block realBlock = realBlockState.getBlock();
        if (BlockStateHelper.areBlockStatesEqual(blockState, realBlockState)) {
            final NBTSync handler = SyncRegistry.INSTANCE.getHandler(realBlock);
            if (handler != null) {
                this.timeout[x][y][z] = (byte)ConfigurationHandler.timeout;
                Integer tries = this.syncBlacklist.get(realPos);
                if (tries == null) {
                    tries = 0;
                }
                else if (tries >= 10) {
                    return false;
                }
                Reference.logger.trace("Trying to sync block at {} {}", (Object)realPos, (Object)tries);
                final boolean success = handler.execute((EntityPlayer)player, (World)this.schematic, pos, (World)world, realPos);
                if (success) {
                    this.syncBlacklist.put(realPos, tries + 1);
                }
                return success;
            }
            return false;
        }
        else {
            if (ConfigurationHandler.destroyBlocks && !world.isAirBlock(realPos) && this.minecraft.playerController.isInCreativeMode()) {
                this.minecraft.playerController.clickBlock(realPos, EnumFacing.DOWN);
                this.timeout[x][y][z] = (byte)ConfigurationHandler.timeout;
                return !ConfigurationHandler.destroyInstantly;
            }
            if (this.schematic.isAirBlock(pos)) {
                return false;
            }
            if (!realBlock.isReplaceable((IBlockAccess)world, realPos)) {
                return false;
            }
            final ItemStack itemStack = BlockStateToItemStack.getItemStack(blockState, new RayTraceResult((Entity)player), this.schematic, pos, (EntityPlayer)player);
            if (itemStack.isEmpty()) {
                Reference.logger.debug("{} is missing a mapping!", (Object)blockState);
                return false;
            }
            if (this.placeBlock(world, player, realPos, blockState, itemStack)) {
                this.timeout[x][y][z] = (byte)ConfigurationHandler.timeout;
                if (!ConfigurationHandler.placeInstantly) {
                    return true;
                }
            }
            return false;
        }
    }
    
    private boolean isSolid(final World world, final BlockPos pos, final EnumFacing side) {
        final BlockPos offset = pos.offset(side);
        final IBlockState blockState = world.getBlockState(offset);
        final Block block = blockState.getBlock();
        return block != null && !block.isAir(blockState, (IBlockAccess)world, offset) && !(block instanceof BlockFluidBase) && !block.isReplaceable((IBlockAccess)world, offset);
    }
    
    private List<EnumFacing> getSolidSides(final World world, final BlockPos pos) {
        if (!ConfigurationHandler.placeAdjacent) {
            return Arrays.asList(EnumFacing.VALUES);
        }
        final boolean l_LiquidInteract = ModuleManager.Get().GetMod(LiquidInteractModule.class).isEnabled();
        final List<EnumFacing> list = new ArrayList<EnumFacing>();
        for (final EnumFacing side : EnumFacing.VALUES) {
            if (this.isSolid(world, pos, side)) {
                list.add(side);
            }
        }
        if (list.isEmpty() && l_LiquidInteract) {
            final BlockInteractionHelper.ValidResult l_Result = BlockInteractionHelper.valid(pos);
            if (l_Result == BlockInteractionHelper.ValidResult.Ok) {
                list.add(EnumFacing.UP);
            }
        }
        return list;
    }
    
    private boolean placeBlock(final WorldClient world, final EntityPlayerSP player, final BlockPos pos, final IBlockState blockState, final ItemStack itemStack) {
        if (itemStack.getItem() instanceof ItemBucket) {
            return false;
        }
        final PlacementData data = PlacementRegistry.INSTANCE.getPlacementData(blockState, itemStack);
        if (data != null && !data.isValidPlayerFacing(blockState, (EntityPlayer)player, pos, (World)world)) {
            return false;
        }
        final List<EnumFacing> solidSides = this.getSolidSides((World)world, pos);
        if (solidSides.size() == 0) {
            return false;
        }
        GoproHackMod.EVENT_BUS.post(new EventSchematicaPlaceBlock(pos));
        EnumFacing direction;
        float offsetX;
        float offsetY;
        float offsetZ;
        int extraClicks;
        if (data != null) {
            final List<EnumFacing> validDirections = data.getValidBlockFacings(solidSides, blockState);
            if (validDirections.size() == 0) {
                return false;
            }
            direction = validDirections.get(0);
            offsetX = data.getOffsetX(blockState);
            offsetY = data.getOffsetY(blockState);
            offsetZ = data.getOffsetZ(blockState);
            extraClicks = data.getExtraClicks(blockState);
        }
        else {
            direction = solidSides.get(0);
            offsetX = 0.5f;
            offsetY = 0.5f;
            offsetZ = 0.5f;
            extraClicks = 0;
        }
        if (itemStack != null) {
            final int l_Slot = player.inventory.getSlotFor(itemStack);
            if (l_Slot == -1 || l_Slot >= 9) {
                return false;
            }
            if (player.inventory.getStackInSlot(l_Slot).getItem() == itemStack.getItem()) {
                player.inventory.currentItem = l_Slot;
                this.minecraft.playerController.updateController();
            }
        }
        this.Statgopro336y = false;
        final EventSchematicaPlaceBlockFull l_Event = new EventSchematicaPlaceBlockFull(pos, itemStack.getItem());
        GoproHackMod.EVENT_BUS.post(l_Event);
        if (l_Event.isCancelled()) {
            return l_Event.GetResult();
        }
        return this.placeBlock(world, player, pos, direction, offsetX, offsetY, offsetZ, extraClicks);
    }
    
    private boolean placeBlock(final WorldClient world, final EntityPlayerSP player, final BlockPos pos, final EnumFacing direction, final float offsetX, final float offsetY, final float offsetZ, final int extraClicks) {
        final EnumHand hand = EnumHand.MAIN_HAND;
        final ItemStack itemStack = player.getHeldItem(hand);
        boolean success = false;
        if (!this.minecraft.playerController.isInCreativeMode() && !itemStack.isEmpty() && itemStack.getCount() <= extraClicks) {
            return false;
        }
        final BlockPos offset = pos.offset(direction);
        final EnumFacing side = direction.getOpposite();
        final Vec3d hitVec = new Vec3d((double)(offset.getX() + offsetX), (double)(offset.getY() + offsetY), (double)(offset.getZ() + offsetZ));
        success = this.placeBlock(world, player, itemStack, offset, side, hitVec, hand);
        for (int i = 0; success && i < extraClicks; success = this.placeBlock(world, player, itemStack, offset, side, hitVec, hand), ++i) {}
        if (itemStack.getCount() == 0 && success) {
            player.inventory.mainInventory.set(player.inventory.currentItem, (Object)ItemStack.EMPTY);
        }
        return success;
    }
    
    private boolean placeBlock(final WorldClient world, final EntityPlayerSP player, final ItemStack itemStack, final BlockPos pos, final EnumFacing side, final Vec3d hitVec, final EnumHand hand) {
        final BlockPos actualPos = ConfigurationHandler.placeAdjacent ? pos : pos.offset(side);
        final EnumActionResult result = this.minecraft.playerController.processRightClickBlock(player, world, actualPos, side, hitVec, hand);
        if (result != EnumActionResult.SUCCESS) {
            return false;
        }
        player.swingArm(hand);
        return true;
    }
    
    private void syncSneaking(final EntityPlayerSP player, final boolean isSneaking) {
        player.setSneaking(isSneaking);
        player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)player, isSneaking ? CPacketEntityAction.Action.START_SNEAKING : CPacketEntityAction.Action.STOP_SNEAKING));
    }
    
    private boolean swapToItem(final InventoryPlayer inventory, final ItemStack itemStack) {
        return this.swapToItem(inventory, itemStack, true);
    }
    
    private boolean swapToItem(final InventoryPlayer inventory, final ItemStack itemStack, final boolean swapSlots) {
        final int slot = this.getInventorySlotWithItem(inventory, itemStack);
        if (this.minecraft.playerController.isInCreativeMode() && (slot < 0 || slot >= 9) && ConfigurationHandler.swapSlotsQueue.size() > 0) {
            inventory.setInventorySlotContents(inventory.currentItem = this.getNextSlot(), itemStack.copy());
            this.minecraft.playerController.sendSlotPacket(inventory.getStackInSlot(inventory.currentItem), 36 + inventory.currentItem);
            return true;
        }
        if (slot >= 0 && slot < 9) {
            inventory.currentItem = slot;
            return true;
        }
        return swapSlots && slot >= 9 && slot < 36 && this.swapSlots(inventory, slot) && this.swapToItem(inventory, itemStack, false);
    }
    
    private int getInventorySlotWithItem(final InventoryPlayer inventory, final ItemStack itemStack) {
        for (int i = 0; i < inventory.mainInventory.size(); ++i) {
            if (((ItemStack)inventory.mainInventory.get(i)).isItemEqual(itemStack)) {
                return i;
            }
        }
        return -1;
    }
    
    private boolean swapSlots(final InventoryPlayer inventory, final int from) {
        if (ConfigurationHandler.swapSlotsQueue.size() > 0) {
            final int slot = this.getNextSlot();
            this.swapSlots(from, slot);
            return true;
        }
        return false;
    }
    
    private int getNextSlot() {
        final int slot = ConfigurationHandler.swapSlotsQueue.poll() % 9;
        ConfigurationHandler.swapSlotsQueue.offer(slot);
        return slot;
    }
    
    private boolean swapSlots(final int from, final int to) {
        return this.minecraft.playerController.windowClick(this.minecraft.player.inventoryContainer.windowId, from, to, ClickType.SWAP, (EntityPlayer)this.minecraft.player) == ItemStack.EMPTY;
    }
    
    static {
        INSTANCE = new SchematicPrinter();
    }
}
