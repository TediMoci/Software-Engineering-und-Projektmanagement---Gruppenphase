import { TestBed } from '@angular/core/testing';

import { EditCourseService } from './edit-course.service';

describe('EditCourseService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: EditCourseService = TestBed.get(EditCourseService);
    expect(service).toBeTruthy();
  });
});
