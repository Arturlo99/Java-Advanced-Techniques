package example;

import processing.Status;
import processing.StatusListener;

public class MyStatusListener implements StatusListener {
    // listener jest znany podczas kompilacji projektu
	// mo�na wi�c wi�za� si� w nim z interfejsem u�ytkownika
    private int progress;
    
	@Override
	public void statusChanged(Status s) {
			progress = s.getProgress();
	}

	public int getProgress() {
		return progress;
	}

}
