import java.awt.Canvas;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Transparency;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class Engine extends Thread implements KeyListener, MouseMotionListener, FocusListener, MouseWheelListener, MouseListener, ComponentListener, GenCanvasListener {
	//With help from http://stackoverflow.com/questions/1963494/java-2d-game-graphics
	private JFrame frame;
	private int width = 800;
	private int height = 600;
	private GenCanvas canvas;
	private boolean run = true;
	private long beforeTime = 1, afterTime = 0;
	private double timeOfLastFrame = 1;
	private String title = "Untitled";
	private EngineListener listener;
	private GraphicsConfiguration config =
			GraphicsEnvironment.getLocalGraphicsEnvironment()
			.getDefaultScreenDevice()
			.getDefaultConfiguration();
	public final BufferedImage create(final int width, final int height, final boolean alpha) {
		return config.createCompatibleImage(width, height, alpha?Transparency.TRANSLUCENT : Transparency.OPAQUE);
	}
	public Engine(String xtitle, EngineListener xlistener) {
		title=xtitle;
		listener=xlistener;
		
		frame = new JFrame();
		frame.setTitle(title);
		frame.addWindowListener(new FrameClose());
		frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		frame.setSize(width,height);
		frame.setVisible(true);
		frame.addComponentListener(this);
		
		canvas = new GenCanvas(this, width, height);
		
		frame.addKeyListener(this);
		frame.addMouseMotionListener(this);
		frame.addFocusListener(this);
		frame.addMouseWheelListener(this);
		frame.addMouseListener(this);
		
		start();
	}
	public void addCanvas(Canvas canvas) {
		frame.add(canvas);
	}
	@Override
	public void run() {
		beforeTime = System.nanoTime();
		while(run) {
			afterTime = System.nanoTime();
			timeOfLastFrame = ((double)afterTime - beforeTime) / 1000000000;//in seconds
			beforeTime = System.nanoTime();
			listener.logic(timeOfLastFrame);
			canvas.startFrame();
			try {
				Thread.sleep(1000/1);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		frame.dispose();
	}
	@Override
	public void canvasRender(Graphics2D gfx) {
		listener.render(gfx);
	}
	public double getFPS() {
		return 1 / timeOfLastFrame;
	}
	public Graphics2D getGraphics() {
		return canvas.getGraphics();
	}
	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}
	private class FrameClose extends WindowAdapter {
		@Override
		public void windowClosing(final WindowEvent e) {
			run=false;
			listener.exit();
		}
	}
	
	
	public void updateGame() {
		
	}
	@Override
	public void keyPressed(KeyEvent arg0) {
		listener.onKeyDown(arg0);
	}
	@Override
	public void keyReleased(KeyEvent arg0) {
		listener.onKeyUp(arg0);
	}
	@Override
	public void keyTyped(KeyEvent arg0) {
		
	}
	@Override
	public void mouseDragged(MouseEvent arg0) {
		listener.onMouseDragged(arg0.getX(), arg0.getY());
	}
	@Override
	public void mouseMoved(MouseEvent arg0) {
		listener.onMouseMoved(arg0.getX(), arg0.getY());
	}
	@Override
	public void focusGained(FocusEvent arg0) {
		listener.onFocus(arg0);
	}
	@Override
	public void focusLost(FocusEvent arg0) {
		listener.onFocusLost(arg0);
	}
	@Override
	public void mouseWheelMoved(MouseWheelEvent arg0) {
		listener.onMouseScroll(arg0.getButton()==1, arg0.getX(), arg0.getY());
	}
	@Override
	public void mouseClicked(MouseEvent arg0) {
		
	}
	@Override
	public void mouseEntered(MouseEvent arg0) {
		
	}
	@Override
	public void mouseExited(MouseEvent arg0) {
		
	}
	@Override
	public void mousePressed(MouseEvent arg0) {
		listener.onMouseDown(arg0.getButton()==1, arg0.getX(), arg0.getY());
	}
	@Override
	public void mouseReleased(MouseEvent arg0) {
		listener.onMouseUp(arg0.getButton()==1, arg0.getX(), arg0.getY());
	}
	@Override
	public void componentHidden(ComponentEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void componentMoved(ComponentEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void componentResized(ComponentEvent arg0) {
		int w = arg0.getComponent().getWidth();
		int h = arg0.getComponent().getHeight();
		width = w;
		height = h;
		if(canvas != null)
			canvas.resizeWindow(w, h);
	}
	@Override
	public void componentShown(ComponentEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void logic(double time) {
		// TODO Auto-generated method stub
	}
	@Override
	public void onWindowResize(int w, int h) {
		// TODO Auto-generated method stub
		
	}
}