import { GenericService } from './generic.service';
import { HELP_DESK_API } from './helpdesk.api';
import { User } from './../model/user.model';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable()
export class UserService extends GenericService<User> {

  constructor(public http: HttpClient) {
    super(http, 'api/user');
  }

  login(user: User) {
    return this.http.post(`${HELP_DESK_API}/api/auth`, user);
  }
}
