import { ResponseApi } from './../../model/response-api';
import { FilterCriteria } from './../../model/filtercriteria.dto';
import { DialogService } from './../../services/dialog.service';
import { Router } from '@angular/router';
import { UserService } from './../../services/user.service';
import { SharedService } from './../../services/shared.service';
import { User } from './../../model/user.model';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.css']
})
export class UserListComponent implements OnInit {

  user: User
  filter: FilterCriteria;
  shared: SharedService;
  message = {};
  classCss = {};
  listUser = [];
  pages: number;


  constructor(
    private dialogService: DialogService,
    private userService: UserService,
    private router: Router
  ) {
    this.shared = SharedService.getInstance();
  }

  ngOnInit() {
    this.user = new User(null, null, null, null);
    this.filter = new FilterCriteria(this.user, 5, 0, 'ASC', null);
    this.find();
  }

  find() {
    this.userService.findAll(this.filter).subscribe((reponseApi: ResponseApi) => {
      this.listUser = reponseApi['data']['content'];
      this.pages = reponseApi['data']['totalPages'];
    }, err => {
      this.showMessage({
        type: 'error',
        text: err['error']['error']
      });
    });
  }

  edit(id: string) {
    this.router.navigate(['/user-new', id]);
  }

  delete(id: string) {
    this.dialogService.confirm('Do you want to delete the user ?').then((canDelete: boolean) => {
      if (canDelete) {
        this.message = {};
        this.userService.delete(id).subscribe((responseApi: ResponseApi) => {
          this.showMessage({
            type: 'success',
            text: 'Record deleted'
          });
          this.find();
        }, err => {
          this.showMessage({
            type: 'error',
            text: err['error']['error']
          })
        });
      }
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

  setNextPage(){
    
  }

}
