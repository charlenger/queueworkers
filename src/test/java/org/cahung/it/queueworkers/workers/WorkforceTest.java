package org.cahung.it.queueworkers.workers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.cahung.it.queueworkers.work.Batch;
import org.cahung.it.queueworkers.work.RandomBatch;
import org.junit.jupiter.api.Test;

public class WorkforceTest {
	@Test
	public void testWorkforce() {
		Workforce siloedWorkforce = new SiloedWorkforce(10, 10, TeamPerformanceType.FIXED, new int[] { 1, 2, 3 });
		Workforce xfuncWorkforce = new CrossFunctionalWorkforce(10, 10, TeamPerformanceType.FIXED,
				new int[] { 1, 2, 3 });
		Batch batch1 = new RandomBatch(10, 10, 3);
		Batch batch2 = new RandomBatch(batch1);
		assertEquals(10, siloedWorkforce.pushWork(batch1));
		assertEquals(10, siloedWorkforce.getQueueSize());
		assertEquals(10, siloedWorkforce.getTeams().size());
		for (Team team : siloedWorkforce.getTeams()) {
			assertEquals(10, team.getPerformanceLevel());
		}
		assertEquals(10, xfuncWorkforce.pushWork(batch2));
		assertEquals(10, xfuncWorkforce.getQueueSize());
		assertEquals(10, xfuncWorkforce.getTeams().size());
		for (Team team : xfuncWorkforce.getTeams()) {
			assertEquals(10, team.getPerformanceLevel());
		}
	}
}
