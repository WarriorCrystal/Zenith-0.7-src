//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.gui.chat;

import net.minecraft.util.TabCompleter;
import java.util.Iterator;
import java.util.List;
import net.minecraft.util.text.ITextComponent;
import me.gopro336.goprohack.managers.CommandManager;
import me.gopro336.goprohack.util.render.RenderUtil;
import com.mojang.realmsclient.gui.ChatFormatting;
import java.util.ArrayList;
import org.lwjgl.input.Mouse;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraft.client.Minecraft;
import java.io.IOException;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.input.Keyboard;
import me.gopro336.goprohack.modules.ui.ConsoleModule;
import me.gopro336.goprohack.command.Command;
import net.minecraft.client.gui.GuiChat;

public final class GoproGuiConsole extends GuiChat
{
    private Command CurrentCommand;
    private ConsoleModule Console;
    
    public GoproGuiConsole(final ConsoleModule p_Console) {
        this.CurrentCommand = null;
        this.Console = p_Console;
    }
    
    public void initGui() {
        Keyboard.enableRepeatEvents(true);
        this.sentHistoryCursor = this.mc.ingameGUI.getChatGUI().getSentMessages().size();
        (this.inputField = new GoproGuiTextField(2, this.fontRenderer, this.width - 295, 5, 595, 12, true)).setMaxStringLength(256);
        this.inputField.setEnableBackgroundDrawing(false);
        this.inputField.setFocused(true);
        this.inputField.setText("");
        this.inputField.setCanLoseFocus(false);
        this.tabCompleter = (TabCompleter)new GuiChat.ChatTabCompleter(this.inputField);
    }
    
    protected void keyTyped(final char typedChar, final int keyCode) throws IOException {
        this.tabCompleter.resetRequested();
        if (keyCode == 15) {
            this.tabCompleter.complete();
        }
        else {
            this.tabCompleter.resetDidComplete();
        }
        if (keyCode == 1) {
            this.mc.displayGuiScreen((GuiScreen)null);
        }
        else if (keyCode != 28 && keyCode != 156) {
            if (keyCode == 200) {
                this.getSentHistory(-1);
            }
            else if (keyCode == 208) {
                this.getSentHistory(1);
            }
            else if (keyCode == 201) {
                this.mc.ingameGUI.getChatGUI().scroll(this.mc.ingameGUI.getChatGUI().getLineCount() - 1);
            }
            else if (keyCode == 209) {
                this.mc.ingameGUI.getChatGUI().scroll(-this.mc.ingameGUI.getChatGUI().getLineCount() + 1);
            }
            else {
                this.inputField.textboxKeyTyped(typedChar, keyCode);
            }
        }
        else {
            final String s = this.inputField.getText().trim();
            this.sendChatMessage(s);
        }
    }
    
    public void sendChatMessage(final String msg) {
        if (this.CurrentCommand != null) {
            this.CurrentCommand.ProcessCommand(msg);
        }
    }
    
    public void setWorldAndResolution(final Minecraft mc, final int width, final int height) {
        this.mc = mc;
        this.itemRender = mc.getRenderItem();
        this.fontRenderer = mc.fontRenderer;
        this.width = width / 2;
        this.height = height / 2;
        if (!MinecraftForge.EVENT_BUS.post((Event)new GuiScreenEvent.InitGuiEvent.Pre((GuiScreen)this, this.buttonList))) {
            this.buttonList.clear();
            this.initGui();
        }
        MinecraftForge.EVENT_BUS.post((Event)new GuiScreenEvent.InitGuiEvent.Post((GuiScreen)this, this.buttonList));
    }
    
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        drawRect(this.width - 300, 0, this.width + 300, 17, Integer.MIN_VALUE);
        this.inputField.drawTextBox();
        final ITextComponent itextcomponent = this.mc.ingameGUI.getChatGUI().getChatComponent(Mouse.getX(), Mouse.getY());
        if (itextcomponent != null && itextcomponent.getStyle().getHoverEvent() != null) {
            this.handleComponentHover(itextcomponent, mouseX, mouseY);
        }
        final ArrayList<String> l_Commands = new ArrayList<String>();
        final String s = this.inputField.getText();
        if (s.isEmpty()) {
            drawRect(this.width - 300, 23, this.width + 300, 40, Integer.MIN_VALUE);
            RenderUtil.drawStringWithShadow(String.format("Type a %scommand%s to get help.", ChatFormatting.GREEN, ChatFormatting.RESET), (float)(this.width - 295), 26.0f, 16777215);
            return;
        }
        final String[] l_Split = s.split(" ");
        if (l_Split == null || l_Split.length == 0) {
            return;
        }
        final List<Command> l_CommandsLike = CommandManager.Get().GetCommandsLike(l_Split[0]);
        if (l_CommandsLike == null || l_CommandsLike.isEmpty() || l_CommandsLike.size() == 0) {
            drawRect(this.width - 300, 23, this.width + 300, 40, Integer.MIN_VALUE);
            RenderUtil.drawStringWithShadow("No commands found...", (float)(this.width - 295), 26.0f, 16711680);
            return;
        }
        final float l_Divider = 17.0f;
        this.CurrentCommand = l_CommandsLike.get(0);
        int l_RealItr = 0;
        for (int l_I = 0; l_I < l_CommandsLike.size(); ++l_I) {
            final Command l_Command = l_CommandsLike.get(l_I);
            int l_Color = 16777215;
            if (l_Command.GetName().equalsIgnoreCase(l_Split[0])) {
                for (final String l_Addon : l_Command.GetChunks()) {
                    final String[] l_AddonSplit = l_Addon.split(" ");
                    l_Color = 16777215;
                    String l_ToWrite = ChatFormatting.GREEN + l_Command.GetName() + ChatFormatting.RESET + " " + l_Addon;
                    if (l_AddonSplit != null && l_AddonSplit.length > 0 && l_Split.length > 1) {
                        if (!l_AddonSplit[0].toLowerCase().startsWith(l_Split[1].toLowerCase())) {
                            continue;
                        }
                        l_ToWrite = ChatFormatting.GREEN + l_Command.GetName() + " ";
                        for (int l_Y = 0; l_Y < l_AddonSplit.length; ++l_Y) {
                            if (l_Y == 0) {
                                l_ToWrite = l_ToWrite + ChatFormatting.GREEN + l_AddonSplit[l_Y] + ChatFormatting.RESET;
                            }
                            else {
                                l_ToWrite = l_ToWrite + " " + l_AddonSplit[l_Y];
                            }
                        }
                        drawRect(this.width - 300, 23 + (int)(l_RealItr * 17.0f), this.width + 300, 40 + (int)(l_RealItr * 17.0f), Integer.MIN_VALUE);
                        RenderUtil.drawStringWithShadow(l_ToWrite, (float)(this.width - 295), 26.0f + l_RealItr++ * 17.0f, l_Color);
                    }
                    else {
                        drawRect(this.width - 300, 23 + (int)(l_RealItr * 17.0f), this.width + 300, 40 + (int)(l_RealItr * 17.0f), Integer.MIN_VALUE);
                        RenderUtil.drawStringWithShadow(l_ToWrite, (float)(this.width - 295), 26.0f + l_RealItr++ * 17.0f, l_Color);
                    }
                }
                if (l_Split.length == 1) {
                    l_Color = 16772096;
                    drawRect(this.width - 300, 23 + (int)(l_RealItr * 17.0f), this.width + 300, 40 + (int)(l_RealItr * 17.0f), Integer.MIN_VALUE);
                    RenderUtil.drawStringWithShadow(ChatFormatting.GREEN + l_Command.GetName() + ChatFormatting.RESET + " " + l_Command.GetDescription(), (float)(this.width - 295), 26.0f + l_RealItr++ * 17.0f, l_Color);
                }
            }
            else {
                drawRect(this.width - 300, 23 + (int)(l_RealItr * 17.0f), this.width + 300, 40 + (int)(l_RealItr * 17.0f), Integer.MIN_VALUE);
                RenderUtil.drawStringWithShadow(l_Command.GetName(), (float)(this.width - 295), 26.0f + l_RealItr++ * 17.0f, l_Color);
            }
        }
        drawRect(2, this.height, this.width, this.height, Integer.MIN_VALUE);
    }
    
    public void onGuiClosed() {
        super.onGuiClosed();
        if (this.Console.isEnabled()) {
            this.Console.toggle();
        }
    }
}
