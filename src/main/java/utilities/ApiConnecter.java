package utilities;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.io.BufferedReader;

import org.json.JSONArray;
import org.json.JSONObject;


public class ApiConnecter {
	protected static final Logger LOGGER = Logger.getLogger(DataBaseReader.class.getName());
	private String baseUrl;
	URL url;
	HttpURLConnection con;
	
	@SuppressWarnings("deprecation")
	public ApiConnecter(String baseUrl) {
		this.baseUrl = baseUrl;
		
	}
	public String baseUrl() {
		return this.baseUrl;
	}

	public void baseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}
	
	private void setupConnection(String url) {
		try {
			this.url = new URL("https://randomuser.me/api/");
            this.con = (HttpURLConnection) this.url.openConnection();
		}
		catch(Exception e){
			
		}
	}
	private int getStatusCode() {
		 int status = 0;
		 try {
			status = con.getResponseCode();
			LOGGER.info("Status Code: " + status);
		 } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		 }
		 return status;
	}
	
	private StringBuilder readContent() {
		BufferedReader in;
		StringBuilder content = null;
		try {
			in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
	        content = new StringBuilder();
	        while ((inputLine = in.readLine()) != null) {
	            content.append(inputLine);
	        }
	        in.close();
	        con.disconnect();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return content;
	}
	
	private JSONObject convertJson(StringBuilder content) {
		JSONObject obj = new JSONObject(content.toString());
		LOGGER.info("Json object: " + obj);
		return obj;
	}
	
	private JSONObject commonQuery(ApiMethods get) {
		JSONObject obj= null;
		try {
			con.setRequestMethod(get.getDescripcion());
            int status = getStatusCode();
            if (status == 0) {throw new Exception("Invalid code");}
            else if (status!= 200) {throw new Exception("Code status is an invalid value: " + status);}
            StringBuilder content = readContent();
            if (content == null) {throw new Exception("Content wasn't read");}
            obj = convertJson(content);
		}
		catch (Exception e)
		{
		}
		return obj;
	}

	private JSONObject getResult(JSONObject obj, String filter) {
		JSONArray results = obj.optJSONArray(filter);
        return results.getJSONObject(0);
	}
	
	public JSONObject getQuery(String endpoint) throws Exception {
		setupConnection(this.baseUrl + endpoint);
		JSONObject obj = commonQuery(ApiMethods.GET);
		if(obj == null) {throw new Exception("Json onjet is null");}
		return obj;
	}
	public JSONObject getQuery() throws Exception {
		setupConnection(this.baseUrl);
		JSONObject obj = commonQuery(ApiMethods.GET);
		if(obj == null) {throw new Exception("Json onjet is null");}
		return getResult(obj, "results");
	}
	
	public Map<String, Object> filterFields(JSONObject results, List<String> filedsList) {
		Map <String, Object> mapingFileds = new HashMap<>();
		for(String field: filedsList) {
			String[]parts = field.split("\\.");
			JSONObject current = results;
			for (int i = 0; i < parts.length - 1; i++) {
                current = current.getJSONObject(parts[i]);  // baja en la jerarquía
            }
			try {
			String key = parts[parts.length - 1];
			Object value = current.get(key);
			mapingFileds.put(field, value.toString());
			}
			catch (Exception e) {
				LOGGER.severe("Value not found");
			}
		}
		return mapingFileds;
	}
	public char[] filterFields(JSONObject response, String string, String string2, String string3) {
		// TODO Auto-generated method stub
		return null;
	}

}
