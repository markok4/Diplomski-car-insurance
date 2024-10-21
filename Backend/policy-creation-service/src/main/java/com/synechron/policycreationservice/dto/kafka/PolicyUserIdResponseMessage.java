package com.synechron.policycreationservice.dto.kafka;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PolicyUserIdResponseMessage {
    public String correlationId;
    public Long userId;
    public int page;
    public int size;

}
