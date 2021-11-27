import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Start extends JFrame {
	private Main main;
	private Game game;
	ImageIcon back = new ImageIcon("img\\¹è°æ.jpg");
	Image bi = back.getImage();

	public Start() {

		this.setTitle("Shooting Game");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		backimg panel = new backimg();
        panel.setLayout(new FlowLayout());
		
		JButton btn3 = new JButton("Start Game");
		btn3.addActionListener(new StartGame());
		btn3.setBounds(250, 100 , 50, 80);
		panel.add(btn3);

		/*
		 * JButton btn1 = new JButton("4 key"); btn1.addActionListener(new KeySet());
		 * c.add(btn1); JButton btn2 = new JButton("Normal"); btn2.addActionListener(new
		 * SpeedSet()); c.add(btn2);
		 */
		this.add(panel);
		this.setSize(600, 1000);
		this.setVisible(true);
		//game.Sound("img\\bg.wav", true);

	}

	class backimg extends JPanel {
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(bi, 0, 0, getWidth(), getHeight(), this);

		}
	}

	/*
	 * class KeySet implements ActionListener { public void
	 * actionPerformed(ActionEvent e) { JButton b = (JButton) e.getSource(); if
	 * (b.getText().equals("4 key")) b.setText("8 key"); else b.setText("4 key");
	 * 
	 * }
	 * 
	 * 
	 * }
	 * 
	 * class SpeedSet implements ActionListener { public void
	 * actionPerformed(ActionEvent e) { JButton b = (JButton) e.getSource(); if
	 * (b.getText().equals("Normal")) b.setText("Fast"); else b.setText("Normal");
	 * 
	 * }
	 * 
	 * }
	 */

	class StartGame implements ActionListener {
		Start start;
		Game game;

		public void actionPerformed(ActionEvent e) {
			JButton b = (JButton) e.getSource();
			if (b.getText().equals("Start Game")) {
				main.showGame();
			}
		}
	}

	public void setMain(Main main) {
		this.main = main;
	}

}
