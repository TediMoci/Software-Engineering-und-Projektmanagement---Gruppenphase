import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FollowerDudeComponent } from './follower-dude.component';

describe('FollowerDudeComponent', () => {
  let component: FollowerDudeComponent;
  let fixture: ComponentFixture<FollowerDudeComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FollowerDudeComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FollowerDudeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
