package utilities.test;

import java.util.List;

import org.json.JSONObject;

import utilities.ApiConnecter;

public class TestApiConnecter {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JSONObject response = null;
		ApiConnecter apiCon = new ApiConnecter("https://randomuser.me/api/");
		try {
			response = apiCon.getQuery();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Actual result; "+ response.getString("phone"));
		System.out.println(apiCon.filterFields(response, List.of("phone", "name.first", "location")));
	}

}
