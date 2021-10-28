package org.cahung.it.queueworkers.workers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.UUID;

import org.cahung.it.queueworkers.work.WorkItem;
import org.junit.jupiter.api.Test;

public class TeamTest {
	@Test
	public void teamFormation() {
		Team team = new Team(5, new int[] { 1, 2, 3 });
		assertTrue(null != UUID.fromString(team.getId().toString()));
		assertEquals(5, team.getPerformanceLevel());

		WorkItem item1 = new WorkItem(10, 1);
		WorkItem item2 = new WorkItem(10, 2);
		WorkItem item3 = new WorkItem(10, 3);
		WorkItem item4 = new WorkItem(10, 4);
		assertTrue(team.canWorkOn(item1.getCategory()));
		assertTrue(team.canWorkOn(item2.getCategory()));
		assertTrue(team.canWorkOn(item3.getCategory()));
		assertFalse(team.canWorkOn(item4.getCategory()));

		team.workOn(item1);
		assertEquals(5, item1.getWorkload());
		team.workOn(item4);
		assertEquals(10, item4.getWorkload());
	}
}
