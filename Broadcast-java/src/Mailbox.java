import java.util.concurrent.LinkedBlockingQueue;

// Simple implementation of an asynchronous mailbox for message passing between nodes in a simulated distributed system

public class Mailbox<T> {
    private LinkedBlockingQueue<T> messages;

    public Mailbox() {
        this.messages = new LinkedBlockingQueue<>();
    }

    public void sendMessage(T message) {
        messages.offer(message);
    }

    public T receiveMessage() throws InterruptedException {
        return messages.take();
    }
}
