package org.cahung.it.queueworkers.workers;

import java.security.SecureRandom;
import java.util.LinkedList;
import java.util.List;

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
}
