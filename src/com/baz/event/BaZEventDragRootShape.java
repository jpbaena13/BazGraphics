/*
 * BaZEventDragRootShape.java	0.1  5/05/2011
 *
 * Copyright 2011 Universidad de Antioquia. All rights reserved.
 */
package com.baz.event;

import com.baz.shape.base.BaZVector2D;
import com.baz.canvas.BaZCanvas;
import java.awt.Point;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;

/**
 * Manejador de acciones para arrstrar de un solo clic todas las figuras dibujadas
 * sobre el lienzo principal al realizar el evento Drag a la figura raíz que las
 * contiene a todas.
 * 
 * @author JpBaena
 * @version 0.1
 * @since 1.6
 */
public class BaZEventDragRootShape extends BaZAbstractEvent {

    /** Determina si se está llevado a cabo la acción de arrastre todo el lienzo principal. */
    private boolean dragging = false;

    /**
     * Contruye un manejador de evento de arrastre de la figura raíz.
     *
     * @param canvas
     *        Lienzo principa donde se encuentra la figura raíz.
     */
    public BaZEventDragRootShape(BaZCanvas canvas) {
        super(canvas);
    }

    @Override
    public void mousePressed(MouseEvent e, Point mousePosition) {
        if (this.hasExclusiveValue(BaZEventManager.EXCLUSIVE_DRAG_VALUE)
                || e.getClickCount() != 1)
           return;

        int mask = InputEvent.SHIFT_DOWN_MASK + InputEvent.BUTTON1_DOWN_MASK;

        if ((e.getModifiersEx() & mask) == mask && delegate.searchShapeAtPoint(mousePosition) == null) {
            this.addExclusiveValue(BaZEventManager.EXCLUSIVE_DRAG_VALUE);
            delegate.getRootShape().startingDrag();
            dragging = true;
        }

    }

    @Override
    public void mouseReleased(MouseEvent e, Point mousePosition) {
        this.removeExclisiveValue(BaZEventManager.EXCLUSIVE_DRAG_VALUE);
        delegate.repaint();
        dragging = false;
    }

    @Override
    public void mouseDragged(MouseEvent e, Point mousePosition) {
        if (dragging) {
            delegate.getRootShape().drag(BaZVector2D.createBazVector2D(mousePosition));
            delegate.repaint();
        }
    }
}
