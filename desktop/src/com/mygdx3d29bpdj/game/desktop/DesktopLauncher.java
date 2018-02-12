package com.mygdx3d29bpdj.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import game.UD4_1_Cam3dModAnim;
import game.UD4_2_ColFueRay;
import game.UD4_3_Juego3D01;
import game.UD4_3_Juego3D02;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new UD4_3_Juego3D02(), config);
	}
}
