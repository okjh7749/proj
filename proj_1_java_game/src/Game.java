import java.awt.*;
import java.awt.Font.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.BufferedInputStream;
import java.io.FileInputStream;

public class Game extends JFrame implements Runnable, KeyListener {
	// 화면 엮기
	private Main main;
	private Start start;
	// 기본적인 게임 설정
	private boolean left = false, right = false, up = false, down = false, fire = false, boom = false;
	private boolean start1 = false, end = false;
	private int w = 600, h = 1000, x = 130, y = 450, myw = 80, myh = 80;
	int mywp = 3;
	int score = 0;
	int Move_Speed = 3;
	int itemn = 8;
	int boomn = 3;
	int power = 0;
	int[] cx = { 0, 0, 0 };
	int bx = 0;
	// 이미지 넣기
	private BufferedImage bi = null;
	private ArrayList shootList = null;
	private ArrayList shootenList = null;
	private ArrayList boomList = null;
	private ArrayList enshootList = null;
	private ArrayList enList = null;
	private ArrayList enList2 = null;
	private ArrayList enList3 = null;
	private ArrayList itList = null;
	Graphics buffg;
	Toolkit tk = Toolkit.getDefaultToolkit();
	Image Enemy_img;
	Image Enemy2_img;
	Image Enemy3_img;
	Image Me_img;
	Image Back_img;
	Image Item_img;
	Image ms_img;
	Image enms_img;
	Image boom1_img;
	Image boom2_img;

	public Game() {
		bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		shootList = new ArrayList();
		shootenList = new ArrayList();
		enList = new ArrayList();
		enList2 = new ArrayList();
		enList3 = new ArrayList();
		itList = new ArrayList();
		boomList = new ArrayList();
		this.addKeyListener(this);
		this.setSize(w, h);
		this.setTitle("Shooting Game");
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		Enemy_img = tk.getImage("img\\en.png");
		Enemy2_img = tk.getImage("img\\en2.png");
		Enemy3_img = tk.getImage("img\\en.png");
		Me_img = tk.getImage("img\\me1.png");
		Back_img = tk.getImage("img\\sky.jpg");
		Item_img = tk.getImage("img\\hpitem.png");
		ms_img = tk.getImage("img\\ms1.png");
		enms_img = tk.getImage("img\\enms.png");
		boom1_img = tk.getImage("img\\boom3.png");
		boom2_img = tk.getImage("img\\boom4.png");
	}



	public void Sound(String file, boolean Loop) {

		Clip clip;

		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(new BufferedInputStream(new FileInputStream(file)));
			clip = AudioSystem.getClip();
			clip.open(ais);
			clip.start();
			if (Loop)
				clip.loop(-1);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void run() { // 쓰레드 실행코드 외부 코딩 참고
		try {
			int msCnt = 0;
			int enCnt = 0;
			int enmscnt = 0;
			int boomcnt = 0;
			while (true) {
				Thread.sleep(10);

				if (start1) {
					if (enCnt > 200) {
						en2Create();
						enCreate();
						enCnt = 0;
					}
					if (msCnt >= 15) {
						fireMs();
						fireBoom();
						msCnt = 0;
					}
					if (enmscnt >= 130) {
						fireenMs();
						enmscnt = 0;
					}
					if (boomcnt >= 30) {

						boomcnt = 0;
					}
					enmscnt++;
					boomcnt++;
					msCnt += 1;
					enCnt += 1;
					keyControl();
					crashChk();
				}

				draw();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void fireMs() { // 미사일 발사 위치
		if (fire) {
			if (power == 0) {
				if (shootList.size() < 100) {
					Ms m = new Ms(this.x - myw + 55, this.y);
					shootList.add(m);
					Sound("img\\mfire.wav", false);
				}
			}
			if (power == 1) {
				if (shootList.size() < 100) {
					Ms m = new Ms(this.x +15 - myw, this.y);
					shootList.add(m);
					Ms m2 = new Ms(this.x + 17, this.y);
					shootList.add(m2);
					Sound("img\\mfire.wav", false);
				}
			}
		}
	}

	public void fireBoom() { // 미사일 발사 위치
		if (boom) {
			if (boomn > 0) {
				Boom b = new Boom(w / 2 - 30, h);
				boomList.add(b);
				boomn--;
			}
		}
	}

	public void enCreate() { // 적 생성위치
		for (int i = 0; i < 8; i++) {
			double rx = Math.random() * (w - myw);
			double ry = Math.random() * 50;
			Enemy en = new Enemy((int) rx, (int) ry);
			enList.add(en);
		}
	}

	public void en2Create() { // 적2 생성위치(미사일발사)
		int n;
		n = (int) (Math.random() * 4);
		for (int i = 0; i < n; i++) {
			double rx = Math.random() * (w - myw);
			double ry = Math.random() * 50;
			Enemy2 en = new Enemy2((int) rx, (int) ry);
			enList2.add(en);
		}
	}

	public void fireenMs() { // 적 미사일 발사 위치
		for (int j = 0; j < enList2.size(); j++) {
			Enemy2 en = (Enemy2) enList2.get(j);
			enMs m = new enMs(en.x - 25, en.y + 70, 330);
			shootenList.add(m);
			enMs m2 = new enMs(en.x - 25, en.y + 70, 360);
			shootenList.add(m2);
			enMs m3 = new enMs(en.x - 25, en.y + 70, 390);
			shootenList.add(m3);
		}

	}

	public void en3Create() { // 적3 생성위치(나를 유도)
		int n;
		n = (int) (Math.random() * 2);
		for (int i = 0; i < n; i++) {
			double rx = Math.random() * (w - myw);
			double ry = Math.random() * (h - myh);
			Enemy en = new Enemy((int) rx, (int) ry);//////////////////////////////////
			enList3.add(en);
		}
	}

	public void itCreate(int x, int y) {
		Item it = new Item(x, y);
		itList.add(it);

	}

	public void crashChk() { // 충돌 체크
		Graphics g = this.getGraphics();
		Polygon p = null;
		for (int i = 0; i < shootList.size(); i++) { // 적이 미사일에 충돌시
			Ms m = (Ms) shootList.get(i);
			for (int j = 0; j < enList.size(); j++) {
				Enemy e = (Enemy) enList.get(j);

				int[] xpoints = { m.x, (m.x + m.w), (m.x + m.w), m.x };
				int[] ypoints = { m.y, m.y, (m.y + m.h), (m.y + m.h) };
				p = new Polygon(xpoints, ypoints, 4);
				if (p.intersects((double) e.x, (double) e.y, (double) e.w, (double) e.h)) {
					shootList.remove(i);
					e.hp--;
					Sound("img\\explo.wav", false);
				}
				if (p.intersects((double) e.x, (double) e.y, (double) e.w, (double) e.h) && e.hp == 0) {
					itemn--;
					if (itemn == 0) {
						itCreate(e.x, e.y);
						itemn = 8;
					}
					enList.remove(j);
					score += 10;
					e.hp = 2;
				}
			}
		}
		for (int i = 0; i < boomList.size(); i++) { // 적1 이 폭탄에 충돌시
			Boom b = (Boom) boomList.get(i);
			for (int j = 0; j < enList.size(); j++) {
				Enemy e = (Enemy) enList.get(j);

				int[] xpoints = { b.x, (b.x + b.w), (b.x + b.w), b.x };
				int[] ypoints = { b.y, b.y, (b.y + b.h), (b.y + b.h) };
				p = new Polygon(xpoints, ypoints, 4);
				if (p.intersects((double) e.x, (double) e.y, (double) e.w, (double) e.h)) {
					enList.remove(j);
					score += 10;
				}
			}
		}
		for (int i = 0; i < boomList.size(); i++) { // 적2가 폭탄에 충돌시
			Boom b = (Boom) boomList.get(i);
			for (int j = 0; j < enList2.size(); j++) {
				Enemy2 en = (Enemy2) enList2.get(j);
				int[] xpoints = { b.x, (b.x + b.w), (b.x + b.w), b.x };
				int[] ypoints = { b.y, b.y, (b.y + b.h), (b.y + b.h) };
				p = new Polygon(xpoints, ypoints, 4);
				if (p.intersects((double) en.x, (double) en.y, (double) en.w, (double) en.h)) {
					enList2.remove(j);
					score += 50;
				}
			}
		}
		for (int i = 0; i < boomList.size(); i++) { // 적2가 폭탄에 충돌시
			Boom b = (Boom) boomList.get(i);
			for (int j = 0; j < shootenList.size(); j++) {
				enMs m = (enMs) shootenList.get(j);
				int[] xpoints = { b.x, (b.x + b.w), (b.x + b.w), b.x };
				int[] ypoints = { b.y, b.y, (b.y + b.h), (b.y + b.h) };
				p = new Polygon(xpoints, ypoints, 4);
				if (p.intersects((double) m.x, (double) m.y, (double) m.w, (double) m.h)) {
					shootenList.remove(j);
				}
			}
		}
		for (int i = 0; i < shootList.size(); i++) { // 적2가 미사일에 충돌시
			Ms m = (Ms) shootList.get(i);
			for (int j = 0; j < enList2.size(); j++) {
				Enemy2 en = (Enemy2) enList2.get(j);
				int[] xpoints = { m.x, (m.x + m.w), (m.x + m.w), m.x };
				int[] ypoints = { m.y, m.y, (m.y + m.h), (m.y + m.h) };
				p = new Polygon(xpoints, ypoints, 4);
				if (p.intersects((double) en.x, (double) en.y, (double) en.w, (double) en.h)) {
					shootList.remove(i);
					en.hp--;
					Sound("img\\explo.wav", false);
				}

				if (p.intersects((double) en.x, (double) en.y, (double) en.w, (double) en.h) && en.hp == 0) {
					itemn -= 4;
					if (itemn < 0) {
						itCreate(en.x, en.y);
						itemn = 8;
					}
					enList2.remove(j);
					score += 50;
					en.hp = 5;
				}
			}
		}
		for (int i = 0; i < enList.size(); i++) { // 내가 적에게 충돌시
			Enemy e = (Enemy) enList.get(i);
			int[] xpoints = { x, (x + myw), (x + myw), x };
			int[] ypoints = { y, y, (y + myh), (y + myh) };
			p = new Polygon(xpoints, ypoints, 4);
			if (p.intersects((double) e.x, (double) e.y, (double) e.w, (double) e.h)) {
				enList.remove(i);
				mywp--;
				Sound("img\\explo.wav", false);
			}

			if (mywp == 0) {
				start1 = false;
				end = true;
				mywp = 3;
				score = 0;
				boomn = 3;
			}
		}
		for (int i = 0; i < enList2.size(); i++) { // 내가 적2에게 충돌시
			Enemy2 en = (Enemy2) enList2.get(i);
			int[] xpoints = { x, (x + myw), (x + myw), x };
			int[] ypoints = { y, y, (y + myh), (y + myh) };
			p = new Polygon(xpoints, ypoints, 4);
			if (p.intersects((double) en.x, (double) en.y, (double) en.w, (double) en.h)) {
				shootList.remove(i);
				mywp--;
				Sound("img\\explo.wav", false);
			}

			if (mywp == 0) {
				start1 = false;
				end = true;
				mywp = 3;
				score = 0;
			}
		}
		for (int i = 0; i < shootenList.size(); i++) { // 내가 적 미사일에 충돌시
			enMs m = (enMs) shootenList.get(i);
			int[] xpoints = { x, (x + myw), (x + myw), x };
			int[] ypoints = { y, y, (y + myh), (y + myh) };
			p = new Polygon(xpoints, ypoints, 4);
			if (p.intersects((double) m.x, (double) m.y, (double) m.w, (double) m.h)) {
				shootenList.remove(i);
				mywp--;
				power = 0;
				Sound("img\\explo.wav", false);
				if (mywp == 0) {
					start1 = false;
					end = true;
					mywp = 3;
					score = 0;
				}
			}

		}
		for (int i = 0; i < itList.size(); i++) { // 아이템을 먹었을때
			Item e = (Item) itList.get(i);
			int[] xpoints = { x, (x + myw), (x + myw), x };
			int[] ypoints = { y, y, (y + myh), (y + myh) };
			p = new Polygon(xpoints, ypoints, 4);
			if (p.intersects((double) e.x, (double) e.y, (double) e.iw, (double) e.ih)) {
				itList.remove(i);
				if (mywp != 3)
					mywp++;
				if (boomn != 3)
					boomn++;
				if (power == 0)
					power = 1;
			}

		}

	}

	public void draw() {
		Graphics gs = bi.getGraphics();
		gs.setColor(Color.white);
		gs.fillRect(0, 0, w, h);
		Draw_Back(gs);
		gs.setColor(Color.white);
		gs.drawString("----H P---- : " + (mywp), 480, 50);
		gs.drawString("---Score--- : " + score, 480, 70);
		gs.drawString("----Boom--- : " + boomn, 480, 90);
		gs.drawString("게임시작 : Enter", 480, 110);
		if (end) {
			gs.setFont(new Font("serif", Font.BOLD, 25));
			gs.setColor(Color.red);
			gs.drawString("G A M E     O V E R", 250, 450);
		}
		Draw_ms(gs);
		Draw_Me(gs);
		Draw_Enemy(gs);
		Draw_Enemy2(gs);
		Draw_enms(gs);
		Draw_boom(gs);
		Draw_Item(gs);
		Graphics ge = this.getGraphics();
		ge.drawImage(bi, 0, 0, w, h, this);
	}

	public void Draw_ms(Graphics g) { // 미사일을 그리는 부분
		for (int i = 0; i < shootList.size(); i++) {
			Ms m = (Ms) shootList.get(i);

			g.drawImage(ms_img, m.x, m.y, this);
			if (m.y < 0)
				shootList.remove(i);
			m.moveMs();
		}
	}

	public void Draw_enms(Graphics g) { // 적 미사일을 그리는 부분
		for (int i = 0; i < shootenList.size(); i++) {
			enMs m = (enMs) shootenList.get(i);
			g.drawImage(enms_img, m.x, m.y, this);
			if (m.y > h)
				shootenList.remove(i);
			m.moveMs();
		}
	}

	public void Draw_boom(Graphics g) { // 미사일을 그리는 부분
		for (int i = 0; i < boomList.size(); i++) {
			Boom b = (Boom) boomList.get(i);
			g.drawImage(boom1_img, b.x, b.y, this);
			if (b.y <= 400) {
				g.drawImage(boom2_img, b.x, b.y, this);
			}
			if (b.y == 0) {
				boomList.remove(i);
			}
			b.moveboom();
		}
	}

	public void Draw_Enemy(Graphics g) { // 적 이미지를 그리는 부분
		for (int i = 0; i < enList.size(); i++) {
			Enemy e = (Enemy) enList.get(i);
			g.drawImage(Enemy_img, e.x, e.y, this);
			if (e.y > h)
				enList.remove(i);
			e.moveEn();
		}
	}

	public void Draw_Enemy2(Graphics g) { // 적2 이미지를 그리는 부분
		for (int i = 0; i < enList2.size(); i++) {
			Enemy2 e = (Enemy2) enList2.get(i);
			g.drawImage(Enemy2_img, e.x, e.y, this);

			e.moveEn2();
		}
	}

	public void Draw_Enemy3(Graphics g) { // 적3 이미지를 그리는 부분
		for (int i = 0; i < enList3.size(); i++) {
			Enemy e = (Enemy) enList3.get(i);
			g.drawImage(Enemy3_img, e.x, e.y, this);
			if (e.y > h)
				enList3.remove(i);
			e.moveEn();
		}
	}

	public void Draw_Item(Graphics g) { // 아이템 이미지를 그리는 부분
		for (int i = 0; i < itList.size(); i++) {
			Item e = (Item) itList.get(i);
			g.drawImage(Item_img, e.x, e.y, this);
			e.moveIt();
		}
	}

	public void Draw_Me(Graphics g) {// 나를 그리는 부분
		g.drawImage(Me_img, x, y, myw, myh, this);
	}

	public void Draw_Back(Graphics g) {// 배경화면을 그려주는 부분

		if (bx > -1000) {
			g.drawImage(Back_img, 0, bx, this);
			bx -= 1;

		} else {
			bx = 0;
		}
	}

	public void keyControl() { // 이동 제어
		if (0 < x) {
			if (left)
				x -= Move_Speed;
		}
		if (w > x + myw) {
			if (right)
				x += Move_Speed;
		}
		if (25 < y) {
			if (up)
				y -= Move_Speed;
		}
		if (h > y + myh) {
			if (down)
				y += Move_Speed;
		}
	}

	public void keyPressed(KeyEvent ke) { // 키를 누르고 있을때
		switch (ke.getKeyCode()) {
		case KeyEvent.VK_LEFT:
			left = true;
			break;
		case KeyEvent.VK_RIGHT:
			right = true;
			break;
		case KeyEvent.VK_UP:
			up = true;
			break;
		case KeyEvent.VK_DOWN:
			down = true;
			break;
		case KeyEvent.VK_SPACE:
			fire = true;
			break;
		case KeyEvent.VK_ENTER:
			start1 = true;
			end = false;
			break;
		case KeyEvent.VK_A:
			boom = true;
			break;
		}
	}

	public void keyReleased(KeyEvent ke) { // 키를 땟을때
		switch (ke.getKeyCode()) {
		case KeyEvent.VK_LEFT:
			left = false;
			break;
		case KeyEvent.VK_RIGHT:
			right = false;
			break;
		case KeyEvent.VK_UP:
			up = false;
			break;
		case KeyEvent.VK_DOWN:
			down = false;
			break;
		case KeyEvent.VK_SPACE:
			fire = false;
			break;
		case KeyEvent.VK_A:
			boom = false;
			break;
		}
	}

	public void keyTyped(KeyEvent ke) {
	}

	class Ms { // 미사일 객체
		int x;
		int y;
		int w = 50;
		int h = 50;

		public Ms(int x, int y) {
			this.x = x + (myw / 2);
			this.y = y;
		}

		public void moveMs() {
			this.y -= 3;
		}
	}

	class enMs { // 미사일 객체
		int x;
		int y;
		int w = 30;
		int h = 30;
		int angle;

		public enMs(int x, int y, int angle) {
			this.x = x + (90 / 2);
			this.y = y;
			this.angle = angle;
		}

		public void moveMs() {
			x += Math.sin(Math.toRadians(angle)) * 3;
			y += Math.cos(Math.toRadians(angle)) * 3;
		}
	}

	class Enemy { // 적 객체
		int x;
		int y;
		int w =70;
		int h = 70;
		int hp = 2;

		public Enemy(int x, int y) {
			this.x = x;
			this.y = y;
		}

		public void moveEn() {
			y=y+3;
		}
	}

	class Enemy2 {
		int x;
		int y;
		int w = 90;
		int h = 90;
		int stop = 150;
		int stopn = 0;
		int hp = 8;

		public Enemy2(int x, int y) {
			this.x = x;
			this.y = y;
		}

		public void moveEn2() {
			if (stopn == 0) {
				stop = (int) (Math.random() * 150) + 50;
				stopn++;
			}
			if (y < stop)
				y++;

		}

	}

	class Enemy3 {
		int x;
		int y;
		int w = 40;
		int h = 40;
		int hp = 1;

		public Enemy3(int x, int y) {
			this.x = x;
			this.y = y;
		}

		public void moveEn(int x, int y) {///////////////////////
			x += (this.x - x);
		}

	}

	class Boom { // 아이템
		int x;
		int y;
		int w = 50;
		int h = 50;

		public Boom(int x, int y) {
			this.x = x;
			this.y = y;
		}

		public void moveboom() {

			if (y > 400) {
				y -= 3;
			} else if (y <= 400 && y > 300) {
				this.x -= 150;
				this.y -= 150;
				this.w = 400;
				this.h = 400;
			}
			if (this.y + 100 > y) {
				y--;
			}

		}
	}

	class Item { // 아이템
		int x;
		int y;
		int iw = 10;
		int ih = 10;
		int rx = 1;
		int ry = 2;

		public Item(int x, int y) {
			this.x = x;
			this.y = y;
		}

		public void moveIt() {

			x += rx;
			y += ry;

			if (x < 0 || w < x + iw || y < 25 || h < ih + y) {
				rx = (int) (Math.random() * 10) - 4;
				ry = (int) (Math.random() * 10) - 4;
			}

		}
	}
}