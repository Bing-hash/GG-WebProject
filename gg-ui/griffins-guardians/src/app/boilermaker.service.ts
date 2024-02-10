import { Injectable } from '@angular/core';
import { Donor } from './donor';
import { Runner } from './runner';
import { Observable, of} from 'rxjs';
import { MessageService } from './message.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError, map, tap } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class BoilermakerService {

  baseurl = 'https://localhost:8443/runners';
  // baseurl = "https://backend.griffinsguardians.org:443/runners";

  constructor(private http: HttpClient, private messageService: MessageService) { }

  getRunners(): Observable<Runner[]> {
    return this.http.get<Runner[]>(this.baseurl);
    // const runners = of(RUNNERS);
    // this.log('grabbed runners');

    // return runners;
  }

  addDonor(runner: Runner, donor: Donor): Observable<any> {
    const body = {};
    console.log('PUT with'+runner.name+" "+donor.name+" "+donor.ammount);
    return this.http.put<any>(this.baseurl+'/'+runner.id.toString()+'/'+donor.name+'/'+donor.ammount.toString(), body).pipe(
      tap(_ => console.log('updated runner id=${runner.id}'))
      );
  }

  private log(message: string) {
    this.messageService.add('BoilermakerService: ${message}');
  }
}
