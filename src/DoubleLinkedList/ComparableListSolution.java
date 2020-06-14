package DoubleLinkedList;

import java.util.ListIterator;
import java.util.NoSuchElementException;

public class ComparableListSolution<T extends Comparable<T>> {
//***** assignment 3.1 : API ***************************************************

    public ComparableListSolution() {
        head = new Element<T>();
        tail = new Element<T>();
        head.data = null;
        tail.data = null;
        head.next = tail;
        head.prev = head;
        tail.prev = head;
        tail.next = tail;
    }

    public int size() {
        return size;
    }

    public void addHead(T t) {
        ++size;
        Element<T> r = new Element<T>();
        r.data = t;
        r.next = head.next;
        r.prev = head;
        head.next = r;
        r.next.prev = r;
    }

    public void addTail(T t) {
        ++size;
        Element<T> r = new Element<T>();
        r.data = t;
        r.prev = tail.prev;
        r.next = tail;
        tail.prev = r;
        r.prev.next = r;
    }

    public T removeHead() {
        if (size == 0)
            return null;
        else {
            --size;
            Element<T> r = head.next;
            head.next = r.next;
            r.next.prev = head;
            return r.data;
        }
    }

    public T removeTail() {
        if (size == 0)
            return null;
        else {
            --size;
            Element<T> r = tail.prev;
            tail.prev = r.prev;
            r.prev.next = tail;
            return r.data;
        }
    }

    private void remove(Element<T> e) {          // assignment 3.2
        if (e != null && e != head && e != tail) {
            e.prev.next = e.next;
            e.next.prev = e.prev;
            --size;
        }
    }


    public ListIterator<T> iterator() {            // assignment 3.3
        return new CListIterator();
    }

    public ListIterator<T> iterator(int index) {   // assignment 3.3
        if (index >= size) throw new IndexOutOfBoundsException();
        CListIterator ret = new CListIterator();
        Element<T> r = head.next;
        for (int i = 0; i < index; ++i)
            r = r.next;
        ret.next = r;
        ret.index = index;
        return ret;
    }

    @Override
    public String toString() {
        String result = "";
        Element<T> r = head.next;
        while (r.next != r) {
            result += ("  " + r.data != null ? r.data.toString() : "");
            r = r.next;
        }
        return result;
    }


//***** assignment 3.2 : iterator class ****************************************

    private class CListIterator implements ListIterator<T> {

        public CListIterator() {
            next = head.next;
            returned = null;
        }

        public CListIterator(CListIterator i) {
            next = i.next;
            returned = null;
            index = i.index;
        }


        public void remove() {
            if (returned == null) {
                throw new IllegalStateException();
            } else {
                if (returned == next) {       // previous() has been executed before
                    next = returned.next;
                } else {                       // next() has been executed before
                    index--;
                }
                ComparableListSolution.this.remove(returned);
                returned = null;
            }
        }

        public void set(T data) {
            if (returned == null)
                throw new IllegalStateException();
            else {
                if (!(data instanceof Comparable<?>))
                    throw new IllegalArgumentException();
                returned.data = data;
            }
        }

        public boolean hasNext() {
            return (next.next != next);
        }

        public boolean hasPrevious() {
            return (next.prev != next.prev.prev);
        }

        public T next() {
            if (!hasNext()) throw new NoSuchElementException();
            returned = next;
            next = next.next;
            ++index;
            return returned.data;
        }

        public T previous() {
            if (!hasPrevious()) throw new NoSuchElementException();
            next = next.prev;
            returned = next;
            --index;
            return next.data;
        }

        public int nextIndex() {
            if (hasNext()) {
                return this.index;
            } else {
                return size();
            }
        }

        public int previousIndex() {
            if (!hasPrevious())
                return -1;
            else
                return nextIndex() - 1;
        }

        public void add(T t) {
            Element<T> r = new Element<T>();
            r.data = t;
            r.prev = next.prev;
            r.next = next;
            next.prev.next = r;
            next.prev = r;
            ++size;
        }

        private Element<T> returned = null;
        private Element<T> next = null;
        private int index;

    }

//***** merge sort (assignment 3.4) ********************************************

    public void mergeSort() {
        if (this.size > 1) {
            ComparableListSolution<T> leftPart = this.split();
            leftPart.mergeSort();
            this.mergeSort();
            merge(leftPart);
        }
    }


    public void merge(ComparableListSolution<T> other) {
        ListIterator<T> group1 = this.iterator();
        ListIterator<T> group2 = other.iterator();
        ListIterator<T> insert = this.iterator();
        T element1, element2;
        while (group1.hasNext() || group2.hasNext()) {
            if (group1.hasNext() && group2.hasNext()) {
                element1 = group1.next();
                element2 = group2.next();
                if (element1.compareTo(element2) < 0) {
                    insert.add(element1);
                    group2.previous();
                } else {
                    insert.add(element2);
                    group1.previous();
                }
            } else {
                if (group1.hasNext()) {
                    insert.add(group1.next());
                } else {
                    insert.add(group2.next());
                }
            }
        }
        while (insert.hasNext()) {
            insert.next();
            insert.remove();
        }
    }


    public ComparableListSolution<T> split() {
        ComparableListSolution<T> leftPart = new ComparableListSolution<>();
        int size = this.size / 2;
        for (int i = 0; i < size; ++i) {
            leftPart.addTail(this.removeHead());
        }
        return leftPart;
    }


    public boolean isSorted() {
        if (this.size < 2) {
            return true;
        } else {
            ListIterator<T> r = this.iterator();
            T oldValue;
            T newValue = r.next();
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

//***** attributes and Element class *******************************************

    private static class Element<T> {
        T data;
        Element<T> next;
        Element<T> prev;
    }


    private Element<T> head;
    private Element<T> tail;
    private int size = 0;
}
