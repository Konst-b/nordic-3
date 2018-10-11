package second_hometask;

public class Node<T> {
    T content;
    Node<T> before;
    Node<T> after;

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
        Node<T> node = (Node<T>) o;
        return this.content.equals(node.content);
    }
}

