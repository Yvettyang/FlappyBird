package flappybird.yvettyang;
import java.util.Random;
import java.awt.image.BufferedImage;

public class Column {/*������*/
	BufferedImage image;
	int x,y;
	int width,height;
	int gap;// �����м�ķ�϶
	int distance;// ��������֮��ľ���
	int max = 480;// y���������Χ�����ֵ<ground.y-gap/2-40
	int min = 114;// y���������Χ����Сֵ>0+gap/2+40
	Random rand = new Random();// ʵ����һ�����������
	
	Column(int n){/*���췽�� ���ݲ���n�ԼƷ�*/
		image = Game.column;
		width = image.getWidth();// column.width=78
		height = image.getHeight();// column.height=1200
		gap = 148;
		distance = 361;// distance=(background.width+column.width)/4
		x = 1405 + (n-1)*distance;// x����Ϊ��distanceΪ����ĵȲ�����
		y = rand.nextInt(max)%(max-min+1) + min;// y����Ϊ[min,max]֮��������
	}
	
	public void step(){/*���Ӻ��˷���*/
		x--;
		if(x == - width/2){// ֱ��ͼƬ���Ҷ���ʧ����������
			x = distance*4 - width/2;// x���궨λ��������Ҷ���-width/2�����Ա�repaint
			y = rand.nextInt(max)%(max-min+1) + min;// ��������Ran[0,max]��Ȼ���(max-min+1)ȡģ��Ran[o,max-min]
		}
	}
	
}