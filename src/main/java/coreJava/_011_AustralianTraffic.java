package coreJava;

public class _011_AustralianTraffic implements _010_InterfaceCentralTraffic{

	public static void main(String[] args) {
		
		_010_InterfaceCentralTraffic at = new _011_AustralianTraffic(); // initialize class object
		at.redStop(); 
		at.greenGo();
		at.YellowWait();
	}

	/*Add interface via 'implements'
		Need to define all the methods
	*/
	
	@Override
	public void greenGo() {

		System.out.println("Green: Go");
	}

	@Override
	public void redStop() {

		System.out.println("Red: Stop");
		
	}

	@Override
	public void YellowWait() {

		System.out.println("Yellow: Wait");
		
	}

}
