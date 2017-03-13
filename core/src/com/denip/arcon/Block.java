package com.denip.arcon;

import com.badlogic.gdx.math.Rectangle;

public class Block
{
	public int x, y, color, shape, width, height;
	public boolean visible = true;
	public float radius, angle, alpha = 1f, alpha2 = 1f;
	public float incre = 2;
	public float alphaincre = 0.005f;
	public float ykatsayi = 1;
	public Rectangle rect=new Rectangle();

	public Block(int x, int y, int color, boolean visible, float radius) // circle
																			// 0
	{
		this.x = x;
		this.y = y;
		this.color = color;
		this.shape = 0;
		this.visible = visible;
		this.radius = radius;
		this.height = (int) radius;
		this.width = (int) radius;
		rect.x=x;
		rect.y=y;
		rect.height=128;
		rect.width=128;
	}

	public Block(int x, int y, int color, boolean visible, int width, int height)// rectangle
																					// 1
	{
		this.x = x;
		this.y = y;
		this.color = color;
		this.shape = 1;
		this.visible = visible;
		this.width = width;
		this.height = height;
		this.radius = width;
		rect.x=x;
		rect.y=y;
		rect.height=128;
		rect.width=128;
	}

	public Block(int x, int y, int color, boolean visible, float radius, float angle) // triangle
																						// 2
	{
		this.x = x;
		this.y = y;
		this.color = color;
		this.shape = 2;
		this.visible = visible;
		this.radius = radius;
		this.height = (int) radius;
		this.width = (int) radius;
		rect.x=x;
		rect.y=y;
		rect.height=128;
		rect.width=128;
	}

	public boolean iscollision(Ball ball)
	{
	    Rectangle a=new Rectangle(ball.x-20f, ball.y-20f, 40f, 40f);
	    Rectangle b=new Rectangle(540 - ball.x-20, 60-ball.y-20,40f,40f);
	    rect.x=x;
	    rect.y=y;
		return rect.overlaps(a)||rect.overlaps(b) ;	
	}

	public void Update(float speed)
	{
		// TODO Auto-generated method stub
		if (incre < 15f)
		{
			incre *= 1.03;
		}
		ykatsayi += 0.001f;
		// alpha2-=0.01;
	}

}
