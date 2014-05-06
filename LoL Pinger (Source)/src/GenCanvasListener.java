import java.awt.Canvas;
import java.awt.Graphics2D;
public interface GenCanvasListener {
	public void logic(double time);
	public void canvasRender(Graphics2D gfx);
	public void onWindowResize(int w, int h);
	public void addCanvas(Canvas canvas);
}