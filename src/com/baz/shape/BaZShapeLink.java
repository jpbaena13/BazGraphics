/*
 * BaZShapeLink.java	0.1  6/05/2011
 *
 * Copyright 2011 Universidad de Antioquia. All rights reserved.
 */
package com.baz.shape;

import com.baz.shape.base.BaZAnchor2D;
import com.baz.shape.base.BaZRect;
import com.baz.shape.base.BaZVector2D;
import com.baz.shape.link.BaZAbstractLink;
import com.baz.shape.link.BaZLinkArc;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

/**
 * Ésta clase crea una figura de tipo enlace. Permite enlazar dos figuras con
 * diferentes características, por ejemplo, con punta en flecha tanto para
 * la punta inicial así como la final.
 * 
 * @author JpBaena
 * @version 0.1
 * @since 1.6
 */
public class BaZShapeLink extends BaZAbstractShape {

    /** Llaves para definir un tipo de enlace en arco. */
    public static final int SHAPE_ARC = 0;
    public static final int SHAPE_ELBOW = 1;
    public static final int SHAPE_BEZIER = 2;
    
    /** Figura de donde sale el enlace. */
    protected BaZAbstractShape source = null;
    
    /** Figura a donde apunta el enlace. */
    protected BaZAbstractShape target = null;
    
    /** Llave para la ancla de la figura fuente. */
    protected String sourceAnchorKey = null;

    /** Llave para la ancla de la figura destino. */
    protected String targetAnchorKey = null;

    /** Patrón. */
    protected String pattern = null;

    /** Objeto <code>BaZAbstractLink</code> para ésta figura de enlace. */
    protected BaZAbstractLink link = null;

    /** Tipo de arco a utilizar por defecto. */
    protected int shape = SHAPE_ARC;


    /**
     * Construye un enlace con las características por defecto.
     */
    public BaZShapeLink() {
        super();
    }

    /**
     * Construye una figura tipo enlace a partir de las características especificadas.
     *
     * @param source
     *        Figura de donde partirá el enlace.
     *
     * @param sourceAnchorKey
     *        Llave de la ancla de la figura fuente.
     *
     * @param target
     *        Figura a donde llegará o apuntará el enlace.
     *
     * @param targetAnchorKey
     *        Llave de la ancla de la figura destino.
     *
     * @param shape
     *        Tipo de enlace a utilizar.
     *
     * @param pattern
     *        Patrón.
     *
     * @param mouse
     *        Punto del mouse donde se llama el evento de crear enlace.
     *
     * @param flatness
     *        Planaridad del enlace.
     */
    public BaZShapeLink(BaZAbstractShape source, String sourceAnchorKey, BaZAbstractShape target, String targetAnchorKey, int shape, String pattern, Point mouse, double flatness) {
        this.source = source;
        this.target = target;
        this.sourceAnchorKey = sourceAnchorKey;
        this.targetAnchorKey = targetAnchorKey;
        this.shape = shape;
        this.pattern = pattern;
        this.initLink(flatness);
        this.setSourceTangentOffset(source.getDefaultAnchorOffset(sourceAnchorKey));
        this.setTargetTangentOffset(target.getDefaultAnchorOffset(targetAnchorKey));
        link.setDirection(BaZVector2D.createBazVector2D(mouse).operation(target.getPosition(), BaZVector2D.SUB));
    }

    /**
     * Construye una figura tipo enlace a partir de las características especificadas.
     *
     * @param source
     *        Figura de donde partirá el enlace.
     *
     * @param sourceAnchorKey
     *        Llave de la ancla de la figura fuente.
     *
     * @param target
     *        Figura a donde llegará o apuntará el enlace.
     *
     * @param targetAnchorKey
     *        Llave de la ancla de la figura destino.
     *
     * @param shape
     *        Tipo de enlace a utilizar.
     *
     * @param pattern
     *        Patrón.
     *
     * @param flatness
     *        Planaridad del enlace.
     */
    public BaZShapeLink(BaZAbstractShape source, String sourceAnchorKey, BaZAbstractShape target, String targetAnchorKey, int shape, String pattern, double flatness) {
        this.source = source;
        this.target = target;
        this.sourceAnchorKey = sourceAnchorKey;
        this.targetAnchorKey = targetAnchorKey;
        this.shape = shape;
        this.pattern = pattern;
        this.initLink(flatness);

        if (source == target)
            link.setDirection(BaZAnchor2D.DIRECTION_BOTTOM);
        else
            link.setDirection(source.getPosition().operation(target.getPosition(), BaZVector2D.SUB));

        this.setSourceTangentOffset(source.getDefaultAnchorOffset(sourceAnchorKey));
        this.setTargetTangentOffset(target.getDefaultAnchorOffset(targetAnchorKey));
    }

    /**
     * Construye una figura tipo enlace a partir de las características especificadas.
     * las demás características las configura con valores por defecto.
     *
     * @param source
     *        Figura de donde partirá el enlace.
     *
     * @param target
     *        Figura a donde llegará o apuntará el enlace.
     *
     */
    public static BaZShapeLink createDefaultLink(BaZAbstractShape source, BaZAbstractShape target) {
        BaZShapeLink shapeLink = new BaZShapeLink();
        shapeLink.source = source;
        shapeLink.target = target;
        shapeLink.sourceAnchorKey = BaZAbstractShape.ANCHOR_CENTER;
        shapeLink.targetAnchorKey = BaZAbstractShape.ANCHOR_CENTER;
        shapeLink.shape = BaZShapeLink.SHAPE_ARC;
        shapeLink.pattern = "";
        shapeLink.initLink(0);

        if (source == target)
            shapeLink.getLink().setDirection(BaZAnchor2D.DIRECTION_BOTTOM);
        else if (target != null)
            shapeLink.getLink().setDirection(source.getPosition().operation(target.getPosition(), BaZVector2D.SUB));

        shapeLink.setSourceTangentOffset(source.getDefaultAnchorOffset(shapeLink.sourceAnchorKey));

        if (target != null)
            shapeLink.setTargetTangentOffset(target.getDefaultAnchorOffset(shapeLink.targetAnchorKey));

        return shapeLink;
    }

    /**
     * Crea una instancia del enlace de acuerdo a la forma especificada en ésta clase.
     *
     * @return Instancia de un objeto <code>BaZAbstractLink</code> con forma de
     *         acuerdo a la especificada para ésta clase.
     */
    protected BaZAbstractLink createLinkInstance() {
        switch(shape) {
            case SHAPE_ARC:
                return new BaZLinkArc();
        }
        
        return null;
    }

    /**
     * Inicializa el enlace.
     *
     * @param flatness
     *        Planaridad del enlace.
     */
    protected final void initLink(double flatness) {
        if (link == null) {
            link = this.createLinkInstance();
            link.setFlatness(flatness);
        }
    }

    /**
     * Modifica el offset tangencial del enlace en la punta inicial de éste.
     *
     * @param offset
     *        Offset tangencial para la punta inicial del enlace.
     */
    public final void setSourceTangentOffset(double offset) {
        link.setStartTangentOffset(offset);
    }

    /**
     * Modifica el offset tangencial del enlace en la punta final de éste.
     *
     * @param offset
     *        Offset tangencial para la punta final del enlace.
     */
    public final void setTargetTangentOffset(double offset) {
        link.setEndTangentOffset(offset);
    }

    /**
     * Modifica el offset de la figura fuente a partir de las coordenadas
     * X,Y especificadas.
     *
     * @param x
     *        Coordenada X para el offset de la figura fuente.
     * @param y
     *        Coordenada Y para el offset de la figura fuente.
     */
    public void setSourceOffset(double x, double y) {
        this.setSourceOffset(new BaZVector2D(x, y));
    }

    /**
     * Modifica el offset de la figura fuente a partir del vector especificadas.
     *
     * @param offset
     *        Vector offset para la figura fuente.
     */
    public void setSourceOffset(BaZVector2D offset) {
        link.setStartOffset(offset);
    }

    /**
     * Modifica el offset de la figura destino a partir de las coordenadas
     * X,Y especificadas.
     *
     * @param x
     *        Coordenada X para el offset de la figura destino.
     * @param y
     *        Coordenada Y para el offset de la figura destno.
     */
    public void setTargetOffset(double x, double y) {
        this.setTargetOffset(new BaZVector2D(x, y));
    }

    /**
     * Modifica el offset de la figura destino a partir del vector especificadas.
     *
     * @param offset
     *        Vector offset para la figura destino.
     */
    public void setTargetOffset(BaZVector2D offset) {
        link.setEndOffset(offset);
    }

    /**
     * Retorna el objeto <code>BaZAbstractLink</code> de la figura enlace.
     *
     * @return Objeto <code>BaZAbstractLink</code> del enlace.
     */
    public BaZAbstractLink getLink() {
        return link;
    }

    /**
     * Modifica el objeto <code>BaZAbstractLink</code> del enlace.
     *
     * @param link
     *        Objeto <code>BaZAbstractLink</code> para el enlace.
     */
    public void setLink(BaZAbstractLink link) {
        this.link = link;
    }

    /**
     * Retorna la variable patrón.
     *
     * @return Variable patrón.
     */
    public String getPattern() {
        return pattern;
    }

    /**
     * Modifica la variable patrón
     *
     * @param pattern
     *        Nuevo valor para la variable patrón.
     */
    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    /**
     * Retorna la forma del enlace.
     *
     * @return Forma del enlace.
     */
    public int getShape() {
        return shape;
    }

    /**
     * Modifica la forma del enlace
     *
     * @param shape
     *        Forma para el enlace.
     */
    public void setShape(int shape) {
        this.shape = shape;
    }

    /**
     * Retorna la figura fuente, es decir, la figura correspondiente a la punta
     * inicial del enlace.
     *
     * @return Figura fuente del enlace.
     */
    public BaZAbstractShape getSource() {
        return source;
    }

    /**
     * Modifica la figura fuente del enlace, es decir, la figura correspondiente
     * a la punta inicial del enlace.
     *
     * @param source
     *        Figura inicial para el enlace.
     */
    public void setSource(BaZAbstractShape source) {
        this.source = source;
    }

    /**
     * Retorna la llave del ancla de la figura fuente.
     *
     * @return Llave de ancla de la figura fuente.
     */
    public String getSourceAnchorKey() {
        return sourceAnchorKey;
    }

    /**
     * Modifica la llave de ancla de la figura fuente.
     *
     * @param sourceAnchorKey
     *        Llave de ancle para la figura fuente.
     */
    public void setSourceAnchorKey(String sourceAnchorKey) {
        this.sourceAnchorKey = sourceAnchorKey;
    }

    /**
     * Retorna la figura que se encuentra en la punta final del enlace.
     *
     * @return Figura destino del enlace.
     */
    public BaZAbstractShape getTarget() {
        return target;
    }

    /**
     * Modifica la figura que se encuentra en la punta final del enlace.
     *
     * @param target
     *        Figura destino para la punta final del enlace.
     */
    public void setTarget(BaZAbstractShape target) {
        this.target = target;
    }

    /**
     * Retorna la llave del ancla de la figura destino.
     *
     * @return Llave de ancla de la figura destino.
     */
    public String getTargetAnchorKey() {
        return targetAnchorKey;
    }

    /**
     * Modifica la llave de ancla de la figura destino.
     *
     * @param targetAnchorKey
     *        Llave de ancla para la figura destino.
     */
    public void setTargetAnchorKey(String targetAnchorKey) {
        this.targetAnchorKey = targetAnchorKey;
    }

    /**
     * Modifica la dirección y la acomoda el enlace de acuerdo a la posición
     * del mouse.
     *
     * @param mouse
     *        Posición del mouse.
     */
    public void setMousePosition(Point mouse) {
        link.setDirection(BaZVector2D.createBazVector2D(mouse).operation(target.getPosition(), BaZVector2D.SUB));
        link.setMousePosition(BaZVector2D.createBazVector2D(mouse));
    }

    /**
     * Actualiza en link con cada movimento de arrastre del mismo.
     */
    public void update() {
        this.initLink(0);

        source.updateAnchors();
        target.updateAnchors();

        link.setStartAnchor(source.getAnchor(sourceAnchorKey));
        link.setEndAnchor(target.getAnchor(targetAnchorKey));
        link.setLabel(pattern);
        link.setHasLoop(source == target);

        link.update();
    }

    @Override
    public BaZRect getFrame() {
        this.update();
        return link.getFrame();
    }

    @Override
    public boolean isInside(Point p) {
        if (link == null)
            return false;

        return link.contains(p.x, p.y);
    }

    @Override
    public void setText(String text) {
        this.pattern = text;
    }

    @Override
    public void setTextColor(Color color) {
        link.setLabelColor(textColor);
    }

    @Override
    public void setTextVisible(boolean visible) {
        if (link != null)
            link.setLabelVisible(visible);
    }

    @Override
    public boolean isTextVisible(){
        if (link == null)
            return false;
        else
            return link.isTextVisible();
    }

    @Override
    public void paintFeatures(Graphics2D g) {
        this.update();

        g.setStroke(new BasicStroke(borderSize));

        if (borderColor != null)
            link.setColor(borderColor);
        else
            link.setColor(Color.BLACK);

        link.paintFeatures(g);
    }

    @Override
    public void paintShape(Graphics2D g) {
        link.paintShape(g);
    }
}