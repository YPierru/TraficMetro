package com.yanp.traficmetro.ui.panel;

/**
 * Manage the differents panel. Force them to implement the method appear and disappear.
 */
public interface IPanel {

    /**
     * Show the panel, with the animation
     */
    public void appear();

    /**
     * Hide the panel, with the animation
     */
    public void disappear();

}
