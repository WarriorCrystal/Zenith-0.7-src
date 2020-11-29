// 
// Decompiled by Procyon v0.5.36
// 

package baritone;

import java.util.List;
import baritone.api.utils.SettingsUtil;
import baritone.api.Settings$Setting;
import baritone.api.command.helpers.TabCompleteHelper;
import java.util.Locale;
import java.util.Arrays;
import java.util.stream.Stream;
import baritone.api.command.argument.IArgConsumer;
import baritone.api.IBaritone;
import baritone.api.command.Command;

public final class bw extends Command
{
    public bw(final IBaritone baritone) {
        super(baritone, new String[] { "set", "setting", "settings" });
    }
    
    @Override
    public final void execute(final String p0, final IArgConsumer p1) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: invokeinterface baritone/api/command/argument/IArgConsumer.hasAny:()Z
        //     6: ifeq            24
        //     9: aload_2        
        //    10: invokeinterface baritone/api/command/argument/IArgConsumer.getString:()Ljava/lang/String;
        //    15: getstatic       java/util/Locale.US:Ljava/util/Locale;
        //    18: invokevirtual   java/lang/String.toLowerCase:(Ljava/util/Locale;)Ljava/lang/String;
        //    21: goto            26
        //    24: ldc             "list"
        //    26: astore_1       
        //    27: iconst_2       
        //    28: anewarray       Ljava/lang/String;
        //    31: dup            
        //    32: iconst_0       
        //    33: ldc             "s"
        //    35: aastore        
        //    36: dup            
        //    37: iconst_1       
        //    38: ldc             "save"
        //    40: aastore        
        //    41: invokestatic    java/util/Arrays.asList:([Ljava/lang/Object;)Ljava/util/List;
        //    44: aload_1        
        //    45: invokeinterface java/util/List.contains:(Ljava/lang/Object;)Z
        //    50: ifeq            66
        //    53: invokestatic    baritone/a.a:()Lbaritone/api/Settings;
        //    56: invokestatic    baritone/api/utils/SettingsUtil.save:(Lbaritone/api/Settings;)V
        //    59: aload_0        
        //    60: ldc             "Settings saved"
        //    62: invokevirtual   baritone/bw.logDirect:(Ljava/lang/String;)V
        //    65: return         
        //    66: iconst_3       
        //    67: anewarray       Ljava/lang/String;
        //    70: dup            
        //    71: iconst_0       
        //    72: ldc             "m"
        //    74: aastore        
        //    75: dup            
        //    76: iconst_1       
        //    77: ldc             "mod"
        //    79: aastore        
        //    80: dup            
        //    81: iconst_2       
        //    82: ldc             "modified"
        //    84: aastore        
        //    85: invokestatic    java/util/Arrays.asList:([Ljava/lang/Object;)Ljava/util/List;
        //    88: aload_1        
        //    89: invokeinterface java/util/List.contains:(Ljava/lang/Object;)Z
        //    94: istore_3       
        //    95: iconst_3       
        //    96: anewarray       Ljava/lang/String;
        //    99: dup            
        //   100: iconst_0       
        //   101: ldc             "all"
        //   103: aastore        
        //   104: dup            
        //   105: iconst_1       
        //   106: ldc             "l"
        //   108: aastore        
        //   109: dup            
        //   110: iconst_2       
        //   111: ldc             "list"
        //   113: aastore        
        //   114: invokestatic    java/util/Arrays.asList:([Ljava/lang/Object;)Ljava/util/List;
        //   117: aload_1        
        //   118: invokeinterface java/util/List.contains:(Ljava/lang/Object;)Z
        //   123: istore          4
        //   125: iload_3        
        //   126: ifne            134
        //   129: iload           4
        //   131: ifeq            138
        //   134: iconst_1       
        //   135: goto            139
        //   138: iconst_0       
        //   139: ifeq            314
        //   142: aload_2        
        //   143: invokeinterface baritone/api/command/argument/IArgConsumer.hasAny:()Z
        //   148: ifeq            171
        //   151: aload_2        
        //   152: ldc             Ljava/lang/Integer;.class
        //   154: invokeinterface baritone/api/command/argument/IArgConsumer.peekAsOrNull:(Ljava/lang/Class;)Ljava/lang/Object;
        //   159: ifnonnull       171
        //   162: aload_2        
        //   163: invokeinterface baritone/api/command/argument/IArgConsumer.getString:()Ljava/lang/String;
        //   168: goto            173
        //   171: ldc             ""
        //   173: astore          4
        //   175: aload_2        
        //   176: iconst_1       
        //   177: invokeinterface baritone/api/command/argument/IArgConsumer.requireMax:(I)V
        //   182: iload_3        
        //   183: ifeq            195
        //   186: invokestatic    baritone/a.a:()Lbaritone/api/Settings;
        //   189: invokestatic    baritone/api/utils/SettingsUtil.modifiedSettings:(Lbaritone/api/Settings;)Ljava/util/List;
        //   192: goto            201
        //   195: invokestatic    baritone/a.a:()Lbaritone/api/Settings;
        //   198: getfield        baritone/api/Settings.allSettings:Ljava/util/List;
        //   201: invokeinterface java/util/List.stream:()Ljava/util/stream/Stream;
        //   206: invokedynamic   BootstrapMethod #0, test:()Ljava/util/function/Predicate;
        //   211: invokeinterface java/util/stream/Stream.filter:(Ljava/util/function/Predicate;)Ljava/util/stream/Stream;
        //   216: aload           4
        //   218: invokedynamic   BootstrapMethod #1, test:(Ljava/lang/String;)Ljava/util/function/Predicate;
        //   223: invokeinterface java/util/stream/Stream.filter:(Ljava/util/function/Predicate;)Ljava/util/stream/Stream;
        //   228: invokedynamic   BootstrapMethod #2, compare:()Ljava/util/Comparator;
        //   233: invokeinterface java/util/stream/Stream.sorted:(Ljava/util/Comparator;)Ljava/util/stream/Stream;
        //   238: invokestatic    java/util/stream/Collectors.toList:()Ljava/util/stream/Collector;
        //   241: invokeinterface java/util/stream/Stream.collect:(Ljava/util/stream/Collector;)Ljava/lang/Object;
        //   246: checkcast       Ljava/util/List;
        //   249: astore          5
        //   251: aload_2        
        //   252: new             Lbaritone/api/command/helpers/Paginator;
        //   255: dup            
        //   256: aload           5
        //   258: invokespecial   baritone/api/command/helpers/Paginator.<init>:(Ljava/util/List;)V
        //   261: aload_0        
        //   262: aload           4
        //   264: iload_3        
        //   265: invokedynamic   BootstrapMethod #3, run:(Lbaritone/bw;Ljava/lang/String;Z)Ljava/lang/Runnable;
        //   270: invokedynamic   BootstrapMethod #4, apply:()Ljava/util/function/Function;
        //   275: new             Ljava/lang/StringBuilder;
        //   278: dup            
        //   279: invokespecial   java/lang/StringBuilder.<init>:()V
        //   282: getstatic       baritone/api/command/IBaritoneChatControl.FORCE_COMMAND_PREFIX:Ljava/lang/String;
        //   285: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   288: ldc             "set "
        //   290: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   293: aload_1        
        //   294: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   297: ldc             " "
        //   299: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   302: aload           4
        //   304: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   307: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   310: invokestatic    baritone/api/command/helpers/Paginator.paginate:(Lbaritone/api/command/argument/IArgConsumer;Lbaritone/api/command/helpers/Paginator;Ljava/lang/Runnable;Ljava/util/function/Function;Ljava/lang/String;)V
        //   313: return         
        //   314: aload_2        
        //   315: iconst_1       
        //   316: invokeinterface baritone/api/command/argument/IArgConsumer.requireMax:(I)V
        //   321: aload_1        
        //   322: ldc             "reset"
        //   324: invokevirtual   java/lang/String.equalsIgnoreCase:(Ljava/lang/String;)Z
        //   327: istore          4
        //   329: aload_1        
        //   330: ldc             "toggle"
        //   332: invokevirtual   java/lang/String.equalsIgnoreCase:(Ljava/lang/String;)Z
        //   335: istore          5
        //   337: iload           4
        //   339: ifne            347
        //   342: iload           5
        //   344: ifeq            351
        //   347: iconst_1       
        //   348: goto            352
        //   351: iconst_0       
        //   352: istore_3       
        //   353: iload           4
        //   355: ifeq            431
        //   358: aload_2        
        //   359: invokeinterface baritone/api/command/argument/IArgConsumer.hasAny:()Z
        //   364: ifne            388
        //   367: aload_0        
        //   368: ldc             "Please specify 'all' as an argument to reset to confirm you'd really like to do this"
        //   370: invokevirtual   baritone/bw.logDirect:(Ljava/lang/String;)V
        //   373: aload_0        
        //   374: ldc             "ALL settings will be reset. Use the 'set modified' or 'modified' commands to see what will be reset."
        //   376: invokevirtual   baritone/bw.logDirect:(Ljava/lang/String;)V
        //   379: aload_0        
        //   380: ldc             "Specify a setting name instead of 'all' to only reset one setting"
        //   382: invokevirtual   baritone/bw.logDirect:(Ljava/lang/String;)V
        //   385: goto            431
        //   388: aload_2        
        //   389: invokeinterface baritone/api/command/argument/IArgConsumer.peekString:()Ljava/lang/String;
        //   394: ldc             "all"
        //   396: invokevirtual   java/lang/String.equalsIgnoreCase:(Ljava/lang/String;)Z
        //   399: ifeq            431
        //   402: invokestatic    baritone/a.a:()Lbaritone/api/Settings;
        //   405: invokestatic    baritone/api/utils/SettingsUtil.modifiedSettings:(Lbaritone/api/Settings;)Ljava/util/List;
        //   408: invokedynamic   BootstrapMethod #5, accept:()Ljava/util/function/Consumer;
        //   413: invokeinterface java/util/List.forEach:(Ljava/util/function/Consumer;)V
        //   418: aload_0        
        //   419: ldc             "All settings have been reset to their default values"
        //   421: invokevirtual   baritone/bw.logDirect:(Ljava/lang/String;)V
        //   424: invokestatic    baritone/a.a:()Lbaritone/api/Settings;
        //   427: invokestatic    baritone/api/utils/SettingsUtil.save:(Lbaritone/api/Settings;)V
        //   430: return         
        //   431: iload           5
        //   433: ifeq            443
        //   436: aload_2        
        //   437: iconst_1       
        //   438: invokeinterface baritone/api/command/argument/IArgConsumer.requireMin:(I)V
        //   443: iload_3        
        //   444: ifeq            456
        //   447: aload_2        
        //   448: invokeinterface baritone/api/command/argument/IArgConsumer.getString:()Ljava/lang/String;
        //   453: goto            457
        //   456: aload_1        
        //   457: astore          6
        //   459: invokestatic    baritone/a.a:()Lbaritone/api/Settings;
        //   462: getfield        baritone/api/Settings.allSettings:Ljava/util/List;
        //   465: invokeinterface java/util/List.stream:()Ljava/util/stream/Stream;
        //   470: aload           6
        //   472: invokedynamic   BootstrapMethod #6, test:(Ljava/lang/String;)Ljava/util/function/Predicate;
        //   477: invokeinterface java/util/stream/Stream.filter:(Ljava/util/function/Predicate;)Ljava/util/stream/Stream;
        //   482: invokeinterface java/util/stream/Stream.findFirst:()Ljava/util/Optional;
        //   487: aconst_null    
        //   488: invokevirtual   java/util/Optional.orElse:(Ljava/lang/Object;)Ljava/lang/Object;
        //   491: checkcast       Lbaritone/api/Settings$Setting;
        //   494: dup            
        //   495: astore          6
        //   497: ifnonnull       517
        //   500: new             Lbaritone/api/command/exception/CommandInvalidTypeException;
        //   503: dup            
        //   504: aload_2        
        //   505: invokeinterface baritone/api/command/argument/IArgConsumer.consumed:()Lbaritone/api/command/argument/ICommandArgument;
        //   510: ldc_w           "a valid setting"
        //   513: invokespecial   baritone/api/command/exception/CommandInvalidTypeException.<init>:(Lbaritone/api/command/argument/ICommandArgument;Ljava/lang/String;)V
        //   516: athrow         
        //   517: iload_3        
        //   518: ifne            564
        //   521: aload_2        
        //   522: invokeinterface baritone/api/command/argument/IArgConsumer.hasAny:()Z
        //   527: ifne            564
        //   530: aload_0        
        //   531: ldc_w           "Value of setting %s:"
        //   534: iconst_1       
        //   535: anewarray       Ljava/lang/Object;
        //   538: dup            
        //   539: iconst_0       
        //   540: aload           6
        //   542: invokevirtual   baritone/api/Settings$Setting.getName:()Ljava/lang/String;
        //   545: aastore        
        //   546: invokestatic    java/lang/String.format:(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
        //   549: invokevirtual   baritone/bw.logDirect:(Ljava/lang/String;)V
        //   552: aload_0        
        //   553: aload           6
        //   555: invokestatic    baritone/api/utils/SettingsUtil.settingValueToString:(Lbaritone/api/Settings$Setting;)Ljava/lang/String;
        //   558: invokevirtual   baritone/bw.logDirect:(Ljava/lang/String;)V
        //   561: goto            1043
        //   564: aload           6
        //   566: invokestatic    baritone/api/utils/SettingsUtil.settingValueToString:(Lbaritone/api/Settings$Setting;)Ljava/lang/String;
        //   569: astore_3       
        //   570: iload           4
        //   572: ifeq            583
        //   575: aload           6
        //   577: invokevirtual   baritone/api/Settings$Setting.reset:()V
        //   580: goto            724
        //   583: iload           5
        //   585: ifeq            681
        //   588: aload           6
        //   590: invokevirtual   baritone/api/Settings$Setting.getValueClass:()Ljava/lang/Class;
        //   593: ldc_w           Ljava/lang/Boolean;.class
        //   596: if_acmpeq       619
        //   599: new             Lbaritone/api/command/exception/CommandInvalidTypeException;
        //   602: dup            
        //   603: aload_2        
        //   604: invokeinterface baritone/api/command/argument/IArgConsumer.consumed:()Lbaritone/api/command/argument/ICommandArgument;
        //   609: ldc_w           "a toggleable setting"
        //   612: ldc_w           "some other setting"
        //   615: invokespecial   baritone/api/command/exception/CommandInvalidTypeException.<init>:(Lbaritone/api/command/argument/ICommandArgument;Ljava/lang/String;Ljava/lang/String;)V
        //   618: athrow         
        //   619: aload           6
        //   621: dup            
        //   622: getfield        baritone/api/Settings$Setting.value:Ljava/lang/Object;
        //   625: checkcast       Ljava/lang/Boolean;
        //   628: invokevirtual   java/lang/Boolean.booleanValue:()Z
        //   631: iconst_1       
        //   632: ixor           
        //   633: invokestatic    java/lang/Boolean.valueOf:(Z)Ljava/lang/Boolean;
        //   636: putfield        baritone/api/Settings$Setting.value:Ljava/lang/Object;
        //   639: aload_0        
        //   640: ldc_w           "Toggled setting %s to %s"
        //   643: iconst_2       
        //   644: anewarray       Ljava/lang/Object;
        //   647: dup            
        //   648: iconst_0       
        //   649: aload           6
        //   651: invokevirtual   baritone/api/Settings$Setting.getName:()Ljava/lang/String;
        //   654: aastore        
        //   655: dup            
        //   656: iconst_1       
        //   657: aload           6
        //   659: getfield        baritone/api/Settings$Setting.value:Ljava/lang/Object;
        //   662: checkcast       Ljava/lang/Boolean;
        //   665: invokevirtual   java/lang/Boolean.booleanValue:()Z
        //   668: invokestatic    java/lang/Boolean.toString:(Z)Ljava/lang/String;
        //   671: aastore        
        //   672: invokestatic    java/lang/String.format:(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
        //   675: invokevirtual   baritone/bw.logDirect:(Ljava/lang/String;)V
        //   678: goto            724
        //   681: aload_2        
        //   682: invokeinterface baritone/api/command/argument/IArgConsumer.getString:()Ljava/lang/String;
        //   687: astore          7
        //   689: invokestatic    baritone/a.a:()Lbaritone/api/Settings;
        //   692: aload_1        
        //   693: aload           7
        //   695: invokestatic    baritone/api/utils/SettingsUtil.parseAndApply:(Lbaritone/api/Settings;Ljava/lang/String;Ljava/lang/String;)V
        //   698: goto            724
        //   701: dup            
        //   702: astore_1       
        //   703: invokevirtual   java/lang/Throwable.printStackTrace:()V
        //   706: new             Lbaritone/api/command/exception/CommandInvalidTypeException;
        //   709: dup            
        //   710: aload_2        
        //   711: invokeinterface baritone/api/command/argument/IArgConsumer.consumed:()Lbaritone/api/command/argument/ICommandArgument;
        //   716: ldc_w           "a valid value"
        //   719: aload_1        
        //   720: invokespecial   baritone/api/command/exception/CommandInvalidTypeException.<init>:(Lbaritone/api/command/argument/ICommandArgument;Ljava/lang/String;Ljava/lang/Throwable;)V
        //   723: athrow         
        //   724: iload           5
        //   726: ifne            774
        //   729: aload_0        
        //   730: ldc_w           "Successfully %s %s to %s"
        //   733: iconst_3       
        //   734: anewarray       Ljava/lang/Object;
        //   737: dup            
        //   738: iconst_0       
        //   739: iload           4
        //   741: ifeq            749
        //   744: ldc             "reset"
        //   746: goto            751
        //   749: ldc             "set"
        //   751: aastore        
        //   752: dup            
        //   753: iconst_1       
        //   754: aload           6
        //   756: invokevirtual   baritone/api/Settings$Setting.getName:()Ljava/lang/String;
        //   759: aastore        
        //   760: dup            
        //   761: iconst_2       
        //   762: aload           6
        //   764: invokestatic    baritone/api/utils/SettingsUtil.settingValueToString:(Lbaritone/api/Settings$Setting;)Ljava/lang/String;
        //   767: aastore        
        //   768: invokestatic    java/lang/String.format:(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
        //   771: invokevirtual   baritone/bw.logDirect:(Ljava/lang/String;)V
        //   774: new             Lho;
        //   777: dup            
        //   778: ldc_w           "Old value: %s"
        //   781: iconst_1       
        //   782: anewarray       Ljava/lang/Object;
        //   785: dup            
        //   786: iconst_0       
        //   787: aload_3        
        //   788: aastore        
        //   789: invokestatic    java/lang/String.format:(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
        //   792: invokespecial   ho.<init>:(Ljava/lang/String;)V
        //   795: dup            
        //   796: astore          7
        //   798: invokeinterface hh.b:()Lhn;
        //   803: getstatic       a.h:La;
        //   806: invokevirtual   hn.a:(La;)Lhn;
        //   809: new             Lhj;
        //   812: dup            
        //   813: getstatic       hj$a.a:Lhj$a;
        //   816: new             Lho;
        //   819: dup            
        //   820: ldc_w           "Click to set the setting back to this value"
        //   823: invokespecial   ho.<init>:(Ljava/lang/String;)V
        //   826: invokespecial   hj.<init>:(Lhj$a;Lhh;)V
        //   829: invokevirtual   hn.a:(Lhj;)Lhn;
        //   832: new             Lhg;
        //   835: dup            
        //   836: getstatic       hg$a.c:Lhg$a;
        //   839: new             Ljava/lang/StringBuilder;
        //   842: dup            
        //   843: invokespecial   java/lang/StringBuilder.<init>:()V
        //   846: getstatic       baritone/api/command/IBaritoneChatControl.FORCE_COMMAND_PREFIX:Ljava/lang/String;
        //   849: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   852: ldc_w           "set %s %s"
        //   855: iconst_2       
        //   856: anewarray       Ljava/lang/Object;
        //   859: dup            
        //   860: iconst_0       
        //   861: aload           6
        //   863: invokevirtual   baritone/api/Settings$Setting.getName:()Ljava/lang/String;
        //   866: aastore        
        //   867: dup            
        //   868: iconst_1       
        //   869: aload_3        
        //   870: aastore        
        //   871: invokestatic    java/lang/String.format:(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
        //   874: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   877: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   880: invokespecial   hg.<init>:(Lhg$a;Ljava/lang/String;)V
        //   883: invokevirtual   hn.a:(Lhg;)Lhn;
        //   886: pop            
        //   887: aload_0        
        //   888: iconst_1       
        //   889: anewarray       Lhh;
        //   892: dup            
        //   893: iconst_0       
        //   894: aload           7
        //   896: aastore        
        //   897: invokevirtual   baritone/bw.logDirect:([Lhh;)V
        //   900: aload           6
        //   902: invokevirtual   baritone/api/Settings$Setting.getName:()Ljava/lang/String;
        //   905: ldc_w           "chatControl"
        //   908: invokevirtual   java/lang/String.equals:(Ljava/lang/Object;)Z
        //   911: ifeq            946
        //   914: aload           6
        //   916: getfield        baritone/api/Settings$Setting.value:Ljava/lang/Object;
        //   919: checkcast       Ljava/lang/Boolean;
        //   922: invokevirtual   java/lang/Boolean.booleanValue:()Z
        //   925: ifne            946
        //   928: invokestatic    baritone/a.a:()Lbaritone/api/Settings;
        //   931: getfield        baritone/api/Settings.chatControlAnyway:Lbaritone/api/Settings$Setting;
        //   934: getfield        baritone/api/Settings$Setting.value:Ljava/lang/Object;
        //   937: checkcast       Ljava/lang/Boolean;
        //   940: invokevirtual   java/lang/Boolean.booleanValue:()Z
        //   943: ifeq            992
        //   946: aload           6
        //   948: invokevirtual   baritone/api/Settings$Setting.getName:()Ljava/lang/String;
        //   951: ldc_w           "chatControlAnyway"
        //   954: invokevirtual   java/lang/String.equals:(Ljava/lang/Object;)Z
        //   957: ifeq            1005
        //   960: aload           6
        //   962: getfield        baritone/api/Settings$Setting.value:Ljava/lang/Object;
        //   965: checkcast       Ljava/lang/Boolean;
        //   968: invokevirtual   java/lang/Boolean.booleanValue:()Z
        //   971: ifne            1005
        //   974: invokestatic    baritone/a.a:()Lbaritone/api/Settings;
        //   977: getfield        baritone/api/Settings.chatControl:Lbaritone/api/Settings$Setting;
        //   980: getfield        baritone/api/Settings$Setting.value:Ljava/lang/Object;
        //   983: checkcast       Ljava/lang/Boolean;
        //   986: invokevirtual   java/lang/Boolean.booleanValue:()Z
        //   989: ifne            1005
        //   992: aload_0        
        //   993: ldc_w           "Warning: Chat commands will no longer work. If you want to revert this change, use prefix control (if enabled) or click the old value listed above."
        //   996: getstatic       a.m:La;
        //   999: invokevirtual   baritone/bw.logDirect:(Ljava/lang/String;La;)V
        //  1002: goto            1043
        //  1005: aload           6
        //  1007: invokevirtual   baritone/api/Settings$Setting.getName:()Ljava/lang/String;
        //  1010: ldc_w           "prefixControl"
        //  1013: invokevirtual   java/lang/String.equals:(Ljava/lang/Object;)Z
        //  1016: ifeq            1043
        //  1019: aload           6
        //  1021: getfield        baritone/api/Settings$Setting.value:Ljava/lang/Object;
        //  1024: checkcast       Ljava/lang/Boolean;
        //  1027: invokevirtual   java/lang/Boolean.booleanValue:()Z
        //  1030: ifne            1043
        //  1033: aload_0        
        //  1034: ldc_w           "Warning: Prefixed commands will no longer work. If you want to revert this change, use chat control (if enabled) or click the old value listed above."
        //  1037: getstatic       a.m:La;
        //  1040: invokevirtual   baritone/bw.logDirect:(Ljava/lang/String;La;)V
        //  1043: invokestatic    baritone/a.a:()Lbaritone/api/Settings;
        //  1046: invokestatic    baritone/api/utils/SettingsUtil.save:(Lbaritone/api/Settings;)V
        //  1049: return         
        //    StackMapTable: 00 21 FF 00 18 00 03 07 00 02 00 07 00 17 00 00 41 07 00 08 FF 00 27 00 03 07 00 02 07 00 08 07 00 17 00 00 FC 00 43 01 03 40 01 1F 41 07 00 08 FC 00 15 07 00 08 45 07 00 37 F9 00 70 FE 00 20 00 01 01 03 40 01 FF 00 23 00 06 07 00 02 07 00 08 07 00 17 01 01 01 00 00 2A 0B 0C 40 07 00 08 FC 00 3B 07 00 F0 FF 00 2E 00 07 07 00 02 07 00 08 07 00 17 00 01 01 07 00 F0 00 00 FF 00 12 00 07 07 00 02 07 00 08 07 00 17 07 00 08 01 01 07 00 F0 00 00 FF 00 23 00 07 07 00 02 00 00 07 00 08 01 01 07 00 F0 00 00 FF 00 3D 00 07 07 00 02 07 00 08 07 00 17 07 00 08 01 01 07 00 F0 00 00 FF 00 13 00 03 00 00 07 00 17 00 01 07 00 15 FF 00 16 00 07 07 00 02 00 00 07 00 08 01 01 07 00 F0 00 00 FF 00 18 00 07 07 00 02 00 00 07 00 08 00 00 07 00 F0 00 05 07 00 02 07 00 08 07 01 56 07 01 56 01 FF 00 01 00 07 07 00 02 00 00 07 00 08 00 00 07 00 F0 00 06 07 00 02 07 00 08 07 01 56 07 01 56 01 07 00 08 16 FF 00 AB 00 07 07 00 02 00 00 00 00 00 07 00 F0 00 00 FF 00 2D 00 01 07 00 02 00 00 FF 00 0C 00 07 07 00 02 00 00 00 00 00 07 00 F0 00 00 FF 00 25 00 00 00 00
        //    Exceptions:
        //  Try           Handler
        //  Start  End    Start  End    Type                 
        //  -----  -----  -----  -----  ---------------------
        //  689    698    701    724    Ljava/lang/Throwable;
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
    public final Stream<String> tabComplete(String string, final IArgConsumer argConsumer) {
        if (argConsumer.hasAny()) {
            string = argConsumer.getString();
            if (argConsumer.hasExactlyOne() && !Arrays.asList("s", "save").contains(argConsumer.peekString().toLowerCase(Locale.US))) {
                if (string.equalsIgnoreCase("reset")) {
                    return new TabCompleteHelper().addModifiedSettings().prepend("all").filterPrefix(argConsumer.getString()).stream();
                }
                if (string.equalsIgnoreCase("toggle")) {
                    return new TabCompleteHelper().addToggleableSettings().filterPrefix(argConsumer.getString()).stream();
                }
                final Settings$Setting<?> settings$Setting;
                if ((settings$Setting = a.a().byLowerName.get(string.toLowerCase(Locale.US))) != null) {
                    if (settings$Setting.getType() == Boolean.class) {
                        final TabCompleteHelper tabCompleteHelper = new TabCompleteHelper();
                        if (settings$Setting.value) {
                            tabCompleteHelper.append("true", "false");
                        }
                        else {
                            tabCompleteHelper.append("false", "true");
                        }
                        return tabCompleteHelper.filterPrefix(argConsumer.getString()).stream();
                    }
                    return Stream.of(SettingsUtil.settingValueToString(settings$Setting));
                }
            }
            else if (!argConsumer.hasAny()) {
                return new TabCompleteHelper().addSettings().sortAlphabetically().prepend("list", "modified", "reset", "toggle", "save").filterPrefix(string).stream();
            }
        }
        return Stream.empty();
    }
    
    @Override
    public final String getShortDesc() {
        return "View or change settings";
    }
    
    @Override
    public final List<String> getLongDesc() {
        return Arrays.asList("Using the set command, you can manage all of Baritone's settings. Almost every aspect is controlled by these settings - go wild!", "", "Usage:", "> set - Same as `set list`", "> set list [page] - View all settings", "> set modified [page] - View modified settings", "> set <setting> - View the current value of a setting", "> set <setting> <value> - Set the value of a setting", "> set reset all - Reset ALL SETTINGS to their defaults", "> set reset <setting> - Reset a setting to its default", "> set toggle <setting> - Toggle a boolean setting", "> set save - Save all settings (this is automatic tho)");
    }
}
