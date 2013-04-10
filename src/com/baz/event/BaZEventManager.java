/*
 * BaZEventManager.java	0.1  29/04/2011
 *
 * Copyright 2011 Universidad de Antioquia. All rights reserved.
 */
package com.baz.event;

import com.baz.canvas.BaZCanvasDelegate;
import com.baz.shape.BaZAbstractShape;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Gestor de eventos de mouse y teclado, Se encarga de llevar a cabo los
 * diferentes eventos que se presentan sobre el panel principal.
 *
 * @author JpBaena
 * @version 0.1
 * @since 1.6
 */
public class BaZEventManager {

    /** Bandera para un evento de pintar sobre lienzo. */
    public static final int EVENT_PAINT = 1;

    /** Bandera para evento de mouse presionado. */
    public static final int EVENT_MOUSE_PRESSED = 2;

    /** Bandera para evento de mouse liberado. */
    public static final int EVENT_MOUSE_RELEASED = 3;

    /** Bandera para evento de mouse arrastrado. */
    public static final int EVENT_MOUSE_DRAGGED = 4;

    /** Bandera para evento de mouse movido. */
    public static final int EVENT_MOUSE_MOVED = 5;

    /** Bandera para evento de tecla presionada*/
    public static final int EVENT_KEY_PRESSED = 6;

    /** Bandera para evento de tecla tipeada*/
    public static final int EVENT_KEY_TYPED = 7;

    /** Valor exclusivo para arrastrado de mouse. */
    public static final Integer EXCLUSIVE_DRAG_VALUE = new Integer(99);

    /** Valor exclusivo para cración de enlace. */
    public static final Integer EXCLUSIVE_CREATE_LINK_VALUE = new Integer(100);
    
    /** Lista de objetos matriculados para accionarse sobre algún evento generado desde el canvas. */
    private List eventObjects = new ArrayList();

    /** Conjunto de valores exclusivos. */
    private Set exclusiveValueSet = new HashSet();

    /** Not Specified. */
    private BaZCanvasDelegate eventDelegate = null;


    /**
     * Construye un gestor de evento para delegado a partir del delegado especificado.
     *
     * @param delegate
     *        Instancia a gestionar eventos de mouse y teclado.
     */
    public BaZEventManager(BaZCanvasDelegate eventDelegate) {
        this.eventDelegate = eventDelegate;
    }

    /**
     * Añade un evento abstracto para el manejador de eventos de la instancia delegada.
     *
     * @param event
     *        Evento a ser añadido.
     */
    public void add(BaZAbstractEvent event) {
        event.setManager(this);
        event.setDelegate(eventDelegate);
        eventObjects.add(event);
    }

    /**
     * Remueve el evento abstrato desde el manejador de eventos de la instancia delgada.
     *
     * @param event
     *        Evento a ser removido.
     */
    public void remove(BaZAbstractEvent event) {
        event.setManager(null);
        event.setDelegate(null);
        eventObjects.remove(event);
    }

    /**
     * Eliminar todos los eventos abstractos que contenta el manejador de eventos
     * de al instancia delegada.
     */
    public void clear() {
        for (Object event : eventObjects) {
            ((BaZAbstractEvent) event).setManager(null);
            ((BaZAbstractEvent) event).setDelegate(null);
        }

        eventObjects.clear();
    }

    /**
     * Añade el valor especificado a la lista de valores exclusivos.
     * 
     * @param value
     *        Valor a ser añadido.
     */
    public void addExclusiveValue(Object value) {
        exclusiveValueSet.add(value);
    }

    /**
     * Remueve el valor especificado desde la lista de valores exclusivos.
     *
     * @param value
     *        Valor a ser removido.
     */
    public void removeExclisiveValue(Object value) {
        exclusiveValueSet.remove(value);
    }

    /**
     * Verifica que el valor especificado se encuentre en la lista de valores
     * exclusivos. Retorna <code>true</code> si el valor es encontrado, de lo
     * contrario retorna <code>false</code.
     *
     * @param value
     *        Valor a buscar en la lista de valores exclusivos.
     *
     * @return <code>true</code> si el valor es encontrado, de lo contrario <code>false</code>.
     */
    public boolean hasExclusiveValue(Object value) {
        return exclusiveValueSet.contains(value);
    }

    public boolean canFocusedOnShape(BaZAbstractShape shape) {
        Iterator iterator = eventObjects.iterator();

        while(iterator.hasNext()) {
            BaZAbstractEvent eventObject = (BaZAbstractEvent) iterator.next();

            if (!eventObject.shouldFocusedOnShape(shape))
                return false;
        }

        return true;
    }

    /**
     * Accióna realizar cuando el un evento de mouse es ocurrido sobre el delegado
     * de eventos. Éste delegado de eventos viene siendo el lienzo principal.
     *
     * @param action
     *        Acción que ha de realizarse, debe ser alguna de las acciones especificadas por esta clase.
     *
     * @param event
     *        Evento que ocurre.
     *
     * @param mousePosition
     *        Posición del mouse sobre el lienzo donde ocurre el evento.
     *
     * @param param
     *        Parámetros de objeto.
     */
    public void performEventObjects(int action, Object event, Point mousePosition, Object param) {
        Iterator iterator = eventObjects.iterator();
        while(iterator.hasNext()) {
            BaZAbstractEvent eventObject = (BaZAbstractEvent)iterator.next();
            switch(action) {
                case EVENT_PAINT:
                    eventObject.paint((Graphics)param);
                    break;
                case EVENT_MOUSE_PRESSED:
                    eventObject.mousePressed((MouseEvent)event, mousePosition);
                    break;
                case EVENT_MOUSE_RELEASED:
                    eventObject.mouseReleased((MouseEvent)event, mousePosition);
                    break;
                case EVENT_MOUSE_DRAGGED:
                    eventObject.mouseDragged((MouseEvent)event, mousePosition);
                    break;
                case EVENT_MOUSE_MOVED:
                    eventObject.mouseMoved((MouseEvent)event, mousePosition);
                    break;
                case EVENT_KEY_PRESSED:
                    eventObject.keyPressed((KeyEvent)event);
                    break;
                case EVENT_KEY_TYPED:
                    eventObject.keyTyped((KeyEvent)event);
                    break;
            }
        }
    }
}
