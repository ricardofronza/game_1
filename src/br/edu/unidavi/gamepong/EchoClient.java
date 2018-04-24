package br.edu.unidavi.gamepong;

import br.edu.unidavi.gamepong.Game1;
import java.io.*;
import java.net.*;

/**
 *
 * @author Marcondes Maçaneiro
 */
public class EchoClient {

    public static void main(String[] args) throws IOException {
        
        String serverHostname = new String("127.0.0.1");
        
        if (args.length > 0) {
            serverHostname = args[0];
        }
        System.out.println("Attemping to connect to host "+ serverHostname + " on port 12345.");
        
        Socket echoSocket = new Socket(serverHostname, 12345);
        PrintWriter out = new PrintWriter(echoSocket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
        
        Game1 game = new Game1();
        game.inicia();
        
        while (echoSocket.isConnected()) {
            String envia = game.getJogador().getPosBarra1()+"/"+game.getJogador().getPosBarra2()+"/"+game.getJogador().getPosBolaX()+"/"+game.getJogador().getPosBolaY();
            out.println(envia);
            
            String seta[] = in.readLine().split("/");
            
            game.getJogador().setaPosicoes(Integer.parseInt(seta[0]), Integer.parseInt(seta[1]), Integer.parseInt(seta[2]), Integer.parseInt(seta[3]));
            
            game.GetGameEngine().run();
        }
        
        in.close();
        echoSocket.close();
    }
}
