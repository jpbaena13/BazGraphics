/*
 * BaZAbstractShape.java	0.1  29/04/2011
 *
 * Copyright 2011 Universidad de Antioquia. All rights reserved.
 */
package com.baz.shape;

import com.baz.shape.base.BaZAnchor2D;
import com.baz.shape.base.BaZRect;
import com.baz.shape.base.BaZVector2D;
import com.baz.canvas.BaZCanvas;
import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Esta clase es la abstracción de una figura dibujable sobre un lienzo.
 * Define las características mínimas que deben de tener las diferentes formas
 * que podrán ser dibujables.
 * Toda figura dibujable sobre un lienzo (Canvas) deberá extender de ésta clase.
 * A su vez, una figura podrá contener otras figuras dentro de si misma, por lo
 * que deberá de gestionarlas.
 *
 * @author JpBaena
 * @version 0.1
 * @since 1.6
 */
public class BaZAbstractShape {

    /** Llave para tipo de ancla a utilizar para enlace. */
    public static final String ANCHOR_CENTER = "CENTER";

    /** Llave para tipo de ancla a utilizar para enlace. */
    public static final String ANCHOR_TOP = "TOP";

    /** Llave para tipo de ancla a utilizar para enlace. */
    public static final String ANCHOR_BOTTOM = "BOTTOM";

    /** Llave para tipo de ancla a utilizar para enlace. */
    public static final String ANCHOR_LEFT = "LEFT";

    /** Llave para tipo de ancla a utilizar para enlace. */
    public static final String ANCHOR_RIGHT = "RIGHT";

    /** Lienzo padre donde será pintada la figura. */
    public transient BaZCanvas canvas = null;

    /** Lista de figuras que contiene esta figura. */
    protected List<BaZAbstractShape> shapes = new ArrayList<BaZAbstractShape>();

    /** Posición de la figura dentro del lienzo. */
    protected BaZVector2D position = new BaZVector2D();

    /** Posición anterior a la posición actual cuando la figura esta siendo
     * desplazada por todo el lienzo padre.*/
    protected transient BaZVector2D oldPosition = null;

    /** Conjunto de anclas asociadas a la figura. */
    protected transient Map anchors = new HashMap();

    /** Texto que contendrá la figura en el centro. */
    protected String text = null;

    /** Color del texto que contendrá la figura en el centro. */
    protected Color textColor = Color.BLACK;

    /** Color del borde de la figura. */
    protected Color borderColor = Color.BLACK;

    /** Color del fondo de la figura. */
    protected Color background = Color.WHITE;

    /** Grueso de línea del borde la figura. */
    protected int borderSize = 1;

    /** Permite determinar si se muestra o no el texto del centro. */
    protected boolean textVisible = true;

    /** Bandera que determina si la figura esta seleccionada. */
    protected transient boolean selected = false;

    /** Bandera que deterina si la figura esta enfocada. */
    protected transient boolean focused = false;

    /** Bandera que determina si la figura es o no arrastrable. */
    protected boolean draggable = false;

    /* --- Trazos Básicos para el borde la figura --- */
    protected transient BasicStroke borderStroke = new BasicStroke(borderSize);
    protected transient final BasicStroke STROKE_NORMAL = new BasicStroke(1);
    protected transient final BasicStroke STROKE_BOLD = new BasicStroke(3);

    /** Objeto que servirá como cerrojo para sincronizar el acceso a algunos métodos de ésta clase*/
    protected final Object lock = new Object();

    /**
     * Configura el canvas de clase referenciando al canvas especificado.
     * Este canvas será el padre del objeto, así como el padre de todos los
     * demás objetos que estén contenidos en éste, esto es, el lienzo donde dichos
     * objetos serán pintado.
     *
     * @param canvas
     *        Canvas padre para la figura y sus elementos.
     */
    public void setCanvas(BaZCanvas canvas) {
        this.canvas = canvas;
        synchronized(lock) {
            for (int i = 0; i < shapes.size(); i++) {
                BaZAbstractShape shape = (BaZAbstractShape) shapes.get(i);
                shape.setCanvas(canvas);
            }
        }
    }

    /**
     * Determina si el texto especificado es igual o no al texto ubicado en el
     * centro de la figura.
     *
     * @param text
     *        Texto a comparar con el texto del centro de la figura.
     *
     * @return <code>true</code> si el texto especificado es igual al texto del
     *         centro de la figura. <code>false</code> si no lo es.
     */
    public boolean isTextEquals(String text) {
        if (this.text == null)
            return (text == null);

        return this.text.equals(text);
    }

    /**
     * Añade la figura especificda a la lista de elementos embebidos que contiene éste objeto.
     *
     * @param shape
     *        Figura a se añadida a la lista.
     */
    public void addShape(BaZAbstractShape shape) {
        shape.setCanvas(canvas);
        synchronized(lock) {
            this.shapes.add(0, shape);
        }
    }

    /**
     * Añade la figura especificada en el index especificado dentro de la lista
     * de elementos embebidos que contiene éste objeto.
     *
     * @param shape
     *        Figura a ser añadida a la lista.
     *
     * @param index
     *       Posición donde va a ser añadia la figura.
     */
    public void addShape(int index, BaZAbstractShape shape) {
        shape.setCanvas(canvas);
        synchronized(lock) {
            if (!this.shapes.contains(shape))
                this.shapes.add(index, shape);
        }
    }

    /**
     * Añade la figura especificada a la lista de elementos embebidos que contiene
     * éste objeto en la parte final de la lista. Esto indica que estos elementos
     * serán los último en ser pintados y por lo tanto quedarán detrás de los
     * demás elementos.
     *
     *
     * @param shape
     *        Figura a se añadida a la lista.
     */
    public void addShapeAtLast(BaZAbstractShape shape) {
        shape.setCanvas(canvas);
        synchronized(lock) {
            if (!this.shapes.contains(shape))
                this.shapes.add(shape);
        }
    }

    /**
     * Remueve el objeto especificado desde la lista de objetos embebidos que
     * contiene éste objeto. Si el objeto existe y es removido retorna <code>true</code>
     * de lo contrario retorna <code>false</code>.
     *
     * @param shape
     *        Figura a ser removidad desde la lista.
     *
     * @return <code>true</code> si el objeto existe y es removido de lo
     *         contrario retorna <code>false</code>.
     */    
    public boolean removeShape(BaZAbstractShape shape) {
        synchronized(lock) {
            return this.shapes.remove(shape);
        }
    }

    /**
     * Retorna el primer elemento de la lista de objetos embebidos.
     *
     * @return Primer elemento de la lista de objetos embebidos.
     */
    public BaZAbstractShape getFirstShape() {
        if (shapes == null || shapes.isEmpty())
            return null;

        return shapes.get(0);
    }

    /**
     * Retorna el último elemento de la lista de objetos embebidos.
     *
     * @return Último elemento de la lista de objetos embebidos.
     */
    public BaZAbstractShape getLastShape() {
        if (shapes == null || shapes.isEmpty())
            return null;

        return shapes.get(shapes.size() - 1);
    }

    /**
     * Busca un elementos desde la lista de objetos embebidos tal que el texto ubicado
     * en la parte central del mismo sea igual al texto especificado.
     *
     * @param text
     *        Texto a compara con el texto central de los objetos embebidos.
     *
     * @return Un <code>BaZAbstractShape</code> que tiene el mismo texto central
     *         que el texto especificado. Si este objeto no exite retorna
     *         <code>null</code>.
     */
    public BaZAbstractShape searchShapeByText(String text) {
        if (this.isTextEquals(text))
            return this;

        if (shapes == null)
            return null;

        for (int i = 0; i < shapes.size(); i++) {
            BaZAbstractShape shape = shapes.get(i);

            if (shape.isTextEquals(text))
                return shape;
            else {
                shape = shape.searchShapeByText(text);
                if (shape != null)
                    return shape;
            }
        }

        return null;
    }

    /**
     * Permite mover el componente un diferencia en X y en Y de acuerdo a los
     * parámetros especificado.
     *
     * @param dx
     *        Diferencial en X a mover la figura.
     * 
     * @param dy
     *        Diferencial en Y a mover la figura.
     */
    public void move(double dx, double dy) {
        position.shift(dx, dy);
        
        synchronized(lock) {
            for (int i = 0; i < shapes.size(); i++) {
                BaZAbstractShape shape = shapes.get(i);
                shape.move(dx, dy);
            }
        }
    }

    /**
     * Mueve el elemento a otra posición relativa a la que tiene.
     *
     * @param position
     *        Posición a donde se moverá el objeto.
     */
    public void moveToPosition(BaZVector2D position) {
        double dx = position.getX() - this.getX();
        double dy = position.getY() - this.getY();
        this.move(dx, dy);
    }

    /**
     * Retorna el figura que contiene el punto especificado.
     *
     * @param p
     *        Punto de referencia para la búsqueda de figura que lo contiene.
     *
     * @return <code>BaZAbstractShape</code> que contiene el punto especificado.
     *         Si ningún objeto lo contiene entonces retorna <code>null</code>.
     */
    public BaZAbstractShape match(Point p) {
        synchronized(lock) {
            for (int i = 0; i < shapes.size(); i++) {
                BaZAbstractShape shape = shapes.get(i);
                BaZAbstractShape match = shape.match(p);
                if (match != null)
                    return match;
            }
        }

        if (this.isInside(p))
            return this;

        return null;
    }

    /**
     * Retorna el objeto <code>BaZRect</code> que delimita ésta figura.
     *
     * @return Objeto <code>BaZRect</code> que delimita la figura.
     */
    public BaZRect bounds() {
        BaZRect rect = this.getFrame();

        synchronized(lock) {
            for (int i = 0; i < shapes.size(); i++) {
                BaZAbstractShape shape = (BaZAbstractShape) shapes.get(i);

                if (shape == this)
                    continue;

                if (rect == null)
                    rect = shape.bounds();
                else
                    rect = rect.union(shape.bounds());
            }
        }
       return rect;
    }

    /**
     * Configura la variable <code>oldPosition</code> para comenzar el arrastre
     * de la(s) figura(s) seleccionada(s).
     */
    public void startingDrag() {
        oldPosition = null;
    }

    /**
     * Comportamiento del arrastre del objeto alrededor del lienzo principal.
     *
     * @param p
     *        Posición actual de la figura.
     *
     * @return
     *       Posición inmediatamente despues de la posición actual. Ésta se convierte
     *       en la posición actual un instante después.
     */
    public BaZVector2D dragShapePosition(BaZVector2D p) {
        BaZVector2D ep = p.copy();

        if (oldPosition != null) {
            ep.setX(ep.getX() + (p.getX() -  oldPosition.getX()));
            ep.setY(ep.getX() + (p.getY() -  oldPosition.getY()));
        }

        return ep;
    }

    /**
     * Lleva a cabo el movimiendo de la figura cuando es arrastrada a por todo
     * el lienzo padre.
     *
     * @param p
     *        Posición actual de la figura que será convertida en la posición
     *        anterior para dar paso a la nueva posición.
     */
    public void drag(BaZVector2D p) {
        double dx = 0;
        double dy = 0;

        if (oldPosition == null) {
            oldPosition = new BaZVector2D();
        } else {
            dx = p.getX() - oldPosition.getX();
            dy = p.getY() - oldPosition.getY();
        }

        oldPosition.setX(p.getX());
        oldPosition.setY(p.getY());

        move(dx, dy);
    }

    /**
     * Pinta de manera recursiva los objetos embebidos al objeto fuente.
     *
     * @param g
     *        <code>Graphics2D</code> del lienzo padre.
     */
    @SuppressWarnings("NestedSynchronizedStatement")
    public synchronized void paintRecursive(Graphics2D g) {
        synchronized(lock) {
            for (int i = shapes.size()-1 ; i >= 0; i--) {
                BaZAbstractShape shape = shapes.get(i);
                shape.paintRecursive(g);
            }
        }

        this.paintFeatures(g);

        if (isSelected())
            this.paintSelected(g);
        else if (this.isFocused())
            this.paintFocused(g);
    }

    /**
     * Pintando sobre el lienzo padre cuando la figura ha sido seleccionada.
     *
     * @param g
     *         <code>Graphics2D</code> del lienzo padre.
     */
    private void paintSelected(Graphics2D g) {
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, canvas.getSelectedAlphaValue()));
        g.setColor(Color.gray);
        g.setStroke(STROKE_BOLD);

        paintShape(g);

        g.setStroke(STROKE_NORMAL);
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f ));
    }

    /**
     * Pintando sobre el lienzo padre cuando la figura ha sido enfocada.
     *
     * @param g
     *         <code>Graphics2D</code> del lienzo padre.
     */
    private void paintFocused(Graphics2D g) {
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, canvas.getFocusedAlphaValue()));
        g.setColor(Color.blue);
        g.setStroke(STROKE_BOLD);

        paintShape(g);

        g.setStroke(STROKE_NORMAL);
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f ));
    }
    
    /**
     * Retorna el ancla asociada a la figura que tiene como llave la cadena
     * especificada.
     * 
     * @param key
     *        Llave del ancla a retornar.
     * 
     * @return Ancla que tiene por llave la cadena de caractéres especificada.
     */
    public BaZAnchor2D getAnchor(String key) {
        return (BaZAnchor2D) anchors.get(key);
    }

   /**
    * Retorna la llave del ancla que se encuenta mas cercana al punto especificado.
    *
    * @param p
    *        Punto de referencia.
    *
    * @return Llave del ancla mas cercana al punto de referencia.
    */
    public String getAnchorKeyClosestToPoint(Point p) {
        BaZAnchor2D anchor = this.getAnchorClosestToPoint(p);

        for (Iterator iterator = anchors.keySet().iterator(); iterator.hasNext();) {
            String key = (String) iterator.next();

            if (anchors.get(key) == anchor)
                return key;
        }

        return null;
    }

    /**
     * Retorna el ancla mas cerana al punto especificado.
     *
     * @param p
     *        Punto de referencia.
     *
     * @return Ancla mas cercana al punto de referencia.
     */
    public BaZAnchor2D getAnchorClosestToPoint(Point p) {
        double smallest_distance = Integer.MAX_VALUE;
        BaZAnchor2D closest_anchor = null;

        Iterator iterator = anchors.values().iterator();

        while(iterator.hasNext()) {
            BaZAnchor2D anchor = (BaZAnchor2D) iterator.next();
            double dx = anchor.getPosition().getX() - p.getX();
            double dy = anchor.getPosition().getY() - p.getY();

            double d = Math.sqrt(dx*dx + dy*dy);

            if (d < smallest_distance) {
                smallest_distance = d;
                closest_anchor = anchor;
            }
        }

        return closest_anchor;
    }

    /**
     * Modifica el ancla del objeto que contiene por llave la palabra <code>key</code>
     * y le asigna a la ancla la posición y dirección especificada.
     *
     * @param key
     *        Llave del ancla a modificar.
     * @param position
     *        Posición para el ancla.
     * @param direction
     *        Dirección para el ancla.
     */
    public void setAnchor(String key, BaZVector2D position, BaZVector2D direction) {
        BaZAnchor2D anchor = this.getAnchor(key);

        if (anchor == null) {
            anchor = new BaZAnchor2D();
            anchors.put(key, anchor);
        }

        anchor.setPosition(position);
        anchor.setDirection(direction);
    }

    /* --- METODOS GETTERS Y SETTERS PARA LAS VARIABLES GLOBALES --- */

    /**
     * Retorna el color de fondo de la figura.
     *
     * @return Color de fondo de la figura.
     */
    public Color getBackground() {
        return background;
    }

    /**
     * Modifica el color de fondo de la figura.
     *
     * @param background
     *        Color para el fondo de la figura.
     */
    public void setBackground(Color background) {
        this.background = background;
    }

    /**
     * Retorna el color de borde de la figura.
     *
     * @return Color de borde de la figura.
     */
    public Color getBorderColor() {
        return borderColor;
    }

    /**
     * Modifica el color de borde de la figura.
     *
     * @param borderColor
     *        Color para el borde de la figura.
     */
    public void setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
    }

    /**
     * Retorna el grueso de línea del borde de la figura
     *
     * @return Grueso de línea del borde la figura.
     */
    public int getBorderSize() {
        return borderSize;
    }

    /**
     * Modifica el grueso de línea del borde de la figura.
     *
     * @param borderSize
     *        Tamaña de línea para el borde de la figura.
     */
    public void setBorderSize(int borderSize) {
        this.borderSize = borderSize;
        borderStroke = new BasicStroke(this.borderSize);
    }

    /**
     * Determina si la figura puede ser arrastrable
     *
     * @return <code>true</code> si la figura es arrastrable, de lo contrario <code>false</code>.
     */
    public boolean isDraggable() {
        return draggable;
    }

    /**
     * Modifica la figura permitiendo que sea arrastrable o no.
     *
     * @param draggable
     *        <code>true</code> para darle a la figura la cualidad de ser
     *        arrastrable, <code>false</code> para obligar que la figura no sea
     *        arrastable.
     */
    public void setDraggable(boolean draggable) {
        this.draggable = draggable;
    }

    /**
     * Retorna la posición actual de la figura dentro del lienzo padre.
     *
     * @return Posición de la figura.
     */
    public BaZVector2D getPosition() {
        return position;
    }

    /**
     * Retorna la posición X actual de la figura dentro del lienzo padre.
     *
     * @return Posición X de la figura.
     */
    public double getX() {
        return position.getX();
    }

    /**
     * Modifica la posición X actual de la figura dentro del lienzo padre.
     *
     * @param x
     *        Posición X para la figura.
     */
    public void setX(double x) {
        position.setX(x);
    }

    /**
     * Retorna la posición Y actual de la figura dentro del lienzo padre.
     *
     * @return Posición Y de la figura.
     */
    public double getY() {
        return position.getY();
    }

    /**
     * Modifica la posición Y actual de la figura dentro del lienzo padre.
     *
     * @param y
     *        Posición Y para la figura.
     */
    public void setY(double y) {
        position.setY(y);
    }

    /**
     * Modifica la posición actual de la figura ubicándola en la posición
     * espeficiadas
     *
     * @param position
     *        Posición para la figura.
     */
    public void setPosition(BaZVector2D position) {
        this.position = position;
        this.updateAnchors();
    }

    /**
     * Modifica la posición actual de la figura ubicándola en las coordenadas
     * especificadas.
     *
     * @param position
     *        Posición para la figura.
     */
    public void setPosition(double x, double y) {
        position.setX(x);
        position.setY(y);
        this.updateAnchors();
    }

    /**
     * Determina si la figura está seleccionada o no.
     *
     * @return <code>true</code> si la figura esta seleccionada, <code>false</code>
     *         si no lo está.
     */
    public boolean isSelected() {
        return selected;
    }

    /**
     * Modifica la figura para indiciar si está seleccionada o no.
     *
     * @param selected
     *        <code>true</code> para indicar que la figura ha sido seleccionada,
     *        <code>false</code> para indicar lo contrario.
     */
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    /**
     * Determina si la figura está seleccionada o no.
     *
     * @return <code>true</code> si la figura esta seleccionada, <code>false</code>
     *         si no lo está.
     */
    public boolean isFocused() {
        return this.focused;
    }

    /**
     * Modifica la figura para indiciar si está enfocada o no.
     *
     * @param selected
     *        <code>true</code> para indicar que la figura ha sido enfocada,
     *        <code>false</code> para indicar lo contrario.
     */
    public void setFocused(boolean focused) {
        this.focused = focused;
    }

    /**
     * Retorna la lista de figuras embebidas a esta figura.
     *
     * @return Lista de figuras embebidas.
     */
    public List getShapes() {
        return shapes;
    }

    /**
     * Modifica la lista de figuras embebidas referenciando la lista especificada.
     *
     * @param shapes
     *        Lista de figuras a ser referenciadas por esta figura.
     */
    public void setShapes(List shapes) {
        this.shapes = shapes;
    }

    /**
     * Retorna el texto que se ubica en el centro de la figura.
     *
     * @return  Texto del centro de la figura.
     */
    public String getText() {
        return text;
    }

    /**
     * Modifica el texto que se ubica en el centro de la figura por el texto
     * especificado.
     *
     * @param text
     *        Texto para el centro de la figura.
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Retorna el color del texto del centro de la figura.
     *
     * @return Color de texto del centro de la figura.
     */
    public Color getTextColor() {
        return textColor;
    }

    /**
     * Modifica el color del texto del centro de la figura.
     *
     * @param textColor
     *        Color para el texto del centro de la figura.
     */
    public void setTextColor(Color textColor) {
        this.textColor = textColor;
    }

    /**
     * Determina si el texto ubicado en el centro de la figura es o no visible.
     *
     * @return <code>true</code> si el texto es visible, <code>false</code> si no lo es.
     */
    public boolean isTextVisible() {
        return textVisible;
    }

    /**
     * Modifica el texto del centro de la figura para determinar si se muestra o no.
     *
     * @param textVisible
     *        <code>true</code> para determinar que el texto del centro de la
     *        la figura es visible, <code>false</code> para determinar que no lo es.
     */
    public void setTextVisible(boolean textVisible) {
        this.textVisible = textVisible;
    }

    // --- --- ---

    /**
     * Retorna el frame que envuleve la figura. Éste método es sobre-escrito en
     * todo figura hija de ésta clase. Eso se debe a que cada figura tendrá un
     * frame diferente de acuerdo a la forma que tenga.
     *
     * @return Objeto <code>BaZRect</code> que delimita la figura de acuerdo a
     *         su forma.
     */
    public BaZRect getFrame() {
        return null;
    }

    /**
     * Determina si el punto se encuentra dentro de la figura. Este método deberá
     * ser sobre-escrito para cada una de las figuras que extiendan de esta clase
     * y su resultado dependera de la forma de la figura.
     *
     * @param p
     *        Punto a determinar si se encuentra dentro de los límites de la figura.
     *
     * @return <code>true</code> si el punto se encuntra dentro de la figura,
     *         <code>false</code> si no se encuentra dentro de ella.
     */
    public boolean isInside(Point p) {
        return false;
    }

    /**
     * Determina si ésta figura acepta o no enlace de entrada. Cuando una figura acepta
     * enlaces de entrada esta figura se convierte en la figura final del
     * enlace. Éste método es sobre-escrito por cada figura que herede de ésta clase.
     *
     * @return <code>true</code> si la figura permite entrada de enlaces.
     */
    public boolean acceptIncomingLink() {
        return false;
    }

    /**
     * Determina si ésta figura acepta o no enlace de entrada. Cuando una figura acepta
     * enlaces de entrada esta figura se convierte en la figura final del
     * enlace. Éste método es sobre-escrito por cada figura que herede de ésta clase.
     *
     * @return <code>true</code> si la figura permite entrada de enlaces.
     */
    public boolean acceptOutcomingLink() {
        return false;
    }

    /**
     * Retorna el offset estandar del ancla. Éste método será sobre-escrito
     * por las clases que hereden de ésta, y su resultado dependerá del tipo
     * de figura que la implemente.
     *
     * @param anchorKey
     *        Llave del ancla.
     *
     * @return Valor del offset de acuerdo a la llave especificada.
     */
    public double getDefaultAnchorOffset(String anchorKey) {
        return 0;
    }

    /**
     * Sobre éste método se pintan las características de la figura que será
     * dibujada sobre el lienzo padre. Por lo tanto deberá ser sobre-escrito
     * por cada clase que lo herede.
     *
     * @param g
     *        <code>Graphics2D</code> del lienzo padre.
     */
    public void paintFeatures(Graphics2D g) {}

    /**
     * Sobre éste método se pinta la figura en concreto que será dibujada sobre
     * el lienzo padre. Por lo tanto deberá ser sobre-escrito por cada clase
     * que lo herede.
     *
     * @param g
     *        <code>Graphics2D</code> del lienzo padre.
     */
    public void paintShape(Graphics2D g) {}

    /**
     * Actualiza todas las anclas asociadas a la figura. Éste método será
     * sobre-escrito por toda clase que herede de <code>BaZAbstractShape</code>.
     */
    public void updateAnchors() {}
}