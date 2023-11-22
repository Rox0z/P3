package aed.sorting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

//podem alterar esta classe, se quiserem
class Limits
{
    char minChar;
    char maxChar;
    int maxLength;
}

public class RecursiveStringSort extends Sort
{
    private static final Random R = new Random();


    //esta implementação base do quicksort é fornecida para que possam comparar o tempo de execução do quicksort
    //com a vossa implementação do RecursiveStringSort
    public static <T extends Comparable<T>> void quicksort(T[] a)
    {
        qsort(a, 0, a.length-1);   
    }

    private static <T extends Comparable<T>> void qsort(T[] a, int low, int high)
    {
        if (high <= low) return;
        int j = partition(a, low, high);
        qsort(a, low, j-1);
        qsort(a, j+1, high);
    }

    private static <T extends Comparable<T>> int partition(T[] a, int low, int high)
    {
        //partition into a[low...j-1],a[j],[aj+1...high] and return j
        //choose a random pivot
        int pivotIndex = low + R.nextInt(high+1-low);
        exchange(a,low,pivotIndex);
        T v = a[low];
        int i = low, j = high +1;

        while(true)
        {
            while(less(a[++i],v)) if(i == high) break;
            while(less(v,a[--j])) if(j == low) break;

            if(i >= j) break;
            exchange(a , i, j);
        }
        exchange(a, low, j);

        return j;
    }

    

    //método de ordenação insertionSort
    //no entanto este método recebe uma Lista de Strings em vez de um Array de Strings
    public static void insertionSort(List<String> a)
    {
        //TODO: implement
    }

    public static Limits determineLimits(List<String> a, int characterIndex)
    {
        //TODO implement

        return null;
    }

    //ponto de entrada principal para o vosso algoritmo de ordenação
    public static void sort(String[] a)
    {
        recursive_sort(Arrays.asList(a),0);
    }


	//mas este é que faz o trabalho todo
    public static void recursive_sort(List<String> a, int depth)
    {
        //TODO: implement
    }

    public static void fasterSort(String[] a)
    {
        //TODO: implement
    }

    //  Bonus: tecnicamente o melhor e pior algoritmo de ordenação são o mesmo, o BogoSort
    private static void bogoSort(String[] a)
    {
        while (!isSorted(a))
            for (int i = 0; i < a.length; i++)
                exchange(a, i, R.nextInt(a.length));
    }
}
