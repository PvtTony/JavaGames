package me.songt.snake2d.sprite;

import me.songt.snake2d.GameConstants;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.StateBasedGame;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by tony on 2017/3/9.
 * Snake
 */
public class Snake
{
    public final int SNAKE_INITIAL_LENGTH = 4;
    public final int SNAKE_BLOCK_SIZE = 20;
    private int snakeLength = SNAKE_INITIAL_LENGTH;
    private Queue<Rectangle> bodyQueue = new LinkedList<>();
    private Graphics graphics;
    private ThreadLocalRandom rnd = ThreadLocalRandom.current();
    private SnakeDirection snakeDirection = SnakeDirection.EAST;
    private long runningTime = 0;
    private int score = 0;
    private boolean alive = true;

    private void initSnake()
    {
        final int snakeSpawnBorderMin = SNAKE_BLOCK_SIZE * snakeLength;
        final int snakeSpawnBorderXmax = GameConstants.WINDOW_WIDTH_RESOLUTION - SNAKE_BLOCK_SIZE * snakeLength;
        final int snakeSpawnBorderYmax = GameConstants.WINDOW_HEIGHT_RESOLUTION - SNAKE_BLOCK_SIZE * snakeLength;
        int tailPosX = rnd.nextInt(snakeSpawnBorderMin, snakeSpawnBorderXmax);
        int tailPosY = rnd.nextInt(snakeSpawnBorderMin, snakeSpawnBorderYmax);
//        SnakeBody snakeTail = new SnakeBody(tailPosX, tailPosY);
        Rectangle snakeTail = new Rectangle(tailPosX, tailPosY, SNAKE_BLOCK_SIZE, SNAKE_BLOCK_SIZE);
        bodyQueue.add(snakeTail);
        for (int i = 1; i < snakeLength; i++)
        {
            switch (snakeDirection)
            {
                case NORTH:
//                    bodyQueue.offer(new SnakeBody(tailPosX, tailPosY - i * SNAKE_BLOCK_SIZE));
                    bodyQueue.offer(new Rectangle(tailPosX, tailPosY - i * SNAKE_BLOCK_SIZE, SNAKE_BLOCK_SIZE, SNAKE_BLOCK_SIZE));
                    break;
                case EAST:
                    bodyQueue.offer(new Rectangle(tailPosX + i * SNAKE_BLOCK_SIZE, tailPosY, SNAKE_BLOCK_SIZE, SNAKE_BLOCK_SIZE));
                    break;
                case SOUTH:
                    bodyQueue.offer(new Rectangle(tailPosX, tailPosY + i * SNAKE_BLOCK_SIZE, SNAKE_BLOCK_SIZE, SNAKE_BLOCK_SIZE));
                    break;
                case WEST:
                    bodyQueue.offer(new Rectangle(tailPosX - i * SNAKE_BLOCK_SIZE, tailPosY, SNAKE_BLOCK_SIZE, SNAKE_BLOCK_SIZE));
                    break;
                default:
                    break;
            }
        }
    }

    public Snake()
    {
        initSnake();

    }

    public void draw()
    {
        this.graphics.setColor(Color.green);

        for (Rectangle body : bodyQueue)
        {
//            this.graphics.fillRect(body.getxPos(), body.getyPos(), SNAKE_BLOCK_SIZE, SNAKE_BLOCK_SIZE);
            this.graphics.fill(body);
        }
    }

    private boolean checkCollision()
    {
        List<Rectangle> bodyList = (LinkedList<Rectangle>) bodyQueue;
        Rectangle head = getHead();
        for (int i = 0; i < snakeLength - 1; i++)
        {
            if (head.getX() == bodyList.get(i).getX() && head.getY() == bodyList.get(i).getY())
            {
                return true;
            }
        }
        return false;
    }

    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta)
    {
        if (!alive) return;


//        runningTime = 0;
        if (gameContainer.getInput().isKeyDown(Input.KEY_UP) && this.snakeDirection != SnakeDirection.SOUTH)
        {
            this.snakeDirection = SnakeDirection.NORTH;
        }
        if (gameContainer.getInput().isKeyDown(Input.KEY_DOWN) && this.snakeDirection != SnakeDirection.NORTH)
        {
            this.snakeDirection = SnakeDirection.SOUTH;
        }
        if (gameContainer.getInput().isKeyDown(Input.KEY_LEFT) && this.snakeDirection != SnakeDirection.EAST)
        {
            this.snakeDirection = SnakeDirection.WEST;
        }
        if (gameContainer.getInput().isKeyDown(Input.KEY_RIGHT) && this.snakeDirection != SnakeDirection.WEST)
        {
            this.snakeDirection = SnakeDirection.EAST;
        }

        runningTime += delta;
        if (runningTime > GameConstants.GAME_SPEED)
        {
            run();
            runningTime = 0;
        }
        if (checkCollision()) alive = false;
    }

    public boolean intersects(Food food)
    {
        List<Rectangle> bodyList = (LinkedList<Rectangle>) bodyQueue;
        Rectangle head = bodyList.get(snakeLength - 1);
        if (head.intersects(food.getRectangle()))
        {
            return true;
        }
        return false;

    }

    public void addLength()
    {
        this.addBody();
        this.snakeLength++;
    }

    private Rectangle getHead()
    {
        List<Rectangle> bodyList = (LinkedList<Rectangle>) bodyQueue;
        return bodyList.get(snakeLength - 1);
    }

    private void addBody()
    {
        /*List<Rectangle> bodyList = (LinkedList<Rectangle>) bodyQueue;
        float headX = bodyList.get(snakeLength - 1).getX();
        float headY = bodyList.get(snakeLength - 1).getY();*/
        float headX = getHead().getX();
        float headY = getHead().getY();

        switch (snakeDirection)
        {
            case NORTH:
                bodyQueue.offer(new Rectangle(headX, headY - SNAKE_BLOCK_SIZE, SNAKE_BLOCK_SIZE, SNAKE_BLOCK_SIZE));
                break;
            case SOUTH:
                bodyQueue.offer(new Rectangle(headX, headY + SNAKE_BLOCK_SIZE, SNAKE_BLOCK_SIZE, SNAKE_BLOCK_SIZE));
                break;
            case EAST:
                bodyQueue.offer(new Rectangle(headX + SNAKE_BLOCK_SIZE, headY, SNAKE_BLOCK_SIZE, SNAKE_BLOCK_SIZE));
                break;
            case WEST:
                bodyQueue.offer(new Rectangle(headX - SNAKE_BLOCK_SIZE, headY, SNAKE_BLOCK_SIZE, SNAKE_BLOCK_SIZE));
                break;
        }
    }


    private void run()
    {
        addBody();
        bodyQueue.poll();
    }


    public void setGraphics(Graphics graphics)
    {
        this.graphics = graphics;
    }

    public int getScore()
    {
        return score;
    }

    public void addScore()
    {
        this.score++;
    }

    public boolean isAlive()
    {
        return alive;
    }

    enum SnakeDirection
    {
        NORTH, SOUTH, WEST, EAST
    }

}
