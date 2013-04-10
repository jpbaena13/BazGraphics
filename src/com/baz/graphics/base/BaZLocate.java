/*
 * i18n.java	0.1  20/05/2011
 *
 * Copyright 2011 Universidad de Antioquia. All rights reserved.
 */
package com.baz.graphics.base;

import java.util.ResourceBundle;

/**
 *
 * @author JpBaena
 * @version 0.1
 * @since 1.6
 */
public class BaZLocate {

    /**
     * Retorna el String corresponiente a la llave especificada, dentro del
     * archivo de propiedades que se encuentra en la ruta dada.
     *
     * @param path
     *        Ruta donde se encuentra el archivo de propiedades
     * @param key
     *        Llave del String dentro del archivo de propiedades
     *
     * @return String asociado a la llave especificada.
     */
    public static String getString(String path, String key) {
        return ResourceBundle.getBundle(path).getString(key);
    }

    /**
     * Retorna el String corresponiente a la llave especificada, dentro del
     * archivo de propiedades que se encuentra en la ruta dada.
     * Utilize éste método para buscar una llave dentro del archivo de propiedades
     * global, éste es, el que tiene todos los String comunes a cualquier aplicación.
     *
     * @param path
     *        Ruta donde se encuentra el archivo de propiedades
     * @param key
     *        Llave del String dentro del archivo de propiedades
     *
     * @return String asociado a la llave especificada.
     */
    public static String getString(String key) {
        return ResourceBundle.getBundle("com/baz/properties/Strings").getString(key);
    }
}
