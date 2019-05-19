import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { OwnTrainingScheduleComponent } from './own-training-schedule.component';

describe('OwnTrainingScheduleComponent', () => {
  let component: OwnTrainingScheduleComponent;
  let fixture: ComponentFixture<OwnTrainingScheduleComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ OwnTrainingScheduleComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(OwnTrainingScheduleComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
