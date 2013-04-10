/*
 * BaZLink.java	0.1  6/05/2011
 *
 * Copyright 2011 Universidad de Antioquia. All rights reserved.
 */
package com.baz.shape.link;

import com.baz.graphics.base.BaZLabel;
import com.baz.shape.base.BaZAnchor2D;
import com.baz.shape.base.BaZRect;
import com.baz.shape.base.BaZVector2D;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

/**
 * Clase abstracta para la cración de un enlace entre dos posiciones dadas.
 * 
 * @author JpBaena
 * @version 0.1
 * @since 1.6
 */
public abstract class BaZAbstractLink {

    /** Posición inicial del enlace. */
    protected BaZVector2D startPosition;

    /** Posición final del enlace. */
    protected BaZVector2D endPosition;

    /** Dirección en el punto inicial del enlace. */
    protected BaZVector2D startDirection;

    /** Dirección en el punto final del enlace. */
    protected BaZVector2D endDirection;

    /** Dirección general del enlace. */
    protected BaZVector2D direction;

    /** Offset respecto al punto inicial del enlace. */
    protected BaZVector2D startOffset;

    /** Offset respecto al punto final del enlace. */
    protected BaZVector2D endOffset;

    /**Offset tangencial respecto al punto final del enlace. */
    protected double startTangentOffset;

    /**Offset tangencial respecto al punto inicial del enlace. */
    protected double endTangentOffset;

    /** Planaridad de la flecha cuando esta siendo creada. */
    protected double flatness;

    /** Punta en flecha que contendrá el enlace en la posición inicial. */
    protected BaZArrow startArrow = new BaZArrow();

    /** Punta en flecha que contendrá el enlace en la posición final. */
    protected BaZArrow endArrow = new BaZArrow();

    /** Label que contiene el enlace en el medio. */
    protected BaZLabel text = new BaZLabel();

    /** Determina si el enlace apunta hacia la misma figura o no. */
    protected boolean hasLoop = false;

    /** Determina si la flecha en la punta inicial del enlace es visible o no. */
    protected boolean startArrowVisible = false;

    /** Determina si la flecha en la punta final del enlace es visible o no. */
    protected boolean endArrowVisible = false;

    /** Determina si la flecha es editable o no. */
    protected boolean editable = true;

    /** Color del línea de enlace. */
    protected Color color = Color.BLACK;

    //transient objects
    protected transient BaZVector2D startWithOffset = null;
    protected transient BaZVector2D endWithOffset = null;

    /**
     * Constructor vacio.
     */
    public BaZAbstractLink() {}

    /**
     * Modifica el ancla inicial, esto es, la posición y la dirección en el
     * punto inicial del enlace.
     *
     * @param anchor
     *        Objeto <code>BaZAnchor2D</code> que contiene la posición y la
     *        dirección para el punto inicial del enlace.
     */
    public void setStartAnchor(BaZAnchor2D anchor) {
        this.setStartPosition(anchor.getPosition());
        this.setStartDirection(anchor.getDirection());
    }

    /**
     * Modifica el ancla final, esto es, la posición y la dirección en el
     * punto final del enlace.
     *
     * @param anchor
     *        Objeto <code>BaZAnchor2D</code> que contiene la posición y la
     *        dirección para el punto final del enlace.
     */
    public void setEndAnchor(BaZAnchor2D anchor) {
        this.setEndPosition(anchor.getPosition());
        this.setEndDirection(anchor.getDirection());
    }

    /**
     * Realiza un calculo de acuerdo al offset del enlace.
     */
    public void computeOffset() {
        startWithOffset = startPosition;
        endWithOffset = endPosition;

        if (startPosition != null && startOffset != null)
            startWithOffset = startPosition.operation(startOffset, BaZVector2D.ADD);

        if (endPosition != null && endOffset != null)
            endWithOffset = endPosition.operation(endOffset, BaZVector2D.ADD);
    }

    /**
     * Retorna la flecha contenida en la punta inicial del enlace
     *
     * @return Objeto <code>Arrow</code> ubicado en la punta inicial del enlace.
     */
    public BaZArrow getStartArrow() {
        return startArrow;
    }

    /**
     * Modifica la flecha contenida en la punta inicial del enlace.
     *
     * @param startArrow
     *        Flecha para la punta inicial del enlace.
     */
    public void setStartArrow(BaZArrow startArrow) {
        this.startArrow = startArrow;
    }

     /**
     * Retorna la flecha contenida en la punta final del enlace
     *
     * @return Objeto <code>Arrow</code> ubicado en la punta final del enlace.
     */
    public BaZArrow getEndArrow() {
        return endArrow;
    }

    /**
     * Modifica la flecha contenida en la punta final del enlace.
     *
     * @param endArrow
     *        Flecha para la punta final del enlace.
     */
    public void setEndArrow(BaZArrow endArrow) {
        this.endArrow = endArrow;
    }

    /**
     * Retorna la posicion del punto inicial del enlace.
     *
     * @return Posición del punto inicial del enlace.
     */
    public BaZVector2D getStartPosition() {
        return startPosition;
    }

    /**
     * Modifica la posición del punto inicial del enlace.
     *
     * @param startPosition
     *        Posición para el punto inicial del enlace.
     */
    public void setStartPosition(BaZVector2D startPosition) {
        this.startPosition = startPosition.copy();
        this.computeOffset();
    }

    /**
     * Modifica la posición del punto inicial del enlace ubicandolo en las coordenas
     * X,Y especificadas.
     *
     * @param x
     *        Coordenada X para la posición inicial del enlace.
     * @param Y
     *        Coordenada Y para la posición inicial del enlace.
     */
    public void setStartPosition(double x, double y) {
        this.setStartPosition(new BaZVector2D(x, y));
    }
    
    /**
     * Retorna la posición del punto final del enlace.
     *
     * @return Posición del punto final del enlace.
     */
    public BaZVector2D getEndPosition() {
        return endPosition;
    }

    /**
     * Modifica la posición del punto final del enlace.
     *
     * @param endPosition
     *        Posición final para el enlace.
     */
    public void setEndPosition(BaZVector2D endPosition) {
        this.endPosition = endPosition.copy();
        this.computeOffset();
    }

    /**
     * Modifica la posición del punto inicial del enlace de acuerdo a las coordenadas
     * X,Y especificadas.
     *
     * @param x
     *        Coordenada X para la posición del enlace.
     * @param y
     *        Coordenada Y para la posición del enlace.
     */
    public void setEndPosition(double x, double y) {
        this.setEndPosition(new BaZVector2D(x, y));
    }

    /**
     * Modifica la posición del punto inicial del enlace de acuerdo al punto
     * especificado
     *
     * @param x
     *        Punto para la posición del enlace
     */
    public void setEndPosition(Point p) {
        endPosition = BaZVector2D.createBazVector2D(p);
    }

    /**
     * Retorna la dirección del ancla para el punto inicial del enlace.
     *
     * @return Dirección ancla en el punto final del enlace.
     */
    public BaZVector2D getStartDirection() {
        return startDirection;
    }

    /**
     * Modifica la dirección del ancla para el punto incial del enlace.
     *
     * @param startDirection
     *        Dirección para el ancla del punto inicial del enlace.
     */
    public void setStartDirection(BaZVector2D startDirection) {
        this.startDirection = startDirection;
    }

    /**
     * Retorna la dirección del ancla para el punto final del enlace.
     *
     * @return Dirección ancla en el punto final del enlace.
     */
    public BaZVector2D getEndDirection() {
        return endDirection;
    }

    /**
     * Modifica la dirección del ancla para el punto final del enlace.
     *
     * @param endDirection
     *        Dirección para el ancla del punto final del enlace.
     */
    public void setEndDirection(BaZVector2D endDirection) {
        this.endDirection = endDirection;
    }

    /**
     * Retorna la dirección general del enlace.
     *
     * @return Dirección en general del enlace.
     */
    public BaZVector2D getDirection() {
        return direction;
    }

    /**
     * Modifica la dirección general del enlace
     *
     * @param direction
     *        Dirección en general para el enlace.
     */
    public void setDirection(BaZVector2D direction) {
        this.direction = direction;
    }

    /**
     * Retorna el color de la línea del enlace.
     *
     * @return Color de la línea del enlace.
     */
    public Color getColor() {
        return color;
    }

    /**
     * Modifica el color de la línea del enlace.
     *
     * @param color
     *        Color para la línea del enlace
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Retorna el offset del punto inicial del enlace.
     *
     * @return Offset del punto inicial del enlace.
     */
    public BaZVector2D getStartOffset() {
        return startOffset;
    }

    /**
     * Modifica el offset del punto inicial del enlace.
     *
     * @param startOffset
     *        Offset para el punto inicial del enlace.
     */
    public void setStartOffset(BaZVector2D startOffset) {
        this.startOffset = startOffset;
        this.computeOffset();
    }

    /**
     * Retorna el Ofsset del punto final del enlace.
     *
     * @return Offset del punto final del enlace.
     */
    public BaZVector2D getEndOffset() {
        return endOffset;
    }

    /**
     * Modifica el offset del punto final del enlace.
     *
     * @param endOffset
     *        Offset para el punto final del enlace.
     */
    public void setEndOffset(BaZVector2D endOffset) {
        this.endOffset = endOffset;
        this.computeOffset();
    }

    // ---  Métodos getters y setters para las variables transientes --
    public BaZVector2D getStartWithOffset() {
        return startWithOffset;
    }

    public void setStartWithOffset(BaZVector2D startWithOffset) {
        this.startWithOffset = startWithOffset;
    }

    public BaZVector2D getEndWithOffset() {
        return endWithOffset;
    }

    public void setEndWithOffset(BaZVector2D endWithOffset) {
        this.endWithOffset = endWithOffset;
    }

    /**
     * Retorna el offset tangencial del punto inicial del enlace.
     *
     * @return Offset tangencial del punto inicial del enlace.
     */
    public double getStartTangentOffset() {
        return startTangentOffset;
    }

    /**
     * Modifica el offset tangencial del punto inicial del lilnk
     *
     * @param startTangentOffset
     *        Offset tangencial para el punto inicial del enlace.
     */
    public void setStartTangentOffset(double startTangentOffset) {
        this.startTangentOffset = startTangentOffset;
    }

    /**
     * Retorna el offset tangencial del punto final del enlace.
     *
     * @return Offset tangencial del punto final del enlace.
     */
    public double getEndTangentOffset() {
        return endTangentOffset;
    }

    /**
     * Modifica el offset tangencial del punto final del enlace.
     *
     * @param endTangentOffse
     *        Offset tangencial para el punto final del enlace.
     */
    public void setEndTangentOffset(double endTangentOffset) {
        this.endTangentOffset = endTangentOffset;
    }

    /**
     * Retorna el flatness
     *
     * @return Flatness
     */
    public double getFlatness() {
        return flatness;
    }

    /**
     * Modifica el flatness
     *
     * @param flatness
     *        Flatness para la clase.
     */
    public void setFlatness(double flatness) {
        this.flatness = flatness;
    }

    /**
     * Retorna <code>true</code> si el enlace es editable graficamente,
     * <code>false</code> si no es editable.
     *
     * @return <code>true</code> si el enlace es editable, de lo contrario <code>false</code>
     */
    public boolean isEditable() {
        return editable;
    }

    /**
     * Determina si el enlace es editable o no.
     *
     * @param editable
     *        Bandera para determinar que el enlace sea editable o no.
     */
    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    /**
     * Retorna <code>true</code> si el enlace está dirigido hacia la misma figura
     * de donde parte, de lo contrario retorna <code>false</code>.
     *
     * @return <code>true</code> si el enlace es un bucle, de lo contrario <code>false</code>.
     */
    public boolean isHasLoop() {
        return hasLoop;
    }

    /**
     * Modifica el tipo de enlace. Éste puede ser bucle o no.
     *
     * @param hasLoop
     *        <code>true</code> para determinar que el enlace es un bucle, esto es,
     *        que apunta hacia la misma figura de la que parte, de lo contrario
     *        entre <code>false</code>.
     */
    public void setHasLoop(boolean hasLoop) {
        this.hasLoop = hasLoop;
    }

    /**
     * Retorna el texto que se ubica en el centro del enlace.
     *
     * @return Texto en el centro del enlace.
     */
    public BaZLabel getLabel() {
        return text;
    }

    /**
     * Modifica el texto que se encuentra en el centro del enlace.
     *
     * @param label
     *        Texto para el centro del enlace.
     */
    public void setLabel(BaZLabel text) {
        this.text = text;
    }

    /**
     * Modifica el texto que se encuentra en el centro del enlace.
     *
     * @param text
     *        Texto para el centro del enlace.
     */
    public void setLabel(String text) {
        this.text.setText(text);
    }

    /**
     * Modifica el color del texto posicionado en el centro del enlace.
     *
     * @param labelColor
     *        Color para el texto del enlace.
     */
    public void setLabelColor(Color labelColor) {
        text.setColor(color);
    }

    /**
     * Modifica la visibilidad el texto ubicado en el centro del enlace;
     * <code>true</code> para determinar que el label es visible,
     * <code>false</code> para ocultarlo.
     *
     * @param visible
     *        Visibilidad para el texto del centro del enlace.
     */
    public void setLabelVisible(boolean visible) {
        text.setVisible(visible);
    }

    /**
     * Retorna la visibilidad del texto ubicado en el centro del enlace.
     *
     * @return <code>true</code> para determinar de que el texto es visible.
     *         <code>false</code> para determinar que el texto está oculto.
     */
    public boolean isTextVisible() {
        return text.isVisible();
    }

    /**
     * Retorna el rectangulo que contiene la figura del enlace.
     *
     * @return Rectangulo que contiene la figura del enlace.
     */
    public BaZRect getFrame() {
        return new BaZRect(startPosition, endPosition);
    }

    /**
     * Retorna <code>true</code> si la flecha en la punta inicial del enlace es visible,
     * de lo contrario retorna <code>false</code>.
     *
     * @return <code>true</code> si la flecha en el punto inicial es visible.
     */
    public boolean isArrowStartVisible() {
        return startArrowVisible;
    }

    /**
     * Modifica al visibilidad de la flecha en el punto inicial del enlace.
     *
     * @param visible
     *        <code>true</code> para hacer que la flecha en el punto inicial del
     *        enlace sea visible, o <code>false</code> para ocultarla.
     */
    public void setArrowStartVisible(boolean visible) {
        this.startArrowVisible = visible;
    }

    /**
     * Retorna <code>true</code> si la flecha en la punta final del enlace es visible,
     * de lo contrario retorna <code>false</code>.
     *
     * @return <code>true</code> si la flecha en el punto final es visible.
     */
    public boolean isArrowEndVisible() {
        return endArrowVisible;
    }

    /**
     * Modifica al visibilidad de la flecha en el punto final del enlace.
     *
     * @param visible
     *        <code>true</code> para hacer que la flecha en el punto final del
     *        enlace sea visible, o <code>false</code> para ocultarla.
     */
    public void setArrowEndVisible(boolean visible) {
        this.endArrowVisible = visible;
    }

    /**
     * Modifica la posición del mouse.
     *
     * Éste método será sobre-escrito en las clases que hereden de ésta clase.
     *
     * @param position
     *        Posición del mouse.
     *
     */
    public void setMousePosition(BaZVector2D position) {}

    // --- Método Abstractos a implementar en las clases que hereden de ésta.

    /**
     * Este método se implementar para determinar si un determinado punto con
     * coordenas x,y pertenecen a la línea del enlace.
     *
     * @param x
     *        Coordenada X del punto de referencia.
     * @param y
     *        Coordenada Y del punto de referencia.
     *
     * @return <code>true</code> para determinar que el punto si está contenido
     *         dentro de la linea del enlace, de lo contrario <code>false</code>.
     */
    public abstract boolean contains(double x, double y);

    /**
     * Éste método permite actualizar la posición y dirección del enlace.
     */
    public abstract void update();

    /**
     * Sobre éste método se dibujan las características del enlace, como puede
     * ser el texto que se ubica en el centro del enlace.
     *
     * @param g
     *        Objeto <code>Graphics2D</code> del lienzo principal.
     */
    public abstract void paintFeatures(Graphics2D g);

    /**
     * Sobre éste método se dibujan la línea del enlace como tal.
     *
     * @param g
     *        Objeto <code>Graphics2D</code> del lienzo principal.
     */
    public abstract void paintShape(Graphics2D g);
}
