package org.cahung.it.queueworkers.performance;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.cahung.it.queueworkers.work.Batch;
import org.cahung.it.queueworkers.work.RandomBatch;
import org.cahung.it.queueworkers.workers.CrossFunctionalWorkforce;
import org.cahung.it.queueworkers.workers.SiloedWorkforce;
import org.cahung.it.queueworkers.workers.TeamPerformanceType;
import org.cahung.it.queueworkers.workers.Workforce;

public class PerformanceAnalyzer {

	public static void main(String[] args) throws IOException {
		// Defines categories
		int numberOfCategories = 10;
		int[] categories = new int[numberOfCategories];
		for (int i = 0; i < numberOfCategories; ++i) {
			categories[i] = i + 1;
		}
		// Creates and Duplicates the work batches for comparisons
		int batchSize = 10000;
		int maxWorkload = 100;
		Batch batch1 = new RandomBatch(batchSize, maxWorkload, numberOfCategories);
		Batch batch2 = new RandomBatch(batch1);
		System.out.println("Created identical batches: " + batch1.getSize() + " = " + batch2.getSize());
		// Create the two workforces
		int numberOfTeams = 10;
		int teamMaxPerformance = 10;
		float modifier = 2.0f;
		Workforce siloed = new SiloedWorkforce(numberOfTeams, (int) (teamMaxPerformance * modifier),
				TeamPerformanceType.FIXED, categories);
		Workforce xfunc = new CrossFunctionalWorkforce(numberOfTeams, teamMaxPerformance, TeamPerformanceType.FIXED,
				categories);
		// Push work to workforces
		siloed.pushWork(batch1);
		xfunc.pushWork(batch2);
		// Initialize writers
		File currentDirectory = new File(".");
		System.out.println("Current path: " + currentDirectory.getAbsolutePath());
		try (FileWriter siloedWriter = new FileWriter(new File(currentDirectory, "siloed.csv"));
				FileWriter xfuncWriter = new FileWriter(new File(currentDirectory, "xfunc.csv"))) {
			// write headers
			String headers = "Cycle; TotalWork; QueueSize; WIP; TotalTeams; BusyTeams;\n";
			siloedWriter.write(headers);
			xfuncWriter.write(headers);
			// Run a maximum number of cycles
			int maxCycles = 10000;
			for (int i = 0; i < maxCycles; ++i) {
				siloed.doWorkCycle();
				xfunc.doWorkCycle();
				siloedWriter.write(
						i + ";" + batchSize + ";" + siloed.getQueueSize() + ";" + siloed.getWorkInProgress().size()
								+ ";" + numberOfTeams + ";" + siloed.getWorkingTeams().size() + ";\n");
				xfuncWriter
						.write(i + ";" + batchSize + ";" + xfunc.getQueueSize() + ";" + xfunc.getWorkInProgress().size()
								+ ";" + numberOfTeams + ";" + xfunc.getWorkingTeams().size() + ";\n");
			}
		}
		System.out.println("Execution done");
	}
}
