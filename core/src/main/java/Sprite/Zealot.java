package Sprite;


import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;
import org.jbox2d.dynamics.contacts.Contact;
import playn.core.Layer;
import playn.core.PlayN;
import playn.core.Pointer;
import playn.core.util.Callback;
import playn.core.util.Clock;
import sut.game01.core.Screen.TestScreen;

public class Zealot {

    private Sprite sprite;
    private  int spriteIndex = 0;
    private boolean hasLoaded = false;

    private World world;
    private Body body;



    public enum State {
        RUN, DIE
    };

    private State state = State.RUN;

    private int e = 0;
    private int offset = 0;
    private int l = 100;
    private int t = 0;

    public Zealot(final World world, final float x,final float y){
        this.sprite = SpriteLoader.getSprite("images/zealot.json");
        sprite.addCallback(new Callback<Sprite>() {

            @Override
            public void onSuccess(Sprite result) {
                sprite.setSprite(spriteIndex);
                sprite.layer().setOrigin(sprite.width()/2f,sprite.height()/2f);
                sprite.layer().setTranslation(x, y);

                body = initPhysicsBody(world,
                        TestScreen.M_PER_PIXEL * x,
                        TestScreen.M_PER_PIXEL * y);
                hasLoaded = true;
            }

            @Override
            public void onFailure(Throwable cause) {
                PlayN.log().error("Error loading image!", cause);
            }
        });


        sprite.layer().addListener(new Pointer.Adapter() {
            @Override
            public void onPointerEnd(Pointer.Event event) {
                state = State.RUN;
                spriteIndex = -1;
                e = 0;
                l = 100;
            }
        });

    }

        private Body initPhysicsBody(World world, float x, float y) {
            BodyDef bodyDef = new BodyDef();
            bodyDef.type = BodyType.DYNAMIC;
            bodyDef.position = new Vec2(0, 0);
            Body body = world.createBody(bodyDef);

            PolygonShape shape = new PolygonShape();
            shape.setAsBox(56 * TestScreen.M_PER_PIXEL / 2,
                           sprite.layer().height()* TestScreen.M_PER_PIXEL /2);
            FixtureDef fixtureDef = new FixtureDef();
            fixtureDef.shape = shape;
            fixtureDef.density = 0.4f;
            fixtureDef.friction = 0.1f;
            body.createFixture(fixtureDef);

            body.setLinearDamping(0.2f);
            body.setTransform(new Vec2(x, y), 0f);
            return body;
        }

    public void contact (Contact contact) {

            state = State.DIE;

    }


    public Layer layer() {
        return sprite.layer();

    }

    public void update(int delta){
        if(!hasLoaded) return;
        e += delta;
        t+=delta;
        if(t>1000) {l -= 50; t=0;}

        if(l==0){state = State.DIE;}

        if(e > 150) {
            switch (state){

            case RUN:
                       offset = 0;
                       break;

            case DIE:
                       offset = 4;
                       break;
            }
        }
        if(spriteIndex==7){spriteIndex=spriteIndex+0;}else{
        spriteIndex = offset + ((spriteIndex + 1) % 4);
        sprite.setSprite(spriteIndex);
        e = 0;}
    }

    public void paint(Clock clock) {
        if (!hasLoaded) return;
        sprite.layer().setTranslation(
                (body.getPosition().x / TestScreen.M_PER_PIXEL) - 10,
                body.getPosition().y / TestScreen.M_PER_PIXEL);
    }


}
