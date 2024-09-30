import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ApiCaller {
    private static final String API_URL = "https://api.example.com/data"; // Đặt API URL ở đây

    public static void main(String[] args) {
        try {
            // Tạo một URL object
            URL url = new URL(API_URL);

            // Mở kết nối
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // Kiểm tra mã phản hồi (response code)
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) { // 200 OK
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder content = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }

                // Đóng kết nối
                in.close();
                connection.disconnect();

                // Xử lý dữ liệu
                System.out.println("Dữ liệu API: " + content.toString());
            } else {
                System.out.println("Lỗi khi gọi API, Mã phản hồi: " + responseCode);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
