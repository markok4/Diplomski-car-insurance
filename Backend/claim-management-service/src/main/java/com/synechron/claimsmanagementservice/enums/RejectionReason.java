package com.synechron.claimsmanagementservice.enums;

public enum RejectionReason {
    POLICY_NOT_COVERED, //the claim is not covered by the policy
    INCORRECT_DOCUMENTATION, //the documentation provided is incorrect or insufficient
    POLICY_EXPIRED, //the claim came after the policy expiration date
    INVALID_POLICY, //the policy itself is invalid, incorrect information or the lack of payment
    DAMAGE_NOT_ELIGIBLE, //the damage for which the claim was made does not fall under the coverages
    LATE_SUBMISSION, //the claim was submitted late, after the term established by the policy roles
    INCOMPLETE_ASSESSMENT, //the damage assessment provided with the claim was incomplete or not detailed enough
    UNSUBSTANTIATED_CLAIM //the claim wasn't substantiated with the supporting documents or evidence provided
}
