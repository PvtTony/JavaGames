package me.songt.snake2d.sprite;

import me.songt.snake2d.GameConstants;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by tony on 2017/3/9.
 * Food Sprite
 */
public class Food
{
    //The length of the food (Square Boarder)
    private final int BORDER_LENGTH = 20;
    private final int X_Y_BORDER_MIN = BORDER_LENGTH;
    //The max x-axis position of food
    private final int X_BORDER_MAX = GameConstants.WINDOW_WIDTH_RESOLUTION - BORDER_LENGTH;
    //The max y-axis position of food
    private final int Y_BORDER_MAX = GameConstants.WINDOW_HEIGHT_RESOLUTION - BORDER_LENGTH;
    private float posX, posY;
    //Rectangle Object
    private Rectangle rectangle;
    //Randomize
    private ThreadLocalRandom rnd = ThreadLocalRandom.current();

    public Food(float posX, float posY)
    {
        this.posX = posX;
        this.posY = posY;
        rectangle = new Rectangle(posX, posY, BORDER_LENGTH, BORDER_LENGTH);
    }

    public void generateNewPosition()
    {
        this.posX = rnd.nextInt(X_Y_BORDER_MIN, X_BORDER_MAX);
        this.posY = rnd.nextInt(X_Y_BORDER_MIN, Y_BORDER_MAX);
        rectangle = new Rectangle(posX, posY, BORDER_LENGTH, BORDER_LENGTH);
    }

    public Food()
    {
        this.generateNewPosition();
    }

    public void draw(Graphics graphics)
    {
        graphics.setColor(Color.cyan);
        graphics.fill(rectangle);
    }
    public Rectangle getRectangle()
    {
        return rectangle;
    }

    public float getPosX()
    {
        return posX;
    }

    public void setPosX(float posX)
    {
        this.posX = posX;
    }

    public float getPosY()
    {
        return posY;
    }

    public void setPosY(float posY)
    {
        this.posY = posY;
    }
}
