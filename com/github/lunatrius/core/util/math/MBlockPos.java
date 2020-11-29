//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package com.github.lunatrius.core.util.math;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.math.Vec3d;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;

public class MBlockPos extends BlockPos
{
    public int x;
    public int y;
    public int z;
    
    public MBlockPos() {
        this(0, 0, 0);
    }
    
    public MBlockPos(final Entity source) {
        this(source.posX, source.posY, source.posZ);
    }
    
    public MBlockPos(final Vec3d source) {
        this(source.x, source.y, source.z);
    }
    
    public MBlockPos(final Vec3i source) {
        this(source.getX(), source.getY(), source.getZ());
    }
    
    public MBlockPos(final double x, final double y, final double z) {
        this(MathHelper.floor(x), MathHelper.floor(y), MathHelper.floor(z));
    }
    
    public MBlockPos(final int x, final int y, final int z) {
        super(0, 0, 0);
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public MBlockPos set(final Entity source) {
        return this.set(source.posX, source.posY, source.posZ);
    }
    
    public MBlockPos set(final Vec3d source) {
        return this.set(source.x, source.y, source.z);
    }
    
    public MBlockPos set(final Vec3i source) {
        return this.set(source.getX(), source.getY(), source.getZ());
    }
    
    public MBlockPos set(final double x, final double y, final double z) {
        return this.set(MathHelper.floor(x), MathHelper.floor(y), MathHelper.floor(z));
    }
    
    public MBlockPos set(final int x, final int y, final int z) {
        this.x = x;
        this.y = y;
        this.z = z;
        return this;
    }
    
    public MBlockPos add(final Vec3i vec) {
        return this.add(vec.getX(), vec.getY(), vec.getZ());
    }
    
    public MBlockPos add(final double x, final double y, final double z) {
        return this.add(MathHelper.floor(x), MathHelper.floor(y), MathHelper.floor(z));
    }
    
    public MBlockPos add(final int x, final int y, final int z) {
        return new MBlockPos(this.x + x, this.y + y, this.z + z);
    }
    
    public MBlockPos multiply(final int factor) {
        return new MBlockPos(this.x * factor, this.y * factor, this.z * factor);
    }
    
    public MBlockPos subtract(final Vec3i vec) {
        return this.subtract(vec.getX(), vec.getY(), vec.getZ());
    }
    
    public MBlockPos subtract(final double x, final double y, final double z) {
        return this.subtract(MathHelper.floor(x), MathHelper.floor(y), MathHelper.floor(z));
    }
    
    public MBlockPos subtract(final int x, final int y, final int z) {
        return new MBlockPos(this.x - x, this.y - y, this.z - z);
    }
    
    public MBlockPos up() {
        return this.up(1);
    }
    
    public MBlockPos up(final int n) {
        return this.offset(EnumFacing.UP, n);
    }
    
    public MBlockPos down() {
        return this.down(1);
    }
    
    public MBlockPos down(final int n) {
        return this.offset(EnumFacing.DOWN, n);
    }
    
    public MBlockPos north() {
        return this.north(1);
    }
    
    public MBlockPos north(final int n) {
        return this.offset(EnumFacing.NORTH, n);
    }
    
    public MBlockPos south() {
        return this.south(1);
    }
    
    public MBlockPos south(final int n) {
        return this.offset(EnumFacing.SOUTH, n);
    }
    
    public MBlockPos west() {
        return this.west(1);
    }
    
    public MBlockPos west(final int n) {
        return this.offset(EnumFacing.WEST, n);
    }
    
    public MBlockPos east() {
        return this.east(1);
    }
    
    public MBlockPos east(final int n) {
        return this.offset(EnumFacing.EAST, n);
    }
    
    public MBlockPos offset(final EnumFacing facing) {
        return this.offset(facing, 1);
    }
    
    public MBlockPos offset(final EnumFacing facing, final int n) {
        return new MBlockPos(this.x + facing.getXOffset() * n, this.y + facing.getYOffset() * n, this.z + facing.getZOffset() * n);
    }
    
    public MBlockPos crossProduct(final Vec3i vec) {
        return new MBlockPos(this.y * vec.getZ() - this.z * vec.getY(), this.z * vec.getX() - this.x * vec.getZ(), this.x * vec.getY() - this.y * vec.getX());
    }
    
    public BlockPos toImmutable() {
        return new BlockPos((Vec3i)this);
    }
    
    public int getX() {
        return this.x;
    }
    
    public int getY() {
        return this.y;
    }
    
    public int getZ() {
        return this.z;
    }
}
