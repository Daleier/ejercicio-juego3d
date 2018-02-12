package modelo;


import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;

import modelo.Elemento3D;
import modelo.Terra;

public class Mundo01 {
    private ArrayList<Elemento3D> suelos, enemigos;
    private Terra nave;

    public Mundo01() {
        this.nave = new Terra(new Vector3(0, 0, 100f), 1f, new Vector3(100f, 0, 0), 0, new Vector3(0, 0, 0));
        this.iniciarSuelo();
        this.iniciarEnemigo();
    }

    private void iniciarSuelo() {
        suelos = new ArrayList<Elemento3D>();
        for (int i=0; i<=7;i++)
            suelos.add(new Elemento3D(new Vector3(0f, -200f, (350f-i*50)), 300f, new Vector3(0, 0, -80f)));

    }

    private void iniciarEnemigo() {
        enemigos = new ArrayList<Elemento3D>();
        for (int i=0; i<=3;i++)
            enemigos.add(new Elemento3D(new Vector3(((float)Math.random()*200f-100), 20f, (350f-i*100)), 7f, new Vector3(0, 0, -80f)));
    }

    public ArrayList<Elemento3D> getSuelos() {
        return suelos;
    }

    public ArrayList<Elemento3D> getEnemigos() {
        return enemigos;
    }

    public Terra getNave() {
        return nave;
    }
}
