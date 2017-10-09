package flappybird.yvettyang;
import sun.audio.*;
import java.io.IOException;
import java.awt.image.BufferedImage;

public class Bird {/*鸟类*/
	BufferedImage image;
	int x,y;
	int width,height;
	double v0;// 初始上抛速度
	double speed;// 当前上抛速度
	double g;// 重力加速度
	double t;// 时间间隔t
	double s;// 时间t后的垂直位移
	double alpha;// 小鸟头部倾角
	int index;// 图片变换计数
	BufferedImage[] images = new BufferedImage[]// 实例化一个数组，用于鸟的动画效果
	{Game.bird0,Game.bird1,Game.bird2,Game.bird3,Game.bird4,Game.bird5,Game.bird6,Game.bird7};
	
	Bird(){/*构造方法*/
		image = Game.bird0;
		width = image.getWidth();// birdi.width=56
		height = image.getHeight();// birdi.height=48
		x = 760;
		y = 270;// 设置初始坐标参数
		v0 = 20;
		g = 3;
		t = 0.2;
		s = 0;
		alpha = 0;
		index = 0;// 初始化计数为0
	}
	
	public void step(){/*垂直位移方法*/
		double v0 = speed;// 重置初始上抛速度为当前速度
		s = v0*t - g*t*t/2;// 垂直方向位移公式
		y = y - (int)s;// s取整后改变y坐标
		speed = v0 - g*t;// 加速度公式
		alpha = Math.atan(s/5);// 计算倾角
		if(y < height/2){// 小鸟图像超过面板上边界
			y = height/2;// 控制小鸟始终沿上边界在面板内
		}
	}
	
	public void fly(){/*飞行动画效果*/
		index++;
		image = images[(index/12)%8];// 实现图片切换
	}
	
	public void flappy(){
		speed = v0;// 重置速度 重新向上飞
		new Thread(new Runnable(){
			public void run(){
				try {
					AudioStream wing = new AudioStream(getClass().getResourceAsStream("audio/wing.au"));// 实例化声音流
					AudioStream drop = new AudioStream(getClass().getResourceAsStream("audio/drop.au"));
					AudioPlayer.player.start(wing);// 播放音效
					AudioPlayer.player.start(drop);
				} catch(IOException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
	
	public boolean hit(Ground ground){/*碰撞方法(撞击地面)*/
		boolean hit = y + height/2 > ground.y;// 小鸟图像低于地面
			if(hit){// 判断是否撞击
				y = ground.y - height/2;// 将小鸟落在地面上
				alpha = 3.141592653/2;// 改变角度实现小鸟撞地栽头效果
			}
			return hit;// 返回值
	}
	
	public boolean hit(Column column){/*碰撞方法（撞击柱子）*/
		int x1 = column.x - column.width/2 - width/2 + 5;// 撞击柱子右边时x坐标
		int x2 = column.x + column.width/2 + width/2 - 5;// 撞击柱子右边时x坐标
		int y1 = column.y - column.gap/2 + height/2 - 5;// 撞击柱子上方时y坐标
		int y2 = column.y + column.gap/2 - height/2 + 5;// 撞击柱子下方时y坐标
		if(x > x1 && x < x2){// 先检测x坐标是否在柱子范围内
			if(y > y1 && y < y2){// 再检测y坐标是否在柱子缝隙内
				return false;// 未成功通过柱子
			}
			return true;
		}
		return false;
	}
	
}