package Bases11_Applet;

import java.applet.Applet;
import java.awt.Graphics;

public class Simple extends Applet {
	StringBuffer buff;
	
	public void  init(){
		buff = new StringBuffer();
		addItem("initialisation....");
	}

	public void start(){
		addItem("Lancement...");
		
	}
	public void stop(){
		addItem(" arret en cours ...");
	}
	
	public void destroy(){
		addItem("preparation de destruction ...");
	}
	
	private void addItem(String newWord){
			System.out.println(newWord);
			buff.append(newWord);
			repaint();
			
	}
	
	public void paint(Graphics g){
		g.drawRect(0,0,getWidth()-1,getHeight()-1);
		
		g.drawString(buff.toString(),5,15);
		
		
	}
}
