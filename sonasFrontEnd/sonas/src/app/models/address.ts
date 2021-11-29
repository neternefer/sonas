export class Address {
 
    public get street(): string {
        return this.street;
    }
    public set street(value: string) {
        this.street = value;
    }

    public get streetNumber(): string {
        return this.streetNumber;
    }
    public set streetNumber(value: string) {
        this.streetNumber = value;
    }

    public get city(): string {
        return this.city;
    }
    public set city(value: string) {
        this.city = value;
    }

    public get zip(): string {
        return this.zip;
    }
    public set zip(value: string) {
        this.zip = value;
    }
  
    public get country(): string {
        return this.country;
    }
    public set country(value: string) {
        this.country = value;
    }
 
    public get contactId(): number {
        return this.contactId;
    }
    public set contactId(value: number) {
        this.contactId = value;
    }
}