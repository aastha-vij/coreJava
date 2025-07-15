package coreJava;

public class A01_Firstclass {

	public void getData() {
		System.out.println("Inside getData Method in First Class");
	}
	
	public static void main(String[] args) {
		System.out.println("welcome");

		A01_Firstclass fn= new A01_Firstclass();
		fn.getData();
		
		A02_Secondclass sc = new A02_Secondclass();
		sc.setData();
	}

}
