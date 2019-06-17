import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { StatisticTrainingscheduleComponent } from './statistic-trainingschedule.component';

describe('StatisticTrainingscheduleComponent', () => {
  let component: StatisticTrainingscheduleComponent;
  let fixture: ComponentFixture<StatisticTrainingscheduleComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ StatisticTrainingscheduleComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(StatisticTrainingscheduleComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
