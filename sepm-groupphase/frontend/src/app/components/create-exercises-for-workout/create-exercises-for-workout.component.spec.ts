import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateExercisesForWorkoutComponent } from './create-exercises-for-workout.component';

describe('CreateExercisesForWorkoutComponent', () => {
  let component: CreateExercisesForWorkoutComponent;
  let fixture: ComponentFixture<CreateExercisesForWorkoutComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CreateExercisesForWorkoutComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateExercisesForWorkoutComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
