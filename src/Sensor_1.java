import java.io.*;
import java.net.*;

public class Sensor_1 {
    public static void main(String[] args) {
        String serverAddress = "127.0.0.1"; // Sunucu adresi
        int serverPort = 8081; // Sunucu portu
        int commandBasePort = 8080;
        int x = -5;
        int y = 1;

        try {
            ServerSocket serverSocket = new ServerSocket(serverPort);
            System.out.println("Sensor 1 dinliyor...");

            while (true) {
                Socket clientSocket = serverSocket.accept();

                // İstemciden gelen verileri okuma
                DataInputStream inputStream = new DataInputStream(clientSocket.getInputStream());
                int receivedX = inputStream.readInt();
                int receivedY = inputStream.readInt();

                //System.out.println("Hedeften gelen X: " + receivedX);
                //System.out.println("Hedeften gelen Y: " + receivedY);

                double aciDereceSensor1 = hesaplaKerterizAci(x, y, receivedX, receivedY);
                System.out.println("Sensör 1 için hedefin kerterizi y+ ekseninden saat yönündeki açısı " + aciDereceSensor1);

                // Command Base'e hesaplanan veriyi gönderir
                Socket commandBaseSocket = new Socket(serverAddress, commandBasePort);
                DataOutputStream commandBaseOutputStream = new DataOutputStream(commandBaseSocket.getOutputStream());
                commandBaseOutputStream.writeInt(1); // sensor1 olduğunu belli eder.
                commandBaseOutputStream.writeDouble(aciDereceSensor1);
                commandBaseOutputStream.writeInt(x); // Sensörün x koordinatı
                commandBaseOutputStream.writeInt(y); // Sensörün y koordinatı
                commandBaseOutputStream.close();
                commandBaseSocket.close();

                clientSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static double hesaplaKerterizAci(double sensorX, double sensorY, double hedefX, double hedefY) {
        double aciRadyan = Math.atan2(hedefY - sensorY, hedefX - sensorX);
        double aciDerece = Math.toDegrees(aciRadyan);

        if (aciDerece < 0) {
            aciDerece += 360;
        }

        aciDerece = (360 - aciDerece + 90) % 360; // Saat yönünde dönüştürme
        return aciDerece;
    }
}
