package com.bubble.Entidades;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.bubble.Juego;

import static com.bubble.Constantes.PIXELS_EN_METROS;

/**
 * Created by Daniel on 14/03/2016.
 */
public class Jugador extends Actor {

    private Texture texture;
    private World   world;
    private Body    body;
    private Fixture fixture;
    private Juego   game;
    private Sound   movimiento;
    private int     contador;

    public Jugador(World world,Juego game,Texture texture,Vector2 position){

        this.world = world;
        this.texture = texture;
        this.game = game;
        movimiento = game.getManager().get("sound/movimiento.ogg");
        contador = 0;

        //Definición del cuerpo

        BodyDef def = new BodyDef();
        def.position.set(position);
        def.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(def);

        //Defición del area para las colisiones

        CircleShape shape = new CircleShape();
        shape.setRadius(1);
        fixture = body.createFixture(shape, 1);
        fixture.setUserData("jugador");
        shape.dispose();

        setSize(2*PIXELS_EN_METROS,2*PIXELS_EN_METROS);

    }

    @Override
    public void act(float delta) {

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        setPosition((body.getPosition().x - 1)* PIXELS_EN_METROS,
                   ((body.getPosition().y - 1)* PIXELS_EN_METROS));
        batch.draw(texture, getX(), getY(), getWidth(), getHeight());

    }



    public void Mover(Vector2 position){

        if(contador==0) {           //Evita repeticion múltiple del sonido
            movimiento.play();
            contador++;
        }else if(contador==12){
            contador=0;
        }else{
            contador++;
        }

        // Con las coordenadas position y la coordenadas de objeto, se cálcula el vector de fuerza
        // que se aplica al objeto.
        Vector2 bodyposition = body.getPosition();
        bodyposition.set( body.getPosition().x*PIXELS_EN_METROS,body.getPosition().y*PIXELS_EN_METROS);
        Vector2 force = new Vector2();
        float strength;                                             //Longitud del vector
        if(game.getDificultad() == 1){strength = 10f;}              //Según dificultad
        else strength = 30f;
        force.set(position).sub(bodyposition).nor().scl(strength);  //Cáculo del vector posición
        body.applyForceToCenter(force,true);                        //Se aplica la fuerza al objeto

    }

    public void detach(){
        body.destroyFixture(fixture);
        world.destroyBody(body);
    }

}
