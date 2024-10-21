package com.synechron.policycreationservice.utils;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
public class SearchData {
    private LocalDate date = null;
    private List<Long> subscriberIds = null;
    private List<Long> carIds = null;
    private boolean hasCarServiceResponded;
    private boolean hasUserServiceResponded;

    public boolean isComplete() {
        return hasCarServiceResponded && hasUserServiceResponded;
    }
}
