import { TestBed } from '@angular/core/testing';

import { FitnessProviderProfileService } from './fitness-provider-profile.service';

describe('FitnessProviderProfileService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: FitnessProviderProfileService = TestBed.get(FitnessProviderProfileService);
    expect(service).toBeTruthy();
  });
});
