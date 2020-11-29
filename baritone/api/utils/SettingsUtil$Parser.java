// 
// Decompiled by Procyon v0.5.36
// 

package baritone.api.utils;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.lang.reflect.ParameterizedType;
import java.awt.Color;
import java.util.stream.Stream;
import java.lang.reflect.Type;
import java.util.Objects;
import java.util.function.Function;

enum SettingsUtil$Parser implements SettingsUtil$ISettingParser
{
    DOUBLE((Class<T>)Double.class, (Function<String, T>)Double::parseDouble), 
    BOOLEAN((Class<T>)Boolean.class, (Function<String, T>)Boolean::parseBoolean), 
    INTEGER((Class<T>)Integer.class, (Function<String, T>)Integer::parseInt), 
    FLOAT((Class<T>)Float.class, (Function<String, T>)Float::parseFloat), 
    LONG((Class<T>)Long.class, (Function<String, T>)Long::parseLong), 
    STRING((Class<T>)String.class, (Function<String, T>)String::new), 
    ENUMFACING((Class<T>)fa.class, (Function<String, T>)fa::a), 
    COLOR((Class<T>)Color.class, s -> new Color(Integer.parseInt(s.split(",")[0]), Integer.parseInt(s.split(",")[1]), Integer.parseInt(s.split(",")[2])), color -> color.getRed() + "," + color.getGreen() + "," + color.getBlue()), 
    VEC3I((Class<T>)fq.class, s2 -> new fq(Integer.parseInt(s2.split(",")[0]), Integer.parseInt(s2.split(",")[1]), Integer.parseInt(s2.split(",")[2])), fq -> fq.p() + "," + fq.q() + "," + fq.r()), 
    BLOCK((Class<T>)aow.class, s3 -> BlockUtils.stringToBlockRequired(s3.trim()), (Function<T, String>)BlockUtils::blockToString), 
    ITEM((Class<T>)ain.class, s4 -> ain.b(s4.trim()), ain -> ((nf)ain.g.b((Object)ain)).toString()), 
    LIST("LIST", 11) {
        SettingsUtil$Parser$1(final String s, final int n) {
        }
        
        @Override
        public final Object parse(final SettingsUtil$ParserContext settingsUtil$ParserContext, final String s) {
            return Stream.of(s.split(",")).map(s2 -> SettingsUtil$Parser.getParser(((ParameterizedType)settingsUtil$ParserContext.getSetting().getType()).getActualTypeArguments()[0]).parse(settingsUtil$ParserContext, s2)).collect((Collector<? super Object, ?, List<? super Object>>)Collectors.toList());
        }
        
        @Override
        public final String toString(final SettingsUtil$ParserContext settingsUtil$ParserContext, final Object o) {
            return (String)((List)o).stream().map(o2 -> SettingsUtil$Parser.getParser(((ParameterizedType)settingsUtil$ParserContext.getSetting().getType()).getActualTypeArguments()[0]).toString(settingsUtil$ParserContext, o2)).collect(Collectors.joining(","));
        }
        
        @Override
        public final boolean accepts(final Type type) {
            return List.class.isAssignableFrom(TypeUtils.resolveBaseClass(type));
        }
    };
    
    private final Class<?> cla$$;
    private final Function<String, Object> parser;
    private final Function<Object, String> toString;
    
    private SettingsUtil$Parser() {
        this.cla$$ = null;
        this.parser = null;
        this.toString = null;
    }
    
    private <T> SettingsUtil$Parser(final Class<T> clazz, final Function<String, T> function) {
        this(clazz, function, Object::toString);
    }
    
    private <T> SettingsUtil$Parser(final Class<T> cla$$, final Function<String, T> function, final Function<T, String> function2) {
        this.cla$$ = cla$$;
        this.parser = function::apply;
        this.toString = (t -> function2.apply(t));
    }
    
    @Override
    public Object parse(final SettingsUtil$ParserContext settingsUtil$ParserContext, final String s) {
        final Object apply;
        Objects.requireNonNull(apply = this.parser.apply(s));
        return apply;
    }
    
    @Override
    public String toString(final SettingsUtil$ParserContext settingsUtil$ParserContext, final Object o) {
        return this.toString.apply(o);
    }
    
    @Override
    public boolean accepts(final Type type) {
        return type instanceof Class && this.cla$$.isAssignableFrom((Class<?>)type);
    }
    
    public static SettingsUtil$Parser getParser(final Type type) {
        return Stream.of(values()).filter(settingsUtil$Parser -> settingsUtil$Parser.accepts(type)).findFirst().orElse(null);
    }
}
