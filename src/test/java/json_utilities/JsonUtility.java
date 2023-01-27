package json_utilities;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;

public class JsonUtility {

    public static <T> T deserializeJson(String fileName, Class<T> T) throws IOException {
        InputStream inputStream = JsonUtility.class.getClassLoader().getResourceAsStream(fileName);
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(inputStream, T);
    }
}
