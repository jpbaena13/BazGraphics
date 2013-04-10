/*
 * BaZAlphaVariator.java	0.1  29/04/2011
 *
 * Copyright 2011 Universidad de Antioquia. All rights reserved.
 */
package com.baz.graphics.base;

/**
 * Esta clase permite variar de manera dinámica el alpha de alguna figura dibujable
 *
 * @author JpBaena
 * @version 0.1
 * @since 1.6
 */
public class BaZAlphaVariator {

    /** Velocidad de Cambio de la variacion del alfa de una figura. */
    private int alphaColorVariationSpeed = 1;

    /** Valor del alpha. */
    private float alphaColorValue = 0;

    /**
     * Retorna el valor del alpha
     *
     * @return Valor del alpha
     */
    public float getAlphaValue() {
        return alphaColorValue;
    }

    /**
     * Método que incrementa y decrementa el valor del alfa entre los valores
     * de 0 y 1
     */
    public void run() {
        alphaColorValue += alphaColorVariationSpeed*0.1f;

        if (alphaColorValue > 1) {
            alphaColorValue = 1;
            alphaColorVariationSpeed = -alphaColorVariationSpeed;
        } else if (alphaColorValue < 0) {
            alphaColorValue = 0;
            alphaColorVariationSpeed = -alphaColorVariationSpeed;
        }
    }

}
