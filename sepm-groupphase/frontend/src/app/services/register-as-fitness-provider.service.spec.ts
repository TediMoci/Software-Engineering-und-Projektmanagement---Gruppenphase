import { TestBed } from '@angular/core/testing';

import { RegisterAsFitnessProviderService } from './register-as-fitness-provider.service';

describe('RegisterAsFitnessProviderService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: RegisterAsFitnessProviderService = TestBed.get(RegisterAsFitnessProviderService);
    expect(service).toBeTruthy();
  });
});
