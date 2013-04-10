/*
 * BaZMenuItem.java	0.1  20/05/2011
 *
 * Copyright 2011 Universidad de Antioquia. All rights reserved.
 */
package com.baz.menu;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

/**
 * Ésta clase crea un item de menú para añadir a un menu contextual de tipo
 * <code>BaZMenu</code>.
 *
 * @author JpBaena
 * @version 0.1
 * @since 1.6
 */
public class BaZMenuItem {

    /** <code>JMenuItem</code> asociado la clase. */
    protected JMenuItem item = null;

    /** Posición sobre el menú. */
    protected int tag = 0;

    /** Objeto asociado al item. */
    protected Object object = null;

    /** Menú padre del item de menú. */
    protected BaZMenu parentMenu = null;

    /** Objeto que tiene asociado los eventos de menú. */
    protected BaZMenuItemDelegate delegate = null;
    
    /**
     * Constructor por defecto. Inicializa las características del item de menú.
     */
    public BaZMenuItem() {
        this.init();
    }

    /**
     * @param text
     *        Texto del item de menú.
     * @param tag
     *        Posición del item de menú sobre el menú padre.
     * @param delegate
     *        Objeto que tiene asociado los eventos de menú.
     */
    public BaZMenuItem(String text, int tag, BaZMenuItemDelegate delegate) {
        this.init();
        this.setTag(tag);
        this.setDelegate(delegate);
    }

    /**
     * Construye un <code>BaZMenuItem</code> a partir de los parémtros especificados.
     *
     * @param text
     *        Texto del item de menú.
     * @param accelerator
     *        Valor del strokeMenu para el item de menú.
     * @param tag
     *        Posición del item de menú sobre el menú padre.
     * @param delegate
     *        Objeto que tiene asociado los eventos de menú.
     */
    public BaZMenuItem(String text, int accelerator, int tag, BaZMenuItemDelegate delegate) {
        this(text, accelerator, tag, delegate, null, null);
    }

    /**
     * Construye un <code>BaZMenuItem</code> a partir de los parémtros especificados.
     *
     * @param text
     *        Texto del item de menú.
     * @param accelerator
     *        Valor del strokeMenu para el item de menú.
     * @param tag
     *        Posición del item de menú sobre el menú padre.
     * @param delegate
     *        Objeto que tiene asociado los eventos de menú.
     * @param icon
     *        Icono que acompaña al item de menú.
     */
    public BaZMenuItem(String text, int accelerator, int tag, BaZMenuItemDelegate delegate, Icon icon) {
        this(text, accelerator, tag, delegate, null, icon);
    }

    /**
     * Construye un <code>BaZMenuItem</code> a partir de los parémtros especificados.
     *
     * @param text
     *        Texto del item de menú.
     * @param accelerator
     *        Valor del strokeMenu para el item de menú.
     * @param tag
     *        Posición del item de menú sobre el menú padre.
     * @param delegate
     *        Objeto que tiene asociado los eventos de menú.
     * @param modifiers
     *        Modificador de la propieadad accelerator de item de menú
     */
    public BaZMenuItem(String text, int accelerator, int tag, BaZMenuItemDelegate delegate, Integer modifiers) {
        this(text, accelerator, tag, delegate, modifiers, null);
    }

    /**
     * Construye un <code>BaZMenuItem</code> a partir de los parémtros especificados.
     *
     * @param text
     *        Texto del item de menú.
     * @param accelerator
     *        Valor del strokeMenu para el item de menú.
     * @param tag
     *        Posición del item de menú sobre el menú padre.
     * @param delegate
     *        Objeto que tiene asociado los eventos de menú.
     * @param modifiers
     *        Modificador de la propieadad accelerator de item de menú
     * @param icon
     *        Icono que acompaña al item de menú.
     */
    public BaZMenuItem(String text, int accelerator, int tag, BaZMenuItemDelegate delegate, Integer modifiers, Icon icon) {
        this.init();
        this.setText(text);
        this.setAccelerator(accelerator, modifiers);
        this.setTag(tag);
        this.setDelegate(delegate);
        this.setIcon(icon);
    }

     /**
     * Inicialia las características del item de menú.
     */
    private void init() {
        item = new JMenuItem();
        item.addActionListener(new MenuActionListener());
    }

    /**
     * Retorna el objeto de clase que tiene asociado los eventos de menú
     *
     * @return Objeto que tiene asociado los eventos de menú.
     */
    public BaZMenuItemDelegate getDelegate() {
        return delegate;
    }

    /**
     * Modifica el objeto de clase que tiene asociado los eventos de menú.
     *
     * @param delegate
     *        Objeto que tiene asociado los eventos de menú.
     */
    public final void setDelegate(BaZMenuItemDelegate delegate) {
        this.delegate = delegate;
    }

    /**
     * Retorna el <code>JMenuItem</code> asociado a la clase.
     *
     * @return Objeto <code>JMenuItem</code> asociado a la clase.
     */
    public JMenuItem getItem() {
        return item;
    }

    /**
     * Modifica el objeto <code>JMenuItem</code> asociado a la clase.
     *
     * @param item
     *        Objeto <code>JMenuItem</code> que será referenciado por ésta clase.
     */
    public void setItem(JMenuItem item) {
        this.item = item;
    }

    /**
     * Retorna el objeto asociado al item.
     *
     * @return Objeto de clase.
     */
    public Object getObject() {
        return object;
    }

    /**
     * Modifica la referencia del objeto asociado al item.
     *
     * @param object
     *        Objeto para la clase.
     */
    public void setObject(Object object) {
        this.object = object;
    }

    /**
     * Retorna el menu padre de la instancia de clase.
     *
     * @return Objeto <code>BaZMenu</code> padre de la instancia de clase.
     */
    public BaZMenu getParent() {
        return parentMenu;
    }

    /**
     * Modifica el menú padre de la instancia de clase.
     *
     * @param parentMenu
     *        Objeto <code>BaZMenu</code> que será el padre de la instancia de clase.
     */
    public void setParentMenu(BaZMenu parentMenu) {
        this.parentMenu = parentMenu;
    }

    /**
     * Retorna la posición del item dentro del menú.
     *
     * @return Index del item dentro del menú.
     */
    public int getTag() {
        return tag;
    }

    /**
     * Modifica el index del item dentro de menú.
     *
     * @param tag
     *        Index para el item dentro del menú.
     */
    public final void setTag(int tag) {
        this.tag = tag;
    }

    /**
     * Modifica el atributo accelerator del objeto <code>JMenuItem</code>
     * asociado a la clase.
     *
     * @param keyStroke
     *        Valor del keyStroke para el objeto <code>JMenuItem</code>
     */
    public void setAccelerator(int keyStroke) {
        this.setAccelerator(keyStroke, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask());
    }

    /**
     * Modifica el atributo accelerator del objeto <code>JMenuItem</code>
     * asociado a la clase.
     *
     * @param keyStroke
     *        Valor del keyStroke para el objeto <code>JMenuItem</code>
     *
     * @param keyModifier
     *        Valor del keyModifier para el objeto <code>JMenuItem</code>
     */
    public final void setAccelerator(int keyStroke, Integer keyModifier) {
        if (keyModifier == null) {
            this.setAccelerator(keyStroke);
            return;
        }
        item.setAccelerator(KeyStroke.getKeyStroke(keyStroke, keyModifier.intValue()));
    }

    /**
     * Modifica el icono asociado al item de menú.
     *
     * @param icon
     *        Icono para el item de menú.
     */
    public final void setIcon(Icon icon) {
        item.setIcon(icon);
    }

    /**
     * Retorna el texto del item de menú.
     *
     * @return String con el texto del item de menú.
     */
    public String getText() {
        return item.getText();
    }

    /**
     * Modifica el texto del item de menú.
     *
     * @param text
     *        Texto para el item de menú.
     */
    public final void setText(String text) {
        item.setText(text);
    }

    /**
     * Determina si el item de menú está habilitado o no.
     *
     * @return <code>true</code> determina que está habilitado el item de
     *         menú, <code>false</code> determina que no esta habilitado.
     */
    public boolean isEnabled() {
        return item.isEnabled();
    }

    /**
     * Modifica el estado de habilitado del menú item.
     *
     * @param enabled
     *        <code>true</code> para determinar que está habilitado, <code>false</code>
     *        para determinar que esta deshabilitado.
     */
    public void setEnabled(boolean enabled) {
        if (enabled && !this.isEnabled() || !enabled && this.isEnabled())
            item.setEnabled(enabled);
    }
    
    /**
     * Determina si el item de menú esta seleccionado o no.
     * 
     * @return <code>true</code> determina que si esta seleccionado, <code>false</code>
     *         determina que no está seleccionado.
     */
    public boolean isSelected() {
        return item.isSelected();
    }

    /**
     * Modifica el item de menú para determinar si está seleccionado o no.
     *
     * @param selected
     *        <code>true</code> para determinar que ha sido seleccionado,
     *        <code>false</code> para determinar que no ha sido seleccionado.
     */
    public void setSelected(boolean selected) {
        if (selected && !this.isSelected() || !selected && this.isSelected())
            item.setSelected(selected);
    }

    /**
     * Retorna el componente swing asociado a la instancia de clase.
     *
     * @return Componente swing asociado a la instancia de clase.
     */
    public JComponent getSwingComponent() {
        return item;
    }

    public class MenuActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(delegate != null)
                delegate.handlerMenuEvent(parentMenu, BaZMenuItem.this);
        }
    }
}