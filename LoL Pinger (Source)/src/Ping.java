import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Ping extends Thread {
	private Runtime rt;
	private Process pr;
	private InputStream is;
	private BufferedReader br;
	private PingListener pl;
	private boolean running = true;
	public Ping(PingListener pl) {
		try {
			this.pl = pl;
			rt = Runtime.getRuntime();
			pr = rt.exec("ping 203.174.139.185 -t");
			is = pr.getInputStream();
			br = new BufferedReader(new InputStreamReader(is));
			start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	public void run() {
		while(running) {
			try {
				String line = br.readLine();
				if(line == null) {
					running = false;
					continue;
				}
				if(!line.contains("time="))
					continue;
				String s = line.substring(line.indexOf("time=") + 5, line.indexOf("ms"));
				pl.newPing(new Integer(s));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	public void close() {
		running = false;
		pr.destroy();
	}
}