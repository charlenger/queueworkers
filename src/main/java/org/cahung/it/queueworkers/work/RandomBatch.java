package org.cahung.it.queueworkers.work;

import java.security.SecureRandom;
import java.util.LinkedList;

public class RandomBatch implements Batch {
	private int size;
	private int maxWorkload;
	private int maxCategories;
	private LinkedList<WorkItem> queue;

	public RandomBatch(int size, int maxWorkload, int maxCategories) {
		super();
		this.size = size;
		this.maxWorkload = maxWorkload;
		this.maxCategories = maxCategories;
		initializeQueue();
	}

	private void initializeQueue() {
		queue = new LinkedList<WorkItem>();
		SecureRandom random = new SecureRandom();
		for (int i = 0; i < size; ++i) {
			WorkItem item = new WorkItem(random.nextInt(maxWorkload), random.nextInt(maxCategories));
			queue.add(item);
		}
	}

	@Override
	public WorkItem pull() {
		return queue.poll();
	}

	@Override
	public boolean isEmpty() {
		return queue.isEmpty();
	}

	@Override
	public int getSize() {
		return queue.size();
	}

}
