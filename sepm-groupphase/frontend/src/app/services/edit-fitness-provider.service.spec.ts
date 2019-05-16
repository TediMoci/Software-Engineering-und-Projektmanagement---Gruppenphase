import { TestBed } from '@angular/core/testing';

import { EditFitnessProviderService } from './edit-fitness-provider.service';

describe('EditFitnessProviderService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: EditFitnessProviderService = TestBed.get(EditFitnessProviderService);
    expect(service).toBeTruthy();
  });
});
