package com.mygdx3d29bpdj.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx3d29bpdj.game.MyGdxGame;
import com.mygdx3d29bpdj.game.UD4_1_Cam3dModAnim;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new UD4_1_Cam3dModAnim(), config);
	}
}
