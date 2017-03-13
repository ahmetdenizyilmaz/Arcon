package com.denip.arcon;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.denip.arcon.DesktopGoogleServices;
import com.denip.arcon.Pow;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width=540;
		config.height=960;
		 config.resizable = false;
		 System.setProperty("org.lwjgl.opengl.Window.undecorated", "true");
		new LwjglApplication(new Pow(null,new DesktopGoogleServices()), config);
	}
}
