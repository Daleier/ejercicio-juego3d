package ej2;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Sphere;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;
import ej1.Elemento3D;

public class UD4_2_ColFue extends Game implements InputProcessor {
	public static enum TIPOS_VIDA{ACIERTOS,FALLOS};
	private Array<TIPOS_VIDA> numVidas;

    private ArrayList<Elemento3D> naves;
	private Terra terra;
	private float velocidadNave = 500f;

    private PerspectiveCamera camara3d;

    //Declarar una esfera para la tierra.
    public Sphere esferaTierra;
    //Declarar una esfera para una nave.
    public Sphere esferaNave;

    /*Nuevos atributos*/
    private ModelBatch modelBatch;
    private Environment environment;
    private ModelInstance instanceNave;
    private ModelInstance instanceTierra;
    //fUENTES
	private SpriteBatch spritebatch;
	private BitmapFont bitMapFont;
	private StringBuilder sbufferAciertos;
	private StringBuilder sbufferFallos;
	private StringBuilder sbufferIdentificador;

	private float proporcionAncho;
	private float proporcionAlto;
	private int pantallaAncho;
	private int pantallaAlto;

    @Override
    public void create() {
		numVidas = new Array<TIPOS_VIDA>();

		//recursos para mostrar en pantalla
		this.pantallaAlto=Gdx.graphics.getHeight();
		this.pantallaAncho=Gdx.graphics.getWidth();
		sbufferAciertos = new StringBuilder();
		sbufferAciertos.append("Aciertos: 0");
		sbufferFallos= new StringBuilder();
		sbufferFallos.append("Fallos: 0");
		sbufferIdentificador = new StringBuilder();
		sbufferIdentificador.append("29BPDJ");
		spritebatch = new SpriteBatch();

        AssetManager assets = new AssetManager();
        assets.load("modelos/ship/ship.obj", Model.class);
        assets.load("modelos/tierra/tierra.obj", Model.class);
        assets.finishLoading();

        Model modelNave = assets.get("modelos/ship/ship.obj", Model.class);
        Model modelTierra = assets.get("modelos/tierra/tierra.obj", Model.class);

        this.instanceNave = new ModelInstance(modelNave);
        this.instanceTierra = new ModelInstance(modelTierra);

        camara3d = new PerspectiveCamera();
        modelBatch = new ModelBatch();
        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
        environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, 0f, -1f, -1f));

        this.iniciarNaves();
        this.terra = new Terra(new Vector3(0, 0, 100f), 25f, new Vector3(100f, 0, 0), 100f, new Vector3(0, 1, 1));

        //Instanciar la esfera de la tierra a partir de la posición y escala de la tierra.
        esferaTierra = new Sphere(terra.posicion,terra.escala);
        //Instanciar la esfera de la nave a partir de la posición y escala de la nave que ocupa la primera posición del arrayList
		esferaNave = new Sphere(this.naves.get(0).posicion,this.naves.get(0).escala);
        //Establecer el propio objeto de la clase (this) como listener de eventos de entrada de pantalla y teclado.
		Gdx.input.setInputProcessor(this);
    }

    void iniciarNaves() {
        naves = new ArrayList<Elemento3D>();
        naves.add(new Elemento3D(new Vector3(-380f, 0f, -250f), 20f, new Vector3(0, 0, 0)));
        naves.add(new Elemento3D(new Vector3(-250f, 0f, -250f), 20f, new Vector3(0, 0, 0)));
        naves.add(new Elemento3D(new Vector3(-120f, 0f, -250f), 20f, new Vector3(0, 0, 0)));
        naves.add(new Elemento3D(new Vector3(+10f, 0f, -250f), 20f, new Vector3(0, 0, 0)));
        naves.add(new Elemento3D(new Vector3(+140f, 0f, -250f), 20f, new Vector3(0, 0, 0)));
        naves.add(new Elemento3D(new Vector3(+270f, 0f, -250f), 20f, new Vector3(0, 0, 0)));
        naves.add(new Elemento3D(new Vector3(+400f, 0f, -250f), 20f, new Vector3(0, 0, 0)));
    }

    private void controlarTerra(float delta) {
        //Actualizar la tierra
		terra.update(delta);
        //Cambiar de signo la velocidad de la tierra cada vez que se alcanza un límite de pantalla
		if(terra.posicion.x >= terra.POSFINALX || terra.posicion.x <= terra.POSINICALX){
			terra.velocidade.x = -1 * terra.velocidade.x;
		}
    }

    private void controlarNaves(float delta) {
        //Actualizar cada una de las naves del ArrayList
		for( Elemento3D i : naves){
			i.update(delta);
		}
        //Control de naves que caen y colisiones
        if (!this.naves.isEmpty()) {
            //posicionar el centro de la esfera de la Tierra en la posición del modelo Tierra.
			esferaTierra.center.set(terra.posicion);
            //posicionar el centro de la esfera de la nave en la posición de la primera nave del array de naves.
			esferaNave.center.set(naves.get(0).posicion);

            if (this.esferaNave.overlaps(this.esferaTierra)){
                //Eliminar la nave del array de naves.
                this.naves.remove(this.naves.get(0));
            	numVidas.add(TIPOS_VIDA.ACIERTOS);
			}else if (this.naves.get(0).posicion.z > this.terra.posicion.z + 150) {
				//Eliminar la nave del array de naves.
				this.naves.remove(this.naves.get(0));
				numVidas.add(TIPOS_VIDA.FALLOS);
			}if (this.naves.isEmpty())
                //Iniciar de nuevo el array de naves.
				this.iniciarNaves();
        }
    }

    @Override
    public void render() {

        Gdx.gl20.glClearColor(0f, 0f, 0f, 1f);
		Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        Gdx.gl20.glEnable(GL20.GL_DEPTH_TEST);

        //Posicionar la cámara en (0,500,0)
		camara3d.lookAt(0,500,0);
        //Actuazliar la cámara
		camara3d.update();

        controlarTerra(Gdx.graphics.getDeltaTime());
        controlarNaves(Gdx.graphics.getDeltaTime());

        modelBatch.begin(camara3d);
        //Dibujar cada una de las naves del array de naves.
		for(Elemento3D i: naves){
			instanceNave.transform.set(i.matriz);
			modelBatch.render(instanceNave,environment);
		}
        //Dibujar la tierra.
		instanceTierra.transform.set(terra.matriz);
		modelBatch.render(instanceTierra,environment);
        modelBatch.end();

        sbufferAciertos.setLength(0);
        sbufferAciertos.append("Aciertos: "+this.getNumAciertos());
        sbufferFallos.setLength(0);
        sbufferFallos.append("Fallos: "+this.getNumFallos());

		spritebatch.begin();
		bitMapFont.setColor(Color.YELLOW);
		bitMapFont.draw(spritebatch, sbufferIdentificador,
				(this.pantallaAncho/2)*this.proporcionAncho-35, 20*this.proporcionAlto);
		bitMapFont.draw(spritebatch, sbufferAciertos,
				10+this.proporcionAncho, 20);
		bitMapFont.draw(spritebatch, sbufferFallos,
				this.pantallaAncho*this.proporcionAncho-120, 20*this.proporcionAlto);
		spritebatch.end();

        Gdx.gl20.glDisable(GL20.GL_DEPTH_TEST);
    }

    @Override
    public void resize(int width, int height) {
        float aspectRatio = (float) width / (float) height;
        camara3d.viewportWidth = aspectRatio * 1f;
        camara3d.viewportHeight = 1f;
        camara3d.far = 5000f;
        camara3d.near = 0.1f;
		//Posicionar la cámara en (0,500,0)
		camara3d.position.set(0,500,0);
		//Hacer que la cámar enfoque hacia el origen de coordenadas: (0,0,0)
		camara3d.lookAt(0,0,0);
		//Actuazliar la cámara
		camara3d.update();




        //Para las fuentes
		this.proporcionAncho= (float)(width/this.pantallaAncho);
		this.proporcionAlto= (float)(height/this.pantallaAlto);
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/dsdigit.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.size = (int)(25 * this.proporcionAncho);
		this.bitMapFont = generator.generateFont(parameter); // font size in pixels
		generator.dispose(); // don't forget to dispose to avoid memory leaks!
        //Establecer el propio objeto de la clase (this) como listener de eventos de entrada de pantalla y teclado.
		Gdx.input.setInputProcessor(this);

	}

    @Override
    public void dispose() {
        modelBatch.dispose();
        //Establecer a null el listener de eventos de entrada de pantalla y teclado.
		Gdx.input.setInputProcessor(null);
		//dispose recursos fuentes
		spritebatch.dispose();
		bitMapFont.dispose();
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
        //Asignar a la primera nave del array de naves una velocidad con una componente Z mayor que 0
		this.naves.get(0).velocidade.z = velocidadNave;
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
        //Establecer a null el listener de eventos de entrada de pantalla y teclado.
		Gdx.input.setInputProcessor(null);
	}

    public void resume() {
        //Establecer el propio objeto de la clase (this) como listener de eventos de entrada de pantalla y teclado.
		Gdx.input.setInputProcessor(this);
	}

	public int getNumAciertos(){
		int num=0;
		for (TIPOS_VIDA vida : numVidas){
			if (vida == TIPOS_VIDA.ACIERTOS) num++;
		}

		return num;
	}

	public int getNumFallos(){
		int num=0;
		for (TIPOS_VIDA vida : numVidas){
			if (vida == TIPOS_VIDA.FALLOS) num++;
		}

		return num;
	}

}
