//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package me.gopro336.goprohack.modules.dupe;

import net.minecraft.client.Minecraft;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketInput;
import java.util.Comparator;
import net.minecraft.block.state.IBlockState;
import me.gopro336.goprohack.managers.TickRateManager;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumFacing;
import net.minecraft.init.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.RayTraceResult;
import me.gopro336.goprohack.managers.ModuleManager;
import java.util.Iterator;
import net.minecraft.client.gui.GuiScreen;
import java.util.TimerTask;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.item.ItemShulkerBox;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.ItemStack;
import me.gopro336.goprohack.main.GoproHack;
import net.minecraft.entity.passive.AbstractChestHorse;
import net.minecraft.network.play.server.SPacketWindowItems;
import java.util.function.Predicate;
import me.gopro336.goprohack.events.network.EventNetworkPacketEvent;
import me.zero.alpine.fork.listener.EventHandler;
import me.gopro336.goprohack.events.player.EventPlayerUpdate;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.util.math.BlockPos;
import net.minecraft.entity.Entity;
import java.util.Timer;
import me.gopro336.goprohack.modules.exploit.EntityDesyncModule;
import me.gopro336.goprohack.modules.exploit.PacketCancellerModule;
import net.minecraft.util.math.Vec3d;
import me.gopro336.goprohack.modules.Value;
import me.gopro336.goprohack.modules.Module;

public final class DupeBotModule extends Module
{
    public final Value<Boolean> TPSScaling;
    public final Value<Integer> PacketToggleDelay;
    public final Value<Integer> DupeDelay;
    public final Value<Integer> RemountDelay;
    public final Value<Integer> RestartTimer;
    public final Value<Boolean> BypassMode;
    public final Value<Boolean> UseEntityDesync;
    public final Value<Integer> EntityDesyncDelay;
    public final Value<Integer> DupeRemountDelay;
    public final Value<Integer> InventoryDelay;
    public final Value<Boolean> LockOriginalToStart;
    public final Value<Boolean> IgnorePosUpdate;
    public final Value<Integer> BypassRemount;
    private Vec3d StartPos;
    private float Pitch;
    private float Yaw;
    private DupeFreecam Freecam;
    private PacketCancellerModule PacketCanceller;
    private EntityDesyncModule EntityDesync;
    private Timer timer;
    private Entity riding;
    private BlockPos button;
    private int ShulkersDuped;
    private Vec3d StartingPosition;
    private boolean RestartDupeNoInv;
    private boolean SetIgnoreStartClip;
    private int StreakCounter;
    private me.gopro336.goprohack.util.Timer packetTimer;
    @EventHandler
    private Listener<EventPlayerUpdate> OnPlayerUpdate;
    @EventHandler
    private Listener<EventNetworkPacketEvent> PacketEvent;
    
    public DupeBotModule() {
        super("DupeBot", new String[] { "Duper", "Autodonkeydupe" }, "Automaticly performs the wwe donkey dupe", "NONE", -1, ModuleType.DUPE);
        this.TPSScaling = new Value<Boolean>("TpsScaling", new String[] { "TPSScaling" }, "Use TPS scaling on base timers", true);
        this.PacketToggleDelay = new Value<Integer>("PacketToggleDelay", new String[] { "PacketToggleDelay" }, "Delay for packet toggling", 12000, 0, 30000, 1000);
        this.DupeDelay = new Value<Integer>("DupeDelay", new String[] { "DupeDelay" }, "Time for DupeDelay (TP back to starting point)", 24000, 0, 30000, 1000);
        this.RemountDelay = new Value<Integer>("RemountDelay", new String[] { "RemountDelay" }, "Time for remounting after throwing items", 1000, 0, 10000, 1000);
        this.RestartTimer = new Value<Integer>("RestartTimer", new String[] { "RestartTimer" }, "Time for restarting after remounting", 1000, 0, 10000, 1000);
        this.BypassMode = new Value<Boolean>("BypassMode", new String[] { "BypassMode" }, "BypassMode for 2b2t on a patch day", false);
        this.UseEntityDesync = new Value<Boolean>("EntityDesync", new String[] { "EntityDesync" }, "use EntityDesync", true);
        this.EntityDesyncDelay = new Value<Integer>("EntityDesyncDelay", new String[] { "EntityDesyncDelay" }, "edsync delay", 300, 0, 10000, 1000);
        this.DupeRemountDelay = new Value<Integer>("DupeRemountDelay", new String[] { "DupeRemountDelay" }, "DupeRemountDelay", 300, 0, 10000, 1000);
        this.InventoryDelay = new Value<Integer>("InventoryDelay", new String[] { "InventoryDelay" }, "InventoryDelay", 1000, 0, 10000, 1000);
        this.LockOriginalToStart = new Value<Boolean>("LockOriginalToStart", new String[] { "Lock" }, "Lock", false);
        this.IgnorePosUpdate = new Value<Boolean>("IgnorePosUpdate", new String[] { "IgnorePosUpdate" }, "IgnorePosUpdate", false);
        this.BypassRemount = new Value<Integer>("BypassRemount", new String[] { "" }, "BypassRemount", 18000, 0, 18000, 1000);
        this.StartPos = Vec3d.ZERO;
        this.ShulkersDuped = 0;
        this.StartingPosition = Vec3d.ZERO;
        this.RestartDupeNoInv = false;
        this.SetIgnoreStartClip = false;
        this.StreakCounter = 0;
        this.packetTimer = new me.gopro336.goprohack.util.Timer();
        final float seconds;
        this.OnPlayerUpdate = new Listener<EventPlayerUpdate>(p_Event -> {
            seconds = (System.currentTimeMillis() - this.packetTimer.getTime()) / 1000.0f % 60.0f;
            if (!this.isEnabled()) {
                return;
            }
            else if (this.StartPos == Vec3d.ZERO) {
                return;
            }
            else {
                if (!this.SetIgnoreStartClip && !this.IgnorePosUpdate.getValue()) {
                    this.mc.player.setPosition(this.StartPos.x, this.StartPos.y, this.StartPos.z);
                }
                this.mc.player.rotationPitch = this.Pitch;
                this.mc.player.rotationYaw = this.Yaw;
                return;
            }
        }, (Predicate<EventPlayerUpdate>[])new Predicate[0]);
        AbstractChestHorse l_Donkey;
        SPacketWindowItems l_Packet;
        int l_I;
        final Iterator<ItemStack> iterator;
        ItemStack l_Stack;
        this.PacketEvent = new Listener<EventNetworkPacketEvent>(p_Event -> {
            if (p_Event.getPacket() != null) {
                this.packetTimer.reset();
            }
            if (p_Event.getPacket() instanceof SPacketWindowItems) {
                if (this.mc.player.isRiding() && this.mc.player.getRidingEntity() instanceof AbstractChestHorse) {
                    l_Donkey = this.GetNearDonkey();
                    this.RestartDupeNoInv = false;
                    if (l_Donkey == null) {
                        GoproHack.SendMessage("Could not find the donkey near you");
                        this.mc.player.closeScreen();
                        this.HandleDupe();
                    }
                    else {
                        GoproHack.SendMessage("Dumping items from " + this.mc.player.getRidingEntity().getName());
                        l_Packet = (SPacketWindowItems)p_Event.getPacket();
                        l_I = 0;
                        l_Packet.getItemStacks().iterator();
                        while (iterator.hasNext()) {
                            l_Stack = iterator.next();
                            if (l_I > 1 && l_I < 17) {
                                this.mc.playerController.windowClick(l_Packet.getWindowId(), l_I, 1, ClickType.THROW, (EntityPlayer)this.mc.player);
                                if (l_Stack.getItem() instanceof ItemShulkerBox) {
                                    ++this.ShulkersDuped;
                                }
                            }
                            ++l_I;
                        }
                        GoproHack.SendMessage(ChatFormatting.GREEN + "Done dumping items from " + this.mc.player.getRidingEntity().getName() + "!");
                        ++this.StreakCounter;
                        this.timer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                DupeBotModule.this.mc.player.closeScreen();
                                DupeBotModule.this.mc.displayGuiScreen((GuiScreen)null);
                                DupeBotModule.this.Remount();
                            }
                        }, this.RemountDelay.getValue());
                    }
                }
            }
        }, (Predicate<EventNetworkPacketEvent>[])new Predicate[0]);
    }
    
    @Override
    public String getMetaData() {
        return "" + this.ShulkersDuped + ChatFormatting.GOLD + " Streak: " + this.StreakCounter;
    }
    
    public void ToggleOffMods() {
        if (this.Freecam != null && this.Freecam.isEnabled()) {
            this.Freecam.toggle();
        }
        if (this.PacketCanceller != null && this.PacketCanceller.isEnabled()) {
            this.PacketCanceller.toggle();
        }
        if (this.EntityDesync != null && this.EntityDesync.isEnabled()) {
            this.EntityDesync.toggle();
        }
    }
    
    @Override
    public void onEnable() {
        super.onEnable();
        if (this.mc.player == null) {
            this.toggle();
            return;
        }
        if (this.mc.player.getRidingEntity() == null) {
            GoproHack.SendMessage(ChatFormatting.RED + "You are not riding an entity!");
            this.toggle();
            return;
        }
        this.StartingPosition = new Vec3d(this.mc.player.getRidingEntity().posX, this.mc.player.getRidingEntity().posY, this.mc.player.getRidingEntity().posZ);
        this.StartPos = new Vec3d(this.mc.player.posX, this.mc.player.posY, this.mc.player.posZ);
        this.Pitch = this.mc.player.rotationPitch;
        this.Yaw = this.mc.player.rotationYaw;
        this.Freecam = (DupeFreecam)ModuleManager.Get().GetMod(DupeFreecam.class);
        this.PacketCanceller = (PacketCancellerModule)ModuleManager.Get().GetMod(PacketCancellerModule.class);
        this.EntityDesync = (EntityDesyncModule)ModuleManager.Get().GetMod(EntityDesyncModule.class);
        this.ToggleOffMods();
        this.button = null;
        this.SetIgnoreStartClip = false;
        GoproHack.SendMessage("Last Streak counter was " + this.StreakCounter);
        this.StreakCounter = 0;
        this.HandleDupe();
    }
    
    public int CalculateNewTime(final int p_BaseTime, final float p_Tps) {
        if (this.TPSScaling.getValue()) {
            return (int)(p_BaseTime * (20.0f / p_Tps));
        }
        return p_BaseTime;
    }
    
    public void HandleDupe() {
        GoproHack.SendMessage(ChatFormatting.LIGHT_PURPLE + "Starting dupe!");
        if (this.timer != null) {
            this.timer.cancel();
        }
        this.timer = new Timer();
        if (this.LockOriginalToStart.getValue()) {
            this.mc.player.getRidingEntity().setPosition(this.StartingPosition.x, this.StartingPosition.y, this.StartingPosition.z);
        }
        final RayTraceResult ray = this.mc.objectMouseOver;
        if (ray != null && ray.typeOfHit == RayTraceResult.Type.BLOCK && this.button == null) {
            final BlockPos pos = ray.getBlockPos();
            final IBlockState iblockstate = this.mc.world.getBlockState(pos);
            if (iblockstate.getMaterial() != Material.AIR && this.mc.world.getWorldBorder().contains(pos) && (iblockstate.getBlock() == Blocks.WOODEN_BUTTON || iblockstate.getBlock() == Blocks.STONE_BUTTON)) {
                this.button = pos;
            }
        }
        if (this.button == null) {
            GoproHack.SendMessage("Button is null!");
            return;
        }
        if (this.StartPos != Vec3d.ZERO) {
            this.mc.player.setPosition(this.StartPos.x, this.StartPos.y, this.StartPos.z);
        }
        this.mc.playerController.processRightClickBlock(this.mc.player, this.mc.world, this.button, EnumFacing.UP, new Vec3d(0.0, 0.0, 0.0), EnumHand.MAIN_HAND);
        GoproHack.SendMessage("Rightclicked!");
        final float l_Tps = TickRateManager.Get().getTickRate();
        GoproHack.SendMessage("Tps: " + l_Tps);
        this.riding = this.mc.player.getRidingEntity();
        this.timer.schedule(new TimerTask() {
            @Override
            public void run() {
                DupeBotModule.this.Freecam.toggle();
                DupeBotModule.this.PacketCanceller.toggle();
                GoproHack.SendMessage("Toggled the hax!");
            }
        }, 100L);
        this.timer.schedule(new TimerTask() {
            @Override
            public void run() {
                DupeBotModule.this.PacketCanceller.toggle();
                GoproHack.SendMessage("Moving the donkey!");
                DupeBotModule.this.timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        DupeBotModule.this.PacketCanceller.toggle();
                        GoproHack.SendMessage("Not moving the donkey!");
                    }
                }, DupeBotModule.this.CalculateNewTime(1000, l_Tps));
            }
        }, this.CalculateNewTime(this.PacketToggleDelay.getValue(), l_Tps));
        this.timer.schedule(new TimerTask() {
            @Override
            public void run() {
                DupeBotModule.this.Freecam.toggle();
                if (DupeBotModule.this.BypassMode.getValue()) {
                    DupeBotModule.this.timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            if (!DupeBotModule.this.BypassMode.getValue() && DupeBotModule.this.UseEntityDesync.getValue()) {
                                DupeBotModule.this.PacketCanceller.toggle();
                                DupeBotModule.this.timer.schedule(new TimerTask() {
                                    @Override
                                    public void run() {
                                        DupeBotModule.this.EntityDesync.toggle();
                                        GoproHack.SendMessage("EntityDesync - ON");
                                        DupeBotModule.this.timer.schedule(new TimerTask() {
                                            @Override
                                            public void run() {
                                                DupeBotModule.this.EntityDesync.toggle();
                                                GoproHack.SendMessage("EntityDesync - OFF");
                                            }
                                        }, DupeBotModule.this.CalculateNewTime(100, l_Tps));
                                    }
                                }, DupeBotModule.this.CalculateNewTime(DupeBotModule.this.EntityDesyncDelay.getValue(), l_Tps));
                                DupeBotModule.this.timer.schedule(new TimerTask() {
                                    @Override
                                    public void run() {
                                        if (DupeBotModule.this.Freecam.isEnabled()) {
                                            DupeBotModule.this.Freecam.toggle();
                                        }
                                        if (DupeBotModule.this.PacketCanceller.isEnabled()) {
                                            DupeBotModule.this.PacketCanceller.toggle();
                                        }
                                        DupeBotModule.this.timer.schedule(new TimerTask() {
                                            @Override
                                            public void run() {
                                                DupeBotModule.this.RestartDupeNoInv = true;
                                                DupeBotModule.this.mc.player.sendHorseInventory();
                                                DupeBotModule.this.timer.schedule(new TimerTask() {
                                                    @Override
                                                    public void run() {
                                                        if (DupeBotModule.this.RestartDupeNoInv) {
                                                            GoproHack.SendMessage("Detected Ghost Donkey, retrying");
                                                            DupeBotModule.this.riding = null;
                                                            DupeBotModule.this.Remount();
                                                        }
                                                    }
                                                }, 2000L);
                                            }
                                        }, DupeBotModule.this.InventoryDelay.getValue());
                                    }
                                }, DupeBotModule.this.CalculateNewTime(DupeBotModule.this.DupeRemountDelay.getValue() + DupeBotModule.this.EntityDesyncDelay.getValue() + 1000, l_Tps));
                            }
                            else if (DupeBotModule.this.BypassMode.getValue()) {
                                DupeBotModule.this.timer.schedule(new TimerTask() {
                                    @Override
                                    public void run() {
                                        DupeBotModule.this.PacketCanceller.toggle();
                                    }
                                }, 500L);
                                DupeBotModule.this.timer.schedule(new TimerTask() {
                                    @Override
                                    public void run() {
                                        DupeBotModule.this.PacketCanceller.toggle();
                                    }
                                }, 600L);
                                DupeBotModule.this.timer.schedule(new TimerTask() {
                                    @Override
                                    public void run() {
                                        GoproHack.SendMessage("Bypass done");
                                        if (DupeBotModule.this.PacketCanceller.isEnabled()) {
                                            DupeBotModule.this.PacketCanceller.toggle();
                                        }
                                        DupeBotModule.this.SetIgnoreStartClip = false;
                                        if (DupeBotModule.this.UseEntityDesync.getValue()) {
                                            DupeBotModule.this.timer.schedule(new TimerTask() {
                                                @Override
                                                public void run() {
                                                    DupeBotModule.this.EntityDesync.toggle();
                                                    GoproHack.SendMessage("EntityDesync - ON");
                                                    DupeBotModule.this.timer.schedule(new TimerTask() {
                                                        @Override
                                                        public void run() {
                                                            DupeBotModule.this.EntityDesync.toggle();
                                                            GoproHack.SendMessage("EntityDesync - OFF");
                                                        }
                                                    }, DupeBotModule.this.CalculateNewTime(100, l_Tps));
                                                }
                                            }, DupeBotModule.this.CalculateNewTime(DupeBotModule.this.EntityDesyncDelay.getValue(), l_Tps));
                                        }
                                        DupeBotModule.this.timer.schedule(new TimerTask() {
                                            @Override
                                            public void run() {
                                                if (DupeBotModule.this.StartPos != Vec3d.ZERO) {
                                                    DupeBotModule.this.mc.player.setPosition(DupeBotModule.this.StartPos.x, DupeBotModule.this.StartPos.y, DupeBotModule.this.StartPos.z);
                                                }
                                                if (DupeBotModule.this.Freecam.isEnabled()) {
                                                    DupeBotModule.this.Freecam.toggle();
                                                }
                                                if (DupeBotModule.this.PacketCanceller.isEnabled()) {
                                                    DupeBotModule.this.PacketCanceller.toggle();
                                                }
                                                DupeBotModule.this.timer.schedule(new TimerTask() {
                                                    @Override
                                                    public void run() {
                                                        DupeBotModule.this.RestartDupeNoInv = true;
                                                        DupeBotModule.this.mc.player.sendHorseInventory();
                                                        GoproHack.SendMessage("Sending inventory.");
                                                        DupeBotModule.this.timer.schedule(new TimerTask() {
                                                            @Override
                                                            public void run() {
                                                                if (DupeBotModule.this.RestartDupeNoInv) {
                                                                    GoproHack.SendMessage("Detected Ghost Donkey, retrying");
                                                                    DupeBotModule.this.riding = null;
                                                                    DupeBotModule.this.Remount();
                                                                }
                                                            }
                                                        }, 2000L);
                                                    }
                                                }, DupeBotModule.this.InventoryDelay.getValue());
                                            }
                                        }, DupeBotModule.this.CalculateNewTime(DupeBotModule.this.DupeRemountDelay.getValue() + DupeBotModule.this.EntityDesyncDelay.getValue(), l_Tps));
                                    }
                                }, DupeBotModule.this.BypassRemount.getValue());
                            }
                        }
                    }, DupeBotModule.this.DupeRemountDelay.getValue());
                }
                else {
                    DupeBotModule.this.PacketCanceller.toggle();
                    if (DupeBotModule.this.StartPos != Vec3d.ZERO) {
                        DupeBotModule.this.mc.player.setPosition(DupeBotModule.this.StartPos.x, DupeBotModule.this.StartPos.y, DupeBotModule.this.StartPos.z);
                    }
                    if (DupeBotModule.this.Freecam.isEnabled()) {
                        DupeBotModule.this.Freecam.toggle();
                    }
                    if (DupeBotModule.this.PacketCanceller.isEnabled()) {
                        DupeBotModule.this.PacketCanceller.toggle();
                    }
                    DupeBotModule.this.timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            DupeBotModule.this.RestartDupeNoInv = true;
                            DupeBotModule.this.mc.player.sendHorseInventory();
                            GoproHack.SendMessage("Sending inventory.");
                            DupeBotModule.this.timer.schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    if (DupeBotModule.this.RestartDupeNoInv) {
                                        GoproHack.SendMessage("Detected Ghost Donkey, retrying");
                                        DupeBotModule.this.riding = null;
                                        DupeBotModule.this.Remount();
                                    }
                                }
                            }, 2000L);
                        }
                    }, DupeBotModule.this.InventoryDelay.getValue());
                }
            }
        }, this.CalculateNewTime(this.DupeDelay.getValue(), l_Tps));
    }
    
    @Override
    public void onDisable() {
        if (this.timer != null) {
            this.timer.cancel();
        }
        this.timer = null;
        this.StartPos = Vec3d.ZERO;
        this.ToggleOffMods();
    }
    
    public AbstractChestHorse GetNearDonkey() {
        final int l_EntityId = (this.riding != null) ? this.riding.getEntityId() : 0;
        final int n;
        final AbstractChestHorse l_Donkey = (AbstractChestHorse)this.mc.world.loadedEntityList.stream().filter(entity -> entity instanceof AbstractChestHorse && entity != this.riding && this.mc.player.getDistance(entity) < 10.0f && entity.getEntityId() != n).map(entity -> entity).min(Comparator.comparing(c -> this.mc.player.getDistance(c))).orElse(null);
        return l_Donkey;
    }
    
    public void Remount() {
        final AbstractChestHorse l_Donkey = this.GetNearDonkey();
        if (l_Donkey != null) {
            GoproHack.SendMessage(ChatFormatting.GREEN + "Processing remount on " + l_Donkey.getName());
            this.riding = null;
            this.mc.player.connection.sendPacket((Packet)new CPacketInput(this.mc.player.moveStrafing, this.mc.player.moveForward, this.mc.player.movementInput.jump, true));
            this.timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    DupeBotModule.this.mc.playerController.interactWithEntity((EntityPlayer)DupeBotModule.this.mc.player, (Entity)l_Donkey, EnumHand.MAIN_HAND);
                }
            }, 111L);
            this.timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    GoproHack.SendMessage("Restarting dupe!");
                    DupeBotModule.this.HandleDupe();
                }
            }, this.RestartTimer.getValue() + 111);
        }
    }
}
