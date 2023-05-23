import org.junit.platform.engine.support.hierarchical.Node;

import java.util.ArrayList;
import java.util.List;

public class LinkedListDeque<T> implements Deque<T> {
    private int size;

    private Node<T> first;

    private Node<T> last;

    public LinkedListDeque() {
    }

    @Override
    public void addFirst(T x) {
        final Node<T> f = first;
        final Node<T> newNode = new Node<>(x, null, f);
        first = newNode;
        if (f == null)
            last = newNode;
        else
            f.prev = newNode;
        size++;
    }

    @Override
    public void addLast(T x) {
        final Node<T> l = last;
        final Node<T> newNode = new Node<>(x, l, null);
        last = newNode;
        if (l == null)
            first = newNode;
        else
            l.next = newNode;
        size++;
    }

    @Override
    public List<T> toList() {
        List<T> returnList = new ArrayList<>();
        Node<T> node = first;
        for (int i = 0; i < size; node = node.next, i++) {
            returnList.add(node.element);
        }
        return returnList;
    }

    @Override
    public boolean isEmpty() {
        return size==0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public T removeFirst() {
        final Node<T> f = first;
        first = f.next;
        if (first == null)
            last = null;
        size--;
        return f.element;
    }

    @Override
    public T removeLast() {
        final Node<T> l = last;
        last = l.prev;
        if (last == null)
            first = null;
        size--;
        return l.element;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index > size - 1)
            return null;
        if (index < (size >> 1)) {
            Node<T> node = first;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
            return node.element;
        } else {
            Node<T> node = last;
            for (int i = size - 1; i > index; i--) {
                node = node.prev;
            }
            return node.element;
        }
    }

    @Override
    public T getRecursive(int index) {
        if (index > size) {
            return null;
        }
        Node<T> node = first;
        return getRecursiveInternal(node, index);
    }

    private T getRecursiveInternal(Node<T> node, int index) {
        if (index == 0) {
            return node.element;
        } else {
            return getRecursiveInternal(node.next, index - 1);
        }
    }

    public static class Node<T> {
        public T element;
        public Node<T> prev;
        public Node<T> next;

        public Node(T element, Node<T> prev, Node<T> next) {
            this.element = element;
            this.prev = prev;
            this.next = next;
        }
    }

}
