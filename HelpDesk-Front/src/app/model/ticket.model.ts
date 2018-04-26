import { ChangeStatus } from './change-status.model';
import { User } from './user.model';
export class Ticket {
    constructor(
        public id: string,
        public user: User,
        public date: Date,
        public title: string,
        public number: number,
        public status: string,
        public priority: string,
        public assignedUser: User,
        public description: string,
        public image: string,
        public changes: Array<ChangeStatus>
    ){}
}

