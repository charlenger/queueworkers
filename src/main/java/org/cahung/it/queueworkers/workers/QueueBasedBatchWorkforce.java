package org.cahung.it.queueworkers.workers;

import java.util.LinkedList;

import org.cahung.it.queueworkers.work.Batch;
import org.cahung.it.queueworkers.work.WorkItem;

public abstract class QueueBasedBatchWorkforce implements Workforce {
	private LinkedList<WorkItem> queue;

	public QueueBasedBatchWorkforce() {
		queue = new LinkedList<>();
	}

	@Override
	public int pushWork(Batch batch) {
		synchronized (queue) {
			while (!batch.isEmpty()) {
				queue.push(batch.pull());
			}
			return queue.size();
		}
	}

	@Override
	public int getQueueSize() {
		synchronized (queue) {
			return queue.size();
		}
	}
}
