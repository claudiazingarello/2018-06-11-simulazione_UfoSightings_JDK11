package it.polito.tdp.ufo.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.ufo.model.Adiacenza;
import it.polito.tdp.ufo.model.AnnoAvvistamenti;
import it.polito.tdp.ufo.model.Sighting;

public class SightingsDAO {

	public List<Sighting> getSightings() {
		String sql = "SELECT * FROM sighting" ;

		List<Sighting> list = new ArrayList<>() ;

		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;

			ResultSet res = st.executeQuery() ;

			while(res.next()) {
				list.add(new Sighting(res.getInt("id"),
						res.getTimestamp("datetime").toLocalDateTime(),
						res.getString("city"), 
						res.getString("state"), 
						res.getString("country"),
						res.getString("shape"),
						res.getInt("duration"),
						res.getString("duration_hm"),
						res.getString("comments"),
						res.getDate("date_posted").toLocalDate(),
						res.getDouble("latitude"), 
						res.getDouble("longitude"))) ;
			}

			conn.close();
			return list ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}

	public List<AnnoAvvistamenti> listAnnoAvvistamenti() {
		String sql = "SELECT distinct YEAR(s.datetime) AS anno, COUNT(*) AS numAvvistamenti " + 
				"FROM sighting AS s " + 
				"WHERE s.country ='us' " + 
				"GROUP BY anno " + 
				"ORDER BY anno asc" ;


		try {
			Connection conn = DBConnect.getConnection() ;
			PreparedStatement st = conn.prepareStatement(sql) ;
			ResultSet res = st.executeQuery() ;

			List<AnnoAvvistamenti> list = new ArrayList<>() ;
			while(res.next()) {
				list.add(new AnnoAvvistamenti(Year.of(res.getInt("anno")), 
						res.getInt("numAvvistamenti"))) ;
			}

			conn.close();
			return list ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}

	public List<String> getStatiForYear(Year year) {
		String sql = "SELECT DISTINCT s.state AS stato, COUNT(s.id) AS num " + 
				"FROM sighting AS s " + 
				"WHERE s.country = 'us' AND YEAR(s.datetime) = ? " + 
				"GROUP BY stato " + 
				"HAVING num >=1" ;


		try {
			Connection conn = DBConnect.getConnection() ;
			PreparedStatement st = conn.prepareStatement(sql) ;

			st.setInt(1, year.getValue());
			ResultSet res = st.executeQuery() ;

			List<String> list = new ArrayList<>() ;
			while(res.next()) {
				list.add(res.getString("stato")) ;
			}

			conn.close();
			return list ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}


	public List<Adiacenza> getAdiacenze(Year year) {
		String sql = "SELECT DISTINCT s1.state AS stato1, s2.state AS stato2, COUNT(*) cnt " + 
				"FROM sighting AS s1, sighting AS s2 " + 
				"WHERE s1.country='us' AND s2.country='us' AND s1.state <> s2.state " + 
				"AND YEAR(s1.datetime) = ? AND YEAR(s1.datetime) = YEAR (s2.datetime) " + 
				"AND s1.datetime < s2.datetime " + 
				"GROUP BY stato1, stato2 " + 
				"HAVING cnt >= 1" ;


		try {
			Connection conn = DBConnect.getConnection() ;
			PreparedStatement st = conn.prepareStatement(sql) ;

			st.setInt(1, year.getValue());
			ResultSet res = st.executeQuery() ;

			List<Adiacenza> list = new ArrayList<>() ;
			while(res.next()) {
				list.add(new Adiacenza(res.getString("stato1"), res.getString("stato2"))) ;
			}

			conn.close();
			return list ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}

}
