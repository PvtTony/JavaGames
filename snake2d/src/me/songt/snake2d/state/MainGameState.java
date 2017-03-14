package me.songt.snake2d.state;

import me.songt.snake2d.GameConstants;
import me.songt.snake2d.sprite.Food;
import me.songt.snake2d.sprite.Snake;
import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Created by tony on 2017/3/9.
 * Main Game State
 */
public class MainGameState extends BasicGameState
{
    private Food food;
    private Snake snake;

    @Override
    public int getID()
    {
        return GameConstants.MAIN_GAME_STATE_ID;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException
    {
        food = new Food();
        snake = new Snake();
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException
    {
        food.draw(graphics);
        snake.draw(graphics);
        graphics.drawString("Socre: " + snake.getScore(), 30, 30);
        graphics.drawString("Speed: " + snake.getMoveSpeed(), GameConstants.WINDOW_WIDTH_RESOLUTION - 100, 30);
        graphics.setColor(Color.red);
        graphics.drawString(snake.isAlive() ? "" : "Dead", 300 ,230);
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta) throws SlickException
    {
        snake.update(gameContainer, stateBasedGame, delta);
        if (snake.intersects(food))
        {
            food.generateNewPosition();
            snake.addLength();
            snake.addScore();
        }

        if (gameContainer.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) && !snake.isAlive())
        {
            snake = new Snake();
        }
    }
}
