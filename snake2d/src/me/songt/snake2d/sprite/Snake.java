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
 * Snake Sprite
 */
public class Snake
{
    //Initial Snake Length
    private final int SNAKE_INITIAL_LENGTH = 4;
    //Snake body part size
    private final int SNAKE_BLOCK_SIZE = 20;
    private int snakeLength = SNAKE_INITIAL_LENGTH;
    //Snake Body Queue
    private Queue<Rectangle> bodyQueue = new LinkedList<>();
    //Randomize
    private ThreadLocalRandom rnd = ThreadLocalRandom.current();
    //Snake Direction
    private SnakeDirection snakeDirection = SnakeDirection.EAST;
    //Timer
    private long runningTime = 0;
    //Score
    private int score = 0;
    //Alive status
    private boolean alive = true;
    //Move Speed
    private int moveSpeed = 100;


    //Snake Initialization
    private void initSnake()
    {
        final int snakeSpawnBorderMin = SNAKE_BLOCK_SIZE * snakeLength;
        final int snakeSpawnBorderXmax = GameConstants.WINDOW_WIDTH_RESOLUTION - SNAKE_BLOCK_SIZE * snakeLength;
        final int snakeSpawnBorderYmax = GameConstants.WINDOW_HEIGHT_RESOLUTION - SNAKE_BLOCK_SIZE * snakeLength;
        int tailPosX = rnd.nextInt(snakeSpawnBorderMin, snakeSpawnBorderXmax);
        int tailPosY = rnd.nextInt(snakeSpawnBorderMin, snakeSpawnBorderYmax);
        Rectangle snakeTail = new Rectangle(tailPosX, tailPosY, SNAKE_BLOCK_SIZE, SNAKE_BLOCK_SIZE);
        bodyQueue.add(snakeTail);
        for (int i = 1; i < snakeLength; i++)
        {
            switch (snakeDirection)
            {
                case NORTH:
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
    /*
    * Draw Snake
    * */
    public void draw(Graphics graphics)
    {
        graphics.setColor(Color.green);

        for (Rectangle body : bodyQueue)
        {
            graphics.fill(body);
        }
    }
    /*
    * Check Snake Collision
    * */
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
    /*
        * Update Snake
        * */
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta)
    {
        if (!alive) return;

        if (gameContainer.getInput().isKeyPressed(Input.KEY_UP) && this.snakeDirection != SnakeDirection.SOUTH)
        {
            this.snakeDirection = SnakeDirection.NORTH;
        }
        if (gameContainer.getInput().isKeyPressed(Input.KEY_DOWN) && this.snakeDirection != SnakeDirection.NORTH)
        {
            this.snakeDirection = SnakeDirection.SOUTH;
        }
        if (gameContainer.getInput().isKeyPressed(Input.KEY_LEFT) && this.snakeDirection != SnakeDirection.EAST)
        {
            this.snakeDirection = SnakeDirection.WEST;
        }
        if (gameContainer.getInput().isKeyPressed(Input.KEY_RIGHT) && this.snakeDirection != SnakeDirection.WEST)
        {
            this.snakeDirection = SnakeDirection.EAST;
        }
        if (gameContainer.getInput().isKeyPressed(Input.KEY_EQUALS))
        {
            speedUp();
        }
        if (gameContainer.getInput().isKeyPressed(Input.KEY_MINUS))
        {
            speedDown();
        }


        runningTime += delta;
        if (runningTime > moveSpeed)
        {
            run();
            runningTime = 0;
        }
        if (checkCollision()) alive = false;
    }

    /*
    * Check Snake is intersected with food.
    * */
    public boolean intersects(Food food)
    {
        List<Rectangle> bodyList = (LinkedList<Rectangle>) bodyQueue;
        Rectangle head = bodyList.get(snakeLength - 1);
        return head.intersects(food.getRectangle());

    }
    //Add snake length
    public void addLength()
    {
        this.addBody();
        this.snakeLength++;
    }

    //Get Snake Head
    private Rectangle getHead()
    {
        List<Rectangle> bodyList = (LinkedList<Rectangle>) bodyQueue;
        return bodyList.get(snakeLength - 1);
    }

    //Add Snake Body
    private void addBody()
    {
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

    //Speed Up
    private void speedUp()
    {
        runningTime = 0;
        if (moveSpeed > 50)
        {
            moveSpeed -= 50;
        }
    }

    //Speed Down
    private void speedDown()
    {
        runningTime = 0;
        this.moveSpeed += 50;

    }

    public int getMoveSpeed()
    {
        return moveSpeed;
    }
}
