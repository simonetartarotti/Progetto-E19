package states;

import GameScore.ScoreBoard;
import graphics.GUI.ScoreBoardMenuGUI;
import graphics.Screen;
import graphics.SpriteDrawer;
import logic.SinglePlayer.Record;
import org.newdawn.slick.*;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class ScoreBoardState extends BasicGameState {
    private static final int ID = 9;
    private Record record;
    private GameContainer container;
    private StateBasedGame statebasedgame;
    private ScoreBoardMenuGUI gui;
    private Screen screen;
    private SpriteDrawer drawer;
    private TextField text1;


    @Override
    public int getID() {
        return ID;
    }

    public ScoreBoardState(Record record){
        super();
        this.record = record;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        this.container= gameContainer;
        this.statebasedgame= stateBasedGame;
        screen = new Screen(gameContainer.getWidth(), gameContainer.getHeight(), 0,0);
        drawer = new SpriteDrawer(screen);
        gui = new ScoreBoardMenuGUI(gameContainer, this, screen);
        container.getGraphics().clearWorldClip();
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        graphics.clearWorldClip();
        drawer.drawBackgroundSingle(graphics);
        gui.render();
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {

    }


}