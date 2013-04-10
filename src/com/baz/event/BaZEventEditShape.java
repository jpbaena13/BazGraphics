/*
 * BaZEventEditShape.java	0.1  18/05/2011
 *
 * Copyright 2011 Universidad de Antioquia. All rights reserved.
 */
package com.baz.event;

import com.baz.canvas.BaZCanvas;
import com.baz.shape.BaZAbstractShape;
import java.awt.Point;
import java.awt.event.MouseEvent;

/**
 * Ésta clase permite gestionar el evento de modificación de una figura.
 * Comúnmente es utilizado para modificar la clase de dominio asociada a la
 * figura, y por lo tanto modifica la parte visual correspondiente al dato
 * modificado, por ejemplo, el texto central de la figura.
 *
 * @see BaZAbstractEvent
 *
 * @author JpBaena
 * @version 0.1
 * @since 1.6
 */
public class BaZEventEditShape extends BaZAbstractEvent {

    /**
     * Constructor por defecto
     *
     * @param canvas
     *        Lienzo donde ocurrirá el evento a ser gestionado.
     */
    public BaZEventEditShape(BaZCanvas canvas) {
        super(canvas);
    }

    @Override
    public void mousePressed(MouseEvent e, Point mousePosition) {
        BaZAbstractShape selectedShape = delegate.searchShapeAtPoint(mousePosition);

        if (e.getClickCount() == 2 && selectedShape != null) {
            delegate.editShape(selectedShape);
        }
    }
}
