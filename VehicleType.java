/**
 * This file has been implemented for you. You only need to use it.
 * Rather than declare a variable of type int or char, we will declare one of type
 * VehicleType when we want to store the type of Vehicle.
 * 
 * @author Osvaldo
 *
 */
public enum VehicleType {
	MYCAR, TRUCK, AUTO;
	
	public String toString() {
		switch(this) {
			case MYCAR: return "car";
			case TRUCK: return "truck";
			case AUTO: return "auto";
		}
		return "n/a";
	}
}
