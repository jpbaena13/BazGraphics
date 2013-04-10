/*
 * BaZEventDelegate.java	0.1  29/04/2011
 *
 * Copyright 2011 Universidad de Antioquia. All rights reserved.
 */
package com.baz.canvas;

import com.baz.shape.BaZAbstractShape;
import java.awt.Point;

/**
 * Intefaz que será implementada por la clase <code>BaZCanvas</code> y gestiona
 * los método necesarios que deben de responder a ciertos eventos de mouse y
 * teclado.
 *
 * @author JpBaena
 * @version 0.1
 * @since 1.6
 */
public interface BaZCanvasDelegate {

    /**
     * Repinta el componente despues de que todo los ciertos eventos sean disparados.
     */
    public void repaint();

    /**
     * Éste método sirve para determinar que objeto <code>BaZAbstractShape</code>
     * contiene el punto especificado dentro de su frontera.
     *
     * @param p
     *        Punto de referencia para determinar el objeto
     *        <code>BaZAbstracShape</code> que lo contiene.
     *
     * @return Objeto <code>BazAbstractShape</code> que contiene el punto especificado.
     */
    public BaZAbstractShape searchShapeAtPoint(Point p);

    /**
     * Éste método es implementado para crear una nueva figura sobre el panel
     * principal de acuerdo al evento de doble-clic.
     *
     * @param p
     *        Punto donde ocurre el evento y donde se debe de insertar la nueva
     *        figura.
     *
     * @param doubleClick
     *        <code>true</code> para determinar que la acción de mouse fue un
     *        doble-clic y se debe de insertar una nueva figura; <code>false</code>
     *        para no realizar nada.
     */
    public void createShape(Point p, boolean doubleClick);

    /**
     * Éste método es implementado apra remover la figura especificado desde el
     * lienzo principal.
     *
     * @param shape
     *        Figura a ser removida desde el lienzo principal.
     */
    public void removeShape(BaZAbstractShape shape);

    /**
     * Se implementa para las figuras que permiten crear enlaces con otras figuras
     * sobre el lienzo principal.
     *
     * @param source
     *        Figura fuente, de donde parte el enlace.
     * @param sourceAnchorKey
     *        Llave de ancla de la figura fuente.
     * @param target
     *        Figura destino, a donde llega el enlace.
     * @param targetAnchorKey
     *        Llace de ancla para la figura destino.
     * @param shape
     *        Tipo de link a realizar.
     * @param p
     *        Posición del mouse que debe seguir la punta destino antes de enlazarla.
     */
    public void createLink(BaZAbstractShape source, String sourceAnchorKey,
            BaZAbstractShape target, String targetAnchorKey, int shape, Point p);

    /**
     * Se implementa para determinar si el canvas permite o no crear enlaces
     * entre figuras.
     *
     * @return <code>true</code> para determinar que el canvas permite enlaces entre
     *         otras figuras, <code>false</code> para determinar lo contrario.
     */
    public boolean canCreateLink();

    /**
     * Se implementa para especificar el comportamiento a llevar a cabo cuando
     * se realiza un evento de mover figuras seleccionadas dentro del lienzo.
     *
     * @param x
     *        Diferencial de x a mover la figura en la coordenada X.
     * @param y
     *        Diferencial de y a mover la figura en la coordenada Y.
     */
    public void moveSelectedShapes(double x, double y);

    /**
     * Se implementa para añadir la figura especificada al timer de figuras enfocadas.
     *
     * @param shape
     *        Figura a ser añadida.
     */
    public void addFocusedShape(BaZAbstractShape shape);

    /**
     * Implementado para remover la figura especificada de timer de figuras enfocadas.
     *
     * @param shape
     *        Figura a ser removida.
     */
    public void removeFocusedShape(BaZAbstractShape shape);

    /**
     * Implementado para añadir la figura especificada al timer de figuras seleccionadas.
     *
     * @param shape
     *        Figura a ser añadida.
     */
    public void addSelectedShape(BaZAbstractShape shape);

    /**
     * Implementado para remover la figura especificada de timer de figuras seleccionadas.
     *
     * @param shape
     *        Figura a ser removida.
     */
    public void removeSelectedShape(BaZAbstractShape shape);

     /**
     * Es implementado para determianar si la figura pasado por parámetro
     * está seleccionado o no.
     *
     * @param shape
     *        Figura a determinar si está seleccionada.
     *
     * @return <code>true</code> si el elemento está seleccionado, de lo contrario
     *         retorna <code>false</code>.
     */
    public boolean isSelectedShape(BaZAbstractShape shape);

    /**
     * Especifica el ancho y largo a patir de las coordenadas x,y especificadas
     * del rectangulo seleccionar por parte del usuario.
     *
     * @param x
     *        Posición X del punyo inicial del rectangulo.
     * @param y
     *        Posición Y del punyo inicial del rectangulo.
     * @param dx
     *        Ancho del rectangulo.
     * @param dx
     *        Largo del rectangulo.
     */
    public void selectingShapesInRect(int x, int y, int dx, int dy);

    /**
     * Permite determinar si evento ejecutado selecciona o no todos los
     * elementos del lienzo principal.
     *
     * @param select <code>true</code> para seleccionar todas las figuras que
     *               hayan en el lienzo principal. <code>false</code> para
     *               determinar que no.
     */
    public void selectingAllShapes(boolean select);

    /**
     * Retorna la figura raíz que contiene todas las figuras dibujables del
     * lienzo.
     *
     * @return Objeto <code>BaZAbstrctShape</code> que es la figura raíz de todas
     *         figuras dibujables añadidas.
     */
    public BaZAbstractShape getRootShape();

    /**
     * Edita una figura, especialmente utilizado para modificar la clase
     * de dominio asociada a ésta por lo tanto la parte gráfica asociada.
     *
     * @param shape
     *        Figura a ser editada.
     */
    public void editShape(BaZAbstractShape shape);
}
