import { ResponseApi } from './../../model/response-api';
import { FilterCriteria } from './../../model/filtercriteria.dto';
import { DialogService } from './../../services/dialog.service';
import { Router } from '@angular/router';
import { UserService } from './../../services/user.service';
import { SharedService } from './../../services/shared.service';
import { User } from './../../model/user.model';
import { Component, OnInit } from '@angular/core';
import { CrudGeneric } from '../crud-generic';

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.css']
})
export class UserListComponent extends CrudGeneric<User> implements OnInit {

  user: User
  shared: SharedService;

  constructor(
    public dialogService: DialogService,
    public userService: UserService,
    private router: Router
  ) {
    super(userService, dialogService);
    this.shared = SharedService.getInstance();
  }

  ngOnInit() {
    this.user = new User(null, null, null, null);
    this.filter.example = this.user;
    this.filter.sortElement = 'email';
    this.find();
  }

  edit(id: string) {
    this.router.navigate(['/user-new', id]);
  }
}
