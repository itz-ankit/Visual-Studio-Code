package LinkedList;

public class utils {

    public <T> void addAll(Linkedlist<T> ll, T[] arr) {// input for linked list.
        for (T item : arr) {
            ll.add(item);
        }
    }

    public <T> void reverse(Linkedlist<T> ll) {// reversing linked list
        if (ll.size() == 0 || ll.size() == 1)
            return;
        Linkedlist<T>.Node current = ll.head;
        Linkedlist<T>.Node nextNode;
        while (current != null) {
            nextNode = current.next;
            current.next = current.prev;
            current.prev = nextNode;
            current = nextNode;
        }
        // swapping tail and head.
        current = ll.head;
        ll.head = ll.tail;
        ll.tail = current;
    }

    public <T> boolean palindrome(Linkedlist<T> ll){//checking if linked list is palindrome 
        Linkedlist<T>.Node headNode=ll.head;
        Linkedlist<T>.Node tailNode=ll.tail;
        while(headNode!=tailNode && tailNode.next!=headNode){//checking for each half of the list
            if(!(headNode.equals(tailNode)))
                return false;
        }
        return true;
    }

    public static void main(String[] args) {
        Linkedlist<Integer> ll = new Linkedlist<>();
        Integer[] arr = { 1, 2, 3, 5, 7 };
        utils ut = new utils();
        ut.addAll(ll, arr);
        System.out.println(ll);
        ut.reverse(ll);
        System.out.println(ll);
    }
}