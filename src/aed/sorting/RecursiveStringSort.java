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

    public Limits() {
        this.minChar = Character.MAX_VALUE;
        this.maxChar = Character.MIN_VALUE;
        this.maxLength = 0;
    }
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
        for (int i = 1; i < a.size(); i++) {
            int prev = i-1;
            while (prev >= 0 && less(a.get(prev+1), a.get(prev))) {
                a.add(prev, a.remove(prev+1));
                prev--;
            }
        }
    }

    public static Limits determineLimits(List<String> a, int characterIndex)
    {
        Limits limits = new Limits();
        for (String s : a) {
            if (s.length() <= characterIndex) limits.minChar = Character.MIN_VALUE;
            else {
                char c = s.charAt(characterIndex);
                if (c < limits.minChar) limits.minChar = c;
                if (c > limits.maxChar) limits.maxChar = c;
                if (s.length() > limits.maxLength) limits.maxLength = s.length();
            }
        }
        return limits;
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

    public static void main(String[] args)
    {
        List<String>a = new ArrayList<String>();
        a.add("ola"); // 5
        a.add("adeus"); // 0
        a.add("cenas"); //1
        a.add("coisas"); //2
        a.add("outras"); // 6
        a.add("mais"); // 4
        a.add("zenos"); // 7
        a.add("limbo"); // 3
        // adeus, cenas, coisas, limbo, mais, ola, outras, zenos
        insertionSort(a);
        System.out.println(a);
    }


    //  Bonus: tecnicamente o melhor e pior algoritmo de ordenação são o mesmo, o BogoSort e foda
    private static void bogoSort(String[] a)
    {
        while (!isSorted(a))
            for (int i = 0; i < a.length; i++)
                exchange(a, i, R.nextInt(a.length));
    }
}
