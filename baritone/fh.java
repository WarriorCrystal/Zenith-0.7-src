// 
// Decompiled by Procyon v0.5.36
// 

package baritone;

import java.util.ListIterator;
import baritone.api.utils.BetterBlockPos;
import baritone.api.selection.ISelection;
import java.util.LinkedList;
import baritone.api.selection.ISelectionManager;

public final class fh implements ISelectionManager
{
    private final LinkedList<ISelection> a;
    private ISelection[] a;
    
    public fh(final a a) {
        this.a = new LinkedList<ISelection>();
        this.a = new ISelection[0];
        new fi(a, this);
    }
    
    private void a() {
        this.a = this.a.toArray(new ISelection[0]);
    }
    
    @Override
    public final synchronized ISelection addSelection(final ISelection e) {
        this.a.add(e);
        this.a();
        return e;
    }
    
    @Override
    public final ISelection addSelection(final BetterBlockPos betterBlockPos, final BetterBlockPos betterBlockPos2) {
        return this.addSelection(new ff(betterBlockPos, betterBlockPos2));
    }
    
    @Override
    public final synchronized ISelection removeSelection(final ISelection o) {
        this.a.remove(o);
        this.a();
        return o;
    }
    
    @Override
    public final synchronized ISelection[] removeAllSelections() {
        final ISelection[] selections = this.getSelections();
        this.a.clear();
        this.a();
        return selections;
    }
    
    @Override
    public final ISelection[] getSelections() {
        return this.a;
    }
    
    @Override
    public final synchronized ISelection getOnlySelection() {
        if (this.a.size() == 1) {
            return this.a.peekFirst();
        }
        return null;
    }
    
    @Override
    public final ISelection getLastSelection() {
        return this.a.peekLast();
    }
    
    @Override
    public final synchronized ISelection expand(final ISelection selection, final fa fa, final int n) {
        final ListIterator<ISelection> listIterator = (ListIterator<ISelection>)this.a.listIterator();
        while (listIterator.hasNext()) {
            final ISelection selection2;
            if ((selection2 = listIterator.next()) == selection) {
                listIterator.remove();
                listIterator.add(selection2.expand(fa, n));
                this.a();
                return listIterator.previous();
            }
        }
        return null;
    }
    
    @Override
    public final synchronized ISelection contract(final ISelection selection, final fa fa, final int n) {
        final ListIterator<ISelection> listIterator = (ListIterator<ISelection>)this.a.listIterator();
        while (listIterator.hasNext()) {
            final ISelection selection2;
            if ((selection2 = listIterator.next()) == selection) {
                listIterator.remove();
                listIterator.add(selection2.contract(fa, n));
                this.a();
                return listIterator.previous();
            }
        }
        return null;
    }
    
    @Override
    public final synchronized ISelection shift(final ISelection selection, final fa fa, final int n) {
        final ListIterator<ISelection> listIterator = (ListIterator<ISelection>)this.a.listIterator();
        while (listIterator.hasNext()) {
            final ISelection selection2;
            if ((selection2 = listIterator.next()) == selection) {
                listIterator.remove();
                listIterator.add(selection2.shift(fa, n));
                this.a();
                return listIterator.previous();
            }
        }
        return null;
    }
}
