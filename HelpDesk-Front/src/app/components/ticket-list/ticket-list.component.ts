import { Router } from '@angular/router';
import { DialogService } from './../../services/dialog.service';
import { TicketService } from './../../services/ticket.service';
import { TicketNewComponent } from './../ticket-new/ticket-new.component';
import { Ticket } from './../../model/ticket.model';
import { CrudGeneric } from './../crud-generic';
import { Component, OnInit } from '@angular/core';
import { SharedService } from '../../services/shared.service';

@Component({
  selector: 'app-ticket-list',
  templateUrl: './ticket-list.component.html',
  styleUrls: ['./ticket-list.component.css']
})
export class TicketListComponent extends CrudGeneric<Ticket> implements OnInit {

  ticket: Ticket;
  shared: SharedService

  constructor(
    public ticketService: TicketService,
    public dialogService: DialogService,
    private router: Router
  ) { 
    super(ticketService,dialogService); 
    this.shared = SharedService.getInstance();
  }

  ngOnInit() {
    this.ticket = new Ticket(null,null,null,null,null,null,null,null,null,null,null);
    this.filter.example = this.ticket;
    this.filter.sortElement = 'number';
    super.find();
  }

  findFilter(){
    this.filter.pageNumber = 0;
    this.find();
  }

  clearFilter(){
    this.ticket = new Ticket(null,null,null,null,null,null,null,null,null,null,null);
    this.findFilter();
  }

  edit(id: string) {
    this.router.navigate(['/ticket-new', id]);
  }

}
