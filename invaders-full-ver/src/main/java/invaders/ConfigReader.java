package invaders;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Statement;

public class ConfigReader {
    private static JSONObject gameInfo;
    private static JSONObject playerInfo;
    private static JSONArray bunkersInfo;
    private static JSONArray enemiesInfo;


    public static void parse(String configPath){
        JSONParser parser = new JSONParser();
        try {
            JSONObject configObject = (JSONObject) parser.parse(new FileReader(configPath));

            // Reading game section
            gameInfo = (JSONObject) configObject.get("Game");

	        // Reading player section
            playerInfo = (JSONObject) configObject.get("Player");

			// Reading bunker section
            bunkersInfo = (JSONArray) configObject.get("Bunkers");

            // Reading enemies section
            enemiesInfo = (JSONArray) configObject.get("Enemies");
        } catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
    }

    public static JSONObject getGameInfo() {
        return gameInfo;
    }

    public static JSONObject getPlayerInfo() {
        return playerInfo;
    }

    public static JSONArray getBunkersInfo() {
        return bunkersInfo;
    }

    public static JSONArray getEnemiesInfo() {
        return enemiesInfo;
    }
}