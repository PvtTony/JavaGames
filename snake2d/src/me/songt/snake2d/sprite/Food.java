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
    private final int BORDER_LENGTH = 20;
    private final int X_Y_BORDER_MIN = BORDER_LENGTH;
    private final int X_BORDER_MAX = GameConstants.WINDOW_WIDTH_RESOLUTION - BORDER_LENGTH;
    private final int Y_BORDER_MAX = GameConstants.WINDOW_HEIGHT_RESOLUTION - BORDER_LENGTH;
    private float posX, posY;
    private Graphics graphics;
    private Rectangle rectangle;
//    private Random rnd = new Random();
    private ThreadLocalRandom rnd = ThreadLocalRandom.current();

    public Food(float posX, float posY)
    {
        this.posX = posX;
        this.posY = posY;
        rectangle = new Rectangle(posX, posY, BORDER_LENGTH, BORDER_LENGTH);
    }



    public void generateNewPosition()
    {
        /*do
        {
            this.posX = rnd.nextInt(X_BORDER_MAX);
            this.posY = rnd.nextInt(Y_BORDER_MAX);
        }while (getPosX() < X_Y_BORDER_MIN || getPosY() < X_Y_BORDER_MIN);*/
        this.posX = rnd.nextInt(X_Y_BORDER_MIN, X_BORDER_MAX);
        this.posY = rnd.nextInt(X_Y_BORDER_MIN, Y_BORDER_MAX);
        rectangle = new Rectangle(posX, posY, BORDER_LENGTH, BORDER_LENGTH);
    }

    public void setGraphics(Graphics graphics)
    {
        this.graphics = graphics;
    }

    public Food()
    {
        this.generateNewPosition();
    }

    public void draw()
    {
//        this.graphics = graphics;
        graphics.setColor(Color.cyan);
        graphics.fill(rectangle);
//        graphics.fillRect(this.posX, this.posY, BORDER_LENGTH, BORDER_LENGTH);
//        graphics.drawRect(this.posX, this.posY, BORDER_LENGTH, BORDER_LENGTH);
//        System.out.println(this.posX);
//        System.out.println(this.posY);
    }

    /*public void eaten()
    {
        this.generateNewPosition();
        this.draw();
    }*/

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
