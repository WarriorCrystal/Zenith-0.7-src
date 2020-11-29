//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.main;

import net.minecraftforge.event.world.WorldEvent;
import me.gopro336.goprohack.events.render.EventRenderGetFOVModifier;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.RenderBlockOverlayEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.client.event.PlayerSPPushOutOfBlocksEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.client.event.InputUpdateEvent;
import net.minecraftforge.event.world.ChunkEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import me.gopro336.goprohack.managers.ModuleManager;
import org.lwjgl.input.Keyboard;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import me.gopro336.goprohack.events.client.EventClientTick;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import me.gopro336.goprohack.events.render.RenderEvent;
import me.gopro336.goprohack.GoproHackMod;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.client.event.RenderWorldLastEvent;

public class ForgeEventProcessor
{
    @SubscribeEvent
    public void onRender(final RenderWorldLastEvent event) {
        if (event.isCanceled()) {
            return;
        }
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.shadeModel(7425);
        GlStateManager.disableDepth();
        GlStateManager.glLineWidth(1.0f);
        GoproHackMod.EVENT_BUS.post(new RenderEvent(event.getPartialTicks()));
        GlStateManager.glLineWidth(1.0f);
        GlStateManager.shadeModel(7424);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
        GlStateManager.enableDepth();
        GlStateManager.enableCull();
    }
    
    @SubscribeEvent
    public void onTick(final TickEvent.ClientTickEvent event) {
        if (Wrapper.GetMC().player == null) {
            return;
        }
        GoproHackMod.EVENT_BUS.post(new EventClientTick());
    }
    
    @SubscribeEvent
    public void onEntitySpawn(final EntityJoinWorldEvent event) {
        if (event.isCanceled()) {
            return;
        }
        GoproHackMod.EVENT_BUS.post(event);
    }
    
    @SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
    public void onKeyInput(final InputEvent.KeyInputEvent event) {
        if (Keyboard.getEventKeyState()) {
            ModuleManager.Get().OnKeyPress(Keyboard.getKeyName(Keyboard.getEventKey()));
        }
    }
    
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onPlayerDrawn(final RenderPlayerEvent.Pre event) {
        GoproHackMod.EVENT_BUS.post(event);
    }
    
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onPlayerDrawn(final RenderPlayerEvent.Post event) {
        GoproHackMod.EVENT_BUS.post(event);
    }
    
    @SubscribeEvent
    public void onChunkLoaded(final ChunkEvent.Load event) {
        GoproHackMod.EVENT_BUS.post(event);
    }
    
    @SubscribeEvent
    public void onEventMouse(final InputEvent.MouseInputEvent event) {
        GoproHackMod.EVENT_BUS.post(event);
    }
    
    @SubscribeEvent
    public void onChunkUnLoaded(final ChunkEvent.Unload event) {
        GoproHackMod.EVENT_BUS.post(event);
    }
    
    @SubscribeEvent
    public void onInputUpdate(final InputUpdateEvent event) {
        GoproHackMod.EVENT_BUS.post(event);
    }
    
    @SubscribeEvent
    public void onLivingEntityUseItemEventTick(final LivingEntityUseItemEvent.Start entityUseItemEvent) {
        GoproHackMod.EVENT_BUS.post(entityUseItemEvent);
    }
    
    @SubscribeEvent
    public void onLivingDamageEvent(final LivingDamageEvent event) {
        GoproHackMod.EVENT_BUS.post(event);
    }
    
    @SubscribeEvent
    public void onEntityJoinWorldEvent(final EntityJoinWorldEvent entityJoinWorldEvent) {
        GoproHackMod.EVENT_BUS.post(entityJoinWorldEvent);
    }
    
    @SubscribeEvent
    public void onPlayerPush(final PlayerSPPushOutOfBlocksEvent event) {
        GoproHackMod.EVENT_BUS.post(event);
    }
    
    @SubscribeEvent
    public void onLeftClickBlock(final PlayerInteractEvent.LeftClickBlock event) {
        GoproHackMod.EVENT_BUS.post(event);
    }
    
    @SubscribeEvent
    public void onAttackEntity(final AttackEntityEvent entityEvent) {
        GoproHackMod.EVENT_BUS.post(entityEvent);
    }
    
    @SubscribeEvent
    public void onRenderBlockOverlay(final RenderBlockOverlayEvent event) {
        GoproHackMod.EVENT_BUS.post(event);
    }
    
    @SubscribeEvent
    public void onClientChat(final ClientChatReceivedEvent event) {
        GoproHackMod.EVENT_BUS.post(event);
    }
    
    @SubscribeEvent
    public void getFOVModifier(final EntityViewRenderEvent.FOVModifier p_Event) {
        final EventRenderGetFOVModifier l_Event = new EventRenderGetFOVModifier((float)p_Event.getRenderPartialTicks(), true);
        GoproHackMod.EVENT_BUS.post(l_Event);
        if (l_Event.isCancelled()) {
            p_Event.setFOV(l_Event.GetFOV());
        }
    }
    
    @SubscribeEvent
    public void OnWorldChange(final WorldEvent p_Event) {
        GoproHackMod.EVENT_BUS.post(p_Event);
    }
}
