package com.bubble;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;

import static com.bubble.Constantes.ALTO;
import static com.bubble.Constantes.ANCHO;


/**
 * Created by Daniel on 14/03/2016.
 */




public class PantallaOpciones extends PantallaBase{


    private Stage       stage;           //Escenario de la pantalla
    private Skin        skin;            //Skin para los botones
    private Image       IPantallaInicial;//Fondo de pantalla
    private TextButton  BVolver;         //Botón para volver a pantalla de inicio
    private FitViewport viewport;        //Viewport para el stage
    private CheckBox    Cdnormal;        //Checbox para seleccionar la dificultad normal
    private CheckBox    Cdavanzado;      //Checbox para seleccionar la dificultad díficil
    private Sound       sonidoBoton;

    public PantallaOpciones(final Juego game) {

        super(game);
        viewport = new FitViewport(ANCHO, ALTO);
        stage = new Stage(viewport);
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        IPantallaInicial = new Image(game.getManager().get("pantallaOpciones.png", Texture.class));
        BVolver = new TextButton("Volver",skin);
        BVolver.setColor(Color.LIGHT_GRAY);
        Cdnormal   = new CheckBox(null,skin);
        Cdavanzado = new CheckBox(null,skin);
        sonidoBoton = game.getManager().get("sound/movimiento.ogg", Sound.class);


        //Eventos del botón volver y los check de dificultad

        BVolver.addCaptureListener(new ChangeListener() {

            @Override
            public void changed(ChangeEvent event, Actor actor) {
                sonidoBoton.play();
                if (Cdnormal.isChecked() || Cdavanzado.isChecked()) {
                    game.setScreen(game.getPantallaI());
                }
            }
        });

        Cdnormal.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

                game.setDificultad(1);
                Cdavanzado.setChecked(false);

            }
        });

        Cdavanzado.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

                game.setDificultad(2);
                Cdnormal.setChecked(false);

            }
        });


        //Definición de caracteristicas de los actores y elementos del stage.

        IPantallaInicial.setPosition(0, 0);
        IPantallaInicial.setSize(ANCHO, ALTO);
        BVolver.setSize(80, 40);
        BVolver.setPosition(270, 90);

        if(game.getDificultad()== 1)Cdnormal.setChecked(true);
        else Cdnormal.setChecked(false);
        Cdnormal.setPosition(292, 183);

        if(game.getDificultad()== 2)Cdavanzado.setChecked(true);
        else Cdavanzado.setChecked(false);
        Cdavanzado.setPosition(432, 184);

        //Se añaden los actores al escenario

        stage.addActor(IPantallaInicial);
        stage.addActor(BVolver);
        stage.addActor(Cdnormal);
        stage.addActor(Cdavanzado);


    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.4f, 0.5f, 0.8f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        stage.getCamera().update();
    }
}
