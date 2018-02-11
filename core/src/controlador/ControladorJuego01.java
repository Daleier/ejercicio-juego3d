package controlador;

import modelo.Mundo01;
import modelo.Elemento3D;

public class ControladorJuego01 {

    Mundo01 meuMundo;

    public ControladorJuego01(Mundo01 meuMundo) {
        this.meuMundo=meuMundo;
    }

    public void update(float delta) {
        controlarEnemigo(delta);
        controlarSuelo(delta);
        controlarNave(delta);
    }

    private void controlarNave(float delta) {
        this.meuMundo.getNave().update(delta);
        if ((this.meuMundo.getNave().posicion.x >= 100) | (this.meuMundo.getNave().posicion.x <= -100)) {
            this.meuMundo.getNave().velocidade.x = -1 * this.meuMundo.getNave().velocidade.x;
        }
    }

    private void controlarSuelo(float delta) {
        for (Elemento3D s : this.meuMundo.getSuelos()) {
            s.update(delta);
        }
        Elemento3D ultimo = this.meuMundo.getSuelos().get(this.meuMundo.getSuelos().size() - 1);
        if (ultimo.posicion.z < 0) {
            ultimo.posicion.z = 350f;
            this.meuMundo.getSuelos().add(0, ultimo);
            this.meuMundo.getSuelos().remove(this.meuMundo.getSuelos().size() - 1);
        }
    }

    private void controlarEnemigo(float delta) {
        for (Elemento3D e : this.meuMundo.getEnemigos()) {
            e.update(delta);
        }
        Elemento3D ultimo = this.meuMundo.getEnemigos().get(this.meuMundo.getEnemigos().size() - 1);
        if (ultimo.posicion.z < 0) {
            ultimo.posicion.z = 350f;
            this.meuMundo.getEnemigos().add(0, ultimo);
            this.meuMundo.getEnemigos().remove(this.meuMundo.getEnemigos().size() - 1);
        }
    }

}
