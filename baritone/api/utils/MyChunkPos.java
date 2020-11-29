// 
// Decompiled by Procyon v0.5.36
// 

package baritone.api.utils;

import com.google.gson.annotations.SerializedName;

public class MyChunkPos
{
    @SerializedName("x")
    public int x;
    @SerializedName("z")
    public int z;
    
    @Override
    public String toString() {
        return this.x + ", " + this.z;
    }
}
