package processing;
public interface StatusListener {
	/**
	 * Metoda dostarczająca słuchaczowi status przetwarzania zadania
	 * s - status przetwarzania zadania
	 */
	void statusChanged(Status s);
}
