import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
public class Node implements Runnable {

    public Mailbox<Message<String>> mailbox = new Mailbox<>();
    private List<Mailbox<Message<String>>> neighbors = new ArrayList<>();
    private List<Integer> neighborIds = new ArrayList<>();
    private Map<Long, Long> receivedMessages = new ConcurrentHashMap<>(); // Map to store received messages and their timestamps for cleanup
    private int id;

    public Node(int id, Mailbox<Message<String>> mailbox) {
        this.id = id;
        this.mailbox = mailbox;
    }

    public int getId() {
        return id;
    }

    public void addNeighbor(Mailbox<Message<String>> neighborMailbox, int neighborId) {
        neighbors.add(neighborMailbox);
        neighborIds.add(neighborId);
    }

    public void run () {
        while (true) {
            try {
                Message<String> message = mailbox.receiveMessage();
                if (!receivedMessages.containsKey(message.getTimestamp())) {
                    System.out.println("Node " + id + " received message: " + message.getContent());
                    receivedMessages.put(message.getTimestamp(), System.currentTimeMillis()); // Storing the time so we can remove outdated messsages
                    for (Mailbox<Message<String>> neighbor : neighbors) {
                        neighbor.sendMessage(message);
                    }
                }
                
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
