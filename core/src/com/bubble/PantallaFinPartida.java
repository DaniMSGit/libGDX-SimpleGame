package com.bubble;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
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


public class PantallaFinPartida extends PantallaBase{


    private Stage       stage;          //Escenario de la pantalla
    private Skin        skin;           //Skin para los botones
    private Image       IPantallaFinal; //Fondo de pantalla
    private FitViewport viewport;       //Viewport para el stage
    private TextButton  bVolver;        //Botón para volver a inicio
    private Sound       sonido;         //sonido fin del juego


    public PantallaFinPartida(final Juego game) {

        super(game);
        viewport         = new FitViewport(ANCHO, ALTO);
        stage            = new Stage(viewport);
        skin             = new Skin(Gdx.files.internal("skin/uiskin.json"));
        IPantallaFinal   = new Image(game.getManager().get("pantallaFinPartida.png", Texture.class));
        bVolver          = new TextButton("INICIO", skin);
        sonido           = game.getManager().get("sound/gameover.ogg");

        //Definición de caracteristicas de los actores y elementos del stage.

        IPantallaFinal.setPosition(0, 0);
        IPantallaFinal.setSize(ANCHO, ALTO);
        bVolver.setSize(80, 40);
        bVolver.setPosition(280, 80);

        //Evento del botón volver.

        bVolver.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(game.getPantallaI());
            }
        });

        stage.addActor(IPantallaFinal);
        stage.addActor(bVolver);
    }

    @Override
    public void show() {
        sonido.play();
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        skin.dispose();
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
