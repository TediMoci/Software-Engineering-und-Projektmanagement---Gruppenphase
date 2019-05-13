import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LoginAsFitnessProviderComponent } from './login-as-fitness-provider.component';

describe('LoginAsFitnessProviderComponent', () => {
  let component: LoginAsFitnessProviderComponent;
  let fixture: ComponentFixture<LoginAsFitnessProviderComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LoginAsFitnessProviderComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LoginAsFitnessProviderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
