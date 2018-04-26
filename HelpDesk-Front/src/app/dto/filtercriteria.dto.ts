export class FilterCriteria {

    constructor(
        public example: any,
        public pageSize: number,
        public pageNumber: number,
        public sort: string,
        public sortElement: string
    ){}

}
