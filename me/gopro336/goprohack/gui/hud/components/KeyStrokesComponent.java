//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.gui.hud.components;

import me.gopro336.goprohack.util.imgs.GoproDynamicTexture;
import me.gopro336.goprohack.managers.FontManager;
import me.gopro336.goprohack.util.render.AbstractGui;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.GlStateManager;
import me.gopro336.goprohack.managers.ImageManager;
import me.gopro336.goprohack.util.render.RenderUtil;
import me.gopro336.goprohack.util.Timer;
import net.minecraft.client.Minecraft;
import java.util.ArrayList;
import me.gopro336.goprohack.modules.Value;
import me.gopro336.goprohack.gui.hud.HudComponentItem;

public class KeyStrokesComponent extends HudComponentItem
{
    public final Value<Float> Red;
    public final Value<Float> Green;
    public final Value<Float> Blue;
    public final Value<Float> Alpha;
    private ArrayList<Button> Buttons;
    
    public KeyStrokesComponent() {
        super("KeyStrokes", 2.0f, 300.0f);
        this.Red = new Value<Float>("Red", new String[] { "bRed" }, "Red for rendering", 1.0f, 0.0f, 1.0f, 0.1f);
        this.Green = new Value<Float>("Green", new String[] { "bGreen" }, "Green for rendering", 1.0f, 0.0f, 1.0f, 0.1f);
        this.Blue = new Value<Float>("Blue", new String[] { "bBlue" }, "Blue for rendering", 1.0f, 0.0f, 1.0f, 0.1f);
        this.Alpha = new Value<Float>("Alpha", new String[] { "bAlpha" }, "Alpha for rendering", 1.0f, 0.0f, 1.0f, 0.1f);
        (this.Buttons = new ArrayList<Button>()).add(new Button("W", 35.0f, 35.0f));
        this.Buttons.add(new Button("S", 35.0f, 35.0f));
        this.Buttons.add(new Button("A", 35.0f, 35.0f));
        this.Buttons.add(new Button("D", 35.0f, 35.0f));
        this.Buttons.add(new Button("SPACE", 115.0f, 35.0f));
    }
    
    @Override
    public void render(final int mouseX, final int mouseY, final float partialTicks) {
        super.render(mouseX, mouseY, partialTicks);
        final boolean l_Forward = this.mc.gameSettings.keyBindForward.isKeyDown();
        final boolean l_Left = this.mc.gameSettings.keyBindLeft.isKeyDown();
        final boolean l_Right = this.mc.gameSettings.keyBindRight.isKeyDown();
        final boolean l_Back = this.mc.gameSettings.keyBindBack.isKeyDown();
        final boolean l_Jump = this.mc.gameSettings.keyBindJump.isKeyDown();
        if (l_Forward) {
            this.Buttons.get(0).OnPress();
        }
        if (l_Back) {
            this.Buttons.get(1).OnPress();
        }
        if (l_Left) {
            this.Buttons.get(2).OnPress();
        }
        if (l_Right) {
            this.Buttons.get(3).OnPress();
        }
        if (l_Jump) {
            this.Buttons.get(4).OnPress();
        }
        this.Buttons.get(0).Display(this.GetX() + 40.0f, this.GetY());
        this.Buttons.get(2).Display(this.GetX(), this.GetY() + 40.0f);
        this.Buttons.get(1).Display(this.GetX() + 40.0f, this.GetY() + 40.0f);
        this.Buttons.get(3).Display(this.GetX() + 80.0f, this.GetY() + 40.0f);
        this.Buttons.get(4).Display(this.GetX(), this.GetY() + 80.0f);
        this.SetWidth(120.0f);
        this.SetHeight(120.0f);
    }
    
    public class Button
    {
        private String Name;
        public float Width;
        public float Height;
        public final Timer timer;
        public final Timer pressedTimer;
        public float RemainingAnimationW;
        public float RemainingAnimationH;
        boolean Pressed;
        
        public Button(final String p_Name, final float p_Width, final float p_Height) {
            this.timer = new Timer();
            this.pressedTimer = new Timer();
            this.RemainingAnimationW = 0.0f;
            this.RemainingAnimationH = 0.0f;
            this.Pressed = false;
            this.Name = p_Name;
            this.Width = p_Width;
            this.Height = p_Height;
        }
        
        public void Display(final float p_X, final float p_Y) {
            if (this.pressedTimer.passed(50.0)) {
                this.pressedTimer.reset();
                this.Pressed = false;
            }
            if (this.timer.passed(1.0)) {
                this.timer.reset();
                final float remainingAnimationW = this.RemainingAnimationW + 1.0f;
                this.RemainingAnimationW = remainingAnimationW;
                if (remainingAnimationW >= this.Width) {}
                this.RemainingAnimationW = this.Width;
                final float remainingAnimationH = this.RemainingAnimationH + 1.0f;
                this.RemainingAnimationH = remainingAnimationH;
                if (remainingAnimationH >= this.Height) {}
                this.RemainingAnimationH = this.Height;
            }
            RenderUtil.drawRect(p_X, p_Y, p_X + this.Width, p_Y + this.Height, 1963986960);
            if (this.Pressed) {
                final GoproDynamicTexture l_Texture = ImageManager.Get().GetDynamicTexture("OutlinedEllipse");
                KeyStrokesComponent.this.mc.renderEngine.bindTexture(l_Texture.GetResourceLocation());
                GlStateManager.pushMatrix();
                RenderHelper.enableGUIStandardItemLighting();
                GL11.glColor4f((float)KeyStrokesComponent.this.Red.getValue(), (float)KeyStrokesComponent.this.Green.getValue(), (float)KeyStrokesComponent.this.Blue.getValue(), (float)KeyStrokesComponent.this.Alpha.getValue());
                GlStateManager.enableBlend();
                GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
                GlStateManager.glTexParameteri(3553, 10240, 9729);
                GlStateManager.glTexParameteri(3553, 10241, 9729);
                AbstractGui.blit((int)p_X, (int)p_Y, (int)this.Width, (int)this.Height, 0.0f, 0.0f, (int)this.Width, (int)this.Height, 1, 1);
                GlStateManager.disableBlend();
                RenderHelper.disableStandardItemLighting();
                GlStateManager.popMatrix();
            }
            FontManager.Get().GetFontBySize(24).drawCenteredString(this.Name, p_X + this.Width / 2.0f, p_Y + this.Height / 2.0f - 5.0f, 16777215);
        }
        
        public void OnPress() {
            this.pressedTimer.resetTimeSkipTo(30L);
            this.timer.resetTimeSkipTo(30L);
            this.Pressed = true;
            this.RemainingAnimationW = 0.0f;
            this.RemainingAnimationH = 0.0f;
        }
    }
}
