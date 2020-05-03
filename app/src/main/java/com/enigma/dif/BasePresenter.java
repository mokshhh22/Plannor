package com.enigma.dif;

public interface BasePresenter<V> {
    // This method is called by the View on the presenter when it is active
    void bindView(V view);

    // This method is called by the View when it is no longer active
    void unBindView();
}

