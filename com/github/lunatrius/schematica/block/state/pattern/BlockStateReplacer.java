//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package com.github.lunatrius.schematica.block.state.pattern;

import java.util.Collection;
import java.util.HashMap;
import com.github.lunatrius.core.exceptions.LocalizedException;
import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import com.google.common.base.Predicate;
import net.minecraft.block.state.pattern.BlockStateMatcher;
import java.util.Iterator;
import net.minecraft.block.properties.IProperty;
import java.util.Map;
import net.minecraft.block.state.IBlockState;

public class BlockStateReplacer
{
    private final IBlockState defaultReplacement;
    
    private BlockStateReplacer(final IBlockState defaultReplacement) {
        this.defaultReplacement = defaultReplacement;
    }
    
    public IBlockState getReplacement(final IBlockState original, final Map<IProperty, Comparable> properties) {
        IBlockState replacement = this.defaultReplacement;
        replacement = this.applyProperties(replacement, (Map<IProperty, Comparable>)original.getProperties());
        replacement = this.applyProperties(replacement, properties);
        return replacement;
    }
    
    private <K extends IProperty, V extends Comparable> IBlockState applyProperties(IBlockState blockState, final Map<K, V> properties) {
        for (final Map.Entry<K, V> entry : properties.entrySet()) {
            try {
                blockState = blockState.withProperty((IProperty)entry.getKey(), (Comparable)entry.getValue());
            }
            catch (IllegalArgumentException ex) {}
        }
        return blockState;
    }
    
    public static BlockStateReplacer forBlockState(final IBlockState replacement) {
        return new BlockStateReplacer(replacement);
    }
    
    public static BlockStateMatcher getMatcher(final BlockStateInfo blockStateInfo) {
        final BlockStateMatcher matcher = BlockStateMatcher.forBlock(blockStateInfo.block);
        for (final Map.Entry<IProperty, Comparable> entry : blockStateInfo.stateData.entrySet()) {
            matcher.where((IProperty)entry.getKey(), (Predicate)new Predicate<Comparable>() {
                public boolean apply(final Comparable input) {
                    return input != null && input.equals(entry.getValue());
                }
            });
        }
        return matcher;
    }
    
    public static BlockStateInfo fromString(final String input) throws LocalizedException {
        final int start = input.indexOf(91);
        final int end = input.indexOf(93);
        String blockName;
        String stateData;
        if (start > -1 && end > -1) {
            blockName = input.substring(0, start);
            stateData = input.substring(start + 1, end);
        }
        else {
            blockName = input;
            stateData = "";
        }
        final ResourceLocation location = new ResourceLocation(blockName);
        if (!Block.REGISTRY.containsKey((Object)location)) {
            throw new LocalizedException("schematica.message.invalidBlock", new Object[] { blockName });
        }
        final Block block = (Block)Block.REGISTRY.getObject((Object)location);
        final Map<IProperty, Comparable> propertyData = parsePropertyData(block.getDefaultState(), stateData, true);
        return new BlockStateInfo(block, propertyData);
    }
    
    public static Map<IProperty, Comparable> parsePropertyData(final IBlockState blockState, final String stateData, final boolean strict) throws LocalizedException {
        final HashMap<IProperty, Comparable> map = new HashMap<IProperty, Comparable>();
        if (stateData == null || stateData.length() == 0) {
            return map;
        }
        final String[] split2;
        final String[] propertyPairs = split2 = stateData.split(",");
        for (final String propertyPair : split2) {
            final String[] split = propertyPair.split("=");
            if (split.length != 2) {
                throw new LocalizedException("schematica.message.invalidProperty", new Object[] { propertyPair });
            }
            putMatchingProperty(map, blockState, split[0], split[1], strict);
        }
        return map;
    }
    
    private static boolean putMatchingProperty(final Map<IProperty, Comparable> map, final IBlockState blockState, final String name, final String value, final boolean strict) throws LocalizedException {
        for (final IProperty property : blockState.getPropertyKeys()) {
            if (property.getName().equalsIgnoreCase(name)) {
                final Collection<Comparable> allowedValues = (Collection<Comparable>)property.getAllowedValues();
                for (final Comparable allowedValue : allowedValues) {
                    if (String.valueOf(allowedValue).equalsIgnoreCase(value)) {
                        map.put(property, allowedValue);
                        return true;
                    }
                }
            }
        }
        if (strict) {
            throw new LocalizedException("schematica.message.invalidPropertyForBlock", new Object[] { name + "=" + value, Block.REGISTRY.getNameForObject((Object)blockState.getBlock()) });
        }
        return false;
    }
    
    public static class BlockStateInfo
    {
        public final Block block;
        public final Map<IProperty, Comparable> stateData;
        
        public BlockStateInfo(final Block block, final Map<IProperty, Comparable> stateData) {
            this.block = block;
            this.stateData = stateData;
        }
    }
}
