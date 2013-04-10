/*
 * BazTimerDelegate.java	0.1  28/04/2011
 *
 * Copyright 2011 Universidad de Antioquia. All rights reserved.
 */
package com.baz.timer;

/**
 * @author JpBaena
 * @version 0.1
 * @since 1.6
 */
public interface BaZTimerDelegate {
    /**
     * Método a implementar cuando el objeto <code>BaZTimer</code> es activado.
     *
     * @param timer
     */
    public void timerFired(BaZTimer timer);
}
