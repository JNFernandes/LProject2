package app.domain.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class BubbleSortTest {

    private Comparator<Integer> criterion;
    private BubbleSort bubbleSort;
    private List<Integer> listOfInts;
    private List<Double> listOfDoubles;

    @BeforeEach
    public void setUp()
    {
        listOfInts = new ArrayList<>(); // {65, 25, 12, 22, 11};
        listOfInts.add(65);
        listOfInts.add(25);
        listOfInts.add(12);
        listOfInts.add(22);
        listOfInts.add(11);

        listOfDoubles = new ArrayList<>(); // {65, 25, 12, 22, 11};
        listOfDoubles.add(65.0);
        listOfDoubles.add(65.0);
        listOfDoubles.add(12.0);
        listOfDoubles.add(22.0);
        listOfDoubles.add(22.0);

        bubbleSort = new BubbleSort();
        criterion = new Comparator<>() {
            @Override
            public int compare(Integer int1, Integer int2)
            {
                return Integer.compare(int1, int2);
            }
        };

    }

    @Test
    public void shouldDoNothingWithEmptyArray()
    {
        Assertions.assertEquals(new ArrayList<Integer>(), bubbleSort.sortData(new ArrayList<>(), criterion));
    }

    @Test
    public void shouldDoNothingWithOneElementArray()
    {
        List<Integer> expected = new ArrayList<>();
        expected.add(11);
        Assertions.assertEquals(expected, bubbleSort.sortData(expected, criterion));
    }

    @Test
    public void shouldSortListOfIntegers()
    {
        List<Integer> expected = new ArrayList<>();
        expected.add(11);
        expected.add(12);
        expected.add(22);
        expected.add(25);
        expected.add(65);

        List<Integer> actual = bubbleSort.sortData(listOfInts, criterion);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void givenListWithEqualEntries_ReturnsSortedList()
    {
        Comparator<Double> doubleCriterion = new Comparator<>() {
            @Override
            public int compare(Double dbl1, Double dbl2)
            {
                return Double.compare(dbl1, dbl2);
            }
        };


        List<Double> expected = new ArrayList<>();
        expected.add(12.0);
        expected.add(22.0);
        expected.add(22.0);
        expected.add(65.0);
        expected.add(65.0);

        Assertions.assertEquals(expected, bubbleSort.sortData(listOfDoubles,doubleCriterion));

    }

}
