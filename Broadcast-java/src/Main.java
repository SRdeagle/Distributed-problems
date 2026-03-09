
// This is a demonstration of a simple broadcast algorithm (flooding), in a
// simulated distributed system using mailboxes
public class Main {
    public static void main(String[] args) {
        
        Node node1 = new Node(1, new Mailbox<>());
        Node node2 = new Node(2, new Mailbox<>());
        Node node3 = new Node(3, new Mailbox<>());
        Node node4 = new Node(4, new Mailbox<>());
        Node node5 = new Node(5, new Mailbox<>());

        // Graph could also be connected as a directed graph, would still work
        node1.addNeighbor(node2.mailbox, 2);
        node1.addNeighbor(node3.mailbox, 3);
        node2.addNeighbor(node1.mailbox, 1);
        node2.addNeighbor(node4.mailbox, 4);
        node3.addNeighbor(node1.mailbox, 1);
        node3.addNeighbor(node5.mailbox, 5);
        node4.addNeighbor(node2.mailbox, 2);
        node4.addNeighbor(node5.mailbox, 5);
        node5.addNeighbor(node3.mailbox, 3);
        node5.addNeighbor(node4.mailbox, 4);
        node1.addNeighbor(node5.mailbox, 5);
        node5.addNeighbor(node1.mailbox, 1);



        
        Thread thread1 = new Thread(node1);
        Thread thread2 = new Thread(node2);
        Thread thread3 = new Thread(node3);
        Thread thread4 = new Thread(node4);
        Thread thread5 = new Thread(node5);
        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
        thread5.start();

        
        Initiator initiator = new Initiator(node1);
        while (true) {
            initiator.initiate();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
