package org.cahung.it.queueworkers.workers;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class SiloedWorkforce extends QueueBasedBatchWorkforce {
	private Map<Integer, LinkedList<Team>> teams;
	private SecureRandom random = new SecureRandom();

	public SiloedWorkforce(int numberOfTeams, int performanceLevel, TeamPerformanceType type, int[] categories) {
		super();
		int currentCategoryIdx = 0;
		this.teams = new HashMap<>();
		for (int i = 0; i < numberOfTeams; ++i) {
			Integer currentCategory = Integer.valueOf(categories[currentCategoryIdx]);
			addNewTeamToMap(performanceLevel, type, currentCategory);
			currentCategoryIdx = currentCategoryIdx < categories.length - 1 ? currentCategoryIdx + 1 : 0;
		}
	}

	private void addNewTeamToMap(int performanceLevel, TeamPerformanceType type, Integer currentCategory) {
		LinkedList<Team> currentList = teams.get(currentCategory);
		if (currentList == null) {
			currentList = new LinkedList<>();
		}
		currentList.add(buildTeam(performanceLevel, type, currentCategory));
		teams.put(currentCategory, currentList);
	}

	private Team buildTeam(int performanceLevel, TeamPerformanceType type, int currentCategory) {
		int performance;
		switch (type) {
		case CAPPED:
			performance = random.nextInt(performanceLevel);
			break;
		case FIXED:
		default:
			performance = performanceLevel;
		}
		return new Team(performance, new int[] { currentCategory });
	}

	@Override
	public List<Team> getTeams() {
		List<Team> teamsList = new ArrayList<>();
		for (List<Team> list : teams.values()) {
			teamsList.addAll(list);
		}
		return teamsList;
	}
}
