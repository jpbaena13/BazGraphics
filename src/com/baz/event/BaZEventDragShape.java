/*
 * BaZEventDragShape.java	0.1  1/05/2011
 *
 * Copyright 2011 Universidad de Antioquia. All rights reserved.
 */
package com.baz.event;

import com.baz.shape.base.BaZVector2D;
import com.baz.canvas.BaZCanvas;
import com.baz.shape.BaZAbstractShape;
import java.awt.Point;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;

/**
 * Manejador de acciones cuando una figura va a ser arrastrada por el lienzo
 * principal.
 * 
 * @author JpBaena
 * @version 0.1
 * @since 1.6
 */
public class BaZEventDragShape extends BaZAbstractEvent {

    /** Figura que será arrastrado por el evento. */
    public BaZAbstractShape dragShape = null;

    /** Determina si la figura será arrastrada o no.*/
    protected boolean dragging = false;

    /** Posición inmediatamente actual del mouse en el movientro de arrastre. */
    protected Point p1 = null;

    /** Posición anterior a la posición actual del mouse en el movientro de arrastre. */
    protected Point p2 = null;

    /** Offset para el movimiento de arrastre. */
    protected BaZVector2D dragShapeOffset = null;

    /**
     * Construye un evento para el arrastre de objetos dentro del lienzo especificado.
     *
     * @param canvas
     *        Lienzo donde ocurrirá el arrastre del objeto.
     */
    public BaZEventDragShape(BaZCanvas canvas) {
        super(canvas);
    }

    @Override
    public void mousePressed(MouseEvent e, Point mousePosition) {
        if (this.hasExclusiveValue(BaZEventManager.EXCLUSIVE_DRAG_VALUE)
                || this.hasExclusiveValue(BaZEventManager.EXCLUSIVE_CREATE_LINK_VALUE)
                || e.getClickCount() != 1
                || (e.getModifiersEx() & InputEvent.SHIFT_DOWN_MASK) == InputEvent.SHIFT_DOWN_MASK
                || (e.getModifiersEx()) == InputEvent.BUTTON3_DOWN_MASK
                || (e.getModifiersEx()) == InputEvent.BUTTON2_DOWN_MASK)
            return;

        dragShape = delegate.searchShapeAtPoint(mousePosition);

        if (dragShape != null && delegate.isSelectedShape(dragShape)) {
            this.addExclusiveValue(BaZEventManager.EXCLUSIVE_DRAG_VALUE);
            dragging = true;
            p1 = mousePosition;

        } else if (dragShape != null && dragShape.isDraggable()) {
            this.addExclusiveValue(BaZEventManager.EXCLUSIVE_DRAG_VALUE);
            dragShape.startingDrag();
            dragShapeOffset = BaZVector2D.createBazVector2D(mousePosition).
                    operation(dragShape.getPosition(), BaZVector2D.SUB);
            return;
        }

        dragShape = null;
    }

    @Override
    public void mouseReleased(MouseEvent e, Point mousePosition) {
        this.removeExclisiveValue(BaZEventManager.EXCLUSIVE_DRAG_VALUE);

        dragShape = null;
        dragging = false;
    }

    @Override
    public void mouseDragged(MouseEvent e, Point mousePosition) {
        if (dragShape != null) {
            BaZVector2D mouse = BaZVector2D.createBazVector2D(mousePosition);
            BaZVector2D position = mouse.operation(dragShapeOffset, BaZVector2D.SUB);

            dragShape.moveToPosition(position);

            delegate.repaint();
            
        } else if (dragging) {
            p2 = mousePosition;
            delegate.moveSelectedShapes(p2.getX() - p1.getX(), p2.getY() - p1.getY());
            p1 = p2;
            delegate.repaint();
        }
    }

}
