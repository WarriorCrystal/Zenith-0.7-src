// 
// Decompiled by Procyon v0.5.36
// 

package com.github.lunatrius.core.version;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import com.github.lunatrius.core.reference.Reference;
import java.util.Map;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.common.ForgeVersion;
import net.minecraftforge.fml.common.versioning.ComparableVersion;

public class ForgeVersionCheck
{
    public static ForgeVersion.Status getStatus(final ComparableVersion versionRemote, final ComparableVersion versionLocal) {
        final int diff = versionRemote.compareTo(versionLocal);
        if (diff == 0) {
            return ForgeVersion.Status.UP_TO_DATE;
        }
        if (diff > 0) {
            return ForgeVersion.Status.OUTDATED;
        }
        return ForgeVersion.Status.AHEAD;
    }
    
    public static void notify(final ModContainer container, final ForgeVersion.Status status, final ComparableVersion target, final Map<ComparableVersion, String> changes, final String url) {
        try {
            final Map<ModContainer, ForgeVersion.CheckResult> versionMap = getVersionMap();
            final ForgeVersion.CheckResult checkResult = getCheckResult(status, target, changes, url);
            if (versionMap != null && checkResult != null) {
                versionMap.put(container, checkResult);
            }
        }
        catch (Throwable t) {
            Reference.logger.error("Failed to notify Forge!", t);
        }
    }
    
    private static Map<ModContainer, ForgeVersion.CheckResult> getVersionMap() throws ReflectiveOperationException {
        try {
            final Field field = ForgeVersion.class.getDeclaredField("results");
            field.setAccessible(true);
            return (Map<ModContainer, ForgeVersion.CheckResult>)field.get(null);
        }
        catch (Throwable t) {
            Reference.logger.error("Failed to get the version map!", t);
            return null;
        }
    }
    
    private static ForgeVersion.CheckResult getCheckResult(final ForgeVersion.Status status, final ComparableVersion target, final Map<ComparableVersion, String> changes, final String url) throws ReflectiveOperationException {
        try {
            final Constructor<?> constructor = ForgeVersion.CheckResult.class.getDeclaredConstructor(ForgeVersion.Status.class, ComparableVersion.class, Map.class, String.class);
            constructor.setAccessible(true);
            return (ForgeVersion.CheckResult)constructor.newInstance(status, target, changes, url);
        }
        catch (Throwable t) {
            Reference.logger.error("Failed to construct the CheckResult object!", t);
            return null;
        }
    }
}
