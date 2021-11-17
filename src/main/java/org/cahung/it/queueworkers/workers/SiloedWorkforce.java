package org.cahung.it.queueworkers.workers;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.cahung.it.queueworkers.work.WorkItem;

public class SiloedWorkforce extends QueueBasedBatchWorkforce {
	private Map<Integer, LinkedList<Team>> teams;
	private SecureRandom random = new SecureRandom();
	private int numberOfTeams;

	public SiloedWorkforce(int numberOfTeams, int performanceLevel, TeamPerformanceType type, int[] categories) {
		super();
		this.numberOfTeams = numberOfTeams;
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
				LinkedList<Team> categoryTeams = teams.get(Integer.valueOf(item.getCategory()));
				categoryTeams.add(team);
				teams.put(Integer.valueOf(item.getCategory()), categoryTeams);
			}
		}
		for (WorkItem item : done) {
			workInProgressMapping.remove(item);
		}
	}

	private void addWorkInProgress() {
		if (workingTeams.size() < numberOfTeams) {
			WorkItem item = queue.pollLast();
			LinkedList<Team> teamsForCategory = teams.get(Integer.valueOf(item.getCategory()));
			if (!teamsForCategory.isEmpty()) {
				Team team = teamsForCategory.pop();
				workInProgress.add(item);
				workingTeams.add(team);
				workInProgressMapping.put(item, team);
			} else {
				queue.addLast(item);
			}
		}
	}
}
