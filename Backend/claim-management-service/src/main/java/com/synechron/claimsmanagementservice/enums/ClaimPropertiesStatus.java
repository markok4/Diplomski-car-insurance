package com.synechron.claimsmanagementservice.enums;

public enum ClaimPropertiesStatus {
    CLAIM_SUBMITTED, //claim is submitted, not reviewed yet
    CLAIM_UNDER_REVIEW, //claim is under review by the claim handler
    CLAIM_REJECTED, //claim is reviewed and rejected due to policy coverage issues
    CLAIM_SENT_FOR_ASSESSMENT, //the claim handler has sent the claim for damage assessment
    ASSESSMENT_UNDERWAY, //claim is being assessed by the claim adjuster
    ASSESSMENT_COMPLETE, //claim adjuster has completed the damage assessment
    CLAIM_APPROVED, //claim is approved by the claim handler after reviewing the assessment
    CLAIM_DENIED, //claim is denied by the claim handler after reviewing the assessment
    COMPLAINT_SUBMITTED, //status when the subscriber has submitted a complaint about a rejected claim
    COMPLAINT_UNDER_REVIEW, //status when the claim handler is reviewing the complaint
    COMPLAINT_ACCEPTED, //status when the complaint has been accepted
    COMPLAINT_REJECTED, //status when the complaint has been rejected
    CLAIM_CLOSED, //status when the claim (and complaint process) is concluded and the claim is closed
    CLAIM_PAID //final status of the claim, only happens if the claim is approved by the claim handler and the money is transferred to the subscribers account
}
