package modelo;

import com.badlogic.gdx.math.Vector3;

import modelo.Elemento3D;

/**
 * Created by dam203 on 08/02/2018.
 */

public class Terra extends Elemento3D{

	public float velocidadeRotar;
	public Vector3 eixeRotar;
	private float anguloRotacion;

	public final float POSINICALX = -400;
	public final float POSFINALX = 400;


	public Terra(Vector3 pos, float escala, Vector3 velocidade, float velocidadeRotar, Vector3 eixeRotar) {
		super(pos, escala, velocidade);
		this.velocidadeRotar = velocidadeRotar;
		this.eixeRotar = eixeRotar;

	}

	public void update(float delta) {

		super.update(delta);

		anguloRotacion += delta * velocidadeRotar;
		matriz.rotate(eixeRotar, anguloRotacion);


	}

}
