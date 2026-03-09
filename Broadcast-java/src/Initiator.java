public class Initiator {
    private Node node;

    public Initiator(Node node) {
        this.node = node;
    }

    public void initiate() {
        Message<String> message = new Message<>("Hello, World!");
        System.out.println("Initiator sending message: " + message);
        node.mailbox.sendMessage(message);
    }
    
}
