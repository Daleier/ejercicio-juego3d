package controlador;

import com.badlogic.gdx.math.Vector3;

import java.util.HashMap;

import modelo.Enemigo;
import modelo.Mundo02;
import modelo.Suelo;

public class ControladorJuego02 {

    Mundo02 meuMundo;

    //Control de la nave mediante teclado
    public enum Keys {
        ESQUERDA, DEREITA, ARRIBA, ABAIXO, ESPAZO
    }

    HashMap<Keys, Boolean> keys = new HashMap<Keys, Boolean>();

    {
        keys.put(Keys.ESQUERDA, false);
        keys.put(Keys.DEREITA, false);
        keys.put(Keys.ARRIBA, false);
        keys.put(Keys.ABAIXO, false);
        keys.put(Keys.ESPAZO, false);

    }

    ;
    private Vector3 auxTecla, auxDisparoVel, auxDisparoPos;


    public ControladorJuego02(Mundo02 meuMundo) {
        this.meuMundo = meuMundo;
        this.auxTecla = new Vector3(0, 0, 0);
        this.auxDisparoPos = new Vector3(0, 0, 0);
        this.auxDisparoVel = new Vector3(0, 0, 0);
    }

    public void update(float delta) {
        controlarNave(delta);
        controlarEnemigo(delta);
        controlarSuelo(delta);
        controlarDisparo(delta);
        procesarEntradas(delta);
    }

    private void controlarNave(float delta) {
        this.meuMundo.getNave().update(delta);

        //Detectar colisión de la nave con algún enemigo.
        for (Enemigo e : this.meuMundo.getEnemigos()) {
            if (this.meuMundo.getNave().getEsfera().overlaps(e.getEsfera())) {
                this.meuMundo.getNave().posicion.set(0, 40, 25);
                //Utiles.imprimirLog("ControladorJuego02","controlarNave", "Colisión de nave con enemigo");
            }
        }
    }

    private void controlarDisparo(float delta) {
        this.meuMundo.getDisparo().update(delta);
        //Utiles.imprimirLog("ControladorJuego02", "controlarDisparo", "Posición Sphere: " + this.meuMundo.getDisparo().getEsfera().center + " Radio: " + this.meuMundo.getDisparo().getEsfera().radius);
        if ((this.meuMundo.getDisparo().posicion.z > 250) && (this.meuMundo.getDisparo().getVelocidade().z > 0)) {
            this.auxDisparoVel.set(0, 0, 0);
            this.meuMundo.getDisparo().setVelocidade(this.auxDisparoVel);
            this.auxDisparoPos.set(0, 40, 25);
            this.meuMundo.getDisparo().posicion = this.auxDisparoPos;
        }
        //Colisión de disparo con enemigo
        for (Enemigo e : this.meuMundo.getEnemigos()) {
            if (this.meuMundo.getDisparo().getEsfera().overlaps(e.getEsfera())) {
                this.auxDisparoVel.set(0, 0, 0);
                this.meuMundo.getDisparo().setVelocidade(this.auxDisparoVel);
                this.auxDisparoPos.set(0, 40, 25);
                this.meuMundo.getDisparo().posicion = this.auxDisparoPos;
                //Hacer que el enemigo desaparezca de pantalla y se vuelva a regenerar.
                e.posicion.z = -150;
                //Utiles.imprimirLog("ControladorJuego02", "controlarDisparo", "Colisión de disparo  con enemigo");
            }
        }
    }

    private void controlarSuelo(float delta) {
        for (Suelo s : this.meuMundo.getSuelos()) {
            s.update(delta);
        }
        Suelo ultimo = this.meuMundo.getSuelos().get(this.meuMundo.getSuelos().size() - 1);
        if (ultimo.posicion.z < -500) {
            ultimo.posicion.z = 1200f;
            this.meuMundo.getSuelos().add(0, ultimo);
            this.meuMundo.getSuelos().remove(this.meuMundo.getSuelos().size() - 1);
        }
    }

    private void controlarEnemigo(float delta) {
        for (Enemigo e : this.meuMundo.getEnemigos()) {
            e.update(delta);
        }
        Enemigo ultimo = this.meuMundo.getEnemigos().get(this.meuMundo.getEnemigos().size() - 1);
        if (ultimo.posicion.z < 0) {
            ultimo.posicion.z = 850f;
            ultimo.posicion.x = ((float) Math.random() * 500f - 250);
            this.meuMundo.getEnemigos().add(0, ultimo);
            this.meuMundo.getEnemigos().remove(this.meuMundo.getEnemigos().size() - 1);
        }
    }

    private void procesarEntradas(float delta) {

        if (keys.get(ControladorJuego02.Keys.ESQUERDA)) {
            if (this.meuMundo.getNave().posicion.x < 100)
                this.meuMundo.getNave().setVelocidade(this.meuMundo.getNave().getVelocidadeMax());
            else {
                this.auxTecla.set(0, 0, 0);
                this.meuMundo.getNave().setVelocidade(this.auxTecla);
            }

        }
        if (keys.get(ControladorJuego02.Keys.DEREITA)) {
            if (this.meuMundo.getNave().posicion.x > -100) {
                this.auxTecla = this.meuMundo.getNave().getVelocidadeMax().cpy();
                this.auxTecla.x = -1 * this.auxTecla.x;
                this.meuMundo.getNave().setVelocidade(this.auxTecla);
            } else {
                this.auxTecla.set(0, 0, 0);
                this.meuMundo.getNave().setVelocidade(this.auxTecla);
            }

        }
        if (!(keys.get(ControladorJuego02.Keys.ESQUERDA)) && (!(keys.get(ControladorJuego02.Keys.DEREITA))))
            this.meuMundo.getNave().setVelocidade(new Vector3(0, 0, 0));

        if (keys.get(ControladorJuego02.Keys.ARRIBA))
            modificarVelocidadSueloEnemigos(-delta * 800);
        if (keys.get(ControladorJuego02.Keys.ABAIXO))
            modificarVelocidadSueloEnemigos(delta * 800);
        if (keys.get(ControladorJuego02.Keys.ESPAZO)) {
            if (meuMundo.getDisparo().posicion.z == 25) {
                this.meuMundo.getDisparo().posicion.set(this.meuMundo.getNave().posicion.cpy());
                this.meuMundo.getDisparo().setVelocidade(this.meuMundo.getDisparo().getVelocidadeMax());
            }
        }
    }

    void modificarVelocidadSueloEnemigos(float cantidad) {
        for (Suelo s : this.meuMundo.getSuelos()) {
            this.auxTecla = s.getVelocidade().cpy();
            this.auxTecla.z = this.auxTecla.z + cantidad;
            s.setVelocidade(this.auxTecla);
        }
        for (Enemigo e : this.meuMundo.getEnemigos()) {
            this.auxTecla = e.getVelocidade().cpy();
            this.auxTecla.z = this.auxTecla.z + cantidad;
            e.setVelocidade(this.auxTecla);
        }
    }

    public void pulsarTecla(ControladorJuego02.Keys tecla) {
        keys.put(tecla, true);
    }

    public void liberarTecla(ControladorJuego02.Keys tecla) {
        keys.put(tecla, false);
    }
}
