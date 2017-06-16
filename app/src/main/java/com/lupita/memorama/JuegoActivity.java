package com.lupita.memorama;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.lupita.memorama.Clases.Tarjeta;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.LinkedList;

public class JuegoActivity extends AppCompatActivity implements View.OnClickListener{

    /* Modo de juego. Historia de Mexico: 0. Historia Universal: 1 */
    private int modoJuego;
    /* Número de tarjetas con las que jugaremos */
    private int numeroDeTarjetas;
    /* Listado de todas las tarjetas */
    private LinkedList<Tarjeta> todasLasTarjetas;
    /* Array de las tarjetas con las que jugaremos */
    private Tarjeta[] tarjetasEnJuego;
    /* View's que haremos visibles dependiendo el numero de tarjetas solicitado */
    private View includeCuatro, includeOcho, includeDoce;
    /* Botón ir atras */
    private ImageView atras;
    /* Objeto random para posicionar tarjetas al azar */
    private SecureRandom random;
    /* Variable que nos indica si una tarjeta esta volteada */
    private boolean hayVolteada;
    /* Posición de la tarjeta que esta volteada, -1 si ninguna lo esta */
    private int posicionVolteada;
    /* Tarjeta Auxiliar */
    private Tarjeta tarjetaEnPosition;
    /* ImageView's que contienen a las tarjetas */
    private ImageView uno, dos, tres, cuatro, cinco, seis, siete, ocho, nueve, diez, once, doce;
    /* ImageView's que indican si un par de tarjetas volteadas fueron iguales o no */
    private ImageView unoCorrecto, dosCorrecto, tresCorrecto, cuatroCorrecto, cincoCorrecto, seisCorrecto;
    private ImageView sieteCorrecto, ochoCorrecto, nueveCorrecto, diezCorrecto, onceCorrecto, doceCorrecto;
    /* Contador que indica cuantos pares de tarjetas ha hecho el usuario */
    private int pares;
    /* Entero que nos indica los números de pares que tenemos que lograr */
    private int totalDePares;
    /* Lista de ImageView's que tienen desactivado el OnClickListener */
    private LinkedList<ImageView> imagenesSinListener;

    private View view;
    private int uiOptions=View.SYSTEM_UI_FLAG_FULLSCREEN;


     @Override
    protected void onCreate(Bundle savedInstanceState) {
         setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_juego);

         view=getWindow().getDecorView();
         view.setSystemUiVisibility(uiOptions);

         // Nos pasaran por el bundle el numero de tarjetas que tendremos que poner en el activity
         numeroDeTarjetas = 0;

         Bundle bundle = getIntent().getExtras();
         if (bundle != null) {
             numeroDeTarjetas = bundle.getInt("numeroDeTarjetas");
             modoJuego = bundle.getInt("modoJuego");
         }

         // Si ocurre un error desconocido con el bundle
         if (numeroDeTarjetas == 0)
             finish();

         atras = (ImageView) findViewById(R.id.juego_regresar);
         atras.setOnClickListener(this);

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
                unoCorrecto = (ImageView) findViewById(R.id.cuatro_img_uno_correct);
                dos = (ImageView) findViewById(R.id.cuatro_img_dos);
                dosCorrecto = (ImageView) findViewById(R.id.cuatro_img_dos_correct);
                tres = (ImageView) findViewById(R.id.cuatro_img_tres);
                tresCorrecto = (ImageView) findViewById(R.id.cuatro_img_tres_correct);
                cuatro = (ImageView) findViewById(R.id.cuatro_img_cuatro);
                cuatroCorrecto = (ImageView) findViewById(R.id.cuatro_img_cuatro_correct);
                break;
            case 8:
                uno = (ImageView) findViewById(R.id.ocho_img_uno);
                unoCorrecto = (ImageView) findViewById(R.id.ocho_img_uno_correcto);
                dos = (ImageView) findViewById(R.id.ocho_img_dos);
                dosCorrecto = (ImageView) findViewById(R.id.ocho_img_dos_correcto);
                tres = (ImageView) findViewById(R.id.ocho_img_tres);
                tresCorrecto = (ImageView) findViewById(R.id.ocho_img_tres_correcto);
                cuatro = (ImageView) findViewById(R.id.ocho_img_cuatro);
                cuatroCorrecto = (ImageView) findViewById(R.id.ocho_img_cuatro_correcto);
                cinco = (ImageView) findViewById(R.id.ocho_img_cinco);
                cincoCorrecto = (ImageView) findViewById(R.id.ocho_img_cinco_correcto);
                seis = (ImageView) findViewById(R.id.ocho_img_seis);
                seisCorrecto = (ImageView) findViewById(R.id.ocho_img_seis_correcto);
                siete = (ImageView) findViewById(R.id.ocho_img_siete);
                sieteCorrecto = (ImageView) findViewById(R.id.ocho_img_siete_correcto);
                ocho = (ImageView) findViewById(R.id.ocho_img_ocho);
                ochoCorrecto = (ImageView) findViewById(R.id.ocho_img_ocho_correcto);
                break;
            case 12:
                uno = (ImageView) findViewById(R.id.doce_img_uno);
                unoCorrecto = (ImageView) findViewById(R.id.doce_img_uno_correcto);
                dos = (ImageView) findViewById(R.id.doce_img_dos);
                dosCorrecto = (ImageView) findViewById(R.id.doce_img_dos_correcto);
                tres = (ImageView) findViewById(R.id.doce_img_tres);
                tresCorrecto = (ImageView) findViewById(R.id.doce_img_tres_correcto);
                cuatro = (ImageView) findViewById(R.id.doce_img_cuatro);
                cuatroCorrecto = (ImageView) findViewById(R.id.doce_img_cuatro_correcto);
                cinco = (ImageView) findViewById(R.id.doce_img_cinco);
                cincoCorrecto = (ImageView) findViewById(R.id.doce_img_cinco_correcto);
                seis = (ImageView) findViewById(R.id.doce_img_seis);
                seisCorrecto = (ImageView) findViewById(R.id.doce_img_seis_correcto);
                siete = (ImageView) findViewById(R.id.doce_img_siete);
                sieteCorrecto = (ImageView) findViewById(R.id.doce_img_siete_correcto);
                ocho = (ImageView) findViewById(R.id.doce_img_ocho);
                ochoCorrecto = (ImageView) findViewById(R.id.doce_img_ocho_correcto);
                nueve = (ImageView) findViewById(R.id.doce_img_nueve);
                nueveCorrecto = (ImageView) findViewById(R.id.doce_img_nueve_correcto);
                diez = (ImageView) findViewById(R.id.doce_img_diez);
                diezCorrecto = (ImageView) findViewById(R.id.doce_img_diez_correcto);
                once = (ImageView) findViewById(R.id.doce_img_once);
                onceCorrecto = (ImageView) findViewById(R.id.doce_img_once_correcto);
                doce = (ImageView) findViewById(R.id.doce_img_doce);
                doceCorrecto = (ImageView) findViewById(R.id.doce_img_doce_correcto);
                break;
        }
    }

    private void colocaTodasLasTarjetas() {
        todasLasTarjetas = new LinkedList<>();
        if (modoJuego==0){
            Tarjeta aux;
            aux = new Tarjeta(R.drawable.mx_tarjeta_1, R.drawable.mx_texto_1, R.drawable.mx_completa_1);
            todasLasTarjetas.add(aux);
            aux = new Tarjeta(R.drawable.mx_tarjeta_2, R.drawable.mx_texto_2, R.drawable.mx_completa_2);
            todasLasTarjetas.add(aux);
            aux = new Tarjeta(R.drawable.mx_tarjeta_3, R.drawable.mx_texto_3, R.drawable.mx_completa_3);
            todasLasTarjetas.add(aux);
            aux = new Tarjeta(R.drawable.mx_tarjeta_4, R.drawable.mx_texto_4, R.drawable.mx_completa_4);
            todasLasTarjetas.add(aux);
            aux = new Tarjeta(R.drawable.mx_tarjeta_5, R.drawable.mx_texto_5, R.drawable.mx_completa_5);
            todasLasTarjetas.add(aux);
            aux = new Tarjeta(R.drawable.mx_tarjeta_6, R.drawable.mx_texto_6, R.drawable.mx_completa_6);
            todasLasTarjetas.add(aux);
            aux = new Tarjeta(R.drawable.mx_tarjeta_7, R.drawable.mx_texto_7, R.drawable.mx_completa_7);
            todasLasTarjetas.add(aux);
            aux = new Tarjeta(R.drawable.mx_tarjeta_8, R.drawable.mx_texto_8, R.drawable.mx_completa_8);
            todasLasTarjetas.add(aux);
            aux = new Tarjeta(R.drawable.mx_tarjeta_9, R.drawable.mx_texto_9, R.drawable.mx_completa_9);
            todasLasTarjetas.add(aux);
            aux = new Tarjeta(R.drawable.mx_tarjeta_10, R.drawable.mx_texto_10, R.drawable.mx_completa_10);
            todasLasTarjetas.add(aux);
            aux = new Tarjeta(R.drawable.mx_tarjeta_11, R.drawable.mx_texto_11, R.drawable.mx_completa_11);
            todasLasTarjetas.add(aux);
            aux = new Tarjeta(R.drawable.mx_tarjeta_12, R.drawable.mx_texto_12, R.drawable.mx_completa_12);
            todasLasTarjetas.add(aux);
            aux = new Tarjeta(R.drawable.mx_tarjeta_13, R.drawable.mx_texto_13, R.drawable.mx_completa_13);
            todasLasTarjetas.add(aux);
            aux = new Tarjeta(R.drawable.mx_tarjeta_14, R.drawable.mx_texto_14, R.drawable.mx_completa_14);
            todasLasTarjetas.add(aux);
            aux = new Tarjeta(R.drawable.mx_tarjeta_15, R.drawable.mx_texto_15, R.drawable.mx_completa_15);
            todasLasTarjetas.add(aux);
            aux = new Tarjeta(R.drawable.mx_tarjeta_16, R.drawable.mx_texto_16, R.drawable.mx_completa_16);
            todasLasTarjetas.add(aux);
            aux = new Tarjeta(R.drawable.mx_tarjeta_17, R.drawable.mx_texto_17, R.drawable.mx_completa_17);
            todasLasTarjetas.add(aux);
            aux = new Tarjeta(R.drawable.mx_tarjeta_18, R.drawable.mx_texto_18, R.drawable.mx_completa_18);
            todasLasTarjetas.add(aux);
            aux = new Tarjeta(R.drawable.mx_tarjeta_19, R.drawable.mx_texto_19, R.drawable.mx_completa_19);
            todasLasTarjetas.add(aux);
            aux = new Tarjeta(R.drawable.mx_tarjeta_20, R.drawable.mx_texto_20, R.drawable.mx_completa_20);
            todasLasTarjetas.add(aux);
            aux = new Tarjeta(R.drawable.mx_tarjeta_21, R.drawable.mx_texto_21, R.drawable.mx_completa_21);
            todasLasTarjetas.add(aux);
            aux = new Tarjeta(R.drawable.mx_tarjeta_22, R.drawable.mx_texto_22, R.drawable.mx_completa_22);
            todasLasTarjetas.add(aux);
            aux = new Tarjeta(R.drawable.mx_tarjeta_23, R.drawable.mx_texto_23, R.drawable.mx_completa_23);
            todasLasTarjetas.add(aux);
            aux = new Tarjeta(R.drawable.mx_tarjeta_24, R.drawable.mx_texto_24, R.drawable.mx_completa_24);
            todasLasTarjetas.add(aux);
            aux = new Tarjeta(R.drawable.mx_tarjeta_25, R.drawable.mx_texto_25, R.drawable.mx_completa_25);
            todasLasTarjetas.add(aux);
            aux = new Tarjeta(R.drawable.mx_tarjeta_26, R.drawable.mx_texto_26, R.drawable.mx_completa_26);
            todasLasTarjetas.add(aux);
            aux = new Tarjeta(R.drawable.mx_tarjeta_27, R.drawable.mx_texto_27, R.drawable.mx_completa_27);
            todasLasTarjetas.add(aux);
        }
        else {
            Tarjeta aux;
            aux = new Tarjeta(R.drawable.uni_tarjeta_1, R.drawable.uni_texto_1, R.drawable.uni_completa_1);
            todasLasTarjetas.add(aux);
            aux = new Tarjeta(R.drawable.uni_tarjeta_2, R.drawable.uni_texto_2, R.drawable.uni_completa_2);
            todasLasTarjetas.add(aux);
            aux = new Tarjeta(R.drawable.uni_tarjeta_3, R.drawable.uni_texto_3, R.drawable.uni_completa_3);
            todasLasTarjetas.add(aux);
            aux = new Tarjeta(R.drawable.uni_tarjeta_4, R.drawable.uni_texto_4, R.drawable.uni_completa_4);
            todasLasTarjetas.add(aux);
            aux = new Tarjeta(R.drawable.uni_tarjeta_5, R.drawable.uni_texto_5, R.drawable.uni_completa_5);
            todasLasTarjetas.add(aux);
            aux = new Tarjeta(R.drawable.uni_tarjeta_6, R.drawable.uni_texto_6, R.drawable.uni_completa_6);
            todasLasTarjetas.add(aux);
            aux = new Tarjeta(R.drawable.uni_tarjeta_7, R.drawable.uni_texto_7, R.drawable.uni_completa_7);
            todasLasTarjetas.add(aux);
            aux = new Tarjeta(R.drawable.uni_tarjeta_8, R.drawable.uni_texto_8, R.drawable.uni_completa_8);
            todasLasTarjetas.add(aux);
            aux = new Tarjeta(R.drawable.uni_tarjeta_9, R.drawable.uni_texto_9, R.drawable.uni_completa_9);
            todasLasTarjetas.add(aux);
            aux = new Tarjeta(R.drawable.uni_tarjeta_10, R.drawable.uni_texto_10, R.drawable.uni_completa_10);
            todasLasTarjetas.add(aux);
            aux = new Tarjeta(R.drawable.uni_tarjeta_11, R.drawable.uni_texto_11, R.drawable.uni_completa_11);
            todasLasTarjetas.add(aux);
            aux = new Tarjeta(R.drawable.uni_tarjeta_12, R.drawable.uni_texto_12, R.drawable.uni_completa_12);
            todasLasTarjetas.add(aux);
            aux = new Tarjeta(R.drawable.uni_tarjeta_13, R.drawable.uni_texto_13, R.drawable.uni_completa_13);
            todasLasTarjetas.add(aux);
            aux = new Tarjeta(R.drawable.uni_tarjeta_14, R.drawable.uni_texto_14, R.drawable.uni_completa_14);
            todasLasTarjetas.add(aux);
            aux = new Tarjeta(R.drawable.uni_tarjeta_15, R.drawable.uni_texto_15, R.drawable.uni_completa_15);
            todasLasTarjetas.add(aux);
            aux = new Tarjeta(R.drawable.uni_tarjeta_16, R.drawable.uni_texto_16, R.drawable.uni_completa_16);
            todasLasTarjetas.add(aux);
            aux = new Tarjeta(R.drawable.uni_tarjeta_17, R.drawable.uni_texto_17, R.drawable.uni_completa_17);
            todasLasTarjetas.add(aux);
            aux = new Tarjeta(R.drawable.uni_tarjeta_18, R.drawable.uni_texto_18, R.drawable.uni_completa_18);
            todasLasTarjetas.add(aux);
            aux = new Tarjeta(R.drawable.uni_tarjeta_19, R.drawable.uni_texto_19, R.drawable.uni_completa_19);
            todasLasTarjetas.add(aux);
            aux = new Tarjeta(R.drawable.uni_tarjeta_20, R.drawable.uni_texto_20, R.drawable.uni_completa_20);
            todasLasTarjetas.add(aux);
            aux = new Tarjeta(R.drawable.uni_tarjeta_21, R.drawable.uni_texto_21, R.drawable.uni_completa_21);
            todasLasTarjetas.add(aux);
            aux = new Tarjeta(R.drawable.uni_tarjeta_22, R.drawable.uni_texto_22, R.drawable.uni_completa_22);
            todasLasTarjetas.add(aux);
            aux = new Tarjeta(R.drawable.uni_tarjeta_23, R.drawable.uni_texto_23, R.drawable.uni_completa_23);
            todasLasTarjetas.add(aux);
            aux = new Tarjeta(R.drawable.uni_tarjeta_24, R.drawable.uni_texto_24, R.drawable.uni_completa_24);
            todasLasTarjetas.add(aux);
            aux = new Tarjeta(R.drawable.uni_tarjeta_25, R.drawable.uni_texto_25, R.drawable.uni_completa_25);
            todasLasTarjetas.add(aux);
            aux = new Tarjeta(R.drawable.uni_tarjeta_26, R.drawable.uni_texto_26, R.drawable.uni_completa_26);
            todasLasTarjetas.add(aux);
            aux = new Tarjeta(R.drawable.uni_tarjeta_27, R.drawable.uni_texto_27, R.drawable.uni_completa_27);
            todasLasTarjetas.add(aux);
            aux = new Tarjeta(R.drawable.uni_tarjeta_28, R.drawable.uni_texto_28, R.drawable.uni_completa_28);
            todasLasTarjetas.add(aux);
        }

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
            // Boton atras
            case R.id.juego_regresar:
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
        tarjetaEnPosition = tarjetasEnJuego[position];
        Glide.with(this).load(tarjetaEnPosition.getIdDrawable()).into(imageViewFrom(position));

        if (hayVolteada){
            Tarjeta tarjetaVolteada = tarjetasEnJuego[posicionVolteada];
            if (tarjetaEnPosition.equals(tarjetaVolteada)){
                // Volteó ambas cartas iguales
                // Colocamos encima la imagen de correcto
                Glide.with(this).load(R.drawable.correctas).into(imageViewCorrectFrom(position));
                Glide.with(this).load(R.drawable.correctas).into(imageViewCorrectFrom(posicionVolteada));

                AsyncTaskTarjetasCorrectas asyncTaskTarjetasCorrectas = new AsyncTaskTarjetasCorrectas(position);
                asyncTaskTarjetasCorrectas.execute();

            }else{
                // No volteó las mismas tarjetas
                // Las mostramos por 1 segundo y despues las ponemos boca abajo

                Glide.with(this).load(R.drawable.incorrectas).into(imageViewCorrectFrom(position));
                Glide.with(this).load(R.drawable.incorrectas).into(imageViewCorrectFrom(posicionVolteada));

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

    private ImageView imageViewCorrectFrom(int position){
        switch (position){
            case 0:
                return unoCorrecto;
            case 1:
                return dosCorrecto;
            case 2:
                return tresCorrecto;
            case 3:
                return cuatroCorrecto;
            case 4:
                return cincoCorrecto;
            case 5:
                return seisCorrecto;
            case 6:
                return sieteCorrecto;
            case 7:
                return ochoCorrecto;
            case 8:
                return nueveCorrecto;
            case 9:
                return diezCorrecto;
            case 10:
                return onceCorrecto;
            case 11:
                return doceCorrecto;
            default:
                return unoCorrecto;
        }
    }

    private void colocaBocaAbajo(int position){
        Glide.with(this).load(R.drawable.carta_boca_abajo).into(imageViewFrom(position));
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

            // Les asignamos el listener si es que no se lo habiamos quitado anteriormente
            for (int i = 0; i < numeroDeTarjetas; i++)
                if (!imagenesSinListener.contains(imageViewFrom(i)))
                    imageViewFrom(i).setOnClickListener(JuegoActivity.this);

            Glide.with(JuegoActivity.this).load(R.drawable.transparent).into(imageViewCorrectFrom(position));
            Glide.with(JuegoActivity.this).load(R.drawable.transparent).into(imageViewCorrectFrom(posicionVolteada));

            hayVolteada = false;
            posicionVolteada = -1;
        }
    }

    private class AsyncTaskTarjetasCorrectas extends AsyncTask<Void, Void, Void>{

        int position;
        Intent infoIntent;

        public AsyncTaskTarjetasCorrectas(int position) {
            super();
            this.position = position;
        }

        @Override
        protected void onPreExecute() {
            // Quitamos listeners para evitar que puedan dar click en ellos
            imageViewFrom(position).setOnClickListener(null);
            imagenesSinListener.add(imageViewFrom(position));
            imageViewFrom(posicionVolteada).setOnClickListener(null);
            imagenesSinListener.add(imageViewFrom(posicionVolteada));

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
            hayVolteada = false;
            posicionVolteada = -1;
            pares++;

            if (pares == totalDePares){
                infoIntent = new Intent(JuegoActivity.this, InformacionTarjetaActivity.class);
                infoIntent.putExtra("tarjeta", tarjetaEnPosition);
                infoIntent.putExtra("gane", true);
            }else{
                infoIntent = new Intent(JuegoActivity.this, InformacionTarjetaActivity.class);
                infoIntent.putExtra("tarjeta", tarjetaEnPosition);
                infoIntent.putExtra("gane", false);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            // Les asignamos el listener si es que no se lo habiamos quitado anteriormente
            for (int i = 0; i < numeroDeTarjetas; i++)
                if (!imagenesSinListener.contains(imageViewFrom(i)))
                    imageViewFrom(i).setOnClickListener(JuegoActivity.this);

            hayVolteada = false;
            posicionVolteada = -1;

            startActivity(infoIntent);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        view=getWindow().getDecorView();
        view.setSystemUiVisibility(uiOptions);
    }


}
