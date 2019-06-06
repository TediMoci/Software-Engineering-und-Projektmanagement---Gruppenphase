import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateTrainingScheduleManuallyComponent } from './create-training-schedule-manually.component';

describe('CreateTrainingScheduleManuallyComponent', () => {
  let component: CreateTrainingScheduleManuallyComponent;
  let fixture: ComponentFixture<CreateTrainingScheduleManuallyComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CreateTrainingScheduleManuallyComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateTrainingScheduleManuallyComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
