// 
// Decompiled by Procyon v0.5.36
// 

package baritone.api;

import baritone.api.command.manager.ICommandManager;
import baritone.api.selection.ISelectionManager;
import baritone.api.event.listener.IEventBus;
import baritone.api.utils.IPlayerContext;
import baritone.api.utils.IInputOverrideHandler;
import baritone.api.pathing.calc.IPathingControlManager;
import baritone.api.cache.IWorldProvider;
import baritone.api.process.IGetToBlockProcess;
import baritone.api.process.ICustomGoalProcess;
import baritone.api.process.IFarmProcess;
import baritone.api.process.IExploreProcess;
import baritone.api.process.IBuilderProcess;
import baritone.api.process.IMineProcess;
import baritone.api.process.IFollowProcess;
import baritone.api.behavior.ILookBehavior;
import baritone.api.behavior.IPathingBehavior;

public interface IBaritone
{
    IPathingBehavior getPathingBehavior();
    
    ILookBehavior getLookBehavior();
    
    IFollowProcess getFollowProcess();
    
    IMineProcess getMineProcess();
    
    IBuilderProcess getBuilderProcess();
    
    IExploreProcess getExploreProcess();
    
    IFarmProcess getFarmProcess();
    
    ICustomGoalProcess getCustomGoalProcess();
    
    IGetToBlockProcess getGetToBlockProcess();
    
    IWorldProvider getWorldProvider();
    
    IPathingControlManager getPathingControlManager();
    
    IInputOverrideHandler getInputOverrideHandler();
    
    IPlayerContext getPlayerContext();
    
    IEventBus getGameEventHandler();
    
    ISelectionManager getSelectionManager();
    
    ICommandManager getCommandManager();
    
    void openClick();
}
