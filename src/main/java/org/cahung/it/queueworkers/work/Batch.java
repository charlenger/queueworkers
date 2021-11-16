package org.cahung.it.queueworkers.work;

import java.util.List;

public interface Batch {
	List<WorkItem> getQueue();
	
	WorkItem pull();

	boolean isEmpty();

	int getSize();
}
