// 
// Decompiled by Procyon v0.5.36
// 

package com.github.lunatrius.schematica.client.printer.registry;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import java.util.List;

public interface IValidBlockFacing
{
    List<EnumFacing> getValidBlockFacings(final List<EnumFacing> p0, final IBlockState p1);
}
