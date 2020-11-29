// 
// Decompiled by Procyon v0.5.36
// 

package com.github.lunatrius.schematica.network.transfer;

import com.github.lunatrius.schematica.api.ISchematic;

public class SchematicTransfer
{
    public final ISchematic schematic;
    public final String name;
    public final int width;
    public final int height;
    public final int length;
    public State state;
    public int timeout;
    public int retries;
    public int baseX;
    public int baseY;
    public int baseZ;
    
    public SchematicTransfer(final ISchematic schematic, final String name) {
        this.state = State.BEGIN_WAIT;
        this.timeout = 0;
        this.retries = 0;
        this.baseX = 0;
        this.baseY = 0;
        this.baseZ = 0;
        this.schematic = schematic;
        this.name = name;
        this.width = schematic.getWidth();
        this.height = schematic.getHeight();
        this.length = schematic.getLength();
    }
    
    public boolean confirmChunk(final int chunkX, final int chunkY, final int chunkZ) {
        if (chunkX == this.baseX && chunkY == this.baseY && chunkZ == this.baseZ) {
            this.setState(State.CHUNK_WAIT);
            this.baseX += 16;
            if (this.baseX >= this.width) {
                this.baseX = 0;
                this.baseY += 16;
                if (this.baseY >= this.height) {
                    this.baseY = 0;
                    this.baseZ += 16;
                    if (this.baseZ >= this.length) {
                        this.setState(State.END_WAIT);
                        return false;
                    }
                }
            }
        }
        return true;
    }
    
    public void setState(final State state) {
        this.state = state;
        this.timeout = 0;
        this.retries = 0;
    }
    
    public enum State
    {
        BEGIN_WAIT(true), 
        BEGIN, 
        CHUNK_WAIT(true), 
        CHUNK, 
        END_WAIT(true), 
        END;
        
        private boolean waiting;
        
        private State(final boolean waiting) {
            this.waiting = waiting;
        }
        
        public boolean isWaiting() {
            return this.waiting;
        }
    }
}
