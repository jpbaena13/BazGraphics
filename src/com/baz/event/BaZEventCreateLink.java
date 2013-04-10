/*
 * BaZEventCreateLink.java	0.1  6/05/2011
 *
 * Copyright 2011 Universidad de Antioquia. All rights reserved.
 */
package com.baz.event;

import com.baz.shape.base.BaZAnchor2D;
import com.baz.shape.base.BaZVector2D;
import com.baz.canvas.BaZCanvas;
import com.baz.shape.BaZAbstractShape;
import com.baz.shape.BaZShapeLink;
import com.baz.shape.link.BaZAbstractLink;
import com.baz.shape.link.BaZLinkArc;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;

/**
 * Manejador de acciones para crear enlaces entre las diferentes figuras que se
 * pintan sobre el lienzo principal.
 *
 * @author JpBaena
 * @version 0.1
 * @since 1.6
 */
public class BaZEventCreateLink extends BaZAbstractEvent {

    /** Figura desde donde comienza el enlace. */
    public BaZAbstractShape startShape = null;

    /** Llave del ancla para la figura de la parte inicial del enlace. */
    public String startAnchorKey = null;

    /** Figura de tipo <code>BaZAbstractLink</code> que se crea con el evento. */
    public BaZAbstractLink linkShape = null;

    /** Figura de tipo <code>BaZAbstractLink</code> que se crea con el evento. */
    public BaZLinkArc linkArc = null;

    /**
     * Contruye el manejador para la creación de enlaces entre figuras.
     *
     * @param canvas
     *        Lienzo principal donde se dibujan las figuras.
     */
    public BaZEventCreateLink(BaZCanvas canvas) {
        super(canvas);
    }
    
    /**
     * Permite modificar el ancla inicial del enlace.
     * 
     * @param anchor
     *        Ancla inicial para el enlace.
     */
    private void setLinkStartAnchor(BaZAnchor2D anchor) {
        linkArc.setStartAnchor(anchor);
    }

    /**
     * Permite modificar la posición y dirección del enlace en su punto final.
     *
     * @param position
     *        Posición para el punto final del enlace.
     * @param direction
     *        Dirección para el punto final del enlace.
     */
    private void setLinkEnd(BaZVector2D position, BaZVector2D direction) {
        linkArc.setEndPosition(position);
        linkArc.setEndDirection(direction);
    }

    /**
     * Permite determinar si el link que se va a crear es un bucle o no. Un link
     * bucle es un enlace tal que la figura origen y la figura destino es la misma.
     *
     * @param hasLoop <code>true</code> para determinar de que el link es un link
     *                bucle, o <code>false</code> para determinar que no lo es.
     */
    private void setLinkHasLoop(boolean hasLoop) {
        linkArc.setHasLoop(hasLoop);
    }

    @Override
    public boolean shouldFocusedOnShape(BaZAbstractShape shape) {
        if (startShape == null)
            return true;
        else
            return shape.acceptIncomingLink();
    }

    /**
     * Actualiza el enlace de acuerdo a la posición del mouse cuando apenas
     * se está creando. Permite la definción de bucle para el link cuando el
     * mouse está sobre la figura origen como al figura destino.
     *
     * @param mouse
     *        Posición del mouse.
     */
    public void updateLink(Point mousePosition) {
        BaZAbstractShape shape = delegate.searchShapeAtPoint(mousePosition);

        boolean hasLoop = (shape == startShape);
        this.setLinkStartAnchor(startShape.getAnchor(startAnchorKey));

        if (shape == null || shape instanceof BaZShapeLink) {
            this.setLinkEnd(BaZVector2D.createBazVector2D(mousePosition), BaZAnchor2D.DIRECTION_BOTTOM);

        } else {
            BaZAnchor2D anchor = shape.getAnchorClosestToPoint(mousePosition);
            String anchorKey = shape.getAnchorKeyClosestToPoint(mousePosition);
            this.setLinkEnd(anchor.getPosition(), anchor.getDirection());

            if (hasLoop) {
                if (anchor.getDirection() == BaZAnchor2D.DIRECTION_FREE)
                    linkArc.setMouse(mousePosition);
                else
                    linkArc.setMouse(anchor.getPosition().operation(anchor.getDirection(), BaZVector2D.ADD));

                linkArc.setEndTangentOffset(startShape.getDefaultAnchorOffset(anchorKey));

            } else {
                linkArc.setMouse(mousePosition);
                linkArc.setEndTangentOffset(shape.getDefaultAnchorOffset(anchorKey));
            }

            if (hasLoop && canvas.defaultShapeLink() == BaZShapeLink.SHAPE_ELBOW
                    && startShape.getAnchor(startAnchorKey).equals(anchor)) {
                linkShape = linkArc;

            } else if (canvas.defaultShapeLink() == BaZShapeLink.SHAPE_ELBOW)
                linkShape = linkArc;

            else if (canvas.defaultShapeLink() == BaZShapeLink.SHAPE_ARC)
                linkShape = linkArc;
        }

        this.setLinkHasLoop(hasLoop);

        linkShape.update();
    }

    @Override
    public void mousePressed(MouseEvent e, Point mousePosition) {
        BaZAbstractShape selectedShape = delegate.searchShapeAtPoint(mousePosition);

        if (startShape != null) {
            if (selectedShape != null) {
                int type = BaZShapeLink.SHAPE_ARC;

                delegate.createLink(startShape, startAnchorKey, selectedShape,
                        selectedShape.getAnchorKeyClosestToPoint(mousePosition), type, mousePosition);
            }
            this.removeExclisiveValue(BaZEventManager.EXCLUSIVE_CREATE_LINK_VALUE);
            startShape = null;
            linkShape = null;
            delegate.repaint();
            return;
        }

        if (selectedShape == null || !selectedShape.acceptOutcomingLink())
            return;

        int mask = InputEvent.SHIFT_DOWN_MASK + InputEvent.BUTTON1_DOWN_MASK;

        if ((e.getModifiersEx() & mask) == mask || delegate.canCreateLink()) {
            startShape = selectedShape;
            startAnchorKey = startShape.getAnchorKeyClosestToPoint(mousePosition);

            linkArc = new BaZLinkArc();
            linkArc.setStartTangentOffset(startShape.getDefaultAnchorOffset(startAnchorKey));

            linkShape = linkArc;
            
            linkShape.setFlatness(BaZCanvas.DEFAULT_LINK_FLATNESS);
            
            this.addExclusiveValue(BaZEventManager.EXCLUSIVE_CREATE_LINK_VALUE);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e, Point mousePosition) {
        if (startShape == null)
            return;

        this.updateLink(mousePosition);
        delegate.repaint();
    }

    @Override
    public void paint(Graphics g) {
        if (linkShape == null)
            return;

        linkShape.paintFeatures((Graphics2D) g);
    }
}
