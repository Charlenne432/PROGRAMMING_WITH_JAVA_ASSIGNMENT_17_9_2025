package Shapes;

import java.util.Scanner;

public class TestShapes {

	public static void main(String[] args) {
		Scanner sc=new Scanner(System.in);
		char choice;
		do{
			System.out.println("Shapes we have");
			System.out.println("1.Rectangle");
			System.out.println("2.Circle");
			System.out.println("3.Square");
			System.out.println("4.Triangle");
			System.out.print("Select shape");
			int choosen=sc.nextInt();

			switch(choosen){
			case 1:
				System.out.print("Enter length: ");
				double length=sc.nextDouble();

				System.out.print("Enter width: ");
				double width=sc.nextDouble();
				Rectangle rect=new Rectangle(length,width);

				System.out.println("What would you like to do ");
				System.out.println("1: Area");
				System.out.println("2: Perimeter");
				System.out.print("Choose: ");

				int b = sc.nextInt();
				if(b == 1) {
					System.out.println("Area Of Rectangle: " + rect.calculateArea());
				}else if(b == 2) {
					System.out.println("Perimeter Of Rectangle:" + rect.calculatePerimeter());
				}else {
					System.out.println("Choose 1 or 2 options");
				}

				break;

			case 2:
				System.out.print("Enter radius: ");
				double rad = sc.nextDouble();
				Circle circ=new Circle(rad);

				System.out.println("What would you like to do ");
				System.out.println("1: Area");
				System.out.println("2: Perimeter");
				System.out.print("Choose: ");

				int r = sc.nextInt();
				if(r == 1) {
					System.out.println("Area Of Rectangle: " + circ.calculateArea());
				}else if(r == 2) {
					System.out.println("Parameter Of Rectangle:" + circ.calculatePerimeter());
				}else {
					System.out.println("Choose 1 or 2 options");
				}
				break;

			case 3:
				System.out.print("Enter side: ");
				double sq=sc.nextDouble();
				Square Sq=new Square(sq);

				System.out.println("What would you like to do ");
				System.out.println("1: Area");
				System.out.println("2: Perimeter");
				System.out.print("Choose: ");

				int side = sc.nextInt();
				if(side == 1) {
					System.out.println("Area Of Rectangle: " + Sq.calculateArea());
				}else if(side == 2) {
					System.out.println("Parameter Of Rectangle:" + Sq.calculatePerimeter());
				}else {
					System.out.println("Choose 1 or 2 options");


				}
				break;

			case 4:
				System.out.print("Enter Base: ");
				double base = sc.nextDouble();

				System.out.print("Enter height: ");
				double height = sc.nextDouble();
				Triangle Tr=new Triangle(base, height);

				System.out.println("What would you like to do ");
				System.out.println("1: Area");
				System.out.println("2: Perimeter");
				System.out.print("Choose: ");

				int basee = sc.nextInt();
				if(basee == 1) {
					System.out.println("Area Of Rectangle: " + Tr.calculateArea());
				}else if(basee == 2) {
					System.out.println("Parameter Of Rectangle:" + Tr.calculatePerimeter());
				}else {
					System.out.println("Choose 1 or 2 options");
				}

				break;
			default:
				System.out.println("Invalid selection");
				break;
			}




			System.out.print("Would You Like to continue y/n: ");
			choice = sc.next().toLowerCase().charAt(0);

		}
		while (choice=='y');
		System.out.println("THANK YOU");
	}
}