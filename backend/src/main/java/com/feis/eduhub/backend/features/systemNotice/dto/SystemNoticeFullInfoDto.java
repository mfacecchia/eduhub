package com.feis.eduhub.backend.features.systemNotice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class SystemNoticeFullInfoDto {
    private Long noticeId;
    private String noticeMessage;
    private Long recipientId;
    private String recipientFirstName;
    private String recipientLastName;
    private String recipientEmail;
    private Long senderId;
    private String senderFirstName;
    private String senderLastName;
}
