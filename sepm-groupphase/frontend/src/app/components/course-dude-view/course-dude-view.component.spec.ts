import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CourseDudeViewComponent } from './course-dude-view.component';

describe('CourseDudeViewComponent', () => {
  let component: CourseDudeViewComponent;
  let fixture: ComponentFixture<CourseDudeViewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CourseDudeViewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CourseDudeViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
