export type InsurancePlan = {
    id: number;
    name: string;
    isPremium?: boolean; // Assuming Boolean can be null in BE, making it optional in FE
    isDeleted?: boolean; // Assuming Boolean can be null in BE, making it optional in FE
    insuranceItemIds: number[]; // IDs of the insurance items
  }