package org.cahung.it.queueworkers.workers;

import java.util.List;
import java.util.Set;

import org.cahung.it.queueworkers.work.Batch;
import org.cahung.it.queueworkers.work.WorkItem;

public interface Workforce {
	int pushWork(Batch batch);

	int getQueueSize();

	List<Team> getTeams();

	void doWorkCycle();

	void processQueue(int maxIterations);

	Set<WorkItem> getWorkInProgress();

	Set<Team> getWorkingTeams();

}
