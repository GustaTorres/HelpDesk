import { ResponseApi } from './../../model/response-api';
import { ActivatedRoute } from '@angular/router';
import { Ticket } from './../../model/ticket.model';
import { Component, OnInit } from '@angular/core';
import { SharedService } from '../../services/shared.service';
import { TicketService } from '../../services/ticket.service';

@Component({
  selector: 'app-ticket-detail',
  templateUrl: './ticket-detail.component.html',
  styleUrls: ['./ticket-detail.component.css']
})
export class TicketDetailComponent implements OnInit {

  ticket = new Ticket(null, null, null, null, null, null, null, null, null, null, null);
  shared: SharedService;
  message: {};
  classCss: {};

  constructor(
    private ticketService: TicketService,
    private route: ActivatedRoute
  ) { 
    this.shared = SharedService.getInstance();
  }

  ngOnInit() {
    let id: string = this.route.snapshot.params['id'];
    if(id != undefined){
      this.findByid(id);
    }
  }

  findByid(id: string) {
    this.ticketService.findById(id).subscribe((responseApi: ResponseApi) => { 
      this.ticket = responseApi.data;
    },err => {
      this.showMessage({
        type: 'error',
        text: err['error']['errors'][0]
      });
    });
  }

  private showMessage(message: { type: string, text: string }) {
    this.message = message;
    this.buildClasses(message.type);
    setTimeout(() => {
      this.message = undefined;
    }, 3000);
  }

  private buildClasses(type: string) {
    this.classCss = {
      'alert': true
    }
    this.classCss['alert-' + type] = true;
  }

  changeStatus(status: string) {
    this.ticket.status = status;
    this.ticketService.changeStatus(this.ticket).subscribe((responseApi: ResponseApi) => {
      this.ticket = responseApi.data;
      this.showMessage({
        type: 'success',
        text: 'Successfully changed status'
      });
    }, err => {
      this.showMessage({
        type: 'error',
        text: err['error']['errors'][0]
      });
    }); 
  }

}
