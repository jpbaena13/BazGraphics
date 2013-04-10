/*
 * BaZShapeLabel.java	0.1  18/05/2011
 *
 * Copyright 2011 Universidad de Antioquia. All rights reserved.
 */
package com.baz.shape;

import com.baz.graphics.base.BaZLabel;
import com.baz.shape.base.BaZRect;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;

/**
 * Esta clase crea un figura de tipo label que contiene el texto especificado.
 *
 * @see BaZAbstractShape
 * 
 * @author JpBaena
 * @version 0.1
 * @since 1.6
 */
public class BaZShapeLabel extends BaZAbstractShape {

    /** Elemento grafico del label. */
    protected transient Graphics2D g2d = null;

    /**
     * Constructor por defecto
     */
    public BaZShapeLabel() {
        super();
    }

    @Override
    public BaZRect getFrame() {
        return BaZLabel.getFrame(this.getText(), this.getX(), this.getY(), g2d);
    }

    @Override
    public boolean isInside(Point p) {
        return this.getFrame().contains(p);
    }

    @Override
    public void paintFeatures(Graphics2D g) {
        super.paintFeatures(g);

        this.g2d = g;

        if (textVisible) {
            g2d.setColor(textColor);
            g2d.setFont(new Font("Verdana", Font.BOLD, 11));
            BaZLabel.paintCenteredString(this.getText(), (int)getX(), (int)getY(), g);
        }
    }
}