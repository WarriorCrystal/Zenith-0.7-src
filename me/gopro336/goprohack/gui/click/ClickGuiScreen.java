//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.gui.click;

import java.io.Writer;
import java.util.HashMap;
import java.nio.file.OpenOption;
import com.google.gson.GsonBuilder;
import org.lwjgl.input.Mouse;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.gui.ScaledResolution;
import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;
import java.util.Random;
import java.util.Map;
import java.nio.file.Files;
import java.nio.file.Paths;
import com.google.gson.Gson;
import java.io.File;
import me.gopro336.goprohack.managers.PresetsManager;
import me.gopro336.goprohack.gui.click.component.menus.mods.MenuComponentPresetsList;
import me.gopro336.goprohack.gui.click.component.menus.mods.MenuComponentModList;
import me.gopro336.goprohack.modules.Module;
import me.gopro336.goprohack.managers.ImageManager;
import me.gopro336.goprohack.modules.ui.ColorsModule;
import me.gopro336.goprohack.modules.ui.ClickGuiModule;
import me.gopro336.goprohack.gui.click.effects.Snow;
import me.gopro336.goprohack.util.imgs.GoproDynamicTexture;
import me.gopro336.goprohack.gui.click.component.MenuComponent;
import java.util.ArrayList;
import me.gopro336.goprohack.gui.GoproGuiScreen;

public class ClickGuiScreen extends GoproGuiScreen
{
    private ArrayList<MenuComponent> MenuComponents;
    private GoproDynamicTexture Watermark;
    private GoproDynamicTexture BlueBlur;
    private ArrayList<Snow> _snowList;
    private float OffsetY;
    private ClickGuiModule ClickGuiMod;
    
    public ClickGuiScreen(final ClickGuiModule p_Mod, final ColorsModule p_Colors) {
        this.MenuComponents = new ArrayList<MenuComponent>();
        this.Watermark = ImageManager.Get().GetDynamicTexture("GoproHackWatermark");
        this.BlueBlur = ImageManager.Get().GetDynamicTexture("BlueBlur");
        this._snowList = new ArrayList<Snow>();
        this.OffsetY = 0.0f;
        this.MenuComponents.add(new MenuComponentModList("Combat", Module.ModuleType.COMBAT, 10.0f, 3.0f, "Shield", p_Colors, p_Mod));
        this.MenuComponents.add(new MenuComponentModList("Exploit", Module.ModuleType.EXPLOIT, 120.0f, 3.0f, "skull", p_Colors, p_Mod));
        this.MenuComponents.add(new MenuComponentModList("Misc", Module.ModuleType.MISC, 230.0f, 3.0f, "questionmark", p_Colors, p_Mod));
        this.MenuComponents.add(new MenuComponentModList("Transport", Module.ModuleType.MOVEMENT, 340.0f, 3.0f, "Arrow", p_Colors, p_Mod));
        this.MenuComponents.add(new MenuComponentModList("Render", Module.ModuleType.RENDER, 450.0f, 3.0f, "Eye", p_Colors, p_Mod));
        this.MenuComponents.add(new MenuComponentModList("Player", Module.ModuleType.PLAYER, 120.0f, 213.0f, "questionmark", p_Colors, p_Mod));
        this.MenuComponents.add(new MenuComponentModList("Interface", Module.ModuleType.UI, 340.0f, 239.0f, "mouse", p_Colors, p_Mod));
        this.MenuComponents.add(new MenuComponentModList("Build", Module.ModuleType.WORLD, 562.0f, 3.0f, "blockimg", p_Colors, p_Mod));
        this.MenuComponents.add(new MenuComponentModList("Dupe", Module.ModuleType.DUPE, 562.0f, 277.0f, "robotimg", p_Colors, p_Mod));
        MenuComponentPresetsList presetList = null;
        this.MenuComponents.add(presetList = new MenuComponentPresetsList("Presets", Module.ModuleType.SCHEMATICA, 230.0f, 289.0f, "robotimg", p_Colors, p_Mod));
        PresetsManager.Get().InitalizeGUIComponent(presetList);
        this.ClickGuiMod = p_Mod;
        for (final MenuComponent l_Component : this.MenuComponents) {
            final File l_Exists = new File("GoproHack/GUI/" + l_Component.GetDisplayName() + ".json");
            if (!l_Exists.exists()) {
                continue;
            }
            try {
                final Gson gson = new Gson();
                final Reader reader = Files.newBufferedReader(Paths.get("GoproHack/GUI/" + l_Component.GetDisplayName() + ".json", new String[0]));
                final Map<?, ?> map = (Map<?, ?>)gson.fromJson(reader, (Class)Map.class);
                for (final Map.Entry<?, ?> entry : map.entrySet()) {
                    final String l_Key = (String)entry.getKey();
                    final String l_Value = (String)entry.getValue();
                    if (l_Key.equals("PosX")) {
                        l_Component.SetX(Float.parseFloat(l_Value));
                    }
                    else {
                        if (!l_Key.equals("PosY")) {
                            continue;
                        }
                        l_Component.SetY(Float.parseFloat(l_Value));
                    }
                }
                reader.close();
            }
            catch (Exception ex) {}
        }
        final Random random = new Random();
        for (int i = 0; i < 100; ++i) {
            for (int y = 0; y < 3; ++y) {
                final Snow snow = new Snow(25 * i, y * -50, random.nextInt(3) + 1, random.nextInt(2) + 1);
                this._snowList.add(snow);
            }
        }
    }
    
    public boolean doesGuiPauseGame() {
        return false;
    }
    
    @Override
    public void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
        for (final MenuComponent l_Menu : this.MenuComponents) {
            if (l_Menu.MouseClicked(mouseX, mouseY, mouseButton, this.OffsetY)) {
                break;
            }
        }
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }
    
    @Override
    public void mouseReleased(final int mouseX, final int mouseY, final int state) {
        for (final MenuComponent l_Menu : this.MenuComponents) {
            l_Menu.MouseReleased(mouseX, mouseY, state);
        }
        super.mouseReleased(mouseX, mouseY, state);
    }
    
    @Override
    public void mouseClickMove(final int mouseX, final int mouseY, final int clickedMouseButton, final long timeSinceLastClick) {
        for (final MenuComponent l_Menu : this.MenuComponents) {
            l_Menu.MouseClickMove(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
        }
        super.mouseClickMove(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
    }
    
    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        final ScaledResolution res = new ScaledResolution(this.mc);
        if (!this._snowList.isEmpty() && this.ClickGuiMod.Snowing.getValue()) {
            this._snowList.forEach(snow -> snow.Update(res));
        }
        GlStateManager.pushMatrix();
        GlStateManager.disableRescaleNormal();
        RenderHelper.disableStandardItemLighting();
        MenuComponent l_LastHovered = null;
        for (final MenuComponent l_Menu : this.MenuComponents) {
            if (l_Menu.Render(mouseX, mouseY, true, this.AllowsOverflow(), this.OffsetY)) {
                l_LastHovered = l_Menu;
            }
        }
        if (l_LastHovered != null) {
            this.MenuComponents.remove(l_LastHovered);
            this.MenuComponents.add(l_LastHovered);
        }
        RenderHelper.enableGUIStandardItemLighting();
        GlStateManager.enableRescaleNormal();
        GlStateManager.popMatrix();
        final int l_Scrolling = Mouse.getEventDWheel();
        if (l_Scrolling > 0) {
            this.OffsetY = Math.max(0.0f, this.OffsetY - 1.0f);
        }
        else if (l_Scrolling < 0) {
            this.OffsetY = Math.min(100.0f, this.OffsetY + 1.0f);
        }
    }
    
    public void keyTyped(final char typedChar, final int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);
        for (final MenuComponent l_Menu : this.MenuComponents) {
            l_Menu.keyTyped(typedChar, keyCode);
        }
    }
    
    public void onGuiClosed() {
        super.onGuiClosed();
        if (this.ClickGuiMod.isEnabled()) {
            this.ClickGuiMod.toggle();
        }
        for (final MenuComponent l_Component : this.MenuComponents) {
            try {
                final GsonBuilder builder = new GsonBuilder();
                final Gson gson = builder.setPrettyPrinting().create();
                final Writer writer = Files.newBufferedWriter(Paths.get("GoproHack/GUI/" + l_Component.GetDisplayName() + ".json", new String[0]), new OpenOption[0]);
                final Map<String, String> map = new HashMap<String, String>();
                map.put("PosX", String.valueOf(l_Component.GetX()));
                map.put("PosY", String.valueOf(l_Component.GetY()));
                gson.toJson((Object)map, (Appendable)writer);
                writer.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    public boolean AllowsOverflow() {
        return this.ClickGuiMod.AllowOverflow.getValue();
    }
    
    public void ResetToDefaults() {
        this.MenuComponents.forEach(comp -> comp.Default());
    }
}
