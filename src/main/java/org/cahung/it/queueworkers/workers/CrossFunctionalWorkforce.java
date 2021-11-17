package org.cahung.it.queueworkers.workers;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.cahung.it.queueworkers.work.WorkItem;

public class CrossFunctionalWorkforce extends QueueBasedBatchWorkforce {
	private LinkedList<Team> teams;
	private SecureRandom random = new SecureRandom();

	public CrossFunctionalWorkforce(int numberOfTeams, int performanceLevel, TeamPerformanceType type,
			int[] categories) {
		super();
		teams = new LinkedList<Team>();
		buildTeamsList(numberOfTeams, performanceLevel, type, categories);
	}

	private void buildTeamsList(int numberOfTeams, int performanceLevel, TeamPerformanceType type, int[] categories) {
		for (int i = 0; i < numberOfTeams; ++i) {
			int performance;
			switch (type) {
			case CAPPED:
				performance = random.nextInt(performanceLevel);
				break;
			case FIXED:
			default:
				performance = performanceLevel;
			}
			Team team = new Team(performance, categories);
			teams.add(team);
		}
	}

	@Override
	public List<Team> getTeams() {
		return teams;
	}

	@Override
	public void doWorkCycle() {
		synchronized (queue) {
			runWorkInProgress();
			addWorkInProgress();
		}
	}

	private void runWorkInProgress() {
		List<WorkItem> done = new ArrayList<>();
		for (Map.Entry<WorkItem, Team> wip : workInProgressMapping.entrySet()) {
			Team team = wip.getValue();
			WorkItem item = wip.getKey();
			team.workOn(item);
			if (item.getWorkload() <= 0) {
				done.add(item);
				workInProgress.remove(item);
				workingTeams.remove(team);
				teams.add(team);
			}
		}
		for (WorkItem item : done) {
			workInProgressMapping.remove(item);
		}

	}

	private void addWorkInProgress() {
		if (!teams.isEmpty()) {
			WorkItem item = queue.pollLast();
			Team team = teams.pop();
			workInProgress.add(item);
			workingTeams.add(team);
			workInProgressMapping.put(item, team);
		}
	}

}
