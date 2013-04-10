/*
 * BaZShapeCircle.java	0.1  30/04/2011
 *
 * Copyright 2011 Universidad de Antioquia. All rights reserved.
 */
package com.baz.shape;

import com.baz.graphics.base.BaZLabel;
import com.baz.shape.base.BaZAnchor2D;
import com.baz.shape.base.BaZRect;
import com.baz.shape.base.BaZVector2D;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

/**
 * Ésta clase crea una figura en forma de circulo a partir de las características
 * de una figura abstracta.
 *
 * @see BaZAbstractShape
 * 
 * @author JpBaena
 * @version 0.1
 * @since 1.6
 */
public class BaZShapeCircle extends BaZAbstractShape {

    /** Radio por defecto del círculo. */
    public static final int DEFAULT_RADIUS = 30;

    /** Radio del circulo. */
    protected double radius;
    

    /**
     * Construye un circulo con el tamaño de radio por defecto.
     */
    public BaZShapeCircle() {
        this.radius = DEFAULT_RADIUS;
    }

    /**
     * Construye un cirulo con el tamaño del radio especificado.
     *
     * @param radius
     *        Tamaño del radio del círculo a construir.
     */
    public BaZShapeCircle(double radius) {
        this.radius = radius;
    }

    /**
     * Modifica el radio del círculo.
     *
     * @param radius
     *        Radio para el círculo.
     */
    public void setRadius(double radius) {
        this.radius = radius;
    }

    /**
     * Retorna el radio actual del círculo.
     *
     * @return Valor del radio del círculo.
     *
     */
    public double getRadius() {
        return this.radius;
    }

    @Override
    public BaZRect getFrame() {
        double x = this.getX() - radius;
        double y = this.getY() - radius;
        double dx = radius*2;
        double dy = radius*2;
        return new BaZRect(x, y, dx, dy);
    }

    @Override
    public boolean isInside(Point p) {
       return Math.abs(p.getX() - this.getX()) < radius && Math.abs(p.getY() - this.getY()) < radius;
    }

    @Override
    public double getDefaultAnchorOffset(String anchorKey) {
        if (anchorKey != null && anchorKey.equals(ANCHOR_CENTER))
            return radius;

        return 0;
    }

    @Override
    public void updateAnchors() {
        this.setAnchor(ANCHOR_CENTER, position, BaZAnchor2D.DIRECTION_FREE);
        this.setAnchor(ANCHOR_TOP, position.operation(new BaZVector2D(0, -radius), BaZVector2D.ADD), BaZAnchor2D.DIRECTION_TOP);
        this.setAnchor(ANCHOR_BOTTOM, position.operation(new BaZVector2D(0, radius), BaZVector2D.ADD), BaZAnchor2D.DIRECTION_BOTTOM);
        this.setAnchor(ANCHOR_LEFT, position.operation(new BaZVector2D(-radius, 0), BaZVector2D.ADD), BaZAnchor2D.DIRECTION_LEFT);
        this.setAnchor(ANCHOR_RIGHT, position.operation(new BaZVector2D(radius, 0), BaZVector2D.ADD), BaZAnchor2D.DIRECTION_RIGHT);
    }

    @Override
    public void paintFeatures(Graphics2D g) {
        g.setStroke(borderStroke);

        this.paintShape(g);

        g.setStroke(STROKE_NORMAL);

        if (textVisible) {
            g.setColor(textColor);
            BaZLabel.paintCenteredString(this.getText(), this.getX(), this.getY(), g);
        }
    }

    @Override
    public void paintShape(Graphics2D g) {
        super.paintShape(g);
        
        int x = (int) (this.getX() - radius);
        int y = (int) (this.getY() - radius);

        if (!this.isSelected()) {
            g.setColor(background);
            g.fillOval(x, y, (int) radius*2, (int) radius*2);
        }

        if (!this.isSelected()) {
            if (borderColor != null)
                g.setColor(borderColor);
            else
                g.setColor(Color.BLACK);
        }
        g.drawOval(x, y, (int) radius*2, (int) radius*2);
    }
}