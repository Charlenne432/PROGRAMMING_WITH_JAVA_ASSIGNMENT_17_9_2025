package Shapes;

public class Circle {
	private double radius;
	

	public Circle(double r){
		this.radius=r;	
	}
	public double calculateArea(){
		return Math.PI*this.radius*this.radius;
	}
	public double calculatePerimeter(){
		return Math.PI*this.radius*2;
	}

}
