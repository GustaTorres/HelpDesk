export class Summary {
    constructor(
        public amountNew: number,
        public amountResolved: number,
        public amountApproved: number,
        public amountDispproved: number,
        public amountAssigned: number,
        public amountClosed: number
    ) { }
}