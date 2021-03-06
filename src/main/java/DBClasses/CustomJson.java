package DBClasses;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CustomJson extends JSONObject {

    public  CustomJson(String function, JSONObject data){
        super();

        try {
            this.put("function", function);
            this.put("data", data);
        }catch(JSONException e){
            System.out.println("Error during custom json creation");
        }
    }

    public CustomJson(String function, JSONArray data){
        super();

        try {
            this.put("function", function);
            this.put("data", data);
        }catch(JSONException e){
            System.out.println("Error during custom json creation");
        }
    }

}