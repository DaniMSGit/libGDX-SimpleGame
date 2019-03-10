package com.bubble.Entidades;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

import static com.bubble.Constantes.PIXELS_EN_METROS;

/**
 * Created by Daniel on 14/03/2016.
 */
public class Pincho1 extends Actor {

    private Texture texture;
    private World   world;
    private Body    body;
    private Fixture fixture;

    public Pincho1(World world, Texture texture, Vector2 position){

        this.world = world;
        this.texture = texture;

        //Definición del cuerpo

        BodyDef def = new BodyDef();
        def.position.set(position);
        def.type = BodyDef.BodyType.StaticBody;
        body = world.createBody(def);

        //Defición del area para las colisiones

        Vector2[] vertices = new Vector2[3];
        vertices[0] = new Vector2(-0.50f,-1f);
        vertices[1] = new Vector2(0.50f,-1f);
        vertices[2] = new Vector2(0,1f);

        PolygonShape shape = new PolygonShape();
        shape.set(vertices);
        fixture = body.createFixture(shape,1);
        fixture.setUserData("pincho1");
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


    public void detach(){
        body.destroyFixture(fixture);
        world.destroyBody(body);
    }

}
