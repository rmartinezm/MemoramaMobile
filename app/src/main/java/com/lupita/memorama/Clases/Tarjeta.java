package com.lupita.memorama.Clases;

import java.io.Serializable;

/**
 * Created by rmartinezm
 */

public class Tarjeta implements Serializable{

    /** Entero identificador de la imagen asignada a la tarjeta **/
    private int idDrawable;
    /** Texto informativo asociado a la tarjeta **/
    private String information;

    /**
     *  Constructor público vacío.
     */
    public Tarjeta() {}

    /**
     * Contructor público con parametros.
     * @param idDrawable identificador del drawable que le corresponde a la tarjeta.
     * @param information texto informativo de la tarjeta.
     */
    public Tarjeta(int idDrawable, String information) {
        this.idDrawable = idDrawable;
        this.information = information;
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
     * @return la cadena representativa de la información de la tarjeta.
     */
    public String getInformation() {
        return information;
    }

    /**
     * Coloca la cadena informativa que queremos asociar a la tarjeta.
     * @param information cadena que asociaremos con la tarjeta.
     */
    public void setInformation(String information) {
        this.information = information;
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
            if (aComparar.getIdDrawable() == idDrawable && aComparar.getInformation().equals(information))
                return true;
        }
        return false;
    }
}
