/*
 * BaZEventDragSelection.java	0.1  29/04/2011
 *
 * Copyright 2011 Universidad de Antioquia. All rights reserved.
 */
package com.baz.event;

import com.baz.canvas.BaZCanvas;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;

/**
 * Manejador de acciones cuando el evento Drag está siendo disparado de un panel
 * pero el clic inicial no ocurre sobre una figura establecida.
 *
 * @see BaZCanvas
 * 
 * @author JpBaena
 * @version 0.1
 * @since 1.6
 */
public class BaZEventDragSelection extends BaZAbstractEvent {

    /** Determina si la acción del evento ocurre cuando se está accion una selección de elementos. */
    private boolean selecting = false;

    /** Punto inicial donde se dio clic donde se genera el evento. */
    private Point p1 = null;

    /** Punto que determina la posición del mouse durante tode el drag de éste. */
    private Point p2 = null;


    /**
     * Contruye un evento de selección por arrastre de mouse efectuado desde el
     * canvas especificado.
     *
     * @param canvas
     *        Lienzo donde ocurrirá el evento a ser gestionado.
     */
    public BaZEventDragSelection(BaZCanvas canvas) {
        super(canvas);
    }
    
    @Override
    public void mousePressed(MouseEvent e, Point mousePosition) {
        if (this.hasExclusiveValue(BaZEventManager.EXCLUSIVE_DRAG_VALUE)
                || e.getClickCount() != 1)
            return;

        int mask = InputEvent.BUTTON1_DOWN_MASK;

        if ((e.getModifiersEx() & mask) == mask && delegate.searchShapeAtPoint(mousePosition) == null) {
            this.addExclusiveValue(BaZEventManager.EXCLUSIVE_DRAG_VALUE);
            selecting = true;
            delegate.selectingAllShapes(false);
            p1 = mousePosition;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e, Point mousePosition) {
        this.removeExclisiveValue(BaZEventManager.EXCLUSIVE_DRAG_VALUE);
        selecting = false;
        p1 = null;
        p2 = null;
        delegate.repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e, Point mousePosition) {
        if (!selecting)
            return;

        p2 = mousePosition;
        delegate.selectingShapesInRect(p1.x, p1.y, p2.x - p1.x, p2.y - p1.y);
        delegate.repaint();
    }

    @Override
    public void paint(Graphics g) {
        if(selecting && p1 != null && p2 != null) {
            Graphics2D g2d = (Graphics2D)g;

            int x  = Math.min(p1.x, p2.x);
            int y  = Math.min(p1.y, p2.y);
            int dx = Math.abs(p2.x - p1.x);
            int dy = Math.abs(p2.y - p1.y);

            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f ));
            g.setColor(Color.GRAY);
            g.fillRect(x, y, dx, dy);
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f ));
            g.setColor(Color.WHITE);
            g.drawRect(x, y, dx, dy);
        }
    }
}
