

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;



public class Main implements EngineListener, PingListener {
	
	public static Engine engine;
	
	private Ping ping;
	private ArrayList<Integer> pings = new ArrayList<Integer>();
	
	public Main(String title, int width, int height) {
		engine = new Engine(title, this);
		ping = new Ping(this);
	}
	public static Graphics2D getGraphics() {
		return engine.getGraphics();
	}
	public static int getWidth() {
		return engine.getWidth();
	}
	public double getFPS() {
		return engine.getFPS();
	}
	public static int getHeight() {
		return engine.getHeight();
	}
	public static void main(String[] args) {
		new Main("LoL Pinger (OCE) by JezaMasu",800,600);
	}
	public void logic(double time) {
		
	}
	
	public void render(Graphics2D gfx) {
		//background
		gfx.setColor(Color.black);
		gfx.fillRect(0, 0, getWidth(), getHeight());
		
		int highestIndex = 0;
		int lowestIndex = 0;
		
		int highestScreen = 0;//Highest ping currently being drawn onscreen
		int lowestScreen = 0;
		
		int dotSpacing = 8;
		int startIndex = (int)Math.max(0, pings.size() - (Main.getWidth() / dotSpacing));
		
		int averagePing = 0;//Recent Average Ping
		for(int i = 0;i < pings.size();i++) {
			int ping = pings.get(i);
			if(highestIndex == 0 || highestIndex < ping)
				highestIndex = ping;
			if(lowestIndex == 0 || lowestIndex > ping)
				lowestIndex = ping;
			
			if(i >= startIndex) {
				averagePing += ping;
				if(highestScreen == 0 || highestScreen < ping)
					highestScreen = ping;
				if(lowestScreen == 0 || lowestScreen > ping)
					lowestScreen = ping;
			}
		}
		if(pings.size() > 0)
			averagePing /= pings.size() - startIndex;
		
		GenVec oldPos = new GenVec(0,0);
		int x = 0;
		for(int i = startIndex;i < pings.size();i++) {
			int ping = pings.get(i);
			double math = getHeight(ping, highestIndex, lowestIndex);
			GenVec newPos = new GenVec(x * dotSpacing, math);
			gfx.setColor(Color.white);
			gfx.drawLine((int)oldPos.getX(), (int)oldPos.getY(), (int)newPos.getX(), (int)newPos.getY());
			gfx.fillOval((int)newPos.getX() - 5, (int)newPos.getY() - 5, 10, 10);
			oldPos = newPos.clone();
			
			x++;
		}
		
		//Guides
		gfx.setStroke(new BasicStroke(5, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[] {10,3}, 0));
		
		//All Time High
		gfx.setColor(new Color(255, 51, 0));
		int allTimeY = (int)getHeight(highestIndex, highestIndex, lowestIndex);
		gfx.drawLine(0, allTimeY, Main.getWidth(), allTimeY);
		gfx.drawString("All time high: "+highestIndex, 250, allTimeY + 20);
		
		//All Time Low
		int allTimeLowY = (int)getHeight(lowestIndex, highestIndex, lowestIndex);
		gfx.setColor(new Color(61, 216, 255));
		gfx.drawLine(0, allTimeLowY, Main.getWidth(), allTimeLowY);
		gfx.drawString("All time low: "+lowestIndex, 220, allTimeLowY - 10);
		
		//Good lol ping
		int recommendedY = (int)getHeight(50, highestIndex, lowestIndex);
		gfx.setColor(new Color(61, 255, 100));
		gfx.drawLine(0, recommendedY, Main.getWidth(), recommendedY);
		gfx.drawString("Good LoL ping of 50", 300, recommendedY - 10);
		
		//Unplayable lol ping
		int unplayableY = (int)getHeight(240, highestIndex, lowestIndex);
		gfx.setColor(new Color(255, 0, 0));
		gfx.drawLine(0, unplayableY, Main.getWidth(), unplayableY);
		gfx.drawString("Unplayable LoL ping of 240", 300, unplayableY - 10);
		
		//Bad lol ping
		int badLolY = (int)getHeight(120, highestIndex, lowestIndex);
		gfx.setColor(new Color(255, 10, 61));
		gfx.drawLine(0, badLolY, Main.getWidth(), badLolY);
		gfx.drawString("Bad LoL ping of 120", 300, badLolY - 10);
		
		//Screen High
		if(highestScreen != highestIndex) {
			gfx.setColor(new Color(255, 179, 0));
			int screenHighY = (int)getHeight(highestScreen, highestIndex, lowestIndex);
			gfx.drawLine(0, screenHighY, Main.getWidth(), screenHighY);
			gfx.drawString("Recent spike: "+highestScreen, 225, screenHighY + 20);
		}
		
		//Screen Low
		if(lowestScreen != lowestIndex) {
			int screenLowY = (int)getHeight(lowestScreen, highestIndex, lowestIndex);
			gfx.setColor(new Color(61, 255, 100));
			gfx.drawLine(0, screenLowY, Main.getWidth(), screenLowY);
			gfx.drawString("Recent low: "+lowestScreen, 200, screenLowY - 10);
		}
		
		//Average
		gfx.setStroke(new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[] {10, 3}, 0));
		gfx.setColor(new Color(255, 255, 255));
		int averageY = (int)getHeight(averagePing, highestIndex, lowestIndex);
		gfx.drawLine(0, averageY, Main.getWidth(), averageY);
		gfx.drawString("Recent Average Ping ("+averagePing+")", 375, averageY);
	}
	public double getHeight(int ping, int highest, int lowest) {
		return ((Main.getHeight() - 80) - ((double)(ping - lowest) / (highest - lowest)) * (Main.getHeight() - 80)) + 20;
	}
	//Controller methods
	@Override
	public void onMouseUp(boolean left, int x, int y) {
		
	}
	@Override
	public void onMouseDown(boolean left, int x, int y) {
		
	}
	@Override
	public void onMouseMoved(int x, int y) {
		
	}
	@Override
	public void onMouseDragged(int x, int y) {
		
	}
	@Override
    public void onKeyDown(KeyEvent e) {
    	
    }
    @Override
    public void onKeyUp(KeyEvent e) {
    	
    }
	public void onMouseScroll(boolean down, int x, int y) {
		
	}
	public void onFocus(FocusEvent e) {
		
	}
	public void onFocusLost(FocusEvent e) {
		
	}
	@Override
	public void onWindowResize(int w, int h) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void newPing(int ping) {
		System.out.println(ping);
		pings.add(new Integer(ping));
	}
	@Override
	public void exit() {
		ping.close();
	}
}