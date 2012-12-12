package org.FiveChessGame.Model;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class MyImgIO 
{
	public Image getHode() {
		return hode;
	}
	public Image getBgImg() {
		return bgImg;
	}
	public Image getChessBrodImg() {
		return chessBrodImg;
	}
	public Image getBlackChessImg()
	{
		return blackChess;
	}
	
	public ImageIcon getBlackChessIco()
	{
		return new ImageIcon(imgPatch+"blackChess.png");
	}
	
	public ImageIcon getWhiteChessIco()
	{
		return new ImageIcon(imgPatch+"whiteChess.png");
	}
	
	public Image getWhiteChessImg()
	{
		return whiteChess;
	}
	public MyImgIO()
	{
		imgPatch = System.getProperty("user.dir")+"/images/";
		imgPatch = imgPatch.replaceAll("\\\\", "/");
		bgImg = new ImageIcon(imgPatch+"MainBG.jpg").getImage();
		hode = new ImageIcon(imgPatch+"hode.png").getImage();
		blackChess = new ImageIcon(imgPatch+"blackChess.png").getImage();
		whiteChess = new ImageIcon(imgPatch+"whiteChess.png").getImage();
		chessBrodImg = new ImageIcon(imgPatch+"chessBord.png").getImage();
	}
	
	
	private Image hode;
	private Image blackChess;
	private Image whiteChess;
	private Image bgImg;
	private Image chessBrodImg;
	private String imgPatch;
}
