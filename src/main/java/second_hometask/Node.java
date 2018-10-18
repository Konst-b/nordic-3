package second_hometask;

public class Node<T> implements Comparable <T> {
    public T content;
    Node <T> before;
    Node <T> after;

    public Node(T content) {
        this.content = content;
        this.before = null;
        this.after = null;
    }

    @Override
    public String toString() {
        return this.content.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Node)) return false;
        Node <T> node = (Node <T>) o;
        return this.content.equals(node.content);
    }

    public int compareTo(Object o) {
        if (o.getClass().toString().indexOf("Integer") != -1)
            return (Integer) this.content < (Integer) o ? -1 : (Integer) this.content > (Integer) o ? +1 : 0;
        else if (o.getClass().toString().indexOf("Double") != -1)
            return (Double) this.content < (Double) o ? -1 : (Double) this.content > (Double) o ? +1 : 0;
        else
            return this.content.toString().compareTo(o.toString());
    }
}

