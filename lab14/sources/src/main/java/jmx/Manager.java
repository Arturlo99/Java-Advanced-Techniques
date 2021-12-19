package jmx;

import queue.system.CaseCategory;

import javax.management.Notification;
import javax.management.NotificationBroadcasterSupport;
import java.util.ArrayList;
import java.util.List;

public class Manager extends NotificationBroadcasterSupport implements ManagerMXBean {

	private List<CaseCategory> caseCategoryList = new ArrayList<>();
	private long notificationCounter = 0L;

	@Override
	public List<CaseCategory> getCategoryList() {
		return caseCategoryList;
	}

	@Override
	public void setCategoryList(List<CaseCategory> caseCategoryList) {
		this.caseCategoryList = caseCategoryList;
		this.sendNotification("Case category list has been edited!");
	}

	private void sendNotification(String msg) {
		Notification notification = new Notification("notification", this, notificationCounter++, msg);
		sendNotification(notification);
	}
}
