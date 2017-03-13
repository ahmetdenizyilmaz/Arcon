package com.denip.arcon;

import com.badlogic.gdx.math.Rectangle;

public class Powerup
{

	public float x;
	public float y;

	public boolean combo = false;
	public float ykatsayi = 1f;
	private Rectangle rect=new Rectangle();;

	public Powerup(float x, float y, boolean combo)
	{
		this.x = x;
		this.y = y;
		this.combo = combo;
		rect.x=x;
		rect.y=y;
		rect.height=144;
		rect.width=144;
	}

	public void Update()
	{

		ykatsayi += 0.1f;
	}

	public boolean iscollision(Ball ball)
	{
		Rectangle a=new Rectangle(ball.x-32f, ball.y-32f, 64f, 64f);
	    Rectangle b=new Rectangle(540 - ball.x-32, 60-ball.y-32,64,64);
	    rect.x=x;
	    rect.y=y;
		return rect.overlaps(a)||rect.overlaps(b) ;
	}

}
