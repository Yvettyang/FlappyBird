package flappybird.yvettyang;
import java.awt.*;
import sun.audio.*;
import javax.swing.JPanel;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class Game extends JPanel {/*游戏运行类*/
	// 该变量的值将被JVM产生和用于序列化和对象的反序列化，解决兼容问题
	private static final long serialVersionUID = 1L;
	// 声明静态域存储图片
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
	// 声明游戏状态参数
	int state;
	public int score;
	public static final int START = 0;
	public static final int RUNNING = 1;
	public static final int GAME_OVER = 2;
	// 声明对象变量
	Bird bird;
	Ground ground;
	Column column1,column2,column3,column4;
	
	Game(){/*构造方法 初始化各个变量*/
		// 初始化游戏状态参数
		score = 0;
		state = 0;
		// 实例化对象
		bird = new Bird();
		ground = new Ground();
		column1 = new Column(1);
		column2 = new Column(2);
		column3 = new Column(3);
		column4 = new Column(4);
	}
	
	public void paint(Graphics g){/*重写paint方法*/
		// 画画面移动要素
		g.drawImage(background, 0, 0, null);
		g.drawImage(column1.image, column1.x-column1.width/2, column1.y-column1.height/2, null);
		g.drawImage(column2.image, column2.x-column2.width/2, column2.y-column2.height/2, null);
		g.drawImage(column3.image, column3.x-column3.width/2, column3.y-column3.height/2, null);
		g.drawImage(column4.image, column4.x-column4.width/2, column4.y-column4.height/2, null);
		g.drawImage(ground.image, ground.x, ground.y, null);// 将第一个参数创建到面板上的坐标位置
		// 画小鸟动画2D效果
		Graphics2D g2 = (Graphics2D) g;
		g.drawImage(bird.image, bird.x-bird.width/2, bird.y-bird.height/2, null);
		g2.rotate(-bird.alpha, bird.x, bird.y);// 旋转坐标轴
		g.drawImage(bird.image, bird.x-bird.width/2, bird.y-bird.height/2, null);
		g2.rotate(bird.alpha, bird.x, bird.y);
		// 画状态提示界面
		switch(state){
		case 2:// 游戏结束状态
			g.drawImage(gameOver, 0, 0, null);
			break;
		case 0:// 游戏初始状态
			g.drawImage(start, 0, 0, null);
			break;
		}
		// 画实时分数
		paintScore(g);// 调用画分数方法
	}
	
	public void move(){/*游戏画面移动方法*/
		new Thread(new Runnable(){// 在方法内创建新线程
			public void run(){// 重写类的run方法
				while(true){// 始终无限循环以下语句
					switch(state){
					case 0:// 游戏初始状态
						bird.fly();// 调用小鸟飞行动画效果
						ground.step();// 调用地面后退方法
						break;// 终止循环
					case 1:// 游戏运行状态
						bird.fly();
						ground.step();
						column1.step();
						column2.step();
						column3.step();
						column4.step();// 调用柱子后退方法
						bird.step();// 调用小鸟垂直位移方法
						score();// 调用计分方法
						birdHit();// 调用检测碰撞方法
						break;
					}
					repaint();// 重画游戏画面
					try {// 每秒处理90次线程在睡眠、等待或因其他原因暂停时被其他线程打断的错误，实现运行流畅
						Thread.sleep(1000/90);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
				}
			}
		}).start();// 启动线程并执行run方法
	}
	
	public void birdHit(){/*检测是否碰撞*/
		if(bird.hit(column1) || bird.hit(column2) || bird.hit(column3) || bird.hit(column4) || bird.hit(ground)){// 小鸟撞击当前面板内任一柱子或地面
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
			state = 2;// 游戏状态切换为结束
		}
	}
	
	public void score(){/*计分方法*/
		if(bird.x == column1.x || bird.x == column2.x || bird.x == column3.x || bird.x == column4.x){// 小鸟成功通过当前面板内柱子
			score++;// 分数累加
		}
	}
	
	public void paintScore(Graphics g){/*画分数*/
		int x=50, y=80;// 定义坐标参数变量
		g.setColor(new Color(0xFFFFFF));// 设置颜色为0xFFFFFF
		g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 50));// 设置字体为SANS_SERIF，粗体，50号
		g.drawString("" + score, x, y);// 在(x,y)坐标处画分数
	}
	
	static{/*静态块加载资源*/
		try {// 获取路径并读取资源
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
		} catch (Exception e2) {// 捕捉异常
			e2.printStackTrace();// 输出当前异常对象的堆栈使用轨迹
		}
	}
	
}