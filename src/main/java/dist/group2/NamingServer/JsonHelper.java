package dist.group2.NamingServer;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.Map;
import java.util.TreeMap;

public class JsonHelper {
    private static final String path = "src/files/map.json";

    public static void convertMapToJson(Map<Integer, String> map) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(new File(path), map);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Map<Integer, String> convertJsonToMap() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            Map map = mapper.readValue(new File(path), Map.class);

            Map<Integer, String> map2 = new TreeMap<>();
            for (Object key : map.keySet()) {
                map2.put(Integer.parseInt((String) key), (String) map.get(key));
            }
            return map2;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
