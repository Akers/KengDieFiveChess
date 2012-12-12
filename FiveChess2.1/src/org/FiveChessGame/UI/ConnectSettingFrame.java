package org.FiveChessGame.UI;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.FiveChessGame.Model.FiveChessGame;

public class ConnectSettingFrame extends JFrame 
{
	//构造方法
	public ConnectSettingFrame(MainFrame mf, FiveChessGame fcg)
	{
		super("联网设置");
		this.mf = mf;
		this.fcg = fcg;
		this.setResizable(false);
		this.setBounds(mf.getLocation().x+80, mf.getLocation().y+150, 310, 100);
		JPanel p1 = new JPanel();
		JPanel p3 = new JPanel();
		p1.add(new JLabel("连接到主机:"));
		txt_inputIP = new JTextField();
		txt_inputIP.setPreferredSize(new Dimension(200, 25));
		txt_inputIP.setText("在此输入主机IP");
		p1.add(txt_inputIP);
		
		Dimension d = new Dimension(90, 25);
		btn_con = new JButton("连接主机");
		btn_start = new JButton("新建主机");
		btn_clean = new JButton("取消");
		btn_con.setPreferredSize(d);
		btn_start.setPreferredSize(d);
		btn_clean.setPreferredSize(d);
		
		//添加监听器
		ButtonActions ba = new ButtonActions(this);
		btn_con.addActionListener(ba);
		btn_start.addActionListener(ba);
		btn_clean.addActionListener(ba);
		p3.setLayout(new FlowLayout(FlowLayout.LEFT));
		p3.add(btn_con);
		p3.add(btn_start);
		p3.add(btn_clean);
		
		Container c = this.getContentPane();
		c.setLayout(new BoxLayout(c, BoxLayout.Y_AXIS));
		c.add(p1);
//		c.add(p2);
		c.add(p3);
		this.setVisible(true);
	}
	
	//新建主机
	public void newMaster()
	{
		//新建一个主机，并在监听端口上建立监听
		//new Connector();
	}
	
	//连接到主机
	public void connectToMaster()
	{
		//连接到IP为connIP的主机
		//new Connector(connIP);
	}
	
	//ip地址是否合法
	public boolean checkInputIP()
	{
		String inputIP = txt_inputIP.getText().trim();
		if(inputIP.equals(""))
		{
			JOptionPane.showMessageDialog(this, "IP地址不能为空，请重新输入");
			return false;
		}
		else 
		{
			//正则表达式，格式判断
			Pattern regx = Pattern.compile("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}");
			//Matcher对象
			Matcher m = regx.matcher(inputIP);
			//匹配失败
			if(m.matches() == false)
			{
				JOptionPane.showMessageDialog(this, "IP地址输入错误，请重新输入");
				return false;
			}
			else if(m.matches())
			{
				//数值判断
				String[] ipPart = inputIP.split("\\.");
				
				for(int i=0;i<ipPart.length;i++)
				{
					try {
						int j = Integer.parseInt(ipPart[i]);
						if(j<0 || j>255)
						{
							JOptionPane.showMessageDialog(this, "IP地址输入错误，请重新输入");
							return false;
						}
					} catch (NumberFormatException e) {
						JOptionPane.showMessageDialog(this, "IP地址输入错误，请重新输入");
						return false;
					}
				}
				return true;
			}
		}
		return false;
		
	}
	
//	//端口是否合法
//	public boolean chectKnputPort()
//	{
//		String inPort = txt_inputPort.getText().trim();
//		if(inPort.equals(""))
//		{
//			JOptionPane.showMessageDialog(this, "端口号不能为空！！");
//			return false;
//		}
//		int iport;
//		try 
//		{
//			iport = Integer.parseInt(inPort);
//			setPort(iport);
//			return true;
//		} 
//		catch (Exception e) 
//		{
//			JOptionPane.showMessageDialog(this, "端口输入错误！！");
//			return false;
//		}
//	}
	
	//设置IP
	public void setIP(String ipStr)
	{
		try {
			this.connIP = InetAddress.getByName(ipStr);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//设置端口
	public void setPort(int port)
	{
		this.port = port;
	}
	
	//按钮事件响应
	private class ButtonActions implements ActionListener
	{
		public JFrame f;
		public ButtonActions(JFrame f)
		{
			this.f = f;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			if(e.getActionCommand().equals("连接主机"))
			{
				if(checkInputIP() /*&& chectKnputPort()*/)
				{
					connectToMaster();
					
				}
			}
			if(e.getActionCommand().equals("新建主机"))
			{
				newMaster();//新建主机
//				if(chectKnputPort())
//					JOptionPane.showMessageDialog(null, "端口输入正确");
			}
			if(e.getActionCommand().equals("取消"))
			{
				f.dispose();
			}
			
		}
	}
	
	private int port = 8888;
	private InetAddress connIP = null;
	private JButton btn_con;
	private JButton btn_start;
	private JButton btn_clean;
	private JTextField txt_inputIP;
	private JTextField txt_inputPort;
	private MainFrame mf;
	private FiveChessGame fcg;
	private static final long serialVersionUID = 2132531566L;

}
