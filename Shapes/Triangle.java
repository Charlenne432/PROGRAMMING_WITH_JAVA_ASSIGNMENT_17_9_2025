package Shapes;

public class Triangle {
	private double base;
	private double height;
	private double hyp;


	public Triangle(double base,double height){
		this.base=base;
		this.height=height;	
		double result=(base*base+height*height);
		this .hyp=Math.sqrt(result);
	}
	public double calculateArea(){
		return this.base*this.height/2;
	}
	public double calculatePerimeter(){
		return base+height+hyp;
	}

}


