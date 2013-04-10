/*
 * BaZEventEditLink.java	0.1  18/05/2011
 *
 * Copyright 2011 Universidad de Antioquia. All rights reserved.
 */
package com.baz.event;

import com.baz.canvas.BaZCanvas;
import com.baz.shape.BaZAbstractShape;
import com.baz.shape.BaZShapeLink;
import java.awt.Point;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;

/**
 * Ésta clase gestiona el evento que modifica un enlace
 *
 * @author JpBaena
 * @version 0.1
 * @since 1.6
 */
public class BaZEventEditLink extends BaZAbstractEvent {

    /** Referencia al enlace que esta seleccionado y sera modificado. */
    public BaZAbstractShape selectedLink = null;

    /**
     * Construye un evento de modificación de enlaces.
     *
     * @param canvas
     *        Lienzo donde ocurrirá el evento a ser gestionado.
     */
    public BaZEventEditLink(BaZCanvas canvas) {
        super(canvas);
    }

    @Override
    public void mousePressed(MouseEvent e, Point mousePosition) {

        if (this.hasExclusiveValue(BaZEventManager.EXCLUSIVE_DRAG_VALUE) 
                || e.getClickCount() != 1
                || (e.getModifiersEx()) == InputEvent.BUTTON3_DOWN_MASK
                || (e.getModifiersEx()) == InputEvent.BUTTON2_DOWN_MASK)
            return;
        
        selectedLink = delegate.searchShapeAtPoint(mousePosition);

        if (selectedLink == null
                || !(selectedLink instanceof BaZShapeLink)
                || !((BaZShapeLink) selectedLink).getLink().isEditable()) {

            selectedLink = null;
            return;
        }

        this.addExclusiveValue(BaZEventManager.EXCLUSIVE_DRAG_VALUE);
    }

    @Override
    public void mouseReleased(MouseEvent e, Point mousePosition) {
        this.removeExclisiveValue(BaZEventManager.EXCLUSIVE_DRAG_VALUE);
        selectedLink = null;
    }

    @Override
    public void mouseDragged(MouseEvent e, Point mousePosition) {

        if (selectedLink == null)
            return;

        ((BaZShapeLink) selectedLink).setMousePosition(mousePosition);

        delegate.repaint();
    }

    @Override
    public boolean shouldFocusedOnShape(BaZAbstractShape shape) {
        return selectedLink == null;
    }
}
