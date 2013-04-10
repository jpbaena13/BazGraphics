/*
 * BaZAbstractEvent.java	0.1  29/04/2011
 *
 * Copyright 2011 Universidad de Antioquia. All rights reserved.
 */
package com.baz.event;

import com.baz.canvas.BaZCanvasDelegate;
import com.baz.canvas.BaZCanvas;
import com.baz.shape.BaZAbstractShape;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/**
 *
 * @author JpBaena
 * @version 0.1
 * @since 1.6
 */
public abstract class BaZAbstractEvent {

    /** Objeto manejador de eventos. */
    protected BaZEventManager manager = null;

    /** Lienzo delegado, es la clase principal donde ocurren cualquier eventos. */
    protected BaZCanvasDelegate delegate = null;

    /** Lienzo sobre el que se generan todos los eventos por componentes dibujables. */
    protected BaZCanvas canvas = null;

    /**
     * Contruye un evento abstracto asignado el lienzo especificado.
     *
     * @param canvas
     *        Lienzo que genera los eventos y que serán gestionados.
     */
    public BaZAbstractEvent(BaZCanvas canvas) {
        this.canvas = canvas;
    }

    /*
     * Asigna al delegado de clase el delegado especificado.
     *
     * @param delegate
     *        Delegado desde donde se generan los eventos.
     */
    public void setDelegate(BaZCanvasDelegate delegate) {
        this.delegate = delegate;
    }

    /**
     * Modifica el gesto de eventos asignado referenciandolo al gestor de
     * eventos especificado.
     *
     * @param manager
     *        Gestor de eventos a ser referenciado.
     */
    public void setManager(BaZEventManager manager) {
        this.manager = manager;
    }

    /**
     * Añade un valor exclusivo al gesto de eventos.
     *
     * @param value
     *        Valor a ser añañdido.
     */
    public void addExclusiveValue(Object value) {
        manager.addExclusiveValue(value);
    }

    /**
     * Remueve el valor especificado desde la lista de valores exclusivos de
     * gestor de eventos.
     *
     * @param value
     *        Valor a ser removido.
     */
    public void removeExclisiveValue(Object value) {
        manager.removeExclisiveValue(value);
    }

    /**
     * Verifica que el valor especificado se encuentre en la lista de valores
     * exclusivos del gestor de eventos. Retorna <code>true</code> si el valor
     * es encontrado, de lo contrario retorna <code>false</code>.
     *
     * @param value
     *        Valor a buscar en la lista de valores exclusivos.
     *
     * @return <code>true</code> si el valor es encontrado, de lo contrario <code>false</code>.
     */
    public boolean hasExclusiveValue(Object value) {
        return manager.hasExclusiveValue(value);
    }

    /**
     * Determinar si la figura especificada debería poder ser enfocada o no.
     *
     * @param shape
     *        Elemento a determinar debería de ser enfocado o no.
     */
    public boolean shouldFocusedOnShape(BaZAbstractShape shape) {
        return true;
    }

    /* --- METODOS QUE REALIZAN ALGUNA ACCIÓN PARA CADA UNA DE LOS EVENTO GENERADOS ---
     * Éstos métodos podrán ser sobre-escritos por las clases que implementen
     * ésta clase. */

    /**
     * Dibuja sobre el <code>Graphics</code> especificado lo relacionado con el
     * evento que hereda esta clase y que ocurre en el lienzo principal.
     *
     * @param g
     *        Graphics del lienzo principal.
     */
    public void paint(Graphics g) {}

    public void mousePressed(MouseEvent e, Point mousePosition) {}

    public void mouseReleased(MouseEvent e, Point mousePosition) {}

    public void mouseDragged(MouseEvent e, Point mousePosition) {}

    public void mouseMoved(MouseEvent e, Point mousePosition) {}

    public void keyPressed(KeyEvent e) {}

    public void keyTyped(KeyEvent e) {}
}
