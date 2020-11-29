// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.util;

import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.net.URLClassLoader;
import me.gopro336.goprohack.main.GoproHack;
import java.net.URL;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public final class ReflectionUtil
{
    public static List<Class<?>> getClassesEx(final String path) {
        final List<Class<?>> classes = new ArrayList<Class<?>>();
        try {
            final File dir = new File(path);
            for (final File file : dir.listFiles()) {
                if (file.getName().endsWith(".jar") || file.getName().endsWith(".zip")) {
                    final ClassLoader classLoader = URLClassLoader.newInstance(new URL[] { file.toURI().toURL() }, GoproHack.class.getClassLoader());
                    final ZipFile zip = new ZipFile(file);
                    final Enumeration list = zip.entries();
                    while (list.hasMoreElements()) {
                        final ZipEntry entry = list.nextElement();
                        if (entry.getName().contains(".class") && !entry.getName().contains(".classpath")) {
                            classes.add(classLoader.loadClass(entry.getName().substring(0, entry.getName().length() - 6).replace('/', '.')));
                        }
                    }
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return classes;
    }
}
