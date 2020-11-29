//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.managers;

import me.gopro336.goprohack.util.ReflectionUtil;
import java.io.File;
import net.minecraft.client.gui.GuiScreen;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import me.gopro336.goprohack.util.render.RenderUtil;
import me.gopro336.goprohack.GoproHackMod;
import java.util.Iterator;
import java.util.function.Function;
import java.util.Comparator;
import java.util.List;
import java.lang.reflect.Field;
import me.gopro336.goprohack.modules.Value;
import me.gopro336.goprohack.preset.Preset;
import me.gopro336.goprohack.modules.dupe.DupeFreecam;
import me.gopro336.goprohack.modules.dupe.VanishDismountModule;
import me.gopro336.goprohack.modules.dupe.DupeBotModule;
import me.gopro336.goprohack.modules.schematica.PrinterBypassModule;
import me.gopro336.goprohack.modules.schematica.PrinterModule;
import me.gopro336.goprohack.modules.world.TorchAnnihilatorModule;
import me.gopro336.goprohack.modules.world.TimerModule;
import me.gopro336.goprohack.modules.world.StashLoggerModule;
import me.gopro336.goprohack.modules.world.StashFinderModule;
import me.gopro336.goprohack.modules.world.SpeedMine;
import me.gopro336.goprohack.modules.world.ScaffoldModule;
import me.gopro336.goprohack.modules.world.NukerModule;
import me.gopro336.goprohack.modules.world.NoWeatherModule;
import me.gopro336.goprohack.modules.world.NoGlitchBlocksModule;
import me.gopro336.goprohack.modules.world.LawnmowerModule;
import me.gopro336.goprohack.modules.world.FastPlaceModule;
import me.gopro336.goprohack.modules.world.EnderChestFarmer;
import me.gopro336.goprohack.modules.world.AutoWitherModule;
import me.gopro336.goprohack.modules.world.AutoTunnelModule;
import me.gopro336.goprohack.modules.player.AutoToolModule;
import me.gopro336.goprohack.modules.world.AutoNameTagModule;
import me.gopro336.goprohack.modules.misc.DonkeySpawnNotifierModule;
import me.gopro336.goprohack.modules.world.AutoHighwayModule;
import me.gopro336.goprohack.modules.world.AutoBuilderModule;
import me.gopro336.goprohack.modules.ui.CustomFontChatModule;
import me.gopro336.goprohack.modules.ui.HudModule;
import me.gopro336.goprohack.modules.ui.HudEditorModule;
import me.gopro336.goprohack.modules.ui.ClickGuiModule;
import me.gopro336.goprohack.modules.ui.ColorsModule;
import me.gopro336.goprohack.modules.render.WaypointsModule;
import me.gopro336.goprohack.modules.render.VoidESPModule;
import me.gopro336.goprohack.modules.render.ViewClipModule;
import me.gopro336.goprohack.modules.render.TrajectoriesModule;
import me.gopro336.goprohack.modules.render.TracersModule;
import me.gopro336.goprohack.modules.render.StorageESPModule;
import me.gopro336.goprohack.modules.render.SkeletonModule;
import me.gopro336.goprohack.modules.render.ShulkerPreviewModule;
import me.gopro336.goprohack.modules.render.NoRenderModule;
import me.gopro336.goprohack.modules.render.NoBobModule;
import me.gopro336.goprohack.modules.render.NametagsModule;
import me.gopro336.goprohack.modules.render.HoleESPModule;
import me.gopro336.goprohack.modules.render.HandProgressModule;
import me.gopro336.goprohack.modules.player.FreecamModule;
import me.gopro336.goprohack.modules.render.FarmESPModule;
import me.gopro336.goprohack.modules.render.EntityESPModule;
import me.gopro336.goprohack.modules.render.ContainerPreviewModule;
import me.gopro336.goprohack.modules.render.CityESPModule;
import me.gopro336.goprohack.modules.render.ChunkAnimator;
import me.gopro336.goprohack.modules.render.BrightnessModule;
import me.gopro336.goprohack.modules.render.BlockHighlightModule;
import me.gopro336.goprohack.modules.render.AntiFog;
import me.gopro336.goprohack.modules.player.AvoidModule;
import me.gopro336.goprohack.modules.movement.YawModule;
import me.gopro336.goprohack.modules.movement.StepModule;
import me.gopro336.goprohack.modules.movement.SprintModule;
import me.gopro336.goprohack.modules.movement.SpeedModule;
import me.gopro336.goprohack.modules.movement.SneakModule;
import me.gopro336.goprohack.modules.movement.SafeWalkModule;
import me.gopro336.goprohack.modules.movement.JesusModule;
import me.gopro336.goprohack.modules.movement.NoSlowModule;
import me.gopro336.goprohack.modules.movement.NoRotateModule;
import me.gopro336.goprohack.modules.movement.NoFallModule;
import me.gopro336.goprohack.modules.movement.FlightModule;
import me.gopro336.goprohack.modules.movement.EntityControlModule;
import me.gopro336.goprohack.modules.movement.ElytraFlyModule;
import me.gopro336.goprohack.modules.movement.BlinkModule;
import me.gopro336.goprohack.modules.movement.AutoWalkModule;
import me.gopro336.goprohack.modules.movement.AntiLevitationModule;
import me.gopro336.goprohack.modules.player.XCarryModule;
import me.gopro336.goprohack.modules.player.VisualRangeModule;
import me.gopro336.goprohack.modules.misc.TotemPopNotifierModule;
import me.gopro336.goprohack.modules.misc.StopWatchModule;
import me.gopro336.goprohack.modules.misc.RetardChatModule;
import me.gopro336.goprohack.modules.player.MiddleClickFriendsModule;
import me.gopro336.goprohack.modules.player.HotbarCacheModule;
import me.gopro336.goprohack.modules.exploit.SoundLocaterModule;
import me.gopro336.goprohack.modules.player.FriendsModule;
import me.gopro336.goprohack.modules.player.FakePlayer;
import me.gopro336.goprohack.modules.misc.DiscordRPCModule;
import me.gopro336.goprohack.modules.player.ChestSwapModule;
import me.gopro336.goprohack.modules.misc.ChestStealerModule;
import me.gopro336.goprohack.modules.misc.ChatNotifierModule;
import me.gopro336.goprohack.modules.misc.ChatModificationsModule;
import me.gopro336.goprohack.modules.world.BuildHeightModule;
import me.gopro336.goprohack.modules.misc.AutoTendModule;
import me.gopro336.goprohack.modules.misc.AutoTameModule;
import me.gopro336.goprohack.modules.misc.AutoSignModule;
import me.gopro336.goprohack.modules.misc.AutoShovelPathModule;
import me.gopro336.goprohack.modules.misc.AutoShearModule;
import me.gopro336.goprohack.modules.misc.AutoReconnectModule;
import me.gopro336.goprohack.modules.misc.AutoMountModule;
import me.gopro336.goprohack.modules.misc.AutoMendArmorModule;
import me.gopro336.goprohack.modules.misc.AutoFarmlandModule;
import me.gopro336.goprohack.modules.misc.AutoDyeModule;
import me.gopro336.goprohack.modules.misc.AutoEatModule;
import me.gopro336.goprohack.modules.misc.AutoBonemealModule;
import me.gopro336.goprohack.modules.player.AntiAFKModule;
import me.gopro336.goprohack.modules.exploit.ThunderHackModule;
import me.gopro336.goprohack.modules.exploit.SwingModule;
import me.gopro336.goprohack.modules.exploit.PortalGodModeModule;
import me.gopro336.goprohack.modules.exploit.PacketFlyModule;
import me.gopro336.goprohack.modules.exploit.PacketCancellerModule;
import me.gopro336.goprohack.modules.exploit.NewChunksModule;
import me.gopro336.goprohack.modules.exploit.NoMiningTrace;
import me.gopro336.goprohack.modules.exploit.MountBypassModule;
import me.gopro336.goprohack.modules.exploit.LiquidInteractModule;
import me.gopro336.goprohack.modules.exploit.EntityDesyncModule;
import me.gopro336.goprohack.modules.exploit.CrashExploitModule;
import me.gopro336.goprohack.modules.exploit.CoordTPExploitModule;
import me.gopro336.goprohack.modules.exploit.AntiHungerModule;
import me.gopro336.goprohack.modules.combat.SelfWebModule;
import me.gopro336.goprohack.modules.combat.AntiFaceplaceModule;
import me.gopro336.goprohack.modules.combat.VelocityModule;
import me.gopro336.goprohack.modules.combat.SurroundModule;
import me.gopro336.goprohack.modules.combat.SelfTrapModule;
import me.gopro336.goprohack.modules.exploit.ReachModule;
import me.gopro336.goprohack.modules.combat.OffhandModule;
import me.gopro336.goprohack.modules.combat.KillAuraModule;
import me.gopro336.goprohack.modules.combat.HoleFillerModule;
import me.gopro336.goprohack.modules.combat.CriticalsModule;
import me.gopro336.goprohack.modules.combat.BowSpamModule;
import me.gopro336.goprohack.modules.combat.AutoTrapFeet;
import me.gopro336.goprohack.modules.combat.AutoTrap;
import me.gopro336.goprohack.modules.combat.AutoTotemModule;
import me.gopro336.goprohack.modules.combat.AutoCrystalOptimise;
import me.gopro336.goprohack.modules.combat.AutoCrystalModule;
import me.gopro336.goprohack.modules.combat.AutoCityModule;
import me.gopro336.goprohack.modules.combat.AutoArmorModule;
import me.gopro336.goprohack.modules.combat.Auto32kModule;
import me.gopro336.goprohack.modules.combat.AntiTrapCityModule;
import me.gopro336.goprohack.modules.combat.AntiBots;
import me.gopro336.goprohack.modules.combat.AimbotModule;
import me.gopro336.goprohack.main.GoproHack;
import me.gopro336.goprohack.modules.ui.KeybindsModule;
import me.gopro336.goprohack.modules.Module;
import java.util.ArrayList;

public class ModuleManager
{
    public ArrayList<Module> Mods;
    private ArrayList<Module> ArrayListAnimations;
    private KeybindsModule Keybinds;
    
    public static ModuleManager Get() {
        return GoproHack.GetModuleManager();
    }
    
    public ModuleManager() {
        this.Mods = new ArrayList<Module>();
        this.ArrayListAnimations = new ArrayList<Module>();
        this.Keybinds = null;
    }
    
    public void Init() {
        this.Add(new AimbotModule());
        this.Add(new AntiBots());
        this.Add(new AntiTrapCityModule());
        this.Add(new Auto32kModule());
        this.Add(new AutoArmorModule());
        this.Add(new AutoCityModule());
        this.Add(new AutoCrystalModule());
        this.Add(new AutoCrystalOptimise());
        this.Add(new AutoTotemModule());
        this.Add(new AutoTrap());
        this.Add(new AutoTrapFeet());
        this.Add(new BowSpamModule());
        this.Add(new CriticalsModule());
        this.Add(new HoleFillerModule());
        this.Add(new KillAuraModule());
        this.Add(new OffhandModule());
        this.Add(new ReachModule());
        this.Add(new SelfTrapModule());
        this.Add(new SurroundModule());
        this.Add(new VelocityModule());
        this.Add(new AntiFaceplaceModule());
        this.Add(new SelfWebModule());
        this.Add(new AntiHungerModule());
        this.Add(new CoordTPExploitModule());
        this.Add(new CrashExploitModule());
        this.Add(new EntityDesyncModule());
        this.Add(new LiquidInteractModule());
        this.Add(new MountBypassModule());
        this.Add(new NoMiningTrace());
        this.Add(new NewChunksModule());
        this.Add(new PacketCancellerModule());
        this.Add(new PacketFlyModule());
        this.Add(new PortalGodModeModule());
        this.Add(new SwingModule());
        this.Add(new ThunderHackModule());
        this.Add(new AntiAFKModule());
        this.Add(new AutoBonemealModule());
        this.Add(new AutoEatModule());
        this.Add(new AutoDyeModule());
        this.Add(new AutoFarmlandModule());
        this.Add(new AutoMendArmorModule());
        this.Add(new AutoMountModule());
        this.Add(new AutoReconnectModule());
        this.Add(new AutoShearModule());
        this.Add(new AutoShovelPathModule());
        this.Add(new AutoSignModule());
        this.Add(new AutoTameModule());
        this.Add(new AutoTendModule());
        this.Add(new BuildHeightModule());
        this.Add(new ChatModificationsModule());
        this.Add(new ChatNotifierModule());
        this.Add(new ChestStealerModule());
        this.Add(new ChestSwapModule());
        this.Add(new DiscordRPCModule());
        this.Add(new FakePlayer());
        this.Add(new FriendsModule());
        this.Add(new SoundLocaterModule());
        this.Add(new HotbarCacheModule());
        this.Add(new MiddleClickFriendsModule());
        this.Add(new RetardChatModule());
        this.Add(new StopWatchModule());
        this.Add(new TotemPopNotifierModule());
        this.Add(new VisualRangeModule());
        this.Add(new XCarryModule());
        this.Add(new AntiLevitationModule());
        this.Add(new AutoWalkModule());
        this.Add(new BlinkModule());
        this.Add(new ElytraFlyModule());
        this.Add(new EntityControlModule());
        this.Add(new FlightModule());
        this.Add(new NoFallModule());
        this.Add(new NoRotateModule());
        this.Add(new NoSlowModule());
        this.Add(new JesusModule());
        this.Add(new SafeWalkModule());
        this.Add(new SneakModule());
        this.Add(new SpeedModule());
        this.Add(new SprintModule());
        this.Add(new StepModule());
        this.Add(new YawModule());
        this.Add(new AvoidModule());
        this.Add(new AntiFog());
        this.Add(new BlockHighlightModule());
        this.Add(new BrightnessModule());
        this.Add(new ChunkAnimator());
        this.Add(new CityESPModule());
        this.Add(new ContainerPreviewModule());
        this.Add(new EntityESPModule());
        this.Add(new FarmESPModule());
        this.Add(new FreecamModule());
        this.Add(new HandProgressModule());
        this.Add(new HoleESPModule());
        this.Add(new NametagsModule());
        this.Add(new NoBobModule());
        this.Add(new NoRenderModule());
        this.Add(new ShulkerPreviewModule());
        this.Add(new SkeletonModule());
        this.Add(new StorageESPModule());
        this.Add(new TracersModule());
        this.Add(new TrajectoriesModule());
        this.Add(new ViewClipModule());
        this.Add(new VoidESPModule());
        this.Add(new WaypointsModule());
        this.Add(new ColorsModule());
        this.Add(new ClickGuiModule());
        this.Add(new HudEditorModule());
        this.Add(new HudModule());
        this.Add(this.Keybinds = new KeybindsModule());
        this.Add(new CustomFontChatModule());
        this.Add(new AutoBuilderModule());
        this.Add(new AutoHighwayModule());
        this.Add(new DonkeySpawnNotifierModule());
        this.Add(new AutoNameTagModule());
        this.Add(new AutoToolModule());
        this.Add(new AutoTunnelModule());
        this.Add(new AutoWitherModule());
        this.Add(new EnderChestFarmer());
        this.Add(new FastPlaceModule());
        this.Add(new LawnmowerModule());
        this.Add(new NoGlitchBlocksModule());
        this.Add(new NoWeatherModule());
        this.Add(new NukerModule());
        this.Add(new ScaffoldModule());
        this.Add(new SpeedMine());
        this.Add(new StashFinderModule());
        this.Add(new StashLoggerModule());
        this.Add(new TimerModule());
        this.Add(new TorchAnnihilatorModule());
        this.Add(new PrinterModule());
        this.Add(new PrinterBypassModule());
        this.Add(new DupeBotModule());
        this.Add(new VanishDismountModule());
        this.Add(new DupeFreecam());
        this.LoadExternalModules();
        this.Mods.sort((p_Mod1, p_Mod2) -> p_Mod1.getDisplayName().compareTo(p_Mod2.getDisplayName()));
        final Preset preset = PresetsManager.Get().getActivePreset();
        this.Mods.forEach(mod -> preset.initValuesForMod(mod));
        this.Mods.forEach(mod -> mod.init());
    }
    
    public void Add(final Module mod) {
        try {
            for (final Field field : mod.getClass().getDeclaredFields()) {
                if (Value.class.isAssignableFrom(field.getType())) {
                    if (!field.isAccessible()) {
                        field.setAccessible(true);
                    }
                    final Value val = (Value)field.get(mod);
                    val.InitalizeMod(mod);
                    mod.getValueList().add(val);
                }
            }
            this.Mods.add(mod);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public final List<Module> GetModuleList(final Module.ModuleType p_Type) {
        final List<Module> list = new ArrayList<Module>();
        for (final Module module : this.Mods) {
            if (module.getType().equals(p_Type)) {
                list.add(module);
            }
        }
        list.sort(Comparator.comparing((Function<? super Module, ? extends Comparable>)Module::getDisplayName));
        return list;
    }
    
    public final List<Module> GetModuleList() {
        return this.Mods;
    }
    
    public void OnKeyPress(final String string) {
        if (string == null || string.isEmpty() || string.equalsIgnoreCase("NONE")) {
            return;
        }
        this.Mods.forEach(p_Mod -> {
            if (p_Mod.IsKeyPressed(string)) {
                p_Mod.toggle();
            }
        });
    }
    
    public Module GetMod(final Class p_Class) {
        for (final Module l_Mod : this.Mods) {
            if (l_Mod.getClass() == p_Class) {
                return l_Mod;
            }
        }
        GoproHackMod.log.error("Could not find the class " + p_Class.getName() + " in Mods list");
        return null;
    }
    
    public Module GetModLike(final String p_String) {
        for (final Module l_Mod : this.Mods) {
            if (l_Mod.GetArrayListDisplayName().toLowerCase().startsWith(p_String.toLowerCase())) {
                return l_Mod;
            }
        }
        return null;
    }
    
    public void OnModEnable(final Module p_Mod) {
        this.ArrayListAnimations.remove(p_Mod);
        this.ArrayListAnimations.add(p_Mod);
        final String firstName;
        final String secondName;
        final float dif;
        final Comparator<Module> comparator = (first, second) -> {
            firstName = first.GetFullArrayListDisplayName();
            secondName = second.GetFullArrayListDisplayName();
            dif = RenderUtil.getStringWidth(secondName) - RenderUtil.getStringWidth(firstName);
            return (dif != 0.0f) ? ((int)dif) : secondName.compareTo(firstName);
        };
        this.ArrayListAnimations = this.ArrayListAnimations.stream().sorted((Comparator<? super Object>)comparator).collect((Collector<? super Object, ?, ArrayList<Module>>)Collectors.toList());
    }
    
    public void Update() {
        if (this.ArrayListAnimations.isEmpty()) {
            return;
        }
        final Module module;
        final Module l_Mod = module = this.ArrayListAnimations.get(0);
        final float remainingXAnimation = module.RemainingXAnimation - RenderUtil.getStringWidth(l_Mod.GetFullArrayListDisplayName()) / 10.0f;
        module.RemainingXAnimation = remainingXAnimation;
        if (remainingXAnimation <= 0.0f) {
            this.ArrayListAnimations.remove(l_Mod);
            l_Mod.RemainingXAnimation = 0.0f;
        }
    }
    
    public boolean IgnoreStrictKeybinds() {
        return (GuiScreen.isAltKeyDown() && !this.Keybinds.Alt.getValue()) || (GuiScreen.isCtrlKeyDown() && !this.Keybinds.Ctrl.getValue()) || (GuiScreen.isShiftKeyDown() && !this.Keybinds.Shift.getValue());
    }
    
    public void LoadExternalModules() {
        try {
            final File dir = new File("GoproHack/CustomMods");
            for (final Class newClass : ReflectionUtil.getClassesEx(dir.getPath())) {
                if (newClass == null) {
                    continue;
                }
                if (!Module.class.isAssignableFrom(newClass)) {
                    continue;
                }
                final Module module = newClass.newInstance();
                if (module == null) {
                    continue;
                }
                this.Add(module);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
