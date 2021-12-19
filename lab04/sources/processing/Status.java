package processing;
public class Status {
	
	/**
	 * name of the task
	 */
	private String taskName;	
	/**
	 * progress in percents
	 */
	private int progress;

	public int getProgress() {
		return progress;
	}

	public String getTaskName() {
		return taskName;
	}

	public Status(String taskName, int progress) {
		this.taskName = taskName;
		this.progress = progress;
	}
}
