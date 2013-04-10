/*
 * BaZMenuBarDelegate.java	0.1  20/05/2011
 *
 * Copyright 2011 Universidad de Antioquia. All rights reserved.
 */
package com.baz.menu;

/**
 * Intefaz que es implementada para determinar que un objeto tiene asociado
 * una barra de menú.
 *
 * @author JpBaena
 * @version 0.1
 * @since 1.6
 */
public interface BaZMenuBarDelegate {
    /**
     * Implementado para determinar el estado de un item de menú.
     *
     * @param item
     *        Item de menú
     */
    public void menuItemState(BaZMenuItem item);

    /**
     * Implementado gestionar el evento de ocurre sobre un item de menú.
     *
     * @param menu
     *        Menú asociado a la barra de menú
     * @param item
     *        Item asociado al menú especificado.
     */
    public void handlerMenuEvent(BaZMenu menu, BaZMenuItem item);

    /**
     * Implementado para manejar el eveto de seleccionar un menu de la barra de
     * menú.
     *
     * @param menu
     *        Menú seleccionado.
     */
    public void handlerMenuSelected(BaZMenu menu);
}
