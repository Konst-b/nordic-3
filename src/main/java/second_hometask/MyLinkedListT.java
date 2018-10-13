package second_hometask;

import java.util.Collection;
import java.util.Iterator;

public class MyLinkedListT<T> implements Collection<T>, Iterable<T> {
    Node<T> head;
    Node<T> last;
    int size;

    public MyLinkedListT() {
        this.head = null;
        this.last = null;
        this.size = 0;
    }

    public MyLinkedListT(Node<T> head) {
        this.head = head;
        this.last = head;
        this.size = 1;
    }

    public static void main(String[] args) {
        MyLinkedListT list = new MyLinkedListT<String>();
        for (int i = 546; i < 578; i++) {
            list.add(i);                                            //проверка add(T t)
        }
        System.out.println("Заполненный список");
        list.print();

        Object[] slist = list.toArray();                            //проверка toArray()
        System.out.println("Массив toArray()");
        for (Object o : slist)
            System.out.print(o.toString() + " ");

        System.out.println();

        list.remove(slist[6]);                                      //проверка remove()
        list.remove(slist[0]);
        System.out.println("После удаления элементов 0 и 6");
        list.print();

        MyLinkedListT c = new MyLinkedListT();
        c.add("574");
        c.add("Прощай немытая Россия...");
        c.add(899);
        c.add(549);
        c.add("Hello world!");
        System.out.println("Новый список");
        c.print();
        String[] aS = new String[c.size()];

        aS = (String[]) c.toArray(aS);                              //проверка toArray(A[] a)
        System.out.print("toArray<String>:");
        for (String s : aS)
            if (s != null) System.out.print(s + " ");
        System.out.println();

        Integer[] aI = new Integer[c.size()];
        aI = (Integer[]) c.toArray(aI);
        System.out.print("toArray<Integer>:");
        for (Integer i : aI)
            if (i != null) System.out.print(i + " ");
        System.out.println();

        System.out.println("containsAll:" + list.containsAll(c));   //проверка containsAll()

        System.out.println("Сохранить все");
        System.out.println("retainALL:" + list.retainAll(list));    //проверка retainAll()
        list.print();

        System.out.println("Объединить списки");
        System.out.println("addALL:" + list.addAll(c));             // проверка addAll()
        list.print();

        System.out.println("Сохранить пересечение списков");
        System.out.println("retainALL:" + list.retainAll(c));
        list.print();

        System.out.println("Удалить из списка элементы коллекции");
        System.out.println("removeALL:" + list.removeAll(c));       // проверка removeAll()
        list.print();

        c.clear();                                                  //проверка clear()
        System.out.println("После clear");
        c.print();
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean contains(Object o) {
        if (o.getClass() != Node.class) return false;
        T containNode = (T) o;
        Node<T> now = head;
        while (now != null) {
            if (now.equals(containNode)) {
                return true;
            }
            now = now.after;
        }
        return false;
    }

    public Iterator<T> iterator() {
        Iterator<T> itr = new Iterator<T>() {

            private Node<T> curr = head;
            private T now;

            @Override
            public boolean hasNext() {
                return curr != null;
            }

            @Override
            public T next() {
                now = (T) curr;
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

    @Override
    public Object[] toArray() {
        Object[] array = new Node[this.size];
        Node<T> now = this.head;
        for (int i = 0; i < this.size; i++) {
            array[i] = now;
            now = now.after;
        }
        return array;
    }

    public <A> A[] toArray(A[] a) {
        Node<T> now = this.head;
        int i = 0;
        while (now != null) {
            try {
                if (i < a.length) a[i] = (A) now.content;
                else break;

            } catch ( Exception e ) {} //ошибка приведения типов -- скинул как допустимую
            now = now.after;
            i++;
        }
        return a;
    }

    public boolean add(T t) {
        return add(new Node<T>(t));
    }

    public boolean add(Node<T> t) {
        if (this.isEmpty()) {
            //t.before=null;  - эти присвоения были в конструкторе Node(name)
            //t.after=null;
            this.head = t;
        } else {
            t.before = this.last;
            t.after = null;
            this.last.after = t;
        }
        this.last = t;
        ++this.size;
        return true;
    }

    public boolean remove(Object o) {
        if (o.getClass() != Node.class) return false;
        Node<T> containNode = (Node<T>) o;
        Node<T> now = head;
        while (now != null) {
            if (now.equals(containNode)) {
                if (now == head) head = head.after;
                if (now == last) last = last.before;

                if (now.after != null) now.after.before = now.before;
                if (now.before != null) now.before.after = now.after;

                now.content = null;
                now.after = null;
                now.before = null;
                --size;
                return true;
            }
            now = now.after;
        }
        return false;
    }

    public boolean containsAll(Collection<?> c) {
        if (c.getClass() != this.getClass()) return false;
        if (c.size() > this.size()) return false;

        Object[] slist = c.toArray();
        for (Object o : slist) {
            if (!this.contains(o))
                return false;
        }
        return true;
    }

    public boolean addAll(Collection<? extends T> c) {
        boolean result = true;
        Iterator<? extends T> itr = c.iterator();
        T now;
        while (itr.hasNext()) {
            now = itr.next();
            result = this.add(now) || result;
        }
        return result;
    }

    public boolean removeAll(Collection<?> c) {
        boolean result = false;
        Iterator<?> itr = c.iterator();
        Node<T> now;
        while (itr.hasNext()) {
            now = (Node<T>) itr.next();
            result = this.remove(now) || result; // если хоть один удалил значит result=true
        }
        return result;
    }

    public boolean retainAll(Collection<?> c) {
        boolean result = false;
        Node<T> del, nowC;

        boolean retain;
        for (T now : this) {
            Iterator<?> itr = c.iterator();
            retain = false;
            while (itr.hasNext()) {
                nowC = (Node<T>) itr.next();
                if (now.equals(nowC)) {
                    retain = true;
                    break;
                }
            }
            if (!retain) {
                del = (Node<T>) now;
                result = this.remove(del) || result; // если хоть один удалил значит result=true
            }
        }
        return result;
    }

    public void clear() {
        head = null;
        last = null;
        size = 0;
    }

    public void print() {
        if (isEmpty()) System.out.print("Список пуст!");
        else   // заодно здесь проверка итератора
            for (T now : this) System.out.print(((Node<T>) now).toString() + " ");
        System.out.println("/всего:" + this.size());
    }
}
