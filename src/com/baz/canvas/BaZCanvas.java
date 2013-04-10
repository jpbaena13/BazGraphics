/*
 * BaZCanvas.java	0.1  28/04/2011
 *
 * Copyright 2011 Universidad de Antioquia. All rights reserved.
 */
package com.baz.canvas;

import com.baz.graphics.base.BaZAlphaVariator;
import com.baz.shape.base.BaZRect;
import com.baz.event.BaZEventCreateLink;
import com.baz.event.BaZEventCreateShape;
import com.baz.event.BaZEventDragRootShape;
import com.baz.event.BaZEventDragSelection;
import com.baz.event.BaZEventDragShape;
import com.baz.event.BaZEventEditLink;
import com.baz.event.BaZEventEditShape;
import com.baz.event.BaZEventFocusShape;
import com.baz.event.BaZEventManager;
import com.baz.frame.BaZPanel;
import com.baz.menu.BaZMenu;
import com.baz.menu.BaZMenuItem;
import com.baz.menu.BaZMenuItemDelegate;
import com.baz.shape.BaZAbstractShape;
import com.baz.shape.BaZShapeLink;
import com.baz.timer.BaZTimer;
import com.baz.timer.BaZTimerDelegate;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.Iterator;
import javax.swing.JPopupMenu;

/**
 * Lienzo principal de objetos dibujables.
 *
 * @author JpBaena
 * @version 0.1
 * @since 1.6
 */
public class BaZCanvas extends BaZPanel implements BaZCanvasDelegate, BaZTimerDelegate, BaZMenuItemDelegate {

    /** Planaridad por defecto de los enlances a crear. */
    public static final double DEFAULT_LINK_FLATNESS = 20;

    /** Gestor de eventos para este lienzo. */
    protected BaZEventManager eventManager = new BaZEventManager(this);

    /** Canvas asignado para la instancia. */
    protected BaZCanvasDelegate delegate = null;

    /** Figura raiz, ésta figura contiene cualquier otra figura que sea creada.
        Abarcará todo el lienzo padre.*/
    protected BaZAbstractShape rootShape = null;

    /** Determina el nivel de zoom para el canvas. */
    public float zoom = 1;

    /** Determina si las lineas de los graves son sueaves o no. */
    protected boolean smoothGraphics = true;

    /** Timer que contendra los elementos que sean seleccionados*/
    protected BaZTimer selectedTimer = new BaZTimer(this);

    /** Timer que contendra el elemento que está enfocado*/
    protected BaZTimer focusedTimer = new BaZTimer(this);

    /** Determina la variación del alpha del alguna figura cuando está seleccionada*/
    protected BaZAlphaVariator selectedAlphaVariator = new BaZAlphaVariator();

    /** Determina la variación del alpha del alguna figura cuando está enfocada*/
    protected BaZAlphaVariator focusedAlphaVariator = new BaZAlphaVariator();

    /**
     * Contructor de un lienzo por defecto.
     */
    public BaZCanvas() {
        this.setFocusable(false);

        this.setBackground(Color.lightGray);
        this.setPreferredSize(new Dimension(1024, 600));

        this.addDefaultEventManager();
    }

    /**
     * Añade los eventos por defecto al gestor de eventos.
     */
    private void addDefaultEventManager() {
        // El orden de estos métodos es importante.
        eventManager.add(new BaZEventDragShape(this));
        eventManager.add(new BaZEventDragRootShape(this));
        eventManager.add(new BaZEventDragSelection(this));
        eventManager.add(new BaZEventEditShape(this));
        eventManager.add(new BaZEventCreateLink(this));
        eventManager.add(new BaZEventCreateShape(this));
        eventManager.add(new BaZEventFocusShape(this));
        eventManager.add(new BaZEventEditLink(this));
    }

    /**
     * Retorna la el tipo de enlace por defecto que tendrá el canvas.
     *
     * @return Tipo de enlace por defecto.
     */
    public int defaultShapeLink() {
        return BaZShapeLink.SHAPE_ARC;
    }

    /**
     * Retorna la posición del mouse sobre el <code>BaZCanvas</code> de acuerdo
     * al valor de nivel de zoom.
     *
     * @param e
     *        Evento del mouse con información de la posición de éste.
     * @return
     */
    public Point getMousePosition(MouseEvent e) {
        Point p = e.getPoint();
        if(zoom != 1)
            return new Point((int)(p.x/zoom), (int)(p.y/zoom));
        else
            return p;
    }

    /**
     * Retorna el valor del objeto de variación de alfa cuando un objeto
     * es seleccionado.
     *
     * @return Valor correspondiente al variador de alfa para objetos seleccionados.
     */
    public float getSelectedAlphaValue() {
        return selectedAlphaVariator.getAlphaValue();
    }

     /**
     * Retorna el valor del objeto de variación de alfa cuando un objeto
     * es enfocados.
     *
     * @return Valor correspondiente al variador de alfa para objetos enfocados.
     */
    public float getFocusedAlphaValue() {
        return focusedAlphaVariator.getAlphaValue();
    }

    /**
     * Retorna el gestor de eventos de ésta instancia.
     *
     * @return Objeto <code>BaZEventManager</code> que es el gestor de eventos
     *         de ésta instancia.
     */
    public BaZEventManager getEventManager() {
        return eventManager;
    }

    /**
     * Modifica el gestor de eventos de la instancia de clase.
     *
     * @param eventManager
     *        Gestor de eventos para la clase.
     */
    public void setEventManager(BaZEventManager eventManager) {
        this.eventManager = eventManager;
    }

    /**
     * Retorna el canvas delegado de la instancia de clase.
     *
     * @return Objeto <code>BaZCanvasDelegate</code> como canvas delegado para
     *         la instancia de clase.
     */
    public BaZCanvasDelegate getDelegate() {
        return delegate;
    }

    /**
     * Modifica el canvas delegado de la instancia de clase.
     *
     * @param delegate
     *        Objeto <code>BaZCanvasDelegate</code> para la instancia de clase.
     */
    public void setDelegate(BaZCanvasDelegate delegate) {
        this.delegate = delegate;
    }

    /**
     * Modifica la figura raíz que contiene todas las figuras dibujables del lienzo.
     *
     * @param rootShape
     *        Objeto <code>BaZAbsractShape</code> que será la figura raíz de todos
     *        los objetos dibujables.
     */
    public final void setRootShape(BaZAbstractShape rootShape) {
        this.rootShape = rootShape;

         if(rootShape != null) {
            this.rootShape.setCanvas(this);
        }
    }

    /**
     * Permite obtener el menú contextual del la figura especificada. Cada
     * figura podrá tener un menú contextual diferente, de acuerdo a la tipo
     * de aplicación que la utilice. Para ello se deberá sobre-escribir éste
     * método.
     *
     * @param shape
     *        Figura a realizar el menú contextual.
     *
     * @return Objeto <code>JPopupMenu</code> que contiene el menú contextual.
     */
    public JPopupMenu getContextualMenu(BaZAbstractShape shape) {
        return null;
    }

    /**
     * Añade un item al menú especificado.
     *
     * @param menu
     *        Menú donde será añadido el item.
     * @param text
     *        Texto que llevará el item de menú.
     * @param tag
     *        Posición del item dentro del menú.
     * @param object
     *        Objeto asociado al item.
     */
    public void addMenuItem(JPopupMenu menu, String text, int tag, Object object) {
        BaZMenuItem item = new BaZMenuItem();
        item.setText(text);
        item.setTag(tag);
        item.setObject(object);
        item.setDelegate(this);

        menu.add(item.getSwingComponent());
    }

    /**
     * Añade un item al menú especificado.
     *
     * @param menu
     *        Menú donde será añadido el item.
     * @param text
     *        Texto que llevará el item de menú.
     * @param tag
     *        Posición del item dentro del menú.
     * @param object
     *        Objeto asociado al item.
     */
    public void addMenuItem(BaZMenu menu, String text, int tag, Object object) {
        BaZMenuItem item = new BaZMenuItem();
        item.setText(text);
        item.setTag(tag);
        item.setObject(object);
        item.setDelegate(this);

        menu.addItem(item);
    }

    @Override
    public void processMouseEvent(MouseEvent e) {
        if (e.isPopupTrigger()) {
            JPopupMenu menu = this.getContextualMenu(this.searchShapeAtPoint(getMousePosition(e)));
            if(menu != null) {
                menu.show(this, e.getX(), e.getY());
                return;
            }
        }
        
        super.processMouseEvent(e);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        // Determina si las lineas de los gráficos son suaves o no
        if(smoothGraphics)
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);

        // Determina el nivel de Zoom de los gráficos
        g2d.scale(zoom, zoom);

        if(rootShape != null)
            rootShape.paintRecursive(g2d);

        eventManager.performEventObjects(BaZEventManager.EVENT_PAINT, null, null, g);
    }

    /* --- METODOS INTERFAZ BaZCanvasDelegate --- */

    @Override
    public BaZAbstractShape getRootShape() {
        return rootShape;
    }

    @Override
    public BaZAbstractShape searchShapeAtPoint(Point p) {
        if(rootShape != null)
            return rootShape.match(p);
        else
            return null;
    }

    @Override
    public void selectingAllShapes(boolean select) {
        if (rootShape == null)
            return;

        Iterator iterator = rootShape.getShapes().iterator();

        while (iterator.hasNext()) {
            BaZAbstractShape shape = (BaZAbstractShape) iterator.next();
            shape.setSelected(select);

            if (select)
                addSelectedShape(shape);
            else
                removeSelectedShape(shape);
        }
    }

    @Override
    public void addFocusedShape(BaZAbstractShape shape) {
        focusedTimer.add(shape);
    }

   @Override
    public void removeFocusedShape(BaZAbstractShape shape) {
        focusedTimer.remove(shape);
    }

    @Override
    public void addSelectedShape(BaZAbstractShape shape) {
        selectedTimer.add(shape);
    }

    @Override
    public void removeSelectedShape(BaZAbstractShape shape) {
        selectedTimer.remove(shape);
    }

    @Override
    public boolean isSelectedShape(BaZAbstractShape shape) {
        return selectedTimer.contains(shape);
    }

  @Override
    public void moveSelectedShapes(double dx, double dy) {
        Iterator iterator = selectedTimer.getElements().iterator();

        while (iterator.hasNext()) {
            BaZAbstractShape shape = (BaZAbstractShape) iterator.next();
            shape.move(dx, dy);
        }
    }

    @Override
    public void selectingShapesInRect(int x, int y, int dx, int dy) {
        if (rootShape == null || rootShape.getShapes() == null)
            return;
        
        BaZRect rect = new BaZRect(x, y , dx, dy);

        Iterator iterator = rootShape.getShapes().iterator();

        while (iterator.hasNext()) {
            BaZAbstractShape shape = (BaZAbstractShape) iterator.next();

            boolean selected = BaZRect.getIntersect(rect, shape.bounds());
            shape.setSelected(selected);
            
            if (selected)
                this.addSelectedShape(shape);
            else
                this.removeSelectedShape(shape);
        }
    }

    /* Éste método deberá ser sobre-escrito por las clases que hereden de ésta clase. */
    @Override
    public void createShape(Point p, boolean doubleClick) {}

    /* Éste método deberá ser sobre-escrito por las clases que hereden de ésta clase. */
    @Override
    public void removeShape(BaZAbstractShape shape) {}

    /* Éste método deberá ser sobre-escrito por las clases que hereden de ésta clase. */
    @Override
    public boolean canCreateLink() {
        return false;
    }

    @Override
    public void editShape(BaZAbstractShape shape) {}

    @Override
    public void createLink(BaZAbstractShape source, String sourceAnchorKey,
            BaZAbstractShape target, String targetAnchorKey, int shape, Point p) {
        
        rootShape.addShape(new BaZShapeLink(source, sourceAnchorKey, target,
                targetAnchorKey, shape, "", p, BaZCanvas.DEFAULT_LINK_FLATNESS));
    }

     /* --- METODO INTERFAZ BaZTimerDelegate --- */
    @Override
    public void timerFired(BaZTimer timer) {
        if (timer == selectedTimer)
            selectedAlphaVariator.run();
        else if (timer == focusedTimer)
            focusedAlphaVariator.run();

        this.repaint();
    }

    /* --- METODO INTERFAZ BaZMenuItemDelegate --- */
    @Override
    public void handlerMenuEvent(BaZMenu menu, BaZMenuItem item) {}


    /* --- METODOS ENCARGADOS DE MANEJAR LOS EVENTOS DE MOUSE Y TECLADO --- */
    @Override
    public void handlerMousePressed(MouseEvent e) {
        eventManager.performEventObjects(BaZEventManager.EVENT_MOUSE_PRESSED, e, getMousePosition(e), null);
    }

    @Override
    public void handlerMouseReleased(MouseEvent e) {
        eventManager.performEventObjects(BaZEventManager.EVENT_MOUSE_RELEASED, e, getMousePosition(e), null);
    }

    @Override
    public void handlerMouseDragged(MouseEvent e) {
        eventManager.performEventObjects(BaZEventManager.EVENT_MOUSE_DRAGGED, e, getMousePosition(e), null);
    }

    @Override
    public void handlerMouseMoved(MouseEvent e) {
        eventManager.performEventObjects(BaZEventManager.EVENT_MOUSE_MOVED, e, getMousePosition(e), null);
    }

    @Override
    public void handlerMouseEntered(MouseEvent e) {
        selectedTimer.refresh();
        focusedTimer.refresh();
    }

    @Override
    public void handlerMouseExited(MouseEvent e) {
        selectedTimer.stop();
        focusedTimer.stop();
    }

    @Override
    public void handlerMouseWheel(MouseWheelEvent e) {
        if (e.getWheelRotation() > 0) {
            if (zoom < 2)
                zoom += 0.1;
        } else if (zoom > 0.5)
            zoom -= 0.1;
        
        this.repaint();
    }

    @Override
    public void handlerKeyTyped(KeyEvent e) {
        eventManager.performEventObjects(BaZEventManager.EVENT_KEY_TYPED, e, null, null);
    }

    @Override
    public void handlerKeyPressed(KeyEvent e) {
        eventManager.performEventObjects(BaZEventManager.EVENT_KEY_PRESSED, e, null, null);
    }
}
