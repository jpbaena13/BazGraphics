/*
 * BaZLabel.java	0.1  30/04/2011
 *
 * Copyright 2011 Universidad de Antioquia. All rights reserved.
 */
package com.baz.graphics.base;

import com.baz.shape.base.BaZRect;
import com.baz.shape.base.BaZVector2D;
import com.baz.shape.BaZAbstractShape;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;

/**
 * Ésta clase instancia un label para una figura en particular. Será implementada
 * para general el label que se encuentra en el centro de la figura.
 *
 * @see BaZAbstractShape
 * 
 * @author JpBaena
 * @version 0.1
 * @since 1.6
 */
public class BaZLabel {

    /** Posición del vector. */
    protected BaZVector2D position = null;

    /** Texto del label. */
    protected String text = null;

    /** COlor del texto. */
    protected Color color = Color.BLACK;

    /**Determina si el label es visible o no. */
    protected boolean visible = true;

    /**
     * Constructor vacio.
     */
    public BaZLabel() {}

    /**
     * Retorna el color del texto.
     *
     * @return <code>Color</code> del texto.
     */
    public Color getColor() {
        return color;
    }

    /**
     * Modifica el color del texto
     *
     * @param color
     *        <code>Color</code> para el texto.
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Retorna la posición actual del texto.
     *
     * @return Posición del texto.
     */
    public BaZVector2D getPosition() {
        return position;
    }

    /**
     * Modifica la posición de texto.
     *
     * @param position
     *        Posición para el texto.
     */
    public void setPosition(BaZVector2D position) {
        this.position = position;
    }

    /**
     * Modifica la posición de texto.
     *
     * @param x
     *        Coordenada X de para la posición del texto.
     *
     * @param y
     *        Coordenada Y para la posición el texto.
     */
    public void setPosition(int x, int y) {
        this.setPosition(new BaZVector2D(x, y));
    }

    /**
     * Retorna el texto que contiene el label.
     *
     * @return Texto del label.
     */
    public String getText() {
        return text;
    }

    /**
     * Modifica el texto que contiene el label.
     *
     * @param text
     *        Texto para el label.
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Determina si el label es o no visible
     *
     * @return <code>true</code> si el labe es visible, de lo contrario <code>false</code>.
     */
    public boolean isVisible() {
        return visible;
    }

    /**
     * Modifica la visibilidad del label.
     *
     * @param visible
     *        <code>true</code> para determinar que el label sea visible
     *        <code>false</code> para determinar que no es visible.
     */
    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    /**
     * Permite pintar el label en la posición del objeto.
     *
     * @param g
     *        <code>Graphics</code> de lienzo padre.
     */
    public void paint(Graphics g) {
        if (position == null || text == null || !visible)
            return;

        g.setColor(color);
        BaZLabel.paintCenteredString(text, position.getX(), position.getY(), g);
    }

    /**
     * Permite pintar el texto especificado en el centro del label.
     *
     * @param text
     *        Texto a pintar en el label.
     * @param x
     *        Coordenada X del label.
     * @param y
     *        Coordneada Y del label.
     * @param g
     *        <code>Graphics</code> de lienzo padre.
     */
    public static void paintCenteredString(String text, double x, double y, Graphics g) {
        if (text != null) {
            FontMetrics fm = g.getFontMetrics();
            int _x = (int) (x - fm.stringWidth(text) * 0.5);
            int _y = (int) (y + fm.getHeight() * 0.5);
            g.drawString(text, _x, _y);
        }
    }

    /**
     * Permite obtener el rectangulo que delimita la cadena de caracteres dentro
     * del <code>Graphics</code> especificado.
     *
     * @param s
     *        Cadena de caracteres que está contenida en el label.
     * @param x
     *        Coordenada X de la posición del label.
     * @param y
     *        Coordenada Y de la posición del label.
     * @param g
     *        Objeto <code>Graphics</code> donde es pintado el label.
     * @return
     */
    public static BaZRect getFrame(String s, double x, double y, Graphics g) {

        if (s != null && g != null) {
            FontMetrics fm = g.getFontMetrics();
            return new BaZRect(x - fm.stringWidth(s)*0.5, y - fm.getHeight()*0.5, fm.stringWidth(s), fm.getHeight());
        }

        return new BaZRect(0, 0, 0, 0);
    }
}