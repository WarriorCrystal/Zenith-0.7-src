// 
// Decompiled by Procyon v0.5.36
// 

package baritone.api.utils;

public final class RayTraceUtils
{
    private RayTraceUtils() {
    }
    
    public static bhc rayTraceTowards(final vg vg, final Rotation rotation, final double n) {
        return rayTraceTowards(vg, rotation, n, false);
    }
    
    public static bhc rayTraceTowards(final vg vg, final Rotation rotation, final double n, final boolean b) {
        bhe bhe;
        if (b) {
            bhe = inferSneakingEyePosition(vg);
        }
        else {
            bhe = vg.f(1.0f);
        }
        final bhe calcVec3dFromRotation = RotationUtils.calcVec3dFromRotation(rotation);
        return vg.l.a(bhe, bhe.b(calcVec3dFromRotation.b * n, calcVec3dFromRotation.c * n, calcVec3dFromRotation.d * n), false, false, true);
    }
    
    public static bhe inferSneakingEyePosition(final vg vg) {
        return new bhe(vg.p, vg.q + IPlayerContext.eyeHeight(true), vg.r);
    }
}
