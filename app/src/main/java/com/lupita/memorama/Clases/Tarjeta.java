package com.lupita.memorama.Clases;

import java.io.Serializable;

/**
 * Created by rmartinezm
 */

public class Tarjeta implements Serializable{

    /* Entero identificador de la imagen asignada a la tarjeta */
    private int idDrawable;
    /* Entero identificador del texto asignado a la tarjeta */
    private int idInformation;
    /* Entero identificador de la imagen completa que esta asociada a la tarjeta */
    private int idCompleta;

    /**
     *  Constructor público vacío.
     */
    public Tarjeta() {}

    /**
     * Contructor público con parametros.
     * @param idDrawable identificador del drawable que le corresponde a la tarjeta.
     * @param idInformation identificador del drawable/texto informativo de la tarjeta.
     * @param idCompleta identificador del drawable que corresponte a la imagen completa asociada
     */
    public Tarjeta(int idDrawable, int idInformation, int idCompleta) {
        this.idDrawable = idDrawable;
        this.idInformation = idInformation;
        this.idCompleta = idCompleta;
    }

    /**
     * @return el entero que funciona como id del drawable asociado a la tarjeta.
     */
    public int getIdDrawable() {
        return idDrawable;
    }

    /**
     * Coloca el id del drawable que le queremos asociar a la tarjeta.
     * @param idDrawable id que asociaremos a la tarjeta.
     */
    public void setIdDrawable(int idDrawable) {
        this.idDrawable = idDrawable;
    }

    /**
     * @return el id del drawable representativo de la información de la tarjeta.
     */
    public int getIdInformation() {
        return idInformation;
    }

    /**
     * Coloca la cadena informativa que queremos asociar a la tarjeta.
     * @param idInformation identificador del drawable/texto que asociaremos con la tarjeta.
     */
    public void setIdInformation(int idInformation) {
        this.idInformation = idInformation;
    }

    /**
     * @return
     */
    public int getIdCompleta() {
        return idCompleta;
    }

    public void setIdCompleta(int idCompleta) {
        this.idCompleta = idCompleta;
    }

    /**
     * Verifica si el objeto que pasamos como parametro es igual a esta tarjeta.
     * @param obj Objeto con el cual compararemos si es el mismo o no lo es.
     * @return <code>true</code> si el objeto que pasamos como parametro es el mismo que la tarjeta.
     * <code>false</code> en otro caso.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Tarjeta){
            Tarjeta aComparar = (Tarjeta) obj;
            if (aComparar.getIdDrawable() == idDrawable && aComparar.getIdInformation() == idInformation
                    && aComparar.getIdCompleta() == idCompleta)
                return true;
        }
        return false;
    }
}
