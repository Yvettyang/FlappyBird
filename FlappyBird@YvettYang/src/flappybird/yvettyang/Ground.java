package flappybird.yvettyang;
import java.awt.image.BufferedImage;

public class Ground {/*������*/
	BufferedImage image;
	int x,y;
	int width,height;// ����ͼƬ�������
	
	Ground(){/*���췽��*/
		image = Game.groundImage;
		width = image.getWidth();// groundImage.width=3/2*background.width=2049
		height = image.getHeight();// groundImage.height=174
		x = 0;
		y = 594;// y=background.height-groundImage.height=768-174
	}
	
	public void step(){/*������˷��� */
		x--;// x���겻������
		if(x < -683){// ֱ��ͼƬ���Ҷ�������������Ҷˣ���x<-(groundImage.width-background.width)=-(2049-1366)
			x = 0;// x������㣬�Ա����¼���ʹͼƬ����˴��������˿�ʼ����
		}
	}
	
}