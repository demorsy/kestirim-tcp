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

        // Hedefin hareket hızı ve bekleme süresi (ms cinsinden)
        int moveSpeed = 10; // Örneğin, her 10ms'de bir birim hareket
        int sleepTime = 5000; // 5 saniye bekleme


        try {

            while (true){
                x+=1;
                y+=1;
                //x+=moveSpeed;
                //y+= moveSpeed-5;
                System.out.println("Hedefin güncel kesin konumu: (" + x + "," + y + ")");

                try {
                    Socket socketSensor1 = new Socket(serverAddress, serverPortSensor1);
                    OutputStream outputStreamSensor1 = socketSensor1.getOutputStream();
                    DataOutputStream dataOutputStreamSensor1 = new DataOutputStream(outputStreamSensor1);

                    // Sensor 1'e veriyi gönder
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

                    // Sensor 2'ye veriyi gönder
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
