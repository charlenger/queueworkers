package org.cahung.it.queueworkers.workers;

import java.util.List;

import org.cahung.it.queueworkers.work.Batch;

public interface Workforce {
	int pushWork(Batch batch);

	int getQueueSize();

	List<Team> getTeams();
}
