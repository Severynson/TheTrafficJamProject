public class Warning {

	private String message;

	public Warning(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "\u001B[33m" + message + "\u001B[0m";
	}
	
	public void print() {
		System.out.println(this);
	}
}