import { Component } from '@angular/core';
import { HostListener } from '@angular/core';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})


export class NavbarComponent {

ngOnInit() {}
  
@HostListener("window:scroll", []) scrollevent(): void {
  const navbarElement = document.getElementById("navbar");
  const logobckgrdElement = document.getElementById("logobckgrd");

  const buttElement1 = document.getElementById("topbutt1");
  const buttElement2 = document.getElementById("topbutt2");
  const buttElement3 = document.getElementById("topbutt3");
  const buttElement4 = document.getElementById("topbutt4");

  const textElement1 = document.getElementById("linker1");
  const textElement2 = document.getElementById("linker2");
  const textElement3 = document.getElementById("linker3");
  const textElement4 = document.getElementById("linker4");

  // console.log(textElement);
    if(navbarElement && textElement1 && textElement2 && textElement3 && textElement4){
    if(document.body.scrollTop > 80 || document.documentElement.scrollTop > 80) {
      navbarElement.style.paddingTop = "0px";
      logobckgrdElement.style.marginTop = "-30px";
      textElement1.style.fontSize = "10px";
      textElement2.style.fontSize = "10px";
      textElement3.style.fontSize = "10px";
      textElement4.style.fontSize = "10px";
      
      // buttElement1.style.width = "10%";
      // buttElement2.style.width = "10%";
      // buttElement3.style.width = "10%";
      // buttElement4.style.width = "10%";
    } else {
      navbarElement.style.paddingTop = "20px";
      logobckgrdElement.style.marginTop = "-5px";
      textElement1.style.fontSize = "12px";
      textElement2.style.fontSize = "12px";
      textElement3.style.fontSize = "12px";     
      textElement4.style.fontSize = "12px";
      
      // buttElement1.style.width = "auto";
      // buttElement2.style.width = "auto";
      // buttElement3.style.width = "auto";
      // buttElement4.style.width = "auto";
    }
  }
  

}
  
}

