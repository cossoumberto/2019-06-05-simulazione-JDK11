package it.polito.tdp.crimes.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.crimes.model.District;
import it.polito.tdp.crimes.model.Event;

public class EventsDao {
	
/*	public List<Event> listEvents(){
		String sql = "SELECT * FROM events" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Event> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Event(res.getLong("incident_id"),
							res.getInt("offense_code"),
							res.getInt("offense_code_extension"), 
							res.getString("offense_type_id"), 
							res.getString("offense_category_id"),
							res.getTimestamp("reported_date").toLocalDateTime(),
							res.getString("incident_address"),
							res.getDouble("geo_lon"),
							res.getDouble("geo_lat"),
							res.getInt("district_id"),
							res.getInt("precinct_id"), 
							res.getString("neighborhood_id"),
							res.getInt("is_crime"),
							res.getInt("is_traffic")));
				} catch (Throwable t) {
					t.printStackTrace();
					System.out.println(res.getInt("id"));
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}*/
	
	public List<Integer> listYears(){
		String sql = "SELECT DISTINCT YEAR(reported_date) AS Y FROM `events` ORDER BY Y ASC";
		List<Integer> list = new ArrayList<>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while(res.next()) {
				list.add(res.getInt("Y"));
			}
			conn.close();
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}
	}
	
	public void listDistrictForYears(Integer year, Map<Integer, District> idMap){
		String sql = "SELECT district_id AS ID, AVG(geo_lat) AS LAT, AVG(geo_lon) AS LON FROM `events` WHERE YEAR(reported_date)=? GROUP BY district_id";
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, year);
			ResultSet res = st.executeQuery();
			while(res.next()) {
				idMap.put(res.getInt("ID"), new District(res.getInt("ID"), res.getDouble("LAT"), res.getDouble("LON")));
			}
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public List<Event> listEventsDate(LocalDate data, Map<Integer, District> idMap){
		String sql = "SELECT * FROM `events` WHERE DATE(reported_date) = ?";
		List<Event> list = new ArrayList<>() ;
		try {
			Connection conn = DBConnect.getConnection() ;
			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setString(1, data.toString());
			ResultSet res = st.executeQuery() ;
			while(res.next()) {
				try {
					list.add(new Event(res.getLong("incident_id"),
							res.getInt("offense_code"),
							res.getInt("offense_code_extension"), 
							res.getString("offense_type_id"), 
							res.getString("offense_category_id"),
							res.getTimestamp("reported_date").toLocalDateTime(),
							res.getString("incident_address"),
							res.getDouble("geo_lon"),
							res.getDouble("geo_lat"),
							idMap.get(res.getInt("district_id")),
							res.getInt("precinct_id"), 
							res.getString("neighborhood_id"),
							res.getInt("is_crime"),
							res.getInt("is_traffic")));
				} catch (Throwable t) {
					t.printStackTrace();
					System.out.println(res.getInt("id"));
				}
			}
			conn.close();
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}
	}
	
	public District localizzaCentralePolizia(Integer anno, Map<Integer, District> idMap) {
		String sql = "SELECT district_id AS d, COUNT(incident_id) AS c FROM `events` WHERE YEAR(reported_date)=? GROUP BY district_id";
		District centrale = null;
		Integer numCrimini = -1;
		boolean primo = true;
		try {
			Connection conn = DBConnect.getConnection() ;
			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setInt(1, anno);
			ResultSet res = st.executeQuery() ;
			while(res.next()) {
				if(primo == true) {
					centrale = idMap.get(res.getInt("d"));
					numCrimini = res.getInt("c");
					primo = false;
				} else {
					if(res.getInt("c") < numCrimini) {
						centrale = idMap.get(res.getInt("d"));
						numCrimini = res.getInt("c");
					}
				}
			}
			conn.close();
			return centrale;
		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}
	}
	
}
