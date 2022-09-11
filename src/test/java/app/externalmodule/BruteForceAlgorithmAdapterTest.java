package app.externalmodule;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BruteForceAlgorithmAdapterTest {

    private BruteForceAlgorithmAdapter bruteForceAlgorithmAdapter;

    public  BruteForceAlgorithmAdapterTest() {
        this.bruteForceAlgorithmAdapter = new BruteForceAlgorithmAdapter();
    }

    private int [] expectedResultInitialAndEndingAndMax1;
    private int [] expectedResultInitialAndEndingAndMax2;
    private int [] expectedResultInitialAndEndingAndMax3;

    private int [] actualResultInitialAndEndingAndMax1;
    private int [] actualResultInitialAndEndingAndMax2;
    private int [] actualResultInitialAndEndingAndMax3;

    private int [] expectedFinalArr1;
    private int [] expectedFinalArr2;
    private int [] expectedFinalArr3;

    private int [] actualFinalArr1;
    private int [] actualFinalArr2;
    private int [] actualFinalArr3;

    @BeforeEach
    void SetUp() {

        // Test 1
        expectedResultInitialAndEndingAndMax1 = new int[] {0, 10, 946};
        int[] inputArr1 = new int[]{171, 160, 105, 190, 107, 36, 33, 0, 47, 58, 39, -98, -278, -151, -53,
                -43, 38, 117, 204, 116, 70, 55, 11, 10, -15, -14, -118, -244, -4, 83, 222, 12, -8, -69, -117, -255};

        actualResultInitialAndEndingAndMax1 = bruteForceAlgorithmAdapter.performanceAlgorithm(inputArr1);

        expectedFinalArr1 = new int []{171, 160, 105, 190, 107, 36, 33, 0, 47, 58, 39};

        actualFinalArr1 = bruteForceAlgorithmAdapter.obtainFinalArray(inputArr1, 0, 11);

        // Test 2
        expectedResultInitialAndEndingAndMax2 = new int[] {0, 7, 944};
        int[] inputArr2 = new int[]{436, 333, 80, -1, -482, 112, 390, 76, -147, -165, 226, -441};

        actualResultInitialAndEndingAndMax2 = bruteForceAlgorithmAdapter.performanceAlgorithm(inputArr2);

        expectedFinalArr2 = new int []{436, 333, 80, -1, -482, 112, 390, 76};

        actualFinalArr2 = bruteForceAlgorithmAdapter.obtainFinalArray(inputArr2, 0, 8);

        // Test 3
        expectedResultInitialAndEndingAndMax3 = new int[] {0, 3, 944};
        int[] inputArr3 = new int[]{769, 79, -370, 466, -312, -215};

        actualResultInitialAndEndingAndMax3 = bruteForceAlgorithmAdapter.performanceAlgorithm(inputArr3);

        expectedFinalArr3 = new int []{769, 79, -370, 466};

        actualFinalArr3 = bruteForceAlgorithmAdapter.obtainFinalArray(inputArr3, 0, 4);

    }

    @Test
    void performanceAlgorithm() {
        Assertions.assertArrayEquals(expectedResultInitialAndEndingAndMax1, actualResultInitialAndEndingAndMax1);
        Assertions.assertArrayEquals(expectedResultInitialAndEndingAndMax2, actualResultInitialAndEndingAndMax2);
        Assertions.assertArrayEquals(expectedResultInitialAndEndingAndMax3, actualResultInitialAndEndingAndMax3);
    }

    @Test
    void obtainFinalArr() {
        Assertions.assertArrayEquals(expectedFinalArr1, actualFinalArr1);
        Assertions.assertArrayEquals(expectedFinalArr2, actualFinalArr2);
        Assertions.assertArrayEquals(expectedFinalArr3, actualFinalArr3);
    }

    @AfterEach
    void DumpMemory() {
        this.expectedResultInitialAndEndingAndMax1 = null;
        this.expectedResultInitialAndEndingAndMax2 = null;
        this.expectedResultInitialAndEndingAndMax3 = null;

        this.actualResultInitialAndEndingAndMax1 = null;
        this.actualResultInitialAndEndingAndMax2 = null;
        this.actualResultInitialAndEndingAndMax3 = null;

        this.expectedFinalArr1 = null;
        this.expectedFinalArr2 = null;
        this.expectedFinalArr3 = null;

        this.actualFinalArr1 = null;
        this.actualFinalArr2 = null;
        this.actualFinalArr3 = null;
    }

}