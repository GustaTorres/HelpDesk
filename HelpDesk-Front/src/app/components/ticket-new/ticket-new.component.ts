import { Observable } from 'rxjs/Observable';
import { ResponseApi } from './../../model/response-api';
import { ActivatedRoute } from '@angular/router';
import { TicketService } from './../../services/ticket.service';
import { Component, OnInit, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Ticket } from '../../model/ticket.model';
import { SharedService } from '../../services/shared.service';

@Component({
  selector: 'app-ticket-new',
  templateUrl: './ticket-new.component.html',
  styleUrls: ['./ticket-new.component.css']
})
export class TicketNewComponent implements OnInit {

  @ViewChild("form")
  form: NgForm

  ticket = new Ticket(null,null,null,'',null,'NEW',null,null,'','',null);
  shared = SharedService;
  message: {};
  classCss: {};

  constructor(
    private ticketService :TicketService,
    private route: ActivatedRoute
  ) { }

  ngOnInit() {
    let id: string = this.route.snapshot.params['id'];
    if (id != undefined) {
      this.findById(id);
    }
  }

  findById(id: string) {
    this.ticketService.findById(id).subscribe((responseApi: ResponseApi) => {
      this.ticket = responseApi.data;
    }, err => {
      this.showMessage({
        type: 'error',
        text: err['error']['errors'][0]
      });
    });
  }

  register() {
    this.message = {};
    var observable: Observable<Object> = null;

    if(this.ticket.id == null){
      observable = this.ticketService.save(this.ticket);
    }else{
      observable = this.ticketService.update(this.ticket);
    }

    observable.subscribe((responseApi: ResponseApi) => {
      this.ticket = new Ticket(null,null,null,'',null,'NEW',null,null,'','',null);
      let ticketRet: Ticket = responseApi.data;
      this.form.resetForm();
      this.showMessage({
        type: 'success',
        text: `Ticket ${ticketRet.number} registered successfully`
      });
    }, err => {
      this.showMessage({
        type: 'error',
        text: err['error']['message']
      });
    });
  }

  onfileChange(event: any){
    if(event.target.files[0].size > 2000000){
      this.showMessage({
        type: 'error',
        text: 'Maximum image size is 2MB'
      })
    } else {
      this.ticket.image = null;
      var reader = new FileReader();
      reader.onloadend = (e: Event) => {
        this.ticket.image = reader.result;
      }
      reader.readAsDataURL(event.target.files[0]);
    }
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

  getFromGroupClass(isInvalid: boolean, isDirty: boolean) {
    return {
      'form-group': true,
      'has-error': isInvalid && isDirty,
      'has-success': !isInvalid && isDirty
    }
  }
}
