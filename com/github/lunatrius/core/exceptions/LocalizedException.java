//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package com.github.lunatrius.core.exceptions;

import net.minecraft.util.text.translation.I18n;

public class LocalizedException extends Exception
{
    public LocalizedException(final String format) {
        super(I18n.translateToLocal(format));
    }
    
    public LocalizedException(final String format, final Object... arguments) {
        super(I18n.translateToLocalFormatted(format, arguments));
    }
}
