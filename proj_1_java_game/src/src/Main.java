import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class Main {
	Game game;
	Start start;

	public static void main(String[] args) {
		Main main = new Main();
		main.start = new Start();
		main.start.setMain(main);
	}

	public void showGame() {
		start.dispose();
		Thread t = new Thread(new Game());
		t.start();		
		
	}

}
