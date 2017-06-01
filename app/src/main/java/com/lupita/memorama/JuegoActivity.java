package com.lupita.memorama;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lupita.memorama.Clases.Tarjeta;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.LinkedList;

public class JuegoActivity extends AppCompatActivity implements View.OnClickListener{

    /**Modo de juego. Historia de Mexico: 0. Historia Universal: 1**/
    private int modoJuego;
    /** Número de tarjetas con las que jugaremos **/
    private int numeroDeTarjetas;
    /** Listado de todas las tarjetas **/
    private LinkedList<Tarjeta> todasLasTarjetas;
    /** Array de las tarjetas con las que jugaremos **/
    private Tarjeta[] tarjetasEnJuego;
    /** View's que haremos visibles dependiendo el numero de tarjetas solicitado **/
    private View includeCuatro, includeOcho, includeDoce;
    /** Botón ir atras **/
    private ImageView ivAtras;
    /** TextView con la misma funcionalidad que la imagen **/
    private TextView tvAtras;
    /** Objeto random para posicionar tarjetas al azar **/
    private SecureRandom random;
    /** Variable que nos indica si una tarjeta esta volteada **/
    private boolean hayVolteada;
    /** Posición de la tarjeta que esta volteada, -1 si ninguna lo esta **/
    private int posicionVolteada;
    /** ImageView's que contienen a las tarjetas **/
    private ImageView uno, dos, tres, cuatro, cinco, seis, siete, ocho, nueve, diez, once, doce;
    /** Contador que indica cuantos pares de tarjetas ha hecho el usuario **/
    private int pares;
    /** Entero que nos indica los números de pares que tenemos que lograr **/
    private int totalDePares;
    /** Lista de ImageView's que tienen desactivado el OnClickListener **/
    private LinkedList<ImageView> imagenesSinListener;

     @Override
    protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_juego);

         // Ocultamos el actionbar
         getSupportActionBar().hide();

         // Nos pasaran por el bundle el numero de tarjetas que tendremos que poner en el activity
         numeroDeTarjetas = 0;

         Bundle bundle = getIntent().getExtras();
         if (bundle != null) {
             numeroDeTarjetas = bundle.getInt("numeroDeTarjetas");
             modoJuego=bundle.getInt("modoJuego");
         }

         // Si ocurre un error desconocido con el bundle
         if (numeroDeTarjetas == 0)
             finish();

         ivAtras = (ImageView) findViewById(R.id.iv_atras);
         tvAtras = (TextView) findViewById(R.id.tv_atras);

         ivAtras.setOnClickListener(this);
         tvAtras.setOnClickListener(this);

         // Inicializamos las variables y hacemos visible la que corresponda
         includeCuatro = findViewById(R.id.include_cuatro);
         includeCuatro.setVisibility(View.INVISIBLE);
         includeOcho = findViewById(R.id.include_ocho);
         includeCuatro.setVisibility(View.INVISIBLE);
         includeDoce = findViewById(R.id.include_doce);
         includeCuatro.setVisibility(View.INVISIBLE);

         inicializaImageViews();
         // LLenamos la lista con todas las tarjetas disponibles
         colocaTodasLasTarjetas();
         pares = 0;

         random = new SecureRandom();
         imagenesSinListener = new LinkedList<>();

         switch (numeroDeTarjetas) {
             case 4:
                 includeCuatro.setVisibility(View.VISIBLE);
                 includeOcho.setVisibility(View.INVISIBLE);
                 includeDoce.setVisibility(View.INVISIBLE);
                 totalDePares = 2;
                 cuatroTarjetas();
                 break;
             case 8:
                 includeCuatro.setVisibility(View.INVISIBLE);
                 includeOcho.setVisibility(View.VISIBLE);
                 includeDoce.setVisibility(View.INVISIBLE);
                 totalDePares = 4;
                 ochoTarjetas();
                 break;
             case 12:
                 includeCuatro.setVisibility(View.INVISIBLE);
                 includeOcho.setVisibility(View.INVISIBLE);
                 includeDoce.setVisibility(View.VISIBLE);
                 totalDePares = 6;
                 doceTarjetas();
                 break;
         }

         agregaListeners();
    }

    private void agregaListeners() {
        switch (numeroDeTarjetas){
            case 12:
                doce.setOnClickListener(this);
                once.setOnClickListener(this);
                diez.setOnClickListener(this);
                nueve.setOnClickListener(this);
            case 8:
                ocho.setOnClickListener(this);
                siete.setOnClickListener(this);
                seis.setOnClickListener(this);
                cinco.setOnClickListener(this);
            case 4:
                cuatro.setOnClickListener(this);
                tres.setOnClickListener(this);
                dos.setOnClickListener(this);
                uno.setOnClickListener(this);
        }
    }

    private void inicializaImageViews() {
        switch (numeroDeTarjetas){
            case 4:
                uno = (ImageView) findViewById(R.id.cuatro_img_uno);
                dos = (ImageView) findViewById(R.id.cuatro_img_dos);
                tres = (ImageView) findViewById(R.id.cuatro_img_tres);
                cuatro = (ImageView) findViewById(R.id.cuatro_img_cuatro);
                break;
            case 8:
                uno = (ImageView) findViewById(R.id.ocho_img_uno);
                dos = (ImageView) findViewById(R.id.ocho_img_dos);
                tres = (ImageView) findViewById(R.id.ocho_img_tres);
                cuatro = (ImageView) findViewById(R.id.ocho_img_cuatro);
                cinco = (ImageView) findViewById(R.id.ocho_img_cinco);
                seis = (ImageView) findViewById(R.id.ocho_img_seis);
                siete = (ImageView) findViewById(R.id.ocho_img_siete);
                ocho = (ImageView) findViewById(R.id.ocho_img_ocho);
                break;
            case 12:
                uno = (ImageView) findViewById(R.id.doce_img_uno);
                dos = (ImageView) findViewById(R.id.doce_img_dos);
                tres = (ImageView) findViewById(R.id.doce_img_tres);
                cuatro = (ImageView) findViewById(R.id.doce_img_cuatro);
                cinco = (ImageView) findViewById(R.id.doce_img_cinco);
                seis = (ImageView) findViewById(R.id.doce_img_seis);
                siete = (ImageView) findViewById(R.id.doce_img_siete);
                ocho = (ImageView) findViewById(R.id.doce_img_ocho);
                nueve = (ImageView) findViewById(R.id.doce_img_nueve);
                diez = (ImageView) findViewById(R.id.doce_img_diez);
                once = (ImageView) findViewById(R.id.doce_img_once);
                doce = (ImageView) findViewById(R.id.doce_img_doce);
                break;
        }
    }

    private void colocaTodasLasTarjetas() {
        todasLasTarjetas = new LinkedList<>();
        // Ejemplo
        Tarjeta java = new Tarjeta(R.drawable.java_logo, "Java es muy genial");
        Tarjeta kotlin = new Tarjeta(R.drawable.kotlin_logo, "Kotlin es genial");
        Tarjeta python = new Tarjeta(R.drawable.python_logo, "Python no es genial");
        Tarjeta cSharp = new Tarjeta(R.drawable.csharp_logo, "C# == Java");
        Tarjeta cPlusPlus = new Tarjeta(R.drawable.cpp_logo, "C++ es cool");
        Tarjeta html = new Tarjeta(R.drawable.html_logo, "HTML es el mejor lenguaje de programación.\nNótese el sarcasmo");
        todasLasTarjetas.add(java);
        todasLasTarjetas.add(kotlin);
        todasLasTarjetas.add(python);
        todasLasTarjetas.add(cSharp);
        todasLasTarjetas.add(cPlusPlus);
        todasLasTarjetas.add(html);
    }

    private void cuatroTarjetas() {
        // Asignamos las tarjetas correspondientes
        tarjetasEnJuego = new Tarjeta[4];
        ArrayList<Integer> listaAleatorea = dameListaRandom(todasLasTarjetas.size());
        Tarjeta tarjetaUno, tarjetaDos;

        // Dos tarjetas distintas
        tarjetaUno = todasLasTarjetas.get(listaAleatorea.get(0));
        tarjetaDos = todasLasTarjetas.get(listaAleatorea.get(1));

        // numerosRandom guardará los primeros cuatro naturales acomodados de forma aleatorea
        ArrayList<Integer> numerosRandom = dameListaRandom(4);

        // LLenamos tarjetasEnJuego con las tarjetas obtenidas anteriormente
        for (int j = 0; j < 4; j++)
            if (j < 2)
                tarjetasEnJuego[numerosRandom.get(j)] = tarjetaUno;
            else
                tarjetasEnJuego[numerosRandom.get(j)] = tarjetaDos;
    }

    private void ochoTarjetas(){
        // Asignamos las tarjetas correspondientes
        tarjetasEnJuego = new Tarjeta[8];
        ArrayList<Integer> listaAleatorea = dameListaRandom(todasLasTarjetas.size());
        Tarjeta tarjetaUno, tarjetaDos, tarjetaTres, tarjetaCuatro;

        // Cuatro tarjetas distintas
        tarjetaUno = todasLasTarjetas.get(listaAleatorea.get(0));
        tarjetaDos = todasLasTarjetas.get(listaAleatorea.get(1));
        tarjetaTres = todasLasTarjetas.get(listaAleatorea.get(2));
        tarjetaCuatro = todasLasTarjetas.get(listaAleatorea.get(3));

        // numerosRandom guardará los primeros ocho naturales acomodados de forma aleatorea
        ArrayList<Integer> numerosRandom = dameListaRandom(8);

        // LLenamos tarjetasEnJuego con las tarjetas obtenidas anteriormente
        for (int j = 0; j < 8; j++){
            if (j < 2)
                tarjetasEnJuego[numerosRandom.get(j)] = tarjetaUno;
            if (j > 1 && j < 4)
                tarjetasEnJuego[numerosRandom.get(j)] = tarjetaDos;
            if (j > 3 && j < 6)
                tarjetasEnJuego[numerosRandom.get(j)] = tarjetaTres;
            if (j > 5)
                tarjetasEnJuego[numerosRandom.get(j)] = tarjetaCuatro;
        }
    }

    private void doceTarjetas(){
        // Asignamos las tarjetas correspondientes
        tarjetasEnJuego = new Tarjeta[12];
        ArrayList<Integer> listaAleatorea = dameListaRandom(todasLasTarjetas.size());
        Tarjeta tarjetaUno, tarjetaDos, tarjetaTres, tarjetaCuatro, tarjetaCinco, tarjetaSeis;

        // Cuatro tarjetas distintas
        tarjetaUno = todasLasTarjetas.get(listaAleatorea.get(0));
        tarjetaDos = todasLasTarjetas.get(listaAleatorea.get(1));
        tarjetaTres = todasLasTarjetas.get(listaAleatorea.get(2));
        tarjetaCuatro = todasLasTarjetas.get(listaAleatorea.get(3));
        tarjetaCinco = todasLasTarjetas.get(listaAleatorea.get(4));
        tarjetaSeis = todasLasTarjetas.get(listaAleatorea.get(5));

        // numerosRandom guardará los primeros ocho naturales acomodados de forma aleatorea
        ArrayList<Integer> numerosRandom = dameListaRandom(12);

        // LLenamos tarjetasEnJuego con las tarjetas obtenidas anteriormente
        for (int j = 0; j < 12; j++){
            if (j < 2)
                tarjetasEnJuego[numerosRandom.get(j)] = tarjetaUno;
            if (j > 1 && j < 4)
                tarjetasEnJuego[numerosRandom.get(j)] = tarjetaDos;
            if (j > 3 && j < 6)
                tarjetasEnJuego[numerosRandom.get(j)] = tarjetaTres;
            if (j > 5 && j < 8)
                tarjetasEnJuego[numerosRandom.get(j)] = tarjetaCuatro;
            if (j > 7 && j < 10)
                tarjetasEnJuego[numerosRandom.get(j)] = tarjetaCinco;
            if (j > 9)
                tarjetasEnJuego[numerosRandom.get(j)] = tarjetaSeis;
        }
    }

    /**
     * Método que devuelve una lista con los primeros n naturales acomodados de forma aleatorea
     * @param n el número de elementos que contendrá la lista
     * @return una lista con los primeros n naturales acomodados de forma aleatorea
     */
    private ArrayList<Integer> dameListaRandom(int n) {
        ArrayList<Integer> numbers = new ArrayList<>(n);
        ArrayList<Integer> randomList = new ArrayList<>(n);
        for (int i = 0; i < n; i++)
            numbers.add(i);
        while(!numbers.isEmpty()){
            int unRandom = random.nextInt(numbers.size());
            randomList.add(numbers.get(unRandom));
            numbers.remove(unRandom);
        }
        return randomList;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            // Botones atras
            case R.id.iv_atras:
                onBackPressed();
                break;
            case R.id.tv_atras:
                onBackPressed();
                break;

            // Cuatro Tarjetas
            case R.id.cuatro_img_uno:
                clickEnTarjeta(0);
                break;
            case R.id.cuatro_img_dos:
                clickEnTarjeta(1);
                break;
            case R.id.cuatro_img_tres:
                clickEnTarjeta(2);
                break;
            case R.id.cuatro_img_cuatro:
                clickEnTarjeta(3);
                break;

            // Ocho Tarjetas
            case R.id.ocho_img_uno:
                clickEnTarjeta(0);
                break;
            case R.id.ocho_img_dos:
                clickEnTarjeta(1);
                break;
            case R.id.ocho_img_tres:
                clickEnTarjeta(2);
                break;
            case R.id.ocho_img_cuatro:
                clickEnTarjeta(3);
                break;
            case R.id.ocho_img_cinco:
                clickEnTarjeta(4);
                break;
            case R.id.ocho_img_seis:
                clickEnTarjeta(5);
                break;
            case R.id.ocho_img_siete:
                clickEnTarjeta(6);
                break;
            case R.id.ocho_img_ocho:
                clickEnTarjeta(7);
                break;

            // Doce Tarjetas
            case R.id.doce_img_uno:
                clickEnTarjeta(0);
                break;
            case R.id.doce_img_dos:
                clickEnTarjeta(1);
                break;
            case R.id.doce_img_tres:
                clickEnTarjeta(2);
                break;
            case R.id.doce_img_cuatro:
                clickEnTarjeta(3);
                break;
            case R.id.doce_img_cinco:
                clickEnTarjeta(4);
                break;
            case R.id.doce_img_seis:
                clickEnTarjeta(5);
                break;
            case R.id.doce_img_siete:
                clickEnTarjeta(6);
                break;
            case R.id.doce_img_ocho:
                clickEnTarjeta(7);
                break;
            case R.id.doce_img_nueve:
                clickEnTarjeta(8);
                break;
            case R.id.doce_img_diez:
                clickEnTarjeta(9);
                break;
            case R.id.doce_img_once:
                clickEnTarjeta(10);
                break;
            case R.id.doce_img_doce:
                clickEnTarjeta(11);
                break;
        }
    }

    private void clickEnTarjeta(final int position) {
        final Tarjeta tarjetaEnPosition = tarjetasEnJuego[position];
        Glide.with(this).load(tarjetaEnPosition.getIdDrawable()).into(imageViewFrom(position));

        if (hayVolteada){
            Tarjeta tarjetaVolteada = tarjetasEnJuego[posicionVolteada];
            if (tarjetaEnPosition.equals(tarjetaVolteada)){
                // Volteó ambas cartas iguales
                // Quitamos listeners para evitar que puedan dar click en ellos
                imageViewFrom(position).setOnClickListener(null);
                imagenesSinListener.add(imageViewFrom(position));
                imageViewFrom(posicionVolteada).setOnClickListener(null);
                imagenesSinListener.add(imageViewFrom(posicionVolteada));

                hayVolteada = false;
                posicionVolteada = -1;
                pares++;

                Intent infoIntetn;

                if (pares == totalDePares){
                    Intent infoIntent = new Intent(this, InformacionTarjetaActivity.class);
                    infoIntent.putExtra("tarjeta", tarjetaEnPosition);
                    infoIntent.putExtra("gane", true);
                    startActivity(infoIntent);
                    finish();
                }else{
                    Intent infoIntent = new Intent(this, InformacionTarjetaActivity.class);
                    infoIntent.putExtra("tarjeta", tarjetaEnPosition);
                    infoIntent.putExtra("gane", false);
                    startActivity(infoIntent);
                }

            }else{
                // No volteó las mismas tarjetas
                // Las mostramos por 1 segundo y despues las ponemos boca abajo
                AsyncTaskTarjetas att = new AsyncTaskTarjetas(position);
                att.execute();
            }

        }else{
            // Como no hay tarjetas volteadas simplemente asignamos los valores y quitamos listener
            // para que no puedan volverlo a clickear
            hayVolteada = true;
            posicionVolteada = position;
            imageViewFrom(position).setOnClickListener(null);
        }
    }

    private ImageView imageViewFrom(int position) {
        switch (position){
            case 0:
                return uno;
            case 1:
                return dos;
            case 2:
                return tres;
            case 3:
                return cuatro;
            case 4:
                return cinco;
            case 5:
                return seis;
            case 6:
                return siete;
            case 7:
                return ocho;
            case 8:
                return nueve;
            case 9:
                return diez;
            case 10:
                return once;
            case 11:
                return doce;
        }
        return null;
    }

    private void colocaBocaAbajo(int position){
        int fondoPorOmision = R.drawable.omision;
        Glide.with(this).load(fondoPorOmision).into(imageViewFrom(position));
    }

    /** Clase privada para asegurar que hay a lo más dos tarjetas seleccionadas **/
    private class AsyncTaskTarjetas extends AsyncTask<Void, Void, Void>{

        int position;

        public AsyncTaskTarjetas(int position){
            super();
            this.position = position;
        }

        @Override
        protected void onPreExecute() {
            // Quitamos listeners para que nada pueda ser seleccionado mientras esperamos
            // switch en forma de cascada
            switch(numeroDeTarjetas){
                case 12:
                    nueve.setOnClickListener(null);
                    diez.setOnClickListener(null);
                    once.setOnClickListener(null);
                    doce.setOnClickListener(null);
                case 8:
                    cinco.setOnClickListener(null);
                    seis.setOnClickListener(null);
                    siete.setOnClickListener(null);
                    ocho.setOnClickListener(null);
                case 4:
                    uno.setOnClickListener(null);
                    dos.setOnClickListener(null);
                    tres.setOnClickListener(null);
                    cuatro.setOnClickListener(null);
            }

        }

        @Override
        protected Void doInBackground(Void... params) {
            // Esperamos un segundo mostrando las tarjetas
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            colocaBocaAbajo(position);
            colocaBocaAbajo(posicionVolteada);
            hayVolteada = false;
            posicionVolteada = -1;

            // Les asignamos el listener si es que no se lo habiamos quitado anteriormente
            for (int i = 0; i < numeroDeTarjetas; i++)
                if (!imagenesSinListener.contains(imageViewFrom(i)))
                    imageViewFrom(i).setOnClickListener(JuegoActivity.this);
        }
    }

}
