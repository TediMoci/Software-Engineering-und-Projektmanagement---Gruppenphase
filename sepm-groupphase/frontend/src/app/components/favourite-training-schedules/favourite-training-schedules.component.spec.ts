import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FavouriteTrainingSchedulesComponent } from './favourite-training-schedules.component';

describe('FavouriteTrainingSchedulesComponent', () => {
  let component: FavouriteTrainingSchedulesComponent;
  let fixture: ComponentFixture<FavouriteTrainingSchedulesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FavouriteTrainingSchedulesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FavouriteTrainingSchedulesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
