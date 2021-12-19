package textmodifiers;


import processing.Status;
import processing.StatusListener;

public class RemoveDots implements processing.Processor, Runnable {

	private String result;
	public StatusListener sl;
	public String input;

	
	@Override
	public boolean submitTask(String task, StatusListener sl) {
        this.sl = sl;
        input = task;
        result = "";
        Thread thread = new Thread(this);
        thread.start();
        return true;
	}

	@Override
	public String getInfo() {
		return "Delete dots from text";
	}

	@Override
	public String getResult() {
		return this.result.toString();
	}

	@Override
	public void run() {
		int progressDivider = input.length() / 20;
        for(int i = 0; i < input.length(); i++) {
			if (input.charAt(i) == '.') {
				result += (' ');
			}
			else {
				result += (input.charAt(i));
			}
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if((i % progressDivider) == 0)
                sl.statusChanged(new Status("Delete dots from text", i * 100 / input.length()));
        }
        sl.statusChanged(new Status("Delete dots from text", 100));
		
	}


}
