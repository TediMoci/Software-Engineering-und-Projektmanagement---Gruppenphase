import { TestBed, async, inject } from '@angular/core/testing';

import { FitnessProviderRoleGuard } from './fitness-provider-role.guard';

describe('FitnessProviderRoleGuard', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [FitnessProviderRoleGuard]
    });
  });

  it('should ...', inject([FitnessProviderRoleGuard], (guard: FitnessProviderRoleGuard) => {
    expect(guard).toBeTruthy();
  }));
});
