package me.songt.snake2d;

import me.songt.snake2d.state.MainGameState;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Created by tony on 2017/3/9.
 * Main Game Class
 */
public class MainGame extends StateBasedGame
{
    public MainGame(String name)
    {
        super(name);
    }

    @Override
    public void initStatesList(GameContainer gameContainer) throws SlickException
    {
        this.addState(new MainGameState());
    }

    public static void main(String[] args)
    {
        try
        {
            AppGameContainer container = new AppGameContainer(new MainGame(GameConstants.GAME_TITLE));
            container.setDisplayMode(GameConstants.WINDOW_WIDTH_RESOLUTION, GameConstants.WINDOW_HEIGHT_RESOLUTION, false);
            container.start();

        } catch (SlickException e)
        {
            e.printStackTrace();
        }
    }
}
