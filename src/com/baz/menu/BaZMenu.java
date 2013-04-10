/*
 * BaZMenu.java	0.1  20/05/2011
 *
 * Copyright 2011 Universidad de Antioquia. All rights reserved.
 */
package com.baz.menu;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

/**
 * Ésta clase crea un menú contextual que debe ser añadido a un objeto 
 * <code>BaZMenuBar</code>, y contendrá elementos de tipo <code>BaZMenuItem</code>.
 * 
 * @author JpBaena
 * @version 0.1
 * @since 1.6
 */
public class BaZMenu extends BaZMenuItem {

    /** <code>JMenu</code> asociado a la clase. */
    protected JMenu menu = null;

    /** Conjunto de objetos <code>BaZMenuItem</code> que están asociado a la instancia de clase.*/
    protected List items = null;

    /** Barra de menú padre.*/
    protected BaZMenuBar parentMenuBar = null;

    /**
     * Construye un menú con el título especificado.
     *
     * @param title
     *        Título para el menú.
     */
    public BaZMenu(String title) {
        menu = new JMenu(title);
        items = new ArrayList();
        menu.addMenuListener(new BaZMenuListener());
    }

    /**
     * Modifica la barra de menú padre de la instancia de clase.
     *
     * @param parentMenuBar
     *        Barra de menú padre para la instancia de clase.
     */
    public void setParentMenuBar(BaZMenuBar parentMenuBar) {
        this.parentMenuBar = parentMenuBar;

        for (int i = 0; i < items.size(); i++) {
            Object obj = items.get(i);

            if (obj instanceof BaZMenu)
                ((BaZMenu) obj).setParentMenuBar(parentMenuBar);
        }
    }

    /**
     * Modifica el texto asociado al menu contextual.
     *
     * @param title
     *        Texto como título para el menú.
     */
    public void setTitle(String title) {
        menu.setText(title);
    }

    /**
     * Añade el item especificado al menú contextual.
     *
     * @param item
     *        Objeto <code>BaZMenuItem</code> a ser añadido al menú.
     */
    public void addItem(BaZMenuItem item) {
        item.setParentMenu(this);
        items.add(item);
        menu.add(item.getSwingComponent());
    }

    /**
     * Añade el menú especificado al menú contextual.
     *
     * @param menu
     *        Objeto <code>BaZMenu</code> a se añadido al menu.
     */
    public void addItem(BaZMenu menu) {
        menu.setParentMenu(this);
        items.add(menu);
        this.menu.add(menu.getSwingComponent());
    }

    /**
     * Añade el item especificado en la posición especificado sobre el menú.
     *
     * @param item
     *        Item a ser añadido.
     *
     * @param index
     *        Posición en la que será añadido el item.
     */
    public void addItemAtIndex(BaZMenuItem item, int index) {
        items.add(index, item);
        menu.add(item.getSwingComponent(), index);
    }

    /**
     * Añade un item antes de la posición especificada.
     *
     * @param item
     *        Item a ser añadido.
     *
     * @param menuItemTag
     *        Posición posterior a donde será añadido el item.
     */
    public void addItemBefore(BaZMenuItem item, int menuItemTag) {
        BaZMenuItem _item = this.getItemByTag(menuItemTag);

        if (_item == null)
            return;

        this.addItemAtIndex(item, items.indexOf(_item));
    }

    /**
     * Añade un item depués de la posición especificada.
     *
     * @param item
     *        Item a ser añadido.
     *
     * @param menuItemTag
     *        Posición anterior a donde será añadido el item.
     */
    public void addItemAfter(BaZMenuItem item, int menuItemTag) {
        BaZMenuItem _item = this.getItemByTag(menuItemTag);

        if (_item == null)
            return;

        this.addItemAtIndex(item, items.indexOf(_item) + 1);
    }

    /**
     * Remueve el item que se encuentra en la posición especificada.
     *
     * @param index
     *        Posición del item a ser removido.
     */
    public void removeItem(int index) {
        menu.remove(index);
    }

    /**
     * Añade un separado de items dentro del menú contextual.
     */
    public void addSeparator() {
        items.add(new BaZMenuItemSeparator());
        menu.addSeparator();
    }

    /**
     * Añade un separador de items en la posición index especificada, sobre el
     * menu
     *
     * @param index
     *        Posición sobre el menú, donde será añadido el separador
     */
    public void addSeparatorAtIndex(int index) {
        items.add(index, new BaZMenuItemSeparator());
        menu.insertSeparator(index);
    }

    /**
     * Añade un elemento separador antes de la posición especificada del item de menú.
     *
     * @param menuItemTag
     *        Posición del item de menú posterior a donde se añadirá el separador.
     */
    public void addSeparatorBefore(int menuItemTag) {
        BaZMenuItem _item = this.getItemByTag(menuItemTag);

        if (_item == null)
            return;

        this.addSeparatorAtIndex(items.indexOf(menuItemTag));
    }

    /**
     * Añade un elemento separador después de la posición especificada del item de menú.
     *
     * @param menuItemTag
     *        Posición del item de menú anterior a donde se añadirá el separador.
     */
    public void addSeparatorAfter(int menuItemTag) {
        BaZMenuItem _item = this.getItemByTag(menuItemTag);

        if (_item == null)
            return;

        this.addSeparatorAtIndex(items.indexOf(menuItemTag) + 1);
    }

    /**
     * Retorn el item de menú que se encuentra en la posición tag especificada.
     *
     * @param tag
     *        Posición del item a retorna.
     *
     * @return Item que se encuentra en la posición tad especificada.
     */
    public BaZMenuItem getItemByTag(int tag) {
        Iterator iterator = items.iterator();

        while (iterator.hasNext()) {
            BaZMenuItem _item = (BaZMenuItem) iterator.next();

            if (_item.getTag() == tag)
                return _item;
        }

        return null;
    }

    /**
     * Retorna el menú que se encuentra en la posición index del menú.
     *
     * @param index
     *        Posición del item que será retornado.
     *
     * @return Item de menú que se encuentra en la posición index.
     */
    public BaZMenuItem getItemAtIndex(int index) {

        if (index >= 0 && index < items.size())
            return (BaZMenuItem) items.get(index);

        return null;
    }

    /**
     * Retorna el número de items que contiene el menú, incluyendo separadores.
     *
     * @return Un número entero igual al número items que contiene el menú.
     */
    public int getItemCount() {
        return menu.getItemCount();
    }

    /**
     * Retorna el iterador de la lista de items del menú.
     *
     * @return Iterator de la lista de items del menú.
     */
    public Iterator itemIterator() {
        return items.iterator();
    }

    /**
     * Elimina todo los items del menú dejandolo vacio.
     */
    public void clear() {
        items.clear();
        menu.removeAll();
    }

    @Override
    public JComponent getSwingComponent() {
        return menu;
    }

    public class BaZMenuListener implements MenuListener {
        @Override
        public void menuSelected(MenuEvent e) {
            if(parentMenuBar != null)
                parentMenuBar.menuSelected(BaZMenu.this);
        }

        @Override
        public void menuDeselected(MenuEvent e) {}

        @Override
        public void menuCanceled(MenuEvent e) {}
    }
}
