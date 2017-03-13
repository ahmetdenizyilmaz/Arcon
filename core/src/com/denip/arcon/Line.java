package com.denip.arcon;



import com.badlogic.gdx.math.MathUtils;

public class Line
{
	public int x;
	public int y;
	public int speed =MathUtils.random(250, 350);
	public float ykatsayi=1f;

	public Line(int x, int y)
	{
		this.x = x;
		this.y = y;
		
	}
	public Line(int x, int y,int speed)
	{
		this.x = x;
		this.y = y;
		this.speed=speed;
		
	}
	public void Update()
	{
		// TODO Auto-generated method stub
		
		ykatsayi+=0.2f;
		//alpha2-=0.01;
	}
}
