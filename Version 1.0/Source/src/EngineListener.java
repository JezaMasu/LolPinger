
import java.awt.Graphics2D;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;

public interface EngineListener {
	public void logic(double time);
	public void render(Graphics2D gfx);
	public void onMouseUp(boolean left, int x, int y);
	public void onMouseDown(boolean left, int x, int y);
	public void onMouseScroll(boolean down, int x, int y);
	public void onMouseMoved(int x, int y);
	public void onMouseDragged(int x, int y);
	
	public void onKeyDown(KeyEvent e);
	public void onKeyUp(KeyEvent e);
	
	public void onFocus(FocusEvent e);
	public void onFocusLost(FocusEvent e);
	
	public void onWindowResize(int w, int h);
	public void exit();
}