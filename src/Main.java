import DoubleLinkedList.ComparableList;

import java.util.ListIterator;

public class Main {
    public static void main(String[] args){
        ComparableList<Integer> list1 = new ComparableList<Integer>();
        for (int i = 0; i < 5; i++) {
            list1.addHead(i);
        }
        System.out.println(list1);

        for (int i = 5; i < 10; i++) {
            list1.addTail(i);
        }
        System.out.println(list1);
    }
}
