import { User } from './user.model';
import { Ticket } from './ticket.model';
export class ChangeStatus{
    constructor(
        public id: string, 
        public ticket: Ticket,
        public user: User,
        public dateChangeStatus: Date,
        public status: string
    ){}   
}