import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public class Driver<T, V> {

    public void benchmarkInsertionSort(int k) {

        for (int i = 1; i <= 5; i++) {
            BaseHelper<Integer> helper = new BaseHelper<>("InsertionSort", k);
            GenericSort<Integer> sorter = new InsertionSort(helper);

            final UnaryOperator<Integer[]> prefunc = (arr) -> {
                Integer[] foo2 = sorter.sort(arr, true);
                return null;
            };

            final Function<Integer[], Integer[]> targetfunc = (arr) -> {
                Integer[] foo2 = sorter.sort(arr, true);
                return foo2;
            };
            Integer[] arr = getRandomOrderedIntArr(k);
            Supplier<Integer[]> argsSupplier = getSupplierFunc(arr);
            double mlt = new Timer().repeat(100, argsSupplier, targetfunc, prefunc, null);
            System.out.println("Size of Random Ordered Array:" + k + " Mean lap time:" + mlt);

            arr = getOrderedIntArr(k);
            argsSupplier = getSupplierFunc(arr);
            mlt = new Timer().repeat(100, argsSupplier, targetfunc, prefunc, null);
            System.out.println("Size of Ordered Array:" + k + " Mean lap time:" + mlt);

            arr = getPartiallyOrderedIntArr(k);
            argsSupplier = getSupplierFunc(arr);
            mlt = new Timer().repeat(100, argsSupplier, targetfunc, prefunc, null);
            System.out.println("Size of Partially Ordered Array:" + k + " Mean lap time:" + mlt);

            arr = getReverseOrderedArray(k);
            argsSupplier = getSupplierFunc(arr);
            mlt = new Timer().repeat(100, argsSupplier, targetfunc, prefunc, null);
            System.out.println("Size of Reverse Ordered Array:" + k + " Mean lap time:" + mlt);

            k *= 2;
        }


    }

    public Supplier<Integer[]> getSupplierFunc(Integer[] arr){
        return ()->{
            return  arr;
        };
    }


    public static Integer[] getOrderedIntArr(int size){
        Integer[] orderedArr = new Integer[size];
        for(int i=0; i<orderedArr.length; i++){
            orderedArr[i] = i;
        }

        return orderedArr;
    }

    public static Integer[] getRandomOrderedIntArr(int size){
        Integer[] randomOrderedArr = new Integer[size];
        Random rand = new Random();
        for(int i=0; i<randomOrderedArr.length; i++){
            randomOrderedArr[i] = Math.abs(rand.nextInt(100000));
        }

        return randomOrderedArr;
    }

    public static Integer[] getPartiallyOrderedIntArr(int size){
        Integer[] partiallyOrderedArr = new Integer[size];
        Random rand = new Random();
        int checkpoint1 = size/3;
        int checkpoint2 = size*2/3;
        for(int i=0; i<checkpoint1; i++){
            partiallyOrderedArr[i] = Math.abs(rand.nextInt(100000));
        }
        for(int i=checkpoint1; i<checkpoint2; i++){
            partiallyOrderedArr[i] = i;
        }
        for(int i=checkpoint2; i<size; i++){
            partiallyOrderedArr[i] = Math.abs(rand.nextInt(100000));
        }

        return partiallyOrderedArr;
    }

    public static Integer[] getReverseOrderedArray(int size){
        Integer[] reverseOrderedArray = new Integer[size];
        for(int i=size-1; i>=0; i--){
            reverseOrderedArray[i] = (size-1-i);
        }

        return reverseOrderedArray;
    }

    public static void main(String[] args) {
        int startingValueN = 1000;
        new Driver<Integer, Integer>().benchmarkInsertionSort(startingValueN);
    }
}
