//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.managers;

import java.util.Iterator;
import java.util.Map;
import java.io.Writer;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.HashMap;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Paths;
import com.google.gson.GsonBuilder;
import me.gopro336.goprohack.main.GoproHack;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.opengl.GL11;
import me.gopro336.goprohack.gui.hud.GuiHudEditor;
import me.gopro336.goprohack.main.Wrapper;
import java.lang.reflect.Field;
import me.gopro336.goprohack.modules.ValueListeners;
import me.gopro336.goprohack.modules.Value;
import me.gopro336.goprohack.gui.hud.components.SelectorMenuComponent;
import me.gopro336.goprohack.gui.hud.components.SchematicaMaterialInfoComponent;
import me.gopro336.goprohack.gui.hud.components.PvPInfoComponent;
import me.gopro336.goprohack.gui.hud.components.StopwatchComponent;
import me.gopro336.goprohack.gui.hud.components.TrueDurabilityComponent;
import me.gopro336.goprohack.gui.hud.components.ChestCountComponent;
import me.gopro336.goprohack.gui.hud.components.PingComponent;
import me.gopro336.goprohack.gui.hud.components.TotemCountComponent;
import me.gopro336.goprohack.gui.hud.components.YawComponent;
import me.gopro336.goprohack.gui.hud.components.NearestEntityFrameComponent;
import me.gopro336.goprohack.gui.hud.components.PlayerFrameComponent;
import me.gopro336.goprohack.gui.hud.components.PlayerCountComponent;
import me.gopro336.goprohack.gui.hud.components.HoleInfoComponent;
import me.gopro336.goprohack.gui.hud.components.KeyStrokesComponent;
import me.gopro336.goprohack.gui.hud.components.ArmorComponent;
import me.gopro336.goprohack.gui.hud.components.TooltipComponent;
import me.gopro336.goprohack.gui.hud.components.DirectionComponent;
import me.gopro336.goprohack.gui.hud.components.FPSComponent;
import me.gopro336.goprohack.gui.hud.components.TPSComponent;
import me.gopro336.goprohack.gui.hud.components.TimeComponent;
import me.gopro336.goprohack.gui.hud.components.BiomeComponent;
import me.gopro336.goprohack.gui.hud.components.SpeedComponent;
import me.gopro336.goprohack.gui.hud.components.CoordsComponent;
import me.gopro336.goprohack.gui.hud.components.NotificationComponent;
import me.gopro336.goprohack.gui.hud.components.TabGUIComponent;
import me.gopro336.goprohack.gui.hud.components.InventoryComponent;
import me.gopro336.goprohack.gui.hud.components.WelcomeComponent;
import me.gopro336.goprohack.gui.hud.components.ArrayList2Component;
import me.gopro336.goprohack.gui.hud.components.ArrayListComponent;
import me.gopro336.goprohack.gui.hud.components.WatermarkComponent;
import me.gopro336.goprohack.gui.hud.HudComponentItem;
import java.util.ArrayList;

public class HudManager
{
    public ArrayList<HudComponentItem> Items;
    private boolean CanSave;
    
    public HudManager() {
        this.Items = new ArrayList<HudComponentItem>();
        this.CanSave = false;
    }
    
    public void Init() {
        this.Add(new WatermarkComponent());
        this.Add(new ArrayListComponent());
        this.Add(new ArrayList2Component());
        this.Add(new WelcomeComponent());
        this.Add(new InventoryComponent());
        this.Add(new TabGUIComponent());
        this.Add(new NotificationComponent());
        this.Add(new CoordsComponent());
        this.Add(new SpeedComponent());
        this.Add(new BiomeComponent());
        this.Add(new TimeComponent());
        this.Add(new TPSComponent());
        this.Add(new FPSComponent());
        this.Add(new DirectionComponent());
        this.Add(new TooltipComponent());
        this.Add(new ArmorComponent());
        this.Add(new KeyStrokesComponent());
        this.Add(new HoleInfoComponent());
        this.Add(new PlayerCountComponent());
        this.Add(new PlayerFrameComponent());
        this.Add(new NearestEntityFrameComponent());
        this.Add(new YawComponent());
        this.Add(new TotemCountComponent());
        this.Add(new PingComponent());
        this.Add(new ChestCountComponent());
        this.Add(new TrueDurabilityComponent());
        this.Add(new StopwatchComponent());
        this.Add(new PvPInfoComponent());
        this.Add(new SchematicaMaterialInfoComponent());
        this.Add(new SelectorMenuComponent());
        this.CanSave = false;
        this.Items.forEach(p_Item -> p_Item.LoadSettings());
        this.CanSave = true;
    }
    
    public void Add(final HudComponentItem p_Item) {
        try {
            for (final Field field : p_Item.getClass().getDeclaredFields()) {
                if (Value.class.isAssignableFrom(field.getType())) {
                    if (!field.isAccessible()) {
                        field.setAccessible(true);
                    }
                    final Value val = (Value)field.get(p_Item);
                    final ValueListeners listener = new ValueListeners() {
                        @Override
                        public void OnValueChange(final Value p_Val) {
                            HudManager.this.ScheduleSave(p_Item);
                        }
                    };
                    val.Listener = listener;
                    p_Item.ValueList.add(val);
                }
            }
            this.Items.add(p_Item);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void OnRender(final float p_PartialTicks) {
        final GuiScreen l_CurrScreen = Wrapper.GetMC().currentScreen;
        if (l_CurrScreen != null && l_CurrScreen instanceof GuiHudEditor) {
            return;
        }
        GL11.glPushMatrix();
        this.Items.forEach(p_Item -> {
            if (!p_Item.IsHidden() && !p_Item.HasFlag(HudComponentItem.OnlyVisibleInHudEditor)) {
                try {
                    p_Item.render(0, 0, p_PartialTicks);
                }
                catch (Exception e) {
                    System.out.println(e.toString());
                }
            }
            return;
        });
        GL11.glPopMatrix();
    }
    
    public static HudManager Get() {
        return GoproHack.GetHudManager();
    }
    
    public void ScheduleSave(final HudComponentItem p_Item) {
        if (!this.CanSave) {
            return;
        }
        try {
            final GsonBuilder builder = new GsonBuilder();
            final Gson gson = builder.setPrettyPrinting().create();
            final Writer writer = Files.newBufferedWriter(Paths.get("GoproHack/HUD/" + p_Item.GetDisplayName() + ".json", new String[0]), new OpenOption[0]);
            final Map<String, String> map = new HashMap<String, String>();
            map.put("displayname", p_Item.GetDisplayName());
            map.put("visible", p_Item.IsHidden() ? "false" : "true");
            map.put("PositionX", String.valueOf(p_Item.GetX()));
            map.put("PositionY", String.valueOf(p_Item.GetY()));
            map.put("ClampLevel", String.valueOf(p_Item.GetClampLevel()));
            map.put("ClampPositionX", String.valueOf(p_Item.GetX()));
            map.put("ClampPositionY", String.valueOf(p_Item.GetY()));
            map.put("Side", String.valueOf(p_Item.GetSide()));
            for (final Value l_Val : p_Item.ValueList) {
                map.put(l_Val.getName().toString(), l_Val.getValue().toString());
            }
            gson.toJson((Object)map, (Appendable)writer);
            writer.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
