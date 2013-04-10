/*
 * BaZLinkArc.java	0.1  6/05/2011
 *
 * Copyright 2011 Universidad de Antioquia. All rights reserved.
 */
package com.baz.shape.link;

import com.baz.shape.base.BaZVector2D;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.CubicCurve2D;
import java.awt.geom.FlatteningPathIterator;
import java.awt.geom.PathIterator;
import java.awt.geom.QuadCurve2D;

/**
 * Ésta clase genera un link en forma de arco, que puede ser modificado por
 * medio del movimiento del mouse en la interacción con el usuario.
 *
 * @see BaZAbstractLink
 *
 * @author JpBaena
 * @version 0.1
 * @since 1.6
 */
public class BaZLinkArc extends BaZAbstractLink {
    
    /** Determinar la curva 2D cuadrada a implementar para el arco. */
    protected transient QuadCurve2D.Double quad;

    /** Determinar la curva 2D cúbica a implementar para el arco. */
    protected transient CubicCurve2D.Double cubic;

    /** Representación de una figura dibujable con forma geométrica. */
    protected transient Shape shape;

    /** Posición del label*/
    BaZVector2D vLabel;

    /** Posición media del label*/
    BaZVector2D pMiddle;

    /**
     * Constructor para un link de tipo arco.
     */
    public BaZLinkArc() {
        super();
    }

    /**
     * Determinar si el punto con coordenadas X,Y especificadas ésta contenido
     *  en el PathIterator especificado.
     *
     * @param iterator
     *        Camino de puntos que determinar la línea que forma el link.
     * @param x
     *        Coordenada X de punto de referencia.
     * @param y
     *        Coordenada Y de punto de referencia.
     *
     * @return <code>true</code> si el punto especificado esta contenido en el
     *         path especificado, si no lo está retonar <code>false</code>.
     */
    public boolean contains(PathIterator iterator, double x, double y) {
        double coord[] = new double[6];
        double oldx = -1;
        double oldy = -1;

        final double flatness_ = 0.8;
        final double inset = 4;

        FlatteningPathIterator i = new FlatteningPathIterator(iterator, flatness_);

        while (!i.isDone()) {

            switch (i.currentSegment(coord)) {

                case FlatteningPathIterator.SEG_MOVETO:
                    oldx = coord[0];
                    oldy = coord[1];
                    break;

                case FlatteningPathIterator.SEG_LINETO:
                    double nx = coord[0];
                    double ny = coord[1];

                    double rx1 = Math.min(oldx, nx);
                    double ry1 = Math.min(oldy, ny);
                    double rx2 = Math.max(oldx, nx);
                    double ry2 = Math.max(oldy, ny);

                    if (Math.abs(rx1 - rx2) < inset || Math.abs(ry1 - ry2) < inset) {
                        rx1 -= inset;
                        ry1 -= inset;
                        rx2 += inset;
                        ry2 += inset;
                    }

                    if (x >= rx1 && x <= rx2 && y >= ry1 && y <= ry2)
                        return true;

                    oldx = nx;
                    oldy = ny;
                    break;
            }
            i.next();
        }
        return false;
    }

    /**
     * Modifica la dirección del punto final del link de acuerdo a la posición
     * del mouse.
     *
     * Éste método será sobre-escrito en las clases que hereden de ésta clase.
     *
     * @param mouse
     *        Posición del mouse que determinará la dirección del punto final del
     *        link.
     */
    public void setMouse(Point mouse) {
        this.setMouse(BaZVector2D.createBazVector2D(mouse));
    }

    /**
     * Modifica la dirección del punto final del link de acuerdo a la posición
     * del mouse.
     *
     * Éste método será sobre-escrito en las clases que hereden de ésta clase.
     *
     * @param mouse
     *        Posición del mouse que determinará la dirección del punto final del
     *        link.
     */
    public void setMouse(BaZVector2D mouse) {
        this.setDirection(mouse.operation(endPosition, BaZVector2D.SUB));
    }

    @Override
    public void setMousePosition(BaZVector2D position) {
        BaZVector2D corde = this.getEndWithOffset().operation(this.getStartWithOffset(), BaZVector2D.SUB);
        double dot = position.operation(this.getStartWithOffset(), BaZVector2D.SUB).product(corde.normalize(), BaZVector2D.DOT);
        corde.setLength(dot);

        BaZVector2D z = this.getStartWithOffset().operation(corde, BaZVector2D.ADD);
        BaZVector2D f = position.operation(z, BaZVector2D.SUB);

        double cross = corde.product(f, BaZVector2D.CROSS);

        if (cross == 0)
            this.setFlatness(0);
        else
            this.setFlatness(-2*f.lenght()*cross/Math.abs(cross));
    }

    @Override
    public boolean contains(double x, double y) {
        if (hasLoop && cubic != null)
            return this.contains(cubic.getPathIterator(null), x, y);

        if (!hasLoop && quad != null)
            return this.contains(quad.getPathIterator(null), x, y);

        return false;
    }

    @Override
    public void update() {
        
        //Computa el punto de control de la curva.
        if (hasLoop) {
            if (cubic == null)
                cubic = new CubicCurve2D.Double();

            BaZVector2D corde = this.getDirection().copy();
            corde.stretch(1.7);

            if (corde.lenght() < 100)
                corde.setLength(100);

            corde.rotate(-40);
            cubic.ctrlx1 = this.getStartWithOffset().getX() + corde.getX();
            cubic.ctrly1 = this.getStartWithOffset().getY() + corde.getY();

            corde.rotate(+80);
            cubic.ctrlx2 = this.getStartWithOffset().getX() + corde.getX();
            cubic.ctrly2 = this.getStartWithOffset().getY() + corde.getY();

            //Mover los puntos iniciales y finales de acuerdo al offset

            BaZVector2D v1 = new BaZVector2D(cubic.ctrlx1, cubic.ctrly1).operation(this.getStartWithOffset(), BaZVector2D.SUB).normalize();
            BaZVector2D v2 = new BaZVector2D(cubic.ctrlx2, cubic.ctrly2).operation(this.getStartWithOffset(), BaZVector2D.SUB).normalize();

            v1.stretch(startTangentOffset);
            v2.stretch(endTangentOffset);

            cubic.x1 = this.getStartWithOffset().getX() + v1.getX();
            cubic.y1 = this.getStartWithOffset().getY() + v1.getY();
            cubic.x2 = this.getEndWithOffset().getX() + v2.getX();
            cubic.y2 = this.getEndWithOffset().getY() + v2.getY();

            //Posición del texto del link.

            BaZVector2D vLabel_ = this.getDirection().copy();
            vLabel_.setLength(vLabel_.lenght() + 15);

            if (vLabel_.lenght() < 75)
                vLabel_.setLength(75);

            BaZVector2D pLabel = this.getStartWithOffset().operation(vLabel_, BaZVector2D.ADD);
            text.setPosition(pLabel);

            //Crear la fecha al inicio del link
            startArrow.setAnchor(cubic.x1, cubic.y1);
            startArrow.setDirection(new BaZVector2D(cubic.ctrlx1 - cubic.x1, cubic.ctrly1 - cubic.y1));

            //Crear la fecha al final del link
            endArrow.setAnchor(cubic.x2, cubic.y2);
            endArrow.setDirection(new BaZVector2D(cubic.ctrlx2 - cubic.x2, cubic.ctrly2 - cubic.y2));

            shape = cubic;

        } else {
            BaZVector2D middle = this.getEndWithOffset().operation(this.getStartWithOffset(), BaZVector2D.SUB);
            middle.stretch(0.5);

            BaZVector2D height = middle.normalize();
            height.rotate(-90);

            if (flatness == 0)
                height.setLength(0.01);
            else
                height.setLength(flatness);

            BaZVector2D ctrl = middle.operation(height, BaZVector2D.ADD);

            if (quad == null)
                quad = new QuadCurve2D.Double();

            quad.x1 = this.getStartWithOffset().getX();
            quad.y1 = this.getStartWithOffset().getY();
            quad.x2 = this.getEndWithOffset().getX();
            quad.y2 = this.getEndWithOffset().getY();
            quad.ctrlx = this.getStartWithOffset().getX() + ctrl.getX();
            quad.ctrly = this.getStartWithOffset().getY() + ctrl.getY();

            BaZVector2D controlPoint = new BaZVector2D(quad.ctrlx, quad.ctrly);

            // Mover el punton inicial/final de acuerdo al offset

            BaZVector2D v1 = controlPoint.operation(this.getStartWithOffset(), BaZVector2D.SUB).normalize();
            BaZVector2D v2 = controlPoint.operation(this.getEndWithOffset(), BaZVector2D.SUB).normalize();

            v1.stretch(startTangentOffset);
            v2.stretch(endTangentOffset);

            quad.x1 = this.getStartWithOffset().getX() + v1.getX();
            quad.y1 = this.getStartWithOffset().getY() + v1.getY();
            quad.x2 = this.getEndWithOffset().getX() + v2.getX();
            quad.y2 = this.getEndWithOffset().getY() + v2.getY();

            //Posicionamos el texto

            pMiddle = new BaZVector2D(quad.x1 + (quad.x2 - quad.x1)*0.5, quad.y1 + (quad.y2 - quad.y1)*0.5);
            vLabel = new BaZVector2D(quad.x2 - quad.x1, quad.y2 - quad.y1).rotate(90*(flatness<0?1:-1));

            vLabel.setLength(Math.abs(flatness)*0.5 + 20);
            text.setPosition(pMiddle.operation(vLabel, BaZVector2D.ADD));

            //Crear la fecha al inicio del link
            startArrow.setAnchor(quad.x1, quad.y1);
            startArrow.setDirection(controlPoint.operation(this.getStartWithOffset(), BaZVector2D.SUB));
            
            // Crear la flecha en la punta final del link.
            endArrow.setAnchor(quad.x2, quad.y2);
            endArrow.setDirection(controlPoint.operation(this.getEndWithOffset(), BaZVector2D.SUB));

            shape = quad;
        }
    }

    @Override
    public void paintFeatures(Graphics2D g) {
        if (shape == null || endArrow == null || text == null)
            return;

        g.setColor(color);

        this.paintShape(g);

        text.paint(g);
    }

    @Override
    public void paintShape(Graphics2D g) {
        if (shape == null || endArrow == null || text == null)
            return;

        g.draw(shape);

        if (endArrowVisible)
            endArrow.paint(g);

        if (startArrowVisible)
            startArrow.paint(g);
    }
}
