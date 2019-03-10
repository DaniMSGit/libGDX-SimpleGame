package com.bubble.Entidades;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.bubble.Constantes;

/**
 * Created by Daniel on 14/03/2016.
 */
public class Enemigo2 extends Actor {

    private Texture texture;
    private World   world;
    private Body    body;
    private Fixture fixture;
    private float   limiteMovientoA;
    private float   limiteMovientoAb;
    private int     sentido;

    public Enemigo2(World world, Texture texture, Vector2 position){

        this.world = world;
        this.texture = texture;

        //Definición del cuerpo

        BodyDef def = new BodyDef();
        def.position.set(position);
        def.type = BodyDef.BodyType.KinematicBody;
        body = world.createBody(def);

        //Defición del area para las colisiones

        CircleShape shape = new CircleShape();
        shape.setRadius(1);
        fixture = body.createFixture(shape, 1);
        fixture.setUserData("enemigo2");
        shape.dispose();

        setSize(2* Constantes.PIXELS_EN_METROS,2* Constantes.PIXELS_EN_METROS);

        limiteMovientoA = position.y + 2f;
        limiteMovientoAb = position.y - 2f;
        sentido = 1;
    }

    @Override
    public void act(float delta) {

        //Movimiento del objeto

        float y = body.getPosition().y;

        if(y > limiteMovientoAb && sentido==1) {
            body.setLinearVelocity(0, -1);
        }else{
            sentido = 0;
        }

        if(y < limiteMovientoA && sentido==0) {
            body.setLinearVelocity(0, 1);
        }else{
            sentido = 1;
        }



    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        setPosition((body.getPosition().x - 1)* Constantes.PIXELS_EN_METROS,
                   ((body.getPosition().y - 1)* Constantes.PIXELS_EN_METROS));
        batch.draw(texture, getX(), getY(), getWidth(), getHeight());

    }



    public void Mover(Vector2 position){

        Vector2 bodyposition = body.getPosition();
        bodyposition.set(body.getPosition().x * Constantes.PIXELS_EN_METROS, body.getPosition().y * Constantes.PIXELS_EN_METROS);
        Vector2 force = new Vector2();
        float strength = 30f;
        force.set(position).sub(bodyposition).nor().scl(strength);
        body.applyForceToCenter(force,true);

    }

    public void detach(){
        body.destroyFixture(fixture);
        world.destroyBody(body);
    }

}
