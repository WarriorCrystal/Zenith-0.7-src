//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.gui.hud;

import java.io.IOException;
import java.util.Iterator;
import me.gopro336.goprohack.util.render.RenderUtil;
import me.gopro336.goprohack.managers.HudManager;
import org.lwjgl.opengl.GL11;
import me.gopro336.goprohack.modules.ui.HudEditorModule;
import me.gopro336.goprohack.gui.GoproGuiScreen;

public class GuiHudEditor extends GoproGuiScreen
{
    private HudEditorModule HudEditor;
    private boolean Clicked;
    private boolean Dragging;
    private int ClickMouseX;
    private int ClickMouseY;
    
    public GuiHudEditor(final HudEditorModule p_HudEditor) {
        this.Clicked = false;
        this.Dragging = false;
        this.ClickMouseX = 0;
        this.ClickMouseY = 0;
        this.HudEditor = p_HudEditor;
    }
    
    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.drawDefaultBackground();
        GL11.glPushMatrix();
        HudComponentItem l_LastHovered = null;
        for (final HudComponentItem l_Item : HudManager.Get().Items) {
            if (!l_Item.IsHidden() && l_Item.Render(mouseX, mouseY, partialTicks)) {
                l_LastHovered = l_Item;
            }
        }
        if (l_LastHovered != null) {
            HudManager.Get().Items.remove(l_LastHovered);
            HudManager.Get().Items.add(l_LastHovered);
        }
        if (this.Clicked) {
            final float l_MouseX1 = (float)Math.min(this.ClickMouseX, mouseX);
            final float l_MouseX2 = (float)Math.max(this.ClickMouseX, mouseX);
            final float l_MouseY1 = (float)Math.min(this.ClickMouseY, mouseY);
            final float l_MouseY2 = (float)Math.max(this.ClickMouseY, mouseY);
            RenderUtil.drawOutlineRect(l_MouseX2, l_MouseY2, l_MouseX1, l_MouseY1, 1.0f, 1963290310);
            RenderUtil.drawRect(l_MouseX1, l_MouseY1, l_MouseX2, l_MouseY2, 356038, 205.0f);
            final float p_MouseX1;
            final float p_MouseX2;
            final float p_MouseY1;
            final float p_MouseY2;
            HudManager.Get().Items.forEach(p_Item -> {
                if (!p_Item.IsHidden()) {
                    if (p_Item.IsInArea(p_MouseX1, p_MouseX2, p_MouseY1, p_MouseY2)) {
                        p_Item.SetSelected(true);
                    }
                    else if (p_Item.IsSelected()) {
                        p_Item.SetSelected(false);
                    }
                }
                return;
            });
        }
        GL11.glPopMatrix();
    }
    
    @Override
    public void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        for (final HudComponentItem l_Item : HudManager.Get().Items) {
            if (!l_Item.IsHidden() && l_Item.OnMouseClick(mouseX, mouseY, mouseButton)) {
                return;
            }
        }
        this.Clicked = true;
        this.ClickMouseX = mouseX;
        this.ClickMouseY = mouseY;
    }
    
    @Override
    public void mouseReleased(final int mouseX, final int mouseY, final int state) {
        super.mouseReleased(mouseX, mouseY, state);
        HudManager.Get().Items.forEach(p_Item -> {
            if (!p_Item.IsHidden()) {
                p_Item.OnMouseRelease(mouseX, mouseY, state);
                if (p_Item.IsSelected()) {
                    p_Item.SetMultiSelectedDragging(true);
                }
                else {
                    p_Item.SetMultiSelectedDragging(false);
                }
            }
            return;
        });
        this.Clicked = false;
    }
    
    @Override
    public void mouseClickMove(final int mouseX, final int mouseY, final int clickedMouseButton, final long timeSinceLastClick) {
        super.mouseClickMove(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
    }
    
    public void onGuiClosed() {
        super.onGuiClosed();
        if (this.HudEditor.isEnabled()) {
            this.HudEditor.toggle();
        }
        this.Clicked = false;
        this.Dragging = false;
        this.ClickMouseX = 0;
        this.ClickMouseY = 0;
    }
}
