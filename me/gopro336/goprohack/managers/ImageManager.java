//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.managers;

import me.gopro336.goprohack.main.GoproHack;
import java.util.Iterator;
import java.util.Map;
import java.io.InputStream;
import java.awt.image.BufferedImage;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.Minecraft;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.TreeMap;
import me.gopro336.goprohack.util.imgs.GoproDynamicTexture;
import java.util.NavigableMap;

public class ImageManager
{
    public NavigableMap<String, GoproDynamicTexture> Pictures;
    
    public ImageManager() {
        this.Pictures = new TreeMap<String, GoproDynamicTexture>();
    }
    
    public void Load() {
        this.LoadImage("BloodOverlay");
        this.LoadImage("RareFrame");
        this.LoadImage("OutlinedEllipse");
        this.LoadImage("Arrow");
        this.LoadImage("blockimg");
        this.LoadImage("BlueBlur");
        this.LoadImage("Eye");
        this.LoadImage("mouse");
        this.LoadImage("questionmark");
        this.LoadImage("robotimg");
        this.LoadImage("GoproHackWatermark");
        this.LoadImage("Shield");
        this.LoadImage("skull");
    }
    
    public void LoadImage(final String p_Img) {
        BufferedImage l_Image = null;
        final InputStream l_Stream = ImageManager.class.getResourceAsStream("/assets/goprohack/imgs/" + p_Img + ".png");
        try {
            l_Image = ImageIO.read(l_Stream);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        if (l_Image == null) {
            System.out.println("Couldn't load image: " + p_Img);
            return;
        }
        final int l_Height = l_Image.getHeight();
        final int l_Width = l_Image.getWidth();
        final GoproDynamicTexture l_Texture = new GoproDynamicTexture(l_Image, l_Height, l_Width);
        if (l_Texture != null) {
            l_Texture.SetResourceLocation(Minecraft.getMinecraft().getTextureManager().getDynamicTextureLocation("goprohack/textures", (DynamicTexture)l_Texture));
            this.Pictures.put(p_Img, l_Texture);
            System.out.println("Loaded Img: " + p_Img);
        }
    }
    
    public GoproDynamicTexture GetDynamicTexture(final String p_Image) {
        if (this.Pictures.containsKey(p_Image)) {
            return this.Pictures.get(p_Image);
        }
        return null;
    }
    
    public String GetNextImage(final String value, final boolean p_Recursive) {
        String l_String = null;
        for (final Map.Entry<String, GoproDynamicTexture> l_Itr : this.Pictures.entrySet()) {
            if (!l_Itr.getKey().equalsIgnoreCase(value)) {
                continue;
            }
            if (p_Recursive) {
                l_String = this.Pictures.lowerKey(l_Itr.getKey());
                if (l_String == null) {
                    return this.Pictures.lastKey();
                }
            }
            else {
                l_String = this.Pictures.higherKey(l_Itr.getKey());
                if (l_String == null) {
                    return this.Pictures.firstKey();
                }
            }
            return l_String;
        }
        return l_String;
    }
    
    public static ImageManager Get() {
        return GoproHack.GetImageManager();
    }
}
