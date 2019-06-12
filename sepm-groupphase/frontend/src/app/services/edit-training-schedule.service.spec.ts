import { TestBed } from '@angular/core/testing';

import { EditTrainingScheduleService } from './edit-training-schedule.service';

describe('EditTrainingScheduleService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: EditTrainingScheduleService = TestBed.get(EditTrainingScheduleService);
    expect(service).toBeTruthy();
  });
});
