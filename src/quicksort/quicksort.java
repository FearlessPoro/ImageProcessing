package quicksort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class quicksort {

    public static void quicksort(List<Long> toSort) {
        int L = 0;
        int R = toSort.size()-1;
        int k;
        int q;
        long tmp;
        int comparator;
        do {
            if ( L < R) {
                q = partition(L, R, toSort);
                k = getMaxElement(L, q-1, toSort);
                tmp=toSort.get(k);
                if (q != 0) {
                    toSort.set(k, toSort.get(q - 1));
                    toSort.set(q - 1, toSort.get(q));
                } else {
                    toSort.set(k, toSort.get(q ));
                }
                toSort.set(q, toSort.get(R));
                toSort.set(R, tmp);
                R = q-2;
            } else {
                L = R+2;
                if (R+1 < 0) {
                    comparator = 0;
                } else {
                    comparator = R+1;
                }
                for (int i = R+2; i < toSort.size(); i++) {
                    if(toSort.get(i) < toSort.get(comparator)){
                        swap(toSort, comparator+1, comparator);
                        if (i != comparator) {
                            swap(toSort, i, comparator);
                        }
                        R = i;
                        break;

                    }
                }

            }

        } while (!isSorted(toSort));
    }

    public static boolean isSorted(List<Long> list) {
        for (int i = 1; i < list.size(); i++) {
            if (list.get(i-1) > list.get(i)) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        List<Long> list = new ArrayList<>();
        list.add(4L);
        list.add(2L);
        list.add(-2L);
        list.add(1L);
        list.add(5L);
        list.add(8L);
        list.add(3L);
        System.out.println(Arrays.toString(list.toArray()));
        quicksort(list);
        System.out.println(Arrays.toString(list.toArray()));
    }


    public static int getMaxElement(int L, int R, List<Long> list) {
        int max = L;
        for (int i = L+1; i <= R; i++) {
            if (list.get(i) < list.get(max)) {
                max = i;
            }
        }
        return max;

    }

    public static int partition(int L, int R, List<Long> toSort) {
        long q = toSort.get(R);
        int i = L - 1;
        for (int j = L; j < R; j++) {
            if (toSort.get(j) <= q) {
                i++;
                swap(toSort, i, j);
            }
        }
        swap(toSort, i+1, R);

        return i+1;
    }

    public static void swap(List<Long> list, int one, int two) {
        long temp = list.get(one);
        list.set(one, list.get(two));
        list.set(two, temp);
    }
}
