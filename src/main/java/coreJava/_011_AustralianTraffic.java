package coreJava;

public class _011_AustralianTraffic implements _010_InterfaceCentralTraffic,_012_InterfaceContinental {

	// One class can implement multiple interfaces
	public static void main(String[] args) {
		
		_010_InterfaceCentralTraffic at = new _011_AustralianTraffic(); // initialize class object> works only for methods present in _010_InterfaceCentralTraffic
		
		at.redStop(); 
		at.greenGo();
		at.YellowWait();
		//at.otherThanInterface(); ---> Doesn't exist
		
		_011_AustralianTraffic obj = new _011_AustralianTraffic();
		obj.otherThanInterface();
		
		_012_InterfaceContinental ic = new _011_AustralianTraffic();
		ic.trainSymbol();
		
	}

	/*Add interface via 'implements'
		Need to define all the methods
	*/
	
	public void otherThanInterface() {
		System.out.println("Method not in interface");
	}
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

	@Override
	public void trainSymbol() {
		System.out.println("Other Interface: Train Symbol");
		
	}

}
