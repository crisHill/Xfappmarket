package com.zls.xfappmarket.e1;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by oop on 2018/2/9.
 */

public class director {

    private List<Actor> actors;

    public void registerActor(Actor actor){
        if(actors == null){
            actors = new ArrayList<>();
        }
        actors.add(actor);
    }

    public void onGlobalLayoutFinished(int halfStageWidth, int stageHeight) {
        for (Actor actor : actors){
            actor.init(halfStageWidth, stageHeight);
        }
    }

    public void startNextPhase(){
        if(Var.curPhase >= Const.PHASE_LAST){
            return;
        }

        Var.curPhase ++;

        int size = actors.size();
        Informer informer = new Informer(size) {
            @Override
            public void allPhaseDone() {
                startNextPhase();
            }
        };

        for (Actor actor : actors){
            actor.startNextPhase(informer);
        }
    }

}
