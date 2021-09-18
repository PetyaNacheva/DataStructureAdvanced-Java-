import java.util.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        AATree<Integer> aaTree = new AATree<>();
        int[] input = {18, 13, 1, 7
                , 42
                , 73, 56, 24, 6, 2, 74, 69, 55
        };

        for (Integer integer : input) {
            aaTree.insert(integer);
        }

        System.out.println();
        System.out.println("size = " + aaTree.countNodes());
        System.out.println();
        List<Integer> list = new ArrayList<>();
        aaTree.inOrder(list::add);

        int count = 0;
        for (Integer integer : list) {
            System.out.println(++count + " -> " + integer);
        }
    }
}
