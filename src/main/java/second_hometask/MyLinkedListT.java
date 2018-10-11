package second_hometask;
import java.util.Collection;
import java.util.Iterator;

public class MyLinkedListT<T> implements Collection<T> {
    T head;
    T last;
    int size;

    public MyLinkedListT() {
        this.head = null;
        this.last = null;
        this.size = 0;
    }

    public MyLinkedListT(T head) {
        this.head = head;
        this.last = head;
        this.size = 1;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }


    public boolean contains(Object o) {
        if (o.getClass() != T.class) return false;
        T containNode = (T) o;
        T now = head;
        while (now != null) {
            if (now.equals(containNode)) {
                return true;
            }
            now = now.after;
        }
        return false;
    }
    @Override
    public Iterator<T> iterator() {
        Iterator<T> itr = new Iterator<T>() {

            private T curr = head, now;

            @Override
            public boolean hasNext() {
                return curr != null;
            }

            @Override
            public T next() {
                now = curr;
                curr = curr.after;
                return now;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
        return itr;
    }

    public Object[] toArray() {
        return new Object[0];
    }

    public <T1> T1[] toArray(T1[] a) {
        return null;
    }

    public boolean add(T t) {
        return false;
    }

    public boolean remove(Object o) {
        return false;
    }

    public boolean containsAll(Collection<?> c) {
        return false;
    }

    public boolean addAll(Collection<? extends T> c) {
        return false;
    }

    public boolean removeAll(Collection<?> c) {
        return false;
    }

    public boolean retainAll(Collection<?> c) {
        return false;
    }

    public void clear() {

    }
}
