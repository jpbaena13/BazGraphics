/*
 * BaZApplicationDelegate.java	0.1  20/05/2011
 *
 * Copyright 2011 Universidad de Antioquia. All rights reserved.
 */
package com.baz.apps.base;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ComponentAdapter;

/**
 * Interfaz implementada solo para determinar que tipo de aplicación se va
 * a construir, si es una de escritorio o un applet.
 * 
 * @author JpBaena
 * @version 0.1
 * @since 1.6
 */
public interface BaZApplicationDelegate {
    
    /**
     * Retorna el container principal de la aplicación.
     * 
     * @return Container principal de la aplicación
     */
    public Container getContentPane();

    /**
     * Añade la aplicacióno a un escuchador de componentes.
     *
     * @param adapter
     *        Adapatador de la interfaz de escuchador de componentes.
     */
    public void addComponentListener(ComponentAdapter adapter);

    /**
     * Retorna el tamaño del componente frame de la aplicación.
     *
     * @return Objeto <code>Dimension</code> que es igual al tamaño del componente
     *                frame de la aplicación.
     */
    public Dimension getSize();

    /**
     * Retorna el String corresponiente a la llave especificada, dentro del
     * archivo de propiedades que se encuentra en la ruta que debe ser configurada
     * por la clase que extiende de la interfaz.
     *
     * @param key
     *        Llave del String dentro del archivo de propiedades
     *
     * @return String asociado a la llave especificada.
     */
    public String getString(String key);
}
