package com;

// calculating x and y coordinates
class AnalysisOfData {
	private double valueA = -1;
	private double valueB = -1;
	private double x = -1;
	private double y = -1;
	private boolean flag = false;
	private double base = 10;
	private double ht = 9;
	private double diff = 1.5;
	private double side = 7;
	
	public AnalysisOfData(double valueA, double valueB) {
		this.valueA = valueA;
		this.valueB = valueB;
		this.calculateXY();
	}

	public double getValueA() {
		return valueA;
	}

	void setValueA(double valueA) {
		this.valueA = valueA;
	}

	public double getValueB() {
		return valueB;
	}

	void setValueB(double valueB) {
		this.valueB = valueB;
	}

	public double getX() {
		return x;
	}

	void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	void setY(double y) {
		this.y = y;
	}

	public boolean isFlag() {
		return flag;
	}

	void setFlag(boolean flag) {
		this.flag = flag;
	}

	private void calculateXY(){
		double s = ( this.getValueA() + this.getValueB() + this.base ) / 2;
		System.out.println("s:" + s);
		double val1 = s - this.getValueA();
		double val2 = s - this.getValueB();
		double val3 = s - this.base;
		double x = -1;
		double y = -1;
		if(!(val1 < 0 || val2 < 0 || val3 < 0)){
			// area of the triangle
			double area = Math.sqrt(s*val1*val2*val3);
			System.out.println("area:"+area);

			// calculating height
			double height = ( area * 2 ) / this.base;
			System.out.println("h:"+height);
			double length = Math.sqrt(Math.pow(this.getValueA(), 2) - Math.pow(height, 2));
			System.out.println("w:"+length);
			y = height - this.ht;
			x = length - this.diff;

			if((height < this.ht || height > (this.ht+this.side)) || (length < this.diff || length > (this.base-this.diff)) || x<0 || y<0){
				this.setFlag(false);
			} else {
				this.setY(height - this.ht);
				this.setX(length - this.diff);
				this.setFlag(true);
			}
		}
		
		System.out.println(x);
		System.out.println(y);
	}
	
}
