package second_hometask;

import java.util.Collection;
import java.util.Iterator;

public class MyLinkedList implements Collection<Node>{
Node head;
Node last;
int size;

    public MyLinkedList() {
      this.head=null;
      this.last=null;
      this.size=0;
    }

    public MyLinkedList(Node head) {
        this.head = head;
        this.last = head;
        this.size = 1;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size==0;
    }

    public boolean contains(Object o) {
        if (o.getClass()!= Node.class) return false;
        Node containNode =(Node)o;
        Node now=head;
        while (now!=null){
            if (now.equals(containNode)){
                return true;
            }
            now=now.after;
        }
        return false;
    }
    @Override
    public Iterator<Node> iterator() {
        Iterator<Node> itr = new Iterator<Node>() {

            private Node curr = head, now;

            @Override
            public boolean hasNext() {
                return curr != null;
            }

            @Override
            public Node next() {
                now=curr;
                curr=curr.after;
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
        Object[] array = new Node[this.size];
        Node now=this.head;
        for (int i=0; i<this.size;i++){
            array[i]=now;
            now=now.after;
        }
        return array;
    }

   public <T> T[] toArray(T[] a) { //выгружает список в массив a[] - столько сколько влезет
       Node now=this.head;
       int i=0;
       while (now!=null){
           if (i<a.length) a[i] = (T)now;
           else break;
           now=now.after;
           i++;
       }
       return a;
   }

    public boolean add(Node node) {
        if (this.isEmpty()) {
            node.before=null;
            node.after=null;
            this.head=node;
        }
        else {
            node.before = this.last;
            node.after = null;
            this.last.after = node;
        }
        this.last=node;
        this.size++;
        return true;
    }

    public boolean remove(Object o) {
        if (o.getClass()!= Node.class) return false;
        Node containNode =(Node)o;
        Node now=head;
        while (now!=null){
            if (now.equals(containNode)){
                if (now==head) head=head.after;
                if (now==last) last=last.before;

                if (now.after!=null) now.after.before=now.before;
                if (now.before!=null) now.before.after=now.after;

                now.name=null;
                now.after=null;
                now.before=null;
                size--;
                return true;
            }
            now=now.after;
        }
        return false;
    }

    public boolean containsAll(Collection<?> c) {
        if (c.getClass()!=this.getClass()) return false;
        if (c.size()>this.size()) return false;

        Object[] slist =c.toArray();
        for(int i=0; i<slist.length; i++){
          if (!this.contains(slist[i]))
              return false;
        }
        return true;
    }

    public boolean addAll(Collection<? extends Node> c) {
        boolean result=true;
        Iterator<? extends Node> itr = c.iterator();
        Node now;
        while (itr.hasNext()){
           now=itr.next();
           result = result||this.add(now);
        }
        return result;
    }

    public boolean removeAll(Collection<?> c) {
        boolean result=false;
        Iterator<?> itr = c.iterator();
        Node now;
        while (itr.hasNext()){
            now=(Node)itr.next();
            result = this.remove(now)||result; // если хоть один удалил значит result=true
        }
        return result;
    }

    public boolean retainAll(Collection<?> c) {
        boolean result=false;
        Node del, nowC;
        Node next;
        boolean retain;
        for (Node now: this){
            Iterator<?> itr = c.iterator();
            retain=false;
            while (itr.hasNext()) {
                nowC = (Node) itr.next();
                if (now.equals(nowC)) {
                    retain=true;
                    break;
                }
            }
            if (!retain) {
                del = now;
                result = this.remove(del)||result; // если хоть один удалил значит result=true
            }
            now = now.after;
        }
        return result;
    }

    public void clear() {
        Node now=head;
        Node next;
        while (now!=null) {
            next = now.after;
            now.name = null;
            now.after = null;
            now.before = null;
            now = next;
        }
        head=null;
        last=null;
        size=0;
    }

    public void print(){
        if (isEmpty()) System.out.print("Список пуст!");
        else   // заодно здесь проверка итератора
          for (Node now: this) System.out.print(now.getName()+" ");
        System.out.println();
    }

    public static void main(String[] args){
        MyLinkedList list=new MyLinkedList();
        for (int i=0;i<10;i++){
            list.add(new Node("элемент:"+Integer.toString(i)));
        }
        System.out.println("Заполненный список");
        list.print();

        Object[] slist = list.toArray();
        System.out.println("Массив toArray()");
        for(int i=0; i<slist.length; i++) {
            System.out.print(((Node)(slist[i])).getName()+" ");
        }
        System.out.println();

        list.remove(slist[6]);
        list.remove(slist[0]);
        System.out.println("После удаления элементов 0 и 6");
        list.print();

        Collection c=new MyLinkedList();
        c.add(new Node("FF"));
        c.add(new Node("DD"));

        System.out.println("новый список");
        ((MyLinkedList) c).print();
        System.out.println("containsAll:"+list.containsAll(c));

        System.out.println("Сохранить все");
        System.out.println("retainALL:"+list.retainAll(list));
        list.print();

        System.out.println("Сохранить несуществующие");
        System.out.println("retainALL:"+list.retainAll(c));
        list.print();
    }
}
