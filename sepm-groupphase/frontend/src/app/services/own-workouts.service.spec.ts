import { TestBed } from '@angular/core/testing';

import { OwnWorkoutsService } from './own-workouts.service';

describe('OwnWorkoutsService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: OwnWorkoutsService = TestBed.get(OwnWorkoutsService);
    expect(service).toBeTruthy();
  });
});
