/*
 * BaZAnchor2D.java	0.1  6/05/2011
 *
 * Copyright 2011 Universidad de Antioquia. All rights reserved.
 */
package com.baz.shape.base;

/**
 * Ésta clase crea un ancla 2D que especifica una posición y una dirección.
 *
 * @author JpBaena
 * @version 0.1
 * @since 1.6
 */
public class BaZAnchor2D {

    /** Llave utilizada para especificar una dirección libre. */
    public static final BaZVector2D DIRECTION_FREE = new BaZVector2D(0, 0);

    /** Llave utilizada para especificar una dirección hacia abajo. */
    public static final BaZVector2D DIRECTION_BOTTOM = new BaZVector2D(0, 1);

    /** Llave utilizada para especificar una dirección hacia arriba. */
    public static final BaZVector2D DIRECTION_TOP = new BaZVector2D(0, -1);

    /** Llave utilizada para especificar una dirección hacia la izquierda. */
    public static final BaZVector2D DIRECTION_LEFT = new BaZVector2D(-1, 0);

    /** Llave utilizada para especificar una dirección hacia la derecha. */
    public static final BaZVector2D DIRECTION_RIGHT = new BaZVector2D(1, 0);

    /** Posición de la ancla. */
    private BaZVector2D position = null;

    /** Dirección de la ancla. */
    private BaZVector2D direction = null;

    /**
     * Contructor vacio.
     */
    public BaZAnchor2D() {}

    /**
     * Construye un ancla a partir de la posición y la dirección especificada.
     *
     * @param position
     *        Vector posición para el ancla.
     * @param direction
     *        Vector dirección para el ancla.
     */
    public BaZAnchor2D(BaZVector2D position, BaZVector2D direction) {
        this.position = position;
        this.direction = direction;
    }

     /**
     * Retorna la posición del ancla.
     *
     * @return Posición del ancla.
     */
    public BaZVector2D getPosition() {
        return position;
    }

    /**
     * Modifica la posición del ancla.
     *
     * @param position
     *        Posición para el ancla.
     */
    public void setPosition(BaZVector2D position) {
        this.position = position;
    }

    /**
     * Retorna la dirección del ancla.
     *
     * @return Dirección del ancla.
     */
    public BaZVector2D getDirection() {
        return direction;
    }

    /**
     * Modifica la dirección del ancla.
     *
     * @param direction
     *        Dirección para el ancla.
     */
    public void setDirection(BaZVector2D direction) {
        this.direction = direction;
    }

    @Override
    @SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
    public boolean equals(Object other) {
        BaZAnchor2D anchor = (BaZAnchor2D) other;
        return position.equals(anchor.position) && direction.equals(anchor.direction);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + (this.position != null ? this.position.hashCode() : 0);
        hash = 83 * hash + (this.direction != null ? this.direction.hashCode() : 0);
        return hash;
    }
}
