package org.FiveChessGame.UI;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import org.FiveChessGame.Model.MyImgIO;

class MyBGPane extends JPanel 
{
	public MyBGPane(Image image, MainFrame mf)
	{
		bgIMG = image;
		this.mf = mf;
		bc = new MyImgIO().getBlackChessImg();
		wc = new MyImgIO().getWhiteChessImg();
	}
	
	protected void paintComponent(Graphics gg) 
	{
		// TODO Auto-generated method stub
		BufferedImage temBG = new BufferedImage(485, 500, BufferedImage.TYPE_INT_ARGB);
		Graphics g = temBG.getGraphics();
		
		g.drawImage(bgIMG, 0, 0, null);
		
		g.drawImage(bc, 410, 175, this);
		g.drawImage(wc, 410, 210, this);
		
		if(mf.isBlackSelected())
			g.drawImage(bc, 410, 135, this);
		else
			g.drawImage(wc, 410, 135, this);
		
		String time = mf.getLeftTime()+"";
		//初始化新字体
		Font font = new Font("宋体", Font.BOLD, 50);
		//备份原字体
		Font oFont = g.getFont();
		//设置新字体
		g.setFont(font);
		//绘制
		g.drawString(time, 400, 110);
		//还原字体
		g.setFont(oFont);
		g.dispose();
		gg.drawImage(temBG, 0, 0,this.getWidth(), this.getHeight(), this);
	}
	
	
	Image bc;
	Image wc;
	private MainFrame mf;
	private Image bgIMG = null;
	private static final long serialVersionUID = 2L;
}
