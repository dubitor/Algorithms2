// Linked list representation of extended ascii chars to help MoveToFront.java
public class ASCIILinkedList {
    private Node first;

    public ASCIILinkedList() {
        Node next = new Node((char) 255, null);
        for (int i = 254; i >= 0; i--) {
            Node current = new Node((char) i, next);
            next = current;
        }
        first = next;
    }

    // return index of c and move c to front of list
    public char moveToFront(char c) {
        if (first.c == c) {
            return 0;
        }

        Node previous = first;
        Node current = previous.next;
        char i = 1;
        while (current.c != c) {
            previous = current;
            current = current.next;
            i++;
        }

        previous.next = current.next;
        current.next = first;
        first = current;

        return i;
    }

    // return char at index j and move char to front
    public char moveToFront(int j) {
        if (j == 0) {
            return first.c;
        }

        Node previous = first;
        Node current = previous.next;
        int i = 1;
        while (i < j) {
            previous = current;
            current = current.next;
            i++;
        }

        previous.next = current.next;
        current.next = first;
        first = current;

        return first.c;
    }

        

    // for debugging
    public Node first() {
        return this.first;
    }

    private class Node {
        private final char c;
        private Node next;

        public Node(char c, Node next) {
            this.c = c;
            this.next = next;
        }
    }

    // unit test
    public static void main(String[] args) {
        CharSequence seq = "ASCII";
        ASCIILinkedList ll = new ASCIILinkedList();

        System.out.println("printing current indices of 'ASCII'");
        for (int i = 0; i < seq.length(); i++) {
            char c = seq.charAt(i);
            System.out.print((int) ll.moveToFront(c) + " ");
        }
        System.out.println();
        System.out.println("first characters of list after moving 'ASCII' to front: ");
        Node node = ll.first();
        for (int i = 0; i < 10; i++) {
            System.out.print(node.c + " ");
            node = node.next;
        }
        System.out.println();
    }
}
