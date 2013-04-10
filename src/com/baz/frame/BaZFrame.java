/*
 * BaZFrame.java	0.1  28/04/2011
 *
 * Copyright 2011 Universidad de Antioquia. All rights reserved.
 */
package com.baz.frame;

import com.baz.apps.base.BaZApplicationDelegate;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;

/**
 * Clase que genera el frame de la aplicación.
 *
 * @author JpBaena
 * @version 0.1
 * @since 1.6
 */
public class BaZFrame extends JFrame implements BaZApplicationDelegate {

    /**
     * Contruyte un Frame con la propiedades por defecto.
     */
    public BaZFrame() {
        super();
        this.addWindowListener(new DefaultWindowListener());
        this.addWindowFocusListener(new DefaultWindowListener());
        this.addComponentListener(new DefaultComponentListener());
    }

    /**
     * Contruyte un Frame con la propiedades por defecto añadiendo el título especificado.
     */
    public BaZFrame(String title) {
        super(title);
        this.addWindowListener(new DefaultWindowListener());
        this.addWindowFocusListener(new DefaultWindowListener());
        this.addComponentListener(new DefaultComponentListener());
    }

    /* Clase Adapter para los eventos de ventana. */
    public class DefaultWindowListener extends WindowAdapter {
        @Override
        public void windowActivated(WindowEvent e) {
            BaZFrame.this.windowActivated();
        }

        @Override
        public void windowDeactivated(WindowEvent e) {
            if(this != null)
                BaZFrame.this.windowDeactivated();
        }
    }

    /* Clase Adapter para los eventos de componente. */
    public class DefaultComponentListener extends ComponentAdapter {
        @Override
        public void componentResized(ComponentEvent e) {
        }
    }

    /* --- METODOS DE INTERFAZ BaZApplicationDelegate --- */
    
    @Override
    public final Container getContentPane() {
        return super.getContentPane();
    }

    @Override
    public final void addComponentListener(ComponentAdapter adapter) {
        super.addComponentListener(adapter);
    }

    @Override
    public final Dimension getSize() {
        return super.getSize();
    }

    @Override
    public String getString(String key) {
        return null;
    }

    /**
     * Acción que se lleva a cabo cuando la ventana o frame es activada.
     */
    public void windowActivated() {
    }

    /**
     * Acción que se lleva a cabo cuando la ventana o frame es desactivada.
     */
    public void windowDeactivated() {
    }
}
