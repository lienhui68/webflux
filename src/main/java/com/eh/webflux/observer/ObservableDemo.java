package com.eh.webflux.observer;

import java.util.Observable;

public class ObservableDemo extends Observable {

    public static void main(String[] args) {
        ObservableDemo observableDemo = new ObservableDemo();
        observableDemo.addObserver((o, arg) -> System.out.println("hello"));
        observableDemo.addObserver((o, arg) -> System.out.println("world"));
        observableDemo.setChanged();
        observableDemo.notifyObservers();

    }
}
