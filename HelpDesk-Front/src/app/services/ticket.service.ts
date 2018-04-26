import { HELP_DESK_API } from './helpdesk.api';
import { HttpClient } from '@angular/common/http';
import { Ticket } from './../model/ticket.model';
import { GenericService } from './generic.service';
import { Injectable } from '@angular/core';

@Injectable()
export class TicketService extends GenericService<Ticket> {

  constructor(public http: HttpClient) {
    super(http, 'api/ticket');
  }

  changeStatus(ticket: Ticket) {
    return this.http.put(`${HELP_DESK_API}/${this.path}/changeStatus`, ticket);
  }

  summary() {
    return this.http.get(`${HELP_DESK_API}/${this.path}/summary`);
  }
}
