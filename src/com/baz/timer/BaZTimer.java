/*
 * BaZTimer.java	0.1  28/04/2011
 *
 * Copyright 2011 Universidad de Antioquia. All rights reserved.
 */
package com.baz.timer;

import com.baz.shape.BaZAbstractShape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Timer;

/**
 *
 * @author JpBaena
 * @version 0.1
 * @since 1.6
 */
public class BaZTimer {

    /** Lista de figuras que estarán asociadoas al timer*/
    protected List shapes = new ArrayList();

    /** Objeto <code>Timer</code> propio de la clase. */
    protected Timer timer = null;

    /** Lienzo sobre el que actuará el objeto <code>Timer</code>. */
    protected BaZTimerDelegate delegate = null;

    /** Retardo para eventos del objeto <code>Timer</code>. */
    protected int delay = 50;

    /**
     * Construye un timer para el objeto <code>BaZTimerDelegate</code> especificado.
     * con un valor de retardo por defecto de 50ms.
     *
     * @param delegate
     *        Objeto <code>BaZTimerDelegate</code> que por lo general es un lienzo
     *        sobre el cual pueden pintarse figuras dibujables.
     */
    public BaZTimer(BaZTimerDelegate delegate) {
        this.delegate = delegate;
    }

    /**
     * Construye un timer para el objeto <code>BaZTimerDelegate</code> especificado.
     * con un valor de retardo de acuerdo al especificado.
     *
     * @param delegate
     *        Objeto <code>BaZTimerDelegate</code> que por lo general es un lienzo
     *        sobre el cual pueden pintarse figuras dibujables.
     *
     * @param delay
     *        Retardo del timer.
     */
    public BaZTimer(BaZTimerDelegate delegate, int delay) {
        this.delegate = delegate;
        this.delay = delay;
    }

    /**
     * Modifica el retardo del timer
     *
     * @param delay
     *        Retardo para el timer.
     */
    public void setDelay(int delay) {
        this.delay = delay;
    }

    /**
     * Permite iniciar o detener el timer de acuerdo al número de elementos
     * que ésten asociados a él. Si el número de elementos es igual a cero el
     * timer es detenido, de lo contario es iniciado.
     */
    public void refresh() {
        if(shapes.isEmpty())
            this.stop();
        else
            this.start();
    }

    /**
     * Inicia el timer de acuerdo al retardo especificado a la instancia.
     */
    public void start() {
        if(timer == null) {
            timer = new Timer(delay, new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    delegate.timerFired(BaZTimer.this);
                }
            });
            timer.start();
        } else if(!timer.isRunning()) {
            timer.start();
        }
    }

    /**
     * Detiene el timer si este se encuentra corriendo.
     */
    public void stop() {
        if(timer == null)
            return;

        if(timer.isRunning())
            timer.stop();
    }

    /**
     * Añade la figura especificada a la lista de figuras de la instancia.
     *
     * @param shape
     *        Figura a ser añadida.
     */
    public synchronized void add(BaZAbstractShape shape) {
        if(shapes.contains(shape))
            return;

        shapes.add(shape);
        this.refresh();
    }

    /**
     * Remueve la figura especificada de la lista de figuras de la instancia.
     *
     * @param shape
     *        Figura a ser eliminada.
     */
    public synchronized void remove(BaZAbstractShape shape) {
        shapes.remove(shape);
        this.refresh();
    }

    /**
     * Limpia la lista eliminando todas las figuras añadidas en ella.
     */
    public synchronized void clear() {
        shapes.clear();
        this.refresh();
    }

    /**
     * Determina si la figura especificada se encuentra en la lista de ésta
     * instancia
     *
     * @param shape
     *        Figura a ser buscanda dentro de la lista.
     *
     * @return <code>true</code> si la figura se encuentra dentro de la lista
     *         de lo contrario retorna <code>false</code>.
     */
    public synchronized boolean contains(BaZAbstractShape shape) {
        return shapes.contains(shape);
    }

    /**
     * Retorna toda la lista de figuras asociadas a la instancia de clase.
     *
     * @return Lista de figuras.
     */
    public List getElements() {
        return shapes;
    }
}