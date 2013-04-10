/*
 * BaZPanel.java	0.1  27/04/2011
 *
 * Copyright 2011 Universidad de Antioquia. All rights reserved.
 */
package com.baz.frame;

import java.awt.BasicStroke;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import javax.swing.JPanel;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

/**
 * Panel básico que será extendido por la clase <code>BaZCanvas</code> la cual
 * servirá como lienzo principal para la creación de figuras dibujables.
 * Ésta clase añade todos los eventos de mouse y teclado para que sus acciones
 * sean manejanadas desde los método manejadores (Handler's).
 *
 * @author JpBaena
 * @version 0.1
 * @since 1.6
 */
public class BaZPanel extends JPanel {

    /** Atributo de renderización para líneas de figuras primitivas. */
    protected static final BasicStroke strokeNormal = new BasicStroke(1);

    /** Atributo de renderización para líneas de figuras primitivas. */
    protected static final BasicStroke strokeBold = new BasicStroke(2);

    /**
     * Contructor de clase. Añade el panel a los eventos de Mouse y Teclado
     * para que sean gestionados por medio de sus método manejadores.
     */
    public BaZPanel() {
        this.setFocusable(true);

        this.addMouseListener(new DefaultMouseListener());
        this.addMouseMotionListener(new DefaultMouseMotionListener());
        this.addKeyListener(new DefaultKeyListener());
        this.addFocusListener(new DefaultFocusListener());
        this.addMouseWheelListener(new DefaultMouseWheelListener());

    }

    /* --- CLASES Y METODOS MANEJADORES DE EVENTOS DE MOUSE Y TECLADO --- */
    public void handlerMousePressed(MouseEvent e) {}
    public void handlerMouseReleased(MouseEvent e) {}
    public void handlerMouseDragged(MouseEvent e) {}
    public void handlerMouseMoved(MouseEvent e) {}
    public void handlerMouseEntered(MouseEvent e) {}
    public void handlerMouseExited(MouseEvent e) {}
    public void handlerKeyTyped(KeyEvent e) {}
    public void handlerKeyPressed(KeyEvent e) {}
    public void handlerMouseWheel(MouseWheelEvent e) {}

    private class DefaultMouseMotionListener extends MouseMotionAdapter {
        @Override
        public void mouseDragged(MouseEvent e) {
            handlerMouseDragged(e);
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            handlerMouseMoved(e);
        }
    }

    private class DefaultMouseListener extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent e) {
            handlerMousePressed(e);
            requestFocus();
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            handlerMouseReleased(e);
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            handlerMouseEntered(e);
        }

        @Override
        public void mouseExited(MouseEvent e) {
            handlerMouseExited(e);
        }
    }

    private class DefaultMouseWheelListener implements MouseWheelListener {
        public void mouseWheelMoved(MouseWheelEvent e) {
            handlerMouseWheel(e);
        }

    }

    private class DefaultKeyListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            handlerKeyPressed(e);
        }

        @Override
        public void keyTyped(KeyEvent e) {
            handlerKeyTyped(e);
        }
    }

    private class DefaultFocusListener extends FocusAdapter {
        @Override
        public void focusGained(FocusEvent e) {
            repaint();
        }

        @Override
        public void focusLost(FocusEvent e) {
            repaint();
        }
    }

    public class ContextualMenuListener implements PopupMenuListener {

        @Override
        public void popupMenuWillBecomeVisible(PopupMenuEvent event) {}

        @Override
        public void popupMenuWillBecomeInvisible(PopupMenuEvent event) {
            repaint();
        }

        @Override
        public void popupMenuCanceled(PopupMenuEvent event) {}
    }
}