import { DialogService } from './../services/dialog.service';
import { FilterCriteria } from './../model/filtercriteria.dto';
import { ResponseApi } from './../model/response-api';
import { GenericService } from './../services/generic.service';
export class CrudGeneric<T>{

  constructor(public service: GenericService<T>, public dialogService: DialogService) {

  }

  public data: Array<any>;
  public pages: Array<number>;
  public totalPages: number;
  public message = {};
  public classCss = {};
  public filter = new FilterCriteria(null, 5, 0, 'ASC', null);
  public classCssPage = {};

  find() {
    this.service.findAll(this.filter).subscribe((reponseApi: ResponseApi) => {
      this.data = reponseApi['data']['content'];
      this.pages = new Array(reponseApi['data']['totalPages']);
      this.totalPages = reponseApi['data']['totalPages'];
    }, err => {
      this.showMessage({
        type: 'error',
        text: err['error']['error']
      });
    });
  }

  delete(id: string) {
    this.dialogService.confirm('Do you want to delete this register ?').then((canDelete: boolean) => {
      if (canDelete) {
        this.message = {};
        this.service.delete(id).subscribe((responseApi: ResponseApi) => {
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

  setNextPage(event: any) {
    event.preventDefault();
    if (this.filter.pageNumber + 1 < this.totalPages) {
      this.filter.pageNumber = this.filter.pageNumber + 1;
      this.find();
    }
  }

  setPreviousPage(event: any) {
    event.preventDefault();
    if (this.filter.pageNumber > 0) {
      this.filter.pageNumber = this.filter.pageNumber - 1;
      this.find();
    }
  }

  setPage(i, event: any) {
    event.preventDefault();
    this.filter.pageNumber = i;
    this.find();
  }

}