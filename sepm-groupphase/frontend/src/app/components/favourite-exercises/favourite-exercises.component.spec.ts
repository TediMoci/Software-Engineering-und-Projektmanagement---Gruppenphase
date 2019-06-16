import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FavouriteExercisesComponent } from './favourite-exercises.component';

describe('FavouriteExercisesComponent', () => {
  let component: FavouriteExercisesComponent;
  let fixture: ComponentFixture<FavouriteExercisesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FavouriteExercisesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FavouriteExercisesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
