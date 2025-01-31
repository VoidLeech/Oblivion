package com.github.voidleech.oblivion.util;

import java.util.function.Supplier;

public class AssignOnceSupplier<T> implements Supplier<Supplier<T>> {
    private Supplier<T> ref;
    private boolean assigned = false;

    public boolean isAssigned(){
        return assigned;
    }

    public void assign(Supplier<T> ref){
        if (assigned){
            throw new RuntimeException("Can't assign AOS after it's been assigned");
        }
        this.ref = ref;
        assigned = true;
    }

    @Override
    public Supplier<T> get() {
        if (!assigned){
            throw new RuntimeException("Can't get from AOS before it's assigned.");
        }
        return ref;
    }
}
