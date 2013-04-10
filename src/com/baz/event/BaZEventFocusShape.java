/*
 * BaZEventFocusShape.java	0.1  1/05/2011
 *
 * Copyright 2011 Universidad de Antioquia. All rights reserved.
 */
package com.baz.event;

import com.baz.canvas.BaZCanvas;
import com.baz.shape.BaZAbstractShape;
import java.awt.Point;
import java.awt.event.MouseEvent;

/**
 * Manejador de acciones cuando el puntero del mouse se posiciona sobre una
 * figura establecida.
 * 
 * @author JpBaena
 * @version 0.1
 * @since 1.6
 */
public class BaZEventFocusShape extends BaZAbstractEvent {

    /** Elemento que está siendo enfocado. */
    public BaZAbstractShape focusedShape = null;

    /**
     * Construye un evento para el enfoque de objetos dentro del lienzo especificado.
     *
     * @param canvas
     *        Lienzo donde ocurrirá el enfoque del objeto.
     */
    public BaZEventFocusShape(BaZCanvas canvas) {
        super(canvas);
    }

    /**
     * Cambia la propiedad de enfocado a la figura especificada.
     *
     * @param shape
     *        Figura a cambiarle al propiedad de focus.
     */
    public void setFocusedShape(BaZAbstractShape shape) {
        if (focusedShape != null) {
            focusedShape.setFocused(false);
            delegate.removeFocusedShape(focusedShape);
            delegate.repaint();
        }

        focusedShape = shape;

        if (focusedShape != null) {
            focusedShape.setFocused(true);
            delegate.addFocusedShape(shape);
            delegate.repaint();
        }
    }

    @Override
    public void mouseDragged(MouseEvent e, Point mousePosition) {
        BaZAbstractShape shape = delegate.searchShapeAtPoint(mousePosition);

        if (shape != null) {
            if (manager.canFocusedOnShape(shape))
                this.setFocusedShape(shape);
        } else
            this.setFocusedShape(null);
    }

    @Override
    public void mouseMoved(MouseEvent e, Point mousePosition) {
        this.mouseDragged(e, mousePosition);
    }
}
