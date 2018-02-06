package com.mygdx3d29bpdj.game;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

import ej1.UD4_1_Cam3dModAnim;
import ej2.UD4_2_Cam3dModAnim;
import ej3.UD4_3_Cam3dModAnim;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new UD4_1_Cam3dModAnim(), config);
	}
}
