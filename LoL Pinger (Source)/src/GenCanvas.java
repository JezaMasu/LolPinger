

import java.awt.Canvas;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class GenCanvas extends Thread implements ComponentListener {
	//With help from http://stackoverflow.com/questions/1963494/java-2d-game-graphics
	private Canvas canvas;
	private BufferStrategy strategy;
	private BufferedImage background;
	private Graphics2D backgroundGraphics;
	private Graphics2D graphics;
	//private Graphics2D bg;//Used in Method B
	private GenCanvasListener parent;
	private int width, height;
	private GraphicsConfiguration config =
			GraphicsEnvironment.getLocalGraphicsEnvironment()
			.getDefaultScreenDevice()
			.getDefaultConfiguration();
	public final BufferedImage create(final int width, final int height, final boolean alpha) {
		//return config.createCompatibleImage(width, height, alpha?Transparency.TRANSLUCENT : Transparency.OPAQUE);
		return new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);//TODO switch back
	}
	public Graphics2D getGraphics() {
		return backgroundGraphics;
	}
	public GenCanvas(GenCanvasListener parent, int width, int height) {
		this.parent = parent;
		this.width = width;
		this.height = height;
		
		canvas = new Canvas(config);
		canvas.setSize(width,height);
		
		parent.addCanvas(canvas);
		
		/*
		canvas.addKeyListener(this);
		canvas.addMouseMotionListener(this);
		canvas.addFocusListener(this);
		canvas.addMouseWheelListener(this);
		canvas.addMouseListener(this);
		*/
		background = create(width,height,false);
		canvas.createBufferStrategy(2);
		do {
			strategy = canvas.getBufferStrategy();
		} while (strategy==null);
		start();
	}
	public Canvas getCanvas() {
		return canvas;
	}
	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}
	private void createGraphics() {
		try {
			graphics = (Graphics2D) strategy.getDrawGraphics();
		} catch(IllegalStateException e) {
			e.printStackTrace();
		}
	}
	private Graphics2D getBuffer() {
		if(graphics==null) {
			createGraphics();
		}
		return graphics;
	}
	private boolean updateScreen() {
		graphics.dispose();
		graphics=null;
		try {
			strategy.show();
			Toolkit.getDefaultToolkit().sync();
			return (!strategy.contentsLost());
		} catch(Exception e) {
			return true;
		}
	}
	//Method A
	public void startFrame() {
		backgroundGraphics = (Graphics2D) background.getGraphics();
		do {
			Graphics2D bg = getBuffer();
			parent.canvasRender(backgroundGraphics);
		bg.drawImage(background,0,0,null);
		} while(!updateScreen());
	}
	//Method B
	/*
	public void startDraw() {
		backgroundGraphics = (Graphics2D) background.getGraphics();
	}
	public Graphics2D gfx() {
		return backgroundGraphics;
	}
	public void endDraw() {
		do {
			Graphics2D bg = getBuffer();
			bg.drawImage(background,0,0,null);
		} while(!updateScreen());
	}
	*/
	
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
		
		resizeWindow(w, h);
	}
	public void resizeWindow(int w, int h) {
		width = w;
		height = h;
		
		canvas.setSize(width, height);
		background = create(width, height, false);
		backgroundGraphics = (Graphics2D) background.getGraphics();
		
		parent.onWindowResize(w, h);
	}
	@Override
	public void componentShown(ComponentEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}