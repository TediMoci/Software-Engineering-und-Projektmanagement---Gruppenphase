import { TestBed } from '@angular/core/testing';

import { OwnCoursesService } from './own-courses.service';

describe('OwnCoursesService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: OwnCoursesService = TestBed.get(OwnCoursesService);
    expect(service).toBeTruthy();
  });
});
