package org.medium.queueprocessor.model;

public record QueueMessage(String message, boolean isProcessed) {
    public QueueMessage copy(boolean isProcessed) {
        return new QueueMessage(this.message, isProcessed);
    }
}
