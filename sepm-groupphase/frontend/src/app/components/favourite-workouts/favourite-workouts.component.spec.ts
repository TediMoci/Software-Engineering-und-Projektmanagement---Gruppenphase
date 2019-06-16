import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FavouriteWorkoutsComponent } from './favourite-workouts.component';

describe('FavouriteWorkoutsComponent', () => {
  let component: FavouriteWorkoutsComponent;
  let fixture: ComponentFixture<FavouriteWorkoutsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FavouriteWorkoutsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FavouriteWorkoutsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
