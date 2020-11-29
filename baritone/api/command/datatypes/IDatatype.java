// 
// Decompiled by Procyon v0.5.36
// 

package baritone.api.command.datatypes;

import java.util.stream.Stream;

public interface IDatatype
{
    Stream<String> tabComplete(final IDatatypeContext p0);
}
