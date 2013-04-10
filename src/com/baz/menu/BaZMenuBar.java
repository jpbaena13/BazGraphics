/*
 * BaZMainMenuBar.java	0.1  20/05/2011
 *
 * Copyright 2011 Universidad de Antioquia. All rights reserved.
 */
package com.baz.menu;

import javax.swing.JMenuBar;

/**
 * Ésta clase crea una barra de menús contextuales que contendrá elementos de tipo
 * <code>BaZMenu</code>.
 *
 * @author JpBaena
 * @version 0.1
 * @since 1.6
 */
public class BaZMenuBar implements BaZMenuItemDelegate {

    /** <code>JMenuBar</code> asociado a la clase.*/
    protected JMenuBar menuBar = null;
    
    /** Objeto que tiene asociado los eventos de la barra de menús. */
    protected BaZMenuBarDelegate delegate = null;

    /**
     * Manejador para el menú seleccionado.
     *
     * @param menu
     *        Menú que ha sido seleccionado.
     */
    public void menuSelected(BaZMenu menu) {
        if (delegate != null)
            delegate.handlerMenuSelected(menu);
    }

    public void handlerMenuEvent(BaZMenu menu, BaZMenuItem item) {
    }
}
