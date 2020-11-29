//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package com.github.lunatrius.schematica.world.schematic;

import net.minecraft.util.math.Vec3d;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.tileentity.TileEntity;
import java.util.Iterator;
import net.minecraft.item.ItemStack;
import com.github.lunatrius.schematica.reference.Reference;
import com.github.lunatrius.schematica.nbt.NBTHelper;
import com.github.lunatrius.schematica.world.storage.Schematic;
import net.minecraft.world.gen.structure.template.Template;
import com.github.lunatrius.schematica.api.ISchematic;
import net.minecraft.nbt.NBTTagCompound;

public class SchematicStructure extends SchematicFormat
{
    @Override
    public ISchematic readFromNBT(final NBTTagCompound tagCompound) {
        final ItemStack icon = SchematicUtil.getIconFromNBT(tagCompound);
        final Template template = new Template();
        template.read(tagCompound);
        final Schematic schematic = new Schematic(icon, template.size.getX(), template.size.getY(), template.size.getZ(), template.getAuthor());
        for (final Template.BlockInfo block : template.blocks) {
            schematic.setBlockState(block.pos, block.blockState);
            if (block.tileentityData != null) {
                try {
                    block.tileentityData.setInteger("x", block.pos.getX());
                    block.tileentityData.setInteger("y", block.pos.getY());
                    block.tileentityData.setInteger("z", block.pos.getZ());
                    final TileEntity tileEntity = NBTHelper.readTileEntityFromCompound(block.tileentityData);
                    if (tileEntity == null) {
                        continue;
                    }
                    schematic.setTileEntity(block.pos, tileEntity);
                }
                catch (Exception e) {
                    Reference.logger.error("TileEntity failed to load properly!", (Throwable)e);
                }
            }
        }
        return schematic;
    }
    
    @Override
    public boolean writeToNBT(final NBTTagCompound tagCompound, final ISchematic schematic) {
        final Template template = new Template();
        template.size = new BlockPos(schematic.getWidth(), schematic.getHeight(), schematic.getLength());
        template.setAuthor(schematic.getAuthor());
        for (final BlockPos pos : BlockPos.getAllInBox(BlockPos.ORIGIN, template.size.add(-1, -1, -1))) {
            final TileEntity tileEntity = schematic.getTileEntity(pos);
            NBTTagCompound compound;
            if (tileEntity != null) {
                compound = NBTHelper.writeTileEntityToCompound(tileEntity);
                compound.removeTag("x");
                compound.removeTag("y");
                compound.removeTag("z");
            }
            else {
                compound = null;
            }
            template.blocks.add(new Template.BlockInfo(pos, schematic.getBlockState(pos), compound));
        }
        for (final Entity entity : schematic.getEntities()) {
            try {
                final Vec3d vec3d = new Vec3d(entity.posX, entity.posY, entity.posZ);
                final NBTTagCompound nbttagcompound = new NBTTagCompound();
                entity.writeToNBTOptional(nbttagcompound);
                final BlockPos blockpos = new BlockPos(vec3d);
                template.entities.add(new Template.EntityInfo(vec3d, blockpos, nbttagcompound));
            }
            catch (Throwable t) {
                Reference.logger.error("Entity {} failed to save, skipping!", (Object)entity, (Object)t);
            }
        }
        template.writeToNBT(tagCompound);
        return true;
    }
    
    @Override
    public String getName() {
        return "schematica.format.structure";
    }
    
    @Override
    public String getExtension() {
        return ".nbt";
    }
}
