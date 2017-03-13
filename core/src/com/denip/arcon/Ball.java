package com.denip.arcon;

import com.badlogic.gdx.math.MathUtils;

public class Ball
{
	public int color = 0;
	public float alpha1 = 1f, alpha2 = 1f;
	public float radius = 64f;
	public float spd, x, y;
	public int direction;
	public boolean isright;
	public float alphaincre = 0.003f;
	public float degree = 0;
	private boolean sign = true;
	public float incre = 1f;
	private float increlim = 140f;
	private float increincre = 1.05f;
	public float rand;

	public Ball(int x, int y, int color)
	{

		this.x = x;
		this.y = y;
		this.color = color;
		isright = MathUtils.randomBoolean();
	}

	public void changeSide()
	{
		isright = !isright;
		rand = 0;
		if (isright)
		{
			sign = !sign;
			incre = 5f;
			degree=0;
		}
		// System.out.println(""+isright);
	}

	public void Update(float speed)
	{

		if (isright)
		{
			spd += 15f;
		} else
		{
			spd -= 15f;
		}

		if (rand <= 5)
		{
			x += spd;
			if (x < 60)
			{
				x = 60;

				spd = 0;
			} else if (x > 270 - radius)
			{

				spd = 0;
			}
		}
		if (isright)
		{
			if (x > 200)
			{
				x = ((float) MathUtils.cosDeg(degree % 360) * (52f - Math.min(incre, 20f)) + 270f);
				y = ((float) MathUtils.sinDeg(degree % 360) * (52f - Math.min(incre, 20f)) + 30f);
				if (incre<200)
				{
				 incre *= 1.003f+(speed/60); // +=(100 - incre) / 60;
				}
				// System.out.println(x+"-"+y);
				if(incre>20)
				{
				 if(alpha1<alpha2)
				 {
				  alpha1+=0.06;
		          alpha2-=0.01;
				 }else if(alpha1>alpha2)
				 {
					 alpha2+=0.06;
			         alpha1-=0.01; 
				 }
				 
				 	
				}
				degree += (sign ? 1 : -1) * incre;
				degree = degree % 360;
				rand = 0;
			}
		} else
		{
			//degree = 0;
			incre = 5;
			y = 30;
			if (rand < 5f)
			{
				rand += 0.05f;
			}
			else
			{
				rand+=0.005f;
			}
			degree+=(sign ? 1 : -1) * rand*10;
		}

	}

	public void Updatebegin()
	{

		x = ((float) MathUtils.cosDeg(degree % 360) * (82f - Math.min(incre / 2, 50f)) + 270f);
		y = ((float) MathUtils.sinDeg(degree % 360) * (82f - Math.min(incre / 2, 50f)) + 30f);
		if (incre > increlim)
		{
			incre=increlim;
			increincre = -MathUtils.random(0.2f, 4f);
		}
		if (incre < 1.1)
		{
			increincre = MathUtils.random(0.2f, 4f);
			sign = !sign;
			increlim = MathUtils.random(60f, 160f);
		}
		
		incre += increincre;
		// +=(100 - incre) / 60;
	//	System.out.println(incre);
		degree += (sign ? 1 : -1) * incre;
		degree = degree % 360;

	}

	// radius -- 270-radius

}
