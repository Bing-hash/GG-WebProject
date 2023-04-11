import { Component, AfterViewChecked, ViewChild, ElementRef, resolveForwardRef } from '@angular/core';
import { Donor } from '../donor';
import { Runner } from '../runner';
import { BoilermakerService } from '../boilermaker.service';
import { IPayPalConfig, ICreateOrderRequest } from 'ngx-paypal/public_api';

declare let paypal: any;


@Component({
  selector: 'app-boilermaker',
  templateUrl: './boilermaker.component.html',
  styleUrls: ['./boilermaker.component.css']
})
export class BoilermakerComponent {
  

  constructor(private boilermakerService: BoilermakerService) {}

  ngOnInit(): void {
    this.getRunners();
  }

  addScript: boolean = false;

  runners: Runner[] = [];
  currRunner = <Runner>{};
  currRunnerPercent: number = 0;
  percentBarLength: number = 0;
  flip: boolean = false;

  ammountInp: number;
  nameInp: string;

  currentDonor = <Donor>{};
  lastDonorName: String;

  public paypalConfig?: IPayPalConfig;

  authBool: boolean = false;

  private initConfig(): void {
    this.paypalConfig = {
      currency: 'USD',
      clientId: 'ATSIJPSRpc_GDmcSISF9LLMHu8Gtmwotvz7Hd6fbp5JvaAyqkrb77hbyqCUnVVOM7fahjU_LlAf7OS8p',
      createOrderOnClient: (data) => <ICreateOrderRequest>{
        intent: 'CAPTURE',
        purchase_units: [
          {
            amount: {
              currency_code: 'USD',
              value: this.currentDonor.ammount.toString(),
              breakdown: {
                item_total: {
                  currency_code: 'USD',
                  value: this.currentDonor.ammount.toString()
                }
              }
            },
            // items: [
            //   {
            //     name: 'Donation to '+this.currRunner.name,
            //     quantity: '1',
            //     category
                
            //   }
            // ]
          }
        ]
      },
      advanced: {
        commit: 'true'
      },
      style: {
        label: 'paypal',
        layout: 'vertical'
      },
      onApprove: (data, actions) => {
        console.log('onApprove - transaction was approved, but not authorized', data, actions);
        actions.order.get().then(details => {
          console.log('onApprove - you can get full order details inside onApprove: ', details);
        });
      },
      onClientAuthorization: (data) => {
        console.log('Client authorized payment');
        // do stuff here on authorization
        this.onAuthorize();
      },
      onError: err => {
        console.log('OnError', err);
      }
    };
  }

  

  getRunners(): void {
    this.boilermakerService.getRunners()
      .subscribe(runners => this.runners = runners);

    
  }

  calcTotal(run: Runner): void {
    let tempTotal = 0;
    for (let i = 0; i<run.donors.length; i++) {
      tempTotal += run.donors[i].ammount;
    }
    run.total = tempTotal;

    
  }

  changeCurrentRunner(id: number): void {
    this.authBool = false;
    this.currentDonor = <Donor>{};
    for(let i = 0; i<this.runners.length; i++){
      if(this.runners[i].id == id) {
        this.currRunner = this.runners[i];
      }
    }
    this.calcTotal(this.currRunner);

    this.currRunnerPercent = Math.round(this.currRunner.total / this.currRunner.goal * 100);
    if(this.currRunnerPercent > 100) {
      this.currRunnerPercent = 100;
    }

    this.percentBarLength = this.currRunnerPercent / 100 *521
  }

  // Used to create alternate color pattern for donor list
  flipper(): boolean {
    this.flip = !this.flip;
    return this.flip;
  }

  changeCurrentDonor(): void {
    let name: string = ((document.getElementById("name") as HTMLInputElement).value);
    let amount: number = +((document.getElementById("amount") as HTMLInputElement).value);
    if(amount == 0) {
      return;
    }
    let newDonor = <Donor>{name: name, ammount:amount};
    this.currentDonor = newDonor;
    this.initConfig();
  }

  onAuthorize(): void {
    this.boilermakerService.addDonor(this.currRunner, this.currentDonor).subscribe(response => {});;
    this.lastDonorName = this.currentDonor.name;
    this.currentDonor = <Donor>{};
    this.authBool = true;
  }
  
}

