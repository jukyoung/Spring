package kh.board.publicdata;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.springframework.stereotype.Service;

@Service
public class PublicDataService {

	public String getJSON() throws Exception{
		   StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/6290000/festivalbaseinfo/getfestivalbaseinfo"); /*URL*/
	        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=W1gpSaAjrm4HFqjQndntxD1L%2BJHvHOzW%2BIQ0EDHvxcClAXXj7rcsC%2BYe4X%2BUxFCHjf7Pvss4OPRFyQvK3Onq3Q%3D%3D"); /*Service Key*/
	        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); /*한 페이지 결과 수*/
	        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지 번호*/
	        urlBuilder.append("&" + URLEncoder.encode("type","UTF-8") + "=" + URLEncoder.encode("json", "UTF-8")); /*응답 받을 데이터 형식*/
	        //urlBuilder.append("&" + URLEncoder.encode("festivalNm","UTF-8") + "=" + URLEncoder.encode("신년", "UTF-8")); /*축제명*/
	        URL url = new URL(urlBuilder.toString());
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setRequestMethod("GET");
	        conn.setRequestProperty("Content-type", "application/json");
	        System.out.println("Response code: " + conn.getResponseCode());
	        BufferedReader rd;
	        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
	            rd = new BufferedReader(new InputStreamReader(conn.getInputStream())); //읽어온 데이터
	        } else {
	            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
	        }
	        StringBuilder sb = new StringBuilder(); // 스트링을 이어붙여주는 객체
	        String line;
	        while ((line = rd.readLine()) != null) { 
	            sb.append(line);
	        }
	        rd.close();
	        conn.disconnect();
	        System.out.println(sb.toString()); // 반환받는 값
	        return sb.toString();
	    }
	
	public String getXML() throws Exception{
		 StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/6290000/festivalbaseinfo/getfestivalbaseinfo"); /*URL*/
	        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=W1gpSaAjrm4HFqjQndntxD1L%2BJHvHOzW%2BIQ0EDHvxcClAXXj7rcsC%2BYe4X%2BUxFCHjf7Pvss4OPRFyQvK3Onq3Q%3D%3D"); /*Service Key*/
	        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); /*한 페이지 결과 수*/
	        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지 번호*/
	        urlBuilder.append("&" + URLEncoder.encode("type","UTF-8") + "=" + URLEncoder.encode("XML", "UTF-8")); /*응답 받을 데이터 형식*/
	        //urlBuilder.append("&" + URLEncoder.encode("festivalNm","UTF-8") + "=" + URLEncoder.encode("신년", "UTF-8")); /*축제명*/
	        URL url = new URL(urlBuilder.toString());
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setRequestMethod("GET");
	        conn.setRequestProperty("Content-type", "application/json");
	        System.out.println("Response code: " + conn.getResponseCode());
	        BufferedReader rd;
	        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
	            rd = new BufferedReader(new InputStreamReader(conn.getInputStream())); //읽어온 데이터
	        } else {
	            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
	        }
	        StringBuilder sb = new StringBuilder(); // 스트링을 이어붙여주는 객체
	        String line;
	        while ((line = rd.readLine()) != null) { 
	            sb.append(line);
	        }
	        rd.close();
	        conn.disconnect();
	        System.out.println(sb.toString()); // 반환받는 값
	        return sb.toString();
	    }
	}
