package com.izeye.app.numbergame;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;

/**
 * Main class.
 *
 * @author Johnny Lim
 */
public class Main {
    public static void main(String[] args) throws UnsupportedAudioFileException, LineUnavailableException, IOException, InterruptedException {
        try (Scanner scanner = new Scanner(System.in)) {
            int questionCount = 0;
            List<String> wrongAnswers = new ArrayList<>();

            int number  = 4;
            int start = number;
            int end = number;
            for (int i = start; i <= end; i++) {
                for (int j = 1; j <= 9; j++) {
                    questionCount++;

                    String question = String.format("%d x %d = ", i, j);
                    System.out.print(question);
                    int answer = i * j;
                    int yourAnswer = scanner.nextInt();
                    if (yourAnswer == answer) {
                        System.out.println("Correct!");
                        playSound("sounds/correct.wav");
                    }
                    else {
                        System.out.printf("Wrong! The answer is %d.%n", answer);
                        playSound("sounds/wrong.wav");
                        wrongAnswers.add(String.format("%s %d -> %d", question, yourAnswer, answer));
                    }
                }
            }

            double score = (questionCount - wrongAnswers.size()) * 100d / questionCount;
            System.out.printf("Your score is %.2f%n.", score);
            System.out.println("Wrong answers:");
            wrongAnswers.forEach(System.out::println);
            if (score == 100d) {
                playSound("sounds/perfect.wav");
            }
            else {
                playSound("sounds/fail.wav");
            }
        }
    }

    private static void playSound(String soundPath)
            throws UnsupportedAudioFileException, IOException, LineUnavailableException, InterruptedException {
        InputStream inputStream = Main.class.getClassLoader().getResourceAsStream(soundPath);
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(inputStream);
        DataLine.Info info = new DataLine.Info(Clip.class, audioInputStream.getFormat());
        Clip clip = (Clip) AudioSystem.getLine(info);
        clip.open(audioInputStream);
        CountDownLatch latch = new CountDownLatch(1);
        clip.addLineListener((event) -> {
            if (event.getType() == LineEvent.Type.STOP) {
                latch.countDown();
            }
        });
        clip.start();
        latch.await();
        clip.close();
        audioInputStream.close();
    }
}
