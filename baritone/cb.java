// 
// Decompiled by Procyon v0.5.36
// 

package baritone;

import baritone.api.command.IBaritoneChatControl;
import java.util.Date;
import baritone.api.cache.IWaypoint;
import java.util.function.BiFunction;
import java.util.Arrays;
import java.util.List;
import baritone.api.command.datatypes.RelativeBlockPos;
import baritone.api.command.datatypes.ForWaypoints;
import baritone.api.cache.IWaypoint$Tag;
import baritone.api.command.helpers.TabCompleteHelper;
import java.util.stream.Stream;
import baritone.api.command.argument.IArgConsumer;
import baritone.api.IBaritone;
import baritone.api.command.Command;

public final class cb extends Command
{
    public cb(final IBaritone baritone) {
        super(baritone, new String[] { "waypoints", "waypoint", "wp" });
    }
    
    @Override
    public final void execute(final String p0, final IArgConsumer p1) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: invokeinterface baritone/api/command/argument/IArgConsumer.hasAny:()Z
        //     6: ifeq            21
        //     9: aload_2        
        //    10: invokeinterface baritone/api/command/argument/IArgConsumer.getString:()Ljava/lang/String;
        //    15: invokestatic    baritone/cc.a:(Ljava/lang/String;)Lbaritone/cc;
        //    18: goto            24
        //    21: getstatic       baritone/cc.a:Lbaritone/cc;
        //    24: dup            
        //    25: astore_3       
        //    26: ifnonnull       45
        //    29: new             Lbaritone/api/command/exception/CommandInvalidTypeException;
        //    32: dup            
        //    33: aload_2        
        //    34: invokeinterface baritone/api/command/argument/IArgConsumer.consumed:()Lbaritone/api/command/argument/ICommandArgument;
        //    39: ldc             "an action"
        //    41: invokespecial   baritone/api/command/exception/CommandInvalidTypeException.<init>:(Lbaritone/api/command/argument/ICommandArgument;Ljava/lang/String;)V
        //    44: athrow         
        //    45: aload_1        
        //    46: invokedynamic   BootstrapMethod #0, apply:(Ljava/lang/String;)Ljava/util/function/BiFunction;
        //    51: dup            
        //    52: astore          4
        //    54: aload_3        
        //    55: invokedynamic   BootstrapMethod #1, apply:(Ljava/util/function/BiFunction;Lbaritone/cc;)Ljava/util/function/Function;
        //    60: astore          5
        //    62: aload_3        
        //    63: getstatic       baritone/cc.a:Lbaritone/cc;
        //    66: if_acmpne       247
        //    69: aload_2        
        //    70: invokeinterface baritone/api/command/argument/IArgConsumer.hasAny:()Z
        //    75: ifeq            90
        //    78: aload_2        
        //    79: invokeinterface baritone/api/command/argument/IArgConsumer.peekString:()Ljava/lang/String;
        //    84: invokestatic    baritone/api/cache/IWaypoint$Tag.getByName:(Ljava/lang/String;)Lbaritone/api/cache/IWaypoint$Tag;
        //    87: goto            91
        //    90: aconst_null    
        //    91: dup            
        //    92: astore          6
        //    94: ifnull          104
        //    97: aload_2        
        //    98: invokeinterface baritone/api/command/argument/IArgConsumer.get:()Lbaritone/api/command/argument/ICommandArgument;
        //   103: pop            
        //   104: aload           6
        //   106: ifnull          121
        //   109: aload_0        
        //   110: getfield        baritone/cb.baritone:Lbaritone/api/IBaritone;
        //   113: aload           6
        //   115: invokestatic    baritone/api/command/datatypes/ForWaypoints.getWaypointsByTag:(Lbaritone/api/IBaritone;Lbaritone/api/cache/IWaypoint$Tag;)[Lbaritone/api/cache/IWaypoint;
        //   118: goto            128
        //   121: aload_0        
        //   122: getfield        baritone/cb.baritone:Lbaritone/api/IBaritone;
        //   125: invokestatic    baritone/api/command/datatypes/ForWaypoints.getWaypoints:(Lbaritone/api/IBaritone;)[Lbaritone/api/cache/IWaypoint;
        //   128: dup            
        //   129: astore          7
        //   131: arraylength    
        //   132: ifle            220
        //   135: aload_2        
        //   136: iconst_1       
        //   137: invokeinterface baritone/api/command/argument/IArgConsumer.requireMax:(I)V
        //   142: aload_2        
        //   143: aload           7
        //   145: aload_0        
        //   146: aload           6
        //   148: invokedynamic   BootstrapMethod #2, run:(Lbaritone/cb;Lbaritone/api/cache/IWaypoint$Tag;)Ljava/lang/Runnable;
        //   153: aload           5
        //   155: ldc             "%s%s %s%s"
        //   157: iconst_4       
        //   158: anewarray       Ljava/lang/Object;
        //   161: dup            
        //   162: iconst_0       
        //   163: getstatic       baritone/api/command/IBaritoneChatControl.FORCE_COMMAND_PREFIX:Ljava/lang/String;
        //   166: aastore        
        //   167: dup            
        //   168: iconst_1       
        //   169: aload_1        
        //   170: aastore        
        //   171: dup            
        //   172: iconst_2       
        //   173: aload_3        
        //   174: invokestatic    baritone/cc.a:(Lbaritone/cc;)[Ljava/lang/String;
        //   177: iconst_0       
        //   178: aaload         
        //   179: aastore        
        //   180: dup            
        //   181: iconst_3       
        //   182: aload           6
        //   184: ifnull          210
        //   187: new             Ljava/lang/StringBuilder;
        //   190: dup            
        //   191: ldc             " "
        //   193: invokespecial   java/lang/StringBuilder.<init>:(Ljava/lang/String;)V
        //   196: aload           6
        //   198: invokevirtual   baritone/api/cache/IWaypoint$Tag.getName:()Ljava/lang/String;
        //   201: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   204: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   207: goto            212
        //   210: ldc             ""
        //   212: aastore        
        //   213: invokestatic    java/lang/String.format:(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
        //   216: invokestatic    baritone/api/command/helpers/Paginator.paginate:(Lbaritone/api/command/argument/IArgConsumer;[Ljava/lang/Object;Ljava/lang/Runnable;Ljava/util/function/Function;Ljava/lang/String;)V
        //   219: return         
        //   220: aload_2        
        //   221: iconst_0       
        //   222: invokeinterface baritone/api/command/argument/IArgConsumer.requireMax:(I)V
        //   227: new             Lbaritone/api/command/exception/CommandInvalidStateException;
        //   230: dup            
        //   231: aload           6
        //   233: ifnull          241
        //   236: ldc             "No waypoints found by that tag"
        //   238: goto            243
        //   241: ldc             "No waypoints found"
        //   243: invokespecial   baritone/api/command/exception/CommandInvalidStateException.<init>:(Ljava/lang/String;)V
        //   246: athrow         
        //   247: aload_3        
        //   248: getstatic       baritone/cc.c:Lbaritone/cc;
        //   251: if_acmpne       458
        //   254: aload_2        
        //   255: invokeinterface baritone/api/command/argument/IArgConsumer.getString:()Ljava/lang/String;
        //   260: invokestatic    baritone/api/cache/IWaypoint$Tag.getByName:(Ljava/lang/String;)Lbaritone/api/cache/IWaypoint$Tag;
        //   263: dup            
        //   264: astore          6
        //   266: ifnonnull       295
        //   269: new             Lbaritone/api/command/exception/CommandInvalidStateException;
        //   272: dup            
        //   273: ldc             "'%s' is not a tag "
        //   275: iconst_1       
        //   276: anewarray       Ljava/lang/Object;
        //   279: dup            
        //   280: iconst_0       
        //   281: aload_2        
        //   282: invokeinterface baritone/api/command/argument/IArgConsumer.consumedString:()Ljava/lang/String;
        //   287: aastore        
        //   288: invokestatic    java/lang/String.format:(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
        //   291: invokespecial   baritone/api/command/exception/CommandInvalidStateException.<init>:(Ljava/lang/String;)V
        //   294: athrow         
        //   295: aload_2        
        //   296: invokeinterface baritone/api/command/argument/IArgConsumer.hasAny:()Z
        //   301: ifeq            313
        //   304: aload_2        
        //   305: invokeinterface baritone/api/command/argument/IArgConsumer.getString:()Ljava/lang/String;
        //   310: goto            315
        //   313: ldc             ""
        //   315: astore          7
        //   317: aload_2        
        //   318: invokeinterface baritone/api/command/argument/IArgConsumer.hasAny:()Z
        //   323: ifeq            350
        //   326: aload_2        
        //   327: getstatic       baritone/api/command/datatypes/RelativeBlockPos.INSTANCE:Lbaritone/api/command/datatypes/RelativeBlockPos;
        //   330: aload_0        
        //   331: getfield        baritone/cb.ctx:Lbaritone/api/utils/IPlayerContext;
        //   334: invokeinterface baritone/api/utils/IPlayerContext.playerFeet:()Lbaritone/api/utils/BetterBlockPos;
        //   339: invokeinterface baritone/api/command/argument/IArgConsumer.getDatatypePost:(Lbaritone/api/command/datatypes/IDatatypePost;Ljava/lang/Object;)Ljava/lang/Object;
        //   344: checkcast       Lbaritone/api/utils/BetterBlockPos;
        //   347: goto            359
        //   350: aload_0        
        //   351: getfield        baritone/cb.ctx:Lbaritone/api/utils/IPlayerContext;
        //   354: invokeinterface baritone/api/utils/IPlayerContext.playerFeet:()Lbaritone/api/utils/BetterBlockPos;
        //   359: astore          8
        //   361: aload_2        
        //   362: iconst_0       
        //   363: invokeinterface baritone/api/command/argument/IArgConsumer.requireMax:(I)V
        //   368: new             Lbaritone/api/cache/Waypoint;
        //   371: dup            
        //   372: aload           7
        //   374: aload           6
        //   376: aload           8
        //   378: invokespecial   baritone/api/cache/Waypoint.<init>:(Ljava/lang/String;Lbaritone/api/cache/IWaypoint$Tag;Lbaritone/api/utils/BetterBlockPos;)V
        //   381: astore          9
        //   383: aload_0        
        //   384: getfield        baritone/cb.baritone:Lbaritone/api/IBaritone;
        //   387: invokestatic    baritone/api/command/datatypes/ForWaypoints.waypoints:(Lbaritone/api/IBaritone;)Lbaritone/api/cache/IWaypointCollection;
        //   390: aload           9
        //   392: invokeinterface baritone/api/cache/IWaypointCollection.addWaypoint:(Lbaritone/api/cache/IWaypoint;)V
        //   397: new             Lho;
        //   400: dup            
        //   401: ldc             "Waypoint added: "
        //   403: invokespecial   ho.<init>:(Ljava/lang/String;)V
        //   406: dup            
        //   407: astore          10
        //   409: invokeinterface hh.b:()Lhn;
        //   414: getstatic       a.h:La;
        //   417: invokevirtual   hn.a:(La;)Lhn;
        //   420: pop            
        //   421: aload           10
        //   423: aload           4
        //   425: aload           9
        //   427: getstatic       baritone/cc.d:Lbaritone/cc;
        //   430: invokeinterface java/util/function/BiFunction.apply:(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
        //   435: checkcast       Lhh;
        //   438: invokeinterface hh.a:(Lhh;)Lhh;
        //   443: pop            
        //   444: aload_0        
        //   445: iconst_1       
        //   446: anewarray       Lhh;
        //   449: dup            
        //   450: iconst_0       
        //   451: aload           10
        //   453: aastore        
        //   454: invokevirtual   baritone/cb.logDirect:([Lhh;)V
        //   457: return         
        //   458: aload_3        
        //   459: getstatic       baritone/cc.b:Lbaritone/cc;
        //   462: if_acmpne       562
        //   465: aload_2        
        //   466: iconst_1       
        //   467: invokeinterface baritone/api/command/argument/IArgConsumer.requireMax:(I)V
        //   472: aload_2        
        //   473: invokeinterface baritone/api/command/argument/IArgConsumer.getString:()Ljava/lang/String;
        //   478: invokestatic    baritone/api/cache/IWaypoint$Tag.getByName:(Ljava/lang/String;)Lbaritone/api/cache/IWaypoint$Tag;
        //   481: astore          6
        //   483: aload_0        
        //   484: getfield        baritone/cb.baritone:Lbaritone/api/IBaritone;
        //   487: aload           6
        //   489: invokestatic    baritone/api/command/datatypes/ForWaypoints.getWaypointsByTag:(Lbaritone/api/IBaritone;Lbaritone/api/cache/IWaypoint$Tag;)[Lbaritone/api/cache/IWaypoint;
        //   492: dup            
        //   493: astore          7
        //   495: dup            
        //   496: astore          8
        //   498: arraylength    
        //   499: istore          9
        //   501: iconst_0       
        //   502: istore          10
        //   504: iload           10
        //   506: iload           9
        //   508: if_icmpge       538
        //   511: aload           8
        //   513: iload           10
        //   515: aaload         
        //   516: astore          4
        //   518: aload_0        
        //   519: getfield        baritone/cb.baritone:Lbaritone/api/IBaritone;
        //   522: invokestatic    baritone/api/command/datatypes/ForWaypoints.waypoints:(Lbaritone/api/IBaritone;)Lbaritone/api/cache/IWaypointCollection;
        //   525: aload           4
        //   527: invokeinterface baritone/api/cache/IWaypointCollection.removeWaypoint:(Lbaritone/api/cache/IWaypoint;)V
        //   532: iinc            10, 1
        //   535: goto            504
        //   538: aload_0        
        //   539: ldc_w           "Cleared %d waypoints"
        //   542: iconst_1       
        //   543: anewarray       Ljava/lang/Object;
        //   546: dup            
        //   547: iconst_0       
        //   548: aload           7
        //   550: arraylength    
        //   551: invokestatic    java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
        //   554: aastore        
        //   555: invokestatic    java/lang/String.format:(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
        //   558: invokevirtual   baritone/cb.logDirect:(Ljava/lang/String;)V
        //   561: return         
        //   562: aload_2        
        //   563: getstatic       baritone/api/command/datatypes/ForWaypoints.INSTANCE:Lbaritone/api/command/datatypes/ForWaypoints;
        //   566: invokeinterface baritone/api/command/argument/IArgConsumer.getDatatypeFor:(Lbaritone/api/command/datatypes/IDatatypeFor;)Ljava/lang/Object;
        //   571: checkcast       [Lbaritone/api/cache/IWaypoint;
        //   574: astore          6
        //   576: aconst_null    
        //   577: astore          7
        //   579: aload_2        
        //   580: invokeinterface baritone/api/command/argument/IArgConsumer.hasAny:()Z
        //   585: ifeq            703
        //   588: aload_2        
        //   589: invokeinterface baritone/api/command/argument/IArgConsumer.peekString:()Ljava/lang/String;
        //   594: ldc_w           "@"
        //   597: invokevirtual   java/lang/String.equals:(Ljava/lang/Object;)Z
        //   600: ifeq            703
        //   603: aload_2        
        //   604: iconst_2       
        //   605: invokeinterface baritone/api/command/argument/IArgConsumer.requireExactly:(I)V
        //   610: aload_2        
        //   611: invokeinterface baritone/api/command/argument/IArgConsumer.get:()Lbaritone/api/command/argument/ICommandArgument;
        //   616: pop            
        //   617: aload_2        
        //   618: ldc_w           Ljava/lang/Long;.class
        //   621: invokeinterface baritone/api/command/argument/IArgConsumer.getAs:(Ljava/lang/Class;)Ljava/lang/Object;
        //   626: checkcast       Ljava/lang/Long;
        //   629: invokevirtual   java/lang/Long.longValue:()J
        //   632: lstore          8
        //   634: aload           6
        //   636: dup            
        //   637: astore          10
        //   639: arraylength    
        //   640: istore          4
        //   642: iconst_0       
        //   643: istore          11
        //   645: iload           11
        //   647: iload           4
        //   649: if_icmpge       684
        //   652: aload           10
        //   654: iload           11
        //   656: aaload         
        //   657: dup            
        //   658: astore          12
        //   660: invokeinterface baritone/api/cache/IWaypoint.getCreationTimestamp:()J
        //   665: lload           8
        //   667: lcmp           
        //   668: ifne            678
        //   671: aload           12
        //   673: astore          7
        //   675: goto            684
        //   678: iinc            11, 1
        //   681: goto            645
        //   684: aload           7
        //   686: ifnonnull       700
        //   689: new             Lbaritone/api/command/exception/CommandInvalidStateException;
        //   692: dup            
        //   693: ldc_w           "Timestamp was specified but no waypoint was found"
        //   696: invokespecial   baritone/api/command/exception/CommandInvalidStateException.<init>:(Ljava/lang/String;)V
        //   699: athrow         
        //   700: goto            748
        //   703: aload           6
        //   705: arraylength    
        //   706: lookupswitch {
        //                0: 732
        //                1: 742
        //          default: 748
        //        }
        //   732: new             Lbaritone/api/command/exception/CommandInvalidStateException;
        //   735: dup            
        //   736: ldc             "No waypoints found"
        //   738: invokespecial   baritone/api/command/exception/CommandInvalidStateException.<init>:(Ljava/lang/String;)V
        //   741: athrow         
        //   742: aload           6
        //   744: iconst_0       
        //   745: aaload         
        //   746: astore          7
        //   748: aload           7
        //   750: ifnonnull       813
        //   753: aload_2        
        //   754: iconst_1       
        //   755: invokeinterface baritone/api/command/argument/IArgConsumer.requireMax:(I)V
        //   760: aload_2        
        //   761: aload           6
        //   763: aload_0        
        //   764: invokedynamic   BootstrapMethod #3, run:(Lbaritone/cb;)Ljava/lang/Runnable;
        //   769: aload           5
        //   771: ldc_w           "%s%s %s %s"
        //   774: iconst_4       
        //   775: anewarray       Ljava/lang/Object;
        //   778: dup            
        //   779: iconst_0       
        //   780: getstatic       baritone/api/command/IBaritoneChatControl.FORCE_COMMAND_PREFIX:Ljava/lang/String;
        //   783: aastore        
        //   784: dup            
        //   785: iconst_1       
        //   786: aload_1        
        //   787: aastore        
        //   788: dup            
        //   789: iconst_2       
        //   790: aload_3        
        //   791: invokestatic    baritone/cc.a:(Lbaritone/cc;)[Ljava/lang/String;
        //   794: iconst_0       
        //   795: aaload         
        //   796: aastore        
        //   797: dup            
        //   798: iconst_3       
        //   799: aload_2        
        //   800: invokeinterface baritone/api/command/argument/IArgConsumer.consumedString:()Ljava/lang/String;
        //   805: aastore        
        //   806: invokestatic    java/lang/String.format:(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
        //   809: invokestatic    baritone/api/command/helpers/Paginator.paginate:(Lbaritone/api/command/argument/IArgConsumer;[Ljava/lang/Object;Ljava/lang/Runnable;Ljava/util/function/Function;Ljava/lang/String;)V
        //   812: return         
        //   813: aload_3        
        //   814: getstatic       baritone/cc.d:Lbaritone/cc;
        //   817: if_acmpne       1115
        //   820: aload_0        
        //   821: iconst_1       
        //   822: anewarray       Lhh;
        //   825: dup            
        //   826: iconst_0       
        //   827: aload           5
        //   829: aload           7
        //   831: invokeinterface java/util/function/Function.apply:(Ljava/lang/Object;)Ljava/lang/Object;
        //   836: checkcast       Lhh;
        //   839: aastore        
        //   840: invokevirtual   baritone/cb.logDirect:([Lhh;)V
        //   843: aload_0        
        //   844: ldc_w           "Position: %s"
        //   847: iconst_1       
        //   848: anewarray       Ljava/lang/Object;
        //   851: dup            
        //   852: iconst_0       
        //   853: aload           7
        //   855: invokeinterface baritone/api/cache/IWaypoint.getLocation:()Lbaritone/api/utils/BetterBlockPos;
        //   860: aastore        
        //   861: invokestatic    java/lang/String.format:(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
        //   864: invokevirtual   baritone/cb.logDirect:(Ljava/lang/String;)V
        //   867: new             Lho;
        //   870: dup            
        //   871: ldc_w           "Click to delete this waypoint"
        //   874: invokespecial   ho.<init>:(Ljava/lang/String;)V
        //   877: dup            
        //   878: astore          8
        //   880: invokeinterface hh.b:()Lhn;
        //   885: new             Lhg;
        //   888: dup            
        //   889: getstatic       hg$a.c:Lhg$a;
        //   892: ldc_w           "%s%s delete %s @ %d"
        //   895: iconst_4       
        //   896: anewarray       Ljava/lang/Object;
        //   899: dup            
        //   900: iconst_0       
        //   901: getstatic       baritone/api/command/IBaritoneChatControl.FORCE_COMMAND_PREFIX:Ljava/lang/String;
        //   904: aastore        
        //   905: dup            
        //   906: iconst_1       
        //   907: aload_1        
        //   908: aastore        
        //   909: dup            
        //   910: iconst_2       
        //   911: aload           7
        //   913: invokeinterface baritone/api/cache/IWaypoint.getTag:()Lbaritone/api/cache/IWaypoint$Tag;
        //   918: invokevirtual   baritone/api/cache/IWaypoint$Tag.getName:()Ljava/lang/String;
        //   921: aastore        
        //   922: dup            
        //   923: iconst_3       
        //   924: aload           7
        //   926: invokeinterface baritone/api/cache/IWaypoint.getCreationTimestamp:()J
        //   931: invokestatic    java/lang/Long.valueOf:(J)Ljava/lang/Long;
        //   934: aastore        
        //   935: invokestatic    java/lang/String.format:(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
        //   938: invokespecial   hg.<init>:(Lhg$a;Ljava/lang/String;)V
        //   941: invokevirtual   hn.a:(Lhg;)Lhn;
        //   944: pop            
        //   945: new             Lho;
        //   948: dup            
        //   949: ldc_w           "Click to set goal to this waypoint"
        //   952: invokespecial   ho.<init>:(Ljava/lang/String;)V
        //   955: dup            
        //   956: astore          9
        //   958: invokeinterface hh.b:()Lhn;
        //   963: new             Lhg;
        //   966: dup            
        //   967: getstatic       hg$a.c:Lhg$a;
        //   970: ldc_w           "%s%s goal %s @ %d"
        //   973: iconst_4       
        //   974: anewarray       Ljava/lang/Object;
        //   977: dup            
        //   978: iconst_0       
        //   979: getstatic       baritone/api/command/IBaritoneChatControl.FORCE_COMMAND_PREFIX:Ljava/lang/String;
        //   982: aastore        
        //   983: dup            
        //   984: iconst_1       
        //   985: aload_1        
        //   986: aastore        
        //   987: dup            
        //   988: iconst_2       
        //   989: aload           7
        //   991: invokeinterface baritone/api/cache/IWaypoint.getTag:()Lbaritone/api/cache/IWaypoint$Tag;
        //   996: invokevirtual   baritone/api/cache/IWaypoint$Tag.getName:()Ljava/lang/String;
        //   999: aastore        
        //  1000: dup            
        //  1001: iconst_3       
        //  1002: aload           7
        //  1004: invokeinterface baritone/api/cache/IWaypoint.getCreationTimestamp:()J
        //  1009: invokestatic    java/lang/Long.valueOf:(J)Ljava/lang/Long;
        //  1012: aastore        
        //  1013: invokestatic    java/lang/String.format:(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
        //  1016: invokespecial   hg.<init>:(Lhg$a;Ljava/lang/String;)V
        //  1019: invokevirtual   hn.a:(Lhg;)Lhn;
        //  1022: pop            
        //  1023: new             Lho;
        //  1026: dup            
        //  1027: ldc_w           "Click to return to the waypoints list"
        //  1030: invokespecial   ho.<init>:(Ljava/lang/String;)V
        //  1033: dup            
        //  1034: astore          10
        //  1036: invokeinterface hh.b:()Lhn;
        //  1041: new             Lhg;
        //  1044: dup            
        //  1045: getstatic       hg$a.c:Lhg$a;
        //  1048: ldc_w           "%s%s list"
        //  1051: iconst_2       
        //  1052: anewarray       Ljava/lang/Object;
        //  1055: dup            
        //  1056: iconst_0       
        //  1057: getstatic       baritone/api/command/IBaritoneChatControl.FORCE_COMMAND_PREFIX:Ljava/lang/String;
        //  1060: aastore        
        //  1061: dup            
        //  1062: iconst_1       
        //  1063: aload_1        
        //  1064: aastore        
        //  1065: invokestatic    java/lang/String.format:(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
        //  1068: invokespecial   hg.<init>:(Lhg$a;Ljava/lang/String;)V
        //  1071: invokevirtual   hn.a:(Lhg;)Lhn;
        //  1074: pop            
        //  1075: aload_0        
        //  1076: iconst_1       
        //  1077: anewarray       Lhh;
        //  1080: dup            
        //  1081: iconst_0       
        //  1082: aload           8
        //  1084: aastore        
        //  1085: invokevirtual   baritone/cb.logDirect:([Lhh;)V
        //  1088: aload_0        
        //  1089: iconst_1       
        //  1090: anewarray       Lhh;
        //  1093: dup            
        //  1094: iconst_0       
        //  1095: aload           9
        //  1097: aastore        
        //  1098: invokevirtual   baritone/cb.logDirect:([Lhh;)V
        //  1101: aload_0        
        //  1102: iconst_1       
        //  1103: anewarray       Lhh;
        //  1106: dup            
        //  1107: iconst_0       
        //  1108: aload           10
        //  1110: aastore        
        //  1111: invokevirtual   baritone/cb.logDirect:([Lhh;)V
        //  1114: return         
        //  1115: aload_3        
        //  1116: getstatic       baritone/cc.e:Lbaritone/cc;
        //  1119: if_acmpne       1144
        //  1122: aload_0        
        //  1123: getfield        baritone/cb.baritone:Lbaritone/api/IBaritone;
        //  1126: invokestatic    baritone/api/command/datatypes/ForWaypoints.waypoints:(Lbaritone/api/IBaritone;)Lbaritone/api/cache/IWaypointCollection;
        //  1129: aload           7
        //  1131: invokeinterface baritone/api/cache/IWaypointCollection.removeWaypoint:(Lbaritone/api/cache/IWaypoint;)V
        //  1136: aload_0        
        //  1137: ldc_w           "That waypoint has successfully been deleted"
        //  1140: invokevirtual   baritone/cb.logDirect:(Ljava/lang/String;)V
        //  1143: return         
        //  1144: aload_3        
        //  1145: getstatic       baritone/cc.f:Lbaritone/cc;
        //  1148: if_acmpne       1202
        //  1151: new             Lbaritone/api/pathing/goals/GoalBlock;
        //  1154: dup            
        //  1155: aload           7
        //  1157: invokeinterface baritone/api/cache/IWaypoint.getLocation:()Lbaritone/api/utils/BetterBlockPos;
        //  1162: invokespecial   baritone/api/pathing/goals/GoalBlock.<init>:(Let;)V
        //  1165: astore          8
        //  1167: aload_0        
        //  1168: getfield        baritone/cb.baritone:Lbaritone/api/IBaritone;
        //  1171: invokeinterface baritone/api/IBaritone.getCustomGoalProcess:()Lbaritone/api/process/ICustomGoalProcess;
        //  1176: aload           8
        //  1178: invokeinterface baritone/api/process/ICustomGoalProcess.setGoal:(Lbaritone/api/pathing/goals/Goal;)V
        //  1183: aload_0        
        //  1184: ldc_w           "Goal: %s"
        //  1187: iconst_1       
        //  1188: anewarray       Ljava/lang/Object;
        //  1191: dup            
        //  1192: iconst_0       
        //  1193: aload           8
        //  1195: aastore        
        //  1196: invokestatic    java/lang/String.format:(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
        //  1199: invokevirtual   baritone/cb.logDirect:(Ljava/lang/String;)V
        //  1202: return         
        //    StackMapTable: 00 23 15 42 07 00 1F FC 00 14 07 00 1F FD 00 2C 00 07 00 5A 40 07 00 54 FC 00 0C 07 00 54 10 46 07 00 6D FF 00 51 00 00 00 08 07 00 15 07 00 6D 07 00 9B 07 00 5A 07 00 08 07 00 9D 07 00 9D 01 FF 00 01 00 00 00 09 07 00 15 07 00 6D 07 00 9B 07 00 5A 07 00 08 07 00 9D 07 00 9D 01 07 00 08 FF 00 07 00 07 00 00 07 00 15 00 00 00 07 00 54 00 00 FF 00 14 00 00 00 02 08 00 E3 08 00 E3 FF 00 01 00 00 00 03 08 00 E3 08 00 E3 07 00 08 FF 00 03 00 06 07 00 02 07 00 08 07 00 15 07 00 1F 07 00 B2 07 00 5A 00 00 FF 00 2F 00 07 07 00 02 00 07 00 15 00 07 00 B2 00 07 00 54 00 00 11 41 07 00 08 FC 00 22 07 00 08 48 07 00 D0 FF 00 62 00 06 07 00 02 07 00 08 07 00 15 07 00 1F 00 07 00 5A 00 00 FF 00 2D 00 0B 07 00 02 00 00 00 00 00 00 07 00 6D 07 00 6D 01 01 00 00 F8 00 21 FF 00 17 00 06 07 00 02 07 00 08 07 00 15 07 00 1F 00 07 00 5A 00 00 FF 00 52 00 0B 07 00 02 07 00 08 07 00 15 07 00 1F 01 07 00 5A 07 00 6D 05 04 07 00 6D 01 00 00 20 FF 00 05 00 08 07 00 02 07 00 08 07 00 15 07 00 1F 00 07 00 5A 07 00 6D 07 01 2A 00 00 0F FF 00 02 00 08 07 00 02 07 00 08 07 00 15 07 00 1F 00 07 00 5A 07 00 6D 05 00 00 FF 00 1C 00 00 00 00 FF 00 09 00 07 07 00 02 07 00 08 07 00 15 07 00 1F 00 07 00 5A 07 00 6D 00 00 FC 00 05 07 01 2A FF 00 40 00 08 07 00 02 07 00 08 00 07 00 1F 00 07 00 5A 00 07 01 2A 00 00 FF 01 2D 00 08 07 00 02 00 00 07 00 1F 00 00 00 07 01 2A 00 00 1C FF 00 39 00 00 00 00
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
        if (argConsumer.hasAny()) {
            if (argConsumer.hasExactlyOne()) {
                return new TabCompleteHelper().append(cc.a()).sortAlphabetically().filterPrefix(argConsumer.getString()).stream();
            }
            final cc a = cc.a(argConsumer.getString());
            if (argConsumer.hasExactlyOne()) {
                if (a == cc.a || a == cc.c || a == cc.b) {
                    return new TabCompleteHelper().append(IWaypoint$Tag.getAllNames()).sortAlphabetically().filterPrefix(argConsumer.getString()).stream();
                }
                return argConsumer.tabCompleteDatatype(ForWaypoints.INSTANCE);
            }
            else if (argConsumer.has(3) && a == cc.c) {
                argConsumer.get();
                argConsumer.get();
                return argConsumer.tabCompleteDatatype(RelativeBlockPos.INSTANCE);
            }
        }
        return Stream.empty();
    }
    
    @Override
    public final String getShortDesc() {
        return "Manage waypoints";
    }
    
    @Override
    public final List<String> getLongDesc() {
        return Arrays.asList("The waypoint command allows you to manage Baritone's waypoints.", "", "Waypoints can be used to mark positions for later. Waypoints are each given a tag and an optional name.", "", "Note that the info, delete, and goal commands let you specify a waypoint by tag. If there is more than one waypoint with a certain tag, then they will let you select which waypoint you mean.", "", "Usage:", "> wp [l/list] - List all waypoints.", "> wp <s/save> <tag> - Save your current position as an unnamed waypoint with the specified tag.", "> wp <s/save> <tag> <name> - Save the waypoint with the specified name.", "> wp <s/save> <tag> <name> <pos> - Save the waypoint with the specified name and position.", "> wp <i/info/show> <tag> - Show info on a waypoint by tag.", "> wp <d/delete> <tag> - Delete a waypoint by tag.", "> wp <g/goal/goto> <tag> - Set a goal to a waypoint by tag.");
    }
}
