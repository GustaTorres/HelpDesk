import { DialogService } from './../../services/dialog.service';
import { Router } from '@angular/router';
import { UserService } from './../../services/user.service';
import { SharedService } from './../../services/shared.service';
import { User } from './../../model/user.model';
import { FilterCriteria } from './../../dto/filtercriteria.dto';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.css']
})
export class UserListComponent implements OnInit {

  user: User = new User('', '', '', '');
  filter: FilterCriteria = new FilterCriteria(this.user, 5, 0, 'ASC', null);
  shared: SharedService;
  message = {};
  classCss = {};
  listUser = [];


  constructor(
    private dialogService: DialogService,
    private userService: UserService,
    private router: Router
  ) { 
    this.shared = SharedService.getInstance();
  }

}
