package flappybird.yvettyang;
import java.util.Random;
import java.awt.image.BufferedImage;

public class Column {/*柱子类*/
	BufferedImage image;
	int x,y;
	int width,height;
	int gap;// 柱子中间的缝隙
	int distance;// 两根柱子之间的距离
	int max = 480;// y坐标随机范围的最大值<ground.y-gap/2-40
	int min = 114;// y坐标随机范围的最小值>0+gap/2+40
	Random rand = new Random();// 实例化一个随机数对象
	
	Column(int n){/*构造方法 传递参数n以计分*/
		image = Game.column;
		width = image.getWidth();// column.width=78
		height = image.getHeight();// column.height=1200
		gap = 148;
		distance = 361;// distance=(background.width+column.width)/4
		x = 1405 + (n-1)*distance;// x坐标为以distance为公差的等差数列
		y = rand.nextInt(max)%(max-min+1) + min;// y坐标为[min,max]之间的随机数
	}
	
	public void step(){/*柱子后退方法*/
		x--;
		if(x == - width/2){// 直至图片最右端消失于面板最左端
			x = distance*4 - width/2;// x坐标定位于面板最右端外-width/2处，以便repaint
			y = rand.nextInt(max)%(max-min+1) + min;// 首先生成Ran[0,max]，然后对(max-min+1)取模得Ran[o,max-min]
		}
	}
	
}