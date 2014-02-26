package sut.game01.core;

import java.util.ArrayList;
import java.util.List;

import playn.core.*;
import playn.core.util.Clock;
import sut.game01.core.Screen.HomeScreen;
import tripleplay.game.Screen;
import tripleplay.game.ScreenStack;

public class MyGame extends Game.Default {

    /*GroupLayer peaLayer;
    List<Pea> peas = new ArrayList<Pea>(0);*/

//public static final int UPDATE_RATE = 25;

    private ScreenStack ss = new ScreenStack();
    private Clock.Source clock = new Clock.Source(33);

    public MyGame() {
        super(33);
//super(UPDATE_RATE);
    }

    @Override
    public void init() {
/*// create and add background image layer
Image bgImage = assets().getImage("images/bg.png");
ImageLayer bgLayer = graphics().createImageLayer(bgImage);
graphics().rootLayer().add(bgLayer);

// create a group layer to hold the peas
peaLayer = graphics().createGroupLayer();
graphics().rootLayer().add(peaLayer);

// preload the pea image into the asset manager cache
assets().getImage(Pea.IMAGE);

// add a listener for pointer (mouse, touch) input
pointer().setListener(new Pointer.Adapter() {
@Override
public void onPointerEnd(Pointer.Event event) {
Pea pea = new Pea(peaLayer, event.x(), event.y());
peas.add(pea);
}
});*/
        final Screen home = new HomeScreen(ss);
        ss.push(home);
        PlayN.keyboard().setListener(new Keyboard.Adapter() {
            @Override
            public void onKeyDown (Keyboard.Event event){
                if(event.key()==Key.ESCAPE){
                    ss.popTo(home);
                }
            }

        });
    }

    @Override
    public void update(int delta) {
/*for (Pea pea : peas) {
pea.update(delta);
}*/
        ss.update(delta);

    }

    @Override
    public void paint(float alpha) {
/*for (Pea pea : peas) {
pea.paint(alpha);
}*/

        clock.paint(alpha);
        ss.paint(clock);
    }
}
