package com.bubble;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;


public class Juego extends Game {


	//Declaración de todos los elementos comunes del juego

	private  AssetManager            manager;
	private  PantallaInicial         pantallaI;
	private  PantallaFinal			 pantallaF;
	private  PantallaFinPartida	     pantallaFP;
	private  PantallaOpciones        pantallaO;
	private  Pantalla1               pantalla1;
	private  Pantalla2               pantalla2;
	private  Pantalla3               pantalla3;
	private  int 				     dificultad;
	private  Preferences	         preferencias;
	private  Tiempo				     temporizador;
	private  int					 tAcumulado;
	private  ListaPuntuaciones		 listapF;
	private  ListaPuntuaciones		 listapD;
	private  Music                   musica;

	//Métodos getter y setter de los elementos del juego

	public AssetManager       getManager()     {return manager;}
	public PantallaInicial    getPantallaI()   {return pantallaI;}
	public PantallaFinal      getPantallaF()   {return pantallaF;}
	public PantallaFinPartida getPantallaFP()  {return pantallaFP;}
	public PantallaOpciones   getPantallaO()   {return pantallaO;}
	public Pantalla1          getPantalla1()   {return pantalla1;}
	public Pantalla2          getPantalla2()   {return pantalla2;}
	public Pantalla3          getPantalla3()   {return pantalla3;}
	public int                getDificultad()  {return dificultad;}

	public Preferences        getPreferencias(){return preferencias;}
	public Tiempo             getTemporizador(){return temporizador;}
	public int		          gettAcumulado()  {return tAcumulado;}
	public ListaPuntuaciones  getListapF()     {return listapF;}
	public ListaPuntuaciones  getListapD()     {return listapD;}
	public Music			  getMusica()      {return musica;}


	public void settAcumulado(int tiempo) {this.tAcumulado = tiempo;}
	public void setDificultad(int dificultad) {this.dificultad = dificultad;}


	
	@Override
	public void create () {

		preferencias    = Gdx.app.getPreferences("preferencias");		//Objeto para guaradar datos del juego
		temporizador    = new Tiempo();									//Se usará para contabilizar el tiempo en la pantallas
		tAcumulado      = 0;											//Tiempo acumulado total en las partidas
		int d           = preferencias.getInteger("dificultad",-1);		//Lectura de preferencias, si no existe valor devuelto -1
		float v			= preferencias.getFloat("volumen",-1);

		if(d == -1){													//Establece dificultad en función de lo leído
			setDificultad(1);
		}else{
			setDificultad(preferencias.getInteger("dificultad"));
		}


		listapF = obtenerPuntuacionesF();								//Obtienen puntuacines en  dificultad fácil
		listapD = obtenerPuntuacionesD();								//Obtienen puntuacines en  dificultad difícil


		manager = new AssetManager();									//Manejar de texturas, sonidos, etcs

		manager.load("jugador.png", Texture.class);
		manager.load("pantallaInicial.png", Texture.class);
		manager.load("pantallaFinal.png", Texture.class);
		manager.load("pantallaFinPartida.png", Texture.class);
		manager.load("pantallaOpciones.png", Texture.class);
		manager.load("fondo.png",Texture.class);
		manager.load("suelo1.png",Texture.class);
		manager.load("suelo2.png",Texture.class);
		manager.load("techo1.png",Texture.class);
		manager.load("marcoSI.png",Texture.class);
		manager.load("marcoID.png",Texture.class);
		manager.load("enemigo1.png",Texture.class);
		manager.load("enemigo2.png",Texture.class);
		manager.load("pincho1.png",Texture.class);
		manager.load("pincho2.png",Texture.class);
		manager.load("meta.png", Texture.class);
		manager.load("sound/muerte.ogg", Sound.class);
		manager.load("sound/movimiento.ogg", Sound.class);
		manager.load("sound/gameover.ogg", Sound.class);
		manager.load("sound/musica.ogg", Music.class);
		manager.load("sound/final.ogg", Sound.class);
		manager.finishLoading();									   //Fin de carga de elementos

		musica = manager.get("sound/musica.ogg");					   //Se carga la música en el juego para poder pararla desde cualquier clase
		musica.setLooping(true);									   //Música repitiendose siempre
		musica.setVolume(0.30f);

		pantallaI  = new PantallaInicial(this);						   //Se creán todas la pantallas asignandole el juego
		pantallaF  = new PantallaFinal(this);
		pantallaFP = new PantallaFinPartida(this);
		pantalla1  = new Pantalla1(this);
		pantalla2  = new Pantalla2(this);
		pantalla3  = new Pantalla3(this);
		pantallaO  = new PantallaOpciones(this);

		setScreen(pantallaI);										   //Se carga la pantalla inicial
	}

	@Override
	public void dispose() {
		preferencias.putInteger("dificultad",dificultad);					//Se guardan las preferencias del juego una vez termina
		if(listapF.getsize()!=0) {
			preferencias.putString("puntuacionesF", listapF.getCadena());
		}else preferencias.putString("puntuacionesF","n");
		if(listapD.getsize()!=0) {
			preferencias.putString("puntuacionesD", listapD.getCadena());
		}else preferencias.putString("puntuacionesD","n");
		preferencias.flush();
	}

	private ListaPuntuaciones obtenerPuntuacionesF(){
		String puntuaciones = preferencias.getString("puntuacionesF","n");	//Si las lista están vacias de genera una lista vacía
		if(puntuaciones.equals("n")) return new ListaPuntuaciones();
		else{
			ListaPuntuaciones tmp = new ListaPuntuaciones();				//En caso contrario de lee la cadena, se corta y guarda en
			String[] s = puntuaciones.split("\\s+");						//una lista.
			for (int i=0;i<s.length;i=i+2){
				tmp.actulizar(new Puntuacion(s[i],Integer.valueOf(s[i+1])));
			}
			return tmp;
		}
	}

	private ListaPuntuaciones obtenerPuntuacionesD(){
		String puntuaciones = preferencias.getString("puntuacionesD","n");  //Si las lista están vacias de genera una lista vacía
		if(puntuaciones.equals("n")) return new ListaPuntuaciones();
		else{
			ListaPuntuaciones tmp = new ListaPuntuaciones();				//En caso contrario de lee la cadena, se corta y guarda en
			String[] s = puntuaciones.split("\\s+");						//una lista.
			for (int i=0;i<s.length;i=i+2){
				tmp.actulizar(new Puntuacion(s[i],Integer.valueOf(s[i+1])));
			}
			return tmp;
		}
	}
}
