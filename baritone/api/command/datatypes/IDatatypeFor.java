// 
// Decompiled by Procyon v0.5.36
// 

package baritone.api.command.datatypes;

public interface IDatatypeFor<T> extends IDatatype
{
    T get(final IDatatypeContext p0);
}
