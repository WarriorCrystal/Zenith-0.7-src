//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package com.tterrag.blur;

import java.util.Iterator;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.InsnNode;
import java.util.Map;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.ClassNode;
import net.minecraft.launchwrapper.IClassTransformer;

public class BlurTransformer implements IClassTransformer
{
    private static final String GUI_SCREEN_CLASS_NAME = "net.minecraft.client.gui.GuiScreen";
    private static final String DRAW_WORLD_BAGKGROUND_METHOD = "drawWorldBackground";
    private static final String DRAW_WORLD_BAGKGROUND_METHOD_OBF = "drawWorldBackground";
    private static final String BLUR_MAIN_CLASS = "com/tterrag/blur/Blur";
    private static final String COLOR_HOOK_METHOD_NAME = "getBackgroundColor";
    private static final String COLOR_HOOK_METHOD_DESC = "(Z)I";
    
    public byte[] transform(final String name, final String transformedName, final byte[] basicClass) {
        if (transformedName.equals("net.minecraft.client.gui.GuiScreen")) {
            System.out.println("Transforming Class [" + transformedName + "], Method [" + "drawWorldBackground" + "]");
            final ClassNode classNode = new ClassNode();
            final ClassReader classReader = new ClassReader(basicClass);
            classReader.accept((ClassVisitor)classNode, 0);
            for (final MethodNode m : classNode.methods) {
                if (m.name.equals("drawWorldBackground") || m.name.equals("drawWorldBackground")) {
                    for (int i = 0; i < m.instructions.size(); ++i) {
                        final AbstractInsnNode next = m.instructions.get(i);
                        if (next.getOpcode() == 18) {
                            System.out.println("Modifying GUI background darkness... ");
                            final AbstractInsnNode colorHook = (AbstractInsnNode)new MethodInsnNode(184, "com/tterrag/blur/Blur", "getBackgroundColor", "(Z)I", false);
                            final AbstractInsnNode colorHook2 = colorHook.clone((Map)null);
                            m.instructions.set(next, colorHook);
                            m.instructions.set(colorHook.getNext(), colorHook2);
                            m.instructions.insertBefore(colorHook, (AbstractInsnNode)new InsnNode(4));
                            m.instructions.insertBefore(colorHook2, (AbstractInsnNode)new InsnNode(3));
                            break;
                        }
                    }
                    break;
                }
            }
            final ClassWriter cw = new ClassWriter(1);
            classNode.accept((ClassVisitor)cw);
            System.out.println("Transforming " + transformedName + " Finished.");
            return cw.toByteArray();
        }
        return basicClass;
    }
}
