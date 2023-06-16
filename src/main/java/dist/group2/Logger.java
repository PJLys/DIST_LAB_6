package dist.group2;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.JSONValue;
import net.minidev.json.parser.ParseException;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Logger {
    public static int getOwner(String filePath) {
        JSONObject jsonObject = readLogFile(filePath);
        return (int) jsonObject.get("owner");
    }

    public static List<Integer> getReplicators(String filePath) {
        JSONObject jsonObject = readLogFile(filePath);
        JSONArray replicatorsArray = (JSONArray) jsonObject.get("replicators");
        List<Integer> replicatorsList = new ArrayList<>();
        for (Object replicator : replicatorsArray) {
            replicatorsList.add((int) replicator);
        }
        return replicatorsList;
    }

    public static void createLogFile(String filePath, int owner, List<Integer> replicators) {
        System.out.println("Creating log file: " + filePath);
        JSONObject logData = new JSONObject();
        JSONArray replicatorsArray = new JSONArray();
        replicatorsArray.addAll(replicators);
        logData.put("owner", owner);
        logData.put("replicators", replicatorsArray);
        writeJSONObject(filePath, logData);
    }

    private static void writeJSONObject(String filePath, JSONObject jsonObject) {
        writeJSONString(filePath, jsonObject.toJSONString());
    }

    public static void writeJSONString(String filePath, String jsonString) {
        try (FileWriter fileWriter = new FileWriter(filePath)) {
            fileWriter.write(jsonString);
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("Error while writing log file " + filePath);
            e.printStackTrace();
            ClientApplication.failure();
        }
    }

    public static JSONObject readLogFile(String filePath) {
        try {
            String json = readLogFileString(filePath);
            if (json != null) {
                return (JSONObject) JSONValue.parseWithException(json);
            }
        } catch (ParseException e) {
            System.out.println("Failed to parse log file " + filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new JSONObject();
    }

    public static String readLogFileString(String filePath) {
        try {
            return Files.readString(Paths.get(filePath));
        } catch (IOException e) {
            System.out.println("Failed to read log file " + filePath);
        }
        return null;
    }

    public static void addReplicator(String filePath, int replicator) {
        JSONObject jsonObject = readLogFile(filePath);
        JSONArray replicators = (JSONArray) jsonObject.get("replicators");
        if (replicators == null) {
            System.out.println("Replicators not found or empty in log file " + filePath);
            replicators = new JSONArray();
        }
        replicators.add(replicator);
        writeJSONObject(filePath, jsonObject);
    }

    public static void removeReplicator(String filePath, int replicator) {
        JSONObject jsonObject = readLogFile(filePath);
        JSONArray replicators = (JSONArray) jsonObject.get("replicators");
        replicators.add(replicator);
        boolean success = (boolean) replicators.remove(replicator);
        if (success) {
            System.out.println("Successfully removed " + replicator + " from log file " + filePath);
        } else {
            System.out.println(replicator + " not found in log file " + filePath);
        }
        writeJSONObject(filePath, jsonObject);
    }

    public static void setOwner(String filePath, int newOwner) {
        JSONObject jsonObject = readLogFile(filePath);
        jsonObject.put("owner", newOwner);
        System.out.println("Set " + newOwner + " as owner in log file " + filePath);
        writeJSONObject(filePath, jsonObject);
    }
}
