import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class test {
    int processCount;
    int resourceCount;
    int[] available;
    int[][] maximum;
    int[][] allocation;

    public test(String fileName) throws IOException {
        Scanner sc = new Scanner(new BufferedReader(new FileReader(fileName)));
        processCount = sc.nextInt();
        resourceCount = sc.nextInt();
        available = new int[resourceCount];
        for (int i = 0; i < resourceCount; i++) {
            available[i] = sc.nextInt();
        }
        maximum = new int[processCount][resourceCount];
        allocation = new int[processCount][resourceCount];
        for (int i = 0; i < processCount; i++) {
            for (int j = 0; j < resourceCount; j++) {
                maximum[i][j] = sc.nextInt();
            }
        }
        for (int i = 0; i < processCount; i++) {
            for (int j = 0; j < resourceCount; j++) {
                allocation[i][j] = sc.nextInt();
            }
        }
        sc.close();
    }

    public boolean isSafe() {
        int[] work = Arrays.copyOf(available, resourceCount);
        boolean[] finish = new boolean[processCount];
        int[] safeSeq = new int[processCount];
        int count = 0;
        int solutionCount = 0;
        boolean found = false;
    
        while (count < processCount && solutionCount < 2) {
            boolean exists = false;
            for (int i = 0; i < processCount; i++) {
                if (!finish[i]) {
                    exists = true;
                    int j;
                    for (j = 0; j < resourceCount; j++) {
                        if (maximum[i][j] - allocation[i][j] > work[j]) {
                            break;
                        }
                    }
                    if (j == resourceCount) {
                        for (int k = 0; k < resourceCount; k++) {
                            work[k] += allocation[i][k];
                        }
                        safeSeq[count++] = i;
                        finish[i] = true;
                        exists = false;
                    }
                }
            }
            if (!exists) {
                found = true;
                if (count == processCount) {
                    solutionCount++;
                    if (solutionCount == 1) {
                        System.out.println("Solution 1: " + Arrays.toString(safeSeq));
                    } else if (solutionCount == 2) {
                        System.out.println("Solution 2: " + Arrays.toString(safeSeq));
                        return true;
                    }
                    count = 0;
                    work = Arrays.copyOf(available, resourceCount);
                    finish = new boolean[processCount];
                    safeSeq = new int[processCount];
                }
            } else {
                count = 0;
            }
        }
        if (!found) {
            System.out.println("There is only one solution");
            System.out.println("Safe sequence is: " + Arrays.toString(safeSeq));
        }
        return true;
    }
    
    

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the name of the input file: ");
        String fileName = sc.nextLine();
        test ba = new test(fileName);
        System.out.println("Number of processes: " + ba.processCount);
        System.out.println("Number of resources: " + ba.resourceCount);
        if (ba.isSafe()) {
            System.out.println("The system is in a safe state.");
        }
    }
}
