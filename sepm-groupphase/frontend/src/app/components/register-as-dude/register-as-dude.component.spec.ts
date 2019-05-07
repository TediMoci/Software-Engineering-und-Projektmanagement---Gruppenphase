import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RegisterAsDudeComponent } from './register-as-dude.component';

describe('RegisterAsDudeComponent', () => {
  let component: RegisterAsDudeComponent;
  let fixture: ComponentFixture<RegisterAsDudeComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RegisterAsDudeComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RegisterAsDudeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
