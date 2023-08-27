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
            int max = 0;
            String maxTextA = null;
            for (int i = 0; i < 10_000; i++) {
                int maxInText = 0;
                try {
                    String textA = maxA.take();
                    char[] text = textA.toCharArray();
                    for (char s : text) {
                        if (s == 'a') {
                            maxInText++;
                        }
                    }
                    if (maxInText > max) {
                        max = maxInText;
                        maxTextA = textA;
                    }
                } catch (InterruptedException e) {
                    return;
                }
            }
            System.out.println("Текст с максимальным количеством букв a (" + max + "):\n" + maxTextA + "\n");
        }).start();

        new Thread(() -> {
            int max = 0;
            String maxTextB = null;
            for (int i = 0; i < 10_000; i++) {
                int maxInText = 0;
                try {
                    String textB = maxB.take();
                    char[] text = textB.toCharArray();
                    for (char s : text) {
                        if (s == 'a') {
                            maxInText++;
                        }
                    }
                    if (maxInText > max) {
                        max = maxInText;
                        maxTextB = textB;
                    }
                } catch (InterruptedException e) {
                    return;
                }
            }
            System.out.println("Текст с максимальным количеством букв b (" + max + "):\n" + maxTextB + "\n");
        }).start();

        new Thread(() -> {
            int max = 0;
            String maxTextC = null;
            for (int i = 0; i < 10_000; i++) {
                int maxInText = 0;
                try {
                    String textC = maxC.take();
                    char[] text = textC.toCharArray();
                    for (char s : text) {
                        if (s == 'a') {
                            maxInText++;
                        }
                    }
                    if (maxInText > max) {
                        max = maxInText;
                        maxTextC = textC;
                    }
                } catch (InterruptedException e) {
                    return;
                }
            }
            System.out.println("Текст с максимальным количеством букв c (" + max + "):\n" + maxTextC + "\n");
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

}
