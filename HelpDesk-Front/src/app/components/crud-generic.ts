import { ResponseApi } from './../model/response-api';
import { GenericService } from './../services/generic.service';
export class CrudGeneric<T>{

    constructor(public service: GenericService<T>){

    }

    public data: Array<any>;
    public pages: number;

    find() {
        this.service.findAll(this.filter).subscribe((reponseApi: ResponseApi) => {
          this.data = reponseApi['data']['content'];
          this.pages = reponseApi['data']['totalPages'];
        }, err => {
          this.showMessage({
            type: 'error',
            text: err['error']['error']
          });
        });
      }

}