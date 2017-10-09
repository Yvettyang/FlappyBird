package flappybird.yvettyang;
import java.awt.image.BufferedImage;

public class Ground {/*地面类*/
	BufferedImage image;
	int x,y;
	int width,height;// 定义图片长宽变量
	
	Ground(){/*构造方法*/
		image = Game.groundImage;
		width = image.getWidth();// groundImage.width=3/2*background.width=2049
		height = image.getHeight();// groundImage.height=174
		x = 0;
		y = 594;// y=background.height-groundImage.height=768-174
	}
	
	public void step(){/*地面后退方法 */
		x--;// x坐标不断左移
		if(x < -683){// 直到图片最右端左移至面板最右端，即x<-(groundImage.width-background.width)=-(2049-1366)
			x = 0;// x坐标归零，以便重新加载使图片最左端从面板最左端开始左移
		}
	}
	
}