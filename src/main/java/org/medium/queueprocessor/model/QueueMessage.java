package org.medium.queueprocessor.model;

import java.util.UUID;

public record QueueMessage(UUID id, String message, boolean isProcessed) {
    public QueueMessage copy(boolean isProcessed) {
        return new QueueMessage(this.id, this.message, isProcessed);
    }
}
