// 
// Decompiled by Procyon v0.5.36
// 

package com.github.lunatrius.schematica.world.schematic;

public class UnsupportedFormatException extends Exception
{
    public UnsupportedFormatException(final String format) {
        super(String.format("Unsupported format: %s", format));
    }
}
