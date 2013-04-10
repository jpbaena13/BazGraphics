/*
 * BaZMenuItemDelegate.java	0.1  20/05/2011
 *
 * Copyright 2011 Universidad de Antioquia. All rights reserved.
 */
package com.baz.menu;

/**
 * Interfaz que es implementada para determinar que un objeto tiene asociado
 * eventos de menú.
 *
 * @author JpBaena
 * @version 0.1
 * @since 1.6
 */
public interface BaZMenuItemDelegate {
    /**
     * Método manejador de evento del menú item especificado.
     *
     * @param menu
     *        Objeto de tipo menú.
     *
     * @param item
     *        Objeto tipo item de menú.
     */
    public void handlerMenuEvent(BaZMenu menu, BaZMenuItem item);
}
