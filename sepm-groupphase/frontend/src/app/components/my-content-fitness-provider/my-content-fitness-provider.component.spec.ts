import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MyContentFitnessProviderComponent } from './my-content-fitness-provider.component';

describe('MyContentFitnessProviderComponent', () => {
  let component: MyContentFitnessProviderComponent;
  let fixture: ComponentFixture<MyContentFitnessProviderComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MyContentFitnessProviderComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MyContentFitnessProviderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
