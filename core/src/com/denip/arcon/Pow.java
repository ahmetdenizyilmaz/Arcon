package com.denip.arcon;

import java.util.Iterator;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

public class Pow extends Game implements InputProcessor
{
	SpriteBatch batch;
	Texture balltext;
	// private OrthographicCamera camera;
	private boolean vibrate = true;
	private Ball ball;
	private Array<Block> blocks;
	private Array<Line> lines;
	private float speed = 0.4f;
	private float fspeed = 0.4f;
	private int countdowngnr = 0;
	private float carpan = 1f;
	private Texture[][][] blocktxt = new Texture[2][3][7];
	private int light = 1;
	private float sallan = 0;
	private float sabitsallan = 0;
	private Color lastcolor = new Color(0f, 1f, 0f, 1f);
	private Color firstcolor = new Color(0f, 1f, 0f, 1f);
	private Texture speedtext;
	private BitmapFont fontskor;
	private GlyphLayout glyphLayout;
	private int score = 0;
	private float scoredublor = 0;
	private float scoredepo = 0;
	private boolean gameover;
	private boolean gamestart = true;
	public boolean[] achievements = new boolean[21];
	private int collcontrol = 60;
	private PerspectiveCamera camera;
	private int alphachange = 0;
	private float times;
	private int highscore;
	private float combo = 1f;
	private Music music;
	private Sound sound;
	private Array<Powerup> powerups;
	private int combotime = 600;
	private Sound sound2;
	private int powerupcd = MathUtils.random(500, 900);
	private Texture combo2;
	private Texture combo3;
	private int lastscore = 0;
	private AdsController adsController;
	private int geny = 1000;
	private Texture lead;
	private OrthographicCamera cam;
	private Texture soundon;
	private Texture soundoff;
	private Vector3 coor = new Vector3();
	private Texture ach;
	private Texture rate;
	private int played;
	private Texture vibeon;
	private Texture vibeoff;
	private long tapped;
	private int combo2x = 0;
	private int combo3x = 0;
	private boolean rated = false;
	public static IGoogleServices googleServices;
	private Texture balltext2;
	private Texture balltext3;
	private Texture balltext4;
	private Texture balltext5;
	private Texture balltext6;
	private Texture balltext7;
	private Texture balltext8;
	private Texture balltext9;
	private boolean isshowing = false;

	public Pow(AdsController adsController, IGoogleServices googleServices)
	{
		super();
		Pow.googleServices = googleServices;
		if (adsController != null)
		{
			this.adsController = adsController;
		} else
		{
			this.adsController = new DummyAdsController();
		}
	}

	@Override
	public void create()
	{
	    
		batch = new SpriteBatch();
		sound = Gdx.audio.newSound(Gdx.files.internal("gameover.ogg"));
		sound2 = Gdx.audio.newSound(Gdx.files.internal("powerup.ogg"));
		music = Gdx.audio.newMusic(Gdx.files.internal("gamesong.ogg"));
		music.play();
		music.setLooping(true);
		fontskor = new BitmapFont(Gdx.files.internal("fontskor.fnt"), false);
		fontskor.getData().setScale(2f);
		fontskor.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		lead = new Texture(Gdx.files.internal("leaderboard.png"));
		balltext = new Texture(Gdx.files.internal("ball.png"));
		balltext2 = new Texture(Gdx.files.internal("ball2.png"));
		balltext3 = new Texture(Gdx.files.internal("ball3.png"));
		balltext4 = new Texture(Gdx.files.internal("ball4.png"));
		balltext5 = new Texture(Gdx.files.internal("ball5.png"));
		balltext6 = new Texture(Gdx.files.internal("ball6.png"));
		balltext7 = new Texture(Gdx.files.internal("ball7.png"));
		balltext8 = new Texture(Gdx.files.internal("ball8.png"));
		balltext9 = new Texture(Gdx.files.internal("ball9.png"));
		ach = new Texture(Gdx.files.internal("ach.png"));
		rate = new Texture(Gdx.files.internal("rate.png"));

		soundon = new Texture(Gdx.files.internal("soundon.png"));
		soundoff = new Texture(Gdx.files.internal("soundoff.png"));
		vibeon = new Texture(Gdx.files.internal("vibeon.png"));
		vibeoff = new Texture(Gdx.files.internal("vibeoff.png"));
		speedtext = new Texture(Gdx.files.internal("speed.png"));
		combo2 = new Texture(Gdx.files.internal("combo2.png"));
		combo3 = new Texture(Gdx.files.internal("combo3.png"));
		rate.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		ach.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		soundoff.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		soundon.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		vibeoff.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		vibeon.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		lead.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		combo2.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		combo3.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		balltext.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		balltext2.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		balltext3.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		balltext4.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		balltext5.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		balltext6.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		balltext7.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		balltext8.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		balltext9.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		Preferences prefs = Gdx.app.getPreferences("ady");
		// prefs.putInteger("highscore", 0);
		// prefs.flush();
		for (int i = 0; i < 21; i++)
		{
			achievements[i] = prefs.getBoolean("achievements" + i, false);
		//	System.out.println("achievements" + i);
		}
		if (prefs.getFloat("speed", 0.2f)<0.3f)
			{
			prefs.putFloat("speed", 0.4f);
			prefs.flush();
			}
		//JsonReader jsonReader = new JsonReader();
		// JsonValue jv = jsonReader.parse(file.reader());
		fspeed = prefs.getFloat("speed", 0.4f);
		//Vector3 touch=camera.unproject( new Vector3(Gdx.input.getX(), Gdx.input.getY() , 0  ) ,viewportX, viewportY, viewportWidth, viewportHeight);
		//touch.x // translated touch x
		//touch.y // translated touch y
		speed = fspeed;
		times = prefs.getFloat("times", 1f);
		highscore = prefs.getInteger("highscore", 0);
		carpan = prefs.getFloat("carpan", 1f);
		rated = prefs.getBoolean("rated", false);
		combo2x = prefs.getInteger("2x", 0);
		combo3x = prefs.getInteger("3x", 0);
		tapped = prefs.getLong("tapped", 0l);
		played = prefs.getInteger("played", 0);
		ball = new Ball(50, -200, 2);
		ball.isright = true;
		// adsController.showBannerAd();
		if (highscore == 0)
		{
			lastcolor.set(1, 1, 1, 1);
		} else
		{
			switch (MathUtils.random(5))
			{
			case 0:
				lastcolor.set(1, 0, 0, 1);
				break;
			case 1:
				lastcolor.set(0, 1, 0, 1);
				break;
			case 2:
				lastcolor.set(0, 0, 1, 1);
				break;
			case 3:
				lastcolor.set(1, 1, 0, 1);
				break;
			case 4:
				lastcolor.set(0, 1, 1, 1);
				break;
			case 5:
				lastcolor.set(1, 0, 1, 1);
				break;

			}
		}
		firstcolor.set(lastcolor);
		// lastcolor.set(1, 1, 1, 1);
		// firstcolor.set(1, 1, 1, 1);
		for (int i = 0; i < 7; i++)
		{
			for (int j = 0; j < 3; j++)
			{
				for (int k = 0; k < 2; k++)
				{

					String name = "";
					switch (i)
					{
					case 0:
						name = "aqua";
						break;
					case 1:
						name = "green";
						break;
					case 2:
						name = "navy";
						break;
					case 3:
						name = "pink";
						break;
					case 4:
						name = "red";
						break;
					case 5:
						name = "turk";
						break;
					case 6:
						name = "yellow";
						break;

					}
					if (k == 0)
					{
						name += "dark";
					} else
					{
						name += "light";
					}
					switch (j)
					{
					case 0:
						name += "circle";
						break;
					case 1:
						name += "rect";
						break;
					case 2:
						name += "tri";
						break;
					}

					blocktxt[k][j][i] = new Texture(Gdx.files.internal(name + ".png"));
					blocktxt[k][j][i].setFilter(TextureFilter.Linear, TextureFilter.Linear);
				}
			}
		}
		cam = new OrthographicCamera(540, 960);
		cam.setToOrtho(false, 540, 960);
//cam = new OrthographicCamera(1080, Gdx.graphics.getHeight()/Gdx.graphics.getWidth()*1080 );
		
		camera = new PerspectiveCamera(67, 540, 960);
		camera.position.set(270f, -480.0f, 700);
		camera.lookAt(270f, 480.0f, 00.0f);
		camera.near = 0.01f;
		camera.far = 30000.0f;
		camera.update();
		glyphLayout = new GlyphLayout();
		Gdx.input.setInputProcessor(this);
		blocks = new Array<Block>();
		lines = new Array<Line>();
		powerups = new Array<Powerup>();

	}

	@Override
	public void render()
	{
		
        
		batch.setProjectionMatrix(camera.combined);
		Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		float xsal = MathUtils.random(sallan) - sallan / 2 + MathUtils.random(sabitsallan) - sabitsallan / 2;
		float ysal = MathUtils.random(sallan) - sallan / 2 + MathUtils.random(sabitsallan) - sabitsallan / 2;
		camera.position.set(xsal + 270, ysal - 400, 700);

		camera.update();
		// System.out.println(speed+"");
		cam.position.set(xsal + 270, ysal + 480, 0);
		cam.update();
		if (collcontrol < 320)
		{
			collcontrol++;
		}
		if (gameover)
		{
			ball.isright = true;

			played++;
			if (vibrate)
			{
				Gdx.input.vibrate(500);
			}
			// System.out.println(speed);
			googleServices.submitSpeedScore((long) (speed * 10000f));
			played += 1;
			powerups.clear();
			ball.y = -500;
			combo = 1f;
			combotime = 0;
			// lines.clear();
			blocks.clear();
			geny = 1000;
			scoredublor += scoredepo;
			score = (int) scoredublor;
			lastscore = score;
			if (score > highscore)
			{
				highscore = score;
			}
			Preferences prefs = Gdx.app.getPreferences("ady");
			prefs.putInteger("played", played);
			prefs.putLong("tapped", tapped);
			if (score > 150)
			{

				fspeed += (speed / 2 - fspeed) / 40;
				prefs.putFloat("times", (times + 0.5f) * 1.05f);
				prefs.putFloat("speed", fspeed);
				times *= 1.1f;
				if (score > highscore / 5 && MathUtils.randomBoolean(0.7f))
					adsController.showInterstitialAd(new Runnable()
					{
						@Override
						public void run()
						{
						}
					});

			}
			speed = fspeed;
			if (score > prefs.getInteger("highscore", 0))
			{
				prefs.putInteger("highscore", highscore);
				googleServices.submitScore(score);

				if (score > 150)
				{
					adsController.showInterstitialAd(new Runnable()
					{
						@Override
						public void run()
						{
						}
					});
					googleServices.showScore();
				}
			}
			prefs.flush();
			scoredublor = 0;
			scoredepo = 0;
			score = 0;
			ball.alpha1 = 1f;
			ball.alpha2 = 1f;
			gameover = false;
			gamestart = true;
		}

		camera.update();
		sallan /= 1.05f;

		///////// skor/////////////
		if (!gamestart)
		{
			scoredepo += ((speed + 1) * (speed + 1) / 1000f) * times * combo * carpan;
			if (speed < 1.3f)
			{
				speed += (2.3f - speed) / 16000;
				scoredepo += ((speed + 1) * (speed + 1) / 1000f) * times * combo * carpan;
			} else
			{
				speed += 0.0001;
				scoredepo += ((speed + 1) * (speed + 1) / 500f) * times * combo * carpan;
			}
			if (score > highscore)
			{
				highscore = score;
			}
		}
		scoredublor += scoredepo / 20;
		scoredepo -= scoredepo / 20;
		score = (int) scoredublor;

		batch.begin();

		batch.setColor(1f, 1f, 1f, 0.5f);

		batch.end();

		if (!gamestart)
		{
			countdowngnr++;
		}
		if (countdowngnr >= 60 - (int) Math.min(35, speed * 13f) || (geny < 8000 && !gamestart && countdowngnr % 5 == 0))
		{
			countdowngnr = 0;
			generateBlock();
		}

		if (!gamestart)
		{
			ball.Update(speed);
		} else
		{
			ball.Updatebegin();
		}
		batch.begin();

		//////////////////////////////////

		if (gamestart)
		{
			batch.setColor(1f, 1f, 1f, 0.3f);
		} else
		{
			batch.setColor(1f, 1f, 1f, 0.15f);
		}
		//////////////////////////////////
		if (MathUtils.random() < 0.6f)
		{
			batch.draw(blocktxt[MathUtils.random(1)][MathUtils.random(2)][MathUtils.random(6)], -757, -484, 2048, 2048);
		}
		batch.setColor(1f, 1f, 1f, 1f);
		////////////////

		Iterator<Block> iter = blocks.iterator();
		while (iter.hasNext())
		{
			Block temp = iter.next();

			if (!temp.visible)
			{

				if (temp.alpha <= 0)
				{
					temp.alpha = 0;
				}

			}
			temp.y -= speed * 20 * temp.ykatsayi;

			if (temp.iscollision(ball))
			{
				sound.play();
				sallan += 100;
				gameover = true;

				switch (temp.color)
				{
				case 0:
					lastcolor = new Color(0, 1, 1, 1);
					break;
				case 1:
					lastcolor = new Color(0, 1, 0, 1);
					break;
				case 2:
					lastcolor = new Color(0, 0, 0.8f, 1);
					break;
				case 3:
					lastcolor = new Color(1, 0.2f, 1, 1);
					break;
				case 4:
					lastcolor = new Color(1, 0, 0, 1);
					break;
				case 5:
					lastcolor = new Color(0.6f, 1, 0.8f, 1);
					break;
				case 6:
					lastcolor = new Color(1f, 1, 0f, 1);
					break;

				}
				firstcolor.set(lastcolor);

			} else if (temp.alpha2 <= 0.01f)
			{
				iter.remove();
			}
			if (!temp.visible)
			{
				if (speed > 2.5f)
				{
					temp.alpha = 0f;
				} else
				{
					if (speed > 2f)
					{
						temp.alpha = 1;
					} else
					{
						if (speed > 1.5f)
						{
							temp.alpha = 0f;
						} else if (speed > 1.0f)
						{
							temp.alpha -= 0.005f;
							if (temp.alpha <= 0)
							{
								temp.alpha = 0f;
							}

						} else if (speed > 0.7f)
						{

							temp.alpha -= temp.alphaincre * 2;
							if (temp.alpha <= 0 || temp.alpha >= 1)
							{
								temp.alphaincre *= -1;
								temp.alpha -= temp.alphaincre;
							}
							if (temp.alpha <= 0)
							{
								temp.alpha = 0f;
							}
							if (temp.alpha >= 1f)
							{
								temp.alpha = 0.99f;
							}

						}
					}
				}
			}
			if (temp.y < 5)
			{

				sallan = speed / 5;
				// System.out.println("sallan");
				switch (temp.color)
				{
				case 0:
					lastcolor = new Color(0, 1, 1, 1);
					break;
				case 1:
					lastcolor = new Color(0, 1, 0, 1);
					break;
				case 2:
					lastcolor = new Color(0, 0, 0.8f, 1);
					break;
				case 3:
					lastcolor = new Color(1, 0.2f, 1, 1);
					break;
				case 4:
					lastcolor = new Color(1, 0, 0, 1);
					break;
				case 5:
					lastcolor = new Color(0.6f, 1, 0.8f, 1);
					break;
				case 6:
					lastcolor = new Color(1f, 1, 0f, 1);
					break;

				}
				if (temp.y < -1500)
				{
					iter.remove();
				}

			}

			temp.Update(speed);

			if (temp.visible)
			{

				batch.setColor(1f, 1f, 1f, 1f);

			} else
			{

				batch.setColor(1f, 1f, 1f, temp.alpha);
			}
			// batch.setColor(1f, 1f, 1f, temp.alpha);
			batch.draw(blocktxt[light][temp.shape][temp.color], temp.x - temp.incre / 2, temp.y - temp.incre / 2, temp.width + temp.incre,
					temp.height + temp.incre);

		}
		batch.setColor(1f, 1f, 1f, 1f);
		fontskor.setColor(lastcolor.r, lastcolor.g, lastcolor.b, 0.6f);

		String item = "" + score;
		glyphLayout.setText(fontskor, item);
		float w = glyphLayout.width;
		item = "" + highscore;
		glyphLayout.setText(fontskor, item);
		float w2 = glyphLayout.width;
		if (!gamestart)
		{
			if (score < highscore)
			{
				fontskor.draw(batch, "" + score, 270 - w / 2, 800);
			} else
			{
				highscore = score;
			}
			 
			fontskor.draw(batch, "" + highscore, 270 - w2 / 2, 1050).setText(fontskor, "adadadadadad");
			if (combo > 1)
			{
				fontskor.getData().setScale(Math.min((float) Math.sqrt(combo), 3f));
				item = "x " + combo;
				glyphLayout.setText(fontskor, item);
				float w3 = glyphLayout.width;
				fontskor.setColor(lastcolor.r, lastcolor.g, lastcolor.b, combotime / 1200f + 0.1f);
				fontskor.draw(batch, "x " + combo, 270 - w3 / 2, 500);

				fontskor.getData().setScale(2f);
			}

		}

		fontskor.setColor(lastcolor.r, lastcolor.g, lastcolor.b, 1f);
		if (gamestart)
		{

			fontskor.getData().setScale(3f + MathUtils.random(-0.05f, 0.05f));
			glyphLayout.setText(fontskor, "Arcon");
			float arc = glyphLayout.width;
			fontskor.draw(batch, "Arcon", 270 - arc / 2, 500);
			if (lastscore > 0)
			{
				batch.setProjectionMatrix(cam.combined);
				fontskor.getData().setScale(2f);
				glyphLayout.setText(fontskor, "" + lastscore);
				arc = glyphLayout.width;
				fontskor.draw(batch, "" + lastscore, 270 - arc / 2, 650);
				batch.setProjectionMatrix(camera.combined);
			}
			fontskor.getData().setScale(2f);
		}
		/*
		 * Iterator<Line> iterl = lines.iterator(); while (iterl.hasNext()) {
		 * Line temp = iterl.next(); if (temp.y < -200) { iterl.remove(); }
		 * temp.Update(); temp.y -= speed * 5 * temp.ykatsayi;
		 * batch.draw(speedtext, temp.x, temp.y, 3, 200); }
		 */
		Iterator<Powerup> iterp = powerups.iterator();
		while (iterp.hasNext())
		{
			Powerup temp = iterp.next();
			if (temp.y < -200)
			{
				iterp.remove();
			}

			if (temp.iscollision(ball))
			{
				if (vibrate)
				{
					Gdx.input.vibrate(30);
				}
				if (combo == 1f)
				{
					combotime = 600;
				} else
				{
					combotime += 300;
				}
				Preferences prefs = Gdx.app.getPreferences("ady");
				if (temp.combo)
				{
					combo2x++;
					prefs.putInteger("2x", combo2x);
				} else
				{
					combo3x++;
					prefs.putInteger("3x", combo3x);
				}
				prefs.flush();

				combo *= temp.combo ? 2f : 3f;
				scoredepo += temp.combo ? speed * 20f * combo : speed * 40f * combo;
				iterp.remove();
				sound2.play();
			}
			temp.Update();
			temp.y -= speed * temp.ykatsayi;
			batch.setColor(lastcolor);
			batch.draw(temp.combo ? combo2 : combo3, temp.x, temp.y, 144, 144);

		}
		batch.setColor(1f, 1f, 1f, 1f);
		if (!gamestart)
		{
			if (powerupcd-- == 0)
			{
				generatePowerUp();
			}
			if (combotime-- == 0)
			{
				combo = 1f;
			}

		}
		if (speed > 1.6)
		{
			ball.alpha1 = 1f;
			ball.alpha2 = 1f;
		} else
		{
			if (speed > 1.2)
			{
				alphachange -= 1;
				if (alphachange <= 0)
				{
					alphachange = 600;
					if (MathUtils.randomBoolean())
					{
						ball.alpha2 = 1f;
						ball.alpha1 = 0.2f;
					} else
					{
						ball.alpha2 = 0.2f;
						ball.alpha1 = 1f;
					}
				}
			} else if (speed > 0.9f)
			{
				if (!ball.isright)
				{
					ball.alpha1 -= ball.alphaincre;
					if (ball.alpha1 <= 0.1f)
					{
						ball.alpha1 = 0.1f;
						ball.alphaincre *= -1;
					}
					if (ball.alpha1 >= 1)
					{
						ball.alpha1 = 1f;

						ball.alphaincre *= -1;
					}
					ball.alpha2 = 1f - ball.alpha1;
				}
			}
		}
		// System.out.prinln("alpha" + ball.alpha1 + "---" + ball.alpha2);
		// System.out.println(speed);
		if (ball.incre >= 120)
		{
			batch.setColor(lastcolor.r, lastcolor.g, lastcolor.b, ball.alpha1);
			// batch.draw(balltext7, ball.x - 32, ball.y - 64, 32, 64, 128, 128,
			// 1f, 1f,180+ball.degree, 0, 0, 256, 256, false, false);

			batch.draw(balltext7, 270 - 64, 30 - 64, 128, 128);
			batch.setColor(lastcolor.r, lastcolor.g, lastcolor.b, ball.alpha2);
			batch.draw(balltext7, 270 - 64, 30 - 64, 128, 128);
			batch.setColor(lastcolor.r, lastcolor.g, lastcolor.b, 1);
			batch.draw(balltext7, 270 - 64, 30 - 64, 128, 128);
		} else if (ball.incre > 100)
		{
			batch.setColor(lastcolor.r, lastcolor.g, lastcolor.b, ball.alpha1);
			// batch.draw(balltext7, 270-64, 30-64, 128, 128);
			Vector2 as = new Vector2(ball.x - 270, ball.y - 30);
			// System.out.println(as);
			// System.out.println(as.angle());

			batch.draw(balltext6, ball.x - 32f, ball.y - 59f, 32, 59, 80, 118, 1f, 1f, 180 + as.angle(), 0, 0, 160, 237, false, false);
			batch.setColor(lastcolor.r, lastcolor.g, lastcolor.b, ball.alpha2);
			batch.draw(balltext6, 540f - ball.x - 32f, 60f - ball.y - 59f, 32, 59, 80, 118, 1f, 1f, as.angle(), 0, 0, 160, 237, false, false);
		} else if (ball.incre > 70)
		{
			Vector2 as = new Vector2(ball.x - 270, ball.y - 30);
			batch.setColor(lastcolor.r, lastcolor.g, lastcolor.b, ball.alpha1);
			batch.draw(balltext5, ball.x - 32, ball.y - 52, 32, 52, 72, 104, 1f, 1f, 180 + as.angle(), 0, 0, 143, 207, false, false);
			batch.setColor(lastcolor.r, lastcolor.g, lastcolor.b, ball.alpha2);
			batch.draw(balltext5, 540 - ball.x - 32, 60 - ball.y - 52, 32, 52, 72, 104, 1f, 1f, as.angle(), 0, 0, 143, 207, false, false);
		} else if (ball.incre > 45)
		{
			Vector2 as = new Vector2(ball.x - 270, ball.y - 30);
			batch.setColor(lastcolor.r, lastcolor.g, lastcolor.b, ball.alpha1);
			batch.draw(balltext4, ball.x - 32, ball.y - 47, 32, 47, 68, 93, 1f, 1f, 180 + as.angle(), 0, 0, 137, 186, false, false);
			batch.setColor(lastcolor.r, lastcolor.g, lastcolor.b, ball.alpha2);
			batch.draw(balltext4, 540 - ball.x - 32, 60 - ball.y - 47, 32, 47, 68, 93, 1f, 1f, as.angle(), 0, 0, 137, 186, false, false);
		} else if (ball.incre > 25)
		{
			Vector2 as = new Vector2(ball.x - 270, ball.y - 30);
			batch.setColor(lastcolor.r, lastcolor.g, lastcolor.b, ball.alpha1);
			batch.draw(balltext3, ball.x - 32, ball.y - 39, 32, 39, 66, 78, 1f, 1f, 180 + as.angle(), 0, 0, 132, 156, false, false);
			batch.setColor(lastcolor.r, lastcolor.g, lastcolor.b, ball.alpha2);
			batch.draw(balltext3, 540 - ball.x - 32, 60 - ball.y - 39, 32, 39, 66, 78, 1f, 1f, as.angle(), 0, 0, 132, 156, false, false);
		} else if (ball.incre > 10)
		{
			Vector2 as = new Vector2(ball.x - 270, ball.y - 30);
			batch.setColor(lastcolor.r, lastcolor.g, lastcolor.b, ball.alpha1);
			batch.draw(balltext2, ball.x - 33, ball.y - 36, 33, 36, 66, 72, 1f, 1f, 180 + as.angle(), 0, 0, 132, 144, false, false);
			batch.setColor(lastcolor.r, lastcolor.g, lastcolor.b, ball.alpha2);
			batch.draw(balltext2, 540 - ball.x - 33, 60 - ball.y - 36, 33, 36, 66, 72, 1f, 1f, as.angle(), 0, 0, 132, 144, false, false);
		} else
		{
			Vector2 as = new Vector2(ball.x - 270, ball.y - 30);
			if (ball.isright)
			{
				batch.setColor(lastcolor.r, lastcolor.g, lastcolor.b, ball.alpha1);
				batch.draw(balltext, ball.x - 32, ball.y - 32, 32, 32, 64, 64, 1f, 1f, 180 + as.angle(), 0, 0, 128, 128, false, false);
				batch.setColor(lastcolor.r, lastcolor.g, lastcolor.b, ball.alpha2);
				batch.draw(balltext, 540 - ball.x - 32, 60 - ball.y - 32, 32, 32, 64, 64, 1f, 1f, as.angle(), 0, 0, 128, 128, false, false);
			} else
			{
				float randx = MathUtils.random(ball.rand) - ball.rand / 2;
				float randy = MathUtils.random(ball.rand) - ball.rand / 2;
				if (ball.rand > 4f)
				{
					batch.setColor(lastcolor.r, lastcolor.g, lastcolor.b, ball.alpha1);
					batch.draw(balltext9, ball.x - 32 + randx * 2, ball.y - 32 + randy * 2, 32, 32, 64, 64, 1f, 1f, ball.degree, 0, 0, 128, 128, false, false);
					batch.setColor(lastcolor.r, lastcolor.g, lastcolor.b, ball.alpha2);
					batch.draw(balltext9, 540 - ball.x - 32 - randx * 2, 60 - ball.y - 32 + randy * 2, 32, 32, 64, 64, 1f, 1f, -1 * ball.degree, 0, 0, 128, 128,
							false, false);
				} else if (ball.rand > 2f)
				{
					batch.setColor(lastcolor.r, lastcolor.g, lastcolor.b, ball.alpha1);
					batch.draw(balltext8, ball.x - 32 + randx * 2, ball.y - 32 + randy * 2, 32, 32, 64, 64, 1f, 1f, ball.degree, 0, 0, 128, 128, false, false);
					batch.setColor(lastcolor.r, lastcolor.g, lastcolor.b, ball.alpha2);
					batch.draw(balltext8, 540 - ball.x - 32 - randx * 2, 60 - ball.y - 32 + randy * 2, 32, 32, 64, 64, 1f, 1f, -1 * ball.degree, 0, 0, 128, 128,
							false, false);
				} else
				{

					batch.setColor(lastcolor.r, lastcolor.g, lastcolor.b, ball.alpha1);
					batch.draw(balltext, ball.x - 32 + randx * 2, ball.y - 32 + randy * 2, 32, 32, 64, 64, 1f, 1f, ball.degree, 0, 0, 128, 128, false, false);
					batch.setColor(lastcolor.r, lastcolor.g, lastcolor.b, ball.alpha2);
					batch.draw(balltext, 540 - ball.x - 32 - randx * 2, 60 - ball.y - 32 + randy * 2, 32, 32, 64, 64, 1f, 1f, -1 * ball.degree, 0, 0, 128, 128,
							false, false);
				}
			}
		}

		batch.setProjectionMatrix(cam.combined);

		if (gamestart)
		{
			firstcolor.a = 1f;
			batch.setColor(firstcolor);
			batch.draw(rate, 328, 750, 64, 64);
			batch.draw(ach, 148, 745, 64, 64);
			if (vibrate)
			{
				batch.draw(vibeon, 243, 850, 64, 64);
			}
			{
				batch.draw(vibeoff, 243, 850, 64, 64);
			}

		} else
		{
			firstcolor.a = 0.7f;
			batch.setColor(firstcolor);
			batch.draw(rate, 446, 750, 64, 64);
			batch.draw(ach, 30, 745, 64, 64);
		}

		if (music.isPlaying())
		{
			batch.draw(soundon, 30, 850, 64, 64);
		} else
		{
			batch.draw(soundoff, 30, 850, 64, 64);
		}

		batch.draw(lead, 446, 850, 64, 64);

		batch.setColor(1f, 1f, 1f, 1f);
		batch.end();
		check_achievements();
		if (combotime > 0 && score > 100)
		{
			if (!isshowing)
			{
				adsController.showBannerAd();
				isshowing = true;
			}
		} else
		{
			adsController.hideBannerAd();
			isshowing = false;
		}
	}

	public void dispose()
	{
		vibeon.dispose();
		vibeoff.dispose();
		speedtext.dispose();
		combo2.dispose();
		combo3.dispose();
		sound.dispose();
		sound2.dispose();
		batch.dispose();
		fontskor.dispose();
		balltext.dispose();
		balltext2.dispose();
		balltext3.dispose();
		balltext4.dispose();
		balltext5.dispose();
		balltext6.dispose();
		balltext7.dispose();
		balltext8.dispose();
		balltext9.dispose();
		music.dispose();
		soundon.dispose();
		soundoff.dispose();
		ach.dispose();
		rate.dispose();
		lead.dispose();
		for (int i = 0; i < 7; i++)
		{
			for (int j = 0; j < 3; j++)
			{
				for (int k = 0; k < 2; k++)
				{
					blocktxt[k][j][i].dispose();
				}
			}
		}

	}

	public void generateLine()
	{
		for (int i = 0; i < 400; i++)
		{
			lines.add(new Line(MathUtils.random(2000) - 1000 + 270, MathUtils.random(2000, 60420)));
		}

	}

	public void generatePowerUp()
	{
		powerupcd = MathUtils.random(400, 1100) - (int) (speed * 100);
		int coor = MathUtils.random(1) * 142;
		if (MathUtils.randomBoolean())
		{
			coor = 412 - coor;
		}
		powerups.add(new Powerup(coor, 6000, MathUtils.randomBoolean(0.8f)));

	}

	public void generateBlock()
	{
		// light= light==1? 0:1;
		if (geny < 8000)
		{
			geny += 800;
		}
		Block a = null, b = null;
		int c = MathUtils.random(0, 6);

		if (MathUtils.random() < 0.8f + speed / 9 || geny < 8000)
		{
			switch (MathUtils.random(2))
			{
			case 0:

				a = new Block(MathUtils.random(1) * 142, geny, c, true, 128f);
				b = new Block(412 - a.x, geny, c, true, 128);
				break;
			case 1:
				a = new Block(MathUtils.random(1) * 142, geny, c, true, 128f, 128f);
				b = new Block(412 - a.x, geny, c, true, 128f, 128f);
				break;
			case 2:
				a = new Block(MathUtils.random(1) * 142, geny, c, true, 128, 128);
				b = new Block(412 - a.x, geny, c, true, 128, 128);
				break;
			}
			a.visible = MathUtils.randomBoolean();
			if (a.visible)
			{
				b.visible = MathUtils.randomBoolean();
			} else
			{
				b.visible = true;
			}
			// System.out.println("" + a.y + "-" + a.x);
			blocks.add(a);
			blocks.add(b);
			// System.out.println("" + blocks.size);
		}
	}

	@Override
	public boolean keyDown(int keycode)
	{
		// TODO Auto-generated method stub
		if (keycode == Keys.SPACE)
		{
			// light= light==1? 0:1;
			ball.changeSide();
			// sound.setPitch(soundid, (float) (speed + 10) / 30);
			// pan= ball.isright ? 0.7f : -0.7f;
			// System.out.println("speed: " + speed);
			gamestart = false;
			// ball.y = 30;
		}
		if (keycode == Keys.A)
		{
			speed += 0.5f;
			scoredepo += 1127 * speed;
			highscore = (int) (scoredepo * 2f * speed);
			switch (MathUtils.random(5))
			{
			case 0:
				lastcolor.set(1, 0, 0, 1);
				break;
			case 1:
				lastcolor.set(0, 1, 0, 1);
				break;
			case 2:
				lastcolor.set(0, 0, 1, 1);
				break;
			case 3:
				lastcolor.set(1, 1, 0, 1);
				break;
			case 4:
				lastcolor.set(0, 1, 1, 1);
				break;
			case 5:
				lastcolor.set(1, 0, 1, 1);
				break;

			}
			Iterator<Block> iter8 = blocks.iterator();
			while (iter8.hasNext())
			{
				Block temp = iter8.next();
				temp.color = MathUtils.random(0, 6);
				temp.shape = MathUtils.random(0, 2);
			}

		}
		return false;
	}

	@Override
	public boolean keyUp(int keycode)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character)
	{
		// TODO Auto-generated method stub
		return false;
	}

	public void check_achievements()
	{
		// skorlar
		if (score > 500 && !achievements[0])
		{

			googleServices.unlockAchievementGPGS("CgkI5PT2i-wWEAIQAw");
			achievements[0] = true;
			carpan *= 1.2;
			Preferences prefs = Gdx.app.getPreferences("ady");
			prefs.putBoolean("achievements0", true);
			prefs.putFloat("carpan", carpan);
			prefs.flush();

		} else if (score > 1000 && !achievements[1])
		{
			achievements[1] = true;
			googleServices.unlockAchievementGPGS("CgkI5PT2i-wWEAIQBA");
			carpan *= 1.2;
			Preferences prefs = Gdx.app.getPreferences("ady");
			prefs.putBoolean("achievements1", true);
			prefs.putFloat("carpan", carpan);
			prefs.flush();
		} else if (score > 3000 && !achievements[2])
		{
			achievements[2] = true;
			googleServices.unlockAchievementGPGS("CgkI5PT2i-wWEAIQBQ");
			carpan *= 1.2;
			Preferences prefs = Gdx.app.getPreferences("ady");
			prefs.putBoolean("achievements2", true);
			prefs.putFloat("carpan", carpan);
			prefs.flush();
		} else if (score > 15000 && !achievements[3])
		{
			achievements[3] = true;
			googleServices.unlockAchievementGPGS("CgkI5PT2i-wWEAIQBg");
			carpan *= 2;
			Preferences prefs = Gdx.app.getPreferences("ady");
			prefs.putBoolean("achievements3", true);
			prefs.putFloat("carpan", carpan);
			prefs.flush();
		}
		//////////////////////

		if (played > 3 && !achievements[4])
		{
			googleServices.unlockAchievementGPGS("CgkI5PT2i-wWEAIQBw");
			achievements[4] = true;
			carpan *= 1.1;
			Preferences prefs = Gdx.app.getPreferences("ady");
			prefs.putBoolean("achievements4", true);
			prefs.putFloat("carpan", carpan);
			prefs.flush();
		} else if (played > 10 && !achievements[5])
		{
			googleServices.unlockAchievementGPGS("CgkI5PT2i-wWEAIQCA");
			achievements[5] = true;
			carpan *= 1.2;
			Preferences prefs = Gdx.app.getPreferences("ady");
			prefs.putBoolean("achievements5", true);
			prefs.putFloat("carpan", carpan);
			prefs.flush();
		} else if (played > 25 && !achievements[6])
		{
			googleServices.unlockAchievementGPGS("CgkI5PT2i-wWEAIQCQ");
			achievements[6] = true;
			carpan *= 1.2;
			Preferences prefs = Gdx.app.getPreferences("ady");
			prefs.putBoolean("achievements6", true);
			prefs.putFloat("carpan", carpan);
			prefs.flush();
		} else if (played > 100 && !achievements[7])
		{
			googleServices.unlockAchievementGPGS("CgkI5PT2i-wWEAIQCg");
			achievements[7] = true;
			carpan *= 2;
			Preferences prefs = Gdx.app.getPreferences("ady");
			prefs.putBoolean("achievements7", true);
			prefs.putFloat("carpan", carpan);
			prefs.flush();
		}
		//////////////////////////////////////

		if (tapped > 300 && !achievements[8])
		{
			googleServices.unlockAchievementGPGS("CgkI5PT2i-wWEAIQCw");
			achievements[8] = true;
			carpan *= 1.1;
			Preferences prefs = Gdx.app.getPreferences("ady");
			prefs.putBoolean("achievements8", true);
			prefs.putFloat("carpan", carpan);
			prefs.flush();
		} else if (tapped > 1000 && !achievements[9])
		{
			googleServices.unlockAchievementGPGS("CgkI5PT2i-wWEAIQDA");
			achievements[9] = true;
			carpan *= 1.2;
			Preferences prefs = Gdx.app.getPreferences("ady");
			prefs.putBoolean("achievements9", true);
			prefs.putFloat("carpan", carpan);
			prefs.flush();
		} else if (tapped > 5000 && !achievements[10])
		{
			googleServices.unlockAchievementGPGS("CgkI5PT2i-wWEAIQDQ");
			achievements[10] = true;
			carpan *= 1.2;
			Preferences prefs = Gdx.app.getPreferences("ady");
			prefs.putBoolean("achievements10", true);
			prefs.putFloat("carpan", carpan);
			prefs.flush();
		} else if (tapped > 25000 && !achievements[11])
		{
			googleServices.unlockAchievementGPGS("CgkI5PT2i-wWEAIQDg");
			achievements[11] = true;
			carpan *= 2;
			Preferences prefs = Gdx.app.getPreferences("ady");
			prefs.putBoolean("achievements11", true);
			prefs.putFloat("carpan", carpan);
			prefs.flush();
		}
		//////////////////////////////////////

		if (combo2x > 10 && !achievements[12])
		{
			googleServices.unlockAchievementGPGS("CgkI5PT2i-wWEAIQDw");
			achievements[12] = true;
			carpan *= 1.1;
			Preferences prefs = Gdx.app.getPreferences("ady");
			prefs.putBoolean("achievements12", true);
			prefs.putFloat("carpan", carpan);
			prefs.flush();
		} else if (combo2x > 25 && !achievements[13])
		{
			googleServices.unlockAchievementGPGS("CgkI5PT2i-wWEAIQEA");
			achievements[13] = true;
			carpan *= 1.2;
			Preferences prefs = Gdx.app.getPreferences("ady");
			prefs.putBoolean("achievements13", true);
			prefs.putFloat("carpan", carpan);
			prefs.flush();
		} else if (combo2x > 100 && !achievements[14])
		{
			googleServices.unlockAchievementGPGS("CgkI5PT2i-wWEAIQEQ");
			achievements[14] = true;
			carpan *= 1.2;
			Preferences prefs = Gdx.app.getPreferences("ady");
			prefs.putBoolean("achievements14", true);
			prefs.putFloat("carpan", carpan);
			prefs.flush();
		} else if (combo2x > 300 && !achievements[15])
		{
			googleServices.unlockAchievementGPGS("CgkI5PT2i-wWEAIQEg");
			achievements[15] = true;
			carpan *= 2;
			Preferences prefs = Gdx.app.getPreferences("ady");
			prefs.putBoolean("achievements15", true);
			prefs.putFloat("carpan", carpan);
			prefs.flush();
		}

		////////////////////////////

		if (combo3x > 10 && !achievements[16])
		{
			googleServices.unlockAchievementGPGS("CgkI5PT2i-wWEAIQEw");
			achievements[16] = true;
			carpan *= 1.2;
			Preferences prefs = Gdx.app.getPreferences("ady");
			prefs.putBoolean("achievements16", true);
			prefs.putFloat("carpan", carpan);
			prefs.flush();
		} else if (combo3x > 25 && !achievements[17])
		{
			googleServices.unlockAchievementGPGS("CgkI5PT2i-wWEAIQFA");
			achievements[17] = true;
			carpan *= 1.3;
			Preferences prefs = Gdx.app.getPreferences("ady");
			prefs.putBoolean("achievements17", true);
			prefs.putFloat("carpan", carpan);
			prefs.flush();
		} else if (combo3x > 100 && !achievements[18])
		{
			googleServices.unlockAchievementGPGS("CgkI5PT2i-wWEAIQFQ");
			achievements[18] = true;
			carpan *= 1.3;
			Preferences prefs = Gdx.app.getPreferences("ady");
			prefs.putBoolean("achievements18", true);
			prefs.putFloat("carpan", carpan);
			prefs.flush();
		} else if (combo3x > 300 && !achievements[19])
		{
			googleServices.unlockAchievementGPGS("CgkI5PT2i-wWEAIQFg");
			achievements[19] = true;
			carpan *= 2;
			Preferences prefs = Gdx.app.getPreferences("ady");
			prefs.putBoolean("achievements19", true);
			prefs.putFloat("carpan", carpan);
			prefs.flush();
		}

		////////////////////////////

	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button)
	{
		// TODO Auto-generated method stub
		coor.set(screenX, screenY, 0);

		cam.unproject(coor);
		int mouse_x = (int) coor.x;
		int mouse_y = (int) coor.y;

		// System.out.println(mouse_x+"/"+mouse_y);

		//
		if (mouse_x > 446 && mouse_x < 510 && mouse_y > 850 && mouse_y < 914)
		{
			googleServices.showScores();
		} else if (mouse_x > 30 && mouse_x < 96 && mouse_y > 850 && mouse_y < 914)
		{

			if (music.isPlaying())
			{
				music.pause();
			} else
			{
				music.play();
				music.setLooping(true);
			}
		} else
		{
			if (gamestart)
			{
				// batch.draw(rate, 328, 750, 64, 64);
				// batch.draw(ach, 148, 745, 64, 64);
				if (mouse_x > 148 && mouse_x < 212 && mouse_y > 745 && mouse_y < 809)
				{

					// ach
					googleServices.get_ach();

				} else if (mouse_x > 328 && mouse_x < 392 && mouse_y > 750 && mouse_y < 814)
				{

					// ratebatch.draw(vibeoff, 243, 850, 64, 64);
					googleServices.rateGame();

					if (!rated)
					{
						rated = true;
						googleServices.unlockAchievementGPGS("CgkI5PT2i-wWEAIQFw");
						carpan *= 2;
					}
					Preferences prefs = Gdx.app.getPreferences("ady");
					prefs.putBoolean("rated", true);
					prefs.flush();

				} else if (mouse_x > 243 && mouse_x < 298 && mouse_y > 850 && mouse_y < 914)
				{
					vibrate = !vibrate;
				} else
				{
					ball.changeSide();
					gamestart = false;
					// ball.y = 30;
					tapped++;
				}
			} else
			{
				// batch.draw(rate, 446, 750, 64, 64);
				// batch.draw(ach, 30, 745, 64, 64);
				if (mouse_x > 30 && mouse_x < 96 && mouse_y > 745 && mouse_y < 809)
				{

					// ach
					googleServices.get_ach();

				} else if (mouse_x > 446 && mouse_x < 510 && mouse_y > 750 && mouse_y < 814)
				{

					googleServices.rateGame();
					if (!rated)
					{
						rated = true;
						googleServices.unlockAchievementGPGS("CgkI5PT2i-wWEAIQFw");
						carpan *= 2;
					}
					Preferences prefs = Gdx.app.getPreferences("ady");
					prefs.putBoolean("rated", true);
					prefs.flush();
				} else
				{
					ball.changeSide();
					gamestart = false;
					// ball.y = 30;
				}
			}

		}
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount)
	{
		// TODO Auto-generated method stub

		return false;
	}

	public void resize(int width, int height)
	{
		render();
	}
}
