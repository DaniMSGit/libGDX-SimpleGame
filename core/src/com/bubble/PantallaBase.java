package com.bubble;

import com.badlogic.gdx.Screen;

/**
 * Created by Daniel on 14/03/2016.
 */

/* Clase básica que define una pantalla todas las pantallas heredaran de esta
   clase*/

public class PantallaBase implements Screen {

    protected Juego game;


    public PantallaBase(Juego game){
        this.game = game;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        //Delta almacena el tiempo entre llamadas a render
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

}
