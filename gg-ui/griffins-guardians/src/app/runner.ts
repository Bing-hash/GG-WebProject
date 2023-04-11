import { Donor } from "./donor";

export interface Runner {
    id: number;
    name: String;
    donors: Array<Donor>;
    goal: number;
    total: number;
    
}