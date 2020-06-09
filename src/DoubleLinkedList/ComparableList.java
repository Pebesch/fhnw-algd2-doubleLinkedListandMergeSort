package DoubleLinkedList;

public class ComparableList<E extends Comparable> {
    private int size;
    private Element head, tail;

    // Lösung schlägt dummy Elemente vor
    public ComparableList() {
        this.size = 0;
        this.head = null;
        this.tail = null;
    }

    public int size() {
        return size;
    }

    public void addHead(E data) {
        if(size == 0) {
            head = new Element(data);
            tail = head;
        } else {
            Element e = new Element(data);
            e.next = head.next;
            head.next.prev = e;
            head = e;
        }
        size++;
    }

    public void addTail(E data) {
        if(size == 0) {
            tail = new Element(data);
            head = tail;
        } else {
            Element e = new Element(data);
            e.prev = tail;
            tail.prev.next = e;
            tail = e;
        }
        size++;
    }

    public E removeHead() {
        if(size >= 2) {
            E data = (E)head.data;
            head.next.prev = null;
            head = head.next;
            return data;
        } else if(size == 1) {
            E data = (E)head.data;
            head = tail = null;
            return data;
        }
        return null;
    }

    public E removeTail() {
        if(size >= 2) {
            E data = (E)tail.data;
            tail.prev.next = null;
            tail = tail.prev;
            return data;
        } else if(size == 1) {
            E data = (E)tail.data;
            tail = head = null;
            return data;
        }
        return null;
    }

    @Override
    public String toString(){
        String result = "";
        Element<E> r = head;
        while (r != null && r.next != r){
            result += ("  " + r.data.toString());
            r = r.next;
        }
        return result;
    }

    private static class Element<E extends Comparable>  {
        E data;
        Element prev, next;

        public Element(E data) {
            this.data = data;
        }
    }
}
