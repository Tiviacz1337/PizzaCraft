package com.tiviacz.pizzacraft.compat.jei;

public class XYProperties 
{
   	private final int xPos;
    private final int yPos;
    private final int height;
    private final int width;

    public XYProperties(int xPos, int yPos, int height, int width) 
    {
        this.xPos = xPos;
        this.yPos = yPos;
        this.height = height;
        this.width = width;
    }

    public XYProperties(int xPos, int yPos, int xy) 
    {
        this(xPos, yPos, xy, xy);
    }
    
    public int getX()
    {
    	return this.xPos;
    }
    
    public int getY()
    {
    	return this.yPos;
    }
    
    public int getHeight()
    {
    	return this.height;
    }
    
    public int getWidth()
    {
    	return this.width;
    }
}
