/*
 * BaZRect.java	0.1  4/05/2011
 *
 * Copyright 2011 Universidad de Antioquia. All rights reserved.
 */
package com.baz.shape.base;

import java.awt.Point;
import java.awt.Rectangle;

/**
 * Ésta clase permite especificar un rectangulo y realizar operaciones sobre
 * éste mismo, tales como, unión o intercepción con otros figuras.
 *
 * @author JpBaena
 * @version 0.1
 * @since 1.6
 */
public class BaZRect {

    /** Rectangulo de clase. */
    private Rectangle rect;

    /**
     * Construye un objeto <code>BaZRect</code> a partir del rectangulo especificado.
     *
     * @param rect
     *        Rectangulo con el que será construido el objeto <code>BaZRect</code>
     */
    public BaZRect(Rectangle rect) {
        this.rect = rect;
    }

    /**
     * Constuye un rectangulo a partir de las coordenadas X, Y especificadas con
     * dimensión de <code>dx</code> a lo ancho y <code>dy</code> a lo alto
     *
     * @param x
     *        Coordenada x del punto inicial del rectangulo.
     * @param y
     *        Coordenada y de punto inicial del rectangulo.
     * @param dx
     *        Ancho del rectangulo.
     * @param dy
     *        Largo del ractengulo.
     */
    public BaZRect(double x, double y, double dx, double dy) {
        if (dx < 0) {
            x += dx;
            dx = -dx;
        }

        if (dy < 0) {
            y +=dy;
            dy = -dy;
        }

        this.rect = new Rectangle((int)x, (int)y, (int)dx, (int)dy);
    }

    /**
     * Construye un objeto <code>BaZRect</code> partir de los puntos especificados.
     *
     * @param v1
     *        Punto izquierdo-superior o derecho-inferior del rectangulo. Deberá
     *        ser el punto excluyendo del punto v2.
     * @param v2
     *        Punto izquierdo-superior o derecho-inferior del rectangulo. Deberá
     *        ser el punto excluyendo del punto v1.
     */
    public BaZRect(BaZVector2D v1, BaZVector2D v2) {
        this.create(v1, v2, 0, 0);
    }

    /**
     * Construye un objeto <code>BaZRect</code> partir de los puntos especificados.
     *
     * @param v1
     *        Punto izquierdo-superior o derecho-inferior del rectangulo. Deberá
     *        ser el punto excluyendo del punto v2.
     * @param v2
     *        Punto izquierdo-superior o derecho-inferior del rectangulo. Deberá
     *        ser el punto excluyendo del punto v1.
     * @param min_dx
     *        Minimo ancho del rectangulo.
     * @param min_dy
     *        Mínimo alto del rectangulo.
     */
    public BaZRect(BaZVector2D v1, BaZVector2D v2, double min_dx, double min_dy) {
        this.create(v1, v2, 0, 0);
    }

    /**
     * Permite crear un Rectangulo a partir de los vectores especificados.
     * 
     * @param v1
     *        Posición del mouse donde ocurre el clic de arrastre para crear el
     *        cuadro de selección.
     * @param v2
     *        Posición del mouse cuando es arrastrado a lo largo del lienzo.
     * @param min_dx
     *        Minimo tamaño del ancho del rectangulo
     * @param min_dy
     *        Minitmo tamaño del alto del rectangulo.
     */
    private void create(BaZVector2D v1, BaZVector2D v2, double min_dx, double min_dy) {
        double x = Math.min(v1.getX(), v2.getX());
        double y = Math.min(v1.getY(), v2.getY());
        double dx = Math.max(min_dx, Math.abs(v1.getX() - v2.getX()));
        double dy = Math.max(min_dy, Math.abs(v1.getY() - v2.getY()));

        this.rect = new Rectangle((int)x, (int)y, (int)dx, (int)dy);
    }

    /**
     * Retorna el rectangulo resultante de la unión entre el rectangulo fuente
     * y el rectangulo especificado.
     *
     * @param rect
     *        Rectangulo de referencia para realizar al unión.
     *
     * @return Rectangulo resultante de la unión.
     */
    public BaZRect union(BaZRect rect) {
        return new BaZRect((Rectangle) this.getRectangle().createUnion(rect.getRectangle()));
    }

    /**
     * Retorna el rectangulo de la clase.
     *
     * @return  Rectangulo de clase.
     */
    public Rectangle getRectangle() {
        return rect;
    }

    /**
     * Determina si los objetos <code>BaZRect</code> especificados tiene una
     * intersección diferente de vacia o no.
     *
     * @param r1
     *        Referencia del rectangulo 1 a comparar.
     * @param r2
     *        Referencia del rectangulo 2 a comporar.
     *
     * @return <code>true</code> si la intersección es diferente de vacia, de lo
     *         contrario retorna <code>false</code>.
     */
    public static boolean getIntersect(BaZRect r1, BaZRect r2) {
        return r1.getRectangle().intersects(r2.getRectangle());
    }

    /**
     * Determina si el objeto <code>BaZRect</code> especificados tiene una
     * intersección diferente de vacia o no con el objeto fuente.
     *
     * @param r1
     *        Referencia del rectangulo 1 a comparar.
     * @return
     */
    public boolean intersect(BaZRect r1) {
        return this.getRectangle().intersects(r1.getRectangle());
    }

    /**
     * Determina si el rectangulo fuente contiene a el punto correspondiente
     * a las coordenadas especificadas.
     *
     * @param x
     *        Coordenada X del punto de referencia.
     * @param y
     * *        Coordenada Y del punto de referencia.
     * @return <code>true</code> si el punto está contenido en el rectangulo,
     * de lo contrario <code>false</code>;
     */
    public boolean contains(double x, double y) {
        return rect.contains(x, y);
    }

    /**
     * Determina si el rectangulo fuente contiene al punto especificado.
     *
     * @param point
     *        Punto de referencia a determinar si esta contenido en el rectangulo.
     *
     * @return <code>true</code> si el punto está contenido en el rectangulo,
     * de lo contrario <code>false</code>;
     */
    public boolean contains(Point p) {
        return rect.contains(p);
    }

    /**
     * Inset del rectangulo.
     *
     * @param f
     */
    public void inset(double f) {
        rect.x += f;
        rect.y += f;
        rect.width -= 2*f;
        rect.height -= 2*f;
    }

    @Override
    public String toString() {
        if (rect == null)
            return super.toString();

        return rect.toString();
    }
}
