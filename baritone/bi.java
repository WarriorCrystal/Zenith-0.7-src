// 
// Decompiled by Procyon v0.5.36
// 

package baritone;

import baritone.api.command.IBaritoneChatControl;
import baritone.api.command.ICommand;
import java.util.Arrays;
import java.util.List;
import baritone.api.command.helpers.TabCompleteHelper;
import java.util.stream.Stream;
import baritone.api.command.argument.IArgConsumer;
import baritone.api.IBaritone;
import baritone.api.command.Command;

public final class bi extends Command
{
    public bi(final IBaritone baritone) {
        super(baritone, new String[] { "help", "?" });
    }
    
    @Override
    public final void execute(final String p0, final IArgConsumer p1) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: iconst_1       
        //     2: invokeinterface baritone/api/command/argument/IArgConsumer.requireMax:(I)V
        //     7: aload_2        
        //     8: invokeinterface baritone/api/command/argument/IArgConsumer.hasAny:()Z
        //    13: ifeq            27
        //    16: aload_2        
        //    17: ldc             Ljava/lang/Integer;.class
        //    19: invokeinterface baritone/api/command/argument/IArgConsumer.is:(Ljava/lang/Class;)Z
        //    24: ifeq            109
        //    27: aload_2        
        //    28: new             Lbaritone/api/command/helpers/Paginator;
        //    31: dup            
        //    32: aload_0        
        //    33: getfield        baritone/bi.baritone:Lbaritone/api/IBaritone;
        //    36: invokeinterface baritone/api/IBaritone.getCommandManager:()Lbaritone/api/command/manager/ICommandManager;
        //    41: invokeinterface baritone/api/command/manager/ICommandManager.getRegistry:()Lbaritone/api/command/registry/Registry;
        //    46: invokevirtual   baritone/api/command/registry/Registry.descendingStream:()Ljava/util/stream/Stream;
        //    49: invokedynamic   BootstrapMethod #0, test:()Ljava/util/function/Predicate;
        //    54: invokeinterface java/util/stream/Stream.filter:(Ljava/util/function/Predicate;)Ljava/util/stream/Stream;
        //    59: invokestatic    java/util/stream/Collectors.toList:()Ljava/util/stream/Collector;
        //    62: invokeinterface java/util/stream/Stream.collect:(Ljava/util/stream/Collector;)Ljava/lang/Object;
        //    67: checkcast       Ljava/util/List;
        //    70: invokespecial   baritone/api/command/helpers/Paginator.<init>:(Ljava/util/List;)V
        //    73: aload_0        
        //    74: invokedynamic   BootstrapMethod #1, run:(Lbaritone/bi;)Ljava/lang/Runnable;
        //    79: aload_1        
        //    80: invokedynamic   BootstrapMethod #2, apply:(Ljava/lang/String;)Ljava/util/function/Function;
        //    85: new             Ljava/lang/StringBuilder;
        //    88: dup            
        //    89: invokespecial   java/lang/StringBuilder.<init>:()V
        //    92: getstatic       baritone/api/command/IBaritoneChatControl.FORCE_COMMAND_PREFIX:Ljava/lang/String;
        //    95: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    98: aload_1        
        //    99: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   102: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   105: invokestatic    baritone/api/command/helpers/Paginator.paginate:(Lbaritone/api/command/argument/IArgConsumer;Lbaritone/api/command/helpers/Paginator;Ljava/lang/Runnable;Ljava/util/function/Function;Ljava/lang/String;)V
        //   108: return         
        //   109: aload_2        
        //   110: invokeinterface baritone/api/command/argument/IArgConsumer.getString:()Ljava/lang/String;
        //   115: invokevirtual   java/lang/String.toLowerCase:()Ljava/lang/String;
        //   118: astore_2       
        //   119: aload_0        
        //   120: getfield        baritone/bi.baritone:Lbaritone/api/IBaritone;
        //   123: invokeinterface baritone/api/IBaritone.getCommandManager:()Lbaritone/api/command/manager/ICommandManager;
        //   128: aload_2        
        //   129: invokeinterface baritone/api/command/manager/ICommandManager.getCommand:(Ljava/lang/String;)Lbaritone/api/command/ICommand;
        //   134: dup            
        //   135: astore_3       
        //   136: ifnonnull       148
        //   139: new             Lbaritone/api/command/exception/CommandNotFoundException;
        //   142: dup            
        //   143: aload_2        
        //   144: invokespecial   baritone/api/command/exception/CommandNotFoundException.<init>:(Ljava/lang/String;)V
        //   147: athrow         
        //   148: aload_0        
        //   149: ldc             "%s - %s"
        //   151: iconst_2       
        //   152: anewarray       Ljava/lang/Object;
        //   155: dup            
        //   156: iconst_0       
        //   157: ldc             " / "
        //   159: aload_3        
        //   160: invokeinterface baritone/api/command/ICommand.getNames:()Ljava/util/List;
        //   165: invokestatic    java/lang/String.join:(Ljava/lang/CharSequence;Ljava/lang/Iterable;)Ljava/lang/String;
        //   168: aastore        
        //   169: dup            
        //   170: iconst_1       
        //   171: aload_3        
        //   172: invokeinterface baritone/api/command/ICommand.getShortDesc:()Ljava/lang/String;
        //   177: aastore        
        //   178: invokestatic    java/lang/String.format:(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
        //   181: invokevirtual   baritone/bi.logDirect:(Ljava/lang/String;)V
        //   184: aload_0        
        //   185: ldc             ""
        //   187: invokevirtual   baritone/bi.logDirect:(Ljava/lang/String;)V
        //   190: aload_3        
        //   191: invokeinterface baritone/api/command/ICommand.getLongDesc:()Ljava/util/List;
        //   196: aload_0        
        //   197: invokedynamic   BootstrapMethod #3, accept:(Lbaritone/bi;)Ljava/util/function/Consumer;
        //   202: invokeinterface java/util/List.forEach:(Ljava/util/function/Consumer;)V
        //   207: aload_0        
        //   208: ldc             ""
        //   210: invokevirtual   baritone/bi.logDirect:(Ljava/lang/String;)V
        //   213: new             Lho;
        //   216: dup            
        //   217: ldc             "Click to return to the help menu"
        //   219: invokespecial   ho.<init>:(Ljava/lang/String;)V
        //   222: dup            
        //   223: astore_2       
        //   224: invokeinterface hh.b:()Lhn;
        //   229: new             Lhg;
        //   232: dup            
        //   233: getstatic       hg$a.c:Lhg$a;
        //   236: new             Ljava/lang/StringBuilder;
        //   239: dup            
        //   240: invokespecial   java/lang/StringBuilder.<init>:()V
        //   243: getstatic       baritone/api/command/IBaritoneChatControl.FORCE_COMMAND_PREFIX:Ljava/lang/String;
        //   246: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   249: aload_1        
        //   250: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   253: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   256: invokespecial   hg.<init>:(Lhg$a;Ljava/lang/String;)V
        //   259: invokevirtual   hn.a:(Lhg;)Lhn;
        //   262: pop            
        //   263: aload_0        
        //   264: iconst_1       
        //   265: anewarray       Lhh;
        //   268: dup            
        //   269: iconst_0       
        //   270: aload_2        
        //   271: aastore        
        //   272: invokevirtual   baritone/bi.logDirect:([Lhh;)V
        //   275: return         
        //    StackMapTable: 00 03 1B FB 00 51 FF 00 26 00 04 07 00 02 07 00 08 00 07 00 9D 00 00
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
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:675)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:552)
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
    
    @Override
    public final Stream<String> tabComplete(final String s, final IArgConsumer argConsumer) {
        if (argConsumer.hasExactlyOne()) {
            return new TabCompleteHelper().addCommands(this.baritone.getCommandManager()).filterPrefix(argConsumer.getString()).stream();
        }
        return Stream.empty();
    }
    
    @Override
    public final String getShortDesc() {
        return "View all commands or help on specific ones";
    }
    
    @Override
    public final List<String> getLongDesc() {
        return Arrays.asList("Using this command, you can view detailed help information on how to use certain commands of Baritone.", "", "Usage:", "> help - Lists all commands and their short descriptions.", "> help <command> - Displays help information on a specific command.");
    }
}
