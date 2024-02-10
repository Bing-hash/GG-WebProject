import { Component, AfterViewChecked, ViewChild, ElementRef, resolveForwardRef, OnInit } from '@angular/core';
import { Donor } from '../donor';
import { Runner } from '../runner';
import { BoilermakerService } from '../boilermaker.service';
import { render } from 'creditcardpayments/creditCardPayments';

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

  // public paypalConfig?: IPayPalConfig;

  authBool: boolean = false;

  

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

  delay(ms: number) {
    return new Promise( resolve => setTimeout(resolve, ms) );
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

  async changeCurrentDonor(): Promise<void> {
    let name: string = ((document.getElementById("name") as HTMLInputElement).value);
    let amount: number = +((document.getElementById("amount") as HTMLInputElement).value);
    if(amount == 0) {
      return;
    }
    let newDonor = <Donor>{name: name, ammount:amount};
    this.currentDonor = newDonor;
    await this.delay(1000)
    // this.initConfig();
    render(
      {
        id: "#myPalpalButtons",
        currency: "USD",
        value: this.currentDonor.ammount.toString(),
        onApprove: (details) => {
          console.log('Client authorized payment');
          // do stuff here on authorization
          this.onAuthorize();
        }
        
      }
    )
  }

  onAuthorize(): void {
    this.boilermakerService.addDonor(this.currRunner, this.currentDonor).subscribe(response => {});;
    this.lastDonorName = this.currentDonor.name;
    this.currentDonor = <Donor>{};
    this.authBool = true;
  }
  
}

