package org.cahung.it.queueworkers.work;

import java.util.UUID;

public class WorkItem {
	private UUID uuid;
	private int workload;
	private int category;

	public WorkItem(int workload, int category) {
		this.workload = workload;
		this.category = category;
		this.uuid = UUID.randomUUID();
	}

	public WorkItem(WorkItem item) {
		this.workload = item.getWorkload();
		this.category = item.getCategory();
		this.uuid = item.getUuid();
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

	public UUID getUuid() {
		return uuid;
	}

	@Override
	public int hashCode() {
		return uuid.hashCode();
	}

	@Override
	public String toString() {
		return uuid.toString() + " - Workload: " + workload + " | category: " + category;
	}

	@Override
	public boolean equals(Object itemObj) {
		if (!(itemObj instanceof WorkItem)) {
			return false;
		}
		return uuid.equals(((WorkItem) itemObj).getUuid());
	}
}
