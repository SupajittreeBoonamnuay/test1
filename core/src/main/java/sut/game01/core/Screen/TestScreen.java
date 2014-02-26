package sut.game01.core.Screen;

import Sprite.Zealot;
import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.callbacks.DebugDraw;
import org.jbox2d.collision.Manifold;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;
import org.jbox2d.dynamics.contacts.Contact;
import playn.core.CanvasImage;
import playn.core.Image;
import playn.core.ImageLayer;
import playn.core.Pointer;
import playn.core.util.Callback;
import playn.core.util.Clock;
import sut.game01.core.DebugDrawBox2D;
import tripleplay.game.ScreenStack;
import tripleplay.game.UIScreen;
import tripleplay.ui.Root;

import static playn.core.PlayN.assets;
import static playn.core.PlayN.graphics;
import static playn.core.PlayN.pointer;

public class TestScreen extends UIScreen {

    private final ScreenStack ss;

    public TestScreen(ScreenStack ss){this.ss = ss;}

    private Root root;
    Zealot z ;

    public static float M_PER_PIXEL = 1 / 26.666667f;

    //size of world
    private static int width= 24; // 640px in physic unit (meter)
    private static int height = 18; // 480px in physic unit (meter)

    private World world;
    private boolean showDebugDraw = true;
    private DebugDrawBox2D debugDraw;



    @Override
    public void wasAdded(){

        super.wasAdded();

        Vec2 gravity = new Vec2(0.0f, 10.0f);
        world = new World(gravity, true);
        world.setWarmStarting(true);
        world.setAutoClearForces(true);

        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                    z.contact(contact);

            }

            @Override
            public void endContact(Contact contact) {

            }

            @Override
            public void preSolve(Contact contact, Manifold manifold) {

            }

            @Override
            public void postSolve(Contact contact, ContactImpulse contactImpulse) {

            }
        });

        if(showDebugDraw){

            CanvasImage image = graphics().createImage(640, 480);

            layer.add(graphics().createImageLayer(image));
            debugDraw = new DebugDrawBox2D();
            debugDraw.setCanvas(image);
            debugDraw.setFlipY(false);
            debugDraw.setStrokeAlpha(150);
            debugDraw.setFillAlpha(75);
            debugDraw.setStrokeWidth(2.0f);
            debugDraw.setFlags(DebugDraw.e_shapeBit |
                               DebugDraw.e_jointBit |
                               DebugDraw.e_aabbBit);
            debugDraw.setCamera(0, 0, 26.66667f);
            world.setDebugDraw(debugDraw);

        }

        Body ground = world.createBody(new BodyDef());
        PolygonShape groundShape = new PolygonShape();
        groundShape.setAsEdge(new Vec2(2f, height-2),
                              new Vec2(width-2f, height-2));
        ground.createFixture(groundShape, 0.0f);

        /*BodyDef af = new BodyDef();
        af.type = BodyType.DYNAMIC;
        af.position = new Vec2(0, 0);
        final Body a = world.createBody(af);
        PolygonShape ashape = new PolygonShape();
        ashape.setAsBox(2f, 2f);
        FixtureDef afd = new FixtureDef();
        afd.shape = ashape;
        afd.density = 0.1f;
        afd.friction = 0.1f;
        a.createFixture(afd);
        a.setLinearDamping(0.5f);
        a.setTransform(new Vec2(5f, 15f), 0);*/

        BodyDef bf = new BodyDef();
        bf.type = BodyType.DYNAMIC;
        bf.position = new Vec2(0, 0);
        Body b = world.createBody(bf);
        PolygonShape bshape = new PolygonShape();
        bshape.setAsBox(1f, 1f);
        FixtureDef bfd = new FixtureDef();
        bfd.shape = bshape;
        bfd.density = 0.1f;
        bfd.friction = 0.1f;
        b.createFixture(bfd);
        b.setLinearDamping(0.5f);
        b.setTransform(new Vec2(10f, 15f), 0);

        /*z.layer().addListener(new Pointer.Adapter() {
            @Override
            public void onPointerEnd(Pointer.Event event) {
                a.applyLinearImpulse(new Vec2(100f, 0f), a.getPosition());
            }
        });*/





        Image bgImage = assets().getImage("images/bg.png");
        bgImage.addCallback(new Callback<Image>() {
            @Override
            public void onSuccess(Image result) {

            }

            @Override
            public void onFailure(Throwable cause) {

            }
        });

        ImageLayer bgLayer = graphics().createImageLayer(bgImage);
        //layer.add(bgLayer);
        z = new Zealot(world, 100f, 100f);
        layer.add(z.layer());


    }

    private void createBox() {
        BodyDef bf = new BodyDef();
        bf.type = BodyType.DYNAMIC;
        bf.position = new Vec2(0, 0);

        Body body = world.createBody(bf);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(1f, 1f);
        FixtureDef fd = new FixtureDef();
        fd.shape = shape;
        fd.density = 0.1f;
        fd.friction = 0.1f;
        fd.restitution = 1f;
        body.createFixture(fd);
        body.setLinearDamping(0.5f);
        body.setTransform(new Vec2(10f, 0f), 0);
    }

    @Override
    public void wasShown(){
        super.wasShown();
        /*root = iface.createRoot(
                vertical().gap(15),
                newSheet(),
                layer);
        root.addStyles(Style.BACKGROUND
                .is(Background.bordered(0xFFCCCCCC, 0xFF99CCFF, 5)
                        .inset(5, 10)));
        root.setSize(width(), height());

        root.add(new Button("back").onClick(new UnitSlot() {
            @Override
            public void onEmit() {
                ss.remove(ss.top());
            }
        }));*/

    }

    @Override
    public void update(int delta) {
        super.update(delta);
        world.step(0.033f, 10, 10);
        z.update(delta);


    }

    @Override
    public void paint(Clock clock) {
        super.paint(clock);
        if(showDebugDraw){
            debugDraw.getCanvas().clear();
            world.drawDebugData();
        }
        z.paint(clock);


    }




}
