import java.io.*;
import java.net.*;
public class Target {
    public static void main(String[] args) {
        String serverAddress = "127.0.0.1"; // Sunucu adresi
        int serverPortSensor1 = 8081; // Sensor_1 portu
        int serverPortSensor2 = 8082; // Sensor_2 portu

        // Hedefin başlangıç koordinatları
        int x = -1;
        int y = 5;

        // Hedef 1000x1000 haritada hareket etmelidir.
        // Bu yüzden yönlendirmeyi bu şekilde yapıyoruz.
        boolean xDirectionPositive = true;
        boolean yDirectionPositive = true;

        // bekleme süresi (ms cinsinden)
        int sleepTime = 3000; // 3 saniye bekleme
        int moveSpeed = 1; // hareket hızı


        try {

            while (true){

                // Hareket yönüne göre yer değiştirme
                if (xDirectionPositive) {
                    x += moveSpeed;
                } else {
                    x -= moveSpeed;
                }

                if (yDirectionPositive) {
                    y += moveSpeed;
                } else {
                    y -= moveSpeed;
                }

                // Koordinatların sınırlarını kontrol ediyor ve yönü değiştiriyor
                if (x >= 1000 || x <= -1000) {
                    xDirectionPositive = !xDirectionPositive;
                }
                if (y >= 1000 || y <= -1000) {
                    yDirectionPositive = !yDirectionPositive;
                }

                System.out.println("Hedefin güncel kesin konumu: (" + x + "," + y + ")");

                try {
                    Socket socketSensor1 = new Socket(serverAddress, serverPortSensor1);
                    OutputStream outputStreamSensor1 = socketSensor1.getOutputStream();
                    DataOutputStream dataOutputStreamSensor1 = new DataOutputStream(outputStreamSensor1);

                    // Sensor 1'e veriyi gönderir
                    dataOutputStreamSensor1.writeInt(x);
                    dataOutputStreamSensor1.writeInt(y);

                    dataOutputStreamSensor1.close();
                    socketSensor1.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    Socket socketSensor2 = new Socket(serverAddress, serverPortSensor2);
                    OutputStream outputStreamSensor2 = socketSensor2.getOutputStream();
                    DataOutputStream dataOutputStreamSensor2 = new DataOutputStream(outputStreamSensor2);

                    // Sensor 2'ye veriyi gönderir
                    dataOutputStreamSensor2.writeInt(x);
                    dataOutputStreamSensor2.writeInt(y);

                    dataOutputStreamSensor2.close();
                    socketSensor2.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }

                // Belirli bir süre bekle
                Thread.sleep(sleepTime);

            }





        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
}
