package org.cahung.it.queueworkers.work;

public interface Batch {
	WorkItem pull();

	boolean isEmpty();

	int getSize();
}
