import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { OwnCoursesComponent } from './own-courses.component';

describe('OwnCoursesComponent', () => {
  let component: OwnCoursesComponent;
  let fixture: ComponentFixture<OwnCoursesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ OwnCoursesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(OwnCoursesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
