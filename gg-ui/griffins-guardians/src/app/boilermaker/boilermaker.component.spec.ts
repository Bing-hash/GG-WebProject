import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BoilermakerComponent } from './boilermaker.component';

describe('BoilermakerComponent', () => {
  let component: BoilermakerComponent;
  let fixture: ComponentFixture<BoilermakerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BoilermakerComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BoilermakerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
