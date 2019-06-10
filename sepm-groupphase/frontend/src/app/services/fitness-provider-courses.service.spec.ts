import { TestBed } from '@angular/core/testing';

import { FitnessProviderCoursesService } from './fitness-provider-courses.service';

describe('FitnessProviderCoursesService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: FitnessProviderCoursesService = TestBed.get(FitnessProviderCoursesService);
    expect(service).toBeTruthy();
  });
});
