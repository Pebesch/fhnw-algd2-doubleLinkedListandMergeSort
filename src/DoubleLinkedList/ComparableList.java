package DoubleLinkedList;

public class ComparableList<E extends Comparable<E>> {
    private int size;
    private Element<E> head, tail;

    // Solution suggests Dummy elements
    public ComparableList() {
        this.size = 0;
        this.head = new Element<E>(null);
        this.tail = new Element<E>(null);
        head.next = tail;
        head.prev = head;
        tail.prev = head;
        tail.next = tail;
    }

    public int size() {
        return size;
    }

    public void addHead(E data) {
        Element<E> e = new Element<E>(data);
        e.next = head.next;
        e.prev = head;
        head.next.prev = e;
        head.next = e;
        size++;
    }

    public void addTail(E data) {
        Element<E> e = new Element<E>(data);
        e.prev = tail.prev;
        e.next = tail;
        e.prev.next = e;
        tail.prev = e;
        size++;
    }

    public E removeHead() {
        if(size == 0) {
            return null;
        } else {
            Element<E> e = head.next;
            head.next = e.next;
            e.next.prev = head;
            size--;
            return (E)e.data;
        }
    }

    public E removeTail() {
        if(size == 0) {
            return null;
        } else {
            Element<E> e = tail.prev;
            tail.prev = e.prev;
            e.prev.next = tail;
            size--;
            return (E)e.data;
        }
    }

    @Override
    public String toString(){
        String result = "";
        Element<E> r = head.next;
        result += "Head";
        while (r.next != r){
            String str = r.data != null ? r.data.toString() : "";
            result += ("  " + str);
            r = r.next;
        }
        result += " Tail";
        return result;
    }

    private static class Element<E>  {
        E data;
        Element<E> prev, next;

        public Element(E data) {
            this.data = data;
        }
    }
}
