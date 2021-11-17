package org.cahung.it.queueworkers.workers;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import org.cahung.it.queueworkers.work.Batch;
import org.cahung.it.queueworkers.work.WorkItem;

public abstract class QueueBasedBatchWorkforce implements Workforce {
	protected LinkedList<WorkItem> queue;
	protected Set<WorkItem> workInProgress;
	protected Set<Team> workingTeams;
	protected Map<WorkItem, Team> workInProgressMapping;

	public QueueBasedBatchWorkforce() {
		queue = new LinkedList<>();
		workingTeams = new HashSet<>();
		workInProgress = new HashSet<>();
		workInProgressMapping = new HashMap<>();
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

	@Override
	public void processQueue(int maxIterations) {
		synchronized (queue) {
			int iterations = 0;
			while (!queue.isEmpty() && iterations < maxIterations) {
				iterations++;
				doWorkCycle();
			}
		}
	}

	@Override
	public Set<Team> getWorkingTeams() {
		return workingTeams;
	}

	@Override
	public Set<WorkItem> getWorkInProgress() {
		return workInProgress;
	}

	@Override
	public abstract void doWorkCycle();
}
