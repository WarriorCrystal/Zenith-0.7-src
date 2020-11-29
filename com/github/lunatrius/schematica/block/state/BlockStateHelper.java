//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package com.github.lunatrius.schematica.block.state;

import net.minecraft.block.Block;
import com.google.common.collect.UnmodifiableIterator;
import net.minecraft.util.text.TextFormatting;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;

public class BlockStateHelper
{
    public static <T extends Comparable<T>> IProperty<T> getProperty(final IBlockState blockState, final String name) {
        for (final IProperty prop : blockState.getPropertyKeys()) {
            if (prop.getName().equals(name)) {
                return (IProperty<T>)prop;
            }
        }
        return null;
    }
    
    public static <T extends Comparable<T>> T getPropertyValue(final IBlockState blockState, final String name) {
        final IProperty<T> property = getProperty(blockState, name);
        if (property == null) {
            throw new IllegalArgumentException(name + " does not exist in " + blockState);
        }
        return (T)blockState.getValue((IProperty)property);
    }
    
    public static List<String> getFormattedProperties(final IBlockState blockState) {
        final List<String> list = new ArrayList<String>();
        for (final Map.Entry<IProperty<?>, Comparable<?>> entry : blockState.getProperties().entrySet()) {
            final IProperty key = entry.getKey();
            final Comparable value = entry.getValue();
            String formattedValue = value.toString();
            if (Boolean.TRUE.equals(value)) {
                formattedValue = TextFormatting.GREEN + formattedValue + TextFormatting.RESET;
            }
            else if (Boolean.FALSE.equals(value)) {
                formattedValue = TextFormatting.RED + formattedValue + TextFormatting.RESET;
            }
            list.add(key.getName() + ": " + formattedValue);
        }
        return list;
    }
    
    public static boolean areBlockStatesEqual(final IBlockState blockStateA, final IBlockState blockStateB) {
        if (blockStateA == blockStateB) {
            return true;
        }
        final Block blockA = blockStateA.getBlock();
        final Block blockB = blockStateB.getBlock();
        return blockA == blockB && blockA.getMetaFromState(blockStateA) == blockB.getMetaFromState(blockStateB);
    }
}
