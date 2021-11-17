package org.cahung.it.queueworkers.workers;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.cahung.it.queueworkers.work.WorkItem;

public class Team {
	private UUID id;
	private Set<Integer> categories;
	private int performanceLevel;

	public Team(int performanceLevel, int[] categories) {
		this.id = UUID.randomUUID();
		this.performanceLevel = performanceLevel;
		this.categories = new HashSet<>();
		for (int i : categories) {
			this.categories.add(i);
		}
	}

	public int getPerformanceLevel() {
		return this.performanceLevel;
	}

	public boolean canWorkOn(int category) {
		return categories.contains(category);
	}

	public WorkItem workOn(WorkItem item) {
		if (canWorkOn(item.getCategory())) {
			int newWorkload = item.getWorkload() - performanceLevel;
			newWorkload = newWorkload < 0 ? 0 : newWorkload;
			item.setWorkload(newWorkload);
		}
		return item;
	}

	public UUID getId() {
		return this.id;
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Team)) {
			return false;
		}
		return id.equals(((Team) obj).id);
	}
}
