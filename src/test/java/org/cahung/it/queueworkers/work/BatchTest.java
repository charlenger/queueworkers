package org.cahung.it.queueworkers.work;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class BatchTest {

	@Test
	public void testWorkItemsBatch() {
		Batch batch = new RandomBatch(10, 10, 3);
		assertEquals(10, batch.getSize());
		while (!batch.isEmpty()) {
			WorkItem item = batch.pull();
			assertTrue(item.getWorkload() < 10);
			assertTrue(item.getCategory() < 3);
		}
		assertEquals(0, batch.getSize());
	}
}
