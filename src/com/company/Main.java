package com.company;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) {
        int [] array = new int[] {100, 6, 88, 44, 9, 177, 5, 99};

        int [] arrRnd = new int[100000];

        Random rnd = new Random();

        for(int i = 0; i<arrRnd.length; i++){
            arrRnd[i] = Math.abs(rnd.nextInt());
        }

        System.out.println("Массив готов");

        long tBubbleSort = timer(() -> {
            bubbleSort(array);
        }, TimeUnit.NANOSECONDS);

        long tSelectionSort = timer(() -> {
            selectionSort(array);
        }, TimeUnit.NANOSECONDS);

        long tQuickSort = timer(() -> {
            quickSort(array, 0, array.length-1);
        }, TimeUnit.NANOSECONDS);

        long tMergeSort = timer(() -> {
            mergeSort(array);
        }, TimeUnit.NANOSECONDS);

        System.out.println("---------Bubble Sort---------");
        System.out.printf("%,9.3f ms\n", tBubbleSort/1_000_000.0);
        System.out.println("---------Selection Sort---------");
        System.out.printf("%,9.3f ms\n", tSelectionSort/1_000_000.0);
        System.out.println("---------Quick Sort---------");
        System.out.printf("%,9.3f ms\n", tQuickSort/1_000_000.0);
        System.out.println("---------Merge Sort---------");
        System.out.printf("%,9.3f ms\n", tMergeSort/1_000_000.0);
    }



    private static long timer(Runnable method, TimeUnit timeUnit) {
        long time = System.nanoTime();
        method.run();
        time = System.nanoTime() - time;
        return TimeUnit.NANOSECONDS.convert(time, timeUnit);
    }

    public static int[] mergeSort(int[] array) {
        int[] tmp;
        int[] currentSrc = array; //массив источник
        int[] currentDest = new int[array.length]; //массив приемник

        int size = 1; //текущий размер рез массива
        while (size < array.length) {
            for (int i = 0; i < array.length; i += 2 * size) {
                merge(currentSrc, i, currentSrc, i + size, currentDest, i, size);
            }

            tmp = currentSrc;
            currentSrc = currentDest;
            currentDest = tmp;

            size = size * 2;

            //printArray(currentSrc);
            //System.out.println();

        }
        return currentSrc;
    }

    private static void merge(int[] arr1, //первый массив
                              int arr1Start, //начало слияния для массива 1
                              int[] arr2, //второй массив
                              int arr2Start, //начало слияния для массива 2
                              int[] resArr, //резаультирующий массив
                              int destStart, //размер подмассива
                              int size) {
        int index1 = arr1Start;
        int index2 = arr2Start;

        int arr1End = Math.min(arr1Start + size, arr1.length); //подмассив для рез массива
        int arr2End = Math.min(arr2Start + size, arr2.length); //подмассив для рез массива

        int iterationCount = arr1End - arr1Start + arr2End - arr2Start; //количество итераций для создания рез массива

        for (int i = destStart; i < destStart + iterationCount; i++) {
            if (index1 < arr1End && (index2 >= arr2End || arr1[index1] < arr2[index2])) {
                //если больше элемент первого массива, записываем его в рез массив
                resArr[i] = arr1[index1];
                index1++;
            } else {
                //если больше элемент второго массива, записываем его в рез массив
                resArr[i] = arr2[index2];
                index2++;
            }
        }
    }

    //quickSort(array, 0, array.length-1)
    public static void quickSort(int[] arr, int from, int to){
        if(from<to){
            int divInd = partition(arr, from, to);
            quickSort(arr, from, divInd - 1);
            quickSort(arr, divInd, to);
        }
    }

    public static int partition(int[] arr, int from, int to){
        int rightIng = to;
        int leftInd = from;

        int pivot = arr[from + (to-from) /2];

        while(leftInd <= rightIng) {

            while(arr[leftInd] < pivot){
                leftInd++;
            }

            while(arr[rightIng]>pivot){
                rightIng--;
            }

            if(leftInd <= rightIng){
                swap(arr, rightIng, leftInd);
                leftInd++;
                rightIng--;
            }
        }
        return leftInd;
    }

    public static void swap(int[] arr, int ind1, int ind2){
        int temp = arr[ind1];
        arr[ind1] = arr[ind2];
        arr[ind2] = temp;
    }

   //-------------------------------------------------

    public static void selectionSort(int[] arr){
        for(int i=0; i<arr.length; i++){
            int index = findMin(arr, i);
            int temp = arr[i];
            arr[i] = arr[index];
            arr[index] = temp;
        }
        //printArray(arr);
    }

    public static int findMin(int[] arr, int start){
        int minVal = arr[start];
        int index = start;

        for(int i = start+1; i<arr.length; i++){
            if(arr[i]<minVal){
                minVal=arr[i];
                index = i;
            }
        }
        return index;
    }
    //-------------------------------------------------
    public static void bubbleSort(int[] arr){
        boolean finish = false;

        while(!finish){
            finish = true;
            for(int i = 1; i<arr.length; i++) {
                if (arr[i] < arr[i-1]) {
                    int temp;
                    temp = arr[i-1];
                    arr[i-1] = arr[i];
                    arr[i] = temp;
                    finish = false;
                }
            }
            //printArray(arr);
            //System.out.println();
        }
    }
    //-------------------------------------------------
    public static void printArray(int[] arr){
        for(int i = 0; i<arr.length; i++){
            System.out.print(arr[i]+" ");
        }
    }
}


