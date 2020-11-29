// 
// Decompiled by Procyon v0.5.36
// 

package com.github.lunatrius.schematica.client.printer.registry;

import net.minecraft.world.World;
import net.minecraft.util.math.BlockPos;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.block.state.IBlockState;

public interface IValidPlayerFacing
{
    boolean isValid(final IBlockState p0, final EntityPlayer p1, final BlockPos p2, final World p3);
}
