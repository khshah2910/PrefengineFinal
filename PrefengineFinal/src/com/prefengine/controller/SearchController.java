package com.prefengine.controller;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.prefengine.domain.Itinerary;
import com.prefengine.domain.NonFunctionalAttributes;
import com.prefengine.domain.SearchAttributes;
import com.prefengine.service.APIService;
import com.prefengine.service.NonFunctionalParser;
import com.prefengine.service.SearchCriteria;
import com.prefengine.service.SearchService;
import com.prefengine.util.DurationComparator;
import com.prefengine.util.MilageComarator;
import com.prefengine.util.PriceComparator;
import com.prefengine.util.StopsComparator;

/**
 * Servlet implementation class SearchController
 */
@WebServlet("/SearchController")
public class SearchController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SearchController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Object sessionTripRecord = session.getAttribute("tripRecord");
		Object sessionSearchAttr = session.getAttribute("searchAttr");
		String order = request.getParameter("orderBy");
		if(order!= null||"".equals(order)){
			
			
			
			ArrayList<Itinerary> itirnerary = (ArrayList<Itinerary>) sessionTripRecord;
			
			System.out.println("-->>--<< "+order);
			//	itirnerary.sort(new PriceComparator());
			if(order!=null){
				if(order.equals("price")){
					itirnerary.sort(new PriceComparator());
				}
				if(order.equals("stops")){
					itirnerary.sort(new StopsComparator());
				}
				if(order.equals("milage")){
					itirnerary.sort(new MilageComarator());
				}
				if(order.equals("duration")){
					itirnerary.sort(new DurationComparator());
				}
			}
			session.setAttribute("tripRecord",itirnerary);
			RequestDispatcher rd = request.getRequestDispatcher("./web/search_result.jsp");
			rd.forward(request, response);
			return;
			
		}
		else{
			session = request.getSession();
		}
		SearchCriteria searchCriteria = new SearchCriteria();
		SearchService service = new SearchService();
		try {

			String departure =request.getParameter("departure");
			String destination =request.getParameter("destination");
			String departureDate =request.getParameter("departureDate");
			String returnDate =request.getParameter("returnDate");
			String stops1 = request.getParameter("stops");
			String rank = request.getParameter("rank");
			String duration = request.getParameter("duration");
			String[] stops =request.getParameterValues("stops");
			String[] cabin = request.getParameterValues("cabin");
			String[] requirements  = request.getParameterValues("req");
			String count = request.getParameter("count");
			int countInt = Integer.parseInt(count);
			String[] operators = new String[countInt];
			String fuzzyString = null;
			
			
			for(int i=0;i<countInt;i++){
				operators[i]=request.getParameter("operator"+(i+1));
			}

			String temp=requirements[0];
			for(int i=0;i<operators.length+1;i++){
				
				if(i<requirements.length-1 && operators[i]!=""){
					fuzzyString = operators[i]+"("+temp+","+requirements[i+1]+")";
					System.out.println("----"+fuzzyString);
					
				}
				if(i<requirements.length-1 && operators[i]==""){
					fuzzyString = operators[i]+"("+temp+"+"+requirements[i+1]+")/2";
					System.out.println("----"+fuzzyString);
				}
				temp=fuzzyString;
			}
			/*
			if(requirements.length==1)
				for(NonFunctionalAttributes s: NonFunctionalAttributes.values())
					if(s.toString().toLowerCase().toCharArray()[0] == requirements[0].toCharArray()[0])
						fuzzyString = s.sqlName();
				*/
			
		/*	if(requirements!=null)
				fuzzyString = requirements[0];
			if(countInt>1){
				for(int i=0;i<operators.length;i++){
					fuzzyString += operators[i] + requirements[i + 1];	
				
			}
			System.out.println("Final String : "+fuzzyString);
			NonFunctionalParser np = new NonFunctionalParser(fuzzyString);
			np.sliceForTree();
			np.showEntireStructure();
			System.out.println(np.getOutput());
			fuzzyString = np.getOutput();
			}else
				for(NonFunctionalAttributes s: NonFunctionalAttributes.values())
					if(s.toString().toLowerCase().toCharArray()[0] == requirements[0].toCharArray()[0])
						fuzzyString = s.sqlName();
			
			*/
			
			System.out.println("Final String : "+fuzzyString);
			searchCriteria.setFuzzyString(fuzzyString);

			String numberOfPassengers = request.getParameter("numberOfPassengers");


			System.out.println("passengers --------- >>>>>> "+	numberOfPassengers);
			
			//int actualStops = Integer.parseInt(stops);
			//String price =request.getParameter("price");
			searchCriteria.setDeparture(departure);
			searchCriteria.setDestination(destination);
			searchCriteria.setDepartureDate(departureDate);
			searchCriteria.setNumberOfPassengers(Integer.parseInt(numberOfPassengers));
			String price = request.getParameter("price");
			String minPrice1 = price.split(" - ")[0];
			String maxPrice1 = price.split(" - ")[1];
			
			String minStops = stops1.split(" - ")[0];
			String maxStops = stops1.split(" - ")[1];
			
			String minDuration = duration.split(" - ")[0];
			String maxDuration = duration.split(" - ")[1];
			
			String minRank = rank.split(" - ")[0];
			String maxRank = rank.split(" - ")[1];
			
			// Max - Min Stops
			if((maxStops==null||"".equals(maxStops)) && (minStops==null||"".equals(minStops))){
				searchCriteria.setMaxStops(0);
				searchCriteria.setMinStops(0);
			}	
			else{
				int maxStops1 = Integer.parseInt(maxStops);
				int minStops1 = Integer.parseInt(minStops);
				searchCriteria.setMaxStops(maxStops1);
				searchCriteria.setMinStops(minStops1);
			}
			
			// Max - Min Duration
			
			if((maxDuration==null||"".equals(maxDuration)) && (minDuration==null||"".equals(minDuration))){
				searchCriteria.setMaxDutation(0);
				searchCriteria.setMinDuration(0);
			}	
			else{
				int maxDuration1 = Integer.parseInt(maxDuration);
				int minDuration1 = Integer.parseInt(minDuration);
				searchCriteria.setMaxDutation(maxDuration1);
				searchCriteria.setMinDuration(minDuration1);
			}
			
			// Max - Min Price
			if((maxPrice1==null||"".equals(maxPrice1)) && (minPrice1==null||"".equals(minPrice1))){
				searchCriteria.setMaxPrice(0);
				searchCriteria.setMinPrice(0);
			}	
			else{
				float maxPrice = Float.parseFloat(maxPrice1);
				float minPrice = Float.parseFloat(minPrice1);
				searchCriteria.setMaxPrice(maxPrice);
				searchCriteria.setMinPrice(minPrice);
			}
			// Min - Max Rank
			if((maxRank==null||"".equals(maxRank)) && (minRank==null||"".equals(minRank))){
				searchCriteria.setMaxRank(0);
				searchCriteria.setMinRank(0);
			}	
			else{
				int maxRank1 = Integer.parseInt(maxRank);
				int minRank1 = Integer.parseInt(minRank);
				searchCriteria.setMaxRank(maxRank1);
				searchCriteria.setMinRank(minRank1);
			}
			

			if(stops!=null){
				for(int i=0;i<stops.length;i++){
					if("0".equals(stops[i])){
						searchCriteria.setNonStop(true);
					}
					else if("1".equals(stops[i])){
						searchCriteria.setOneStop(true);
					}
					else if("2".equals(stops[i])){
						searchCriteria.setTwoOrMoreStop(true);
					}
				}
			}
			if(cabin!=null){
				for(int j=0;j<cabin.length;j++){
					if("COACH".equals(cabin[j])){
						searchCriteria.setEconomy(true);
					}
					else if("BUSINESS".equals(cabin[j])){
						searchCriteria.setBusiness(true);
					}
					else if("FIRST".equals(cabin[j])){
						searchCriteria.setFirst(true);
					}
				}
			}
			SearchAttributes searchAttr = service.getSearchAttributes(searchCriteria);
			ArrayList<Itinerary> tripRecord = service.search(searchCriteria);
			
			//if no records found then go to NoRecords.jsp file
			if(tripRecord==null || tripRecord.size()==0){
				RequestDispatcher rd = request.getRequestDispatcher("./web/NoRecords.jsp");
				rd.forward(request, response);
				return;
			}
			System.out.println(tripRecord.get(0));
			
			session.setAttribute("searchAttr", searchAttr);
			session.setAttribute("tripRecord", tripRecord);
			System.out.println("passengers --------- >>>>>> "+	tripRecord.get(0).getNumberOfPassengers()+"----------------");

		}
		catch (GeneralSecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		RequestDispatcher rd = request.getRequestDispatcher("./web/search_result.jsp");
		rd.forward(request, response);	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
