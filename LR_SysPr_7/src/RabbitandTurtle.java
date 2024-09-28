import java.util.Random;

class AnimalThread extends Thread {
    private String animalName;
    private int priority;
    private int distanceCovered = 0;

    public AnimalThread(String animalName, int priority) {
        this.animalName = animalName;
        this.priority = priority;
        this.setPriority(priority);
    }

    @Override
    public void run() {
        Random random = new Random();
        while (distanceCovered < 100) { // Цель - преодолеть 100 метров
            distanceCovered += random.nextInt(10) + 1; // Каждый раз добавляем от 1 до 10 метров
            System.out.println(animalName + " пробежал " + distanceCovered + " метров.");

            // Меняем приоритет, если это необходимо
            adjustPriority();

            try {
                Thread.sleep(100); // Имитация времени движения
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(animalName + " достиг цели!");
    }

    // Метод для динамического изменения приоритета
    private void adjustPriority() {
        if (distanceCovered < 50 && this.priority < Thread.MAX_PRIORITY) {
            this.priority++;
            this.setPriority(this.priority);
            System.out.println(animalName + " увеличил приоритет до " + this.priority);
        } else if (distanceCovered >= 50 && this.priority > Thread.MIN_PRIORITY) {
            this.priority--;
            this.setPriority(this.priority);
            System.out.println(animalName + " уменьшил приоритет до " + this.priority);
        }
    }
}

public class RabbitandTurtle {
    public static void main(String[] args) {
        AnimalThread rabbit = new AnimalThread("Кролик", Thread.MAX_PRIORITY);
        AnimalThread turtle = new AnimalThread("Черепаха", Thread.MIN_PRIORITY);

        rabbit.start();
        turtle.start();

        // Ожидание завершения потоков
        try {
            rabbit.join();
            turtle.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Соревнование завершено!");
    }
}
