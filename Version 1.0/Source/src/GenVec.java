
public class GenVec {
	private double x, y;
	public GenVec() {
		this.x = 0;
		this.y = 0;
	}
	public GenVec(double x, double y) {
		this.x = x;
		this.y = y;
	}
	public double getX() {
		return x;
	}
	public double getY() {
		return y;
	}
	public double getLength() {
		return Math.sqrt(getLengthSq());
	}
	public double getLengthSq() {
		return Math.pow(x, 2) + Math.pow(y, 2);
	}
	public GenVec normalize() {
		x *= 1 / getLength();
		y *= 1 / getLength();
		return this;
	}
	public void setX(double x) {
		this.x = x;
	}
	public void setY(double y) {
		this.y = y;
	}
	public GenVec setXY(double x, double y) {
		this.x = x;
		this.y = y;
		return this;
	}
	public GenVec setXY(GenVec v) {
		this.x = v.getX();
		this.y = v.getY();
		return this;
	}
	//Cross between two vectors resulting in a double
	public double cross(GenVec vec) {
		return x * vec.getY() - y * vec.getX();
	}
	//Cross a vector with a number resulting in a GenVec 
	public GenVec cross(double a) {
		y *= a;
		x *= -a;
		return this;
	}
	public GenVec divide(double d) {
		this.x /= d;
		this.y /= d;
		return this;
	}
	public GenVec addition(double a) {
		this.x += a;
		this.y += a;
		return this;
	}
	public GenVec addition(double a, double b) {
		this.x += a;
		this.y += b;
		return this;
	}
	public GenVec addition(GenVec vec) {
		this.x += vec.getX();
		this.y += vec.getY();
		return this;
	}
	public GenVec subtract(double a) {
		this.x -= a;
		this.y -= a;
		return this;
	}
	public GenVec subtract(double a, double b) {
		this.x -= a;
		this.y -= b;
		return this;
	}
	public GenVec subtract(GenVec vec) {
		this.x -= vec.getX();
		this.y -= vec.getY();
		return this;
	}
	public GenVec multiply(double m) {
		this.x *= m;
		this.y *= m;
		return this;
	}
	public GenVec multiply(GenVec v) {
		this.x *= v.getX();
		this.y *= v.getY();
		return this;
	}
	public GenVec clone() {
		return new GenVec(x, y);
	}
	public GenVec neg() {
		x = -x;
		y = -y;
		return this;
	}
}