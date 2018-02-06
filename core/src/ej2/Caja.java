package ej2;

import com.badlogic.gdx.math.Vector3;

public class Caja extends Elemento3D {

    public float velocidadeRotar;
	public Vector3 eixeRotar;
    private float anguloRotacion;

    public final float POSINICALX = -150;
    public final float POSFINALX = 150;


    public Caja(Vector3 pos, float escala, Vector3 velocidade, float velocidadeRotar, Vector3 eixeRotar) {
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
