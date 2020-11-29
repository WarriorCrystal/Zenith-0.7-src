// 
// Decompiled by Procyon v0.5.36
// 

package baritone;

import java.io.IOException;
import java.awt.Toolkit;
import java.awt.SystemTray;
import org.apache.commons.lang3.SystemUtils;
import java.awt.TrayIcon;

public final class fs
{
    private static TrayIcon a;
    private final double a;
    private final double b;
    private final double c;
    private final double d;
    
    public static void a(final String s) {
        if (SystemUtils.IS_OS_WINDOWS) {
            b(s);
            return;
        }
        if (SystemUtils.IS_OS_MAC_OSX) {
            c(s);
            return;
        }
        if (SystemUtils.IS_OS_LINUX) {
            d(s);
        }
    }
    
    private static void b(final String text) {
        if (SystemTray.isSupported()) {
            try {
                if (fs.a == null) {
                    final SystemTray systemTray = SystemTray.getSystemTray();
                    (fs.a = new TrayIcon(Toolkit.getDefaultToolkit().createImage(""), "Baritone")).setImageAutoSize(true);
                    fs.a.setToolTip("Baritone");
                    systemTray.add(fs.a);
                }
                fs.a.displayMessage("Baritone", text, TrayIcon.MessageType.ERROR);
                return;
            }
            catch (Exception ex) {
                ex.printStackTrace();
                return;
            }
        }
        System.out.println("SystemTray is not supported");
    }
    
    private static void c(final String str) {
        final ProcessBuilder processBuilder;
        (processBuilder = new ProcessBuilder(new String[0])).command("osascript", "-e", "display notification \"" + str + "\" with title \"Baritone\"");
        try {
            processBuilder.start();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    private static void d(final String s) {
        final ProcessBuilder processBuilder;
        (processBuilder = new ProcessBuilder(new String[0])).command("notify-send", "-a", "Baritone", s);
        try {
            processBuilder.start();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public fs(final axn axn) {
        this.a = axn.b();
        this.b = axn.d();
        this.c = axn.c();
        this.d = axn.e();
    }
    
    public final boolean a(final int n, final int n2) {
        return n + 1 > this.a && n < this.b && n2 + 1 > this.c && n2 < this.d;
    }
    
    public final boolean b(final int n, final int n2) {
        return n > this.a && n + 1 < this.b && n2 > this.c && n2 + 1 < this.d;
    }
}
