package com.bubble;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;

import static com.bubble.Constantes.ALTO;
import static com.bubble.Constantes.ANCHO;


/**
 * Created by Daniel on 14/03/2016.
 */


public class PantallaFinal extends PantallaBase{


    private Stage       stage;          //Escenario de la pantalla
    private Skin        skin;           //Skin para los botones
    private Image       IPantallaFinal; //Fondo de pantalla
    private FitViewport viewport;       //Viewport para el stage
    private TextArea   nombre;          //Area de texto para las puntuaciones
    private TextButton  bVolver;        //Botón para volver a inicio
    private Sound       sonido;         //sonido celebración

    public PantallaFinal(final Juego game) {

        super(game);
        viewport         = new FitViewport(ANCHO, ALTO);
        stage            = new Stage(viewport);
        skin             = new Skin(Gdx.files.internal("skin/uiskin.json"));
        IPantallaFinal   = new Image(game.getManager().get("pantallaFinal.png", Texture.class));
        nombre           = new TextArea("INICIALES",skin);
        bVolver          = new TextButton("INICIO", skin);
        sonido           = game.getManager().get("sound/final.ogg",Sound.class);

        //Definición de caracteristicas de los actores y elementos del stage.

        IPantallaFinal.setPosition(0, 0);
        IPantallaFinal.setSize(ANCHO, ALTO);
        nombre.setSize(80, 30);
        nombre.setMaxLength(3);
        nombre.setPosition(270, 140);
        bVolver.setSize(80, 40);
        bVolver.setPosition(270, 50);
        bVolver.setDisabled(true);

        //Eventos del botón volver y el area de texto

        bVolver.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(game.getPantallaI());
            }
        });

        nombre.setOnscreenKeyboard(new TextField.OnscreenKeyboard() {
            @Override
            public void show(boolean visible) {
                //Se guarda la puntuación en la lista facil o avanzado y se muestra por pantalla
                Gdx.input.getTextInput(new Input.TextInputListener(){
                    @Override
                    public void input(String text){
                        if(text.length()== 3){
                            bVolver.setDisabled(false);
                            nombre.setSize(100, 110);
                            nombre.setPosition(260, 105);
                            if(game.getDificultad()==1) {
                                game.getListapF().actulizar(new Puntuacion(text, game.gettAcumulado()));
                                nombre.setMaxLength(1000);
                                nombre.setText(game.getListapF().mostrar());
                            }else{
                                game.getListapD().actulizar(new Puntuacion(text, game.gettAcumulado()));
                                nombre.setMaxLength(1000);
                                nombre.setText(game.getListapD().mostrar());
                            }
                            nombre.setDisabled(true);
                        }else bVolver.setDisabled(true);
                    }

                    @Override
                    public void canceled(){}
                }, "Introduce tus iniciales","","");
            }
        });

        //Se añaden los actores al escenario

        stage.addActor(IPantallaFinal);
        stage.addActor(nombre);
        stage.addActor(bVolver);
    }

    @Override
    public void show() {
        game.getMusica().stop();               //Se para la banda sonora
        sonido.play();                         //Se reproduce sonido gameover
        Gdx.input.setInputProcessor(stage);

    }

    @Override
    public void hide() {
        sonido.stop();                        //Se reinician caracteristicas de la pantalla
        nombre.setMaxLength(1000);
        nombre.setDisabled(false);
        nombre.setSize(80, 30);
        nombre.setPosition(270, 140);
        nombre.setText("INICIALES");
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
