package flappybird.yvettyang;
import sun.audio.*;
import java.io.IOException;
import java.awt.image.BufferedImage;

public class Bird {/*����*/
	BufferedImage image;
	int x,y;
	int width,height;
	double v0;// ��ʼ�����ٶ�
	double speed;// ��ǰ�����ٶ�
	double g;// �������ٶ�
	double t;// ʱ����t
	double s;// ʱ��t��Ĵ�ֱλ��
	double alpha;// С��ͷ�����
	int index;// ͼƬ�任����
	BufferedImage[] images = new BufferedImage[]// ʵ����һ�����飬������Ķ���Ч��
	{Game.bird0,Game.bird1,Game.bird2,Game.bird3,Game.bird4,Game.bird5,Game.bird6,Game.bird7};
	
	Bird(){/*���췽��*/
		image = Game.bird0;
		width = image.getWidth();// birdi.width=56
		height = image.getHeight();// birdi.height=48
		x = 760;
		y = 270;// ���ó�ʼ�������
		v0 = 20;
		g = 3;
		t = 0.2;
		s = 0;
		alpha = 0;
		index = 0;// ��ʼ������Ϊ0
	}
	
	public void step(){/*��ֱλ�Ʒ���*/
		double v0 = speed;// ���ó�ʼ�����ٶ�Ϊ��ǰ�ٶ�
		s = v0*t - g*t*t/2;// ��ֱ����λ�ƹ�ʽ
		y = y - (int)s;// sȡ����ı�y����
		speed = v0 - g*t;// ���ٶȹ�ʽ
		alpha = Math.atan(s/5);// �������
		if(y < height/2){// С��ͼ�񳬹�����ϱ߽�
			y = height/2;// ����С��ʼ�����ϱ߽��������
		}
	}
	
	public void fly(){/*���ж���Ч��*/
		index++;
		image = images[(index/12)%8];// ʵ��ͼƬ�л�
	}
	
	public void flappy(){
		speed = v0;// �����ٶ� �������Ϸ�
		new Thread(new Runnable(){
			public void run(){
				try {
					AudioStream wing = new AudioStream(getClass().getResourceAsStream("audio/wing.au"));// ʵ����������
					AudioStream drop = new AudioStream(getClass().getResourceAsStream("audio/drop.au"));
					AudioPlayer.player.start(wing);// ������Ч
					AudioPlayer.player.start(drop);
				} catch(IOException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
	
	public boolean hit(Ground ground){/*��ײ����(ײ������)*/
		boolean hit = y + height/2 > ground.y;// С��ͼ����ڵ���
			if(hit){// �ж��Ƿ�ײ��
				y = ground.y - height/2;// ��С�����ڵ�����
				alpha = 3.141592653/2;// �ı�Ƕ�ʵ��С��ײ����ͷЧ��
			}
			return hit;// ����ֵ
	}
	
	public boolean hit(Column column){/*��ײ������ײ�����ӣ�*/
		int x1 = column.x - column.width/2 - width/2 + 5;// ײ�������ұ�ʱx����
		int x2 = column.x + column.width/2 + width/2 - 5;// ײ�������ұ�ʱx����
		int y1 = column.y - column.gap/2 + height/2 - 5;// ײ�������Ϸ�ʱy����
		int y2 = column.y + column.gap/2 - height/2 + 5;// ײ�������·�ʱy����
		if(x > x1 && x < x2){// �ȼ��x�����Ƿ������ӷ�Χ��
			if(y > y1 && y < y2){// �ټ��y�����Ƿ������ӷ�϶��
				return false;// δ�ɹ�ͨ������
			}
			return true;
		}
		return false;
	}
	
}