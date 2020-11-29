// 
// Decompiled by Procyon v0.5.36
// 

package baritone.api.command.datatypes;

public interface IDatatypePost<T, O> extends IDatatype
{
    T apply(final IDatatypeContext p0, final O p1);
}
