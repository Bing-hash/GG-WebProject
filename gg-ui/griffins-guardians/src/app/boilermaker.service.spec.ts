import { TestBed } from '@angular/core/testing';

import { BoilermakerService } from './boilermaker.service';

describe('BoilermakerService', () => {
  let service: BoilermakerService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BoilermakerService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
