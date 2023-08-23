public class Main {
    public static void main(String[] args) {
        Thread sensor1Thread = new Thread(() -> {
            Sensor_1.main(null); // Sensor başlatma
        });

        Thread sensor2Thread = new Thread(() -> {
            Sensor_2.main(null); // Sensor başlatma
        });


        Thread targetThread = new Thread(() -> {
            Target.main(null); // Target başlatma
        });

        Thread commandBaseThread = new Thread(() -> {
            CommandBase.main(null); // Command Base başlatma
        });

        sensor1Thread.start();
        sensor2Thread.start();
        targetThread.start();
        commandBaseThread.start();

    }


}