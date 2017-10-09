package flappybird.yvettyang;
import java.awt.event.*;
import javax.swing.JFrame;

public class Flappybird {/*游戏界面类*/
	// 声明最后的静态私有类变量设置窗体长宽
	public static final int WIDTH = 1366;
	public static final int HEIGHT = 768;
	// 声明实例变量
	JFrame frame;
	Game game;
	
	Flappybird(String frmTitle){/*构造方法*/
		frame = new JFrame(frmTitle);// 构造窗体对象
	}
	
	public static void main(String args[]){/*主函数*/
		Flappybird yyw = new Flappybird("FlappyBird@YvettYang");// 实例化游戏窗体对象并指定标题
		yyw.init();// 调用初始化方法
	}
	
	public void init(){/*初始化方法*/
		game = new Game();// 实例化游戏运行对象
		frame.add(game);// 将游戏运行面板添加到窗体中
		frame.setSize(WIDTH, HEIGHT);// 设置窗体大小
		frame.setAlwaysOnTop(true);// 设置窗体总在最上
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// 默认关闭操作
		frame.setLocationRelativeTo(null);// 设置窗体初始位置
		frame.setVisible(true);// 新创建的JFrame为不可见，需要使其可见，尽快调用paint方法
		// 将键操作事件监听挂在窗体上
		frame.addKeyListener(new KeyAdapter(){// 注册给监听者方法
			public void keyPressed(KeyEvent e){// 键操作事件监听
				if(e.getKeyCode() == KeyEvent.VK_SPACE){// 按下空格时
					switch(game.state){
					case 0:// 游戏初始状态
						game.state = 1;// 进入游戏运行状态
					case 1:// 游戏运行状态
						game.bird.flappy();// 重置小鸟初始上抛速度
					}
				}
			}
		});
		// 将鼠标事件监听挂在窗体上
		frame.addMouseListener(new MouseAdapter(){// 通过构造子类对象方法创建父类对象
			public void mousePressed(MouseEvent e){// 鼠标事件监听
				switch(game.state){
				case 2:
					game.state = 0;
					game.score = 0;
					game.bird = new Bird();
					game.column1 = new Column(1);
					game.column2 = new Column(2);
					game.column3 = new Column(3);
					game.column4 = new Column(4);
					break;
				case 0:
					game.state = 1;
				case 1:
					game.bird.flappy();
				}
			}
		});
		game.move();// 画面始终移动
	}
	
}