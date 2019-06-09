import { TestBed } from '@angular/core/testing';

import { FitnessProvidersFollowedService } from './fitness-providers-followed.service';

describe('FitnessProvidersFollowedService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: FitnessProvidersFollowedService = TestBed.get(FitnessProvidersFollowedService);
    expect(service).toBeTruthy();
  });
});
