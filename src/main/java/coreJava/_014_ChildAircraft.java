package coreJava;

public class _014_ChildAircraft extends _013_AbstractParentAirCraft{

	public static void main(String[] args) {
		_013_AbstractParentAirCraft obj = new _014_ChildAircraft();
		obj.color();
		obj.engine();	
	}

	@Override
	public void color() { // Compulsory to add all unimplemented methods
		System.out.println("Unimplemented in abstract");
	}

}
