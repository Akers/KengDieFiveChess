package org.FiveChessGame.Model;

import java.util.Random;

import javax.swing.JOptionPane;

public class FiveChessGame
{

	//构造方法
	public FiveChessGame(int color)//默认开始颜色，1为白，2为黑
	{
		this.stepingChess = color;
		chessBordArray = new int[11][11];
		lastChess = new int[3];
//		limitTime = 30;
	}
	
	//开始游戏
	public void startGame(int color)
	{
		this.stepingChess = color;
		this.started = true;
	}
	
	//重新开始游戏
	public void restartGame()
	{
		this.stepingChess = 1;
		this.resetChessBord();
		this.started = false;
	}
	
	//本地添加棋子
	public void addChess(int x, int y)
	{
		if(this.isConnecting() && this.isMyTurn() == false)
			return;
		if(this.chessBordArray[x][y] == 0)
		{
			//坑爹模式一：颜色突变
			if(this.kengDie() == 1)
			{
				 //正走棋为黑色-->下白棋
				if(stepingChess == 2)
				{
					JOptionPane.showMessageDialog(null, "只见你手夹一粒黑棋，用力的放在棋盘上，突然。。。。\n咦？？？这。。。这棋子怎么会掉色！！！原来五子棋也玩无间道啊！！！");
					this.chessBordArray[x][y] = 1;
				}
				//正走棋为白色-->下黑棋
				if(stepingChess == 1)
				{
					JOptionPane.showMessageDialog(null, "只见你手夹一粒白棋，用力的放在棋盘上，突然。。。。\n棋子变成了黑色！！！我勒个去，你吃完墨鱼没洗手啊你！！！！");
					this.chessBordArray[x][y] = 2;
				}
				swangChessingColor();
			}
			//坑爹模式二：砸出洞洞
			else if(this.kengDie() == 2)
			{
				JOptionPane.showMessageDialog(null, "只见你手夹一粒棋，用力的放在棋盘上，突然。。。。\n棋盘被你砸出一个洞！！！你陪！！你陪！！！你陪啊！！！");
				this.chessBordArray[x][y] = 3;
				swangChessingColor();
			}
			else//正常情况。。。
			{
				if(stepingChess == 2)
					this.chessBordArray[x][y] = 2;	
				if(stepingChess == 1)
					this.chessBordArray[x][y] = 1;
				swangChessingColor();
			}
			
			//记录最后一步棋
			this.setLastChess(this.chessBordArray[x][y], x, y);
		}
		//当棋子落入洞中
		else if(this.chessBordArray[x][y] == 3)
		{
			JOptionPane.showMessageDialog(null, "只见你手夹一粒棋，用力的放在棋盘上，突然。。。。\n对，那棋华丽丽的落入洞中了。。。");
			//交换正在下棋者
			swangChessingColor();
		}
		//判断输赢
		if(chessBordArray[x][y] == 1 || chessBordArray[x][y] == 2)
		{
			if(checkWin(x, y))
			{
				this.started = false;
				JOptionPane.showMessageDialog(null, "恭喜"+(chessBordArray[x][y] == 2?"黑棋":"白棋")+"取得了胜利！！", "恭喜", JOptionPane.OK_OPTION);
				this.resetChessBord();
			}
		}
	}
	
	//添加特定棋子（用于联网落棋）
	public void addChess(int color, int x, int y)
	{
		try
		{
			if(chessBordArray[x][y] == 0)
				this.chessBordArray[x][y] = color;
		}
		catch(ArrayIndexOutOfBoundsException aiooe)
		{
			return;
		}
	}
	
	//重置棋盘
	public void resetChessBord()
	{
		for(int i=0;i<chessBordArray.length;i++)
			for(int j=0;j<chessBordArray[i].length;j++)
				chessBordArray[i][j] = 0;
	}
	
	//判断输赢
	private boolean checkWin(int x, int y) {
		boolean flag = false;
		// 保存共有相同颜色多少棋子相连
		int count = 1;
		// 判断横向是否有5个棋子相连，特点 纵坐标 是相同， 即allChess[x][y]中y值是相同
		int color = chessBordArray[x][y];

		// 判断横向
		count = this.checkCount(1, 0, x, y, color);
		if (count >= 5) 
		{
			flag = true;
		} 
		else 
		{
			// 判断纵向
			count = this.checkCount(0, 1, x, y, color);
			if (count >= 5) 
			{
				flag = true;
			} 
			else 
			{
				// 判断右上、左下
				count = this.checkCount(1, -1, x, y, color);
				if (count >= 5) 
				{
					flag = true;
				} 
				else 
				{
					// 判断右下、左上
					count = this.checkCount(1, 1, x, y, color);
					if (count >= 5) 
					{
						flag = true;
					}
				}
			}
		}

		return flag;
	}

	// 判断棋子连接的数量
	private int checkCount(int xChange, int yChange,int x, int y, int color) 
	{
		int count = 1;
		int tempX = xChange;
		int tempY = yChange;
		while (x + xChange >= 0 && x + xChange <= 10 && y + yChange >= 0
				&& y + yChange <= 10
				&& color == chessBordArray[x + xChange][y + yChange]) 
		{
			count++;
			if (xChange != 0)
				xChange++;
			if (yChange != 0) 
			{
				if (yChange > 0)
					yChange++;
				else 
				{
					yChange--;
				}
			}
		}
		xChange = tempX;
		yChange = tempY;
		while (x - xChange >= 0 && x - xChange <= 10 && y - yChange >= 0
				&& y - yChange <= 10
				&& color == chessBordArray[x - xChange][y - yChange]) 
		{
			count++;
			if (xChange != 0)
				xChange++;
			if (yChange != 0) 
			{
				if (yChange > 0)
					yChange++;
				else 
				{
					yChange--;
				}
			}
		}
		return count;
	}
	
	//坑爹模式！！
	public int kengDie()
	{
		if(kengdieModeOn)
		{
			int ran = new Random().nextInt(10);
			if(ran/3 == 1)
				return 1;
			if(ran/3 == 2)
				return 2;
			return 0;
		}
		return 0;
	}
	
	//交换黑白颜色
	public void swangChessingColor()
	{
		if(this.isConnecting() && this.isMyTurn())
			this.setMyTurn(false);
		else
			stepingChess = 3 - stepingChess;
		leftTime = 30;
		
	}
	
	//Setters&Getters
	public int[] getLastChess()
	{
		return this.lastChess;
	}
	
	public void setLastChess(int color, int x, int y)
	{
		this.lastChess[0] = color;
		this.lastChess[1] = x;
		this.lastChess[2] = y;
	}
	
	//stepingChess 下一步的棋子颜色
	public int getStepingChess()
	{
		return stepingChess;
	}

	public void setStepingChess(int stepingChess)
	{
		this.stepingChess = stepingChess;
	}
	
	//坑爹模式是否开启
	public boolean isKengdieModeOn()
	{
		return kengdieModeOn;
	}

	public void setKengdieModeOn(boolean kengdieModeOn)
	{
		this.kengdieModeOn = kengdieModeOn;
	}
	
	//是否我的回合（用于联网）
	public boolean isMyTurn()
	{
		return myTurn;
	}

	public void setMyTurn(boolean myTurn)
	{
		this.myTurn = myTurn;
	}

	//是否已经连接（用于联网）
	public boolean isConnecting()
	{
		return connecting;
	}

	public void setConnecting(boolean connecting)
	{
		this.connecting = connecting;
	}

	//limitTime 每回合限制时间
	public int getLimitTime()
	{
		return limitTime;
	}

	//游戏是否已经开始
	public boolean isStarted()
	{
		return started;
	}

	public void setStarted(boolean started)
	{
		this.started = started;
	}

	//棋盘数组
	public int[][] getChessBordArray()
	{
		return chessBordArray;
	}

	public void setChessBordArray(int[][] chessBordArray)
	{
		this.chessBordArray = chessBordArray;
	}
	
	//当前回合剩余时间
	public int getLeftTime()
	{
		return this.leftTime;
	}
	
	public void setLeftTime(int t)
	{
		this.leftTime = t;
	}
	
	private int[] lastChess;
	private int stepingChess;
	private boolean kengdieModeOn = false;
	private boolean myTurn = true;
	private boolean connecting = false;
	private final int limitTime = 30;
	private int leftTime = 30;
	private boolean started = false;
	/** 棋盘数组
	 *0表示无棋
	 * 1-->白
	 * 2-->黑
	 * 3-->坑
	 */
	private int[][] chessBordArray;
}
