package aed.sorting;

import com.sun.tools.jconsole.JConsoleContext;

import java.util.*;

import static aed.utils.TimeAnalysisUtils.*;

//podem alterar esta classe, se quiserem
class Limits
{
    char minChar;
    char maxChar;
    int maxLength = 0;

    @Override
    public String toString() {
        return "Limits{" +
                "minChar=" + minChar +
                ", maxChar=" + maxChar +
                ", maxLength=" + maxLength +
                '}';
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
        if (a.size() <= 1) return;
        for (int i = 1; i < a.size(); i++) {
            int prev = i-1;
            while (prev >= 0 && less(a.get(prev+1), a.get(prev))) {
                Collections.swap(a, prev, prev+1);
                prev--;
            }
        }
    }

    public static Limits determineLimits(List<String> a, int characterIndex)
    {
        Limits limits = new Limits();
        if (a.isEmpty()) limits.minChar = Character.MIN_VALUE;
        else limits.minChar = Character.MAX_VALUE;
        for (String s : a) {
            if (s.length() > characterIndex) {
                char c = s.charAt(characterIndex);
                if (c < limits.minChar) limits.minChar = c;
                if (c > limits.maxChar) limits.maxChar = c;
            } else {
                limits.minChar = 0;
            }
            if (s.length() > limits.maxLength) limits.maxLength = s.length();
        }
        return limits;
    }

    //ponto de entrada principal para o vosso algoritmo de ordenação
    public static void sort(String[] a)
    {
        recursive_sort(Arrays.asList(a),0);
    }


	//mas este é que faz o trabalho todo
    public static void recursive_sort(List<String> a, int characterIndex)
    {
        if (a.size() <= 70) {
            insertionSort(a);
            return;
        }

        Limits limite = determineLimits(a, characterIndex);

        @SuppressWarnings("unchecked")
        List<String>[] baldes = new List[limite.maxChar - limite.minChar + 1];
        List<String> baldeMalAmado = new ArrayList<>();
        for (String palavra : a) {
            if (palavra.length() > characterIndex) {
                int index = palavra.charAt(characterIndex) - limite.minChar;
                if (baldes[index] == null) baldes[index] = new ArrayList<>();
                baldes[index].add(palavra);
            } else {
                baldeMalAmado.add(palavra);
            }
        }
        for (List<String> balde : baldes) {
            if (balde != null) recursive_sort(balde, characterIndex+1);
        }
        int i = 0;
        for (String palavra : baldeMalAmado) {
            a.set(i++, palavra);
        }
        for (List<String> balde : baldes) {
            if (balde != null)
                for (String palavra : balde) {
                    a.set(i++, palavra);
                }
        }
    }

    public static void fasterSort(String[] a)
    {
        List<List<String>> baldes = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            baldes.add(new ArrayList<>());
        }

        for (String palavra : a) {
            if (!palavra.isEmpty()) {
                int index = palavra.charAt(0) - ' ' > 0 ? 2 : 1;
                baldes.get(index).add(palavra);
            } else {
                baldes.get(0).add(palavra);
            }
        }

        for (List<String> balde : baldes) {
            recursive_sort(balde, 0);
        }

        int i = 0;
        for (List<String> balde : baldes) {
            for (String palavra : balde) {
                a[i++] = palavra;
            }
        }
    }

    public static void main(String[] args)
    {

            System.out.println((int) ' ');
            System.out.println((int) 'a');
            System.out.println("  ".length());
//        List<String>a = new ArrayList<>();
//        a.add("ola"); // 5
//        a.add("adeus"); // 0
//        a.add("cenas"); //1
//        a.add("coisas"); //2
//        a.add("outras"); // 6
//        a.add("mais"); // 4
//        a.add("zenos"); // 7
//        a.add("limbo"); // 3
        // {adeus, cenas, coisas, limbo, mais, ola, outras, zenos}
        String[] b = {"ola", "  ", "cen", "cos", "ous", "mas", "zes", "lio"};
        // {cen, cos, ede, lio, mas, ola, ous, zes}
        fasterSort(b);
        System.out.println(Arrays.toString(b));

//        LIMITES
//        a b c d [e f g h i j k l m n o p q r s t u] v w x y z
//               /                                 /
//        <-    /  anda o minChar                 / ignorados
//             /                                 /
//            /                                 /
//           /                                 /
//          /                                 /
//         /                                 /
//        /                                 /
//        [e f g h i j k l m n o p q r s t u]
//        c[e]nas
//        l[i]mbo
    }


    //  Bonus: tecnicamente o melhor e pior algoritmo de ordenação são o mesmo, o BogoSort e foda
    private static void bogoSort(String[] a)
    {
        while (!isSorted(a))
            for (int i = 0; i < a.length; i++)
                exchange(a, i, R.nextInt(a.length));
    }
}
