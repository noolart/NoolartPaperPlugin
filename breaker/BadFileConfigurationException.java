package breaker;

class BadFileConfigurationException extends Exception {
	public BadFileConfigurationException(String message) {
		super(message);
	}
	
	private static final long serialVersionUID = 1L;
}