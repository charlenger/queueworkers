package org.cahung.it.queueworkers.work;

public class WorkItem {
	private int workload;
	private int category;

	public WorkItem(int workload, int category) {
		this.workload = workload;
		this.category = category;
	}

	public int getWorkload() {
		return this.workload;
	}

	public int getCategory() {
		return this.category;
	}

	public void setWorkload(int workload) {
		this.workload = workload;
	}

	public void setCategory(int category) {
		this.category = category;
	}
}
