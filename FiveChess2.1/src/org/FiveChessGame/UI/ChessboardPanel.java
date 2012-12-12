package org.FiveChessGame.UI;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.FiveChessGame.Model.FiveChessGame;
import org.FiveChessGame.Model.MyImgIO;

public class ChessboardPanel extends JPanel implements MouseListener, MouseMotionListener
{
	public ChessboardPanel(FiveChessGame mf)
	{
		this.fcg = mf;
		MyImgIO mii = new MyImgIO();
		bgImg = mii.getChessBrodImg();
		blackImg = mii.getBlackChessImg();
		whiteImg = mii.getWhiteChessImg();
		hodeImg = mii.getHode();
		mouseBlackIcon = mii.getBlackChessIco();
		mouseWhiteIcon = mii.getWhiteChessIco();
		mouseIcon = new JLabel();
		mouseIcon.setOpaque(false);
		mouseIcon.setBounds(10, 10, 30, 30);
		mouseIcon.setVisible(false);

		this.add(mouseIcon);
		this.addMouseMotionListener(this);
		this.addMouseListener(this);
		this.setLayout(null);
	}
	
	@Override
	protected void paintComponent(Graphics g) 
	{
		//创建一个chessBrodImg作为画布
//		Image tBG = bgImg;
		chessBrodImg = new BufferedImage(360, 360, BufferedImage.TYPE_INT_ARGB);
		Graphics big = chessBrodImg.getGraphics();
		big.drawImage(bgImg, 0, 0, 360, 360, this);
		int[][] cba = fcg.getChessBordArray();
		//绘制棋子
		for(int i=0; i<cba.length; i++)
		{
			for(int j=0; j<cba[i].length; j++)
			{
				switch(cba[i][j])
				{
				case 0:
					break;
				case 1:
					big.drawImage(whiteImg, (i+1)*30-15, (j+1)*30-15, null);
					break;
				case 2:
					big.drawImage(blackImg, (i+1)*30-15, (j+1)*30-15, null);
					break;
				case 3:
					big.drawImage(hodeImg, (i+1)*30-15, (j+1)*30-15, null);
					break;
				}
			}
		}
		
		
		big.dispose();
		g.drawImage(chessBrodImg, 0, 0, 360, 360, this);
	}

	//鼠标点击事件
	@Override
	public void mouseClicked(MouseEvent e) 
	{
		if(fcg.isStarted() && fcg.isMyTurn())
		{
			int mX = 0;//鼠标X坐标
			int mY = 0;//鼠标Y坐标
			int cX = 0;//棋子X坐标
			int cY = 0;//棋子Y坐标
			mX = e.getX();
			mY = e.getY();
			//X坐标异常纠正
			if(mX<15)
				mX = 15;
			if(mX>330)
				mX = 330;
			//Y坐标异常纠正
			if(mY<15)
				mY = 15;
			if(mY>330)
				mY = 330;
			cX = (mX+15)/30;
			cY = (mY+15)/30;
			fcg.addChess(cX-1, cY-1);
			this.repaint();
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) 
	{
		drawWithMouse = true;
		this.mouseIcon.setVisible(false);
	}

	@Override
	public void mouseExited(MouseEvent arg0) 
	{
		drawWithMouse = false;
		this.mouseIcon.setVisible(false);
	}

	@Override
	public void mousePressed(MouseEvent arg0) {}

	@Override
	public void mouseReleased(MouseEvent arg0) {}
	
	@Override
	public void mouseDragged(MouseEvent e){}

	//鼠标跟随图像：
	@Override
	public void mouseMoved(MouseEvent e)
	{
		if(this.drawWithMouse)
		{
			if(fcg.isStarted())
			{
				if(fcg.getStepingChess() == 2)
					this.mouseIcon.setIcon(this.mouseBlackIcon);
				else
					this.mouseIcon.setIcon(this.mouseWhiteIcon);
				mouseX = e.getX();
				mouseY = e.getY();
				this.mouseIcon.setLocation(mouseX-15, mouseY-15);
				this.mouseIcon.setVisible(true);
				this.mouseIcon.updateUI();
				this.repaint();
			}
		}
	}
	
	
	
	
	private FiveChessGame fcg;
	
	private int mouseX;
	private int mouseY;
	private ImageIcon mouseBlackIcon;
	private ImageIcon mouseWhiteIcon;
	private JLabel mouseIcon;
	private boolean drawWithMouse = false;
	private BufferedImage chessBrodImg;
	private Image hodeImg;
	private Image bgImg;
	private Image blackImg;
	private Image whiteImg;
	private static final long serialVersionUID = 3L;
	
}
