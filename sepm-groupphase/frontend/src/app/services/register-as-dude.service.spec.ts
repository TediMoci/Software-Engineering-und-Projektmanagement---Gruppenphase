import { TestBed } from '@angular/core/testing';

import { RegisterAsDudeService } from './register-as-dude.service';

describe('RegisterAsDudeService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: RegisterAsDudeService = TestBed.get(RegisterAsDudeService);
    expect(service).toBeTruthy();
  });
});
