import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Main {

    public static BlockingQueue<String> maxA = new ArrayBlockingQueue<>(100);
    public static BlockingQueue<String> maxB = new ArrayBlockingQueue<>(100);
    public static BlockingQueue<String> maxC = new ArrayBlockingQueue<>(100);

    public static void main(String[] args) {

        new Thread(() -> {
            for (int i = 0; i < 10_000; i++) {
                try {
                    maxA.put(generateText("abc", 100_000));
                    maxB.put(generateText("abc", 100_000));
                    maxC.put(generateText("abc", 100_000));
                } catch (InterruptedException e) {
                    return;
                }
            }
        }).start();

        new Thread(() -> {
            try {
                System.out.println("Текст с максимальным количеством букв a (" + maxABC(maxA, 'a') + "):\n");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();

        new Thread(() -> {
            try {
                System.out.println("Текст с максимальным количеством букв b (" + maxABC(maxB, 'b') + "):\n");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();

        new Thread(() -> {
            try {
                System.out.println("Текст с максимальным количеством букв c (" + maxABC(maxC, 'c') + "):\n");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    public static int maxABC(BlockingQueue<String> testABC, char abc) throws InterruptedException {
        int max = 0;
        for (int i = 0; i < 10_000; i++) {
            int maxInText = 0;
            String texts = testABC.take();
            char[] text = texts.toCharArray();
            for (char s : text) {
                if (s == abc) {
                    maxInText++;
                }
            }
            if (maxInText > max) {
                max = maxInText;
            }
        }
        return max;
    }

}
