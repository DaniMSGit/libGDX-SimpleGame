package com.bubble;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
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

public class PantallaInicial extends PantallaBase{


    private Stage       stage;                  //Escenario de la pantalla
    private Skin        skin;                   //Skin para los botones
    private Image       IPantallaInicial;       //Fondo de pantalla
    private TextButton  BComenzar;              //Botón para comenzar la partida
    private TextButton  BOpciones;              //Botón para ir a opciones
    private FitViewport viewport;               //Viewport para el stage
    private Sound       sonidoBoton;

    public PantallaInicial(final Juego game) {
        super(game);
        viewport         = new FitViewport(ANCHO, ALTO);
        stage            = new Stage(viewport);
        skin             = new Skin(Gdx.files.internal("skin/uiskin.json"));
        IPantallaInicial = new Image(game.getManager().get("pantallaInicial.png", Texture.class));
        BComenzar        = new TextButton("Comenzar",skin);
        BOpciones        = new TextButton("Opciones",skin);
        sonidoBoton      = game.getManager().get("sound/movimiento.ogg", Sound.class);

        //Eventos de los botones para comenzar el juego o ir a opcines

        BComenzar.addCaptureListener(new ChangeListener() {

            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.settAcumulado(0);  //Reset tiempo acumulado
                sonidoBoton.play();
                game.setScreen(game.getPantalla1());
            }
        });

        BOpciones.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                sonidoBoton.play();
                game.setScreen(game.getPantallaO());
            }
        });


        //Definición de caracteristicas de los actores y elementos del stage.

        IPantallaInicial.setPosition(0, 0);
        IPantallaInicial.setSize(ANCHO, ALTO);
        BComenzar.setColor(Color.LIGHT_GRAY);
        BOpciones.setColor(Color.LIGHT_GRAY);
        BComenzar.setSize(80, 40);
        BOpciones.setSize(80, 40);
        BComenzar.setPosition(270,140);
        BOpciones.setPosition(270,90);

        //Se añaden los actores al escenario

        stage.addActor(IPantallaInicial);
        stage.addActor(BComenzar);
        stage.addActor(BOpciones);

    }

    @Override
    public void show() {
        //Cuando arranca la pantalla
        game.getMusica().play();            //Reproduce la música
        Gdx.input.setInputProcessor(stage); //Se asigna al gestor de entrada el escenario actual
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null); //Se desasigna al gestor de entrada el escenario actual
    }

    @Override
    public void dispose() {
        skin.dispose();                    //Se elemina elementos del stage
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
