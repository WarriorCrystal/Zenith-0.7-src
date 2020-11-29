// 
// Decompiled by Procyon v0.5.36
// 

package com.github.lunatrius.core.version;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.ArrayList;
import net.minecraftforge.common.ForgeModContainer;
import java.util.Map;
import net.minecraftforge.common.ForgeVersion;
import net.minecraftforge.fml.common.versioning.ComparableVersion;
import java.util.Iterator;
import java.io.InputStream;
import java.net.URLConnection;
import com.google.gson.Gson;
import com.google.common.io.ByteStreams;
import java.net.URL;
import com.github.lunatrius.core.reference.Reference;
import net.minecraftforge.fml.common.ModMetadata;
import com.google.common.base.Joiner;
import net.minecraftforge.fml.common.ModContainer;
import java.util.List;

public class VersionChecker
{
    public static final String VER_CHECK_API_URL = "http://mc.lunatri.us/json?v=%d&mc=%s&limit=5";
    public static final int VER_CHECK_API_VER = 2;
    public static final String UPDATE_URL = "https://mods.io/mods?author=Lunatrius";
    private static final List<ModContainer> REGISTERED_MODS;
    private static final Joiner NEWLINE_JOINER;
    
    public static void registerMod(final ModContainer container, final String forgeVersion) {
        VersionChecker.REGISTERED_MODS.add(container);
        final ModMetadata metadata = container.getMetadata();
        if (metadata.description != null) {
            final StringBuilder sb = new StringBuilder();
            final ModMetadata modMetadata = metadata;
            modMetadata.description = sb.append(modMetadata.description).append("\n---\nCompiled against Forge ").append(forgeVersion).toString();
        }
    }
    
    public static void startVersionCheck() {
        new Thread("LunatriusCore Version Check") {
            @Override
            public void run() {
                try {
                    if ("null".equals("1.12.2")) {
                        Reference.logger.error("Minecraft version is null! This is a bug!");
                        return;
                    }
                    final URL url = new URL(String.format("http://mc.lunatri.us/json?v=%d&mc=%s&limit=5", 2, "1.12.2"));
                    final URLConnection connection = url.openConnection();
                    connection.addRequestProperty("User-Agent", "lunatriuscore/1.2.0.42");
                    final InputStream inputStream = connection.getInputStream();
                    final String data = new String(ByteStreams.toByteArray(inputStream));
                    inputStream.close();
                    this.process((VersionData)new Gson().fromJson(data, (Class)VersionData.class));
                }
                catch (Throwable t) {
                    Reference.logger.error("Something went wrong!", t);
                }
            }
            
            private void process(final VersionData versionData) {
                if (versionData.version != 2) {
                    return;
                }
                if (versionData.mods == null) {
                    return;
                }
                for (final ModContainer container : VersionChecker.REGISTERED_MODS) {
                    final String modid = container.getModId();
                    if (!VersionChecker.isAllowedToCheck(modid)) {
                        Reference.logger.info("Skipped version check for {}", (Object)modid);
                    }
                    else {
                        final ModData modData = versionData.mods.get(modid);
                        if (modData == null) {
                            continue;
                        }
                        if (modData.latest == null) {
                            continue;
                        }
                        this.processMod(container, modData);
                    }
                }
            }
            
            private void processMod(final ModContainer container, final ModData modData) {
                final BuildData latestBuild = modData.latest;
                final ComparableVersion versionRemote = latestBuild.getVersion();
                final String version = container.getVersion();
                final ComparableVersion versionLocal = new ComparableVersion(version);
                final ForgeVersion.Status status = ForgeVersionCheck.getStatus(versionRemote, versionLocal);
                final ComparableVersion target = latestBuild.getVersion();
                final Map<ComparableVersion, String> changes = modData.getAllChanges();
                final String url = "https://mods.io/mods?author=Lunatrius";
                ForgeVersionCheck.notify(container, status, target, changes, "https://mods.io/mods?author=Lunatrius");
            }
        }.start();
    }
    
    public static boolean isAllowedToCheck(final String scope) {
        return ForgeModContainer.getConfig().get("version_checking", scope, true).getBoolean();
    }
    
    static {
        REGISTERED_MODS = new ArrayList<ModContainer>();
        NEWLINE_JOINER = Joiner.on('\n');
    }
    
    public static class VersionData
    {
        public int version;
        public Map<String, ModData> mods;
    }
    
    public static class ModData
    {
        public BuildData latest;
        public List<BuildData> builds;
        
        public Map<ComparableVersion, String> getAllChanges() {
            final LinkedHashMap<ComparableVersion, String> changes = new LinkedHashMap<ComparableVersion, String>();
            if (this.builds != null) {
                Collections.sort(this.builds, new Comparator<BuildData>() {
                    @Override
                    public int compare(final BuildData a, final BuildData b) {
                        return b.getVersion().compareTo(a.getVersion());
                    }
                });
                for (final BuildData build : this.builds) {
                    changes.put(build.getVersion(), build.getChanges());
                }
                return changes;
            }
            if (this.latest != null) {
                changes.put(this.latest.getVersion(), this.latest.getChanges());
                return changes;
            }
            return changes;
        }
    }
    
    public static class BuildData
    {
        public String mc;
        public String version;
        public int build;
        public List<String> changes;
        
        public ComparableVersion getVersion() {
            return new ComparableVersion(this.version);
        }
        
        public String getChanges() {
            if (this.changes == null) {
                return "";
            }
            return VersionChecker.NEWLINE_JOINER.join((Iterable)this.changes);
        }
    }
}
