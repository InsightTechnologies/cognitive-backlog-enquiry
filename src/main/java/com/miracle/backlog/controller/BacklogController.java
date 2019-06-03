package com.miracle.backlog.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.miracle.backlog.exception.BacklogException;
import com.miracle.backlog.utility.BacklogUtility;
import com.miracle.cognitive.response.FeatureResponse;
import com.miracle.database.bean.Filter;
import com.miracle.database.bean.Release;
import com.miracle.database.bean.Status;
import com.miracle.scrum.bean.FeatureWithEstimates;
import com.miracle.utility.DataUtility;

@RestController
public class BacklogController {

	@Autowired
	private DataUtility dataUtility;

	@Autowired
	private BacklogUtility backlogUtility;

	@GetMapping(value = "/backlog")
	public ResponseEntity<FeatureResponse> buildFeatures(@RequestParam(value = "version") double version) {
		FeatureResponse response = new FeatureResponse();

		try {
			Release release = dataUtility.loadRelease(version);
			if (release == null) {
				response.setObject("Invalid version provided");
				response.setSuccess(false);
				return new ResponseEntity<FeatureResponse>(response, HttpStatus.BAD_REQUEST);
			}
			String projectName = release.getProjectName();
			List<Filter> filterList = dataUtility.getFilterList();

			List<Status> stateList = dataUtility.getStatusList();
			List<Integer> featureStateList = backlogUtility.getFeatureStateList(stateList);
			List<Integer> storyStateList = backlogUtility.getStoryStateList(stateList);

			int maxStorypoints = backlogUtility.getMaxStoryPoints(release, version);

			Object gatewayResponse = invokeReleasePlanService(maxStorypoints, filterList, projectName, featureStateList,
					storyStateList);

			response.setObject(gatewayResponse);
			response.setSuccess(true);
			return new ResponseEntity<FeatureResponse>(response, HttpStatus.OK);
		} catch (Exception exception) {
			exception.printStackTrace();
			response.setObject("Failed to build Backlog features");
			response.setSuccess(false);
			return new ResponseEntity<FeatureResponse>(response, HttpStatus.BAD_GATEWAY);
		}
	}

	private Object invokeReleasePlanService(int maxStorypoints, List<Filter> filterList, String projectName,
			List<Integer> featureStateList, List<Integer> storyStateList) throws BacklogException {
		try {
//			APIMicroServiceBean microServiceBean = new APIMicroServiceBean();
//			microServiceBean.setFeatureStateList(featureStateList);
//			microServiceBean.setStoryStateList(storyStateList);
//			microServiceBean.setMaxStoryPoint(maxStorypoints);
//			microServiceBean.setFilterType(filterList.get(0).getFilterType());
//			microServiceBean.setProjectName(projectName);

			// Invoke API Gateway with Feature state list, story state list, max story
			// points, filter type, project name
//			HttpEntity<APIMicroServiceBean> request = new HttpEntity<>(microServiceBean);
			String resourceUrl = "/masterBot/project/releasePlan";
//			RestTemplate restTemplate = new RestTemplate();
//			return restTemplate.postForObject(resourceUrl, request, Object.class);

			FeatureWithEstimates featureWithEstimates = new FeatureWithEstimates();
			featureWithEstimates.setEffort(20);
			featureWithEstimates.setFeatureID(1);
			featureWithEstimates.setFeatureName("test");
			featureWithEstimates.setUid(1);
			List<FeatureWithEstimates> list = new ArrayList<>();
			list.add(featureWithEstimates);
			return list;
		} catch (Exception exception) {
			throw new BacklogException(exception, "Failed to invoke Release plan service and retrieve response");
		}

	}

}
