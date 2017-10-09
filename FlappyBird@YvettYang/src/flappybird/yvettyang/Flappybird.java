package flappybird.yvettyang;
import java.awt.event.*;
import javax.swing.JFrame;

public class Flappybird {/*��Ϸ������*/
	// �������ľ�̬˽����������ô��峤��
	public static final int WIDTH = 1366;
	public static final int HEIGHT = 768;
	// ����ʵ������
	JFrame frame;
	Game game;
	
	Flappybird(String frmTitle){/*���췽��*/
		frame = new JFrame(frmTitle);// ���촰�����
	}
	
	public static void main(String args[]){/*������*/
		Flappybird yyw = new Flappybird("FlappyBird@YvettYang");// ʵ������Ϸ�������ָ������
		yyw.init();// ���ó�ʼ������
	}
	
	public void init(){/*��ʼ������*/
		game = new Game();// ʵ������Ϸ���ж���
		frame.add(game);// ����Ϸ���������ӵ�������
		frame.setSize(WIDTH, HEIGHT);// ���ô����С
		frame.setAlwaysOnTop(true);// ���ô�����������
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// Ĭ�Ϲرղ���
		frame.setLocationRelativeTo(null);// ���ô����ʼλ��
		frame.setVisible(true);// �´�����JFrameΪ���ɼ�����Ҫʹ��ɼ����������paint����
		// ���������¼��������ڴ�����
		frame.addKeyListener(new KeyAdapter(){// ע��������߷���
			public void keyPressed(KeyEvent e){// �������¼�����
				if(e.getKeyCode() == KeyEvent.VK_SPACE){// ���¿ո�ʱ
					switch(game.state){
					case 0:// ��Ϸ��ʼ״̬
						game.state = 1;// ������Ϸ����״̬
					case 1:// ��Ϸ����״̬
						game.bird.flappy();// ����С���ʼ�����ٶ�
					}
				}
			}
		});
		// ������¼��������ڴ�����
		frame.addMouseListener(new MouseAdapter(){// ͨ������������󷽷������������
			public void mousePressed(MouseEvent e){// ����¼�����
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
		game.move();// ����ʼ���ƶ�
	}
	
}