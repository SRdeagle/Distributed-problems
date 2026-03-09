public class Message<T> {
    private T content;
    private long timestamp; // Timestamp to uniquely identify messages and for cleanup of old messages

    public Message(T content) {
        this.content = content;
        this.timestamp = System.currentTimeMillis();
    }

    public T getContent() {
        return content;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
