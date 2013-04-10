/*
 * BaZArrow.java	0.1  6/05/2011
 *
 * Copyright 2011 Universidad de Antioquia. All rights reserved.
 */
package com.baz.shape.link;

import com.baz.shape.base.BaZVector2D;
import java.awt.Graphics;

/**
 * Esta clase especifica un flecha en característica y forma que puede ser
 * añadida en los extremos de un objeto <code>BaZAbstractLink</code>.
 *
 * @author JpBaena
 * @version 0.1
 * @since 1.6
 */
public class BaZArrow {

    /** Punto ancla de la flecha. */
    protected BaZVector2D anchor = null;

    /** Dirección de la flecha. */
    protected BaZVector2D direction = null;

    /** Longiutd de la flecha. */
    protected double length = 10;

    /** Angulo de las puntas de la flecha. */
    protected double angle = 45;

    /**
     * Constructor vacio.
     */
    public BaZArrow() {}

    /**
     * Modifica el ancla de la flecha ubicándola en posición especificada
     * por las coordenadas X,Y.
     *
     * @param x
     *        Coordenada X del ancla de la flecha.
     * @param y
     *        Coordenada Y del ancla de la flecha.
     */
    public void setAnchor(double x, double y) {
        anchor = new BaZVector2D(x, y);
    }

    /**
     * Modifica el ancla de la flecha ubicándola en posición dada por el vector
     * especificado.
     *
     * @param anchor
     *        Objeto que determinará la posición del ancla de la flecha.
     */
    public void setAnchor(BaZVector2D anchor) {
        this.anchor = anchor;
    }

    /**
     * Retorna el ancla de la flecha.
     *
     * @return Ancla de la flecha.
     */
    public BaZVector2D getAnchor() {
        return this.anchor;
    }

    /**
     * Modifica la dirección de la flecha.
     *
     * @param direction
     *        Vector que dará la nueva dirección a la flecha.
     */
    public void setDirection(BaZVector2D direction) {
        if (direction != null)
            this.direction = direction.copy();
        else
            this.direction = null;
    }

    /**
     * Retorna el vector dirección de la flecha.
     *
     * @return Vector dirección de la fleha.
     */
    public BaZVector2D getDirection() {
        return this.direction;
    }

    /**
     * Modifica la longitud de la flecha.
     *
     * @param length Longitud para la flecha.
     */
    public void setLength(double length) {
        this.length = length;
    }

    /**
     * Retorna la longiutd de la flecha.
     *
     * @return Longitud de la flecha.
     */
    public double getLength() {
        return this.length;
    }

    /**
     * Modifica el ángulo entre la sepación de la punta de la flecha.
     *
     * @param angle Angúlo para la separación de la punta.
     */
    public void setAngle(double angle) {
        this.angle = angle;
    }

    /**
     * Retorna el ángulo de separación entre las puntas de la flecha.
     *
     * @return Ángulo de separación entre las puntas de la flecha.
     */
    public double getAngle() {
        return this.angle;
    }

    /**
     * Dibuja el link, con las característicadas dadas, en el <code>Graphics</code>
     * especificad.
     *
     * @param g Objeto <code>Graphics</code> donde se pintará el link.
     */
    public void paint(Graphics g) {
        if (direction == null || anchor == null)
            return;

        BaZVector2D dir = direction.copy();
        dir.setLength(length);

        dir.rotate(angle*0.5);
        g.drawLine((int) anchor.getX(), (int) anchor.getY(), (int)(anchor.getX() + dir.getX()), (int) (anchor.getY() + dir.getY()));

        dir.rotate(-angle);
        g.drawLine((int) anchor.getX(), (int) anchor.getY(), (int)(anchor.getX() + dir.getX()), (int) (anchor.getY() + dir.getY()));
    }
}
