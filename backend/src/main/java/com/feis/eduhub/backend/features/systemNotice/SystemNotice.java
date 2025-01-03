package com.feis.eduhub.backend.features.systemNotice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SystemNotice {
    @NonNull
    private Long noticeId;
    @NonNull
    private String noticeMessage;
    @NonNull
    private Long recipientId;
    private Long senderId;

    public SystemNotice(@NonNull String noticeMessage, @NonNull Long recipientId, Long senderId) {
        this.noticeMessage = noticeMessage;
        this.recipientId = recipientId;
        this.senderId = senderId;
    }
}
