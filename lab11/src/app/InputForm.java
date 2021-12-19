package app;

import javax.swing.*;
import java.util.Arrays;
import java.util.Scanner;

public class InputForm extends JFrame {
    static private ScalarProduct sc;
    private JFrame frame;
    private JPanel mainPanel;
    private JTextField vectorATxtField;
    private JTextField vectorBTxtField;
    private JButton confirmBtn;
    private Double[] a;
    private Double[] b;
    private boolean isDone = false;

    public InputForm() {
        confirmBtn.addActionListener(e -> {
            createVectors();
            isDone = true;
            InputForm.super.dispose();
        });
    }

    public static void main(String[] args) {
        sc = new ScalarProduct();

        sc.a = new Double[]{4.0d, 5.0d, 2.0d};
        sc.b = new Double[]{3.0d, 4.0d, 5.0d};
        System.out.println("Program will cut longer vector to compute result of vectors that have the same leghts");
        System.out.println("Sample vectors");
        System.out.println("Vector A: " + Arrays.toString(sc.a));
        System.out.println("Vector B: " + Arrays.toString(sc.b));
        System.out.println("------------");
        Scanner scanner = new Scanner(System.in);

        int choice;
        do {
            System.out.println("Choose method:");
            System.out.println("1. Multi01");
            System.out.println("2. Multi02");
            System.out.println("3. Multi03");
            System.out.println("0. Exit");
            System.out.println("------------");
            System.out.print("Select: ");
            choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    sc.c = sc.multi01(sc.a, sc.b);
                    System.out.println("Result: c = " + sc.c);
                    break;
                case 2:
                    sc.c = sc.multi02(sc.a);
                    System.out.println("Result: c = " + sc.c);
                    break;
                case 3:
                    sc.multi03();
                    System.out.println("Result: c = " + sc.c);
                    break;
                case 0:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Wrong input!");
                    choice = 0;
            }
        } while (choice != 0);
    }

    private void createVectors() {
        if (!vectorATxtField.getText().isEmpty() && !vectorBTxtField.getText().isEmpty()) {
            String[] vectorAElements = vectorATxtField.getText().split(",");
            String[] vectorBElements = vectorBTxtField.getText().split(",");
            a = new Double[vectorAElements.length];
            b = new Double[vectorBElements.length];
            for (int i = 0; i < vectorAElements.length; i++) {
                a[i] = Double.parseDouble(vectorAElements[i]);
            }
            for (int i = 0; i < vectorBElements.length; i++) {
                b[i] = Double.parseDouble(vectorBElements[i]);
            }
            sc.a = a.clone();
            sc.b = b.clone();
        }
    }

    public void open() throws InterruptedException {
        this.setContentPane(mainPanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setResizable(false);
        this.setVisible(true);
        while (!isDone) {
            Thread.sleep(100);
        }
    }
}
