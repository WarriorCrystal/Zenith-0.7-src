// 
// Decompiled by Procyon v0.5.36
// 

package baritone.api.command.datatypes;

import baritone.api.command.argument.IArgConsumer;
import java.util.stream.Stream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum RelativeCoordinate implements IDatatypePost<Double, Double>
{
    INSTANCE;
    
    private static Pattern PATTERN;
    
    @Override
    public final Double apply(final IDatatypeContext datatypeContext, Double value) {
        if (value == null) {
            value = 0.0;
        }
        final Matcher matcher;
        if (!(matcher = RelativeCoordinate.PATTERN.matcher(datatypeContext.getConsumer().getString())).matches()) {
            throw new IllegalArgumentException("pattern doesn't match");
        }
        final boolean b = !matcher.group(1).isEmpty();
        double d = matcher.group(2).isEmpty() ? 0.0 : Double.parseDouble(matcher.group(2).replaceAll("k", ""));
        if (matcher.group(2).contains("k")) {
            d *= 1000.0;
        }
        if (b) {
            return value + d;
        }
        return d;
    }
    
    @Override
    public final Stream<String> tabComplete(final IDatatypeContext datatypeContext) {
        final IArgConsumer consumer;
        if (!(consumer = datatypeContext.getConsumer()).has(2) && consumer.getString().matches("^(~|$)")) {
            return Stream.of("~");
        }
        return Stream.empty();
    }
    
    static {
        RelativeCoordinate.PATTERN = Pattern.compile("^(~?)([+-]?(?:\\d+(?:\\.\\d*)?|\\.\\d+)([k-k]?)|)$");
    }
}
