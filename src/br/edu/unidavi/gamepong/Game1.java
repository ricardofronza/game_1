package br.edu.unidavi.gamepong;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javaPlay.GameEngine;

/**
 * Classe que define a incialização do Game Pong
 * @author marcondes
 */
public class Game1 {
    
    private Player player;
    
    public Game1() {
        try {
            this.player = new Player();
        } catch (IOException e) {e.printStackTrace();}
    }
    
    public void inicia() {
        GameEngine.getInstance().addGameStateController(0, this.player);        
        GameEngine.getInstance().setStartingGameStateController(0);
        GameEngine.getInstance().addGameStateController(1, this.player);        
        GameEngine.getInstance().setStartingGameStateController(1);
        GameEngine.getInstance().run();
    }
    
    public static GameEngine GetGameEngine(){
        return GameEngine.getInstance();
    }
    
    public Player getJogador() {
        return player;
    }
    
    public void setJogador(Player playe) {
        this.player = playe;
    }
    
}
