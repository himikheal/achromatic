import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.nio.file.Files;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class CustomLevelScreen extends ScreenAdapter {

  ColourGame game;
  ShapeRenderer sr;
  String code = "";
  Texture[] letters = new Texture[26];
  Sprite[] letterSprites = new Sprite[26];
  Texture playButton = new Texture("assets/sprites/playButton.png");
  Sprite playButtonSprite = new Sprite(playButton);
  private Socket socket;
  private ObjectInputStream input;
  private ObjectOutputStream output;

  public CustomLevelScreen(ColourGame game) {
    this.game = game;
  }

  @Override
  public void show() {
    playButtonSprite.setSize(174, 78);
    playButtonSprite.setPosition((Gdx.graphics.getWidth() / 2) - (playButtonSprite.getWidth() / 2), (Gdx.graphics.getHeight() / 2) - 146);
    sr = new ShapeRenderer();
    for(int i = 0; i < 26; i++) {
      letters[i] = new Texture("assets/sprites/text_" + (char)(i + 97) + ".png");
      letterSprites[i] = new Sprite(letters[i]);
      letterSprites[i].setSize(64, 64);
    }
  }

  @Override
  public void render(float delta) {
    Gdx.gl.glClearColor(0, 0, 0, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    if(code.length() < 5) {
      if(Gdx.input.isKeyJustPressed(Keys.A)) {
        code = code + "A";
      }
      else if(Gdx.input.isKeyJustPressed(Keys.B)) {
        code = code + "B";
      }
      else if(Gdx.input.isKeyJustPressed(Keys.C)) {
        code = code + "C";
      }
      else if(Gdx.input.isKeyJustPressed(Keys.D)) {
        code = code + "D";
      }
      else if(Gdx.input.isKeyJustPressed(Keys.E)) {
        code = code + "E";
      }
      else if(Gdx.input.isKeyJustPressed(Keys.F)) {
        code = code + "F";
      }
      else if(Gdx.input.isKeyJustPressed(Keys.G)) {
        code = code + "G";
      }
      else if(Gdx.input.isKeyJustPressed(Keys.H)) {
        code = code + "H";
      }
      else if(Gdx.input.isKeyJustPressed(Keys.I)) {
        code = code + "I";
      }
      else if(Gdx.input.isKeyJustPressed(Keys.J)) {
        code = code + "J";
      }
      else if(Gdx.input.isKeyJustPressed(Keys.K)) {
        code = code + "K";
      }
      else if(Gdx.input.isKeyJustPressed(Keys.L)) {
        code = code + "L";
      }
      else if(Gdx.input.isKeyJustPressed(Keys.M)) {
        code = code + "M";
      }
      else if(Gdx.input.isKeyJustPressed(Keys.N)) {
        code = code + "N";
      }
      else if(Gdx.input.isKeyJustPressed(Keys.O)) {
        code = code + "O";
      }
      else if(Gdx.input.isKeyJustPressed(Keys.P)) {
        code = code + "P";
      }
      else if(Gdx.input.isKeyJustPressed(Keys.Q)) {
        code = code + "Q";
      }
      else if(Gdx.input.isKeyJustPressed(Keys.R)) {
        code = code + "R";
      }
      else if(Gdx.input.isKeyJustPressed(Keys.S)) {
        code = code + "S";
      }
      else if(Gdx.input.isKeyJustPressed(Keys.T)) {
        code = code + "T";
      }
      else if(Gdx.input.isKeyJustPressed(Keys.U)) {
        code = code + "U";
      }
      else if(Gdx.input.isKeyJustPressed(Keys.V)) {
        code = code + "V";
      }
      else if(Gdx.input.isKeyJustPressed(Keys.W)) {
        code = code + "W";
      }
      else if(Gdx.input.isKeyJustPressed(Keys.X)) {
        code = code + "X";
      }
      else if(Gdx.input.isKeyJustPressed(Keys.Y)) {
        code = code + "Y";
      }
      else if(Gdx.input.isKeyJustPressed(Keys.Z)) {
        code = code + "Z";
      }
    }
    if(!code.equals("") && Gdx.input.isKeyJustPressed(Keys.BACKSPACE)) {
      code = code.substring(0, code.length() - 1);
    }
    else if(Gdx.input.getX() > playButtonSprite.getX()
         && Gdx.input.getX() < playButtonSprite.getX() + playButtonSprite.getWidth()
         && Gdx.input.getY() < Gdx.graphics.getHeight() - playButtonSprite.getY()
         && Gdx.input.getY() > (Gdx.graphics.getHeight() - playButtonSprite.getY()) - playButtonSprite.getHeight()
         && Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
      System.out.println("CLICKE D");
      connect("127.0.0.1", 5000);
      try {
        output.writeObject("/DOWNLOAD!");
        output.writeObject(code);
        if((boolean)input.readObject()) {
          File downloadMap = new File("assets/levels/downloadTempFile.txt");
          byte[] fileBytes = (byte[])input.readObject();
          Files.write(downloadMap.toPath(), fileBytes);
          game.setScreen(new GameScreen(game, "downloadTempFile.txt"));
        }
        else {
          System.out.println("Invalid Code!");
        }
        
        input.close();
        output.close();
        socket.close();
      } catch(IOException e) {
        System.out.println("IOException occured");
        e.printStackTrace();
      } catch(ClassNotFoundException e2) {
        System.out.println("Class not found");
      }

    }


    sr.begin(ShapeType.Line);
    sr.rect((Gdx.graphics.getWidth() / 2) - 212, (Gdx.graphics.getHeight() / 2) - 52, 424, 104);
    sr.end();

    game.batch.begin();
    playButtonSprite.draw(game.batch);
    for(int i = 0; i < code.length(); i++) {
      char c = code.charAt(i);
      letterSprites[((int)c) - 65].setPosition(((Gdx.graphics.getWidth() / 2) - 192) + (16 * i) + (64 * i), (Gdx.graphics.getHeight() / 2) - 32);
      letterSprites[((int)c) - 65].draw(game.batch);
    }
    game.batch.end();
  }

  @Override
  public void hide() {
    
  }

  public Socket connect(String ip, int port) {
    System.out.println("Attempting to make a connection..");

    try {

      socket = new Socket("127.0.0.1", 5000); //attempt socket connection (local address). This will wait until a connection is made
      output = new ObjectOutputStream(socket.getOutputStream());
      input = new ObjectInputStream(socket.getInputStream());
      
    } catch (IOException e) {  //connection error occured
      System.out.println("Connection to Server Failed");
      e.printStackTrace();
    }
    
    System.out.println("Connection made.");
    return socket;
  }
}