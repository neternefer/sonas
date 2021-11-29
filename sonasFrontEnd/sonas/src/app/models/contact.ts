import { Social } from "./social";
import { Address } from "./address";
export class Contact {
  
    public get phone(): string {
        return this.phone;
    }
    public set phone(value: string) {
        this.phone = value;
    }

    public get social(): Social[] {
        return this.social;
    }
    public set social(value: Social[]) {
        this.social = value;
    }

    public get address(): Address[] {
        return this.address;
    }
    public set address(value: Address[]) {
        this.address = value;
    }

}