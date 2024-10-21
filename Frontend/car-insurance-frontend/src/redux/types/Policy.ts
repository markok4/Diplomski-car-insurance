import { Proposal } from "./Proposal";

export type Policy = {
    id: number
    dateSigned: string;
    expiringDate: string;
    moneyReceivedDate?: string;
    amount: number;
    isDeleted: boolean;
    proposal: Proposal;
}