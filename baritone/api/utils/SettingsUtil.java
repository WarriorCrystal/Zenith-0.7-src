// 
// Decompiled by Procyon v0.5.36
// 

package baritone.api.utils;

import java.io.BufferedReader;
import baritone.api.BaritoneAPI;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import java.io.BufferedWriter;
import baritone.api.Settings$Setting;
import java.nio.file.OpenOption;
import java.util.regex.Matcher;
import java.nio.file.NoSuchFileException;
import baritone.api.Settings;
import java.nio.file.Files;
import java.util.function.Consumer;
import java.util.regex.Pattern;
import java.nio.file.Path;

public class SettingsUtil
{
    private static final Path SETTINGS_PATH;
    private static final Pattern SETTING_PATTERN;
    
    private static boolean isComment(final String s) {
        return s.startsWith("#") || s.startsWith("//");
    }
    
    private static void forEachLine(Path bufferedReader, final Consumer<String> consumer) {
        bufferedReader = (Path)Files.newBufferedReader(bufferedReader);
        Throwable t = null;
        try {
            String line;
            while ((line = ((BufferedReader)bufferedReader).readLine()) != null) {
                if (!line.isEmpty() && !isComment(line)) {
                    consumer.accept(line);
                }
            }
            if (bufferedReader != null) {
                ((BufferedReader)bufferedReader).close();
            }
        }
        catch (Throwable t3) {
            final Throwable t2 = t3;
            t = t3;
            throw t2;
        }
        finally {
            if (bufferedReader != null) {
                if (t != null) {
                    try {
                        ((BufferedReader)bufferedReader).close();
                    }
                    catch (Throwable exception) {
                        t.addSuppressed(exception);
                    }
                }
                else {
                    ((BufferedReader)bufferedReader).close();
                }
            }
        }
    }
    
    public static void readAndApply(final Settings settings) {
        try {
            final Object o;
            final Matcher matcher;
            final String s;
            final String s2;
            forEachLine(SettingsUtil.SETTINGS_PATH, obj -> {
                SettingsUtil.SETTING_PATTERN.matcher(obj);
                if (!((Matcher)o).matches()) {
                    System.out.println("Invalid syntax in setting file: ".concat(String.valueOf(obj)));
                }
                else {
                    matcher.group("setting").toLowerCase();
                    matcher.group("value");
                    try {
                        parseAndApply(settings, s, s2);
                    }
                    catch (Exception ex) {
                        System.out.println("Unable to parse line ".concat(String.valueOf(obj)));
                        ex.printStackTrace();
                    }
                }
            });
        }
        catch (NoSuchFileException ex3) {
            System.out.println("Baritone settings file not found, resetting.");
        }
        catch (Exception ex2) {
            System.out.println("Exception while reading Baritone settings, some settings may be reset to default values!");
            ex2.printStackTrace();
        }
    }
    
    public static synchronized void save(final Settings settings) {
        try {
            final BufferedWriter bufferedWriter = Files.newBufferedWriter(SettingsUtil.SETTINGS_PATH, new OpenOption[0]);
            Throwable t = null;
            try {
                final Iterator<Settings$Setting> iterator = modifiedSettings(settings).iterator();
                while (iterator.hasNext()) {
                    bufferedWriter.write(settingToString(iterator.next()) + "\n");
                }
                if (bufferedWriter != null) {
                    bufferedWriter.close();
                }
            }
            catch (Throwable t3) {
                final Throwable t2 = t3;
                t = t3;
                throw t2;
            }
            finally {
                if (bufferedWriter != null) {
                    if (t != null) {
                        try {
                            bufferedWriter.close();
                        }
                        catch (Throwable exception) {
                            t.addSuppressed(exception);
                        }
                    }
                    else {
                        bufferedWriter.close();
                    }
                }
            }
        }
        catch (Exception ex) {
            System.out.println("Exception thrown while saving Baritone settings!");
            ex.printStackTrace();
        }
    }
    
    public static List<Settings$Setting> modifiedSettings(final Settings settings) {
        final ArrayList<Settings$Setting<?>> list = (ArrayList<Settings$Setting<?>>)new ArrayList<Settings$Setting>();
        final Iterator<Settings$Setting<?>> iterator = settings.allSettings.iterator();
        while (iterator.hasNext()) {
            final Settings$Setting<?> settings$Setting;
            if ((settings$Setting = iterator.next()).value == null) {
                System.out.println("NULL SETTING?" + settings$Setting.getName());
            }
            else {
                if (settings$Setting.getName().equals("logger") || settings$Setting.value == settings$Setting.defaultValue) {
                    continue;
                }
                list.add(settings$Setting);
            }
        }
        return (List<Settings$Setting>)list;
    }
    
    public static String settingTypeToString(final Settings$Setting settings$Setting) {
        return settings$Setting.getType().getTypeName().replaceAll("(?:\\w+\\.)+(\\w+)", "$1");
    }
    
    public static <T> String settingValueToString(final Settings$Setting<T> settings$Setting, final T t) {
        final SettingsUtil$Parser parser;
        if ((parser = SettingsUtil$Parser.getParser(settings$Setting.getType())) == null) {
            throw new IllegalStateException("Missing " + settings$Setting.getValueClass() + " " + settings$Setting.getName());
        }
        return parser.toString(new SettingsUtil$ParserContext(settings$Setting, null), t);
    }
    
    public static String settingValueToString(final Settings$Setting settings$Setting) {
        return settingValueToString(settings$Setting, (Object)settings$Setting.value);
    }
    
    public static String settingDefaultToString(final Settings$Setting settings$Setting) {
        return settingValueToString(settings$Setting, (Object)settings$Setting.defaultValue);
    }
    
    public static String maybeCensor(final int i) {
        if (BaritoneAPI.getSettings().censorCoordinates.value) {
            return "<censored>";
        }
        return Integer.toString(i);
    }
    
    public static String settingToString(final Settings$Setting settings$Setting) {
        if (settings$Setting.getName().equals("logger")) {
            return "logger";
        }
        return settings$Setting.getName() + " " + settingValueToString(settings$Setting);
    }
    
    public static void parseAndApply(final Settings settings, final String s, final String s2) {
        final Settings$Setting<?> settings$Setting;
        if ((settings$Setting = settings.byLowerName.get(s)) == null) {
            throw new IllegalStateException("No setting by that name");
        }
        final Class<?> valueClass = settings$Setting.getValueClass();
        final SettingsUtil$Parser parser;
        final Object parse = (parser = SettingsUtil$Parser.getParser(settings$Setting.getType())).parse(new SettingsUtil$ParserContext(settings$Setting, null), s2);
        if (!valueClass.isInstance(parse)) {
            throw new IllegalStateException(parser + " parser returned incorrect type, expected " + valueClass + " got " + parse + " which is " + parse.getClass());
        }
        settings$Setting.value = parse;
    }
    
    static {
        SETTINGS_PATH = bib.z().w.toPath().resolve("baritone").resolve("settings.txt");
        SETTING_PATTERN = Pattern.compile("^(?<setting>[^ ]+) +(?<value>.+)");
    }
}
