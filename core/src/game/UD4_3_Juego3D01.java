package game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;

import controlador.ControladorJuego01;
import modelo.Elemento3D;
import modelo.Mundo01;

public class UD4_3_Juego3D01 extends Game implements InputProcessor {
    private Mundo01 meuMundo;

    private PerspectiveCamera camara3d;

    private ModelBatch modelBatch;
    private Environment environment;
    private ModelInstance instanceNave, instanceSuelo, instanceEnemigo;

    private ControladorJuego01 controladorJuego01;

    @Override
    public void create() {
        // TODO Auto-generated method stub
        this.meuMundo = new Mundo01();

        AssetManager assets = new AssetManager();
        assets.load("modelos/floor/floor.obj", Model.class);
        assets.load("modelos/f-117/f-117_nighthawk.obj", Model.class);
        assets.load("modelos/fish/fish.obj", Model.class);
        assets.finishLoading();

        Model modelSuelo = assets.get("modelos/floor/floor.obj", Model.class);
        Model modelNave = assets.get("modelos/f-117/f-117_nighthawk.obj", Model.class);
        Model modelEnemigo = assets.get("modelos/fish/fish.obj", Model.class);

        camara3d = new PerspectiveCamera();
        modelBatch = new ModelBatch();
        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
        environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, 0f, -1f, 1f));

        this.instanceNave = new ModelInstance(modelNave);
        this.instanceSuelo = new ModelInstance(modelSuelo);
        this.instanceEnemigo = new ModelInstance(modelEnemigo);

        this.controladorJuego01 = new ControladorJuego01(this.meuMundo);
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render() {

        Gdx.gl20.glClearColor(0f, 0.3f, 0.4f, 1f);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        Gdx.gl20.glEnable(GL20.GL_DEPTH_TEST);

        controladorJuego01.update(Gdx.graphics.getDeltaTime());

        modelBatch.begin(camara3d);

        this.instanceNave.transform.set(this.meuMundo.getNave().matriz);
        modelBatch.render(instanceNave, environment);

        for (Elemento3D s : this.meuMundo.getSuelos()) {
            this.instanceSuelo.transform.set(s.matriz);
            modelBatch.render(instanceSuelo, environment);
        }
        for (Elemento3D e : this.meuMundo.getEnemigos()) {
            this.instanceEnemigo.transform.set(e.matriz);
            modelBatch.render(instanceEnemigo, environment);
        }


        modelBatch.end();

        Gdx.gl20.glDisable(GL20.GL_DEPTH_TEST);

    }

    @Override
    public void resize(int width, int height) {
        float aspectRatio = (float) width / (float) height;
        camara3d.viewportWidth = aspectRatio * 1f;
        camara3d.viewportHeight = 1f;
        camara3d.far = 500f;
        camara3d.near = 0.1f;
        camara3d.position.set(0, 90f, -50f);
        camara3d.lookAt(0, 60, 0);
        camara3d.update();
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void dispose() {
        modelBatch.dispose();
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {

        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }


    public void pause() {
        // TODO Auto-generated method stub
        Gdx.input.setInputProcessor(null);
    }

    public void resume() {
        // TODO Auto-generated method stub
        Gdx.input.setInputProcessor(this);
    }

}
