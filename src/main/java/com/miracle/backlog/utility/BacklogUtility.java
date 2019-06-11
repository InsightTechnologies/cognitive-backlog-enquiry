package com.miracle.backlog.utility;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.miracle.backlog.exception.BacklogException;
import com.miracle.database.bean.Release;
import com.miracle.database.bean.Resource;
import com.miracle.database.bean.Sprint;
import com.miracle.database.bean.Status;
import com.miracle.database.bean.Team;
import com.miracle.utility.DataUtility;

@Component
public class BacklogUtility {

	@Autowired
	private DataUtility dataUtility;

	public List<Integer> getStoryStateList(List<Status> stateList) {

		List<Integer> storyStateList = new ArrayList<>();
		for (Status status : stateList) {
			if ((status.getObject().equalsIgnoreCase("Story") && status.getType().equalsIgnoreCase("state"))
					&& (status.getStatusValue().equalsIgnoreCase("estimated"))) {
				storyStateList.add(status.getStatusId());
			}
		}
		return storyStateList;

	}

	public List<Integer> getFeatureStateList(List<Status> stateList) {
		List<Integer> featureStateList = new ArrayList<>();
		for (Status status : stateList) {
			if ((status.getObject().equalsIgnoreCase("Feature") && status.getType().equalsIgnoreCase("state"))
					&& (status.getStatusValue().equalsIgnoreCase("todo")
							|| status.getStatusValue().equalsIgnoreCase("in progress"))) {
				featureStateList.add(status.getStatusId());
			}
		}
		return featureStateList;
	}

	public int getMaxStoryPoints(Release release, double version) throws BacklogException {
		try {
			int numOfSprints = release.getNoOfSprints();
			String teamName = release.getTeamName();

			Sprint sprint = dataUtility.getSprintList().get(0);
			int sprintDuration = sprint.getDuration();

			List<Resource> resourceList = dataUtility.getResourceList();
			int resourceCount = 0;
			for (Resource resource : resourceList) {
				if (resource.getTeamName().equalsIgnoreCase(teamName)) {
					resourceCount++;
				}
			}

			int totalWorkingHours = numOfSprints * sprintDuration * resourceCount * 8;

			Team team = dataUtility.loadTeam(teamName);
			int velocity = team.getVelocity();
			int workingHours = team.getWorkingHours();
			double velocityRatio = workingHours / velocity;
			int maxStorypoints = (int) (totalWorkingHours * velocity / workingHours);
			return maxStorypoints;
		} catch (Exception exception) {
			throw new BacklogException(exception, "Failed to calculate maximum story points");
		}
	}

}
