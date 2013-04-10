/*
 * BaZCreateShape.java	0.1  30/04/2011
 *
 * Copyright 2011 Universidad de Antioquia. All rights reserved.
 */
package com.baz.event;

import com.baz.canvas.BaZCanvas;
import com.baz.shape.BaZAbstractShape;
import java.awt.Point;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;

/**
 * Esta clase gestiona la creación de una figura dentro del lienzo pricipal.
 *
 * @author JpBaena
 * @version 0.1
 * @since 1.6
 */
public class BaZEventCreateShape extends BaZAbstractEvent {

    /**
     * Contructor por defecto. Solo llama al constructor padre.
     *
     * @param canvas
     *        Objeto <code>BaZCanvas</code> que realiza la función de lienzo principal.
     */
    public BaZEventCreateShape(BaZCanvas canvas) {
        super(canvas);
    }

    @Override
    public void mousePressed(MouseEvent e, Point mousePositiion) {
        BaZAbstractShape selectedShape = delegate.searchShapeAtPoint(mousePositiion);

        if (selectedShape == null && e.getModifiersEx() == InputEvent.BUTTON1_DOWN_MASK) {
                delegate.createShape(mousePositiion, !(e.getClickCount() == 1));
        }
    }
}
