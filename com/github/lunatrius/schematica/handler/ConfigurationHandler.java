//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package com.github.lunatrius.schematica.handler;

import java.util.HashSet;
import java.util.ArrayDeque;
import java.util.Arrays;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import java.io.IOException;
import com.github.lunatrius.schematica.reference.Reference;
import com.github.lunatrius.schematica.Schematica;
import net.minecraft.block.Block;
import java.util.Set;
import net.minecraftforge.common.config.Property;
import java.util.Queue;
import java.io.File;
import net.minecraftforge.common.config.Configuration;

public class ConfigurationHandler
{
    public static final ConfigurationHandler INSTANCE;
    public static final String VERSION = "1";
    public static Configuration configuration;
    public static final boolean DUMP_BLOCK_LIST_DEFAULT = false;
    public static final boolean SHOW_DEBUG_INFO_DEFAULT = true;
    public static final boolean ENABLE_ALPHA_DEFAULT = false;
    public static final double ALPHA_DEFAULT = 1.0;
    public static final boolean HIGHLIGHT_DEFAULT = true;
    public static final boolean HIGHLIGHT_AIR_DEFAULT = true;
    public static final double BLOCK_DELTA_DEFAULT = 0.005;
    public static final int RENDER_DISTANCE_DEFAULT = 8;
    public static final int PLACE_DELAY_DEFAULT = 1;
    public static final int TIMEOUT_DEFAULT = 10;
    public static final int PLACE_DISTANCE_DEFAULT = 5;
    public static final boolean PLACE_INSTANTLY_DEFAULT = false;
    public static final boolean DESTROY_BLOCKS_DEFAULT = false;
    public static final boolean DESTROY_INSTANTLY_DEFAULT = false;
    public static final boolean PLACE_ADJACENT_DEFAULT = true;
    public static final boolean[] SWAP_SLOTS_DEFAULT;
    public static final String SCHEMATIC_DIRECTORY_STR = "./schematics";
    public static final File SCHEMATIC_DIRECTORY_DEFAULT;
    public static final String[] EXTRA_AIR_BLOCKS_DEFAULT;
    public static final String SORT_TYPE_DEFAULT = "";
    public static final boolean PRINTER_ENABLED_DEFAULT = true;
    public static final boolean SAVE_ENABLED_DEFAULT = true;
    public static final boolean LOAD_ENABLED_DEFAULT = true;
    public static final int PLAYER_QUOTA_KILOBYTES_DEFAULT = 8192;
    public static boolean dumpBlockList;
    public static boolean showDebugInfo;
    public static boolean enableAlpha;
    public static float alpha;
    public static boolean highlight;
    public static boolean highlightAir;
    public static double blockDelta;
    public static int renderDistance;
    public static int placeDelay;
    public static int timeout;
    public static int placeDistance;
    public static boolean placeInstantly;
    public static boolean destroyBlocks;
    public static boolean destroyInstantly;
    public static boolean placeAdjacent;
    public static boolean[] swapSlots;
    public static final Queue<Integer> swapSlotsQueue;
    public static File schematicDirectory;
    public static String[] extraAirBlocks;
    public static String sortType;
    public static boolean printerEnabled;
    public static boolean saveEnabled;
    public static boolean loadEnabled;
    public static int playerQuotaKilobytes;
    public static Property propDumpBlockList;
    public static Property propShowDebugInfo;
    public static Property propEnableAlpha;
    public static Property propAlpha;
    public static Property propHighlight;
    public static Property propHighlightAir;
    public static Property propBlockDelta;
    public static Property propRenderDistance;
    public static Property propPlaceDelay;
    public static Property propTimeout;
    public static Property propPlaceDistance;
    public static Property propPlaceInstantly;
    public static Property propDestroyBlocks;
    public static Property propDestroyInstantly;
    public static Property propPlaceAdjacent;
    public static Property[] propSwapSlots;
    public static Property propSchematicDirectory;
    public static Property propExtraAirBlocks;
    public static Property propSortType;
    public static Property propPrinterEnabled;
    public static Property propSaveEnabled;
    public static Property propLoadEnabled;
    public static Property propPlayerQuotaKilobytes;
    private static final Set<Block> extraAirBlockList;
    
    public static void init(final File configFile) {
        if (ConfigurationHandler.configuration == null) {
            ConfigurationHandler.configuration = new Configuration(configFile, "1");
            loadConfiguration();
        }
    }
    
    public static void loadConfiguration() {
        loadConfigurationDebug();
        loadConfigurationRender();
        loadConfigurationPrinter();
        loadConfigurationSwapSlots();
        loadConfigurationGeneral();
        loadConfigurationServer();
        Schematica.proxy.createFolders();
        if (ConfigurationHandler.configuration.hasChanged()) {
            ConfigurationHandler.configuration.save();
        }
    }
    
    private static void loadConfigurationDebug() {
        (ConfigurationHandler.propDumpBlockList = ConfigurationHandler.configuration.get("debug", "dumpBlockList", false, "Dump all block states on startup.")).setLanguageKey("schematica.config.dumpBlockList");
        ConfigurationHandler.propDumpBlockList.requiresMcRestart();
        ConfigurationHandler.dumpBlockList = ConfigurationHandler.propDumpBlockList.getBoolean(false);
        (ConfigurationHandler.propShowDebugInfo = ConfigurationHandler.configuration.get("debug", "showDebugInfo", true, "Display extra information on the debug screen (F3).")).setLanguageKey("schematica.config.showDebugInfo");
        ConfigurationHandler.showDebugInfo = ConfigurationHandler.propShowDebugInfo.getBoolean(true);
    }
    
    private static void loadConfigurationRender() {
        (ConfigurationHandler.propEnableAlpha = ConfigurationHandler.configuration.get("render", "alphaEnabled", false, "Enable transparent textures.")).setLanguageKey("schematica.config.alphaEnabled");
        ConfigurationHandler.enableAlpha = ConfigurationHandler.propEnableAlpha.getBoolean(false);
        (ConfigurationHandler.propAlpha = ConfigurationHandler.configuration.get("render", "alpha", 1.0, "Alpha value used when rendering the schematic (1.0 = opaque, 0.5 = half transparent, 0.0 = transparent).", 0.0, 1.0)).setLanguageKey("schematica.config.alpha");
        ConfigurationHandler.alpha = (float)ConfigurationHandler.propAlpha.getDouble(1.0);
        (ConfigurationHandler.propHighlight = ConfigurationHandler.configuration.get("render", "highlight", true, "Highlight invalid placed blocks and to be placed blocks.")).setLanguageKey("schematica.config.highlight");
        ConfigurationHandler.highlight = ConfigurationHandler.propHighlight.getBoolean(true);
        (ConfigurationHandler.propHighlightAir = ConfigurationHandler.configuration.get("render", "highlightAir", true, "Highlight blocks that should be air.")).setLanguageKey("schematica.config.highlightAir");
        ConfigurationHandler.highlightAir = ConfigurationHandler.propHighlightAir.getBoolean(true);
        (ConfigurationHandler.propBlockDelta = ConfigurationHandler.configuration.get("render", "blockDelta", 0.005, "Delta value used for highlighting (if you experience z-fighting increase this).", 0.0, 0.2)).setLanguageKey("schematica.config.blockDelta");
        ConfigurationHandler.blockDelta = ConfigurationHandler.propBlockDelta.getDouble(0.005);
        (ConfigurationHandler.propRenderDistance = ConfigurationHandler.configuration.get("render", "renderDistance", 8, "Schematic render distance.", 2, 16)).setLanguageKey("schematica.config.renderDistance");
        ConfigurationHandler.renderDistance = ConfigurationHandler.propRenderDistance.getInt(8);
    }
    
    private static void loadConfigurationPrinter() {
        (ConfigurationHandler.propPlaceDelay = ConfigurationHandler.configuration.get("printer", "placeDelay", 1, "Delay between placement attempts (in ticks).", 0, 20)).setLanguageKey("schematica.config.placeDelay");
        ConfigurationHandler.placeDelay = ConfigurationHandler.propPlaceDelay.getInt(1);
        (ConfigurationHandler.propTimeout = ConfigurationHandler.configuration.get("printer", "timeout", 10, "Timeout before re-trying failed blocks.", 0, 100)).setLanguageKey("schematica.config.timeout");
        ConfigurationHandler.timeout = ConfigurationHandler.propTimeout.getInt(10);
        (ConfigurationHandler.propPlaceDistance = ConfigurationHandler.configuration.get("printer", "placeDistance", 5, "Maximum placement distance.", 1, 5)).setLanguageKey("schematica.config.placeDistance");
        ConfigurationHandler.placeDistance = ConfigurationHandler.propPlaceDistance.getInt(5);
        (ConfigurationHandler.propPlaceInstantly = ConfigurationHandler.configuration.get("printer", "placeInstantly", false, "Place all blocks that can be placed in one tick.")).setLanguageKey("schematica.config.placeInstantly");
        ConfigurationHandler.placeInstantly = ConfigurationHandler.propPlaceInstantly.getBoolean(false);
        (ConfigurationHandler.propDestroyBlocks = ConfigurationHandler.configuration.get("printer", "destroyBlocks", false, "The printer will destroy blocks (creative mode only).")).setLanguageKey("schematica.config.destroyBlocks");
        ConfigurationHandler.destroyBlocks = ConfigurationHandler.propDestroyBlocks.getBoolean(false);
        (ConfigurationHandler.propDestroyInstantly = ConfigurationHandler.configuration.get("printer", "destroyInstantly", false, "Destroy all blocks that can be destroyed in one tick.")).setLanguageKey("schematica.config.destroyInstantly");
        ConfigurationHandler.destroyInstantly = ConfigurationHandler.propDestroyInstantly.getBoolean(false);
        (ConfigurationHandler.propPlaceAdjacent = ConfigurationHandler.configuration.get("printer", "placeAdjacent", true, "Place blocks only if there is an adjacent block next to them.")).setLanguageKey("schematica.config.placeAdjacent");
        ConfigurationHandler.placeAdjacent = ConfigurationHandler.propPlaceAdjacent.getBoolean(true);
    }
    
    private static void loadConfigurationSwapSlots() {
        ConfigurationHandler.swapSlotsQueue.clear();
        for (int i = 0; i < ConfigurationHandler.SWAP_SLOTS_DEFAULT.length; ++i) {
            (ConfigurationHandler.propSwapSlots[i] = ConfigurationHandler.configuration.get("printer.swapslots", "swapSlot" + i, ConfigurationHandler.SWAP_SLOTS_DEFAULT[i], "Allow the printer to use this hotbar slot.")).setLanguageKey("schematica.config.swapSlot" + i);
            ConfigurationHandler.swapSlots[i] = ConfigurationHandler.propSwapSlots[i].getBoolean(ConfigurationHandler.SWAP_SLOTS_DEFAULT[i]);
            if (ConfigurationHandler.swapSlots[i]) {
                ConfigurationHandler.swapSlotsQueue.offer(i);
            }
        }
    }
    
    private static void loadConfigurationGeneral() {
        (ConfigurationHandler.propSchematicDirectory = ConfigurationHandler.configuration.get("general", "schematicDirectory", "./schematics", "Schematic directory.")).setLanguageKey("schematica.config.schematicDirectory");
        ConfigurationHandler.schematicDirectory = getSchematicDirectoryFile(ConfigurationHandler.propSchematicDirectory.getString());
        (ConfigurationHandler.propExtraAirBlocks = ConfigurationHandler.configuration.get("general", "extraAirBlocks", ConfigurationHandler.EXTRA_AIR_BLOCKS_DEFAULT, "Extra blocks to consider as air for the schematic renderer.")).setLanguageKey("schematica.config.extraAirBlocks");
        ConfigurationHandler.extraAirBlocks = ConfigurationHandler.propExtraAirBlocks.getStringList();
        (ConfigurationHandler.propSortType = ConfigurationHandler.configuration.get("general", "sortType", "", "Default sort type for the material list.")).setShowInGui(false);
        ConfigurationHandler.sortType = ConfigurationHandler.propSortType.getString();
        normalizeSchematicPath();
        populateExtraAirBlocks();
    }
    
    private static File getSchematicDirectoryFile(final String path) {
        if (path.startsWith(".")) {
            return Schematica.proxy.getDirectory(path);
        }
        return new File(path);
    }
    
    private static void normalizeSchematicPath() {
        try {
            ConfigurationHandler.schematicDirectory = ConfigurationHandler.schematicDirectory.getCanonicalFile();
            final String schematicPath = ConfigurationHandler.schematicDirectory.getAbsolutePath();
            final String dataPath = Schematica.proxy.getDataDirectory().getAbsolutePath();
            final String newSchematicPath = mergePaths(schematicPath, dataPath);
            ConfigurationHandler.propSchematicDirectory.set(newSchematicPath);
            Reference.logger.debug("Schematic path: {}", (Object)schematicPath);
            Reference.logger.debug("Data path: {}", (Object)dataPath);
            Reference.logger.debug("New schematic path: {}", (Object)newSchematicPath);
        }
        catch (IOException e) {
            Reference.logger.warn("Could not canonize path!", (Throwable)e);
        }
    }
    
    private static String mergePaths(final String schematicPath, final String dataPath) {
        String newPath;
        if (schematicPath.startsWith(dataPath)) {
            newPath = "." + schematicPath.substring(dataPath.length());
        }
        else {
            newPath = schematicPath;
        }
        return newPath.replace("\\", "/");
    }
    
    private static void populateExtraAirBlocks() {
        ConfigurationHandler.extraAirBlockList.clear();
        for (final String name : ConfigurationHandler.extraAirBlocks) {
            final Block block = (Block)Block.REGISTRY.getObject((Object)new ResourceLocation(name));
            if (block != Blocks.AIR) {
                ConfigurationHandler.extraAirBlockList.add(block);
            }
        }
    }
    
    private static void loadConfigurationServer() {
        (ConfigurationHandler.propPrinterEnabled = ConfigurationHandler.configuration.get("server", "printerEnabled", true, "Allow players to use the printer.")).setLanguageKey("schematica.config.printerEnabled");
        ConfigurationHandler.printerEnabled = ConfigurationHandler.propPrinterEnabled.getBoolean(true);
        (ConfigurationHandler.propSaveEnabled = ConfigurationHandler.configuration.get("server", "saveEnabled", true, "Allow players to save schematics.")).setLanguageKey("schematica.config.saveEnabled");
        ConfigurationHandler.saveEnabled = ConfigurationHandler.propSaveEnabled.getBoolean(true);
        (ConfigurationHandler.propLoadEnabled = ConfigurationHandler.configuration.get("server", "loadEnabled", true, "Allow players to load schematics.")).setLanguageKey("schematica.config.loadEnabled");
        ConfigurationHandler.loadEnabled = ConfigurationHandler.propLoadEnabled.getBoolean(true);
        (ConfigurationHandler.propPlayerQuotaKilobytes = ConfigurationHandler.configuration.get("server", "playerQuotaKilobytes", 8192, "Amount of storage provided per-player for schematics on the server.")).setLanguageKey("schematica.config.playerQuotaKilobytes");
        ConfigurationHandler.playerQuotaKilobytes = ConfigurationHandler.propPlayerQuotaKilobytes.getInt(8192);
    }
    
    private ConfigurationHandler() {
    }
    
    @SubscribeEvent
    public void onConfigurationChangedEvent(final ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.getModID().equalsIgnoreCase("schematica")) {
            loadConfiguration();
        }
    }
    
    public static boolean isExtraAirBlock(final Block block) {
        return ConfigurationHandler.extraAirBlockList.contains(block);
    }
    
    static {
        INSTANCE = new ConfigurationHandler();
        SWAP_SLOTS_DEFAULT = new boolean[] { false, false, false, false, false, true, true, true, true };
        SCHEMATIC_DIRECTORY_DEFAULT = new File(Schematica.proxy.getDataDirectory(), "./schematics");
        EXTRA_AIR_BLOCKS_DEFAULT = new String[0];
        ConfigurationHandler.dumpBlockList = false;
        ConfigurationHandler.showDebugInfo = true;
        ConfigurationHandler.enableAlpha = false;
        ConfigurationHandler.alpha = 1.0f;
        ConfigurationHandler.highlight = true;
        ConfigurationHandler.highlightAir = true;
        ConfigurationHandler.blockDelta = 0.005;
        ConfigurationHandler.renderDistance = 8;
        ConfigurationHandler.placeDelay = 1;
        ConfigurationHandler.timeout = 10;
        ConfigurationHandler.placeDistance = 5;
        ConfigurationHandler.placeInstantly = false;
        ConfigurationHandler.destroyBlocks = false;
        ConfigurationHandler.destroyInstantly = false;
        ConfigurationHandler.placeAdjacent = true;
        ConfigurationHandler.swapSlots = Arrays.copyOf(ConfigurationHandler.SWAP_SLOTS_DEFAULT, ConfigurationHandler.SWAP_SLOTS_DEFAULT.length);
        swapSlotsQueue = new ArrayDeque<Integer>();
        ConfigurationHandler.schematicDirectory = ConfigurationHandler.SCHEMATIC_DIRECTORY_DEFAULT;
        ConfigurationHandler.extraAirBlocks = Arrays.copyOf(ConfigurationHandler.EXTRA_AIR_BLOCKS_DEFAULT, ConfigurationHandler.EXTRA_AIR_BLOCKS_DEFAULT.length);
        ConfigurationHandler.sortType = "";
        ConfigurationHandler.printerEnabled = true;
        ConfigurationHandler.saveEnabled = true;
        ConfigurationHandler.loadEnabled = true;
        ConfigurationHandler.playerQuotaKilobytes = 8192;
        ConfigurationHandler.propDumpBlockList = null;
        ConfigurationHandler.propShowDebugInfo = null;
        ConfigurationHandler.propEnableAlpha = null;
        ConfigurationHandler.propAlpha = null;
        ConfigurationHandler.propHighlight = null;
        ConfigurationHandler.propHighlightAir = null;
        ConfigurationHandler.propBlockDelta = null;
        ConfigurationHandler.propRenderDistance = null;
        ConfigurationHandler.propPlaceDelay = null;
        ConfigurationHandler.propTimeout = null;
        ConfigurationHandler.propPlaceDistance = null;
        ConfigurationHandler.propPlaceInstantly = null;
        ConfigurationHandler.propDestroyBlocks = null;
        ConfigurationHandler.propDestroyInstantly = null;
        ConfigurationHandler.propPlaceAdjacent = null;
        ConfigurationHandler.propSwapSlots = new Property[ConfigurationHandler.SWAP_SLOTS_DEFAULT.length];
        ConfigurationHandler.propSchematicDirectory = null;
        ConfigurationHandler.propExtraAirBlocks = null;
        ConfigurationHandler.propSortType = null;
        ConfigurationHandler.propPrinterEnabled = null;
        ConfigurationHandler.propSaveEnabled = null;
        ConfigurationHandler.propLoadEnabled = null;
        ConfigurationHandler.propPlayerQuotaKilobytes = null;
        extraAirBlockList = new HashSet<Block>();
    }
}
