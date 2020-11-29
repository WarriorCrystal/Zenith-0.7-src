//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package com.github.lunatrius.schematica.world.schematic;

import java.util.LinkedHashMap;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.entity.player.EntityPlayer;
import java.io.DataOutput;
import net.minecraft.nbt.NBTBase;
import java.io.DataOutputStream;
import java.io.OutputStream;
import java.util.zip.GZIPOutputStream;
import java.io.FileOutputStream;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.common.MinecraftForge;
import com.github.lunatrius.schematica.api.event.PostSchematicCaptureEvent;
import javax.annotation.Nullable;
import com.github.lunatrius.schematica.reference.Reference;
import java.io.File;
import com.github.lunatrius.schematica.api.ISchematic;
import net.minecraft.nbt.NBTTagCompound;
import java.util.Map;

public abstract class SchematicFormat
{
    public static final Map<String, SchematicFormat> FORMATS;
    public static String FORMAT_DEFAULT;
    
    public abstract ISchematic readFromNBT(final NBTTagCompound p0);
    
    public abstract boolean writeToNBT(final NBTTagCompound p0, final ISchematic p1);
    
    public abstract String getName();
    
    public abstract String getExtension();
    
    public static ISchematic readFromFile(final File file) {
        try {
            final NBTTagCompound tagCompound = SchematicUtil.readTagCompoundFromFile(file);
            SchematicFormat schematicFormat;
            if (tagCompound.hasKey("Materials")) {
                final String format = tagCompound.getString("Materials");
                schematicFormat = SchematicFormat.FORMATS.get(format);
                if (schematicFormat == null) {
                    throw new UnsupportedFormatException(format);
                }
            }
            else {
                schematicFormat = SchematicFormat.FORMATS.get("Structure");
            }
            return schematicFormat.readFromNBT(tagCompound);
        }
        catch (Exception ex) {
            Reference.logger.error("Failed to read schematic!", (Throwable)ex);
            return null;
        }
    }
    
    public static ISchematic readFromFile(final File directory, final String filename) {
        return readFromFile(new File(directory, filename));
    }
    
    public static boolean writeToFile(final File file, @Nullable String format, final ISchematic schematic) {
        try {
            if (format == null) {
                format = SchematicFormat.FORMAT_DEFAULT;
            }
            if (!SchematicFormat.FORMATS.containsKey(format)) {
                throw new UnsupportedFormatException(format);
            }
            final PostSchematicCaptureEvent event = new PostSchematicCaptureEvent(schematic);
            MinecraftForge.EVENT_BUS.post((Event)event);
            final NBTTagCompound tagCompound = new NBTTagCompound();
            SchematicFormat.FORMATS.get(format).writeToNBT(tagCompound, schematic);
            final DataOutputStream dataOutputStream = new DataOutputStream(new GZIPOutputStream(new FileOutputStream(file)));
            try {
                NBTTagCompound.writeEntry("Schematic", (NBTBase)tagCompound, (DataOutput)dataOutputStream);
            }
            finally {
                dataOutputStream.close();
            }
            return true;
        }
        catch (Exception ex) {
            Reference.logger.error("Failed to write schematic!", (Throwable)ex);
            return false;
        }
    }
    
    public static boolean writeToFile(final File directory, final String filename, @Nullable final String format, final ISchematic schematic) {
        return writeToFile(new File(directory, filename), format, schematic);
    }
    
    public static void writeToFileAndNotify(final File file, @Nullable final String format, final ISchematic schematic, final EntityPlayer player) {
        final boolean success = writeToFile(file, format, schematic);
        final String message = success ? "schematica.command.save.saveSucceeded" : "schematica.command.save.saveFailed";
        player.sendMessage((ITextComponent)new TextComponentTranslation(message, new Object[] { file.getName() }));
    }
    
    public static String getFormatName(final String format) {
        if (!SchematicFormat.FORMATS.containsKey(format)) {
            Reference.logger.warn("No format with id {}; returning invalid for name", (Object)format, (Object)new UnsupportedFormatException(format).fillInStackTrace());
            return "schematica.format.invalid";
        }
        return SchematicFormat.FORMATS.get(format).getName();
    }
    
    public static String getExtension(@Nullable String format) {
        if (format == null) {
            format = SchematicFormat.FORMAT_DEFAULT;
        }
        if (!SchematicFormat.FORMATS.containsKey(format)) {
            Reference.logger.warn("No format with id {}; returning default extension", (Object)format, (Object)new UnsupportedFormatException(format).fillInStackTrace());
            format = SchematicFormat.FORMAT_DEFAULT;
        }
        return SchematicFormat.FORMATS.get(format).getExtension();
    }
    
    static {
        (FORMATS = new LinkedHashMap<String, SchematicFormat>()).put("Alpha", new SchematicAlpha());
        SchematicFormat.FORMATS.put("Structure", new SchematicStructure());
        SchematicFormat.FORMAT_DEFAULT = "Alpha";
    }
}
