// 
// Decompiled by Procyon v0.5.36
// 

package baritone.api.command.helpers;

import baritone.api.command.ICommand;
import baritone.api.utils.SettingsUtil;
import baritone.api.Settings$Setting;
import baritone.api.BaritoneAPI;
import baritone.api.command.manager.ICommandManager;
import java.util.Locale;
import java.util.Comparator;
import java.util.function.Predicate;
import java.util.function.Function;
import java.util.List;
import java.util.stream.Stream;

public class TabCompleteHelper
{
    private Stream<String> stream;
    
    public TabCompleteHelper(final String[] values) {
        this.stream = Stream.of(values);
    }
    
    public TabCompleteHelper(final List<String> list) {
        this.stream = list.stream();
    }
    
    public TabCompleteHelper() {
        this.stream = Stream.empty();
    }
    
    public TabCompleteHelper append(final Stream<String> b) {
        this.stream = Stream.concat((Stream<? extends String>)this.stream, (Stream<? extends String>)b);
        return this;
    }
    
    public TabCompleteHelper append(final String... values) {
        return this.append(Stream.of(values));
    }
    
    public TabCompleteHelper append(final Class<? extends Enum<?>> clazz) {
        return this.append(Stream.of(clazz.getEnumConstants()).map((Function<? super Enum<?>, ?>)Enum::name).map((Function<? super Object, ? extends String>)String::toLowerCase));
    }
    
    public TabCompleteHelper prepend(final Stream<String> a) {
        this.stream = Stream.concat((Stream<? extends String>)a, (Stream<? extends String>)this.stream);
        return this;
    }
    
    public TabCompleteHelper prepend(final String... values) {
        return this.prepend(Stream.of(values));
    }
    
    public TabCompleteHelper prepend(final Class<? extends Enum<?>> clazz) {
        return this.prepend(Stream.of(clazz.getEnumConstants()).map((Function<? super Enum<?>, ?>)Enum::name).map((Function<? super Object, ? extends String>)String::toLowerCase));
    }
    
    public TabCompleteHelper map(final Function<String, String> function) {
        this.stream = this.stream.map((Function<? super String, ? extends String>)function);
        return this;
    }
    
    public TabCompleteHelper filter(final Predicate<String> predicate) {
        this.stream = this.stream.filter(predicate);
        return this;
    }
    
    public TabCompleteHelper sort(final Comparator<String> comparator) {
        this.stream = this.stream.sorted(comparator);
        return this;
    }
    
    public TabCompleteHelper sortAlphabetically() {
        return this.sort(String.CASE_INSENSITIVE_ORDER);
    }
    
    public TabCompleteHelper filterPrefix(final String s2) {
        return this.filter(s -> s.toLowerCase(Locale.US).startsWith(s2.toLowerCase(Locale.US)));
    }
    
    public TabCompleteHelper filterPrefixNamespaced(final String s) {
        return this.filterPrefix(new nf(s).toString());
    }
    
    public String[] build() {
        return this.stream.toArray(String[]::new);
    }
    
    public Stream<String> stream() {
        return this.stream;
    }
    
    public TabCompleteHelper addCommands(final ICommandManager commandManager) {
        return this.append(commandManager.getRegistry().descendingStream().flatMap(command -> command.getNames().stream()).distinct());
    }
    
    public TabCompleteHelper addSettings() {
        return this.append(BaritoneAPI.getSettings().allSettings.stream().map((Function<? super Object, ? extends String>)Settings$Setting::getName).filter(s -> !s.equalsIgnoreCase("logger")).sorted(String.CASE_INSENSITIVE_ORDER));
    }
    
    public TabCompleteHelper addModifiedSettings() {
        return this.append(SettingsUtil.modifiedSettings(BaritoneAPI.getSettings()).stream().map((Function<? super Object, ? extends String>)Settings$Setting::getName).sorted(String.CASE_INSENSITIVE_ORDER));
    }
    
    public TabCompleteHelper addToggleableSettings() {
        return this.append(BaritoneAPI.getSettings().getAllValuesByType(Boolean.class).stream().map((Function<? super Object, ? extends String>)Settings$Setting::getName).sorted(String.CASE_INSENSITIVE_ORDER));
    }
}
