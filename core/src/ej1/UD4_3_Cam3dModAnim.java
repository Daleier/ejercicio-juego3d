package ej1;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.math.Vector3;

public class UD4_3_Cam3dModAnim extends Game {
	private PerspectiveCamera camara3d;
    private ModelBatch modelBatch;
    private Environment environment;
    private ModelInstance instanceCaja;

    private Caja caja;


    @Override
    public void create() {
        //Cargar assets
        AssetManager assets = new AssetManager();
//        assets.load("modelos/crate/crate1.obj", Model.class);
        assets.load("modelos/wolf/wolf_obj.obj", Model.class);
        assets.finishLoading();
        //Crear modelo
//        Model modelCaja = assets.get("modelos/crate/crate1.obj", Model.class);
        Model modelCaja = assets.get("modelos/wolf/wolf_obj.obj", Model.class);

        //Crear instancia del modelo
        this.instanceCaja = new ModelInstance(modelCaja);
        //Instanciar la cámara, modelBatch y environment
        camara3d = new PerspectiveCamera();
        modelBatch = new ModelBatch();
        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
        environment.add(new DirectionalLight().set(1f, 1f, 1f, 1f, 0f, 0f));
        //Iniciar el array de cajas
        this.iniciarCajas();
    }

	void iniciarCajas() {
		caja = new Caja(new Vector3(0, 0, 0f), 45f, new Vector3(0f, 0, 200), 0f, new Vector3(0, 0, 0));
	}

    private void controlarCaja(Caja caja, float delta) {
        caja.update(delta);
       /* if ((caja.posicion.z >= 500) | (caja.posicion.z <= -500)) {
            caja.velocidade.z = -1 * caja.velocidade.z;
        }*/
    }
    @Override
    public void render() {
        Gdx.gl20.glClearColor(0.3f, 0.27f, 0.6f, 1f);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        Gdx.gl20.glEnable(GL20.GL_DEPTH_TEST);
        //Actualizar las cajas
		this.controlarCaja(caja,Gdx.graphics.getDeltaTime());
		camara3d.lookAt(caja.posicion);
		camara3d.update();
        modelBatch.begin(camara3d);
		this.instanceCaja.transform.set(caja.matriz);
		modelBatch.render(instanceCaja, environment);
        modelBatch.end();

        Gdx.gl20.glDisable(GL20.GL_DEPTH_TEST);
    }

    @Override
    public void resize(int width, int height) {
        // Se definen los parámetros de la cámara
        float aspectRatio = (float) width / (float) height;
        camara3d.viewportWidth = aspectRatio * 1f;
        camara3d.viewportHeight = 1f;
        camara3d.position.set(0, 0, 400f);
        camara3d.far = 5000f;
        camara3d.near = 0.1f;
        camara3d.lookAt(caja.posicion);
        camara3d.update();
    }

    @Override
    public void dispose() {
        modelBatch.dispose();
    }
}
