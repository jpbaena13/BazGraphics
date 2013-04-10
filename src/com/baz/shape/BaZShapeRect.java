/*
 * BaZShapeRect.java	0.1  23/05/2011
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
 * Ésta clase crea una figura en forma de rectangulo a partir de las características
 * de una figura abstracta.
 *
 * @see BaZAbstractShape
 *
 * @author JpBaena
 * @version 0.1
 * @since 1.6
 */
public class BaZShapeRect extends BaZAbstractShape {

    /** Ancho por defecto del rectangulo. */
    public static final int DEFAULT_WIDTH = 40;

    /** Alto por defecto del rectangulo. */
    public static final int DEFAULT_HEIGHT = 40;

    /** Ancho del rectangulo. */
    protected int width;

    /** Alto del rectangulo*/
    protected int height;


    /**
     * Construye un rectangulo con los valores de alto y ancho por defecto.
     */
    public BaZShapeRect() {
        this.width = DEFAULT_WIDTH;
        this.height = DEFAULT_HEIGHT;
    }

    /**
     * Construye un rectangulo con los valores de alto y ancho especificados.
     *
     * @param width
     *        Ancho del rectangulo.
     *
     * @param height
     *        Alto del rectangulo.
     */
    public BaZShapeRect(int width, int height) {
        this.width = width;
        this.height = height;
    }

    /**
     * Modifica el ancho del rectangulo.
     *
     * @param width
     *        Ancho para el rectangulo.
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * Retorna el ancho actual del rectangulo.
     *
     * @return Valor del ancho del rectangulo.
     *
     */
    public int getWidth() {
        return this.width;
    }

    /**
     * Modifica el alto del rectangulo.
     *
     * @param radius
     *        Alt0 para el rectangulo.
     */
    public void setHeigth(int height) {
        this.height = height;
    }

    /**
     * Retorna el alto actual del rectangulo.
     *
     * @return Valor del alto del rectangulo.
     *
     */
    public int getHeight() {
        return this.height;
    }

    @Override
    public BaZRect getFrame() {
        double x = this.getX() - width/2;
        double y = this.getY() - height/2;
        double dx = width*2;
        double dy = height*2;
        return new BaZRect(x, y, dx, dy);
    }

    @Override
    public boolean isInside(Point p) {
       return Math.abs(p.getX() - this.getX()) < width/2 && Math.abs(p.getY() - this.getY()) < height/2;
    }

    @Override
    public double getDefaultAnchorOffset(String anchorKey) {
        if (anchorKey != null && anchorKey.equals(ANCHOR_CENTER))
            return width/2;

        return 0;
    }

    @Override
    public void updateAnchors() {
        this.setAnchor(ANCHOR_CENTER, position, BaZAnchor2D.DIRECTION_FREE);
        this.setAnchor(ANCHOR_TOP, position.operation(new BaZVector2D(0, -height/2), BaZVector2D.ADD), BaZAnchor2D.DIRECTION_TOP);
        this.setAnchor(ANCHOR_BOTTOM, position.operation(new BaZVector2D(0, height/2), BaZVector2D.ADD), BaZAnchor2D.DIRECTION_BOTTOM);
        this.setAnchor(ANCHOR_LEFT, position.operation(new BaZVector2D(-width/2, 0), BaZVector2D.ADD), BaZAnchor2D.DIRECTION_LEFT);
        this.setAnchor(ANCHOR_RIGHT, position.operation(new BaZVector2D(width/2, 0), BaZVector2D.ADD), BaZAnchor2D.DIRECTION_RIGHT);
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

        if (borderColor != null)
            g.setColor(borderColor);
        else
            g.setColor(Color.BLACK);
    }

    @Override
    public void paintShape(Graphics2D g) {
        super.paintShape(g);

        int x = (int) (this.getX() - width/2);
        int y = (int) (this.getY() - height/2);

        if (!this.isSelected()) {
            g.setColor(background);
            g.fillRect(x, y, (int) width, (int) height);
            g.setColor(borderColor);
        }
            
        g.drawRect(x, y, (int) width, (int) height);
    }
}