import { FilterCriteria } from './../dto/filtercriteria.dto';
import { HELP_DESK_API } from './helpdesk.api';
import { HttpClient } from '@angular/common/http';
export class GenericService<T> {

  public filter: FilterCriteria = new FilterCriteria(null,null,null,null,null);

  constructor(public http: HttpClient, public path: string) { }

  create(entity: T) {
    return this.http.post(`${HELP_DESK_API}/${this.path}`, entity);
  }

  update(entity: T) {
    return this.http.put(`${HELP_DESK_API}/${this.path}`, entity);
  }

  findAll(filter: FilterCriteria) {
    return this.http.post(`${HELP_DESK_API}/${this.path}/filter`, filter);
  }

  delete(id: string) {
    return this.http.delete(`${HELP_DESK_API}/${this.path}/${id}`);
  }

  findById(id: string) {
    return this.http.get(`${HELP_DESK_API}/${this.path}/${id}`);
  }
}