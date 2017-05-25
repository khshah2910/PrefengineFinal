package com.prefengine.controller;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.prefengine.domain.Itinerary;
import com.prefengine.domain.SearchAttributes;
import com.prefengine.service.SearchCriteria;
import com.prefengine.service.SearchService;
import com.prefengine.service.compilerForPF.CostFunctionType;
import com.prefengine.service.compilerForPF.DurationFunctionType;
import com.prefengine.service.compilerForPF.LeaveAndArriveFunctionType;
import com.prefengine.service.compilerForPF.NumberofStopFunctionType;
import com.prefengine.service.compilerForPF.Parser;
import com.prefengine.service.compilerForPF.Scanner;
import com.prefengine.service.compilerForPF.SeatClassFunctionType;

/**
 * Servlet implementation class SearchControllerForParser
 */
@WebServlet("/SearchControllerForParser")
public class SearchControllerForParser extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchControllerForParser() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		SearchCriteria searchCriteriaFromSentence = new SearchCriteria();
		SearchService serviceFromSentence = new SearchService();
		String outputString = "";
		SearchAttributes searchAttrFromSentence = null;
		ArrayList<Itinerary> tripRecordFromSentence = null;
		String sentenceInput = "eg: I want flight from boston to new york....";
		sentenceInput =request.getParameter("requirementSentence");
		ArrayList<String> non_functional_attributes = new ArrayList<String>();
		if (!sentenceInput.equals("eg: I want flight from boston to new york....")) {
			// when there is sentences input from UI
			if (request.getParameter("getCompilerReview") != null) {

				Scanner scanner = new Scanner(sentenceInput);
				scanner.scannerEngine();
				scanner.printMessage();
				Parser parser = new Parser(scanner);
				
				parser.parserEngine();
				outputString = parser.generateMessageForUI();
			} else {
				searchCriteriaFromSentence.setNumberOfPassengers(1);
				Scanner scanner = new Scanner(sentenceInput);
				scanner.scannerEngine();
				scanner.printMessage();
				Parser parser = new Parser(scanner);
				ArrayList<ArrayList<Object>> resultFromCompiler = parser.parserEngine();
				parser.printMessage();
				non_functional_attributes = parser.getNonFunctionalAttributeArrayList();
				// ===============================================================================

				// int actualStops = Integer.parseInt(stops);
				// String price =request.getParameter("price");
				searchCriteriaFromSentence.setNonFunctionalAttributes(non_functional_attributes);
				// even user request round trip, still only use requirements
				// for single trip for now
				if (resultFromCompiler.size() != 0) {
					for (Object content : resultFromCompiler.get(0)) {
						if (content instanceof LeaveAndArriveFunctionType) {
							LeaveAndArriveFunctionType landaInstance = (LeaveAndArriveFunctionType) content;
							if (landaInstance.getArrivePlace() != null)
								searchCriteriaFromSentence
										.setDestination(landaInstance.getArrivePlace().getImage().toUpperCase());
							if (landaInstance.getLeavePlace() != null)
								searchCriteriaFromSentence
										.setDeparture(landaInstance.getLeavePlace().getImage().toUpperCase());
							if (landaInstance.getLeaveDay() != null)
								searchCriteriaFromSentence.setDepartureDate(landaInstance.getLeaveDay().toString());
							else if (landaInstance.getArriveDay() != null)
								searchCriteriaFromSentence
										.setDepartureDate(landaInstance.getArriveDay().toString());
							else {
								Date date = new Date();
								SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd");
								String currentDate = "2018-05-03";
								searchCriteriaFromSentence.setDepartureDate(currentDate);

							}
						} else if (content instanceof CostFunctionType) {
							CostFunctionType costInstance = (CostFunctionType) content;
							if (costInstance.hasPossibility() == true) {
								// if there is only general price range
								// requested by user, the max and min price
								// will be same, this is the sign of general
								// price range
								searchCriteriaFromSentence.setMaxPrice(costInstance.getPriceInPossibility());
								searchCriteriaFromSentence.setMinPrice(costInstance.getPriceInPossibility());

							} else {
								searchCriteriaFromSentence.setMaxPrice(costInstance.getPriceRange()[1]);
								searchCriteriaFromSentence.setMinPrice(costInstance.getPriceRange()[0]);
							}
						} else if (content instanceof DurationFunctionType) {
							DurationFunctionType durationInstance = (DurationFunctionType) content;
							if (durationInstance.hasPossibility() == true) {
								// if there is only general duration range
								// requested by user, the max and min price
								// will be same, this is the sign of general
								// price range
								searchCriteriaFromSentence
										.setMinDuration((int) durationInstance.getDuationInPossibility());
								searchCriteriaFromSentence
										.setMaxDutation((int) durationInstance.getDuationInPossibility());

							} else {
								searchCriteriaFromSentence
										.setMaxDutation((int) durationInstance.getDuationInHour()[1]);
								searchCriteriaFromSentence
										.setMinDuration((int) durationInstance.getDuationInHour()[0]);
							}
						} else if (content instanceof NumberofStopFunctionType) {
							NumberofStopFunctionType stopsInstance = (NumberofStopFunctionType) content;
							switch (stopsInstance.getNumberOfStop()) {
							case 0:
								searchCriteriaFromSentence.setNonStop(true);
							case 1:
								searchCriteriaFromSentence.setOneStop(true);
								searchCriteriaFromSentence.setNonStop(true);
							default:
								searchCriteriaFromSentence.setTwoOrMoreStop(true);
								searchCriteriaFromSentence.setOneStop(true);
								searchCriteriaFromSentence.setNonStop(true);

							}

						}
						/*
						 * else if(content instanceof MileageFunctionType) {
						 * MileageFunctionType mileageInstance =
						 * (MileageFunctionType)content; if(
						 * mileageInstance.hasPossibility() == true) { //if
						 * there is only general price range requested by
						 * user, the max and min price will be same, this is
						 * the sign of general price range
						 * searchCriteriaFromSentence.setMaxMileage(
						 * mileageInstance.getMileageInPossibility());
						 * searchCriteriaFromSentence.setMinMileage(
						 * mileageInstance.getMileageInPossibility());
						 * 
						 * } else {
						 * searchCriteriaFromSentence.setMaxMileage(
						 * mileageInstance.getMileage()[1]);
						 * searchCriteriaFromSentence.setMinMileage(
						 * mileageInstance.getMileage()[0]);
						 * 
						 * }
						 * 
						 * } else if(content instanceof LayoutFunctionType)
						 * { LayoutFunctionType layoutInstance =
						 * (LayoutFunctionType)content; if(
						 * layoutInstance.hasPossibility() == true) { //if
						 * there is only general price range requested by
						 * user, the max and min price will be same, this is
						 * the sign of general price range
						 * searchCriteriaFromSentence.setMin(layoutInstance.
						 * getLayoutInPossibility());
						 * searchCriteriaFromSentence.setMaxLayout(
						 * layoutInstance.getLayoutInPossibility());
						 * 
						 * } else { searchCriteriaFromSentence.setMinLayout(
						 * layoutInstance.getLayoutInHour()[1]);
						 * searchCriteriaFromSentence.setMaxLayout(
						 * layoutInstance.getLayoutInHour()[01]); }
						 * 
						 * }
						 */
						else if (content instanceof SeatClassFunctionType) {
							SeatClassFunctionType seatclassInstance = (SeatClassFunctionType) content;
							switch (seatclassInstance.getSeatClass()) {

							case "BUSINESS":
								searchCriteriaFromSentence.setBusiness(true);
							case "FIRST":
								searchCriteriaFromSentence.setFirst(true);
							default:
								searchCriteriaFromSentence.setEconomy(true);
							}
						}
						/*
						 * else if(content instanceof
						 * ReputationFunctionType) { ReputationFunctionType
						 * reputationInstance =
						 * (ReputationFunctionType)content;
						 * searchCriteriaFromSentence.setRankElements(
						 * reputationInstance.getRankElements()); }
						 */

					}
					searchCriteriaFromSentence.setReturnDate(searchCriteriaFromSentence.getDepartureDate());
					searchAttrFromSentence = serviceFromSentence.getSearchAttributes(searchCriteriaFromSentence);
					try {
						tripRecordFromSentence = serviceFromSentence.search(searchCriteriaFromSentence);
					} catch (GeneralSecurityException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
//					
				}
			}

		}
		
		request.setAttribute("searchAttr", searchAttrFromSentence);
		request.setAttribute("tripRecord", tripRecordFromSentence);
		request.setAttribute("outputOfReview", outputString);
		request.setAttribute("sentenceRequest", sentenceInput);
		RequestDispatcher rd = request.getRequestDispatcher("./web/review.jsp");
		rd.forward(request, response);
		//response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
