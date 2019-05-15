import { TestBed } from '@angular/core/testing';

import { EditDudeService } from './edit-dude.service';

describe('EditDudeService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: EditDudeService = TestBed.get(EditDudeService);
    expect(service).toBeTruthy();
  });
});
