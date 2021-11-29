export class Social {

    public get linkType(): string {
        return this.linkType;
    }
    public set linkType(value: string) {
        this.linkType = value;
    }

    public get link(): URL {
        return this.link;
    }
    public set link(value: URL) {
        this.link = value;
    }

    public get contactId(): number {
        return this.contactId;
    }
    public set contactId(value: number) {
        this.contactId = value;
    }
}