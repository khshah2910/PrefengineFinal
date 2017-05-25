package com.prefengine.dao;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.prefengine.domain.Itinerary;
import com.prefengine.service.SearchCriteria;
import com.prefengine.util.SQLConnection;

public class FlightRecordDAO {
	
	Connection connection = SQLConnection.getConnection();
	Statement stmt;
	ResultSet rs;
	
	public void truncateRecord() throws SQLException{
		
		String query = "truncate flightRecord";
		PreparedStatement pst = connection.prepareStatement(query);
		pst.execute();
		System.out.println("Records Deleted!!	");
	}
	
	public int saveFlightRecordsBatch(ArrayList<Itinerary> tripRec){
		System.out.println("--->>Insertion Begins----> ");
		/*try {
			truncateRecord();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}*/
		System.out.println(tripRec.size());
		int value;
		try {
			value = this.getLatestSearchId();
			this.updateSearchId(value);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return -1;
		}
		
		for(int i=0;i<tripRec.size();i++)
		{
			String tripId = tripRec.get(i).getTripId();
			String origin = tripRec.get(i).getOrigin();
			String destination =tripRec.get(i).getDestination();
			String departureTime = tripRec.get(i).getDepartureTime();
			String arrivalTime = tripRec.get(i).getArrivalTime();
			int stops = tripRec.get(i).getNumberOfStops();
			float price = tripRec.get(i).getPrice();
			String carrier =  tripRec.get(i).getTripCarrier();
			float totalDuration=  tripRec.get(i).getTotalDuration();
			double miles = tripRec.get(i).getTotalMiles();
			String coach = tripRec.get(i).getCoach();
			String departureCityName = tripRec.get(i).getOriginCityName();
			String arrivalCityName = tripRec.get(i).getDestinationCityName();
			String carrierName = tripRec.get(i).getCarrierName();
			
			
			String query = "insert into flightRecord (searchId, tripId, departure, destination, stops, departureTime, arrivalTime, price, carrier,duration, milage,cabin, thisTrip, jsonData,departureCityName,destinationCityName,carrierName) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			try {
				
				PreparedStatement pst = connection.prepareStatement(query);
				pst.setInt(1, value);
				pst.setString(2,tripId);
				pst.setString(3, origin);
				pst.setString(4, destination);
				pst.setInt(5, stops);
				pst.setString(6,departureTime);
				pst.setString(7,arrivalTime);
				pst.setFloat(8, price);
				pst.setString(9, carrier);
				pst.setFloat(10,totalDuration );
				pst.setDouble(11, miles);
				pst.setString(12, coach);
				pst.setString(13, null);
				pst.setString(14, null);
				pst.setString(15, departureCityName);
				pst.setString(16, arrivalCityName);
				pst.setString(17, carrierName);
				pst.execute();
				//return value;

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		System.out.println("--->>Insertion Ends----> ");
		return value;

	}
	
	private ArrayList<Itinerary> processResultSet(ResultSet rs, boolean flag, int passengers) throws SQLException{
		
			ArrayList<Itinerary> records = new ArrayList<>();
			int numberOfPassengers = passengers;
			while(rs.next()&& rs != null){
				Itinerary tr = new Itinerary();
				
				tr.setTripId(rs.getString("tripId"));
				tr.setOrigin(rs.getString("departure"));
				tr.setDestination(rs.getString("destination"));
				tr.setNumberOfStops(rs.getInt("stops"));
				tr.setDepartureTime(rs.getString("departureTime"));
				tr.setArrivalTime(rs.getString("arrivalTime"));
				tr.setPrice(rs.getFloat("price")*numberOfPassengers);
				tr.setTripCarrier(rs.getString("carrier"));
				tr.setTotalDuration(rs.getFloat("duration"));
				tr.setTotalMiles(rs.getDouble("milage"));
				tr.setCoach(rs.getString("cabin"));
				tr.setOriginCityName(rs.getString("departureCityName"));
				tr.setDestinationCityName(rs.getString("destinationCityName"));
				tr.setCarrierName(rs.getString("carrierName"));
				if(flag){
					tr.setScore(rs.getDouble("score"));
				}
				
				tr.setFlightRecord(new ArrayList<>());
				
				records.add(tr);
				
			}
			return records;
	}

	public HashMap<Integer,Float> getRecordsByStops(SearchCriteria sc) throws SQLException{
		String sql="select distinct stops,min(price) as price from flightRecord group by stops order by stops asc";
		HashMap<Integer,Float> result= new HashMap<>();
		stmt = connection.createStatement();
		rs = stmt.executeQuery(sql);
		while(rs.next()){
			result.put(rs.getInt(1), rs.getFloat(2));
		}
		return result;
	}
	
	public HashMap<String, Float> getRecordsByAirline(SearchCriteria sc) throws SQLException{
		String sql="select distinct carrierName ,min(price) as price from flightRecord group by carrierName order by price asc";
		HashMap<String, Float> result= new HashMap<>();
		stmt = connection.createStatement();
		rs = stmt.executeQuery(sql);
		while(rs.next()){
			result.put(rs.getString(1), rs.getFloat(2));
		}
		return result;
	}
	
	public HashMap<String, Float> getRecordsByCabin(SearchCriteria sc) throws SQLException{
		String sql = "select distinct cabin,min(price) as price from flightRecord group by cabin order by price asc";
		HashMap<String, Float> result1 = new HashMap<>();
		stmt = connection.createStatement();
		rs = stmt.executeQuery(sql);
		while(rs.next()){
			result1.put(rs.getString(1),rs.getFloat(2));
			
		}
		return result1;
	}
	
	
	/*public ArrayList<Itinerary> searchByAirline(String choice) throws SQLException{
		String sql="select * from flightRecord where carrier LIKE "+choice;
		stmt = connection.createStatement();
		rs = stmt.executeQuery(sql);
		return this.processResultSet(rs,false);
	}*/
	public ArrayList<Itinerary> searchByParameters(SearchCriteria sc) throws SQLException{
		
		String fuzzy = sc.getFuzzyString();
			int passengers =sc.getNumberOfPassengers(); 
			String sql;
			if(fuzzy!=null){
				sql = ""
				+ "SELECT distinct "
				+ "    F1.* ,"+fuzzy
				+ "   as score  "
				+ "FROM "
				+ "    (SELECT *, "
				+ "	      TRUNCATE((M1.MAX_STOPS - F.STOPS) / (M1.MAX_STOPS - M1.MIN_STOPS), 2) AS s_Stops, "
				+ "		  TRUNCATE(((M1.MAX_PRICE - F.price) / (M1.MAX_PRICE - M1.MIN_PRICE)), 2) AS s_price, "
				+ "		  TRUNCATE((M1.MAX_DURATION - F.duration) / (M1.MAX_DURATION - M1.MIN_DURATION), 2) AS s_duration, "
				+ "		  if(TRUNCATE((M1.MAX_RANK - AR.RANK) / (M1.MAX_RANK - M1.MIN_RANK), 2) is null, 0, TRUNCATE((M1.MAX_RANK - AR.RANK) / (M1.MAX_RANK - M1.MIN_RANK), 2)) as s_rank "
				+ "    FROM "
				+ "        FLIGHTRECORD F left join AirlineRanks AR on F.carrier = AR.AirlineCode, "
				+ "        (SELECT "
				+ 			sc.getMinStops()+" AS MIN_STOPS, "
				+ 			sc.getMaxStops()+" AS MAX_STOPS, "
				+ 			sc.getMinPrice()+" AS MIN_PRICE, "
				+ 			sc.getMaxPrice()+" AS MAX_PRICE, "
				+ 			sc.getMinDuration()+" AS MIN_DURATION, "
				+ 			sc.getMaxDutation()+" AS MAX_DURATION, "
				+ 			sc.getMaxRank()+" AS MAX_RANK, "
				+ 			sc.getMinRank()+" AS MIN_RANK "
				+ "             "
				+ "		FROM "
				+ "        FLIGHTRECORD fr left join AirlineRanks ar on fr.carrier = ar.AirlineCode) M1 where departure LIKE ? AND destination LIKE  ? AND  SEARCHID = ? ) F1 order by score desc";
		
		
			}
			else{
				sql = ""
						+ "SELECT distinct "
						+ "    F1.* ,"+fuzzy
						+ "   as score  "
						+ "FROM "
						+ "    (SELECT *, "
						+ "	      TRUNCATE((M1.MAX_STOPS - F.STOPS) / (M1.MAX_STOPS - M1.MIN_STOPS), 2) AS s_Stops, "
						+ "		  TRUNCATE(((M1.MAX_PRICE - F.price) / (M1.MAX_PRICE - M1.MIN_PRICE)), 2) AS s_price, "
						+ "		  TRUNCATE((M1.MAX_DURATION - F.duration) / (M1.MAX_DURATION - M1.MIN_DURATION), 2) AS s_duration, "
						+ "		  if(TRUNCATE((M1.MAX_RANK - AR.RANK) / (M1.MAX_RANK - M1.MIN_RANK), 2) is null, 0, TRUNCATE((M1.MAX_RANK - AR.RANK) / (M1.MAX_RANK - M1.MIN_RANK), 2)) as s_rank "
						+ "    FROM "
						+ "        FLIGHTRECORD F left join AirlineRanks AR on F.carrier = AR.AirlineCode, "
						+ "        (SELECT "
						+ 			sc.getMinStops()+" AS MIN_STOPS, "
						+ 			sc.getMaxStops()+" AS MAX_STOPS, "
						+ 			sc.getMinPrice()+" AS MIN_PRICE, "
						+ 			sc.getMaxPrice()+" AS MAX_PRICE, "
						+ 			sc.getMinDuration()+" AS MIN_DURATION, "
						+ 			sc.getMaxDutation()+" AS MAX_DURATION, "
						+ 			sc.getMaxRank()+" AS MAX_RANK, "
						+ 			sc.getMinRank()+" AS MIN_RANK "
						+ "             "
						+ "		FROM "
						+ "        FLIGHTRECORD fr left join AirlineRanks ar on fr.carrier = ar.AirlineCode) M1 where departure LIKE ? AND destination LIKE  ? AND  SEARCHID = ? ) F1 order by price";
				
			}
		
		
		/*//String sql = "select * from flightRecord where departure LIKE ? AND destination LIKE ? ";
		String stopsCriteria = "";
		String cabinCriteria = "";
		String priceCriteria = "";
		// search by Stops
		if(sc.isNonStop()){
			stopsCriteria += "AND (stops = 0";
		}
		if(sc.isOneStop()){
			if("".equals(stopsCriteria)){
				stopsCriteria += "AND (stops = 1";
			}
			else{
				stopsCriteria += " OR stops = 1";
			}
		}
		if(sc.isTwoOrMoreStop()){
			if("".equals(stopsCriteria)){
				stopsCriteria += "AND (stops >= 2";
			}
			else{
				stopsCriteria += " OR stops >= 2";
			}
		}
		if(!"".equals(stopsCriteria)){
			stopsCriteria += ")";
			sql+= stopsCriteria;
			System.out.println(sql);
		}
		// search by cabin(Coach)
		if(sc.isEconomy()){
			cabinCriteria += " AND (cabin LIKE 'COACH'";
		}
		if(sc.isBusiness()){
			if("".equals(cabinCriteria)){
				cabinCriteria += "AND (cabin LIKE 'BUSINESS'";
			}
			else{
				cabinCriteria += " OR cabin LIKE 'BUSINESS'";
			}
		}
		if(sc.isFirst()){
			if("".equals(cabinCriteria)){
				cabinCriteria += "AND (cabin LIKE 'FIRST'";
			}
			else{
				cabinCriteria += " OR cabin LIKE 'FIRST'";
			}
		}
		if(!"".equals(cabinCriteria)){
			cabinCriteria += ")";
			sql+= cabinCriteria;
			System.out.println(sql);
		}
		//search by price
		System.out.println(sc.getMaxDutation());
		System.out.println(sc.getMinDuration());
		System.out.println(sc.getMaxStops());
		System.out.println(sc.getMinDuration());
		System.out.println(sc.getMaxPrice());
		System.out.println(sc.getMinPrice());
		
		
		if(sc.getMaxPrice()!=0 && sc.getMinPrice()!=0){
			priceCriteria = " AND price between ? AND ?";
			sql+= priceCriteria;
			System.out.println(sql);
		}*/
		//sql+= " ) F1 order by score desc";
		PreparedStatement pst = connection.prepareStatement(sql);
		pst.setString(1, sc.getDeparture());
		pst.setString(2, sc.getDestination());
		pst.setInt(3, sc.getSearchId());
//		if(sc.getMaxPrice()!=0 && sc.getMinPrice()!=0){
//			pst.setFloat(3, sc.getMinPrice());
//			pst.setFloat(4, sc.getMaxPrice());
//		}
		rs = pst.executeQuery();
		System.out.println(sql);
		return this.processResultSet(rs,true,passengers);
		
	}
	
	public void savePreferences(SearchCriteria sc){
		
		String query = "INSERT INTO users_preferences (ipAddress, departure, destination, date, userPreference, priceRange, stopRange, durationRange, rankRange) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		try {
			//UserPreferences userPref = new UserPreferences();
			PreparedStatement pst = connection.prepareStatement(query);
			try {
				pst.setString(1, Inet4Address.getLocalHost().getHostAddress());
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
			pst.setString(2, sc.getDeparture());
			pst.setString(3, sc.getDestination());
			pst.setString(4, sc.getDepartureDate());
			pst.setString(5, sc.getUserPreferenceString());
			pst.setString(6, sc.getMinPrice()+" - "+sc.getMaxPrice());
			pst.setString(7, sc.getMinStops()+" - "+sc.getMinStops());
			pst.setString(8, sc.getMinDuration()+" - "+sc.getMaxDutation());
			pst.setString(9, sc.getMinRank()+" - "+sc.getMaxRank());
			pst.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public int getLatestSearchId() throws SQLException{
		String query= "SELECT * FROM prefengine.searchId_seq";
		PreparedStatement stmt = null;
		ResultSet rs= null;
		try {
			stmt = connection.prepareStatement(query);
			rs = stmt.executeQuery();
			while(rs.next()){
				int value = rs.getInt(1);
				return value;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			if(rs!=null){
				rs.close();
			}
			if(stmt!=null){
				stmt.close();
			}
		}
		return 0;
	}
	
	public boolean updateSearchId(int value) throws SQLException{
		String query = "UPDATE searchId_seq SET search_id =?";
		PreparedStatement stmt = null;
		try {
			stmt = connection.prepareStatement(query);
			stmt.setInt(1, value+1);
			stmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			if(stmt!=null){
				stmt.close();
			}
		}
		
		return true;
	}
}
