/*
 * BaZVector2D.java	0.1  20/04/2011
 *
 * Copyright 2011 Universidad de Antioquia. All rights reserved.
 */
package com.baz.shape.base;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.io.Serializable;

/**
 * Construye un Vector de 2 Dimensiones y ofrece un conjunto de funcionalidad
 * a partir de él. Esta clase es utilizada comúnmente para especificar posiciones
 * o direcciones.
 *
 * @author JpBaena
 * @version 0.1
 * @since 1.6
 */
public class BaZVector2D implements Cloneable, Serializable {

    /** Especifica que la operación a realizar es una suma*/
    public static final int ADD   = 0;

    /** Especifica que la operación a realizar es una resta*/
    public static final int SUB   = 1;

    /** Especifica que la operación a realizar es un producto punto*/
    public static final int DOT   = 0;

    /** Especifica que la operación a realizar es un producto cruz*/
    public static final int CROSS = 1;

    /** Especifica que la operación a realizar es un producto cruz con signo*/
    public static final int CROSS_SIGN = 2;

    /** Coordenada X del vector*/
    private double x;

    /** Coordenada Y del vector*/
    private double y;

    /**
     * Construye un vector de 2 dimensiones inicializado en coordenadas [0,0]
     */
    public BaZVector2D() {
        this.x = 0;
        this.y = 0;
    }

    /**
     * Construye un vector de 2 dimensiones inicializado en las coordenadas
     * [X,Y] especificadas.
     *
     * @param x
     *        Coordenada X del vector.
     *
     * @param y
     *        Coordenada Y del vector.
     */
    public BaZVector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Construye un vector de 2 dimensiones inicializado en las coordenadas
     * en el punto especificado..
     *
     * @param p
     *        Punto para asignar las coordenadas al vector.
     */
    public BaZVector2D(Point p) {
        this.x = p.x;
        this.y = p.y;
    }

    /**
     * Crea un vector de 2 dimensiones de manera estática.
     *
     * @param p
     *        Punto que hace referencia a las coordenadas X,Y del vector.
     *
     * @return Vector2D posicionado en las coordenadas [X,Y].
     */
    public static BaZVector2D createBazVector2D(Point p) {
        return new BaZVector2D(p.getX(), p.getY());
    }

    /**
     * Crea un vector de 2 dimensiones de manera estática.
     *
     * @param p
     *        Punto2D que hace referencia a las coordenadas X,Y del vector.
     *
     * @return Vector2D posicionado en las coordenadas [X,Y].
     */
    public static BaZVector2D createBazVector2D(Point2D p) {
        return new BaZVector2D(p.getX(), p.getY());
    }
    
    //*** GETTERS AND SETTERS METHODS ***

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    //*** END GETTERS AND SETTERS METHODS ***

    /**
     * Retorna la longitud del vector.
     *
     * @return Longitud del vector
     */
    public double lenght() {
        return Math.sqrt(x*x+y*y);
    }

    /**
     * Modifica la longitud del vector reubicando las coordenadas a una nueva
     * posición pero manteniendo el mismo ángulo;
     *
     * @param lenght
     *        Nueva longitud del vector
     *
     * @return Esta misma instancia pero reubicado con la longitud especificada.
     */
    public BaZVector2D setLength(double lenght) {
        double oldLenght = this.lenght();

        if (oldLenght == 0) {
            x = 0;
            y = 0;
        } else {
            x = x/oldLenght*lenght;
            y = y/oldLenght*lenght;
        }

        return this;
    }

    /**
     * Estira el vector en la proporción especificada.
     *
     * @param f
     *        Porporción a estirar el vector.
     *
     * @return Esta misma instancia pero reubicado con la longitud especificada.
     */
    public BaZVector2D stretch(double f) {
        x *= f;
        y *= f;
        return this;
    }

    /**
     * Cambia el tamaño del vector en la proporción especificada.
     *
     * @param f
     *        Porporción a estirar el vector.
     *
     * @return Esta misma instancia pero reubicado con la longitud especificada.
     */
    public BaZVector2D shift(double dx, double dy) {
        x += dx;
        y += dy;
        return this;
    }

    /**
     * Crea un nuevo vector a partir del vector fuente con las longitud especificada.
     *
     * @param lenght
     *        Longitud del nuevo vector.
     *
     * @return Nuevo instancia de un vector.
     */
    public BaZVector2D newVectorLenght(double lenght) {
        BaZVector2D v = this.copy();
        v.setLength(lenght);
        return v;
    }

    /**
     * Rota el vector en la cantidad de grados especificados.
     *
     * @param degree
     *        Cantidad de grados a rotar el vector.
     *
     * @return Esta instancia rotada tantos grados fueron especificados.
     */
    public BaZVector2D rotate(double degree) {
        
        if (Math.abs(degree) == 90) {
            double aux = x;
            x = y;
            y = aux;

            if (degree < 0)
                y = -y;
            else
                x = -x;

        } else {
            double angle = Math.toRadians(degree);

            double rx = Math.cos(angle)*x - Math.sin(angle)*y;
            double ry = Math.sin(angle)*x + Math.cos(angle)*y;

            x = rx;
            y = ry;
        }

        return this;
    }

    /**
     * Retorna un nuevo vector normalizado a partir del vector fuente.
     *
     * @return Nuevo vector normalizado.
     */
    public BaZVector2D normalize() {
        double l = this.lenght();

        if (l == 0)
            return new BaZVector2D();
        else
            return new BaZVector2D(x/l, y/l);
    }

    /**
     * Clona la instancia actual
     *
     * @return Nueva instancia con las características del vector fuente.
     */
    public BaZVector2D copy() {
        return new BaZVector2D(x, y);
    }

    /**
     * Éste método permite realizar operaciones básicas sobre vectores.
     * Éstas operaciones son la suma o resta, tomando como primer término el
     * vector fuente y segundo término el vector especificado.
     * Si la operación especificada no corresponde a una operación soportada
     * el método retorna el vector fuente.
     *
     * @param v2
     *        Vector como segundo término para realizar la operación
     *
     * @param op
     *        Operación a realizar.
     *
     * @return Vector resultante de la operación
     */
    public BaZVector2D operation(BaZVector2D v2, int op) {

        switch (op) {
            case BaZVector2D.ADD:
                return new BaZVector2D(x + v2.x, y + v2.y);

            case BaZVector2D.SUB:
                return new BaZVector2D(x - v2.x, y - v2.y);
        }

        return this;
    }

    /**
     * Éste método permite realizar productos básicos sobre vectores.
     * Éstas productos son producto punto y producto cruz, tomando como primer
     * término el vector fuente y segundo término el vector especificado.
     * Si la operación especificada no corresponde a una producto soportado
     * el método retorna cero.
     *
     * @param v2
     *        Vector como segundo término para realizar la operación
     *
     * @param op
     *        Operación a realizar.
     *
     * @return Valor resultante de la operación
     */
    public double product(BaZVector2D v2, int op) {

        switch (op) {
            case BaZVector2D.DOT:
                return x*v2.x + y*v2.y;

            case BaZVector2D.CROSS:
                return x*v2.y - y*v2.x;

            case BaZVector2D.CROSS_SIGN:
                double cross = x*v2.y - y*v2.x;

                if (cross != 0)
                    return cross<0?-1:1;
        }

        return 0;
    }

    /**
     * Retorna el punto correspondiente al vector
     *
     * @return Punto correspondiente al vector.
     */
    public Point toPoint() {
        return new Point((int)x, (int)y);
    }

    @Override
    @SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
    public boolean equals(Object other) {
        BaZVector2D o = (BaZVector2D) other;
        return o.x == x && o.y == y;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + (int) (Double.doubleToLongBits(x) ^ (Double.doubleToLongBits(x) >>> 32));
        hash = 17 * hash + (int) (Double.doubleToLongBits(y) ^ (Double.doubleToLongBits(y) >>> 32));
        return hash;
    }

    @Override
    public String toString() {
        return "<BaZVector2D: " + x + ", " + y + ">";
    }
}
