package com.zls.xfappmarket.e1;

/**
 * Created by oop on 2018/2/10.
 */

public abstract class Informer {

    private int total;
    private int done;

    public Informer(int total){
        this.total = total;
    }

    public void onPhaseDone(){
        done ++;
        if(done == total){
            allPhaseDone();
        }
    }

    public abstract void allPhaseDone();

}
