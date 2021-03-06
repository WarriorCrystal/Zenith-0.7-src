// 
// Decompiled by Procyon v0.5.36
// 

package baritone.api;

import java.util.Iterator;
import java.lang.reflect.Field;
import java.util.Collections;
import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Arrays;
import java.lang.reflect.Type;
import java.util.Map;
import java.awt.Color;
import java.util.function.Consumer;
import java.util.List;

public final class Settings
{
    public final Settings$Setting<Boolean> allowBreak;
    public final Settings$Setting<Boolean> allowSprint;
    public final Settings$Setting<Boolean> allowPlace;
    public final Settings$Setting<Boolean> allowInventory;
    public final Settings$Setting<Double> blockPlacementPenalty;
    public final Settings$Setting<Double> blockBreakAdditionalPenalty;
    public final Settings$Setting<Double> jumpPenalty;
    public final Settings$Setting<Double> walkOnWaterOnePenalty;
    public final Settings$Setting<Boolean> allowWaterBucketFall;
    public final Settings$Setting<Boolean> assumeWalkOnWater;
    public final Settings$Setting<Boolean> assumeWalkOnLava;
    public final Settings$Setting<Boolean> assumeStep;
    public final Settings$Setting<Boolean> assumeSafeWalk;
    public final Settings$Setting<Boolean> allowJumpAt256;
    public final Settings$Setting<Boolean> allowParkourAscend;
    public final Settings$Setting<Boolean> allowDiagonalDescend;
    public final Settings$Setting<Boolean> allowDiagonalAscend;
    public final Settings$Setting<Boolean> allowDownward;
    public final Settings$Setting<List<ain>> acceptableThrowawayItems;
    public final Settings$Setting<List<aow>> blocksToAvoid;
    public final Settings$Setting<List<aow>> blocksToAvoidBreaking;
    public final Settings$Setting<List<aow>> buildIgnoreBlocks;
    public final Settings$Setting<Boolean> buildIgnoreExisting;
    public final Settings$Setting<Boolean> avoidUpdatingFallingBlocks;
    public final Settings$Setting<Boolean> allowVines;
    public final Settings$Setting<Boolean> allowWalkOnBottomSlab;
    public final Settings$Setting<Boolean> allowParkour;
    public final Settings$Setting<Boolean> allowParkourPlace;
    public final Settings$Setting<Boolean> considerPotionEffects;
    public final Settings$Setting<Boolean> sprintAscends;
    public final Settings$Setting<Boolean> overshootTraverse;
    public final Settings$Setting<Boolean> pauseMiningForFallingBlocks;
    public final Settings$Setting<Integer> rightClickSpeed;
    public final Settings$Setting<Float> blockReachDistance;
    public final Settings$Setting<Double> randomLooking;
    public final Settings$Setting<Double> costHeuristic;
    public final Settings$Setting<Integer> pathingMaxChunkBorderFetch;
    public final Settings$Setting<Double> backtrackCostFavoringCoefficient;
    public final Settings$Setting<Boolean> avoidance;
    public final Settings$Setting<Double> mobSpawnerAvoidanceCoefficient;
    public final Settings$Setting<Integer> mobSpawnerAvoidanceRadius;
    public final Settings$Setting<Double> mobAvoidanceCoefficient;
    public final Settings$Setting<Integer> mobAvoidanceRadius;
    public final Settings$Setting<Boolean> rightClickContainerOnArrival;
    public final Settings$Setting<Boolean> enterPortal;
    public final Settings$Setting<Boolean> minimumImprovementRepropagation;
    public final Settings$Setting<Boolean> cutoffAtLoadBoundary;
    public final Settings$Setting<Double> maxCostIncrease;
    public final Settings$Setting<Integer> costVerificationLookahead;
    public final Settings$Setting<Double> pathCutoffFactor;
    public final Settings$Setting<Integer> pathCutoffMinimumLength;
    public final Settings$Setting<Integer> planningTickLookahead;
    public final Settings$Setting<Integer> pathingMapDefaultSize;
    public final Settings$Setting<Float> pathingMapLoadFactor;
    public final Settings$Setting<Integer> maxFallHeightNoWater;
    public final Settings$Setting<Integer> maxFallHeightBucket;
    public final Settings$Setting<Boolean> allowOvershootDiagonalDescend;
    public final Settings$Setting<Boolean> simplifyUnloadedYCoord;
    public final Settings$Setting<Integer> movementTimeoutTicks;
    public final Settings$Setting<Long> primaryTimeoutMS;
    public final Settings$Setting<Long> failureTimeoutMS;
    public final Settings$Setting<Long> planAheadPrimaryTimeoutMS;
    public final Settings$Setting<Long> planAheadFailureTimeoutMS;
    public final Settings$Setting<Boolean> slowPath;
    public final Settings$Setting<Long> slowPathTimeDelayMS;
    public final Settings$Setting<Long> slowPathTimeoutMS;
    public final Settings$Setting<Boolean> chunkCaching;
    public final Settings$Setting<Boolean> pruneRegionsFromRAM;
    public final Settings$Setting<Boolean> containerMemory;
    public final Settings$Setting<Boolean> backfill;
    public final Settings$Setting<Boolean> chatDebug;
    public final Settings$Setting<Boolean> chatControl;
    public final Settings$Setting<Boolean> chatControlAnyway;
    public final Settings$Setting<Boolean> renderPath;
    public final Settings$Setting<Boolean> renderPathAsLine;
    public final Settings$Setting<Boolean> renderGoal;
    public final Settings$Setting<Boolean> renderSelectionBoxes;
    public final Settings$Setting<Boolean> renderGoalIgnoreDepth;
    public final Settings$Setting<Boolean> renderGoalXZBeacon;
    public final Settings$Setting<Boolean> renderSelectionBoxesIgnoreDepth;
    public final Settings$Setting<Boolean> renderPathIgnoreDepth;
    public final Settings$Setting<Float> pathRenderLineWidthPixels;
    public final Settings$Setting<Float> goalRenderLineWidthPixels;
    public final Settings$Setting<Boolean> fadePath;
    public final Settings$Setting<Boolean> freeLook;
    public final Settings$Setting<Boolean> antiCheatCompatibility;
    public final Settings$Setting<Boolean> pathThroughCachedOnly;
    public final Settings$Setting<Boolean> sprintInWater;
    public final Settings$Setting<Boolean> blacklistClosestOnFailure;
    public final Settings$Setting<Boolean> renderCachedChunks;
    public final Settings$Setting<Float> cachedChunksOpacity;
    public final Settings$Setting<Boolean> prefixControl;
    public final Settings$Setting<String> prefix;
    public final Settings$Setting<Boolean> shortBaritonePrefix;
    public final Settings$Setting<Boolean> echoCommands;
    public final Settings$Setting<Boolean> censorCoordinates;
    public final Settings$Setting<Boolean> censorRanCommands;
    public final Settings$Setting<Boolean> preferSilkTouch;
    public final Settings$Setting<Boolean> walkWhileBreaking;
    public final Settings$Setting<Boolean> splicePath;
    public final Settings$Setting<Integer> maxPathHistoryLength;
    public final Settings$Setting<Integer> pathHistoryCutoffAmount;
    public final Settings$Setting<Integer> mineGoalUpdateInterval;
    public final Settings$Setting<Integer> maxCachedWorldScanCount;
    public final Settings$Setting<Boolean> exploreForBlocks;
    public final Settings$Setting<Integer> worldExploringChunkOffset;
    public final Settings$Setting<Integer> exploreChunkSetMinimumSize;
    public final Settings$Setting<Integer> exploreMaintainY;
    public final Settings$Setting<Boolean> replantCrops;
    public final Settings$Setting<Boolean> replantNetherWart;
    public final Settings$Setting<Boolean> extendCacheOnThreshold;
    public final Settings$Setting<Boolean> buildInLayers;
    public final Settings$Setting<Boolean> layerOrder;
    public final Settings$Setting<fq> buildRepeat;
    public final Settings$Setting<Integer> buildRepeatCount;
    public final Settings$Setting<Boolean> breakFromAbove;
    public final Settings$Setting<Boolean> goalBreakFromAbove;
    public final Settings$Setting<Boolean> mapArtMode;
    public final Settings$Setting<Boolean> okIfWater;
    public final Settings$Setting<Integer> incorrectSize;
    public final Settings$Setting<Double> breakCorrectBlockPenaltyMultiplier;
    public final Settings$Setting<Boolean> schematicOrientationX;
    public final Settings$Setting<Boolean> schematicOrientationY;
    public final Settings$Setting<Boolean> schematicOrientationZ;
    public final Settings$Setting<String> schematicFallbackExtension;
    public final Settings$Setting<Integer> builderTickScanRadius;
    public final Settings$Setting<Boolean> mineScanDroppedItems;
    public final Settings$Setting<Long> mineDropLoiterDurationMSThanksLouca;
    public final Settings$Setting<Boolean> distanceTrim;
    public final Settings$Setting<Boolean> cancelOnGoalInvalidation;
    public final Settings$Setting<Integer> axisHeight;
    public final Settings$Setting<Boolean> disconnectOnArrival;
    public final Settings$Setting<Boolean> legitMine;
    public final Settings$Setting<Integer> legitMineYLevel;
    public final Settings$Setting<Boolean> legitMineIncludeDiagonals;
    public final Settings$Setting<Boolean> forceInternalMining;
    public final Settings$Setting<Boolean> internalMiningAirException;
    public final Settings$Setting<Double> followOffsetDistance;
    public final Settings$Setting<Float> followOffsetDirection;
    public final Settings$Setting<Integer> followRadius;
    public final Settings$Setting<Boolean> disableCompletionCheck;
    public final Settings$Setting<Long> cachedChunksExpirySeconds;
    public final Settings$Setting<Consumer<hh>> logger;
    public final Settings$Setting<Double> yLevelBoxSize;
    public final Settings$Setting<Color> colorCurrentPath;
    public final Settings$Setting<Color> colorNextPath;
    public final Settings$Setting<Color> colorBlocksToBreak;
    public final Settings$Setting<Color> colorBlocksToPlace;
    public final Settings$Setting<Color> colorBlocksToWalkInto;
    public final Settings$Setting<Color> colorBestPathSoFar;
    public final Settings$Setting<Color> colorMostRecentConsidered;
    public final Settings$Setting<Color> colorGoalBox;
    public final Settings$Setting<Color> colorInvertedGoalBox;
    public final Settings$Setting<Color> colorSelection;
    public final Settings$Setting<Color> colorSelectionPos1;
    public final Settings$Setting<Color> colorSelectionPos2;
    public final Settings$Setting<Float> selectionOpacity;
    public final Settings$Setting<Float> selectionLineWidth;
    public final Settings$Setting<Boolean> renderSelection;
    public final Settings$Setting<Boolean> renderSelectionIgnoreDepth;
    public final Settings$Setting<Boolean> renderSelectionCorners;
    public final Settings$Setting<Boolean> desktopNotifications;
    public final Map<String, Settings$Setting<?>> byLowerName;
    public final List<Settings$Setting<?>> allSettings;
    public final Map<Settings$Setting<?>, Type> settingTypes;
    
    Settings() {
        this.allowBreak = new Settings$Setting<Boolean>(this, Boolean.TRUE, null);
        this.allowSprint = new Settings$Setting<Boolean>(this, Boolean.TRUE, null);
        this.allowPlace = new Settings$Setting<Boolean>(this, Boolean.TRUE, null);
        this.allowInventory = new Settings$Setting<Boolean>(this, Boolean.FALSE, null);
        this.blockPlacementPenalty = new Settings$Setting<Double>(this, 20.0, null);
        this.blockBreakAdditionalPenalty = new Settings$Setting<Double>(this, 2.0, null);
        this.jumpPenalty = new Settings$Setting<Double>(this, 2.0, null);
        this.walkOnWaterOnePenalty = new Settings$Setting<Double>(this, 3.0, null);
        this.allowWaterBucketFall = new Settings$Setting<Boolean>(this, Boolean.TRUE, null);
        this.assumeWalkOnWater = new Settings$Setting<Boolean>(this, Boolean.FALSE, null);
        this.assumeWalkOnLava = new Settings$Setting<Boolean>(this, Boolean.FALSE, null);
        this.assumeStep = new Settings$Setting<Boolean>(this, Boolean.FALSE, null);
        this.assumeSafeWalk = new Settings$Setting<Boolean>(this, Boolean.FALSE, null);
        this.allowJumpAt256 = new Settings$Setting<Boolean>(this, Boolean.FALSE, null);
        this.allowParkourAscend = new Settings$Setting<Boolean>(this, Boolean.TRUE, null);
        this.allowDiagonalDescend = new Settings$Setting<Boolean>(this, Boolean.FALSE, null);
        this.allowDiagonalAscend = new Settings$Setting<Boolean>(this, Boolean.FALSE, null);
        this.allowDownward = new Settings$Setting<Boolean>(this, Boolean.TRUE, null);
        this.acceptableThrowawayItems = new Settings$Setting<List<ain>>(this, new ArrayList(Arrays.asList(ain.a(aox.d), ain.a(aox.e), ain.a(aox.aV), ain.a(aox.b))), null);
        this.blocksToAvoid = new Settings$Setting<List<aow>>(this, new ArrayList(), null);
        this.blocksToAvoidBreaking = new Settings$Setting<List<aow>>(this, new ArrayList(Arrays.asList(aox.ai, aox.al, aox.am, (aow)aox.ae, aox.cg, aox.an, aox.ax)), null);
        this.buildIgnoreBlocks = new Settings$Setting<List<aow>>(this, new ArrayList(Arrays.asList(new aow[0])), null);
        this.buildIgnoreExisting = new Settings$Setting<Boolean>(this, Boolean.FALSE, null);
        this.avoidUpdatingFallingBlocks = new Settings$Setting<Boolean>(this, Boolean.TRUE, null);
        this.allowVines = new Settings$Setting<Boolean>(this, Boolean.FALSE, null);
        this.allowWalkOnBottomSlab = new Settings$Setting<Boolean>(this, Boolean.TRUE, null);
        this.allowParkour = new Settings$Setting<Boolean>(this, Boolean.FALSE, null);
        this.allowParkourPlace = new Settings$Setting<Boolean>(this, Boolean.FALSE, null);
        this.considerPotionEffects = new Settings$Setting<Boolean>(this, Boolean.TRUE, null);
        this.sprintAscends = new Settings$Setting<Boolean>(this, Boolean.TRUE, null);
        this.overshootTraverse = new Settings$Setting<Boolean>(this, Boolean.TRUE, null);
        this.pauseMiningForFallingBlocks = new Settings$Setting<Boolean>(this, Boolean.TRUE, null);
        this.rightClickSpeed = new Settings$Setting<Integer>(this, 4, null);
        this.blockReachDistance = new Settings$Setting<Float>(this, 4.5f, null);
        this.randomLooking = new Settings$Setting<Double>(this, 0.01, null);
        this.costHeuristic = new Settings$Setting<Double>(this, 3.563, null);
        this.pathingMaxChunkBorderFetch = new Settings$Setting<Integer>(this, 50, null);
        this.backtrackCostFavoringCoefficient = new Settings$Setting<Double>(this, 0.5, null);
        this.avoidance = new Settings$Setting<Boolean>(this, Boolean.FALSE, null);
        this.mobSpawnerAvoidanceCoefficient = new Settings$Setting<Double>(this, 2.0, null);
        this.mobSpawnerAvoidanceRadius = new Settings$Setting<Integer>(this, 16, null);
        this.mobAvoidanceCoefficient = new Settings$Setting<Double>(this, 1.5, null);
        this.mobAvoidanceRadius = new Settings$Setting<Integer>(this, 8, null);
        this.rightClickContainerOnArrival = new Settings$Setting<Boolean>(this, Boolean.TRUE, null);
        this.enterPortal = new Settings$Setting<Boolean>(this, Boolean.TRUE, null);
        this.minimumImprovementRepropagation = new Settings$Setting<Boolean>(this, Boolean.TRUE, null);
        this.cutoffAtLoadBoundary = new Settings$Setting<Boolean>(this, Boolean.FALSE, null);
        this.maxCostIncrease = new Settings$Setting<Double>(this, 10.0, null);
        this.costVerificationLookahead = new Settings$Setting<Integer>(this, 5, null);
        this.pathCutoffFactor = new Settings$Setting<Double>(this, 0.9, null);
        this.pathCutoffMinimumLength = new Settings$Setting<Integer>(this, 30, null);
        this.planningTickLookahead = new Settings$Setting<Integer>(this, 150, null);
        this.pathingMapDefaultSize = new Settings$Setting<Integer>(this, 1024, null);
        this.pathingMapLoadFactor = new Settings$Setting<Float>(this, 0.75f, null);
        this.maxFallHeightNoWater = new Settings$Setting<Integer>(this, 3, null);
        this.maxFallHeightBucket = new Settings$Setting<Integer>(this, 20, null);
        this.allowOvershootDiagonalDescend = new Settings$Setting<Boolean>(this, Boolean.TRUE, null);
        this.simplifyUnloadedYCoord = new Settings$Setting<Boolean>(this, Boolean.TRUE, null);
        this.movementTimeoutTicks = new Settings$Setting<Integer>(this, 100, null);
        this.primaryTimeoutMS = new Settings$Setting<Long>(this, 500L, null);
        this.failureTimeoutMS = new Settings$Setting<Long>(this, 2000L, null);
        this.planAheadPrimaryTimeoutMS = new Settings$Setting<Long>(this, 4000L, null);
        this.planAheadFailureTimeoutMS = new Settings$Setting<Long>(this, 5000L, null);
        this.slowPath = new Settings$Setting<Boolean>(this, Boolean.FALSE, null);
        this.slowPathTimeDelayMS = new Settings$Setting<Long>(this, 100L, null);
        this.slowPathTimeoutMS = new Settings$Setting<Long>(this, 40000L, null);
        this.chunkCaching = new Settings$Setting<Boolean>(this, Boolean.TRUE, null);
        this.pruneRegionsFromRAM = new Settings$Setting<Boolean>(this, Boolean.TRUE, null);
        this.containerMemory = new Settings$Setting<Boolean>(this, Boolean.FALSE, null);
        this.backfill = new Settings$Setting<Boolean>(this, Boolean.FALSE, null);
        this.chatDebug = new Settings$Setting<Boolean>(this, Boolean.FALSE, null);
        this.chatControl = new Settings$Setting<Boolean>(this, Boolean.TRUE, null);
        this.chatControlAnyway = new Settings$Setting<Boolean>(this, Boolean.FALSE, null);
        this.renderPath = new Settings$Setting<Boolean>(this, Boolean.TRUE, null);
        this.renderPathAsLine = new Settings$Setting<Boolean>(this, Boolean.FALSE, null);
        this.renderGoal = new Settings$Setting<Boolean>(this, Boolean.TRUE, null);
        this.renderSelectionBoxes = new Settings$Setting<Boolean>(this, Boolean.TRUE, null);
        this.renderGoalIgnoreDepth = new Settings$Setting<Boolean>(this, Boolean.TRUE, null);
        this.renderGoalXZBeacon = new Settings$Setting<Boolean>(this, Boolean.FALSE, null);
        this.renderSelectionBoxesIgnoreDepth = new Settings$Setting<Boolean>(this, Boolean.TRUE, null);
        this.renderPathIgnoreDepth = new Settings$Setting<Boolean>(this, Boolean.TRUE, null);
        this.pathRenderLineWidthPixels = new Settings$Setting<Float>(this, 5.0f, null);
        this.goalRenderLineWidthPixels = new Settings$Setting<Float>(this, 3.0f, null);
        this.fadePath = new Settings$Setting<Boolean>(this, Boolean.FALSE, null);
        this.freeLook = new Settings$Setting<Boolean>(this, Boolean.TRUE, null);
        this.antiCheatCompatibility = new Settings$Setting<Boolean>(this, Boolean.TRUE, null);
        this.pathThroughCachedOnly = new Settings$Setting<Boolean>(this, Boolean.FALSE, null);
        this.sprintInWater = new Settings$Setting<Boolean>(this, Boolean.TRUE, null);
        this.blacklistClosestOnFailure = new Settings$Setting<Boolean>(this, Boolean.TRUE, null);
        this.renderCachedChunks = new Settings$Setting<Boolean>(this, Boolean.FALSE, null);
        this.cachedChunksOpacity = new Settings$Setting<Float>(this, 0.5f, null);
        this.prefixControl = new Settings$Setting<Boolean>(this, Boolean.TRUE, null);
        this.prefix = new Settings$Setting<String>(this, "#", null);
        this.shortBaritonePrefix = new Settings$Setting<Boolean>(this, Boolean.FALSE, null);
        this.echoCommands = new Settings$Setting<Boolean>(this, Boolean.TRUE, null);
        this.censorCoordinates = new Settings$Setting<Boolean>(this, Boolean.FALSE, null);
        this.censorRanCommands = new Settings$Setting<Boolean>(this, Boolean.FALSE, null);
        this.preferSilkTouch = new Settings$Setting<Boolean>(this, Boolean.FALSE, null);
        this.walkWhileBreaking = new Settings$Setting<Boolean>(this, Boolean.TRUE, null);
        this.splicePath = new Settings$Setting<Boolean>(this, Boolean.TRUE, null);
        this.maxPathHistoryLength = new Settings$Setting<Integer>(this, 300, null);
        this.pathHistoryCutoffAmount = new Settings$Setting<Integer>(this, 50, null);
        this.mineGoalUpdateInterval = new Settings$Setting<Integer>(this, 5, null);
        this.maxCachedWorldScanCount = new Settings$Setting<Integer>(this, 10, null);
        this.exploreForBlocks = new Settings$Setting<Boolean>(this, Boolean.TRUE, null);
        this.worldExploringChunkOffset = new Settings$Setting<Integer>(this, 0, null);
        this.exploreChunkSetMinimumSize = new Settings$Setting<Integer>(this, 10, null);
        this.exploreMaintainY = new Settings$Setting<Integer>(this, 64, null);
        this.replantCrops = new Settings$Setting<Boolean>(this, Boolean.TRUE, null);
        this.replantNetherWart = new Settings$Setting<Boolean>(this, Boolean.FALSE, null);
        this.extendCacheOnThreshold = new Settings$Setting<Boolean>(this, Boolean.FALSE, null);
        this.buildInLayers = new Settings$Setting<Boolean>(this, Boolean.FALSE, null);
        this.layerOrder = new Settings$Setting<Boolean>(this, Boolean.FALSE, null);
        this.buildRepeat = new Settings$Setting<fq>(this, new fq(0, 0, 0), null);
        this.buildRepeatCount = new Settings$Setting<Integer>(this, -1, null);
        this.breakFromAbove = new Settings$Setting<Boolean>(this, Boolean.FALSE, null);
        this.goalBreakFromAbove = new Settings$Setting<Boolean>(this, Boolean.FALSE, null);
        this.mapArtMode = new Settings$Setting<Boolean>(this, Boolean.FALSE, null);
        this.okIfWater = new Settings$Setting<Boolean>(this, Boolean.FALSE, null);
        this.incorrectSize = new Settings$Setting<Integer>(this, 100, null);
        this.breakCorrectBlockPenaltyMultiplier = new Settings$Setting<Double>(this, 10.0, null);
        this.schematicOrientationX = new Settings$Setting<Boolean>(this, Boolean.FALSE, null);
        this.schematicOrientationY = new Settings$Setting<Boolean>(this, Boolean.FALSE, null);
        this.schematicOrientationZ = new Settings$Setting<Boolean>(this, Boolean.FALSE, null);
        this.schematicFallbackExtension = new Settings$Setting<String>(this, "schematic", null);
        this.builderTickScanRadius = new Settings$Setting<Integer>(this, 5, null);
        this.mineScanDroppedItems = new Settings$Setting<Boolean>(this, Boolean.TRUE, null);
        this.mineDropLoiterDurationMSThanksLouca = new Settings$Setting<Long>(this, 250L, null);
        this.distanceTrim = new Settings$Setting<Boolean>(this, Boolean.TRUE, null);
        this.cancelOnGoalInvalidation = new Settings$Setting<Boolean>(this, Boolean.TRUE, null);
        this.axisHeight = new Settings$Setting<Integer>(this, 120, null);
        this.disconnectOnArrival = new Settings$Setting<Boolean>(this, Boolean.FALSE, null);
        this.legitMine = new Settings$Setting<Boolean>(this, Boolean.FALSE, null);
        this.legitMineYLevel = new Settings$Setting<Integer>(this, 11, null);
        this.legitMineIncludeDiagonals = new Settings$Setting<Boolean>(this, Boolean.FALSE, null);
        this.forceInternalMining = new Settings$Setting<Boolean>(this, Boolean.TRUE, null);
        this.internalMiningAirException = new Settings$Setting<Boolean>(this, Boolean.TRUE, null);
        this.followOffsetDistance = new Settings$Setting<Double>(this, 0.0, null);
        this.followOffsetDirection = new Settings$Setting<Float>(this, 0.0f, null);
        this.followRadius = new Settings$Setting<Integer>(this, 3, null);
        this.disableCompletionCheck = new Settings$Setting<Boolean>(this, Boolean.FALSE, null);
        this.cachedChunksExpirySeconds = new Settings$Setting<Long>(this, -1L, null);
        this.logger = new Settings$Setting<Consumer<hh>>(this, bib.z().q.d()::a, null);
        this.yLevelBoxSize = new Settings$Setting<Double>(this, 15.0, null);
        this.colorCurrentPath = new Settings$Setting<Color>(this, Color.RED, null);
        this.colorNextPath = new Settings$Setting<Color>(this, Color.MAGENTA, null);
        this.colorBlocksToBreak = new Settings$Setting<Color>(this, Color.RED, null);
        this.colorBlocksToPlace = new Settings$Setting<Color>(this, Color.GREEN, null);
        this.colorBlocksToWalkInto = new Settings$Setting<Color>(this, Color.MAGENTA, null);
        this.colorBestPathSoFar = new Settings$Setting<Color>(this, Color.BLUE, null);
        this.colorMostRecentConsidered = new Settings$Setting<Color>(this, Color.CYAN, null);
        this.colorGoalBox = new Settings$Setting<Color>(this, Color.GREEN, null);
        this.colorInvertedGoalBox = new Settings$Setting<Color>(this, Color.RED, null);
        this.colorSelection = new Settings$Setting<Color>(this, Color.CYAN, null);
        this.colorSelectionPos1 = new Settings$Setting<Color>(this, Color.BLACK, null);
        this.colorSelectionPos2 = new Settings$Setting<Color>(this, Color.ORANGE, null);
        this.selectionOpacity = new Settings$Setting<Float>(this, 0.5f, null);
        this.selectionLineWidth = new Settings$Setting<Float>(this, 2.0f, null);
        this.renderSelection = new Settings$Setting<Boolean>(this, Boolean.TRUE, null);
        this.renderSelectionIgnoreDepth = new Settings$Setting<Boolean>(this, Boolean.TRUE, null);
        this.renderSelectionCorners = new Settings$Setting<Boolean>(this, Boolean.TRUE, null);
        this.desktopNotifications = new Settings$Setting<Boolean>(this, Boolean.FALSE, null);
        final Field[] fields = this.getClass().getFields();
        final HashMap<String, Settings$Setting<?>> m = new HashMap<String, Settings$Setting<?>>();
        final ArrayList<Settings$Setting<?>> list = new ArrayList<Settings$Setting<?>>();
        final HashMap<Settings$Setting<?>, Type> i = new HashMap<Settings$Setting<?>, Type>();
        try {
            for (int length = fields.length, j = 0; j < length; ++j) {
                final Field field;
                if ((field = fields[j]).getType().equals(Settings$Setting.class)) {
                    final Settings$Setting settings$Setting = (Settings$Setting)field.get(this);
                    final String name = field.getName();
                    settings$Setting.name = name;
                    final String lowerCase = name.toLowerCase();
                    if (m.containsKey(lowerCase)) {
                        throw new IllegalStateException("Duplicate setting name");
                    }
                    m.put(lowerCase, settings$Setting);
                    list.add(settings$Setting);
                    i.put(settings$Setting, ((ParameterizedType)field.getGenericType()).getActualTypeArguments()[0]);
                }
            }
        }
        catch (IllegalAccessException cause) {
            throw new IllegalStateException(cause);
        }
        this.byLowerName = (Map<String, Settings$Setting<?>>)Collections.unmodifiableMap((Map<?, ?>)m);
        this.allSettings = (List<Settings$Setting<?>>)Collections.unmodifiableList((List<?>)list);
        this.settingTypes = (Map<Settings$Setting<?>, Type>)Collections.unmodifiableMap((Map<?, ?>)i);
    }
    
    public final <T> List<Settings$Setting<T>> getAllValuesByType(final Class<T> obj) {
        final ArrayList<Settings$Setting<?>> list = (ArrayList<Settings$Setting<?>>)new ArrayList<Settings$Setting<T>>();
        final Iterator<Settings$Setting<?>> iterator = this.allSettings.iterator();
        while (iterator.hasNext()) {
            final Settings$Setting<?> settings$Setting;
            if ((settings$Setting = iterator.next()).getValueClass().equals(obj)) {
                list.add(settings$Setting);
            }
        }
        return (List<Settings$Setting<T>>)list;
    }
}
