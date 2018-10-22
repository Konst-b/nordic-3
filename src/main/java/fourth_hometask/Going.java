package fourth_hometask;

public class Going {
    private static final Object step = new Object();
    final int TIMEOUT = 100;
    Thread t1, t2, t3, t4;

    Going() throws InterruptedException {
        // вариант #1
        RightLeg leg1 = new RightLeg();
        LeftLeg leg2 = new LeftLeg();
        t1 = new Thread(leg1);
        t2 = new Thread(leg2);

        //  вариант #2
        Leg leg_1 = new Leg("Правая нога");
        Leg leg_2 = new Leg("Левая нога");
        t3 = new Thread(leg_1);
        t4 = new Thread(leg_2);
    }

    public static void main(String args[]) throws InterruptedException {
        Going going = new Going();
        going.go();
    }

    private void go() throws InterruptedException {
        System.out.println("---------------------Вариант 1");
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println("---------------------Вариант 2");
        t3.start();
        t4.start();
    }

    //вариант #1
    class RightLeg implements Runnable {

        public synchronized void run() {
            for (int i = 0; i < 10; i++) {
                System.out.println("Правая нога");
                try {
                    wait(TIMEOUT);
                } catch ( InterruptedException e ) {
                    e.printStackTrace();
                }
                notify();
            }
        }
    }

    class LeftLeg implements Runnable {

        public synchronized void run() {
            for (int i = 0; i < 10; i++) {
                System.out.println("Левая нога");
                try {
                    wait(TIMEOUT);
                } catch ( InterruptedException e ) {
                    e.printStackTrace();
                }
                notify();
            }
        }
    }
    // вариант #2
    class Leg implements Runnable {
        private String name;

        Leg(String name) {
            this.name = name;
        }

        public void run() {
            synchronized (step) {
                for (int i = 0; i < 10; i++) {
                    try {
                        name = name + ".";
                        System.out.println(name);
                        step.wait(TIMEOUT);
                    } catch ( InterruptedException e ) {
                        e.printStackTrace();
                    }
                    step.notify();
                }
            }
        }
    }

}
