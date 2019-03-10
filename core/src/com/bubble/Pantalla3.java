package com.bubble;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.bubble.Entidades.Enemigo1;
import com.bubble.Entidades.Enemigo2;
import com.bubble.Entidades.Jugador;
import com.bubble.Entidades.MarcoIzqDer;
import com.bubble.Entidades.MarcoSuperiorInferior;
import com.bubble.Entidades.Meta;
import com.bubble.Entidades.Pincho1;
import com.bubble.Entidades.Pincho2;
import com.bubble.Entidades.Suelo1;

import java.util.ArrayList;
import java.util.List;

import static com.bubble.Constantes.ALTO;
import static com.bubble.Constantes.ANCHO;

/**
 * Created by Daniel on 14/03/2016.
 */
public class Pantalla3 extends PantallaBase {

    private Stage       stage;          //Escenario de la pantalla
    private World       world;          //Mundo box2d de la pantalla
    private Skin        skin;           //Skin para el temporizador
    private TextField   temporizador;   //Texto para el temporizador
    private Image       fondo;          //Fondo de pantalla

    //Elementos de la pantalla

    private Jugador                     jugador;
    private List<Suelo1>                suelos1  = new ArrayList<Suelo1>();
    private List<MarcoSuperiorInferior> marcosSI = new ArrayList<MarcoSuperiorInferior>();
    private List<MarcoIzqDer>           marcosID = new ArrayList<MarcoIzqDer>();
    private List<Pincho1>               pinchos1  = new ArrayList<Pincho1>();
    private List<Pincho2>               pinchos2  = new ArrayList<Pincho2>();
    private Enemigo1                    enemigo1;
    private Enemigo2                    enemigo2;
    private Enemigo2                    enemigo3;
    private Enemigo1                    enemigo4;
    private Meta                        meta;
    private String                      cadenaTiempo;
    private int                         tiempo;
    private Sound                       muerte;
    private boolean                     fin;




    public Pantalla3(final Juego game) {

        super(game);
        stage        = new Stage(new ExtendViewport(ANCHO, ALTO));
        world        = new World(new Vector2(0,0),true);
        skin         = new Skin(Gdx.files.internal("skin/uiskin.json"));
        temporizador = new TextField("00:00",skin);
        fondo        = new Image(game.getManager().get("fondo.png",Texture.class));
        muerte       = game.getManager().get("sound/muerte.ogg");
        fin          = false;

        //Definición de caracteristicas de algunos elementos del stage.

        temporizador.setSize(48, 15);
        temporizador.setPosition(298, 343);
        fondo.setPosition(0, 0);
        fondo.setSize(ANCHO, ALTO);

        //Gestor de colisiones de la pantalla

        world.setContactListener(new ContactListener() {

            private boolean hanColisionado(Contact contacto, Object usuarioA, Object usuarioB) {

                return (contacto.getFixtureA().getUserData().equals(usuarioA) && contacto.getFixtureB().getUserData().equals(usuarioB))
                        || (contacto.getFixtureA().getUserData().equals(usuarioB) && contacto.getFixtureB().getUserData().equals(usuarioA));
            }

            @Override
            public void beginContact(Contact contact) {

                if (hanColisionado(contact, "jugador", "enemigo1")) {
                    if(fin==false) {
                        fin = true;
                        muerte.play();
                        game.getMusica().stop();
                        pantallaFinPartida();
                    }
                }
                if (hanColisionado(contact, "jugador", "enemigo2")) {
                    if(fin==false) {
                        fin = true;
                        muerte.play();
                        game.getMusica().stop();
                        pantallaFinPartida();
                    }
                }
                if (hanColisionado(contact, "jugador", "pincho1")) {
                    if(fin==false) {
                        fin = true;
                        muerte.play();
                        game.getMusica().stop();
                        pantallaFinPartida();
                    }
                }
                if (hanColisionado(contact, "jugador", "pincho2")) {
                    if(fin==false) {
                        fin = true;
                        muerte.play();
                        game.getMusica().stop();
                        pantallaFinPartida();
                    }
                }
                if (hanColisionado(contact, "jugador", "meta")) {
                    if(fin==false) {
                        fin = true;
                        game.settAcumulado(game.gettAcumulado() + (tiempo - game.getTemporizador().getSegundos()));
                        pantallaSiguiente();
                    }
                }
            }

            @Override
            public void endContact(Contact contact) {
            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {
            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {
            }
        });
    }



    @Override
    public void show() {

        if(game.getDificultad()==1)tiempo = 60;     //Tiempo según dificultad
        else tiempo = 30;
        game.getTemporizador().Contar();             //Inicio temporizador

        //Carga de texturas para los actores

        Texture jugadorTexture  = game.getManager().get("jugador.png");
        Texture suelo1Texture   = game.getManager().get("suelo1.png");
        Texture suelo2Texture   = game.getManager().get("suelo2.png");
        Texture techo1Texture   = game.getManager().get("techo1.png");
        Texture marcoSITexture  = game.getManager().get("marcoSI.png");
        Texture marcoIDTexture  = game.getManager().get("marcoID.png");
        Texture enemigo1Texture = game.getManager().get("enemigo1.png");
        Texture enemigo2Texture = game.getManager().get("enemigo2.png");
        Texture pincho1Texture  = game.getManager().get("pincho1.png");
        Texture pincho2Texture  = game.getManager().get("pincho2.png");
        Texture metaTexture     = game.getManager().get("meta.png");


        //Definición de los actores

        jugador  = new Jugador(world,game,jugadorTexture,new Vector2(9,9));
        enemigo1 = new Enemigo1(world,enemigo1Texture,new Vector2(16.5f,4.5f));
        enemigo4 = new Enemigo1(world,enemigo1Texture,new Vector2(1.5f,4.5f));
        enemigo2 = new Enemigo2(world,enemigo2Texture,new Vector2(5.2f,7f));
        enemigo3 = new Enemigo2(world,enemigo2Texture,new Vector2(12.7f,7f));

        meta     = new Meta(world,metaTexture,new Vector2(9,1.5f));


        suelos1.add(new Suelo1(world,suelo2Texture,new Vector2(1.5f,2f)));
        suelos1.add(new Suelo1(world,suelo1Texture, new Vector2(9,5f)));
        suelos1.add(new Suelo1(world,suelo2Texture, new Vector2(16.5f,2f)));


        suelos1.add(new Suelo1(world,techo1Texture, new Vector2(18f,11)));
        suelos1.add(new Suelo1(world,techo1Texture, new Vector2(-0.5f,11)));

        marcosSI.add(new MarcoSuperiorInferior(world,marcoSITexture, new Vector2(9,10.5f)));
        marcosSI.add(new MarcoSuperiorInferior(world,marcoSITexture, new Vector2(9,-0.5f)));

        marcosID.add(new MarcoIzqDer(world,marcoIDTexture, new Vector2(-0.5f,5)));
        marcosID.add(new MarcoIzqDer(world,marcoIDTexture, new Vector2(18.3f,5)));

        pinchos2.add(new Pincho2(world,pincho2Texture,new Vector2(0.5f,7.5f)));
        pinchos2.add(new Pincho2(world,pincho2Texture,new Vector2(17.3f,7.5f)));


        //Se añaden los actores al stage


        stage.addActor(fondo);
        stage.addActor(jugador);

        for(Suelo1 suelo: suelos1){stage.addActor(suelo);}

        for(MarcoSuperiorInferior marco: marcosSI){stage.addActor(marco);}
        for(MarcoIzqDer marco: marcosID){stage.addActor(marco);}

        stage.addActor(enemigo1);
        stage.addActor(enemigo2);
        stage.addActor(enemigo3);
        stage.addActor(enemigo4);

        for(Pincho2 pincho : pinchos2){stage.addActor(pincho);}
        for(Pincho1 pincho : pinchos1){stage.addActor(pincho);}

        stage.addActor(meta);
        stage.addActor(temporizador);

    }

    @Override
    public void hide() {
        //Liberación y reset de caracteristicas del stage una vez termina
        fin = false;
        game.getTemporizador().Detener();
        stage.clear();
        jugador.detach();
        suelos1.clear();
        marcosSI.clear();
        marcosID.clear();
        enemigo1.detach();
        enemigo2.detach();
        enemigo3.detach();
        enemigo4.detach();
        pinchos2.clear();
        pinchos1.clear();
        meta.detach();

    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0.4f, 0.5f, 0.8f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if((tiempo - game.getTemporizador().getSegundos())<=0){     //Comprobación se no se ha terminado el tiempo
            game.getMusica().stop();                                //Si es así se salta a la pantalla final.
            pantallaFinPartida();
        }

        stage.act();
        world.step(delta, 6, 2);

        //Si se ha pulsado la pantalla se envia las coordonadas al actor jugador para moverse en consecuencia

        if(Gdx.input.isTouched()){
            Vector3 v3 = new Vector3(Gdx.input.getX(),Gdx.input.getY(),0f);
            stage.getCamera().unproject(v3);
            Vector2 v2 = new Vector2(v3.x,v3.y);
            jugador.Mover(v2);
        }


        // Se actualiza el contador de tiempo de la pantalla

        int segundos = tiempo - game.getTemporizador().getSegundos();
        if(segundos<=9) {
            cadenaTiempo = "00:0" + segundos;
        }else cadenaTiempo = "00:" + segundos;


        temporizador.setText(cadenaTiempo);

        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
        world.dispose();
    }

    //LLamadas a las siguientes pantallas

    private void pantallaSiguiente() {

        stage.addAction(
                Actions.sequence(
                        Actions.delay(0.5f),
                        Actions.run(new Runnable() {

                            @Override
                            public void run() {
                                game.setScreen(game.getPantallaF());
                            }
                        })
                )
        );
    }

    private void pantallaFinPartida() {

        stage.addAction(
                Actions.sequence(
                        Actions.delay(0.5f),
                        Actions.run(new Runnable() {

                            @Override
                            public void run() {
                                game.setScreen(game.getPantallaFP());
                            }
                        })
                )
        );
    }


}
