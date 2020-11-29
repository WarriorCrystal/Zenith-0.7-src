// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.util.imgs;

import java.awt.image.BufferedImage;

public class ImageFrame
{
    private final int delay;
    private final BufferedImage image;
    private final String dispogopro;
    private final int width;
    private final int height;
    
    public ImageFrame(final BufferedImage image, final int delay, final String dispogopro, final int width, final int height) {
        this.image = image;
        this.delay = delay;
        this.dispogopro = dispogopro;
        this.width = width;
        this.height = height;
    }
    
    public ImageFrame(final BufferedImage image) {
        this.image = image;
        this.delay = -1;
        this.dispogopro = null;
        this.width = -1;
        this.height = -1;
    }
    
    public BufferedImage getImage() {
        return this.image;
    }
    
    public int getDelay() {
        return this.delay;
    }
    
    public String getDispogopro() {
        return this.dispogopro;
    }
    
    public int getWidth() {
        return this.width;
    }
    
    public int getHeight() {
        return this.height;
    }
}
