import { TestBed } from '@angular/core/testing';

import { OwnTrainingSchedulesService } from './own-training-schedules.service';

describe('OwnTrainingSchedulesService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: OwnTrainingSchedulesService = TestBed.get(OwnTrainingSchedulesService);
    expect(service).toBeTruthy();
  });
});
