package game;

import entityComponent.Entity;
import entityComponent.EntityFactory;
import entityComponent.components.LogicComponent;
import entityComponent.implementations.ScrollingElement;
import entityComponent.implementations.bird.BirdLogicComponent;
import game.gameEvents.GameEventDispatcher;
import game.gameEvents.GameEventType;
import graphics.Canvas;
import graphics.HUD.Hud;
import graphics.HUD.MultiplayerHud;
import logic.player.MultiModePlayer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import resources.FileKeys;
import resources.PathHandler;
import resources.PathKeys;

import java.util.concurrent.CopyOnWriteArrayList;

public class RemoteGame extends GameEventDispatcher {
    private CopyOnWriteArrayList<Entity> entities;
    private CopyOnWriteArrayList<ScrollingElement> scrollingElements;
    private BirdLogicComponent bird;
    private MultiModePlayer player;
    private Canvas canvas;
    private double gameSpeed;
    private Image background;
    private Hud hud;

    public RemoteGame(Canvas canvas, DifficultySettings settings, MultiModePlayer player) {
        this.canvas = canvas;
        this.gameSpeed = settings.getSpeed();
        entities = new CopyOnWriteArrayList<>();
        scrollingElements = new CopyOnWriteArrayList<>();
        Entity birdEntity = EntityFactory.makeBird(0.2, 0.5,canvas);
        entities.add(birdEntity);
        bird = (BirdLogicComponent) birdEntity.getLogicComponent();
        this.player=player;
        try {
            hud = new MultiplayerHud(player, canvas);
            background = new Image(PathHandler.getInstance().getPath(FileKeys.SPRITES, PathKeys.BACKGROUND));
        } catch (SlickException e) {
            e.printStackTrace();
        }

    }
    public void update(int delta){
        delta*=gameSpeed;
        updateEntities(delta);
        checkOutOfBounds();

    }
    public void render(){
        canvas.drawImage(background, 0, 0, 1, 1);
        renderEntities();
        hud.render();
    }
    public void playerJump(){
        bird.jump();
        notifyEvent(GameEventType.JUMP);
    }
    public void increaseScore(){
        player.addScore();
    }
    private void checkOutOfBounds(){
        for (ScrollingElement element: scrollingElements){
            if (element.outOfBounds()){
                scrollingElements.remove(element);
                removeEntity(element);
            }
        }
    }
    public void removeScrollingElement(ScrollingElement toRemove){
        scrollingElements.remove(toRemove);
        removeEntity(toRemove);
    }
    public void addScrollingElement(Entity scrollingElement){
        scrollingElements.add((ScrollingElement)scrollingElement.getLogicComponent());
        entities.add(scrollingElement);
    }
    private void updateEntities(int delta){
        for(Entity entity: entities){
            entity.update(delta);
        }
    }

    private void removeEntity(LogicComponent logic){
        entities.removeIf(entity -> entity.getLogicComponent() == logic);
    }
    private void renderEntities(){
        for(Entity entity: entities)
            entity.render();
    }
    private void gameover(){
        notifyEvent(GameEventType.GAMEOVER);
    }

    public BirdLogicComponent getBird() {
        return bird;
    }
    public void obstacleCollision(){
        notifyEvent(GameEventType.COLLISION);
        bird.acquireImmunity();
    }
    public void increaseCoins(){
    }
    public Entity getEntityByID(int ID) {
        for(Entity entity: entities)
            if (entity.getID()==ID)
                return entity;
        return null;
    }
    public Canvas getCanvas() {
        return canvas;
    }

    public MultiModePlayer getPlayer() {
        return player;
    }
}

