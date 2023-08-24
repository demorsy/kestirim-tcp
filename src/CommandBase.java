import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class CommandBase {
        public static void main(String[] args) {
            int commandBasePort = 8080;
                double receivedAciDereceSensor1 = 0;
                int receivedXSensor1 = 0;
                int receivedYSensor1 = 0;

                double receivedAciDereceSensor2 = 0;
                int receivedXSensor2 = 0;
                int receivedYSensor2 = 0;
            try {
                ServerSocket serverSocket = new ServerSocket(commandBasePort);
                System.out.println("Command Base dinliyor...");

                while (true) {
                    Socket clientSocket = serverSocket.accept();

                    DataInputStream inputStream = new DataInputStream(clientSocket.getInputStream());
                    int whichSensor = inputStream.readInt();
                    double receivedAciDerece = inputStream.readDouble();
                    int receivedX = inputStream.readInt();
                    int receivedY = inputStream.readInt();

                    if (whichSensor == 1) {
                        //System.out.println("Sensor 1'den gelen açı: " + receivedAciDerece);
                        //System.out.println("Sensor 1'in koordinatı (X,Y): (" + receivedX + "," + receivedY + ")");
                        receivedAciDereceSensor1 = receivedAciDerece;
                        receivedXSensor1 = receivedX;
                        receivedYSensor1 = receivedY;
                    } else if (whichSensor == 2) {
                        //System.out.println("Sensor 2'den gelen açı: " + receivedAciDerece);
                        //System.out.println("Sensor 2'nin koordinatı (X,Y): (" + receivedX + "," + receivedY + ")");
                        receivedAciDereceSensor2 = receivedAciDerece;
                        receivedXSensor2 = receivedX;
                        receivedYSensor2 = receivedY;
                    }

                    // Her iki sensörden de veri geldiyse hesaplama yap
                    if (receivedAciDereceSensor1 != 0 && receivedAciDereceSensor2 != 0) {
                        findTargetLocation(receivedAciDereceSensor1, receivedAciDereceSensor2,
                                receivedXSensor1, receivedYSensor1, receivedXSensor2);

                        // Verileri sıfırla
                        receivedAciDereceSensor1 = 0;
                        receivedXSensor1 = 0;
                        receivedYSensor1 = 0;
                        receivedAciDereceSensor2 = 0;
                        receivedXSensor2 = 0;
                        receivedYSensor2 = 0;
                    }



                    clientSocket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        public static void findTargetLocation(double aciDereceSensor1, double aciDereceSensor2, double sensor1X,
                                              double sensor1Y, double sensor2X)
        {
            // Sensörlerin hedefe olan y+ açılarını radyan cinse çevirir
            double aciRadyanSensor1 = Math.toRadians(aciDereceSensor1);
            double aciRadyanSensor2 = Math.toRadians(aciDereceSensor2);

            // Hedefin koordinatlarını hesaplar
            double hedefX = (sensor1X * Math.tan(aciRadyanSensor2) - sensor2X * Math.tan(aciRadyanSensor1)) /
                    (Math.tan(aciRadyanSensor2) - Math.tan(aciRadyanSensor1));
            double hedefY = sensor1Y + (hedefX - sensor1X) * Math.tan(aciRadyanSensor1);
            System.out.println("Hedefin hesaplanan yaklaşık koordinatı: (" + (hedefX) + ", " + hedefY + ")\n\n");

        }


}
