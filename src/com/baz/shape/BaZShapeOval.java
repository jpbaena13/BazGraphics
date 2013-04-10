/*
 * BaZShapeOval.java	0.1  23/05/2011
 *
 * Copyright 2011 Universidad de Antioquia. All rights reserved.
 */
package com.baz.shape;

import com.baz.graphics.base.BaZLabel;
import com.baz.shape.base.BaZAnchor2D;
import com.baz.shape.base.BaZRect;
import com.baz.shape.base.BaZVector2D;
import java.awt.Graphics2D;
import java.awt.Point;

/**
 * Ésta clase crea una figura en forma de ovalo a partir de las características
 * de una figura abstracta.
 *
 * @see BaZAbstractShape
 *
 * @author JpBaena
 * @version 0.1
 * @since 1.6
 */
public class BaZShapeOval extends BaZAbstractShape {

    /** Ancho por defecto del ovalo. */
    public static final int DEFAULT_WIDTH = 50;

    /** Alto por defecto del ovalo. */
    public static final int DEFAULT_HEIGHT = 30;

    /** Ancho del ovalo. */
    protected int width;

    /** Alto del ovalo*/
    protected int height;


    /**
     * Construye un ovalo con los valores de alto y ancho por defecto.
     */
    public BaZShapeOval() {
        this.width = DEFAULT_WIDTH;
        this.height = DEFAULT_HEIGHT;
    }
    
    /**
     * Construye un ovalo con los valores de alto y ancho especificados.
     * 
     * @param width
     *        Ancho del ovalo.
     * 
     * @param height
     *        Alto del ovalo.
     */
    public BaZShapeOval(int width, int height) {
        this.width = width;
        this.height = height;
    }

    /**
     * Modifica el ancho del ovalo.
     *
     * @param width
     *        Ancho para el ovalo.
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * Retorna el ancho actual del ovalo.
     *
     * @return Valor del ancho del ovalo.
     *
     */
    public int getWidth() {
        return this.width;
    }

    /**
     * Modifica el alto del ovalo.
     *
     * @param radius
     *        Alto para el ovalo.
     */
    public void setHeigth(int height) {
        this.height = height;
    }

    /**
     * Retorna el alto actual del ovalo.
     *
     * @return Valor del alto del ovalo.
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
            return height/2;

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
    }

    @Override
    public void paintShape(Graphics2D g) {
        super.paintShape(g);

        int x = (int) (this.getX() - width/2);
        int y = (int) (this.getY() - height/2);

        if (!this.isSelected()) {
            g.setColor(background);
            g.fillOval(x, y, (int) width, (int) height);
        }

        if (!this.isSelected())
            g.setColor(borderColor);
        
        g.drawOval(x, y, (int) width, (int) height);
    }
}