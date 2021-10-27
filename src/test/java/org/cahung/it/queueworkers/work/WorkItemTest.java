package org.cahung.it.queueworkers.work;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class WorkItemTest {

	@Test
	public void testWorkloadAndCategory() {
		WorkItem item = new WorkItem(5, 6);
		assertEquals(5, item.getWorkload());
		assertEquals(6, item.getCategory());
		WorkItem item2 = new WorkItem(3, 2);
		assertEquals(3, item2.getWorkload());
		assertEquals(2, item2.getCategory());
	}
}
