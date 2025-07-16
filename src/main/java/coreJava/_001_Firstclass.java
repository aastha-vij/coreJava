package coreJava;

public class _001_Firstclass {

	public void getData() {
		System.out.println("Inside getData Method in First Class");
	}
	
	public static void main(String[] args) {
		System.out.println("welcome");

		_001_Firstclass fn= new _001_Firstclass();
		fn.getData();
		
		_002_Secondclass sc = new _002_Secondclass();
		sc.setData();
	}

}
