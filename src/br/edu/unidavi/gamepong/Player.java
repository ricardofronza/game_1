package br.edu.unidavi.gamepong;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.IOException;
import static java.lang.Thread.sleep;
import java.util.logging.Level;
import java.util.logging.Logger;
import javaPlay.GameEngine;
import javaPlay.GameStateController;
import javaPlay.Keyboard;
import javaPlay.Sprite;

public class Player implements GameStateController {

    //Inicialização de variaveis de controle
    private int posBarra1;
    private int posBarra2;
    private int altura;
    private int direcaoX = 0;
    private int direcaoY = 0;
    private int posBolaY;
    private int posBolaX;
    private int ponto = 0;
    private int pontuacaoA = 0;
    private int pontuacaoB = 0;
    private int velocidadeBola = 3;
    
    //tela
    private int largura;
    
    //Variáveis utilizadas para os Sprites
    private Sprite figuraBola;
    private Sprite barra1;
    private Sprite barra2;
    private Sprite figuraBackground;
    
    
    //Inicialização das Classes do Game
    Background background = new Background();
    Bola bola1  = new Bola();
    Barra barraA = new Barra();
    Barra barraB = new Barra();
    
    public void setaPosicoes(int bara1, int bara2, int poX, int poY){
        this.posBarra1 = bara1;
        this.posBarra2 = bara2;
        this.posBolaY = poX;
        this.posBolaY = poY;
    }
    
    public int getPosBarra1() {
        return this.posBarra1;
    }
    public int getPosBarra2() {
        return this.posBarra2;
    }
    public int getPosBolaX() {
        return this.posBolaX;
    }
    public int getPosBolaY() {
        return this.posBolaY;
    }
    
    public Player() throws IOException {
        //inicializando o game com a tamanho padrão do GameEngine
        altura  = GameEngine.getInstance().getGameCanvas().getHeight();
        largura = GameEngine.getInstance().getGameCanvas().getWidth();
             
        //Iniciando a bola no meio da tela
        posBarra1 = altura  / 2;
        posBarra2 = altura  / 2;
        posBolaY  = altura  / 2;
        posBolaX  = largura / 2;
        
        //velocidade inicial da bola em cada eixo (px)
        try {
            //Carregamento dos sprites do game
            figuraBackground = new Sprite("background.png", 1, 800, 800);
            figuraBola       = new Sprite("bola.png", 3, 77, 77);
            barra1           = new Sprite("Pong_pad01.png", 3, 25, 100);
            barra2           = new Sprite("Pong_pad02.png", 3, 25, 100);
        }
        catch(Exception erro) {
            Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, erro);
        }
        background.setSprite(figuraBackground);
        bola1.setSprite(figuraBola);
        barraA.setSprite(barra1);
        barraB.setSprite(barra2);
        
    }
    
    @Override
    public void step(long l) {
        try {
            sleep(10);
        }
        catch(InterruptedException erro) {
            Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, erro);
        }
        
        //Configração das teclas de controle do Game
        Keyboard teclado = GameEngine.getInstance().getKeyboard();
        if( (teclado.keyDown(Keyboard.UP_KEY) == true) && (posBarra2 > 10) ) {
            posBarra2 -= 3;
        }
        if( (teclado.keyDown(Keyboard.DOWN_KEY) == true) && (posBarra2 < (altura - 150) ) ) {
            posBarra2 += 3;
        }
        /**
         * Novas teclas A e Z adicionadas para utilizar o game com dois Players
         */
        if( (teclado.keyDown(Keyboard.A_KEY) == true) && (posBarra1 > 10) ) {
            posBarra1 -= 3;
        }
        if( (teclado.keyDown(Keyboard.Z_KEY) == true) && (posBarra1 < (altura - 150) ) ) {
            posBarra1 += 3;
        }
        
        //Todas as verificações para identificar as colisões com da bola com parede
        if((direcaoX == 0) && (posBolaX > 10)) {
            posBolaX -= velocidadeBola;
        }
        else {
            direcaoX = 1;
        }
        if((direcaoX == 1) && (posBolaX < (largura - 60))){
            posBolaX += velocidadeBola;
        }
        else{
            direcaoX = 0;
        }
        if((direcaoY == 0) && (posBolaY > 10)) {
            posBolaY -= velocidadeBola;
        }
        else {
            direcaoY = 1;
        }
        if((direcaoY == 1) && (posBolaY < (altura - 85))){
            posBolaY += velocidadeBola;
        }
        else{
            direcaoY = 0;
        }
        if(posBolaX > 100 && posBolaX < 600){
            ponto = 0;
        }
        /**
         * Verificando se a bola colidiu com a barra ou parede.
         * Se foi com a parede deve-se aumentar a pontuação do Player Adversário.
         */
        if(posBolaX >= ((largura / 4))){
            verificaBola(bola1, barraB);
        }
        else if(posBolaX <= ((largura / 4))){
            verificaBola(bola1, barraA);
        }
        else {
            ponto = 0;
        }
    }
    
    /**
     * Metodo Drown. Executado a cada ciclo de clock para redesenhar a tela do Game
     * @param graphic 
     */
    @Override
    public void draw(Graphics graphic){
        
        //Inicializando a tela de fundo do game
        background.x = -1;
        background.y = 0;
        background.draw(graphic);
        
        //Escrevendo os nomes dos players na tela
        graphic.setColor(Color.green);
        Font font = new Font("arial", Font.BOLD, 18);
        graphic.setFont(font);
        graphic.drawString("Player A", largura / 2 - 90, 55);
        graphic.drawString("Player B", largura / 2 + 10, 55);
        graphic.drawString(String.valueOf(pontuacaoB), largura / 2 - 60, 75);
        graphic.drawString(String.valueOf(pontuacaoA), largura / 2 + 40 , 75);
        
        //Desenhando a Bola
        bola1.x = posBolaX;
        bola1.y = posBolaY;
        bola1.draw(graphic);
        
        //Desenhando a Barra A
        barraA.x = 13;
        barraA.y = posBarra1;
        barraA.draw(graphic);
        
        //Desenhando a Barra B
        barraB.x = largura-55;
        barraB.y = posBarra2;
        barraB.draw(graphic);
    }

    @Override
    public void load() {
        
    }

    @Override
    public void unload() {
        
    }

    @Override
    public void start() {
        
    }

    @Override
    public void stop() {
        
    }

    /**
     * Verificações das colisões da bola com as Barras
     * @param bola
     * @param barra 
     */
    private void verificaBola(Bola bola, Barra barra) {
        if(barra.x != 13){
            if(( bola.x+40 ) >= (barra.x) ){
                if(( ((bola.y+77) < barra.y+10) || (bola.y > barra.y+90) )){
                    if((bola.x+77+velocidadeBola) >= (largura - velocidadeBola)) {
                        if(ponto == 0) {
                            ponto = 1;
                            pontuacaoB++;
                            if(velocidadeBola < 5) {
                                velocidadeBola++;
                            }
                        }
                    }
                }
                else{
                    direcaoX = 0;
                    if(bola.y+77 < barra.y+30){
                        direcaoY = 0;
                    }
                    if(bola.y > barra.y+70){
                        direcaoY = 1;
                    }
                }
            }
        }
        else {
            if(bola.x-velocidadeBola <= (barra.x + 25)){
                if(( ((bola.y+77) < barra.y+10) || (bola.y > barra.y+90) )){
                    if(bola.x-velocidadeBola <= 10+velocidadeBola) {
                        if(ponto == 0) {
                            ponto = 1;
                            pontuacaoA++;
                            if(velocidadeBola < 5) {
                                velocidadeBola++;
                            }
                        }
                    }
                }
                else{
                    direcaoX = 1;
                    if(bola.y+77 < barra.y+30){
                        direcaoY = 0;
                    }
                    if(bola.y > barra.y+70){
                        direcaoY = 1;
                    }
                }
            }
        }
    }
     
    
}
