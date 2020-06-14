package DoubleLinkedList;

import java.util.ListIterator;
import java.util.NoSuchElementException;

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

    private void remove(Element<E> e) {
        if (e != null && e != head && e != tail){
            e.prev.next = e.next;
            e.next.prev = e.prev;
            size--;
        }
    }

    @Override
    public String toString(){
        String result = "";
        Element<E> r = head.next;
        while (r.next != r){
            String str = r.data != null ? r.data.toString() : "";
            result += ("  " + str);
            r = r.next;
        }
        return result;
    }

    public CListIterator iterator() {
        return new CListIterator();
    }

    public ListIterator<E> iterator(int index) {
        if (index >= size)  throw new IndexOutOfBoundsException();
        CListIterator ret = new CListIterator();
        Element<E> r = head.next;
        for (int i=0; i<index; ++i)
            r = r.next;
        ret.next = r;
        ret.index = index;
        return ret;
    }

    private static class Element<E>  {
        E data;
        Element<E> prev, next;

        public Element(E data) {
            this.data = data;
        }
    }

    /* Merge Sort */
    private void merge(ComparableList<E> other) {
        CListIterator iterator = this.iterator();
        CListIterator otherItr = other.iterator();
        CListIterator inserter = this.iterator();

        while(iterator.hasNext() || otherItr.hasNext()) {
            if(iterator.hasNext() && otherItr.hasNext()) {
                E elem1 = iterator.next();
                E elem2 = otherItr.next();
                if (elem1.compareTo(elem2) < 0) {
                    inserter.add(elem1);
                    otherItr.previous();
                }else{
                    inserter.add(elem2);
                    iterator.previous();
                }
            } else {
                if(iterator.hasNext()) {
                    inserter.add(iterator.next());
                } else {
                    inserter.add(otherItr.next());
                }
            }
        }
        while (inserter.hasNext()){
            inserter.next();
            inserter.remove();
        }
    }

    private ComparableList<E> split() {
        ComparableList<E> other = new ComparableList<>();
        // Using size / 2 in the for loop will gradually make it smaller
        int half = size / 2;
        for(int i = 0; i < half; i++) {
            other.addTail(this.removeHead());
        }
        return other;
    }

    public void mergeSort() {
        if(size > 1) {
            ComparableList other = this.split();
            other.mergeSort();
            this.mergeSort();
            merge(other);
        }
    }

    public boolean isSorted(){
        if (this.size < 2) {
            return true;
        }else{
            ListIterator<E> r = this.iterator();
            E oldValue;
            E newValue = r.next();
            while (r.hasNext()) {
                oldValue = newValue;
                newValue = r.next();
                if (oldValue.compareTo(newValue) > 0) {
                    return false;
                }
            }
        }
        return true;
    }

    /* C List Iterator */
    private class CListIterator implements ListIterator<E> {
        // Last element returned
        Element<E> returned;
        int index;
        // Next to index of iterator
        Element<E> next;

        public CListIterator() {
            this.index = 0;
            this.next = (Element<E>)head.next;
            returned = null;
        }

        public CListIterator(CListIterator i){
            next = i.next;
            returned = null;
            index = i.index;
        }

        @Override
        public boolean hasNext() {
            return next != next.next;
        }

        @Override
        public E next() {
            if(!hasNext()) throw new NoSuchElementException();
            returned = next;
            next = next.next;
            index++;
            return returned.data;
        }

        @Override
        public boolean hasPrevious() {
            return next.prev != next.prev.prev;
        }

        @Override
        public E previous() {
            if(!hasPrevious()) throw new NoSuchElementException();
            next = next.prev;
            returned = next;
            index--;
            return returned.data;
        }

        @Override
        public int nextIndex() {
            if(hasNext()) {
                return this.index;
            } else {
                return size();
            }
        }

        @Override
        public int previousIndex() {
            if(hasPrevious()) {
                return index - 1;
            } else {
                return -1;
            }
        }

        @Override
        public void remove() {
            if (returned == null) {
                throw new IllegalStateException();
            } else {
                if (returned == next) {
                    next = returned.next;
                } else index--;
                ComparableList.this.remove(returned);
                returned = null;
            }
        }

        @Override
        public void set(E data){
            if (returned == null)
                throw new IllegalStateException();
            else{
                if (! (data instanceof Comparable<?>)){
                    throw new IllegalArgumentException();
                }
                returned.data = data;
            }
        }

        @Override
        public void add(E e) {
            Element<E> r = new Element<E>(e);
            r.prev = next.prev;
            r.next = next;
            next.prev.next = r;
            next.prev = r;
            size++;
        }
    }
}
