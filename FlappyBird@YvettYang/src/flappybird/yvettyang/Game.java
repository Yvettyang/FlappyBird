package flappybird.yvettyang;
import java.awt.*;
import sun.audio.*;
import javax.swing.JPanel;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class Game extends JPanel {/*��Ϸ������*/
	// �ñ�����ֵ����JVM�������������л��Ͷ���ķ����л��������������
	private static final long serialVersionUID = 1L;
	// ������̬��洢ͼƬ
	public static BufferedImage background;
	public static BufferedImage start;
	public static BufferedImage gameOver;
	public static BufferedImage bird0;
	public static BufferedImage bird1;
	public static BufferedImage bird2;
	public static BufferedImage bird3;
	public static BufferedImage bird4;
	public static BufferedImage bird5;
	public static BufferedImage bird6;
	public static BufferedImage bird7;
	public static BufferedImage groundImage;
	public static BufferedImage column;
	// ������Ϸ״̬����
	int state;
	public int score;
	public static final int START = 0;
	public static final int RUNNING = 1;
	public static final int GAME_OVER = 2;
	// �����������
	Bird bird;
	Ground ground;
	Column column1,column2,column3,column4;
	
	Game(){/*���췽�� ��ʼ����������*/
		// ��ʼ����Ϸ״̬����
		score = 0;
		state = 0;
		// ʵ��������
		bird = new Bird();
		ground = new Ground();
		column1 = new Column(1);
		column2 = new Column(2);
		column3 = new Column(3);
		column4 = new Column(4);
	}
	
	public void paint(Graphics g){/*��дpaint����*/
		// �������ƶ�Ҫ��
		g.drawImage(background, 0, 0, null);
		g.drawImage(column1.image, column1.x-column1.width/2, column1.y-column1.height/2, null);
		g.drawImage(column2.image, column2.x-column2.width/2, column2.y-column2.height/2, null);
		g.drawImage(column3.image, column3.x-column3.width/2, column3.y-column3.height/2, null);
		g.drawImage(column4.image, column4.x-column4.width/2, column4.y-column4.height/2, null);
		g.drawImage(ground.image, ground.x, ground.y, null);// ����һ����������������ϵ�����λ��
		// ��С�񶯻�2DЧ��
		Graphics2D g2 = (Graphics2D) g;
		g.drawImage(bird.image, bird.x-bird.width/2, bird.y-bird.height/2, null);
		g2.rotate(-bird.alpha, bird.x, bird.y);// ��ת������
		g.drawImage(bird.image, bird.x-bird.width/2, bird.y-bird.height/2, null);
		g2.rotate(bird.alpha, bird.x, bird.y);
		// ��״̬��ʾ����
		switch(state){
		case 2:// ��Ϸ����״̬
			g.drawImage(gameOver, 0, 0, null);
			break;
		case 0:// ��Ϸ��ʼ״̬
			g.drawImage(start, 0, 0, null);
			break;
		}
		// ��ʵʱ����
		paintScore(g);// ���û���������
	}
	
	public void move(){/*��Ϸ�����ƶ�����*/
		new Thread(new Runnable(){// �ڷ����ڴ������߳�
			public void run(){// ��д���run����
				while(true){// ʼ������ѭ���������
					switch(state){
					case 0:// ��Ϸ��ʼ״̬
						bird.fly();// ����С����ж���Ч��
						ground.step();// ���õ�����˷���
						break;// ��ֹѭ��
					case 1:// ��Ϸ����״̬
						bird.fly();
						ground.step();
						column1.step();
						column2.step();
						column3.step();
						column4.step();// �������Ӻ��˷���
						bird.step();// ����С��ֱλ�Ʒ���
						score();// ���üƷַ���
						birdHit();// ���ü����ײ����
						break;
					}
					repaint();// �ػ���Ϸ����
					try {// ÿ�봦��90���߳���˯�ߡ��ȴ���������ԭ����ͣʱ�������̴߳�ϵĴ���ʵ����������
						Thread.sleep(1000/90);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
				}
			}
		}).start();// �����̲߳�ִ��run����
	}
	
	public void birdHit(){/*����Ƿ���ײ*/
		if(bird.hit(column1) || bird.hit(column2) || bird.hit(column3) || bird.hit(column4) || bird.hit(ground)){// С��ײ����ǰ�������һ���ӻ����
			new Thread(new Runnable(){
				public void run(){
					try {
						AudioStream hit = new AudioStream(getClass().getResourceAsStream("audio/hit.au"));
						AudioPlayer.player.start(hit);
					} catch(IOException e) {
						e.printStackTrace();
					}
				}
			}).start();
			state = 2;// ��Ϸ״̬�л�Ϊ����
		}
	}
	
	public void score(){/*�Ʒַ���*/
		if(bird.x == column1.x || bird.x == column2.x || bird.x == column3.x || bird.x == column4.x){// С��ɹ�ͨ����ǰ���������
			score++;// �����ۼ�
		}
	}
	
	public void paintScore(Graphics g){/*������*/
		int x=50, y=80;// ���������������
		g.setColor(new Color(0xFFFFFF));// ������ɫΪ0xFFFFFF
		g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 50));// ��������ΪSANS_SERIF�����壬50��
		g.drawString("" + score, x, y);// ��(x,y)���괦������
	}
	
	static{/*��̬�������Դ*/
		try {// ��ȡ·������ȡ��Դ
			background = ImageIO.read(Game.class.getResource("image/background.png"));
			gameOver = ImageIO.read(Game.class.getResource("image/gameover.png"));
			start = ImageIO.read(Game.class.getResource("image/start.png"));
			column =ImageIO.read(Game.class.getResource("image/column.png"));
			groundImage = ImageIO.read(Game.class.getResource("image/ground.png"));
			bird0 = ImageIO.read(Game.class.getResource("image/bird0.png"));
			bird1 = ImageIO.read(Game.class.getResource("image/bird1.png"));
			bird2 = ImageIO.read(Game.class.getResource("image/bird2.png"));
			bird3 = ImageIO.read(Game.class.getResource("image/bird3.png"));
			bird4 = ImageIO.read(Game.class.getResource("image/bird4.png"));
			bird5 = ImageIO.read(Game.class.getResource("image/bird5.png"));
			bird6 = ImageIO.read(Game.class.getResource("image/bird6.png"));
			bird7 = ImageIO.read(Game.class.getResource("image/bird7.png"));
		} catch (Exception e2) {// ��׽�쳣
			e2.printStackTrace();// �����ǰ�쳣����Ķ�ջʹ�ù켣
		}
	}
	
}