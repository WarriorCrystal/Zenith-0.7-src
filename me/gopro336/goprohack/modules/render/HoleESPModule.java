// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.modules.render;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.math.AxisAlignedBB;
import me.gopro336.goprohack.events.render.RenderEvent;
import me.zero.alpine.fork.listener.EventHandler;
import me.gopro336.goprohack.events.player.EventPlayerUpdate;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.client.renderer.culling.ICamera;
import me.gopro336.goprohack.util.Hole;
import java.util.List;
import me.gopro336.goprohack.util.render.ESPUtil;
import me.gopro336.goprohack.modules.Value;
import me.gopro336.goprohack.modules.Module;

public class HoleESPModule extends Module
{
    public final Value<ESPUtil.HoleModes> HoleMode;
    public final Value<Integer> Radius;
    public final Value<Boolean> IgnoreOwnHole;
    public final Value<Float> ObsidianRed;
    public final Value<Float> ObsidianGreen;
    public final Value<Float> ObsidianBlue;
    public final Value<Float> ObsidianAlpha;
    public final Value<Float> BedrockRed;
    public final Value<Float> BedrockGreen;
    public final Value<Float> BedrockBlue;
    public final Value<Float> BedrockAlpha;
    public final List<Hole> holes;
    private ICamera camera;
    @EventHandler
    private Listener<EventPlayerUpdate> OnPlayerUpdate;
    @EventHandler
    private Listener<RenderEvent> OnRenderEvent;
    
    public HoleESPModule() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: ldc             "HoleESP"
        //     3: iconst_1       
        //     4: anewarray       Ljava/lang/String;
        //     7: dup            
        //     8: iconst_0       
        //     9: ldc             ""
        //    11: aastore        
        //    12: ldc             "Highlights holes for crystal pvp"
        //    14: ldc             "NONE"
        //    16: iconst_m1      
        //    17: getstatic       me/gopro336/goprohack/modules/Module$ModuleType.RENDER:Lme/gopro336/goprohack/modules/Module$ModuleType;
        //    20: invokespecial   me/gopro336/goprohack/modules/Module.<init>:(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ILme/gopro336/goprohack/modules/Module$ModuleType;)V
        //    23: aload_0         /* this */
        //    24: new             Lme/gopro336/goprohack/modules/Value;
        //    27: dup            
        //    28: ldc             "Mode"
        //    30: iconst_1       
        //    31: anewarray       Ljava/lang/String;
        //    34: dup            
        //    35: iconst_0       
        //    36: ldc             "HM"
        //    38: aastore        
        //    39: ldc             "Mode for rendering holes"
        //    41: getstatic       me/gopro336/goprohack/util/render/ESPUtil$HoleModes.FlatOutline:Lme/gopro336/goprohack/util/render/ESPUtil$HoleModes;
        //    44: invokespecial   me/gopro336/goprohack/modules/Value.<init>:(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/Object;)V
        //    47: putfield        me/gopro336/goprohack/modules/render/HoleESPModule.HoleMode:Lme/gopro336/goprohack/modules/Value;
        //    50: aload_0         /* this */
        //    51: new             Lme/gopro336/goprohack/modules/Value;
        //    54: dup            
        //    55: ldc             "Radius"
        //    57: iconst_3       
        //    58: anewarray       Ljava/lang/String;
        //    61: dup            
        //    62: iconst_0       
        //    63: ldc             "Radius"
        //    65: aastore        
        //    66: dup            
        //    67: iconst_1       
        //    68: ldc             "Range"
        //    70: aastore        
        //    71: dup            
        //    72: iconst_2       
        //    73: ldc             "Distance"
        //    75: aastore        
        //    76: ldc             "Radius in blocks to scan for holes."
        //    78: bipush          8
        //    80: invokestatic    java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
        //    83: iconst_0       
        //    84: invokestatic    java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
        //    87: bipush          32
        //    89: invokestatic    java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
        //    92: iconst_1       
        //    93: invokestatic    java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
        //    96: invokespecial   me/gopro336/goprohack/modules/Value.<init>:(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)V
        //    99: putfield        me/gopro336/goprohack/modules/render/HoleESPModule.Radius:Lme/gopro336/goprohack/modules/Value;
        //   102: aload_0         /* this */
        //   103: new             Lme/gopro336/goprohack/modules/Value;
        //   106: dup            
        //   107: ldc             "IgnoreOwnHole"
        //   109: iconst_1       
        //   110: anewarray       Ljava/lang/String;
        //   113: dup            
        //   114: iconst_0       
        //   115: ldc             "NoSelfHole"
        //   117: aastore        
        //   118: ldc             "Doesn't render the hole you're standing in"
        //   120: iconst_0       
        //   121: invokestatic    java/lang/Boolean.valueOf:(Z)Ljava/lang/Boolean;
        //   124: invokespecial   me/gopro336/goprohack/modules/Value.<init>:(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/Object;)V
        //   127: putfield        me/gopro336/goprohack/modules/render/HoleESPModule.IgnoreOwnHole:Lme/gopro336/goprohack/modules/Value;
        //   130: aload_0         /* this */
        //   131: new             Lme/gopro336/goprohack/modules/Value;
        //   134: dup            
        //   135: ldc             "ObsidianRed"
        //   137: iconst_1       
        //   138: anewarray       Ljava/lang/String;
        //   141: dup            
        //   142: iconst_0       
        //   143: ldc             "oRed"
        //   145: aastore        
        //   146: ldc             "Red for rendering"
        //   148: fconst_0       
        //   149: invokestatic    java/lang/Float.valueOf:(F)Ljava/lang/Float;
        //   152: fconst_0       
        //   153: invokestatic    java/lang/Float.valueOf:(F)Ljava/lang/Float;
        //   156: fconst_1       
        //   157: invokestatic    java/lang/Float.valueOf:(F)Ljava/lang/Float;
        //   160: ldc             0.1
        //   162: invokestatic    java/lang/Float.valueOf:(F)Ljava/lang/Float;
        //   165: invokespecial   me/gopro336/goprohack/modules/Value.<init>:(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)V
        //   168: putfield        me/gopro336/goprohack/modules/render/HoleESPModule.ObsidianRed:Lme/gopro336/goprohack/modules/Value;
        //   171: aload_0         /* this */
        //   172: new             Lme/gopro336/goprohack/modules/Value;
        //   175: dup            
        //   176: ldc             "ObsidianGreen"
        //   178: iconst_1       
        //   179: anewarray       Ljava/lang/String;
        //   182: dup            
        //   183: iconst_0       
        //   184: ldc             "oGreen"
        //   186: aastore        
        //   187: ldc             "Green for rendering"
        //   189: fconst_1       
        //   190: invokestatic    java/lang/Float.valueOf:(F)Ljava/lang/Float;
        //   193: fconst_0       
        //   194: invokestatic    java/lang/Float.valueOf:(F)Ljava/lang/Float;
        //   197: fconst_1       
        //   198: invokestatic    java/lang/Float.valueOf:(F)Ljava/lang/Float;
        //   201: ldc             0.1
        //   203: invokestatic    java/lang/Float.valueOf:(F)Ljava/lang/Float;
        //   206: invokespecial   me/gopro336/goprohack/modules/Value.<init>:(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)V
        //   209: putfield        me/gopro336/goprohack/modules/render/HoleESPModule.ObsidianGreen:Lme/gopro336/goprohack/modules/Value;
        //   212: aload_0         /* this */
        //   213: new             Lme/gopro336/goprohack/modules/Value;
        //   216: dup            
        //   217: ldc             "ObsidianBlue"
        //   219: iconst_1       
        //   220: anewarray       Ljava/lang/String;
        //   223: dup            
        //   224: iconst_0       
        //   225: ldc             "oBlue"
        //   227: aastore        
        //   228: ldc             "Blue for rendering"
        //   230: fconst_0       
        //   231: invokestatic    java/lang/Float.valueOf:(F)Ljava/lang/Float;
        //   234: fconst_0       
        //   235: invokestatic    java/lang/Float.valueOf:(F)Ljava/lang/Float;
        //   238: fconst_1       
        //   239: invokestatic    java/lang/Float.valueOf:(F)Ljava/lang/Float;
        //   242: ldc             0.1
        //   244: invokestatic    java/lang/Float.valueOf:(F)Ljava/lang/Float;
        //   247: invokespecial   me/gopro336/goprohack/modules/Value.<init>:(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)V
        //   250: putfield        me/gopro336/goprohack/modules/render/HoleESPModule.ObsidianBlue:Lme/gopro336/goprohack/modules/Value;
        //   253: aload_0         /* this */
        //   254: new             Lme/gopro336/goprohack/modules/Value;
        //   257: dup            
        //   258: ldc             "ObsidianAlpha"
        //   260: iconst_1       
        //   261: anewarray       Ljava/lang/String;
        //   264: dup            
        //   265: iconst_0       
        //   266: ldc             "oAlpha"
        //   268: aastore        
        //   269: ldc             "Alpha for rendering"
        //   271: ldc             0.5
        //   273: invokestatic    java/lang/Float.valueOf:(F)Ljava/lang/Float;
        //   276: fconst_0       
        //   277: invokestatic    java/lang/Float.valueOf:(F)Ljava/lang/Float;
        //   280: fconst_1       
        //   281: invokestatic    java/lang/Float.valueOf:(F)Ljava/lang/Float;
        //   284: ldc             0.1
        //   286: invokestatic    java/lang/Float.valueOf:(F)Ljava/lang/Float;
        //   289: invokespecial   me/gopro336/goprohack/modules/Value.<init>:(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)V
        //   292: putfield        me/gopro336/goprohack/modules/render/HoleESPModule.ObsidianAlpha:Lme/gopro336/goprohack/modules/Value;
        //   295: aload_0         /* this */
        //   296: new             Lme/gopro336/goprohack/modules/Value;
        //   299: dup            
        //   300: ldc             "BedrockRed"
        //   302: iconst_1       
        //   303: anewarray       Ljava/lang/String;
        //   306: dup            
        //   307: iconst_0       
        //   308: ldc             "bRed"
        //   310: aastore        
        //   311: ldc             "Red for rendering"
        //   313: fconst_0       
        //   314: invokestatic    java/lang/Float.valueOf:(F)Ljava/lang/Float;
        //   317: fconst_0       
        //   318: invokestatic    java/lang/Float.valueOf:(F)Ljava/lang/Float;
        //   321: fconst_1       
        //   322: invokestatic    java/lang/Float.valueOf:(F)Ljava/lang/Float;
        //   325: ldc             0.1
        //   327: invokestatic    java/lang/Float.valueOf:(F)Ljava/lang/Float;
        //   330: invokespecial   me/gopro336/goprohack/modules/Value.<init>:(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)V
        //   333: putfield        me/gopro336/goprohack/modules/render/HoleESPModule.BedrockRed:Lme/gopro336/goprohack/modules/Value;
        //   336: aload_0         /* this */
        //   337: new             Lme/gopro336/goprohack/modules/Value;
        //   340: dup            
        //   341: ldc             "BedrockGreen"
        //   343: iconst_1       
        //   344: anewarray       Ljava/lang/String;
        //   347: dup            
        //   348: iconst_0       
        //   349: ldc             "bGreen"
        //   351: aastore        
        //   352: ldc             "Green for rendering"
        //   354: fconst_1       
        //   355: invokestatic    java/lang/Float.valueOf:(F)Ljava/lang/Float;
        //   358: fconst_0       
        //   359: invokestatic    java/lang/Float.valueOf:(F)Ljava/lang/Float;
        //   362: fconst_1       
        //   363: invokestatic    java/lang/Float.valueOf:(F)Ljava/lang/Float;
        //   366: ldc             0.1
        //   368: invokestatic    java/lang/Float.valueOf:(F)Ljava/lang/Float;
        //   371: invokespecial   me/gopro336/goprohack/modules/Value.<init>:(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)V
        //   374: putfield        me/gopro336/goprohack/modules/render/HoleESPModule.BedrockGreen:Lme/gopro336/goprohack/modules/Value;
        //   377: aload_0         /* this */
        //   378: new             Lme/gopro336/goprohack/modules/Value;
        //   381: dup            
        //   382: ldc             "BedrockBlue"
        //   384: iconst_1       
        //   385: anewarray       Ljava/lang/String;
        //   388: dup            
        //   389: iconst_0       
        //   390: ldc             "bBlue"
        //   392: aastore        
        //   393: ldc             "Blue for rendering"
        //   395: ldc             0.8
        //   397: invokestatic    java/lang/Float.valueOf:(F)Ljava/lang/Float;
        //   400: fconst_0       
        //   401: invokestatic    java/lang/Float.valueOf:(F)Ljava/lang/Float;
        //   404: fconst_1       
        //   405: invokestatic    java/lang/Float.valueOf:(F)Ljava/lang/Float;
        //   408: ldc             0.1
        //   410: invokestatic    java/lang/Float.valueOf:(F)Ljava/lang/Float;
        //   413: invokespecial   me/gopro336/goprohack/modules/Value.<init>:(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)V
        //   416: putfield        me/gopro336/goprohack/modules/render/HoleESPModule.BedrockBlue:Lme/gopro336/goprohack/modules/Value;
        //   419: aload_0         /* this */
        //   420: new             Lme/gopro336/goprohack/modules/Value;
        //   423: dup            
        //   424: ldc             "BedrockAlpha"
        //   426: iconst_1       
        //   427: anewarray       Ljava/lang/String;
        //   430: dup            
        //   431: iconst_0       
        //   432: ldc             "bAlpha"
        //   434: aastore        
        //   435: ldc             "Alpha for rendering"
        //   437: ldc             0.5
        //   439: invokestatic    java/lang/Float.valueOf:(F)Ljava/lang/Float;
        //   442: fconst_0       
        //   443: invokestatic    java/lang/Float.valueOf:(F)Ljava/lang/Float;
        //   446: fconst_1       
        //   447: invokestatic    java/lang/Float.valueOf:(F)Ljava/lang/Float;
        //   450: ldc             0.1
        //   452: invokestatic    java/lang/Float.valueOf:(F)Ljava/lang/Float;
        //   455: invokespecial   me/gopro336/goprohack/modules/Value.<init>:(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)V
        //   458: putfield        me/gopro336/goprohack/modules/render/HoleESPModule.BedrockAlpha:Lme/gopro336/goprohack/modules/Value;
        //   461: aload_0         /* this */
        //   462: new             Ljava/util/ArrayList;
        //   465: dup            
        //   466: invokespecial   java/util/ArrayList.<init>:()V
        //   469: putfield        me/gopro336/goprohack/modules/render/HoleESPModule.holes:Ljava/util/List;
        //   472: aload_0         /* this */
        //   473: new             Lnet/minecraft/client/renderer/culling/Frustum;
        //   476: dup            
        //   477: invokespecial   net/minecraft/client/renderer/culling/Frustum.<init>:()V
        //   480: putfield        me/gopro336/goprohack/modules/render/HoleESPModule.camera:Lnet/minecraft/client/renderer/culling/ICamera;
        //   483: aload_0         /* this */
        //   484: new             Lme/zero/alpine/fork/listener/Listener;
        //   487: dup            
        //   488: aload_0         /* this */
        //   489: invokedynamic   BootstrapMethod #0, invoke:(Lme/gopro336/goprohack/modules/render/HoleESPModule;)Lme/zero/alpine/fork/listener/EventHook;
        //   494: iconst_0       
        //   495: anewarray       Ljava/util/function/Predicate;
        //   498: invokespecial   me/zero/alpine/fork/listener/Listener.<init>:(Lme/zero/alpine/fork/listener/EventHook;[Ljava/util/function/Predicate;)V
        //   501: putfield        me/gopro336/goprohack/modules/render/HoleESPModule.OnPlayerUpdate:Lme/zero/alpine/fork/listener/Listener;
        //   504: aload_0         /* this */
        //   505: new             Lme/zero/alpine/fork/listener/Listener;
        //   508: dup            
        //   509: aload_0         /* this */
        //   510: invokedynamic   BootstrapMethod #1, invoke:(Lme/gopro336/goprohack/modules/render/HoleESPModule;)Lme/zero/alpine/fork/listener/EventHook;
        //   515: iconst_0       
        //   516: anewarray       Ljava/util/function/Predicate;
        //   519: invokespecial   me/zero/alpine/fork/listener/Listener.<init>:(Lme/zero/alpine/fork/listener/EventHook;[Ljava/util/function/Predicate;)V
        //   522: putfield        me/gopro336/goprohack/modules/render/HoleESPModule.OnRenderEvent:Lme/zero/alpine/fork/listener/Listener;
        //   525: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Could not infer any expression.
        //     at com.strobel.decompiler.ast.TypeAnalysis.runInference(TypeAnalysis.java:374)
        //     at com.strobel.decompiler.ast.TypeAnalysis.run(TypeAnalysis.java:96)
        //     at com.strobel.decompiler.ast.AstOptimizer.optimize(AstOptimizer.java:344)
        //     at com.strobel.decompiler.ast.AstOptimizer.optimize(AstOptimizer.java:42)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:214)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createConstructor(AstBuilder.java:713)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:549)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:150)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:125)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at com.strobel.decompiler.DecompilerDriver.decompileType(DecompilerDriver.java:330)
        //     at com.strobel.decompiler.DecompilerDriver.decompileJar(DecompilerDriver.java:251)
        //     at com.strobel.decompiler.DecompilerDriver.main(DecompilerDriver.java:126)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
}
