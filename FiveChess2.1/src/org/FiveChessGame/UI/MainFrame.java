package org.FiveChessGame.UI;

import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import org.FiveChessGame.Model.FiveChessGame;
import org.FiveChessGame.Model.MyImgIO;

public class MainFrame extends JFrame 
{
	public MainFrame()
	{
	    this.initialize();
	}
	
	//初始化窗体
	public void initialize()
	{
		//属性设置
		// 取得屏幕的宽度
		int width = Toolkit.getDefaultToolkit().getScreenSize().width;
		// 取得屏幕的高度
		int height = Toolkit.getDefaultToolkit().getScreenSize().height;
		this.setTitle("坑爹五子棋");
		this.setBounds(width/2-485/2, height/2-250, 485, 500);
		this.setResizable(false);
		//this.setUndecorated(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		把Layout设置成NULL使用绝对布局
		this.setLayout(null);
		
		//region 初始化panel_bgImage
		/** 初始化panel_bgImage
		 * 显示背景图片的panel
		 */
		bgImg = new MyImgIO().getBgImg();
		panel_bgImage = new MyBGPane(bgImg, this);
		panel_bgImage.setLayout(null);
		panel_bgImage.setBounds(0, 0, 485, 500);
		
		//endregion
		
		//region 初始化panel_Chessboard
		/** 初始化panel_Chessboard
		 * 显示棋盘的Panel
		 */
		//实例化五子棋游戏模型
	    fcg = new FiveChessGame(1);
	    fcg.setLeftTime(fcg.getLimitTime());
		panel_Chessboard = new ChessboardPanel(this.fcg);
		panel_Chessboard.setBounds(10, 50, 360, 360);
		
		//endregion
		
		/** 初始化操作控件
		 */
		//region颜色选择单选按钮
		group_chessColor = new ButtonGroup();
		rb_black = new JRadioButton();//new ImageIcon(new MyImgIO().getBlackChessImg()));
		rb_white = new JRadioButton();//new ImageIcon(new MyImgIO().getWhiteChessImg()));
		group_chessColor.add(rb_black);
		group_chessColor.add(rb_white);
		rb_black.setSelected(true);
		rb_black.setBounds(380, 185, 17, 15);
		rb_white.setBounds(380, 210, 17, 15);
		rb_black.setOpaque(false);
		rb_white.setOpaque(false);
		
		//endregion
		
		//region开始游戏按钮
		btn_newGame = new JButton("开始游戏");
		btn_newGame.setAlignmentX(LEFT_ALIGNMENT);
		
		btn_newGame.addActionListener(
			new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) 
				{	
					if(e.getActionCommand().equals("开始游戏"))
					{
						if(fcg.isStarted() == false)
						{
							if(JOptionPane.showConfirmDialog(null, "是否开始游戏？", "确认", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
							{
								fcg.startGame(rb_white.isSelected()? 1 : 2);
								fcg.setLeftTime(fcg.getLimitTime());
							}
						}
					}
					else if(e.getActionCommand().equals("重新开始"))
					{
						if(JOptionPane.showConfirmDialog(null, "是否重新开始游戏？", "确认", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
						{
							fcg.restartGame();
							fcg.setLeftTime(fcg.getLimitTime());
						}
					}
					
				}
			}
		);
		btn_newGame.setBounds(380, 250, 90, 25);
		
		//endregion
		
		//region联机按钮
		btn_connGame = new JButton("联机对战");
		btn_connGame.setAlignmentX(LEFT_ALIGNMENT);
		
		class MyButtonListioner implements ActionListener
		{
			private MainFrame mf;
			public MyButtonListioner(MainFrame mf){this.mf = mf;}
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showConfirmDialog(mf, "联机功能不可用.....", "错误！！", JOptionPane.CLOSED_OPTION, JOptionPane.WARNING_MESSAGE);
				//ToDo: 联机对战功能
//				new ConnectSettingFrame(mf, mf.fcg);
				
			}
		}
		
		btn_connGame.addActionListener(new MyButtonListioner(this));
		btn_connGame.setBounds(380, 280, 90, 25);
		
		//endregion
		
		//
		btn_kengdieOn = new JButton("开启坑爹");
		btn_kengdieOn.setLayout(new FlowLayout(FlowLayout.LEFT));
		btn_kengdieOn.addActionListener(new ActionListener()
		{
			
			@Override
			public void actionPerformed(ActionEvent e)
			{	
				JButton jb = (JButton)(e.getSource());
				if(jb.getText().equals("关闭坑爹"))
				{
//					kengdieModeOn = false;
					fcg.setKengdieModeOn(false);
					jb.setText("开启坑爹");
					
				}
				else if(jb.getText().equals("开启坑爹"))
				{
//					kengdieModeOn = true;
					fcg.setKengdieModeOn(true);
					jb.setText("关闭坑爹");
				}
				
			}
		});
		btn_kengdieOn.setBounds(380, 310, 90, 25);
		
		//region关于按钮
		btn_about = new JButton("关于...");
		btn_about.addActionListener(
			new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					JOptionPane.showMessageDialog(null, "坑爹五子棋\nVersion:2.0Demo\nAuthor:Akers", "关于坑爹五子棋", JOptionPane.PLAIN_MESSAGE);
				}				
			}
		);
		btn_about.setBounds(380, 340, 90, 25);
		//endregion
		
		//region退出按钮
		btn_exit = new JButton("退出程序");
		btn_exit.setAlignmentX(LEFT_ALIGNMENT);
		//监听器
		btn_exit.addActionListener(
			new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) 
				{
					if(JOptionPane.showConfirmDialog(null, "确定要退出游戏？", "确认退出", JOptionPane.YES_NO_OPTION)== JOptionPane.YES_OPTION)
						System.exit(0);
				}
			}
		);
		btn_exit.setBounds(380, 370, 90, 25);
		//endregion
		
		
		//endregion


		//region添加控件
		panel_bgImage.add(btn_newGame);
		panel_bgImage.add(btn_connGame);
		panel_bgImage.add(btn_kengdieOn);
		panel_bgImage.add(btn_about);
		panel_bgImage.add(btn_exit);
		panel_bgImage.add(rb_black);
		panel_bgImage.add(rb_white);
		panel_bgImage.add(panel_Chessboard);
		this.add(panel_bgImage);
		
		
		//endregion
		
		//启动后台线程
		ChessGameBGThread cgb = new ChessGameBGThread();
		cgb.start();
		
		
		this.setVisible(true);
	}
	
	//取得剩余时间
	public int getLeftTime()
	{
		return this.fcg.getLeftTime();
	}
	
	//取得单选框状态，true为黑选中白为选，false为白选中黑未选
	public boolean isBlackSelected()
	{
		if(this.rb_black.isSelected())
			return true;
		if(this.rb_white.isSelected())
			return false;
		else
			return false;
	}
	
	
	//正在下的棋子是什么颜色
	public boolean drawBlackChess()
	{
		if(rb_black.isSelected())
			return true;
		else
			return false;
	}

	private FiveChessGame fcg;
	private JButton btn_kengdieOn;//开关坑爹模式
	private JButton btn_about;//About Button
	private JButton btn_newGame;//开始游戏
	private JButton btn_connGame;//联机对战
	private JButton btn_exit;//退出
	private Image bgImg;
	private ButtonGroup group_chessColor;
	private JRadioButton rb_black;
	private JRadioButton rb_white;
	private JPanel panel_Chessboard;
	private JPanel panel_bgImage;
	private long lastRepaintTime = 0;
	//后台线程，用于刷新棋盘以及计算剩余时间
	private class ChessGameBGThread extends Thread
	{
		public void run()
		{
			while(true)
			{
				panel_Chessboard.repaint();
				if(fcg.isStarted())
				{
					//修改控件状态：
					rb_black.setEnabled(false);
					rb_white.setEnabled(false);
					btn_newGame.setActionCommand("重新开始");
					btn_newGame.setText("重新开始");
					btn_kengdieOn.setEnabled(false);
					
					//计算剩余时间并刷新计时器：
					if(System.currentTimeMillis() - lastRepaintTime >= 1000)
					{
						lastRepaintTime = System.currentTimeMillis();
						fcg.setLeftTime(fcg.getLeftTime() - 1);
						
						//判断定时时间是否已经用完
						if(fcg.getLeftTime() <= 0)
						{
							fcg.swangChessingColor();
							fcg.setLeftTime(fcg.getLimitTime());
						}
					}
					
					//交换棋子标识
					if(fcg.getStepingChess() == 1)
					{
						rb_white.setSelected(true);
						rb_black.setSelected(false);
					}
					else if(fcg.getStepingChess() == 2)
					{
						rb_white.setSelected(false);
						rb_black.setSelected(true);
					}
				}
				else
				{
					btn_newGame.setActionCommand("开始游戏");
					btn_newGame.setText("开始游戏");
					rb_black.setEnabled(true);
					rb_white.setEnabled(true);
					btn_kengdieOn.setEnabled(true);
				}
				
				//重绘背景
				panel_bgImage.repaint();
				
				try 
				{
					sleep(500);
				} 
				catch (InterruptedException e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	private static final long serialVersionUID = 1L;
}
